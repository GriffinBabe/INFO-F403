import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;

import java.util.stream.StreamSupport;

/**
 * Main class. Will initialize a stream to the source file and initialize the {@link Scanner} at construction.
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
		Scanner scanner = new Scanner(args[0]);
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());

		try {
			parser.parseSequence();
		}
		catch (SyntaxException e) {
			System.out.println("Syntax error detected: " + e.getMessage());
			System.exit(1);
		}
	}

}