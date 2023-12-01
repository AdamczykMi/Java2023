package org.simplestore.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Inventory {
    // TODO: Remember of synchronization logic!
    // There is several methods to achieve this.
    private final Map<Integer, Product> products = new HashMap<>();

    public synchronized void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public synchronized void removeProduct(int id) throws ProductNotFoundException{
        if (products.containsKey(id)) {
            products.remove(id);
        } else {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
    }

    public synchronized Product getProduct(int id) throws ProductNotFoundException {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return product;
    }

    public Collection<Product> listAllProducts() {
        return products.values();
    }

    // See file: src/test/java/org/simplestore/model/InventoryTest.java
    // TODO: Implement a method to list all products
    // TODO: Implement a method to remove a product by id
}
