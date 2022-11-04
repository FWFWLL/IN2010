// This whole program is like O(fuck) or smth 💀
public class Main {
	private final static String HIDE_CURSOR = "\u001B[?25l";
	private final static String SHOW_CURSOR = "\u001B[?25h";

	public static void main(String[] args) {
		long t = System.nanoTime();

		System.out.print(HIDE_CURSOR + "\r");

		Exercise1.run();
		Exercise2.run();
		Exercise3.run();
		Exercise4.run();

		float time = (System.nanoTime() - t) / 1_000_000_000.0f;
		System.out.printf("%s\nRuntime: %.2f sec\n", SHOW_CURSOR, time);
	}
}
