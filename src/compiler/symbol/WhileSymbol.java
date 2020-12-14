package compiler.symbol;

import compiler.CompilerTable;
import parser.ParseTree;

import java.util.ArrayList;

public class WhileSymbol extends InstructionSymbol {

    CodeSymbol verifiedBody;

    CompareSymbol compareSymbol;

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

    public void setVerifiedBody(CodeSymbol verifiedBody) {
        this.verifiedBody = verifiedBody;
    }

    public void setCompareSymbol(CompareSymbol compareSymbol) {
        this.compareSymbol = compareSymbol;
    }

}
