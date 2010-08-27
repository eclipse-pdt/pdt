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

package org.eclipse.php.internal.core.documentModel.parser.php4;

import org.eclipse.php.internal.core.util.collections.IntHashtable;

%%

%class PhpLexer
%public
%extends org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer
%type String
%unicode
%caseless



%state ST_PHP_IN_SCRIPTING
%state ST_PHP_DOUBLE_QUOTES
%state ST_PHP_SINGLE_QUOTE
%state ST_PHP_BACKQUOTE
%state ST_PHP_QUOTES_AFTER_VARIABLE
%state ST_PHP_HEREDOC
%state ST_PHP_LOOKING_FOR_PROPERTY
%state ST_PHP_COMMENT
%state ST_PHP_DOC_COMMENT
%state ST_PHP_LINE_COMMENT
%state ST_PHP_HIGHLIGHTING_ERROR


%{
    public PhpLexer(int state){
        initialize(state);
    }
    public void reset(char array[], int offset, int length) {
        this.zzBuffer = array;
        this.zzCurrentPos = offset;
        this.zzMarkedPos = offset;
        this.zzPushbackPos = offset;
        this.yychar = offset;
        this.zzEndRead = offset + length;
        this.zzStartRead = offset;
        this.zzAtEOF = zzCurrentPos >= zzEndRead;
        this.firstPos = offset;
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
    	initialize(parameters[6]);
    }

    protected boolean isHeredocState(int state){
    	return state == ST_PHP_HEREDOC;
    }
    
    public int[] getParamenters(){
    	return new int[]{zzMarkedPos, zzPushbackPos, zzCurrentPos, zzStartRead, zzEndRead, yyline, zzLexicalState};
    }

    protected int getZZLexicalState() {
        return zzLexicalState;
    }

    protected int getZZMarkedPos() {
        return zzMarkedPos;
    }

    protected int getZZEndRead() {
        return zzEndRead;
    }

    public char[] getZZBuffer() {
        return zzBuffer;
    }

    protected int getZZStartRead() {
    	return this.zzStartRead;
    }

    protected int getZZPushBackPosition() {
    	return this.zzPushbackPos;
    }
    
	protected void pushBack(int i) {
		yypushback(i);
	}

	// A pool of states. To avoid creation of a new state on each createMemento.
	private static final IntHashtable lexerStates = new IntHashtable(100);
	
	protected IntHashtable getLexerStates() {
		return lexerStates;
	}

 // End user code

%}
LNUM=[0-9]+
DNUM=([0-9]*[\.][0-9]+)|([0-9]+[\.][0-9]*)
EXPONENT_DNUM=(({LNUM}|{DNUM})[eE][+-]?{LNUM}?)
HNUM="0x"[0-9a-fA-F]+
LABEL=[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*
WHITESPACE=[ \n\r\t]+
TABS_AND_SPACES=[ \t]*
TOKENS=[:,.\[\]()|\^&+-//*=%!~$<>?@]
CLOSE_EXPRESSION=[;]
ENCAPSED_TOKENS=[\[\]{}$]
ESCAPED_AND_WHITESPACE=[\n\t\r #'.:;,()|\^&+-//*=%!~<>?@]+
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")

PHP_OPERATOR=       "=>"|"++"|"--"|"==="|"!=="|"=="|"!="|"<>"|"<="|">="|"+="|"-="|"*="|"/="|".="|"%="|"<<="|">>="|"&="|"|="|"^="|"||"|"&&"|"OR"|"AND"|"XOR"|"<<"|">>"


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
    return PHP_FUNCTION;
}

<ST_PHP_IN_SCRIPTING>"old_function" { /* guy - support old versions */
    return PHP_FUNCTION;
}

<ST_PHP_IN_SCRIPTING>"cfunction" { /* guy - support old versions */
    return PHP_FUNCTION;
}

<ST_PHP_IN_SCRIPTING>"const" {
    return PHP_CONST;
}

<ST_PHP_IN_SCRIPTING>"return" {
    return PHP_RETURN;
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

<ST_PHP_IN_SCRIPTING>"print" {
    return PHP_PRINT;
}

<ST_PHP_IN_SCRIPTING>"class" {
    return PHP_CLASS;
}

<ST_PHP_IN_SCRIPTING>"extends" {
    return PHP_EXTENDS;
}

<ST_PHP_IN_SCRIPTING>"->" {
    pushState(ST_PHP_LOOKING_FOR_PROPERTY);
    return PHP_OPERATOR;
}

<ST_PHP_QUOTES_AFTER_VARIABLE> {
    "->" {
    popState();
    pushState(ST_PHP_LOOKING_FOR_PROPERTY);
    return PHP_OPERATOR;
    }
    {ANY_CHAR} {
        yypushback(1);
        popState();
    }
}

<ST_PHP_LOOKING_FOR_PROPERTY>{LABEL} {
    popState();
    return PHP_STRING;
}

<ST_PHP_LOOKING_FOR_PROPERTY>{ANY_CHAR} {
    yypushback(1);
    popState();
}

<ST_PHP_IN_SCRIPTING>"::" {
    return PHP_PAAMAYIM_NEKUDOTAYIM;
}

<ST_PHP_IN_SCRIPTING>"new" {
    return PHP_NEW;
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

<ST_PHP_IN_SCRIPTING>"static" {
    return PHP_STATIC;
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

<ST_PHP_IN_SCRIPTING>{CLOSE_EXPRESSION} {
    return PHP_SEMICOLON;
}

<ST_PHP_IN_SCRIPTING>"{" {
    return PHP_CURLY_OPEN;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"${" {
    pushState(ST_PHP_IN_SCRIPTING);
    return PHP_TOKEN;
}

<ST_PHP_IN_SCRIPTING>"}" {
    if (!phpStack.isEmpty()) {
        popState();
    }
	return  PHP_CURLY_CLOSE;
}

<ST_PHP_IN_SCRIPTING>{LNUM} {
    return PHP_NUMBER;
}

<ST_PHP_IN_SCRIPTING>{HNUM} {
    return PHP_NUMBER;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>{LNUM}|{HNUM} {
    return PHP_CONSTANT_ENCAPSED_STRING;
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

<ST_PHP_IN_SCRIPTING>"__LINE__" {
    return PHP__LINE__;
}

<ST_PHP_IN_SCRIPTING>"__FILE__" {
    return PHP__FILE__;
}

<ST_PHP_IN_SCRIPTING>"$this" {
    return PHP_THIS;
}

<ST_PHP_IN_SCRIPTING>"$"{LABEL} {
    return PHP_VARIABLE;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"$"{LABEL} {
    pushState(ST_PHP_QUOTES_AFTER_VARIABLE);
    return PHP_VARIABLE;
}

<ST_PHP_IN_SCRIPTING>"null" {
    return  PHP_KEYWORD;
}

<ST_PHP_IN_SCRIPTING>{LABEL} {
    return  PHP_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>{LABEL} {
	return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>{WHITESPACE} {
    return  WHITESPACE;
}

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
			break;
		default:
			popState();
	}
	return PHP_LINE_COMMENT;
}

<ST_PHP_LINE_COMMENT>{NEWLINE} {
    popState();
    return PHP_LINE_COMMENT;
}

<ST_PHP_IN_SCRIPTING>"/**"(#@\+)?{WHITESPACE} {
    pushState(ST_PHP_DOC_COMMENT);
    return PHPDOC_COMMENT_START;
}

<ST_PHP_DOC_COMMENT>{

    "@access"        {return PHPDOC_ACCESS;}
    "@abstract"      {return PHPDOC_ABSTRACT;}
    "@author"        {return PHPDOC_AUTHOR;}
    "@category"      {return PHPDOC_CATEGORY;}
    "@copyright"     {return PHPDOC_COPYRIGHT;}
    "@deprecated"    {return PHPDOC_DEPRECATED;}
    "@desc"          {return PHPDOC_DESC;}
    "@example"       {return PHPDOC_EXAMPLE;}
    "@exception"     {return PHPDOC_EXCEPTION;}
    "@final"         {return PHPDOC_FINAL;}
    "@filesource"    {return PHPDOC_FILESOURCE;}
    "@global"        {return PHPDOC_GLOBAL;}
    "@ignore"        {return PHPDOC_IGNORE;}
    "@internal"      {return PHPDOC_INTERNAL;}
    "@license"       {return PHPDOC_LICENSE;}
    "@link"          {return PHPDOC_LINK;}
    "@magic"         {return PHPDOC_MAGIC;}
    "@method"        {return PHPDOC_METHOD;}    
    "@name"          {return PHPDOC_NAME;}
    "@package"       {return PHPDOC_PACKAGE;}
    "@param"         {return PHPDOC_PARAM;}
    "@property"      {return PHPDOC_PROPERTY;}
    "@return"        {return PHPDOC_RETURN;}
    "@see"           {return PHPDOC_SEE;}
    "@since"         {return PHPDOC_SINCE;}
    "@static"        {return PHPDOC_STATIC;}
    "@staticvar"     {return PHPDOC_STATICVAR;}
    "@subpackage"    {return PHPDOC_SUBPACKAGE;}
    "@throws"        {return PHPDOC_THROWS;}
    "@todo"          {return PHPDOC_TODO;}
    "@tutorial"      {return PHPDOC_TUTORIAL;}
    "@uses"		     {return PHPDOC_USES;}
    "@var"           {return PHPDOC_VAR;}
    "@version"       {return PHPDOC_VERSION;}

   {ANY_CHAR}     {return PHPDOC_COMMENT;}
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

<ST_PHP_IN_SCRIPTING,ST_PHP_LINE_COMMENT>"?>"{WHITESPACE}? {
	return PHP_CLOSETAG;
}
<ST_PHP_IN_SCRIPTING>"%>"{WHITESPACE}? {
	if (asp_tags) {
	    return PHP_CLOSETAG;
	}
	return UNKNOWN_TOKEN;
}

<ST_PHP_LINE_COMMENT>"%>"{WHITESPACE}? {
	if (asp_tags) {
	    return PHP_CLOSETAG;
	}
	String text = yytext();
	if(text.indexOf('\r') != -1 || text.indexOf('\n') != -1 ){
		popState();
	}
	return PHP_LINE_COMMENT;
}

<ST_PHP_IN_SCRIPTING>([\"]([^$\"\\]|("\\".))*[\"]) {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>([']([^'\\]|("\\".))*[']) {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>[\"] {
    pushState(ST_PHP_DOUBLE_QUOTES);
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>"<<<"{TABS_AND_SPACES}{LABEL}{NEWLINE} {
    int startString=3;
    heredoc_len = yylength()-3-1-(yytext().charAt(yylength()-2)=='\r'?1:0);
    while ((yytext().charAt(startString) == ' ') || (yytext().charAt(startString) == '\t')) {
        startString++;
        heredoc_len--;
    }
    heredoc = yytext().substring(startString,heredoc_len+startString);
    pushState(ST_PHP_HEREDOC);
    return PHP_HEREDOC_TAG;
}

<ST_PHP_IN_SCRIPTING>[`] {
    pushState(ST_PHP_BACKQUOTE);
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_IN_SCRIPTING>['] {
    pushState(ST_PHP_SINGLE_QUOTE);
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_HEREDOC>^{LABEL}(";")?{NEWLINE} {
    int label_len;
    int length=yylength();
    if (yytext().charAt(length-2)=='\r') {
        label_len = length-2;
    } else {
        label_len = length-1;
    }

    if (yytext().charAt(label_len-1)==';') {
	    label_len--;
    }

    if (label_len==heredoc_len && yytext().substring(0,label_len).equals(heredoc)) {
        heredoc=null;
        heredoc_len=0;
        popState();
        return  PHP_HEREDOC_TAG;
    } else {
        return  PHP_CONSTANT_ENCAPSED_STRING;
    }
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>{ESCAPED_AND_WHITESPACE} {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>([^'\\]|\\[^'\\])+ {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES>[`]+ {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>[\"]+ {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"$"[^a-zA-Z_\x7f-\xff{] {
    if (yylength() == 2) {
        yypushback(1);
    }
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>{ENCAPSED_TOKENS} {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC,ST_PHP_QUOTES_AFTER_VARIABLE>"{$" {
    yypushback(1);
    pushState(ST_PHP_IN_SCRIPTING);
    return PHP_CURLY_OPEN;
}

<ST_PHP_SINGLE_QUOTE>"\\'" {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>"\\\\" {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES>"\\\"" {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>"\\`" {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"\\"[0-7]{1,3} {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"\\x"[0-9A-Fa-f]{1,2} {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>"\\"{ANY_CHAR} {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_HEREDOC>[\"'`]+ {
    return PHP_ENCAPSED_AND_WHITESPACE;
}

<ST_PHP_DOUBLE_QUOTES>[\"] {
    popState();
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>[`] {
    popState();
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>['] {
    popState();
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_DOUBLE_QUOTES>. {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_BACKQUOTE>. {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

<ST_PHP_SINGLE_QUOTE>. {
    return PHP_CONSTANT_ENCAPSED_STRING;
}

/* ============================================
   Stay in this state until we find a whitespace.
   After we find a whitespace we go the the prev state and try again from the next token.
   ============================================ */
<ST_PHP_HIGHLIGHTING_ERROR> {
	{WHITESPACE}	{popState();return WHITESPACE;}
    .   	        {return UNKNOWN_TOKEN;}
}

/* ============================================
   This rule must be the last in the section!!
   it should contain all the states.
   ============================================ */
<ST_PHP_IN_SCRIPTING,ST_PHP_DOUBLE_QUOTES,ST_PHP_SINGLE_QUOTE,ST_PHP_BACKQUOTE,ST_PHP_HEREDOC>. {
    yypushback(1);
    pushState(ST_PHP_HIGHLIGHTING_ERROR);
}

