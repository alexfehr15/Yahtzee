import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import java.util.Random;

//map example at figure 19_20
class Human implements Player
{
	private String name;
	private int done = 0;
	private int extra = 0;
	private int playerNumber;
	private Map< String, String > scoreMap;
	private String [] scoreLabels = {"Player:", "1", "2", "3", "4", "5", "6",
									"Bonus", "Upper Total", "", "3 of a kind",
									"4 of a kind", "Full House", 
									"Small Straight", "Large Straight",
									"Yahtzee", "Chance", "Lower Total",
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
		scoreMap.put("Bonus", "-");
		scoreMap.put("Upper Total", "0");
		scoreMap.put("Lower Total", "0");
		scoreMap.put("Grand Total", "0");
	}

	public boolean yahtzeeBool(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		for (int i : die)
			++counter[i - 1];
		for (int i : counter)
			if (i == 5)
				return true;
		return false;
	}

	public int getDone()
	{
		return done;
	}

	public boolean takeTurn(int [] die, String name, Map < JLabel, JLabel > yahtzeeMap)
	{
		//testing
		/*for (int i : die)
			System.out.println(i);
		System.out.println('\n');*/

		//see if have bonus for extra yahtzee
		if (yahtzeeBool(die, name) && scoreMap.get("Yahtzee") != "")
			++extra;

		//find out which score item is desired by user
		if (name == "1")
			one(die, name);
		else if (name == "2")
			two(die, name);
		else if (name == "3")
			three(die, name);
		else if (name == "4")
			four(die, name);
		else if (name == "5")
			five(die, name);
		else if (name == "6")
			six(die, name);
		else if (name == "3 of a kind")
			threeOfAKind(die, name);
		else if (name == "4 of a kind")
			fourOfAKind(die, name);
		else if (name == "Full House")
			fullHouse(die, name);
		else if (name == "Small Straight")
			smallStraight(die, name);
		else if (name == "Large Straight")
			largeStraight(die, name);
		else if (name == "Yahtzee")
			yahtzee(die, name);
		else if (name == "Chance")
			chance(die, name);

		//compute totals (upper, lower, and grand)
		int upperTotal = 0;
		int lowerTotal = 0;
		int grandTotal = 0;
		for (String key : scoreMap.keySet())
		{
			if (scoreMap.get(key) != "" && (key == "1" || key == "2" || key == "3" || key == "4" || key == "5" || key == "6"))
				upperTotal += Integer.parseInt(scoreMap.get(key));
			else if (scoreMap.get(key) != "" && (key == "3 of a kind" || key == "4 of a kind" || key == "Full House" || key == "Small Straight" || key == "Large Straight" || key == "Yahtzee" || key == "Chance"))
				lowerTotal += Integer.parseInt(scoreMap.get(key));
		}
		lowerTotal += extra * 100;
		grandTotal = upperTotal + lowerTotal;
		scoreMap.put("Upper Total", Integer.toString(upperTotal));
		scoreMap.put("Lower Total", Integer.toString(lowerTotal));
		scoreMap.put("Grand Total", Integer.toString(grandTotal));

		//if upper section completely filled in, compute whether got bonus
		if (scoreMap.get("1") != "" && scoreMap.get("2") != "" && scoreMap.get("3") != "" && scoreMap.get("4") != "" && scoreMap.get("5") != "" && scoreMap.get("6") != "" && scoreMap.get("Bonus") == "-" && upperTotal >= 63)
		{
			scoreMap.put("Bonus", "35");
			upperTotal += 35;
			grandTotal = lowerTotal + upperTotal;
			scoreMap.put("Upper Total", Integer.toString(upperTotal));
			scoreMap.put("Grand Total", Integer.toString(grandTotal));
		}

		//testing
		System.out.println(scoreMap);

		//return true if all areas of score map have been filled in
		for (String key : scoreMap.keySet())
		{
			if (scoreMap.get(key) == "" && key != "" && key != "Bonus")
			{
				return false;
			}
		}
		++done;
		return true;
	}

	public String getName()
	{
		return name;
	}

	public int getPlayer()
	{
		return playerNumber;
	}

	//only called once at very beginning of game
	public void initializeLabels(Map < JLabel, JLabel > yahtzeeMap)
	{
		for (String key : scoreMap.keySet())
		{
			yahtzeeMap.put(new JLabel(key), new JLabel(scoreMap.get(key)));
		}
	}

	//called every time a new player is up
	public void updateLabels(Map < JLabel, JLabel > yahtzeeMap)
	{
		for (JLabel key : yahtzeeMap.keySet())
		{
			String text = key.getText();
			yahtzeeMap.get(key).setText(scoreMap.get(text));
		}
	}

	public String getText()
	{
		return getName() + ": " + scoreMap.get("Grand Total");
	}

	public void one(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 1)
				++counter;
		scoreMap.put(name, Integer.toString(counter));
	}

	public void two(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 2)
				++counter;
		scoreMap.put(name, Integer.toString(2 * counter));
	}

	public void three(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 3)
				++counter;
		scoreMap.put(name, Integer.toString(3 * counter));
	}

	public void four(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 4)
				++counter;
		scoreMap.put(name, Integer.toString(4 * counter));
	}

	public void five(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 5)
				++counter;
		scoreMap.put(name, Integer.toString(5 * counter));
	}

	public void six(int [] die, String name)
	{
		int counter = 0;
		for (int i : die)
			if (i == 6)
				++counter;
		scoreMap.put(name, Integer.toString(6 * counter));
	}

	public void threeOfAKind(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		boolean zero = true;
		for (int i : die)
			++counter[i - 1];
		for (int i : counter)
			if (i > 2)
				zero = false;
		if (!zero)
			for (int i : die)
				answer += i;
		scoreMap.put(name, Integer.toString(answer));
	}

	public void fourOfAKind(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		boolean zero = true;
		for (int i : die)
			++counter[i - 1];
		for (int i : counter)
			if (i > 3)
				zero = false;
		if (!zero)
			for (int i : die)
				answer += i;
		scoreMap.put(name, Integer.toString(answer));
	}

	public void fullHouse(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		int zero = 0;
		for (int i : die)
			++counter[i - 1];
		for (int i : counter)
		{
			if (i == 3)
			{
				++zero;
				for (int j : counter)
					if (j == 2)
						++zero;
				break;
			}
		}
		if (zero == 2)
			answer = 25;
		if (yahtzeeBool(die, name) && scoreMap.get("Yahtzee") != "")
			scoreMap.put(name, "25");
		else
			scoreMap.put(name, Integer.toString(answer));
	}

	public void smallStraight(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		for (int i : die)
			++counter[i - 1];
		if (counter[0] == 1 && counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1)
			answer = 30;
		else if (counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1)
			answer = 30;
		else if (counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1 && counter[5] >= 1)
			answer = 30;
		if (yahtzeeBool(die, name) && scoreMap.get("Yahtzee") != "")
			scoreMap.put(name, "30");
		else
			scoreMap.put(name, Integer.toString(answer));
	}

	public void largeStraight(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		for (int i : die)
			++counter[i - 1];
		if (counter[0] == 1 && counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1)
			answer = 40;
		else if (counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1 && counter[5] >= 1)
			answer = 40;
		if (yahtzeeBool(die, name) && scoreMap.get("Yahtzee") != "")
			scoreMap.put(name, "40");
		else
			scoreMap.put(name, Integer.toString(answer));
	}

	public void yahtzee(int [] die, String name)
	{
		int [] counter = new int[6];
		int answer = 0;
		for (int i : die)
			++counter[i - 1];
		for (int i : counter)
			if (i == 5)
				answer = 50;
		scoreMap.put(name, Integer.toString(answer));
	}

	public void chance(int [] die, String name)
	{
		int answer = 0;
		for (int i : die)
			answer += i;
		scoreMap.put(name, Integer.toString(answer));
	}

	public void reset(Map < JLabel, JLabel > yahtzeeMap)
	{
		//reset all to blank
		for (String key : scoreMap.keySet())
		{
			scoreMap.put(key, "");
		}

		//add some others that should be there
		scoreMap.put("Player:", name);
		scoreMap.put("Bonus", "-");
		scoreMap.put("Upper Total", "0");
		scoreMap.put("Lower Total", "0");
		scoreMap.put("Grand Total", "0");

		//reset other variables
		done = 0;
		extra = 0;
	}
}