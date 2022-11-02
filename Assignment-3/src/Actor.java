import java.util.HashSet;
import java.util.Set;

public class Actor {
	private String id;
	private String name;
	private Set<String> movieAppearances;

	public Actor(String id, String name) {
		this.id = id;
		this.name = name;

		movieAppearances = new HashSet<>();
	}

	public String getId() {return id;}
	public String getName() {return name;}
	public Set<String> getMovieAppearances() {return movieAppearances;}

	public void addMovie(String movieId) {
		movieAppearances.add(movieId);
	}
}
