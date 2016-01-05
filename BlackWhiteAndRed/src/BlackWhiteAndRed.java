import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JButton;

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
	private JTextPane txtpnWelcomeToUa;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	
	public BlackWhiteAndRed(int balance){
		money = highestBalance = balance;
		die = new Die();
		deck = new Deck();
		key = new Scanner(System.in);
		games = wins = 0;
		
		
		
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		
		txtpnWelcomeToUa = new JTextPane();
		txtpnWelcomeToUa.setBounds(0, 0, 450, 90);
		txtpnWelcomeToUa.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		txtpnWelcomeToUa.setEditable(false);
		txtpnWelcomeToUa.setText("Welcome to UA Casino! Current game: Black, White and Red\n$10 to play\nRules:\nRoll a die. If the result is even, you want to draw red cards, and vice versa.\nDraw a card. If it matches, draw another one. If that matches, you win $40.\nOtherwise, you lose.");
		frame.getContentPane().add(txtpnWelcomeToUa);
		
		label = new JLabel();
		label.setBounds(0, 124, 150, 120);
		frame.getContentPane().add(label);
		label.setIcon(getimg("/1-dice-clipart-4cbRaxecg.jpeg", label));
		
		label_1 = new JLabel();
		label_1.setBounds(162, 102, 126, 176);
		frame.getContentPane().add(label_1);
		label.setIcon(getimg("/back-red_1024x1024.png", label_1));
		
		label_2 = new JLabel();
		label_2.setBounds(324, 102, 126, 176);
		frame.getContentPane().add(label_2);
		label.setIcon(getimg("/back-red_1024x1024.png", label_2));
		
		JButton btnNewButton = new JButton("Roll");
		btnNewButton.setBounds(150, 90, 150, 12);
		frame.getContentPane().add(btnNewButton);

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
		label.setText(""+roll);
		draw1 = deck.draw();
		label_1.setIcon(new ImageIcon(Deck.cardString(draw1)));
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
		label_1.setIcon(new ImageIcon(Deck.cardString(draw2)));
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
	private ImageIcon getimg(String image, JLabel label){
		BufferedImage img = null;
		
		try {
		    img = ImageIO.read(getClass().getResource(image));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return new ImageIcon(img.getScaledInstance(label.getWidth(), label.getHeight(),
        Image.SCALE_SMOOTH));
		
	}
}
