/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016, 2017 IBM Corporation and others.
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
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
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
	private static final ITextRegion[] EMPTY_REGION = new ITextRegion[0];
	private PhpTokenContainer tokensContainer = new PhpTokenContainer();
	private IProject project;
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=510509
	// When a project php version changes,
	// there seem to be a very small race window where PhpScriptRegion objects
	// can be updated through updateRegion() with the new project php version
	// *BEFORE* PHPStructuredEditor.fPhpVersionListener was notified to call
	// completeReparse() to apply the project php version change.
	// In this case a PhpScriptRegion object will contain tokens and lexer
	// states for 2 different php versions!
	// We introduce "currentPhpVersion" to check if updateRegion() is called
	// with unchanged project php version and force a call to completeReparse()
	// when necessary (to renew the "tokensContainer" content).
	PHPVersion currentPhpVersion;
	private int updatedTokensStart = -1;
	private int updatedTokensEnd = -1;

	public int getUpdatedTokensStart() {
		if (updatedTokensStart == -1) {
			return 0;
		}
		return updatedTokensStart;
	}

	public int getUpdatedTokensLength() {
		return updatedTokensEnd - updatedTokensStart;
	}

	private int inScriptingState;
	private int[] phpQuotesStates;
	private int[] heredocStates;

	// true when the last reparse action is full reparse
	protected boolean isFullReparsed;

	public PhpScriptRegion(String newContext, int startOffset, @Nullable IProject project,
			@NonNull AbstractPhpLexer phpLexer) {
		super(newContext, startOffset, 0, 0, PhpScriptRegion.PHP_SCRIPT);

		this.project = project;
		currentPhpVersion = ProjectOptions.getPHPVersion(this.project);
		// must be done by the caller when phpLexer is newly created or when it
		// was used on a different project:
		// phpLexer.setAspTags(ProjectOptions.isSupportingAspTags(project));

		// these values are specific to each PHP version lexer
		inScriptingState = phpLexer.getInScriptingState();
		phpQuotesStates = phpLexer.getPhpQuotesStates();
		heredocStates = phpLexer.getHeredocStates();

		completeReparse(phpLexer);
	}

	/**
	 * @see IPhpScriptRegion#getPhpTokenType(int)
	 */
	public final @NonNull String getPhpTokenType(int offset) throws BadLocationException {
		final ITextRegion tokenForOffset = getPhpToken(offset);
		return tokenForOffset.getType();
	}

	/**
	 * @see IPhpScriptRegion#getPhpToken(int)
	 */
	public final @NonNull ITextRegion getPhpToken(int offset) throws BadLocationException {
		return tokensContainer.getToken(offset);
	}

	/**
	 * @see IPhpScriptRegion#getPhpTokens(int, int)
	 */
	public final @NonNull ITextRegion[] getPhpTokens(int offset, int length) throws BadLocationException {
		return tokensContainer.getTokens(offset, length);
	}

	/**
	 * @throws BadLocationException
	 * @see IPhpScriptRegion#getUpdatedPhpTokens(int, int)
	 */
	public @NonNull ITextRegion[] getUpdatedPhpTokens() throws BadLocationException {
		if (updatedTokensStart == -1) {
			return EMPTY_REGION;
		}
		return tokensContainer.getTokens(updatedTokensStart, updatedTokensEnd - updatedTokensStart);
	}

	/**
	 * @see IPhpScriptRegion#getPartition(int)
	 */
	public @NonNull String getPartition(int offset) throws BadLocationException {
		return tokensContainer.getPartitionType(offset);
	}

	protected boolean isHeredocState(int offset) throws BadLocationException {
		String type = getPhpTokenType(offset);
		// First, check if current type is a known "heredoc/nowdoc" type.
		if (type == PHPRegionTypes.PHP_HEREDOC_START_TAG || type == PHPRegionTypes.PHP_HEREDOC_CLOSE_TAG
				|| type == PHPRegionTypes.PHP_NOWDOC_START_TAG || type == PHPRegionTypes.PHP_NOWDOC_CLOSE_TAG) {
			return true;
		}
		// If not, it means that maybe we are "deeper" in the stack, in an
		// encapsed variable for example, or maybe simply in the "text" part of
		// a heredoc/nowdoc section.
		// Also note that the states PHP_HEREDOC_START_TAG and
		// PHP_NOWDOC_START_TAG are NOT put on the lexer substates stack
		// (because the way the lexers actually work), but previous type tests
		// will be enough to catch them.
		LexerState lexState = tokensContainer.getState(offset);
		if (lexState == null) {
			return false;
		}
		for (int state : heredocStates) {
			if (lexState.isSubstateOf(state)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see IPhpScriptRegion#isPhpQuotesState(int)
	 */
	public boolean isPhpQuotesState(int offset) throws BadLocationException {
		String type = getPhpTokenType(offset);
		// First, check if current type is a known "quoted" type.
		if (PHPPartitionTypes.isPhpQuotesState(type)) {
			return true;
		}
		// If not, it means that maybe we are "deeper" in the stack, in an
		// encapsed variable for example.
		// Also note that the states PHP_HEREDOC_START_TAG and
		// PHP_NOWDOC_START_TAG are NOT put on the lexer substates stack
		// (because the way the lexers actually work), but
		// PHPPartitionTypes.isPhpQuotesState(type) will be enough to catch
		// them.
		LexerState lexState = tokensContainer.getState(offset);
		if (lexState == null) {
			return false;
		}
		for (int state : phpQuotesStates) {
			if (lexState.isSubstateOf(state)) {
				return true;
			}
		}
		return false;
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
	public StructuredDocumentEvent updateRegion(Object requester, IStructuredDocumentRegion flatnode, String changes,
			int requestStart, int lengthToReplace) {
		isFullReparsed = true;
		updatedTokensStart = -1;
		updatedTokensEnd = -1;
		try {
			final int offset = requestStart - flatnode.getStartOffset() - getStart();

			// support the <?php case
			if (offset < 4) {
				return null;
			}
			// checks for odd quotes
			final String deletedText = lengthToReplace == 0 ? "" //$NON-NLS-1$
					: flatnode.getParentDocument().get(requestStart, lengthToReplace);
			final int length = changes.length();
			if (startQuoted(deletedText) || startQuoted(changes)) {
				return null;
			}

			synchronized (tokensContainer) {
				if (ProjectOptions.getPHPVersion(project) != currentPhpVersion) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=510509
					// force full reparse
					return null;
				}

				// get the region to re-parse
				ITextRegion tokenStart = tokensContainer.getToken(offset == 0 ? 0 : offset - 1);
				ITextRegion tokenEnd = tokensContainer.getToken(offset + lengthToReplace);

				// make sure, region to re-parse doesn't start with unknown
				// token
				while (PHPRegionTypes.UNKNOWN_TOKEN.equals(tokenStart.getType()) && (tokenStart.getStart() > 0)) {
					tokenStart = tokensContainer.getToken(tokenStart.getStart() - 1);
				}

				int newTokenOffset = tokenStart.getStart();

				if (isHeredocState(newTokenOffset)) {
					// https://bugs.eclipse.org/bugs/show_bug.cgi?id=498525
					// Fully re-parse when we're in a heredoc/nowdoc section.
					// NB: it's much easier and safer to use the lexer state to
					// determine if we're in a heredoc/nowdoc section,
					// using PHPRegionTypes make us depend on how each PHP
					// lexer version analyzes the heredoc/nowdoc content.
					// In the same way, PHPPartitionTypes.isPhpQuotesState(type)
					// cannot be used here because it's not exclusive to
					// heredoc/nowdoc sections.
					return null;
				}
				if (isMaybeStartingNewHeredocSection(tokenStart)) {
					// In case a user is (maybe) starting to write a brand new
					// heredoc/nowdoc section in a php document, we should fully
					// re-parse the document to update the lexer to
					// distinguish "<<" bitwise shift operators from "<<" and
					// "<<<" operators that are followed by a label.
					// This also allows highlighters to correctly detect and
					// highlight opening and closing heredoc/nowdoc tags asap.
					return null;
				}

				// make sure, region to re-parse doesn't end with unknown token
				while (PHPRegionTypes.UNKNOWN_TOKEN.equals(tokenEnd.getType())
						&& (tokensContainer.getLastToken() != tokenEnd)) {
					tokenEnd = tokensContainer.getToken(tokenEnd.getEnd());
				}

				boolean shouldDeprecatedKeyword = false;
				int previousIndex = tokensContainer.phpTokens.indexOf(tokenStart) - 1;
				if (previousIndex >= 0) {
					ITextRegion previousRegion = tokensContainer.phpTokens.get(previousIndex);
					if (PhpTokenContainer.deprecatedKeywordAfter(previousRegion.getType())) {
						shouldDeprecatedKeyword = true;
					}
					if (PHPPartitionTypes.isPHPMultiLineCommentRegion(tokenStart.getType())
							&& tokenStart.getLength() == 1
							&& PHPPartitionTypes.isPHPMultiLineCommentStartRegion(previousRegion.getType())) {
						requestStart = previousRegion.getStart();
					}
				}

				// get start and end states
				final LexerState startState = tokensContainer.getState(newTokenOffset);
				final LexerState endState = tokensContainer.getState(tokenEnd.getEnd());

				assert startState != null && endState != null;

				final PhpTokenContainer newContainer = new PhpTokenContainer();
				final AbstractPhpLexer phpLexer = getPhpLexer(
						new DocumentReader(flatnode, changes, requestStart, lengthToReplace, newTokenOffset),
						startState, currentPhpVersion);

				LexerState state = startState;
				try {
					String yylex = phpLexer.getNextToken();
					if (shouldDeprecatedKeyword && PhpTokenContainer.isKeyword(yylex)) {
						yylex = PHPRegionTypes.PHP_LABEL;
					}
					int yylength;
					final int toOffset = offset + length;
					while (yylex != null && newTokenOffset <= toOffset && yylex != PHPRegionTypes.PHP_CLOSETAG) {
						yylength = phpLexer.getLength();
						newContainer.addLast(yylex, newTokenOffset, yylength, yylength, state);
						newTokenOffset += yylength;
						state = phpLexer.createLexicalStateMemento();
						yylex = phpLexer.getNextToken();
					}
					if (yylex == PHPRegionTypes.WHITESPACE) {
						yylength = phpLexer.getLength();
						newContainer.adjustWhitespace(yylex, newTokenOffset, yylength, yylength, state);
					}
				} catch (IOException e) {
					Logger.logException(e);
				}

				// if the fast reparser couldn't lex - - reparse all
				if (newContainer.isEmpty()) {
					return null;
				}

				// if the two streams end with the same lexer state -
				// 1. replace the regions
				// 2. adjust next regions start location
				// 3. update state changes
				final int size = length - lengthToReplace;
				final int end = newContainer.getLastToken().getEnd();

				if ((state != null && !state.equals(endState)) || tokenEnd.getEnd() + size != end) {
					return null;
				}

				// 1. replace the regions
				final ListIterator oldIterator = tokensContainer.removeTokensSubList(tokenStart, tokenEnd);

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
				tokensContainer.updateStateChanges(newContainer, tokenStart.getStart(), end);
				updatedTokensStart = tokenStart.getStart();
				updatedTokensEnd = end;
				isFullReparsed = false;
			}

			return super.updateRegion(requester, flatnode, changes, requestStart, lengthToReplace);

		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
			return null; // causes to full reparse in this case
		}
	}

	/**
	 * @see IPhpScriptRegion#completeReparse(IDocument, int, int)
	 */
	public synchronized void completeReparse(IDocument doc, int start, int length) {
		completeReparse(doc, start, length, project);
	}

	/**
	 * @see IPhpScriptRegion#completeReparse(IDocument, int, int, IProject)
	 */
	public synchronized void completeReparse(IDocument doc, int start, int length, @Nullable IProject project) {
		this.project = project;
		currentPhpVersion = ProjectOptions.getPHPVersion(this.project);
		// bug fix for 225118 we need to refresh the constants since this
		// function is being called
		// after the project's PHP version was changed.
		AbstractPhpLexer phpLexer = getPhpLexer(new BlockDocumentReader(doc, start, length), null, currentPhpVersion);

		// these values are specific to each PHP version lexer
		inScriptingState = phpLexer.getInScriptingState();
		phpQuotesStates = phpLexer.getPhpQuotesStates();
		heredocStates = phpLexer.getHeredocStates();

		completeReparse(phpLexer);
	}

	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=464489
	// Workaround for bug 464489:
	// this method is called by the SSE framework when an existing region
	// content has to be updated using a new one.
	// The SSE framework doesn't know that this class also contains additional
	// "region-is-splitted-into-tokens" informations (as PhpScriptRegion doesn't
	// implement ITextRegionCollection), so we have to manually keep those
	// additional internal variables in sync with the new position informations.
	// See also StructuredDocumentReParser#isCollectionRegion(ITextRegion
	// aRegion), StructuredDocumentReParser#swapNewForOldRegion(...) and
	// StructuredDocumentReParser#transferEmbeddedRegions(...).
	@Override
	public void equatePositions(ITextRegion region) {
		super.equatePositions(region);
		if (region instanceof PhpScriptRegion) {
			PhpScriptRegion sRegion = (PhpScriptRegion) region;
			// XXX: we clone the tokens container for more safety (but it's not
			// a deep copy, because tokens and lexer state changes are not
			// cloned)
			this.tokensContainer = (PhpTokenContainer) sRegion.tokensContainer.clone();
			this.project = sRegion.project;
			this.currentPhpVersion = sRegion.currentPhpVersion;
			this.updatedTokensStart = sRegion.updatedTokensStart;
			this.updatedTokensEnd = sRegion.updatedTokensEnd;
			this.inScriptingState = sRegion.inScriptingState;
			this.phpQuotesStates = sRegion.phpQuotesStates;
			this.heredocStates = sRegion.heredocStates;
			this.isFullReparsed = sRegion.isFullReparsed;
		}
	}

	private synchronized final boolean isMaybeStartingNewHeredocSection(final ITextRegion tokenStart) {
		if (tokenStart.getType() == PHPRegionTypes.PHP_TOKEN) {
			try {
				final ITextRegion token = tokensContainer.getToken(tokenStart.getStart() - 1);
				// lexer has maybe found the "<<" bitwise shift operator
				return token.getType() == PHPRegionTypes.PHP_OPERATOR && token.getLength() == 2;
			} catch (BadLocationException e) {
				// never happens
				assert false;
			}
		} else if (tokenStart.getType() == PHPRegionTypes.PHP_LABEL) {
			try {
				ITextRegion token = tokensContainer.getToken(tokenStart.getStart() - 1);
				token = tokensContainer.getToken(token.getStart() - 1);
				// lexer has maybe found the "<<" bitwise shift operator
				return token.getType() == PHPRegionTypes.PHP_OPERATOR && token.getLength() == 2;
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
	private void completeReparse(@NonNull AbstractPhpLexer lexer) {
		setPhpTokens(lexer);
	}

	/**
	 * @param project
	 * @param stream
	 * @param startState
	 * @param phpVersion
	 * @return a new lexer for the given php version with the given stream
	 */
	private AbstractPhpLexer getPhpLexer(Reader stream, LexerState startState, PHPVersion phpVersion) {
		final AbstractPhpLexer lexer = PhpLexerFactory.createLexer(stream, phpVersion);
		lexer.initialize(inScriptingState);

		// set the wanted state
		if (startState != null) {
			startState.restoreState(lexer);
		}
		lexer.setAspTags(ProjectOptions.isSupportingASPTags(project));
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
		this.tokensContainer.getModelForCreation();
		this.tokensContainer.reset();
		try {
			LexerState state = lexer.createLexicalStateMemento();
			String yylex = lexer.getNextToken();
			int yylength = 0;
			while (yylex != null && yylex != PHPRegionTypes.PHP_CLOSETAG) {
				yylength = lexer.getLength();
				this.tokensContainer.addLast(yylex, start, yylength, yylength, state);
				start += yylength;
				state = lexer.createLexicalStateMemento();
				yylex = lexer.getNextToken();
			}
			adjustLength(start);
			adjustTextLength(start);

		} catch (IOException e) {
			Logger.logException(e);
		} finally {
			this.tokensContainer.releaseModelFromCreation();
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

		public DocumentReader(final IStructuredDocumentRegion flatnode, final String change, final int requestStart,
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
			} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
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
					b[off + i] = (char) c;
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

		public BlockDocumentReader(final IDocument parent, final int startPhpRegion, final int length) {
			this.parent = parent;
			this.startPhpRegion = startPhpRegion;
			this.endPhpRegion = startPhpRegion + length;
		}

		@Override
		public int read() throws IOException {
			try {
				return startPhpRegion < endPhpRegion ? parent.getChar(startPhpRegion++) : -1;
			} catch (BadLocationException e) {
				throw new IOException(BAD_LOCATION_ERROR + startPhpRegion);
			}
		}

		@Override
		public int read(char[] b, int off, int len) throws IOException {
			if (b == null) {
				throw new NullPointerException();
			} else if ((off < 0) || (off > b.length) || (len < 0) || ((off + len) > b.length) || ((off + len) < 0)) {
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
					b[off + i] = (char) c;
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
