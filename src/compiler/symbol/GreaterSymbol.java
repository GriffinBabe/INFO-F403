package compiler.symbol;

import compiler.CompilerTable;

public class GreaterSymbol extends CompareSymbol {

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
            retTemp = table.nextRegister();
        }

        // 4. Append the comparison to the StringBuilder.
        // %returnTemp = icmp eq i32 %tempLeft, %tempRight
        sb.append(retTemp).append(" = icmp gt i32 ").append(leftTemp).append(", ").append(rightTemp).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<GT>";
    }
}
