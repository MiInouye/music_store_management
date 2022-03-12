package com.OOAD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

class StoreInitializerTest {//https://www.vogella.com/tutorials/JUnit/article.html
    Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.clerks.add(new Clerk("Velma",.05, false, store, new manualTune()));//tuning methods are applied to clerk for strategy pattern, but hard coded in bc it was simple for only 3 clerks and we were struggling on time.
        store.clerks.add(new Clerk("Daphne",.03, false, store, new haphazardTune()));
        store.clerks.add(new Clerk("Shaggy", .2, false, store, new electronicTune()));
        store.clerks.add(new Clerk("Fred",.04, false, store, new manualTune()));
        store.clerks.add(new Clerk("Scooby",.1, false, store, new haphazardTune()));
        store.clerks.add(new Clerk("Scrappy", .07, false, store, new electronicTune()));
        store.makeSick();
    }


    @RepeatedTest(5)
    @DisplayName("Ensures there are the right number of stores created ")
    void testItemMaker() {
        boolean someSick=false;
        for(Clerk clerk: store.clerks){
            if(clerk.isSick){
                someSick = true;
                assertTrue(someSick,"Not all items were initialized");
            }
        }
        assertFalse(someSick,"Not all items were initialized");
    }
    @Test
    @DisplayName("Ensures there are the right number of items created ")
    void testItemAmount() {
        assertEquals(63, store.inventory.items.size(),"Not all items were initialized");
    }

    @Test
    @DisplayName("Tests is get valid clerk works")
    void testGetClerkSick() {
        Clerk dummy = store.getValidClerk();
        assertFalse(dummy.isSick, "Get valid clerk will pass sick people");
    }
    @Test
    @DisplayName("Tests is get valid clerk works")
    void testGetClerkExausted() {
        Clerk dummy = store.getValidClerk();
        assertFalse(dummy.daysWorked > 3, "Get valid clerk will pass people with more than 3 days worked");
    }
}





public class Store implements Logger {
    public ArrayList<Clerk> clerks = new ArrayList<Clerk>();
    public String storeName;
    public Clerk activeClerk;
    public double cashRegister;
    public double cashFromBank;
    public Inventory inventory;
    public int today;
    ArrayList<IObserver> observers = new ArrayList<IObserver>();
    String event;

    Store() {
        // initialize the store's starting inventory
        inventory = new Inventory();

        cashRegister = 0;   // cash register is empty to begin
        cashFromBank = 0;   // no cash from bank yet

    }

    public Clerk getActiveClerk(){
        return activeClerk;
    }

    public void setActiveClerk(Clerk activeClerk){
        this.activeClerk = activeClerk;
    }

    public void setClerks(Clerk clerk){
        clerks.add(clerk);
    }
    public ArrayList<Clerk> getClerks(){
        return clerks;
    }

//    void removeObserver(IObserver observer){
//        observers.remove(observer);
//    }
//    void addObserver(IObserver observer){//Observer pattern implementations for the daily logger
//        observers.add(observer);
//    }
//    void notifyObservers(){//Observer pattern implementations for the daily logger
//        for(IObserver observer: observers){
//            observer.update();
//        }
//    }

//    String getEvent(){
//        return event;
//    }//Observer pattern implementations for the daily logger
//
//    void setEvent(String event){//Observer pattern implementations for the daily logger
//        this.event = event;
//        notifyObservers();
//    }


    void openToday(int day) {
        today = day;

        out("Store opens today, day "+day);
        //activeClerk = getValidClerk();
        DailyEvents dailyObserver = DailyEvents.getDailyEventsInstance();//instantiating new daily logger observer
        dailyObserver.update(this, " " + (day) + " :day");
        out(activeClerk.name + " is working today.");
        activeClerk.arriveAtStore();
        out("-----------------------------------------");
        activeClerk.checkRegister();
        out("-------------DOING INVENTORY-------------");
        activeClerk.doInventory();
        out("-------------BUYING/SELLING-------------");
        activeClerk.openTheStore();
        out("-------------END OF DAY ACTIONS-------------");
        activeClerk.cleanTheStore();
        activeClerk.leaveTheStore();
        out("---------------------------------------------");
        out("               DAY " + day + " SUMMARY");
        out("---------------------------------------------");
        dailyObserver.update(this,"Daily Event: " + activeClerk.name + " locks up and leaves for the day.");//setting daily event for observer that the day has ended
        //activeClerk.removeObserver(dailyObserver);//removing the observer after the end of the day to avoid repeats
        out("---------------------------------------------");
        out("                     END OF DAY " + day);
        for(Clerk clerk: clerks){//resets everyone to healthy
            clerk.isSick = false;
        }
    }

    void makeSick(){
        for(int i = 0; i < 2; i++){
            int isSick = Utility.rndFromRange(0,60);
            if(isSick % 10 == 1){//decides who gets sick if anyone does
                clerks.get(isSick%3).isSick = true;
            }
        }
    }


    Clerk getValidClerk() {
        makeSick();
//        for (Clerk tmp: clerks) {
//            if (tmp.daysWorked > 3){
//                tmp.isSick = true;//if a worker has worked 3 days in a row, set them to sick so they aren't valid to work
//                tmp.daysWorked = 0; // and reset their counter
//            }
//        }
        // pick a random clerk
        Clerk clerk = clerks.get(Utility.rndFromRange(0,clerks.size()-1));
        if( clerk.isSick){//if the clerk chosen is sick
            out(clerk.name+" is sick and cannot work today.");
            String sickClerk = clerk.name;
            while(clerk.name.equals(sickClerk)){//randomly chooses a clerk until it doesn't choose the sick one
                clerk = clerks.get(Utility.rndFromRange(0,clerks.size()-1));
            }
        //instead of check active < 3 and then add 1 and set everyone else to 0,
        // check everyone less than 3, if so they are valid to be picked, else set flag valid + set days 0
        }
        else if (clerk.daysWorked < 3) {// if they are ok to work, set days worked on other clerks to 0
            clerk.daysWorked += 1;
            for (Clerk other: clerks) {
                if (other != clerk) other.daysWorked = 0; // they had a day off, so clear their counter
            }
        }
        // if they are not ok to work, set their days worked to 0 and get another clerk
        else {
            out(clerk.name+" has worked maximum of 3 days in a row.");
            clerk.daysWorked = 0;   // they can't work, get another clerk
            for (Clerk other: clerks) {
                if (other != clerk) {
                    clerk = other;
                    break;
                }
            }
        }
        return clerk;
    }

    void closedToday(int day) {
        out("Store is closed today, day "+day);
    }
//    void endOfSim(){//function to summarize store stats at the end of the simulation
//        out("---------------END OF SIMULATION SUMMARY---------------");
//        double totalItemVal = 0;
//        out(" ");
//        out("Items in inventory: ");
//        for(Item item: inventory.items){
//            totalItemVal = totalItemVal + item.purchasePrice;
//            out(item.name);
//        }
//        out(Utility.asDollar(totalItemVal) + "$ is the value of inventory");
//        for(Item item: inventory.soldItems){
//            out("item sold: " + item.name + " Day Sold: " + item.daySold + " Sale Price: " + Utility.asDollar(item.salePrice));
//        }
//        out(Utility.asDollar(this.cashRegister) + "$ in the cash register");
//        out(Utility.asDollar(this.cashFromBank) + "$ from the bank");
//    }
}
