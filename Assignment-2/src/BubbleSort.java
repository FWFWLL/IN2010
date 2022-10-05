public final class BubbleSort extends SortingAlgorithm {
	public void sort(int[] array) {
		for(int i = 0; i < array.length - 1; i++)
			for(int j = 0; j < array.length - 1; j++)
				if(array[j] > array[j + 1])
					swap(array, j, j + 1);
	}

	public void sortToFile(int[] array, String inputFileName) {
		sort(array);
		writeToFile(array, inputFileName + "_bubble.out");
	}
}
