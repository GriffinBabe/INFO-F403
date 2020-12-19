package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Reads a number in the standard input stream and assigns it to a new or existing variable. In the case the variable
 * doesn't exists, then it allocates a new variable.
 */
public class ReadSymbol extends InstructionSymbol {

    /**
     * Variable to assign or read.
     */
    private VariableSymbol toRead;

    /**
     * Sets the variable to assign after reading
     * @param toRead the variable to assign.
     */
    public void setToRead(VariableSymbol toRead) {
        this.toRead = toRead;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the multiplication result)
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        TempVariable tempVar = table.nextRegister();
        String variableName = toRead.getVariableName();

        // reads a number from the standard input stream and stores it in a temporary variable
        sb.append(tempVar).append(" = call i32 @readInt()\n");

        // if not allocated yet, allocates it
        if (!table.isAllocated(variableName)) {
            sb.append("%").append(variableName).append(" = alloca i32, align 4\n");
            table.setAllocated(variableName);
        }

        // stores the read temporary variable into a memory variable
        sb.append("store i32 ").append(tempVar).append(", i32* %").append(variableName).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Read>";
    }
}
