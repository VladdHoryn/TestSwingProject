package org.example.ui.panel;

import org.example.model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class OrderHistorySecondaryPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;

    public OrderHistorySecondaryPanel() {
        setLayout(new BorderLayout());

        String[] columns = Arrays.stream(OrderSecondaryColumn.values())
                .map(OrderSecondaryColumn::getTitle)
                .toArray(String[]::new);

        model = new DefaultTableModel(columns, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        table.setAutoCreateRowSorter(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTable getTable(){
        return table;
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    public void refreshTable(List<Order.ProductItem> products) {
        model.setRowCount(0);

        for(var i :  products){
            Object[] row = {
                    i.getCode(),
                    i.getName(),
                    i.getQuantity()
            };

            model.addRow(row);
        }
    }
}
