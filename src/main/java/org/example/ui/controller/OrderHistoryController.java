package org.example.ui.controller;

import org.example.model.Order;
import org.example.service.OrderService;
import org.example.ui.panel.OrderHistoryPanel;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class OrderHistoryController {
    private final OrderService orderService;
    private final OrderHistoryPanel orderHistoryPanel;
    private final OrderHistorySecondaryController secondaryController;

    private List<Order> orders;

    public OrderHistoryController(
            OrderService orderService,
            OrderHistoryPanel orderHistoryPanel,
            OrderHistorySecondaryController secondaryController) {
        this.orderService = orderService;
        this.orderHistoryPanel = orderHistoryPanel;
        this.secondaryController = secondaryController;

        orderHistoryPanel.setSelectionListener(this::handleOrderSelection);

        loadOrders();
    }

    private void handleOrderSelection(int modelRow) {
        if (modelRow == -1 || orders == null || modelRow >= orders.size()) {
            secondaryController.clearProducts();
            return;
        }

        Order selectedOrder = orders.get(modelRow);

        secondaryController.loadProductItems(selectedOrder.getId());
    }

    private void loadOrders() {
        SwingWorker<List<Order>, Void> worker = new SwingWorker<List<Order>, Void>(){

            @Override
            protected List<Order> doInBackground() throws Exception {
                return orderService.findAll();
            }

            @Override
            protected void done() {
                try{
                    orders = get();

                    orderHistoryPanel.refreshTable(orders);
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
                orderHistoryPanel,
                message,
                "Loading Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
