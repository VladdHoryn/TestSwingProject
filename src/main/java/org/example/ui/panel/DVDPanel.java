package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

public class DVDPanel extends JPanel {
    private static final int HIGHT = 50;
    private static final int WIDTH = 100;
    private double x = 0, y = 100;
    private double vx = 70, vy = 30;
    private long lastTime;

    public DVDPanel() {
        setBackground(Color.WHITE);
        lastTime = System.nanoTime();
        new Timer(16, e -> tick()).start();
    }

    private void tick(){
        long now = System.nanoTime();
        double delta = (now - lastTime) / 1_000_000_000; //Time passed
        lastTime = now;

        x = x + vx * delta;
        y = y + vy * delta;

        if(x < 0){
            x = 0;
            vx = -vx;
        }
        if(x + WIDTH > getWidth()){
            x = getWidth() -  WIDTH;
            vx = -vx;
        }
        if(y < 0){
            y = 0;
            vy = -vy;
        }
        if(y + HIGHT > getHeight()){
            y = getHeight() -  HIGHT;
            vy = -vy;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
    }
}
