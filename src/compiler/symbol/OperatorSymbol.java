package compiler.symbol;

/**
 * Base class for operators such as {@link CompareSymbol}, {@link AdditionSymbol}, {@link MultiplicationSymbol} etc...
 */
public abstract class OperatorSymbol extends ExpressionSymbol {

    /**
     * Left side expression.
     */
    protected ExpressionSymbol left;

    /**
     * Right side expression
     */
    protected ExpressionSymbol right;

    /**
     * Sets the {@link #left} expression.
     * @param left the {@link ExpressionSymbol} to set on the left.
     */
    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }

    /**
     * Sets the {@link #right} expression.
     * @param right the {@link ExpressionSymbol} to set on the right.
     */
    public void setRight(ExpressionSymbol right) {
        this.right = right;
    }

}
