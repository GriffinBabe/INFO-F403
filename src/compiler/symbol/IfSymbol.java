package compiler.symbol;

import compiler.CompilerTable;

/**
 * IfSymbol makes the comparison of two variables and then calls the appropriate code block.
 */
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
        StringBuilder sb = new StringBuilder();
        String compareReturn = table.nextRegister();
        sb.append(compare.toLLVM(table, compareReturn));

        String verifiedLabel = table.nextLabel();
        String unverifiedLabel = table.nextLabel();

        sb.append("br i1 ").append(compareReturn).append(", label %").append(verifiedLabel).append(", label %")
                .append(unverifiedLabel).append("\n");

        sb.append(blocks.toLLVM(table, verifiedLabel, unverifiedLabel));
        return sb.toString();
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
