package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

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

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use.
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        TempVariable compareReturn = table.nextRegister();
        sb.append(compare.toLLVM(table, compareReturn));

        TempVariable verifiedLabel = table.nextLabel();
        TempVariable unverifiedLabel = table.nextLabel();

        sb.append("br i1 ").append(compareReturn).append(", label %").append(verifiedLabel).append(", label %")
                    .append(unverifiedLabel).append("\n");

        sb.append(blocks.toLLVM(table, verifiedLabel, unverifiedLabel));

        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<If>";
    }


    /**
     * Sets the comparison.
     * @param compare a {@link CompareSymbol}.
     */
    public void setCompare(CompareSymbol compare) {
        this.compare = compare;
    }

    /**
     * Sets the {@link IfBlockSymbol}, containing the code blocks to execute upon verification or non-verification.
     * @param blocks a {@link IfBlockSymbol}.
     */
    public void setBlocks(IfBlockSymbol blocks) {
        this.blocks = blocks;
    }
}
