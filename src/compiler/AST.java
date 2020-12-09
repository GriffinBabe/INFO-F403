package compiler;

import java.util.ArrayList;

public class AST {

    private Symbol head = null;

    private ArrayList<AST> children = new ArrayList<>();

    public AST() {

    }

    public AST(Symbol symbol) {
        this.head = symbol;
    }

    public boolean addChild(AST child) {
        if (child == null) {
            return false;
        }
        children.add(child);
        return true;
    }

    public void setHead(Symbol head) {
        this.head = head;
    }

    public Symbol getHead() {
        return head;
    }

    public AST getLeft() {
        return children.get(0);
    }

    public AST getRight() {
        return children.get(1);
    }
}
