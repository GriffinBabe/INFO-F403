package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class DivideSymbol extends ExpressionSymbol {
    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Div>";
    }
}
