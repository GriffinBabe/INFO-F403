package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

import java.util.ArrayList;

public class WhileSymbol extends Symbol {

    private ArrayList<InstructionSymbol> body;

    @Override
    public void set(ParseTree tree, CompilerTable table) {

    }

    @Override
    public String toLLVM() {
        return null;
    }

    @Override
    public String toTexString() {
        return "<While>";
    }
}
