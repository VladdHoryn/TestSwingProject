package org.example.ui.controller;

import org.example.model.Product;
import org.example.service.ProductService;
import org.example.ui.panel.ProductTablePanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class ProductTableController {
    private final ProductService service;
    private final ProductTablePanel tablePanel;
    private final JTextField searchField;
    private final ProductSortingController sortingController;

    private List<Product> originalProducts;

    public ProductTableController(
            ProductService service,
            ProductTablePanel tablePanel,
            JTextField searchField
    ){
        this.service = service;
        this.tablePanel = tablePanel;
        this.searchField = searchField;

        this.sortingController = new ProductSortingController(
                tablePanel.getTable()
        );

        initializeSearch();
        loadProducts();
    }

    private void initializeSearch() {

        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        searchProducts();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        searchProducts();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        searchProducts();
                    }
                }
        );
    }

    public void loadProducts() {

        originalProducts = service.findAll();

        searchProducts();
    }

    private void searchProducts() {

        if (originalProducts == null) {
            return;
        }

        String search = searchField.getText()
                .trim()
                .toLowerCase();

        List<Product> filteredProducts = originalProducts.stream()
                .filter(product ->
                        String.valueOf(product.getCode())
                                .toLowerCase()
                                .contains(search)
                                ||
                                product.getName()
                                        .toLowerCase()
                                        .contains(search)
                )
                .collect(java.util.stream.Collectors.toList());

        sortingController.sortProducts(filteredProducts);

        tablePanel.refreshTable(filteredProducts);
    }
}
