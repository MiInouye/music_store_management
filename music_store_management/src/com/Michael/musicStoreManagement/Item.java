package com.Michael.musicStoreManagement;


public interface Item { //ask about this being private
    String name = "";
    Double purchasePrice = 0.0;
    Double listPrice = 0.0;
    Boolean newOrUsed = false;
    int dayArrived = 0;
    int condition = 0;
    Double salePrice = 0.0;
    int daySold = 0;
}