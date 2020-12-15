package compiler.symbol;

import compiler.CompilerTable;

public class VariableSymbol extends ExpressionSymbol {

    private String variableName;

    public VariableSymbol(String name) {
        this.variableName = name;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Var: " + variableName + ">";
    }
}
