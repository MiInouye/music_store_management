package com.OOAD;
// Code taken from Example code for Project 2 Part 2
// Source: Bruce Montgomery - 2/14/22 - OOAD CSCI 4/5448 - CU Boulder

public abstract class Item implements Logger {
    String name;            // I didn't implement a naming scheme - mostly ignoring this - how would you?
    double purchasePrice;   // $1 to $50
    double listPrice;       // purchasePrice x 2
    boolean isNew;          // set by constructor randomly
    int dayArriving;        // 0 at initialization, otherwise set at delivery
    Condition condition;    // set by constructor randomly
    double salePrice;       // set when sold
    int daySold;            // set when sold
    ItemType itemType;      // set by subclass constructors
    boolean isTuned;
    void doTune(){}
    boolean isTuned(){
        return isTuned;
    }


    Item() {
        // common initialization of a new instance
        purchasePrice = Utility.rndFromRange(1,50);
        listPrice = 2 * purchasePrice;
        isNew = (Utility.rnd() > .5);  // coin flip for new or used
        dayArriving = 0;
        condition = Utility.randomEnum(Condition.class);
        salePrice = 0;
        daySold = 0;

    };
}

abstract class Music extends Item {
    String band;
    String album;
    String[] bands = {"Yes","Jethro Tull","Rush","Genesis","ELP","Enya"};
    String[] albums = {"Fragile","Stormwatch","2112","Abacab","Tarkus","The Memory of Trees"};
    Music() {
        super();
        band = bands[Utility.rndFromRange(0,bands.length-1)];
        album = albums[Utility.rndFromRange(0,albums.length-1)];
    }
}

class PaperScore extends Music {
    PaperScore() {
        super();
        itemType = ItemType.PAPERSCORE;
        name = itemType.toString();
    }
}
class CD extends Music {
    CD() {
        super();
        itemType = ItemType.CD;
        name = itemType.toString();
    }
}
class Vinyl extends Music {
    Vinyl() {
        super();
        itemType = ItemType.VINYL;
        name = itemType.toString();
    }
}

class Cassette extends Music {
    Cassette() {
        super();
        itemType = ItemType.CASSETTE;
        name = itemType.toString();
    }
}

abstract class Player extends Item {
    boolean isEqualized;
    void doTune(){}
    boolean isTuned(){
        return isEqualized;
    }//Part of strategy pattern implementation
    Player() {
        super();
        isEqualized = false;
    }
}

class CDPlayer extends Player {
    CDPlayer() {
        super();
        itemType = ItemType.CDPLAYER;
        name = itemType.toString();
        isEqualized = false;
    }
}

class RecordPlayer extends Player {

    RecordPlayer() {
        super();
        itemType = ItemType.RECORDPLAYER;
        name = itemType.toString();
        isEqualized = false;
    }
}

class Mp3 extends Player {

    Mp3() {
        super();
        itemType = ItemType.MP3;
        name = itemType.toString();
        isEqualized = false;
    }
}

class CassettePlayer extends Player {
    CassettePlayer() {
        super();
        itemType = ItemType.CASSETTEPLAYER;
        name = itemType.toString();
        isEqualized = false;
    }
}

abstract class Instrument extends Item {
    boolean isTuned;
    void doTune(){
    }
    boolean isTuned(){
        return isTuned;
    }
}

abstract class Stringed extends Instrument {
    void doTune(){
    }
    boolean isTuned(){
        return isTuned;
    }
    boolean isElectric;
    boolean checkIsElectric(){
        return isElectric;
    }
    boolean isTuned;
    Stringed() {
        super();
        isElectric = (Utility.rnd()>.5); // coin flip for electric or acoustic
        isTuned = false;
    }
}

abstract class StringedDecorator extends Stringed{//Decorator pattern
    Stringed instrument;
    public StringedDecorator(Stringed instrument){
        this.instrument = instrument;
    }
}

class StringedAccessories extends StringedDecorator{//Decorator pattern
    boolean isElectric;
    boolean gigChance;
    boolean ampChance;
    boolean cableChance;
    boolean stringChance;
    int numCable;
    int numStrings;

    public boolean gigChance(){
        return gigChance;
    }

    public boolean ampChance(){
        return ampChance;
    }

    public boolean cableChance(){
        return cableChance;
    }

    public boolean stringChance(){
        return stringChance;
    }

    public int numCable(){
        return numCable;
    }

    public int numStrings(){
        return numStrings;
    }


    StringedAccessories(Stringed instrument){//Decorator pattern
        super(instrument);
        isElectric = checkIsElectric();
        if(isElectric){
            gigChance = Utility.rnd() > .8;
            ampChance = Utility.rnd() > .75;
            cableChance = Utility.rnd() > .7;
            stringChance = Utility.rnd() > .6;
        }
        else{
            gigChance = Utility.rnd() > .9;
            ampChance = Utility.rnd() > .85;
            cableChance = Utility.rnd() > .8;
            stringChance = Utility.rnd() > .7;
        }
        numCable = Utility.rndFromRange(1, 2);
        numStrings = Utility.rndFromRange(1, 3);
    }
}

class Guitar extends Stringed {
    void doTune(){
        isTuned = !isTuned;
    }
    public boolean isTuned(){
        return isTuned;
    }

    Guitar() {
        super();
        itemType = ItemType.GUITAR;
        name = itemType.toString();
        isTuned = true;
    }
}
class Bass extends Stringed {
    public boolean isTuned(){
        return isTuned;
    }
    void doTune(){
        isTuned = !isTuned;
    }

    Bass() {
        super();
        itemType = ItemType.BASS;
        name = itemType.toString();
        isTuned = true;
    }
}
class Mandolin extends Stringed {
    public boolean isTuned(){
        return isTuned;
    }
    void doTune(){
        isTuned = !isTuned;
    }

    Mandolin() {
        super();
        itemType = ItemType.MANDOLIN;
        name = itemType.toString();
        isTuned = true;
    }
}

abstract class Wind extends Instrument {
    void doTune(){

    }
    boolean isTuned(){
        return isAdjusted;
    }

    boolean isAdjusted;
    Wind() {
        super();
        isAdjusted = false;
    }
}

class Flute extends Wind {
    void doTune(){
        isAdjusted = !isAdjusted;
    }
    String type;
    String[] types = {"Piccolo","Alto","Bass","Tierce","Concert","Plastic"};
    Flute() {
        super();
        type = types[Utility.rndFromRange(0,types.length-1)];
        itemType = ItemType.FLUTE;
        name = itemType.toString();
    }
}

class Saxophone extends Wind {
    void doTune(){
        isAdjusted = !isAdjusted;
    }
    String type;
    String[] types = {"Sopranino", "Soprano", "Alto", "Tenor", "Baritone", "Bass"};
    Saxophone() {
        super();
        type = types[Utility.rndFromRange(0,types.length-1)];
        itemType = ItemType.SAXOPHONE;
        name = itemType.toString();
    }
}

class Harmonica extends Wind {
    void doTune(){
        isAdjusted = !isAdjusted;
    }
    String key;
    String[] keys = {"E","A","G","C","D"};
    Harmonica() {
        super();
        key = keys[Utility.rndFromRange(0,keys.length-1)];
        itemType = ItemType.HARMONICA;
        name = itemType.toString();
    }
}

abstract class Clothing extends Item {
    static boolean isOutOfStock(){
        return outOfStock;
    }
    static void setOutOfStock(){
        outOfStock = true;
    }
    static boolean outOfStock = false;
    Clothing() {
        super();
    }
}

class Hat extends Clothing {
    static boolean checkSoldOut(){
        return isSoldOut;
    }
    static void setSoldOut(){
        isSoldOut = true;
    }
    static boolean isSoldOut = false;
    String size;
    String[] sizes = {"S","M","L"};
    Hat() {
        super();
        size = sizes[Utility.rndFromRange(0,sizes.length-1)];
        itemType = ItemType.HAT;
        name = itemType.toString();
    }
}
class Shirt extends Clothing {
    static boolean checkSoldOut(){
        return isSoldOut;
    }
    static void setSoldOut(){
        isSoldOut = true;
    }
    static boolean isSoldOut = false;
    String size;
    String[] sizes = {"S","M","L"};
    Shirt() {
        super();
        size = sizes[Utility.rndFromRange(0,sizes.length-1)];
        itemType = ItemType.SHIRT;
        name = itemType.toString();
    }
}

class Bandana extends Clothing {
    static boolean checkSoldOut(){
        return isSoldOut;
    }
    static void setSoldOut(){
        isSoldOut = true;
    }
    static boolean isSoldOut = false;
    Bandana() {
        super();
        itemType = ItemType.BANDANA;
        name = itemType.toString();
    }
}

abstract class Accessories extends Item {
    Accessories() {
        super();
    }
}

class PracticeAmp extends Accessories {
    Integer wattage;
    Integer[] wattages = {15,20,25,30,40,50};
    PracticeAmp() {
        super();
        wattage = wattages[Utility.rndFromRange(0,wattages.length-1)];
        itemType = ItemType.PRACTICEAMP;
        name = itemType.toString();
    }
}
class Cable extends Accessories {
    Integer length;
    Integer[] lengths = {25,50,75,100};
    Cable() {
        super();
        length = lengths[Utility.rndFromRange(0,lengths.length-1)];
        itemType = ItemType.CABLE;
        name = itemType.toString();
    }
}

class Strings extends Accessories {
    String type;
    String[] types = {"Nickel","Bronze","Copper","Silver"};
    Strings() {
        super();
        type = types[Utility.rndFromRange(0,types.length-1)];
        itemType = ItemType.STRINGS;
        name = itemType.toString();
    }
}

class GigBag extends Music {
    GigBag() {
        super();
        itemType = ItemType.GIGBAG;
        name = itemType.toString();
    }
}