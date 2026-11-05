package org.example.ui.dialog;

import org.example.model.Order;
import org.example.model.Product;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.panel.SearchPanel;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SellProductDialog extends JDialog {
    private final ProductService productService;
    private final OrderService orderService;

    private JPanel productsPanel;
    private JComboBox<Product> productComboBox;

    private final Runnable onProductSold;

    private final Map<Integer, ProductRow> selectedProducts =
            new LinkedHashMap<>();

    public SellProductDialog(JFrame parent,
                             ProductService productService,
                             OrderService orderService,
                             Runnable onProductSold){

        super(parent, "Sell TV", true);

        this.productService = productService;
        this.orderService = orderService;
        this.onProductSold = onProductSold;

        initializeComponents();
        initializeLayout();

        pack();
        setLocationRelativeTo(this);
    }

    private void initializeComponents(){
        List<Product> availableProducts = productService.findAll()
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

        productsPanel = new JPanel();

        productsPanel.setLayout(
                new BoxLayout(productsPanel, BoxLayout.Y_AXIS)
        );

        productsPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        );
    }

    private void initializeLayout(){
        if (productComboBox == null) {
            return;
        }

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton addButton = new JButton("Add product");

        topPanel.add(new JLabel("Select product:"));
        topPanel.add(productComboBox);
        topPanel.add(addButton);

        addButton.addActionListener(e -> {
            Product selectedProduct =
                    (Product) productComboBox.getSelectedItem();

            if (selectedProduct != null) {
                addProductRow(selectedProduct);
            }
        });

        JScrollPane scrollPane = new JScrollPane(productsPanel);

        scrollPane.setPreferredSize(
                new Dimension(500, 250)
        );

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Products in order"
                )
        );

        JPanel buttonsPanel = new JPanel(
                new FlowLayout(FlowLayout.RIGHT)
        );

        JButton sell = new JButton("Sell");
        JButton cancel = new JButton("Cancel");

        buttonsPanel.add(sell);
        buttonsPanel.add(cancel);

        sell.addActionListener(e -> sellProduct());
        cancel.addActionListener(e -> dispose());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        ((JPanel) getContentPane()).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private void addProductRow(Product product) {
        Integer code = product.getCode();

        if (selectedProducts.containsKey(code)) {
            showError("This product has already been added.");
            return;
        }

        ProductRow productRow = new ProductRow(product);

        selectedProducts.put(code, productRow);

        productRow.getRemoveButton().addActionListener(e -> {
            selectedProducts.remove(code);

            productsPanel.remove(productRow);

            productsPanel.revalidate();
            productsPanel.repaint();
        });

        productRow.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 55)
        );

        productRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        productsPanel.add(productRow);

        productsPanel.revalidate();
        productsPanel.repaint();
    }

    private void sellProduct(){

        if (selectedProducts.isEmpty()) {
            showError("Please add at least one product.");
            return;
        }
        try {
            for (ProductRow row : selectedProducts.values()) {

                int quantity = row.getQuantity();

                if (quantity <= 0) {
                    showError("Quantity must be greater than zero.");
                    return;
                }

                if (quantity > row.getProduct().getQuantity()) {
                    showError(
                            "Not enough stock for "
                                    + row.getProduct().getName()
                    );
                    return;
                }
            }

            Order order = orderService.createOrder();

            for (ProductRow row : selectedProducts.values()) {

                Product product = row.getProduct();

                orderService.addProduct(
                        order,
                        product.getCode(),
                        product.getName(),
                        row.getQuantity()
                );
            }

            for (ProductRow row : selectedProducts.values()) {

                Product product = row.getProduct();

                product.setQuantity(
                        product.getQuantity() - row.getQuantity()
                );

                productService.update(product);
            }

            orderService.completeOrder(order);

            JOptionPane.showMessageDialog(
                    this,
                    "Order completed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            if (onProductSold != null) {
                onProductSold.run();
            }

            dispose();

        } catch (NumberFormatException e) {

            showError("Please enter a valid quantity.");

        } catch (Exception e) {

            showError(
                    "Cannot complete order: " + e.getMessage()
            );
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
