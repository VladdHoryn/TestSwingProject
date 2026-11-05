package org.example.ui;

import org.example.model.Product;
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
    private final ProductService service;

    private final SearchPanel searchPanel;
    private final ProductTablePanel tablePanel;
    private final ActionPanel actionPanel;

    private final ProductTableController tableController;

    public MainFrame(ProductService service) {
        this.service = service;

        this.searchPanel = new SearchPanel();
        this.tablePanel = new ProductTablePanel();
        this.actionPanel = new ActionPanel();

        tableController = new ProductTableController(
                service,
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
                    service,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });

        actionPanel.getSellButton().addActionListener(e -> {

            SellProductDialog dialog = new SellProductDialog(
                    this,
                    service,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });
    }
}
