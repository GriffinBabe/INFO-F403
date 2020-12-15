package compiler.symbol;

import compiler.CompilerTable;

public class MultiplicationSymbol extends OperatorSymbol {

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Mult>";
    }
}
