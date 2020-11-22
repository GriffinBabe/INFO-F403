import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class parse {
	@Test
	public void testFactorial() throws SyntaxException {
		Scanner scanner = new Scanner("test/parse_test/Factorial.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testAmbigu() throws SyntaxException {
		Scanner scanner = new Scanner("test/parse_test/Ambi.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testUnary() throws SyntaxException {
		Scanner scanner = new Scanner("test/parse_test/Unary.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testNestedWhile() throws SyntaxException {
		Scanner scanner = new Scanner("test/parse_test/NestedWhile.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
}
