import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;

//interface used to create heterogeneous list of Player pointers to Human and Computer objects
interface Player
{
	public void takeTurn();

	public String getName();

	public int getPlayer();

	public void initializeLabels(Map < JLabel, JLabel > yahtzeeMap);

	public String getText();
}