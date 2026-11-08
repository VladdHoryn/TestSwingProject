package org.example.ui.panel;

import org.example.ui.controller.OrderHistoryController;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    private final OrderHistoryPanel orderHistoryPanel;

    private final OrderHistoryController orderHistoryController;

    public OrderPanel(
            OrderHistoryPanel orderHistoryPanel,
            OrderHistoryController orderHistoryController
    ) {
        this.orderHistoryPanel = orderHistoryPanel;
        this.orderHistoryController = orderHistoryController;

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
