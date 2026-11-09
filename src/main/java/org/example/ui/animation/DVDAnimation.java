package org.example.ui.animation;


import org.example.ui.panel.DVDPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class DVDAnimation implements Runnable {

    private volatile boolean running;

    private double x = 100;
    private double y = 100;

    private double vx = 150;
    private double vy = 100;

    private final int width = 80;
    private final int height = 50;

    private final DVDPanel panel;

    private final Random random = new Random();

    public DVDAnimation(DVDPanel panel) {
        this.panel = panel;
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();

        while (running) {

            long now = System.nanoTime();

            double delta =
                    (now - lastTime) / 1_000_000_000.0;

            lastTime = now;

            x += vx * delta;
            y += vy * delta;

            if (x < 0) {
                x = 0;
                vx = -vx;

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (x + width > panel.getWidth()) {
                x = Math.max(0, panel.getWidth() - width);
                vx = -vx;

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (y < 0) {
                y = 0;
                vy = -vy;

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            if (y + height > panel.getHeight()) {
                y = Math.max(0, panel.getHeight() - height);
                vy = -vy;

                SwingUtilities.invokeLater(() -> {
                    panel.updateColor(this.generateRandomColor());
                });
            }

            int drawX = (int) x;
            int drawY = (int) y;

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

