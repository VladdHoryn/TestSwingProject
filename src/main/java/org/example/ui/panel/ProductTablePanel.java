package org.example.ui.panel;

import org.example.model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductTablePanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ProductTablePanel(){
        setLayout(new BorderLayout());

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


        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTable(){
        return table;
    }

    public void refreshTable(List<Product> products) {
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
}
