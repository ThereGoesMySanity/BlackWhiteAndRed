import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhiteAndRed {
	private int money, highestBalance;
	private Die die;
	private Deck deck;
	private Scanner key;
	private int roll;
	private String[] draw1, draw2;
	private int wins, games;
	private boolean RUN_FOREVER = true;
	private JFrame frame;
	
	public BlackWhiteAndRed(int balance){
		money = highestBalance = balance;
		die = new Die();
		deck = new Deck();
		key = new Scanner(System.in);
		games = wins = 0;
		frame = new JFrame();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 150, 150, 0};
		gridBagLayout.rowHeights = new int[] {128, 128, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JTextPane txtpnWelcomeToUa = new JTextPane();
		txtpnWelcomeToUa.setEditable(false);
		txtpnWelcomeToUa.setText("Welcome to UA Casino! Current game: Black, White and Red\n$10 to play\nRules:\nRoll a die. If the result is even, you want to draw red cards, and vice versa.\nDraw a card. If it matches, draw another one. If that matches, you win $40.\nOtherwise, you lose.");
		GridBagConstraints gbc_txtpnWelcomeToUa = new GridBagConstraints();
		gbc_txtpnWelcomeToUa.anchor = GridBagConstraints.NORTH;
		gbc_txtpnWelcomeToUa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtpnWelcomeToUa.insets = new Insets(0, 0, 5, 0);
		gbc_txtpnWelcomeToUa.gridwidth = 3;
		gbc_txtpnWelcomeToUa.gridx = 0;
		gbc_txtpnWelcomeToUa.gridy = 0;
		frame.getContentPane().add(txtpnWelcomeToUa, gbc_txtpnWelcomeToUa);
		
		JLabel lblTest = new JLabel();
		GridBagConstraints gbc_lblTest = new GridBagConstraints();
		gbc_lblTest.fill = GridBagConstraints.BOTH;
		gbc_lblTest.insets = new Insets(0, 0, 5, 5);
		gbc_lblTest.gridx = 0;
		gbc_lblTest.gridy = 1;
		frame.getContentPane().add(lblTest, gbc_lblTest);
		
		lblTest.setIcon(imageSize("Resources/1-dice-clipart-4cbRaxecg.jpeg", lblTest));
		JLabel lblTest_1 = new JLabel();
		GridBagConstraints gbc_lblTest_1 = new GridBagConstraints();
		gbc_lblTest_1.anchor = GridBagConstraints.WEST;
		gbc_lblTest_1.fill = GridBagConstraints.VERTICAL;
		gbc_lblTest_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblTest_1.gridx = 1;
		gbc_lblTest_1.gridy = 1;
		frame.getContentPane().add(lblTest_1, gbc_lblTest_1);
		
		lblTest_1.setIcon(imageSize("Resources/back-red_1024x1024.png", lblTest_1));
		JLabel lblTest_2 = new JLabel();
		
		GridBagConstraints gbc_lblTest_2 = new GridBagConstraints();
		gbc_lblTest_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblTest_2.anchor = GridBagConstraints.NORTH;
		gbc_lblTest_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTest_2.gridwidth = 2;
		gbc_lblTest_2.gridx = 2;
		gbc_lblTest_2.gridy = 1;
		frame.getContentPane().add(lblTest_2, gbc_lblTest_2);
		lblTest_2.setIcon(imageSize("Resources/back-red_1024x1024.png", lblTest_2));
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
		money -= 10;
		playGame();
		if(money==0){
			if(RUN_FOREVER)money=10;
			else quit();
		}
	}
	
	//Runs game
	public void playGame(){
		games++;
		deck.shuffle();
		roll = die.roll();
		//Display roll
		draw1 = deck.draw();
		//Display card
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
		//Display card
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
	
	public ImageIcon imageSize(String image, JLabel label){
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(image));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(150, 128,
		        Image.SCALE_SMOOTH);
		return new ImageIcon(dimg);
	}
}
