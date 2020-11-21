import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Symbol LIFO queue.
 */
public class SymbolQueue {

	private ArrayList<Symbol> symbolList;
	private int index = 0;
	public SymbolQueue(ArrayList<Symbol> arr) {
		symbolList = arr;
	}

	public Symbol read(){
		return this.symbolList.get(index);
	}
	public void moveAhead(){
		index ++;
	}

}