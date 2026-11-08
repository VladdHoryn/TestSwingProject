package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    private final OrderHistoryPanel orderHistoryPanel;

    public OrderPanel(OrderHistoryPanel orderHistoryPanel) {
        this.orderHistoryPanel = orderHistoryPanel;

        initializeLayout();
    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        add(orderHistoryPanel, BorderLayout.NORTH);

        setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }
}
