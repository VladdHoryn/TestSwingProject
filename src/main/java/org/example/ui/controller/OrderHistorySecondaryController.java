package org.example.ui.controller;

import org.example.model.Order;
import org.example.service.OrderService;
import org.example.ui.panel.OrderHistoryPanel;
import org.example.ui.panel.OrderHistorySecondaryPanel;
import org.example.ui.panel.ProductTablePanel;

import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class OrderHistorySecondaryController {
    private final OrderService orderService;
    private final OrderHistorySecondaryPanel orderHistorySecondaryPanel;

    private List<Order.ProductItem> products;

    public OrderHistorySecondaryController(
            OrderService orderService,
            OrderHistorySecondaryPanel orderHistorySecondaryPanel) {
        this.orderService = orderService;
        this.orderHistorySecondaryPanel = orderHistorySecondaryPanel;
    }

    public void loadProductItems(Integer orderId) {
        SwingWorker<List<Order.ProductItem>, Void> worker = new SwingWorker<List<Order.ProductItem>, Void>(){

            @Override
            protected List<Order.ProductItem> doInBackground() throws Exception {
                return orderService.findById(orderId).get().getProducts().stream().toList();
            }

            @Override
            protected void done() {
                try{
                    products = get();

                    orderHistorySecondaryPanel.refreshTable(products);
                } catch (ExecutionException e) {
                    showError("Failed to load orders: "
                            + e.getCause().getMessage());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    showError("Loading products was interrupted");
                }
            }
        };

        worker.execute();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                orderHistorySecondaryPanel,
                message,
                "Loading Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
