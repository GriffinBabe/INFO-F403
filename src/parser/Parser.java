package parser;

import scanner.LexicalUnit;
import scanner.Symbol;
import util.OutputFileWriter;

import java.util.ArrayList;

/**
 * Parser Class
 */
public class Parser {

	/**
	 * Set to true if the user wishes a more verbose std output.
	 */
	public static boolean VERBOSE = false;

	/**
	 * Reference to the PDA's stack.
	 */
	private final StackWrapper variableStack;

	/**
	 * Reference to the action Table. Used to fetch the rules that define the behaviour of the parser.
	 */
	private final ActionTable actionTable;

	/**
	 * Reference to the FIFO symbol queue. Contains all the symbols obtained by the {@link scanner.Scanner}.
	 */
	private final SymbolQueue symbolQueue;

	/**
	 * Reference to the parser derivation tree. Will be built in the {@link #parseSequence()} method.
	 */
	private final ParseTree tree;

	/**
	 * Public constructor of the parser
	 * @param arr , the list of symbol generated by the scanner
	 */
	public Parser(ArrayList<Symbol> arr){
		variableStack = new StackWrapper();
		actionTable = new ActionTable();
		symbolQueue = new SymbolQueue(arr);
		tree = new ParseTree(new Variable(Variable.Type.V_PROGRAM));
	}

	/**
	 * Public method starting the parsing.
	 *
	 * Until the {@link LexicalUnit#EOS} is not reached:
	 *
	 * {@link Parser} =fetches in=> {@link ActionTable}
	 *   {@link Rule} <==returns=== {@link ActionTable}
	 * calls {@link Rule#action(StackWrapper, SymbolQueue, ParseTree)}.
	 *
	 * @throw SyntaxException if a syntax problem has been detected by the parser.
	 */
	public void parseSequence() throws SyntaxException {
		// Pushes the initial stack variable.
		variableStack.pushVar(new Variable(Variable.Type.V_PROGRAM)); // push initial state on the stack

		// While the symbol queue isn't finished.
		while (!(symbolQueue.read().getType().equals(LexicalUnit.EOS))){
			// reads the stack and queue first variables
			Variable firstQueueVar = new Variable(symbolQueue.read());
			Variable topStackVar = variableStack.readTop();

			// Fetches the corresponding rule in the action table.
			Rule rule = actionTable.getRule(topStackVar, firstQueueVar);

			if (Parser.VERBOSE) {
				System.out.println("----------------------------------");
				System.out.println("Look-ahead = "+ firstQueueVar.getType());
				System.out.println("Top stack = " + topStackVar.getType());
				System.out.print("Fetched rule: " + rule.toString());
			}

			// calls the rule actions (might perform actions on the stack, the symbol queue or the derivation tree).
			rule.action(variableStack, symbolQueue, tree);

			if  (Parser.VERBOSE) {
				System.out.println("----------------------------------\n");
			}
		}

		// checks if the stack is not empty.
		if (!variableStack.isEmpty()) {
			throw new SyntaxException("Stack is non-empty at the end of the parsing.");
		}

		// at this point everything passed
		if (Parser.VERBOSE) {
			System.out.println("[Program parsed successfully.]\n");
		}
	}

	/**
	 * Writes the LaTeX source code of the parsed {@link ParseTree} at the given path.
	 * @param path, the path where to write the file.
	 */
	public void saveTree(String path) {
		OutputFileWriter writer = new OutputFileWriter(path);
		writer.write(tree.toLaTeX());
	}

	/**
	 * @return the starting node of the parse tree
	 */
	public ParseTree getTree(){
		return this.tree;
	}
}
