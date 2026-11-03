package org.example.repository;

import org.example.model.Product;

import java.util.List;

public interface ProductRepository {

    public List<Product> findAll();
    public Product create(Product product);
    public Product update(Product product);
    public Boolean delete(Product product);
}
