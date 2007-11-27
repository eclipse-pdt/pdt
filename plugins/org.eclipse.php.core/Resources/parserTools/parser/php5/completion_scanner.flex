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

package org.eclipse.php.internal.core.phpModel.parser.php5;

import org.eclipse.php.internal.core.phpModel.javacup.runtime.Symbol;
import org.eclipse.php.internal.core.phpModel.javacup.sym;
import org.eclipse.php.internal.core.util.collections.IntHashtable;
import org.eclipse.php.internal.core.phpModel.parser.CompletionLexer;
import org.eclipse.php.internal.core.phpModel.parser.ParserClient;
import org.eclipse.php.internal.core.phpModel.parser.StateStack;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

%%

%class CompletionLexer5
%extends CompletionLexer
%public
%unicode
%line

/* %cup */
%implements org.eclipse.php.internal.core.phpModel.javacup.runtime.Scanner
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
%state ST_BACKQUOTE
%state ST_HEREDOC
%state ST_START_HEREDOC
%state ST_END_HEREDOC
%state ST_LOOKING_FOR_PROPERTY
%state ST_LOOKING_FOR_VARNAME
%state ST_VAR_OFFSET
%state ST_COMMENT
%state ST_DOCBLOCK
%state ST_ONE_LINE_COMMENT
%{
    /** TODO, Do not forget to change yy_refill to the following code:
         private boolean yy_refill() throws java.io.IOException {

             // first: make room (if you can)
             if (yy_startRead > 0) {
                 char temp[] = yy_buffer;
                 //only if the new buffer will be changed then
                 //we have to keep the old copy
                 if (yy_endRead - yy_startRead > 0 || yy_startRead == yy_old_buffer.length) {
                     temp = yy_old_buffer;
                     yy_old_buffer = yy_buffer;
                     yy_old_pushbackPos = yy_pushbackPos;
                     duplicated_string_length = yy_endRead - yy_startRead;
                 }
                 System.arraycopy(yy_buffer, yy_startRead,
                         temp, 0,
                         yy_endRead - yy_startRead);
                 yy_buffer = temp;

                 // translate stored positions
                 yy_endRead -= yy_startRead;
                 yy_currentPos -= yy_startRead;
                 yy_markedPos -= yy_startRead;
                 yy_pushbackPos -= yy_startRead;
                 yy_startRead = 0;
             }

             // is the buffer big enough?
             if (yy_currentPos >= yy_buffer.length) {
                 // if not: blow it up
                 char newBuffer[] = new char[yy_currentPos * 2];
                 System.arraycopy(yy_buffer, 0, newBuffer, 0, yy_buffer.length);
                 yy_buffer = newBuffer;
                 newBuffer = new char[yy_currentPos * 2];
                 System.arraycopy(yy_old_buffer, 0, newBuffer, 0, yy_old_buffer.length);
                 System.arraycopy(yy_buffer, duplicated_string_length, newBuffer, yy_old_buffer.length, newBuffer.length - yy_old_buffer.length);
                 duplicated_string_length += newBuffer.length - yy_old_buffer.length;
                 yy_old_buffer = newBuffer;
             }

             // finally: fill the buffer with new input
             int numRead = yy_reader.read(yy_buffer, yy_endRead,
                     yy_buffer.length - yy_endRead);

             if (numRead < 0) {
                 return true;
             } else {
                 yy_endRead += numRead;
                 return false;
             }
         }
    */
    
    private String heredoc = null;
    private boolean asp_tags = false;
    private boolean short_tags_allowed = true;
    private StateStack stack = new StateStack();
    private ParserClient parserClient;
    private Pattern[] tasksPatterns;
    private char yy_old_buffer[] = new char[YY_BUFFERSIZE];
    private int yy_old_pushbackPos;
    private int duplicated_string_length;

	public void setUseAspTagsAsPhp(boolean useAspTagsAsPhp) {
		asp_tags = useAspTagsAsPhp;
	}
	
    private void pushState(int state) {
        stack.pushStack(yy_lexical_state);
        yybegin(state);
    }

    private void popState() {
        yybegin(stack.popStack());
    }

    public int getCurrentLine() {
        return yyline;
    }


    private int getTokenStartPosition() {
        return yy_startRead - yy_pushbackPos;
    }

    private int getTokenLength() {
        return yy_markedPos - yy_startRead;
    }

    public void setParserClient(ParserClient parserClient) {
        this.parserClient = parserClient;
    }

    public void setTasksPatterns(Pattern[] tasksPatterns){
    	this.tasksPatterns = tasksPatterns;
    }
    
    private void handlePHPStart() {
        if (parserClient != null) {
            int startPosition = getTokenStartPosition();
            parserClient.handlePHPStart(startPosition, startPosition + getTokenLength());
        }
    }

    private void handlePHPEnd() {
        if (parserClient != null) {
            int startPosition = getTokenStartPosition();
            parserClient.handlePHPEnd(startPosition, startPosition + getTokenLength());
        }
    }

    public int getLength() {
        return yy_endRead - yy_pushbackPos;
    }

    private IntHashtable phpDocs = new IntHashtable();
    private IntHashtable phpDocsStart = new IntHashtable();
    private String lastPhpDocText;
    private Integer lastPhpDocStart = new Integer(0);
    private Object[] phpDocValues = new Object [2];
    private String firstPhpDocText = null;
    private Integer firstPhpDocStart = null;
    private boolean noSymboles = true;

	private int commentStartPosition;
	private int commentStartLine;
	private StringBuffer comment = new StringBuffer();
	
	private void handlePHPDocStart() {
		handleCommentStart();
		lastPhpDocStart = new Integer(getTokenStartPosition());    
	}
		    
    private void handlePHPDocEnd() {
    	handleCommentEnd();
  	    lastPhpDocText = comment.toString();
        if (lastPhpDocText != null) {
            if (noSymboles &&((firstPhpDocStart == null)|| (firstPhpDocStart.compareTo(lastPhpDocStart) > 0))){
                    firstPhpDocText = lastPhpDocText;
                    firstPhpDocStart = lastPhpDocStart;
            }
        }
    }

    public Object[] getPHPDoc(int location) {
        phpDocValues[0] = phpDocs.get(location);
        phpDocValues[1] = phpDocsStart.get(location);
        return phpDocValues;
    }

   public Object[] getFirstPHPDoc() {
        phpDocValues[0] = firstPhpDocText;
        phpDocValues[1] = firstPhpDocStart;
        return phpDocValues;
    }

    private void appendTextToComment(){
    	comment.append(yy_buffer, yy_startRead, yy_markedPos-yy_startRead);
    }
    
    private void clearCommentBuffer(){
    	comment.delete(0,comment.length());
    }
    
	private void handleCommentStart() {
		commentStartPosition = yy_startRead;
		commentStartLine = yyline;
		clearCommentBuffer();
		appendTextToComment();
	}
	
	private void handleCommentEnd() {
		appendTextToComment();
		checkForTasks();
	}
	
    private void checkForTasks(){
		ArrayList matchers = createMatcherList();		
		int searchStartPosition = 0;
		int lineCnt = 0;
		int commentEndIndex = comment.length();
		//if its a multiline comment or a phpDoc no nead to search the */ in the end 
		if(comment.charAt(0)=='/' && comment.charAt(1)=='*'){
			commentEndIndex -=2;
		}
		Matcher matcher = getMinimalMatcher(matchers, searchStartPosition);
		while (matcher != null) {
			String taskName = matcher.group();
			int taskStartPositionInComment = matcher.start();
			int descriptionStartPositionInComment = matcher.end();			

			//counting the lines till the begining of the task
			lineCnt+= countNewLines(searchStartPosition, matcher.start());
			
			//calculating the endPosition of the task
			searchStartPosition = matcher.end();
			matcher = getMinimalMatcher(matchers, searchStartPosition);
			
			int endSearch = (matcher == null)? commentEndIndex : matcher.start();
			int taskEndPositionInComment = descriptionStartPositionInComment;
			while(taskEndPositionInComment < endSearch){
				char ch = comment.charAt(taskEndPositionInComment);
				if(ch == '\n' || ch == '\r'){
					break;
				}
				taskEndPositionInComment++;
			}

			String description = comment.substring(descriptionStartPositionInComment,taskEndPositionInComment);
			description = description.trim();
			parserClient.handleTask(taskName, description, commentStartPosition  - yy_pushbackPos + taskStartPositionInComment, commentStartPosition  - yy_pushbackPos + taskEndPositionInComment  - 1,commentStartLine + lineCnt);
		}
    }
    
    private int countNewLines(int startIndex, int endIndex) {
    	int lineCnt = 0;
		for(int i = startIndex; i < endIndex ; i++){
			char ch = comment.charAt(i);
			if(ch == '\n' || ch == '\r'){
				char oppositeCh = (ch == '\n') ? '\r' : '\n';
				lineCnt++;
				if(i + 1 < endIndex){
					ch = comment.charAt(i + 1);
					if(ch == oppositeCh){
						i++;
					}
				}
			}
		}
		return lineCnt;
    }

	private ArrayList createMatcherList() {
		ArrayList list = new ArrayList(tasksPatterns.length);
		for (int i = 0; i < tasksPatterns.length; i++) {
			list.add(i, tasksPatterns[i].matcher(comment));
		}
		return list;
	}

	private Matcher getMinimalMatcher(ArrayList matchers, int startPosition) {
		Matcher minimal = null;
		int size = matchers.size();
		for (int i = 0; i < size;) {
			Matcher tmp = (Matcher) matchers.get(i);
			if (tmp.find(startPosition)) {
				if (minimal == null || tmp.start() < minimal.start()) {
					minimal = tmp;
				}
				i++;
			} else {
				matchers.remove(i);
				size--;
			}
		}
		return minimal;
	}

    private Symbol createFullSymbol(int symbolNumber) {
        Symbol symbol = createSymbol(symbolNumber);
        symbol.value = yytext();
        return symbol;
    }

    private Symbol createSymbol(int symbolNumber) {
        int leftPosition = getTokenStartPosition();
        if (lastPhpDocText != null) {
            if(lastPhpDocText == firstPhpDocText) {
                if(symbolNumber == ParserConstants5.T_FUNCTION ||
                        symbolNumber == ParserConstants5.T_CLASS ||
                        symbolNumber == ParserConstants5.T_DEFINE){
                    phpDocs.put(leftPosition, lastPhpDocText);
                    phpDocsStart.put(leftPosition, new Integer(lastPhpDocStart.intValue()));
                    firstPhpDocText = null;
                }
            } else {
                phpDocs.put(leftPosition, lastPhpDocText);
                phpDocsStart.put(leftPosition, new Integer(lastPhpDocStart.intValue()));
            }
            lastPhpDocText = null;
        }
        if(noSymboles && symbolNumber != ParserConstants5.T_INLINE_HTML){	// T_INLINE_HTML doesn't infect the phpDoc 
        	noSymboles = false;        
        }
        return new Symbol(symbolNumber, leftPosition, leftPosition + getTokenLength());
    }

    public String createString(int startOffset, int endOffset) {
        int startPosition = startOffset + yy_pushbackPos;
        int length =  endOffset - startOffset;
        if(startPosition < 0){
            startPosition = startOffset + yy_old_pushbackPos;
            if(startPosition + length < yy_old_buffer.length){
            	if(startPosition < 0 || startPosition + length > yy_old_buffer.length){
                	return "";
                }
                return new String(yy_old_buffer, startPosition, length);
            }
            // meaning the string was splited between the two buffers
            int remainder = startPosition + length  + duplicated_string_length - yy_old_buffer.length;
            length -= remainder;
            if(startPosition < 0 || startPosition + length > yy_old_buffer.length || remainder > yy_buffer.length){
            	return "";
            }
            return (new String(yy_old_buffer, startPosition, length) + new String(yy_buffer, 0, remainder));
        }
    	if(startPosition < 0 || startPosition + length > yy_buffer.length){
        	return "";
        }
        return new String(yy_buffer, startPosition, length);
    }

%}

LNUM=[0-9]+
DNUM=([0-9]*[\.][0-9]+)|([0-9]+[\.][0-9]*)
EXPONENT_DNUM=(({LNUM}|{DNUM})[eE][+-]?{LNUM})
HNUM="0x"[0-9a-fA-F]+
LABEL=[a-zA-Z_\x7f-\xff][a-zA-Z0-9_\x7f-\xff]*
WHITESPACE=[ \n\r\t]+
TABS_AND_SPACES=[ \t]*
ANY_CHAR=(.|[\n])
NEWLINE=("\r"|"\n"|"\r\n")
DOUBLE_QUOTES_LITERAL_DOLLAR=("$"+([^a-zA-Z_\x7f-\xff$\"\\{]|("\\"{ANY_CHAR})))
BACKQUOTE_LITERAL_DOLLAR=("$"+([^a-zA-Z_\x7f-\xff$`\\{]|("\\"{ANY_CHAR})))
HEREDOC_LITERAL_DOLLAR=("$"+([^a-zA-Z_\x7f-\xff$\n\r\\{]|("\\"[^\n\r])))
HEREDOC_NEWLINE=((({LABEL}";"?((("{"+|"$"+)"\\"?)|"\\"))|(("{"*|"$"*)"\\"?)){NEWLINE})
HEREDOC_CURLY_OR_ESCAPE_OR_DOLLAR=(("{"+[^$\n\r\\{])|("{"*"\\"[^\n\r])|{HEREDOC_LITERAL_DOLLAR})
HEREDOC_NON_LABEL=([^a-zA-Z_\x7f-\xff$\n\r\\{]|{HEREDOC_CURLY_OR_ESCAPE_OR_DOLLAR})
HEREDOC_LABEL_NO_NEWLINE=({LABEL}([^a-zA-Z0-9_\x7f-\xff;$\n\r\\{]|(";"[^$\n\r\\{])|(";"?{HEREDOC_CURLY_OR_ESCAPE_OR_DOLLAR})))
DOUBLE_QUOTES_CHARS=("{"*([^$\"\\{]|("\\"{ANY_CHAR}))|{DOUBLE_QUOTES_LITERAL_DOLLAR})
BACKQUOTE_CHARS=("{"*([^$`\\{]|("\\"{ANY_CHAR}))|{BACKQUOTE_LITERAL_DOLLAR})
HEREDOC_CHARS=("{"*([^$\n\r\\{]|("\\"[^\n\r]))|{HEREDOC_LITERAL_DOLLAR}|({HEREDOC_NEWLINE}+({HEREDOC_NON_LABEL}|{HEREDOC_LABEL_NO_NEWLINE})))


%%

<ST_IN_SCRIPTING>"exit" {
	return createSymbol(ParserConstants5.T_EXIT);
}

<ST_IN_SCRIPTING>"die" {
	return createSymbol(ParserConstants5.T_EXIT);
}

<ST_IN_SCRIPTING>"function"|"cfunction" {
	return createSymbol(ParserConstants5.T_FUNCTION);
}

<ST_IN_SCRIPTING>"const" {
	return createSymbol(ParserConstants5.T_CONST);
}

<ST_IN_SCRIPTING>"return" {
	return createSymbol(ParserConstants5.T_RETURN);
}

<ST_IN_SCRIPTING>"try" {
	return createSymbol(ParserConstants5.T_TRY);
}

<ST_IN_SCRIPTING>"catch" {
	return createSymbol(ParserConstants5.T_CATCH);
}

<ST_IN_SCRIPTING>"throw" {
	return createSymbol(ParserConstants5.T_THROW);
}

<ST_IN_SCRIPTING>"if" {
	return createSymbol(ParserConstants5.T_IF);
}

<ST_IN_SCRIPTING>"elseif" {
	return createSymbol(ParserConstants5.T_ELSEIF);
}

<ST_IN_SCRIPTING>"endif" {
	return createSymbol(ParserConstants5.T_ENDIF);
}

<ST_IN_SCRIPTING>"else" {
	return createSymbol(ParserConstants5.T_ELSE);
}

<ST_IN_SCRIPTING>"while" {
	return createSymbol(ParserConstants5.T_WHILE);
}

<ST_IN_SCRIPTING>"endwhile" {
	return createSymbol(ParserConstants5.T_ENDWHILE);
}

<ST_IN_SCRIPTING>"do" {
	return createSymbol(ParserConstants5.T_DO);
}

<ST_IN_SCRIPTING>"for" {
	return createSymbol(ParserConstants5.T_FOR);
}

<ST_IN_SCRIPTING>"endfor" {
	return createSymbol(ParserConstants5.T_ENDFOR);
}

<ST_IN_SCRIPTING>"foreach" {
	return createSymbol(ParserConstants5.T_FOREACH);
}

<ST_IN_SCRIPTING>"endforeach" {
	return createSymbol(ParserConstants5.T_ENDFOREACH);
}

<ST_IN_SCRIPTING>"declare" {
	return createSymbol(ParserConstants5.T_DECLARE);
}

<ST_IN_SCRIPTING>"enddeclare" {
	return createSymbol(ParserConstants5.T_ENDDECLARE);
}

<ST_IN_SCRIPTING>"instanceof" {
	return createSymbol(ParserConstants5.T_INSTANCEOF);
}

<ST_IN_SCRIPTING>"as" {
	return createSymbol(ParserConstants5.T_AS);
}

<ST_IN_SCRIPTING>"switch" {
	return createSymbol(ParserConstants5.T_SWITCH);
}

<ST_IN_SCRIPTING>"endswitch" {
	return createSymbol(ParserConstants5.T_ENDSWITCH);
}

<ST_IN_SCRIPTING>"case" {
	return createSymbol(ParserConstants5.T_CASE);
}

<ST_IN_SCRIPTING>"default" {
	return createSymbol(ParserConstants5.T_DEFAULT);
}

<ST_IN_SCRIPTING>"break" {
	return createSymbol(ParserConstants5.T_BREAK);
}

<ST_IN_SCRIPTING>"continue" {
	return createSymbol(ParserConstants5.T_CONTINUE);
}

<ST_IN_SCRIPTING>"echo" {
	return createSymbol(ParserConstants5.T_ECHO);
}

<ST_IN_SCRIPTING>"print" {
	return createSymbol(ParserConstants5.T_PRINT);
}

<ST_IN_SCRIPTING>"class" {
	return createSymbol(ParserConstants5.T_CLASS);
}

<ST_IN_SCRIPTING>"interface" {
	return createSymbol(ParserConstants5.T_INTERFACE);
}

<ST_IN_SCRIPTING>"extends" {
	return createSymbol(ParserConstants5.T_EXTENDS);
}

<ST_IN_SCRIPTING>"implements" {
	return createSymbol(ParserConstants5.T_IMPLEMENTS);
}

<ST_IN_SCRIPTING>"->" {
    pushState(ST_LOOKING_FOR_PROPERTY);
    return createSymbol(ParserConstants5.T_OBJECT_OPERATOR);
}

<ST_LOOKING_FOR_PROPERTY>"->" {
	return createSymbol(ParserConstants5.T_OBJECT_OPERATOR);
}

<ST_LOOKING_FOR_PROPERTY>{LABEL} {
    popState();
    return createFullSymbol(ParserConstants5.T_STRING);
}

<ST_LOOKING_FOR_PROPERTY>{ANY_CHAR} {
    yypushback(yylength());
    popState();
}

<ST_IN_SCRIPTING>"::" {
	return createSymbol(ParserConstants5.T_PAAMAYIM_NEKUDOTAYIM);
}

<ST_IN_SCRIPTING>"new" {
	return createSymbol(ParserConstants5.T_NEW);
}

<ST_IN_SCRIPTING>"clone" {
	return createSymbol(ParserConstants5.T_CLONE);
}

<ST_IN_SCRIPTING>"var" {
	return createSymbol(ParserConstants5.T_VAR);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("int"|"integer"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_INT_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("real"|"double"|"float"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_DOUBLE_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"string"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_STRING_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"binary"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_STRING_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"array"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_ARRAY_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}"object"{TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_OBJECT_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("bool"|"boolean"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_BOOL_CAST);
}

<ST_IN_SCRIPTING>"("{TABS_AND_SPACES}("unset"){TABS_AND_SPACES}")" {
	return createSymbol(ParserConstants5.T_UNSET_CAST);
}

<ST_IN_SCRIPTING>"eval" {
	return createSymbol(ParserConstants5.T_EVAL);
}

<ST_IN_SCRIPTING>"include" {
	return createSymbol(ParserConstants5.T_INCLUDE);
}

<ST_IN_SCRIPTING>"include_once" {
	return createSymbol(ParserConstants5.T_INCLUDE_ONCE);
}

<ST_IN_SCRIPTING>"require" {
	return createSymbol(ParserConstants5.T_REQUIRE);
}

<ST_IN_SCRIPTING>"require_once" {
	return createSymbol(ParserConstants5.T_REQUIRE_ONCE);
}

<ST_IN_SCRIPTING>"use" {
	return createSymbol(ParserConstants5.T_USE);
}

<ST_IN_SCRIPTING>"global" {
	return createSymbol(ParserConstants5.T_GLOBAL);
}

<ST_IN_SCRIPTING>"isset" {
	return createSymbol(ParserConstants5.T_ISSET);
}

<ST_IN_SCRIPTING>"empty" {
	return createSymbol(ParserConstants5.T_EMPTY);
}

<ST_IN_SCRIPTING>"__halt_compiler" {
	return createSymbol(ParserConstants5.T_HALT_COMPILER);
}
<ST_IN_SCRIPTING>"static" {
	return createSymbol(ParserConstants5.T_STATIC);
}

<ST_IN_SCRIPTING>"abstract" {
	return createSymbol(ParserConstants5.T_ABSTRACT);
}

<ST_IN_SCRIPTING>"final" {
	return createSymbol(ParserConstants5.T_FINAL);
}

<ST_IN_SCRIPTING>"private" {
	return createSymbol(ParserConstants5.T_PRIVATE);
}

<ST_IN_SCRIPTING>"protected" {
	return createSymbol(ParserConstants5.T_PROTECTED);
}

<ST_IN_SCRIPTING>"public" {
	return createSymbol(ParserConstants5.T_PUBLIC);
}

<ST_IN_SCRIPTING>"unset" {
	return createSymbol(ParserConstants5.T_UNSET);
}

<ST_IN_SCRIPTING>"=>" {
	return createSymbol(ParserConstants5.T_DOUBLE_ARROW);
}

<ST_IN_SCRIPTING>"list" {
	return createSymbol(ParserConstants5.T_LIST);
}

<ST_IN_SCRIPTING>"array" {
	return createSymbol(ParserConstants5.T_ARRAY);
}

<ST_IN_SCRIPTING>"++" {
	return createSymbol(ParserConstants5.T_INC);
}

<ST_IN_SCRIPTING>"--" {
	return createSymbol(ParserConstants5.T_DEC);
}

<ST_IN_SCRIPTING>"===" {
	return createSymbol(ParserConstants5.T_IS_IDENTICAL);
}

<ST_IN_SCRIPTING>"!==" {
	return createSymbol(ParserConstants5.T_IS_NOT_IDENTICAL);
}

<ST_IN_SCRIPTING>"==" {
	return createSymbol(ParserConstants5.T_IS_EQUAL);
}

<ST_IN_SCRIPTING>"!="|"<>" {
	return createSymbol(ParserConstants5.T_IS_NOT_EQUAL);
}

<ST_IN_SCRIPTING>"<=" {
	return createSymbol(ParserConstants5.T_IS_SMALLER_OR_EQUAL);
}

<ST_IN_SCRIPTING>">=" {
	return createSymbol(ParserConstants5.T_IS_GREATER_OR_EQUAL);
}

<ST_IN_SCRIPTING>"+=" {
	return createSymbol(ParserConstants5.T_PLUS_EQUAL);
}

<ST_IN_SCRIPTING>"-=" {
	return createSymbol(ParserConstants5.T_MINUS_EQUAL);
}

<ST_IN_SCRIPTING>"*=" {
	return createSymbol(ParserConstants5.T_MUL_EQUAL);
}

<ST_IN_SCRIPTING>"/=" {
	return createSymbol(ParserConstants5.T_DIV_EQUAL);
}

<ST_IN_SCRIPTING>".=" {
	return createSymbol(ParserConstants5.T_CONCAT_EQUAL);
}

<ST_IN_SCRIPTING>"%=" {
	return createSymbol(ParserConstants5.T_MOD_EQUAL);
}

<ST_IN_SCRIPTING>"<<=" {
	return createSymbol(ParserConstants5.T_SL_EQUAL);
}

<ST_IN_SCRIPTING>">>=" {
	return createSymbol(ParserConstants5.T_SR_EQUAL);
}

<ST_IN_SCRIPTING>"&=" {
	return createSymbol(ParserConstants5.T_AND_EQUAL);
}

<ST_IN_SCRIPTING>"|=" {
	return createSymbol(ParserConstants5.T_OR_EQUAL);
}

<ST_IN_SCRIPTING>"^=" {
	return createSymbol(ParserConstants5.T_XOR_EQUAL);
}

<ST_IN_SCRIPTING>"||" {
	return createSymbol(ParserConstants5.T_BOOLEAN_OR);
}

<ST_IN_SCRIPTING>"&&" {
	return createSymbol(ParserConstants5.T_BOOLEAN_AND);
}

<ST_IN_SCRIPTING>"OR" {
	return createSymbol(ParserConstants5.T_LOGICAL_OR);
}

<ST_IN_SCRIPTING>"AND" {
	return createSymbol(ParserConstants5.T_LOGICAL_AND);
}

<ST_IN_SCRIPTING>"XOR" {
	return createSymbol(ParserConstants5.T_LOGICAL_XOR);
}

<ST_IN_SCRIPTING>"<<" {
	return createSymbol(ParserConstants5.T_SL);
}

<ST_IN_SCRIPTING>">>" {
	return createSymbol(ParserConstants5.T_SR);
}

// TOKENS
<ST_IN_SCRIPTING> {
    ";"                     {return createSymbol(ParserConstants5.T_SEMICOLON);}
    ":"                     {return createSymbol(ParserConstants5.T_NEKUDOTAIM);}
    ","                     {return createSymbol(ParserConstants5.T_COMMA);}
    "."                     {return createSymbol(ParserConstants5.T_NEKUDA);}
    "["                     {return createSymbol(ParserConstants5.T_OPEN_RECT);}
    "]"                     {return createSymbol(ParserConstants5.T_CLOSE_RECT);}
    "("                     {return createSymbol(ParserConstants5.T_OPEN_PARENTHESE);}
    ")"                     {return createSymbol(ParserConstants5.T_CLOSE_PARENTHESE);}
    "|"                     {return createSymbol(ParserConstants5.T_OR);}
    "^"                     {return createSymbol(ParserConstants5.T_KOVA);}
    "&"                     {return createSymbol(ParserConstants5.T_REFERENCE);}
    "+"                     {return createSymbol(ParserConstants5.T_PLUS);}
    "-"                     {return createSymbol(ParserConstants5.T_MINUS);}
    "/"                     {return createSymbol(ParserConstants5.T_DIV);}
    "*"                     {return createSymbol(ParserConstants5.T_TIMES);}
    "="                     {return createSymbol(ParserConstants5.T_EQUAL);}
    "%"                     {return createSymbol(ParserConstants5.T_PRECENT);}
    "!"                     {return createSymbol(ParserConstants5.T_NOT);}
    "~"                     {return createSymbol(ParserConstants5.T_TILDA);}
    "$"                     {return createSymbol(ParserConstants5.T_DOLLAR);}
    "<"                     {return createSymbol(ParserConstants5.T_RGREATER);}
    ">"                     {return createSymbol(ParserConstants5.T_LGREATER);}
    "?"                     {return createSymbol(ParserConstants5.T_QUESTION_MARK);}
    "@"                     {return createSymbol(ParserConstants5.T_AT);}
}

<ST_IN_SCRIPTING>"{" {
    pushState(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants5.T_CURLY_OPEN);

}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"${" {
    pushState(ST_LOOKING_FOR_VARNAME);
    return createSymbol(ParserConstants5.T_DOLLAR_OPEN_CURLY_BRACES);
}

<ST_IN_SCRIPTING>"}" {
	/* This is a temporary fix which is dependant on flex and it's implementation */
    if (!stack.isEmpty()) {
        popState();
    }
    return createSymbol(ParserConstants5.T_CURLY_CLOSE);
}

<ST_LOOKING_FOR_VARNAME>{LABEL} {
    popState();
    pushState(ST_IN_SCRIPTING);
    return createFullSymbol(ParserConstants5.T_STRING_VARNAME);
}

<ST_LOOKING_FOR_VARNAME>{ANY_CHAR} {
    yypushback(yylength());
    popState();
    pushState(ST_IN_SCRIPTING);
}

<ST_IN_SCRIPTING>{LNUM} {
    return createFullSymbol(ParserConstants5.T_DNUMBER);
}

<ST_IN_SCRIPTING>{HNUM} {
    return createFullSymbol(ParserConstants5.T_DNUMBER);
}

<ST_VAR_OFFSET>0|([1-9][0-9]*) {
	return createFullSymbol(ParserConstants5.T_NUM_STRING);
}

<ST_VAR_OFFSET>{LNUM}|{HNUM} { /* treat numbers (almost) as strings inside encapsulated strings */
    return createSymbol(ParserConstants5.T_NUM_STRING);
}

<ST_IN_SCRIPTING>{DNUM}|{EXPONENT_DNUM} {
    return createFullSymbol(ParserConstants5.T_DNUMBER);
}

<ST_IN_SCRIPTING>"__CLASS__" {
    return createSymbol(ParserConstants5.T_CLASS_C);
}

<ST_IN_SCRIPTING>"__FUNCTION__" {
    return createSymbol(ParserConstants5.T_FUNC_C);
}

<ST_IN_SCRIPTING>"__METHOD__" {
    return createSymbol(ParserConstants5.T_METHOD_C);
}

<ST_IN_SCRIPTING>"__LINE__" {
    return createSymbol(ParserConstants5.T_LINE);
}

<ST_IN_SCRIPTING>"__FILE__" {
    return createSymbol(ParserConstants5.T_FILE);
}

<YYINITIAL>(([^<]|"<"[^?%s<])+)|"<s"|"<" {
    return createSymbol(ParserConstants5.T_INLINE_HTML);
}

<YYINITIAL>"<?"|"<script"{WHITESPACE}+"language"{WHITESPACE}*"="{WHITESPACE}*("php"|"\"php\""|"\'php\'"){WHITESPACE}*">" {
    if (short_tags_allowed || yylength()>2) { /* yyleng>2 means it's not <? but <script> */
        handlePHPStart();
        yybegin(ST_IN_SCRIPTING);
        //return T_OPEN_TAG;
    } else {
        return createSymbol(ParserConstants5.T_INLINE_HTML);
    }
}

<YYINITIAL>"<%="|"<?=" {
    String text = yytext();
    if ((text.charAt(1)=='%' && asp_tags)
        || (text.charAt(1)=='?' && short_tags_allowed)) {
        handlePHPStart();
        yybegin(ST_IN_SCRIPTING);
        //return T_OPEN_TAG_WITH_ECHO;
    } else {
        return createSymbol(ParserConstants5.T_INLINE_HTML);
    }
}

<YYINITIAL>"<%" {
    if (asp_tags) {
        handlePHPStart();
        yybegin(ST_IN_SCRIPTING);
		//return T_OPEN_TAG;
    } else {
        return createSymbol(ParserConstants5.T_INLINE_HTML);
    }
}

<YYINITIAL>"<?php"([ \t]|{NEWLINE}) {
    handlePHPStart();
    yybegin(ST_IN_SCRIPTING);
	//return T_OPEN_TAG;
}

<ST_IN_SCRIPTING,ST_DOUBLE_QUOTES,ST_HEREDOC,ST_BACKQUOTE,ST_VAR_OFFSET>"$"{LABEL} {
    return createFullSymbol(ParserConstants5.T_VARIABLE);
}

<ST_DOUBLE_QUOTES,ST_HEREDOC,ST_BACKQUOTE>"$"{LABEL}"->"[a-zA-Z_\x7f-\xff] {
	yypushback(3);
	pushState(ST_LOOKING_FOR_PROPERTY);
	return createFullSymbol(ParserConstants5.T_VARIABLE);
}

<ST_DOUBLE_QUOTES,ST_HEREDOC,ST_BACKQUOTE>"$"{LABEL}"[" {
	yypushback(1);
	pushState(ST_VAR_OFFSET);
	return createFullSymbol(ParserConstants5.T_VARIABLE);
}

<ST_VAR_OFFSET>"]" {
	popState();
	return createFullSymbol(ParserConstants5.T_CLOSE_RECT);
}

//this is instead {TOKENS}|[{}"`]
<ST_VAR_OFFSET> {
    ";"                     {return createSymbol(ParserConstants5.T_SEMICOLON);}
    ":"                     {return createSymbol(ParserConstants5.T_NEKUDOTAIM);}
    ","                     {return createSymbol(ParserConstants5.T_COMMA);}
    "."                     {return createSymbol(ParserConstants5.T_NEKUDA);}
    "["                     {return createSymbol(ParserConstants5.T_OPEN_RECT);}
//    "]"                     {return createSymbol(ParserConstants5.T_CLOSE_RECT);} //we dont need this line because the rull before deals with it
    "("                     {return createSymbol(ParserConstants5.T_OPEN_PARENTHESE);}
    ")"                     {return createSymbol(ParserConstants5.T_CLOSE_PARENTHESE);}
    "|"                     {return createSymbol(ParserConstants5.T_OR);}
    "^"                     {return createSymbol(ParserConstants5.T_KOVA);}
    "&"                     {return createSymbol(ParserConstants5.T_REFERENCE);}
    "+"                     {return createSymbol(ParserConstants5.T_PLUS);}
    "-"                     {return createSymbol(ParserConstants5.T_MINUS);}
    "/"                     {return createSymbol(ParserConstants5.T_DIV);}
    "*"                     {return createSymbol(ParserConstants5.T_TIMES);}
    "="                     {return createSymbol(ParserConstants5.T_EQUAL);}
    "%"                     {return createSymbol(ParserConstants5.T_PRECENT);}
    "!"                     {return createSymbol(ParserConstants5.T_NOT);}
    "~"                     {return createSymbol(ParserConstants5.T_TILDA);}
    "$"                     {return createSymbol(ParserConstants5.T_DOLLAR);}
    "<"                     {return createSymbol(ParserConstants5.T_RGREATER);}
    ">"                     {return createSymbol(ParserConstants5.T_LGREATER);}
    "?"                     {return createSymbol(ParserConstants5.T_QUESTION_MARK);}
    "@"                     {return createSymbol(ParserConstants5.T_AT);}
    "{"                     {return createSymbol(ParserConstants5.T_CURLY_OPEN);}
    "}"                     {return createSymbol(ParserConstants5.T_CURLY_CLOSE);}
    "\""                     {return createSymbol(ParserConstants5.T_QUATE);}
    "`"                     {return createSymbol(ParserConstants5.T_BACKQUATE);}
}

<ST_VAR_OFFSET>[ \n\r\t\\'#] {
	yypushback(1);
	popState();
	return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

<ST_IN_SCRIPTING>"define" {
    /* not a keyword, hust for recognize constans.*/
    return createFullSymbol(ParserConstants5.T_DEFINE);
}

<ST_IN_SCRIPTING,ST_VAR_OFFSET>{LABEL} {
    return createFullSymbol(ParserConstants5.T_STRING);
}

<ST_IN_SCRIPTING>{WHITESPACE} {
}

<ST_IN_SCRIPTING>"#"|"//" {
	handleCommentStart();
	yybegin(ST_ONE_LINE_COMMENT);
//	yymore();
}

<ST_ONE_LINE_COMMENT>"?"|"%"|">" {
appendTextToComment();
//	yymore();
}

<ST_ONE_LINE_COMMENT>[^\n\r?%>]*{ANY_CHAR} {
	String yytext = yytext();
	switch (yytext.charAt(yytext.length() - 1)) {
		case '?':
		case '%':
		case '>':
			yypushback(1);
			appendTextToComment();
			break;
		default:
			handleCommentEnd();
			yybegin(ST_IN_SCRIPTING);
	}
//	yymore();
}

<ST_ONE_LINE_COMMENT>{NEWLINE} {
	handleCommentEnd();
	yybegin(ST_IN_SCRIPTING);
	//return T_COMMENT;
}

<ST_ONE_LINE_COMMENT>"?>"|"%>" {
    if (asp_tags || yytext().charAt(0)!='%') { /* asp comment? */
	    handleCommentEnd();
        yypushback(yylength());
		yybegin(ST_IN_SCRIPTING);
		//return T_COMMENT;
	} else {
		appendTextToComment();
	}
}

<ST_IN_SCRIPTING>"/*"{WHITESPACE}*"@var"{WHITESPACE}("$"?){LABEL}{WHITESPACE}{LABEL}{WHITESPACE}?"*/" {
    return createFullSymbol(ParserConstants5.T_VAR_COMMENT);
}

<ST_IN_SCRIPTING>"/**" {
handlePHPDocStart();
yybegin(ST_DOCBLOCK);
}

<ST_DOCBLOCK>"*/" {
     handlePHPDocEnd();
     yybegin(ST_IN_SCRIPTING);
}

<ST_DOCBLOCK>{NEWLINE} {
appendTextToComment();
}

<ST_DOCBLOCK>{ANY_CHAR} {
appendTextToComment();
}

<ST_IN_SCRIPTING>"/**/" {
}

<ST_IN_SCRIPTING>"/*" {
	handleCommentStart();
    yybegin(ST_COMMENT);
}

<ST_COMMENT>[^*]+ {
appendTextToComment();
}

<ST_COMMENT>"*/" {
	handleCommentEnd();
    yybegin(ST_IN_SCRIPTING);
}

<ST_COMMENT>"*" {
appendTextToComment();
//	yymore();
}

<ST_IN_SCRIPTING>("?>"|"</script"{WHITESPACE}*">"){NEWLINE}? {
    handlePHPEnd();
    yybegin(YYINITIAL);
    return createSymbol(ParserConstants5.T_SEMICOLON);  /* implicit ';' at php-end tag */
}

<ST_IN_SCRIPTING>"%>"{NEWLINE}? {
    if (asp_tags) {
        handlePHPEnd();
        yybegin(YYINITIAL);
        return createSymbol(ParserConstants5.T_SEMICOLON);  /* implicit ';' at php-end tag */
    } else {
        return createSymbol(ParserConstants5.T_INLINE_HTML);
    }
}

<ST_IN_SCRIPTING>(b?[\"]{DOUBLE_QUOTES_CHARS}*("{"*|"$"*)[\"]) {
    return createFullSymbol(ParserConstants5.T_CONSTANT_ENCAPSED_STRING);
}

<ST_IN_SCRIPTING>(b?[']([^'\\]|("\\"{ANY_CHAR}))*[']) {
    return createFullSymbol(ParserConstants5.T_CONSTANT_ENCAPSED_STRING);
}

<ST_IN_SCRIPTING>b?[\"] {
    yybegin(ST_DOUBLE_QUOTES);
    return createSymbol(ParserConstants5.T_QUATE);
}

<ST_IN_SCRIPTING>b?"<<<"{TABS_AND_SPACES}{LABEL}{NEWLINE} {
    int removeChars = (yytext().charAt(0) == 'b')?4:3;
    heredoc = yytext().substring(removeChars).trim();    // for 'b<<<' or '<<<'
    yybegin(ST_HEREDOC);
    return createSymbol(ParserConstants5.T_START_HEREDOC);
}

<ST_IN_SCRIPTING>[`] {
    yybegin(ST_BACKQUOTE);
    return createSymbol(ParserConstants5.T_BACKQUATE);
}

<ST_START_HEREDOC>{ANY_CHAR} {
	yypushback(1);
	yybegin(ST_HEREDOC);
}

<ST_START_HEREDOC>{LABEL}";"?[\n\r] {
    String text = yytext();
    int length = text.length() - 1;
    text = text.trim();
    
    yypushback(1);
    
    if (text.endsWith(";")) {
        text = text.substring(0, text.length() - 1);
        yypushback(1);
    }
    if (text.equals(heredoc)) {
        heredoc = null;
        yybegin(ST_IN_SCRIPTING);
        return createSymbol(ParserConstants5.T_END_HEREDOC);
    } else {
    	   yybegin(ST_HEREDOC);
    }
}

<ST_HEREDOC>{HEREDOC_CHARS}*{HEREDOC_NEWLINE}+{LABEL}";"?[\n\r] {
    	String text = yytext();

    if (text.charAt(text.length() - 2)== ';') {
		text = text.substring(0, text.length() - 2);
        	yypushback(1);
    } else {
		text = text.substring(0, text.length() - 1);
    }
	
	int textLength = text.length();
	int heredocLength = heredoc.length();
	if (textLength > heredocLength && text.substring(textLength - heredocLength, textLength).equals(heredoc)) {
		yypushback(2);
        	yybegin(ST_END_HEREDOC);
	   	return createFullSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
	}
	yypushback(1);
	
}

<ST_END_HEREDOC>{ANY_CHAR} {
     heredoc = null;
	yybegin(ST_IN_SCRIPTING);
	return createFullSymbol(ParserConstants5.T_END_HEREDOC);
}

<ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC>"{$" {
    pushState(ST_IN_SCRIPTING);
    yypushback(yylength()-1);
    return createSymbol(ParserConstants5.T_CURLY_OPEN_WITH_DOLAR);
}

<ST_DOUBLE_QUOTES>{DOUBLE_QUOTES_CHARS}+ {
	return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

/*
The original parsing rule was {DOUBLE_QUOTES_CHARS}*("{"{2,}|"$"{2,}|(("{"+|"$"+)[\"]))
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_DOUBLE_QUOTES>{DOUBLE_QUOTES_CHARS}*("{""{"+|"$""$"+|(("{"+|"$"+)[\"])) {
    yypushback(1);
    return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

<ST_BACKQUOTE>{BACKQUOTE_CHARS}+ {
	return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

/*
The original parsing rule was {BACKQUOTE_CHARS}*("{"{2,}|"$"{2,}|(("{"+|"$"+)[`]))
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_BACKQUOTE>{BACKQUOTE_CHARS}*("{""{"+|"$""$"+|(("{"+|"$"+)[`])) {
	yypushback(1);
	return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

<ST_HEREDOC>{HEREDOC_CHARS}*({HEREDOC_NEWLINE}+({LABEL}";"?)?)? {
	return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

/*
The original parsing rule was {HEREDOC_CHARS}*({HEREDOC_NEWLINE}+({LABEL}";"?)?)?("{"{2,}|"$"{2,})
but jflex doesn't support a{n,} so we changed a{2,} to aa+
*/
<ST_HEREDOC>{HEREDOC_CHARS}*({HEREDOC_NEWLINE}+({LABEL}";"?)?)?("{""{"+|"$""$"+) {
    yypushback(1);
    return createSymbol(ParserConstants5.T_ENCAPSED_AND_WHITESPACE);
}

<ST_DOUBLE_QUOTES>[\"] {
    yybegin(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants5.T_QUATE);
}

<ST_BACKQUOTE>[`] {
    yybegin(ST_IN_SCRIPTING);
    return createSymbol(ParserConstants5.T_BACKQUATE);
}

<ST_IN_SCRIPTING,YYINITIAL,ST_DOUBLE_QUOTES,ST_BACKQUOTE,ST_HEREDOC,ST_START_HEREDOC,ST_END_HEREDOC,ST_VAR_OFFSET>{ANY_CHAR} {
	// do nothing
}