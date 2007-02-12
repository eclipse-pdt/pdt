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

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer4;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer5;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.core.internal.Logger;

/**
 * Description: This text region is a PHP foreign region that includes the php tokens *
 * In order to know that this text region is PhpScript one should use:
 * <code> if (region.getType() == PHPRegionContext())  { (PhpScriptRegion) region } </code>   
 * @author Roy, 2007
 */
public class PhpScriptRegion extends ForeignRegion {

	private static final String PHP_SCRIPT = "PHP Script";
	private final PhpTokenContainer tokensContaier = new PhpTokenContainer();
	private final IProject project;

	// true when the last reparse action is full reparse
	public boolean isFullReparsed;

	public PhpScriptRegion(String newContext, int startOffset, IProject project, PhpLexer phpLexer) {
		super(newContext, startOffset, 0, 0, PhpScriptRegion.PHP_SCRIPT);

		this.project = project;
		completeReparse(phpLexer);
	}

	/**
	 * returns the php token type in the given offset 
	 * @param offset
	 * @throws BadLocationException 
	 */
	public final String getPhpTokenType(int offset) throws BadLocationException {
		final ITextRegion tokenForOffset = getPhpToken(offset);
		return tokenForOffset == null ? null : tokenForOffset.getType();
	}

	/**
	 * returns the php token in the given offset
	 * @param offset
	 * @throws BadLocationException 
	 */
	public final ITextRegion getPhpToken(int offset) throws BadLocationException {
		return tokensContaier.getToken(offset);
	}

	/**
	 * returns the php token in the given offset
	 * @param offset
	 * @throws BadLocationException 
	 */
	public final ITextRegion[] getPhpTokens(int offset, int length) throws BadLocationException {
		return tokensContaier.getTokens(offset, length);
	}

	/**
	 * @param offset
	 * @return the internal partition of the given offset
	 * @throws BadLocationException
	 */
	public String getPartition(int offset) throws BadLocationException {
		return tokensContaier.getPartitionType(offset);
	}

	/**
	 *  
	 * @param offset
	 * @return true if the given offset is in line comment
	 * @throws BadLocationException
	 */
	public boolean isLineComment(int offset) throws BadLocationException {
		final LexerState lexState = tokensContaier.getState(offset);
		return lexState != null && lexState.getTopState() == PhpLexer.ST_PHP_LINE_COMMENT;
	}

	public StructuredDocumentEvent updateRegion(Object requester, IStructuredDocumentRegion flatnode, String changes, int requestStart, int lengthToReplace) {
		try {
			final String newText = getNewRegionText(flatnode, changes, requestStart, lengthToReplace);

			final int offset = requestStart - flatnode.getStartOffset() - getStart();
			assert newText.length() > offset - 1;

			// support the <?php case
			if (offset < 3) {
				return null;
			}
			
			// checks for odd quotes
			final String deletedText = lengthToReplace == 0 ? "" : flatnode.getParentDocument().get(requestStart, lengthToReplace);
			final int length = changes.length();
			if (startQuoted(deletedText) || startQuoted(changes) || isHereDoc(newText, offset)) {
				return null;
			}

			// get the region to re-parse
			final ITextRegion tokenStart = tokensContaier.getToken(offset == 0 ? 0 : offset - 1);
			final int oldEndOffset = offset + lengthToReplace;
			final ITextRegion tokenEnd = tokensContaier.getToken(oldEndOffset);
			int newTokenOffset = tokenStart.getStart();

			// get start and end states
			final LexerState startState = tokensContaier.getState(newTokenOffset);
			final LexerState endState = tokensContaier.getState(tokenEnd.getEnd() + 1);

			final PhpTokenContainer newContainer = new PhpTokenContainer();
			final PhpLexer phpLexer = getPhpLexer(project, getStream(newText, newTokenOffset), startState);

			Object state = startState;
			try {
				String yylex = phpLexer.getNextToken();
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
			final ListIterator oldIterator = tokensContaier.removeTokensSubList(tokenStart, tokenEnd);
			ITextRegion[] newTokens = newContainer.getPhpTokens(); // now, add the new ones
			for (int i = 0; i < newTokens.length; i++) {
				oldIterator.add(newTokens[i]);
			}

			// 2. adjust next regions start location
			while (oldIterator.hasNext()) {
				final ITextRegion adjust = (ITextRegion) oldIterator.next();
				adjust.adjustStart(size);
			}

			// 3. update state changes
			tokensContaier.updateStateChanges(newContainer, tokenStart.getStart(), end);
			isFullReparsed = false;

			return super.updateRegion(requester, flatnode, changes, requestStart, lengthToReplace);

		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
			return null; // causes to full reparse in this case
		}
	}

	protected String getNewRegionText(IStructuredDocumentRegion flatnode, String changes, int requestStart, int lengthToReplace) throws BadLocationException {
		final int start = flatnode.getStart() + getStart();
		final IStructuredDocument parentDocument = flatnode.getParentDocument();
		final StringBuffer buffer = new StringBuffer(parentDocument.get(start, requestStart - start));
		buffer.append(changes);
		buffer.append(parentDocument.get(requestStart + lengthToReplace, start + getLength() - requestStart - lengthToReplace));
		return buffer.toString();
	}

	private boolean isHereDoc(final String change, final int offset) {
		return offset > 2 ? change.charAt(offset - 1) == '<' : false; 
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
	 * @param newText
	 */
	public void completeReparse(PhpLexer lexer) {
		setPhpTokens(lexer);
	}

	/**
	 * @param project
	 * @param stream
	 * @return a new lexer for the given project with the given stream
	 */
	private static PhpLexer getPhpLexer(IProject project, InputStream stream, LexerState startState) {
		PhpLexer lexer;
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			lexer = new PhpLexer5(stream);
		} else {
			lexer = new PhpLexer4(stream);
		}
		lexer.initialize(PhpLexer.ST_PHP_IN_SCRIPTING);
		lexer.setPatterns(project);

		// set the wanted state
		if (startState != null) {
			startState.restoreState(lexer);
		}
		lexer.setAspTags(UseAspTagsHandler.useAspTagsAsPhp(project));
		return lexer;
	}

	/**
	 * @param project
	 * @param stream
	 * @return a new lexer for the given project with the given stream
	 */
	public static PhpLexer getPhpLexer(IProject project, java.io.Reader reader, char[] buffer, int[] parameters) {
		PhpLexer lexer;
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			lexer = new PhpLexer5(reader);
		} else {
			lexer = new PhpLexer4(reader);
		}
		lexer.initialize(parameters[6]);
		lexer.reset(reader, buffer, parameters);
		lexer.setPatterns(project);

		lexer.setAspTags(UseAspTagsHandler.useAspTagsAsPhp(project));
		return lexer;
	}

	public static PhpLexer getPhpLexer(IProject project, InputStream stream) {
		return getPhpLexer(project, stream, null);
	}

	/**
	 * @param script 
	 * @return a list of php tokens
	 */
	private void setPhpTokens(PhpLexer lexer) {
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
				this.tokensContaier.addLast(yylex, start, yylength, yylength, state);
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
	 * Converts the streing to stream that at the end we have EOF (-1)
	 * @param initialScript
	 */
	private final static InputStream getStream(final String text, final int start) {
		return new DataInputStream(new InputStream() {
			private int index = start;
			private final int length = text.length();

			public int read() {
				return index < length ? text.charAt(index++) : -1;
			}
		});
	}
}
