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
LineCommentStart = "//" {InputCharacter}*
BlockCommentStart = "/*"
BlockCommentEnd = "*/"
NonCommentRelated = ~( {CommentStart} | {BlockCommentEnd} | {LineTerminator} | {WhiteSpace} )

// Identifiers and literals
VarName = [a-z][a-z0-9]*
ProgName = [A-Z][A-Za-z0-9]*

// Wrong literal (non-zero number that starts with 0)
WrongNumber = [0][0-9]+
Number = [1-9][0-9]* | [0]

%xstate YYINITIAL, BLOCK_COMMENT

%%
<YYINITIAL> {

    {VarName} { return new Symbol(LexicalUnit.VARNAME,yyline, yycolumn, yytext()); }
    {ProgName} { return new Symbol(LexicalUnit.PROGNAME,yyline, yycolumn, yytext()); }
    {WrongNumber} { throw new Error("Illegal syntax: \""+yytext()+"\", non-zero literal cannot start with 0."); }
    {Number} { return new Symbol(LexicalUnit.NUMBER,yyline, yycolumn, yytext()); }
    // {LineCommentStart} { this.isLineComment = true; yybegin(COMMENT); }
    {LineCommentStart} {/*ignore*/}
    {BlockCommentStart} { yybegin(BLOCK_COMMENT); }

    "BEGINPROG" { return new Symbol(LexicalUnit.BEGINPROG,yyline, yycolumn, "BEGINPROG"); }
    "ENDPROG" { return new Symbol(LexicalUnit.ENDPROG,yyline, yycolumn, "ENDPROG"); }
    "IF" { return new Symbol(LexicalUnit.IF,yyline, yycolumn, "IF"); }
    "THEN" { return new Symbol(LexicalUnit.THEN,yyline, yycolumn, "THEN"); }
    "ELSE" { return new Symbol(LexicalUnit.ELSE,yyline, yycolumn, "ELSE"); }
    "ENDIF" { return new Symbol(LexicalUnit.ENDIF,yyline, yycolumn, "ENDIF"); }
    "WHILE" { return new Symbol(LexicalUnit.WHILE,yyline, yycolumn, "WHILE"); }
    "DO" { return new Symbol(LexicalUnit.DO,yyline, yycolumn, "DO"); }
    "ENDWHILE" { return new Symbol(LexicalUnit.ENDWHILE,yyline, yycolumn, "ENDWHILE"); }
    "PRINT" { return new Symbol(LexicalUnit.PRINT,yyline, yycolumn, "PRINT"); }
    "READ" { return new Symbol(LexicalUnit.READ,yyline, yycolumn, "READ"); }

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

<BLOCK_COMMENT> {
    {BlockCommentStart} { throw new Error("Illegal syntax: nested comments are not supported"); }
    {BlockCommentEnd} { if (!this.isLineComment) { yybegin(YYINITIAL); } }

    {NonCommentRelated} { /* ignores */ }

}

[^]         { throw new Error("Illegal character <" +yytext()+ ">"); }