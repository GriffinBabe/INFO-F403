package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class IfBlockSymbol extends Symbol {

    /**
     * Instruction chain that will be executed upon a verified condition
     */
    private CodeSymbol verifiedBody;

    /**
     * Might be null. Instruction chain that will be executed upon an unverified condition.
     */
    private CodeSymbol unverifiedBody;

    public void setVerifiedBody(CodeSymbol verifiedBody) {
        this.verifiedBody = verifiedBody;
    }

    public void setUnverifiedBody(CodeSymbol unverifiedBody) {
        this.unverifiedBody = unverifiedBody;
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
        return null;
    }
}
