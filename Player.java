import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;

/*
todo:
.make everything look nicer
.put in help page (how to play and typical yahtzee directions)
.make console output nicer to show computer thinking
.make executable jar file and turn in
.could look for full house on 3 of a kind/4 of a kind
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

	public int getScore();
}