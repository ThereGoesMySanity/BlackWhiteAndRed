import java.util.Scanner;


public class BlackWhiteAndRed {
	private int money;
	private Die die;
	private Deck deck;
	private Scanner key;
	private int roll;
	private String[] draw1, draw2;

	public BlackWhiteAndRed(int balance){
		money = balance;
		die = new Die();
		deck = new Deck();
		key = new Scanner(System.in);
	}
	public void lose(){
		printMoney();
		System.out.println("You lost! Want to play again? [Y/N]  ");
		if(key.nextLine().equalsIgnoreCase("y")){
			money -= 10;
			
			playGame();
		}
	}
	public void win(){
		money += 40;
		printMoney();
		System.out.println("You won! Want to play again? [Y/N]  ");
		if(key.nextLine().equalsIgnoreCase("y")){
			money -= 10;
			
			playGame();
		}
	}
	public void start(){
		System.out.println("Welcome to UAHS Casino!\nOnly $10 to play!");
		System.out.print("Would you like to play? [Y/N]  ");
		if(key.nextLine().equalsIgnoreCase("y")){
			money -= 10;
			playGame();
		}
	}
	
	public void playGame(){
		deck.shuffle();
		roll = die.roll();
		System.out.println("You rolled a "+roll+"!");
		draw1 = deck.draw();
		System.out.println("You drew a"+Deck.cardString(draw1)+"!");
		if(draw1[1].equals("Spades")
				||draw1[1].equals("Clubs")){
			if(roll%2==1){
				lose();
				return;
			}
		}else{
			if(roll%2==0){
				lose();
				return;
			}
		}
		draw2 = deck.draw();
		System.out.println("You drew a"+Deck.cardString(draw2)+"!");
		if(draw1[1].equals(draw2[1]))win();
		else lose();
	}
	public void printMoney(){
		System.out.printf("Current balance: $%f.2", money); 
	}
}
