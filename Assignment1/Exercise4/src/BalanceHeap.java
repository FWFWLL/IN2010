import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class BalanceHeap {
	public static void main(String[] args) throws IOException {
		PriorityQueue<Integer> mainHeap = new PriorityQueue<Integer>();

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // Read from stdin

		/* Insert input into PriorityQueue */
		for(String line = stdin.readLine(); line != null; line = stdin.readLine()) {
			mainHeap.offer(Integer.parseInt(line));
		}
		
		printBalancedHeap(mainHeap, 0, mainHeap.size() - 1);
	}

	public static void printBalancedHeap(PriorityQueue<Integer> heap, int start, int end) {
		PriorityQueue<Integer> temp = new PriorityQueue<Integer>(heap);

		if(start > end)
			return;

		int middle = (start + end) / 2;
		for(int i = 0; i < middle; i++)
			temp.poll();

		System.out.println(temp.poll());

		printBalancedHeap(heap, start, middle - 1);
		printBalancedHeap(heap, middle + 1, end);
	}
}
