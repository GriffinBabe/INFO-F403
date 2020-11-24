package parser;

import scanner.Symbol;

import java.util.ArrayList;

/**
 * {@link Symbol} FIFO queue wrapper class.
 * Represents the input sequence returned by the {@link scanner.Scanner}.
 */
public class SymbolQueue {

	/**
	 * List of all the tokens read by the scanner
	 */
	private final ArrayList<Symbol> symbolList;
	/**
	 * Index of lecture
	 */
	private int index = 0;

	private int end = 0;

	/**
	 * Public constructor
	 * @param arr List of all the tokens read by the scanner
	 */
    public SymbolQueue(ArrayList<Symbol> arr) {
        symbolList = arr;
        this.end = symbolList.size();
    }

	/**
	 * move ahead the index
	 * @return the previous look-ahead value
	 */
    public Variable remVar() {
    	if (index >= end) {
    		throw new RuntimeException("Cannot remove variable on an empty queue.");
		}
        Symbol symbol = symbolList.get(index);
        index++;
        return new Variable(symbol);
    }

	/**
	 * @return Look-ahead value
	 */
	public Symbol read(){
		if (index >= end) {
			throw new RuntimeException("Cannot read on a empty queue.");
		}
		return symbolList.get(index);
	}

}