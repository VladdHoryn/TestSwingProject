package org.example.repository;

import org.example.model.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> findAll();
    public Product create();
    public Boolean delete(Product product);
    public Boolean update(Product product);
}
