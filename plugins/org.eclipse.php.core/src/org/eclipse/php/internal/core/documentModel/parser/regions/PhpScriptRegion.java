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

import java.io.IOException;
import java.io.InputStream;
import java.util.ListIterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer4;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexer5;
import org.eclipse.php.internal.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.internal.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
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

	/**
	 * Reparse the PHP editor model
	 * @param newText
	 * @param offset
	 * @param length
	 * @param deletedText
	 * @return true - if short reparse is done, else return false
	 * @throws BadLocationException 
	 */
	public boolean reparse(String change, String newText, final int offset, String deletedText) throws BadLocationException {
		assert newText.length() > offset - 1;

		// checks for odd quotes
		final int length = change.length();
		if (startQuoted(deletedText) || startQuoted(change) || isHereDoc(newText, offset)) {
			return false;
		}

		// get the region to re-parse
		final int deletedLength = deletedText.length();
		final ITextRegion tokenStart = tokensContaier.getToken(offset == 0 ? 0 : offset - 1);
		final int oldEndOffset = offset + deletedLength;
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
			while (yylex != null && newTokenOffset <= toOffset) {
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

		// if the two streams end with the same lexer sate - 
		// 1. replace the regions
		// 2. adjust next regions start location
		// 3. update state changes
		final int size = length - deletedLength;			
		final int end = newContainer.getLastToken().getEnd();
		if (state.equals(endState) && tokenEnd.getEnd() + size == end) {
			// 1. replace the regions
			final ListIterator oldIterator = tokensContaier.removeTokensSubList(tokenStart, tokenEnd);
			ITextRegion[] newTokens = newContainer.getPhpTokens(); // now, add the new ones
			for (int i = 0; i < newTokens.length; i++) {
				oldIterator.add(newTokens[i]);
			}

			// 2. adjust next regions start location
			while (oldIterator.hasNext()) {
				ITextRegion adjust = (ITextRegion) oldIterator.next();
				adjust.adjustStart(size);
			}

			// 3. update state changes
			tokensContaier.updateStateChanges(newContainer, tokenStart.getStart(), end);
			isFullReparsed = false;
			return true;
		}
		return false;
	}
	
	private boolean isHereDoc(final String change, int offset) {
		if (offset == 0) {
			return false;
		}
		return change.charAt(offset - 1) == '<';
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
	 * Performing a fully parse process to php script
	 * @param newText
	 */
	public void completeReparse(String text) {
		setPhpTokens(getPhpLexer(text));
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
	public static PhpLexer getPhpLexer(IProject project, java.io.Reader  reader, char[] buffer, int[] parameters) {
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
		return new InputStream() {
			private int index = start;
			private final int length = text.length();

			public int read() throws IOException {
				return index < length ? text.charAt(index++) : -1;
			}
		};
	}
	
	public PhpLexer getPhpLexer(final String text) {
		return getPhpLexer(project, getStream(text, 0), null);
	}
	
}
