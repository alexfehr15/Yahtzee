import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;

/*
todo:
.add new game feature to menu
.make everything look nicer
.put in help page (how to play and typical yahtzee directions)
.make console output nicer to show computer thinking
.ability for computer to get multiple yahtzee
.allow user to choose how many players (up to a max of 4)
.make executable jar file and turn in
.on click event for player scores at bottom to show in score card for player clicked
*/

//interface used to create heterogeneous list of Player pointers to Human and Computer objects
interface Player
{
	public boolean takeTurn(int [] die, String name, Map < JLabel, JLabel > yahtzeeMap);

	public String getName();

	public int getPlayer();

	public void initializeLabels(Map < JLabel, JLabel > yahtzeeMap);

	public void updateLabels(Map < JLabel, JLabel > yahtzeeMap);

	public String getText();

	public int getDone();

	public void reset(Map < JLabel, JLabel > yahtzeeMap);
}