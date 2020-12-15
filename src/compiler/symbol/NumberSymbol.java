package compiler.symbol;

import compiler.CompilerTable;

public class NumberSymbol extends ExpressionSymbol {

    private Integer value;

    public NumberSymbol(Integer value) {
        this.value = value;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Num: " + value + ">";
    }
}
