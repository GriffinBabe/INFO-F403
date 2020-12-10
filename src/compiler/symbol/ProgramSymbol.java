package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

public class ProgramSymbol extends Symbol {

    // TODO Set hardcoded prefix
    private static String HARDCODED_PREFIX = "";

    // TODO set hardcoded suffix
    private static String HARDCODED_SUFFIX = "";

    /**
     * List of instructions to execute.
     */
    CodeSymbol code;

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        StringBuilder sb = new StringBuilder();
        sb.append(HARDCODED_PREFIX);
        sb.append(code.toLLVM());
        sb.append(HARDCODED_SUFFIX);
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Program>";
    }
}
