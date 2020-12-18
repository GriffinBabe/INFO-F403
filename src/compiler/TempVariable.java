package compiler;

/**
 * As the source code temporary variables must be
 */
public class TempVariable {

    CompilerTable reference;

    int creationId;

    int usageId = 0;

    int literalValue;

    String labelValue;

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

    @Override
    public String toString() {
        switch (this.type) {
            case REGISTER:
                this.usageId = reference.getUsageId(creationId);
                return "%" + usageId;
            case LITERAL:
                return String.valueOf(literalValue);
            case LABEL:
                return this.labelValue;
            default:
                return null;
        }
    }
}
