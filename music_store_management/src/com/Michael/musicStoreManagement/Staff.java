package com.Michael.musicStoreManagement;

import java.util.ArrayList;

abstract class Staff {//ask if this is right for an abstract class
    abstract public String getName();

    abstract public void setName(String name);

    abstract public ArrayList<Integer> getDaysWorked();

    abstract public void setDaysWorked(ArrayList<Integer> daysWorked);

    private String name;

    private ArrayList<Integer> daysWorked;
}
