import java.util.TreeSet;

public class Set {
	private TreeSet<Integer> inner; // Assuming we don't have to implement the entire fucking BST (binary search tree)
	
	/* Constructor */
	public Set() {
		inner = new TreeSet<Integer>();
	}

	/* Check if Set contains `x` and return a boolean */
	boolean contains(int x) {return inner.contains(x);}

	/* Insert `x` into Set */
	void insert(int x) {inner.add(x);}

	/* Remove `x` from Set */
	void remove(int x) {inner.remove(x);}

	/* Return size of Set */
	int size() {return inner.size();}
}
