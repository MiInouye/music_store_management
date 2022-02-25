package com.Michael.musicStoreManagement;

public class ItemFactory{
	public Item getItem(String item){//a switch statement that streamlines the process of creating items
		switch(item){
			case "PaperScore": return new PaperScore();
			case "MusicCD": return new MusicCD();
			case "Vinyl": return new Vinyl();
			case "CDPlayer": return new CDPlayer();
			case "RecordPlayer": return new RecordPlayer();
			case "MP3": return new MP3();
			case "Guitar": return new Guitar();
			case "Bass": return new Bass();
			case "Mandolin": return new Mandolin();
			case "Flute": return new Flute();
			case "Harmonica": return new Harmonica();
			case "Hats": return new Hats();
			case "Shirts": return new Shirts();
			case "Bandana": return new Bandana();
			case "PracticeAmps": return new PracticeAmps();
			case "Cables": return new Cables();
			case "Strings": return new Strings();
			default: return null;
		}
	}
}
