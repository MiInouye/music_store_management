package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import java.util.function.IntUnaryOperator;

public abstract class Staff {
    String name;    // Velma and Shaggy
}

interface Tune{//https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
    void tune(Item item);//Strategy pattern Implementation
    boolean getTuned(Item item);
}
class haphazardTune implements Tune{//https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
    @Override
    public void tune(Item item) {//strategy pattern implementation
            if(Utility.rnd()<.5){
                item.doTune();
            }
    }
    @Override
    public boolean getTuned(Item item){
        return item.isTuned();
    }//Strategy Pattern Implementation
}

class manualTune implements Tune{//https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
    @Override//Strategy Pattern
    public void tune(Item item) {
        if(item.isTuned()){//when item is already tuned,
            if(Utility.rnd()<.2){//20% chance to change from tuned to not tuned
                item.doTune();//dotune func swaps bool values
            }
        }
        else{//if item isn't tuned
            if(Utility.rnd()>.2){//80% chance to tune the instrument correctly
                item.doTune();
                //item.setTuned(true);
            }
        }
    }
    @Override
    public boolean getTuned(Item item){
        return item.isTuned();
    }//Strategy Pattern
}
class electronicTune implements Tune{//https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
    @Override
    public void tune(Item item) {//Strategy Pattern
        if(!item.isTuned()){
            item.doTune();
        }
        //item.setTuned(true);
    }
    @Override
    public boolean getTuned(Item item){
        return item.isTuned();
    }//Strategy Pattern
}

class Clerk extends Staff implements Logger {
    int daysWorked;
    double damageChance;    // Velma = .05, Shaggy = .20, Daphne = .03
    Boolean isSick;
    Store store;
    Tune tuneType;
    ArrayList<IObserver> observers = new ArrayList<IObserver>();
    String event;

//    void removeObserver(IObserver observer){
//        observers.remove(observer);
//    }//Observer Pattern Implementation
//    void addObserver(IObserver observer){
//        observers.add(observer);
//    }//Observer Pattern Implementation
//    void notifyObservers(){//Observer Pattern Implementation
//        for(IObserver observer: observers){
//            observer.update();
//        }
//    }

//    String getEvent(){
//        return event;
//    }//Observer Pattern Implementation
//
//    void setEvent(String event){//Observer Pattern Implementation
//        this.event = event;
//        this.notifyObservers();
//        if(event.contains("endDay")){
//            this.observers.get(0).end
//        }
//    }

    void setTuneType(Tune tuneType){//Strategy Pattern Implementation
        this.tuneType = tuneType;
    }

    void execTune(Item item){//Strategy Pattern Implementation
        tuneType.tune(item);
    }
    Clerk(String name, double damageChance, Boolean isSick, Store store, Tune tuneType) {
         this.name = name;
         this.damageChance = damageChance;
         this.store = store;
         this.isSick = isSick;
         daysWorked = 0;
         this.tuneType = tuneType;
    }


    void arriveAtStore() {
        out(this.name + " arrives at store.");
        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
        dailyEvents1.update(store, "Daily Event: " + this.name + " is working today");
        //setEvent("Daily Event: " + this.name + " is working today");
        // have to check for any arriving items slated for this day
        out( this.name + " checking for arriving items.");
        // there's a tricky concurrent removal thing that prevents doing this
        // with a simple for loop - you need to use an iterator
        // https://www.java67.com/2014/03/2-ways-to-remove-elementsobjects-from-ArrayList-java.html#:~:text=There%20are%20two%20ways%20to,i.e.%20remove(Object%20obj).
        Iterator<Item> itr = store.inventory.arrivingItems.iterator();
        if(store.inventory.arrivingItems.size() > 0){//preventing error where an event is set when no items are arriving.
            dailyEvents1.update(store, "Daily Event: " + store.inventory.arrivingItems.size() + " arriving items");
            //setEvent("Daily Event: " + Integer.toString(store.inventory.arrivingItems.size())+ " arriving items");
            //store.setEvent("");
        }

        while (itr.hasNext()) {//loop to add items to inventory from arrivals
            Item item = itr.next();
            if (item.dayArriving == store.today) {
                out( this.name + " adds " + item.itemType.toString().toLowerCase() + " to inventory.");
                store.inventory.items.add(item);
                itr.remove();
            }
        }
    }

    void checkRegister() {
        out(this.name + " checks: "+Utility.asDollar(store.cashRegister)+" in register.");//
        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
        dailyEvents1.update(store, " Daily Event: " + (store.cashRegister) + "$ in the register");
        //setEvent("Daily Event: " + (store.cashRegister) + "$ in the register");
        if (store.cashRegister<75) {
            out("Cash register is low on funds.");
            this.goToBank();
        }
    }

    void goToBank() {
        out(this.name + " gets money from the bank.");
        store.cashRegister += 1000;
        store.cashFromBank += 1000;
        this.checkRegister();
        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
        dailyEvents1.update(store, " Daily Event: " + (store.cashRegister) + "$ in the register");
        //setEvent("Daily Event: " + (store.cashRegister) + "$ in the register");
    }

    void doInventory() {
        out(this.name + " is doing inventory.");
        for (ItemType type: ItemType.values()) {
            if(type  != ItemType.GUITARKIT){

                int numItems = store.inventory.countByType(store.inventory.items,type);
                if (numItems == 0 && type != ItemType.HAT && type != ItemType.SHIRT && type != ItemType.BANDANA) //check that item isn't clothing before reordering
                {
                    this.placeAnOrder(type);
                }
                if(numItems > 0){//safety statement
                    out(this.name + " counts " + numItems + " " + type.toString().toLowerCase());
                    if(type == ItemType.GUITAR || type == ItemType.MANDOLIN || type == ItemType.BASS || type == ItemType.CDPLAYER || type == ItemType.MP3 || type == ItemType.CASSETTEPLAYER || type == ItemType.RECORDPLAYER || type == ItemType.FLUTE || type == ItemType.HARMONICA || type == ItemType.SAXOPHONE){
                        //checking if the item is an item that needs tuning
                        int count = 0;
                        for(Item item: store.inventory.items) {
                            if (item.itemType == type) {
                                count++;
                                out(this.name + " is tuning " + item.name + count + ". ");
                                boolean alreadyTuned = item.isTuned();
                                this.execTune(item);//Strategy Pattern
                                boolean stillTuned = item.isTuned();
                                if(alreadyTuned){//if the item was already tuned
                                    if(!stillTuned){//and if the item is not tuned anymore after the clerk touched it
                                        this.damageAnItem(item);//item gets damaged
                                        if(item.condition != Condition.POOR){
                                            out(this.name + " damaged " + item.name + count + ".");
                                        }
                                        else{
                                            out(this.name + " damaged " + item.name + count + ". It is now broken and must be discarded.");
                                        }
                                        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
                                        dailyEvents1.update(store, " Daily Event: an item was damaged in tuning");
                                        //setEvent("Daily Event: an item was damaged in tuning");
                                        Tracker tracker1 = Tracker.getTrackerInstance();
                                        tracker1.update(store, " Tracker Event: Item Damaged " + store.activeClerk.name);
                                    }
                                }
                                if(item.isTuned()){//case for if the item wasn't tuned and now is
                                    out(item.name + count + " was tuned successfully.");
                                }
                                else{//case for failed to tune correctly
                                    out(this.name + " tried to tune " + item.name + count + " but was unsuccessful.");
                                }
                            }
                        }
                    }
                }
                if(numItems == 0 && type == ItemType.HAT){
                    Hat.setSoldOut();
                }
                if(numItems == 0 && type == ItemType.SHIRT){
                    Shirt.setSoldOut();
                }
                if(numItems == 0 && type == ItemType.BANDANA){
                    Bandana.setSoldOut();
                }
                //we set each individual clothing item to sold out and when they are all sold out, we confirm clothes are all sold out, so they are no longer accepted to be bought.
                if(Hat.checkSoldOut() && Shirt.checkSoldOut() && Bandana.checkSoldOut()){
                    Clothing.setOutOfStock();
                }
            }
        }
        int count = store.inventory.items.size();
        double worth = store.inventory.getValue(store.inventory.items);
        out(this.name + " finds " + count + " items in store, worth "+Utility.asDollar(worth));
        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
        dailyEvents1.update(store, " Daily Event: " + count + " items in the inventory");
        dailyEvents1.update(store, " Daily Event: " + Utility.asDollar(worth) + "$: total purchase price value of inventory items");

    }

    void placeAnOrder(ItemType type) {
        out(this.name + " needs to order "+type.toString().toLowerCase());
        // order 3 more of this item type
        // they arrive in 1 to 3 days
        int arrivalDay = Utility.rndFromRange(1,3);
        // check to see if any are in the arriving queue
        int count = store.inventory.countByType(store.inventory.arrivingItems,type);
        if (count>0) {
            out("There is an order coming for " + type.toString().toLowerCase());
        }
        else {
            DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
            dailyEvents1.update(store, " Daily Event: 3 have been ordered");
            // order 3 of the missing items if you have the money to buy them
            for (int i = 0; i < 3; i++) {
                Item item = store.inventory.makeNewItemByType(type);
                if (store.cashRegister > item.purchasePrice) {
                    out(this.name + " ordered a " + item.itemType.toString().toLowerCase());
                    item.dayArriving = store.today + arrivalDay;
                    store.inventory.arrivingItems.add(item);
                }
                else {
                    out("Insufficient funds to order this item.");
                }
            }
        }
    }

    //https://stackoverflow.com/questions/9832919/generate-poisson-arrival-in-java
    private int getPoissonRandom() {
        Random r = new Random();
        double L = Math.exp(-3);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }


    void openTheStore() {
        //determine buyers and sellers for the day
        int buyerPoisson = getPoissonRandom();
        int buyers = Utility.rndFromRange(2,2 + buyerPoisson);
        int sellers = Utility.rndFromRange(1,4);
        out(buyers + " buyers, " + sellers + " sellers today.");
        for (int i = 1; i <= buyers; i++) this.sellAnItem(i);
        for (int i = 1; i <= sellers; i++) this.buyAnItem(i);
    }
    void sellAnItem(int customer) {//sell an item to customer
//        if(customer == 0){//big if statement for interactive user
//
//        }
//        else{
//
//        }
        String custName = "Buyer " + customer;
        out(this.name + " serving " + custName);
        //randomly choose item type
        ItemType type = Utility.randomEnum(ItemType.class);
        out(custName + " wants to buy a " + type.toString().toLowerCase());
        int countInStock = store.inventory.countByType(store.inventory.items, type);
        // if no items - bye
        if (countInStock == 0) {
            out (custName + " leaves, no items in stock.");
        }
        else {
            // pick one of the types of items from inventory
            Item item = GetItemFromInventoryByCount(countInStock, type);
            out("Item is " + type.toString().toLowerCase() + " in " + item.condition.toString().toLowerCase() + " condition.");
            out (this.name + " selling at " + Utility.asDollar(item.listPrice));
            double sellChance = 0.0;
            // big if statements to calculate price increases based on item being tuned or not
            if(item.isTuned){
                if(item.itemType.equals(ItemType.CDPLAYER) || item.itemType.equals(ItemType.RECORDPLAYER) || item.itemType.equals(ItemType.MP3) || item.itemType.equals(ItemType.CASSETTEPLAYER)){
                    sellChance = 0.4;//10% increase to sellchance because player is equalized
                }
                else if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){
                    sellChance = 0.35;//15% increase to sellchance because stringed is tuned
                }
                else{
                    sellChance = 0.20;//20% increase to sellchance because wind is adjusted
                }
            }
            else{
                sellChance = 0.5;
            }
            if (Utility.rnd() > sellChance) {
                //stringed decorator cases
                if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){
                    StringedAccessories instrument = new StringedAccessories((Stringed) item);//Decorator Pattern
                    if(instrument.gigChance()){
                        int bagsInStock = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                        if (bagsInStock != 0) {
                            int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                            Item itemgigbag = GetItemFromInventoryByCount(tmpcount, ItemType.GIGBAG);
                            sellItemtoCustomer(itemgigbag, custName);
                        }
                    }
                    if(instrument.ampChance()){
                        int ampsInStock = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                        if (ampsInStock != 0) {
                            int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                            Item practiceAmp = GetItemFromInventoryByCount(tmpcount, ItemType.PRACTICEAMP);
                            sellItemtoCustomer(practiceAmp, custName);
                        }
                    }
                    if(instrument.cableChance()){
                        for(int i = 0; i < instrument.numCable(); i++){
                            int cablesInStock = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                            if (cablesInStock >= instrument.numCable()) {
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                                Item cable = GetItemFromInventoryByCount(tmpcount, ItemType.CABLE);
                                sellItemtoCustomer(cable, custName);
                            }
                        }
                    }
                    if(instrument.stringChance()){
                        for(int i = 0; i < instrument.numStrings(); i++){
                            int stringsInStock = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                            if (stringsInStock >= instrument.numStrings()) {
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                                Item string = GetItemFromInventoryByCount(tmpcount, ItemType.STRINGS);
                                sellItemtoCustomer(string, custName);
                            }
                        }
                    }
                }
                else{
                    sellItemtoCustomer(item, custName);
                }
            }
            else {//case for if the initial offer fails
                // if not, clerk offers 10% off listPrice
                item.listPrice = item.listPrice * .9;
                out (this.name + " selling at " + Utility.asDollar(item.listPrice));
                // now 75%/85%/90%/95% chance of buy
                double newChance = 0.0;

                if(item.isTuned){//another section for calculating new price based on tuned status
                    if(item.itemType.equals(ItemType.CDPLAYER) || item.itemType.equals(ItemType.RECORDPLAYER) || item.itemType.equals(ItemType.MP3) || item.itemType.equals(ItemType.CASSETTEPLAYER)){
                        newChance = 0.15;//10% increase to sellchance because player is equalized
                    }
                    else if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){
                        newChance = 0.10;//15% increase to sellchance because stringed is tuned
                    }
                    else{
                        newChance = 0.05;//20% increase to sellchance because wind is adjusted
                    }

                }
                else{
                    newChance = 0.25;
                }
                if (Utility.rnd() > newChance) {
                    //item.listPrice = newListPrice;
                    if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){//Decorator Pattern
                        StringedAccessories instrument = new StringedAccessories((Stringed) item);//Decorator Pattern
                        if(instrument.gigChance()){
                            int bagsInStock = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                            if (bagsInStock != 0) {
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                                Item itemgigbag = GetItemFromInventoryByCount(tmpcount, ItemType.GIGBAG);
                                sellItemtoCustomer(itemgigbag, custName);
                            }
                        }
                        if(instrument.ampChance()){
                            int ampsInStock = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                            if (ampsInStock != 0) {
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                                Item practiceAmp = GetItemFromInventoryByCount(tmpcount, ItemType.PRACTICEAMP);
                                sellItemtoCustomer(practiceAmp, custName);
                            }
                        }
                        if(instrument.cableChance()){
                            for(int i = 0; i < instrument.numCable(); i++){
                                int cablesInStock = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                                if (cablesInStock >= instrument.numCable()) {
                                    int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                                    Item cable = GetItemFromInventoryByCount(tmpcount, ItemType.CABLE);
                                    sellItemtoCustomer(cable, custName);
                                }
                            }
                        }
                        if(instrument.stringChance()){
                            for(int i = 0; i < instrument.numStrings(); i++){
                                int stringsInStock = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                                if (stringsInStock >= instrument.numStrings()) {
                                    int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                                    Item string = GetItemFromInventoryByCount(tmpcount, ItemType.STRINGS);
                                    sellItemtoCustomer(string, custName);
                                }
                            }
                        }
                    }
                    else{
                        sellItemtoCustomer(item, custName);
                    }

                }
                else {
                    out(custName + " wouldn't buy item.");
                }
            }
        }
    }

    // things we need to do when an item is sold
    void sellItemtoCustomer(Item item,String custName) {
        String itemName = item.itemType.toString().toLowerCase();
        String price = Utility.asDollar(item.listPrice);
        out (this.name + " is selling "+ itemName + " for " + price +" to "+custName);
        // when sold - move item to sold items with daySold and salePrice noted
        out ( "inventory count: "+store.inventory.items.size());
        store.inventory.items.remove(item);
        out ( "inventory count: "+store.inventory.items.size());
        item.salePrice = item.listPrice;
        item.daySold = store.today;
        store.inventory.soldItems.add(item);
        // money for item goes to register
        store.cashRegister += item.listPrice;

        DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
        dailyEvents1.update(store, " Daily Event: an item has been sold");
        Tracker tracker1 = Tracker.getTrackerInstance();//singlton pattern
        tracker1.update(store, " Tracker Event: Item Sold " + store.activeClerk.name);//observer pattern

    }

    // find a selected item of a certain type from the items
    Item GetItemFromInventoryByCount(int countInStock, ItemType type) {
        int count = 0;
        for(Item item: store.inventory.items) {
            if (item.itemType == type) {
                count += 1;
                if (count == countInStock) return item;
            }
        }
        return null;
    }


    void buyAnItem(int customer) {//buy an item from a customer
        int boughtItems = 0;
        String custName = "Seller "+customer;
        out(this.name+" serving "+custName);
        ItemType type = Utility.randomEnum(ItemType.class);
        out(custName + " wants to sell a "+type.toString().toLowerCase());
        Item item = store.inventory.makeNewItemByType(type);
        if((type == ItemType.BANDANA || type == ItemType.HAT || type == ItemType.SHIRT) && Clothing.isOutOfStock()){//condition for if a customer tries to sell a clothing item
            out(custName + " wanted to sell clothing, but the FNMS doesn't buy clothes anymore, sorry!");
        }
        else{
            // clerk will determine new or used, condition, purchase price (based on condition)
            // we'll take the random isNew, condition from the generated item
            out("Item is "+type.toString().toLowerCase()+" in "+item.condition.toString().toLowerCase()+" condition.");
            item.purchasePrice = getPurchasePriceByCondition(item.condition);
            // seller has 50% chance of selling
            out (this.name+" offers "+Utility.asDollar(item.purchasePrice));
            if (Utility.rnd()>.5) {
                buyItemFromCustomer(item, custName);
            }
            else {
                // if not, clerk will add 10% to purchasePrice
                item.purchasePrice += item.purchasePrice * .10;
                out (this.name+" offers "+Utility.asDollar(item.purchasePrice));
                // seller has 75% chance of selling
                if (Utility.rnd()>.25) {
                    buyItemFromCustomer(item, custName);
                }
                else {
                    out(custName + " wouldn't sell item.");
                }
            }
        }
    }

    void buyItemFromCustomer(Item item, String custName) {
        String itemName = item.itemType.toString().toLowerCase();
        String price = Utility.asDollar(item.purchasePrice);
        out (this.name + " is buying "+ itemName + " for " + price + " from " + custName);
        if (store.cashRegister>item.purchasePrice) {
            store.cashRegister -= item.purchasePrice;
            item.listPrice = 2 * item.purchasePrice;
            item.dayArriving = store.today;
            store.inventory.items.add(item);

            DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
            dailyEvents1.update(store, " Daily Event: an item has been bought");
            Tracker tracker1 = Tracker.getTrackerInstance();//singleton pattern
            tracker1.update(store, " Tracker Event: Item Bought " + store.activeClerk.name);

        }
        else {
            out(this.name + "cannot buy item, register only has " + Utility.asDollar(store.cashRegister));
        }
    }


    double getPurchasePriceByCondition(Condition condition) {
        int lowPrice = 2*condition.level;
        int highPrice = 10*condition.level;
        return (double) Utility.rndFromRange(lowPrice,highPrice);
    }

    void damageAnItem(Item i) {//function made to lower the condition by one whenever damage is done to an item.
        Condition cond = i.condition;
        switch(cond){
            case EXCELLENT -> {i.condition = Condition.VERYGOOD; }
            case VERYGOOD -> {i.condition = Condition.GOOD;}
            case GOOD -> {i.condition = Condition.FAIR;}
            case FAIR -> {i.condition = Condition.POOR;}
            case POOR -> {store.inventory.discardedItems.add(i);}
        }
    }

    void cleanTheStore() {
        out(this.name + " is cleaning up the store.");
        if (Utility.rnd()>this.damageChance) {
            out(this.name + " doesn't damage anything while cleaning.");
        }
        else {
            ItemType type = Utility.randomEnum(ItemType.class);//choosing random item type
            int itemNum = store.inventory.countByType(store.inventory.items, type);
            while(itemNum == 0){//short loop to make sure we dont choose an itemtype that we dont have any items of.
                type = Utility.randomEnum(ItemType.class);
                itemNum = store.inventory.countByType(store.inventory.items, type);
            }
            Item item = GetItemFromInventoryByCount(itemNum, type);
            this.damageAnItem(item);
            if(item.condition != Condition.POOR){
                out(this.name + " damaged " + item.name + ".");
            }
            else{
                out(this.name + " damaged " + item.name + ". It is now broken and must be discarded.");
            }

            DailyEvents dailyEvents1 = DailyEvents.getDailyEventsInstance();
            dailyEvents1.update(store, " Daily Event: an item has been bought");
            Tracker tracker1 = Tracker.getTrackerInstance();//singleton pattern
            tracker1.update(store, " Tracker Event: Item Bought " + store.activeClerk.name);

        }
    }
    void leaveTheStore() {
        out(this.name + " locks up the store and leaves.");
    }
    void AskClerkName(){
        out("My name is " + this.name + "! :)");
    }
    void AskForTime(){
        int hourNum = Utility.rndFromRange(9,21);
        int firstMinNum = Utility.rndFromRange(0,5);
        int secondMinNum = Utility.rndFromRange(0,9);
        out("The time is: " + hourNum + ":" + firstMinNum + secondMinNum + ". ");
    }
    void BuyItemFromUser(){
        ItemType type = Utility.randomEnum(ItemType.class);//determining randomly what item the customer is trying to sell
        out("You are trying to sell a " + type.toString().toLowerCase() + " to the store.");
        Item item = store.inventory.makeNewItemByType(type);
        if((type == ItemType.BANDANA || type == ItemType.HAT || type == ItemType.SHIRT) && Clothing.isOutOfStock()){//condition for if a customer tries to sell a clothing item
            out(" Sorry, we don't buy clothes anymore!");
        }
        else{
            // clerk will determine new or used, condition, purchase price (based on condition)
            // we'll take the random isNew, condition from the generated item
            out("Item is " + type.toString().toLowerCase() + " in " + item.condition.toString().toLowerCase() + " condition.");
            item.purchasePrice = this.getPurchasePriceByCondition(item.condition);
            // seller has 50% chance of selling
            out(this.name + " offers " + Utility.asDollar(item.purchasePrice));
            out("Would you like to sell? Enter y or n");
            Scanner myObj = new Scanner(System.in);
            String input = myObj.nextLine().toLowerCase();
            if(input.equals("y") || input.equals("n")){
                if(input.equals("y")){//user input
                    this.buyItemFromCustomer(item, "you");
                } else{
                    // if not, clerk will add 10% to purchasePrice
                    item.purchasePrice += item.purchasePrice * .10;
                    out(this.name + " offers " + Utility.asDollar(item.purchasePrice));
                    out("Would you like to sell? Enter y or n");
                    // seller has 75% chance of selling
                    String newinput = myObj.nextLine().toLowerCase();
                    if(newinput.equals("y")){//user input again
                        this.buyItemFromCustomer(item, "you");
                    } else{
                        out("You decided not to sell your " + type.toString().toLowerCase() + ".");
                    }
                }
            } else{
                out("Sorry, that was not a valid option.");
            }
        }
    }
    ItemType chooseItemType() {//function made to lower the condition by one whenever damage is done to an item.
        out("Choose what item type you would like to buy. Enter a number: ");
        out("1. PAPERSCORE");
        out("2. CD");
        out("3. VINYL");
        out("4. GUITAR");
        out("5. BASS");
        out("6. MANDOLIN");
        out("7. FLUTE");
        out("8. HARMONICA");
        out("9. HAT");
        out("10. SHIRT");
        out("11. BANDANA");
        out("12. PRACTICEAMP");
        out("13. CABLE");
        out("14. STRINGS");
        out("15. SAXOPHONE");
        out("16. CASSETTE");
        out("17. CASSETTEPLAYER");
        out("18. GIGBAG");
        out("19. CDPLAYER");
        out("20. RECORDPLAYER");
        out("21. MP3");

        Scanner myObj = new Scanner(System.in);
        String input = myObj.nextLine();
        int i = Integer.parseInt(input);
        switch(i){
            case 1 -> {return ItemType.PAPERSCORE;}
            case 2 -> {return ItemType.CD;}
            case 3 -> {return ItemType.VINYL;}
            case 4 -> {return ItemType.GUITAR;}
            case 5 -> {return ItemType.BASS;}
            case 6 -> {return ItemType.MANDOLIN;}
            case 7 -> {return ItemType.FLUTE;}
            case 8 -> {return ItemType.HARMONICA;}
            case 9 -> {return ItemType.HAT;}
            case 10 -> {return ItemType.SHIRT;}
            case 11 -> {return ItemType.BANDANA;}
            case 12 -> {return ItemType.PRACTICEAMP;}
            case 13 -> {return ItemType.CABLE;}
            case 14 -> {return ItemType.STRINGS;}
            case 15 -> {return ItemType.SAXOPHONE;}
            case 16 -> {return ItemType.CASSETTE;}
            case 17 -> {return ItemType.CASSETTEPLAYER;}
            case 18 -> {return ItemType.GIGBAG;}
            case 19 -> {return ItemType.CDPLAYER;}
            case 20 -> {return ItemType.RECORDPLAYER;}
            case 21 -> {return ItemType.MP3;}
        }
        return Utility.randomEnum(ItemType.class);//if a not valid input was entered, choose a random type instead
    }
    void SellItemToUser(){
        //Clerk clerk = store.getActiveClerk();
        //randomly choose item type
        Scanner myObj = new Scanner(System.in);

        ItemType type = chooseItemType();
        out( "You want to buy a " + type.toString().toLowerCase());
        int countInStock = store.inventory.countByType(store.inventory.items, type);
        // if no items - bye
        if (countInStock == 0) {
            out ("Sorry, there are no " + type.toString().toLowerCase() + " in stock.");
        }
        else {
            // pick one of the types of items from inventory
            Item item = this.GetItemFromInventoryByCount(countInStock, type);
            out("Item is " + type.toString().toLowerCase() + " in " + item.condition.toString().toLowerCase() + " condition.");
            out (this.name + " offers to sell the " + type.toString().toLowerCase() + " for $" + Utility.asDollar(item.listPrice));
            out("Would you like to buy? Enter y or n");

            String input = myObj.nextLine().toLowerCase();
            if (input.equals("y")) {//if the user decides to buy the item
                //stringed decorator cases
                //if statements for when the item is a guitar/bass/mandolin, asking if they want to buy a gig bag, amp, cable, or strings
                if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){
                    StringedAccessories instrument = new StringedAccessories((Stringed) item);//Decorator Pattern
                    int bagsInStock = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                    if (bagsInStock != 0){
                        out ("This item can be bought with a gig bag as well, would you like to buy one? Enter y or n");
                        Scanner myObj1 = new Scanner(System.in);
                        String gigInput = myObj1.nextLine().toLowerCase();
                        if(gigInput.equals("y")){//user input here
                            int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                            Item itemgigbag = this.GetItemFromInventoryByCount(tmpcount, ItemType.GIGBAG);
                            this.sellItemtoCustomer(itemgigbag, "you");
                        }
                    }
                    else{
                        out("Sorry, there are no gig bags in stock for you to buy at the moment.");
                    }
                    int ampsInStock = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                    if (ampsInStock != 0) {
                        out("This item can be bought with an amp as well, would you like to buy one? Enter y or n");
                        Scanner myObj2 = new Scanner(System.in);
                        String ampInput = myObj2.nextLine().toLowerCase();
                        if (ampInput.equals("y")) {//user input here
                            int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                            Item practiceAmp = this.GetItemFromInventoryByCount(tmpcount, ItemType.PRACTICEAMP);
                            this.sellItemtoCustomer(practiceAmp, "you");
                        }
                    }
                    else{
                        out("Sorry, there are no amps in stock for you to buy at the moment.");
                    }
                    int cablesInStock = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                    if (cablesInStock >= instrument.numCable()) {
                        out("This item can be bought with cables as well, would you like to buy some? Enter y or n");
                        Scanner myObj3 = new Scanner(System.in);
                        String cableInput = myObj3.nextLine().toLowerCase();
                        if (cableInput.equals("y")) {//user input here
                            for(int i = 0; i < instrument.numCable(); i++){
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                                Item cable = this.GetItemFromInventoryByCount(tmpcount, ItemType.CABLE);
                                this.sellItemtoCustomer(cable, "you");
                            }
                        }
                    }
                    else{
                        out("Sorry, there are no cables in stock for you to buy at the moment.");
                    }
                    int stringsInStock = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                    if (stringsInStock >= instrument.numStrings()) {
                        out("This item can be bought with strings as well, would you like to buy some? Enter y or n");
                        Scanner myObj4 = new Scanner(System.in);
                        String stringInput = myObj4.nextLine().toLowerCase();
                        if (stringInput.equals("y")) {//user input here
                            for(int i = 0; i < instrument.numStrings(); i++){
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                                Item string = this.GetItemFromInventoryByCount(tmpcount, ItemType.STRINGS);
                                this.sellItemtoCustomer(string, "you");
                            }
                        }
                    }
                    else{
                        out("Sorry, there are no strings in stock for you to buy at the moment.");
                    }
                }
                else{//if the item isn't a stringed instrument, skip down here
                    this.sellItemtoCustomer(item, "you");
                }
            }
            else if(input.equals("n")){//case for if user says no
                // if not, clerk offers 10% off listPrice
                item.listPrice = item.listPrice * .9;
                out (this.name + " offers to sell the " + type.toString().toLowerCase() + " for $" + Utility.asDollar(item.listPrice));
                out("Would you like to buy it? Enter y or n");
                Scanner myObj5 = new Scanner(System.in);
                String newInput = myObj5.nextLine().toLowerCase();
                if (newInput.equals("y")) {//user input here
                    //if user decides to buy item and it is a stringed instrument, if statement below is entered and user is asked to buy accessories
                    if(item.itemType.equals(ItemType.GUITAR) || item.itemType.equals(ItemType.BASS) || item.itemType.equals(ItemType.MANDOLIN)){
                        StringedAccessories instrument = new StringedAccessories((Stringed) item);//Decorator Pattern
                        int bagsInStock = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                        if (bagsInStock != 0){
                            out ("This item can be bought with a gig bag as well, would you like to buy one? Enter y or n");
                            Scanner myObj6 = new Scanner(System.in);
                            String gigInput = myObj6.nextLine().toLowerCase();
                            if(gigInput.equals("y")){//user input here
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.GIGBAG);
                                Item itemgigbag = this.GetItemFromInventoryByCount(tmpcount, ItemType.GIGBAG);
                                this.sellItemtoCustomer(itemgigbag, "you");
                            }
                        }
                        else{
                            out("Sorry, there are no gig bags in stock for you to buy at the moment.");
                        }
                        int ampsInStock = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                        if (ampsInStock != 0) {
                            out("This item can be bought with an amp as well, would you like to buy one? Enter y or n");
                            Scanner myObj7 = new Scanner(System.in);
                            String ampInput = myObj7.nextLine().toLowerCase();
                            if (ampInput.equals("y")) {//user input here
                                int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.PRACTICEAMP);
                                Item practiceAmp = this.GetItemFromInventoryByCount(tmpcount, ItemType.PRACTICEAMP);
                                this.sellItemtoCustomer(practiceAmp, "you");
                            }
                        }
                        else{
                            out("Sorry, there are no amps in stock for you to buy at the moment.");
                        }
                        int cablesInStock = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                        if (cablesInStock >= instrument.numCable()) {
                            out("This item can be bought with cables as well, would you like to buy some? Enter y or n");
                            Scanner myObj8 = new Scanner(System.in);
                            String cableInput = myObj8.nextLine().toLowerCase();
                            if (cableInput.equals("y")) {//user input here
                                for(int i = 0; i < instrument.numCable(); i++){
                                    int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.CABLE);
                                    Item cable = this.GetItemFromInventoryByCount(tmpcount, ItemType.CABLE);
                                    this.sellItemtoCustomer(cable, "you");
                                }
                            }
                        }
                        else{
                            out("Sorry, there are no cables in stock for you to buy at the moment.");
                        }
                        int stringsInStock = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                        if (stringsInStock >= instrument.numStrings()) {
                            out("This item can be bought with strings as well, would you like to buy some? Enter y or n");
                            Scanner myObj9 = new Scanner(System.in);
                            String stringInput = myObj9.nextLine().toLowerCase();
                            if (stringInput.equals("y")) {//user input here
                                for(int i = 0; i < instrument.numStrings(); i++){
                                    int tmpcount = store.inventory.countByType(store.inventory.items, ItemType.STRINGS);
                                    Item string = this.GetItemFromInventoryByCount(tmpcount, ItemType.STRINGS);
                                    this.sellItemtoCustomer(string, "you");
                                }
                            }
                        }
                        else{
                            out("Sorry, there are no strings in stock for you to buy at the moment.");
                        }
                    }
                    else{//else if the instrument isn't stringed, simply sell the item
                        this.sellItemtoCustomer(item, "you");
                    }
                }
                else if(newInput.equals("n")){//if they decline to buy the item completely
                    out("You decided not to buy the " + type.toString().toLowerCase() + ".");
                }
                else{//bad input
                    out("Sorry, that was not a valid option.");
                }
            }
        }
    }
    void SellGuitarKit(){
        Scanner myObj = new Scanner(System.in);
        Bridge bridge;
        Knobs knobs;
        Covers covers;
        Neck neck;
        Pickguard pickguard;
        Pickups pickups;
        if(store.storeName.equals("store_0")){
            NorthsideKitFactory northStore = new NorthsideKitFactory();

            out("What type of bridge would you like to buy? Enter A, B, or C");
            String input1 = myObj.nextLine().toUpperCase();
            bridge = northStore.createBridge(input1);

            out("What type of knobs would you like to buy? Enter A, B, or C");
            String input2 = myObj.nextLine().toUpperCase();
            knobs = northStore.createKnobs(input2);

            out("What type of cover would you like to buy? Enter A, B, or C");
            String input3 = myObj.nextLine().toUpperCase();
            covers = northStore.createCovers(input3);

            out("What type of neck would you like to buy? Enter A, B, or C");
            String input4 = myObj.nextLine().toUpperCase();
            neck = northStore.createNeck(input4);

            out("What type of pickguard would you like to buy? Enter A, B, or C");
            String input5 = myObj.nextLine().toUpperCase();
            pickguard = northStore.createPickguard(input5);

            out("What type of pickups would you like to buy? Enter A, B, or C");
            String input6 = myObj.nextLine().toUpperCase();
            pickups = northStore.createPickups(input6);
        }
        else{
            SouthsideKitFactory southStore = new SouthsideKitFactory();

            out("What type of bridge would you like to buy? Enter A, B, or D");
            String input1 = myObj.nextLine().toUpperCase();
            bridge = southStore.createBridge(input1);

            out("What type of knobs would you like to buy? Enter A, B, or D");
            String input2 = myObj.nextLine().toUpperCase();
            knobs = southStore.createKnobs(input2);

            out("What type of cover would you like to buy? Enter A, B, or D");
            String input3 = myObj.nextLine().toUpperCase();
            covers = southStore.createCovers(input3);

            out("What type of neck would you like to buy? Enter A, B, or D");
            String input4 = myObj.nextLine().toUpperCase();
            neck = southStore.createNeck(input4);

            out("What type of pickguard would you like to buy? Enter A, B, or D");
            String input5 = myObj.nextLine().toUpperCase();
            pickguard = southStore.createPickguard(input5);

            out("What type of pickups would you like to buy? Enter A, B, or D");
            String input6 = myObj.nextLine().toUpperCase();
            pickups = southStore.createPickups(input6);
        }
        GuitarKit guitarkit = new GuitarKit(bridge, knobs, covers, neck, pickguard, pickups);
        guitarkit.printKit();
        this.sellItemtoCustomer(guitarkit, "you");
    }

}