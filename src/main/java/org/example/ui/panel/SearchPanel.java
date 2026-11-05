package org.example.ui.panel;

import javax.swing.*;
import java.awt.*;

public class SearchPanel extends JPanel {
    private JTextField searchField;

    public SearchPanel(){
        setLayout(new BorderLayout(10, 10));

        setBorder(
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        );

        searchField = new JTextField();

        add(
                new JLabel("Search by code or brand:"),
                BorderLayout.WEST
        );

        add(searchField, BorderLayout.CENTER);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public String getSearchText() {
        return searchField.getText();
    }
}
