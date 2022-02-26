package com.OOAD;
import java.util.ArrayList;

// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

public class Store implements Logger {
    public ArrayList<Clerk> clerks;
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

        // initialize the store's staff
        clerks = new ArrayList<Clerk>();
        clerks.add(new Clerk("Velma",.05, false, this, new manualTune()));//tuning methods are applied to clerk for strategy pattern, but hard coded in bc it was simple for only 3 clerks and we were struggling on time.
        clerks.add(new Clerk("Daphne",.03, false, this, new haphazardTune()));//tuning methods are applied to clerk for strategy pattern, but hard coded in bc it was simple for only 3 clerks and we were struggling on time.
        clerks.add(new Clerk("Shaggy", .2, false, this, new electronicTune()));//tuning methods are applied to clerk for strategy pattern, but hard coded in bc it was simple for only 3 clerks and we were struggling on time.
    }

    void removeObserver(IObserver observer){
        observers.remove(observer);
    }
    void addObserver(IObserver observer){//Observer pattern implementations for the daily logger
        observers.add(observer);
    }
    void notifyObservers(){//Observer pattern implementations for the daily logger
        for(IObserver observer: observers){
            observer.update();
        }
    }

    String getEvent(){
        return event;
    }//Observer pattern implementations for the daily logger

    void setEvent(String event){//Observer pattern implementations for the daily logger
        this.event = event;
        notifyObservers();
    }

    void openToday(int day) {
        today = day;

        out("Store opens today, day "+day);
        activeClerk = getValidClerk();
        IObserver dailyObserver = new DailyEvents(activeClerk);//instantiating new daily logger observer
        out(activeClerk.name + " is working today.");
        activeClerk.setEvent(Integer.toString(day));
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
        activeClerk.setEvent("Daily Event: " + activeClerk.name + " locks up and leaves for the day.");//setting daily event for observer that the day has ended
        activeClerk.removeObserver(dailyObserver);//removing the observer after the end of the day to avoid repeats
        out("---------------------------------------------");
        out("                     END OF DAY " + day);
        out("---------------------------------------------");
    }

    Clerk getValidClerk() {

        for(Clerk clerk: clerks){//resets everyone to healthy
            clerk.isSick = true;
        }
        int isSick = Utility.rndFromRange(0,30);
        if(isSick % 10 == 1){//decides who gets sick if anyone does
            clerks.get(isSick%3).isSick = true;
        }

        // pick a random clerk
        Clerk clerk = clerks.get(Utility.rndFromRange(0,clerks.size()-1));

        if( clerk.isSick){//if the clerk chosen is sick
            out(clerk.name+" is sick and cannot work today.");
            String sickClerk = clerk.name;
            while(clerk.name.equals(sickClerk)){//randomly chooses a clerk until it doesn't choose the sick one
                clerk = clerks.get(Utility.rndFromRange(0,clerks.size()-1));
            }

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
    void endOfSim(){//function to summarize store stats at the end of the simulation
        out("---------------END OF SIMULATION SUMMARY---------------");
        double totalItemVal = 0;
        out(" ");
        out("Items in inventory: ");
        for(Item item: inventory.items){
            totalItemVal = totalItemVal + item.purchasePrice;
            out(item.name);
        }
        out(Utility.asDollar(totalItemVal) + "$ is the value of inventory");
        for(Item item: inventory.soldItems){
            out("item sold: " + item.name + " Day Sold: " + item.daySold + " Sale Price: " + Utility.asDollar(item.salePrice));
        }
        out(Utility.asDollar(this.cashRegister) + "$ in the cash register");
        out(Utility.asDollar(this.cashFromBank) + "$ from the bank");
    }
}
