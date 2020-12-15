package compiler.symbol;

import compiler.CompilerTable;

public class DivideSymbol extends OperatorSymbol {

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Div>";
    }
}
