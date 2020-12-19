package compiler;

import parser.ParseTree;
import util.OutputFileWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Compilation step main class. This will first build the {@link AST} from the {@link ASTBuilder} static methods, then
 * create the LLVM source code from compiler symbols methods.
 */
public class Compiler {

    private AST astTree = null;
    private String compiledCode = null;

    private boolean built = false;
    private boolean compiled = false;

    private static String TEMP_PATH = "as_code.temp";

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
        this.compiledCode = this.astTree.getHead().toLLVM(new CompilerTable());
        this.compiled = true;
        return this.compiledCode;
    }

    /**
     * Saves the {@link #astTree} into a LaTeX format. Source code is generated from {@link AST#toLaTeX()}.
     * @param path the path where to save the source code.
     */
    public void saveTree(String path) {
        if (!built) {
            throw new RuntimeException("Cannot save the AST before building it. Please call the build method before.");
        }
        OutputFileWriter outputFileWriter = new OutputFileWriter(path);
        outputFileWriter.write(astTree.toLaTeX());
    }

    public void saveOutput(String path) {
        if (!compiled) {
            throw new RuntimeException("Cannot save the output LLVM code before compiling it. Please call the compile" +
                    "method before.");
        }
        OutputFileWriter outputFileWriter = new OutputFileWriter(path);
        outputFileWriter.write(this.compiledCode);
    }

    public int execute() throws IOException, InterruptedException {
        if (!compiled) {
            throw new RuntimeException("Cannot execute the LLVM code before compiling it. Please call the compile" +
                    "method before.");
        }
        this.saveOutput(TEMP_PATH);
        ProcessBuilder processBuilder = new ProcessBuilder().inheritIO()
                .command("lli", TEMP_PATH).redirectErrorStream(true);
        Process process = processBuilder.start();
        process.waitFor();
        File file = new File(TEMP_PATH);
        boolean deleted = file.delete();
        if (!deleted) {
            throw new IOException("Couldn't delete temporary file: "+TEMP_PATH);
        }
        return process.exitValue();
    }
}
