import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class BlackWhiteAndRed {
	private int money, highestBalance;
	private Die die;
	private Deck deck;
	private Scanner key;
	private int roll;
	private String[] draw1, draw2;
	private int wins, games;
	private JFrame frame;
	private JTextPane instructions;
	private JLabel dieImg;
	private JLabel card1Img;
	private JLabel card2Img;
	private int stage;
	private JLabel lblWin;
	private JLabel lblMoney;
	private JLabel lblDie;
	private JButton playButton;

	public BlackWhiteAndRed(){
		this(Integer.parseInt(JOptionPane.showInputDialog("How much money do you want to start with?")));
	}

	public BlackWhiteAndRed(int balance){
		money = highestBalance = balance;
		die = new Die();
		deck = new Deck();
		key = new Scanner(System.in);
		games = wins = 0;
		stage = 0;



		frame = new JFrame();
		frame.getContentPane().setLayout(null);

		instructions = new JTextPane();
		instructions.setBackground(SystemColor.window);
		instructions.setBounds(0, 0, 450, 90);
		instructions.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
		instructions.setEditable(false);
		instructions.setText("Welcome to UA Casino! Current game: Black, White and Red\n"
				+ "$10 to play\n"
				+ "Roll a die. If the result is even, "
				+ "you want to draw red cards, and vice versa.\n"
				+ "Draw a card. If it matches, draw another one. "
				+ "If that matches, you win $40.\n"
				+ "Otherwise, you lose.");
		frame.getContentPane().add(instructions);

		dieImg = new JLabel();
		dieImg.addMouseListener(new MouseAdapter() {  //This makes it so if you click the die picture, it rolls it
			public void mousePressed(MouseEvent e) {
				if(stage == 1){
					gameSwitch(1);
				}
			}
		});
		dieImg.setBounds(0, 130, 150, 120);
		frame.getContentPane().add(dieImg);
		dieImg.setIcon(getimg("/1-dice-clipart-4cbRaxecg.jpeg", dieImg));

		card1Img = new JLabel();
		card1Img.addMouseListener(new MouseAdapter() {  //This makes it so if you click the card, it flips
			public void mousePressed(MouseEvent e) {
				if(stage == 2){
					gameSwitch(2);
				}
			}
		});
		card1Img.setBounds(165, 120, 120, 170);
		frame.getContentPane().add(card1Img);
		card1Img.setIcon(getimg("/back-red_1024x1024.png", card1Img));

		card2Img = new JLabel();
		card2Img.addMouseListener(new MouseAdapter() {  //This makes it... you get the idea
			public void mousePressed(MouseEvent e) {
				if(stage == 3){
					gameSwitch(3);
				}
			}
		});
		card2Img.setBounds(315, 120, 120, 170);
		frame.getContentPane().add(card2Img);
		card2Img.setIcon(getimg("/back-red_1024x1024.png", card2Img));

		playButton = new JButton("Play");
		/*
		 * This is one of the important parts in the middle of all the nonsense!
		 * This is where I define the method that runs when I click the button
		 * I use the button to cycle through all the stages.
		 */
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameSwitch(stage);
			}
		});
		playButton.setBounds(150, 90, 150, 24);
		frame.getContentPane().add(playButton);

		lblMoney = new JLabel("Money: $"+money);
		lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoney.setBounds(0, 90, 150, 24);
		frame.getContentPane().add(lblMoney);

		lblWin = new JLabel("Win %:");
		lblWin.setHorizontalAlignment(SwingConstants.CENTER);
		lblWin.setBounds(300, 90, 150, 24);
		frame.getContentPane().add(lblWin);

		lblDie = new JLabel("");
		lblDie.setForeground(Color.RED);
		lblDie.setFont(new Font("Dialog", Font.BOLD, 20));
		lblDie.setHorizontalAlignment(SwingConstants.CENTER);
		lblDie.setBounds(0, 250, 150, 20);
		frame.getContentPane().add(lblDie);


		frame.setSize(450, 320);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	//Runs when the game ends, lose or win.
	public void update(){
		updateStats();
		stage = -2;
		playButton.setText("Play Again");
	}

	//Runs when you lose
	public void lose(){
		JOptionPane.showMessageDialog(frame,

				"You are a loser.",
				"You Lose",
				JOptionPane.PLAIN_MESSAGE);
		update();
	}

	//Runs when you win
	public void win(){
		wins++;
		money += 40;
		JOptionPane.showMessageDialog(frame,
				"You are a winner.",
				"You Win",
				JOptionPane.PLAIN_MESSAGE);
		update();
	}

	//Runs game
	public void stage1(){
		games++;
		deck.shuffle();
		roll = die.roll();
		lblDie.setText(""+roll);
	}
	public void stage2(){
		draw1 = deck.draw();
		String s = Deck.cardString(draw1);
		card1Img.setIcon(getimg(s, card1Img));
		if(draw1[1].equals("spades")
				||draw1[1].equals("clubs")){
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
	}
	public void stage3(){
		draw2 = deck.draw();
		card2Img.setIcon(getimg(Deck.cardString(draw2), card2Img));
		if(draw2[1].equals("spades")
				||draw2[1].equals("clubs")){
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

	//Updates various statistics
	public void updateStats(){
		lblMoney.setText("Money: $"+money);
		lblWin.setText(String.format("Win %%: %.3f%%%n", (double)wins/games));
		if(money>highestBalance)highestBalance=money;
		if(money==0){
			String s = (String)JOptionPane.showInputDialog(frame,
					"You're out of money.\nAdd more:",
					"You Lose",
					JOptionPane.PLAIN_MESSAGE);
			Pattern p = Pattern.compile("\\$?(\\d+)");
			Matcher regex = p.matcher(s);
			while(!regex.matches()){
				String s2 = (String)JOptionPane.showInputDialog(frame,
						"That's not an amount of money...",
						"You Lose",
						JOptionPane.PLAIN_MESSAGE);
				regex = p.matcher(s2);
			}
			money += Integer.parseInt(regex.group(1));
			lblMoney.setText("Money: $"+money);
			if(money == 0)quit();
		}
	}

	//Handles exiting the game
	public void quit(){
		key.close();
		System.exit(0);
	}


	//Convert files into images for use in the JLabels
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

	//This moves through and runs the game
	private void gameSwitch(int i){
		switch(i){
		case -1:
			lblDie.setText("");
			card1Img.setIcon(getimg("/back-red_1024x1024.png", card1Img));
			card2Img.setIcon(getimg("/back-red_1024x1024.png", card2Img));
			stage++;
		case 0: //Game has just started
			money-=10;
			lblMoney.setText("Money: $"+money);
			playButton.setText("Roll");
			break;
		case 1:
			stage1();
			playButton.setText("Draw");
			break;
		case 2:
			stage2();
			break;
		case 3:
			stage3();

			break;
		}
		stage++;
	}
}
