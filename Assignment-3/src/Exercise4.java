import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public final class Exercise4 extends Exercise {
	private static Map<Actor, Boolean> visited = new HashMap<>();

	public static void run() {
		System.out.println("\nExercise " + NUMBERS + "4" + RESET + ":\n");

		System.out.print("Counting components...\r");

		for(Actor actor : graph.keySet())
			visited.put(actor, false);

		Map<Integer, AtomicInteger> result = new HashMap<>();

		for(Actor actor : graph.keySet()) {
			if(!visited.get(actor)) {
				int size = 0;

				size = iterativeDFS(actor);

				result.putIfAbsent(size, new AtomicInteger(0));
				result.get(size).incrementAndGet();
			}
		}

		// We cast our Map to a TreeMap to sort based on the value of our Key
		// TreeMaps sort in natural order so we use descendingMap to print in reverse order
		TreeMap<Integer, AtomicInteger> sorted = new TreeMap<>(result);
		for(Entry<Integer, AtomicInteger> entry : sorted.descendingMap().entrySet())
			System.out.printf("There are %s%d%s components of size %s%d%s\n", NUMBERS, entry.getValue().intValue(), RESET, NUMBERS, entry.getKey(), RESET);
	}

	// Most solutions for this problem uses DFS
	// But our Graph is too large for the recursive implementation
	// Therefore we use an iterative implementation of Depth-First-Search
	// Else we get a StackOverflowError
	public static int iterativeDFS(Actor actor) {
		Stack<Actor> stack = new Stack<>();

		visited.put(actor, true);
		stack.push(actor);

		int size = 1;

		while(!stack.empty()) {
			actor = stack.pop();

			if(!visited.get(actor)) {
				visited.put(actor, true);
				size++;
			}

			for(Actor neighbour : graph.get(actor))
				if(!visited.get(neighbour))
					stack.push(neighbour);
		}

		return size;
	}
}
