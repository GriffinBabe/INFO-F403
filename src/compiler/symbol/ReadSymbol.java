package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class ReadSymbol extends InstructionSymbol {

    private VariableSymbol toRead;

    public ReadSymbol() {}

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
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
