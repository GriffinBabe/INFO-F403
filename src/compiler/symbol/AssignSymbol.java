package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class AssignSymbol extends InstructionSymbol {

    private VariableSymbol variable = null;

    private ExpressionSymbol expression = null;

    public void setVariable(VariableSymbol symbol) {
        this.variable = symbol;
    }

    public void setExpression(ExpressionSymbol expression) {
        this.expression = expression;
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
        return "<Assign>";
    }
}
