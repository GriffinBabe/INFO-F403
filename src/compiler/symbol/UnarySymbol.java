package compiler.symbol;

import compiler.CompilerTable;

public class UnarySymbol extends ExpressionSymbol {

    protected ExpressionSymbol left;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        if (left instanceof NumberSymbol) {
            String numberValue = ((NumberSymbol) left).getValue().toString();
            sb.append(returnRegisters[0]).append(" = mul i32 ").append("-1").append(", ")
                    .append(((NumberSymbol) left).getValue().toString()).append("\n");
        }
        else {
            String elem1 = table.nextRegister();
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

    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }
}
