import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public final class Main {
	private static final int MAX_N_FOR_SLOW_ALGORITHMS = 65535;
	private static final int TIME_LIMIT = 7000; // ms

	public static void main(String[] args) throws IOException {
		if(args.length < 2) {
			System.err.println("Missing arguments");
			return;
		}

		String filepath = args[0];

		int[] array = Files.readAllLines(Paths.get(filepath))
			.stream()
			.mapToInt(Integer::parseInt)
			.toArray();

		SortingAlgorithm[] algorithms = {
			new BubbleSort(),    // O(n^2)
			new InsertionSort(), // O(n^2)
			new MergeSort(),     // O(n * log(n))
			new QuickSort(),     // O(n^2)
		};

		// MergeSort is the only one that can actually sort arrays greater than 100000
		// QuickSort causes a stack overflow
		if(args[1].equals("1"))
			if(array.length > MAX_N_FOR_SLOW_ALGORITHMS)
				algorithms[2].sortToFile(array.clone(), filepath);
			else
				for(SortingAlgorithm algo : algorithms)
					algo.sortToFile(array.clone(), filepath);

		if(!args[1].equals("2"))
			return;

		File csvOutputFile = new File(filepath + "_results.csv");
		try(PrintWriter pw = new PrintWriter(csvOutputFile)) {
			// First line
			pw.println("n,bubble_cmp,bubble_swaps,bubble_time,insertion_cmp,insertion_swaps,insertion_time,merge_cmp,merge_swaps,merge_time,quick_cmp,quick_swaps,quick_time,");

			for(int n = 0; n < array.length; n++) {
				pw.print(n + ",");

				for(SortingAlgorithm algo : algorithms) {
					// Measure time of completion of the algorithm
					long time = 0;

					// Start sorting as long as algorithm isn't timed out
					if(!algo.timedOut)
						time = algo.sortTimed(Arrays.copyOfRange(array, 0, n));

					// Tell algorithm to chill if time exceeded
					if(time > TIME_LIMIT)
						algo.timedOut = true;

					// Print out statistics of algorithm
					if(algo.timedOut)
						pw.print(" , , ,");
					else
						pw.print(algo.comparisons + "," + algo.swaps + "," + time + ",");
				}

				pw.println();
			}
		}
	}
}
