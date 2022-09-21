public class Teque {
	protected class Node {
		protected int data;
		protected Node next;
		protected Node prev;

        public Node(int x) {
            data = x;
			next = null;
			prev = null;
        }
	}

	private Node head;
	private Node middle;
	private Node tail;

	private int size;

	public Teque() {
		head = middle = tail = null;
		size = 0;
	}

	/* Insert `x` at the front */
	public void pushFront(int x) {
		Node newNode = new Node(x);

		if(size == 0)
			head = middle = tail = newNode;

		newNode.next = head;

		if(head != null)
			head.prev = newNode;

		head = newNode;

		size++;
	}

	/* Insert `x` in the middle */
	public void pushMiddle(int x) {
		Node newNode = new Node(x);

		if(size == 0) {
			head = middle = tail = newNode;
		} else {
			middle = findMiddle();

			newNode.prev = middle;

			if(middle != null) {
				newNode.next = middle.next;
				middle.next.prev = newNode;
				middle.next = newNode;
			}

			middle = newNode;
		}

		size++;
	}

	/* Insert `x` at the end */
	public void pushBack(int x) {
		Node newNode = new Node(x);

		if(size == 0)
			head = middle = tail = newNode;

		newNode.prev = tail;

		if(tail != null)
			tail.next = newNode;

		tail = newNode;

		size++;
	}

	/* Return element at `i` */
	public int get(int i) {
		Node currNode = head;

		for(int index = 0; index < i; index++)
			currNode = currNode.next;

		return currNode.data;
	}

	/* Helper function to find the middle node */
	private Node findMiddle() {
		Node currNode = head;

		for(int i = 1; i < (size + 1) / 2; i++)
			currNode = currNode.next;

		return currNode;
	}
}
