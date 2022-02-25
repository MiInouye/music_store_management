package com.Michael.musicStoreManagement;

import java.util.ArrayList;

public class Customer{
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSellItem(){
        return sellItem;
    }
    public void setSellItem(String itemType){
        this.sellItem = itemType;
    }

    public ArrayList<Item> getCart(){
        return cart;
    }
    public void setCart(ArrayList<Item> cart){
        this.cart = cart;
    }

    public String getWantedItemType(){
        return wantedItem;
    }
    public void setWantedItemType(String itemType){
        this.wantedItem = itemType;
    }

    private String name = "";
    private String wantedItem = "";
    private String sellItem = "";
    private ArrayList<Item> cart = new ArrayList<>();
}
