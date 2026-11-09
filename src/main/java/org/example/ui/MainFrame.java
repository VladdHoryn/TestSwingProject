package org.example.ui;

import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.Tabbs.TabbedPane;
import org.example.ui.controller.OrderHistoryController;
import org.example.ui.controller.OrderHistorySecondaryController;
import org.example.ui.controller.ProductTableController;
import org.example.ui.dialog.AddProductDialog;
import org.example.ui.dialog.SellProductDialog;
import org.example.ui.panel.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainFrame extends JFrame {
    private final TabbedPane tabbedPane;


    public MainFrame(ProductService productService,
                     OrderService orderService
                     ) {
        OrderHistoryPanel orderHistoryPanel = new OrderHistoryPanel();
        OrderHistorySecondaryPanel orderHistorySecondaryPanel = new OrderHistorySecondaryPanel();
        OrderHistorySecondaryController orderHistorySecondaryController =
                new OrderHistorySecondaryController(
                        orderService,
                        orderHistorySecondaryPanel
                );
        OrderHistoryController orderHistoryController =
                new OrderHistoryController(
                        orderService,
                        orderHistoryPanel,
                        orderHistorySecondaryController
                );

        this.tabbedPane = new TabbedPane(
                new ProductsPanel(productService, orderService),
                new OrderPanel(orderHistoryPanel, orderHistorySecondaryPanel,
                        orderHistoryController, orderHistorySecondaryController),
                orderHistoryController,
                new DVDPanel()
        );

        initializeLayout();

        setTitle("Inventory Management");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
