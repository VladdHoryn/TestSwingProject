package org.example.ui;

import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.controller.ProductTableController;
import org.example.ui.dialog.AddProductDialog;
import org.example.ui.dialog.SellProductDialog;
import org.example.ui.panel.ActionPanel;
import org.example.ui.panel.ProductTablePanel;
import org.example.ui.panel.SearchPanel;

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
    private final ProductService productService;
    private final OrderService orderService;

    private final SearchPanel searchPanel;
    private final ProductTablePanel tablePanel;
    private final ActionPanel actionPanel;

    private final ProductTableController tableController;

    public MainFrame(ProductService productService, OrderService orderService) {
        this.productService = productService;
        this.orderService = orderService;

        this.searchPanel = new SearchPanel();
        this.tablePanel = new ProductTablePanel();
        this.actionPanel = new ActionPanel();

        tableController = new ProductTableController(
                productService,
                tablePanel,
                searchPanel.getSearchField()
        );

        initializeLayout();
        initializeListeners();

        setTitle("Products");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }

    private void initializeListeners(){
        actionPanel.getAddButton().addActionListener(e -> {

            AddProductDialog dialog = new AddProductDialog(
                    this,
                    productService,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });

        actionPanel.getSellButton().addActionListener(e -> {

            SellProductDialog dialog = new SellProductDialog(
                    this,
                    productService,
                    orderService,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });
    }
}
