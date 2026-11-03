package org.example.model;

import java.math.BigDecimal;

public record Product(Integer code, String name, Double diagonal_size, Double width, Double height,
                      String display_technology, BigDecimal price, Integer quantity) {
}
