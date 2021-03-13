/**Simulates computer play for Yahtzee.
 * @author Jordi A. Cochegrus
 */
public class YahtzeeComputer {
	
	private static int[] rolls;			// stores values rolled
	private static boolean[] rollAgain; // determines which values are to be rerolled
	private static int rolled;			// counts how many times the player has rolled dice this turn
	
	/**
	 * Simulates a player's turn in a game of Yahtzee.
	 * @param computer
	 * @param playerDice
	 */
	public static void computerPlay(int id, ComputerPlayer computer, DiceArray playerDice) {
		rolls = new int[playerDice.getDiceCount()];
		rollAgain = new boolean[playerDice.getDiceCount()];
		for(int i = 0; i < rollAgain.length; i++) rollAgain[i] = true;
		boolean done = false; rolled = 0;
		while(!done) {
			System.out.println("\n\nEnter a command:");
			String command = computer.action(rolls, rolled);
			System.out.println(command);
			done = verify(command, computer, playerDice);
			if(done) rolls = new int[playerDice.getDiceCount()];
		}
		
	}
	
	/**
	 * Determines if action taken by computer would have ended its Turn. May complete if
	 * necessary.
	 * @param command Action taken by computer
	 * @return True if action taken ends the computer player's turn; else, false.
	 */
	private static boolean verify(String command, ComputerPlayer computer, DiceArray playerDice) {
		switch(command) {
		case "roll":
			System.out.println();
			for(int d = 0; d < playerDice.getDiceCount(); d++) {
				rolls[d] = playerDice.getDice(d).roll();
				System.out.println(playerDice.getDice(d).getLabel() + " rolled " + rolls[d]);
			}
			rolled++; return false;
		case "reroll":
			reroll(computer, playerDice);
			rolled++; return false;
		case "set zero":
			computer.setZero();
		default:
			return true;
		}
	}
	
	/**
	 * Rerolls dice results
	 * @param computer		Simulated player that called this function.
	 * @param playerDice	Dice used by simulated player.
	 */
	private static void reroll(ComputerPlayer computer, DiceArray playerDice) {
		boolean[] toRoll = new boolean[5];
		for(int index = 0; index < toRoll.length; index++) toRoll[index] = true;
		boolean check = false;
		if(computer.memory[9] == null || computer.memory[10] == null) {
			// sorts dice rolls
			Sorter sorter = new Sorter(rolls.length, rolls[0]);
			for(int i = 1; i < rolls.length; i++) sorter.sortIn(rolls[i]);
			int[] sorted = sorter.getResult();
			// determines if chances for a straight are high enough
			boolean run = false;
			for(int j = 0; j < sorted.length-2; j++)
				if(run) break;
				else for(int k = 1; k < sorted.length-1; k++)
					if(run) break;
					else if (j != k) for(int l = 2; l < sorted.length; l++)
						if(j != l && k != l) {
							// checks for a consecutive run
							if(sorted[j] == sorted[k]+1 && sorted[j] == sorted[l]+2) {
								run = true; 
								int pos = j;
								for(int count = 0; count < 3; count++)
									for(int c = 0; c < rolls.length; c++)
										if(sorted[pos] == rolls[c]){
												toRoll[c] = false;
												if(count == 0) pos = k; else pos = l;
												break;
										}
								// breaks early if large straight has been tallied
								if(computer.memory[10] == null)
									for(int d = 0; d < rolls.length; d++)
										if(sorted[pos] == rolls[d]+1) {
											toRoll[d] = false; break;
										}
								// if small straight has been tallied and not enough consecutive numbers are available,
								// undoes previous action
								if(computer.memory[9] != null) {
									int counter = 4;
									boolean undo = false;
									while(counter > 0)
										for(int e = 0; e < toRoll.length; e++) {
											if(undo) toRoll[e] = true;
											else if (!toRoll[e]) counter--;
											if(e == toRoll.length-1)
												if(undo) counter = 0;
												else if(counter > 0) undo = true;
										}
									run = false;
								}
								break;
							}
							// does not check for disjointed series if large straight has been tallied
							else if(computer.memory[10] == null) for(int m = 3; m < sorted.length; m++)
								if(j != m && k != m && l != m &&
									sorted[j] == sorted[k]+1 && sorted[j] == sorted[l]+3 && sorted[j] == sorted[m]+4) {
									run = true;
									int pos = j;
									for(int count = 0; count < 4; count++)
										for(int c = 0; c < rolls.length; c++)
											if(sorted[pos] == rolls[c])
												if(toRoll[c]) {
													toRoll[c] = false;
													if(count == 0) pos = k; 
													else if (count == 1) pos = l;
													else pos = j;
													break;
												}
									break;
								}
						}
			if(run) check = true;
		}
		int numCheck = 0;	// used for all other 
		// determines if chances for a full house are high enough
		if(!check && computer.memory[8] == null) {
			for (int i = 0; i < 3; i++) { // All permutations are explored after 3 iterations of every loop
				if (check) break;
				else for (int j = 1; j < 4; j++) {
					if (check) break;
					else if (i != j) for(int k = 2; k < 5; k++) {
						if (i != k && j != k)
							if(rolls[i] == rolls[j] && rolls[i] == rolls[k]) {
								toRoll[i] = toRoll[j] = toRoll[k] = check = true;
								numCheck = rolls[i]; break;
							}else for(int l = 3; l < 5; l++)
								if (i != l && j != l && k != l)
									// checks all possible combinations of for two pairs
									if(rolls[i] == rolls[j] && rolls[l] == rolls[k])
										toRoll[i] = toRoll[j] = toRoll[k] = toRoll[l] = check = true;
									else if(rolls[i] == rolls[l] && rolls[j] == rolls[k])
										toRoll[i] = toRoll[j] = toRoll[k] = toRoll[l] = check = true;
									else if(rolls[l] == rolls[j] && rolls[i] == rolls[k])
										toRoll[i] = toRoll[j] = toRoll[k] = toRoll[l] = check = true;
					}
				}
			}
			// If there is a three of a kind in the set, it keeps an arbitrary roll result
			if(numCheck > 0) for(int z = 0; z < toRoll.length; z++)
				if(toRoll[z]) { toRoll[z] = false; break;}
		}
		// determines which number has a higher chance of producing most meaningful results
		if(!check) {
			int[] occurrences = computer.findFrequency(rolls);
			numCheck = 1;
			for(int x = 1; x < occurrences.length; x++)
				if(occurrences[numCheck-1] <= occurrences[x])
					numCheck = x+1;
			for(int y = 0; y < rolls.length; y++)
				if(rolls[y] == numCheck)
					toRoll[y] = false;
		}
		// rerolls dice
		System.out.println();
		for(int d = 0; d < playerDice.getDiceCount(); d++) {
			if(toRoll[d]) rolls[d] = playerDice.getDice(d).roll();
			System.out.println(playerDice.getDice(d).getLabel() + " rolled " + rolls[d]);
		}
	}
}
