package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

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

    private List<Variable.Type> valid_terminal = new ArrayList<>();
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
        return isTerminal;
    }

    /**
     * @return true if the children {@link #children} is non empty.
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * @param children
     * @return Index of children in the children list
     */
    public int getIndex(ParseTree children){
        for(int i = 0 ; i < this.children.size() ; i++){
            if(this.children.get(i).equals(children)){
                return i;
            }
        }
        return 0;
    }

    /**
     * Removes redundancy from tree to obtain an AST
     */
    public void trimTree() {
        valid_terminal.add(Variable.Type.MINUS);
        valid_terminal.add(Variable.Type.VARNAME);
        valid_terminal.add(Variable.Type.PLUS);
        valid_terminal.add(Variable.Type.TIMES);
        valid_terminal.add(Variable.Type.DIVIDE);
        valid_terminal.add(Variable.Type.WHILE);
        valid_terminal.add(Variable.Type.IF);
        valid_terminal.add(Variable.Type.LPAREN);
        valid_terminal.add(Variable.Type.RPAREN);
        valid_terminal.add(Variable.Type.PRINT);
        valid_terminal.add(Variable.Type.READ);
        valid_terminal.add(Variable.Type.GT);
        valid_terminal.add(Variable.Type.EQ);
        valid_terminal.add(Variable.Type.ELSE);
        valid_terminal.add(Variable.Type.NUMBER);


        List<ParseTree> children_copy = new ArrayList<>(this.children);
        for (ParseTree nextChild : children_copy) {
            List<ParseTree> childrenC_copy = new ArrayList<>(nextChild.children);
            for (ParseTree nextGChild : childrenC_copy) {
                if (nextGChild.label.getType().equals(Variable.Type.MINUS) || nextGChild.label.getType().equals(Variable.Type.PLUS) || nextGChild.label.getType().equals(Variable.Type.TIMES) || nextGChild.label.getType().equals(Variable.Type.DIVIDE)) {
                    if(nextGChild.label.getType().equals(Variable.Type.MINUS) && nextChild.label.getType().equals(Variable.Type.V_C)) {
                        nextChild.setLabel(nextGChild.label);
                        this.children.get(getIndex(nextChild)).children.remove(nextGChild);
                    }
                    else{
                        setLabel(nextGChild.label); //ajuste le tree pour que l'opération dépende de la branche de droite et de gauche
                        this.children.get(getIndex(nextChild)).children.remove(nextGChild); //supprime l'ancien opérateur
                    }
                } else if (!nextGChild.hasChildren()) {
                    if (!isValidVar(nextGChild)) {
                        this.children.get(getIndex(nextChild)).children.remove(nextGChild);
                    }
                }
            }
            if (!nextChild.isTerminal() && !nextChild.hasChildren()) {
                this.children.remove(nextChild);
            }
            if (nextChild.label.getType().equals(Variable.Type.BEGINPROG)) {
                this.children.remove(nextChild);
            }
            nextChild.trimTree();
        }
    }

    public boolean isValidVar(ParseTree child){
        for (Variable.Type vt : valid_terminal) {
            if (child.label.getType().equals(vt)) {
                return true;
            }
         }
        return false;
    }
    /**
     * recursively removes useless intermediates variables
     */
    public void cleanTree(){
        if(this.children.size() == 1){
            while(this.children.get(0).children.size() == 1){
                if(isValidVar(this.children.get(0))){
                    break;
                }
                this.children = this.children.get(0).children;
            }
            //todo : still some intermediates states to remove and clean code
        }
        for (ParseTree nextChild : this.children){
            nextChild.cleanTree();
        }
    }
    /**
     * Writes the tree as LaTeX code
     */
    public String toLaTexTree(){
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
