import java.util.HashMap;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		HashMap<String, String> tree = new HashMap<String, String>(); // Use a HashMap to store parent-child node relations

		Scanner stdin = new Scanner(System.in); // Get input from stdin

		String catNode = stdin.nextLine(); // Grab the node where the cat is located

		/* Insert the nodes into our HashMap */
		while(true) {
			String[] nodes = stdin.nextLine().split(" ");

			/* Stop when we receive a -1 from stdin */
			if(nodes[0].equals("-1"))
				break;
			
			/* Insert the nodes into our HashMap with the child as a unique KEY, and parent as VALUE */
			for(int i = 1; i < nodes.length; i++)
				tree.put(nodes[i], nodes[0]);
		}

		/* Output the route the cat has to take */
		while(true) {
			System.out.print(catNode);

			/* Stop when we reach the root */
			if(!tree.containsKey(catNode))
				break;

			System.out.print(" ");
			catNode = tree.get(catNode); // Cat climbing down the tree
		}
		
		System.out.println();
		stdin.close(); // Remember to close our Scanner
	}
}
