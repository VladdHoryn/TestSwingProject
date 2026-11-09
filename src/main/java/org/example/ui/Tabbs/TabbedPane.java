package org.example.ui.Tabbs;

import org.example.ui.controller.OrderHistoryController;
import org.example.ui.panel.DVDPanel;
import org.example.ui.panel.OrderHistoryPanel;
import org.example.ui.panel.OrderPanel;
import org.example.ui.panel.ProductsPanel;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JTabbedPane {
    private final ProductsPanel productsPanel;
    private final OrderPanel orderPanel;
    private final DVDPanel dvdPanel;
    private final OrderHistoryController orderHistoryController;

    public TabbedPane(
            ProductsPanel productsPanel,
            OrderPanel orderPanel,
            OrderHistoryController orderHistoryController,
            DVDPanel dvdPanel
    ) {
        this.productsPanel = productsPanel;
        this.orderPanel = orderPanel;
        this.orderHistoryController = orderHistoryController;
        this.dvdPanel = dvdPanel;

        this.addTab("Products", this.productsPanel);
        this.addTab("Orders", this.orderPanel);
        this.addTab("DVD", this.dvdPanel);

        addChangeListener(e -> {

            if (getSelectedComponent() == orderPanel) {
                orderHistoryController.refreshOrders();
            }
        });

    }
}
