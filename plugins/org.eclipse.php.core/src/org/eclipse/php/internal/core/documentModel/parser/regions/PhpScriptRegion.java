/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
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

import java.io.IOException;
import java.io.Reader;
import java.util.ListIterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.Logger;

/**
 * Description: This text region is a PHP foreign region that includes the php
 * tokens * In order to know that this text region is PhpScript one should use:
 * 
 * <code> if (region.getType() == PHPRegionContext())  { (PhpScriptRegion) region } </code>
 * 
 * @author Roy, 2007
 */
public class PhpScriptRegion extends ForeignRegion implements IPhpScriptRegion {

	private static final String PHP_SCRIPT = "PHP Script"; //$NON-NLS-1$
	private PhpTokenContainer tokensContaier = new PhpTokenContainer();
	private final IProject project;
	private int updatedTokensStart = -1;

	public int getUpdatedTokensStart() {
		return updatedTokensStart;
	}

	public int getUpdatedTokensLength() {
		return updatedTokensEnd - updatedTokensStart;
	}

	private int updatedTokensEnd = -1;

	private int ST_PHP_LINE_COMMENT = -1;
	private int ST_PHP_IN_SCRIPTING = -1;

	// true when the last reparse action is full reparse
	protected boolean isFullReparsed;

	public PhpScriptRegion(String newContext, int startOffset,
			IProject project, AbstractPhpLexer phpLexer) {
		super(newContext, startOffset, 0, 0, PhpScriptRegion.PHP_SCRIPT);

		this.project = project;

		try {
			// we use reflection here since we don't know the constant value of
			// of this state in specific PHP version lexer
			ST_PHP_LINE_COMMENT = phpLexer.getClass()
					.getField("ST_PHP_LINE_COMMENT").getInt(phpLexer); //$NON-NLS-1$
			ST_PHP_IN_SCRIPTING = phpLexer.getClass()
					.getField("ST_PHP_IN_SCRIPTING").getInt(phpLexer); //$NON-NLS-1$
		} catch (Exception e) {
			Logger.logException(e);
		}
		completeReparse(phpLexer);
	}

	/**
	 * @see IPhpScriptRegion#getPhpTokenType(int)
	 */
	public final String getPhpTokenType(int offset) throws BadLocationException {
		final ITextRegion tokenForOffset = getPhpToken(offset);
		return tokenForOffset == null ? null : tokenForOffset.getType();
	}

	/**
	 * @see IPhpScriptRegion#getPhpToken(int)
	 */
	public final ITextRegion getPhpToken(int offset)
			throws BadLocationException {
		return tokensContaier.getToken(offset);
	}

	/**
	 * @see IPhpScriptRegion#getPhpTokens(int, int)
	 */
	public final ITextRegion[] getPhpTokens(int offset, int length)
			throws BadLocationException {
		return tokensContaier.getTokens(offset, length);
	}

	/**
	 * @throws BadLocationException
	 * @see IPhpScriptRegion#getUpdatedPhpTokens(int, int)
	 */
	public ITextRegion[] getUpdatedPhpTokens() throws BadLocationException {
		if (updatedTokensStart == -1) {
			return null;
		}
		return tokensContaier.getTokens(updatedTokensStart, updatedTokensEnd
				- updatedTokensStart);
	}

	/**
	 * @see IPhpScriptRegion#getPartition(int)
	 */
	public String getPartition(int offset) throws BadLocationException {
		return tokensContaier.getPartitionType(offset);
	}

	/**
	 * @see IPhpScriptRegion#isLineComment(int)
	 */
	public boolean isLineComment(int offset) throws BadLocationException {
		final LexerState lexState = tokensContaier.getState(offset);
		return lexState != null
				&& lexState.getTopState() == ST_PHP_LINE_COMMENT;
	}

	/**
	 * @see IPhpScriptRegion#isFullReparsed()
	 */
	public boolean isFullReparsed() {
		return isFullReparsed;
	}

	/**
	 * @see IPhpScriptRegion#setFullReparsed(boolean)
	 */
	public void setFullReparsed(boolean isFullReparse) {
		isFullReparsed = isFullReparse;
	}

	@Override
	public StructuredDocumentEvent updateRegion(Object requester,
			IStructuredDocumentRegion flatnode, String changes,
			int requestStart, int lengthToReplace) {
		isFullReparsed = true;
		updatedTokensStart = -1;
		try {
			final int offset = requestStart - flatnode.getStartOffset()
					- getStart();

			// support the <?php case
			if (offset < 4) {
				return null;
			}
			// checks for odd quotes
			final String deletedText = lengthToReplace == 0 ? "" : flatnode.getParentDocument().get(requestStart, lengthToReplace); //$NON-NLS-1$
			final int length = changes.length();
			if (startQuoted(deletedText) || startQuoted(changes)) {
				return null;
			}

			synchronized (tokensContaier) {
				// get the region to re-parse
				ITextRegion tokenStart = tokensContaier
						.getToken(offset == 0 ? 0 : offset - 1);
				ITextRegion tokenEnd = tokensContaier.getToken(offset
						+ lengthToReplace);

				// make sure, region to re-parse doesn't start with unknown
				// token
				while (PHPRegionTypes.UNKNOWN_TOKEN
						.equals(tokenStart.getType())
						&& (tokenStart.getStart() > 0)) {
					tokenStart = tokensContaier
							.getToken(tokenStart.getStart() - 1);
				}

				// move sure, region to re-parse doesn't end with unknown token
				while (PHPRegionTypes.UNKNOWN_TOKEN.equals(tokenEnd.getType())
						&& (tokensContaier.getLastToken() != tokenEnd)) {
					tokenEnd = tokensContaier.getToken(tokenEnd.getEnd() + 1);
				}

				boolean shouldDeprecatedKeyword = false;
				int previousIndex = tokensContaier.phpTokens
						.indexOf(tokenStart) - 1;
				if (previousIndex >= 0) {
					ITextRegion previousRegion = tokensContaier.phpTokens
							.get(previousIndex);
					if (PhpTokenContainer.deprecatedKeywordAfter(previousRegion
							.getType())) {
						shouldDeprecatedKeyword = true;
					}
					if (tokenStart.getType().equals(PHPRegionTypes.PHP_COMMENT)
							&& tokenStart.getLength() == 1
							&& previousRegion.getType().equals(
									PHPRegionTypes.PHP_COMMENT_START)) {
						requestStart = previousRegion.getStart();
					}

				}

				int newTokenOffset = tokenStart.getStart();

				if (isHereDoc(tokenStart)) {
					return null;
				}

				// get start and end states
				final LexerState startState = tokensContaier
						.getState(newTokenOffset);
				final LexerState endState = tokensContaier.getState(tokenEnd
						.getEnd() + 1);

				final PhpTokenContainer newContainer = new PhpTokenContainer();
				final AbstractPhpLexer phpLexer = getPhpLexer(
						new DocumentReader(flatnode, changes, requestStart,
								lengthToReplace, newTokenOffset), startState);

				Object state = startState;
				try {
					String yylex = phpLexer.getNextToken();
					if (shouldDeprecatedKeyword
							&& PhpTokenContainer.isKeyword(yylex)) {
						yylex = PHPRegionTypes.PHP_STRING;
					}
					int yylength;
					final int toOffset = offset + length;
					while (yylex != null && newTokenOffset <= toOffset
							&& yylex != PHPRegionTypes.PHP_CLOSETAG) {
						yylength = phpLexer.getLength();
						newContainer.addLast(yylex, newTokenOffset, yylength,
								yylength, state);
						newTokenOffset += yylength;
						state = phpLexer.createLexicalStateMemento();
						yylex = phpLexer.getNextToken();
					}
					if (yylex == PHPRegionTypes.WHITESPACE) {
						yylength = phpLexer.getLength();
						newContainer.adjustWhitespace(yylex, newTokenOffset,
								yylength, yylength, state);
					}
				} catch (IOException e) {
					Logger.logException(e);
				}

				// if the fast reparser couldn't lex - - reparse all
				if (newContainer.isEmpty()) {
					return null;
				}

				// if the two streams end with the same lexer sate -
				// 1. replace the regions
				// 2. adjust next regions start location
				// 3. update state changes
				final int size = length - lengthToReplace;
				final int end = newContainer.getLastToken().getEnd();

				if (!state.equals(endState) || tokenEnd.getEnd() + size != end) {
					return null;
				}

				// 1. replace the regions
				final ListIterator oldIterator = tokensContaier
						.removeTokensSubList(tokenStart, tokenEnd);

				ITextRegion[] newTokens = newContainer.getPhpTokens(); // now,
																		// add
				// the new
				// ones
				for (int i = 0; i < newTokens.length; i++) {
					oldIterator.add(newTokens[i]);
				}

				// 2. adjust next regions start location
				while (oldIterator.hasNext()) {
					final ITextRegion adjust = (ITextRegion) oldIterator.next();
					adjust.adjustStart(size);
				}

				// 3. update state changes
				tokensContaier.updateStateChanges(newContainer,
						tokenStart.getStart(), end);
				updatedTokensStart = tokenStart.getStart();
				updatedTokensEnd = end;
				isFullReparsed = false;
			}

			return super.updateRegion(requester, flatnode, changes,
					requestStart, lengthToReplace);

		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
			return null; // causes to full reparse in this case
		}
	}

	/**
	 * @see IPhpScriptRegion#completeReparse(IDocument, int, int)
	 */
	public void completeReparse(IDocument doc, int start, int length) {
		// bug fix for 225118 we need to refresh the constants since this
		// function is being called
		// after the project's PHP version was changed.
		AbstractPhpLexer phpLexer = getPhpLexer(new BlockDocumentReader(doc,
				start, length), null);
		try {
			ST_PHP_LINE_COMMENT = phpLexer.getClass()
					.getField("ST_PHP_LINE_COMMENT").getInt(phpLexer); //$NON-NLS-1$
			ST_PHP_IN_SCRIPTING = phpLexer.getClass()
					.getField("ST_PHP_IN_SCRIPTING").getInt(phpLexer); //$NON-NLS-1$
		} catch (Exception e) {
			Logger.logException(e);
		}
		completeReparse(phpLexer);
	}

	private synchronized final boolean isHereDoc(final ITextRegion tokenStart) {
		if (tokenStart.getType() == PHPRegionTypes.PHP_TOKEN) {
			try {
				final ITextRegion token = tokensContaier.getToken(tokenStart
						.getStart() - 1);
				return token != null
						&& (token.getType() == PHPRegionTypes.PHP_OPERATOR && token
								.getLength() == 2);
			} catch (BadLocationException e) {
				// never happens
				assert false;
			}
		} else if (tokenStart.getType() == PHPRegionTypes.PHP_STRING) {
			try {
				ITextRegion token = tokensContaier.getToken(tokenStart
						.getStart() - 1);
				if (token != null) {
					token = tokensContaier.getToken(token.getStart() - 1);
					return token != null
							&& (token.getType() == PHPRegionTypes.PHP_OPERATOR && token
									.getLength() == 2);
				}

			} catch (BadLocationException e) {
				// never happens
				assert false;
			}
		}
		return false;
	}

	private boolean startQuoted(final String text) {
		final int length = text.length();
		if (length == 0) {
			return false;
		}

		boolean isOdd = false;
		for (int index = 0; index < length; index++) {
			final char charAt = text.charAt(index);
			if (charAt == '"' || charAt == '\'') {
				isOdd = !isOdd;
			}
		}
		return isOdd;
	}

	/**
	 * Performing a fully parse process to php script
	 * 
	 * @param newText
	 */
	public void completeReparse(AbstractPhpLexer lexer) {
		setPhpTokens(lexer);
	}

	/**
	 * @param project
	 * @param stream
	 * @return a new lexer for the given project with the given stream
	 */
	private AbstractPhpLexer getPhpLexer(Reader stream, LexerState startState) {
		final PHPVersion phpVersion = ProjectOptions.getPhpVersion(project);
		final AbstractPhpLexer lexer = PhpLexerFactory.createLexer(stream,
				phpVersion);
		lexer.initialize(ST_PHP_IN_SCRIPTING);
		lexer.setPatterns(project);

		// set the wanted state
		if (startState != null) {
			startState.restoreState(lexer);
		}
		lexer.setAspTags(ProjectOptions.isSupportingAspTags(project));
		return lexer;
	}

	/**
	 * @param script
	 * @return a list of php tokens
	 */
	private synchronized void setPhpTokens(AbstractPhpLexer lexer) {
		setLength(0);
		setTextLength(0);

		isFullReparsed = true;
		assert lexer != null;

		int start = 0;
		this.tokensContaier.getModelForCreation();
		this.tokensContaier.reset();
		try {
			Object state = lexer.createLexicalStateMemento();
			String yylex = lexer.getNextToken();
			int yylength = 0;
			while (yylex != null && yylex != PHPRegionTypes.PHP_CLOSETAG) {
				yylength = lexer.getLength();
				this.tokensContaier.addLast(yylex, start, yylength, yylength,
						state);
				start += yylength;
				state = lexer.createLexicalStateMemento();
				yylex = lexer.getNextToken();
			}
			adjustLength(start);
			adjustTextLength(start);

		} catch (IOException e) {
			Logger.logException(e);
		} finally {
			this.tokensContaier.releaseModelFromCreation();
		}
	}

	/**
	 * Returns a stream that represents the new text We have three regions: 1)
	 * the php region before the change 2) the change 3) the php region after
	 * the region without the deleted text
	 * 
	 * @param flatnode
	 * @param change
	 * @param requestStart
	 * @param lengthToReplace
	 * @param newTokenOffset
	 */
	private class DocumentReader extends Reader {

		private static final String BAD_LOCATION_ERROR = "Bad location error "; //$NON-NLS-1$

		final private IStructuredDocument parent;
		final private int startPhpRegion;
		final private int endPhpRegion;
		final private int changeLength;
		final private String change;
		final private int requestStart;
		final private int lengthToReplace;

		private int index;
		private int internalIndex = 0;

		public DocumentReader(final IStructuredDocumentRegion flatnode,
				final String change, final int requestStart,
				final int lengthToReplace, final int newTokenOffset) {
			this.parent = flatnode.getParentDocument();
			this.startPhpRegion = flatnode.getStart() + getStart();
			this.endPhpRegion = startPhpRegion + getLength();
			this.changeLength = change.length();
			this.index = startPhpRegion + newTokenOffset;
			this.change = change;
			this.requestStart = requestStart;
			this.lengthToReplace = lengthToReplace;
		}

		@Override
		public int read() throws IOException {
			try {
				// state 1)
				if (index < requestStart) {
					return parent.getChar(index++);
				} // state 2)
				if (internalIndex < changeLength) {
					return change.charAt(internalIndex++);
				}
				// skip the delted text
				if (index < requestStart + lengthToReplace) {
					index = requestStart + lengthToReplace;
				}
				// state 3)
				return index < endPhpRegion ? parent.getChar(index++) : -1;

			} catch (BadLocationException e) {
				throw new IOException(DocumentReader.BAD_LOCATION_ERROR);
			}
		}

		@Override
		public int read(char[] b, int off, int len) throws IOException {
			/**
			 * For boosting performance - Read only 80 characters from the
			 * buffer as the changes are usually small
			 * 
			 * Start of change
			 */
			len = len > 80 ? 80 : len;
			/**
			 * End of change
			 */

			if (b == null) {
				throw new NullPointerException();
			} else if ((off < 0) || (off > b.length) || (len < 0)
					|| ((off + len) > b.length) || ((off + len) < 0)) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}

			int c = read();
			if (c == -1) {
				return -1;
			}
			b[off] = (char) c;

			int i = 1;
			try {
				for (; i < len; i++) {
					c = read();
					if (c == -1) {
						break;
					}
					if (b != null) {
						b[off + i] = (char) c;
					}
				}
			} catch (IOException ee) {
			}
			return i;
		}

		@Override
		public void close() throws IOException {
		}
	}

	/**
	 * Returns a stream that represents the document
	 * 
	 * @param StructuredDocument
	 * @param start
	 * @param length
	 */
	public static class BlockDocumentReader extends Reader {

		private static final String BAD_LOCATION_ERROR = "Bad location error "; //$NON-NLS-1$

		final private IDocument parent;
		private int startPhpRegion;
		final private int endPhpRegion;

		public BlockDocumentReader(final IDocument parent,
				final int startPhpRegion, final int length) {
			this.parent = parent;
			this.startPhpRegion = startPhpRegion;
			this.endPhpRegion = startPhpRegion + length;
		}

		@Override
		public int read() throws IOException {
			try {
				return startPhpRegion < endPhpRegion ? parent
						.getChar(startPhpRegion++) : -1;
			} catch (BadLocationException e) {
				throw new IOException(BAD_LOCATION_ERROR + startPhpRegion);
			}
		}

		@Override
		public int read(char[] b, int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			} else if ((off < 0) || (off > b.length) || (len < 0)
					|| ((off + len) > b.length) || ((off + len) < 0)) {
				throw new IndexOutOfBoundsException();
			} else if (len == 0) {
				return 0;
			}

			int c = read();
			if (c == -1) {
				return -1;
			}
			b[off] = (char) c;

			int i = 1;
			try {
				for (; i < len; i++) {
					c = read();
					if (c == -1) {
						break;
					}
					if (b != null) {
						b[off + i] = (char) c;
					}
				}
			} catch (IOException ee) {
			}
			return i;
		}

		@Override
		public void close() throws IOException {
		}
	}
}
