package compiler;

/**
 * This class stores and traces the LLVM temporary variables (abstracted registers), the LLVM labels and the literal
 * integral values.
 *
 * This class is necessary as during the compilation process some variables are not necessarily initialized then used
 * in the right order, but LLVM requires to specify the temporary variables in the correct order.
 */
public class TempVariable {

    /**
     * Reference to the compiler {@link CompilerTable}
     * The compiler table will map the {@link #creationId} to the correct outputted ID.
     */
    CompilerTable reference;

    /**
     * Unique ID of the creation of this variable.
     * Used only if the type is {@link Type#REGISTER}.
     */
    int creationId;

    /**
     * Literal value (integer number).
     * Used only if the type is {@link Type#LITERAL}.
     */
    int literalValue;

    /**
     * LLVM Label name.
     * Used only if the type is {@link Type#LABEL}.
     */
    String labelValue;

    /**
     * The type of the {@link TempVariable}, as it might be a label, a register or a literal value.
     */
    Type type;

    enum Type {
        LITERAL, REGISTER, LABEL
    }

    public TempVariable(CompilerTable reference, int creationId) {
        this.reference = reference;
        this.creationId = creationId;
        this.type = Type.REGISTER;
    }

    public TempVariable(Integer number) {
        this.literalValue = number;
        this.type = Type.LITERAL;
    }

    public TempVariable(String labelValue) {
        this.labelValue = labelValue;
        this.type = Type.LABEL;
    }

    /**
     * Returns the corresponding value into a string.
     */
    @Override
    public String toString() {
        switch (this.type) {
            case REGISTER:
                return "%" + reference.getUsageId(creationId);
            case LITERAL:
                return String.valueOf(literalValue);
            case LABEL:
                return this.labelValue;
            default:
                return null;
        }
    }
}
