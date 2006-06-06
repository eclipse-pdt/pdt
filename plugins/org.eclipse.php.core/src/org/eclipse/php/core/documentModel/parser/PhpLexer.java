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
package org.eclipse.php.core.documentModel.parser;

import java.io.IOException;
import java.io.Reader;

import javax.swing.text.Segment;

import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.core.util.collections.IntHashtable;

public abstract class PhpLexer implements Scanner, PHPRegionTypes {
	/**
	 * This character denotes the end of file
	 */
	final public static int YYEOF = -1;

	final public static int ST_PHP_SINGLE_QUOTE = 6;
	final public static int ST_PHP_COMMENT = 16;
	final public static int ST_PHP_DOUBLE_QUOTES = 4;
	final public static int ST_PHP_HEREDOC = 12;
	final public static int ST_PHP_LINE_COMMENT = 20;
	final public static int ST_PHP_BACKQUOTE = 8;
	final public static int ST_PHP_DOC_COMMENT = 18;
	final public static int ST_PHP_IN_SCRIPTING = 2;

	protected abstract int getYy_lexical_state();

	protected abstract int getYy_markedPos();

	protected abstract int getYy_endRead();

	protected abstract char[] getYy_buffer();

	public abstract void yybegin(int newState);

	public abstract void reset(char array[], int offset, int length);

	public abstract void reset(Reader reader, char[] buffer, int[] parameters);

	public abstract int[] getParamenters();
	
	protected abstract int getYy_startRead();

	protected abstract int getYy_pushBackPosition();

	public abstract int yylength();

	public int getTokenStart() {
		return this.getYy_startRead() - this.getYy_pushBackPosition();
	}

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

	protected int defaultReturnValue = -1;

	protected int firstPos = -1; // the first position in the array
	protected int heredoc_len = 0;
	protected String heredoc = null;
	protected boolean asp_tags = true;

	protected StateStack phpStack;

	public void initialize(int state) {
		phpStack = new StateStack();
		beginState(state);
	}

	protected void beginState(int state) {
		yybegin(state);
	}

	protected void pushState(int state) {
		//System.out.println("pushState");
		phpStack.pushStack(getYy_lexical_state());
		yybegin(state);
	}

	protected void popState() {
		//System.out.println("popState");
		yybegin(phpStack.popStack());
	}

	/**
	 * build a key that represents the current state of the lexer.
	 */
	private int buildStateKey() {
		int rv = getYy_lexical_state();

		for (int i = 0; i < phpStack.size(); i++) {
			rv = 31 * rv + phpStack.get(i);
		}
		if (heredoc != null) {
			for (int i = 0; i < heredoc.length(); i++) {
				rv = 31 * rv + heredoc.charAt(i);
			}
		}
		return rv;
	}

	private class BasicLexerState implements LexerState {

		private StateStack phpStack;
		private byte lexicalState = (byte) getYy_lexical_state();

		public BasicLexerState() {
			if (!PhpLexer.this.phpStack.isEmpty()) {
				this.phpStack = PhpLexer.this.phpStack.createClone();
			}
		}

		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (o == null) {
				return false;
			}
			if (!(o instanceof BasicLexerState)) {
				return false;
			}
			BasicLexerState tmp = (BasicLexerState) o;
			if (tmp.lexicalState != this.lexicalState) {
				return false;
			}
			if (this.phpStack != null && !this.phpStack.equals(tmp.phpStack)) {
				return false;
			}
			return this.phpStack == tmp.phpStack;
		}

		public boolean equalsTop(LexerState obj) {
			return obj != null && obj.getTopState() == lexicalState;
		}

		public boolean equalsCurrentStack(LexerState obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof BasicLexerState)) {
				return false;
			}
			BasicLexerState tmp = (BasicLexerState) obj;
			if (tmp.lexicalState != this.lexicalState) {
				return false;
			}
			StateStack activeStack = getActiveStack();
			StateStack otherActiveStack = tmp.getActiveStack();
			if (!(activeStack == otherActiveStack || (activeStack != null && activeStack.equals(otherActiveStack)))) {
				return false;
			}
			return true;
		}

		public int getTopState() {
			return lexicalState;
		}

		protected StateStack getActiveStack() {
			return this.phpStack;
		}

		public void restoreState(Scanner scanner) {
			PhpLexer lexer = (PhpLexer) scanner;

			if (this.phpStack == null) {
				lexer.phpStack.clear();
			} else {
				lexer.phpStack.copyFrom(this.phpStack);
			}

			lexer.yybegin(this.lexicalState);
		}

		public boolean isSubstateOf(int state) {
			if (lexicalState == state) {
				return true;
			}
			StateStack activeStack = this.getActiveStack();
			if (activeStack == null) {
				return false;
			}
			return activeStack.contains(state);
		}

		public String toString() {
			StateStack stack = getActiveStack();
			String stackStr = (stack == null) ? "null" : stack.toString();
			return "Stack: " + stackStr + ", currState: " + this.lexicalState;
		}

	}

	private class HeredocState implements LexerState {
		private String myHeredoc;
		private BasicLexerState theState;

		public HeredocState(BasicLexerState state) {
			theState = state;
			myHeredoc = PhpLexer.this.heredoc;
		}

		public boolean isSubstateOf(int state) {
			return theState.isSubstateOf(state);
		}

		public void restoreState(Scanner scanner) {
			PhpLexer lexer = (PhpLexer) scanner;
			theState.restoreState(lexer);
			lexer.heredoc = myHeredoc;
			lexer.heredoc_len = myHeredoc.length();
		}

		public int getTopState() {
			return theState.getTopState();
		}

		public boolean equalsCurrentStack(LexerState obj) {
			if (obj == this) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof HeredocState)) {
				return false;
			}
			return theState.equals(((HeredocState) obj).theState);
		}

		public boolean equalsTop(LexerState obj) {
			return theState.equalsTop(obj);
		}

		public boolean equals(Object obj) {
			return (obj != null && obj instanceof HeredocState && ((HeredocState) obj).theState.equals(theState) && ((HeredocState) obj).myHeredoc.equals(myHeredoc));
		}

	}

	// A pool of states. To avoid creation of a new state on each createMemento.
	private static IntHashtable lexerStates = new IntHashtable(100);

	public Object createLexicalStateMemento() {
		//System.out.println("lexerStates size:" + lexerStates.size());
		int key = buildStateKey();
		Object state = lexerStates.get(key);
		if (state == null) {
			state = new BasicLexerState();
			if (getYy_lexical_state() == PhpLexer.ST_PHP_HEREDOC) {
				state = new HeredocState((BasicLexerState) state);
			}
			lexerStates.put(key, state);
		}
		return state;
	}

	// lex to the EOF. and return the ending state.
	public Object getEndingState() throws IOException {
		lexToEnd();
		return this.createLexicalStateMemento();
	}

	// lex to the end of the stream.
	public String lexToEnd() throws IOException {
		String curr = this.yylex();
		String last = curr;
		while (curr != null) {
			last = curr;
			curr = yylex();
		}
		return last;
	}

	public String lexToTokenAt(int offset) throws IOException {
		if (this.firstPos + offset < this.getYy_markedPos()) {
			throw new RuntimeException("Bad offset");
		}
		String t = yylex();
		while (this.getYy_markedPos() < this.firstPos + offset && t != null) {
			t = yylex();
		}
		return t;
	}

	public int getMarkedPos() {
		return this.getYy_markedPos();
	}

	/**
	 * return the index where start we started to lex.
	 */
	public int getFirstIndex() {
		return firstPos;
	}

	public void getText(int start, int length, Segment s) {
		if (start + length > this.getYy_endRead()) {
			throw new RuntimeException("bad segment !!");
		}
		s.array = this.getYy_buffer();
		s.offset = start;
		s.count = length;
	}

	/**
	 * reset to a new segment. this do not change the state of the lexer.
	 * This method is used to scan nore than one segment as if the are one segment.
	 */

	/**
	 * reset to a new segment. this do not change the state of the lexer.
	 * This method is used to scan nore than one segment as if the are one segment.
	 */
	public void reset(Segment s) {
		reset(s.array, s.offset, s.count);
	}

	public void setState(Object state) {
		((LexerState) state).restoreState(this);
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

	public void setAspTags(boolean b) {
		asp_tags = b;
	}

	public boolean getAspTags() {
		return asp_tags;
	}

	protected static final boolean isLowerCase(String text) {
		if (text == null) {
			return false;
		}
		for (int i = 0; i < text.length(); i++) {
			if (!Character.isLowerCase(text.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isPHPQuotesState(String type) {
		return type == PHP_CONSTANT_ENCAPSED_STRING;
	}

	public static boolean isPHPCommentState(String type) {
		return type == null ? false : isPHPMultiLineCommentState(type) || isPHPLineCommentState(type) || type == PHP_HEREDOC_TAG || isPHPDocState(type);
	}

	public static boolean isPHPDocState(String type) {
		return type == null ? false : type.startsWith("PHPDOC");
	}

	public static boolean isPHPMultiLineCommentState(String type) {
		return type == PHP_COMMENT || type == PHPRegionTypes.PHP_COMMENT_START || type == PHPRegionTypes.PHP_COMMENT_END; 
	}

	public static boolean isPHPLineCommentState(String type) {
		return type == PHP_LINE_COMMENT ;
	}

	public static final boolean isPHPRegularState(String type) {
		return !isPHPCommentState(type) && !isPHPQuotesState(type);
	}
}
