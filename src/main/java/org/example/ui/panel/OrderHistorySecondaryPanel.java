package org.example.ui.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;

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
}
