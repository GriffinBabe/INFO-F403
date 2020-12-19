package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Unary symbol, will load a {@link ExpressionSymbol} value and multiply it by -1.
 */
public class UnarySymbol extends ExpressionSymbol {

    /**
     * The expression to inverse.
     */
    protected ExpressionSymbol left;

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the multiplication result)
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        if (left instanceof NumberSymbol) {
            TempVariable numberValue = new TempVariable(((NumberSymbol) left).getValue());
            sb.append(returnRegisters[0]).append(" = mul i32 ").append("-1").append(", ")
                    .append(numberValue).append("\n");
        }
        else {
            TempVariable elem1 = table.nextRegister();
            // determines what's right of the unary minus and multiply it by -1
            sb.append(left.toLLVM(table,elem1));
            sb.append(returnRegisters[0]).append(" = mul i32 ").append("-1").append(" , ").append(elem1).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Unary>";
    }

    /**
     * Sets the {@link #left} expression to set.
     * @param left a {@link ExpressionSymbol}.
     */
    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }
}
