public final class QuickSort extends SortingAlgorithm {
	// Find position of partition
	private static int partition(int[] array, int low, int high) {
		int pivot = array[high];
		int i = low - 1;

		// Compare and swap all integers with pivot
		for(int j = low; j < high; ++j)
			if(array[j] <= pivot)
				swap(array, ++i, j);

		// Swap pivot with a greater value
		swap(array, ++i, high);

		// Return position from where the partition is done
		return i;
	}

	private static void quickSort(int[] array, int low, int high) {
		if(low < high) {
			// Integers smaller than pivot are on the left
			// Integers greater than pivot are on the right
			int pivot = partition(array, low, high);

			// Recursive left side
			quickSort(array, low, pivot - 1);

			// Recursive right side
			quickSort(array, pivot, high);
		}
	}

	public void sort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}

	public void sortToFile(int[] array, String inputFileName) {
		sort(array);
		writeToFile(array, inputFileName + "_quick.out");
	}
}
