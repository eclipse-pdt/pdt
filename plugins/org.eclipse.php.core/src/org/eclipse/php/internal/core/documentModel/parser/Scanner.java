/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/*
 * Scanner.java
 *
 * Created on May 17, 2000, 11:19 AM
 */
package org.eclipse.php.internal.core.documentModel.parser;

import java.io.IOException;

import javax.swing.text.Segment;

/**
 * @author erez
 */
public interface Scanner {

	/**
	 * Creates new Scanner
	 */
	public Object createLexicalStateMemento();

	public String yylex() throws IOException;

	/**
	 * lex to the EOF. and return the ending state.
	 * 
	 * @throws IOException
	 */
	public Object getEndingState() throws IOException;

	/**
	 * lex to the end of the stream.
	 * 
	 * @throws IOException
	 */
	public String lexToEnd() throws IOException;

	public String lexToTokenAt(int offset) throws IOException;

	public int getMarkedPos();

	/**
	 * return the index where start we started to lex.
	 */
	public int getFirstIndex();

	public void getText(int start, int length, Segment s);

	/**
	 * reset to a new segment. this do not change the state of the lexer. This
	 * method is used to scan nore than one segment as if the are one segment.
	 */
	public void reset(char array[], int offset, int length);

	/**
	 * reset to a new segment. this do not change the state of the lexer. This
	 * method is used to scan nore than one segment as if the are one segment.
	 */
	public void reset(Segment s);

	public void setState(Object state);

	public void initialize(int state);

	public static interface LexerState {

		public boolean isSubstateOf(int state);

		public void restoreState(Scanner scanner);

		public int getTopState();

		public boolean equalsTop(LexerState obj);

		public boolean equalsCurrentStack(LexerState obj);

	}

}