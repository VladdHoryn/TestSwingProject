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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MainFrame extends JFrame {
    private final int WIDTH = 1000;
    private final int HEIGHT = 600;

    // Panels
    private final OrderHistoryPanel orderHistoryPanel;
    private final OrderHistorySecondaryPanel orderHistorySecondaryPanel;
    private final DVDPanel dvdPanel;

    // Controllers
    private final OrderHistoryController orderHistoryController;
    private final OrderHistorySecondaryController orderHistorySecondaryController;
    private final DVDController dvdController;

    // Main container
    private final TabbedPane tabbedPane;


    public MainFrame(
            ProductService productService,
            OrderService orderService
    ) {

        // Initialization
        orderHistoryPanel = new OrderHistoryPanel();
        orderHistorySecondaryPanel = new OrderHistorySecondaryPanel();

        orderHistorySecondaryController =
                new OrderHistorySecondaryController(
                        orderService,
                        orderHistorySecondaryPanel
                );

        orderHistoryController =
                new OrderHistoryController(
                        orderService,
                        orderHistoryPanel,
                        orderHistorySecondaryController
                );

        DVDRectangle dvdRectangle = new DVDRectangle(WIDTH, HEIGHT);
        dvdPanel = new DVDPanel(dvdRectangle);

        dvdController = new DVDController(dvdRectangle, dvdPanel);

        // TabbedPane
        tabbedPane = new TabbedPane(
                new ProductsPanel(productService, orderService),
                new OrderPanel(
                        orderHistoryPanel,
                        orderHistorySecondaryPanel,
                        orderHistoryController,
                        orderHistorySecondaryController
                ),
                orderHistoryController,
                dvdPanel
        );

        initializeLayout();
        initializeListener();
        initializeFrame();
    }

    private void initializeLayout() {

        setLayout(new BorderLayout());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void initializeListener() {
        dvdPanel.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                dvdController.updatePanelSize();
            }
        });
    }

    private void initializeFrame(){
        setTitle("Inventory Management");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
