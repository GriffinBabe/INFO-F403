import java.util.Stack;

/**
 * Parser stack class
 */
public class StackWrapper {

    /**
     * Stack containing variables of execution of the parser
     */
    private Stack<Variable> stack;

    /**
     * Public constructor
     */
    public StackWrapper() {
        stack = new Stack<>();
    }

    /**
     * @param v variable to be added to the top of the stack
     */
    public void pushVar(Variable v){
        stack.push(v);
    }

    /**
     * remove the variable which is on top of the stack
     * @return the removed variable
     * @throws RuntimeException
     */
    public Variable remVar() throws RuntimeException{
        if(stack.isEmpty()){ throw new RuntimeException("Cannot remove element from empty stack");}
        return stack.pop();
    }

    /**
     * @return the variable on top of the stack
     * @throws RuntimeException
     */
    public Variable readLast() throws RuntimeException{
        if(stack.isEmpty()){ throw new RuntimeException("Cannot read element from empty stack");}
        return stack.peek();
    }
}
