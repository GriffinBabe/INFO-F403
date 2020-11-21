public class Variable {

    public final static int VARIABLE_COUNT = 20;
    public final static int TERMINAL_COUNT = 25;

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
        V_C(9),
        V_IF(10),
        V_IFBIS(11),
        V_IFBIS_(12),
        V_COND(13),
        V_COMP(14),
        V_WHILE(15),
        V_WHILE_(16),
        V_V1(17),
        V_V2(18),
        V_V3(19),
        BEGINPROG(20),
        PROGNAME(21),
        ENDLINE(22),
        VARNAME(23),
        LPAREN(24),
        RPAREN(25),
        PLUS(26),
        MINUS(27),
        TIMES(28),
        DIVIDE(29),
        ASSIGN(30),
        NUMBER(31),
        IF(32),
        THEN(33),
        ENDIF(34),
        WHILE(35),
        ENDWHILE(36),
        ENDPROG(37),
        PRINT(38),
        READ(39),
        GT(40),
        EQ(41),
        ELSE(42),
        COMMA(43),
        DO(44);

        int id = 0;

        Type(int id) {
            this.id = id;
        }
    }

    private Type type = null;

    public Variable(Type type) {
        this.type = type;
    }

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

    public Type getType() {
        return this.type;
    }
}
