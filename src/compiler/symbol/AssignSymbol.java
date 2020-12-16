package compiler.symbol;

import compiler.CompilerTable;

public class AssignSymbol extends InstructionSymbol {

    private VariableSymbol variable = null;

    private ExpressionSymbol expression = null;

    public void setVariable(VariableSymbol symbol) {
        this.variable = symbol;
    }

    public void setExpression(ExpressionSymbol expression) {
        this.expression = expression;
    }

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        String varName = this.variable.getVariableName();
        if (!table.isAllocated(varName)) {
            table.setAllocated(varName);
            sb.append("%").append(varName).append(" = alloca i32\n");
        }
        if(this.expression instanceof NumberSymbol){
            sb.append("store i32 ").append(((NumberSymbol) this.expression).getValue().toString()).append(" , i32* ").append("%").append(varName).append("\n");
        }
        else {
            String arithRegister = table.nextRegister(); //register storing the arithmetic value
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
