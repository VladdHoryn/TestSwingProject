package org.example.ui.panel;

import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.controller.ProductTableController;
import org.example.ui.dialog.AddProductDialog;
import org.example.ui.dialog.SellProductDialog;

import javax.swing.*;
import java.awt.*;

public class ProductsPanel extends JPanel {
    private final ProductService productService;
    private final OrderService orderService;

    private final SearchPanel searchPanel;
    private final ProductTablePanel tablePanel;
    private final ActionPanel actionPanel;

    private final ProductTableController tableController;

    public ProductsPanel(ProductService productService, OrderService orderService) {
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
    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        add(searchPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private void initializeListeners(){
        actionPanel.getAddButton().addActionListener(e -> {

            AddProductDialog dialog = new AddProductDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    productService,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });

        actionPanel.getSellButton().addActionListener(e -> {

            SellProductDialog dialog = new SellProductDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    productService,
                    orderService,
                    tableController::loadProducts
            );

            dialog.setVisible(true);
        });
    }
}
