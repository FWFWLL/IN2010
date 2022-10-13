import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SortingAlgorithm {
	protected long comparisons = 0;
	protected long swaps = 0;

	protected boolean timedOut = false;

	protected boolean compare(boolean expr) {
		comparisons++;

		return expr;
	}

	protected void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;

		swaps++;
	}

	protected void insert(int[] array, int i, int j) {
		array[i] = array[j];

		swaps++;
	}

	protected void insertInto(int[] array, int insertionIndex, int elementToInsert) {
		array[insertionIndex] = elementToInsert;

		swaps++;
	}

	// Write an array of integers into a file
	public void writeToFile(int[] array, String filepath) {
		// Don't do anything if array is empty
		if(array.length == 0)
			return;

		// Use try-with-resources to write array into a file
		try(BufferedWriter fw = new BufferedWriter(new FileWriter(filepath))) {
			// Write every number except the last with a newline
			for(int i = 0; i < array.length - 1; i++)
				fw.write(array[i] + "\n");

			// Write the last line without a newline
			fw.write(array[array.length - 1] + "");
		} catch(IOException e) {
			System.err.println("Could not write to file");
		}
	}

	public abstract void sort(int[] array);

	public long sortTimed(int[] array) {
		long t = System.nanoTime();
		sort(array);
		long time = (System.nanoTime() - t) / 1000;

		return time;
	}

	public abstract void sortToFile(int[] array, String inputFileName);
}
