package com.Michael.musicStoreManagement;

import java.util.ArrayList;
import java.util.Random;


public class Store {
    ArrayList<Item> inventory = new ArrayList<>();
    ArrayList<Staff> staff = new ArrayList<>();
    Double register;
    Random rand =  new Random();

    void ArriveAtStore(Clerk clerk, ArrayList<Item> orders){
        System.out.print(clerk.getName() + " arrives at the store on Day ");

    }
    void CheckRegister(){

    }
    void GoToBank(){

    }
    void DoInventory(){

    }
    void PlaceAnOrder(){

    }
    void OpenTheStore(){
        int buyCusty = rand.nextInt(4,10);
        int sellCusty = rand.nextInt(1,4);
    }
    void CleanTheStore(Clerk clerk){
        int chanceOfDamage = rand.nextInt(100);
        if(clerk.name.equals("shaggy") && chanceOfDamage < 20){
            System.out.println("im shaggy");
        }else if(clerk.name.equals("velma") && chanceOfDamage < 5){
            System.out.println("the velma");
        }
    }
    void LeaveTheStore(Clerk clerk){
        System.out.println(clerk.name+" is going home for the day");
    }
}
