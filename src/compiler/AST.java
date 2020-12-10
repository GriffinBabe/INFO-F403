package compiler;

import compiler.symbol.Symbol;
import parser.ParseTree;

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
        try {
            return children.get(0);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
    }

    public AST getRight() {
        try {
            return children.get(1);
        }
        catch (IndexOutOfBoundsException exception) {
            return null;
        }
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
