import java.util.Scanner;


public class BlackWhiteAndRed {
	private int money, highestBalance;
	private Die die;
	private Deck deck;
	private Scanner key;
	private int roll;
	private String[] draw1, draw2;
	private int wins, games;
	private boolean RUN_FOREVER = true;

	public BlackWhiteAndRed(int balance){
		money = highestBalance = balance;
		die = new Die();
		deck = new Deck();
		key = new Scanner(System.in);
		games = wins = 0;
	}
	public void lose(){
		System.out.println("You lost!");
		printStats();
		System.out.print("Want to play again? [Y/N]  ");
	}
	public void win(){
		wins++;
		money += 40;
		System.out.println("You won!");
		printStats();
		System.out.print("Want to play again? [Y/N]  ");
	}
	public void start(){
		System.out.println("Welcome to UAHS Casino!\nOnly $10 to play!");
		System.out.print("Would you like to play? [Y/N]  ");
		while(RUN_FOREVER||key.nextLine().equalsIgnoreCase("y")){
			money -= 10;
			playGame();
			if(money==0){
				if(RUN_FOREVER)money=10;
				else quit();
			}
		}
		quit();
	}
	
	public void playGame(){
		games++;
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
		if(draw2[1].equals("Spades")
				||draw2[1].equals("Clubs")){
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
		win();
	}
	public void printStats(){
		
		System.out.printf("Win percentage: %.4f%%%n", (double)wins/games); 
		System.out.println("Games played: "+games); 
		System.out.printf("Current balance: $%d.00%n", money);
		if(money>highestBalance)highestBalance=money;
		if(money==0){
			System.out.println("You're broke.");
			System.out.println("You should've quit when you had $"+highestBalance);
		}
	}
	public void quit(){
		key.close();
		System.out.println("Thanks for playing!");
		System.exit(0);
	}
}
