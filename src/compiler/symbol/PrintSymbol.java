package compiler.symbol;

import compiler.CompilerTable;

public class PrintSymbol extends InstructionSymbol {

    private VariableSymbol toPrint = null;

    public PrintSymbol() {}

    public VariableSymbol getToPrint() {
        return toPrint;
    }

    public void setToPrint(VariableSymbol toPrint) {
        this.toPrint = toPrint;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Print>";
    }
}
