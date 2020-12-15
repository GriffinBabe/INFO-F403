package compiler.symbol;

import compiler.CompilerTable;

public class UnarySymbol extends ExpressionSymbol {

    protected ExpressionSymbol left;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Unary>";
    }

    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }
}
