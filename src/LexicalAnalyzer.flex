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

LineComment = "//" {InputCharacter}* {LineTerminator}
BlockComment = "/*" [^*]* ~"*/"

VarName = [a-z][a-z0-9]*
ProgName = [A-Z][A-Za-z0-9]*

Number = [ ]([+-]?[1-9][0-9]*|[0])

// (?<!\w[+-])[+-]?[1-9][0-9]*(?![\w+-]) previous regex

%%

<YYINITIAL> "BEGINPROG" { return new Symbol(LexicalUnit.BEGINPROG,yyline, yycolumn); }
<YYINITIAL> "ENDPROG" { return new Symbol(LexicalUnit.ENDPROG,yyline, yycolumn); }
<YYINITIAL> "ENDPROG" { return new Symbol(LexicalUnit.ENDPROG,yyline, yycolumn); }
<YYINITIAL> "IF" { return new Symbol(LexicalUnit.IF,yyline, yycolumn); }
<YYINITIAL> "THEN" { return new Symbol(LexicalUnit.THEN,yyline, yycolumn); }
<YYINITIAL> "ELSE" { return new Symbol(LexicalUnit.ELSE,yyline, yycolumn); }
<YYINITIAL> "ENDIF" { return new Symbol(LexicalUnit.ENDIF,yyline, yycolumn); }
<YYINITIAL> "WHILE" { return new Symbol(LexicalUnit.WHILE,yyline, yycolumn); }
<YYINITIAL> "DO" { return new Symbol(LexicalUnit.DO,yyline, yycolumn); }
<YYINITIAL> "ENDWHILE" { return new Symbol(LexicalUnit.ENDWHILE,yyline, yycolumn); }
<YYINITIAL> "PRINT" { return new Symbol(LexicalUnit.PRINT,yyline, yycolumn); }
<YYINITIAL> "READ" { return new Symbol(LexicalUnit.READ,yyline, yycolumn); }

<YYINITIAL> {
	{VarName} { return new Symbol(LexicalUnit.VARNAME,yyline, yycolumn); }
	{ProgName} { return new Symbol(LexicalUnit.PROGNAME,yyline, yycolumn); }
    {Number} { return new Symbol(LexicalUnit.NUMBER,yyline, yycolumn); }

	"(" { return new Symbol(LexicalUnit.LPAREN,yyline, yycolumn); }
	")" { return new Symbol(LexicalUnit.RPAREN,yyline, yycolumn); }
	":=" { return new Symbol(LexicalUnit.ASSIGN,yyline, yycolumn); }
	"," { return new Symbol(LexicalUnit.COMMA,yyline, yycolumn); }
	"-" { return new Symbol(LexicalUnit.MINUS,yyline, yycolumn); }
	"+" { return new Symbol(LexicalUnit.PLUS,yyline, yycolumn); }
	"*" { return new Symbol(LexicalUnit.TIMES,yyline, yycolumn); }
	"/" { return new Symbol(LexicalUnit.DIVIDE,yyline, yycolumn); }
	"==" { return new Symbol(LexicalUnit.EQ,yyline, yycolumn); }
	">" { return new Symbol(LexicalUnit.GT,yyline, yycolumn); }


	{Comment} { /* ignores */ }

	{WhiteSpace} { /* ignores */ }
}

[^]         { throw new Error("Illegal character <" +yytext()+ ">"); }