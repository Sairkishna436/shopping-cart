package com.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ShoppingCart {

    private Map<String,Integer> cartItems;

    public ShoppingCart() {
        cartItems = new ConcurrentHashMap<String, Integer>();
    }

    public void addProduct(String productName) {
        if(cartItems.get(productName) == null) {
            cartItems.put(productName, new Integer(1));
        }else {
            cartItems.put(productName, cartItems.get(productName)+1);
        }
    }

    public Map getCartItems() {
        return cartItems;
    }

    public void removeProduct(String productName) {
        cartItems.remove(productName);
    }
}
