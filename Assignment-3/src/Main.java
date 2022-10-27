import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// This whole program is like O(fuck) or smth 💀
public class Main {
	private final static String HIDE_CURSOR = "\u001B[?25l";
	private final static String SHOW_CURSOR = "\u001B[?25h";

	public static Map<Actor, List<Actor>> graph = new HashMap<>();
	private static Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);
	public static Map<String, Movie> movies = new HashMap<>();
	public static Map<String, Actor> actors = new HashMap<>();

	public static void main(String[] args) throws Exception {
		System.out.print(HIDE_CURSOR + "\r");

		Exercise1.run(graph, syncGraph, movies, actors);
		Exercise2.run(graph, actors);
		Exercise3.run(graph, actors);

		System.out.print(SHOW_CURSOR + "\r");
	}

	// Helper function for finding common movies between two Actors
	public static Set<Movie> findCommonMovies(Actor actorA, Actor actorB) {
		Set<Movie> results = new HashSet<>();

		Set<Movie> setA = actorA.getMovieAppearances()
			.stream()
			.map((movieId) -> movies.get(movieId))
			.collect(Collectors.toCollection(HashSet::new));

		Set<Movie> setB = actorB.getMovieAppearances()
			.stream()
			.map((movieId) -> movies.get(movieId))
			.collect(Collectors.toCollection(HashSet::new));

		for(Movie movie : setA) {
			if(setB.contains(movie)) {
				results.add(movie);
			}
		}

		return results;
	}
}
