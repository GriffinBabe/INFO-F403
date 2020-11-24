import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;
import util.CommandLineParser;

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
		CommandLineParser clp = new CommandLineParser();
		clp.parse(args);

		Parser.VERBOSE = clp.isVerbose();

		Scanner scanner = new Scanner(clp.getInputSource());
		if (Parser.VERBOSE) {
			scanner.printTable();
		}

		Parser parser = new Parser(scanner.getVariables());
		try {
			parser.parseSequence();
			if (clp.latexOutput() != null) parser.saveTree(clp.latexOutput());
		}
		catch (SyntaxException e) {
			System.err.println("Syntax error detected: " + e.getMessage());
			System.exit(1);
		}
	}

}