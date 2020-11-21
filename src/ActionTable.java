public class ActionTable {

    public Rule table[][];

    public ActionTable() {

    }

    /**
     * Finds the rule in the action table and returns it.
     * @param variable
     */
    public Rule getRule(Variable top, Variable lookahead) {
        return table[top.getType().id][top.getType().id - 20];
    }
}
