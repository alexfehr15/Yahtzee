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

//display main GUI elements
class Yahtzee extends JPanel
{
	private GridBagLayout mainLayout;
	private JLabel rollLabel;
	private HeldDice heldPanel; 
	private ScoreCard scoreCardPanel;
	private RoleResults roleResultsPanel;
	private PlayerScore playerScorePanel;
	private GridLayout playerScoreLayout;
	private static ImageIcon emptyImage;
	private static int numPlayers;
	private GridBagLayout rollResultsLayout;
	private static ImageIcon diceImage1;
	private static ImageIcon diceImage2;
	private static ImageIcon diceImage3;
	private static ImageIcon diceImage4;
	private static ImageIcon diceImage5;
	private static ImageIcon diceImage6;
	private GridBagLayout scoreCardLayout;

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

		//set number of players
		numPlayers = 4;

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

		//set up roleResultsPanel
		GridBagConstraints c4 = new GridBagConstraints();
		roleResultsPanel = new RoleResults();
		c4.fill = GridBagConstraints.BOTH;
		c4.weighty = 0.5;
		c4.gridx = 0;
		c4.gridy = 1;
		c4.gridwidth = 2;
		this.add(roleResultsPanel, c4);

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

	class HeldDice extends JPanel
	{
		public HeldDice()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.RED);

			//fill with empty images to begin with
			for (int i = 0; i < 6; ++i)
			{
				JLabel label = new JLabel("", emptyImage, JLabel.LEFT);
				this.add(label);
			}
		}
	}

	class ScoreCard extends JPanel
	{
		public ScoreCard()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.WHITE);

			//set layout
			scoreCardLayout = new GridBagLayout();
			this.setLayout(scoreCardLayout);

			//set up top cell with game name label
			GridBagConstraints c1 = new GridBagConstraints();
			JLabel gameTypeLabel = new JLabel("Classic Yahtzee");
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 0;
			c1.gridy = 0;
			c1.gridwidth = 2;
			this.add(gameTypeLabel, c1);

			//set up next 9 labels
			GridBagConstraints c2 = new GridBagConstraints();
			/*String [][] firstNine = { 	{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
										{}
									}*/
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 0;
			c2.gridy = 1;
			for (int i = 1; i < 10; ++i)
			{
				JLabel label = new JLabel("");
			}
		}
	}

	class RoleResults extends JPanel
	{
		public RoleResults()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.GREEN);	

			//set layout
			rollResultsLayout = new GridBagLayout();
			this.setLayout(rollResultsLayout);

			//create insets used for padding below
			Insets buttonInsets = new Insets(0, 0, 100, 0);
			Insets diceInsets = new Insets(0, 0, 0, 15);

			//set up roll button
			GridBagConstraints c1 = new GridBagConstraints();
			JButton rollButton = new JButton("Roll");
			c1.fill = GridBagConstraints.BOTH;
			c1.gridx = 1;
			c1.insets = buttonInsets;
			c1.gridy = 0;
			c1.gridwidth = 4;
			this.add(rollButton, c1);

			//set up dice 1
			GridBagConstraints c2 = new GridBagConstraints();
			JLabel label1 = new JLabel("", diceImage1, JLabel.CENTER);
			c2.fill = GridBagConstraints.BOTH;
			c2.gridx = 0;
			c2.insets = diceInsets;
			c2.gridy = 1;
			this.add(label1, c2);

			//set up dice 2
			GridBagConstraints c3 = new GridBagConstraints();
			JLabel label2 = new JLabel("", diceImage2, JLabel.CENTER);
			c3.fill = GridBagConstraints.BOTH;
			c3.gridx = 1;
			c3.insets = diceInsets;
			c3.gridy = 1;
			this.add(label2, c3);

			//set up dice 3
			GridBagConstraints c4 = new GridBagConstraints();
			JLabel label3 = new JLabel("", diceImage3, JLabel.CENTER);
			c4.fill = GridBagConstraints.BOTH;
			c4.gridx = 2;
			c4.insets = diceInsets;
			c4.gridy = 1;
			this.add(label3, c4);

			//set up dice 4
			GridBagConstraints c5 = new GridBagConstraints();
			JLabel label4 = new JLabel("", diceImage4, JLabel.CENTER);
			c5.fill = GridBagConstraints.BOTH;
			c5.gridx = 3;
			c5.insets = diceInsets;
			c5.gridy = 1;
			this.add(label4, c5);

			//set up dice 5
			GridBagConstraints c6 = new GridBagConstraints();
			JLabel label5 = new JLabel("", diceImage5, JLabel.CENTER);
			c6.fill = GridBagConstraints.BOTH;
			c6.gridx = 4;
			c6.insets = diceInsets;
			c6.gridy = 1;
			this.add(label5, c6);

			//set up dice 6 
			GridBagConstraints c7 = new GridBagConstraints();
			JLabel label6 = new JLabel("", diceImage6, JLabel.CENTER);
			c7.fill = GridBagConstraints.BOTH;
			c7.gridx = 5;
			c7.gridy = 1;
			this.add(label6, c7);
		}
	}

	class PlayerScore extends JPanel
	{
		public PlayerScore()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.YELLOW);

			//set layout
			playerScoreLayout = new GridLayout(0, numPlayers);
			this.setLayout(playerScoreLayout);

			//set up player score grid	
			for (int i = 0; i < numPlayers; ++i)
			{
				JLabel label = new JLabel("Player " + (i + 1) + ": ");
				label.setHorizontalAlignment(JLabel.CENTER);
				this.add(label);
			}
		}
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
	}
}