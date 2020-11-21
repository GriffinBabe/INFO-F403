import java.util.Stack;

public class StackWrapper {

    private Stack<Variable> stack;

    public StackWrapper() {
        stack = new Stack<>();
    }

    public void pushVar(Variable v){
        stack.push(v);
    }

    public Variable remVar() throws RuntimeException{
        if(stack.isEmpty()){ throw new RuntimeException("Cannot remove element from empty stack");}
        return stack.pop();
    }

    public Variable readLast() throws RuntimeException{
        if(stack.isEmpty()){ throw new RuntimeException("Cannot read element from empty stack");}
        return stack.peek();
    }
}
