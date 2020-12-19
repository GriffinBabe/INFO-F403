package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Prints in the standard output stream a variable.
 */
public class PrintSymbol extends InstructionSymbol {

    /**
     * Reference to the variable to print.
     */
    private VariableSymbol toPrint = null;
    /**
     * Sets the {@link VariableSymbol} compiler unit to print.
     * @param toPrint, the variable to print.
     */
    public void setToPrint(VariableSymbol toPrint) {
        this.toPrint = toPrint;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use.
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        TempVariable loadedVariable = table.nextRegister();

        // 1. load the variable in the VariableSymbol#toLLVM(...)
        sb.append(toPrint.toLLVM(table, loadedVariable));
        // 2. calls the println() method on this local variable.
        sb.append("call void @println(i32 ").append(loadedVariable).append(")\n");

        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Print>";
    }
}
