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

//display main GUI elements
class Yahtzee extends JPanel
{
	private GridBagLayout mainLayout;
	private JLabel rollLabel;
	private HeldDice heldPanel; 
	private ScoreCard scoreCardPanel;
	private RoleResults roleResultsPanel;
	private PlayerScore playerScorePanel;
	private GridLayout heldLayout;

	public Yahtzee()
	{
		//call superclass's constructor and set background to white
		super();
		this.setBackground(Color.WHITE);

		//set up the mainLayout for the Yahtzee JPanel
		mainLayout = new GridBagLayout();
		this.setLayout(mainLayout);

		//set up rollLabel
		GridBagConstraints c1 = new GridBagConstraints();
		rollLabel = new JLabel("Roll #", JLabel.CENTER);
		c1.fill = GridBagConstraints.BOTH;
		c1.weightx = 0.5;
		c1.ipady = 40;
		c1.gridx = 0;
		c1.gridy = 0;
		this.add(rollLabel, c1);

		//set up heldPanel
		GridBagConstraints c2 = new GridBagConstraints();
		heldPanel = new HeldDice();
		c2.fill = GridBagConstraints.BOTH;
		c2.weightx = 0.5;
		c2.ipady = 40;
		c2.ipadx = 130;
		c2.gridx = 1;
		c2.gridy = 0;
		this.add(heldPanel, c2);

		//set up scoreCardPanel
		GridBagConstraints c3 = new GridBagConstraints();
		scoreCardPanel = new ScoreCard();
		c3.fill = GridBagConstraints.BOTH;
		c3.weightx = 0.5;
		c3.weighty = 0.5;
		c3.gridx = 2;
		c3.gridy = 0;
		c3.gridheight = 3;
		this.add(scoreCardPanel, c3);

		//set up roleResultsPanel
		GridBagConstraints c4 = new GridBagConstraints();
		roleResultsPanel = new RoleResults();
		c4.fill = GridBagConstraints.BOTH;
		c4.weightx = 0.5;
		c4.weighty = 0.5;
		c4.gridx = 0;
		c4.gridy = 1;
		c4.gridwidth = 2;
		this.add(roleResultsPanel, c4);

		//set up playerScorePanel
		GridBagConstraints c5 = new GridBagConstraints();
		playerScorePanel = new PlayerScore();
		c5.fill = GridBagConstraints.BOTH;
		c5.weightx = 0.5;
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

			//set up the layout
			heldLayout = new GridLayout(0, 6);
			this.setLayout(heldLayout);

			ImageIcon image = new ImageIcon(this.getClass().getResource("/empty.jpg"));
			JLabel label = new JLabel("", image, JLabel.CENTER);
			this.add(label);
		}
	}

	class ScoreCard extends JPanel
	{
		public ScoreCard()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.BLUE);
		}
	}

	class RoleResults extends JPanel
	{
		public RoleResults()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.GREEN);	
		}
	}

	class PlayerScore extends JPanel
	{
		public PlayerScore()
		{
			//call superclass's constructor and set background to white
			super();
			this.setBackground(Color.YELLOW);	
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
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}