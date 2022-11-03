import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public final class Exercise3 extends Exercise {
	private final static float MAX_RATING = 10.0f;

	public static void run() {
		System.out.println("\nExercise " + NUMBERS + "3" + RESET + ":");
		printBestPath("nm2255973", "nm0000460");
		printBestPath("nm0424060", "nm0000243");
		printBestPath("nm4689420", "nm0000365");
		printBestPath("nm0000288", "nm0001401");
		printBestPath("nm0031483", "nm0931324");
	}

	// Prints the shortest, weighted path between two Actors
	private static void printBestPath(String srcActorId, String dstActorId) {
		System.out.print("\nRunning Dijkstra...\r");

		Actor srcActor = actors.get(srcActorId);
		Actor dstActor = actors.get(dstActorId);

		Map<Actor, Actor> pred = new HashMap<>();

		float totalDistance = dijkstra(srcActor, dstActor, pred);

		if(totalDistance < 0.0f) {
			System.out.println("Given source and destination are not connected");

			return;
		}

		Stack<Actor> path = new Stack<>();
		for(Actor actor = dstActor; actor != null; actor = pred.get(actor))
			path.push(actor);

		Actor prev = path.pop();
		System.out.println(prev.getName() + "                   ");

		while(!path.empty()) {
			Actor curr = path.pop();

			Movie commonMovie = findCommonMovies(prev, curr)
				.stream()
				.filter(Objects::nonNull)
				.max(Comparator.comparing(Movie::getRating))
				.orElseThrow(NoSuchElementException::new);

			System.out.printf("====[ %s (%s%.1f%s) ]===> %s\n", commonMovie.getTitle(), NUMBERS, commonMovie.getRating(), RESET, curr.getName());

			prev = curr;
		}

		System.out.printf("Total weight: %s%.1f%s\n", NUMBERS, totalDistance, RESET);
	}

	// BFS but harder, an implementation of lazy dijkstra's algorithm
	// With minor optimizations like stopping the algorithm early when certain conditions are met
	private static float dijkstra(Actor srcActor, Actor dstActor, Map<Actor, Actor> pred) {
		// Pair<Actor, Float> helper class
		// For storing Actors together with their distances from our srcActor
		class Pair implements Comparable<Pair> {
			private Actor key;
			private Float value;

			public Pair(Actor key, Float value) {
				this.key = key;
				this.value = value;
			}

			public Actor getKey() {return key;}
			public Float getValue() {return value;}

			@Override
			public int compareTo(Pair other) {
				return this.getValue().compareTo(other.getValue());
			}
		}

		Map<Actor, Boolean> visited = new HashMap<>();
		Map<Actor, Float> dist = new HashMap<>();

		for(Actor actor : graph.keySet()) {
			visited.put(actor, false);
			pred.put(actor, null);
			dist.put(actor, MAX_RATING);
		}

		dist.put(srcActor, 0.0f);

		PriorityQueue<Pair> pq = new PriorityQueue<>();
		pq.offer(new Pair(srcActor, 0.0f));

		while(!pq.isEmpty()) {
			Pair entry = pq.poll();
			Actor actor = entry.getKey();
			float minDistance = entry.getValue();

			visited.put(actor, true);

			if(dist.get(actor) < minDistance)
				continue;

			for(Actor curr : graph.get(actor)) {
				if(visited.get(curr))
					continue;

				float newDistance = dist.get(actor) + calculateMinDistance(actor, curr);

				if(newDistance < dist.get(curr)) {
					pred.put(curr, actor);
					dist.put(curr, newDistance);
					pq.offer(new Pair(curr, newDistance));
				}
			}

			if(actor == dstActor)
				return dist.get(dstActor);
		}

		return -1.0f;
	}

	// Calculate the smallest distance between two actors
	private static float calculateMinDistance(Actor actorA, Actor actorB) {
		float highestRating = 0.0f;

		Set<Movie> commonMovies = findCommonMovies(actorA, actorB);

		for(Movie movie : commonMovies) {
			if(movie != null && movie.getRating() >= highestRating) {
				highestRating = movie.getRating();
			}
		}

		return MAX_RATING - highestRating;
	}
}
