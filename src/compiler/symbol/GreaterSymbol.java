package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Performs a comparison between two {@link ExpressionSymbol} and returns the value into a specified {@link TempVariable}.
 */
public class GreaterSymbol extends CompareSymbol {

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the comparison result)
     * @return the compiled code.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        // 1. load the left variable in a temp variable
        // 2. load the right variable in a temp variable
        StringBuilder sb = new StringBuilder();

        TempVariable leftTemp, rightTemp;
        if (left instanceof NumberSymbol) {
            leftTemp = new TempVariable(((NumberSymbol) left).getValue());
        }
        else {
            leftTemp = table.nextRegister();
            sb.append(left.toLLVM(table, leftTemp));
        }
        if (right instanceof NumberSymbol) {
            rightTemp = new TempVariable(((NumberSymbol) right).getValue());
        }
        else {
            rightTemp = table.nextRegister();
            sb.append(right.toLLVM(table, rightTemp));
        }
        // 3. checks if the return variable set, if not creates a new one from the table
        TempVariable retTemp;
        if (returnRegisters.length > 0) {
            retTemp = returnRegisters[0];
        }
        else {
            retTemp = table.nextRegister();
        }

        // 4. Append the comparison to the StringBuilder.
        // %returnTemp = icmp eq i32 %tempLeft, %tempRight
        sb.append(retTemp).append(" = icmp sgt i32 ").append(leftTemp).append(", ").append(rightTemp).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<GT>";
    }
}
