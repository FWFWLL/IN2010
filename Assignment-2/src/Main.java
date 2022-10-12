import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Main {
	public static void main(String[] args) throws IOException {
		int[] array = Files.readAllLines(Paths.get(args[0]))
			.stream()
			.mapToInt(i -> Integer.parseInt(i))
			.toArray();

		SortingAlgorithm[] algorithms = {
			new BubbleSort(),    // O(n^2)
			new SelectionSort(), // O(n^2)
			new InsertionSort(), // O(n^2)
			new MergeSort(),     // O(n * log(n))
			new QuickSort(),     // O(n^2)
		};

		for(SortingAlgorithm algo : algorithms)
			algo.sortToFile(array, args[0]);

		// File csvOutputFile = new File(args[0] + "_results.csv");
		// try(PrintWriter pw = new PrintWriter(csvOutputFile)) {
		// 	// First line
		// 	pw.println("n,bubble_cmp,bubble_swaps,bubble_time,insertion_cmp,insertion_swaps,insertion_time,merge_cmp,merge_swaps,merge_time,quick_cmp,quick_swaps,quick_time");

		// 	for(int n = 0; n < intArray.length; n++) {
		// 		pw.print(n + ",");

		// 		for(int i = 0; i < algorithms.length; i++) {
		// 			SortingAlgorithm algo = algorithms[i];

		// 			long t = System.nanoTime();
		// 			algo.sort(Arrays.copyOfRange(intArray, 0, n));
		// 			long time = (System.nanoTime() - t) / 1000;

		// 			if(i == algorithms.length - 1)
		// 				pw.print(algo.comparisons + "," + algo.swaps + "," + time);
		// 			else
		// 				pw.print(algo.comparisons + "," + algo.swaps + "," + time + ",");
		// 		}

		// 		pw.println();
		// 	}
		// }
	}
}
