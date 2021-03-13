/** Represents a situation where a dice is not found in the array
 * 
 * @author Jordi A. Cochegrus
 */

public class MissingDiceException extends IllegalArgumentException{
	
	/**  Sets up this exception with an appropriate message.
	   * @param label name of missing dice
	   */
	public MissingDiceException(String label){
		super("Dice not in array: " + label);
	}
}