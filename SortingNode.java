
public class SortingNode {
	
	private SortingNode next;
	private final int num;
	
	public SortingNode(int num) {
		this.next = null;
		this.num =  num;
	}
	
	public void setNext(SortingNode next) {
		this.next = next;
	}
	
	public int getNum() {
		return this.num;
	}

	public SortingNode getNext() {
		return this.next;
	}
}
