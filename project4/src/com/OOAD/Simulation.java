package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Collections;

import static com.OOAD.Tracker.getTrackerInstance;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class SimulationConstructorTest {//https://www.vogella.com/tutorials/JUnit/article.html
    //testing the Simulation constructor

    Simulation simulation;

    @BeforeEach
    void setUp() {
        simulation = new Simulation();
    }

    @Test
    @DisplayName("Ensures there are only 6 clerks added to the array list ")
    void testClerks() {
        assertEquals(6, simulation.allClerks.size(),"wrong number of employees");
    }

    @Test
    @DisplayName("Ensures there are the right number of stores created ")
    void testCreateStores() {
        simulation.createStore(2);
        assertEquals(2, simulation.stores.size(),"wrong number of stores");
    }

    @Test
    @DisplayName("Ensures there are only 6 clerks created")
    void testStores() {
        assertEquals(6, simulation.stores.size(),"wrong number of employees");
    }
    @Test
    @DisplayName("Ensures Velma is created")
    void testVelma() {
        assertEquals("Velma", simulation.allClerks.get(0).name,"wrong name for Velma");
    }
    @Test
    @DisplayName("Ensures Daphne is created")
    void testDaphne() {
        assertEquals("Daphne", simulation.allClerks.get(1).name,"wrong name for Daphne");
    }
    @Test
    @DisplayName("Ensures Shaggy is created")
    void testShaggy() {
        assertEquals("Shaggy", simulation.allClerks.get(2).name,"wrong name for Shaggy");
    }
    @Test
    @DisplayName("Ensures Fred is created")
    void testFred() {
        assertEquals("Fred", simulation.allClerks.get(3).name,"wrong name for Fred");
    }
    @Test
    @DisplayName("Ensures Scooby is created")
    void testScooby() {
        assertEquals("Scooby", simulation.allClerks.get(4).name,"wrong name for Scooby");
    }
    @Test
    @DisplayName("Ensures Scrappy is created")
    void testScrappy() {
        assertEquals("Scrappy", simulation.allClerks.get(5).name,"wrong name for Scrappy");
    }
}



// top level object to run the simulation
public class Simulation implements Logger{
    //Store store;
    ArrayList<Store> stores = new ArrayList<Store>();
    ArrayList<Clerk> allClerks = new ArrayList<Clerk>();
    ArrayList<String> clerkFlags = new ArrayList<String>();
    int dayCounter;
    Weekday weekDay;


    // enum for Weekdays
    // next implementation from
    // https://stackoverflow.com/questions/17006239/whats-the-best-way-to-implement-next-and-previous-on-an-enum-type
    public static enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;
        private static Weekday[] vals = values();
        public Weekday next() {
            return vals[(this.ordinal()+1) % vals.length];
        }
    }

    @Test
    public static void testSimDays(int days) {// Testing if the simulated days are within the correct range

        boolean min = days >= 10;
        boolean max = days <= 30;
        assertTrue(min, "Too few days for the sim");
        assertFalse(max, "Too many days for the sim");

    }

    Simulation() {
        weekDay = Weekday.MONDAY;   //set the starting day
        dayCounter = 0;
        stores = createStore(2);
        // initialize the store's staff
        allClerks.add(new Clerk("Velma",.05, false, stores.get(0), new manualTune()));//tuning methods are applied to clerk for strategy pattern, but hard coded in bc it was simple for only 3 clerks and we were struggling on time.
        allClerks.add(new Clerk("Daphne",.03, false, stores.get(0), new haphazardTune()));
        allClerks.add(new Clerk("Shaggy", .2, false, stores.get(0), new electronicTune()));
        allClerks.add(new Clerk("Fred",.04, false, stores.get(1), new manualTune()));
        allClerks.add(new Clerk("Scooby",.1, false, stores.get(1), new haphazardTune()));
        allClerks.add(new Clerk("Scrappy", .07, false, stores.get(1), new electronicTune()));
        for(int i = 0; i < 2; i++){
            stores.get(i).setClerks(allClerks.get(i*3));
            stores.get(i).setClerks(allClerks.get(1 + (i*3)));
            stores.get(i).setClerks(allClerks.get(2 + (i*3)));
        }

    }

    ArrayList<Store> createStore (int num){// function to create however many stores needed
        ArrayList<Store> stores = new ArrayList<Store>();
        for(int i = 0; i < num; i++){
            Store temp = new Store();
            temp.storeName = "store_" + i;
            stores.add(temp);
        }
        return stores;
    }

    void startSim(int days) {
        for (int day = 1; day <= days; day++) {
            out(" ");
            out("---------------------------------------------");
            out("         *** Simulation day "+day+" ***");
            out("---------------------------------------------");

            Clerk clerk = stores.get(0).getValidClerk();//choosing clerks for each store to be used
            stores.get(0).setActiveClerk(clerk);
            Clerk newClerk = stores.get(1).getValidClerk();
            stores.get(1).setActiveClerk(newClerk);
//            makeClerksSick();
//            setActiveClerks();
            startDay(day);
            Tracker tracker1 = Tracker.getTrackerInstance();//have all stores have a line saying it's the summary
            tracker1.update(stores.get(0), "summaryDayEnd");
            tracker1.update(stores.get(1), "summaryDayEnd");
            //setEvent("summaryDayEnd");//set a day end event for the tracker to subscribe to and publish a summary at the end of each day
        }
        Clerk clerk = stores.get(0).getValidClerk();//choosing clerks for each store to be used
        stores.get(0).setActiveClerk(clerk);
        Clerk newClerk = stores.get(1).getValidClerk();
        stores.get(1).setActiveClerk(newClerk);
//        makeClerksSick();
//        setActiveClerks();
        //referenced user input from https://www.w3schools.com/java/java_user_input.asp
        Scanner myObj = new Scanner(System.in);
        Store store = stores.get(0);
        String input = "";
        while(!input.equals("g")){
            out("Choose an option:");
            out("a. Select a store to issue commands to");
            out("b. Ask the clerk their name");
            out("c. Ask the clerk what time it is");
            out("d. Sell an item to the clerk");
            out("e. Buy an item from the store");
            out("f. Buy a custom guitar kit from the store");
            out("g. Quit");
            input = myObj.nextLine().toLowerCase();
            Invoker invoker = new Invoker(store);
            switch(input){
                case "a" -> {
                    out("Choose which store to interact with: ");
                    out("Northside FNMS or Southside FNMS");
                    out("Enter a or b to choose");
                    String storeinput = myObj.nextLine().toLowerCase();
                    if(storeinput.equals("a")){
                        store = stores.get(0);
                        out("You are now at the Northside FNMS!");
                    }
                    else if(storeinput.equals("b")){
                        store = stores.get(1);
                        out("You are now at the Southside FNMS!");
                    }
                    else{
                        out("Invalid Input");
                    }
                }
                case "b" -> {
                    AskName nameQuestion = new AskName(store);//command example in use
                    invoker.setCommand(nameQuestion);
                    invoker.nameWasAsked();
                }
                case "c" -> {
                    AskTime timeQuestion = new AskTime(store);
                    invoker.setCommand(timeQuestion);
                    invoker.timeWasAsked();
                }
                case "d" -> {
                   SellItemToStore sellItem = new SellItemToStore(store);
                   invoker.setCommand(sellItem);
                   invoker.itemSoldToStore();
                }
                case "e" -> {
                    BuyItemFromStore buyItem = new BuyItemFromStore(store);
                    invoker.setCommand(buyItem);
                    invoker.itemBoughtFromStore();
                }
                case "f" -> {
                    BuyGuitarKitFromStore buyKit = new BuyGuitarKitFromStore(store);
                    invoker.setCommand(buyKit);
                    invoker.guitarKitBoughtFromStore();
                }
                case "g" -> {out("Have a nice day! :D");}
                default -> {out("Choose a letter from a - g");}
            }
        }
        endOfSim();//call store function to output summary of stats
    }

    void startDay(int day) {
            if (weekDay == Weekday.SUNDAY){
                stores.get(0).closedToday(day);
                stores.get(1).closedToday(day);
            }
            else{
                stores.get(0).openToday(day);
                stores.get(1).openToday(day);
            }
            weekDay = weekDay.next();
    }

    void endOfSim(){//function to summarize store stats at the end of the simulation
        out("---------------END OF SIMULATION SUMMARY---------------");
        for(Store store: stores){
            if(store.storeName.equals("store_0")){
                out("");
                out("---------------------------------------------------");
                out("Northside Friendly Neighborhood Music Store Summary");
                out("---------------------------------------------------");
            }
            else{
                out("---------------------------------------------------");
                out("Southside Friendly Neighborhood Music Store Summary");
                out("---------------------------------------------------");
            }
            double totalItemVal = 0;
            out(" ");
            out("Items in inventory: ");
            for(Item item: store.inventory.items){
                totalItemVal = totalItemVal + item.purchasePrice;
                out(item.name);
            }
            out("");
            out(Utility.asDollar(totalItemVal) + "$ is the value of inventory");
            for(Item item: store.inventory.soldItems){
                out("item sold: " + item.name + " Day Sold: " + item.daySold + " Sale Price: " + Utility.asDollar(item.salePrice));
            }
            out(Utility.asDollar(store.cashRegister) + "$ in the cash register");
            out(Utility.asDollar(store.cashFromBank) + "$ from the bank");
        }


    }

}

