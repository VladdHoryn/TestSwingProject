package org.example.model;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product{
    private Integer code;
    private String name;
    private Double diagonalSize;
    private Double width;
    private Double height;
    private String displayTechnology;
    private BigDecimal price;
    private Integer quantity;

    @Override
    public String toString(){
        return code + " " + name + " " + diagonalSize + " " + width + " " + height + " " +
                displayTechnology + " " + price + " " + quantity;
    }
}
