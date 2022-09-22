import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BalanceChecker {
    public static void main(String[] args) throws IOException {
        BinarySearchTree tree = new BinarySearchTree();

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        for(String line = stdin.readLine(); line != null; line = stdin.readLine())
            tree.insert(Integer.parseInt(line));

		if(tree.isBalanced()) 
			System.out.println("This tree looks balanced!");
		else 
			System.out.println("This tree does not look balanced... try again!");
    }
}
