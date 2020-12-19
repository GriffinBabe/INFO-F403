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
     * Maps the link between the variables ID created during the compilation and the corresponding outputted order.
     * This map is used in the {@link #getUsageId(int)} method.
     */
    private Map<Integer, Integer> usageIds = new HashMap<>();

    /**
     * Register output order.
     * Used in the {@link #getUsageId(int)} method.
     */
    private int usageCount = 0;

    /**
     * The number of temporary registers variables already assigned in the LLVM source code.
     * Used in the {@link #getUsageId(int)} method.
     */
    private int registerCount = 0;

    /**
     * The number of label used for if and while blocks.
     */
    private int labelCount = 0;

    /**
     * List of variables name already allocated in the memory.
     */
    List<String> variables = new ArrayList<>();

    /**
     * @return a register with a new creation id.
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
     * @return true if already exists, false otherwise.
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

    /**
     * Returns the corresponding output order number. Used in the {@link TempVariable} objects.
     * @param creationId the unique ID of the {@link TempVariable} creation.
     * @return the corresponding output order number.
     */
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
