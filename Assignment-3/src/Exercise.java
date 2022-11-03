import java.io.BufferedOutputStream;
import java.io.OutputStream;
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

	protected final static OutputStream stdout = new BufferedOutputStream(System.out);

	protected final static Map<Actor, List<Actor>> graph = new HashMap<>();
	protected final static Map<String, Movie> movies = new HashMap<>();
	protected final static Map<String, Actor> actors = new HashMap<>();

	// Helper method for finding a set of movies shared between two Actors
	public static Set<Movie> findCommonMovies(Actor actorA, Actor actorB) {
		Set<Movie> results = new HashSet<>();

		Set<Movie> setA = actorA.getMovieAppearances()
			.stream()
			.map(movies::get)
			.collect(Collectors.toCollection(HashSet::new));

		Set<Movie> setB = actorB.getMovieAppearances()
			.stream()
			.map(movies::get)
			.collect(Collectors.toCollection(HashSet::new));

		for(Movie movie : setA) {
			if(setB.contains(movie)) {
				results.add(movie);
			}
		}

		return results;
	}
}
