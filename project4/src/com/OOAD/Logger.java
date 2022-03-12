package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder
import java.io.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

public interface Logger {
    default void out(String msg) {
        System.out.println(msg);
    }
}

abstract class IObserver{//Observer Pattern Abstract Class
    abstract void update(Store store, String change);
}

class DailyEvents extends IObserver implements Logger{//Concrete Observer Pattern Class
    int day = 1;
    String dayString = "";
    Map<String, ArrayList<String>> eventsByStore = new HashMap<>();
    ArrayList<String> daysEvents = new ArrayList<>();
    Clerk tempClerk;
    boolean ifEndOfDay = false;

    public int getItemsOrdered(){
        return itemsOrdered;
    }

    public void setItemsOrdered(int itemsOrdered){
        this.itemsOrdered = itemsOrdered;
    }

    public int getItemsSold(){
        return itemsSold;
    }

    public void setItemsSold(int itemsSold){
        this.itemsSold = itemsSold;
    }

    public int getItemsBought(){
        return itemsBought;
    }

    public void setItemsBought(int itemsBought){
        this.itemsBought = itemsBought;
    }

    public int getItemsDamagedCleaned(){
        return itemsDamagedCleaned;
    }

    public void setItemsDamagedCleaned(int itemsDamagedCleaned){
        this.itemsDamagedCleaned = itemsDamagedCleaned;
    }

    public int getItemsDamagedTuned(){
        return itemsDamagedTuned;
    }

    public void setItemsDamagedTuned(int itemsDamagedTuned){
        this.itemsDamagedTuned = itemsDamagedTuned;
    }

    private int itemsOrdered = 0;
    private int itemsSold = 0;
    private int itemsBought = 0;
    private int itemsDamagedCleaned = 0;
    private int itemsDamagedTuned = 0;
    String logFile = "Logger-" + dayString + ".txt";
    PrintWriter writer;

    private static DailyEvents dailyEvent = null;//singleton instance
    private DailyEvents(){
        eventsByStore.put("store_0", new ArrayList<String>());
        eventsByStore.put("store_1", new ArrayList<String>());
    }//singleton constructor

    public static synchronized DailyEvents getDailyEventsInstance(){
        if(dailyEvent == null)
            dailyEvent = new DailyEvents();
        return dailyEvent;
    }


    public void update(Store store, String change){// puts the daily event into a map indexed by the store name
        String name = store.activeClerk.name;
        if(change.contains(":day")){
            dayString = change.substring(1, 3);
            logFile = "Logger-" + dayString + ".txt";
        } else if(change.contains("ordered")){
            setItemsOrdered(getItemsOrdered() + 3);
        } else if(change.contains("sold")){
            setItemsSold(getItemsSold() + 1);
        } else if(change.contains("bought")){
            setItemsBought(getItemsBought() + 1);
        } else if(change.contains("cleaning")){
            setItemsDamagedCleaned(getItemsDamagedCleaned() + 1);
        } else if(change.contains("tuning")){
            setItemsDamagedTuned(getItemsDamagedTuned() + 1);
        } else{
            try{//https://medium.com/@p.osinaga/using-singleton-in-java-b1b78cf640ed
                FileWriter fw = new FileWriter(logFile,true);
                writer = new PrintWriter(fw, true);
            } catch(IOException e){
            }
            writer.println(store.storeName + change);
            writer.println(store.storeName + "Daily Event: " + getItemsOrdered() + " items have been ordered\n");
            writer.println(store.storeName + "Daily Event: " + getItemsSold() + " items have been sold\n");
            writer.println(store.storeName + "Daily Event: " + getItemsBought() + " items have been bought\n");
            writer.println(store.storeName + "Daily Event: " + getItemsDamagedCleaned() + " items have been damaged in cleaning\n");
            writer.println(store.storeName + "Daily Event: " + getItemsDamagedTuned() + " items have been damaged in tuning\n");
        }
    }
        //add events to daily event log


    void dumpDailyLog(){//function used in testing to print all data
        for(String event: daysEvents){
            out(event);
        }
    }
    void endDaySummary(ArrayList<String> events){//function for writing days events to the file
        try {//https://www.w3schools.com/java/java_files_create.asp
            FileWriter myWriter = new FileWriter("Logger-" + daysEvents.get(0) + ".txt");
            myWriter.write("Daily Event: " + itemsOrdered + " items have been ordered\n");
            myWriter.write("Daily Event: " + itemsSold + " items have been sold\n");
            myWriter.write("Daily Event: " + itemsBought + " items have been bought\n");
            myWriter.write("Daily Event: " + itemsDamagedCleaned + " items have been damaged in cleaning\n");
            myWriter.write("Daily Event: " + itemsDamagedTuned + " items have been damaged in tuning\n");
            for(int i = 1; i < daysEvents.size(); i++){
                myWriter.write(daysEvents.get(i) + "\n");
            }
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        daysEvents.clear();
    }
}
class Tracker extends IObserver implements Logger{//Tracker Observer Pattern Concrete Class
    Map<String, ArrayList<Integer>> trackerEvents = new HashMap<String, ArrayList<Integer>>();//Map data structure to store clerk's data
    private static Tracker tracker = new Tracker();

    private Tracker(){
//        //initializing clerks to be tracked and a list for each of their stats
        ArrayList<Integer> vals = new ArrayList<>();
        vals.add(0);
        vals.add(0);
        vals.add(0);
        ArrayList<Integer> vals2 = new ArrayList<>();
        vals2.add(0);
        vals2.add(0);
        vals2.add(0);
        ArrayList<Integer> vals3 = new ArrayList<>();
        vals3.add(0);
        vals3.add(0);
        vals3.add(0);
        ArrayList<Integer> vals4 = new ArrayList<>();
        vals4.add(0);
        vals4.add(0);
        vals4.add(0);
        ArrayList<Integer> vals5 = new ArrayList<>();
        vals5.add(0);
        vals5.add(0);
        vals5.add(0);
        ArrayList<Integer> vals6 = new ArrayList<>();
        vals6.add(0);
        vals6.add(0);
        vals6.add(0);
        trackerEvents.put("Velma", vals);
        trackerEvents.put("Daphne", vals2);
        trackerEvents.put("Shaggy", vals3);
        trackerEvents.put("Fred", vals4);
        trackerEvents.put("Scooby", vals5);
        trackerEvents.put("Scrappy", vals6);


    }

    public static synchronized Tracker getTrackerInstance(){
        return tracker;
    }
    public void update(Store store,String change){
        String clerkName = store.activeClerk.name;
        //if statements to increment stats when an event is published
        if(change.contains("Sold")){
            int itemsSold = trackerEvents.get(clerkName).get(0);
            itemsSold += 1;
            trackerEvents.get(clerkName).set(0, itemsSold);
        }
        else if(change.contains("Bought")){
            int itemsBought = trackerEvents.get(clerkName).get(1);
            itemsBought += 1;
            trackerEvents.get(clerkName).set(1, itemsBought);
        }
        else if(change.contains("Damaged")){
            int itemsDamaged = trackerEvents.get(clerkName).get(2);
            itemsDamaged += 1;
            trackerEvents.get(clerkName).set(2, itemsDamaged);
        }

        if(change.contains("summaryDayEnd")){
            endDaySummary();
        }
    }
    void endDaySummary(){
        out("Clerk - Items Sold - Items Purchased - Items Damaged");
        trackerEvents.forEach((k, v) -> out(k + " - " + v.get(0) + " - " + v.get(1) + " - " + v.get(2)));
    }


}