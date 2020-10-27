import java.io.*;
import java.util.ArrayList;

class Main {

	private String sourcePath;

	private ArrayList<Symbol> symbols = new ArrayList<>();

	private LexicalAnalyzer scanner;

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
				symbol = scanner.nextToken();
			}
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