import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Set set = new Set(); // Initialize a new Set object

		/* Using try-with-resources to read from stdin */
		try(Scanner input = new Scanner(System.in)) {

			/* Grab the number of operations from the first line */
			for(int operationsLeft = Integer.parseInt(input.nextLine()); operationsLeft > 0; operationsLeft--) {
				String[] words = input.nextLine().split(" "); // Split the line into a String[] of words

				/* Use a switch case for each unique action we want to take */
				switch(words[0]) {
					case "contains":
						System.out.println(set.contains(Integer.parseInt(words[1])));
						break;
					case "insert":
						set.insert(Integer.parseInt(words[1]));
						break;
					case "remove":
						set.remove(Integer.parseInt(words[1]));
						break;
					case "size":
						System.out.println(set.size());
						break;
				}
			}
		} catch(Exception e) {} // We don't need to handle Exceptions right now
	}
}
