package org.example.repository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {
    public List<Order> findAll();
    public Order create(Order product);
    public Order update(Order product);
    public Order delete(Order product);
}
