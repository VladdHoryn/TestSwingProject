package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository;

    public ProductService() {
        this.productRepository = new ProductRepository();
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    };
    public Product create(Product product){
        return productRepository.create(product);
    };
    public Product update(Product product){
        return productRepository.update(product);
    };
    public Boolean delete(Product product){
        return productRepository.delete(product);
    };
}
