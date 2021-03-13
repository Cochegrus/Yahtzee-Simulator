/** DiceArray class collects dices to be used by a program or user
 * @author Jordi A. Cochegrus
 */
public class DiceArray {

	private Dice[] dices;						// Dice array
	private String[] reference;					// Makes a reference for all dices in dice array
	private final int DEFAULT_CAPACITY = 2;		// Default capacity of dice array
	private int diceCount = 0;					// Number of dices in array
	
	/** Creates empty Dice array with capacity of 2*/
	public DiceArray() {
		this.dices = new Dice[this.DEFAULT_CAPACITY];
		this.reference = new String[this.DEFAULT_CAPACITY];
	}
	
	/**
	 * Creates empty Dice array with a desired capacity
	 * @param numDice number of dices to be stored in the array
	 */
	public DiceArray(int numDice) {
		this.dices = new Dice[numDice];
		this.reference = new String[numDice];
	}
	
	/**
	 * Returns length of Dice array
	 * @return length of Dice array
	 */
	public int length() {
		return this.dices.length;
	}
	
	/** 
	 * Changes capacity of array
	 * 	@param expand true if increasing the capacity; false otherwise
	 */
	private void changeCapacity(boolean expand) {
		Dice[] newDiceArray = null;
		String[] newReferences = null;
		
		if(expand) {
			if (this.length() < 6) { // Doubles capacity of the array
				newDiceArray = new Dice[this.length()*2];
				newReferences = new String[this.length()*2];
			}
			else { // Increases capacity of array by 5
				newDiceArray = new Dice[this.length()+5];
				newReferences = new String[this.length()+5];
			}
			for (int index=0; index < this.length(); index++) {
				newDiceArray[index] = this.dices[index];
				newReferences[index] = this.reference[index];
			}
		}
		else {	// Halves capacity of array
			newDiceArray = new Dice[this.length()/2];
			newReferences = new String[this.length()/2];
			for (int index=0; index < newDiceArray.length; index++) {
				newDiceArray[index] = this.dices[index];
				newReferences[index] = this.reference[index];
			}
		}
		this.dices = newDiceArray;
		this.reference = newReferences;
	}
	
	/**
	 * Adds dice to the array
	 * @param dice dice to be added to array
	 */
	public void setDice(Dice dice) {
		if(diceCount == this.length()) changeCapacity(true);
		dices[diceCount] = dice;
		reference[diceCount] = dice.getLabel();
		diceCount++;
	}
	
	/**
	 * Removes dice from array.
	 * @param label Name of the Dice
	 * @return Dice removed
	 * @throws MissingDiceException
	 */
	public Dice removeDice(String label) throws MissingDiceException{
		int index = this.getDiceCount();
		// Locates dice to be removed
		for(int i = 0; i < this.getDiceCount(); i++)
			if(this.reference[i].contentEquals(label)) {index = i; break;}
			else if(i == this.getDiceCount()-1) {throw new MissingDiceException(label);}
		// Removes dice
		Dice oldDice = dices[index];
		for(int j = index; j < this.length()-1; j++) {
			dices[j] = dices[j+1];
			reference[j] = reference[j+1];
		}
		diceCount--;
		if (diceCount < this.length()/2) changeCapacity(false);
		return oldDice;
	}
	
	/** Returns number of dice in array
	 * 		@return number of dice in array
	 */
	public int getDiceCount() {
		return diceCount;
	}
	
	/** Retrieves dice from array without removing it
	 * 		@param label label of dice
	 * 		@return requested dice
	 */
	public Dice getDice(String label) {
		Dice wanted = null;
		
		for (int i = 0; i < diceCount; i++) {
			if (reference[i].equals(label)) {
				wanted = dices[i];
				break;
			}
		}
		return wanted;
	}
	
	/**
	 * Retrieves dice specific location in array
	 * @param i	Index of dice
	 * @return Dice at position i; may be null
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public Dice getDice(int i) throws ArrayIndexOutOfBoundsException{
		return dices[i];
	}
	
	/**
	 * Finds the location of Dice within DiceArray
	 * @param label	Name of the Dice
	 * @return	Position of the Dice within DiceArray
	 * @throws MissingDiceException
	 */
	public int getDiceLocation(String label) throws MissingDiceException{
		int location = -1;
		for(int i = 0; i < diceCount; i++) {
			if (reference[i].equals(label)) {
				location = i;
				break;
			}
		}
		if(location == -1) throw new MissingDiceException(label);
		else return location;
	}
	
	/**
	 * Rolls every dice in the array
	 * @param limit Number of Dice to be rolled
	 * @return	Total sum of values rolled by all Dice.
	 */
	public int addRolls(int limit) {
		int result = 0;
		for(int i = 0; i < this.getDiceCount() && i < limit; i++){
			int roll = this.getDice(i).roll();
			String str = this.getDice(i).getLabel() + " rolled " + Integer.toString(roll);
			System.out.println(str);
			result += roll;
		}
		return result;
	}
	
	/*
	 * // for when you forget your die but not your computer
	 * static public void main(String[] args) {
	 * DiceArray d4s = new DiceArray(6);
	 * DiceArray d6s = new DiceArray(6);
	 * DiceArray d8s = new DiceArray(6);
	 * DiceArray d10s = new DiceArray(6);
	 * DiceArray d12s = new DiceArray(6);
	 * DiceArray d20s = new DiceArray(6);
	 * DiceArray d100s = new DiceArray(6);
	 * 
	 * for(int i = 0; i < 6; i++) {
	 * d4s.setDice(new Dice(4));
	 * d6s.setDice(new Dice(6));
	 * d8s.setDice(new Dice(8));
	 * d10s.setDice(new Dice(10));
	 * d12s.setDice(new Dice(12));
	 * d20s.setDice(new Dice(20));
	 * d100s.setDice(new Dice(100));
	 * }
	 * BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	 * String command = ""; int num = 0; boolean cont = true;
	 * 
	 * while(cont) {
	 *
	 * System.out.println("Num of die: "); // number of dice to be rolled
	 * try {
	 * command = br.readLine().toString(); System.out.println(command);
	 * if (command == "0"){
	 * command = "s"; cont = false;
	 * } else num = Integer.parseInt(command);
	 * } catch (NumberFormatException | IOException e) {
	 * if (command != "q") {
	 * System.out.println("Not a number.\n\n"); command = "s";
	 * }
	 * }
	 * 
	 * if (command != "s" && command != "q") {
	 * System.out.println("Kind of die: ");	// kind of dice to be rolled
	 * try { command = br.readLine().toString(); } catch (IOException e) {
	 * System.out.println("Try again.\n\n"); command = "pp";
	 * }
	 * }
	 * 
	 * if (command != "pp" && command != "s") {
	 * switch(command) {
	 * case "d4": num = d4s.addRolls(num, d4s); break;
	 * case "d6": num = d6s.addRolls(num, d6s); break;
	 * case "d8": num = d8s.addRolls(num, d8s); break;
	 * case "d10": num = d10s.addRolls(num, d10s); break;
	 * case "d12": num = d12s.addRolls(num, d12s); break;
	 * case "d20": num = d20s.addRolls(num, d20s); break;
	 * case "d100": num = d100s.addRolls(num,d100s); break;
	 * case "q": cont = false; break;
	 * default: System.out.println("Faulty dice type.\n\n"); if (cont) command = "f";
	 * }
	 * if (command != "f" && cont) {
	 * System.out.println("Total: " + Integer.toString(num) + " \n\n");
	 * }
	 * }
	 * 
	 * }
	 * System.out.println("\nGoodbye.\n"); }
	 */
}
