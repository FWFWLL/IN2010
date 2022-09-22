import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BalanceArray {
	public static void main(String[] args) throws IOException {
		ArrayList<Integer> sortedArray = new ArrayList<Integer>(); // ArrayList for collecting our input Integers

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // Read input from stdin
		
		/* Insert from stdin to our ArrayList */
		for(String line = stdin.readLine(); line != null; line = stdin.readLine())
			sortedArray.add(Integer.parseInt(line));
		
		printBalancedArray(sortedArray, 0, sortedArray.size() - 1);
	}

	/* Print a sorted Array as a BalancedArray for a BalancedBinarySearchTree */
	private static void printBalancedArray(ArrayList<Integer> sortedArray, int start, int end) {
		/* Base case */
		if(start > end)
			return;

		int middle = (start + end) / 2; // Get index of the middle element

		System.out.println(sortedArray.get(middle)); // Print current node

		printBalancedArray(sortedArray, start, middle - 1); // Print left side of current node

		printBalancedArray(sortedArray, middle + 1, end); // Print right side of current node
	}
}
