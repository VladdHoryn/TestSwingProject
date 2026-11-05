package org.example.ui.dialog;

import org.example.model.Product;
import org.example.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class AddProductDialog extends JDialog {
    private final ProductService service;
    private final Runnable onProductAdded;

    private JTextField nameField;
    private JTextField diagonalField;
    private JTextField widthField;
    private JTextField heightField;
    private JTextField priceField;
    private JTextField quantityField;

    private JComboBox<String> technologyComboBox;

    public AddProductDialog(
            JFrame parent,
            ProductService service,
            Runnable onProductAdded
    ) {

        super(parent, "Add Product", true);

        this.service = service;
        this.onProductAdded = onProductAdded;

        initializeComponents();
        initializeLayout();

        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {

        nameField = new JTextField();
        diagonalField = new JTextField();
        widthField = new JTextField();
        heightField = new JTextField();
        priceField = new JTextField();
        quantityField = new JTextField();

        String[] technologies = {
                "LED", "LCD", "OLED", "QLED"
        };

        technologyComboBox = new JComboBox<>(technologies);
    }

    private void initializeLayout() {

        setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(
                new GridLayout(7, 2, 10, 10)
        );

        form.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        form.add(new JLabel("Brand:"));
        form.add(nameField);

        form.add(new JLabel("Diagonal size:"));
        form.add(diagonalField);

        form.add(new JLabel("Width:"));
        form.add(widthField);

        form.add(new JLabel("Height:"));
        form.add(heightField);

        form.add(new JLabel("Display technology:"));
        form.add(technologyComboBox);

        form.add(new JLabel("Price:"));
        form.add(priceField);

        form.add(new JLabel("Quantity:"));
        form.add(quantityField);

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttons = new JPanel();

        buttons.add(submitButton);
        buttons.add(cancelButton);

        add(form, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> submit());
        cancelButton.addActionListener(e -> dispose());
    }
    private void submit() {

        try {

            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                throw new IllegalArgumentException(
                        "Brand cannot be empty."
                );
            }

            double diagonal = Double.parseDouble(
                    diagonalField.getText().trim()
            );

            double width = Double.parseDouble(
                    widthField.getText().trim()
            );

            double height = Double.parseDouble(
                    heightField.getText().trim()
            );

            BigDecimal price = new BigDecimal(
                    priceField.getText().trim()
            );

            int quantity = Integer.parseInt(
                    quantityField.getText().trim()
            );

            if (diagonal <= 0 ||
                    width <= 0 ||
                    height <= 0 ||
                    price.compareTo(BigDecimal.ZERO) < 0 ||
                    quantity < 0) {

                throw new IllegalArgumentException(
                        "Values must be positive (price and quantity can be zero)."
                );
            }

            Product product = new Product(
                    null,
                    name,
                    diagonal,
                    width,
                    height,
                    (String) technologyComboBox.getSelectedItem(),
                    price,
                    quantity
            );

            service.create(product);

            onProductAdded.run();

            dispose();

        } catch (NumberFormatException ex) {

            showError("Please enter valid numeric values.");

        } catch (IllegalArgumentException ex) {

            showError(ex.getMessage());
        }
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Input error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
