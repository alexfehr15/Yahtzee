//Alex Fehr, Java Assignment X
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Insets;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.util.Random;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

//display main GUI elements
class Yahtzee extends JPanel
{
	private GridBagLayout mainLayout;
	private static JLabel rollLabel;
	private HeldDice heldPanel; 
	private ScoreCard scoreCardPanel;
	private RollResults RollResultsPanel;
	private PlayerScore playerScorePanel;
	private static GridLayout playerScoreLayout;
	private static ImageIcon emptyImage;
	private GridBagLayout rollResultsLayout;
	private static ImageIcon diceImage1;
	private static ImageIcon diceImage2;
	private static ImageIcon diceImage3;
	private static ImageIcon diceImage4;
	private static ImageIcon diceImage5;
	private static ImageIcon diceImage6;
	private static ImageIcon [] dieImages = new ImageIcon[6];
	private static JLabel [] heldDie = new JLabel[5];
	private static JLabel [] currentDie = new JLabel[5];
	private GridBagLayout scoreCardLayout;
	private static Map< JLabel, JLabel > mainScoreMap;
	private static final int NUMPLAYERS = 4;
	private static final int ROLLSPERTURN = 3;
	private static int numRolls = 0;
	private static int currentRoll = 1;
	private static final int MAXROLLS = NUMPLAYERS * 13 * ROLLSPERTURN;
	private static int currentIndex = 0;
	private static Random rand = new Random();
	private static int randDice = 0;
	private static int dieAvailable = 5;
	private static int heldDieAvailable = 0;
	private static int [] scoreDie = new int[5];
	private static Player[] participants = new Player[NUMPLAYERS];
	private JButton rollButton;

	public Yahtzee()
	{
		//call superclass's constructor and set background to white
		super();
		this.setBackground(Color.WHITE);

		//set up the mainLayout for the Yahtzee JPanel
		mainLayout = new GridBagLayout();
		this.setLayout(mainLayout);

		//construct empty box image to be used throughout
		emptyImage = new ImageIcon(this.getClass().
										getResource("/empty.jpg"));

		//set up die images to be used throughout
		diceImage1 = new ImageIcon(this.getClass().
										getResource("/dice_1.jpg"));
		diceImage2 = new ImageIcon(this.getClass().
										getResource("/dice_2.jpg"));
		diceImage3 = new ImageIcon(this.getClass().
										getResource("/dice_3.jpg"));
		diceImage4 = new ImageIcon(this.getClass().
										getResource("/dice_4.jpg"));
		diceImage5 = new ImageIcon(this.getClass().
										getResource("/dice_5.jpg"));
		diceImage6 = new ImageIcon(this.getClass().
										getResource("/dice_6.jpg"));
		dieImages[0] = diceImage1;
		dieImages[1] = diceImage2;
		dieImages[2] = diceImage3;
		dieImages[3] = diceImage4;
		dieImages[4] = diceImage5;
		dieImages[5] = diceImage6;

		//set up rollLabel
		GridBagConstraints c1 = new GridBagConstraints();
		rollLabel = new JLabel("Roll #", JLabel.CENTER);
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 0.25;
		c1.gridx = 0;
		c1.gridy = 0;
		this.add(rollLabel, c1);

		//set up heldPanel
		GridBagConstraints c2 = new GridBagConstraints();
		heldPanel = new HeldDice();
		c2.fill = GridBagConstraints.VERTICAL;
		c2.gridx = 1;
		c2.gridy = 0;
		this.add(heldPanel, c2);

		//set up scoreCardPanel
		GridBagConstraints c3 = new GridBagConstraints();
		scoreCardPanel = new ScoreCard();
		c3.fill = GridBagConstraints.BOTH;
		c3.weightx = 0.75;
		c3.weighty = 0.5;
		c3.gridx = 2;
		c3.gridy = 0;
		c3.gridheight = 3;
		this.add(scoreCardPanel, c3);

		//set up RollResultsPanel
		GridBagConstraints c4 = new GridBagConstraints();
		RollResultsPanel = new RollResults();
		c4.fill = GridBagConstraints.BOTH;
		c4.weighty = 0.5;
		c4.gridx = 0;
		c4.gridy = 1;
		c4.gridwidth = 2;
		this.add(RollResultsPanel, c4);

		//set up playerScorePanel
		GridBagConstraints c5 = new GridBagConstraints();
		playerScorePanel = new PlayerScore();
		c5.fill = GridBagConstraints.BOTH;
		c5.ipady = 30;
		c5.gridx = 0;
		c5.gridy = 2;
		c5.gridwidth = 2;
		this.add(playerScorePanel, c5);
	}

	public static int getDiceNum(ImageIcon icon)
	{
		if (icon == diceImage1)
			return 1;
		else if (icon == diceImage2)
			return 2;
		else if (icon == diceImage3)
			return 3;
		else if (icon == diceImage4)
			return 4;
		else if (icon == diceImage5)
			return 5;
		else if (icon == diceImage6)
			return 6;
		else
			return -1;
	}

	public static void setRollLabel(int c)
	{
		rollLabel.setText("Roll " + c);
	}

	class HeldDice extends JPanel implements MouseListener
	{
		public HeldDice()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.RED);

			//fill with empty images to begin with
			for (int i = 0; i < 5; ++i)
			{
				JLabel label = new JLabel("", emptyImage, JLabel.LEFT);
				heldDie[i] = label;
				label.addMouseListener(this);
				this.add(label);
			}
		}

		//handle when a dice is clicked in roll results area
		public void mouseClicked(MouseEvent e)
		{
			//move dice image back down to first empty spot
			for (int i = 0; i < 5; ++i)
				if (currentDie[i].getIcon() == emptyImage)
				{
					currentDie[i].setIcon(((JLabel) e.getSource()).getIcon());
					((JLabel) e.getSource()).setIcon(emptyImage);
					++dieAvailable;
					--heldDieAvailable;
					break;
				}
		}

		public void mousePressed(MouseEvent e)
		{
			//todo
		}

		public void mouseReleased(MouseEvent e)
		{
			//todo
		}

		public void mouseEntered(MouseEvent e)
		{
			//todo
		}

		public void mouseExited(MouseEvent e)
		{
			//todo
		}

	}

	class ScoreCard extends JPanel implements MouseListener
	{
		public ScoreCard()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.WHITE);

			//set layout
			scoreCardLayout = new GridBagLayout();
			this.setLayout(scoreCardLayout);

			//set the border
			this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

			//set up top cell with game name label
			GridBagConstraints c1 = new GridBagConstraints();
			JLabel gameTypeLabel = new JLabel("Classic Yahtzee");
			gameTypeLabel.setHorizontalAlignment(JLabel.CENTER);
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 0;
			c1.gridy = 0;
			c1.gridwidth = 2;
			this.add(gameTypeLabel, c1);

			//set up initial labels for before game starts
			GridBagConstraints c2 = new GridBagConstraints();
			Human initialScoreView = new Human("", -1);
			mainScoreMap = new LinkedHashMap < JLabel, JLabel >();
			initialScoreView.initializeLabels(mainScoreMap);
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 0;
			c2.gridy = 1;
			c2.weightx = .5;
			c2.weighty = .5;
			for (JLabel key : mainScoreMap.keySet())
			{
				key.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				key.setHorizontalAlignment(JLabel.CENTER);
				this.add(key, c2);
				c2.gridx = 1;
				(mainScoreMap.get(key)).setBorder(BorderFactory.createLineBorder(Color.BLACK));
				(mainScoreMap.get(key)).setHorizontalAlignment(JLabel.CENTER);

				//add mouseListener when appropriate
				if (key.getText() != "Player:" && key.getText() != "" && key.getText() != "Bonus" && key.getText() != "Upper Total" && key.getText() != "Lower Total" && key.getText() != "Grand Total")
				{
					(mainScoreMap.get(key)).setName(key.getText());
					(mainScoreMap.get(key)).addMouseListener(this);
				}

				this.add(mainScoreMap.get(key), c2);
				c2.gridx = 0;
				c2.gridy++;
			}
		}

		//handle when a cell is clicked in the scoreCard
		public void mouseClicked(MouseEvent e)
		{
			//get current die from board
			int placeCounter = 0;
			for (int i = 0; i < 5; ++i)
			{
				if (heldDie[i].getIcon() != emptyImage)
					scoreDie[placeCounter++] = getDiceNum((ImageIcon) heldDie[i].getIcon());
			}
			for (int i = 0; i < 5; ++i)
			{
				if (currentDie[i].getIcon() != emptyImage)
					scoreDie[placeCounter++] = getDiceNum((ImageIcon) currentDie[i].getIcon());
			}

			System.out.println(((JLabel) e.getSource()).getName());

			//call human function for participants
			participants[currentIndex].takeTurn(scoreDie, ((JLabel) e.getSource()).getName(), mainScoreMap);
			
			//set currentIndex to the next player
			currentIndex = (currentIndex + 1) % NUMPLAYERS;
			currentRoll = 1;

			//set roll label back to what it should be
			Yahtzee.setRollLabel(currentRoll);

			//make roll button once again enabled
			rollButton.setEnabled(true);

			//update initial player name labels at bottom
			for (Player player : participants)
				PlayerScore.updateLabels(player);

			//reset held die back to empty
			for (JLabel i : heldDie)
				i.setIcon(emptyImage);
			for (int i = 0; i < currentDie.length; ++i)
				currentDie[i].setIcon(dieImages[i]);

			//reset heldDieAvailable and dieAvailable
			heldDieAvailable = 0;
			dieAvailable = 5;

			//probably should first check here if the game is over or not***

			//if computer player, call takeTurn, otherwise human rolls
			if (participants[currentIndex] instanceof Human)
			{
				//set up scoreCard for them
				participants[currentIndex].updateLabels(mainScoreMap);
			}
			else
			{
				//call takeTurn for computer
			}
		}

		public void mousePressed(MouseEvent e)
		{
			//todo
		}

		public void mouseReleased(MouseEvent e)
		{
			//todo
		}

		public void mouseEntered(MouseEvent e)
		{
			//todo
		}

		public void mouseExited(MouseEvent e)
		{
			//todo
		}
	}

	class RollResults extends JPanel implements ActionListener, MouseListener
	{
		//private JButton rollButton;
		private JLabel label1;
		private JLabel label2;
		private JLabel label3;
		private JLabel label4;
		private JLabel label5;

		public RollResults()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.GREEN);	

			//set layout
			rollResultsLayout = new GridBagLayout();
			this.setLayout(rollResultsLayout);

			//create insets used for padding below
			Insets buttonInsets = new Insets(0, 0, 100, 0);
			Insets diceInsets = new Insets(0, 0, 0, 23);

			//set up roll button
			GridBagConstraints c1 = new GridBagConstraints();
			rollButton = new JButton("Roll");
			rollButton.addActionListener(this);
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 1;
			c1.insets = buttonInsets;
			c1.gridy = 0;
			c1.gridwidth = 3;
			this.add(rollButton, c1);

			//set up dice 1
			GridBagConstraints c2 = new GridBagConstraints();
			label1 = new JLabel("", diceImage1, JLabel.CENTER);
			label1.addMouseListener(this);
			currentDie[0] = label1;
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 0;
			c2.insets = diceInsets;
			c2.gridy = 1;
			this.add(label1, c2);

			//set up dice 2
			GridBagConstraints c3 = new GridBagConstraints();
			label2 = new JLabel("", diceImage2, JLabel.CENTER);
			label2.addMouseListener(this);
			currentDie[1] = label2;
			c3.fill = GridBagConstraints.BOTH;
			c3.gridx = 1;
			c3.insets = diceInsets;
			c3.gridy = 1;
			this.add(label2, c3);

			//set up dice 3
			GridBagConstraints c4 = new GridBagConstraints();
			label3 = new JLabel("", diceImage3, JLabel.CENTER);
			label3.addMouseListener(this);
			currentDie[2] = label3;
			c4.fill = GridBagConstraints.BOTH;
			c4.gridx = 2;
			c4.insets = diceInsets;
			c4.gridy = 1;
			this.add(label3, c4);

			//set up dice 4
			GridBagConstraints c5 = new GridBagConstraints();
			label4 = new JLabel("", diceImage4, JLabel.CENTER);
			label4.addMouseListener(this);
			currentDie[3] = label4;
			c5.fill = GridBagConstraints.BOTH;
			c5.gridx = 3;
			c5.insets = diceInsets;
			c5.gridy = 1;
			this.add(label4, c5);

			//set up dice 5
			GridBagConstraints c6 = new GridBagConstraints();
			label5 = new JLabel("", diceImage5, JLabel.CENTER);
			label5.addMouseListener(this);
			currentDie[4] = label5;
			c6.fill = GridBagConstraints.BOTH;
			c6.gridx = 4;
			c6.insets = diceInsets;
			c6.gridy = 1;
			this.add(label5, c6);

			//set up dice 6 
			/*GridBagConstraints c7 = new GridBagConstraints();
			JLabel label6 = new JLabel("", diceImage6, JLabel.CENTER);
			c7.fill = GridBagConstraints.BOTH;
			c7.gridx = 5;
			c7.gridy = 1;
			this.add(label6, c7);*/
		}

		//handle when a dice is clicked in roll results area
		public void mouseClicked(MouseEvent e)
		{
			//testing
			System.out.println("Pressed a button");

			//set held dice image to be whatever was clicked, increase counter
			for (int i = 0; i < 5; ++i)
				if (heldDie[i].getIcon() == emptyImage)
				{
					heldDie[i].setIcon(((JLabel) e.getSource()).getIcon());
					((JLabel) e.getSource()).setIcon(emptyImage);
					break;
				}

			//decrease dieAvailable and increase heldDieAvailable
			++heldDieAvailable;
			--dieAvailable;
		}

		public void mousePressed(MouseEvent e)
		{
			//todo
		}

		public void mouseReleased(MouseEvent e)
		{
			//todo
		}

		public void mouseEntered(MouseEvent e)
		{
			//todo
		}

		public void mouseExited(MouseEvent e)
		{
			//todo
		}

		//catch the different events and handle accordingly
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == rollButton)
			{
				//move die remaining over to the left
				for (int i = 4; i > dieAvailable - 1; --i)
					currentDie[i].setIcon(emptyImage);

				//roll the die
				for (int i = 0; i < dieAvailable; ++i)
				{
					randDice = rand.nextInt(6);
					currentDie[i].setIcon(dieImages[randDice]);
				}

				//increase some counters
				Yahtzee.setRollLabel(currentRoll);
				++numRolls;
				++currentRoll;

				//check if just did last roll
				if (currentRoll == 4)
				{
					//user can no longer roll
					rollButton.setEnabled(false);

					//set currentRoll back to 1
					currentRoll = 1;
				}
			}
		}
	}

	static class PlayerScore extends JPanel
	{
		private static JLabel [] labels = new JLabel[4];

		public PlayerScore()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.YELLOW);

			//set layout
			playerScoreLayout = new GridLayout(0, NUMPLAYERS);
			this.setLayout(playerScoreLayout);

			//set up player score grid	
			for (int i = 0; i < NUMPLAYERS; ++i)
			{
				labels[i] = new JLabel("Player " + (i + 1) + ":");
				labels[i].setHorizontalAlignment(JLabel.CENTER);
				this.add(labels[i]);
			}
		}

		public static void updateLabels(Player player)
		{
			labels[player.getPlayer()].setText(player.getText());
		}
	}

	//check to see if the number of rolls has exceeded the maximum
	public boolean gameOver()
	{
		if (numRolls > MAXROLLS)
			return true;
		else
			return false;
	}

	public static void main(String args[])
	{
		//create frame for the main Yahtzee JPanel
		JFrame frame = new JFrame("Yahtzee");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set up File menu
		JMenu fileMenu = new JMenu("File");

		//set up Options menu
		JMenu optionsMenu = new JMenu("Options");

		//set up Help menu
		JMenu helpMenu = new JMenu("Help");

		//set up menu bar at top
		JMenuBar bar = new JMenuBar();
		frame.setJMenuBar(bar);
		bar.add(fileMenu);
		bar.add(optionsMenu);
		bar.add(helpMenu);

		//create Yahtzee JPanel and add to JFrame
		Yahtzee yahtzeeJPanel = new Yahtzee();
		frame.add(yahtzeeJPanel);

		//set initial size of the frame, start location and make visible
		frame.setSize(900, 700);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		//find out how many human and computer players
		String numHumansStr = JOptionPane.showInputDialog("How many human players (max 4): ");
		int numHumans = Integer.parseInt(numHumansStr);
		while (numHumans > 4 || numHumans < 0)
		{
			numHumansStr = JOptionPane.showInputDialog("How many human players (max 4): ");
			numHumans = Integer.parseInt(numHumansStr);
		}
		int numComputers = 4 - numHumans;

		//create heterogeneous list of size 4
		//Player[] participants = new Player[NUMPLAYERS];

		//fill participants list first with human players
		for (int i = 0; i < numHumans; ++i)
		{
			String name = JOptionPane.showInputDialog("Enter name for player " + (i+1) + ":");
			participants[i] = new Human(name, i);
		}

		//fill rest of participants list with computer players
		for (int i = numHumans; i < 4; ++i)
		{
			participants[i] = new Computer("Computer " + (i - numHumans + 1), i);
		}

		//update initial player name labels at bottom (score will be blank)
		for (Player player : participants)
			PlayerScore.updateLabels(player);

		//*************************************************************
		//if no humans, need to just start going through game loop here
		//*************************************************************

		//if humans, then set name on score card and tell them to roll
		if (numHumans != 0)
		{
			//initialize things to current player
			participants[currentIndex].updateLabels(mainScoreMap);
		}
		else
		{
			//start computers going until end of game
		}

		//begin main game loop
		/*while (!yahtzeeJPanel.gameOver())
		{
			//roll the die
			for (int i = 0; i < dieAvailable; ++i)
			{
				randDice = rand.nextInt(6);
				currentDie[i].setIcon(dieImages[randDice]);
			}
			break;
			//participants[currentIndex].takeTurn();
		}*/
	}
}