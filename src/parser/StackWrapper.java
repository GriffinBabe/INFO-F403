package parser;

import java.util.Stack;

/**
 * Parser stack wrapper class
 */
public class StackWrapper {

    /**
     * Stack containing variables of execution of the parser
     */
    private final Stack<Variable> stack;

    /**
     * Public constructor
     */
    public StackWrapper() {
        stack = new Stack<>();
    }

    /**
     * Pushes a new {@link Variable} on the top of the stack.
     * @param v {@link Variable} to be added to the top of the stack
     */
    public void pushVar(Variable v){
        stack.push(v);
    }

    /**
     * Removes the {@link Variable} which is on top of the stack.
     * @return the removed {@link Variable}
     */
    public Variable remVar() {
        if(stack.isEmpty()){ throw new RuntimeException("Cannot remove element from empty stack");}
        return stack.pop();
    }

    /**
     * Reads the top {@link Variable} of the stack.
     * @return the {@link Variable} on top of the stack
     */
    public Variable readTop() {
        if(stack.isEmpty()){ throw new RuntimeException("Cannot read element from empty stack");}
        return stack.peek();
    }

    /**
     * Checks if the stack is empty. Used at the end of the parsing.
     * @return true if it is empty, false otherwise.
     */
    public boolean isEmpty() {
        return (stack.empty());
    }

}
