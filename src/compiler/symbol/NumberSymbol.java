package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class NumberSymbol extends ExpressionSymbol {

    private Integer value;

    public NumberSymbol(Integer value) {
        this.value = value;
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
        return "<Num: " + value + ">";
    }
}
