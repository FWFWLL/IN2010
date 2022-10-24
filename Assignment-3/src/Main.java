import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
		OutputStream stdout = new BufferedOutputStream(System.out);

		Map<Actor, List<Actor>> graph = new HashMap<Actor, List<Actor>>();
		Map<Actor, List<Actor>> syncGraph = Collections.synchronizedMap(graph);

		System.out.print("Reading movies.tsv...");

		Map<String, Movie> movies = new HashMap<String, Movie>();
		try(BufferedReader br = new BufferedReader(new FileReader("tsv/movies.tsv"))) {
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] fields = line.trim().split("\t");

				movies.put(fields[0], new Movie(fields));
			}
		}

		System.out.println(" Finished");

		System.out.print("Reading actors.tsv...");

		Map<String, Actor> actors = new HashMap<String, Actor>();
		try(BufferedReader br = new BufferedReader(new FileReader("tsv/actors.tsv"))) {
			for(String line = br.readLine(); line != null; line = br.readLine()) {
				String[] fields = line.trim().split("\t");
				Actor actor = new Actor(fields[0], fields[1]);
				
				for(int i = 2; i < fields.length; i++)
					actor.addMovie(fields[i]);

				actors.put(fields[0], actor);
			}
		}

		System.out.println(" Finished");
			
		System.out.print("cache.ffl...");

		File file = new File("cache.ffl");
		if(file.exists() && !file.isDirectory()) {
			System.out.println(" Found");

			System.out.print("Populating Graph...");

			try(BufferedReader br = new BufferedReader(new FileReader(file))) {
				for(String line = br.readLine(); line != null; line = br.readLine()) {
					String[] actorIds = line.trim().split(" ");

					Actor actor = actors.get(actorIds[0]);

					graph.put(actor, new ArrayList<Actor>());

					for(int i = 1; i < actorIds.length; i++)
						graph.get(actor).add(actors.get(actorIds[i]));
				}
			} catch(Exception e) {}

			System.out.println(" Finished");
		} else {
			System.out.println(" Not Found");

			// System.out.print("Generating Graph...");

			actors.entrySet().parallelStream().forEach(entry -> {
				String id = entry.getKey();
				Actor actor = entry.getValue();

				syncGraph.put(actor, Collections.synchronizedList(new ArrayList<Actor>()));

				actor.getMovieAppearances().forEach((movieId) -> {
					actors.forEach((innerId, innerActor) -> {
						if(id != innerId && movies.containsKey(movieId) && innerActor.getMovieAppearances().contains(movieId)) {
							syncGraph.get(actor).add(innerActor);
						}
					});
				});

				try {stdout.write(("(" + graph.size() + ") -> Node(" + id + ") - Edges(" + syncGraph.get(actor).size() +  ") -> " + actor.getName() +  "\n").getBytes());}
				catch(Exception e) {}
			});

			// System.out.println(" Finished");

			System.out.print("Generating cache.ffl...");

			BufferedWriter fw = new BufferedWriter(new FileWriter("cache.ffl"));
			graph.forEach((node, neighbours) -> {
				try {
					fw.write(node.getId());

					for(Actor actor : neighbours)
						fw.write(" " + actor.getId());

					fw.newLine();
				} catch(Exception e) {}
			});

			System.out.println(" Finished");

			fw.close();
		}

		// System.out.println("Finished creating Graph")

		// System.out.println("Finished creating cache.ffl");

		int numNodes = graph.size();
		int numEdges = 0;

		for(List<Actor> edges : graph.values())
			numEdges += edges.size();

		numEdges /= 2;

		System.out.println("\nExercise 1:\n");
		System.out.println("Nodes: " + numNodes);
		System.out.println("Edges: " + numEdges);
	}
}
