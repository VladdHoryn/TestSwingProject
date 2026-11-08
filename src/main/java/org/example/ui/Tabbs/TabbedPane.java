package org.example.ui.Tabbs;

import org.example.ui.panel.OrderPanel;
import org.example.ui.panel.ProductsPanel;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JTabbedPane {
    private final ProductsPanel productsPanel;
    private final OrderPanel orderPanel;

    public TabbedPane(ProductsPanel productsPanel, OrderPanel orderPanel) {
        this.productsPanel = productsPanel;
        this.orderPanel = orderPanel;

        this.addTab("Products", this.productsPanel);
        this.addTab("Orders", this.orderPanel);

    }
}
