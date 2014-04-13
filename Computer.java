import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import java.util.Random;

class Computer implements Player
{
	private String name;
	private int playerNumber;
	private Map< String, String > scoreMap;

	private static int rand;
	private static Random r = new Random();
	private static int [] rollOne = new int[5];
	private static int [] rollTwo = new int[5];
	private static int [] rollThree = new int[5];
	private String [] scoreLabels = {"Player:", "1", "2", "3", "4", "5", "6",
									"Bonus", "Upper Total", "", "3 of a kind",
									"4 of a kind", "Full House", 
									"Small Straight", "Large Straight",
									"Yahtzee", "Chance", "Lower Total",
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
		scoreMap.put("Player:", name);
		scoreMap.put("Upper Total", "0");
		scoreMap.put("Lower Total", "0");
		scoreMap.put("Grand Total", "0");
	}

	public void takeTurn(int [] die, String name, Map < JLabel, JLabel > yahtzeeMap)
	{
		//simulate first roll of the die
		for (int i = 0; i < 5; ++i)
		{
			rand = r.nextInt(6) + 1;
			rollOne[i] = rand;
		}

		//testing
		System.out.println("\nRoll One:");
		for (int i : rollOne)
			System.out.println(i);

		//figure out what die to keep after first roll and what to aim for
		String aim = firstRoll();

		//testing
		System.out.println("\nRoll Two:");
		System.out.println(aim);
		for (int i : rollTwo)
			System.out.println(i);

		//fill any empty slots of rollTwo with random die
		int emptySlots = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 0)
			{
				++emptySlots;
				rollTwo[i] = r.nextInt(6) + 1;
			}

		//testing
		System.out.println("\nRoll Three before secondRoll():");
		System.out.println(aim);
		for (int i : rollTwo)
			System.out.println(i);

		//continue with the second roll
		aim = secondRoll(aim, emptySlots);

		//testing
		System.out.println("\nRoll Three:");
		System.out.println(aim);
		for (int i : rollThree)
			System.out.println(i);

		//testing (reset rollTwo)************************
		for (int i = 0; i < 5; ++i)
			rollTwo[i] = 0;

		//testing (reset rollThree)************************
		for (int i = 0; i < 5; ++i)
			rollThree[i] = 0;
	}

	//evaluate what die to keep and place into rollThree
	public String secondRoll(String option, int empty)
	{
		switch (option)
		{
			case "1":
				return two1();
			case "2":
				return two2();
			case "3":
				return two3();
			case "4":
				return two4();
			case "5":
				return two5();
			case "6":
				return two6();
			case "3 of a kind":
				return two3Kind(empty);
			case "4 of a kind":
				return two4Kind(empty);
			case "Full House":
				return twoFullHouse(empty);
			case "Small Straight":
				return twoSmallStraight(empty);
			case "Large Straight":
				return twoLargeStraight(empty);
			default:
				return twoOther(empty);
		}
	}

	public String two1()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 1)
				rollThree[indexOn++] = 1;
		return "1";
	}

	public String two2()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 2)
				rollThree[indexOn++] = 2;
		return "2";
	}

	public String two3()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 3)
				rollThree[indexOn++] = 3;
		return "3";
	}

	public String two4()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 4)
				rollThree[indexOn++] = 4;
		return "4";
	}

	public String two5()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 5)
				rollThree[indexOn++] = 5;
		return "5";
	}

	public String two6()
	{
		int indexOn = 0;
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == 6)
				rollThree[indexOn++] = 6;
		return "6";
	}

	public String two3Kind(int emptySlots)
	{
		int indexOn = 0;
		return "3 of a kind";
	}

	public String two4Kind(int emptySlots)
	{
		int indexOn = 0;
		return "4 of a kind";
	}

	public String twoFullHouse(int emptySlots)
	{
		int indexOn = 0;
		return "Full House";
	}

	public String twoSmallStraight(int emptySlots)
	{
		int indexOn = 0;
		return "Small Straight";
	}

	public String twoLargeStraight(int emptySlots)
	{
		int indexOn = 0;
		return "Large Straight";
	}

	public String twoOther(int emptySlots)
	{
		int indexOn = 0;
		return "Other";
	}

	//returns a string of what will be aiming for, also fills part of rollTwo
	public String firstRoll()
	{
		//calculate frequency of each dice
		int indexOn = 0;
		int [] counter = new int[6];
		for (int i : rollOne)
			++counter[i - 1];

		//check first for 4 or 5 of any digit
		for (int i = 0; i < 6; ++i)
		{
			if (counter[i] >= 3 && scoreMap.get(scoreLabels[i + 1]) == "")
			{
				for (int j : rollOne)
					if (j == i + 1)
						rollTwo[indexOn++] = j;
				return scoreLabels[i + 1];
			}
		}

		//check for straights
		if (scoreMap.get("Small Straight") == "" || scoreMap.get("Large Straight") == "")
		{
			if (counter[0] > 0 && counter[1] > 0 && counter[2] > 0 && counter[3] > 0 && counter[4] > 0)
			{
				for (int i = 0; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				for (int i = 1; i < 6; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[0] > 0 && counter[1] > 0 && counter[2] > 0 && counter[3] > 0)
			{
				for (int i = 0; i < 4; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0 && counter[4] > 0)
			{
				for (int i = 1; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[2] > 0 && counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				for (int i = 2; i < 6; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[0] > 0 && counter[1] > 0 && counter[2] > 0) 
			{
				for (int i = 0; i < 3; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0)
			{
				for (int i = 1; i < 4; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[2] > 0 && counter[3] > 0 && counter[4] > 0)
			{
				for (int i = 2; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				for (int i = 3; i < 6; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
		}

		//check for full house
		if (scoreMap.get("Full House") == "")
		{
			for (int i = 0; i < 6; ++i)
			{
				if (counter[i] >= 2)
				{
					for (int j = 0; j < 6; ++j)
						if (counter[j] >= 2 && i != j)
						{
							for (int k = 0; k < counter[i]; ++k)
								rollTwo[indexOn++] = i + 1;
							for (int x = 0; x < counter[j]; ++x)
								rollTwo[indexOn++] = j + 1;
							return "Full House";
						}
				}
			}
		}

		//check which dice has the highest frequency
		int highest = counter[0];
		int highestIndex = 0;
		for (int i = 1; i < 6; ++i)
		{
			if (counter[i] > highest)
			{
				highest = counter[i];
				highestIndex = i;
			}
			else if (scoreMap.get(Integer.toString(highestIndex + 1)) != ""
					&& scoreMap.get("4 of a kind") != "" && scoreMap.get("3 of a kind") != "" 
					)
			{
				highest = counter[i];
				highestIndex = i;
			}
		}

		//testing
		//System.out.println("\n\n" + highestIndex + " " + highest + "\n");

		//check specific number, four of a kind, three of a kind
		if (scoreMap.get(Integer.toString(highestIndex + 1)) == "")
		{
			for (int i = 0; i < highest; ++i)
				rollTwo[indexOn++] = highestIndex + 1;
			return scoreLabels[highestIndex + 1];
		}
		else if (scoreMap.get("4 of a kind") == "")
		{
			for (int i = 0; i < highest; ++i)
				rollTwo[indexOn++] = highestIndex + 1;
			return "4 of a kind";
		}
		else if (scoreMap.get("3 of a kind") == "")
		{
			for (int i = 0; i < highest; ++i)
				rollTwo[indexOn++] = highestIndex + 1;
			return "3 of a kind";
		}

		//if all else fails
		for (int i = 0; i < 5; ++i)
			rollTwo[i] = rollOne[i];
		return "Other";
	}	

	public String getName()
	{
		return name;
	}

	public int getPlayer()
	{
		return playerNumber;
	}

	//only called once at very beginning of game (prob not called)
	public void initializeLabels(Map < JLabel, JLabel > yahtzeeMap)
	{
		for (String key : scoreMap.keySet())
		{
			yahtzeeMap.put(new JLabel(key), new JLabel(scoreMap.get(key)));
		}
	}

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

	//testing
	public static void main(String [] args)
	{
		Computer test = new Computer("Alex", 1);
	}
}