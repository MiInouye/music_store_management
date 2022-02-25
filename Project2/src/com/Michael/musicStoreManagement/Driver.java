package com.Michael.musicStoreManagement;


import java.util.ArrayList;

public class Driver { //does this need to extend anything idk
    public static void main(String[] args){
        Store store = new Store();
        Clerk Shaggy = new Clerk();
        Clerk Velma = new Clerk();
        Shaggy.setName("Shaggy");//Identity: This is an example of identity because of the Clerks unique names.
        Velma.setName("Velma");
        Shaggy.setDaysWorked(0);
        Velma.setDaysWorked(0);
        store.setDay(0);
        Clerk working = new Clerk();
        for(int i = 0; i < 17; i++){ // initializing 3 of each item
            store.inventory.put(store.itemTypeConverter(i),store.generateItems(3, store.itemTypeConverter(i)));
        }
        for(int i = 0; i < 17; i++){ // initializing sold list
            ArrayList<Item> soldItem = new ArrayList<Item>();
            store.sold.put(store.itemTypeConverter(i), soldItem);
        }



        for(int day = 0; day < 30; day++){
            Day newDay = new Day();
            String temp1 = "new ArrayList<String>()";
            ArrayList<Item> temp2 = new ArrayList<Item>();
            newDay.setClockedIn(temp1);
            newDay.setShippedToStore(temp2);
            store.setDay(day);
            store.calendar.put(day, newDay);
            if(day < 3){ //edge case for the first 2 days so that there aren't segmentation faults
                System.out.println("Day = " + (day + 1));
                int worker = store.rand.nextInt(2);
                if(worker == 0){
                    working = Shaggy;
                    Shaggy.setDaysWorked(Shaggy.getDaysWorked()+1);
                }else{
                    working = Velma;
                    Velma.setDaysWorked(Velma.getDaysWorked()+1);
                }
            }else if(store.calendar().get(day - 1).clockedIn().equals(store.calendar().get(day - 2).clockedIn())){//checking if the previous 2 days had the same clerk working
                System.out.println("Day = " + (day + 1));
                int worker = store.rand.nextInt(2);
                if(worker == 0){
                    working = Shaggy;

                    Shaggy.setDaysWorked(Shaggy.getDaysWorked()+1);
                }else{
                    working = Velma;
                    Velma.setDaysWorked(Velma.getDaysWorked()+1);
                }
            }else{
                System.out.println("Day = " + (day + 1));
                if(store.calendar().get(day - 1).clockedIn().equals(Shaggy.getName())){//if last 2 days were worked by the same clerk, have the other clerk work
                    working = Velma;
                    Velma.setDaysWorked(Velma.getDaysWorked()+1);
                }else{
                    working = Shaggy;
                    Shaggy.setDaysWorked(Shaggy.getDaysWorked()+1);
                }
            }
            if(day % 6 != 0 || day == 0){//checking if the day isn't sunday
                store.ArriveAtStore(working);
                store.CheckRegister();
                store.DoInventory();
                store.OpenTheStore(working);
                store.CleanTheStore(working);
                store.LeaveTheStore(working);
            }else{
                System.out.println("The FNMS is closed on Sundays, please come back on Monday!");
            }

            newDay.setClockedIn(working.getName());
            store.calendar.put(day, newDay);
        }
        System.out.println("After 30 days of running, the store has: ");
        store.printStoreInfo();


        /*Things to be done:
        * uml diagram updates
        * comments
        * specific 6 comments about ood principal bs whatever the fksf
        * github + readme + output file
        * */
    }
}
