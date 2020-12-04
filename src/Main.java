import parser.ParseTree;
import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;
import util.CommandLineParser;
import util.LatexWriter;

/**
 * Main class. Will start by parsing the command line arguments with {@link CommandLineParser},
 * then initialize a stream to the source file and start scanning with a {@link Scanner} object.
 * Continues by initializing a {@link Parser} and starts parsing the source file.
 *
 * If option "-v" is specified in the arguments, the {@link Parser#VERBOSE} will be set to true, giving
 * a much more verbose and detailed output.
 *
 * If option "-wt [output.tex]" is specified, the {@link parser.ParseTree} built by the {@link Parser} will
 * be saved in a LaTeX format to the specified output file with the {@link util.LatexWriter} class.
 *
 */
class Main {

	/**
	 * Program entry point. See {@link Main}
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
		//Temp
		ParseTree parsedTree = parser.getTree();
		parsedTree.trimTree();
		parsedTree.cleanTree();
		LatexWriter writer = new LatexWriter("output_AST.tex");
		writer.write(parsedTree.toLaTeX());
		//!\
	}

}