package parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Extracts the top {@link Variable} of the stack and pushes new {@link Variable} on the stack.
 */
public final class DerivationRule extends Rule {
    /**
     * rule number corresponding to our LL(1) grammar, needed for displaying the rule
     * in standard output when used
     */
    private final int ruleID;
    /**
     * The type of the expected top variable. This is used for an additional check.
     */
    private final Variable.Type expectedType;

    /**
     * Replaces the variable
     */
    private final ArrayList<Variable> replacingVariables;

    /**
     * {@link DerivationRule} constructor. Takes a {@link Variable.Type} in parameter, it is the expected top of the
     * stack for the derivation to happen.
     */
    public DerivationRule(Variable.Type expectedType, int id) {
        this.expectedType = expectedType;
        this.replacingVariables = new ArrayList<>();
        this.ruleID = id;
    }

    /**
     * Adds some replacing variables in the rule's {@link #replacingVariables}.
     * @param vars, the new variables to add.
     */
    public void addReplacingVariable(Variable... vars) {
        Collections.addAll(replacingVariables, vars);
    }

    /**
     * See {@link Rule#action(StackWrapper, SymbolQueue, ParseTree)}.
     * Extracts the top {@link Variable} of the stack and pushes all the Variables in the {@link #replacingVariables}
     * on the {@link StackWrapper}. Finally, derives the left-most node of the parser's {@link ParseTree}.
     */
    @Override
    public void action(StackWrapper stack, SymbolQueue list, ParseTree tree) throws SyntaxException {
        // Extracts the fist var of the stack and checks if
        Variable topVar = stack.remVar();
        if (!topVar.getType().equals(expectedType)) {
            throw new SyntaxException("parser.parser.Variable type on the top of the stack "+topVar.getType().toString()+" doesn't" +
                    " correspond to the rule's expected type "+expectedType.toString()+".");
        }
        // need to do a deep copy of the variables as having the reference to the same variables in all the tree
        // is problematic for the Varname and Number variable values.
        List<Variable> deepCopyVariables = new ArrayList<>();
        for (Variable var : replacingVariables) {
            try {
                Variable clone = (Variable) var.clone();
                deepCopyVariables.add(clone);
            }
            catch (CloneNotSupportedException e) {
                // should not happen in this case.
                System.err.println("Couldn't perform deep copy of variable object.");
                System.exit(1);
            }
        }

        // adds to the stack the replacing variables in reverse order, as from the stack nature
        ListIterator<Variable> itr = deepCopyVariables.listIterator(deepCopyVariables.size());
        while (itr.hasPrevious()) {
            stack.pushVar(itr.previous());
        }
        if (deepCopyVariables.isEmpty()) {
            // adds an epsilon to the tree.
            Variable epsilon = new Variable(Variable.Type.EPS);
            ArrayList<Variable> tempVariables = new ArrayList<>();
            tempVariables.add(epsilon);
            tree.deriveLeftmost(new Variable(expectedType), tempVariables);
        }
        else {
            // adds the replacing variables to the tree
            tree.deriveLeftmost(new Variable(expectedType), deepCopyVariables);
        }
    }

    /**
     * See {@link Rule#toString()}.
     */
    @Override
    public String toString() {
        if (Parser.VERBOSE) {
            StringBuilder builder = new StringBuilder();
            builder.append("Derivation rule: ");
            builder.append(this.expectedType);
            builder.append(" =====> ");
            for (Variable var : replacingVariables) {
                builder.append(var.getType());
                builder.append(", ");
            }
            if (replacingVariables.isEmpty()) {
                builder.append("eps");
            }
            builder.append("\n");
            return builder.toString();
        }
        else {
            return String.valueOf(this.ruleID) + " ";
        }
    }
}