/** Represents a situation where an invalid category is
 * chosen by the player
 * 
 * @author Jordi A. Cochegrus
 */

public class InvalidCategoryException extends IllegalArgumentException{
	
	/**  Sets up this exception with an appropriate message.
	   * @param message represents category that throws exception
	   */
	public InvalidCategoryException(String category) {
		super("Category cannot be used: " + category);
	}
}
