import java.util.ArrayList;
import java.util.Collections;

/**
 * Extracts the top {@link Variable} of the stack and pushes new {@link Variable} on the stack.
 */
public final class DerivationRule extends Rule {

    /**
     * The type of the expected top variable. This is used for an additional check.
     */
    private Variable.Type expectedType;

    /**
     * Replaces the variable
     */
    private ArrayList<Variable> replacingVariables;

    public DerivationRule(Variable.Type expectedType) {
        this.expectedType = expectedType;
        this.replacingVariables = new ArrayList<>();
    }

    /**
     * Adds some replacing variables in the rule's {@link #replacingVariables}.
     * @param vars, the new variables to add.
     */
    public void addReplacingVariable(Variable... vars) {
        Collections.addAll(replacingVariables, vars);
    }

    /**
     * Extracts the top {@link Variable} of the stack and pushes all the Variables in {@link #replacingVariables}.
     * @param stack, reference to the parser's stack.
     * @param list, reference to the parser's symbol queue.
     * @throws SyntaxException, if the extracted {@link Variable} doesn't correspond to the rule left-hand variable type.
     */
    @Override
    public void action(StackWrapper stack, SymbolQueue list) throws SyntaxException {
        // Extracts the fist var of the stack and checks if
        Variable topVar = stack.remVar();
        if (!topVar.getType().equals(expectedType)) {
            throw new SyntaxException("Variable type on the top of the stack "+topVar.getType().toString()+" doesn't" +
                    " correspond to the rule's expected type "+expectedType.toString()+".");
        }
        for (Variable var : replacingVariables) {
            stack.pushVar(var);
        }
    }

}