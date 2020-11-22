import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

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

    /**
     * {@link DerivationRule} constructor. Takes a {@link Variable.Type} in parameter, it is the expected top of the
     * stack for the derivation to happen.
     */
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
     * See {@link Rule#action(StackWrapper, SymbolQueue)}.
     * Extracts the top {@link Variable} of the stack and pushes all the Variables in the {@link #replacingVariables}
     * on the {@link StackWrapper}.
     */
    @Override
    public void action(StackWrapper stack, SymbolQueue list) throws SyntaxException {
        // Extracts the fist var of the stack and checks if
        Variable topVar = stack.remVar();
        if (!topVar.getType().equals(expectedType)) {
            throw new SyntaxException("Variable type on the top of the stack "+topVar.getType().toString()+" doesn't" +
                    " correspond to the rule's expected type "+expectedType.toString()+".");
        }
        // adds to the stack the replacing variables in reverse order, as from the stack nature
        ListIterator<Variable> itr = replacingVariables.listIterator(replacingVariables.size());
        while (itr.hasPrevious()) {
            stack.pushVar(itr.previous());
        }
    }

}