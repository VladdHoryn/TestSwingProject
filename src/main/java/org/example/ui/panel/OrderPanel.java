package org.example.ui.panel;

import org.example.ui.controller.OrderHistoryController;
import org.example.ui.controller.OrderHistorySecondaryController;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {
    private final OrderHistoryPanel orderHistoryPanel;
    private final OrderHistorySecondaryPanel orderHistorySecondaryPanel;

    private final OrderHistoryController orderHistoryController;
    private final OrderHistorySecondaryController orderHistorySecondaryController;

    public OrderPanel(
            OrderHistoryPanel orderHistoryPanel,
            OrderHistorySecondaryPanel orderHistorySecondaryPanel,
            OrderHistoryController orderHistoryController,
            OrderHistorySecondaryController orderHistorySecondaryController
    ) {
        this.orderHistoryPanel = orderHistoryPanel;
        this.orderHistorySecondaryPanel = orderHistorySecondaryPanel;
        this.orderHistoryController = orderHistoryController;
        this.orderHistorySecondaryController = orderHistorySecondaryController;

        initializeLayout();
    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                orderHistoryPanel,
                orderHistorySecondaryPanel
        );

        splitPane.setResizeWeight(0.6);
        splitPane.setDividerLocation(0.6);

        add(splitPane, BorderLayout.CENTER);
    }
}
