package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class CodeSymbol extends Symbol {

    /**
     * Reference to the line's {@link InstructionSymbol}.
     */
    private InstructionSymbol instruction;

    /**
     * Next code line might stay null
     */
    private CodeSymbol nextCode = null;

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        StringBuilder sb = new StringBuilder();
        sb.append(instruction);
        sb.append('\n');
        if (nextCode != null) {
            sb.append(nextCode.toLLVM());
        }
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<EQ>";
    }
}
