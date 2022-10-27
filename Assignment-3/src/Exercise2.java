import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Exercise2 {
	private final static String RESET = "\033[0m";
	private final static String NUMBERS = "\033[0;33m";

	public static void run(Map<Actor, List<Actor>> graph, Map<String, Actor> actors) {
		printOutput(graph, actors);
	}

	private static void printOutput(Map<Actor, List<Actor>> graph, Map<String, Actor> actors) {
		System.out.println("\nExercise " + NUMBERS + "2" + RESET + ":");
		printShortestPath(graph, actors, "nm2255973", "nm0000460");
		printShortestPath(graph, actors, "nm0424060", "nm0000243");
		printShortestPath(graph, actors, "nm4689420", "nm0000365");
		printShortestPath(graph, actors, "nm0000288", "nm0001401");
		printShortestPath(graph, actors, "nm0031483", "nm0931324");
	}

	// Prints the shortest path between two actors
	// While printing a Movie that connects them both
	private static void printShortestPath(Map<Actor, List<Actor>> graph, Map<String, Actor> actors, String srcActorId, String dstActorId) {
		Actor srcActor = actors.get(srcActorId);
		Actor dstActor = actors.get(dstActorId);

		Map<Actor, Actor> predecessors = new HashMap<>();

		if(!BFS(graph, srcActor, dstActor, predecessors)) {
			System.out.println("Given source and destination are not connected");

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

			Movie commonMovie = Main.findCommonMovies(prev, curr)
				.iterator()
				.next();

			System.out.printf("====[ %s (%s%.1f%s) ]===> %s\n", commonMovie.getTitle(), NUMBERS, commonMovie.getRating(), RESET, curr.getName());

			prev = curr;
		}
	}

	// Modified version of Breath-First-Search that stores predecessors
	private static boolean BFS(Map<Actor, List<Actor>> graph, Actor srcActor, Actor dstActor, Map<Actor, Actor> predecessors) {
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
