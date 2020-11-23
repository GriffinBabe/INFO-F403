package parser;

/**
 * Custom exception class thrown in case of a detected syntax error.
 */
public class SyntaxException extends Exception {

    public SyntaxException(String errorMessage) {
        super(errorMessage);
    }

}
