package parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A skeleton class to represent parse trees. The arity is not fixed: a node can
 * have 0, 1 or more children. Trees are represented in the following way: Tree
 * :== Symbol * List<Tree> In other words, trees are defined recursively: A tree
 * is a root (with a label of type Symbol) and a list of trees children. Thus, a
 * leave is simply a tree with no children (its list of children is empty). This
 * class can also be seen as representing the Node of a tree, in which case a
 * tree is simply represented as its root.
 * 
 * @author Léo Exibard, Sarah Winter
 */

public class ParseTree {

    /**
     * The head of this tree/sub-tree
     */
    private Variable label; // The label of the root of the tree

    /**
     * The child sub-trees.
     */
    private List<ParseTree> children; // Its children, which are trees themselves

    /**
     * Creates a singleton tree with only a root labeled by lbl.
     * 
     * @param lbl The label of the root
     */
    public ParseTree(Variable lbl) {
        this.label = lbl;
        this.children = new ArrayList<ParseTree>(); // This tree has no children
    }

    /**
     * Creates a tree with root labeled by lbl and children chdn.
     * 
     * @param lbl  The label of the root
     * @param chdn Its children
     */
    public ParseTree(Variable lbl, List<ParseTree> chdn) {
        this.label = lbl;
        this.children = chdn;
    }

    /**
     * Recursive function, fetches the leftmost non-terminal node and adds the adequate child nodes.
     *
     * @param expected, the type of the {@link #label} that we expect in the left-most node.
     * @param children, a variable number of children node to add.
     * @return boolean, if the children were placed or not.
     */
    public boolean deriveLeftmost(Variable expected, ArrayList<Variable> children) {
        // this is a terminal. wrong case.
        if (this.isTerminal()) {
            return false;
        }
        // good case
        if (!this.hasChildren() && expected.getType().equals(label.getType())) {
            for (Variable var : children) {
                ParseTree newTree = new ParseTree(var);
                this.children.add(newTree);
            }
            if (Parser.VERBOSE) {
                System.out.println("Derivation rule applied to tree.");
            }
            return true;
        }
        // not a terminal, but has children
        for (ParseTree child : this.children) {
            boolean wasFound = child.deriveLeftmost(expected, children);
            if (wasFound) return true;
        }
        return false;
    }
    public void setLabel(Variable label){
        this.label = label;
    }
    /**
     * Checks if this part of the three is a terminal (there are no child nodes and the label is a terminal).
     * @return true if it is a leaf, false otherwise
     */
    public boolean isTerminal() {
        boolean isTerminal = this.label.isTerminal();
        // additional check for debugging
        if (isTerminal && !children.isEmpty()) {
            throw new RuntimeException("Terminal node has children in three.");
        }
        return isTerminal;
    }

    /**
     * @return true if the children {@link #children} is non empty.
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Check if one of its grand-children are arithmetic operators, if yes switch them to obtain an AST
     */
    public void switchOperatorGrandChildren(){
        for(ParseTree c : children){
            for(ParseTree cp : c.children){
                if(cp.label.getType().equals(Variable.Type.MINUS)||cp.label.getType().equals(Variable.Type.PLUS)||cp.label.getType().equals(Variable.Type.TIMES)||cp.label.getType().equals(Variable.Type.DIVIDE)){
                    setLabel(cp.label); //ajuste le tree pour que l'opération dépende de la branche de droite et de gauche
                    cp.setLabel(new Variable(Variable.Type.EPS)); //remplace l'ancien opérator par un type qu'on peut omettre
                    break;
                }
            }
            c.switchOperatorGrandChildren(); // all children doing it recursively
        }
    }
    /**
     * Writes the tree as LaTeX code
     */
    public String toLaTexTree() {
        StringBuilder treeTeX = new StringBuilder();
        treeTeX.append("[");
        treeTeX.append("{" + label.toTexString() + "}");
        treeTeX.append(" ");

        for (ParseTree child : children) {
            treeTeX.append(child.toLaTexTree());
        }
        treeTeX.append("]");
        return treeTeX.toString();
    }

    /**
     * Writes the tree as TikZ code. TikZ is a language to specify drawings in LaTeX
     * files.
     */
    public String toTikZ() {
        StringBuilder treeTikZ = new StringBuilder();
        treeTikZ.append("node {");
        treeTikZ.append(label.toTexString());
        treeTikZ.append("}\n");
        for (ParseTree child : children) {
            treeTikZ.append("child { ");
            treeTikZ.append(child.toTikZ());
            treeTikZ.append(" }\n");
        }
        return treeTikZ.toString();
    }

    /**
     * Writes the tree as a TikZ picture. A TikZ picture embeds TikZ code so that
     * LaTeX undertands it.
     */
    public String toTikZPicture() {
        return "\\begin{tikzpicture}[tree layout]\n\\" + toTikZ() + ";\n\\end{tikzpicture}";
    }

    /**
     * Writes the tree as a LaTeX document which can be compiled (using the LuaLaTeX
     * engine). Be careful that such code will not compile with PDFLaTeX, since the
     * tree drawing algorithm is written in Lua. The code is not very readable as
     * such, but you can have a look at the outputted file if you want to understand
     * better. <br>
     * <br>
     * The result can be used with the command:
     * 
     * <pre>
     * lualatex some-file.tex
     * </pre>
     */
    public String toLaTeXLua() {
        return "\\RequirePackage{luatex85}\n\\documentclass{standalone}\n\n\\usepackage{tikz}\n\n\\usetikzlibrary{graphdrawing, graphdrawing.trees}\n\n\\begin{document}\n\n"
                + toTikZPicture() + "\n\n\\end{document}\n%% Local Variables:\n%% TeX-engine: luatex\n%% End:";
    }

    /**
     * Writes the tree as a forest picture. Returns the tree in forest enviroment
     * using the latex code of the tree
     */
    public String toForestPicture() {
        return "\\begin{forest}for tree={rectangle, draw, l sep=20pt}" + toLaTexTree() + ";\n\\end{forest}";
    }

    /**
     * Writes the tree as a LaTeX document which can be compiled using PDFLaTeX.
     * <br>
     * <br>
     * The result can be used with the command:
     * 
     * <pre>
     * pdflatex some-file.tex
     * </pre>
     */
    public String toLaTeX() {
        return "\\documentclass[border=5pt]{standalone}\n\n\\usepackage{tikz}\n\\usepackage{forest}\n\n\\begin{document}\n\n"
                + toForestPicture() + "\n\n\\end{document}\n%% Local Variables:\n%% TeX-engine: pdflatex\n%% End:";
    }
}
