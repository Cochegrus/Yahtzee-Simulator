/**Help class contains messages to assist users to play
 * the YahtzeeSimulator
 * @author Jordi A. Cochegrus
 */
public class Help {
	
	/**
	 * Prints list of commands that can be used once the player has
	 * chosen to set a category to 0.
	 */
	public static void setZero() {
		System.out.println("\nList of commands for \"set zero\":"
				+ "\n	Aces -			sets Aces to zero and exits \"set zero\"; can be used once"
				+ "\n	Twos -			sets Twos to zero and exits \"set zero\"; can be used once"
				+ "\n	Threes -		sets Threes to zero and exits \"set zero\"; can be used once"
				+ "\n	Fours - 		sets Fours to zero and exits \"set zero\"; can be used once"
				+ "\n	Fives -			sets Fives to zero and exits \"set zero\"; can be used once"
				+ "\n	Sixes -			sets Sixes to zero and exits \"set zero\"; can be used once"
				+ "\n	Three of a kind -	sets Three of a Kind to zero and exits \"set zero\"; can be used once"
				+ "\n	Four of a kind -	sets Four of a Kind to zero and exits \"set zero\"; can be used once"
				+ "\n	Full House -		sets Full House to zero and exits \"set zero\"; can be used once"
				+ "\n	Small Straight -	sets Small Straight to zero and exits \"set zero\"; can be used once"
				+ "\n	Large Straight -	sets Large Straight to zero and exits \"set zero\"; can be used once"
				+ "\n	Yahtzee -		sets Yahtzee to zero and exits \"set zero\"; can be used once"
				+ "\n	Chance -		sets Chance to zero and exits \"set zero\"; can be used once"
				+ "\n	Check categories used -	returns list of categoreis used; keeps you in \"set zero\""
				+ "\n	None or Return - 	exits \"set zero\""
				+ "\n	Help -			produces this message; keeps you in \"set zero\""
				+ "\n\nYour turn ends with any command that exits \"set zero\" with the exception of \"Return\"."
				+ "\nIf a category has been used in a previous turn, game will exit \"set zero\" and continue your turn."
				+ "\nLetter case can be ignored.");
	}

	/**
	 * Prints list of commands that can be used when the player is
	 * playing on their turn.
	 */
	public static void playGenerator() {
		System.out.println("\nList of commands:"
				+ "\n	Roll -			rolls dice and shows result; can be used three times per turn"
				+ "\n	Aces -			adds total of dice equal to one to score; can be used once"
				+ "\n	Twos -			adds total of dice equal to two to score; can be used once"
				+ "\n	Threes -		adds total of dice equal to three to score; can be used once"
				+ "\n	Fours -			adds total of dice equal to four to score; can be used once"
				+ "\n	Fives -			adds total of dice equal to five to score; can be used once"
				+ "\n	Sixes -			adds total of dice equal to six to score; can be used once"
				+ "\n	Three of a Kind -	adds total of all dices to score; can be used once"
				+ "\n	Four of a Kind -	adds total of all dices to score; can be used once"
				+ "\n	Full House -		adds 25 to score; can be used once"
				+ "\n	Small Straight -	adds 30 to score; can be used once"
				+ "\n	Large Straight -	adds 40 to score; can be used once"
				+ "\n	Yahtzee -		adds 50 to score the first time it's invoked; otherwise adds 100 to score; can be used 4 times"
				+ "\n	Chance -		adds total of all dices to score; can be used once"
				+ "\n	Set Zero -		sets a category to zero"
				+ "\n	Check Scoreboard -	produces player's scoreboard"
				+ "\n	Check Categories Used -	produces list of categries used by player; shows number of times \"Yahtzee\" can be used"
				+ "\n	Check Turn -		shows current number of current turn/round"
				+ "\n	Check Leader -		shows name and total score of player in the lead"
				+ "\n	Check Rolls -		shows result of dice roll"
				+ "\n	Help -			produces this message"
				+ "\n	Quit -			end game"
				+ "\n\nYour turn ends with any command that stores points in any category or sets it to zero."
				+ "\nLetter case can be ignored.");
	}
	
	/**
	 * Prints message detailing how to execute the "roll" command.
	 */
	public static void roll() {
		System.out.println("\nList the dice which you do NOT want to reroll. If multiple, seperate with a comma and a space."
				+ "\n	ie. to preserve the first and third value rolled, write \"D1, D3\"\n"
				+ "\nIf you are not sure if you want to reroll the dice, here's a list of commands:"
				+ "\n	None or Return -	exits \"roll\""
				+ "\n	Check Rolls - 		shows result of dice roll"
				+ "\n	Help -			produces this message"
				+ "\n\nYour turn does not end after this play. You can only reroll your initial roll twice."
				+ "\nLetter case can be ignored");
	}
}
