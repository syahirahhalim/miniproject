package springboot.projects.OrderInterface;

import springboot.projects.OrderInterface.models.Products;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    static HashMap<Products, Integer> cartItems = new HashMap<>();

    public static void addToCart(Products product, Integer quantity) {
        for (Products p : cartItems.keySet()) {
            if (p.getId() == product.getId()) {
                cartItems.put(p, cartItems.get(p) + quantity);
                return;
            }
        }
        cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
    }

    public static void removeFromCart(Integer id) {
        for (Products product : cartItems.keySet()) {
            if (product.getId() == id) {
                cartItems.remove(product);
                return;
            }
        }
        System.out.println("Product ID not found.");
    }

    public static void updateCart(Integer id, Integer quantity) {
        if (quantity == 0) {
            removeFromCart(id);
        } else {
            for (Products product : cartItems.keySet()) {
                if (product.getId() == id) {
                    cartItems.put(product, quantity);
                    return;
                }
            }
            System.out.println("Product ID not found.");
        }
    }

    public static void clearCart() {
        cartItems = new HashMap<>();
    }

    public static HashMap<Products, Integer> getCartItems() {
        return cartItems;
    }

    public static Double totalPrice() {
        Double total = 0.;
        for (Map.Entry<Products, Integer> entry : cartItems.entrySet()) {
            Products product = entry.getKey();
            Integer quantity = entry.getValue();
            total += 1. * product.getPrice() * quantity;
        }
        return total;
    }
}