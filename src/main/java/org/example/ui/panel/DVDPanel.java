package org.example.ui.panel;

import org.example.model.DVDRectangle;
import org.example.ui.controller.DVDAnimation;

import javax.swing.*;
import java.awt.*;

public class DVDPanel extends JPanel {
    private DVDRectangle rect;

    private final DVDAnimation animation;

    private Color dvdColor = Color.BLACK;

    public DVDPanel() {
        setBackground(Color.WHITE);

        rect = new DVDRectangle();

        animation = new DVDAnimation(this, rect);
        setLayout(new BorderLayout());
    }

    public void updatePosition(int x, int y) {
        rect.setX(x);
        rect.setY(y);
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
            g2.fillRect((int) rect.getX(), (int) rect.getY(), rect.getWidth(), rect.getHeight());
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
