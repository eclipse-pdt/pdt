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
package org.eclipse.php.internal.core.documentModel.parser;

import java.io.IOException;
import java.io.Reader;

import javax.swing.text.Segment;

import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.collections.IntHashtable;

public abstract class PhpLexer implements Scanner, PHPRegionTypes {
	private class BasicLexerState implements LexerState {

		private final byte lexicalState = (byte) getYy_lexical_state();
		private StateStack phpStack;

		public BasicLexerState() {
			if (!PhpLexer.this.phpStack.isEmpty())
				phpStack = PhpLexer.this.phpStack.createClone();
		}

		public boolean equals(final Object o) {
			if (o == this)
				return true;
			if (o == null)
				return false;
			if (!(o instanceof BasicLexerState))
				return false;
			final BasicLexerState tmp = (BasicLexerState) o;
			if (tmp.lexicalState != lexicalState)
				return false;
			if (phpStack != null && !phpStack.equals(tmp.phpStack))
				return false;
			return phpStack == tmp.phpStack;
		}

		public boolean equalsCurrentStack(final LexerState obj) {
			if (obj == this)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof BasicLexerState))
				return false;
			final BasicLexerState tmp = (BasicLexerState) obj;
			if (tmp.lexicalState != lexicalState)
				return false;
			final StateStack activeStack = getActiveStack();
			final StateStack otherActiveStack = tmp.getActiveStack();
			if (!(activeStack == otherActiveStack || activeStack != null && activeStack.equals(otherActiveStack)))
				return false;
			return true;
		}

		public boolean equalsTop(final LexerState obj) {
			return obj != null && obj.getTopState() == lexicalState;
		}

		protected StateStack getActiveStack() {
			return phpStack;
		}

		public int getTopState() {
			return lexicalState;
		}

		public boolean isSubstateOf(final int state) {
			if (lexicalState == state)
				return true;
			final StateStack activeStack = getActiveStack();
			if (activeStack == null)
				return false;
			return activeStack.contains(state);
		}

		public void restoreState(final Scanner scanner) {
			final PhpLexer lexer = (PhpLexer) scanner;

			if (phpStack == null)
				lexer.phpStack.clear();
			else
				lexer.phpStack.copyFrom(phpStack);

			lexer.yybegin(lexicalState);
		}

		public String toString() {
			final StateStack stack = getActiveStack();
			final String stackStr = stack == null ? "null" : stack.toString();
			return "Stack: " + stackStr + ", currState: " + lexicalState;
		}

	}

	private class HeredocState implements LexerState {
		private String myHeredoc;
		private BasicLexerState theState;

		public HeredocState(final BasicLexerState state) {
			theState = state;
			myHeredoc = heredoc;
		}

		public boolean equals(final Object obj) {
			return obj != null && obj instanceof HeredocState && ((HeredocState) obj).theState.equals(theState) && ((HeredocState) obj).myHeredoc.equals(myHeredoc);
		}

		public boolean equalsCurrentStack(final LexerState obj) {
			if (obj == this)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof HeredocState))
				return false;
			return theState.equals(((HeredocState) obj).theState);
		}

		public boolean equalsTop(final LexerState obj) {
			return theState.equalsTop(obj);
		}

		public int getTopState() {
			return theState.getTopState();
		}

		public boolean isSubstateOf(final int state) {
			return theState.isSubstateOf(state);
		}

		public void restoreState(final Scanner scanner) {
			final PhpLexer lexer = (PhpLexer) scanner;
			theState.restoreState(lexer);
			lexer.heredoc = myHeredoc;
			lexer.heredoc_len = myHeredoc.length();
		}

	}

	// A pool of states. To avoid creation of a new state on each createMemento.
	private static final IntHashtable lexerStates = new IntHashtable(100);
	final public static int ST_PHP_BACKQUOTE = 8;
	final public static int ST_PHP_COMMENT = 16;
	final public static int ST_PHP_DOC_COMMENT = 18;
	final public static int ST_PHP_DOUBLE_QUOTES = 4;
	final public static int ST_PHP_HEREDOC = 12;
	final public static int ST_PHP_IN_SCRIPTING = 2;

	final public static int ST_PHP_LINE_COMMENT = 20;

	final public static int ST_PHP_SINGLE_QUOTE = 6;

	/**
	 * This character denotes the end of file
	 */
	final public static int YYEOF = -1;

	protected static final boolean isLowerCase(final String text) {
		if (text == null)
			return false;
		for (int i = 0; i < text.length(); i++)
			if (!Character.isLowerCase(text.charAt(i)))
				return false;
		return true;
	}

	protected boolean asp_tags = true;

	protected int defaultReturnValue = -1;

	//    public static final boolean isPHPRegularState(int state) {
	//        return !isPHPCommentState(state) && !isPHPQuotesState(state);
	//    }
	//
	//    public static final boolean isPHPCommentState(String currentType) {
	//        return state == ST_PHP_COMMENT ||
	//                state == ST_PHP_DOC_COMMENT ||
	//                state == ST_PHP_LINE_COMMENT;
	//    }
	//
	//    public static final boolean isPHPQuotesState(int state) {
	//        return state == ST_PHP_DOUBLE_QUOTES ||
	//                state == ST_PHP_SINGLE_QUOTE ||
	//                state == ST_PHP_HEREDOC ||
	//                state == ST_PHP_BACKQUOTE;
	//    }

	protected int firstPos = -1; // the first position in the array

	protected String heredoc = null;
	protected int heredoc_len = 0;
	protected StateStack phpStack;

	protected void beginState(final int state) {
		yybegin(state);
	}

	/**
	 * build a key that represents the current state of the lexer.
	 */
	private int buildStateKey() {
		int rv = getYy_lexical_state();

		for (int i = 0; i < phpStack.size(); i++)
			rv = 31 * rv + phpStack.get(i);
		if (heredoc != null)
			for (int i = 0; i < heredoc.length(); i++)
				rv = 31 * rv + heredoc.charAt(i);
		return rv;
	}

	public Object createLexicalStateMemento() {
		//System.out.println("lexerStates size:" + lexerStates.size());
		final int key = buildStateKey();
		Object state = lexerStates.get(key);
		if (state == null) {
			state = new BasicLexerState();
			if (getYy_lexical_state() == PhpLexer.ST_PHP_HEREDOC)
				state = new HeredocState((BasicLexerState) state);
			lexerStates.put(key, state);
		}
		return state;
	}

	public boolean getAspTags() {
		return asp_tags;
	}

	// lex to the EOF. and return the ending state.
	public Object getEndingState() throws IOException {
		lexToEnd();
		return createLexicalStateMemento();
	}

	/**
	 * return the index where start we started to lex.
	 */
	public int getFirstIndex() {
		return firstPos;
	}

	public int getMarkedPos() {
		return getYy_markedPos();
	}

	public abstract int[] getParamenters();

	public void getText(final int start, final int length, final Segment s) {
		if (start + length > getYy_endRead())
			throw new RuntimeException("bad segment !!");
		s.array = getYy_buffer();
		s.offset = start;
		s.count = length;
	}

	public int getTokenStart() {
		return getYy_startRead() - getYy_pushBackPosition();
	}

	protected abstract char[] getYy_buffer();

	protected abstract int getYy_endRead();

	protected abstract int getYy_lexical_state();

	protected abstract int getYy_markedPos();

	protected abstract int getYy_pushBackPosition();

	protected abstract int getYy_startRead();

	public void initialize(final int state) {
		phpStack = new StateStack();
		beginState(state);
	}

	/**
	 * reset to a new segment. this do not change the state of the lexer.
	 * This method is used to scan nore than one segment as if the are one segment.
	 */

	// lex to the end of the stream.
	public String lexToEnd() throws IOException {
		String curr = yylex();
		String last = curr;
		while (curr != null) {
			last = curr;
			curr = yylex();
		}
		return last;
	}

	public String lexToTokenAt(final int offset) throws IOException {
		if (firstPos + offset < getYy_markedPos())
			throw new RuntimeException("Bad offset");
		String t = yylex();
		while (getYy_markedPos() < firstPos + offset && t != null)
			t = yylex();
		return t;
	}

	/*
	 protected void printState(){
	 System.out.println("this.yy_startRead:"+yy_startRead);
	 System.out.println("this.yy_endRead:"+ this.yy_endRead);
	 System.out.println("this.yy_currentPos:" + this.yy_currentPos);
	 System.out.println("this.yy_markedPos:"+ this.yy_markedPos);
	 System.out.println("this.yychar:"+this.yychar);
	 System.out.println("this.yy_atEOF:"+this.yy_atEOF);
	 System.out.println("this.yy_reader:"+yy_reader);
	 }
	 */

	protected void popState() {
		//System.out.println("popState");
		yybegin(phpStack.popStack());
	}

	protected void pushState(final int state) {
		//System.out.println("pushState");
		phpStack.pushStack(getYy_lexical_state());
		yybegin(state);
	}

	public abstract void reset(char array[], int offset, int length);

	public abstract void reset(Reader reader, char[] buffer, int[] parameters);

	/**
	 * reset to a new segment. this do not change the state of the lexer.
	 * This method is used to scan nore than one segment as if the are one segment.
	 */
	public void reset(final Segment s) {
		reset(s.array, s.offset, s.count);
	}

	public void setAspTags(final boolean b) {
		asp_tags = b;
	}

	public void setState(final Object state) {
		((LexerState) state).restoreState(this);
	}

	public abstract void yybegin(int newState);

	public abstract int yylength();
	
    public abstract String yytext();
    
    public int yystart() {
    	return getYy_startRead(); 
    }   
    
    public abstract int yystate();
}
