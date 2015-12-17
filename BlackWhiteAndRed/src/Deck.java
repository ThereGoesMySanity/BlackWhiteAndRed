import java.util.ArrayList;
import java.util.Random;


public class Deck {
	private Random rand;
	private String[] suits;
	private ArrayList<String[]> deck;
	
	public static String cardString(String[] s){
		String i;
		switch (s[0].charAt(0)){
		case 1:i="n Ace";
		case 11:i=" Jack";
		case 12:i=" Queen";
		case 13:i=" King";
		default:i=(int)(s[0].charAt(0))+"";
		}
		i+=" of "+s[1];
		return i;
		
	}
	
	public Deck(){
		rand = new Random();
		suits = new String[]{"Spades","Hearts","Diamonds","Clubs"};
		deck = new ArrayList<String[]>();
		shuffle();
	}
	public void shuffle(){
		deck.clear();
		for(int i = 1; i < 14; i++){
			for(String j : suits){
				deck.add(new String[]{(char)i+"", j});
			}
		}
	}
	public String[] draw(){
		int choice = rand.nextInt(deck.size());
		return deck.remove(choice);
	}
}
