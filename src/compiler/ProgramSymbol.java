package compiler;

import parser.ParseTree;

import java.util.ArrayList;

public class ProgramSymbol extends Symbol {

    // TODO Set hardcoded prefix
    private static String HARDCODED_PREFIX = "";

    // TODO set hardcoded suffix
    private static String HARDCODED_SUFFIX = "";

    /**
     * List of instructions to execute.
     */
    ArrayList<Symbol> instructions;

    @Override
    public void set(ParseTree tree) {
        
    }

    @Override
    public String toLLVM() {
        StringBuilder sb = new StringBuilder();
        sb.append(HARDCODED_PREFIX);
        for (Symbol s : this.instructions) {
            sb.append(s.toLLVM());
        }
        sb.append(HARDCODED_SUFFIX);
        return sb.toString();
    }
}
