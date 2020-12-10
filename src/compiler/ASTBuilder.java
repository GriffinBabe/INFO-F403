package compiler;

import compiler.symbol.*;
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

        if (tree.getLabel().getType() == Variable.Type.VARNAME) {
            System.out.println("Stop here");
        }

        // build the sub threes (normally only two per tree)
        // checks if a sub tree is not null
        boolean hasChildren = false;
        for (ParseTree subParseTree : tree.getChildren()) {
            // might add a child
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
        Symbol symbol;
        Variable head = parseTree.getLabel();
        AST right = tree.getRight();
        AST left = tree.getLeft();

        switch (head.getType()) {
            case V_PROGRAM:
                tree.setHead(new ProgramSymbol());
                return tree;
            case V_CODE:
                symbol = new CodeSymbol();
                if (left != null) {
                    // in that case this code tree is empty (probably an empty line)
                    // so we ignore and pass to the next one.
                    if (left.getHead() instanceof CodeSymbol) {
                        return left;
                    }
                    ((CodeSymbol) symbol).setInstruction((InstructionSymbol) left.getHead());
                    if (right != null) {
                        ((CodeSymbol) symbol).setNextCode((CodeSymbol) right.getHead());
                    }
                    tree.setHead(symbol);
                    return tree;
                }
                return tree.getLeft();
            case V_ASSIGN:
                symbol = new AssignSymbol();
                // set the relation between the variable and the arithmetic expression
                ((AssignSymbol) symbol).setExpression((ExpressionSymbol) left.getHead());
                tree.setHead(symbol);
                return tree;
            case V_INSTRUCTION:
                if (left == null) {
                    // empty EPS
                    return null;
                }
                else if (right.getHead() instanceof AssignSymbol) {
                    // in that case the left is the variable we want to attach to the assign symbol
                    ((AssignSymbol) right.getHead()).setVariable((VariableSymbol) left.getHead());
                    right.addChild(tree.getLeft());
                    // we need to swap as the natural shape of our ParseTree will first fit in the assign symbol
                    // the number then the variable name.
                    right.swapChildren();
                    return tree.getRight();
                }
                else if (left.getHead() instanceof PrintSymbol) {
                    // the left is PRINT and the right is the variable
                    ((PrintSymbol) left.getHead()).setToPrint(((VariableSymbol) right.getHead()));
                    left.addChild(tree.getRight());
                    return tree.getLeft();
                }
                else if (left.getHead() instanceof ReadSymbol) {
                    // the left is READ and the right is the symbol
                    ((ReadSymbol) left.getHead()).setToRead((VariableSymbol) right.getHead());
                    left.addChild(tree.getRight());
                    return tree.getLeft();
                }
                // in the other case we return a child
                return tree.getLeft();
            case READ:
                symbol = new ReadSymbol();
                tree.setHead(symbol);
                return tree;
            case PRINT:
                symbol = new PrintSymbol();
                tree.setHead(symbol);
                return tree;
            case VARNAME:
                String variableName = (String) parseTree.getLabel().getValue();
                symbol = new VariableSymbol(variableName);
                tree.setHead(symbol);
                return tree;
            case NUMBER:
                Integer number = (Integer) parseTree.getLabel().getValue();
                symbol = new NumberSymbol(number);
                tree.setHead(symbol);
                return tree;
            default:
                // ignore and return the only left child
                if (tree.getRight() != null)
                    return tree.getRight();
                return tree.getLeft();
        }

    }

}
