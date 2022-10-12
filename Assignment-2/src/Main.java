import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public final class Main {
	public static void main(String[] args) throws IOException {
		if(args.length < 2) {
			System.err.println("Missing arguments");
			return;
		}

		int[] array = Files.readAllLines(Paths.get(args[0]))
			.stream()
			.mapToInt(i -> Integer.parseInt(i))
			.toArray();

		SortingAlgorithm[] algorithms = {
			new BubbleSort(),    // O(n^2)
			new InsertionSort(), // O(n^2)
			new MergeSort(),     // O(n * log(n))
			new QuickSort(),     // O(n^2)
		};

		if(args[1].equals("1"))
			for(SortingAlgorithm algo : algorithms)
				algo.sortToFile(array, args[0]);

		if(!args[1].equals("2"))
			return;

		File csvOutputFile = new File(args[0] + "_results.csv");
		try(PrintWriter pw = new PrintWriter(csvOutputFile)) {
			// First line
			pw.println("n,bubble_cmp,bubble_swaps,bubble_time,insertion_cmp,insertion_swaps,insertion_time,merge_cmp,merge_swaps,merge_time,quick_cmp,quick_swaps,quick_time,");

			for(int n = 0; n < array.length; n++) {
				pw.print(n + ",");

				for(SortingAlgorithm algo : algorithms) {
					// Measure time of completion of the algorithm
					long t = System.nanoTime();
					algo.sort(Arrays.copyOfRange(array, 0, n));
					long time = (System.nanoTime() - t) / 1000;

					// Print out statistics of algorithm
					pw.print(algo.comparisons + "," + algo.swaps + "," + time + ",");
				}

				pw.println();
			}
		}
	}
}
