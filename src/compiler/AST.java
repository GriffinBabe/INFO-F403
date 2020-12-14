package compiler;

import compiler.symbol.Symbol;
import parser.ParseTree;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Tree data structure that contains {@link Symbol} nodes, necessary for the program compilation.
 * The {@link AST} is built in the  {@link ASTBuilder} class, by parsing the {@link ParseTree} in
 * a recursive method.
 */
public class AST {

    /**
     * The value of the node. Will be later compiled in LLVM code.
     */
    private Symbol head = null;

    /**
     * The sub-trees of this three.
     */
    private ArrayList<AST> children = new ArrayList<>();

    /**
     * Default AST constructor.
     */
    public AST() {}

    /**
     * AST constructor, specify the head symbol.
     * @param symbol, the {@link #head} to set.
     */
    public AST(Symbol symbol) {
        this.head = symbol;
    }

    /**
     * If not null, add a children to the children list (sub-trees) and returns true.
     * If null, returns false
     * @param child, the nex sub-tree to add.
     * @return if a child has been added or not.
     */
    public boolean addChild(AST child) {
        if (child == null) {
            return false;
        }
        children.add(child);
        return true;
    }

    /**
     * Sets the {@link #head} of this tree node.
     * @param head, the head to set.
     */
    public void setHead(Symbol head) {
        this.head = head;
    }

    /**
     * @return the {@link #head} of this tree node.
     */
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

    /**
     * Removes a child from the sub-trees by index.
     */
    public void removeChild(int index) {
        children.remove(index);
    }

    /**
     * Swaps the left child with the right child.
     */
    public void swapChildren() {
        Collections.swap(children, 0, 1);
    }

    /**
     * @return if the {@link #children} list is not empty.
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Similar to the {@link ParseTree}, this returns a graphical presentation in LaTeX code of the AST.
     * This is not standalone, unlike to {@link #toLaTeX()}.
     *
     * @return the graphical presentation in LaTeX source code.
     */
    public String toForestPicture() {
        return "\\begin{forest}for tree={rectangle, draw, l sep=20pt}" + toLaTexTree() + ";\n\\end{forest}";
    }

    /**
     * Similar to the {@link ParseTree}, this returns a standalone graphical presentation in LaTeX source code of the AST
     * that uses the tikz package.
     * @return the complete LaTeX source code for a standalone compilation.
     */
    public String toLaTeX() {
        return "\\documentclass[border=5pt]{standalone}\n\n\\usepackage{tikz}\n\\usepackage{forest}\n\n\\begin{document}\n\n"
                + toForestPicture() + "\n\n\\end{document}\n%% Local Variables:\n%% TeX-engine: pdflatex\n%% End:";
    }

    /**
     * Used by {@link #toForestPicture()} to build the Tree graphical presentation in LaTeX source code.
     * @return part of the LaTeX source code.
     */
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
