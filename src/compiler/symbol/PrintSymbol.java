package compiler.symbol;

import compiler.CompilerTable;

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

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String loadedVariable = table.nextRegister();

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
