package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class CodeSymbol extends CompilerSymbol {

    private InstructionSymbol instruction;
    private CodeSymbol nextCode;

    public void setNextCode(CodeSymbol symbol) {
        this.nextCode = nextCode;
    }

    public void setInstruction(InstructionSymbol instruction) {
        this.instruction = instruction;
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
        return "<Code>";
    }
}
