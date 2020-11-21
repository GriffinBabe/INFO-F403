import java.util.ArrayList;

public class Parser {
	private ArrayList<Symbol> symbolList;
	private int lookAheadIndex = 0;
	public Parser(ArrayList<Symbol> arr){
		symbolList = arr;
	}
}
