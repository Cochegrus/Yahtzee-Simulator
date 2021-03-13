/**ComputerPlayer simulates a player in a game of Yahtzee. 
 * @author Jordi A. Cochegrus
 */
public class ComputerPlayer extends YahtzeeScoreboard{
	
	/**
	 * Creates a new simulated player.
	 * @param name Identifier for simulated player.
	 */
	public ComputerPlayer(String name) {
		super(name);
	}
	
	/**
	 * Determines what action computer player takes in their turn.
	 * @param rolls Numbers rolled by computer player.
	 * @param rolled Number of times computer player has rolled the dice.
	 * @return 	String detailing either an action to take or the category to
	 * 			which the computer player has tallied its points to.
	 */
	public String action(int[] rolls, int rolled) {
		String command = "reroll";
		if(rolls[0] == 0) command = "roll";
		else try {
			this.yahtzee(rolls);
			command = "yahtzee";
		}catch(Exception e1) {
			try{
				this.largeStraight(rolls);
				command = "large straight";
			}catch(Exception e2) {
				try {
					boolean straight = false;
					if(this.memory[10] == null || rolled == 3) try {
						this.smallStraight(rolls);
						command = "small straight";
						straight = true;
					}catch(Exception e3) {
						// Nothing needs to be done here
					}
					if(!straight) {
						this.fullHouse(rolls);
						command = "full house";
					}
				}catch(Exception e4) {
					int[] values = this.findFrequency(rolls);
					int zeros = 0;
					for(int i = 0; i < 6; i++)
						if(values[i] == 0) zeros++;
					if(zeros == 4 && rolled == 3) {
						// four of a kind contains high ranking numbers
						if(values[5] == 4 || values[4] == 4) {
							boolean success = true;
							try{
								this.fourOfAKind(rolls);
								command = "four of a kind";
							}catch(CategoryUsedException e5) {
								try {
									if(values[5] == 4) {
										this.sixes(rolls);
										command = "sixes";
									}
									else { // values[4] == 4
										this.fives(rolls);
										command = "fives";
									}
								}catch(CategoryUsedException e6) {
									try {
										this.chance(rolls);
										command = "chance";
									}catch(CategoryUsedException e7) {
										success = false;
									}
								}
							}	// high ranking values have been used in all the best categories possible
							if(!success)
								command = this.checkUpper(values, rolls, 1);
								if(command == "reroll")			// no more rerolls available
									command = "set zero";		// all options exhausted
						}	// four of a kind contains no high ranking numbers
						else if(rolled == 3){	// forces  a reroll if possible
							command = this.checkUpper(values, rolls, 4);
							if (command == "reroll") command = this.checkUpper(values, rolls, 1);
							if (command == "reroll") try {
								this.fourOfAKind(rolls);
								command = "four of a kind";
							}catch(Exception e8) {
								try {
									this.chance(rolls);
									command = "chance";
								}catch(CategoryUsedException e9) {
									command = "set zero";
								}
							}
						}
					} else if(zeros == 3 && rolled == 3) {
						// three of a kind contains high ranking values
						if(values[5] == 3 || values[4] == 3) {
							boolean success = true;
							try{	// full house already rejected
								this.threeOfAKind(rolls);
								command = "three of a kind";
							}catch(CategoryUsedException e10) {
								try {
									if(values[4] == 3) {
										this.fives(rolls);
										command = "fives";
									}
									else { // values[5] == 3
										this.sixes(rolls);
										command = "sixes";
									}
								}catch(CategoryUsedException e11) {
									try {
										this.chance(rolls);
										command = "chance";
									}catch(CategoryUsedException e12) {
										success = false;
									}
								}
							}	// high ranking values have been used in all the best categories possible
							if(!success) {
								command = this.checkUpper(values, rolls, 2);
								if (command == "reroll") command = this.checkUpper(values, rolls, 1);
								if (command == "reroll")	// no more rerolls available
									command = "set zero";	// all options exhausted
							}
						}// three of a kind contains no high ranking numbers
						else{	// forces a reroll if possible
							command = this.checkUpper(values, rolls, 3);
							if (command == "reroll") command = this.checkUpper(values, rolls, 2);
							if (command == "reroll") command = this.checkUpper(values, rolls, 1);
							if (command == "reroll") try {
								this.threeOfAKind(rolls);
								command = "three of a kind";
							}catch(Exception e13) {
								try {
									this.chance(rolls);
									command = "chance";
								}catch(CategoryUsedException e14) {
									command = "set zero";
								}
							}
						}
					} // no combination to fill lower section of scoreboard
					else if(rolled == 3){ // forces reroll if possible 
						command = this.checkUpper(values, rolls, 2);
						if (command == "reroll") command = this.checkUpper(values, rolls, 1);
						if (command == "reroll") try {
							this.chance(rolls);
							command = "chance";
						}catch(CategoryUsedException e15) {
							command = "set zero";
						}
					}
				}
			}
		}
		return command;
	}
	
	/**
	 * Checks if any action can be taken towards a category in the upper section once
	 * there are little alternatives left.
	 * @param values		List containing how often a certain number was rolled
	 * @param rolls			Array that lists roll results
	 * @param occurrence	Number of times a number needs to be present to be considered
	 * 						as a reliable choice at this stage
	 * @return Recommended course of action to take.
	 * @throws CategoryUsedException
	 */
	private String checkUpper(int[] values, int[] rolls, int occurrence){
		String command = "reroll";
		boolean done = false;
		if(values[0] == occurrence) {
			try {
				this.aces(rolls);
				command = "aces"; done = true;
			}catch(CategoryUsedException e) {}
		}
		if(!done && values[1] == occurrence) {
			try {
				this.twos(rolls);
				command = "twos"; done = true;
			}catch(CategoryUsedException f){}
		}
		if(!done && values[2] == occurrence) {
			try {
				this.threes(rolls);
				command = "threes"; done = true;
			}catch(CategoryUsedException f){}
		}
		if(!done && values[3] == occurrence) {
			try{
				this.fours(rolls);
				command = "fours"; done = true;
			}catch(CategoryUsedException f){}
		}
		if(!done && values[4] == occurrence) {
			try {
				this.fives(rolls);
				command = "fives"; done = true;
			}catch(CategoryUsedException f){}
		}
		if(!done && values[5] == occurrence){
			try {
				this.sixes(rolls);
				command = "sixes";
			}catch(CategoryUsedException f){}
		}
		return command;
	}
	
	/**
	 * Determines which value to set to zero.
	 */
	public void setZero() {
		System.out.println("\nEnter category to be set to zero:");
		int pos = 0;
		for(int i = 0; i < this.memory.length; i ++)
			if(this.memory[i] == null) {
				pos = i; break;
			}
		switch(pos) {
		case 0:
			this.setZero("aces", null); break;
		case 1:
			this.setZero("twos", null); break;
		case 2:
			this.setZero("threes", null); break;
		case 3:
			this.setZero("fours", null); break;	
		case 4:
			this.setZero("fives", null); break;
		case 5:
			this.setZero("sixes", null); break;
		case 6:
			this.setZero("three of a kind", null); break;
		case 7:
			this.setZero("four of a kind", null); break;
		case 12:
			this.setZero("chance", null); break;
		case 8:
			this.setZero("full house", null); break;
		case 9:
			this.setZero("small straight", null); break;
		case 10:
			this.setZero("large straight", null); break;
		default: // case 11
			this.setZero("yahtzee", null);
		}
		System.out.println(this.memory[pos]);
	}
	
	/**
	 * Determines how often each possible value was rolled.
	 * @param rolls	Values rolled by CPU player.
	 * @return Array containing occurrence of each number rolled at i = [number rolled - 1]
	 */
	public int[] findFrequency(int[] rolls) {
		int[] values = new int[6];
		for(int i = 0; i < rolls.length; i++)
			if(rolls[i] == 1) values[0]++;
			else if (rolls[i] == 2) values[1]++;
			else if (rolls[i] == 3) values[2]++;
			else if (rolls[i] == 4) values[3]++;
			else if (rolls[i] == 5) values[4]++;
			else values[5]++; // rolls[i] == 6
		return values;
	}
}
