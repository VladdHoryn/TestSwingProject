package org.example.ui.dialog;

import org.example.model.Product;

import javax.swing.*;
import java.awt.*;

public class ProductRow extends JPanel {

    private final Product product;
    private final JTextField quantityField;
    private final JButton removeButton;

    public ProductRow(Product product) {

        this.product = product;

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0;

        JLabel nameLabel = new JLabel(product.getName());

        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(nameLabel, gbc);

        JLabel codeLabel = new JLabel(
                "Code: " + product.getCode()
        );

        gbc.gridx = 1;
        gbc.weightx = 0;

        add(codeLabel, gbc);

        JLabel stockLabel = new JLabel(
                "Stock: " + product.getQuantity()
        );

        gbc.gridx = 2;

        add(stockLabel, gbc);

        quantityField = new JTextField("1", 5);

        gbc.gridx = 3;

        add(quantityField, gbc);

        removeButton = new JButton("Remove");

        gbc.gridx = 4;

        add(removeButton, gbc);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return Integer.parseInt(
                quantityField.getText().trim()
        );
    }

    public JButton getRemoveButton() {
        return removeButton;
    }
}
