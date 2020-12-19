package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Performs an addition between two {@link ExpressionSymbol} and saves the result in the specified {@link TempVariable}.
 */
public class AdditionSymbol extends OperatorSymbol {

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the addition result)
     * @return the compiled code.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        TempVariable elem1; TempVariable elem2;
        if(left instanceof NumberSymbol){
            elem1 = new TempVariable(((NumberSymbol) left).getValue());
        }
        else{
            elem1 = table.nextRegister();
            sb.append(left.toLLVM(table,elem1));
        }
        if(right instanceof NumberSymbol){
            elem2 = new TempVariable(((NumberSymbol) right).getValue());
        }
        else{
            elem2 = table.nextRegister();
            sb.append(right.toLLVM(table,elem2));
        }
        sb.append(returnRegisters[0]).append(" = add i32 ").append(elem1).append(" , ").append(elem2).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Add>";
    }
}
