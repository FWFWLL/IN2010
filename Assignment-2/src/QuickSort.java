public final class QuickSort extends SortingAlgorithm {
	private static int partition(int[] array, int low, int high) {
		int pivot = array[high];
		int i = low - 1;

		for(int j = low; j < high; ++j)
			if(array[j] <= pivot)
				swap(array, ++i, j);

		swap(array, ++i, high);

		return i;
	}

	private static void quickSort(int[] array, int low, int high) {
		if(low < high) {
			int pivot = partition(array, low, high);
			quickSort(array, low, pivot - 1);
			quickSort(array, pivot, high);
		}
	}

	public static void sort(int[] array) {
		quickSort(array, 0, array.length - 1);
	}
}
