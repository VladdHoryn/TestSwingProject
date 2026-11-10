package org.example.ui.panel;

import org.example.model.Order;
import org.example.model.Product;
import org.example.service.OrderService;
import org.example.ui.controller.OrderHistorySecondaryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class OrderHistoryPanel extends JPanel {
    private final JTable table;
    private final DefaultTableModel model;

    private Consumer<Integer> selectionListener;

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

        initializeListeners();

        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeListeners() {
        table.getSelectionModel().addListSelectionListener(e -> {

            if (e.getValueIsAdjusting()) {
                return;
            }

            int selectedRow = table.getSelectedRow();

            int modelRow = selectedRow == -1
                    ? -1
                    : table.convertRowIndexToModel(selectedRow);

            if (selectionListener != null) {
                selectionListener.accept(modelRow);
            }
        });
    }

    public void setSelectionListener(Consumer<Integer> listener) {
        this.selectionListener = listener;
    }

    public JTable getTable(){
        return table;
    }

    public void refreshTable(List<Order> orders) {
        table.clearSelection();
        model.setRowCount(0);

        for (Order order : orders) {

            Object[] row = {
                    order.getId(),
                    order.getStatus().toString(),
                    order.getCreatedAt().toString(),
                    order.getOrderedAt() != null
                            ? order.getOrderedAt().toString()
                            : "-"
            };

           model.addRow(row);
        }
    }
}
