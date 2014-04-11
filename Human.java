import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import java.util.Random;

//map example at figure 19_20
class Human implements Player
{
	private String name;
	private int playerNumber;
	private Map< String, String > scoreMap;
	private String [] scoreLabels = {"Player:", "1", "2", "3", "4", "5", "6",
									"Bonus", "Total", "", "3 of a kind",
									"4 of a kind", "Full House", 
									"Small Straight", "Large Straight",
									"Yahtzee", "Chance", "Total",
									"Grand Total"};

	//constructor with name and playerNumber parameters
	public Human(String n, int p)
	{
		//initialize basic data
		name = n;
		playerNumber = p;

		//initialize map to all blank
		scoreMap = new LinkedHashMap <String, String >();
		for (String label : scoreLabels)
			scoreMap.put(label, "");
		scoreMap.put("Player:", name);
	}

	public void takeTurn()
	{
		
	}

	public String getName()
	{
		return name;
	}

	public int getPlayer()
	{
		return playerNumber;
	}

	public void initializeLabels(Map < JLabel, JLabel > yahtzeeMap)
	{
		for (String key : scoreMap.keySet())
		{
			yahtzeeMap.put(new JLabel(key), new JLabel(scoreMap.get(key)));
		}
	}

	public String getText()
	{
		return getName() + scoreMap.get("Grand Total");
	}

	//testing
	public static void main(String [] args)
	{
		Human test = new Human("Alex", 1);
	}
}