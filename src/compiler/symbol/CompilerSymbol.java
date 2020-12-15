package compiler.symbol;

import compiler.CompilerTable;

/**
 * Compiler symbol class is the base class that represents
 */
public abstract class CompilerSymbol {

    /**
     * Returns the LLVM source code of the symbol by recursively calling the sub symbols.
     * @param table, tracks the used temporary variables (registers) and allocated variables.
     * @param returnRegisters, if some temporary variable of a child symbol is required in the parent symbol, then the
     *                         variable name name to use is retrieved from here instead of retrieving a new variable name
     *                         in the table. This parameter can be empty so the developer that implements this method
     *                         must be careful to predict both cases.
     * @return the final LLVM source code of this symbol and children symbols.
     */
    public abstract String toLLVM(CompilerTable table, String... returnRegisters);

    /**
     * Returns a simple name in LaTeX of the symbol. This is used to build the graphical presentation of the
     * {@link compiler.AST} in LaTeX under a tree shape.
     */
    public abstract String toTexString();
}
