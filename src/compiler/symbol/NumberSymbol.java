package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Holds a literal number. Some symbol will call the {@link NumberSymbol#toLLVM(CompilerTable, TempVariable...)} method,
 * but it most case the direct {@link #value} will be retrieved and used into other symbols.
 */
public class NumberSymbol extends ExpressionSymbol {

    /**
     * The literal int32 value.
     */
    private Integer value;

    public NumberSymbol(Integer value) {
        this.value = value;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, reference to the {@link CompilerTable}.
     * @param returnRegisters, the list of registers to use (in that case in which register to save the multiplication result)
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        sb.append(returnRegisters[0]).append(" = alloca i32\n");
        sb.append("store i32 ").append(this.value).append(", i32* ").append(returnRegisters[0]).append("\n");
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Num: " + value + ">";
    }

    /**
     * Returns the Integer {@link #value}.
     * @return
     */
    public Integer getValue(){ return this.value;}
}
