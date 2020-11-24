package parser;

import scanner.Scanner;
import scanner.Symbol;

/**
 * This class represents all the Variables and Terminals in the {@link SymbolQueue} and the {@link StackWrapper}.
 */
public class Variable {

    /**
     * Number of variables (excluding terminals).
     */
    public final static int VARIABLE_COUNT = 23;

    /**
     * Number of terminals (excluding variables).
     */
    public final static int TERMINAL_COUNT = 26;

    /**
     * Enumeration to all variables and symbol types.
     */
    public enum Type {

        V_PROGRAM(0, "<Program>"),
        V_PROGRAM_(1, "<Program'>"),
        V_PROGRAM__(2, "<Program''>"),
        V_CODE(3, "<Code>"),
        V_INSTRUCTION(4, "<Instruction>"),
        V_ASSIGN(5, "<Assign>"),
        V_EXPRARITH(6, "<ExprArith>"),
        V_EXPRARITH_(7, "<ExprArith'>"),
        V_B(8, "<B>"),
        V_B_(9, "<B'>"),
        V_C(10, "<C>"),
        V_IF(11, "<If>"),
        V_COND_(12, "<Cond'>"),
        V_IF_(13, "<If'>"),
        V_IFBIS(14, "<Ifbis>"),
        V_IFBIS_(15, "<Ifbis'>"),
        V_COND(16, "<Cond>"),
        V_COMP(17, "<Comp>"),
        V_WHILE(18, "<While>"),
        V_WHILE_(19, "<While'>"),
        V_V1(20, "<V1>"),
        V_V2(21, "<V2>"),
        V_V3(22, "<V3>"),
        BEGINPROG(23, "BEGINPROG"),
        PROGNAME(24, "[ProgName]"),
        ENDLINE(25, "[EndLine]"),
        VARNAME(26, "[VarName]"),
        LPAREN(27, "("),
        RPAREN(28, ")"),
        PLUS(29, "+"),
        MINUS(30, "-"),
        TIMES(31, "*"),
        DIVIDE(32, "/"),
        ASSIGN(33, ":="),
        NUMBER(34, "[Number]"),
        IF(35, "IF"),
        THEN(36, "THEN"),
        ENDIF(37, "ENDIF"),
        WHILE(38, "WHILE"),
        ENDWHILE(39, "ENDWHILE"),
        ENDPROG(40, "ENDPROG"),
        PRINT(41, "PRINT"),
        READ(42, "READ"),
        GT(43, ">"),
        EQ(44, "="),
        ELSE(45, "ELSE"),
        COMMA(46, ","),
        DO(47, "DO"),
        EPS(48, "$\\epsilon$");

        int id;

        String texString;

        Type(int id, String texString) {
            this.id = id;
            this.texString = texString;
        }

        public String toTexString() {
            return this.texString;
        }
    }

    /**
     * The {@link Type} of the {@link Variable}.
     */
    private Type type = null;

    /**
     * {@link Variable} main constructor.
     * @param type, the {@link Type} of the {@link Variable}.
     */
    public Variable(Type type) {
        this.type = type;
    }

    /**
     * {@link Variable} constructor?
     * Converts a {@link Symbol} (used by the {@link Scanner}) into a {@link Variable}.
     * @param symbol, the symbol to convert.
     */
    public Variable(Symbol symbol) {
        switch (symbol.getType()) {
            case BEGINPROG:
                this.type = Type.BEGINPROG;
                break;
            case PROGNAME:
                this.type = Type.PROGNAME;
                break;
            case ENDLINE:
                this.type = Type.ENDLINE;
                break;
            case ENDPROG:
                this.type = Type.ENDPROG;
                break;
            case COMMA:
                this.type = Type.COMMA;
                break;
            case VARNAME:
                this.type = Type.VARNAME;
                break;
            case ASSIGN:
                this.type = Type.ASSIGN;
                break;
            case NUMBER:
                this.type = Type.NUMBER;
                break;
            case LPAREN:
                this.type = Type.LPAREN;
                break;
            case RPAREN:
                this.type = Type.RPAREN;
                break;
            case MINUS:
                this.type = Type.MINUS;
                break;
            case PLUS:
                this.type = Type.PLUS;
                break;
            case TIMES:
                this.type = Type.TIMES;
                break;
            case DIVIDE:
                this.type = Type.DIVIDE;
                break;
            case IF:
                this.type = Type.IF;
                break;
            case THEN:
                this.type = Type.THEN;
                break;
            case ENDIF:
                this.type = Type.ENDIF;
                break;
            case ELSE:
                this.type = Type.ELSE;
                break;
            case EQ:
                this.type = Type.EQ;
                break;
            case GT:
                this.type = Type.GT;
                break;
            case WHILE:
                this.type = Type.WHILE;
                break;
            case DO:
                this.type = Type.DO;
                break;
            case ENDWHILE:
                this.type = Type.ENDWHILE;
                break;
            case PRINT:
                this.type = Type.PRINT;
                break;
            case READ:
                this.type = Type.READ;
                break;
        }
    }

    /**
     * {@link Type} getter.
     * @return the {@link Type} of the {@link Variable}.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Checks if the {@link Variable} is a terminal type or not.
     * @return yes if the {@link Variable} is a terminal or not.
     */
    public boolean isTerminal() {
        return this.type.id >= VARIABLE_COUNT;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }

    public String toTexString() {
        return this.type.toTexString();
    }
}
