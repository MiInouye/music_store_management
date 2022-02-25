package com.Michael.musicStoreManagement;

import java.util.ArrayList;

abstract class Staff { //Abstraction: kept abstract because it will apply to all staff, not just clerks
    abstract public String getName();

    abstract public void setName(String name);

    abstract public int getDaysWorked();

    abstract public void setDaysWorked(int daysWorked);

    private String name;

    private int daysWorked;
}
