package compiler;

import compiler.symbol.*;
import parser.ParseTree;
import parser.Variable;

/**
 * Utility class that builds an {@link AST} by parsing a {@link ParseTree} recursively and follow hardcoded rules in the
 * {@link #buildHead(ParseTree, AST)} method. The robustness is granted by the consistent-ness of the given input
 * (we know in fact that the ParseTree is always well built at this step of the compilation).
 */
public class ASTBuilder {

    /**
     * Recursive function that will build the tree by first building the child sub trees and then by setting and
     * configuring the head symbol.
     * @param tree, the sub tree that is currently being build.
     * @return the completed sub tree.
     */
    public static AST buildAST(ParseTree tree) {
        AST subTree = new AST();

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
     * Builds the head by following hardcoded rules and returns the tree.
     * @param parseTree the corresponding parse tree.
     * @param tree the build AST.
     * @return
     */
    private static AST buildHead(ParseTree parseTree, AST tree) {
        CompilerSymbol compilerSymbol;
        ExpressionSymbol leftExpression, rightExpression, thirdExpression;
        Variable head = parseTree.getLabel();
        AST right = tree.getRight();
        AST left = tree.getLeft();
        AST third = tree.getThird();

        switch (head.getType()) {
            case V_PROGRAM:
                ProgramSymbol v_p = new ProgramSymbol();
                tree.setHead(v_p);
                //temp
                v_p.setCode((CodeSymbol)left.getHead()) ;
                //
                return tree;
            case V_CODE:
                compilerSymbol = new CodeSymbol();
                if (left != null) {
                    // in that case this code tree is empty (probably an empty line)
                    // so we ignore and pass to the next one.
                    if (left.getHead() instanceof CodeSymbol) {
                        return left;
                    }
                    ((CodeSymbol) compilerSymbol).setInstruction((InstructionSymbol) left.getHead());
                    if (right != null) {
                        ((CodeSymbol) compilerSymbol).setNextCode((CodeSymbol) right.getHead());
                    }
                    tree.setHead(compilerSymbol);
                    return tree;
                }
                return left;
            case V_ASSIGN:
                compilerSymbol = new AssignSymbol();
                // set the relation between the variable and the arithmetic expression
                ((AssignSymbol) compilerSymbol).setExpression((ExpressionSymbol) left.getHead());
                tree.setHead(compilerSymbol);
                return tree;
            case V_INSTRUCTION:
                if (left == null) {
                    // empty EPS
                    return null;
                }
                else if (left.getHead() instanceof IfSymbol ||
                        left.getHead() instanceof WhileSymbol) {
                    return left;
                }
                else if (right.getHead() instanceof AssignSymbol) {
                    // in that case the left is the variable we want to attach to the assign symbol
                    ((AssignSymbol) right.getHead()).setVariable((VariableSymbol) left.getHead());
                    right.addChild(tree.getLeft());
                    // we need to swap as the natural shape of our ParseTree will first fit in the assign symbol
                    // the number then the variable name.
                    right.swapChildren();
                    return right;
                }
                else if (left.getHead() instanceof PrintSymbol) {
                    // the left is PRINT and the right is the variable
                    ((PrintSymbol) left.getHead()).setToPrint(((VariableSymbol) right.getHead()));
                    left.addChild(tree.getRight());
                    return left;
                }
                else if (left.getHead() instanceof ReadSymbol) {
                    // the left is READ and the right is the symbol
                    ((ReadSymbol) left.getHead()).setToRead((VariableSymbol) right.getHead());
                    left.addChild(tree.getRight());
                    return left;
                }
                // in the other case we return a child
                return left;
            case V_B: // same as V_EXPRARITH
            case V_EXPRARITH:
                if (right == null) {
                    return left;
                }
                // right node is an actual algebraic operator
                OperatorSymbol rightOperator = (OperatorSymbol) right.getHead();
                // left node is either a number either a varname
                leftExpression = (ExpressionSymbol) left.getHead();

                rightOperator.setLeft(leftExpression);

                // adds the subtrees
                right.addChild(left);
                right.swapChildren();
                return right;
            case V_B_: // same as V_EXPRARITH_
            case V_EXPRARITH_:
                if (left == null) {
                    // empty eps
                    return null;
                }
                // the algebraic operator symbol
                OperatorSymbol leftOperator = (OperatorSymbol) left.getHead();
                // a number or a varname symbol
                rightExpression = (ExpressionSymbol) right.getHead();

                // checks if there are following algebraic operations
                if (third == null) {
                    // in that case this subtree is the end of the operations
                    leftOperator.setRight(rightExpression);
                    // adds the subtree
                    left.addChild(right);
                }
                else {
                    // in that case there is another sub operation
                    thirdExpression = (ExpressionSymbol) third.getHead();
                    ((OperatorSymbol) thirdExpression).setLeft(rightExpression);
                    leftOperator.setRight(thirdExpression);
                    leftOperator.setLeft(rightExpression);
                    left.addChild(third);
                    third.addChild(right);
                    third.swapChildren();
                }
                // left is an arithmetic operator
                return left;
            case V_C:
                if(left.getHead() instanceof MinusSymbol) {
                    tree.removeChild(0);
                    // there is an unary minus
                    // the minus sybmol on the left sub tree is discarded
                    // and the node is returned as an ast with a Unary head.
                    UnarySymbol unary = new UnarySymbol();
                    tree.setHead(unary);
                    unary.setLeft((ExpressionSymbol) right.getHead());
                    return tree;
                }
                return left;
            case V_IF:
                // if symbol is created on the variable instead of terminal as it is easier
                IfSymbol ifSymbol = new IfSymbol();
                ifSymbol.setCompare((CompareSymbol) left.getHead());

                // IfBlockSymbol contains all the blocks of code to execute upon verification or not
                IfBlockSymbol ifBlockSymbol = (IfBlockSymbol) right.getHead();
                ifSymbol.setBlocks(ifBlockSymbol);

                tree.setHead(ifSymbol);
                return tree;
            case V_IF_:
                IfBlockSymbol ifBlock = new IfBlockSymbol();
                ifBlock.setVerifiedBody((CodeSymbol) left.getHead());

                if (right != null) {
                    ifBlock.setUnverifiedBody((CodeSymbol) right.getHead());
                }
                tree.setHead(ifBlock);
                return tree;
            case V_WHILE:
                WhileSymbol whileSymbol = new WhileSymbol();
                whileSymbol.setCompare((CompareSymbol) left.getHead());
                whileSymbol.setVerifiedBody((CodeSymbol) right.getHead());
                tree.setHead(whileSymbol);
                return tree;
            case V_COND:
                // on the left there is an expression, on the middle there is a comparison and on the right
                // there is another epxression
                CompareSymbol compare = (CompareSymbol) right.getHead();
                leftExpression = (ExpressionSymbol) left.getHead();
                rightExpression = (ExpressionSymbol) third.getHead();
                // prepares the compare symbol, sets the head to the comparison and returns it
                compare.setLeft(leftExpression);
                compare.setRight(rightExpression);
                tree.setHead(compare);
                tree.removeChild(1); // remove the compare symbol as now it is in the head
                return tree;
            case READ:
                compilerSymbol = new ReadSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case PRINT:
                compilerSymbol = new PrintSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case VARNAME:
                String variableName = (String) parseTree.getLabel().getValue();
                compilerSymbol = new VariableSymbol(variableName);
                tree.setHead(compilerSymbol);
                return tree;
            case NUMBER:
                Integer number = Integer.parseInt(parseTree.getLabel().getValue());
                compilerSymbol = new NumberSymbol(number);
                tree.setHead(compilerSymbol);
                return tree;
            case PLUS:
                compilerSymbol = new AdditionSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case MINUS:
                compilerSymbol = new MinusSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case TIMES:
                compilerSymbol = new MultiplicationSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case DIVIDE:
                compilerSymbol = new DivideSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case GT:
                compilerSymbol = new GreaterSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            case EQ:
                compilerSymbol = new EqualSymbol();
                tree.setHead(compilerSymbol);
                return tree;
            default:
                // ignore and return the only left child
                if (tree.getRight() != null)
                    return right;
                return left;
        }

    }

}
