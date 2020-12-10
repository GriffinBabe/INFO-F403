package compiler;

import compiler.symbol.ProgramSymbol;
import parser.ParseTree;

public class Compiler {

    private ProgramSymbol program;

    boolean built = false;

    public Compiler() {
    }

    /**
     * Builds the tree and sets the adapted symbols to compile the program
     * @param tree
     */
    public AST build(ParseTree tree) {
        ASTBuilder builder = new ASTBuilder();
        AST astTree = builder.buildAST(tree);
        return astTree;
    }

    public void compile() throws RuntimeException {
        if (!built) {
            throw new RuntimeException("Cannot compile before building the AST.");
        }

    }
}
