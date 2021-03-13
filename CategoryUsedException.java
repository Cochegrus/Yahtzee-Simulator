/** Represents a situation where the user chooses a category
 * they have already used
 * 
 * @author Jordi A. Cochegrus
 */

public class CategoryUsedException extends IllegalArgumentException{
	
	/**  Sets up this exception with an appropriate message.
	   * @param category represents category that throws exception
	   */
	public CategoryUsedException(String category){
		super("Used category: " + category);
	}
}
