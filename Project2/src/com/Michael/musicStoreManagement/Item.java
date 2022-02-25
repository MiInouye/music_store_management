package com.Michael.musicStoreManagement;


public interface Item{
    String name = ""; //name will be item type for now, if we have time, use database
    Double purchasePrice = 0.0;
    Double listPrice = 0.0;
    Boolean newOrUsed = true;
    int dayArrived = 0;
    int condition = 5;//5 is new 0 is unusable
    Double salePrice = 0.0;
    int daySold = 0;
    String itemType = "";
    
    void setName(String name);
    String getName();

    String getItemType();

    void setPurchasePrice(Double price);
    Double getPurchasePrice();
    
    void setListPrice(Double price);
    Double getListPrice();
    
    void setNewOrUsed(boolean torf);
    Boolean getNewOrUsed();
    
    void setDayArrived(int day);
    int getDayArrived();
    
    void setCondition(int cond);
    int getCond();
    
    void setSalePrice(double salePrice);
    Double getSalePrice();
    
    void setDaySold(int day);
    int getDaySold();
}