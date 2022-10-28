import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Exercise1 extends Exercise {
	private final static String CACHE_FILEPATH = "cache.ffl";
	private final static String MOVIES_FILEPATH = "tsv/movies.tsv";
	private final static String ACTORS_FILEPATH = "tsv/actors.tsv";

	public static void run() throws Exception {
		readMoviesTSV(MOVIES_FILEPATH);
		readActorsTSV(ACTORS_FILEPATH);

		if(!populateGraph()) {
			buildGraph();
			generateCacheFile(CACHE_FILEPATH);
		}

		int numNodes = graph.size();
		int numEdges = 0;

		for(List<Actor> edges : graph.values())
			numEdges += edges.size();

		numEdges /= 2;

		System.out.println("\nExercise " + NUMBERS + "1" + RESET + ":\n");
		System.out.println("Nodes: " + NUMBERS + numNodes + RESET);
		System.out.println("Edges: " + NUMBERS + numEdges + RESET);
	}

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
	
	// If cache file is exists, populate graph based on cache file content
	// Otherwise generate the Graph using our algorithm
	private static boolean populateGraph() {
		OutputStream stdout = new BufferedOutputStream(System.out);

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

	// Generates our Graph
	private static void buildGraph() throws Exception {
		OutputStream stdout = new BufferedOutputStream(System.out);

		System.out.print("Generating Graph...\r");

		Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);

		// This algorithm is O(k * n^2)... 💀
		actors.entrySet().parallelStream().forEach(entry -> {
			String id = entry.getKey();
			Actor actor = entry.getValue();

			syncGraph.put(actor, Collections.synchronizedList(new ArrayList<>()));

			actors.forEach((innerId, innerActor) -> {
				if(!id.equals(innerId)) {
					Set<Movie> commonMovies = findCommonMovies(actor, innerActor);

					commonMovies.forEach((movie) -> syncGraph.get(actor).add(innerActor));
				}
			});

			try {
				stdout.write(String.format("Generating Graph... (%s%6d%s/%s%d%s)\r", NUMBERS, graph.size(), RESET, NUMBERS, actors.size(), RESET).getBytes());
			} catch(Exception e) {}
		});

		System.out.print("Generating Graph..." + SUCCESS + " DONE" + RESET + "           \n");
	}

	// Generate a cache file of our Graph
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
