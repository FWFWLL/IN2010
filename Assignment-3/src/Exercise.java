import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Exercise {
	protected final static String RESET = "\033[0m";
	protected final static String SUCCESS = "\033[0;92m";
	protected final static String FAILURE = "\033[0;31m";
	protected final static String NUMBERS = "\033[0;33m";

	protected static Map<Actor, List<Actor>> graph = new HashMap<>();
	protected static Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);
	protected static Map<String, Movie> movies = new HashMap<>();
	protected static Map<String, Actor> actors = new HashMap<>();

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
