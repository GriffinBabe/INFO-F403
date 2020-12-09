package compiler;

import parser.ParseTree;

public class CodeSymbol extends Symbol {

    private InstructionSymbol instruction;
    private CodeSymbol nextCode;

    void setNextCode(CodeSymbol symbol) {
        this.nextCode = nextCode;
    }

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }
}
