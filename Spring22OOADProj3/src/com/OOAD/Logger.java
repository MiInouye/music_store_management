package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface Logger {
    default void out(String msg) {
        System.out.println(msg);
    }
}

abstract class IObserver{//Observer Pattern Abstract Class
    abstract void update();
}

class DailyEvents extends IObserver implements Logger{//Concrete Observer Pattern Class
    int day = 1;
    ArrayList<String> daysEvents = new ArrayList<>();
    Clerk tempClerk;
    boolean ifEndOfDay = false;
    DailyEvents(Clerk clerk){//actual observer
        this.tempClerk = clerk;
        this.tempClerk.addObserver(this);
    }

    public void update(){
        //dumpDailyLog();
        //add events to daily event log
        daysEvents.add(tempClerk.getEvent());
        //looking for locks to signify the end of the day
        if(tempClerk.getEvent().contains("locks")){
            ifEndOfDay = true;
            endDaySummary();
            day++;
        }
    }
    void dumpDailyLog(){//function used in testing to print all data
        for(String event: daysEvents){
            out(event);
        }
    }
    void endDaySummary(){//function for writing days events to the file
        int itemsOrdered = 0;
        int itemsSold = 0;
        int itemsBought = 0;
        int itemsDamagedCleaned = 0;
        int itemsDamagedTuned = 0;
        for(String event: daysEvents){
            if(event.contains("ordered")){
                itemsOrdered = itemsOrdered + 3;
            }else if(event.contains("sold")){
                itemsSold++;
            }else if(event.contains("bought")){
                itemsBought++;
            }else if(event.contains("cleaning")){
                itemsDamagedCleaned++;
            }else if(event.contains("tuning")){
                itemsDamagedTuned++;
            }
        }
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
    Store tempStore;
    Tracker(Store store){
        //initializing clerks to be tracked and a list for each of their stats
        this.tempStore = store;
        this.tempStore.addObserver(this);
        for(Clerk clerk: this.tempStore.clerks){
            trackerEvents.put(clerk.name, new ArrayList<Integer>());
            trackerEvents.get(clerk.name).add(0, 0);
            trackerEvents.get(clerk.name).add(1, 0);
            trackerEvents.get(clerk.name).add(2, 0);
        }
    }

    public void update(){
        if(tempStore.getEvent().contains("Tracker")){//uses the tracker event prefix to differentiate between the tracker and logger
            String name = tempStore.activeClerk.name;
            //if statements to increment stats when an event is published
            if(tempStore.getEvent().contains("Sold")){
                int itemsSold = trackerEvents.get(name).get(0);
                itemsSold += 1;
                trackerEvents.get(name).set(0, itemsSold);
            }
            else if(tempStore.getEvent().contains("Bought")){
                int itemsBought = trackerEvents.get(name).get(1);
                itemsBought += 1;
                trackerEvents.get(name).set(1, itemsBought);
            }
            else if(tempStore.getEvent().contains("Damaged")){
                int itemsDamaged = trackerEvents.get(name).get(2);
                itemsDamaged += 1;
                trackerEvents.get(name).set(2, itemsDamaged);
            }
        }
        if(tempStore.getEvent().contains("summaryDayEnd")){
            endDaySummary();
        }
    }
    void endDaySummary(){
        out("Clerk - Items Sold - Items Purchased - Items Damaged");
        trackerEvents.forEach((k, v) -> out(k + " - " + v.get(0) + " - " + v.get(1) + " - " + v.get(2)));
    }


}