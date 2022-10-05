public final class InsertionSort extends SortingAlgorithm {
	public void sort(int[] array) {
		for(int step = 1; step < array.length; step++) {
			int key = array[step];
			int j  = step - 1;

			// NOTE: Logical AND goes left -> right in Java
			while(j >= 0 && key < array[j]) {
				array[j + 1] = array[j];

				--j;
			}

			array[j + 1] = key;
		}
	}

	public void sortToFile(int[] array, String inputFileName) {
		sort(array);
		writeToFile(array, inputFileName + "_insertion.out");
	}
}
