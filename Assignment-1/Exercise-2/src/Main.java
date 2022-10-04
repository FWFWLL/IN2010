import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Teque teque = new Teque(); // Initialize a new Teque

		/* Using try-with-resources to read from stdin */
		try(Scanner input = new Scanner(System.in)) {

			/* Grab the number of operations from the first line */
			for(int operationsLeft = Integer.parseInt(input.nextLine()); operationsLeft > 0; operationsLeft--) {
				String[] words = input.nextLine().split(" "); // Split the line into a String[] of words

				/* Use a switch case for each unique action we want to take */
				switch(words[0]) {
					case "push_front":
						teque.pushFront(Integer.parseInt(words[1]));
						break;
					case "push_middle":
						teque.pushMiddle(Integer.parseInt(words[1]));
						break;
					case "push_back":
						teque.pushBack(Integer.parseInt(words[1]));
						break;
					case "get":
						System.out.println(teque.get(Integer.parseInt(words[1])));
						break;
				}
			}
		} catch(Exception e) {} // We don't need to handle Exceptions right now
	}
}
