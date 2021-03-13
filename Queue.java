
public class Queue {
	
	private SortingNode next;
	
	public void setQueue(int nums) {
		SortingNode first = this.next = new SortingNode(0);
		for(int i = nums-1; i > 0; i--) {
			SortingNode node = new SortingNode(i);
			node.setNext(this.next);
			this.next = node;
		}
		first.setNext(this.next);
		this.next = first;
	}

	public int getNext() {
		int num = this.next.getNum();
		this.next = this.next.getNext();
		return num;
	}

}
