package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

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
     * Comparison symbol compile unit.
     */
    CompareSymbol compare;

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the multiplication result)
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        TempVariable compareLabel = table.nextLabel();
        TempVariable bodyLabel = table.nextLabel();
        TempVariable endLabel = table.nextLabel();


        // 1. Creates a new label and performs a comparison
        sb.append("br label %").append(compareLabel).append("\n");
        sb.append(compareLabel).append(":\n");

        TempVariable compareReturn = table.nextRegister();
        sb.append(compare.toLLVM(table, compareReturn));
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

    /**
     * Sets the while body root {@link CodeSymbol}.
     */
    public void setVerifiedBody(CodeSymbol verifiedBody) {
        this.verifiedBody = verifiedBody;
    }

    /**
     * Sets the comparison operator.
     */
    public void setCompare(CompareSymbol compare) {
        this.compare = compare;
    }

}
