package org.example.service;

import org.example.model.Product;
import org.example.repository.ProductRepository;

import java.util.List;

public class ProductService {
    private ProductRepository productRepository;

    private static Integer highestCode = 0;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        List<Product> result = productRepository.findAll();

        for(var i : result){
            if(i.getCode() > highestCode)
                highestCode = i.getCode();
        }

        return result;
    };
    public Product create(Product product){
        product.setCode(++highestCode);
        return productRepository.create(product);
    };
    public Product update(Product product){
        return productRepository.update(product);
    };
    public Boolean delete(Product product){
        return productRepository.delete(product);
    };
}
