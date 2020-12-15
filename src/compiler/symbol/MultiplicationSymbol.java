package compiler.symbol;

import compiler.CompilerTable;

public class MultiplicationSymbol extends OperatorSymbol {

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String elem1; String elem2;
        if(left instanceof NumberSymbol){
            elem1 = ((NumberSymbol) left).getValue().toString();
        }
        else{
            elem1 = table.nextRegister();
            sb.append(left.toLLVM(table,elem1));
        }
        if(right instanceof NumberSymbol){
            elem2 = ((NumberSymbol) right).getValue().toString();
        }
        else{
            elem2 = table.nextRegister();
            sb.append(right.toLLVM(table,elem2));
        }
        sb.append(returnRegisters[0]).append(" = mul i32 ").append(elem1).append(" , ").append(elem2).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Mult>";
    }
}
