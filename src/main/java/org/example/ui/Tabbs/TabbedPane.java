package org.example.ui.Tabbs;

import org.example.ui.panel.ProductsPanel;

import javax.swing.*;
import java.awt.*;

public class TabbedPane extends JTabbedPane {
    private final ProductsPanel productsPanel;

    public TabbedPane(ProductsPanel productsPanel) {
        this.productsPanel = productsPanel;

        this.addTab("Products", this.productsPanel);

    }
}
