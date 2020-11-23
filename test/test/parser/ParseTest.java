package test.parser;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntaxException;
import scanner.Scanner;

import static org.junit.jupiter.api.Assertions.fail;


public class ParseTest {
	@Test
	public void testFactorial() throws SyntaxException {
		Scanner scanner = new Scanner("test/test/parser/Factorial.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testAmbiguous() throws SyntaxException {
		Scanner scanner = new Scanner("test/test/parser/Ambiguous.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testUnary() throws SyntaxException {
		Scanner scanner = new Scanner("test/test/parser/Unary.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void testNestedWhile() throws SyntaxException {
		Scanner scanner = new Scanner("test/test/parser/NestedWhile.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());
		parser.parseSequence();
	}
	@Test
	public void unfinishedProgram() {
		Scanner scanner = new Scanner("test/test/parser/UnfinishedProgram.fs");
		scanner.printTable();
		Parser parser = new Parser(scanner.getVariables());

		// This test waits for an exception to be thrown
		try {
			parser.parseSequence();
			// at this point no exception was thrown, the test failed
			fail();
		}
		catch (SyntaxException e) {
			System.out.println("Exception: "+e.getMessage());
			// a syntax exception was expected
		}
	}
}
