package compiler;

/**
 * Manages the names and labels assignation in the final LLVM source code.
 * Temporary register variables must be unique, such as labels for the while and if assignments.
 */
public class CompilerTable {

    /**
     * The number of temporary registers variables already assigned in the LLVM source code.
     */
    private int registerCount = 0;

    /**
     * The number of label used for if and while blocks.
     */
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
