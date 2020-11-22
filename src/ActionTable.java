import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction;

import java.util.ArrayList;

/**
 * The action table is the responsible for initializing and mapping the {@link Rule}s.
 * The ActionTable is prepared at it's constructor.
 * {@link Rule}s can be retrieved by the parser though the {@link #getRule(Variable, Variable)} method.
 */
public class ActionTable {

    /**
     * 2D array representing the ActionTable. Links a rule to all {@link Rule#action(StackWrapper, SymbolQueue)}
     * with the corresponding (top-stack, lookahead) pair.
     */
    public Rule table[][] = null;

    /**
     * List containing all the grammar's {@link DerivationRule}.
     */
    public ArrayList<Rule> ruleList = null;

    /**
     * {@link ActionTable} constructor's. Calls {@link #initRules()} and {@link #populateRules()}.
     */
    public ActionTable() {
        initRules();
        populateRules();
    }

    /**
     * Finds the rule in the action table and returns it.
     * @param top, the variable on the top of the stack.
     * @param lookahead, the first lookahead variable.
     * @reutn Rule, a rule to call {@link Rule#action(StackWrapper, SymbolQueue)}.
     */
    public Rule getRule(Variable top, Variable lookahead) {
        return table[top.getType().id][lookahead.getType().id - Variable.TERMINAL_COUNT];
    }

    /**
     * Initializes the hardcoded {@link DerivationRule} inside this function and sets them into the {@link #ruleList}.
     */
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

        DerivationRule rule17 = new DerivationRule(Variable.Type.V_B);
        rule17.addReplacingVariable(new Variable(Variable.Type.V_C), new Variable(Variable.Type.V_B_));
        ruleList.add(rule17);

        DerivationRule rule18 = new DerivationRule(Variable.Type.V_B_);
        rule18.addReplacingVariable(new Variable(Variable.Type.TIMES), new Variable(Variable.Type.V_C), new Variable(Variable.Type.V_B_));
        ruleList.add(rule18);

        DerivationRule rule19 = new DerivationRule(Variable.Type.V_B_);
        rule19.addReplacingVariable(new Variable(Variable.Type.DIVIDE), new Variable(Variable.Type.V_C), new Variable(Variable.Type.V_B_));
        ruleList.add(rule19);

        DerivationRule rule20 = new DerivationRule(Variable.Type.V_B_);
        //no replacing as it is epsilon
        ruleList.add(rule20);

        DerivationRule rule21 = new DerivationRule(Variable.Type.V_C);
        rule21.addReplacingVariable(new Variable(Variable.Type.LPAREN), new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.RPAREN));
        ruleList.add(rule21);

        DerivationRule rule22 = new DerivationRule(Variable.Type.V_C);
        rule22.addReplacingVariable(new Variable(Variable.Type.MINUS), new Variable(Variable.Type.V_EXPRARITH));
        ruleList.add(rule22);

        DerivationRule rule23 = new DerivationRule(Variable.Type.V_C);
        rule23.addReplacingVariable(new Variable(Variable.Type.NUMBER));
        ruleList.add(rule23);

        DerivationRule rule24 = new DerivationRule(Variable.Type.V_C);
        rule24.addReplacingVariable(new Variable(Variable.Type.VARNAME));
        ruleList.add(rule24);

        DerivationRule rule25 = new DerivationRule(Variable.Type.V_IF);
        rule25.addReplacingVariable(new Variable(Variable.Type.V_COND_), new Variable(Variable.Type.THEN), new Variable(Variable.Type.V_IF_));
        ruleList.add(rule25);

        DerivationRule rule26 = new DerivationRule(Variable.Type.V_COND_);
        rule26.addReplacingVariable(new Variable(Variable.Type.LPAREN), new Variable(Variable.Type.V_COND), new Variable(Variable.Type.RPAREN));
        ruleList.add(rule26);

        DerivationRule rule27 = new DerivationRule(Variable.Type.V_IF_);
        rule27.addReplacingVariable(new Variable(Variable.Type.ENDLINE), new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.V_IFBIS_));
        ruleList.add(rule27);

        DerivationRule rule28 = new DerivationRule(Variable.Type.V_IFBIS);
        rule28.addReplacingVariable(new Variable(Variable.Type.ENDIF));
        ruleList.add(rule28);

        DerivationRule rule29 = new DerivationRule(Variable.Type.V_IFBIS);
        rule29.addReplacingVariable(new Variable(Variable.Type.ELSE), new Variable(Variable.Type.V_IFBIS_));
        ruleList.add(rule29);

        DerivationRule rule30 = new DerivationRule(Variable.Type.V_IFBIS_);
        rule30.addReplacingVariable(new Variable(Variable.Type.ENDLINE), new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDIF));
        ruleList.add(rule30);

        DerivationRule rule31 = new DerivationRule(Variable.Type.V_COND);
        rule31.addReplacingVariable(new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.V_COMP), new Variable(Variable.Type.V_EXPRARITH));
        ruleList.add(rule31);

        DerivationRule rule32 = new DerivationRule(Variable.Type.V_COMP);
        rule32.addReplacingVariable(new Variable(Variable.Type.EQ));
        ruleList.add(rule32);

        DerivationRule rule33 = new DerivationRule(Variable.Type.V_COMP);
        rule33.addReplacingVariable(new Variable(Variable.Type.GT));
        ruleList.add(rule33);

        DerivationRule rule34 = new DerivationRule(Variable.Type.V_WHILE);
        rule34.addReplacingVariable(new Variable(Variable.Type.V_COND_), new Variable(Variable.Type.DO), new Variable(Variable.Type.V_WHILE_));
        ruleList.add(rule34);

        DerivationRule rule35 = new DerivationRule(Variable.Type.V_WHILE_);
        rule35.addReplacingVariable(new Variable(Variable.Type.ENDLINE), new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDWHILE));
        ruleList.add(rule35);

        DerivationRule rule36 = new DerivationRule(Variable.Type.V_V1);
        rule36.addReplacingVariable(new Variable(Variable.Type.LPAREN), new Variable(Variable.Type.V_V2));
        ruleList.add(rule36);

        DerivationRule rule37 = new DerivationRule(Variable.Type.V_V2);
        rule37.addReplacingVariable(new Variable(Variable.Type.VARNAME), new Variable(Variable.Type.V_V3));
        ruleList.add(rule37);

        DerivationRule rule38 = new DerivationRule(Variable.Type.V_V3);
        rule38.addReplacingVariable(new Variable(Variable.Type.RPAREN));
        ruleList.add(rule38);
    }

    /**
     * Maps a corresponding {@link DerivationRule} into the {@link #table}
     * @param top, the variable on the top of the stack.
     * @param lookahead, the first lookahead variable.
     * @param ruleIndex the index of the rule to call in the {@link #ruleList}.
     */
    private void mapRule(Variable top, Variable lookahead, int ruleIndex) {
        table[top.getType().id][lookahead.getType().id - Variable.TERMINAL_COUNT] = ruleList.get(ruleIndex);
    }

    /**
     * Populates the entire {@link #table} with Rules.
     * First sets an invalid rule in all the cells.
     * Then adds a {@link MatchRule} on the Terminal's diagonal.
     * Finally, adds the {@link DerivationRule} in the correct cells.
     */
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

        // Maps the derivation rules in the table
        mapRule(new Variable(Variable.Type.V_PROGRAM), new Variable(Variable.Type.BEGINPROG), 0);
        mapRule(new Variable(Variable.Type.V_PROGRAM_), new Variable(Variable.Type.PROGNAME), 1);
        mapRule(new Variable(Variable.Type.V_PROGRAM__), new Variable(Variable.Type.ENDLINE), 2);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.VARNAME), 3);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.IF), 3);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDIF), 4);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.WHILE), 3);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDWHILE), 4);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.ENDPROG), 4);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.PRINT), 3);
        mapRule(new Variable(Variable.Type.V_CODE), new Variable(Variable.Type.READ), 3);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.ENDLINE), 10);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.VARNAME), 5);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.IF), 6);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.WHILE), 7);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.PRINT), 8);
        mapRule(new Variable(Variable.Type.V_INSTRUCTION), new Variable(Variable.Type.READ), 9);
        mapRule(new Variable(Variable.Type.V_ASSIGN), new Variable(Variable.Type.ASSIGN), 11);
        mapRule(new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.VARNAME), 16);
        mapRule(new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.LPAREN), 16);
        mapRule(new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.MINUS), 16);
        mapRule(new Variable(Variable.Type.V_EXPRARITH), new Variable(Variable.Type.NUMBER), 16);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.ENDLINE), 15);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.RPAREN), 15);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.PLUS), 14);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.MINUS), 13);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.GT), 15);
        mapRule(new Variable(Variable.Type.V_EXPRARITH_), new Variable(Variable.Type.EQ), 15);
        mapRule(new Variable(Variable.Type.V_B), new Variable(Variable.Type.VARNAME), 16);
        mapRule(new Variable(Variable.Type.V_B), new Variable(Variable.Type.LPAREN), 16);
        mapRule(new Variable(Variable.Type.V_B), new Variable(Variable.Type.MINUS), 16);
        mapRule(new Variable(Variable.Type.V_B), new Variable(Variable.Type.NUMBER), 16);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.ENDLINE), 19);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.RPAREN), 19);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.PLUS), 19);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.MINUS), 19);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.TIMES), 17);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.DIVIDE), 18);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.GT), 19);
        mapRule(new Variable(Variable.Type.V_B_), new Variable(Variable.Type.EQ), 19);
        mapRule(new Variable(Variable.Type.V_C), new Variable(Variable.Type.VARNAME), 23);
        mapRule(new Variable(Variable.Type.V_C), new Variable(Variable.Type.LPAREN), 20);
        mapRule(new Variable(Variable.Type.V_C), new Variable(Variable.Type.MINUS), 21);
        mapRule(new Variable(Variable.Type.V_C), new Variable(Variable.Type.NUMBER), 22);
        mapRule(new Variable(Variable.Type.V_IF), new Variable(Variable.Type.LPAREN), 24);
        mapRule(new Variable(Variable.Type.V_COND_), new Variable(Variable.Type.LPAREN), 25);
        mapRule(new Variable(Variable.Type.V_IF_), new Variable(Variable.Type.ENDLINE), 26);
        mapRule(new Variable(Variable.Type.V_IFBIS), new Variable(Variable.Type.ENDIF), 27);
        mapRule(new Variable(Variable.Type.V_IFBIS), new Variable(Variable.Type.ELSE), 28);
        mapRule(new Variable(Variable.Type.V_IFBIS_), new Variable(Variable.Type.ENDLINE), 29);
        mapRule(new Variable(Variable.Type.V_COND), new Variable(Variable.Type.VARNAME), 30);
        mapRule(new Variable(Variable.Type.V_COND), new Variable(Variable.Type.LPAREN), 30);
        mapRule(new Variable(Variable.Type.V_COND), new Variable(Variable.Type.MINUS), 30);
        mapRule(new Variable(Variable.Type.V_COND), new Variable(Variable.Type.NUMBER), 30);
        mapRule(new Variable(Variable.Type.V_COMP), new Variable(Variable.Type.GT), 32);
        mapRule(new Variable(Variable.Type.V_COMP), new Variable(Variable.Type.EQ), 31);
        mapRule(new Variable(Variable.Type.V_WHILE), new Variable(Variable.Type.LPAREN), 33);
        mapRule(new Variable(Variable.Type.V_WHILE_), new Variable(Variable.Type.ENDLINE), 34);
        mapRule(new Variable(Variable.Type.V_V1), new Variable(Variable.Type.LPAREN), 35);
        mapRule(new Variable(Variable.Type.V_V2), new Variable(Variable.Type.ENDLINE), 36);
        mapRule(new Variable(Variable.Type.V_V3), new Variable(Variable.Type.RPAREN), 37);
   }
}
