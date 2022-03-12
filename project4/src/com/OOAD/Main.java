package com.OOAD;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder


public class Main{
	@Test
	public static void testSimDaysMin(int days) {// Testing if the simulated days are 10 and over

		boolean min = days >= 10;
		assertTrue(min, "Too few days for the sim");

	}
	@Test
	public static void testSimDaysMax(int days) {// Testing if the simulated days are 30 or under

		boolean max = days <= 30;
		assertTrue(max, "Too many days for the sim");

	}

    public static void main(String[] args) {
	    int SIM_DAYS = Utility.rndFromRange(10,30);
		testSimDaysMin(SIM_DAYS);
	    testSimDaysMax(SIM_DAYS);
	    Simulation sim = new Simulation();
	    sim.startSim(SIM_DAYS);
	    //sim.summary();
    }

}
