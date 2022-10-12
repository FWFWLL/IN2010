public final class QuickSort extends SortingAlgorithm {
	// Method to find the position of the partition
	private int partition(int[] array, int low, int high) {
		// Choose the rightmost element as pivot
		int pivot = array[high];

		// Pointer for greater element
		int i = low - 1;

		// Compare and swap all elements with pivot
		for(int j = low; j < high; j++)
			if(compare(array[j] <= pivot))
				// If element is smaller than pivot, swap it with the greater element
				swap(array, ++i, j);

		// Swap pivot with the greater element
		swap(array, ++i, high);

		// Return position from where the partition is done
		return i;
	}

	// Recursive method
	private void quickSort(int[] array, int low, int high) {
		if(low < high) {
			// Elements smaller than pivot are on the left
			// Elements greater than pivot are on the right
			int pivot = partition(array, low, high);

			// Recursive call on the left side
			quickSort(array, low, pivot - 1);

			// Recursive call on the right side
			quickSort(array, pivot, high);
		}
	}
	
	// Sorting method shorthand
	public void sort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}

	// Method to sort the file and write sorted array to a file
	public void sortToFile(int[] array, String inputFileName) {
		long t = System.nanoTime();
		sort(array);
		long time = (System.nanoTime() - t) / 1000;

		System.out.println("QuickSort:");
		System.out.println("\tComparisons: " + comparisons);
		System.out.println("\tSwaps: " + swaps);
		System.out.println("\tTime: " + time + "ms");

		writeToFile(array, inputFileName + "_quick.out");
	}
}
