/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/

package org.eclipse.php.internal.core.ast.scanner;

import org.eclipse.php.internal.core.ast.nodes.AST;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.phpModel.javacup.sym;
import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;
import org.eclipse.php.internal.core.phpModel.parser.StateStack;
import org.eclipse.php.internal.core.ast.nodes.IDocumentorLexer;


%%

%class PhpAstLexer4
%public
%unicode
%line

/* %cup */
%implements org.eclipse.php.internal.core.ast.scanner.AstLexer
%function next_token
%type org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol
%eofval{
    return createSymbol(sym.EOF);
%eofval}
%eofclose

%caseless

%standalone
%state ST_IN_SCRIPTING
%state ST_DOUBLE_QUOTES
%state ST_SINGLE_QUOTE
%state ST_BACKQUOTE
%state ST_HEREDOC
%state ST_LOOKING_FOR_PROPERTY
%state ST_LOOKING_FOR_VARNAME
%state ST_COMMENT
%state ST_DOCBLOCK
%state ST_ONE_LINE_COMMENT
%{
	private final LinkedList commentList = new LinkedList();
	private String heredoc = null;
    private boolean asp_tags = false;
    private boolean short_tags_allowed = true;
    private StateStack stack = new StateStack();
    private char zzOld_buffer[] = new char[ZZ_BUFFERSIZE];
    private int zzOld_pushbackPos;
    protected int commentStartPosition;

	private AST ast;

    public void setAST(AST ast) {
    	this.ast = ast;
    }
    
	public String getPHPVersion() {
		return AST.PHP4;
	}
    
	public void setInScriptingState() {
		yybegin(ST_IN_SCRIPTING);
	}

	public void resetCommentList() {
		commentList.clear();
	}
	
	public LinkedList getCommentList() {
		return commentList;
	}
	
	protected void addComment(int type) {
		int leftPosition = getTokenStartPosition();
		Comment comment = new Comment(commentStartPosition, leftPosition + getTokenLength(), this.ast, type);
		commentList.add(comment);
	}	

	public void setUseAspTagsAsPhp(boolean useAspTagsAsPhp) {
		asp_tags = useAspTagsAsPhp;
	}
	
    private void pushState(int state) {
        stack.pushStack(zzLexicalState);
        yybegin(state);
    }

    private void popState() {
        yybegin(stack.popStack());
    }

    public int getCurrentLine() {
        return yyline;
    }

    protected int getTokenStartPosition() {
        return zzStartRead - zzPushbackPos;
    }

    protected int getTokenLength() {
        return zzMarkedPos - zzStartRead;
    }

    public int getLength() {
        return zzEndRead - zzPushbackPos;
    }
    
	private void handleCommentStart() {
		commentStartPosition = getTokenStartPosition();
	}
	
	private void handleLineCommentEnd() {
         addComment(Comment.TYPE_SINGLE_LINE);
    }

    private void handleMultilineCommentEnd() {
    	addComment(Comment.TYPE_MULTILINE);
    }

    private void handlePHPDocEnd() {
		addComment(Comment.TYPE_PHPDOC);
    }
    
    private void handleVarComment() {
    	commentStartPosition = zzStartRead;
    	addComment(Comment.TYPE_MULTILINE);
    }

    private Symbol createFullSymbol(int symbolNumber) {
        Symbol symbol = createSymbol(symbolNumber);
        symbol.value = yytext();
        return symbol;
    }

    protected Symbol createSymbol(int symbolNumber) {
        int leftPosition = getTokenStartPosition();
        return new Symbol(symbolNumber, leftPosition, leftPosition + getTokenLength());
    }
    
    public int[] getParamenters(){
    	return new int[]{zzMarkedPos, zzPushbackPos, zzCurrentPos, zzStartRead, zzEndRead, yyline};
    }
    
	protected boolean parsePHPDoc(){	
		final IDocumentorLexer documentorLexer = getDocumentorLexer(zzReader);
		if(documentorLexer == null){
			return false;
		}
		yypushback(zzMarkedPos - zzStartRead);
		int[] parameters = getParamenters();
		documentorLexer.reset(zzReader, zzBuffer, parameters);
		Object phpDocBlock = documentorLexer.parse();
		commentList.add(phpDocBlock);
		reset(zzReader, documentorLexer.getBuffer(), documentorLexer.getParamenters());
		return true;
	}
	
	
	protected IDocumentorLexer getDocumentorLexer(java.io.Reader  reader) {
		return null;
	}
	
	public void reset(java.io.Reader  reader, char[] buffer, int[] parameters){
		this.zzReader = reader;
		this.zzBuffer = buffer;
		this.zzMarkedPos = parameters[0];
		this.zzPushbackPos = parameters[1];
		this.zzCurrentPos = parameters[2];
		this.zzStartRead = parameters[3];
		this.zzEndRead = parameters[4];
		this.yyline = parameters[5];  
		this.yychar = this.zzStartRead - this.zzPushbackPos;
	}
%}

LNUM=[0-9]+
DNUM=([0-9]*[\.][0-9]+)|([0-9]+[\.][0-9]*)
EXPONENT_DNUM=(({LNUM}|{DNUM})[eE][+-]?{LNUM})
HNUM="0x"[0-9a-fA-F]+
LABEL=[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*
WHITESPACE=[ \n\r\t]+
TABS_AND_SPACES=[ \t]*
ESCAPED_AND_WHITESPACE=[\n\t\r #'.:;,()|\^&+-//*=%!~<>?@]+
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")

%%

<ST_IN_SCRIPTING>"exit" {
	return createFullSymbol(ParserConstants4.T_EXIT);
}

<ST_IN_SCRIPTING>"die" {
	return createFullSymbol(ParserConstants4.T_EXIT);
}

<ST_IN_SCRIPTING>"old_function" {   
	return createSymbol(ParserConstants4.T_OLD_FUNCTION);
}

<ST_IN_SCRIPTING>"function"|"cfunction" {
	return createSymbol(ParserConstants4.T_FUNCTION);
}

<ST_IN_SCRIPTING>"const" {
	return createSymbol(ParserConstants4.T_CONST);
}

<ST_IN_SCRIPTING>"return" {
	return createSymbol(ParserConstants4.T_RETURN);
}

<ST_IN_SCRIPTING>"if" {
	return createSymbol(ParserConstants4.T_IF);
}

<ST_IN_SCRIPTING>"elseif" {
	return createSymbol(ParserConstants4.T_ELSEIF);
}

<ST_IN_SCRIPTING>"endif" {
	return createSymbol(ParserConstants4.T_ENDIF);
}

<ST_IN_SCRIPTING>"else" {
	return createSymbol(ParserConstants4.T_ELSE);
}

<ST_IN_SCRIPTING>"while" {
	return createSymbol(ParserConstants4.T_WHILE);
}

<ST_IN_SCRIPTING>"endwhile" {
	return createSymbol(ParserConstants4.T_ENDWHILE);
}

<ST_IN_SCRIPTING>"do" {
	return createSymbol(ParserConstants4.T_DO);
}

<ST_IN_SCRIPTING>"for" {
	return createSymbol(ParserConstants4.T_FOR);
}

<ST_IN_SCRIPTING>"endfor" {
	return createSymbol(ParserConstants4.T_ENDFOR);
}

<ST_IN_SCRIPTING>"foreach" {
	return createSymbol(ParserConstants4.T_FOREACH);
}

<ST_IN_SCRIPTING>"endforeach" {
	return createSymbol(ParserConstants4.T_ENDFOREACH);
}

<ST_IN_SCRIPTING>"declare" {
	return createSymbol(ParserConstants4.T_DECLARE);
}

<ST_IN_SCRIPTING>"enddeclare" {
	return createSymbol(ParserConstants4.T_ENDDECLARE);
}

<ST_IN_SCRIPTING>"as" {
	return createSymbol(ParserConstants4.T_AS);
}

<ST_IN_SCRIPTING>"switch" {
	return createSymbol(ParserConstants4.T_SWITCH);
}

<ST_IN_SCRIPTING>"endswitch" {
	return createSymbol(ParserConstants4.T_ENDSWITCH);
}

<ST_IN_SCRIPTING>"case" {
	return createSymbol(ParserConstants4.T_CASE);
}

<ST_IN_SCRIPTING>"default" {
	return createSymbol(ParserConstants4.T_DEFAULT);
}

<ST_IN_SCRIPTING>"break" {
	return createSymbol(ParserConstants4.T_BREAK);
}

<ST_IN_SCRIPTING>"continue" {
	return createSymbol(ParserConstants4.T_CONTINUE);
}

<ST_IN_SCRIPTING>"echo" {
	return createSymbol(ParserConstants4.T_ECHO);
}

<ST_IN_SCRIPTING>"print" {
	return createSymbol(ParserConstants4.T_PRINT);
}

<ST_IN_SCRIPTING>"class" {
	return createSymbol(ParserConstants4.T_CLASS);
}

<ST_IN_SCRIPTING>"extends" {
	return createSymbol(ParserConstants4.T_EXTENDS);
}

<ST_IN_SCRIPTING,ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"->" {
    pushState(ST_LOOKING_FOR_PROPERTY);
    return createSymbol(ParserConstants4.T_OBJECT_OPERATOR);
}

<ST_LOOKING_FOR_PROPERTY>{LABEL} {
    popState();
    return createFullSymbol(ParserConstants4.T_STRING);
}

<ST_LOOKING_FOR_PROPERTY>{ANY_CHAR} {
    yypushback(yylength());
    popState();
}

<ST_IN_SCRIPTING>"::" {
	return createSymbol(ParserConstants4.T_PAAMAYIM_NEKUDOTAYIM);
}

<ST_IN_SCRIPTING>"new" {
	return createSymbol(ParserConstants4.T_NEW);
}

<ST_IN_SCRIPTING>"var" {
	return createSymbol(ParserConstants4.T_VAR);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("int"|"integer"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_INT_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("real"|"double"|"float"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_DOUBLE_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"string"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_STRING_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"array"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_ARRAY_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"object"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_OBJECT_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("bool"|"boolean"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_BOOL_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("unset"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants4.T_UNSET_CAST);
}

<ST_IN_SCRIPTING>"eval" {
	return createSymbol(ParserConstants4.T_EVAL);
}

<ST_IN_SCRIPTING>"include" {
	return createSymbol(ParserConstants4.T_INCLUDE);
}

<ST_IN_SCRIPTING>"include_once" {
	return createSymbol(ParserConstants4.T_INCLUDE_ONCE);
}

<ST_IN_SCRIPTING>"require" {
	return createSymbol(ParserConstants4.T_REQUIRE);
}

<ST_IN_SCRIPTING>"require_once" {
	return createSymbol(ParserConstants4.T_REQUIRE_ONCE);
}

<ST_IN_SCRIPTING>"use" {
	return createSymbol(ParserConstants4.T_USE);
}

<ST_IN_SCRIPTING>"global" {
	return createSymbol(ParserConstants4.T_GLOBAL);
}

<ST_IN_SCRIPTING>"isset" {
	return createSymbol(ParserConstants4.T_ISSET);
}

<ST_IN_SCRIPTING>"empty" {
	return createSymbol(ParserConstants4.T_EMPTY);
}

<ST_IN_SCRIPTING>"static" {
	return createSymbol(ParserConstants4.T_STATIC);
}

<ST_IN_SCRIPTING>"unset" {
	return createSymbol(ParserConstants4.T_UNSET);
}

<ST_IN_SCRIPTING>"=>" {
	return createSymbol(ParserConstants4.T_DOUBLE_ARROW);
}

<ST_IN_SCRIPTING>"list" {
	return createSymbol(ParserConstants4.T_LIST);
}

<ST_IN_SCRIPTING>"array" {
	return createSymbol(ParserConstants4.T_ARRAY);
}

<ST_IN_SCRIPTING>"++" {
	return createSymbol(ParserConstants4.T_INC);
}

<ST_IN_SCRIPTING>"--" {
	return createSymbol(ParserConstants4.T_DEC);
}

<ST_IN_SCRIPTING>"===" {
	return createSymbol(ParserConstants4.T_IS_IDENTICAL);
}

<ST_IN_SCRIPTING>"!==" {
	return createSymbol(ParserConstants4.T_IS_NOT_IDENTICAL);
}

<ST_IN_SCRIPTING>"==" {
	return createSymbol(ParserConstants4.T_IS_EQUAL);
}

<ST_IN_SCRIPTING>"!="|"<>" {
	return createSymbol(ParserConstants4.T_IS_NOT_EQUAL);
}

<ST_IN_SCRIPTING>"<=" {
	return createSymbol(ParserConstants4.T_IS_SMALLER_OR_EQUAL);
}

<ST_IN_SCRIPTING>">=" {
	return createSymbol(ParserConstants4.T_IS_GREATER_OR_EQUAL);
}

<ST_IN_SCRIPTING>"+=" {
	return createSymbol(ParserConstants4.T_PLUS_EQUAL);
}

<ST_IN_SCRIPTING>"-=" {
	return createSymbol(ParserConstants4.T_MINUS_EQUAL);
}

<ST_IN_SCRIPTING>"*=" {
	return createSymbol(ParserConstants4.T_MUL_EQUAL);
}

<ST_IN_SCRIPTING>"/=" {
	return createSymbol(ParserConstants4.T_DIV_EQUAL);
}

<ST_IN_SCRIPTING>".=" {
	return createSymbol(ParserConstants4.T_CONCAT_EQUAL);
}

<ST_IN_SCRIPTING>"%=" {
	return createSymbol(ParserConstants4.T_MOD_EQUAL);
}

<ST_IN_SCRIPTING>"<<=" {
	return createSymbol(ParserConstants4.T_SL_EQUAL);
}

<ST_IN_SCRIPTING>">>=" {
	return createSymbol(ParserConstants4.T_SR_EQUAL);
}

<ST_IN_SCRIPTING>"&=" {
	return createSymbol(ParserConstants4.T_AND_EQUAL);
}

<ST_IN_SCRIPTING>"|=" {
	return createSymbol(ParserConstants4.T_OR_EQUAL);
}

<ST_IN_SCRIPTING>"^=" {
	return createSymbol(ParserConstants4.T_XOR_EQUAL);
}

<ST_IN_SCRIPTING>"||" {
	return createSymbol(ParserConstants4.T_BOOLEAN_OR);
}

<ST_IN_SCRIPTING>"&&" {
	return createSymbol(ParserConstants4.T_BOOLEAN_AND);
}

<ST_IN_SCRIPTING>"OR" {
	return createSymbol(ParserConstants4.T_LOGICAL_OR);
}

<ST_IN_SCRIPTING>"AND" {
	return createSymbol(ParserConstants4.T_LOGICAL_AND);
}

<ST_IN_SCRIPTING>"XOR" {
	return createSymbol(ParserConstants4.T_LOGICAL_XOR);
}

<ST_IN_SCRIPTING>"<<" {
	return createSymbol(ParserConstants4.T_SL);
}

<ST_IN_SCRIPTING>">>" {
	return createSymbol(ParserConstants4.T_SR);
}

// TOKENS
<ST_IN_SCRIPTING> {
    ";"                     {return createSymbol(ParserConstants4.T_SEMICOLON);}
    ":"                     {return createSymbol(ParserConstants4.T_NEKUDOTAIM);}
    ","                     {return createSymbol(ParserConstants4.T_COMMA);}
    "."                     {return createSymbol(ParserConstants4.T_NEKUDA);}
    "["                     {return createSymbol(ParserConstants4.T_OPEN_RECT);}
    "]"                     {return createSymbol(ParserConstants4.T_CLOSE_RECT);}
    "("                     {return createSymbol(ParserConstants4.T_OPEN_PARENTHESE);}
    ")"                     {return createSymbol(ParserConstants4.T_CLOSE_PARENTHESE);}
    "|"                     {return createSymbol(ParserConstants4.T_OR);}
    "^"                     {return createSymbol(ParserConstants4.T_KOVA);}
    "&"                     {return createSymbol(ParserConstants4.T_REFERENCE);}
    "+"                     {return createSymbol(ParserConstants4.T_PLUS);}
    "-"                     {return createSymbol(ParserConstants4.T_MINUS);}
    "/"                     {return createSymbol(ParserConstants4.T_DIV);}
    "*"                     {return createSymbol(ParserConstants4.T_TIMES);}
    "="                     {return createSymbol(ParserConstants4.T_EQUAL);}
    "%"                     {return createSymbol(ParserConstants4.T_PRECENT);}
    "!"                     {return createSymbol(ParserConstants4.T_NOT);}
    "~"                     {return createSymbol(ParserConstants4.T_TILDA);}
    "$"                     {return createSymbol(ParserConstants4.T_DOLLAR);}
    "<"                     {return createSymbol(ParserConstants4.T_RGREATER);}
    ">"                     {return createSymbol(ParserConstants4.T_LGREATER);}
    "?"                     {return createSymbol(ParserConstants4.T_QUESTION_MARK);}
    "@"                     {return createSymbol(ParserConstants4.T_AT);}
}

<ST_IN_SCRIPTING>"{" {
    pushState(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants4.T_CURLY_OPEN);

}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"${" {
    pushState(ST_LOOKING_FOR_VARNAME);
    return createSymbol(ParserConstants4.T_DOLLAR_OPEN_CURLY_BRACES);
}

<ST_IN_SCRIPTING>"}" {
	/* This is a temporary fix which is dependant on flex and it's implementation */
    if (!stack.isEmpty()) {
        popState();
    }
    return createSymbol(ParserConstants4.T_CURLY_CLOSE);
}

<ST_LOOKING_FOR_VARNAME>{LABEL} {
    popState();
    pushState(ST_IN_SCRIPTING);
    return createFullSymbol(ParserConstants4.T_STRING_VARNAME);
}

<ST_LOOKING_FOR_VARNAME>{ANY_CHAR} {
    yypushback(yylength());
    popState();
    pushState(ST_IN_SCRIPTING);
}

<ST_IN_SCRIPTING>{LNUM} {
    return createFullSymbol(ParserConstants4.T_LNUMBER);
}

<ST_IN_SCRIPTING>{HNUM} {
    return createFullSymbol(ParserConstants4.T_DNUMBER);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>{LNUM}|{HNUM} { /* treat numbers (almost) as strings inside encapsulated strings */
    return createFullSymbol(ParserConstants4.T_NUM_STRING);
}

<ST_IN_SCRIPTING>{DNUM}|{EXPONENT_DNUM} {
    return createFullSymbol(ParserConstants4.T_DNUMBER);
}

<ST_IN_SCRIPTING>"__CLASS__" {
    return createSymbol(ParserConstants4.T_CLASS_C);
}

<ST_IN_SCRIPTING>"__FUNCTION__" {
    return createSymbol(ParserConstants4.T_FUNC_C);
}

<ST_IN_SCRIPTING>"__LINE__" {
    return createSymbol(ParserConstants4.T_LINE);
}

<ST_IN_SCRIPTING>"__FILE__" {
    return createSymbol(ParserConstants4.T_FILE);
}

<YYINITIAL>(([^<]|"<"[^?%s<])+)|"<s"|"<" {
    return createSymbol(ParserConstants4.T_INLINE_HTML);
}

<YYINITIAL>"<?"|"<script"{WHITESPACE}+"language"{WHITESPACE}*"="{WHITESPACE}*("php"|"\"php\""|"\'php\'"){WHITESPACE}*">" {
    if (short_tags_allowed || yylength()>2) { /* yyleng>2 means it's not <? but <script> */
        yybegin(ST_IN_SCRIPTING);
        //return T_OPEN_TAG;
    } else {
        return createSymbol(ParserConstants4.T_INLINE_HTML);
    }
}

<YYINITIAL>"<%="|"<?=" {
    String text = yytext();
    if ((text.charAt(1)=='%' && asp_tags)
        || (text.charAt(1)=='?' && short_tags_allowed)) {
        yybegin(ST_IN_SCRIPTING);
        //return T_OPEN_TAG_WITH_ECHO;
    } else {
        return createSymbol(ParserConstants4.T_INLINE_HTML);
    }
}

<YYINITIAL>"<%" {
    if (asp_tags) {
        yybegin(ST_IN_SCRIPTING);
		//return T_OPEN_TAG;
    } else {
        return createSymbol(ParserConstants4.T_INLINE_HTML);
    }
}

<YYINITIAL>"<?php"([ \t]|{NEWLINE}) {
    yybegin(ST_IN_SCRIPTING);
	//return T_OPEN_TAG;
}

<ST_IN_SCRIPTING,ST_DOUBLE_QUOTES,ST_HEREDOC,ST_BACKQUOTE>"$"{LABEL} {
    return createFullSymbol(ParserConstants4.T_VARIABLE);
}

<ST_IN_SCRIPTING>"define" {
    /* not a keyword, hust for recognize constans.*/
    return createFullSymbol(ParserConstants4.T_DEFINE);
}

<ST_IN_SCRIPTING>{LABEL} {
    return createFullSymbol(ParserConstants4.T_STRING);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>{LABEL} {
    return createFullSymbol(ParserConstants4.T_STRING);
}

<ST_IN_SCRIPTING>{WHITESPACE} {
}

<ST_IN_SCRIPTING>"#"|"//" {
	handleCommentStart();
	yybegin(ST_ONE_LINE_COMMENT);
//	yymore();
}

<ST_ONE_LINE_COMMENT>"?"|"%"|">" {
//	yymore();
}

<ST_ONE_LINE_COMMENT>[^\n\r?%>]*(.|{NEWLINE}) {
	String yytext = yytext();
	switch (yytext.charAt(yytext.length() - 1)) {
		case '?':
		case '%':
		case '>':
			yypushback(1);
			break;
		default:
			handleLineCommentEnd();
			yybegin(ST_IN_SCRIPTING);
	}
//	yymore();
}

<ST_ONE_LINE_COMMENT>"?>"|"%>" {
    if (asp_tags || yytext().charAt(0)!='%') { /* asp comment? */
   		handleLineCommentEnd();
        yypushback(yylength());
		yybegin(ST_IN_SCRIPTING);
		//return T_COMMENT;
	} 
}

<ST_IN_SCRIPTING>"/*"{WHITESPACE}*"@var"{WHITESPACE}("$"?){LABEL}{WHITESPACE}{LABEL}{WHITESPACE}?"*/" {
    handleVarComment();
    return createFullSymbol(ParserConstants4.T_VAR_COMMENT);
}

<ST_IN_SCRIPTING>"/**" {
if (!parsePHPDoc()) {
handleCommentStart();
yybegin(ST_DOCBLOCK);
}
}

<ST_DOCBLOCK>"*/" {
     handlePHPDocEnd();
     yybegin(ST_IN_SCRIPTING);
}

<ST_DOCBLOCK>{NEWLINE} {
}

<ST_DOCBLOCK>{ANY_CHAR} {
}

<ST_IN_SCRIPTING>"/**/" {
	handleCommentStart();
}

<ST_IN_SCRIPTING>"/*" {
	handleCommentStart();
    yybegin(ST_COMMENT);
}

<ST_COMMENT>[^*]+ {
}

<ST_COMMENT>"*/" {
	handleMultilineCommentEnd();
    yybegin(ST_IN_SCRIPTING);
}

<ST_COMMENT>"*" {
//	yymore();
}

<ST_IN_SCRIPTING>("?>"|"</script"{WHITESPACE}*">"){NEWLINE}? {
    yybegin(YYINITIAL);
    return createSymbol(ParserConstants4.T_SEMICOLON);  /* implicit ';' at php-end tag */
}

<ST_IN_SCRIPTING>"%>"{NEWLINE}? {
    if (asp_tags) {
        yybegin(YYINITIAL);
        return createSymbol(ParserConstants4.T_SEMICOLON);  /* implicit ';' at php-end tag */
    } else {
        return createSymbol(ParserConstants4.T_INLINE_HTML);
    }
}

<ST_IN_SCRIPTING>([\"]([^$\"\\]|("\\".))*[\"]) {
    return createFullSymbol(ParserConstants4.T_CONSTANT_ENCAPSED_STRING);
}

<ST_IN_SCRIPTING>([']([^'\\]|("\\".))*[']) {
    return createFullSymbol(ParserConstants4.T_CONSTANT_ENCAPSED_STRING);
}

<ST_IN_SCRIPTING>[\"] {
    yybegin(ST_DOUBLE_QUOTES);
    return createSymbol(ParserConstants4.T_QUATE);
}

<ST_IN_SCRIPTING>"<<<"{TABS_AND_SPACES}{LABEL}{NEWLINE} {
    heredoc = yytext().substring(3).trim();    // for '<<<'
    yybegin(ST_HEREDOC);
    return createSymbol(ParserConstants4.T_START_HEREDOC);
}

<ST_IN_SCRIPTING>[`] {
    yybegin(ST_BACKQUOTE);
    return createSymbol(ParserConstants4.T_BACKQUATE);
}

<ST_IN_SCRIPTING>['] {
    yybegin(ST_SINGLE_QUOTE);
    return createSymbol(ParserConstants4.T_SINGLE_QUATE);
}

<ST_HEREDOC>^{LABEL}(";")?{NEWLINE} {
    String text = yytext();
    int length = text.length();
    text = text.trim();
    boolean foundNP = false;
    if (text.endsWith(";")) {
        text = text.substring(0, text.length() - 1);
        foundNP = true;
    }
    if (text.equals(heredoc)) {
        if (foundNP) {
            yypushback(length - text.length());
        }
        heredoc = null;
        yybegin(ST_IN_SCRIPTING);
        return createSymbol(ParserConstants4.T_END_HEREDOC);
    } else {
        return createFullSymbol(ParserConstants4.T_STRING);
    }
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>{ESCAPED_AND_WHITESPACE} {
    return createFullSymbol(ParserConstants4.T_ENCAPSED_AND_WHITESPACE);
}

<ST_SINGLE_QUOTE>([^'\\]|\\[^'\\])+ {
    return createSymbol(ParserConstants4.T_ENCAPSED_AND_WHITESPACE);
}

<ST_DOUBLE_QUOTES>[`]+ {
    return createSymbol(ParserConstants4.T_ENCAPSED_AND_WHITESPACE);
}

<ST_BACKQUOTE>[\"]+ {
    return createSymbol(ParserConstants4.T_ENCAPSED_AND_WHITESPACE);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"$"[^a-zA-Z_\x7f-\xff{] {
    if (yylength() == 2) {
        yypushback(1);
    }
    return createSymbol(ParserConstants4.T_CHARACTER);
}

// ENCAPSED_TOKENS
<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC> {
    "["     {return createSymbol(ParserConstants4.T_OPEN_RECT);}

    "]"     {return createSymbol(ParserConstants4.T_CLOSE_RECT); }

    "$"     {return createSymbol(ParserConstants4.T_DOLLAR);}
    
    "{"     {return createSymbol(ParserConstants4.T_CURLY_OPEN); }
    
    "}"     {return createSymbol(ParserConstants4.T_CURLY_CLOSE); }
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"{$" {
    pushState(ST_IN_SCRIPTING);
    yypushback(yylength()-1);
    return createSymbol(ParserConstants4.T_CURLY_OPEN);
}

<ST_SINGLE_QUOTE>"\\'" {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_SINGLE_QUOTE>"\\\\" {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_DOUBLE_QUOTES>"\\\"" {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_BACKQUOTE>"\\`" {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"\\"[0-7]{1,3} {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"\\x"[0-9A-Fa-f]{1,2} {
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"\\"{ANY_CHAR} {
    switch (yytext().charAt(1)) {
        case 'n':
            break;
        case 't':
            break;
        case 'r':
            break;
        case '\\':
            break;
        case '$':
            break;
        case '{':
            break;
        default:
            return createSymbol(ParserConstants4.T_BAD_CHARACTER);
    }
    return createSymbol(ParserConstants4.T_CHARACTER);
}

<ST_HEREDOC>[\"'`]+ {
    return createSymbol(ParserConstants4.T_ENCAPSED_AND_WHITESPACE);
}

<ST_DOUBLE_QUOTES>[\"] {
    yybegin(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants4.T_QUATE);
}

<ST_BACKQUOTE>[`] {
    yybegin(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants4.T_BACKQUATE);
}

<ST_SINGLE_QUOTE>['] {
    yybegin(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants4.T_SINGLE_QUATE);
}

<ST_IN_SCRIPTING,YYINITIAL,ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_SINGLE_QUOTE,ST_HEREDOC>{ANY_CHAR} {
	// do nothing
}