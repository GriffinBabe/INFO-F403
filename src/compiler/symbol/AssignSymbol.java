package compiler.symbol;

import compiler.CompilerTable;
import compiler.TempVariable;

/**
 * Will assign the value of a temporary register to a memory variable. If the variable hasn't been initialized yet,
 * allocates it in the memory.
 */
public class AssignSymbol extends InstructionSymbol {

    private VariableSymbol variable = null;

    private ExpressionSymbol expression = null;

    public void setVariable(VariableSymbol symbol) {
        this.variable = symbol;
    }

    public void setExpression(ExpressionSymbol expression) {
        this.expression = expression;
    }

    /**
     * See {@link CompilerSymbol#toLLVM(CompilerTable, TempVariable...)}.
     * @param table, tracks the used temporary variables (registers) and allocated variables.
     * @param returnRegisters, the list of registers to use (not used in that case).
     * @return the compiled code.
     */
    @Override
    public String toLLVM(CompilerTable table, TempVariable... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String varName = this.variable.getVariableName();
        if (!table.isAllocated(varName)) {
            table.setAllocated(varName);
            sb.append("%").append(varName).append(" = alloca i32, align 4\n");
        }
        if(this.expression instanceof NumberSymbol){
            sb.append("store i32 ").append(((NumberSymbol) this.expression).getValue().toString()).append(" , i32* ").append("%").append(varName).append("\n");
        }
        else {
            TempVariable arithRegister = table.nextRegister(); //register storing the arithmetic value
            sb.append(this.expression.toLLVM(table, arithRegister)); //arithmetic expression
            sb.append("store i32 ").append(arithRegister).append(" , i32* ").append("%").append(varName).append("\n");
            // assign the arithmetic value to the variable memory space
        }

        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Assign>";
    }
}
