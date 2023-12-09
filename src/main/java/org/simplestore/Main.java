package org.simplestore;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;
import org.simplestore.util.InventoryLoader;

public class Main {
    public static void main(String[] args) throws ProductNotFoundException {
        try {
            // Load initial inventory from a file
            Inventory inventory = new Inventory();
            InventoryLoader.loadInventory("C:\\Users\\majkel\\IdeaProjects\\Java2023\\src\\main\\resources\\inventory.txt", inventory);

            // Add a product to the inventory
            Product newProduct = new Product(11, "Eggs", 15.0);
            inventory.addProduct(newProduct);
            System.out.println("Added new product to the inventory.");

            //List all products in the inventory
            System.out.println("Products in the inventory:");
            inventory.listAllProducts();
            System.out.println();

            //Remove a product from the inventory
            int productIdToRemove = 1;
            try {
                inventory.removeProduct(productIdToRemove);
                System.out.println("Removed product with ID " + productIdToRemove + " from the inventory.");
            } catch (ProductNotFoundException e) {
                System.err.println("Product with ID " + productIdToRemove + " not found in the inventory.");
            }

            //List all products in the inventory after removal
            System.out.println("Products in the inventory after removal:");
            inventory.listAllProducts();
        } catch (ProductNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

