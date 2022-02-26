package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

// top level object to run the simulation
public class Simulation implements Logger {
    Store store;
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

    Simulation() {
        weekDay = Weekday.MONDAY;   //set the starting day
        dayCounter = 0;
        store = new Store();
    }

    void startSim(int days) {
        IObserver tracker = new Tracker(store);//make a new tracker to run throughout simulation
        for (int day = 1; day <= days; day++) {
            out(" ");
            out("---------------------------------------------");
            out("         *** Simulation day "+day+" ***");
            out("---------------------------------------------");
            startDay(day);
            store.setEvent("summaryDayEnd");//set a day end event for the tracker to subscribe to and publish a summary at the end of each day
        }
        store.endOfSim();//call store function to output summary of stats
    }

    void startDay(int day) {
        if (weekDay == Weekday.SUNDAY) store.closedToday(day);
        else store.openToday(day);
        weekDay = weekDay.next();
    }

//    void summary() {
//        out("The summary is left as an exercise to the reader :-)");
//    }
}
