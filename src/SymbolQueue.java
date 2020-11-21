import org.graalvm.compiler.api.replacements.Snippet;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Symbol LIFO queue.
 */
public class SymbolQueue {

    private ArrayList<Symbol> symbolList;
    private int index = 0;
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

}
