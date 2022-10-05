public final class MergeSort extends SortingAlgorithm {
	// Merge two sub-arrays
	private static void merge(int[] array, int[] left, int[] right) {
		// Store current indexes of our arrays
		int i = 0;
		int j = 0;
		int k = 0;

		while(i < left.length && j < right.length) {
			if(left[i] <= right[j])
				array[k++] = left[i++];
			else
				array[k++] = right[j++];
		}

		while(i < left.length)
			array[k++] = left[i++];

		while(j < right.length)
			array[k++] = right[j++];
	}

	// Divide the array into two sub-arrays, sort them, then merge the arrays
	private static void mergeSort(int[] array) {
		int length = array.length;

		// Base case
		if(length < 2)
			return;

		// Find index of the point the array is divided
		int middle = length / 2;

		// Initialize two sub-arrays
		int[] left = new int[middle];
		int[] right = new int[length - middle];

		// Copy content of array into sub-arrays
		for(int i = 0; i < middle; i++)
			left[i] = array[i];
		for(int i = middle; i < length; i++)
			right[i - middle] = array[i];

		// Recursively do the same to the sub-arrays
		mergeSort(left);
		mergeSort(right);

		// Merge the sorted sub-arrays
		merge(array, left, right);
	}

	public void sort(int[] array) {
		mergeSort(array);
	}

	public void sortToFile(int[] array, String inputFileName) {
		sort(array);
		writeToFile(array, inputFileName + "_merge.out");
	}
}
