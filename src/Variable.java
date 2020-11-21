public class Variable {

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
        ELSE(42);

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
        }
    }

    public Type getType() {
        return this.type;
    }
}
