import java_cup.runtime.*;

%%

%class LexicalAnalyzer
%unicode
%cup
%line
%column

%{
    StringBuffer string = new StringBuffer();

    private Symbol symbol(LexicalUnit type) {
        return new Symbol(type, yyline, yycolumn); // no value
    }

    private Symbol symbol(LexicalUnit type, Object value) {
        return new Symbol(type, yyline, yycolumn, value); // with value
    }

%}

LineTerminator = \r|\n|\r\n

// All but \r and \n
InputCharacter = [^\r\n]

WhiteSpace = {LineTerminator} | [ \t\f]

Comment = { LineComment } | { BlockComment }

LineComment = "//" {InputCharacter}* {LineTerminator}
BlockComment = "/*" [^*]* ~"*/"

VarName = [a-z][a-z0-9]*
ProgName = [A-Z][A-Za-z0-9]*

// TODO: this is the best I could find but it includes the numbers starting with 0
// Number = (?<!\w[+-])[+-]?[1-9][0-9]*(?![\w+-])

%state STRING

%%

<YYINITIAL> "BEGINPROG" { return symobol(LexicalUnit.BEGINPROG); }
<YYINITIAL> "ENDPROG" { return symbol(LexicalUnit.ENDPROG); }
<YYINITIAL> "ENDPROG" { return symbol(LexicalUnit.ENDPROG); }
<YYINITIAL> "IF" { return symbol(LexicalUnit.IF); }
<YYINITIAL> "THEN" { return symbol(LexicalUnit.THEN); }
<YYINITIAL> "ELSE" { return symbol(LexicalUnit.ELSE); }
<YYINITIAL> "ENDIF" { return symbol(LexicalUnit.ENDIF); }
<YYINITIAL> "WHILE" { return symbol(LexicalUnit.WHILE); }
<YYINITIAL> "DO" { return symbol(LexicalUnit.DO); }
<YYINITIAL> "ENDWHILE" { return symbol(LexicalUnit.ENDWHILE); }
<YYINITIAL> "PRINT" { return symbol(LexicalUnit.PRINT); }
<YYINITIAL> "READ" { return symbol(LexicalUnit.READ); }

<YYINITIAL> {
	{VarName} { return symbol(LexicalUnit.VARNAME); }
	{ProgName} { return symbol(LexicalUnit.PROGNAME); }
//	{Number} { return symbol(LexicalUnit.NUMBER); }

	// TODO add LPAREN RPAREN
	":=" { return symbol(LexicalUnit.ASSIGN); }
	"," { return symbol(LexicalUnit.COMMA); }
	"-" { return symbol(LexicalUnit.MINUS); }
	"+" { return symbol(LexicalUnit.PLUS); }
	"*" { return symbol(LexicalUnit.TIMES); }
	"/" { return symbol(LexicalUnit.DIVIDE); }
	"==" { return symbol(LexicalUnit.EQ); }
	">" { return symbol(LexicalUnit.GT); }


	{Comment} { /* ignores */ }

	{WhiteSpace} { /* ignores */ }
}

[^]         { throw new Error(“Illegal character <“+yytext()+”>”); }