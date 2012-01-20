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
package org.eclipse.php.internal.core.documentModel.parser.regions;

import java.util.*;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * Description: Holds the tokens extracted from the script
 * 
 * @author Roy, 2007
 */
public class PhpTokenContainer {

	// holds PHP tokens
	protected final LinkedList<ContextRegion> phpTokens = new LinkedList<ContextRegion>(); // of
	// ITextRegion

	// holds the location and state, where the lexical anlyzer state was changed
	protected final LinkedList<LexerStateChange> lexerStateChanges = new LinkedList<LexerStateChange>(); // of
	// LexerStateChanged

	// holds the iterator for the php tokens linked list
	// this iterator follows the localization principle
	// i.e. the user usually works in the same area of the document
	protected ListIterator<ContextRegion> tokensIterator = null;

	/**
	 * find token for a given location
	 * 
	 * @param offset
	 * @return
	 * @throws BadLocationException
	 *             - if the offset is out of bound
	 */
	public synchronized ITextRegion getToken(int offset)
			throws BadLocationException {
		assert tokensIterator != null;
		if (phpTokens.isEmpty()) {
			return null;
		}

		// we have at least one region...
		checkBadLocation(offset);

		// smart searching
		ITextRegion result = tokensIterator.hasNext() ? tokensIterator.next()
				: tokensIterator.previous();
		ITextRegion oldResult = result;
		if (isInside(result, offset)) {
			return result;
		}

		if (result != null && offset >= result.getEnd()) { // if the offset is
															// beyond - go fetch
			// from next
			while (tokensIterator.hasNext() && !isInside(result, offset)) {
				if (result == null) {
					return oldResult;
				} else {
					oldResult = result;
				}
				result = tokensIterator.next();

			}
		} else { // else go fetch from previous
			while (tokensIterator.hasPrevious() && !isInside(result, offset)) {
				if (result == null) {
					return oldResult;
				} else {
					oldResult = result;
				}
				result = tokensIterator.previous();
			}
			// moves the iterator to the next one
			if (tokensIterator.hasNext()) {
				tokensIterator.next();
			}
		}

		return result;
	}

	public synchronized ITextRegion[] getTokens(final int offset,
			final int length) throws BadLocationException {
		assert length >= 0;
		List<ITextRegion> result = new ArrayList<ITextRegion>(); // list of
		// ITextRegion

		ITextRegion token = getToken(offset);
		if (token != null) {
			result.add(token);
		}

		while (tokensIterator.hasNext() && token != null
				&& token.getEnd() <= offset + length) {
			token = tokensIterator.next();
			result.add(token);
		}

		return result.toArray(new ITextRegion[result.size()]);
	}

	private final boolean isInside(ITextRegion region, int offset) {
		return region != null && region.getStart() <= offset
				&& offset < region.getEnd();
	}

	/**
	 * @param offset
	 * @return the lexer state at the given offset
	 * @throws BadLocationException
	 */
	public synchronized LexerState getState(int offset)
			throws BadLocationException {
		Iterator<LexerStateChange> iter = lexerStateChanges.iterator();
		assert iter.hasNext();

		LexerStateChange element = iter.next();
		LexerState lastState = null;

		while (offset >= element.getOffset()) {
			lastState = element.state;
			if (!iter.hasNext()) {
				return lastState;
			}
			element = iter.next();
		}
		return lastState;
	}

	/**
	 * @param offset
	 * @return the partition type of the given offset
	 * @throws BadLocationException
	 */
	public synchronized String getPartitionType(int offset)
			throws BadLocationException {
		ITextRegion token = getToken(offset);
		while (token != null
				&& PHPRegionTypes.PHPDOC_TODO.equals(token.getType())
				&& token.getStart() - 1 >= 0) {
			token = getToken(token.getStart() - 1);
		}
		assert token != null;
		final String type = token.getType();

		if (PHPPartitionTypes.isPHPMultiLineCommentState(type))
			return PHPPartitionTypes.PHP_MULTI_LINE_COMMENT;
		else if (PHPPartitionTypes.isPHPLineCommentState(type))
			return PHPPartitionTypes.PHP_SINGLE_LINE_COMMENT;
		else if (PHPPartitionTypes.isPHPDocState(type))
			return PHPPartitionTypes.PHP_DOC;
		else if (PHPPartitionTypes.isPHPQuotesState(type))
			return PHPPartitionTypes.PHP_QUOTED_STRING;
		else {
			return PHPPartitionTypes.PHP_DEFAULT;
		}
	}

	/**
	 * Updates the container with the new states
	 * 
	 * @param newContainer
	 * @param fromOffset
	 * @param toOffset
	 * @param size
	 */
	public synchronized void updateStateChanges(PhpTokenContainer newContainer,
			int fromOffset, int toOffset) {
		if (newContainer.lexerStateChanges.size() < 2) {
			return;
		}

		// remove
		final ListIterator<LexerStateChange> oldIterator = removeOldChanges(
				fromOffset, toOffset);

		// add
		final Iterator<LexerStateChange> newIterator = newContainer.lexerStateChanges
				.iterator();
		newIterator.next(); // ignore the first state change (it is identical to
		// the original one)

		// goto the previous before adding
		setIterator(oldIterator, fromOffset, toOffset);

		while (newIterator.hasNext()) {
			oldIterator.add(newIterator.next());
		}
	}

	private void setIterator(ListIterator<LexerStateChange> oldIterator,
			int fromOffset, int toOffset) {
		if (oldIterator.nextIndex() != 1) {
			oldIterator.previous();
		} else {
			return;
		}

		LexerStateChange next = oldIterator.next();
		int offset = next.getOffset();
		if (offset > fromOffset) {
			oldIterator.previous();
		}

	}

	public synchronized ListIterator<ContextRegion> removeTokensSubList(
			ITextRegion tokenStart, ITextRegion tokenEnd) {
		assert tokenStart != null;

		// go to the start region
		ITextRegion region = null;
		;
		try {
			region = getToken(tokenStart.getStart());
		} catch (BadLocationException e) {
			assert false;
		}
		assert region == tokenStart;

		tokensIterator.remove();

		// if it the start and the end are equal - remove and exit
		if (tokenStart != tokenEnd) {
			// remove to the end
			do {
				region = tokensIterator.next();
				tokensIterator.remove();
			} while (tokensIterator.hasNext() && region != tokenEnd);
		}

		return tokensIterator;
	}

	/**
	 * One must call getModelForWrite() in order to construct the list of php
	 * tokens
	 */
	public synchronized void getModelForCreation() {
		tokensIterator = null;
	}

	/**
	 * One must call releaseModelForWrite() after constructing the
	 */
	public synchronized void releaseModelFromCreation() {
		tokensIterator = phpTokens.listIterator();
	}

	/**
	 * Returns an read-only iterator to the php tokens, calling next() returns
	 * the first token in the wanted offset
	 * 
	 * @param offset
	 * @param length
	 * @return
	 * @throws BadLocationException
	 */
	public synchronized ListIterator<ContextRegion> getPhpTokensIterator(
			final int offset) throws BadLocationException {
		// fast results for empty lists
		if (phpTokens.isEmpty()) {
			return tokensIterator;
		}
		checkBadLocation(offset);

		// set the token iterator to the right place
		getToken(offset);

		return tokensIterator;
	}

	/**
	 * @return the whole tokens as an array
	 */
	public synchronized ITextRegion[] getPhpTokens() {
		return phpTokens.toArray(new ITextRegion[phpTokens.size()]);
	}

	/**
	 * Clears the containers
	 */
	public synchronized void reset() {
		this.phpTokens.clear();
		this.lexerStateChanges.clear();
	}

	/**
	 * @return true for empty container
	 */
	public synchronized boolean isEmpty() {
		return this.phpTokens.isEmpty();
	}

	/**
	 * Push region to the end of the tokens list
	 * 
	 * @param region
	 * @param lexerState
	 */
	public synchronized void addLast(String yylex, int start,
			int yylengthLength, int yylength, Object lexerState) {
		assert (phpTokens.size() == 0 || getLastToken().getEnd() == start)
				&& tokensIterator == null;

		if (phpTokens.size() > 0) {
			ContextRegion lastContextRegion = (ContextRegion) phpTokens
					.get(phpTokens.size() - 1);
			if (deprecatedKeywordAfter(lastContextRegion.getType())) {
				if (isKeyword(yylex)) {
					yylex = PHPRegionTypes.PHP_STRING;
				}
			}
		}
		// if state was change - we add a new token and add state
		if (lexerStateChanges.size() == 0
				|| !getLastChange().state.equals(lexerState)) {
			int textLength = (AbstractPhpLexer.WHITESPACE.equals(yylex)) ? 0
					: yylengthLength;

			final ContextRegion contextRegion = new ContextRegion(yylex, start,
					textLength, yylength);
			phpTokens.addLast(contextRegion);
			lexerStateChanges.addLast(new LexerStateChange(
					(LexerState) lexerState, contextRegion));
			return;
		}

		assert phpTokens.size() > 0;
		// if we can only adjust the previous token size
		if (yylex == AbstractPhpLexer.WHITESPACE) {
			final ITextRegion last = phpTokens.getLast();
			last.adjustLength(yylength);
		} else { // else - add as a new token
			final ContextRegion contextRegion = new ContextRegion(yylex, start,
					yylengthLength, yylength);
			phpTokens.addLast(contextRegion);
		}
	}

	/**
	 * if the keyword could be use as identifier
	 * 
	 * @param yylex
	 * @return if yylex is one of the keywords that could be use as identifier
	 */
	public static boolean isKeyword(String yylex) {
		if (PHPRegionTypes.PHP_FROM.equals(yylex)) {
			return true;
		}
		return false;
	}

	/**
	 * if the keyword should be a normal identifier after special type
	 * 
	 * @param yylex
	 * @return if the keyword should be a normal identifier after yylex
	 */
	public static boolean deprecatedKeywordAfter(String yylex) {
		if (PHPRegionTypes.PHP_FUNCTION.equals(yylex)
				|| PHPRegionTypes.PHP_CONST.equals(yylex)) {
			return true;
		}
		return false;
	}

	/**
	 * Adjust the length of the last token for whitespace tokens
	 * 
	 * @param yylex
	 * @param start
	 * @param yylengthLength
	 * @param yylength
	 * @param lexerState
	 */
	public synchronized void adjustWhitespace(String yylex, int start,
			int yylengthLength, int yylength, Object lexerState) {
		assert (phpTokens.size() == 0 || getLastToken().getEnd() == start)
				&& tokensIterator == null;

		// if state was change - we add a new token and add state
		if (lexerStateChanges.size() != 0
				&& getLastChange().state.equals(lexerState)) {
			final ITextRegion last = phpTokens.getLast();
			last.adjustLength(yylength);
		}
	}

	/**
	 * This node represent a change in the lexer state during lexical analysis
	 */
	protected static final class LexerStateChange {
		public final LexerState state;
		public final ITextRegion firstRegion;

		public LexerStateChange(final LexerState state,
				final ITextRegion firstRegion) {
			assert firstRegion != null && state != null;

			this.state = state;
			this.firstRegion = firstRegion;
		}

		public final int getOffset() {
			return firstRegion.getStart();
		}

		public int hashCode() {
			return 31 + ((state == null) ? 0 : state.hashCode());
		}

		public boolean equals(Object obj) {
			assert state != null && obj.getClass() == LexerState.class;

			if (this.state == obj)
				return true;
			return state.equals((LexerState) obj);
		}

		public final String toString() {
			return "[" + getOffset() + "] - " + this.state.getTopState(); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * check for out of bound
	 * 
	 * @param offset
	 * @throws BadLocationException
	 */
	protected synchronized final void checkBadLocation(int offset)
			throws BadLocationException {
		ITextRegion lastRegion = getLastToken();
		if (offset < 0 || lastRegion.getEnd() < offset) {
			throw new BadLocationException(
					"offset " + offset + " is out of [0, " + lastRegion.getEnd() + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}
	}

	protected synchronized final ITextRegion getLastToken() {
		return phpTokens.getLast();
	}

	protected synchronized LexerStateChange getLastChange() {
		return lexerStateChanges.getLast();
	}

	protected synchronized final ListIterator<LexerStateChange> removeOldChanges(
			int fromOffset, int toOffset) {
		final ListIterator<LexerStateChange> iterator = (ListIterator<LexerStateChange>) lexerStateChanges
				.iterator();

		LexerStateChange element = iterator.next();
		while (element.getOffset() <= toOffset) {
			if (element.getOffset() > fromOffset
					&& element.getOffset() <= toOffset) {
				iterator.remove();
			}
			if (!iterator.hasNext()) {
				return iterator;
			}
			element = iterator.next();
		}

		return iterator;
	}
}
