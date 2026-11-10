package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DVDRectangle {
    private double x = 100;
    private double y = 100;

    private double velocityX = 150;
    private double velocityY = 100;

    private final int width = 80;
    private final int height = 50;
}
