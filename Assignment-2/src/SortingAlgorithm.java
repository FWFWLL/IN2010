import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SortingAlgorithm {
	protected static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	// Write an array of integers into a file
	public void writeToFile(int[] array, String fileName) {
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
