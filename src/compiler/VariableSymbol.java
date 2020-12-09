package compiler;

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
}
