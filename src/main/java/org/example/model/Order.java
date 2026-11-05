package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
public class Order {
    private Set<ProductItem> products;

    private OrderStatus status;

    private final LocalDateTime createdAt;
    private LocalDateTime orderedAt;

    public Order() {
        this.products = new HashSet<>();
        this.status = OrderStatus.NOT_COMPLETED;
        this.createdAt = LocalDateTime.now();
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Invalid product or quantity"
            );
        }

        addProduct(
                product.getCode(),
                product.getName(),
                quantity
        );
    }

    public void addProduct(Integer code, String name, Integer quantity) {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Cannot modify completed order"
            );
        }

        if (code == null || name == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException(
                    "Invalid product data"
            );
        }

        for (ProductItem item : products) {
            if (item.getCode().equals(code)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        products.add(new ProductItem(code, name, quantity));
    }

    public boolean removeProduct(Integer code) {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Cannot modify completed order"
            );
        }

        return products.removeIf(
                item -> item.getCode().equals(code)
        );
    }

    public boolean changeQuantity(Integer code, Integer quantity) {
        if (status == OrderStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Cannot modify completed order"
            );
        }

        if (code == null || quantity == null || quantity <= 0) {
            return false;
        }

        for (ProductItem item : products) {
            if (item.getCode().equals(code)) {
                item.setQuantity(quantity);
                return true;
            }
        }

        return false;
    }

    public void completeOrder() {
        if (status == OrderStatus.COMPLETED) {
            return;
        }

        if (products.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot complete empty order"
            );
        }

        this.status = OrderStatus.COMPLETED;
        this.orderedAt = LocalDateTime.now();
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class ProductItem  {
        private Integer code;
        private String name;
        private Integer quantity;
    }
}
