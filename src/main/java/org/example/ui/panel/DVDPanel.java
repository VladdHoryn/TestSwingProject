package org.example.ui.panel;

import lombok.Setter;
import org.example.model.DVDRectangle;
import org.example.ui.controller.DVDController;

import javax.swing.*;
import java.awt.*;

public class DVDPanel extends JPanel {
    private DVDRectangle rect;

    @Setter
    private DVDController controller;

    private Color dvdColor = Color.BLACK;

    public DVDPanel(DVDRectangle rect) {
        setBackground(Color.WHITE);

        this.rect = rect;

        setLayout(new BorderLayout());
    }

    public DVDPanel(DVDRectangle rect, DVDController controller) {
        setBackground(Color.WHITE);

        this.rect = rect;
        this.controller = controller;

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
            g2.setColor(rect.getColor());
            g2.fillRect((int) rect.getX(), (int) rect.getY(), rect.getWidth(), rect.getHeight());
        } finally {
            g2.dispose();
        }
    }

    public void startAnimation() { controller.startAnimation(); }

    public void stopAnimation() {
        controller.stopAnimation();
    }

    public int getPanelWidth() {
        return getWidth();
    }

    public int getPanelHeight() {
        return getHeight();
    }
}
