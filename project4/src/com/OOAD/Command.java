package com.OOAD;

import java.util.Scanner;

public interface Command extends Logger, Utility{//command example
	void execute(Store store);
}
class AskName implements Command{
	Store store;
	public AskName(Store store){
		this.store = store;
	}

	@Override
	public void execute(Store store){
		store.getActiveClerk().AskClerkName();
	}
}
class AskTime implements Command{
	Store store;
	public AskTime(Store store){
		this.store = store;
	}

	@Override
	public void execute(Store store){
		store.getActiveClerk().AskForTime();
	}

}
class SellItemToStore implements Command{
	Store store;
	public SellItemToStore(Store store){
		this.store = store;
	}

	@Override
	public void execute(Store store){
		this.store.getActiveClerk().BuyItemFromUser();
	}

}
class BuyItemFromStore implements Command{
	Store store;
	public BuyItemFromStore(Store store){
		this.store = store;
	}
	@Override
	public void execute(Store store){
		this.store.getActiveClerk().SellItemToUser();
	}
}
class BuyGuitarKitFromStore implements Command{
	Store store;
	GuitarKit guitarkit;
	public BuyGuitarKitFromStore(Store store){
		this.store = store;
	}

	public void execute(Store store){
		store.getActiveClerk().SellGuitarKit();
	}
}

class Invoker{//this is the remote
	Command slot;
	Store store;
	public Invoker(Store store){
		this.store = store;
	}
	public void setCommand(Command command){
		slot = command;
	}
	public void nameWasAsked(){
		slot.execute(store);
	}
	public void timeWasAsked(){
		slot.execute(store);
	}
	public void itemSoldToStore(){slot.execute(store);}
	public void itemBoughtFromStore(){
		slot.execute(store);
	}
	public void guitarKitBoughtFromStore(){ slot.execute(store); }
}

