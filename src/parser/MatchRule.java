package parser;

/**
 * Matches the top {@link Variable} of the stack with the first {@link Variable} on the list.
 */
public final class MatchRule extends Rule {

    /**
     * See {@link Rule#action(StackWrapper, SymbolQueue, ParseTree)}.
     *
     * Checks if the {@link Variable} at the top of the stack is the same as the {@link Variable} at the lookahead.
     * If the {@link Variable} matches, then removes the top variable of the {@link StackWrapper} and the {@link SymbolQueue}.
     * If the {@link Variable} doesn't match, then throws a {@link SyntaxException}.
     */
    @Override
    public void action(StackWrapper stack, SymbolQueue list, ParseTree tree) throws SyntaxException {
        Variable stackVar = stack.remVar();
        Variable inputVar = list.remVar();
        // the stackVar is normally the same as in the parse tree, we can therefore assign the value
        if (inputVar.getType() == Variable.Type.VARNAME ||
            inputVar.getType() == Variable.Type.NUMBER) {
            stackVar.setValue(inputVar.getValue());
        }
        if (!stackVar.getType().equals(inputVar.getType())) {
            throw new SyntaxException("parser.Variable with type: "+stackVar.getType().toString()
                    +" doesn't watch variable of type: "+inputVar.getType().toString());
        }
    }

    /**
     * See {@link Rule#toString()}
     * @return
     */
    @Override
    public String toString() {
        if (Parser.VERBOSE) {
            return "Match Rule.\n";
        }
        else {
            return "";
        }
    }

}
