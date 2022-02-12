package com.Michael.musicStoreManagement;
 
import java.util.ArrayList;

public class Clerk extends Staff{
    public String getName() {
        return name;
    }
        
    public void setName(String name) {
        this.name = name;
    }
        
    public ArrayList<Integer> getDaysWorked() {
        return daysWorked;
    }
        
    public void setDaysWorked(ArrayList<Integer> daysWorked) {
        this.daysWorked = daysWorked;
    }
        
    private String name;
            
        private ArrayList<Integer> daysWorked;
    //probably put helper functions like move money and sell/buy shit in here idk
}

