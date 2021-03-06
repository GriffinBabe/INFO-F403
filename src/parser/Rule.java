package parser;

/**
 * Base class for all the rules.
 *
 * Rules define the behaviour of the parser.
 * The rules are obtained in the parser through the {@link ActionTable} and are executed with the base
 * {@link #action(StackWrapper, SymbolQueue, ParseTree)} method.
 */
public class Rule {

    /**
     * Number of rules in our LL(1) formatted grammar.
     */
    public final static int RULE_COUNT = 38;

    /**
     * Performs an action. Might or not perform operations on the {@link StackWrapper}, the {@link SymbolQueue} or the {@link ParseTree}.
     *
     * This method is overridden in the derived classes.
     *
     * This method itself should normally never be called, as it is a rule that fills
     * the empty {@link ActionTable#table} cells.
     *
     * @param stack, reference to the stack of the parser.
     * @param list, reference to the list of symbols obtained by the {@link scanner.Scanner} in the parser.
     * @param tree, reference to the parser's {@link ParseTree}.
     * @throws SyntaxException if this rule is called anyway, it will throw an exception.
     */
    public void action(StackWrapper stack, SymbolQueue list, ParseTree tree) throws SyntaxException {
        throw new SyntaxException("No rule found for top stack token: "+stack.readTop().toString()+
                " and first scanned symbol: "+list.read().toString());
    }

    /**
     * Overrides {@link Object#toString()}. Returns {@link Rule}'s information.
     * @return a string containing the {@link Rule} information.
     */
    @Override
    public String toString() {
        return "Invalid rule. Syntax error detected.\n";
    }
}
