import java.util.ArrayList;

public class ActionTable {

    public Rule table[][] = null;

    public ArrayList<Rule> ruleList = null;

    public ActionTable() {
        initRules();
        populateRules();
    }

    /**
     * Finds the rule in the action table and returns it.
     * @param
     */
    public Rule getRule(Variable top, Variable lookahead) {
        return table[top.getType().id][lookahead.getType().id - Variable.TERMINAL_COUNT];
    }

    private void initRules() {
        ruleList = new ArrayList<>(Rule.RULE_COUNT);

        DerivationRule rule1 = new DerivationRule(Variable.Type.V_PROGRAM);
        rule1.addReplacingVariable(new Variable(Variable.Type.BEGINPROG), new Variable(Variable.Type.V_PROGRAM_));
        ruleList.add(rule1);

        DerivationRule rule2 = new DerivationRule(Variable.Type.V_PROGRAM_);
        rule2.addReplacingVariable(new Variable(Variable.Type.PROGNAME), new Variable(Variable.Type.V_PROGRAM__));
        ruleList.add(rule2);

        DerivationRule rule3 = new DerivationRule(Variable.Type.V_PROGRAM__);
        rule3.addReplacingVariable(new Variable(Variable.Type.ENDLINE), new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDPROG));
        ruleList.add(rule3);

        DerivationRule rule4 = new DerivationRule(Variable.Type.V_CODE);
        rule4.addReplacingVariable(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.ENDLINE), new Variable(Variable.Type.V_CODE));
        ruleList.add(rule4);

        DerivationRule rule5 = new DerivationRule(Variable.Type.V_CODE);
        // no replacing as it is epsilon
        ruleList.add(rule5);

        DerivationRule rule6 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        rule6.addReplacingVariable(new Variable(Variable.Type.VARNAME), new Variable(Variable.Type.V_ASSIGN));
        ruleList.add(rule6);

        DerivationRule rule7 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        rule7.addReplacingVariable(new Variable(Variable.Type.IF), new Variable(Variable.Type.V_IF));
        ruleList.add(rule7);

        DerivationRule rule8 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        rule8.addReplacingVariable(new Variable(Variable.Type.WHILE), new Variable(Variable.Type.V_WHILE));
        ruleList.add(rule8);

        DerivationRule rule9 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        rule9.addReplacingVariable(new Variable(Variable.Type.PRINT), new Variable(Variable.Type.V_V1));
        ruleList.add(rule9);

        DerivationRule rule10 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        rule10.addReplacingVariable(new Variable(Variable.Type.READ), new Variable(Variable.Type.V_V1));
        ruleList.add(rule10);

        DerivationRule rule11 = new DerivationRule(Variable.Type.V_INSTRUCTION);
        //no replacing as it is epsilon
        ruleList.add(rule11);

        DerivationRule rule12 = new DerivationRule(Variable.Type.V_ASSIGN);
        rule12.addReplacingVariable(new Variable(Variable.Type.ASSIGN), new Variable(Variable.Type.V_EXPRARITH));
        ruleList.add(rule12);

        DerivationRule rule13 = new DerivationRule(Variable.Type.V_EXPRARITH);
        rule13.addReplacingVariable(new Variable(Variable.Type.V_B), new Variable(Variable.Type.V_EXPRARITH_));
        ruleList.add(rule13);

        DerivationRule rule14 = new DerivationRule(Variable.Type.V_EXPRARITH_);
        rule14.addReplacingVariable(new Variable(Variable.Type.MINUS), new Variable(Variable.Type.V_B), new Variable(Variable.Type.V_EXPRARITH_));
        ruleList.add(rule14);

        DerivationRule rule15 = new DerivationRule(Variable.Type.V_EXPRARITH_);
        rule15.addReplacingVariable(new Variable(Variable.Type.PLUS), new Variable(Variable.Type.V_B), new Variable(Variable.Type.V_EXPRARITH_));
        ruleList.add(rule15);

        DerivationRule rule16 = new DerivationRule(Variable.Type.V_EXPRARITH_);
        //no replacing as it is epsilon
        ruleList.add(rule16);

        // TODO add V_B_ to variables

    }

    private void populateRules() {
        // Populates the entire array with invalid rules
        table = new Rule[Variable.VARIABLE_COUNT + Variable.TERMINAL_COUNT][Variable.TERMINAL_COUNT];
        for (int i = 0; i < Variable.VARIABLE_COUNT; i++) {
            for (int j = 0; j < Variable.TERMINAL_COUNT; j++) {
                table[i][j] = new Rule();
            }
        }

        // Adds a match rule on the terminal/terminal diagonal.
        for (int ij = 0; ij < Variable.TERMINAL_COUNT; ij++) {
            table[ij + Variable.VARIABLE_COUNT][ij] = new MatchRule();
        }

        // Puts the correct values in the table

    }
}
