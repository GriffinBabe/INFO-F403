package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the names and labels assignation in the final LLVM source code.
 * Temporary register variables must be unique, such as labels for the while and if assignments.
 */
public class CompilerTable {

    /**
     * Maps the link between the registered count and printed count.
     */
    private Map<Integer, Integer> usageIds = new HashMap<>();
    private int usageCount = 0;

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
    public TempVariable nextRegister() {
        TempVariable variable = new TempVariable(this, registerCount);
        registerCount++;
        return variable;
    }

    /**
     * @return the next valid label name
     */
    public TempVariable nextLabel() {
        TempVariable variable = new TempVariable("label" + labelCount);
        labelCount++;
        return variable;
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

    public int getUsageId(int creationId) {
        Integer value = creationId;
        if (!usageIds.containsKey(value)) {
            usageIds.put(value, usageCount);
            usageCount++;
            return usageCount - 1;
        }
        else {
            return usageIds.get(value);
        }
    }
}
