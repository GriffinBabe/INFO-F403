package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

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
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Print>";
    }
}
