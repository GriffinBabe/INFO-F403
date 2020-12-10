package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class VariableSymbol extends Symbol {

    private String variableName;

    public VariableSymbol(String name) {
        this.variableName = name;
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
        return "<Var: " + variableName + ">";
    }
}
