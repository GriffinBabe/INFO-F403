package compiler.symbol;

import compiler.CompilerTable;

public class EqualSymbol extends CompareSymbol {

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<EQ>";
    }
}
