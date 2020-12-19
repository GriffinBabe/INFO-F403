package util;

/**
 * Little and unorthodox (we should have prepared a grammar for that too :) ) command line parser.
 *
 * Will check in the command line parser for the "-v" (verbose), "-exec", "-wt output.tex" and "-wast output.tex".
 * (LaTeX {@link parser.ParseTree} output), gather the Fortran-S source file and gather the compiler LLVM output file.
 *
 * At least the Fortran-S source file must be specified.
 */
public class CommandLineParser {

    private String latexOutput = null;
    private boolean verbose = false;
    private boolean execute = false;
    private String inputSource = null;
    private String outputPath = null;
    private String astLatexOutput = null;

    /**
     * Parses the command line arguments. See {@link CommandLineParser}.
     * @param argSplit the program command line arguments.
     */
    public void parse(String[] argSplit) {
        for (int i = 0; i < argSplit.length; i++) {
            String str = argSplit[i];
            switch (str) {
                case "-v":
                    this.verbose = true;
                    break;
                case "-wt":
                    this.latexOutput = argSplit[++i];
                    break;
                case "-wast":
                    this.astLatexOutput = argSplit[++i];
                    break;
                case "-o":
                    this.outputPath = argSplit[++i];
                    break;
                case "-exec":
                    this.execute = true;
                    break;
                default:
                    this.inputSource = str;
                    break;
            }
        }
        // Problem if input source is still null
        if (inputSource == null) {
            System.err.println("Usage: java -jar part3.jar [-v] [-exec] [-wt parsetree.tex] [-wast asttree.tex] [-o as_code.ll] <source.fs>");
            System.exit(1);
        }
    }

    /**
     * Returns the path of the desired LaTeX output file.
     * @return null if no LaTeX output is desired, a path if the output IS desired.
     */
    public String getLatexOutput() {
        return latexOutput;
    }

    /**
     * Returns the path of the desired LaTeX output file for the Abstract Syntax Tree {@link compiler.AST}.
     * @return null if not LaTeX output is desired, a path if the output IS desired.
     */
    public String getAstLatexOutput() {
        return astLatexOutput;
    }

    /**
     * @return true if we want a verbose stdout output, false for a standard one.
     */
    public boolean isVerbose() {
        return verbose;
    }

    /**
     * @return the specified input source file.
     */
    public String getInputSource() {
        return inputSource;
    }

    /**
     * @return if specified, returns the path where to write the LLVM code to a file, else returns null.
     */
    public String getOutputPath() {
        return outputPath;
    }

    /**
     * @return true if the program must be executed internally.
     */
    public boolean isExecute() {
        return execute;
    }
}
