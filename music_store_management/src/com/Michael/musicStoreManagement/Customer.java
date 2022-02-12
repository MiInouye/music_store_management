package com.Michael.musicStoreManagement;

import java.util.ArrayList;

public class Customer {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getCart() {
        return cart;
    }

    public void setCart(ArrayList<Item> cart) {
        this.cart = cart;
    }

    private String name = "";
    private ArrayList<Item> cart = new ArrayList<>();
    
}
