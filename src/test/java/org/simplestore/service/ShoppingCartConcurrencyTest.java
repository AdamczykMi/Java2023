package org.simplestore.service;

import org.junit.jupiter.api.Test;
import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShoppingCartConcurrencyTest {
    private final Inventory inventory = new Inventory();

    @Test
    void addAndRemoveItemsConcurrently() throws InterruptedException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        // Prepare tests with 10 threads. Next:

        // TODO Add 100 items concurrently

        // TODO Remove 50 items concurrently

        // TODO Await for threads termination, eg. join

        int threads = 10;
        int itemsToAdd = 100;
        int itemsToRemove = 50;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(itemsToAdd + itemsToRemove);

        // Add 100 items concurrently
        for (int i = 0; i < itemsToAdd; i++) {
            executorService.submit(() -> {
                shoppingCart.addItem(1, 1);
                latch.countDown();
            });
        }

        // Remove 50 items concurrently
        for (int i = 0; i < itemsToRemove; i++) {
            executorService.submit(() -> {
                try {
                    shoppingCart.removeItem(1, 1);
                } catch (ProductNotFoundException e) {
                    e.printStackTrace(); // Handle the exception as needed
                } finally {
                    latch.countDown();
                }
            });
        }

        // Await for threads termination
        latch.await();

        // Check if the final quantity is as expected
        assertEquals(50, shoppingCart.getItemQuantity(1));

        // Shutdown the executor service
        executorService.shutdown();
    }

    @Test
    void calculateTotalCostConcurrently() throws InterruptedException, ProductNotFoundException {
        ShoppingCart shoppingCart = new ShoppingCart(inventory);
        inventory.addProduct(new Product(1, "Test Product", 10.0));

        // TODO Add 100 items concurrently
        // TODO Await for threads termination, eg. join

        int threads = 10;
        int itemsToAdd = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(itemsToAdd);

        // Add 100 items concurrently
        for (int i = 0; i < itemsToAdd; i++) {
            executorService.submit(() -> {
                shoppingCart.addItem(1, 1);
                latch.countDown();
            });
        }

        // Await for threads termination
        latch.await();

        // Check if the total cost calculation is correct
        assertEquals(1000.0, shoppingCart.calculateTotalCost());

        // Shutdown the executor service
        executorService.shutdown();
    }

    // Note for presenter: Discuss the importance of concurrency testing in a multi-threaded environment.
}
