package org.example.ui;

import org.example.model.Product;
import org.example.service.ProductService;
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


    public MainFrame(SearchPanel searchPanel,
                     ProductTablePanel tablePanel,
                     ActionPanel actionPanel,
                     ProductService service) {
        this.service = service;

        this.searchPanel = new SearchPanel();
        this.tablePanel = new ProductTablePanel();
        this.actionPanel = new ActionPanel();

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

    private void openSellProductDialog() {
        originalProducts = service.findAll();

        List<Product> availableProducts = originalProducts.stream()
                .filter(p -> p.getQuantity() != null && p.getQuantity() > 0)
                .toList();

        if (availableProducts.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "There are no products available for sale.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        JDialog dialog = new JDialog(
                this,
                "Sell TV",
                true
        );

        dialog.setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new FlowLayout());

        JComboBox<Product> productComboBox =
                new JComboBox<>(availableProducts.toArray(new Product[0]));

        panel.add(new JLabel("Select product:"));
        panel.add(productComboBox);

        JPanel buttons = new JPanel();

        JButton sell = new JButton("Sell");
        JButton cancel = new JButton("Cancel");

        buttons.add(sell);
        buttons.add(cancel);

        sell.addActionListener(e -> {

            Product selectedProduct =
                    (Product) productComboBox.getSelectedItem();

            if (selectedProduct == null) {
                return;
            }

            if (selectedProduct.getQuantity() <= 0) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "This product is out of stock."
                );
                return;
            }

            selectedProduct.setQuantity(
                    selectedProduct.getQuantity() - 1
            );

            service.update(selectedProduct);

            loadProducts();

            dialog.dispose();
        });

        cancel.addActionListener(e -> dialog.dispose());

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}
