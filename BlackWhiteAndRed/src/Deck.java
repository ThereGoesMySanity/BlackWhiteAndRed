import java.util.ArrayList;
import java.util.Random;


public class Deck {
	private Random rand;
	private String[] suits;
	private ArrayList<String[]> deck;
	
	public static String cardString(String[] s){
		String i;
		int j = s[0].charAt(0);
		switch(j){
		case 1:i="ace";break;
		case 11:i="jack";break;
		case 12:i="queen";break;
		case 13:i="king";break;
		default:i=""+j;break;
		}
		i+="_of_"+s[1]+".png";
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
