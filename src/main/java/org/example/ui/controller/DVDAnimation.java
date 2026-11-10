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

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (rect.getX() + rect.getWidth() > panel.getWidth()) {
                rect.setX(Math.max(0, panel.getWidth() - rect.getWidth()));
                rect.setVelocityX(-rect.getVelocityX());

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (rect.getY() < 0) {
                rect.setY(0);
                rect.setVelocityY(-rect.getVelocityY());

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (rect.getY() + rect.getHeight() > panel.getHeight()) {
                rect.setY(Math.max(0, panel.getHeight() - rect.getHeight()));
                rect.setVelocityY(-rect.getVelocityY());

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            int drawX = (int) rect.getX();
            int drawY = (int) rect.getY();

            SwingUtilities.invokeLater(() -> {
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

