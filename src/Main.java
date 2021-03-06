import compiler.Compiler;
import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;
import util.CommandLineParser;
import util.OutputFileWriter;

import java.io.IOException;

/**
 * Main class. Will start by parsing the command line arguments with {@link CommandLineParser},
 * then initialize a stream to the source file and start scanning with a {@link Scanner} object.
 * Continues by initializing a {@link Parser} and starts parsing the source file.
 *
 * If option "-v" is specified in the arguments, the {@link Parser#VERBOSE} will be set to true, giving
 * a much more verbose and detailed output.
 *
 * If option "-wt [output.tex]" is specified, the {@link parser.ParseTree} built by the {@link Parser} will
 * be saved in a LaTeX format to the specified output file with the {@link OutputFileWriter} class.
 *
 * If option "-wast [output.tex]" is specified, the {@link compiler.AST} built by the {@link compiler.ASTBuilder} will
 * be saved in a LaTeX format to the specified output file with the {@link OutputFileWriter} class.
 *
 * If option "-exec" is specified, the program will execute the compiled LLVM code using a subprocess. (lli program
 * must be installed in the system).
 *
 * By default, the program will output the compiled LLVM code in the standard output stream.
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
			if (clp.getLatexOutput() != null) parser.saveTree(clp.getLatexOutput());
		}
		catch (SyntaxException e) {
			System.err.println("Syntax error detected: " + e.getMessage());
			System.exit(1);
		}

		Compiler compiler = new Compiler();
		compiler.build(parser.getTree());
		if (clp.getAstLatexOutput() != null) {
			compiler.saveTree(clp.getAstLatexOutput());
		}

		String asCode = compiler.compile();
		if (clp.isExecute()) {
			try {
				int returncode = compiler.execute();
				System.exit(returncode);
			}
			catch (IOException e) {
				System.err.println("Problem with executing the code. " +
						"Does the compiler have the permission to write temporary files here?");
				System.exit(1);
			}
			catch (InterruptedException e) {
				System.err.println("Process interrupted.");
				System.exit(1);
			}
		}

		if (clp.getOutputPath() != null) {
			compiler.saveOutput(clp.getOutputPath());
		}
		else {
			System.out.println(asCode);
		}
	}

}