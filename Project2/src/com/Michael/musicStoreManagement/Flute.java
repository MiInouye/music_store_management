package com.Michael.musicStoreManagement;

public class Flute extends Instruments{



    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public String getItemType(){return itemType;}

    public void setPurchasePrice(Double purchasePrice){this.purchasePrice = purchasePrice;}
    public Double getPurchasePrice(){return purchasePrice;}

    public void setListPrice(Double listPrice){this.listPrice = listPrice;}
    public Double getListPrice(){return listPrice;}

    public void setNewOrUsed(boolean newOrUsed){this.newOrUsed = newOrUsed;}
    public Boolean getNewOrUsed(){return newOrUsed;}

    public void setDayArrived(int dayArrived){this.dayArrived = dayArrived;}
    public int getDayArrived(){return dayArrived;}

    public void setCondition(int condition){this.condition = condition;}
    public int getCond(){return condition;}

    public void setSalePrice(double salePrice){ this.salePrice = salePrice;}
    public Double getSalePrice(){return salePrice;}

    public void setDaySold(int daySold){this.daySold = daySold;}
    public int getDaySold(){return daySold;}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    private int type = 0;

    private String itemType = "Flute";
    private Double purchasePrice = 0.0;
    private Double listPrice = 0.0;
    private Double salePrice = 0.0;
    private String name = "";
    private Boolean newOrUsed = true;
    private int dayArrived = 0;
    private int condition = 5;
    private int daySold = 0;

    public Flute(){
        this.itemType = "Flute";
        this.purchasePrice = 50.0;
        this.listPrice = 100.0;
        this.salePrice = 0.0;
    }
    
    public String getitemType(){return itemType;}
    
}
