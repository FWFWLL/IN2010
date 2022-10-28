public final class BubbleSort extends SortingAlgorithm {
	public void sort(int[] array) {
		for(int i = 0; i < array.length - 1; i++)
			for(int j = 0; j < array.length - i - 1; j++)
				if(compare(array[j] > array[j + 1]))
					swap(array, j, j + 1);
	}

	public void sortToFile(int[] array, String inputFileName) {
		long time = sortTimed(array);

		System.out.println("BubbleSort:");
		System.out.println("\tComparisons: " + comparisons);
		System.out.println("\tSwaps: " + swaps);
		System.out.println("\tTime: " + time + "ms");

		writeToFile(array, inputFileName + "_bubble.out");
	}
}
