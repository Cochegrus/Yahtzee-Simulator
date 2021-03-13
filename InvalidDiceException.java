/** Represents a situation where the number of sides on a dice
 * does not match the number of sides on an existing dice
 * 
 * @author Jordi A. Cochegrus
 */

public class InvalidDiceException extends IllegalArgumentException {
	
	/**  Sets up this exception with an appropriate message.
	   * @param num represents illegal number of sides
	   */
	public InvalidDiceException(int num) {
		super("Inavlid number of sides: " + num);
	}
}
