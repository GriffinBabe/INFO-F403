package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class AdditionSymbol extends ExpressionSymbol {

    private ExpressionSymbol left;

    private ExpressionSymbol right;

    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }

    public void setRight(ExpressionSymbol right) {
        this.right = right;
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
        return null;
    }
}
