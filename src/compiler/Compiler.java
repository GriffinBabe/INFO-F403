package compiler;

import parser.ParseTree;
import util.LatexWriter;

/**
 * Compilation step main class. This will first build the {@link AST} from the {@link ASTBuilder} static methods, then
 * create the LLVM source code from compiler symbols methods.
 */
public class Compiler {

    private AST astTree = null;

    boolean built = false;

    /**
     * Builds the abstract syntax of the compiler (necessary before calling the {@link #saveTree(String)}
     * or the {@link #compile()} methods).
     */
    public void build(ParseTree tree) {
        this.astTree = ASTBuilder.buildAST(tree);
        this.built = true;
    }

    /**
     * Compiles the program into LLVM code from the {@link #astTree}.
     * @return the LLVM source code.
     */
    public String compile() {
        if (!built) {
            throw new RuntimeException("Cannot compile before building the AST. Please call the build method before");
        }
        else{
            String file = this.astTree.getHead().toLLVM(new CompilerTable(), "patate");
            System.out.println(file);
        }
        return null;
    }

    /**
     * Saves the {@link #astTree} into a LaTeX format. Source code is generated from {@link AST#toLaTeX()}.
     * @param path the path where to save the source code.
     */
    public void saveTree(String path) {
        if (!built) {
            throw new RuntimeException("Cannot save the AST before building it. Please call the build method before.");
        }
        LatexWriter latexWriter = new LatexWriter(path);
        latexWriter.write(astTree.toLaTeX());
    }
}
