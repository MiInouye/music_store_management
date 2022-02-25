package com.Michael.musicStoreManagement;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Store{ //Cohesion: Store is an example of low cohesion code, it deals with days, inventory, money, people, all in the same place, but spread out throughout the entire class.
    public Map<Integer, Day> calendar(){return calendar;}

    Map<Integer,Day> calendar = new HashMap<Integer,Day>();
    Map<String,ArrayList<Item>> inventory = new HashMap<String,ArrayList<Item>>(); //realized later that we should have made a class and functions to interact with this easier
    Map<String,ArrayList<Item>> sold = new HashMap<String,ArrayList<Item>>();//Polymorphism: Polymorphism because it allows all different types of items to be stored in one place
    ArrayList<Staff> staff = new ArrayList<>();
    Random rand =  new Random();
    public Double getRegister(){return register;}

    public void setRegister(Double register){this.register = register;}

    public Double getLoans(){return loans;}

    public void setLoans(Double loans){this.loans = loans;}

    public Integer getDay(){return day;}

    public void setDay(Integer day){this.day = day;}

    private Double register = 0.0;
    private Double loans = 0.0;
    private Integer day = 0;



    //we made poor decision with our design to have item type and condition be stored as numbers, which then meant we had to make helper functions to convert to strings
    String condConverter(int condNum){
        return switch(condNum){
            case 1 -> "Poor";
            case 2 -> "Fair";
            case 3 -> "Good";
            case 4 -> "Very Good";
            case 5 -> "Excellent";
            default -> "Error";
        };
    }

    String itemTypeConverter(int itemNum){
        return switch(itemNum){
            case 0 -> "PaperScore";
            case 1 -> "MusicCD";
            case 2 -> "Vinyl";
            case 3 -> "CDPlayer";
            case 4 -> "RecordPlayer";
            case 5 -> "MP3";
            case 6 -> "Guitar";
            case 7 -> "Bass";
            case 8 -> "Mandolin";
            case 9 -> "Flute";
            case 10 -> "Harmonica";
            case 11 -> "Hats";
            case 12 -> "Shirts";
            case 13 -> "Bandana";
            case 14 -> "PracticeAmps";
            case 15 -> "Cables";
            case 16 -> "Strings";
            default -> "Error";
        };
    }
    void printStoreInfo(){//helper func to print everything for testing and end output
        System.out.println("Store Info:");
        double total = 0.0;
        System.out.println("Inventory contains:");
        for (Map.Entry<String,ArrayList<Item>> x:inventory.entrySet()) {//loops through the inventory
            System.out.println(x.getKey());
            for(Item y: x.getValue()){
                total += y.getPurchasePrice();//adds all prices together
            }
        }
        System.out.println("Total value of the inventory is: $" + total + ".");

        double saletotal = 0.0;
        System.out.println("Items Sold:");
        for (Map.Entry<String,ArrayList<Item>> x:sold.entrySet()) {//loops through the sales
            System.out.println(x.getKey());
            for(Item y: x.getValue()){
                saletotal += y.getSalePrice();//adds all prices together
            }
        }
        System.out.println("Total value of the sales is: $" + saletotal + ".");
        System.out.println("Final amount in the Cash Register is: $" + getRegister() + ".");
        System.out.println("Total money added to the register from the bank is: $" + getLoans() + ".");
    }
    

    ArrayList<Item> generateItems(int howMany, String itemType){//func used to make items quickly, used with item factory
        ItemFactory factory = new ItemFactory();
        ArrayList<Item> generated = new ArrayList<>();
        for(int i = 0 ;i < howMany ;i++){
            generated.add(factory.getItem(itemType));
        }
        return generated;
    }

    void ArriveAtStore(Clerk clerk){

        System.out.print(clerk.getName() + " arrives at the store on Day " + (getDay()+ 1) + ". ");
        if(getDay() > 2){ //checking for items sent to the store, after day 2
            ArrayList<Item> arrived = calendar.get(getDay()).shippedToStore();
            for(Item x : arrived){//loops through the items that arrive on that day
                System.out.println("One " + x.getItemType() + " has been added to the inventory");
                ArrayList<Item> temp = inventory.get(x.getItemType());
                temp.add(x);
                inventory.put(x.getItemType(), temp);
            }
        }
    }
    void CheckRegister(){
        System.out.println("The register has $" + getRegister().toString() +" in it");
        if(getRegister() < 75.0){
            GoToBank();
        }
    }
    void GoToBank(){
        setLoans(getLoans() + 1000.0);
        setRegister(getRegister() + 1000.0);
        System.out.println("The register now has $" + getRegister().toString() +" in it");
    }
    void DoInventory(){
        double total = 0.0;
        ItemFactory factory = new ItemFactory();
        for (Map.Entry<String,ArrayList<Item>> x:inventory.entrySet()) {//loops through the inventory
            for(Item y: x.getValue()){
                total += y.getListPrice();//adds all prices together
            }
            if(x.getValue().size() == 0){//checks if there are any items with none in stock
                Item item = factory.getItem(x.getKey());
                PlaceAnOrder(item);
                System.out.println("An order for "+ x.getKey() + " needs to be placed");
            }
        }
        System.out.println("Total value of inventory: $" + total);
    }
    void PlaceAnOrder(Item order){
        int shippingTime = rand.nextInt(3)+1;
        double purchasePrice = rand.nextDouble(50) + 1;
        setRegister(getRegister() - 3.0*purchasePrice);
        ArrayList<Item> generated = generateItems(3,order.getItemType());
        ArrayList<Item> existing = new ArrayList<>();
        if(calendar.get(getDay()+shippingTime) != null){//checks if there are items arriving on the same day as the ones just ordered
            existing = calendar.get((shippingTime+getDay())).shippedToStore() ;
        }
        for(int i = 0; i<3; i++){//loops through to add all newly ordered items to the already ordered items
             generated.get(i).setPurchasePrice(purchasePrice);
            generated.get(i).setPurchasePrice(purchasePrice);
             existing.add((generated.get(i)));
        }
        System.out.println("An order for "+ order.getItemType() + " has been placed");
        Day temp = new Day();
        temp.setClockedIn(calendar().get(getDay()).clockedIn());
        temp.setShippedToStore(existing);
        calendar.put(getDay() + shippingTime, temp);//adds to the items that are being shipped in into the calendar
    }
    void OpenTheStore(Clerk clerk){
        //generating customers for the day
        int numBuyers = rand.nextInt(4,10);
        int numSellers = rand.nextInt(1,4);
        ArrayList<Customer> buyerGroup = new ArrayList<>();
        ArrayList<Customer> sellerGroup = new ArrayList<>();

        for(Integer i = 0; i < numBuyers; i++){
            Customer buyer = new Customer();
            int randItem = rand.nextInt(0, 16);
            buyer.setWantedItemType(itemTypeConverter(randItem));//randomly choosing what a customer wants to buy
            buyer.setName(i.toString());
            buyerGroup.add(buyer);
            ArrayList<Item> itemtype = inventory.get(buyer.getWantedItemType()); //retrieving from inventory
            ///////////////////////////////////////
            //customer buying process starts here//
            ///////////////////////////////////////
            if(itemtype.size() == 0){
                System.out.println("Customer " + buyer.getName() + " wanted to buy a " + buyer.getWantedItemType() + " but none were in inventory, so they left.");
            }
            int chanceBuy = rand.nextInt(100);
            if(chanceBuy < 50){ //if they buy the first offer
                itemtype.get(0).setSalePrice(itemtype.get(0).getListPrice());//item price is same at sale as list
                itemtype.get(0).setDaySold(getDay()); //set day sold
                ArrayList<Item> temp = sold.get(itemTypeConverter(randItem)); //
                temp.add(itemtype.get(0));//////////////////////////////////////adding it to sold items
                sold.put(itemtype.get(0).getItemType(), temp);//////////////////
                setRegister(getRegister() + itemtype.get(0).getSalePrice());//adding money from purchase to register
                System.out.println(clerk.getName() + " sold a " + buyer.getWantedItemType() + " to Customer " + buyer.getName() + " for $" + itemtype.get(0).getSalePrice() + ".");
            }
            else{// if they decline first offer
                itemtype.get(0).setSalePrice(0.9 * itemtype.get(0).getListPrice());//item price is discounted 10%
                int newchanceBuy = rand.nextInt(100);
                if(newchanceBuy < 75){ //if they buy discount
                    itemtype.get(0).setDaySold(getDay()); //set day sold
                    ArrayList<Item> temp = sold.get(itemTypeConverter(randItem));//
                    temp.add(itemtype.get(0));/////////////////////////////////////add to sold items
                    sold.put(itemtype.get(0).getItemType(), temp);/////////////////
                    setRegister(getRegister() + itemtype.get(0).getSalePrice());//adding money from purchase to register
                    System.out.println(clerk.getName() + " sold a " + buyer.getWantedItemType() + " to Customer " + buyer.getName() + " for $" + itemtype.get(0).getSalePrice() + " after a 10% discount.");
                }
                else{ //if they don't buy
                    System.out.println("Customer " + buyer.getName() + " wanted to buy a " + buyer.getWantedItemType() + " but did not like the price, so they left.");
                }
            }

        }
        for(Integer j = 0; j < numSellers; j++){//section below is for generating customers that want to sell, and then making items for them to sell
            Customer seller = new Customer();
            int randItem = rand.nextInt(0, 16);
            seller.setName(j.toString());
            seller.setWantedItemType(itemTypeConverter(randItem));
            ArrayList<Item> sellItem = generateItems(1, seller.getWantedItemType());
            seller.setCart(sellItem);
            sellerGroup.add(seller);
        }
        ////////////////////////////////////////
        //customer selling process starts here//
        ////////////////////////////////////////
        for(Integer k = 0; k < sellerGroup.size(); k++ ){
            ArrayList<Item> itemtype = sellerGroup.get(k).getCart();
            Item item = itemtype.get(0);
            item.setCondition(rand.nextInt(1,5));
            if(item.getCond() == 5){
                double purchaseprice = rand.nextDouble(70.0, 90.0); //setting price for new items
                item.setPurchasePrice(purchaseprice);
            }
            else if(item.getCond() == 4){//setting price for each condition item, starting from very good
                item.setNewOrUsed(false);
                double purchaseprice = rand.nextDouble(60.0, 80.0);
                item.setPurchasePrice(purchaseprice);
            }
            else if(item.getCond() == 3){//good
                item.setNewOrUsed(false);
                double purchaseprice = rand.nextDouble(50.0, 70.0);
                item.setPurchasePrice(purchaseprice);
            }
            else if(item.getCond() == 2){//fair
                item.setNewOrUsed(false);
                double purchaseprice = rand.nextDouble(20.0, 40.0);
                item.setPurchasePrice(purchaseprice);
            }
            else{//poor
                item.setNewOrUsed(false);
                double purchaseprice = rand.nextDouble(10.0, 35.0);
                item.setPurchasePrice(purchaseprice);
            }//selling process for not new items begins below
            int buyChance = rand.nextInt(100);
            if(buyChance < 50){ //customer sells first try
                setRegister(getRegister() - item.getPurchasePrice());//removing money from register for purchase
                item.setDayArrived(getDay()); //set day purchased
                item.setListPrice(2 * item.getPurchasePrice()); //set price to sell item at
                inventory.put(item.getItemType(), itemtype); //putting item into inventory
                System.out.println("Customer " + sellerGroup.get(k).getName() + " sold a " + sellerGroup.get(k).getWantedItemType() + " to the FNMS for $" + item.getPurchasePrice());
            }
            else{
                item.setPurchasePrice(1.1 * item.getPurchasePrice());//10% increase in sell price
                int newbuyChance = rand.nextInt(100);
                if(newbuyChance < 75){ //customer sells second try
                    setRegister(getRegister() - item.getPurchasePrice());//removing money from register for purchase
                    item.setDayArrived(getDay()); //set day purchased
                    item.setListPrice(2 * item.getPurchasePrice()); //set price to sell item at
                    inventory.put(item.getItemType(), itemtype); //putting item into inventory
                    System.out.println("Customer " + sellerGroup.get(k).getName() + " sold a " + sellerGroup.get(k).getWantedItemType() + " to the FNMS for $" + item.getPurchasePrice() + " after a 10% haggle price.");
                }
                else{//didnt like sell price
                    System.out.println("Customer " + sellerGroup.get(k).getName() + " wanted to sell a " + sellerGroup.get(k).getWantedItemType() + " but did not like the price, so they left.");
                }
            }
            //}
        }
    }
    void CleanTheStore(Clerk clerk){
        int chanceOfDamage = rand.nextInt(100);
        if(clerk.getName().equals("shaggy") && chanceOfDamage < 20){ //check if damage is done based on random num generated
            int damagedItemNum = rand.nextInt(0,16); //randomly choosing type of item from list of itemtypes
            String damagedItemType = itemTypeConverter(damagedItemNum);
            ArrayList<Item> tmplist = inventory.get(damagedItemType); //choosing list of a specific item type from an array of various item types
            int randNum = rand.nextInt(0, tmplist.size());
            Item damagedItem = tmplist.get(randNum); //random item pulled from arraylist of one item type
            int tmpcond = damagedItem.getCond(); //condition of the chosen item
            String condtxt1 = condConverter(damagedItem.getCond()); //condition in string form
            if (tmpcond != 1){// when item is not already poor quality
                damagedItem.setCondition((tmpcond - 1));// drop condition number
                double tmpprice = damagedItem.getListPrice();
                damagedItem.setListPrice((0.8 * tmpprice));//lower price from damage
                String condtxt2 = condConverter(damagedItem.getCond());
                System.out.println("While cleaning, Shaggy damaged " + damagedItem.getName() + ". Item condition: " + condtxt1 + " -> " + condtxt2 + ". Price is now: $" + damagedItem.getListPrice());
            }
            else{
                tmplist.remove(damagedItem);
                System.out.println("While cleaning, Shaggy damaged " + damagedItem.getName() + ". Item is now in unusable condition and has been removed from inventory.");
            }
        }
        else if(clerk.getName().equals("velma") && chanceOfDamage < 5){ //check if damage is done based on random num generated
            int damagedItemNum = rand.nextInt(0,16);
            String damagedItemType = itemTypeConverter(damagedItemNum);
            ArrayList<Item> tmplist = inventory.get(damagedItemType); //list of a specific item type chosen from an array of item types
            int randNum = rand.nextInt(0, tmplist.size());
            Item damagedItem = tmplist.get(randNum); //random item pulled from arraylist of one item type
            int tmpcond = damagedItem.getCond();
            String condtxt1 = condConverter(damagedItem.getCond());
            if (tmpcond != 1){
                damagedItem.setCondition((tmpcond - 1));
                double tmpprice = damagedItem.getListPrice();
                damagedItem.setListPrice((0.8 * tmpprice));
                String condtxt2 = condConverter(damagedItem.getCond());
                System.out.println("While cleaning, Velma damaged " + damagedItem.getName() + ". Item condition: " + condtxt1 + " -> " + condtxt2 + ". Price is now: $" + damagedItem.getListPrice());
            }
            else{
                tmplist.remove(damagedItem);
                System.out.println("While cleaning, Velma damaged " + damagedItem.getName() + ". Item is now in unusable condition and has been removed from inventory.");
            }
        }
    }
    void LeaveTheStore(Clerk clerk){
        System.out.println(clerk.getName() + " locks up the store and is going home for the day");
    }
}
