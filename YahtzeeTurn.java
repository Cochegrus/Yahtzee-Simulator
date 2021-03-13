import java.io.*;
/**Simulates a player's turn in a game of Yahtzee.
 * @author Jordi A. Cochegrus
 */
public class YahtzeeTurn {
	
	private static int[] rolls;			// stores values rolled
	private static boolean[] rollAgain; // determines which values are to be rerolled
	private static int rolled;			// counts how many times the player has rolled dice this turn
	private static boolean quit = false;
	
	/**
	 * Simulates a player's turn in a game of Yahtzee.
	 * @param players		A YahtzeeScoreboard array containing every human players' scoreboards
	 * @param computer		A YahtzeeScoreboard array containing every computer players' scoreboards
	 * @param player		The YahtzeeScoreboard of the current player
	 * @param playerDice	The dice being used by the current player
	 * @param turnNum		The current round
	 * @param br			A BufferedReader
	 */
	public static boolean play(YahtzeeScoreboard[] players, ComputerPlayer[] computer, YahtzeeScoreboard player,
			DiceArray playerDice, int turnNum, BufferedReader br) {
		rolls = new int[playerDice.getDiceCount()];
		rollAgain = new boolean[playerDice.getDiceCount()];
		for(int i = 0; i < rollAgain.length; i++) rollAgain[i] = true;
		boolean done = false; rolled = 0;
		while(!done) {
			System.out.println("\n\nEnter a command:");
			try{
				String command = br.readLine();
				done = playGenerator(command, players, computer, player, playerDice, turnNum, br);
			}catch(IOException e) {
				System.out.println("Error interpreting command. Please re-enter command.");
			}catch(CategoryUsedException f) {
				System.out.println(f.getMessage());
			}catch(InvalidCategoryException g) {
				System.out.println(g.getMessage());
			}
		}
		return quit;
	}
	
	/**
	 * Interprets the command given by the player to generate an appropriate play or message.
	 * @param command		A string that determines what the user wants to do.
	 * @param players		A YahtzeeScoreboard array containing every human players' scoreboards
	 * @param computer		A YahtzeeScoreboard array containing every computer players' scoreboards
	 * @param player		The YahtzeeScoreboard of the current player
	 * @param playerDice	The dice being used by the current player
	 * @param turnNum		The current round
	 * @param br			A BufferedReader
	 * @return				If the player ends their turn, true; else false.
	 * @throws CategoryUsedException
	 * @throws InvalidCategoryException
	 */
	private static boolean playGenerator(String command, YahtzeeScoreboard[] players, ComputerPlayer[] computer,
			YahtzeeScoreboard player, DiceArray playerDice, int turnNum, BufferedReader br)
					throws CategoryUsedException, InvalidCategoryException{
		String argument = command.toLowerCase();
		switch(argument) {
		case "roll":
			roll(playerDice, br); return false;
		case "aces":
			return player.aces(rolls);
		case "twos":
			return player.twos(rolls);
		case "threes":
			return player.threes(rolls);
		case "fours":
			return player.fours(rolls);
		case "fives":
			return player.fives(rolls);
		case "sixes":
			return player.sixes(rolls);
		case "three of a kind":
			return player.threeOfAKind(rolls);
		case "four of a kind":
			return player.fourOfAKind(rolls);
		case "full house":
			return player.fullHouse(rolls);
		case "small straight":
			return player.smallStraight(rolls);
		case "large straight":
			return player.largeStraight(rolls);
		case "yahtzee":
			return player.yahtzee(rolls);
		case "chance":
			return player.chance(rolls);
		case "set zero":
			return player.setZero(null, br);
		case "check scoreboard":
			System.out.println(player.toString()); return false;
		case "check categories used":
			System.out.println(player.getCategoriesUsed()); return false;
		case "check turn":
			System.out.println("Turn " + turnNum); return false;
		case "check leader":
			checkLeader(players, computer, player); return false;
		case "check rolls": 
			System.out.println();
			for(int d = 0; d < rolls.length; d++)
				System.out.println(playerDice.getDice(d) + " rolled " + rolls[d]);
			return false;
		case "help":
			Help.playGenerator(); return false;
		case "quit":
			quit = true; return true;
		default:
			System.out.println("Invalid command."); return false;
		}
	}
	
	/**
	 * Determines which player is currently winning
	 * @param players	A YahtzeeScoreboard array containing every human players' scoreboards
	 * @param computer	A YahtzeeScoreboard array containing every computer players' scoreboards
	 * @param player	Initial YahtzeeScoreboard to be compared to
	 */
	private static void checkLeader(YahtzeeScoreboard[] players, ComputerPlayer[] computer, YahtzeeScoreboard player) {
		YahtzeeScoreboard leader = player;
		for(int i = 0; i < players.length; i++)
			if(players[i].totalScore() > leader.totalScore()) leader = players[i];
		for(int j = 0; j < computer.length; j++)
			if(computer[j].totalScore() > leader.totalScore()) leader = computer[j];
		System.out.println("\n" + leader.getName() + " is in the lead with " + leader.totalScore() + " points.");
	}
	
	/**
	 * Determines which Dice are to be rerolled.
	 * @param playerDice	The dice being used by the current player
	 * @param br			A BufferedReader
	 * @return If player can reroll, true; else, false.
	 */
	private static boolean reroll(DiceArray playerDice, BufferedReader br) {
		if(rolled < 3) {
			boolean done = false;
			while(!done) {
				System.out.println("\nWhat dice do you want to keep?");
				try {
					String command = br.readLine();
					if (command.equalsIgnoreCase("help")) {
						Help.roll();
					} else if (command.equalsIgnoreCase("return") || command.equalsIgnoreCase("none")) {
						return false;
					} else if (command.equalsIgnoreCase("check rolls")) {
						System.out.println();
						for(int d = 0; d < rolls.length; d++)
							System.out.println(playerDice.getDice(d).getLabel() + " rolled " + rolls[d]);
					} else {
						String[] saved = command.split(", ");
						for(int i = 0; i < saved.length; i++) {
							try {
								int j = playerDice.getDiceLocation(saved[i].toUpperCase());
								rollAgain[j] = false;
							}catch(MissingDiceException f) {
								System.out.println(f.getMessage());
								return false;
							}
						}
						done = true;
					}
				}catch(IOException e) {
					System.out.println("Error interpreting input. Please try again.");
				}
			}
			return true;
		}else {
			System.out.println("You can't reroll until your next turn.");
			return false;
		}
	}
	
	/**
	 * Rolls dice if player has rerolls left.
	 * @param playerDice	The dice being used by the current player
	 * @param br			A BufferedReader.
	 */
	private static void roll(DiceArray playerDice, BufferedReader br) {
		boolean possible = true;
		if(rolled > 0) possible = reroll(playerDice, br);
		if(possible) {
			System.out.println(); rolled++;
			for(int d = 0; d < rolls.length; d++) {
				if(rollAgain[d] == true) {
					rolls[d] = playerDice.getDice(d).roll();
				}
				System.out.println(playerDice.getDice(d).getLabel() + " rolled " + rolls[d]);
			}
			for(int i = 0; i < rollAgain.length; i++)
				rollAgain[i] = true;
		}
	}
}
