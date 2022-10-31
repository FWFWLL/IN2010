import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Exercise1 extends Exercise {
	private final static String CACHE_FILEPATH = ".cache/cache.ffl";
	private final static String MOVIES_FILEPATH = "tsv/movies.tsv";
	private final static String ACTORS_FILEPATH = "tsv/actors.tsv";

	public static void run() throws Exception {
		readMoviesTSV(MOVIES_FILEPATH);
		readActorsTSV(ACTORS_FILEPATH);

		// If populateGraph does not find a cache file, generate Graph and write to cache file
		if(!populateGraph()) {
			buildGraph();
			generateCacheFile(CACHE_FILEPATH);
		}

		// Number of vertices in our Graph is the number of Keys in our Map
		int numNodes = graph.size();

		// Number of edges in our Graph is the accumulated size of all list Values
		int numEdges = 0;
		for(List<Actor> edges : graph.values())
			numEdges += edges.size();

		// Since we are using an adjacency list, we have duplicate edges for completeness
		// Therefore we just halve our edge count
		numEdges /= 2;

		System.out.println("\nExercise " + NUMBERS + "1" + RESET + ":\n");
		System.out.println("Nodes: " + NUMBERS + numNodes + RESET);
		System.out.println("Edges: " + NUMBERS + numEdges + RESET);
	}

	// Read movies.tsv file and insert into a Map using tt-id as Keys
	private static void readMoviesTSV(String filepath) throws Exception {
		System.out.print("Reading " + filepath);

		try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] fields = line.trim().split("\t");

				movies.put(fields[0], new Movie(fields));
			}
		}

		System.out.println(SUCCESS + " DONE" + RESET);
	}

	// Read actors.tsv file and insert into a Map using nm-id as Keys
	private static void readActorsTSV(String filepath) throws Exception {
		System.out.print("Reading " + filepath);

		try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] fields = line.trim().split("\t");
				Actor actor = new Actor(fields[0], fields[1]);
				
				for(int i = 2; i < fields.length; i++)
					actor.addMovie(fields[i]);

				actors.put(fields[0], actor);
			}
		}

		System.out.println(SUCCESS + " DONE" + RESET);
	}
	
	// If cache file exists, populate graph based on cache file content
	private static boolean populateGraph() {
		System.out.print(CACHE_FILEPATH + "...");

		File file = new File(CACHE_FILEPATH);
		if(file.exists() && !file.isDirectory()) {
			System.out.println(SUCCESS + " FOUND" + RESET);

			System.out.print("Populating Graph...\r");

			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				for(String line = br.readLine(); line != null; line = br.readLine()) {
					String[] actorIds = line.trim().split(" ");

					Actor actor = actors.get(actorIds[0]);

					graph.put(actor, new ArrayList<>());

					for(int i = 1; i < actorIds.length; i++)
						graph.get(actor).add(actors.get(actorIds[i]));

					stdout.write(String.format("Populating Graph... (%s%6d%s/%s%d%s)\r", NUMBERS, graph.size(), RESET, NUMBERS, actors.size(), RESET).getBytes());
				}
			} catch(Exception e) {}

			System.out.print("Populating Graph..." + SUCCESS + " DONE" + RESET + "           \n");

			return true;
		} else {
			System.out.println(FAILURE + " NOT FOUND" + RESET);

			return false;
		}
	}

	// Generates our Graph from scratch
	private static void buildGraph() throws Exception {
		System.out.print("Generating Graph...\r");

		// Since we are planning to insert on multiple threads
		// We need to use a thread-safe implementation
		// To prevent race conditions
		Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);

		// This algorithm is O(M * V^2) (I think)... 💀
		// Using parallelization makes this slightly more bearable
		actors.values().parallelStream().forEach(actor -> {
			// Same deal here to prevent race conditions
			syncGraph.put(actor, Collections.synchronizedList(new ArrayList<>()));

			// Iterate over every actor
			for(Actor innerActor : actors.values()) {

				// Skip if the actors are the same
				if(actor == innerActor)
					continue;

				Set<Movie> commonMovies = findCommonMovies(actor, innerActor);

				// We insert an actor as a neighbour for every movie the actors have in common
				// NOTE: We end up with an adjacency list with duplicate values
				// We do this to represent multiple edges between the same actors
				commonMovies.forEach(movie -> syncGraph.get(actor).add(innerActor));
			}

			try {
				stdout.write(String.format("Generating Graph... (%s%6d%s/%s%d%s)\r", NUMBERS, graph.size(), RESET, NUMBERS, actors.size(), RESET).getBytes());
			} catch(Exception e) {}
		});

		System.out.print("Generating Graph..." + SUCCESS + " DONE" + RESET + "           \n");
	}

	// Generate a cache file for our Graph
	// Sparing us from having to generate the Graph every runtime
	private static void generateCacheFile(String filepath) throws Exception {
		System.out.print("Generating " + filepath);

		BufferedWriter fw = new BufferedWriter(new FileWriter(filepath));
		graph.forEach((node, neighbours) -> {
			try {
				fw.write(node.getId());

				for(Actor actor : neighbours)
					fw.write(" " + actor.getId());

				fw.newLine();
			} catch(Exception e) {}
		});

		fw.close();

		System.out.println(SUCCESS + " DONE" + RESET);
	}
}
