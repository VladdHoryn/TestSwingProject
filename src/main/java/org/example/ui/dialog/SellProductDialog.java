package org.example.ui.dialog;

import org.example.model.Product;
import org.example.service.ProductService;
import org.example.ui.panel.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SellProductDialog extends JDialog {
    private final ProductService service;
    private final Runnable onProductSold;

    JComboBox<Product> productComboBox;

    public SellProductDialog(JFrame parent,
                             ProductService service,
                             Runnable onProductSold){

        super(parent, "Sell TV", true);

        this.service = service;
        this.onProductSold = onProductSold;

        initializeComponents();
        initializeLayout();

        pack();
        setLocationRelativeTo(this);
    }

    private void initializeComponents(){
        List<Product> availableProducts = service.findAll()
                .stream()
                .filter(p ->
                        p.getQuantity() != null &&
                                p.getQuantity() > 0
                )
                .toList();

        if (availableProducts.isEmpty()) {

            JOptionPane.showMessageDialog(
                    getOwner(),
                    "There are no products available for sale.",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();
            return;
        }

        productComboBox = new JComboBox<>(
                availableProducts.toArray(new Product[0])
        );
    }

    private void initializeLayout(){
        if (productComboBox == null) {
            return;
        }

        setLayout(new BorderLayout(10, 10));

        JPanel panel = new JPanel(new FlowLayout());

        panel.add(new JLabel("Select product:"));
        panel.add(productComboBox);

        JPanel buttons = new JPanel();

        JButton sell = new JButton("Sell");
        JButton cancel = new JButton("Cancel");

        buttons.add(sell);
        buttons.add(cancel);

        sell.addActionListener(e -> sellProduct());
        cancel.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
    }

    private void sellProduct(){
        Product selectedProduct =
                (Product) productComboBox.getSelectedItem();

        if (selectedProduct == null) {
            return;
        }

        if (selectedProduct.getQuantity() == null ||
                selectedProduct.getQuantity() <= 0) {
            showError("This product is out of stock.");
            return;
        }

        selectedProduct.setQuantity(
                selectedProduct.getQuantity() - 1
        );

        service.update(selectedProduct);

        onProductSold.run();

        dispose();
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
