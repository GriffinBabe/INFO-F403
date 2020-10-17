import java.util.ArrayList;

class Main {

	private String sourceFile;

	private ArrayList<Symbol> symbols = new ArrayList<>();

	public Main(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java -jar part1.jar <source file>");
			System.exit(1);
		}
		new Main(args[0]);
	}

}