package org.example.ui;

import org.example.model.Product;
import org.example.service.ProductService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final ProductService service;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField searchField;

    private JButton addButton;
    private JButton sellButton;

    private List<Product> originalProducts;

    public MainFrame(ProductService service) {
        this.service = service;

        initializeComponents();
        initializeLayout();
        initializeListeners();

        loadProducts();

        setTitle("Products");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        searchField = new JTextField();

        String[] columns = {
                "Code",
                "Brand",
                "Diagonal",
                "Display Technology",
                "Price",
                "Quantity"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);

        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        addButton = new JButton("Add Product");
        sellButton = new JButton("Sell TV");
    }

    private void initializeLayout() {
        // NORTH   -> search panel
        // CENTER  -> table scroll pane
        // SOUTH   -> buttons panel
    }

    private void initializeListeners() {
        searchField.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        searchProducts(searchField.getText());
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        searchProducts(searchField.getText());
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        searchProducts(searchField.getText());
                    }
                }
        );

        // addButton listener

        // sellButton listener
    }

    private void loadProducts() {
         service.findAll();

        // refreshTable(...)
    }

    private void refreshTable(List<Product> products) {
        // clear model

        // add rows
    }

    private void openAddProductDialog() {
        // dialog

        // Submit

        // Cancel
    }

    private void openSellProductDialog() {
        // dialog

        // ComboBox<Product>

        // Sell

        // Cancel
    }

    private void searchProducts(String query) {
        // filter by:
        // product.getCode()
        // product.getName()

        // refreshTable(...)
    }
}
