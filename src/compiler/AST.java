package compiler;

import compiler.symbol.Symbol;
import parser.ParseTree;

import java.util.ArrayList;
import java.util.Collections;

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

    /**
     * Returns the first subtree.
     * In the final form of the AST there are at most 2 children per node.
     * @return the first subtree in the {@link #children} list, or null if it doesn't exists.
     */
    public AST getLeft() {
        try {
            return children.get(0);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    /**
     * Returns the second subtree.
     * In the final form of the AST there are at most 2 children per node.
     * @return the second subtree in the {@link #children} list, on null if it doesn't exists.
     */
    public AST getRight() {
        try {
            return children.get(1);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    /**
     * Returns the third subtree.
     * This is only used in the {@link ASTBuilder} class as the final form of the AST holds maximum 2 children per node.
     * @return the third subtree in the {@link #children} list, or null if doesn't exists.
     */
    public AST getThird() {
        try {
            return children.get(2);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    public void removeChild(int index) {
        children.remove(index);
    }

    /**
     * Swapes the left child with the right child.
     */
    public void swapChildren() {
        Collections.swap(children, 0, 1);
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public String toForestPicture() {
        return "\\begin{forest}for tree={rectangle, draw, l sep=20pt}" + toLaTexTree() + ";\n\\end{forest}";
    }

    public String toLaTeX() {
        return "\\documentclass[border=5pt]{standalone}\n\n\\usepackage{tikz}\n\\usepackage{forest}\n\n\\begin{document}\n\n"
                + toForestPicture() + "\n\n\\end{document}\n%% Local Variables:\n%% TeX-engine: pdflatex\n%% End:";
    }

    public String toLaTexTree(){
        StringBuilder treeTeX = new StringBuilder();
        treeTeX.append("[");
        treeTeX.append("{" + head.toTexString() + "}");
        treeTeX.append(" ");

        for (AST child : children) {
            treeTeX.append(child.toLaTexTree());
        }
        treeTeX.append("]");
        return treeTeX.toString();
    }
}
