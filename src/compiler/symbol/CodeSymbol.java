package compiler.symbol;

import compiler.CompilerTable;

/**
 * Chain of compiler instructions units.
 * Code symbols holds a reference to a {@link InstructionSymbol} and a reference to the next {@link CodeSymbol}.
 * The reference to the next {@link CodeSymbol} can be null if this is the last instruction of the block/program.
 */
public class CodeSymbol extends CompilerSymbol {

    /**
     * Instruction to be compiled.
     */
    private InstructionSymbol instruction;

    /**
     * Next instruction to be compiled (all instruction are chained).
     * Might be null if this is for example the last instruction.
     */
    private CodeSymbol nextCode;

    /**
     * Sets the next code symbol. Used by the {@link compiler.ASTBuilder}.
     * @param symbol, the next code symbol
     */
    public void setNextCode(CodeSymbol symbol) {
        this.nextCode = symbol;
    }

    /**
     * Sets the instruction symbol. Used by the {@link compiler.ASTBuilder}.
     * @param instruction, the instruction symbol
     */
    public void setInstruction(InstructionSymbol instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        sb.append(instruction.toLLVM(table, returnRegisters));
        if (nextCode != null) {
            sb.append(nextCode.toLLVM(table, returnRegisters));
        }
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Code>";
    }
}
