package compiler.symbol;

import compiler.CompilerTable;

public class IfBlockSymbol extends CompilerSymbol {

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
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        return null;
    }

    @Override
    public String toTexString() {
        return "<IfBlock>";
    }
}
