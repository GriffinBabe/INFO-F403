package compiler;

import parser.ParseTree;
import parser.Variable;

public abstract class Symbol {

    /**
     * Sets all the needed symbols from the {@link ParseTree}.
     * @param tree
     */
    public abstract void set(ParseTree tree);

    /**
     * Returns the LLVM
     * @return
     */
    public abstract String toLLVM();

    /**
     * Returns the corresponding {@link Symbol} from the given {@link Variable}
     */
    public static Symbol fromVariable(Variable var) {
        // TODO: Hard code this once every symbol has been coded
        return null;
    }
}
