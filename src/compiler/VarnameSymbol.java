package compiler;

import parser.ParseTree;

public class VarnameSymbol extends Symbol {

    private String varname;

    @Override
    public void set(ParseTree tree, CompilerTable table) {
        this.varname = tree.getLabel().getVarname();
    }

    @Override
    public String toLLVM() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.varname);
        sb.append(" ");
        return sb.toString();
    }
}
