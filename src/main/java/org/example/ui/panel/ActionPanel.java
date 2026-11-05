package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

public class ActionPanel extends JPanel{
    private JButton addButton;
    private JButton sellButton;

    public ActionPanel(){
        setLayout(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        add(addButton);
        add(sellButton);
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getSellButton() {
        return sellButton;
    }
}
