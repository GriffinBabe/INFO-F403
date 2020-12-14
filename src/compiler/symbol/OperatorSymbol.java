package compiler.symbol;

public abstract class OperatorSymbol extends ExpressionSymbol {
    protected ExpressionSymbol left;

    protected ExpressionSymbol right;

    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }

    public void setRight(ExpressionSymbol right) {
        this.right = right;
    }

}
