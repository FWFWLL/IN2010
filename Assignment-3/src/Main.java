// This whole program is like O(fuck) or smth 💀
public class Main {
	private final static String HIDE_CURSOR = "\u001B[?25l";
	private final static String SHOW_CURSOR = "\u001B[?25h";

	public static void main(String[] args) throws Exception {
		System.out.print(HIDE_CURSOR + "\r");

		Exercise1.run();
		// Exercise2.run();
		// Exercise3.run();
		Exercise4.run();

		System.out.print(SHOW_CURSOR + "\r");
	}
}
