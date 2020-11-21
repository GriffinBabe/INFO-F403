import java.util.Stack;

public class StackWrapper {

    private Stack<Variable> stack;

    public StackWrapper() {
        stack = new Stack<>();
    }

    public void pushVar(Variable var) {
        stack.push(var);
    }

    public Variable remVar() {
        return stack.pop();
    }


}
