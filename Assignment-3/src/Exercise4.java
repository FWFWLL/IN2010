import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map.Entry;

public final class Exercise4 extends Exercise {
	private static Set<Actor> visited = new HashSet<>();

	public static void run() {
		System.out.println("\nExercise " + NUMBERS + "4" + RESET + ":\n");

		System.out.print("Counting components...\r");

		Map<Integer, Integer> result = new HashMap<>();
		for(Actor actor : graph.keySet()) {
			if(!visited.contains(actor)) {
				int size = 0;

				size = iterativeDFS(actor);

				result.putIfAbsent(size, 0);
				result.put(size, result.get(size) + 1);
			}
		}

		// We cast our Map to a TreeMap to sort based on the value of our Key
		// TreeMaps sort in natural order so we use descendingMap to print in reverse order
		TreeMap<Integer, Integer> sorted = new TreeMap<>(result);
		for(Entry<Integer, Integer> entry : sorted.descendingMap().entrySet())
			System.out.printf("There are %s%d%s components of size %s%d%s\n", NUMBERS, entry.getValue().intValue(), RESET, NUMBERS, entry.getKey(), RESET);
	}

	// Most solutions for this problem uses DFS
	// But our Graph is too large for the recursive implementation
	// Therefore we use an iterative implementation of Depth-First-Search
	// Else we get a StackOverflowError
	public static int iterativeDFS(Actor actor) {
		Stack<Actor> stack = new Stack<>();

		visited.add(actor);
		stack.push(actor);

		int size = 1;

		while(!stack.empty()) {
			actor = stack.pop();

			if(!visited.contains(actor)) {
				visited.add(actor);
				size++;
			}

			for(Actor neighbour : graph.get(actor))
				if(!visited.contains(neighbour))
					stack.push(neighbour);
		}

		return size;
	}
}
