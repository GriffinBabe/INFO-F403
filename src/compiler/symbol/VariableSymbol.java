package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Symbol that holds a variable. While there are multiple operations that can be performed on a variable, the toLLVM()
 * method is only to load the variable on a specified register number. Assigning the value of a variable must be done
 * outside for example in the {@link AssignSymbol} and {@link ReadSymbol}.
 */
public class VariableSymbol extends ExpressionSymbol {

    /**
     * The name of the variable.
     */
    private String variableName;

    public VariableSymbol(String name) {
        this.variableName = name;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to load the variable)
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        //if the variable has already been initialized, load it into the given register
        if (table.isAllocated(this.variableName)) {
            sb.append(returnRegisters[0]).append(" = load i32, i32* ").append("%").append(this.variableName).append("\n");
            return sb.toString();
        }
        else{
            return null;
        }
    }

    @Override
    public String toTexString() {
        return "<Var: " + variableName + ">";
    }

    /**
     * @return the variable name.
     */
    public String getVariableName() {
        return variableName;
    }
}
