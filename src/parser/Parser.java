package parser;

import scanner.LexicalUnit;
import scanner.Symbol;
import util.LatexWriter;

import java.util.ArrayList;

/**
 * Parser Class
 */
public class Parser {

	public static boolean VERBOSE = false;

	private final StackWrapper variableStack;
	private final ActionTable actionTable;
	private final SymbolQueue symbolQueue;

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
	 * Public method starting the parsing
	 * @throws SyntaxException, if a syntax problem has been detected by the parser.
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
				System.out.println("Fetched rule: " + rule.toString());
				System.out.println("----------------------------------\n");
			}
			else {
				System.out.print(rule.toString());
			}

			// calls the rule actions (might perform actions on the stack and symbol queue).
			rule.action(variableStack, symbolQueue, tree);
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
		LatexWriter writer = new LatexWriter(path);
		writer.write(tree.toLaTeX());
	}
}
