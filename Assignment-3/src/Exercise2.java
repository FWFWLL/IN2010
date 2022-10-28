import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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
	// While printing a Movie that connects them both
	private static void printShortestPath(String srcActorId, String dstActorId) {
		Actor srcActor = actors.get(srcActorId);
		Actor dstActor = actors.get(dstActorId);

		Map<Actor, Actor> predecessors = new HashMap<>();

		if(!BFS(srcActor, dstActor, predecessors)) {
			System.out.println("\nGiven source and destination are not connected");

			return;
		}

		Stack<Actor> path = new Stack<>();
		Actor crawl = dstActor;
		path.push(crawl);
		while(predecessors.get(crawl) != null) {
			path.push(predecessors.get(crawl));
			crawl = predecessors.get(crawl);
		}

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
	private static boolean BFS(Actor srcActor, Actor dstActor, Map<Actor, Actor> predecessors) {
		Queue<Actor> queue = new LinkedList<>();
		Map<Actor, Boolean> visited = new HashMap<>();

		for(Actor actor : graph.keySet()) {
			visited.put(actor, false);
			predecessors.put(actor, null);
		}

		visited.put(srcActor, true);
		queue.offer(srcActor);

		while(!queue.isEmpty()) {
			Actor actor = queue.poll();

			if(actor == null)
				break;

			for(Actor curr : graph.get(actor)) {
				if(!visited.get(curr)) {
					visited.put(curr, true);
					predecessors.put(curr, actor);
					queue.offer(curr);

					if(curr == dstActor)
						return true;
				}
			}
		}

		return false;
	}
}
