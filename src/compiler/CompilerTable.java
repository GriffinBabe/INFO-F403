package compiler;

/**
 * Compiler table contains
 */
public class CompilerTable {

    private int registerCount = 0;

    public String nextRegister() {
        String s = "%" + registerCount;
        registerCount++;
        return s;
    }

    public String nextCond() {

    }

    public String nextBody() {

    }

    public String nextEnd() {

    }
}
