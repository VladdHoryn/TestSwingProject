package org.example;

import org.example.model.Product;
import org.example.repository.CsvOrderRepository;
import org.example.repository.CsvProductRepository;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.ui.MainFrame;
import org.example.ui.controller.OrderHistorySecondaryController;
import org.example.ui.panel.OrderHistorySecondaryPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        ProductService productService =
                new ProductService(
                        new CsvProductRepository(
                                Path.of("./products.csv")
                        )
                );

        OrderService orderService =
                new OrderService(
                        new CsvOrderRepository(
                                Path.of("./orders.csv")
                        )
                );

        MainFrame mainFrame = new MainFrame(productService, orderService, new OrderHistorySecondaryController(orderService, new OrderHistorySecondaryPanel()));
    }
}
