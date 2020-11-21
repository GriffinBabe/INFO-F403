import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Main class. Will initialize a stream to the source file and initialize the {@link LexicalAnalyzer} at construction.
 * Continues by calling a parser.
 *
 */
class Main {

	/**
	 * Entry point. Checks if a source file is specified in the program arguments, then initializes a {@link Scanner}
	 * objects. Finally it calls the {@link Scanner#printTable()} function.
	 * @param args must contain a path to the scanned source file.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java -jar part1.jar <source file>");
			System.exit(1);
		}
		Scanner scanner = new Scanner(args[1]);
		scanner.printTable();
	}

}