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
package org.eclipse.php.internal.core.documentModel.parser.regions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

/**
 * Description: Holds the tokens extracted from the script   
 * @author Roy, 2007
 */
public class PhpTokenContainer {

	// holds PHP tokens 
	protected final LinkedList phpTokens = new LinkedList(); // of ITextRegion

	// holds the location and state, where the lexical anlyzer state was changed
	protected final LinkedList lexerStateChanges = new LinkedList(); // of LexerStateChanged

	// holds the iterator for the php tokens linked list
	// this iterator follows the localization principle 
	// i.e. the user usually works in the same area of the document 
	protected ListIterator tokensIterator = null;

	/**
	 * find token for a given location 
	 * @param offset
	 * @return
	 * @throws BadLocationException - if the offset is out of bound 
	 */
	public ITextRegion getToken(int offset) throws BadLocationException {
		assert tokensIterator != null;
		if (phpTokens.isEmpty()) {
			return null;
		}

		//we have at least one region...		
		checkBadLocation(offset);

		// smart searching
		ITextRegion result = (ITextRegion) (tokensIterator.hasNext() ? tokensIterator.next() : tokensIterator.previous());
		if (isInside(result, offset)) {
			return result;
		}

		if (offset >= result.getEnd()) { // if the offset is beyond - go fetch from next
			while (tokensIterator.hasNext() && !isInside(result, offset)) {
				result = (ITextRegion) tokensIterator.next();
			}
		} else { // else go fetch from previous
			while (tokensIterator.hasPrevious() && !isInside(result, offset)) {
				result = (ITextRegion) tokensIterator.previous();
			}
			// moves the iterator to the next one
			if (tokensIterator.hasNext()) {
				tokensIterator.next();
			}
		}

		return result;
	}

	public ITextRegion[] getTokens(final int offset, final int length) throws BadLocationException {
		assert length >= 0;
		List result = new ArrayList(); // list of ITextRegion

		ITextRegion token = getToken(offset);
		result.add(token);
		
		while (tokensIterator.hasNext() && token.getEnd() <= offset + length) {
			token = (ITextRegion) tokensIterator.next();
			result.add(token);
		} 

		return (ITextRegion[]) result.toArray(new ITextRegion[result.size()]);
	}

	private final boolean isInside(ITextRegion region, int offset) {
		return region.getStart() <= offset && offset < region.getEnd();
	}

	/**
	 * @param offset
	 * @return the lexer state at the given offset
	 * @throws BadLocationException
	 */
	public LexerState getState(int offset) throws BadLocationException {
		Iterator iter = lexerStateChanges.iterator();
		assert iter.hasNext();

		LexerStateChange element = (LexerStateChange) iter.next();
		LexerState lastState = null;

		while (offset >= element.getOffset()) {
			lastState = element.state;
			if (!iter.hasNext()) {
				return lastState;
			}
			element = (LexerStateChange) iter.next();
		}
		return lastState;
	}

	/**
	 * @param offset
	 * @return the partition type of the given offset
	 * @throws BadLocationException 
	 */
	public String getPartitionType(int offset) throws BadLocationException {
		final ITextRegion token = getToken(offset);

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
		else if (PHPRegionTypes.TASK == type) {
			return getPartitionType(offset - 1);
		} else {
			return PHPPartitionTypes.PHP_DEFAULT;
		}
	}

	/**
	 * Updates the container with the new states
	 * @param newContainer
	 * @param fromOffset
	 * @param toOffset
	 * @param size
	 */
	public void updateStateChanges(PhpTokenContainer newContainer, int fromOffset, int toOffset) {
		if (newContainer.lexerStateChanges.size() < 2) {
			return;
		}

		// remove
		final ListIterator oldIterator = removeOldChanges(fromOffset, toOffset);

		// add
		final Iterator newIterator = newContainer.lexerStateChanges.iterator();
		newIterator.next(); // ignore the first state change (it is identical to the original one)
		while (newIterator.hasNext()) {
			oldIterator.add(newIterator.next());
		}
	}

	public ListIterator removeSubList(ITextRegion tokenStart, ITextRegion tokenEnd) {
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
				region = (ITextRegion) tokensIterator.next();
				tokensIterator.remove();
			} while (tokensIterator.hasNext() && region != tokenEnd);
		}

		return tokensIterator;
	}

	/**
	 * One must call getModelForWrite() in order to construct the list of php tokens 
	 */
	public void getModelForCreation() {
		tokensIterator = null;
	}

	/**
	 * One must call releaseModelForWrite() after constructing the  
	 */
	public void releaseModelFromCreation() {
		tokensIterator = phpTokens.listIterator();
	}

	/**
	 * Returns an read-only iterator to the php tokens, 
	 * calling next() returns the first token in the wanted offset
	 * @param offset
	 * @param length
	 * @return
	 * @throws BadLocationException
	 */
	public ListIterator getPhpTokensIterator(final int offset) throws BadLocationException {
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
	 * Push region to the end of the tokens list
	 * @param region
	 * @param lexerState
	 */
	public void addLast(String yylex, int start, int yylengthLength, int yylength, Object lexerState) {
		assert (phpTokens.size() == 0 || getLastToken().getEnd() == start) && tokensIterator == null;

		final ContextRegion contextRegion = new ContextRegion(yylex, start, yylengthLength, yylength);

		if (lexerStateChanges.size() == 0 || !getLastChange().state.equals(lexerState)) {
			lexerStateChanges.addLast(new LexerStateChange((LexerState) lexerState, contextRegion));
		}
		phpTokens.addLast(contextRegion);
	}

	/**
	 * @return the whole tokens as an array 
	 */
	public ITextRegion[] getPhpTokens() {
		return (ITextRegion[]) phpTokens.toArray(new ITextRegion[phpTokens.size()]);
	}

	/**
	 * Clears the containers
	 */
	public void reset() {
		this.phpTokens.clear();
		this.lexerStateChanges.clear();
	}

	/**
	 * This node represent a change in the lexer state during lexical analysis
	 */
	protected static final class LexerStateChange {
		public final LexerState state;
		public final ITextRegion firstRegion;

		public LexerStateChange(final LexerState state, final ITextRegion firstRegion) {
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
			return "[" + getOffset() + "] - " + this.state.getTopState();
		}
	}

	/**
	 * check for out of bound
	 * @param offset
	 * @throws BadLocationException
	 */
	protected final void checkBadLocation(int offset) throws BadLocationException {
		ITextRegion lastRegion = getLastToken();
		if (lastRegion.getEnd() < offset) {
			throw new BadLocationException();
		}
	}

	protected final ITextRegion getLastToken() {
		return (ITextRegion) phpTokens.getLast();
	}

	protected LexerStateChange getLastChange() {
		return (LexerStateChange) lexerStateChanges.getLast();
	}

	protected final ListIterator removeOldChanges(int fromOffset, int toOffset) {
		final ListIterator iterator = (ListIterator) lexerStateChanges.iterator();

		LexerStateChange element = (LexerStateChange) iterator.next();
		while (element.getOffset() <= toOffset) {
			if (element.getOffset() > fromOffset && element.getOffset() <= toOffset) {
				iterator.remove();
			}
			if (!iterator.hasNext()) {
				return iterator;
			}
			element = (LexerStateChange) iterator.next();
		}

		return iterator;
	}
}
