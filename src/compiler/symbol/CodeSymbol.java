package compiler.symbol;

import compiler.CompilerTable;

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
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<Code>";
    }
}
