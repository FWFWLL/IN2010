public final class SelectionSort extends SortingAlgorithm {
	public void sort(int[] array) {
		for(int step = 0; step < array.length; step++) {
			// Index of smallest element
			int minIndex = step;

			for(int i = step + 1; i < array.length; i++) {

				// Select minimum element in each iteration
				if(compare(array[i] < array[minIndex])) {
					minIndex = i;
				}
			}
			
			// Put min at the correct position
			swap(array, step, minIndex);
		}
	}

	public void sortToFile(int[] array, String inputFileName) {
		long t = System.nanoTime();
		sort(array);
		long time = (System.nanoTime() - t) / 1000;

		System.out.println("SelectionSort:");
		System.out.println("\tComparisons: " + comparisons);
		System.out.println("\tSwaps: " + swaps);
		System.out.println("\tTime: " + time + "ms");

		writeToFile(array, inputFileName + "_selection.out");
	}
}
