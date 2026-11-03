package org.example.repository;

import org.example.model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class CsvProductRepository implements ProductRepository{
    private final Path csvPath;

    public CsvProductRepository(Path path) {
        this.csvPath = path;
    }

    private Product productMapper(String[] parts){
        return new Product(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                parts[5],
                BigDecimal.valueOf(Double.parseDouble(parts[6])),
                Integer.parseInt(parts[7])
        );
    }

    @Override
    public List<Product> findAll() {
        try{
            return Files.lines(csvPath)
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(parts -> Arrays.stream(parts)
                            .map(part -> part.trim()).toArray(String[]::new))
                    .map(parts -> productMapper(parts))
                    .toList();

        }
        catch (IOException e){
            throw new RuntimeException(
                    "Cannot read CSV", e);
        }
    }

    private String productToCsv(Product product) {
        return String.format(
                "%d,%s,%d,%.1f,%d,%s,%.2f,%d",
                product.getCode(),
                product.getName(),
                product.getDiagonalSize(),
                product.getWidth(),
                product.getHeight(),
                product.getDisplayTechnology(),
                product.getPrice(),
                product.getQuantity()
        );
    }

    @Override
    public Product create(Product product) {
        String csvLine = productToCsv(product);
        try{
            Files.writeString(
                    csvPath,
                    csvLine + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );

            return product;
        }
        catch (IOException e){
            throw new RuntimeException(
                    "Cannot create new line in CSV", e);
        }
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public Boolean delete(Product product) {
        return null;
    }
}
