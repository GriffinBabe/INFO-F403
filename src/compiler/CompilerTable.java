package compiler;

import java.util.ArrayList;
import java.util.List;

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
     * List of variables name allocated in the memory.
     */
    List<String> variables = new ArrayList<>();

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

    /**
     * Checks if variable is already created in the memory.
     * @param varname, the variable name to check.
     */
    public boolean isAllocated(String varname) {
        return variables.stream().anyMatch(var -> var.equals(varname));
    }

    /**
     * Adds the variable to the allocated variables.
     * @param varname, the variable name to add.
     */
    public void setAllocated(String varname) {
        boolean already = isAllocated(varname);
        if (already) {
            System.out.println("Warning: variable "+varname+" is already allocated.");
        }
        variables.add(varname);
    }
}
