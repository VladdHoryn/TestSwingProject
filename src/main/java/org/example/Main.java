package org.example;

import org.example.model.Product;
import org.example.repository.CsvProductRepository;
import org.example.service.ProductService;
import org.example.ui.MainFrame;

import java.math.BigDecimal;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        ProductService service =
                new ProductService(
                        new CsvProductRepository(
                                Path.of("./products.csv")
                        )
                );

        MainFrame mainFrame = new MainFrame(service);
    }
}
