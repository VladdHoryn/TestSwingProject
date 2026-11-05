package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Order {
    private Set<Pair> products;

    public Order() {
        this.products = new HashSet<>();
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity");
        }

        changeQuantity(product, quantity + getQuantity(product));

        if (getQuantity(product) == 0) {
            products.add(new Pair(product, quantity));
        }

        products.add(new Pair(product, quantity));
    }

    public Boolean removeProduct(Pair pair) {
        return products.remove(pair);
    }

    public Boolean changeQuantity(Product product, Integer quantity) {
        if(quantity == null || quantity <= 0)
            return false;

        for(Pair p : products) {
            if(p.getProduct().equals(product))
                p.setQuantity(quantity);
        }
        return true;
    }

    private int getQuantity(Product product) {
        return products.stream()
                .filter(pair -> pair.getProduct().equals(product))
                .mapToInt(Pair::getQuantity)
                .findFirst()
                .orElse(0);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class Pair {
        private Product product;
        private Integer quantity;
    }
}
