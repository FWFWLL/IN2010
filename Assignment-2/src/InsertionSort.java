public final class InsertionSort extends SortingAlgorithm {
	public void sort(int[] array) {
		int size = array.length;

		for(int step = 1; step < size; step++) {
			int key = array[step];
			int i = step - 1;

			// NOTE: Logical AND goes left -> right in Java
			while(i >= 0 && compare(key < array[i]))
				array[i + 1] = array[i--];

			array[i + 1] = key;

			swaps++;
		}
	}

	public void sortToFile(int[] array, String inputFileName) {
		long time = sortTimed(array);

		System.out.println("InsertionSort:");
		System.out.println("\tComparisons: " + comparisons);
		System.out.println("\tSwaps: " + swaps);
		System.out.println("\tTime: " + time + "ms");

		writeToFile(array, inputFileName + "_insertion.out");
	}
}
