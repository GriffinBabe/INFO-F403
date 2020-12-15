package compiler.symbol;

import compiler.CompilerTable;

public class WhileSymbol extends InstructionSymbol {

    CodeSymbol verifiedBody;

    CompareSymbol compareSymbol;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
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
