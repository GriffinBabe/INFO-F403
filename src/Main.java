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
//		Symbol symbol = scanner.next_token();
//		while (scanner.next_token())
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