/**
 * Matches the top {@link Variable} of the stack with the first {@link Variable} on the list.
 */
public final class MatchRule extends Rule {

    @Override
    public void action(StackWrapper stack, SymbolQueue list) throws SyntaxException {
        Variable stackVar = stack.remVar();
        Variable inputVar = list.remVar();
        if (!stackVar.getType().equals(inputVar.getType())) {
            throw new SyntaxException("Variable with type: "+stackVar.getType().toString()
                    +" doesn't watch variable of type: "+inputVar.getType().toString());
        }
    }

}
