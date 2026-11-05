package org.example.repository;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class CsvOrderRepository implements OrderRepository{
    private final Path csvPath;

    private static final String HEADER =
            "order_id,created_at,ordered_at,status,product_code,product_name,quantity";

    public CsvOrderRepository(Path path) {
        this.csvPath = path;
    }

    @Override
    public List<Order> findAll() {
        try {
            if (!Files.exists(csvPath)) {
                return new ArrayList<>();
            }

            Map<Integer, Order> orders = new LinkedHashMap<>();

            List<String> lines = Files.readAllLines(csvPath);

            for (int i = 1; i < lines.size(); i++) {
                String[] parts = parseCsvLine(lines.get(i));

                int id = Integer.parseInt(parts[0]);
                LocalDateTime createdAt =
                        LocalDateTime.parse(parts[1]);

                LocalDateTime orderedAt =
                        parts[2].isEmpty()
                                ? null
                                : LocalDateTime.parse(parts[2]);

                OrderStatus status =
                        OrderStatus.valueOf(parts[3]);

                int productCode = Integer.parseInt(parts[4]);
                String productName = parts[5];
                int quantity = Integer.parseInt(parts[6]);

                Order order = orders.computeIfAbsent(
                        id,
                        key -> new Order(
                                id,
                                createdAt,
                                orderedAt,
                                status
                        )
                );

                order.restoreProduct(
                        productCode,
                        productName,
                        quantity
                );
            }

            return orders.values().stream()
                    .sorted(orderComparator())
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot read orders CSV", e
            );
        }
    }
    @Override
    public Order create(Order order) {
        try {
            List<Order> orders = findAll();
            orders.add(order);

            rewriteCsv(orders);

            return order;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot create order in CSV", e
            );
        }
    }
    @Override
    public Order update(Order order) {
        try {
            if (order.getId() == null) {
                throw new IllegalArgumentException(
                        "Order ID cannot be null"
                );
            }

            List<Order> orders = findAll();

            boolean found = false;

            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getId().equals(order.getId())) {
                    orders.set(i, order);
                    found = true;
                    break;
                }
            }

            if (!found) {
                return null;
            }

            rewriteCsv(orders);

            return order;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot update order in CSV", e
            );
        }
    }

    @Override
    public Order delete(Order order) {
        try {
            if (order.getId() == null) {
                return null;
            }

            List<Order> orders = findAll();

            Order deletedOrder = orders.stream()
                    .filter(o -> o.getId().equals(order.getId()))
                    .findFirst()
                    .orElse(null);

            if (deletedOrder == null) {
                return null;
            }

            orders.removeIf(
                    o -> o.getId().equals(order.getId())
            );

            rewriteCsv(orders);

            return deletedOrder;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Cannot delete order from CSV", e
            );
        }
    }

    private void rewriteCsv(List<Order> orders)
            throws IOException, IOException {

        List<String> lines = new ArrayList<>();

        lines.add(HEADER);

        orders.stream()
                .sorted(orderComparator())
                .forEach(order -> {

                    for (Order.ProductItem item : order.getProducts()) {

                        String line = String.join(",",
                                order.getId().toString(),
                                order.getCreatedAt().toString(),
                                order.getOrderedAt() == null
                                        ? ""
                                        : order.getOrderedAt().toString(),
                                order.getStatus().name(),
                                item.getCode().toString(),
                                escapeCsv(item.getName()),
                                item.getQuantity().toString()
                        );

                        lines.add(line);
                    }
                });

        Files.write(
                csvPath,
                lines,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    private Comparator<Order> orderComparator() {
        return Comparator
                .comparing(
                        Order::getOrderedAt,
                        Comparator.nullsLast(Comparator.naturalOrder())
                )
                .thenComparing(Order::getCreatedAt);
    }

    private String escapeCsv(String value) {
        if (value.contains(",") ||
                value.contains("\"") ||
                value.contains("\n")) {

            return "\"" + value.replace("\"", "\"\"") + "\"";
        }

        return value;
    }

    private String[] parseCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();

        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (insideQuotes &&
                        i + 1 < line.length() &&
                        line.charAt(i + 1) == '"') {

                    current.append('"');
                    i++;
                } else {
                    insideQuotes = !insideQuotes;
                }
            } else if (c == ',' && !insideQuotes) {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        parts.add(current.toString());

        return parts.toArray(new String[0]);
    }
}
