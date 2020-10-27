import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Main {

	private String sourcePath;

	private ArrayList<Symbol> variables = new ArrayList<>();

	private LexicalAnalyzer scanner;

	private class CustomComparator implements Comparator<Symbol> {
		@Override
		public int compare(Symbol s1, Symbol s2) {
			return ((String)s1.getValue()).compareTo((String)s2.getValue());
		}
	}

	public Main(String sourcePath) {
		this.sourcePath = sourcePath;
		try {
			FileReader sourceFile = new FileReader(sourcePath);
			BufferedReader reader = new BufferedReader(sourceFile);
			this.scanner = new LexicalAnalyzer(reader);
		} catch (IOException e) {
			System.err.println("Couldn't load file: "+sourcePath);
			System.exit(-1);
		}
	}
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
			Collections.sort(variables,new CustomComparator());
			for(Symbol s : variables){
				System.out.println(s.getValue()+" "+s.getLine());
			}
			System.out.println();
		} catch (Exception e) { // IOException for stream exception or Error for parser exception.
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java -jar part1.jar <source file>");
			System.exit(1);
		}
		Main main = new Main(args[0]);
		main.printTable();
	}

}