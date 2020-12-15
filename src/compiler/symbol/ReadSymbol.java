package compiler.symbol;

import compiler.CompilerTable;

public class ReadSymbol extends InstructionSymbol {

    private VariableSymbol toRead;

    public ReadSymbol() {}

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    public void setToRead(VariableSymbol toRead) {
        this.toRead = toRead;
    }

    @Override
    public String toTexString() {
        return "<Read>";
    }
}
