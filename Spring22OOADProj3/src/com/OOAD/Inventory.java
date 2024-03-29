package com.OOAD;

// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

import java.util.ArrayList;

public class Inventory implements Logger {

    // I specifically wanted to try to do this for now with single level ArrayLists
    // there may be better collections or nested lists that might make some of
    // the operations more efficient...

    public ArrayList<Item> items;
    public ArrayList<Item> arrivingItems;
    public ArrayList<Item> soldItems;
    public ArrayList<Item> discardedItems;

    Inventory() {
        items = new ArrayList<>();
        arrivingItems = new ArrayList<>();
        soldItems = new ArrayList<>();
        discardedItems = new ArrayList<>();

        initializeInventory(items);
    }

    void initializeInventory(ArrayList<Item> list) {
        for (int i = 0; i < 3; i++) {
            for (ItemType type: ItemType.values()) {
                Item item = makeNewItemByType(type);
                list.add(item);
            }
        }
    }

    // as I showed in Piazza posts, there are fancier ways to do this with newInstance
    // and the reflection framework, this has the advantage of being pretty clean and readable
    // we're not applying patterns here yet, otherwise this really wants to be formatted
    // as a factory
    //////////////////////
    //////////////////////kev: Do we need to reformat this then?
    //////////////////////kev: still need to figure out how to make sure clothing isn't generated
    Item makeNewItemByType(ItemType type) {
        Item item;
        if(Clothing.isOutOfStock() && ((type == ItemType.HAT) || (type == ItemType.BANDANA) || (type == ItemType.SHIRT))){//if statement to stop clothing from being made once it is out of stock
            item = null;
        }
        else{
            switch (type) {
                case PAPERSCORE -> item = new PaperScore();
                case CD -> item = new CD();
                case VINYL -> item = new Vinyl();
                case GUITAR -> item = new Guitar();
                case BASS -> item = new Bass();
                case MANDOLIN -> item = new Mandolin();
                case FLUTE -> item = new Flute();
                case HARMONICA -> item = new Harmonica();
                case HAT -> item = new Hat();
                case SHIRT -> item = new Shirt();
                case BANDANA -> item = new Bandana();
                case PRACTICEAMP -> item = new PracticeAmp();
                case STRINGS -> item = new Strings();
                case CABLE -> item = new Cable();
                case SAXOPHONE -> item = new Saxophone();
                case CASSETTE -> item = new Cassette();
                case CASSETTEPLAYER -> item = new CassettePlayer();
                case GIGBAG -> item = new GigBag();
                case RECORDPLAYER -> item = new RecordPlayer();
                case CDPLAYER -> item = new CDPlayer();
                case MP3 -> item = new Mp3();
                default -> {
                    out("Error in makeNewItemByType - unexpected type enum");
                    item = null;
                }
            }
        }
        return item;
    }

    // add(), remove() can be done directly to the list
    // overall count can come from size()
    // count of specific item types is needed sometimes
    int countByType(ArrayList<Item> list, ItemType type) {
        int count = 0;
        for (Item item:list) if (item.itemType == type) count += 1;
        return count;
    }

    // helper to get value of items in a list
    double getValue(ArrayList<Item> list) {
        double value = 0;
        for (Item item:list) value = value + item.purchasePrice;
        return value;
    }



    // return the index of a random Item of a given item type
    // this is a little complicated, which would probably make me question
    // if the simple ArrayList is the best way to carry items
    // Going this way for now...
    int getFromListByType(ArrayList<Item> list, ItemType type) {
        int count = countByType(list, type);
        if (count==0) return 0;
        int index = 0;
        int selecting = 1;
        int selected = 0;
        if (count>1) selecting = Utility.rndFromRange(1,count);
        for (int i = 0; i < list.size(); i++ ) {
            if (list.get(i).itemType == type) {
                index = i;
                selected += 1;
                if (selected == selecting) return index;
            }
        }
        return index;
    }

}
