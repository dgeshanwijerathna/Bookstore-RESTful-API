package com.mycompany.cw2.storage;

import com.mycompany.cw2.model.Cart;
import java.util.*;

public class CartDataStore {
    private static final Map<Integer, Cart> cartStore = new HashMap<>();

    public static Cart getOrCreateCart(int customerId) {
        return cartStore.computeIfAbsent(customerId, Cart::new);
    }

    public static Cart getCartByCustomerId(int customerId) {
        return cartStore.get(customerId);
    }

    public static void saveCart(int customerId, Cart cart) {
        cartStore.put(customerId, cart);
    }

    public static void clearCart(int customerId) {
        Cart cart = cartStore.get(customerId);
        if (cart != null) {
            cart.getBookIds().clear();
        }
    }
}
