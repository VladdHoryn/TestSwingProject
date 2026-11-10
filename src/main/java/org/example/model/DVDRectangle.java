package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.listener.AnimationListener;

import javax.swing.*;
import java.awt.*;

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

    private final int panelWidth;
    private final int panelHeight;

    private Color color;

    private AnimationCalculations calculations;

    public DVDRectangle(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;

        this.color = Color.BLACK;
    }

    public void setAnimationListener(AnimationListener listener) {
        calculations.setListener(listener);
    }

    public DVDRectangle(AnimationListener listener, int panelWidth, int panelHeight) {
        calculations = new AnimationCalculations(listener);

        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
    }

    public class AnimationCalculations implements Runnable {
        @Setter
        private AnimationListener listener;

        private Boolean changeColor = false;

        private volatile boolean running;

        public AnimationCalculations(AnimationListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {

            long lastTime = System.nanoTime();

            while (running) {

                long now = System.nanoTime();

                double delta = (now - lastTime) / 1_000_000_000.0;

                lastTime = now;

                setX(getX() + getVelocityX() * delta);
                setY(getY() + getVelocityY() * delta);

                if (getX() < 0) {
                    setX(0);
                    setVelocityX(-getVelocityX());


                    changeColor = true;
                }

                if (getX() + getWidth() > getPanelWidth()) {
                    setX(Math.max(0, getPanelWidth() - getWidth()));
                    setVelocityX(-getVelocityX());

                    changeColor = true;
                }

                if (getY() < 0) {
                    setY(0);
                    setVelocityY(-getVelocityY());

                    changeColor = true;
                }

                if (getY() + getHeight() > getPanelHeight()) {
                    setY(Math.max(0, getPanelHeight() - getHeight()));
                    setVelocityY(-getVelocityY());

                    changeColor = true;
                }

                if(changeColor){
                    color = this.getRandomColor();
                    changeColor = false;
                }

                listener.onPositionUpdated();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private Color getRandomColor(){
            return new Color(
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256),
                    (int) (Math.random() * 256)
            );
        }

        public void start() {
            if (running) return;

            running = true;

            new Thread(this, "DVD-Animation").start();
        }

        public void stop() {
            running = false;
        }
    }
}
