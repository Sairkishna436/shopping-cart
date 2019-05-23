package com.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static com.test.Constants.APPLE;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @Before
    public void setUp() {
        shoppingCart = new ShoppingCart();
    }

    @Test
    public void addProductToShoppingCart() {
        shoppingCart.addProduct(APPLE);
        Map cartItems = shoppingCart.getCartItems();
        assertNotNull(cartItems);
        assertEquals(1, cartItems.get(APPLE));
    }

    @Test
    public void removeProductFromShoppingCart() {
        shoppingCart.addProduct(APPLE);
        shoppingCart.removeProduct(APPLE);
        Map cartItems = shoppingCart.getCartItems();
        assertNotNull(cartItems);
        assertNull(cartItems.get(APPLE));
    }

    @Test
    public void addProduct_two_times() {
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(APPLE);
        Map cartItems = shoppingCart.getCartItems();
        assertNotNull(cartItems);
        assertEquals(2, cartItems.get(APPLE));
    }
}
