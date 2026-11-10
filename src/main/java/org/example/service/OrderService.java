package org.example.service;

import org.example.model.Order;
import org.example.model.OrderStatus;
import org.example.model.Product;
import org.example.repository.OrderRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;

    private static Integer highestCode = 0;

    public OrderService(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;

        findAll(); //Костиль, але мені потрібно назначати Id
    }

    public List<Order> findAll(){
        List<Order> result = orderRepository.findAll();

        for(var i : result){
            if(i.getId() > highestCode)
                highestCode = i.getId();
        }

        return result;
    };

    public Optional<Order> findById(Integer id){
        for(Order i : this.findAll()){
            if(i.getId().equals(id))
                return Optional.of(i);
        }

        return Optional.empty();
    }

    public Order createOrder() {

        Order newOrder =  new Order();
        newOrder.setId(++highestCode);

        return newOrder;
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
