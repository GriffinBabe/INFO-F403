package compiler;

import parser.ParseTree;

public class AssignSymbol extends InstructionSymbol {

    private VariableSymbol variable = null;

    public void setVariableSymbol(VariableSymbol symbol) {
        this.variable = symbol;
    }

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }
}
