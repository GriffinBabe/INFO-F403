package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class IfSymbol extends Symbol {

    /**
     * Symbol containing the verified or unverified block.
     */
    private IfBlockSymbol blocks;

    /**
     * The comparison is done in this symbol.
     */
    private CompareSymbol compare;

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }

    @Override
    public String toTexString() {
        return null;
    }


    public void setCompare(CompareSymbol compare) {
        this.compare = compare;
    }

    public void setBlocks(IfBlockSymbol blocks) {
        this.blocks = blocks;
    }
}
