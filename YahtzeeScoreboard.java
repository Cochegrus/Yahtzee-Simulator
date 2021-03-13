import java.io.*;
/**YahtzeeScoarboard tallies the number of points
 * scored by any given player and determines when
 * points can be awarded.
 * @author Jordi A. Cochegrus
 */
public class YahtzeeScoreboard {
	
	protected int[] scoreboard = new int[13];
	protected String[] memory = new String[13];
	protected final String player;
	protected int bonusCount = -1;
	
	/**
	 * Creates a new scoreboard.
	 * @param name	The player's name
	 */
	public YahtzeeScoreboard(String name) {
		player = name;
		for (int i = 0; i < 13; i++) {scoreboard[i] = 0; memory[i] = null;}
	}
	
	/**
	 * Adds points to a category in the upper section of the scoreboard.
	 * @param rolls		Values rolled.
	 * @param num		Number to be compared to
	 * @param category	Category to which the points should be added
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	private boolean upperSection(int[] rolls, int num, String category) throws CategoryUsedException, InvalidCategoryException {
		if (memory[num-1] != null) throw new CategoryUsedException(category);
		boolean done = false;
		for (int i = 0; i < rolls.length; i++)
			if (rolls[i] == num) {scoreboard[num-1] += num; done = true;}
		if(done) memory[num-1] = category;
		else throw new InvalidCategoryException(category);
		return true;
	}
	
	/**
	 * Adds total sum of aces to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean aces(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 1, "Aces");
	}
	
	/**
	 * Adds total sum of twos to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean twos(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 2, "Twos");
	}
	
	/**
	 * Adds total sum of threes to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean threes(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 3, "Threes");
	}
	
	/**
	 * Adds total sum of fours to the scoreboard.
	 * @param rolls		Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean fours(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 4, "Fours");
	}
	
	/**
	 * Adds total sum of fives to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean fives(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 5, "Fives");
	}
	
	/**
	 * Adds total sum of sixes to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean sixes(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		return upperSection(rolls, 6, "Sixes");
	}
	
	/**
	 * Adds total sum of dice to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean threeOfAKind(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		if (memory[6] != null) throw new CategoryUsedException("Three of a kind");
		boolean check = false;
		for (int i = 0; i < 3; i++) { // All permutations are explored after 3 iterations of every loop
			if (check) break;
			else for (int j = 1; j < 4; j++) {
				if (check) break;
				else if(i != j) for(int k = 2; k < 5; k++) {
					if (i != k && j != k && rolls[i] == rolls[j] && rolls[i] == rolls[k]) {
						check = true; break;
					}
				}
			}
		}
		if (check) {
			for (int index = 0; index < rolls.length; index++) scoreboard[6] += rolls[index];
			memory[6] = "Three of a kind";
		}else throw new InvalidCategoryException("Three of a kind");
		return true;
	}
	
	/**
	 * Adds total sum of dice to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean fourOfAKind(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		if (memory[7] != null) throw new CategoryUsedException("Four of a kind");
		boolean check = false;
		for (int i = 0; i < 2; i++) { // All permutations are explored after 2 iterations of every loop
			if (check) break;
			else for (int j = 1; j < 3; j++) {
				if (check) break;
				else if (i != j) for(int k = 2; k < 4; k++) {
					if(check) break;
					else if (j != k) for (int l = 3; l < 5; l++) { // i can never equal k
						if (k != l && rolls[i] == rolls[j] &&
								rolls[i] == rolls[k] && rolls[i] == rolls[l]) { // i and j can never equal to l
							check = true; break;
						}
					}
				}
			}
		}
		if (check) {
			for (int index = 0; index < rolls.length; index++) scoreboard[7] += rolls[index];
			memory[7] = "Four of a kind";
		}else throw new InvalidCategoryException("Four of a kind");
		return true;
	}
	
	/**
	 * Adds 25 points to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean fullHouse(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		if (memory[8] != null) throw new CategoryUsedException("Full House");
		boolean[] diceCheck = new boolean[5];
		for (int n = 0; n < diceCheck.length; n ++) {
			diceCheck[n] = false;	// records which dice were used in the three of a kind
		}
		// Looks for a three of a kind
		boolean check = false;
		int numCheck = -1;			// records values used for the three of a kind
		for (int i = 0; i < 3; i++) { // All permutations are explored after 3 iterations of every loop
			if (check) break;
			else for (int j = 1; j < 4; j++) {
				if (check) break;
				else if (i != j) for(int k = 2; k < 5; k++) {
					if (i != k && j != k && rolls[i] == rolls[j] && rolls[i] == rolls[k]) {
						diceCheck[i] = diceCheck[j] = diceCheck[k] = check = true;
						numCheck = rolls[i]; break;
					}
				}
			}
		}
		if (check) {
			// Looks for a pair
			int num1 = -1;
			int num2 = -1;
			for(int n = 0; n < diceCheck.length; n++) {
				if (!diceCheck[n]) {
					if (num1 < 0) num1 = rolls[n];
					else {num2 = rolls[n]; break;}
				}
			}
			if(num1 > -1 && num2 > -1 && num1 == num2 && num1 != numCheck) {scoreboard[8] = 25;	memory[8] = "Full House";}
			else throw new InvalidCategoryException("Full House");
		}
		else throw new InvalidCategoryException("Full House");
		return true;
	}
	
	/**
	 * Adds 30 points to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean smallStraight(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		if (memory[9] != null) throw new CategoryUsedException("Small straight");
		// Sorts rolls from largest to smallest
		Sorter sorted = new Sorter(rolls.length, rolls[0]);
		for(int i = 1; i < rolls.length; i++) sorted.sortIn(rolls[i]);
		rolls = sorted.getResult();
		// Looks for small straight
		boolean check = false;
		for (int i = 0; i < 2; i++) { // All permutations are explored after 2 iterations of every loop
			if (check) break;
			else for (int j = 1; j < 3; j++) {
				if (check) break;
				else if (i != j) for(int k = 2; k < 4; k++) {
					if(check) break;
					else if (j != k) for (int l = 3; l < 5; l++) { // i can never equal k
						if (k != l && rolls[i] == rolls[j]+1 && rolls[i] == rolls[k]+2 &&
								rolls[i] == rolls[l]+3) { // i and j can never equal l
							check = true; break;
						}
					}
				}
			}
		}
		if (check) {scoreboard[9] = 30;	memory[9] = "Small straight";
		}else throw new InvalidCategoryException("Small straight");
		return true;
	}
	
	/**
	 * Adds 40 points to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean largeStraight(int[] rolls) throws CategoryUsedException, InvalidCategoryException{
		if (memory[10] != null) throw new CategoryUsedException("Large straight");
		// Sorts rolls from largest to smallest
		Sorter sorted = new Sorter(rolls.length, rolls[0]);
		for(int i = 1; i < rolls.length; i++) sorted.sortIn(rolls[i]);
		rolls = sorted.getResult();
		// Looks for large straight
		boolean check = false;
		if(rolls[0] == rolls[1]+1 && rolls[0] == rolls[2]+2 &&
				rolls[0] == rolls[3]+3 && rolls[0] == rolls[4]+4)
			check = true;
		if (check) {scoreboard[10] = 40; memory[10] = "Large straight";
		}else throw new InvalidCategoryException("Large straight");
		return true;
	}
	
	/**
	 * Adds 50 points to the scoreboard the first time it's invoked by a player.
	 * Every other time, it adds 100 points.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean yahtzee(int[] rolls) throws CategoryUsedException, InvalidCategoryException {
		if (memory[11] != null && bonusCount == 3) throw new CategoryUsedException("Yahtzee");
		boolean check = false;
		if(rolls[0] == rolls[1] && rolls[0] == rolls[2] &&
				rolls[0] == rolls[3] && rolls[0] == rolls[4])
			check = true;
		if (check && bonusCount == -1) {	// First time
			scoreboard[11] = 50; memory[11] = "Yahtzee";
			bonusCount++;		// Set to zero at this point
		}
		else if (check) {		// Second to fourth time
			scoreboard[11] += 100;
			bonusCount++;		// After the fourth time, it'll have a value of 3,
								// preventing further use of this category
		}
		else throw new InvalidCategoryException("Yahtzee");
		return true;
	}
	
	/**
	 * Adds total sum of dice to the scoreboard.
	 * @param rolls	Values rolled.
	 * @return	True upon completion.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	public boolean chance(int[] rolls) throws CategoryUsedException {
		if (memory[12] != null) throw new CategoryUsedException("Chance");
		
		for (int i = 0; i < rolls.length; i++) scoreboard[12] += rolls[i];
		memory[12] = "Chance";
		return true;
	}
	
	/**
	 * Produces message detailing what categories have already been filled.
	 * @return List of categories used by this scoreboard's player
	 */
	public String getCategoriesUsed() {
		String message = "Categories used:	";
		for (int i = 0; i < memory.length-1; i++)
			if (memory[i] != null) message += memory[i] + "\n			";
		if(memory[12] != null) message += memory[12] + "\n			";
		if(this.bonusCount < 3) message += "Yahtzee has " + (3 - this.bonusCount) + " uses left";
		return message;
	}
	
	/**
	 * Prevents player from using a category in a future turn to gain points.
	 * @param argument	Category to be set to zero.
	 * @param br		A BufferedReader.
	 * @return If a category is set to zero, true; else, false.
	 * @throws CategoryUsedException
	 */
	public boolean setZero(String argument, BufferedReader br) throws CategoryUsedException {
		String category = "";
		if(argument == null) {
			System.out.println("\nEnter category to be set to zero:");
			try{
				category = br.readLine();
			}catch(IOException f) {
				System.out.println("Error interpreting command. Please try again.");
				return this.setZero(null, br);
			}
		} else category = argument;
		category.toLowerCase();
		
		switch(category) {
			case "aces": 
				if(this.memory[0] != null) throw new CategoryUsedException("Aces");
				this.memory[0] = "Aces"; return true;
			case "twos":
				if(this.memory[1] != null) throw new CategoryUsedException("Twos");
				this.memory[1] = "Twos"; return true;
			case "threes":
				if(this.memory[2] != null) throw new CategoryUsedException("Threes");
				this.memory[2] = "Threes"; return true;
			case "fours": 
				if(this.memory[3] != null) throw new CategoryUsedException("Fours");
				this.memory[3] = "Fours"; return true;
			case "fives":
				if(this.memory[4] != null) throw new CategoryUsedException("Fives");
				this.memory[4] = "Fives"; return true;
			case "sixes":
				if(this.memory[5] != null) throw new CategoryUsedException("Sixes");
				this.memory[5] = "Sixes"; return true;
			case "three of a kind": 
				if(this.memory[6] != null) throw new CategoryUsedException("Three of a kind");
				this.memory[6] = "Three of a kind"; return true;
			case "four of a kind": 
				if(this.memory[7] != null) throw new CategoryUsedException("Four of a kind");
				this.memory[7] = "Four of a kind"; return true;
			case "full house":
				if(this.memory[8] != null) throw new CategoryUsedException("Full House");
				this.memory[8] = "Full House"; return true;
			case "small straight":
				if(this.memory[9] != null) throw new CategoryUsedException("Small Straight");
				this.memory[9] = "Small Straight"; return true;
			case "large straight":
				if(this.memory[10] != null) throw new CategoryUsedException("Large Straight");
				this.memory[10] = "Large Straight"; return true;
			case "yahtzee": 
				if(this.memory[11] != null) throw new CategoryUsedException("Yahtzee");
				this.memory[11] = "Yahtzee"; this.bonusCount = 3; return true;
			case "chance":
				if(this.memory[12] != null) throw new CategoryUsedException("Chance");
				this.memory[12] = "Chance"; return true;
			case "check categories used": 
				System.out.println(this.getCategoriesUsed()); return this.setZero(null, br);
			case "return":
				return false;
			case "none":
				return false;
			case "help":
				Help.setZero(); return this.setZero(null, br);
			default: {
				System.out.println("Invalid category or command.\nEnter category to be set to zero:");
				try {
					String command = br.readLine();
					return this.setZero(command, br);
				}
				catch(IOException e) {
					System.out.println("Error interpreting command. Please try again.");
					return this.setZero(null, br);
				}
			}
		}
	}
	
	/**
	 * Adds all of the point tallied in the upper section.
	 * @param determines if total includes upper section's bonus or not
	 * @return total score for the upper section of the scoreboard
	 */
	private int totalUpperSection(boolean addBonus) {
		int total = 0;
		for (int i = 0; i < 6; i++)
			total += this.scoreboard[i];
		if(addBonus && total > 63) total += 35;
		return total;
	}
	
	/**
	 * Adds all of the points tallied in the lower section.
	 * @return total score for the lower section of the scoreboard
	 */
	private int totalLowerSection() {
		int total = 0;
		for (int i = 6; i < 13; i++)
			total += this.scoreboard[i];
		return total;
	}
	
	/**
	 * Adds all of the tallied points.
	 * @return total recorded score
	 */
	public int totalScore() {
		int total = this.totalUpperSection(true) + this.totalLowerSection();
		return total;
	}
	
	/**
	 * Identifies the owner of this scoreboard.
	 * @return player's name
	 */
	public String getName() {
		return this.player;
	}
	
	/**
	 * Produces a message denoting all points tallied per category in this scoreboard.
	 */
	public String toString() {
		int upper = this.totalUpperSection(false);
		int upperBonus = 0;
		if(upper > 63) {upperBonus = 35; upper += upperBonus;}
		int lower = this.totalLowerSection();
		
		String message = "\n"+ player + "'s Scoreboard\n\n"
				
				+ "Aces:			" + scoreboard[0] + "\n"
				+ "Twos:			" + scoreboard[1] + "\n"
				+ "Threes:			" + scoreboard[2] + "\n"
				+ "Fours:			" + scoreboard[3] + "\n"
				+ "Fives:			" + scoreboard[4] + "\n"
				+ "Sixes:			" + scoreboard[5] + "\n"
				+ "Upper Section bonus:	" + upperBonus + "\n"
				+ "Upper Section total:	" + (upper) + "\n\n"
				
				+ "Three of a kind:  	" + scoreboard[6] + "\n"
				+ "Four of a kind: 	" + scoreboard[7] + "\n"
				+ "Full House:		" + scoreboard[8] + "\n"
				+ "Small Straight:		" + scoreboard[9] + "\n"
				+ "Large Straight:		" + scoreboard[10] + "\n"
				+ "Yahtzee:		" + scoreboard[11] + "\n"
				+ "Chance:			" + scoreboard[12] + "\n"
				+ "Lower Section total:	" + lower + "\n"
				+ "Upper Section total:	" + (upper) + "\n\n"
				
				+ "Total score:		" + (lower + upper);
		
		return message;
	}
}
