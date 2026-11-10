package org.example.ui;

import org.example.model.DVDRectangle;
import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.Tabbs.TabbedPane;
import org.example.ui.controller.DVDController;
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

    private final int WIDTH = 1000;
    private final int HEIGHT = 600;


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

        DVDRectangle dvdRectangle = new DVDRectangle(WIDTH, HEIGHT);
        DVDPanel dvdPanel = new DVDPanel(dvdRectangle);

        DVDController dvdController =
                new DVDController(dvdRectangle, dvdPanel);

        dvdController.setPanel(dvdPanel);

        this.tabbedPane = new TabbedPane(
                new ProductsPanel(productService, orderService),
                new OrderPanel(orderHistoryPanel, orderHistorySecondaryPanel,
                        orderHistoryController, orderHistorySecondaryController),
                orderHistoryController,
                dvdPanel
        );

        initializeLayout();

        setTitle("Inventory Management");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
