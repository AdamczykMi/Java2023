package org.simplestore.util;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InventoryLoader {

    public static void loadInventory(String filePath, Inventory inventory) throws ProductNotFoundException {

        // TODO: Refactor this method to use try-with-resource for better resource management.
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // TODO: Implement error handling for file reading and parsing,
            //  eg. by handling NumberFormatException
            //  and writing to System.err.println(...)
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double price = Double.parseDouble(parts[2].trim());
                    Product product = new Product(id, name, price);
                    inventory.addProduct(product);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing line: " + line);

                    // Rethrow the exception to propagate it
                    throw e;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
