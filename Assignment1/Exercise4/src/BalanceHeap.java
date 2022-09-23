import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class BalanceHeap {
	public static void main(String[] args) throws IOException {
		PriorityQueue<Integer> heap = new PriorityQueue<Integer>(); // PriorityQueue to collect and sort out input Integers

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // Read from stdin

		/* Insert input into PriorityQueue */
		for(String line = stdin.readLine(); line != null; line = stdin.readLine())
			heap.offer(Integer.parseInt(line));
		
		printBalancedHeap(heap, 0, heap.size() - 1);
	}

	/* Print a PriorityQueue as a BalancedHeap for a BalancedBinarySearchTree */
	public static void printBalancedHeap(PriorityQueue<Integer> heap, int start, int end) {
		PriorityQueue<Integer> temp = new PriorityQueue<Integer>(heap);

		/* Base case */
		if(start > end)
			return;

		int middle = (start + end) / 2; // Get index of the middle element

		/* Keep polling from the copied heap until we reach the middle index */
		for(int i = 0; i < middle; i++)
			temp.poll();

		System.out.println(temp.poll()); // Print the middle Integer

		printBalancedHeap(heap, start, middle - 1); // Recursive left side
		printBalancedHeap(heap, middle + 1, end); // Recursive right side
	}
}
