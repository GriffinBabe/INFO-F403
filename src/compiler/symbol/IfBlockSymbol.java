package compiler.symbol;

import compiler.CompilerTable;

/**
 * IfBlockSymbol holds a reference for the verified block of code and the unverified block of code of a IF instruction.
 */
public class IfBlockSymbol extends CompilerSymbol {

    /**
     * Instruction chain that will be executed upon a verified condition
     */
    private CodeSymbol verifiedBody;

    /**
     * Might be null. Instruction chain that will be executed upon an unverified condition.
     */
    private CodeSymbol unverifiedBody;

    /**
     * Sets the verified chain of instructions.
     * @param verifiedBody the chain of instruction to set.
     */
    public void setVerifiedBody(CodeSymbol verifiedBody) {
        this.verifiedBody = verifiedBody;
    }

    /**
     * Sets the unverified chain of instructions.
     * @param unverifiedBody the chain of instruction to set.
     */
    public void setUnverifiedBody(CodeSymbol unverifiedBody) {
        this.unverifiedBody = unverifiedBody;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, String...)}.
     * @param returnRegisters is expected to have the name of the label for the verified code and the name of the label
     *                        for the unverified code.
     */
    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        // doneBody is the body after the verified and unverified bodies.
        String verifiedLabel, unverifiedLabel, doneBody;
        if (returnRegisters.length > 2) {
            verifiedLabel = returnRegisters[0];
            unverifiedLabel = returnRegisters[1];
        }
        else {
            System.out.println("Warning: No label names specified in IfBlockSymbol.");
            verifiedLabel = table.nextLabel();
            unverifiedLabel = table.nextLabel();
        }
        doneBody = table.nextLabel();

        // verified body
        StringBuilder sb = new StringBuilder();
        sb.append(verifiedLabel).append(":\n");
        sb.append(verifiedBody.toLLVM(table));
        sb.append("br label %").append(doneBody).append("\n");

        // unverified body
        sb.append(unverifiedLabel).append(":\n");
        sb.append(unverifiedBody.toLLVM(table));

        // finished body
        sb.append(doneBody).append(":\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<IfBlock>";
    }
}
