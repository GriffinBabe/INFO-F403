/**
 * Base class for all the rules.
 * The rules are obtained in the parser through the {@link ActionTable} and are executed with the base
 * {@link #action(StackWrapper, SymbolQueue)} method.
 */
public class Rule {

    /**
     * Number of rules in our LL(1) formatted grammar.
     */
    public final static int RULE_COUNT = 38;

    /**
     * Performs an action. Might or not perform operations on the {@link StackWrapper} or the {@link SymbolQueue}.
     *
     * This method is overridden in the derived classes.
     *
     * This method itself should normally never be called, as it is an rule that fills
     * the empty {@link ActionTable#table} cells.
     *
     * @param stack, reference to the stack of the parser.
     * @param list, reference to the list of symbols obtained by the {@link Scanner} in the parser.
     * @throws SyntaxException, if this rule is called anyway, it will throw an exception.
     */
    public void action(StackWrapper stack, SymbolQueue list) throws SyntaxException {
        throw new SyntaxException("Invalid rule returned by ActionTable");
    }
}
