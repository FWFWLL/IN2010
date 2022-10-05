import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Main {
	public static void main(String[] args) throws IOException {
		int[] intArray = Files.readAllLines(Paths.get(args[0]))
			.stream()
			.mapToInt(i -> Integer.parseInt(i))
			.toArray();

		SortingAlgorithm[] algorithms = {
			new BubbleSort(),    // O(n^2)
			new InsertionSort(), // O(n^2)
			new MergeSort(),     // O(n * log(n))
			new QuickSort(),     // O(n^2)
		};

		for(SortingAlgorithm algorithm : algorithms)
			algorithm.sortToFile(intArray.clone(), args[0]);
	}

	// Return whether an array is sorted
	public static boolean isSorted(int[] array) {
		for(int i = 1; i < array.length; i++)
			if(array[i - 1] > array[i])
				return false;
		return true;
	}
}
