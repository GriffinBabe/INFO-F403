package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class MinusSymbol extends ExpressionSymbol {

    @Override
    public void setLeft(ExpressionSymbol left) {
        super.setLeft(left);
    }

    @Override
    public void setRight(ExpressionSymbol right) {
        super.setRight(right);
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
        return "<Minus>";
    }
}
