/**Sorter class arranges the values of an int array from
 * largest to smallest with the largest value at index 0.
 * @author Jordi A. Cochegrus
 */
public class Sorter{
	
	private int[] result;
	private int top = 0;
	
	/**
	 * Creates a new array to store sorted list
	 * @param initCap initial capacity of array
	 * @param initial initial value for comparison
	 */
	public Sorter(int initCap, int initial) {
		this.result = new int[initCap]; this.result[top] = initial;
	}
	
	/**
	 * Sorts a new value into
	 * @param item int to be sorted
	 */
	public void sortIn(int num) {
		if(this.result.length-1 == this.top) this.changeCapacity();
		int count = 0;
		int ogNum = 0;
		boolean done = false;
		// Determines where in the array the new item belongs in
		for(int i = 0; i < this.result.length; i ++) {
			if (num > this.result[i]) {
				ogNum = this.result[i];
				done = true; break;
			}else count++;
		}
		if(done) {
			// Shifts every value smaller than the new item by one
			for(int j = count+1; j < this.result.length; j++) {
				int temp = this.result[j];
				this.result[j] = ogNum;
				ogNum = temp;
			}
			this.result[count] = num;		// Inserts new item into the array
		}else{
			this.result[this.top] = num;	// Inserts new item into the array
		}
		this.top++;
	}
	
	/**
	 * Returns result of sorted array
	 * @return sorted array
	 */
	public int[] getResult() {
		return this.result;
	}
	
	/**
	 * Increases size of sorted array while preserving its values.
	 */
	private void changeCapacity() {
		int[] newResult;
		if (this.result.length > 40)
			newResult = new int[this.result.length+20];
		else
			newResult = new int[this.result.length+5];
		
		for (int i=0; i < this.result.length; i++) newResult[i] = this.result[i];
		this.result = newResult;
	}
}