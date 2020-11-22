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
    public final static int TERMINAL_COUNT = 25;

    /**
     * Enumeration to all variables and symbol types.
     */
    public enum Type {

        V_PROGRAM(0),
        V_PROGRAM_(1),
        V_PROGRAM__(2),
        V_CODE(3),
        V_INSTRUCTION(4),
        V_ASSIGN(5),
        V_EXPRARITH(6),
        V_EXPRARITH_(7),
        V_B(8),
        V_B_(9),
        V_C(10),
        V_IF(11),
        V_COND_(12),
        V_IF_(13),
        V_IFBIS(14),
        V_IFBIS_(15),
        V_COND(16),
        V_COMP(17),
        V_WHILE(18),
        V_WHILE_(19),
        V_V1(20),
        V_V2(21),
        V_V3(22),
        BEGINPROG(23),
        PROGNAME(24),
        ENDLINE(25),
        VARNAME(26),
        LPAREN(27),
        RPAREN(28),
        PLUS(29),
        MINUS(30),
        TIMES(31),
        DIVIDE(32),
        ASSIGN(33),
        NUMBER(34),
        IF(35),
        THEN(36),
        ENDIF(37),
        WHILE(38),
        ENDWHILE(39),
        ENDPROG(40),
        PRINT(41),
        READ(42),
        GT(43),
        EQ(44),
        ELSE(45),
        COMMA(46),
        DO(47);

        int id = 0;

        Type(int id) {
            this.id = id;
        }
    }

    /**
     * The {@link Type} of the {@link Variable}.
     */
    private Type type = null;

    /**
     * parser.Variable main contructor.
     * @param type, the {@link Type} of the {@link Variable}.
     */
    public Variable(Type type) {
        this.type = type;
    }

    /**
     * Converts a {@link Symbol} (used by the {@link Scanner}) into a {@link Variable}
     * @param symbol
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
}
