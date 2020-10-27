// import java_cup.runtime.*;

%%

%class LexicalAnalyzer
%unicode
%function nextToken
%line
%column
%type Symbol

LineTerminator = \r|\n|\r\n

// All but \r and \n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

Comment = { LineComment } | { BlockComment }

LineComment = "//" {InputCharacter}*
BlockComment = "/*" [^*]* ~"*/"

VarName = [a-z][a-z0-9]*
ProgName = [A-Z][A-Za-z0-9]*

WrongNumber = [0][0-9]+
Number = [1-9][0-9]* | [0]

// (?<!\w[+-])[+-]?[1-9][0-9]*(?![\w+-]) previous regex

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
	{WrongNumber} { throw new Error("Illegal literal: \""+yytext()+"\", non-zero number cannot start with 0."); }
    {Number} { return new Symbol(LexicalUnit.NUMBER,yyline, yycolumn, yytext()); }

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

	{Comment} { /* ignores */ }

	{WhiteSpace} { /* ignores */ }
}

[^]         { throw new Error("Illegal character <" +yytext()+ ">"); }