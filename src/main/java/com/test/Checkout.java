package com.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Checkout {

    private ConcurrentHashMap<String, Offers> discountsMap;

    public Double calculateAmount(Map<String, Integer> cartItems, Map<String, Double> itemsPricingMap) {
        Double totalCost = 0.00;
        for(Map.Entry<String, Integer> entry :cartItems.entrySet()) {
            String productName = entry.getKey();
            Double itemPrice = itemsPricingMap.get(productName);
            if(discountsMap != null && discountsMap.get(productName) != null){
                totalCost = totalCost + calculateDiscountPrice(entry.getValue(), itemPrice, discountsMap.get(productName));
            }else {
                totalCost = totalCost + itemPrice * entry.getValue();
            }
        }
        return new BigDecimal(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    private Double calculateDiscountPrice(Integer quantity, Double itemPrice, Offers discountName) {
        Double totalPrice = 0.00;
        int bundles = 0;
        int remainder = 0;
        switch (discountName) {
            case BUYONE_GETONE:
                bundles = quantity / 2;
                remainder = quantity % 2;
                totalPrice =  (bundles + remainder) * itemPrice;
                break;
            case BUYTHREE_PAYFORTWO:
                bundles = quantity / 3;
                remainder = quantity % 3;
                totalPrice = ((bundles * 2) + remainder)* itemPrice;
                break;
        }
        return new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setDiscounts(ConcurrentHashMap<String, Offers> discountsMap) {
        this.discountsMap = discountsMap;
    }
}
