package compiler;

import parser.ParseTree;
import util.OutputFileWriter;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compilation step main class. This will first build the {@link AST} from the {@link ASTBuilder} static methods, then
 * create the LLVM source code from compiler symbols methods.
 */
public class Compiler {

    private AST astTree = null;
    private String compiledCode = null;

    private boolean built = false;
    private boolean compiled = false;

    /**
     * Builds the abstract syntax of the compiler (necessary before calling the {@link #saveTree(String)}
     * or the {@link #compile()} methods).
     */
    public void build(ParseTree tree) {
        this.astTree = ASTBuilder.buildAST(tree);
        this.built = true;
    }

    /**
     * Compiles the program into LLVM code from the {@link #astTree}.
     * @return the LLVM source code.
     */
    public String compile() {
        if (!built) {
            throw new RuntimeException("Cannot compile before building the AST. Please call the build method before");
        }
        else{
            this.compiledCode = this.astTree.getHead().toLLVM(new CompilerTable());
            // this.compiledCode = reOrder(this.compiledCode);
            this.compiled = true;
        }
        return null;
    }

    public String reOrder(String output){
        StringBuilder sb = new StringBuilder(); //store the new output
        ArrayList<String> registersOccurrence = new ArrayList<>(); //store the registers occurrences
        String[] splited = output.split("\n"); //split the output line by line
        boolean main = false; //boolean indicating if we are in the main function
        for (String ch : splited) {
            String[] espaced = ch.split(" "); //split the line in spaced tokens
            boolean newline = true; //boolean indicating if the newline has been reached
            for(String sp : espaced){
                if(sp.equals("@main()")){ main = true; }
                if(main && sp.length()>1){
                    if(newline && sp.substring(0,1).equals("%")) {
                        try {
                            int registerNum = Integer.parseInt(sp.substring(1));
                            registersOccurrence.add("%" + registerNum);
                            sb.append("%").append(registersOccurrence.indexOf(sp));
                        } catch (NumberFormatException ignored) { sb.append(sp); } // the variable isn't a numerical register
                    }
                    else if(sp.substring(0,1).equals("%")){
                        try {
                            int registerNum = registersOccurrence.indexOf(sp);
                            if(registerNum >= 0){ //if doesn't exist in the array indexOf returns -1
                                sb.append("%").append(registerNum);
                            }
                            else{ //if the variable isn't a numerical register
                                sb.append(sp);
                            }
                        } catch (NumberFormatException ignored) {  }
                    }
                    else{
                        sb.append(sp);
                    }
                }
                else{
                    sb.append(sp);
                }
                sb.append(" ");
                newline = false;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    /**
     * Saves the {@link #astTree} into a LaTeX format. Source code is generated from {@link AST#toLaTeX()}.
     * @param path the path where to save the source code.
     */
    public void saveTree(String path) {
        if (!built) {
            throw new RuntimeException("Cannot save the AST before building it. Please call the build method before.");
        }
        OutputFileWriter outputFileWriter = new OutputFileWriter(path);
        outputFileWriter.write(astTree.toLaTeX());
    }

    public void saveOutput(String path) {
        if (!compiled) {
            throw new RuntimeException("Cannot save the output LLVM code before compiling it. Please call the compile" +
                    "method before.");
        }
        OutputFileWriter outputFileWriter = new OutputFileWriter(path);
        outputFileWriter.write(this.compiledCode);
    }
}
