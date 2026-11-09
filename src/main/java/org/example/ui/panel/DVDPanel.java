package org.example.ui.panel;

import org.example.ui.animation.DVDAnimation;

import javax.swing.*;
import java.awt.*;

public class DVDPanel extends JPanel {
    private static final int HEIGHT = 50;
    private static final int WIDTH = 80;

    private int x = 100;
    private int y = 100;

    private final DVDAnimation animation;

    private Color dvdColor = Color.BLACK;

    public DVDPanel() {
        setBackground(Color.WHITE);

        animation = new DVDAnimation(this);
        setLayout(new BorderLayout());
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
        repaint();
    }

    public void updateColor(Color color) {
        this.dvdColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        try{
            g2.setColor(dvdColor);
            g2.fillRect((int) x, (int) y, WIDTH, HEIGHT);
        } finally {
            g2.dispose();
        }
    }

    public void startAnimation() {
        animation.start();
    }

    public void stopAnimation() {
        animation.stop();
    }
}
