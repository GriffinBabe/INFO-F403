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
	public SymbolQueue(ArrayList<Symbol> arr) {
		symbolList = arr;
	}

	/**
	 * @return Look-ahead value
	 */
	public Symbol read(){
		return symbolList.get(index);
	}

	/**
	 * Move the index to the next look-ahead value
	 */
	public void moveAhead(){
		index ++;
	}

}