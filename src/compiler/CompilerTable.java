package compiler;

/**
 * Manage the names and labels assignation
 */
public class CompilerTable {

    private int registerCount = 0;
    private int labelCount = 0;

    /**
     * @return the next valid register name
     */
    public String nextRegister() {
        String s = "%" + registerCount;
        registerCount++;
        return s;
    }

    /**
     * @return the next valid label name
     */
    public String nextLabel() {
        String s = "label_" + labelCount;
        labelCount++;
        return s;
    }
}
