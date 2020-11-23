package scanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class will initialize a stream to the source file and initialize the {@link LexicalAnalyzer} at construction.
 *
 * All the symbols scanned by the lexical analyzer can be printed with the {@link #printTable()} class method.
 */
public class Scanner {


    /**
     * ArrayList containing all the {@link Symbol} that were identified as variable names (identifiers).
     */
    private ArrayList<Symbol> variables = new ArrayList<>();

    /**
     * Symbol list of token for parser use.
     */
    private ArrayList<Symbol> tokenList = new ArrayList<>();

    /**
     * @return Symbol list
     */
    public ArrayList<Symbol> getVariables() {
        return tokenList;
    }

    /**
     * The {@link LexicalAnalyzer} generated by JFlex.
     */
    private LexicalAnalyzer scanner;

    /**
     * Custom comparator to sort the variables list alphabetically.
     */
    private class CustomComparator implements Comparator<Symbol> {
        @Override
        public int compare(Symbol s1, Symbol s2) {
            return ((String)s1.getValue()).compareTo((String)s2.getValue());
        }
    }

    /**
     * Main function constructor. Initializes a stram to the source file
     * and initializes the {@link LexicalAnalyzer}.
     *
     * @param sourcePath the path to the Fortran-S source file.
     */
    public Scanner(String sourcePath) {
        try {
            // Open a streams to our source file
            FileReader sourceFile = new FileReader(sourcePath);
            BufferedReader reader = new BufferedReader(sourceFile);
            this.scanner = new LexicalAnalyzer(reader);
        } catch (IOException e) {
            System.err.println("Couldn't load file: "+sourcePath);
            System.exit(-1);
        }
        this.scan();
    }

    /**
     * Prepares a {@link Symbol} table as specified in the assignments.
     *
     * If a wrong syntax is detected, a exception will be thrown
     * by {@link LexicalAnalyzer}. The exception trace will be printed
     * in the standard error print and the program will exit with the
     * -1 state. The same happens for a stream exception.
     */
    private void scan() {
        boolean reachedEndOfProgram = false;
        try {
            Symbol symbol = scanner.nextToken();
            while (symbol != null) {
                if (symbol.getType() == LexicalUnit.VARNAME) {
                    checkVariable(symbol);
                }
                else if (symbol.getType() == LexicalUnit.ENDPROG) {
                    reachedEndOfProgram = true;
                }
                else if (symbol.getType() == LexicalUnit.ENDLINE && reachedEndOfProgram) {
                    // Don't add the symbol to the token list.
                    symbol = scanner.nextToken();
                    continue;
                }
                tokenList.add(symbol);
                symbol = scanner.nextToken();
            }
            tokenList.add(new Symbol(LexicalUnit.EOS));
        } catch (IOException | Error e) {
            System.err.println("Couldn't scan source file: "+e.getMessage());
            System.exit(-1);
        }

    }

    /**
     * Checks if the variable is not already registered. If not, then adds it to the {@link #variables} list.
     * @param symbol
     */
    private void checkVariable(Symbol symbol) {
        boolean matched = false;
        for(Symbol s : variables){
            if (((String)(s.getValue())).equals((String)(symbol.getValue()))) {
                matched = true;
                break;
            }
        }
        if (!matched) {
            variables.add(symbol);
        }
    }

    /**
     * Prints a {@link Symbol} table as specified in the assignments.
     *
     * If a wrong syntax is detected, a exception will be thrown
     * by {@link LexicalAnalyzer}. The exception trace will be printed
     * in the standard error print and the program will exit with the
     * -1 state. The same happens for a stream exception.
     */
    public void printTable() {
        for (Symbol symbol : tokenList) {
            System.out.println(symbol);
        }
        System.out.println("\nVariables");
        Collections.sort(variables,new Scanner.CustomComparator());
        for(Symbol s : variables){
            System.out.println(s.getValue()+" "+s.getLine());
        }
        System.out.println();
    }

}
