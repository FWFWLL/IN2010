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

		Map<Actor, Actor> predecessors = new HashMap<>();

		float totalDistance = dijkstra(srcActor, dstActor, predecessors);

		if(totalDistance == -1) {
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
	private static float dijkstra(Actor srcActor, Actor dstActor, Map<Actor, Actor> predecessors) {
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
		Map<Actor, Float> distances = new HashMap<>();

		for(Actor actor : graph.keySet()) {
			visited.put(actor, false);
			predecessors.put(actor, null);
			distances.put(actor, MAX_RATING);
		}

		distances.put(srcActor, 0.0f);

		PriorityQueue<Pair> priorityQueue = new PriorityQueue<>();
		priorityQueue.offer(new Pair(srcActor, 0.0f));

		while(!priorityQueue.isEmpty()) {
			Pair entry = priorityQueue.poll();

			Actor index = entry.getKey();
			float minDistance = entry.getValue();

			visited.put(index, true);

			if(distances.get(index) < minDistance)
				continue;

			if(index == null)
				break;

			for(Actor curr : graph.get(index)) {
				if(visited.get(curr))
					continue;

				float newDistance = distances.get(index) + calculateMinDistance(index, curr);

				if(newDistance < distances.get(curr)) {
					predecessors.put(curr, index);
					distances.put(curr, newDistance);
					priorityQueue.offer(new Pair(curr, newDistance));
				}
			}

			if(index == dstActor)
				return distances.get(dstActor);
		}

		return -1;
	}

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
