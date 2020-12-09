package compiler;

import parser.ParseTree;
import parser.Variable;

public class ASTBuilder {

    /**
     * Recursive function that will build the tree by first building the child sub trees and then by setting and
     * configuring the head symbol.
     * @param tree, the sub tree that is currently being build.
     * @return the completed sub tree.
     */
    public AST buildAST(ParseTree tree) {
        AST subTree = new AST();

        // build the sub threes (normally only two per tree)
        // checks if a sub tree is not null
        boolean hasChildren = false;
        for (ParseTree subParseTree : tree.getChildren()) {
            boolean isChildNonNull = subTree.addChild(buildAST(subParseTree));
            if (isChildNonNull) {
                hasChildren = true;
            }
        }

        return buildHead(tree, subTree);
    }

    /**
     * Builds the head and returns the tree.
     * @param parseTree the corresponding parse tree.
     * @param tree the build AST.
     * @return
     */
    private AST buildHead(ParseTree parseTree, AST tree) {
        Symbol symbol, right, left;

        Variable head = parseTree.getLabel();
        right = tree.getRight().getHead();
        left = tree.getLeft().getHead();
        switch (head.getType()) {
            case V_PROGRAM:
                tree.setHead(new ProgramSymbol());
                return tree;
            case V_CODE:
                symbol = new CodeSymbol();
                tree.setHead(symbol);
                return tree;
            case V_ASSIGN:
                symbol = new AssignSymbol();
                tree.setHead(symbol);
                return tree;
            case V_INSTRUCTION:
                if (right instanceof AssignSymbol) {
                    // in that case the left is the variable we want to attach to the assign symbol
                    ((AssignSymbol) right).setVariableSymbol((VariableSymbol) left);
                    return tree.getRight();
                }
                // in the other case we return a 
                return tree.getLeft();
            default:
                break;
        }

    }

}
