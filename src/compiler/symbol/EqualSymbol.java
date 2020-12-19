package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Checks if two variables are equals. The variables are set in the mother class {@link OperatorSymbol#left} and
 * {@link OperatorSymbol#right}.
 */
public class EqualSymbol extends CompareSymbol {

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param returnRegisters the list of registers to use (expected to hold the {@link TempVariable} used as return)
     * @param table, reference to the {@link CompilerTable}.
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
            System.out.println("Warning: No return variable name specified in EqualsSymbol.");
            retTemp = table.nextRegister();
        }

        // 4. Append the comparison to the StringBuilder.
        // %returnTemp = icmp eq i32 %tempLeft, %tempRight
        sb.append(retTemp).append(" = icmp eq i32 ").append(leftTemp).append(", ").append(rightTemp).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<EQ>";
    }
}
