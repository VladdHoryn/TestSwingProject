package org.example.ui.controller;


import org.example.model.DVDRectangle;
import org.example.ui.panel.DVDPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DVDAnimation implements Runnable {

    private volatile boolean running;

    private DVDRectangle rect;

    private final DVDPanel panel;

    private final Random random = new Random();

    private Boolean changeColor = false;

    public DVDAnimation(DVDPanel panel, DVDRectangle rect) {

        this.panel = panel;
        this.rect = rect;
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();

        while (running) {

            long now = System.nanoTime();

            double delta =
                    (now - lastTime) / 1_000_000_000.0;

            lastTime = now;

            rect.setX(rect.getX() + rect.getVelocityX() * delta);
            rect.setY(rect.getY() + rect.getVelocityY() * delta);

            if (rect.getX() < 0) {
                rect.setX(0);
                rect.setVelocityX(-rect.getVelocityX());


                changeColor = true;
            }

            if (rect.getX() + rect.getWidth() > panel.getWidth()) {
                rect.setX(Math.max(0, panel.getWidth() - rect.getWidth()));
                rect.setVelocityX(-rect.getVelocityX());

                changeColor = true;
            }

            if (rect.getY() < 0) {
                rect.setY(0);
                rect.setVelocityY(-rect.getVelocityY());

                changeColor = true;
            }

            if (rect.getY() + rect.getHeight() > panel.getHeight()) {
                rect.setY(Math.max(0, panel.getHeight() - rect.getHeight()));
                rect.setVelocityY(-rect.getVelocityY());

                changeColor = true;
            }

            int drawX = (int) rect.getX();
            int drawY = (int) rect.getY();

            SwingUtilities.invokeLater(() -> {
                if(changeColor) {
                    panel.updateColor(this.generateRandomColor());
                    changeColor = false;
                }

                panel.updatePosition(drawX, drawY);
            });


            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void start() {
        if (running) return;

        running = true;

        Thread thread = new Thread(this, "DVD-Animation");
        thread.start();
    }

    public void stop() {
        running = false;
    }

    private Color generateRandomColor() {
        return new Color(
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
        );
    }


}

