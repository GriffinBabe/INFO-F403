import java.util.ArrayList;




public class Parser {
	private ArrayList<Symbol> symbolList;
	private int lookAheadIndex = 0;
	private StackWrapper variableStack;
	private ActionTable acTab;
	public Parser(ArrayList<Symbol> arr){
		symbolList = arr;
		variableStack = new StackWrapper();
		acTab = new ActionTable();
	}
	public void parseSequence() throws SyntaxException {
		variableStack.pushVar(new Variable(Variable.Type.V_PROGRAM)); // push initial state on the stack
		while (!(symbolList.get(lookAheadIndex).getType().equals(LexicalUnit.EOS))){
			acTab.getRule(variableStack.readLast(),new Variable(symbolList.get(lookAheadIndex))).action(variableStack,lookAheadIndex);
		}
	}
}
