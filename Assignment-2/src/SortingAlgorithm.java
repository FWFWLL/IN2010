import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SortingAlgorithm {
	protected int comparisons = 0;
	protected int swaps = 0;
	protected long time = 0;

	protected boolean compare(boolean expr) {
		comparisons++;

		return expr;
	}

	protected void swap(int[] array, int a, int b) {
		int temp = array[a];
		array[a] = array[b];
		array[b] = temp;

		swaps++;
	}

	protected void insert(int[] array, int a, int b) {
		array[a] = array[b];

		swaps++;
	}

	protected void insertInto(int[] array, int a, int b) {
		array[a] = b;

		swaps++;
	}

	// Write an array of integers into a file
	public void writeToFile(int[] array, String fileName) {
		if(array.length == 0)
			return;

		try(BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileName))) {
			// Write every number except the last
			for(int i = 0; i < array.length - 1; i++) {
				fileWriter.write(array[i] + "");
				fileWriter.newLine();
			}

			// Write the last without a newline
			fileWriter.write(array[array.length - 1] + "");
		} catch(IOException e) {}
	}

	public abstract void sort(int[] array);
	public abstract void sortToFile(int[] array, String inputFileName);
}
