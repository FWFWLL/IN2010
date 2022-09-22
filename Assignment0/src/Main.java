import java.io.File;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Set set = new Set();
		File file = new File("input/input_100");
		try(Scanner input = new Scanner(file)) {
			for(int operationsLeft = Integer.parseInt(input.nextLine()); operationsLeft > 0; operationsLeft--) {
				String[] words = input.nextLine().split(" ");
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
		} catch(Exception e) {}
	}
}
