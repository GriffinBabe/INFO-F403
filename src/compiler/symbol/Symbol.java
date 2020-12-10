package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public abstract class Symbol {

    /**
     * Sets all the needed symbols from the {@link ParseTree}.
     * @param tree
     */
    public abstract void set(ParseTree tree, CompilerTable table);

    /**
     * Returns the LLVM
     * @return
     */
    public abstract String toLLVM();

    public abstract String toTexString();
}
