import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class Main {
	private final static String RESET = "\033[0m";
	private final static String SUCCESS = "\033[0;92m";
	private final static String FAILURE = "\033[0;31m";
	private final static String NUMBERS = "\033[0;33m";

	private final static String HIDE_CURSOR = "\u001B[?25l";
	private final static String SHOW_CURSOR = "\u001B[?25h";

	private final static String CACHE_FILEPATH = "cache.ffl";
	private final static String MOVIES_FILEPATH = "tsv/movies.tsv";
	private final static String ACTORS_FILEPATH = "tsv/actors.tsv";

	private static Map<Actor, List<Actor>> graph = new HashMap<Actor, List<Actor>>();
	private static Map<String, Movie> movies = new HashMap<String, Movie>();
	private static Map<String, Actor> actors = new HashMap<String, Actor>();

	public static void main(String[] args) throws Exception {
		Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);

		readMoviesTSV(MOVIES_FILEPATH);
		readActorsTSV(ACTORS_FILEPATH);

		buildGraph(syncGraph);

		exercise1(graph);
		exercise2(graph);
		exercise3(graph);
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
	
	// Generates our Graph
	// If cache file is found, populate graph based on cache file content
	// Otherwise generate the Graph using our algorithm
	private static void buildGraph(Map<Actor, List<Actor>> syncGraph) throws Exception {	
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

					graph.put(actor, new ArrayList<Actor>());

					for(int i = 1; i < actorIds.length; i++)
						graph.get(actor).add(actors.get(actorIds[i]));

					stdout.write(String.format("Generating Graph... (%s%6d%s/%s%d%s) \r", NUMBERS, graph.size(), RESET, NUMBERS, actors.size(), RESET).getBytes());
				}
			} catch(Exception e) {}

			System.out.print("Populating Graph..." + SUCCESS + " DONE" + RESET + "           \n" + SHOW_CURSOR);
		} else {
			System.out.println(FAILURE + " NOT FOUND" + RESET);

			System.out.print("Generating Graph...\r" + HIDE_CURSOR);

			// This algorithm is O(k * n^2)... 💀
			actors.entrySet().parallelStream().forEach(entry -> {
				String id = entry.getKey();
				Actor actor = entry.getValue();

				syncGraph.put(actor, Collections.synchronizedList(new ArrayList<Actor>()));

				actor.getMovieAppearances().forEach((movieId) -> {
					actors.forEach((innerId, innerActor) -> {
						if(!id.equals(innerId) && movies.containsKey(movieId) && innerActor.getMovieAppearances().contains(movieId)) {
							syncGraph.get(actor).add(innerActor);
						}
					});
				});

				try {
					stdout.write(String.format("Generating Graph... (%s%6d%s/%s%d%s) \r", NUMBERS, graph.size(), RESET, NUMBERS, actors.size(), RESET).getBytes());
				} catch(Exception e) {}
			});

			System.out.print("Generating Graph..." + SUCCESS + " DONE" + RESET + "           \n" + SHOW_CURSOR);

			generateCacheFile(CACHE_FILEPATH);
		}
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

	// Exercise 1 driver code
	private static void exercise1(Map<Actor, List<Actor>> graph) {
		int numNodes = graph.size();
		int numEdges = 0;

		for(List<Actor> edges : graph.values())
			numEdges += edges.size();

		numEdges /= 2;

		System.out.println("\nExercise " + NUMBERS + "1" + RESET + ":\n");
		System.out.println("Nodes: " + NUMBERS + numNodes + RESET);
		System.out.println("Edges: " + NUMBERS + numEdges + RESET);
	}
	
	// Prints the shortest path between two actors
	// While printing a Movie that connects them both
	private static void printShortestPath(Map<Actor, List<Actor>> graph, String srcActorId, String dstActorId) {
		Actor srcActor = actors.get(srcActorId);
		Actor dstActor = actors.get(dstActorId);

		Map<Actor, Actor> predecessors = new HashMap<Actor, Actor>();

		if(!BFS(graph, srcActor, dstActor, predecessors)) {
			System.out.println("Given source and destination are not connected");

			return;
		}

		Stack<Actor> path = new Stack<Actor>();
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

			Set<Movie> prevMovies = prev.getMovieAppearances()
				.stream()
				.map((movieId) -> movies.get(movieId))
				.collect(Collectors.toCollection(HashSet::new));

			Set<Movie> currMovies = curr.getMovieAppearances()
				.stream()
				.map((movieId) -> movies.get(movieId))
				.collect(Collectors.toCollection(HashSet::new));

			Movie commonMovie = findCommonMovies(prevMovies, currMovies)
				.iterator()
				.next();

			System.out.printf("====[ %s (%s%.1f%s) ]===> %s\n", commonMovie.getTitle(), NUMBERS, commonMovie.getRating(), RESET, curr.getName());

			prev = curr;
		}
	}

	// Modified version of Breath-First-Search that stores predecessors
	private static boolean BFS(Map<Actor, List<Actor>> graph, Actor srcActor, Actor dstActor, Map<Actor, Actor> predecessors) {
		Queue<Actor> queue = new LinkedList<Actor>();
		Map<Actor, Boolean> visited = new HashMap<Actor, Boolean>();

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

	// Helper function for finding common movies in two sets
	private static Set<Movie> findCommonMovies(Set<Movie> setA, Set<Movie> setB) {
		Set<Movie> results = new HashSet<Movie>();

		for(Movie movie : setA) {
			if(setB.contains(movie)) {
				results.add(movie);
			}
		}

		return results;
	}

	// Exercise 2 driver code
	private static void exercise2(Map<Actor, List<Actor>> graph) {
		System.out.println("\nExercise " + NUMBERS + "2" + RESET + ":");
		printShortestPath(graph, "nm2255973", "nm0000460");
		printShortestPath(graph, "nm0424060", "nm0000243");
		printShortestPath(graph, "nm4689420", "nm0000365");
		printShortestPath(graph, "nm0000288", "nm0001401");
		printShortestPath(graph, "nm0031483", "nm0931324");
	}

	// Exercise 3 driver code
	private static void exercise3(Map<Actor, List<Actor>> graph) {
		System.out.println("\nExercise " + NUMBERS + "3" + RESET + ":\n");
	}
}
