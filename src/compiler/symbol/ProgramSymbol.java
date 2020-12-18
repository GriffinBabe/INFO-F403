package compiler.symbol;

import compiler.CompilerTable;

/**
 * Root compiler unit symbol, defines the base functions used for the READ() and PRINT() and sets the main function.
 */
public class ProgramSymbol extends CompilerSymbol {

    /**
     * Contains all function definitions and begin of main function. Source code is from the practical sessions.
     */
    private static String HARDCODED_PREFIX =
            "@.strP = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1\n" +
            "\n" +
            "; Function Attrs: nounwind uwtable\n" +
            "define void @println(i32 %x) #0 {\n" +
            "  %1 = alloca i32, align 4\n" +
            "  store i32 %x, i32* %1, align 4\n" +
            "  %2 = load i32, i32* %1, align 4\n" +
            "  %3 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.strP, i32 0, i32 0), i32 %2)\n" +
            "  ret void\n" +
            "}\n" +
            "declare i32 @printf(i8*, ...) #1\n" +
            "declare i32 @getchar()\n" +
            "\n" +
            "define i32 @readInt() {\n" +
            "entry:                                 ; create variables\n" +
            "  ; encoded in base 10\n" +
            "  %res = alloca i32, align 4\n" +
            "  %digit = alloca i32, align 4\n" +
            "  store i32 0, i32* %res\n" +
            "  br label %read\n" +
            "read:                                   ; read a digit\n" +
            "  ; gets the last character\n" +
            "  %0 = call i32 @getchar()\n" +
            "  %1 = sub i32 %0, 48 ; substracts by 48\n" +
            "  store i32 %1, i32* %digit ; stores the digit\n" +
            "  %2 = icmp ne i32 %0, 10 ; checks if it is not the EOL '\\n' code\n" +
            "  br i1 %2, label %save, label %exit\n" +
            "save:\n" +
            "  ; load and multiply by 10\n" +
            "  %3 = load i32, i32* %res\n" +
            "  %4 = mul i32 %3, 10\n" +
            "  ; adds the new read digit\n" +
            "  %5 = add i32 %4, %1\n" +
            "  store i32 %5, i32* %res\n" +
            "  br label %read\n" +
            "                                          ; Do your computations\n" +
            "\n" +
            "exit:\t                               ; return res\n" +
            "  %6 = load i32, i32* %res\n" +
            "  ret i32 %6\n" +
            "}\n" +
            "define i32 @main() {\n" +
            "  entry:\n";

    /**
     * Contains the end of the main function. Source code is from the course's practical sessions.
     */
    private static String HARDCODED_SUFFIX = "  ret i32 0\n" +
            "}";

    /**
     * First line of code to be called. Following lines are called inside the code symbol.
     */
    CodeSymbol code;

    @Override
    public String toLLVM(CompilerTable table, String... returnRegisters) {
        StringBuilder sb = new StringBuilder();
        sb.append(HARDCODED_PREFIX);
        sb.append(code.toLLVM(table, returnRegisters));
        sb.append(HARDCODED_SUFFIX);
        return sb.toString();
    }

    @Override
    public String toTexString() {
        return "<Program>";
    }

    public void setCode(CodeSymbol code){
        this.code = code;
    }
}
