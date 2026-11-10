package org.example.ui.controller;


import lombok.Setter;
import org.example.listener.AnimationListener;
import org.example.model.DVDRectangle;
import org.example.ui.panel.DVDPanel;

import javax.swing.*;
import java.awt.*;

public class DVDController implements AnimationListener {
    private final DVDRectangle rectangle;

    @Setter
    private DVDPanel panel;

    public DVDController(
            DVDRectangle rectangle,
            DVDPanel panel
    ) {
        this.rectangle = rectangle;
        this.panel = panel;

        rectangle.setAnimationListener(this);
    }


    @Override
    public void onPositionUpdated() {
        SwingUtilities.invokeLater(panel::repaint);
    }

    public void startAnimation() {
        rectangle.getCalculations().start();
    }

    public void stopAnimation() {
        rectangle.getCalculations().stop();
    }
}
