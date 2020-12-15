package compiler.symbol;

import compiler.CompilerTable;

public class IfSymbol extends InstructionSymbol {

    /**
     * Symbol containing the verified or unverified block.
     */
    private IfBlockSymbol blocks;

    /**
     * The comparison is done in this symbol.
     */
    private CompareSymbol compare;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<If>";
    }


    public void setCompare(CompareSymbol compare) {
        this.compare = compare;
    }

    public void setBlocks(IfBlockSymbol blocks) {
        this.blocks = blocks;
    }
}
