package compiler;

/**
 * As the source code temporary variables must be
 */
public class TempVariable {

    CompilerTable reference;

    int creationId;

    public TempVariable(CompilerTable reference, int creationId) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
