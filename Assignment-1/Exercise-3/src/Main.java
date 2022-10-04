import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) throws IOException {
		HashMap<String, String> tree = new HashMap<String, String>(); // Use a HashMap to store parent-child node relations

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in)); // Get input from stdin

		String kitten = stdin.readLine(); // Grab the node where the cat is located

		/* Insert the nodes into our HashMap */
		for(String line = stdin.readLine(); !line.equals("-1"); line = stdin.readLine()) {
			String[] nodes = line.split(" "); // Split line into nodes

			/* Stop when we receive a -1 from stdin */
			if(nodes[0].equals("-1"))
				break;
			
			/* Insert the nodes into our HashMap with the child as a unique KEY, and parent as VALUE */
			for(int i = 1; i < nodes.length; i++)
				tree.put(nodes[i], nodes[0]);
		}

		/* Output the route the cat has to take */
		for(;;) {
			System.out.print(kitten);

			/* Stop when we reach the root */
			if(!tree.containsKey(kitten))
				break;

			System.out.print(" ");
			kitten = tree.get(kitten); // Cat climbing down the tree (^w^)
		}
		
		System.out.println();
	}
}
