package com.Michael.musicStoreManagement;

public class Vinyl extends Music implements Item{
	private String name = ""; //name will be item type for now, if we have time, use database
	private Double purchasePrice = 0.0;
	private Double listPrice = 0.0;
	private Boolean newOrUsed = true;
	private int dayArrived = 0;
	private int condition = 5;//5 is new 0 is unusable
	private Double salePrice = 0.0;
	private int daySold = 0;
	private String itemType = "";

	public void setName(String name){this.name = name;}
	public String getName(){return name;}

	public void setItemType(String itemType){
		this.itemType = itemType;
	}
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

	public void setSalePrice(double salePrice){this.salePrice = salePrice;}
	public Double getSalePrice(){return salePrice;}

	public void setDaySold(int daySold){this.daySold = daySold;}
	public int getDaySold(){return daySold;}

	public Vinyl(){
	this.itemType = "Vinyl";
		this.purchasePrice = 10.0;
		this.listPrice = 20.0;
		this.salePrice = 0.0;
	}

	public String getitemType(){return itemType;}
}
