package org.example.service;

import org.example.model.Order;
import org.example.model.Product;
import org.example.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public Product findByCode(Integer code){ //Не впевнений чи це хороший підхід, але не хотів через кепо писати це
        for(Product p : productRepository.findAll()){
            if(p.getCode().equals(code))
                return p;
        }
        return null;
    }

    public String validateStock(Set<Order.ProductItem> productItems){
        Optional<Order.ProductItem> item;

        StringBuffer result = new StringBuffer();

        List<Product> products = findAll();
        for(Product p : products){
            item = productItems.stream()
                    .filter(i -> i.equalProduct(p))
                    .findFirst();

            if(item.isPresent())
                if(new Order.ProductItem(p).equalProduct(item.get()) && p.getQuantity() < item.get().getQuantity())
                    result.append(p.getName());
        }

        return result.toString();
    }

    public void updateStock(Order order){
        Optional<Order.ProductItem> item;

        List<Product> products = findAll();
        for(Product p : products){
            item = order.getProducts().stream()
                    .filter(i -> i.equalProduct(p))
                    .findFirst();

            if(item.isPresent()) {
                p.setQuantity(p.getQuantity() - item.get().getQuantity());
                this.update(p); //Містить в собі N+1 проблему, але це проблема на мабутнє
            }
        }
    }
}
