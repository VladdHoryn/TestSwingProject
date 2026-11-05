package org.example.ui.controller;

import org.example.model.Product;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;

public class ProductSortingController {
    private final int[] sortStates = new int[6];

    private static final int SORT_DEFAULT = 0;
    private static final int SORT_ASCENDING = 1;
    private static final int SORT_DESCENDING = 2;

    public ProductSortingController(JTable table) {
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

//                        applySorting(column);
                    }
                }
        );
    }

    public void sortProducts(List<Product> products) {

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
