

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Symbol LIFO queue.
 */
public class SymbolQueue {

	/**
	 * List of all the tokens read by the scanner
	 */
	private ArrayList<Symbol> symbolList;
	/**
	 * Index of lecture
	 */
	private int index = 0;

	/**
	 * Public constructor
	 * @param arr List of all the tokens read by the scanner
	 */
    private int end = 0;

    public SymbolQueue(ArrayList<Symbol> arr) {
        symbolList = arr;
        this.end = symbolList.size();
    }

    public Variable remVar() {
        Symbol symbol = symbolList.get(index);
        index++;
        return new Variable(symbol);
    }

	/**
	 * @return Look-ahead value
	 */
	public Symbol read(){
		return symbolList.get(index);
	}

}