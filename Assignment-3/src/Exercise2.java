import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public final class Exercise2 extends Exercise {
	public static void run() {
		System.out.println("\nExercise " + NUMBERS + "2" + RESET + ":");
		printShortestPath("nm2255973", "nm0000460");
		printShortestPath("nm0424060", "nm0000243");
		printShortestPath("nm4689420", "nm0000365");
		printShortestPath("nm0000288", "nm0001401");
		printShortestPath("nm0031483", "nm0931324");
	}

	// Prints the shortest path between two actors
	// While printing the path of Movies that connects them both
	private static void printShortestPath(String srcActorId, String dstActorId) {
		Actor srcActor = actors.get(srcActorId);
		Actor dstActor = actors.get(dstActorId);

		Map<Actor, Actor> pred = new HashMap<>();

		// BFS returns false when no path was found between two Actors
		if(!BFS(srcActor, dstActor, pred)) {
			System.out.println("\nGiven source and destination are not connected");

			return;
		}

		// Reconstruct path using a Stack to reverse the order when printing
		Stack<Actor> path = new Stack<>();
		for(Actor actor = dstActor; actor != null; actor = pred.get(actor))
			path.push(actor);

		Actor prev = path.pop();
		System.out.println("\n" + prev.getName());

		while(!path.empty()) {
			Actor curr = path.pop();

			Movie commonMovie = findCommonMovies(prev, curr)
				.iterator()
				.next();

			System.out.printf("====[ %s (%s%.1f%s) ]===> %s\n", commonMovie.getTitle(), NUMBERS, commonMovie.getRating(), RESET, curr.getName());

			prev = curr;
		}
	}

	// Modified version of Breath-First-Search that stores predecessors
	private static boolean BFS(Actor srcActor, Actor dstActor, Map<Actor, Actor> pred) {
		Queue<Actor> queue = new LinkedList<>();
		Set<Actor> visited = new HashSet<>();

		queue.offer(srcActor);
		visited.add(srcActor);

		while(!queue.isEmpty()) {
			Actor actor = queue.poll();

			for(Actor curr : graph.get(actor)) {
				if(!visited.contains(curr)) {
					pred.put(curr, actor);
					queue.offer(curr);
					visited.add(curr);

					if(curr == dstActor)
						return true;
				}
			}
		}

		return false;
	}
}
