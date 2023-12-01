package org.simplestore.service;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    // TODO Finish implementation. eg. add needed constructor

    // Remember of synchronization logic!
    // It could be achieved in many ways.

    private final Inventory inventory;
    private final Map<Integer, Integer> cartItems = new HashMap<>();
    public ShoppingCart(Inventory inventory) {
        this.inventory = inventory;
    }

    public synchronized void addItem(int productId, int quantity) {
        cartItems.merge(productId, quantity, Integer::sum); // Equivalent of lambda (a, b) -> Integer.sum(a, b)
    }

    public synchronized void removeProductFromTheCart(int productId) throws ProductNotFoundException {
        if (cartItems.containsKey(productId)) {
            cartItems.remove(productId);
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
    }

    public double calculateTotalCost() throws ProductNotFoundException {
        double totalCost = 0.0;

        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            double productPrice = inventory.getProduct(productId).getPrice();
            totalCost += productPrice * quantity;
        }

        return totalCost;
    }

    // See file: src/test/java/org/simplestore/service/ShoppingCartTest.java
    // TODO: Implement a method to remove a product from the cart
    // TODO: Implement a method to calculate the total price of the cart
    // TODO: Implement a method to clear the cart

}
