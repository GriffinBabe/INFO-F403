import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Scanner class. Will initialize a stream to the source file and initialize the {@link LexicalAnalyzer} at construction.
 *
 * All the symbols scanned by the lexical analyzer can be printed with the {@link #printTable()} class method.
 */
public class Scanner {


    /**
     * ArrayList containing all the Symbol that were identified as variable names.
     */
    private ArrayList<Symbol> variables = new ArrayList<>();

    /**
     * @return symbol list
     */
    public ArrayList<Symbol> getVariables() {
        return variables;
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
    }

    /**
     * Prints a {@link Symbol} table as specified in the assignments.
     *
     * If a wrong syntax is detected, a exception will be thrown
     * by {@link LexicalAnalyzer}. The exception trace will be printed
     * in the standard error print and the program will exit with the
     * -1 state. The same happens for a stream exception.
     */
    void printTable() {
        try {
            Symbol symbol = scanner.nextToken();
            while (symbol != null) {
                System.out.println(symbol);
                if (symbol.getType() == LexicalUnit.VARNAME){
                    boolean matched = false;
                    for(Symbol s : variables){
                        if (((String)(s.getValue())).equals((String)(symbol.getValue()))){
                            matched = true;
                        }
                    }
                    if (!matched) {
                        variables.add(symbol);
                    }

                }
                symbol = scanner.nextToken();
            }
            System.out.println("\nVariables");
            Collections.sort(variables,new Scanner.CustomComparator());
            for(Symbol s : variables){
                System.out.println(s.getValue()+" "+s.getLine());
            }
            System.out.println();
        } catch (IOException e) { // IOException for stream exception
            e.printStackTrace();
            System.exit(-1);
        } catch (Error e) { // Syntax errors thrown by JFlex
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
