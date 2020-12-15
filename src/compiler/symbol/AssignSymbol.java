package compiler.symbol;

import compiler.CompilerTable;

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
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Assign>";
    }
}
