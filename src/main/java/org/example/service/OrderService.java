package org.example.service;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.repository.OrderRepository;

import java.util.Comparator;
import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder() {
        return new Order();
    }

    public void addProduct(
            Order order,
            Integer code,
            String name,
            Integer quantity
    ) {
        validateOrder(order);

        order.addProduct(code, name, quantity);
    }

    public boolean changeQuantity(
            Order order,
            Integer code,
            Integer quantity
    ) {
        validateOrder(order);

        return order.changeQuantity(code, quantity);
    }

    public boolean removeProduct(Order order, Integer code) {
        validateOrder(order);

        return order.removeProduct(code);
    }

    public Order completeOrder(Order order) {
        validateOrder(order);

        order.completeOrder();

        return orderRepository.create(order);
    }

    public List<Order> getOrderHistory() {
        return orderRepository.findAll()
                .stream()
                .filter(order ->
                        order.getStatus() == OrderStatus.COMPLETED
                )
                .sorted(
                        Comparator.comparing(Order::getOrderedAt)
                                .reversed()
                )
                .toList();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order deleteOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null"
            );
        }

        return orderRepository.delete(order);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException(
                    "Order cannot be null"
            );
        }

        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException(
                    "Order is already completed"
            );
        }
    }
}
