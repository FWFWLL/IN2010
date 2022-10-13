public final class InsertionSort extends SortingAlgorithm {
	public void sort(int[] array) {
		for(int step = 1; step < array.length; step++) {
			int key = array[step];
			int j  = step - 1;

			// NOTE: Logical AND goes left -> right in Java
			while(j >= 0 && compare(key < array[j]))
				insert(array, ++j, j--);

			insertInto(array, ++j, key);
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
