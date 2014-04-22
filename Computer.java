import java.util.Map;
import java.util.LinkedHashMap;
import javax.swing.JLabel;
import java.util.Random;

class Computer implements Player
{
	private int done = 0;
	private String name;
	private int playerNumber;
	private int extra = 0;
	private Map< String, String > scoreMap;
	private static int rand;
	private static Integer straightType = new Integer(-1);
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
	//need these so that equals function works (maybe can change)
	private static Integer zero = new Integer(0);
	private static Integer one = new Integer(1);
	private static Integer two = new Integer(2);
	private static Integer three = new Integer(3);
	private static Integer four = new Integer(4);
	private static Integer five = new Integer(5);
	private static Integer six = new Integer(6);
	private static Integer seven = new Integer(7);
	private static Integer eight = new Integer(8);

	public int getScore()
	{
		return Integer.parseInt(scoreMap.get("Grand Total"));
	}

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
		scoreMap.put("Bonus", "-");
		scoreMap.put("Upper Total", "0");
		scoreMap.put("Lower Total", "0");
		scoreMap.put("Grand Total", "0");
	}

	public int getDone()
	{
		return done;
	}

	public boolean takeTurn(int [] die, String name, Map < JLabel, JLabel > yahtzeeMap)
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
		System.out.println("\nRoll Two before secondRoll():");
		System.out.println("Empty slots: " + emptySlots);
		System.out.println("straightType: " + straightType);
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

		//fill any empty slots of rollThree with random die
		for (int i = 0; i < 5; ++i)
			if (rollThree[i] == 0)
				rollThree[i] = r.nextInt(6) + 1;

		//testing
		System.out.println("\nRoll Three after randoms:");
		System.out.println(aim);
		for (int i : rollThree)
			System.out.println(i);	

		//write down computer score for that move
		finalizeScore(aim);

		//testing (reset rollTwo)************************
		for (int i = 0; i < 5; ++i)
			rollTwo[i] = 0;

		//testing (reset rollThree)************************
		for (int i = 0; i < 5; ++i)
			rollThree[i] = 0;

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
				//System.out.println("\n*****Not Over b/c " + key + " *****");
				return false;
			}
		}
		++done;
		//System.out.println("Should have returned TRUE Computer");
		return true;
	}

	//evaluate what to put as score when all roles are finished
	public void finalizeScore(String name)
	{
		//check if have yahtzee and it is available
		if (yahtzeeBool(rollThree, name) && scoreMap.get("Yahtzee") == "")
			scoreMap.put("Yahtzee", Integer.toString(50));
		//check for second yahtzee
		else if (yahtzeeBool(rollThree, name) && scoreMap.get("Yahtzee") != "")
		{
			++extra;

			//testing
			//System.out.println("\nextra is now: " + extra);

			secondYahtzee(rollThree, name);
		}
		//check whatever other options it could be
		else if (name == "1")
			one(rollThree, name);
		else if (name == "2")
			two(rollThree, name);
		else if (name == "3")
			three(rollThree, name);
		else if (name == "4")
			four(rollThree, name);
		else if (name == "5")
			five(rollThree, name);
		else if (name == "6")
			six(rollThree, name);
		else if (name == "3 of a kind")
			threeOfAKind(rollThree, name);
		else if (name == "4 of a kind")
			fourOfAKind(rollThree, name);
		else if (name == "Full House")
			fullHouse(rollThree, name);
		else if (name == "Small Straight")
			smallStraight(rollThree, name);
		else if (name == "Large Straight")
			largeStraight(rollThree, name);
		else if (name == "Other")
			other(rollThree, name);
	}

	public void delegateZero(int [] die, String name)
	{
		int score = 0;

		//calculate frequency of each dice
		int [] counter = new int[6];
		for (int i : die)
			++counter[i - 1];

		if (scoreMap.get("1") == "")
		{
			name = "1";
			score = counter[0] * 1;
		}
		else if (scoreMap.get("Chance") == "")
		{
			name = "Chance";
			for (int i : die)
				score += i;
		}
		else if (scoreMap.get("2") == "")
		{
			name = "2";
			score = counter[1] * 2;
		}
		else if (scoreMap.get("4 of a kind") == "")
		{
			name = "4 of a kind";
		}
		else if (scoreMap.get("Yahtzee") == "")
		{
			name = "Yahtzee";
		}
		else if (scoreMap.get("Full House") == "")
		{
			name = "Full House";
		}
		else if (scoreMap.get("3 of a kind") == "")
		{
			name = "3 of a kind";
		}
		else if (scoreMap.get("3") == "")
		{
			name = "3";
			score = counter[2] * 3;
		}
		else if (scoreMap.get("4") == "")
		{
			name = "4";
			score = counter[3] * 4;
		}
		else if (scoreMap.get("5") == "")
		{
			name = "5";
			score = counter[4] * 5;
		}
		else if (scoreMap.get("6") == "")
		{
			name = "6";
			score = counter[5] * 6;
		}
		else if (scoreMap.get("Large Straight") == "")
		{
			name = "Large Straight";
		}
		else if (scoreMap.get("Small Straight") == "")
		{
			name = "Small Straight";
		}

		//fill in score
		scoreMap.put(name, Integer.toString(score));
	}

	public void secondYahtzee(int [] die, String name)
	{
		int score = 0;
		int type = die[0];
		if (scoreMap.get(Integer.toString(type)) == "")
		{
			score = 5 * type;
			name = Integer.toString(type);
		}
		else if (scoreMap.get("Large Straight") == "")
		{
			score = 40;
			name = "Large Straight";
		}
		else if (scoreMap.get("Small Straight") == "")
		{
			score = 30;
			name = "Small Straight";
		}
		else if (scoreMap.get("Full House") == "")
		{
			score = 25;
			name = "Full House";
		}
		else if (scoreMap.get("4 of a kind") == "")
		{
			score = type * 5;
			name = "4 of a kind";
		}
		else if (scoreMap.get("3 of a kind") == "")
		{
			score = type * 5;
			name = "3 of a kind";
		}
		else
		{
			for (String key : scoreMap.keySet())
			{
				if (key != "Bonus" && key != "" && scoreMap.get(key) == "")
				{
					score = type * 5;
					name = key;
				}
			}
		}

		//fill in score
		scoreMap.put(name, Integer.toString(score));
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
		{
			for (int i : die)
				answer += i;

			scoreMap.put(name, Integer.toString(answer));
		}
		else
		{
			delegateZero(die, name);
		}
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
		{
			for (int i : die)
				answer += i;

			scoreMap.put(name, Integer.toString(answer));
		}
		else if (scoreMap.get("3 of a kind") == "")
		{
			for (int i : counter)
				if (i > 2)
					zero = false;	

			if (!zero)
			{
				for (int i : die)
					answer += i;

				scoreMap.put("3 of a kind", Integer.toString(answer));
			}
			else
				delegateZero(die, name);
		}
		else
		{
			delegateZero(die, name);
		}
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
		{
			answer = 25;

			scoreMap.put(name, Integer.toString(answer));
		}
		else
		{
			delegateZero(die, name);
		}
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

		if (answer == 30)
			scoreMap.put(name, Integer.toString(answer));
		else
			delegateZero(die, name);
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

		if (answer == 40)
			scoreMap.put(name, Integer.toString(answer));
		else if (scoreMap.get("Small Straight") == "")
		{
			if (counter[0] == 1 && counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1)
				answer = 30;
			else if (counter[1] >= 1 && counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1)
				answer = 30;
			else if (counter[2] >= 1 && counter[3] >= 1 && counter[4] >= 1 && counter[5] >= 1)
				answer = 30;

			if (answer == 30)
				scoreMap.put("Small Straight", Integer.toString(answer));
			else
				delegateZero(die, name);	
		}
		else
			delegateZero(die, name);
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

	public void other(int [] die, String name)
	{
		int answer = 0;
		if (scoreMap.get("Chance") == "")
		{
			name = "Chance";
			for (int i : die)
				answer += i;
			scoreMap.put(name, Integer.toString(answer));
		}
		else
			delegateZero(die, name);
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
		int targetNum = rollTwo[0];
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == targetNum)
				rollThree[indexOn++] = targetNum;
		return "3 of a kind";
	}

	public String two4Kind(int emptySlots)
	{
		int indexOn = 0;
		int targetNum = rollTwo[0];
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == targetNum)
				rollThree[indexOn++] = targetNum;
		return "4 of a kind";
	}

	public String twoFullHouse(int emptySlots)
	{
		int indexOn = 0;
		int targetOne = rollTwo[0];
		int targetTwo = 0;
		for (int i = 0; i < 5 - emptySlots; ++i)
			if (rollTwo[i] != targetOne)
			{
				targetTwo = rollTwo[i];
				break;
			}
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == targetOne || rollTwo[i] == targetTwo)
				rollThree[indexOn++] = rollTwo[i];
		return "Full House";
	}

	public String twoSmallStraight(int emptySlots)
	{
		int indexOn = 0;
		for (int i = 0; i < 5 - emptySlots; ++i)
			rollThree[indexOn++] = rollTwo[i];
		for (int i = 5 - emptySlots; i < 5; ++i)
		{
			if (straightType.equals(zero))
				;//do nothing
			else if (straightType.equals(one))
				;//do nothing
			else if (straightType.equals(two))
			{
				if (rollTwo[i] == 5)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(three))
			{
				if (rollTwo[i] == 1 || rollTwo[i] == 6)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(four))
			{
				if (rollTwo[i] == 2)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(five))
			{
				if (rollTwo[i] == 4)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(six))
			{
				if (rollTwo[i] == 1 || rollTwo[i] == 5)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(seven))
			{
				if (rollTwo[i] == 2 || rollTwo[i] == 6)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(eight))
			{
				if (rollTwo[i] == 3)
					rollThree[indexOn++] = rollTwo[i];
			}
		}
		return "Small Straight";
	}

	public String twoLargeStraight(int emptySlots)
	{
		int indexOn = 0;
		for (int i = 0; i < 5 - emptySlots; ++i)
			rollThree[indexOn++] = rollTwo[i];
		for (int i = 5 - emptySlots; i < 5; ++i)
		{
			if (straightType.equals(zero))
				;//do nothing
			else if (straightType.equals(one))
				;//do nothing
			else if (straightType.equals(two))
			{
				if (rollTwo[i] == 5)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(three))
			{
				if (rollTwo[i] == 1 || rollTwo[i] == 6)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(four))
			{
				if (rollTwo[i] == 2)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(five))
			{
				if (rollTwo[i] == 4 || rollTwo[i] == 5)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(six))
			{
				if (rollTwo[i] == 1 || rollTwo[i] == 5)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(seven))
			{
				if (rollTwo[i] == 2 || rollTwo[i] == 6)
					rollThree[indexOn++] = rollTwo[i];
			}
			else if (straightType.equals(eight))
			{
				if (rollTwo[i] == 2 || rollTwo[i] == 3)
					rollThree[indexOn++] = rollTwo[i];
			}
		}
		return "Large Straight";
	}

	public String twoOther(int emptySlots)
	{
		int indexOn = 0;
		int targetNum = rollTwo[0];
		for (int i = 0; i < 5; ++i)
			if (rollTwo[i] == targetNum)
				rollThree[indexOn++] = targetNum;
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

		//check first for 3 or more of same digit (and blank)
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
				straightType = 0;
				for (int i = 0; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				straightType = 1;
				for (int i = 1; i < 6; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[0] > 0 && counter[1] > 0 && counter[2] > 0 && counter[3] > 0)
			{
				straightType = 2;
				for (int i = 0; i < 4; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0 && counter[4] > 0)
			{
				straightType = 3;
				for (int i = 1; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[2] > 0 && counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				straightType = 4;
				for (int i = 2; i < 6; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[0] > 0 && counter[1] > 0 && counter[2] > 0) 
			{
				straightType = 5;
				for (int i = 0; i < 3; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[1] > 0 && counter[2] > 0 && counter[3] > 0)
			{
				straightType = 6;
				for (int i = 1; i < 4; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[2] > 0 && counter[3] > 0 && counter[4] > 0)
			{
				straightType = 7;
				for (int i = 2; i < 5; ++i)
					rollTwo[indexOn++] = i + 1;
				if (scoreMap.get("Large Straight") == "")
					return "Large Straight";
				else
					return "Small Straight";
			}
			else if (counter[3] > 0 && counter[4] > 0 && counter[5] > 0)
			{
				straightType = 8;
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

		//check which dice has the highest frequency and has 
		int highest = counter[0];
		int highestIndex = 0;
		for (int i = 1; i < 6; ++i)
		{
			if (counter[i] > highest && (scoreMap.get(Integer.toString(i + 1)) == "" || scoreMap.get("4 of a kind") == "" || scoreMap.get("3 of a kind") == ""))
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
		else
		{
			for (int i = 0; i < highest; ++i)
				rollTwo[indexOn++] = highestIndex + 1;
			return "Other";
		}
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