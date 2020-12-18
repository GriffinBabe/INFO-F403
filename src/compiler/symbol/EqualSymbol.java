package compiler.symbol;

import compiler.CompilerTable;

/**
 * Checks if two variables are equals. The variables are set in the mother class {@link OperatorSymbol#left} and
 * {@link OperatorSymbol#right}.
 */
public class EqualSymbol extends CompareSymbol {

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, String...)}.
     * @param returnRegisters is expected to be the name of the return value.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        // 1. load the left variable in a temp variable
        // 2. load the right variable in a temp variable
        StringBuilder sb = new StringBuilder();

        String leftTemp = table.nextRegister();
        String rightTemp = table.nextRegister();
        sb.append(left.toLLVM(table, leftTemp));
        sb.append(right.toLLVM(table, rightTemp));

        // 3. checks if the return variable set, if not creates a new one from the table
        String retTemp;
        if (returnRegisters.length > 0) {
            retTemp = returnRegisters[0];
        }
        else {
            System.out.println("Warning: No return variable name specified in EqualsSymbol.");
            retTemp = table.nextRegister();
        }

        // 4. Append the comparison to the StringBuilder.
        // %returnTemp = icmp eq i32 %tempLeft, %tempRight
        System.out.println(leftTemp);
        System.out.println(rightTemp);
        sb.append(retTemp).append(" = icmp eq i32 ").append(leftTemp).append(", ").append(rightTemp).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<EQ>";
    }
}
