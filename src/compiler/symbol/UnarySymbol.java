package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class UnarySymbol extends ExpressionSymbol {

    protected ExpressionSymbol left;

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
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
