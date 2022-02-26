package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
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



    void removeObserver(IObserver observer){
        observers.remove(observer);
    }//Observer Pattern Implementation
    void addObserver(IObserver observer){
        observers.add(observer);
    }//Observer Pattern Implementation
    void notifyObservers(){//Observer Pattern Implementation
        for(IObserver observer: observers){
            observer.update();
        }
    }

    String getEvent(){
        return event;
    }//Observer Pattern Implementation

    void setEvent(String event){//Observer Pattern Implementation
        this.event = event;
        this.notifyObservers();
//        if(event.contains("endDay")){
//            this.observers.get(0).end
//        }
    }

    void setTuneType(Tune tuneType){//Strategy Pattern Implementation
        this.tuneType = tuneType;
    }

    void execTune(Item item){//Strategy Pattern Implementation
        tuneType.tune(item);
    }
    Clerk(String name, double damageChance, Boolean health, Store store, Tune tuneType) {
         this.name = name;
         this.damageChance = damageChance;
         this.store = store;
         this.isSick = health;
         daysWorked = 0;
         this.tuneType = tuneType;
    }


    void arriveAtStore() {
        out(this.name + " arrives at store.");
        setEvent("Daily Event: " + this.name + " is working today");
        // have to check for any arriving items slated for this day
        out( this.name + " checking for arriving items.");
        // there's a tricky concurrent removal thing that prevents doing this
        // with a simple for loop - you need to use an iterator
        // https://www.java67.com/2014/03/2-ways-to-remove-elementsobjects-from-ArrayList-java.html#:~:text=There%20are%20two%20ways%20to,i.e.%20remove(Object%20obj).
        Iterator<Item> itr = store.inventory.arrivingItems.iterator();
        if(store.inventory.arrivingItems.size() > 0){//preventing error where an event is set when no items are arriving.
            setEvent("Daily Event: " + Integer.toString(store.inventory.arrivingItems.size())+ " arriving items");
            store.setEvent("");
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
        setEvent("Daily Event: " + (store.cashRegister) + "$ in the register");
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
        setEvent("Daily Event: " + (store.cashRegister) + "$ in the register");
    }

    void doInventory() {
        out(this.name + " is doing inventory.");
        for (ItemType type: ItemType.values()) {
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
                                    setEvent("Daily Event: an item was damaged in tuning");
                                    store.setEvent("Tracker Event: Item Damaged " + this.name);
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
        int count = store.inventory.items.size();
        double worth = store.inventory.getValue(store.inventory.items);
        out(this.name + " finds " + count + " items in store, worth "+Utility.asDollar(worth));
        setEvent("Daily Event: " + count + " items in the inventory");
        setEvent("Daily Event: " + Utility.asDollar(worth) + "$: total purchase price value of inventory items");

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
            setEvent("Daily Event: 3 have been ordered");
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
                double newListPrice = item.listPrice * .9;
                out (this.name + " selling at " + Utility.asDollar(newListPrice));
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
                    item.listPrice = newListPrice;
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
        setEvent("Daily Event: an item has been sold");//Observer Pattern
        store.setEvent("Tracker Event: Item Sold" + this.name);//Observer Pattern
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


    void buyAnItem(int customer) {
        int boughtItems = 0;
        String custName = "Seller "+customer;
        out(this.name+" serving "+custName);
        ItemType type = Utility.randomEnum(ItemType.class);
        out(custName + " wants to sell a "+type.toString().toLowerCase());
        Item item = store.inventory.makeNewItemByType(type);
        if(Clothing.isOutOfStock()){//condition for if a customer tries to sell a clothing item
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
            setEvent("Daily Event: an item has been bought");//Observer Pattern
            store.setEvent("Tracker Event: Item Bought" + this.name);//Observer Pattern
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
            setEvent("Daily Event: an item has been damaged in cleaning");//Observer Pattern
            store.setEvent("Tracker Event: Item Damaged" + this.name);//Observer Pattern
        }
    }
    void leaveTheStore() {
        out(this.name + " locks up the store and leaves.");
    }


}