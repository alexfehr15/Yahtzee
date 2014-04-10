import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;

class Computer implements Player
{
	private String name;
	private int playerNumber;
	private Map< String, String > scoreMap;
	private String [] scoreLabels = {"1", "2", "3", "4", "5", "6",
									"Bonus", "Total", "3 of a kind",
									"4 of a kind", "Full House", 
									"Small Straight", "Large Straight",
									"Yahtzee", "Chance", "Total",
									"Grand Total"};

	//constructor with name and playerNumber parameters
	public Computer(String n, int p)
	{
		//initialize basic data
		name = n;
		playerNumber = p;

		//initialize map to all blank
		scoreMap = new LinkedHashMap <String, String >();
		for (String label : scoreLabels)
			scoreMap.put(label, "");

		System.out.println(scoreMap);
	}

	public int takeTurn()
	{
		return 0;
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

	}

	//testing
	public static void main(String [] args)
	{
		Computer test = new Computer("Alex", 1);
	}
}