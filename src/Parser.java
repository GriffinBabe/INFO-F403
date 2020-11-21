import java.util.ArrayList;




public class Parser {
	private StackWrapper variableStack;
	private ActionTable acTab;
	private SymbolQueue symQue;
	public Parser(ArrayList<Symbol> arr){
		variableStack = new StackWrapper();
		acTab = new ActionTable();
		symQue = new SymbolQueue(arr);
	}

	public void parseSequence() throws SyntaxException {
		this.variableStack.pushVar(new Variable(Variable.Type.V_PROGRAM)); // push initial state on the stack
		while (!(symQue.read().getType().equals(LexicalUnit.EOS))){
			acTab.getRule(variableStack.readLast(),new Variable(symQue.read())).action(variableStack,symQue);
		}
	}
}
