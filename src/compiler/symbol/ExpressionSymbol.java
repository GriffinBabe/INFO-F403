package compiler.symbol;

public abstract class ExpressionSymbol extends Symbol {

    protected ExpressionSymbol left;

    protected ExpressionSymbol right;

    public void setLeft(ExpressionSymbol left) {
        this.left = left;
    }

    public void setRight(ExpressionSymbol right) {
        this.right = right;
    }


}
