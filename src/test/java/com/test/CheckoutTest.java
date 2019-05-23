package com.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.test.Constants.*;
import static com.test.Offers.BUYONE_GETONE;
import static com.test.Offers.BUYTHREE_PAYFORTWO;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutTest {

    private Checkout checkout;

    private ShoppingCart shoppingCart;

    private Map<String, Double> itemsPricingMap;

    @Before
    public void setUp() {
        checkout = new Checkout();
        shoppingCart = new ShoppingCart();
        itemsPricingMap = new ConcurrentHashMap<String, Double>();
        itemsPricingMap.put(APPLE, .35);
        itemsPricingMap.put(BANANA, .20);
        itemsPricingMap.put(MELON, .50);
        itemsPricingMap.put(LIME, .15);
    }

    @Test
    public void calculateTotalCost() {
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(LIME);
        Double totalAmount = checkout.calculateAmount(shoppingCart.getCartItems(), itemsPricingMap);
        assertEquals(new Double(1.20), totalAmount);
    }

    @Test
    public void calculateCost_whenProductsAdded_moreThanOnce() {
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(APPLE);
        Double totalAmount = checkout.calculateAmount(shoppingCart.getCartItems(), itemsPricingMap);
        assertEquals(new Double(1.55), totalAmount);
    }

    @Test
    public void applyDiscounts_forProducts_buyOneGetOneMelon() {
        ConcurrentHashMap<String, Offers> discountsMap = new ConcurrentHashMap<>();
        discountsMap.put(MELON, BUYONE_GETONE);
        checkout.setDiscounts(discountsMap);
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(MELON);
        Double totalAmount = checkout.calculateAmount(shoppingCart.getCartItems(), itemsPricingMap);
        assertEquals(new Double(1.20), totalAmount);
    }

    @Test
    public void applyDiscounts_forProducts_buyThreePayForTwoLimes() {
        ConcurrentHashMap<String, Offers> discountsMap = new ConcurrentHashMap<>();
        discountsMap.put(LIME, BUYTHREE_PAYFORTWO);
        checkout.setDiscounts(discountsMap);
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        Double totalAmount = checkout.calculateAmount(shoppingCart.getCartItems(), itemsPricingMap);
        assertEquals(new Double(1.35), totalAmount);
    }

    @Test
    public void applyDiscounts_forProducts_allCombinations() {
        ConcurrentHashMap<String, Offers> discountsMap = new ConcurrentHashMap<>();
        discountsMap.put(LIME, BUYTHREE_PAYFORTWO);
        discountsMap.put(MELON, BUYONE_GETONE);
        checkout.setDiscounts(discountsMap);
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(APPLE);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(BANANA);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(MELON);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        shoppingCart.addProduct(LIME);
        Double totalAmount = checkout.calculateAmount(shoppingCart.getCartItems(), itemsPricingMap);
        assertEquals(new Double(2.20), totalAmount);
    }

}
