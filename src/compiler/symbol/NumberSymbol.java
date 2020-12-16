package compiler.symbol;

import compiler.CompilerTable;

import java.util.Arrays;

public class NumberSymbol extends ExpressionSymbol {

    private Integer value;

    public NumberSymbol(Integer value) {
        this.value = value;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        sb.append(returnRegisters[0]).append(" = alloca i32\n");
        sb.append("store i32 ").append(this.value).append(", i32* ").append(returnRegisters[0]).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Num: " + value + ">";
    }

    public Integer getValue(){ return this.value;}
}
