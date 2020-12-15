package compiler.symbol;

import compiler.CompilerTable;

/**
 * While symbol. Holds a reference to the verified body, called when the condition is verified, and a reference to the
 * compare body.
 */
public class WhileSymbol extends InstructionSymbol {

    /**
     * Chain of instruction called upon verified condition.
     */
    CodeSymbol verifiedBody;

    /**
     * Comparison sybmol compile unit.
     */
    CompareSymbol compareSymbol;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String compareLabel = table.nextLabel();
        String bodyLabel = table.nextLabel();
        String endLabel = table.nextLabel();

        String compareReturn = table.nextRegister();

        // 1. Creates a new label and performs a comparison
        sb.append(compareLabel).append(":\n");
        sb.append(compareSymbol.toLLVM(table, compareReturn));
        // 2. jumps on the body symbol if the comparison is ok, jumps on the endlabel if not ok
        sb.append("br i1 ").append(compareReturn).append(", label %").append(bodyLabel).append(", label %")
                .append(endLabel).append("\n");

        // 3. Body label and code
        sb.append(bodyLabel).append(":\n");
        sb.append(verifiedBody.toLLVM(table));
        // 4. Jump back to comparison
        sb.append("br label %").append(compareLabel).append("\n");

        // 5. end label
        sb.append(endLabel).append(":\n");

        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<While>";
    }

    public void setVerifiedBody(CodeSymbol verifiedBody) {
        this.verifiedBody = verifiedBody;
    }

    public void setCompareSymbol(CompareSymbol compareSymbol) {
        this.compareSymbol = compareSymbol;
    }

}
