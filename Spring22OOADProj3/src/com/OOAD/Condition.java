package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

// I wanted these enums to have a value so I could decrement condition when I needed to...
// Good discussion of this at https://www.baeldung.com/java-enum-values

public enum Condition {
    POOR (1), FAIR (2), GOOD (3), VERYGOOD (4), EXCELLENT (5);
    public final int level;
    Condition(int level) {
        this.level = level;
    }
}
