package util;

/**
 * Little and unorthodox (we should have prepared a grammar for that too :) ) command line parser.
 *
 * Will check in the command line parser for the "-v" (verbose) and "-wt output.tex"
 * (LaTeX {@link parser.ParseTree} output), and gather the Fortran-S source file.
 *
 * At least the Fortran-S source file must be specified.
 */
public class CommandLineParser {

    private String latexOutput = null;
    private boolean verbose = false;
    private String inputSource = null;

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
                default:
                    this.inputSource = str;
            }
        }
        // Problem if input source is still null
        if (inputSource == null) {
            System.err.println("Usage: java -jar Part2.jar [-v] [-wt output.tex] source.fs");
            System.exit(1);
        }
    }

    /**
     * Returns the path of the desired LaTeX output file.
     * @return null if no LaTeX output is desired, a path if the output IS desired.
     */
    public String latexOutput() {
        return latexOutput;
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
}
