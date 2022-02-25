package com.Michael.musicStoreManagement;
 
import java.util.ArrayList;

public class Clerk extends Staff{
    public String getName() {
        return name;
    }
        
    public void setName(String name) {
        this.name = name;
    }
        
    public int getDaysWorked() {
        return daysWorked;
    }
        
    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }
        
    private String name;
            
    private int daysWorked;
    //probably put helper functions like move money and sell/buy shit in here idk
}

