// import java_cup.runtime.*;

%%

%class LexicalAnalyzer
%unicode
%function nextToken
%line
%column
%type Symbol

%{
    private boolean isLineComment = false;
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

// Comment regexes
CommentStart = {LineCommentStart} | {BlockCommentStart}
LineCommentStart = "//"
BlockCommentStart = "/*"
BlockCommentEnd = "*/"
NonCommentRelated = ~( {CommentStart} | {BlockCommentEnd} | {LineTerminator} | {WhiteSpace} )

// Identifiers and literals
VarName = [a-z][a-z0-9]*
ProgName = [A-Z][A-Za-z0-9]*

// Wrong literal (non-zero number that starts with 0)
WrongNumber = [0][0-9]+
Number = [1-9][0-9]* | [0]

%xstate YYINITIAL, COMMENT

%%

<YYINITIAL> "BEGINPROG" { return new Symbol(LexicalUnit.BEGINPROG,yyline, yycolumn, "BEGINPROG"); }
<YYINITIAL> "ENDPROG" { return new Symbol(LexicalUnit.ENDPROG,yyline, yycolumn, "ENDPROG"); }
<YYINITIAL> "IF" { return new Symbol(LexicalUnit.IF,yyline, yycolumn, "IF"); }
<YYINITIAL> "THEN" { return new Symbol(LexicalUnit.THEN,yyline, yycolumn, "THEN"); }
<YYINITIAL> "ELSE" { return new Symbol(LexicalUnit.ELSE,yyline, yycolumn, "ELSE"); }
<YYINITIAL> "ENDIF" { return new Symbol(LexicalUnit.ENDIF,yyline, yycolumn, "ENDIF"); }
<YYINITIAL> "WHILE" { return new Symbol(LexicalUnit.WHILE,yyline, yycolumn, "WHILE"); }
<YYINITIAL> "DO" { return new Symbol(LexicalUnit.DO,yyline, yycolumn, "DO"); }
<YYINITIAL> "ENDWHILE" { return new Symbol(LexicalUnit.ENDWHILE,yyline, yycolumn, "ENDWHILE"); }
<YYINITIAL> "PRINT" { return new Symbol(LexicalUnit.PRINT,yyline, yycolumn, "PRINT"); }
<YYINITIAL> "READ" { return new Symbol(LexicalUnit.READ,yyline, yycolumn, "READ"); }

<YYINITIAL> {
	{VarName} { return new Symbol(LexicalUnit.VARNAME,yyline, yycolumn, yytext()); }
	{ProgName} { return new Symbol(LexicalUnit.PROGNAME,yyline, yycolumn, yytext()); }
	{WrongNumber} { throw new Error("Illegal syntax: \""+yytext()+"\", non-zero literal cannot start with 0."); }
    {Number} { return new Symbol(LexicalUnit.NUMBER,yyline, yycolumn, yytext()); }
    {LineCommentStart} { this.isLineComment = true; yybegin(COMMENT); }
    {BlockCommentStart} { this.isLineComment = false; yybegin(COMMENT); }

	"(" { return new Symbol(LexicalUnit.LPAREN,yyline, yycolumn, "("); }
	")" { return new Symbol(LexicalUnit.RPAREN,yyline, yycolumn, ")"); }
	":=" { return new Symbol(LexicalUnit.ASSIGN,yyline, yycolumn, ":="); }
	"," { return new Symbol(LexicalUnit.COMMA,yyline, yycolumn, ","); }
	"-" { return new Symbol(LexicalUnit.MINUS,yyline, yycolumn, "-"); }
	"+" { return new Symbol(LexicalUnit.PLUS,yyline, yycolumn, "+"); }
	"*" { return new Symbol(LexicalUnit.TIMES,yyline, yycolumn, "*"); }
	"/" { return new Symbol(LexicalUnit.DIVIDE,yyline, yycolumn, "/"); }
	"==" { return new Symbol(LexicalUnit.EQ,yyline, yycolumn, "=="); }
	">" { return new Symbol(LexicalUnit.GT,yyline, yycolumn, ">"); }

    {LineTerminator} { return new Symbol(LexicalUnit.ENDLINE, yyline, yycolumn, "\\n"); }

	{WhiteSpace} { /* ignores */ }
}

<COMMENT> {
    {BlockCommentStart} { throw new Error("Illegal syntax: nested comments are not supported"); }
    {LineTerminator} { if (this.isLineComment) { System.out.println("end of comment state."); yybegin(YYINITIAL); } }
    {BlockCommentEnd} { if (!this.isLineComment) { System.out.println("end of comment state."); yybegin(YYINITIAL); } }

    {NonCommentRelated} { /* ignores */ }

}

[^]         { throw new Error("Illegal character <" +yytext()+ ">"); }