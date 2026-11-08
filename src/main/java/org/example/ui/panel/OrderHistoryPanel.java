package org.example.ui.panel;

import org.example.model.Order;
import org.example.model.Product;
import org.example.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;

    public OrderHistoryPanel() {
        setLayout(new BorderLayout());

        String[] columns = Arrays.stream(OrderColumn.values())
                .map(OrderColumn::getTitle)
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

    public void refreshTable(List<Order> orders) {
        model.setRowCount(0);

        for (Order order : orders) {

            Object[] row = {
                    order.getId(),
                    order.getStatus().toString(),
                    order.getCreatedAt().toString(),
                    order.getOrderedAt().toString(),
            };

           model.addRow(row);
        }
    }
}
