import java.util.Random;
/** Dice class simulates the behaviour of a dice with any real dice
 * @author Jordi A. Cochegrus
 */
public class Dice {
	
	private int sides;									// Number of sides in a dice
	private String label;								// Distinguishes one dice from another dice
	private Random numGenerator = new Random();			// Generates random result for roll method
	private final int DEFAULT_SIDES = 6;				// Default number of sides for any dice
	
	/** Creates new dice with custom number of sides and preset label
	 * @param numSides Number of sides
	 * @throws InvalidDiceException Number of sides does not represent a real dice
	 */
	public Dice(int numSides) throws InvalidDiceException{
		sides = numSides;
		
		// Sets label according to conventional names used in Dungeons and Dragons
		switch (numSides) {
		case 4: label = "d4"; break;
		case 6: label = "d6"; break;
		case 8: label = "d8"; break;
		case 10: label = "d10"; break;
		case 12: label = "d12"; break;
		case 20: label = "d20"; break;
		case 100: label = "d100"; break;
		// Prevents program from creating impossible dice
		default: throw new InvalidDiceException(numSides);
		}
	}
	
	/** Creates new dice with 6 sides and custom label
	 * @param name Label of dice
	 */
	public Dice(String name) {
		sides = DEFAULT_SIDES;
		label = name;
	}
	
	/** Creates new dice with custom label and number of sides
	 * @param name Label of dice
	 * @param numSides Number of sides
	 * @throws InvalidDiceException	Number of sides does not represent a real dice
	 */
	public Dice(String name, int numSides) throws InvalidDiceException{
		// Prevents program from creating impossible dice
		if(numSides != 4 || numSides != 6 || numSides != 8 || numSides != 10 || numSides != 12 || numSides != 20 || numSides != 100)
			throw new InvalidDiceException(numSides);
		sides = numSides;
		label = name;
	}
	
	/** Returns number of sides on the dice
	 * @return int number of sides of dice
	 */
	public int getSides() {
		return sides;
	}
	
	/** Returns label of the dice
	 * @return String label of dice
	 */
	public String getLabel() {
		return label;
	}
	
	/** Rolls dice
	 * @return int result of roll
	 */
	public int roll() {
		int result = numGenerator.nextInt(sides);		// nextInt generates a number between 0 and input int (exclusive)
		return result+1;								// so 1 is added to make 0 < result <= sides
	}
	
	/** Adds results of two dices
	 * @param dice Other dice
	 * @return int combined results of two dice rolls
	 */
	public int addRolls(Dice dice) {
		Dice other = dice;
		int result = this.roll() + other.roll();
		return result;
	}
	
	/** Returns a string representation of this dice
	 * @return String representation of this stack
	 */
	public String toString() {
		return label + " has " + sides + " sides";
	}
}
