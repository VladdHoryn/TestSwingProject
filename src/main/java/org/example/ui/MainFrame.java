package org.example.ui;

import org.example.model.Product;
import org.example.service.ProductService;

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

    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField searchField;

    private JButton addButton;
    private JButton sellButton;

    private List<Product> originalProducts;

    private List<RowSorter.SortKey> previousSortKeys = new ArrayList<>();

    private final int[] sortStates = new int[6];

    private static final int SORT_DEFAULT = 0;
    private static final int SORT_ASCENDING = 1;
    private static final int SORT_DESCENDING = 2;

    public MainFrame(ProductService service) {
        this.service = service;

        initializeComponents();
        initializeLayout();
        initializeListeners();
        initializeSorting();

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

        table.setAutoCreateRowSorter(false);

        addButton = new JButton("Add Product");
        sellButton = new JButton("Sell TV");
    }

    private void initializeLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 10));
        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        );

        searchPanel.add(
                new JLabel("Search by code or brand:"),
                BorderLayout.WEST
        );

        searchPanel.add(searchField, BorderLayout.CENTER);

        add(searchPanel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        buttonsPanel.add(addButton);
        buttonsPanel.add(sellButton);

        add(buttonsPanel, BorderLayout.SOUTH);
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

        addButton.addActionListener(e -> openAddProductDialog());

        sellButton.addActionListener(e -> openSellProductDialog());
    }

    private void initializeSorting() {

        table.getTableHeader().addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e
                    ) {

                        int column = table.columnAtPoint(e.getPoint());

                        if (column != 1 && column != 2 && column != 4) {
                            return;
                        }

                        sortStates[column] =
                                (sortStates[column] + 1) % 3;

                        for (int i = 0; i < sortStates.length; i++) {
                            if (i != column) {
                                sortStates[i] = SORT_DEFAULT;
                            }
                        }

                        applySorting(column);
                    }
                }
        );
    }

    private void applySorting(int column) {

        List<Product> products = originalProducts.stream()
                .filter(product -> {

                    String search = searchField.getText()
                            .trim()
                            .toLowerCase();

                    return String.valueOf(product.getCode())
                            .toLowerCase()
                            .contains(search)
                            ||
                            product.getName()
                                    .toLowerCase()
                                    .contains(search);
                })
                .collect(java.util.stream.Collectors.toList());

        sortProducts(products);

        refreshTable(products);
    }

    private void loadProducts() {
        originalProducts = service.findAll();

        refreshTable(originalProducts);
    }

    private void refreshTable(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {

            Object[] row = {
                    product.getCode(),
                    product.getName(),
                    product.getDiagonalSize(),
                    product.getDisplayTechnology(),
                    product.getPrice(),
                    product.getQuantity()
            };

            tableModel.addRow(row);
        }
    }

    private void openAddProductDialog() {
        JDialog dialog = new JDialog(
                this,
                "Add Product",
                true
        );

        dialog.setLayout(new BorderLayout(10, 10));

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));

        form.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JTextField nameField = new JTextField();
        JTextField diagonalField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        String[] technologies = {"LED", "LCD", "OLED", "QLED"};

        JComboBox<String> technologyComboBox =
                new JComboBox<>(technologies);

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

        JPanel buttons = new JPanel();

        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");

        buttons.add(submitButton);
        buttons.add(cancelButton);

        submitButton.addActionListener(e -> {

            try {

                String name = nameField.getText().trim();

                if (name.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Brand cannot be empty."
                    );
                }

                double diagonal = Double.parseDouble(
                        diagonalField.getText()
                );

                double width = Double.parseDouble(
                        widthField.getText()
                );

                double height = Double.parseDouble(
                        heightField.getText()
                );

                BigDecimal price = new BigDecimal(
                        priceField.getText()
                );

                int quantity = Integer.parseInt(
                        quantityField.getText()
                );

                if (diagonal <= 0 || width <= 0 || height <= 0
                        || price.compareTo(BigDecimal.ZERO) < 0
                        || quantity < 0) {

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

                loadProducts();

                dialog.dispose();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        "Please enter valid numeric values.",
                        "Input error",
                        JOptionPane.ERROR_MESSAGE
                );

            } catch (IllegalArgumentException ex) {

                JOptionPane.showMessageDialog(
                        dialog,
                        ex.getMessage(),
                        "Input error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(buttons, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
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

    private void searchProducts(String query) {

        String search = query.trim().toLowerCase();

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

        sortProducts(filteredProducts);

        refreshTable(filteredProducts);
    }

    private void sortProducts(List<Product> products) {

        int activeColumn = -1;

        for (int i = 0; i < sortStates.length; i++) {
            if (sortStates[i] != SORT_DEFAULT) {
                activeColumn = i;
                break;
            }
        }

        if (activeColumn == -1) {
            return;
        }

        Comparator<Product> comparator = switch (activeColumn) {

            case 1 -> Comparator.comparing(
                    Product::getName,
                    String.CASE_INSENSITIVE_ORDER
            );

            case 2 -> Comparator.comparingDouble(
                    Product::getDiagonalSize
            );

            case 4 -> Comparator.comparing(
                    Product::getPrice
            );

            default -> null;
        };

        if (comparator == null) {
            return;
        }

        if (sortStates[activeColumn] == SORT_DESCENDING) {
            comparator = comparator.reversed();
        }

        products.sort(comparator);
    }
}
