package com.OOAD;

public interface AbstractGuitarKitFactory{//abstract factory example
	Bridge createBridge(String type);
	Knobs createKnobs(String type);
	Covers createCovers(String type);
	Neck createNeck(String type);
	Pickguard createPickguard(String type);
	Pickups createPickups(String type);
}
class NorthsideKitFactory implements AbstractGuitarKitFactory{
	public Bridge createBridge(String type){
		switch(type){
			case "A" -> {return new BridgeA();}
			case "B" -> {return new BridgeB();}
			case "C" -> {return new BridgeC();}
		}
		return null;
	}
	public Knobs createKnobs(String type){
		switch(type){
			case "A" -> {return new KnobsA();}
			case "B" -> {return new KnobsB();}
			case "C" -> {return new KnobsC();}
		}
		return null;
	}
	public Covers createCovers(String type){
		switch(type){
			case "A" -> {return new CoversA();}
			case "B" -> {return new CoversB();}
			case "C" -> {return new CoversC();}
		}
		return null;
	}
	public Neck createNeck(String type){
		switch(type){
			case "A" -> {return new NeckA();}
			case "B" -> {return new NeckB();}
			case "C" -> {return new NeckC();}
		}
		return null;
	}
	public Pickguard createPickguard(String type){
		switch(type){
			case "A" -> {return new PickguardA();}
			case "B" -> {return new PickguardB();}
			case "C" -> {return new PickguardC();}
		}
		return null;
	}
	public Pickups createPickups(String type){
		switch(type){
			case "A" -> {return new PickupsA();}
			case "B" -> {return new PickupsB();}
			case "C" -> {return new PickupsC();}
		}
		return null;
	}
}
class SouthsideKitFactory implements AbstractGuitarKitFactory{
	public Bridge createBridge(String type){
		switch(type){
			case "A" -> {return new BridgeA();}
			case "B" -> {return new BridgeB();}
			case "D" -> {return new BridgeD();}
		}
		return null;
	}
	public Knobs createKnobs(String type){
		switch(type){
			case "A" -> {return new KnobsA();}
			case "B" -> {return new KnobsB();}
			case "D" -> {return new KnobsD();}
		}
		return null;
	}
	public Covers createCovers(String type){
		switch(type){
			case "A" -> {return new CoversA();}
			case "B" -> {return new CoversB();}
			case "D" -> {return new CoversD();}
		}
		return null;
	}
	public Neck createNeck(String type){
		switch(type){
			case "A" -> {return new NeckA();}
			case "B" -> {return new NeckB();}
			case "D" -> {return new NeckD();}
		}
		return null;
	}
	public Pickguard createPickguard(String type){
		switch(type){
			case "A" -> {return new PickguardA();}
			case "B" -> {return new PickguardB();}
			case "D" -> {return new PickguardD();}
		}
		return null;
	}
	public Pickups createPickups(String type){
		switch(type){
			case "A" -> {return new PickupsA();}
			case "B" -> {return new PickupsB();}
			case "D" -> {return new PickupsD();}
		}
		return null;
	}
}
interface Bridge{
	public Bridge createBridge();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
interface Knobs{
	public Knobs createKnobs();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
interface Covers{
	public Covers createCovers();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
interface Neck{
	public Neck createNeck();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
interface Pickguard{
	public Pickguard createPickguard();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
interface Pickups{
	public Pickups createPickups();
	public void setName(String name);
	public String getName();
	public double getPrice();
}
class BridgeA implements Bridge{
//	@Override//not sure if this should be here or not
	//tentative attempt on implementation?
	String name;
	double price;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public BridgeA(){
		this.name = "BridgeA";
		this.price = 10.00;
	}
	@Override
	public Bridge createBridge(){
		return null;
	}
}
class BridgeB implements Bridge{


	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}
	String name;
	double price;
	public BridgeB(){
		this.name = "BridgeB";
		this.price = 11.00;
	}
	@Override
	public Bridge createBridge(){
		return null;
	}
}
class BridgeC implements Bridge{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public BridgeC(){
		this.name = "BridgeC";
		this.price = 12.00;
	}
	@Override
	public Bridge createBridge(){
		return null;
	}
}
class BridgeD implements Bridge{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public BridgeD(){
		this.name = "BridgeD";
		this.price = 13.00;
	}
	@Override
	public Bridge createBridge(){
		return null;
	}
}
class KnobsA implements Knobs{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public KnobsA(){
		this.name = "KnobsA";
		this.price = 5.00;
	}
	@Override
	public Knobs createKnobs(){
		return null;
	}
}
class KnobsB implements Knobs{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public KnobsB(){
		this.name = "KnobsB";
		this.price = 6.00;
	}
	@Override
	public Knobs createKnobs(){
		return null;
	}
}
class KnobsC implements Knobs{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public KnobsC(){
		this.name = "KnobsC";
		this.price = 7.00;
	}
	@Override
	public Knobs createKnobs(){
		return null;
	}
}
class KnobsD implements Knobs{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public KnobsD(){
		this.name = "KnobsD";
		this.price = 8.00;
	}
	@Override
	public Knobs createKnobs(){
		return null;
	}
}
class CoversA implements Covers{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public CoversA(){
		this.name = "CoversA";
		this.price = 7.00;
	}
	@Override
	public Covers createCovers(){
		return null;
	}
}
class CoversB implements Covers{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public CoversB(){
		this.name = "CoversB";
		this.price = 8.00;
	}
	@Override
	public Covers createCovers(){
		return null;
	}
}
class CoversC implements Covers{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public CoversC(){
		this.name = "CoversC";
		this.price = 9.00;
	}
	@Override
	public Covers createCovers(){
		return null;
	}
}
class CoversD implements Covers{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public CoversD(){
		this.name = "CoversD";
		this.price = 10.00;
	}
	@Override
	public Covers createCovers(){
		return null;
	}
}
class NeckA implements Neck{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public NeckA(){
		this.name = "NeckA";
		this.price = 15.00;
	}
	@Override
	public Neck createNeck(){
		return null;
	}
}
class NeckB implements Neck{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public NeckB(){
		this.name = "NeckB";
		this.price = 16.00;
	}
	@Override
	public Neck createNeck(){
		return null;
	}
}
class NeckC implements Neck{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public NeckC(){
		this.name = "NeckC";
		this.price = 17.00;
	}
	@Override
	public Neck createNeck(){
		return null;
	}
}
class NeckD implements Neck{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public NeckD(){
		this.name = "NeckD";
		this.price = 18.00;
	}
	@Override
	public Neck createNeck(){
		return null;
	}
}
class PickguardA implements Pickguard{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickguardA(){
		this.name = "PickguardA";
		this.price = 3.00;
	}
	@Override
	public Pickguard createPickguard(){
		return null;
	}
}
class PickguardB implements Pickguard{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickguardB(){
		this.name = "PickguardB";
		this.price = 4.00;
	}
	@Override
	public Pickguard createPickguard(){
		return null;
	}
}
class PickguardC implements Pickguard{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickguardC(){
		this.name = "PickguardC";
		this.price = 5.00;
	}
	@Override
	public Pickguard createPickguard(){
		return null;
	}
}
class PickguardD implements Pickguard{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickguardD(){
		this.name = "PickguardD";
		this.price = 6.00;
	}
	@Override
	public Pickguard createPickguard(){
		return null;
	}
}
class PickupsA implements Pickups{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickupsA(){
		this.name = "PickupsA";
		this.price = 20.00;
	}
	@Override
	public Pickups createPickups(){
		return null;
	}
}
class PickupsB implements Pickups{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickupsB(){
		this.name = "PickupsB";
		this.price = 21.00;
	}
	@Override
	public Pickups createPickups(){
		return null;
	}
}
class PickupsC implements Pickups{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickupsC(){
		this.name = "PickupsC";
		this.price = 22.00;
	}
	@Override
	public Pickups createPickups(){
		return null;
	}
}
class PickupsD implements Pickups{
	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public double getPrice(){
		return price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	String name;
	double price;
	public PickupsD(){
		this.name = "PickupsD";
		this.price = 23.00;
	}
	@Override
	public Pickups createPickups(){
		return null;
	}
}

class GuitarKit extends Item{
	Bridge bridge;
	Knobs knobs;
	Covers covers;
	Neck neck;
	Pickguard pickguard;
	Pickups pickups;

	public Bridge getBridge(){
		return bridge;
	}

	public void setBridge(Bridge bridge){
		this.bridge = bridge;
	}

	public Knobs getKnobs(){
		return knobs;
	}

	public void setKnobs(Knobs knobs){
		this.knobs = knobs;
	}

	public Covers getCovers(){
		return covers;
	}

	public void setCovers(Covers covers){
		this.covers = covers;
	}

	public Neck  getNeck(){
		return neck;
	}

	public void setNeck(Neck neck){
		this.neck = neck;
	}

	public Pickguard getPickguard(){
		return pickguard;
	}

	public void setPickguard(Pickguard pickguard){
		this.pickguard = pickguard;
	}

	public Pickups getPickups(){
		return pickups;
	}

	public void setPickups(Pickups pickups){
		this.pickups = pickups;
	}

	public GuitarKit(Bridge bridge, Knobs knobs, Covers covers, Neck neck, Pickguard pickguard, Pickups pickups){
		this.bridge = bridge;
		this.knobs = knobs;
		this.covers = covers;
		this.neck = neck;
		this.pickguard = pickguard;
		this.pickups = pickups;
		this.itemType = ItemType.GUITARKIT;
		this.listPrice = bridge.getPrice() + knobs.getPrice() + covers.getPrice() + neck.getPrice() + pickguard.getPrice() + pickups.getPrice();
	}
	public void printKit(){
		out("This kit has parts:");
		out(bridge.getName() + " - $" + bridge.getPrice());
		out(knobs.getName() + " - $" + knobs.getPrice());
		out(covers.getName() + " - $" + covers.getPrice());
		out(neck.getName() + " - $" + neck.getPrice());
		out(pickguard.getName() + " - $" + pickguard.getPrice());
		out(pickups.getName() + " - $" + pickups.getPrice());
		double total = bridge.getPrice() + knobs.getPrice() + covers.getPrice() + neck.getPrice() + pickguard.getPrice() + pickups.getPrice();
		out("Total price = $" + total);
	}
}
