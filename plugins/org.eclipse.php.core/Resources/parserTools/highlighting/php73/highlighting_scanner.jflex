/*******************************************************************************
 * Copyright (c) 2006-2019 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.documentModel.parser.php73;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;

@SuppressWarnings({"unused", "nls"})

%%

%public
%class PHPLexer
%extends org.eclipse.php.internal.core.documentModel.parser.AbstractPHPLexer
%type String
%unicode
%caseless




%state ST_PHP_IN_SCRIPTING
%state ST_PHP_DOUBLE_QUOTES
%state ST_PHP_SINGLE_QUOTE
%state ST_PHP_BACKQUOTE
%state ST_PHP_QUOTES_AFTER_VARIABLE
%state ST_PHP_HEREDOC
%state ST_PHP_NOWDOC
%state ST_PHP_START_HEREDOC
%state ST_PHP_START_NOWDOC
%state ST_PHP_END_HEREDOC
%state ST_PHP_LOOKING_FOR_PROPERTY
%state ST_PHP_VAR_OFFSET
%state ST_PHP_COMMENT
%state ST_PHP_DOC_COMMENT
%state ST_PHP_LINE_COMMENT
%state ST_PHP_HIGHLIGHTING_ERROR
%state ST_PHP_END_NOWDOC
%state ST_PHP_IDENTIFIER
%state ST_PHP_DOLLAR_CURLY_OPEN

%{
	public PHPLexer(int state) {
		initialize(state);
	}

	// NB: this method resets the lexer only partially
	@Override
	protected void reset(java.io.Reader reader, char[] buffer, int[] parameters) {
		this.zzReader = reader;
		this.zzBuffer = buffer;
		this.zzFinalHighSurrogate = 0;
		this.zzMarkedPos = parameters[0];
		this._zzPushbackPos = parameters[1];
		this.zzCurrentPos = parameters[2];
		this.zzStartRead = parameters[3];
		this.zzEndRead = parameters[4];
		this.yyline = parameters[5];
		initialize(parameters[6]);
	}

	@Override
	public int getInScriptingState() {
		return ST_PHP_IN_SCRIPTING;
	}

	private static final int[] heredocStates = new int[] { ST_PHP_HEREDOC, ST_PHP_NOWDOC, ST_PHP_START_HEREDOC, ST_PHP_START_NOWDOC, ST_PHP_END_HEREDOC, ST_PHP_END_NOWDOC };

	@Override
	public int[] getHeredocStates() {
		return heredocStates;
	}

	private static final int[] phpQuotesStates = new int[] { ST_PHP_DOUBLE_QUOTES, ST_PHP_SINGLE_QUOTE, ST_PHP_BACKQUOTE, ST_PHP_HEREDOC, ST_PHP_NOWDOC, ST_PHP_START_HEREDOC, ST_PHP_START_NOWDOC, ST_PHP_END_HEREDOC, ST_PHP_END_NOWDOC };

	@Override
	public int[] getPHPQuotesStates() {
		return phpQuotesStates;
	}

	@Override
	public int[] getParameters() {
		return new int[]{zzMarkedPos, _zzPushbackPos, zzCurrentPos, zzStartRead, zzEndRead, yyline, zzLexicalState};
	}

	@Override
	protected int getZZLexicalState() {
		return zzLexicalState;
	}

	@Override
	protected int getZZMarkedPos() {
		return zzMarkedPos;
	}

	@Override
	protected int getZZEndRead() {
		return zzEndRead;
	}

	@Override
	public char[] getZZBuffer() {
		return zzBuffer;
	}

	@Override
	protected int getZZStartRead() {
		return this.zzStartRead;
	}

	@Override
	protected int getZZPushBackPosition() {
		return this._zzPushbackPos;
	}

	@Override
	protected void pushBack(int i) {
		yypushback(i);
	}

	@Override
	public int getScriptingState() {
		return ST_PHP_IN_SCRIPTING;
	}

	// A pool of states. To avoid creation of a new state on each createMemento.
	private static final Map<LexerState, LexerState> lexerStates = new HashMap<>();

	@Override
	protected Map<LexerState, LexerState> getLexerStates() {
		return lexerStates;
	}

	// End user code
%}

LNUM=[0-9]+
DNUM=([0-9]*"."[0-9]+)|([0-9]+"."[0-9]*)
EXPONENT_DNUM=(({LNUM}|{DNUM})[eE][+-]?{LNUM}?)
HNUM="0x"[0-9a-fA-F]+
BNUM="0b"[01]+
LABEL=[a-zA-Z_\u007f-\uffff][a-zA-Z0-9_\u007f-\uffff]*
WHITESPACES=[ \n\r\t]+
TABS_AND_SPACES=[ \t]*
TOKENS=[:,.\[\]()|\^&+-//*=%!~$<>?@]
CLOSE_EXPRESSION=[;]
ANY_CHAR=[^]
NEWLINE=("\r"|"\n"|"\r\n")
DOUBLE_QUOTES_LITERAL_DOLLAR=("$"+([^a-zA-Z_\u007f-\uffff$\"\\{]|("\\"{ANY_CHAR})))
BACKQUOTE_LITERAL_DOLLAR=("$"+([^a-zA-Z_\u007f-\uffff$`\\{]|("\\"{ANY_CHAR})))
HEREDOC_LITERAL_DOLLAR=("$"+([^a-zA-Z_\u007f-\uffff$\n\r\\{]|("\\"[^\n\r])))
HEREDOC_NEWLINE=((("{"*|"$"*)"\\"?){NEWLINE})
HEREDOC_CURLY_OR_ESCAPE_OR_DOLLAR=(("{"+[^$\n\r\\{])|("{"*"\\"[^\n\r])|{HEREDOC_LITERAL_DOLLAR})
HEREDOC_NON_LABEL=([^a-zA-Z_\u007f-\uffff$\n\r \t\\{]|{HEREDOC_CURLY_OR_ESCAPE_OR_DOLLAR})
DOUBLE_QUOTES_CHARS=("{"*([^$\"\\{]|("\\"{ANY_CHAR}))|{DOUBLE_QUOTES_LITERAL_DOLLAR})
BACKQUOTE_CHARS=("{"*([^$`\\{]|("\\"{ANY_CHAR}))|{BACKQUOTE_LITERAL_DOLLAR})
HEREDOC_CHARS=("{"*([^$\n\r\\{]|("\\"[^\n\r]))|{HEREDOC_LITERAL_DOLLAR}|(({HEREDOC_NEWLINE}{TABS_AND_SPACES})+{HEREDOC_NON_LABEL}))
NOWDOC_CHARS=([^\n\r]|({NEWLINE}{TABS_AND_SPACES})+[^a-zA-Z_\u007f-\uffff\n\r \t])
PHP_OPERATOR="=>"|"++"|"--"|"==="|"!=="|"=="|"!="|"<>"|"<="|">="|"+="|"-="|"*="|"/="|".="|"%="|"<<="|">>="|"&="|"|="|"^="|"||"|"&&"|"OR"|"AND"|"XOR"|"<<"|">>"|"**"|"**="|"<=>"|"??"

%%

/***********************************************************************************************
**************************************** P  H  P ***********************************************
***********************************************************************************************/

<ST_PHP_IN_SCRIPTING> "exit" {
	return PHP_EXIT;
}

<ST_PHP_IN_SCRIPTING>"die" {
	return PHP_DIE;
}

<ST_PHP_IN_SCRIPTING>"function" {
	pushState(ST_PHP_IDENTIFIER);
	return PHP_FUNCTION;
}

<ST_PHP_IDENTIFIER>"function" {
	return PHP_FUNCTION;
}

<ST_PHP_IN_SCRIPTING>"const" {
	pushState(ST_PHP_IDENTIFIER);
	return PHP_CONST;
}

<ST_PHP_IN_SCRIPTING>"return" {
	return PHP_RETURN;
}

<ST_PHP_IN_SCRIPTING>"yield" {
	return PHP_YIELD;
}

<ST_PHP_IN_SCRIPTING>"yield"{WHITESPACES}"from" {
	return PHP_YIELD;
}

<ST_PHP_IN_SCRIPTING>"try" {
	return PHP_TRY;
}

<ST_PHP_IN_SCRIPTING>"catch" {
	return PHP_CATCH;
}

<ST_PHP_IN_SCRIPTING>"finally" {
	return PHP_FINALLY;
}

<ST_PHP_IN_SCRIPTING>"throw" {
	return PHP_THROW;
}

<ST_PHP_IN_SCRIPTING>"if" {
	return PHP_IF;
}

<ST_PHP_IN_SCRIPTING>"elseif" {
	return PHP_ELSEIF;
}

<ST_PHP_IN_SCRIPTING>"endif" {
	return PHP_ENDIF;
}

<ST_PHP_IN_SCRIPTING>"else" {
	return PHP_ELSE;
}

<ST_PHP_IN_SCRIPTING>"while" {
	return PHP_WHILE;
}

<ST_PHP_IN_SCRIPTING>"endwhile" {
	return PHP_ENDWHILE;
}

<ST_PHP_IN_SCRIPTING>"do" {
	return PHP_DO;
}

<ST_PHP_IN_SCRIPTING>"for" {
	return PHP_FOR;
}

<ST_PHP_IN_SCRIPTING>"endfor" {
	return PHP_ENDFOR;
}

<ST_PHP_IN_SCRIPTING>"foreach" {
	return PHP_FOREACH;
}

<ST_PHP_IN_SCRIPTING>"endforeach" {
	return PHP_ENDFOREACH;
}

<ST_PHP_IN_SCRIPTING>"declare" {
	return PHP_DECLARE;
}

<ST_PHP_IN_SCRIPTING>"enddeclare" {
	return PHP_ENDDECLARE;
}

<ST_PHP_IN_SCRIPTING>"instanceof" {
	return PHP_INSTANCEOF;
}

<ST_PHP_IN_SCRIPTING>"as" {
	return PHP_AS;
}

<ST_PHP_IN_SCRIPTING>"switch" {
	return PHP_SWITCH;
}

<ST_PHP_IN_SCRIPTING>"endswitch" {
	return PHP_ENDSWITCH;
}

<ST_PHP_IN_SCRIPTING>"case" {
	return PHP_CASE;
}

<ST_PHP_IN_SCRIPTING>"default" {
	return PHP_DEFAULT;
}

<ST_PHP_IN_SCRIPTING>"break" {
	return PHP_BREAK;
}

<ST_PHP_IN_SCRIPTING>"continue" {
	return PHP_CONTINUE;
}

<ST_PHP_IN_SCRIPTING>"echo" {
	return PHP_ECHO;
}

<ST_PHP_IN_SCRIPTING>"goto" {
	return PHP_GOTO;
}

<ST_PHP_IN_SCRIPTING>"print" {
	return PHP_PRINT;
}

<ST_PHP_IN_SCRIPTING,ST_PHP_IDENTIFIER>"class" {
	return PHP_CLASS;
}

<ST_PHP_IN_SCRIPTING>"trait" {
	return PHP_TRAIT;
}

<ST_PHP_IN_SCRIPTING>"insteadof" {
	return PHP_INSTEADOF;
}

<ST_PHP_IN_SCRIPTING>"callable" {
	return PHP_CALLABLE;
}

<ST_PHP_IN_SCRIPTING>"interface" {
	return PHP_INTERFACE;
}

<ST_PHP_IN_SCRIPTING>"extends" {
	return PHP_EXTENDS;
}

<ST_PHP_IN_SCRIPTING>"implements" {
	return PHP_IMPLEMENTS;
}

<ST_PHP_IN_SCRIPTING>"self" {
	return PHP_SELF;
}

<ST_PHP_IN_SCRIPTING>"->" {
	pushState(ST_PHP_LOOKING_FOR_PROPERTY);
	return PHP_OBJECT_OPERATOR;
}

<ST_PHP_QUOTES_AFTER_VARIABLE> {
	"->" {
	popState();
	pushState(ST_PHP_LOOKING_FOR_PROPERTY);
	return PHP_OBJECT_OPERATOR;
	}
	{ANY_CHAR} {
		yypushback(1);
		popState();
	}
}

<ST_PHP_IN_SCRIPTING,ST_PHP_LOOKING_FOR_PROPERTY,ST_PHP_IDENTIFIER>{WHITESPACES} {
	return WHITESPACE;
}

<ST_PHP_LOOKING_FOR_PROPERTY>"->" {
	return PHP_OBJECT_OPERATOR;
}

<ST_PHP_LOOKING_FOR_PROPERTY,ST_PHP_IDENTIFIER>{LABEL} {
	popState();
	return PHP_LABEL;
}

<ST_PHP_LOOKING_FOR_PROPERTY,ST_PHP_IDENTIFIER>{ANY_CHAR} {
	yypushback(1);
	popState();
}

<ST_PHP_IN_SCRIPTING>"::" {
	pushState(ST_PHP_IDENTIFIER);
	return PHP_PAAMAYIM_NEKUDOTAYIM;
}
<ST_PHP_IDENTIFIER>"::" {
	return PHP_PAAMAYIM_NEKUDOTAYIM;
}

<ST_PHP_IN_SCRIPTING>"\\" {
	return PHP_NS_SEPARATOR;
}

<ST_PHP_IN_SCRIPTING>"new" {
	return PHP_NEW;
}

<ST_PHP_IN_SCRIPTING>"clone" {
	return PHP_CLONE;
}

<ST_PHP_IN_SCRIPTING>"var" {
	return PHP_VAR;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}("int"|"integer"){TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}("real"|"double"|"float"){TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}"string"{TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}"binary"{TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}"array"{TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}"object"{TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}("bool"|"boolean"){TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"("{TABS_AND_SPACES}("unset"){TABS_AND_SPACES}")" {
	return PHP_CASTING;
}

<ST_PHP_IN_SCRIPTING>"eval" {
	return PHP_EVAL;
}

<ST_PHP_IN_SCRIPTING>"include" {
	return PHP_INCLUDE;
}

<ST_PHP_IN_SCRIPTING>"include_once" {
	return PHP_INCLUDE_ONCE;
}

<ST_PHP_IN_SCRIPTING>"require" {
	return PHP_REQUIRE;
}

<ST_PHP_IN_SCRIPTING>"require_once" {
	return PHP_REQUIRE_ONCE;
}

<ST_PHP_IN_SCRIPTING>"namespace" {
	return PHP_NAMESPACE;
}

<ST_PHP_IN_SCRIPTING>"use" {
	return PHP_USE;
}

<ST_PHP_IN_SCRIPTING>"global" {
	return PHP_GLOBAL;
}

<ST_PHP_IN_SCRIPTING>"isset" {
	return PHP_ISSET;
}

<ST_PHP_IN_SCRIPTING>"empty" {
	return PHP_EMPTY;
}

<ST_PHP_IN_SCRIPTING>"__halt_compiler" {
	return PHP_HALT_COMPILER;
}

<ST_PHP_IN_SCRIPTING>"static" {
	return PHP_STATIC;
}

<ST_PHP_IN_SCRIPTING>"abstract" {
	return PHP_ABSTRACT;
}

<ST_PHP_IN_SCRIPTING>"final" {
	return PHP_FINAL;
}

<ST_PHP_IN_SCRIPTING>"private" {
	return PHP_PRIVATE;
}

<ST_PHP_IN_SCRIPTING>"protected" {
	return PHP_PROTECTED;
}

<ST_PHP_IN_SCRIPTING>"public" {
	return PHP_PUBLIC;
}

<ST_PHP_IN_SCRIPTING>"unset" {
	return PHP_UNSET;
}

<ST_PHP_IN_SCRIPTING>"list" {
	return PHP_LIST;
}

<ST_PHP_IN_SCRIPTING>"array" {
	return PHP_ARRAY;
}

<ST_PHP_IN_SCRIPTING>"string" {
	return PHP_STRING;
}

<ST_PHP_IN_SCRIPTING>"int" {
	return PHP_INT;
}

<ST_PHP_IN_SCRIPTING>"float" {
	return PHP_FLOAT;
}

<ST_PHP_IN_SCRIPTING>"bool" {
	return PHP_BOOL;
}

<ST_PHP_IN_SCRIPTING>"void" {
	return PHP_VOID;
}

<ST_PHP_IN_SCRIPTING>"object" {
	return PHP_OBJECT;
}

<ST_PHP_IN_SCRIPTING>"parent" {
	return PHP_PARENT;
}

<ST_PHP_IN_SCRIPTING>"from" {
	return PHP_FROM;
}

<ST_PHP_IN_SCRIPTING>"true" {
	return PHP_TRUE;
}

<ST_PHP_IN_SCRIPTING>"false" {
	return PHP_FALSE;
}

<ST_PHP_IN_SCRIPTING>{PHP_OPERATOR} {
	return PHP_OPERATOR;
}

<ST_PHP_IN_SCRIPTING>{TOKENS} {
	return PHP_TOKEN;
}
<ST_PHP_IDENTIFIER>"&" {
	return PHP_TOKEN;
}

<ST_PHP_IN_SCRIPTING>{CLOSE_EXPRESSION} {
	return PHP_SEMICOLON;
}

<ST_PHP_IN_SCRIPTING>"{"{WHITESPACES}? {
	// Whitespaces are directly appended to the curly before pushState()
	// is effective or whitespaces would be stored in a separate ContextRegion.
	// Method PhpTokenContainer#addLast() will correct the curlies length...
	if (!phpStack.isEmpty()) {
		// Only push the state when stack is not empty, it's useless otherwise
		// (and it pollutes the stack).
		pushState(ST_PHP_IN_SCRIPTING);
	}
	return PHP_CURLY_OPEN;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"${" {
	// We can have nested curlies after applying this rule,
	// so we have to count all curlies...
	yypushback(1);
	pushState(ST_PHP_DOLLAR_CURLY_OPEN);
	return PHP_TOKEN;
}

<ST_PHP_DOLLAR_CURLY_OPEN>"{"{WHITESPACES}? {
	yybegin(ST_PHP_IN_SCRIPTING);
	return PHP_CURLY_OPEN;
}

<ST_PHP_DOLLAR_CURLY_OPEN>"{"{LABEL} {
	yypushback(yylength() - 1);
	return PHP_CURLY_OPEN;
}

<ST_PHP_DOLLAR_CURLY_OPEN>{LABEL} {
	yybegin(ST_PHP_IN_SCRIPTING);
	return PHP_ENCAPSED_VARIABLE;
}

<ST_PHP_IN_SCRIPTING>"}"{WHITESPACES}? {
	// Whitespaces are directly appended to the curly before pushState()
	// is effective or whitespaces would be stored in a separate ContextRegion.
	// Method PhpTokenContainer#addLast() will correct the curlies length...
	if (!phpStack.isEmpty()) {
		popState();
		// If new state is not ST_PHP_IN_SCRIPTING then some blanks are signifiant (like newlines
		// after the closing curly and before an ending heredoc tag), so don't append them...
		// See rule <ST_PHP_HEREDOC>{HEREDOC_CHARS}*(({HEREDOC_NEWLINE}{TABS_AND_SPACES})+)?
		if (getZZLexicalState() != ST_PHP_IN_SCRIPTING) {
			yypushback(yylength() - 1);
		}
	}
	return PHP_CURLY_CLOSE;
}

<ST_PHP_IN_SCRIPTING>{BNUM} {
	return PHP_NUMBER;
}

<ST_PHP_IN_SCRIPTING>{LNUM} {
	return PHP_NUMBER;
}

<ST_PHP_IN_SCRIPTING>{HNUM} {
	return PHP_NUMBER;
}

<ST_PHP_VAR_OFFSET>[0]|([1-9][0-9]*) { /* Offset could be treated as a long */
	return PHP_NUMBER;
}

<ST_PHP_VAR_OFFSET>{LNUM}|{HNUM}|{BNUM} {
	return PHP_NUMBER;
}

<ST_PHP_IN_SCRIPTING>{DNUM}|{EXPONENT_DNUM} {
	return PHP_NUMBER;
}

<ST_PHP_IN_SCRIPTING>"__CLASS__" {
	return PHP__CLASS__;
}

<ST_PHP_IN_SCRIPTING>"__FUNCTION__" {
	return PHP__FUNCTION__;
}

<ST_PHP_IN_SCRIPTING>"__METHOD__" {
	return PHP__METHOD__;
}

<ST_PHP_IN_SCRIPTING>"__LINE__" {
	return PHP__LINE__;
}

<ST_PHP_IN_SCRIPTING>"__FILE__" {
	return PHP__FILE__;
}

<ST_PHP_IN_SCRIPTING>"__DIR__" {
	return PHP__DIR__;
}

<ST_PHP_IN_SCRIPTING>"__NAMESPACE__" {
	return PHP__NAMESPACE__;
}

<ST_PHP_IN_SCRIPTING>"$this" {
	return PHP_THIS;
}

<ST_PHP_IN_SCRIPTING>"$"{LABEL} {
	return PHP_VARIABLE;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC,ST_PHP_VAR_OFFSET>"$"{LABEL} {
	pushState(ST_PHP_QUOTES_AFTER_VARIABLE);
	return PHP_VARIABLE;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_HEREDOC,ST_PHP_BACKQUOTE>"$"{LABEL}"[" {
	yypushback(1);
	pushState(ST_PHP_VAR_OFFSET);
	return PHP_VARIABLE;
}

<ST_PHP_VAR_OFFSET>"]" {
	popState();
	return PHP_TOKEN;
}

<ST_PHP_VAR_OFFSET>"[" {
	return PHP_TOKEN;
}

<ST_PHP_VAR_OFFSET>{TOKENS}|[;{}\"`] {//the difference from the original rules comes from the fact that we took ';' out of tokens
	return UNKNOWN_TOKEN;
}

<ST_PHP_VAR_OFFSET>[ \n\r\t\\'#] {
	yypushback(1);
	popState();
	return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_IN_SCRIPTING,ST_PHP_VAR_OFFSET>"null" {
	return PHP_KEYWORD;
}

<ST_PHP_IN_SCRIPTING,ST_PHP_VAR_OFFSET>{LABEL} {
	return PHP_LABEL;
}

/*
<ST_PHP_IN_SCRIPTING>{WHITESPACES} {
	return WHITESPACE;
}
*/

<ST_PHP_IN_SCRIPTING>([#]|"//") {
	pushState(ST_PHP_LINE_COMMENT);
	return PHP_LINE_COMMENT;
}

<ST_PHP_LINE_COMMENT>"?"|"%"|">" {
	return PHP_LINE_COMMENT;
}

<ST_PHP_LINE_COMMENT>[^\n\r?%>]*{ANY_CHAR} {
	String yytext = yytext();
	switch (yytext.charAt(yytext.length() - 1)) {
		case '?':
		case '%':
		case '>':
			yypushback(1);
			if (yylength() > 0) {
				return PHP_LINE_COMMENT;
			}
			break;
		default:
			popState();
			return PHP_LINE_COMMENT;
	}
}

<ST_PHP_LINE_COMMENT>{NEWLINE} {
	popState();
	return PHP_LINE_COMMENT;
}

<ST_PHP_IN_SCRIPTING>"/**/" {
	yypushback(2);
	pushState(ST_PHP_COMMENT);
	return PHP_COMMENT_START;
}

<ST_PHP_IN_SCRIPTING>"/**" {
	pushState(ST_PHP_DOC_COMMENT);
	return PHPDOC_COMMENT_START;
}

<ST_PHP_DOC_COMMENT>{

	"{@"[a-zA-Z-]+"}" {
		TagKind tagkind = TagKind.getTagKindFromValue(yytext());
		if (tagkind != null && tagkind != TagKind.UNKNOWN) {
			return PHPDOC_GENERIC_TAG;
		}
		return PHPDOC_COMMENT;
	}

	"@"[^ \n\r\t*]+ {
		TagKind tagkind = TagKind.getTagKindFromValue(yytext());
		if (tagkind != null && tagkind != TagKind.UNKNOWN
			&& /* ignore @todo tag */ tagkind != TagKind.TODO) {
				return PHPDOC_GENERIC_TAG;
		}
		return PHPDOC_COMMENT;
	}

	{ANY_CHAR} {return PHPDOC_COMMENT;}
}

<ST_PHP_IN_SCRIPTING>"/*" {
	pushState(ST_PHP_COMMENT);
	return PHP_COMMENT_START;
}

<ST_PHP_COMMENT>[^*]+ {
	return PHP_COMMENT;
}

<ST_PHP_DOC_COMMENT>"*/" {
	popState();
	return PHPDOC_COMMENT_END;
}

<ST_PHP_COMMENT>"*/" {
	popState();
	return PHP_COMMENT_END;
}

<ST_PHP_COMMENT>"*" {
	return PHP_COMMENT;
}

<ST_PHP_IN_SCRIPTING,ST_PHP_LINE_COMMENT>"?>" {
	return PHP_CLOSETAG;
}
<ST_PHP_IN_SCRIPTING>"%>" {
	if (asp_tags) {
		return PHP_CLOSETAG;
	}
	return UNKNOWN_TOKEN;
}

<ST_PHP_LINE_COMMENT>"%>"{NEWLINE} {
	yypushback(yylength() - 2);
	if (asp_tags) {
		return PHP_CLOSETAG;
	}
	popState();
	return PHP_LINE_COMMENT;
}

<ST_PHP_LINE_COMMENT>"%>" {
	if (asp_tags) {
		return PHP_CLOSETAG;
	}
	return PHP_LINE_COMMENT;
}

<ST_PHP_IN_SCRIPTING>(b?[\"]{DOUBLE_QUOTES_CHARS}*("{"*|"$"*)[\"]) {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>b?[\"] {
	pushState(ST_PHP_DOUBLE_QUOTES);
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>(b?[']([^'\\]|("\\"{ANY_CHAR}))*[']) {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>b?['] {
	pushState(ST_PHP_SINGLE_QUOTE);
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>([^'\\]|\\[^'\\])+ {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>"\\'" {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>"\\\\" {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>b?"<<<"{TABS_AND_SPACES}({LABEL}|([']{LABEL}['])|([\"]{LABEL}[\"])){NEWLINE} {
	String yytext = yytext();
	int bprefix = (yytext.charAt(0) != '<') ? 1 : 0;
	int startString = 3 + bprefix;

	int hereOrNowDoc_len = yylength() - bprefix - 3 - 1 - (yytext.charAt(yylength() - 2) == '\r' ? 1 : 0);
	while ((yytext.charAt(startString) == ' ') || (yytext.charAt(startString) == '\t')) {
		startString++;
		hereOrNowDoc_len--;
	}
	String hereOrNowDoc = yytext.substring(startString, hereOrNowDoc_len + startString);
	if (hereOrNowDoc.charAt(0) == '\'') {
		pushHeredocId(hereOrNowDoc.substring(1, hereOrNowDoc_len - 1));
		pushState(ST_PHP_START_NOWDOC);
		return PHP_NOWDOC_START_TAG;
	} else if (hereOrNowDoc.charAt(0) == '"') {
		pushHeredocId(hereOrNowDoc.substring(1, hereOrNowDoc_len - 1));
		pushState(ST_PHP_START_HEREDOC);
		return PHP_HEREDOC_START_TAG;
	} else {
		pushHeredocId(hereOrNowDoc);
		pushState(ST_PHP_START_HEREDOC);
		return PHP_HEREDOC_START_TAG;
	}
}

<ST_PHP_IN_SCRIPTING>[`] {
	pushState(ST_PHP_BACKQUOTE);
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_START_HEREDOC>{TABS_AND_SPACES}{LABEL} {
	String yytext = yytext();
	int label_len = yylength();
	String heredoc = getHeredocId();
	int heredoc_len = heredoc.length();
	if (label_len > heredoc_len && yytext.substring(label_len - heredoc_len, label_len).equals(heredoc)) {
		char c = yytext.charAt(label_len - heredoc_len - 1);
		if (c == ' ' || c == '\t') {
			popHeredocId();
			popState();
			return PHP_HEREDOC_CLOSE_TAG;
		}
	}
	if (label_len == heredoc_len && yytext.substring(0, label_len).equals(heredoc)) {
		popHeredocId();
		popState();
		return PHP_HEREDOC_CLOSE_TAG;
	}
	return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_START_HEREDOC>{ANY_CHAR} {
	yypushback(1);
	yybegin(ST_PHP_HEREDOC);
}

<ST_PHP_HEREDOC>{HEREDOC_CHARS}*({HEREDOC_NEWLINE}{TABS_AND_SPACES})+{LABEL} {
	String yytext = yytext();
	int label_len = yytext.length();
	String heredoc = getHeredocId();
	int heredoc_len = heredoc.length();
	int startIndex = label_len - heredoc_len;
	if (startIndex > 0 && yytext.substring(startIndex, label_len).equals(heredoc)) {
		char c = yytext.charAt(startIndex - 1);
		if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {

			if (c == ' ' || c == '\t') {
				startIndex--;
			}
			while (startIndex > 0 && yytext.charAt(startIndex - 1) != '\n'
				&& yytext.charAt(startIndex - 1) != '\r') {
				startIndex--;
			}

			if (startIndex - 2 >= 0
				&& yytext.charAt(startIndex - 2) == '\r'
				&& yytext.charAt(startIndex - 1) == '\n') {
				startIndex-= 2;
			} else {
				startIndex--;
			}
			yypushback(yylength() - startIndex);

			yybegin(ST_PHP_END_HEREDOC);
		}
	}
	// In some cases, all text is pushed back (using yypushback()),
	// especially when the parsed document has Windows newlines.
	// In those cases, ignore this rule and try next one...
	if (yylength() > 0) {
		return PHP_ENCAPSED_AND_WHITESPACE;
	}
}

<ST_PHP_END_HEREDOC>{NEWLINE}{TABS_AND_SPACES}{LABEL} {
	popHeredocId();
	popState();
	return PHP_HEREDOC_CLOSE_TAG;
}

<ST_PHP_START_NOWDOC>{TABS_AND_SPACES}{LABEL} {
	String yytext = yytext();
	int label_len = yylength();
	String nowdoc = getHeredocId();
	int nowdoc_len = nowdoc.length();
	if (label_len > nowdoc_len && yytext.substring(label_len - nowdoc_len, label_len).equals(nowdoc)) {
		char c = yytext.charAt(label_len - nowdoc_len - 1);
		if (c == ' ' || c == '\t') {
			popHeredocId();
			popState();
			return PHP_NOWDOC_CLOSE_TAG;
		}
	}
	if (label_len == nowdoc_len && yytext.substring(0, label_len).equals(nowdoc)) {
		popHeredocId();
		popState();
		return PHP_NOWDOC_CLOSE_TAG;
	}
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_START_NOWDOC>{ANY_CHAR} {
	yypushback(1);
	yybegin(ST_PHP_NOWDOC);
}

<ST_PHP_NOWDOC>{NOWDOC_CHARS}*({NEWLINE}{TABS_AND_SPACES})+{LABEL} {
	String yytext = yytext();
	int label_len = yytext.length();
	String nowdoc = getHeredocId();
	int nowdoc_len = nowdoc.length();
	int startIndex = label_len - nowdoc_len;
	if (startIndex > 0 && yytext.substring(startIndex, label_len).equals(nowdoc)) {
		char c = yytext.charAt(startIndex - 1);
		if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {

			if (c == ' ' || c == '\t') {
				startIndex--;
			}
			while (startIndex > 0 && yytext.charAt(startIndex - 1) != '\n'
				&& yytext.charAt(startIndex - 1) != '\r') {
				startIndex--;
			}

			if (startIndex - 2 >= 0
				&& yytext.charAt(startIndex - 2) == '\r'
				&& yytext.charAt(startIndex - 1) == '\n') {
				startIndex-= 2;
			} else {
				startIndex--;
			}
			yypushback(yylength() - startIndex);

			yybegin(ST_PHP_END_NOWDOC);
		}
	}
	// In some cases, all text is pushed back (using yypushback()),
	// especially when the parsed document has Windows newlines.
	// In those cases, ignore this rule and try next one...
	if (yylength() > 0) {
		return PHP_CONSTANT_ENCAPSED_STRING;
	}
}

<ST_PHP_END_NOWDOC>{NEWLINE}{TABS_AND_SPACES}{LABEL} {
	popHeredocId();
	popState();
	return PHP_NOWDOC_CLOSE_TAG;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC,ST_PHP_QUOTES_AFTER_VARIABLE>"{$" {
	// We can have nested curlies after applying rule below for "${",
	// so we have to count all curlies...
	yypushback(1);
	pushState(ST_PHP_IN_SCRIPTING);
	return PHP_CURLY_OPEN;
}

<ST_PHP_DOUBLE_QUOTES>{DOUBLE_QUOTES_CHARS}+ {
	return PHP_ENCAPSED_AND_WHITESPACE;
}

/*
The original parsing rule was {DOUBLE_QUOTES_CHARS}*("{"{2,}|"$"{2,}|(("{"+|"$"+)[\"]))
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_PHP_DOUBLE_QUOTES>{DOUBLE_QUOTES_CHARS}*("{""{"+|"$""$"+|(("{"+|"$"+)[\"])) {
	yypushback(1);
	return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_BACKQUOTE>{BACKQUOTE_CHARS}+ {
	return PHP_ENCAPSED_AND_WHITESPACE;
}

/*
The original parsing rule was {BACKQUOTE_CHARS}*("{"{2,}|"$"{2,}|(("{"+|"$"+)[`]))
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_PHP_BACKQUOTE>{BACKQUOTE_CHARS}*("{""{"+|"$""$"+|(("{"+|"$"+)[`])) {
	yypushback(1);
	return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_HEREDOC>{HEREDOC_CHARS}*(({HEREDOC_NEWLINE}{TABS_AND_SPACES})+)? {
	return PHP_ENCAPSED_AND_WHITESPACE;
}

/*
The original parsing rule was {HEREDOC_CHARS}*(({HEREDOC_NEWLINE}{TABS_AND_SPACES})+)?("{"{2,}|"$"{2,})
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_PHP_HEREDOC>{HEREDOC_CHARS}*(({HEREDOC_NEWLINE}{TABS_AND_SPACES})+)?("{""{"+|"$""$"+) {
	yypushback(1);
	return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_NOWDOC>{NOWDOC_CHARS}*(({NEWLINE}{TABS_AND_SPACES})+)? {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES>[\"] {
	popState();
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>['] {
	popState();
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>[`] {
	popState();
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES>. {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>. {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>. {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

/* ============================================
   Stay in this state until we find a whitespace.
   After we find a whitespace we go the the prev state and try again from the next token.
   ============================================ */
<ST_PHP_HIGHLIGHTING_ERROR> {
	{WHITESPACES}   {popState();return WHITESPACE;}
	.               {return UNKNOWN_TOKEN;}
}

/* ============================================
   This rule must be the last in the section!!
   it should contain all the states.
   ============================================ */
<ST_PHP_IN_SCRIPTING,ST_PHP_DOUBLE_QUOTES,ST_PHP_VAR_OFFSET,ST_PHP_SINGLE_QUOTE,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC,ST_PHP_START_HEREDOC,ST_PHP_END_HEREDOC,ST_PHP_START_NOWDOC,ST_PHP_END_NOWDOC,ST_PHP_NOWDOC,ST_PHP_DOLLAR_CURLY_OPEN>. {
	yypushback(1);
	pushState(ST_PHP_HIGHLIGHTING_ERROR);
}
