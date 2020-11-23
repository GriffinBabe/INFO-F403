package util;

public class CommandLineParser {

    private String latexOutput = null;
    private boolean verbose = false;
    private String inputSource = null;

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
            System.exit(-1);
        }
    }

    public String latexOutput() {
        return latexOutput;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public String getInputSource() {
        return inputSource;
    }
}
