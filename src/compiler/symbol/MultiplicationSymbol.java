package compiler.symbol;

import compiler.CompilerTable;

public class MultiplicationSymbol extends OperatorSymbol {

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String elem1 = table.nextRegister();
        String elem2 = table.nextRegister();
        sb.append(left.toLLVM(table,elem1)).append(right.toLLVM(table,elem2));
        sb.append(returnRegisters[0]).append(" = alloca i32\n");
        sb.append(returnRegisters[0]).append(" = mul i32 ").append(elem1).append(" , ").append(elem2).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Mult>";
    }
}
