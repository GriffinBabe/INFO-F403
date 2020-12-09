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
        Variable treeHead = tree.getLabel();

        // build the sub threes (normally only two per tree)
        for (ParseTree subParseTree : tree.getChildren()) {
            subTree.addChild(buildAST(subParseTree));
        }

        // build the head of the three
        buildHead(treeHead, subTree);

        return subTree;
    }

    private void buildHead(Variable head, AST tree) {
        switch (head.getType()) {
            case V_PROGRAM:
                tree.setHead(new ProgramSymbol());
                break;
            default:
                break;
        }

    }

}
