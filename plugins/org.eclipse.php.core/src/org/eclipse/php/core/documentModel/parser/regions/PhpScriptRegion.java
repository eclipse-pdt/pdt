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
package org.eclipse.php.core.documentModel.parser.regions;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.documentModel.parser.PhpLexer;
import org.eclipse.php.core.documentModel.parser.PhpLexer4;
import org.eclipse.php.core.documentModel.parser.PhpLexer5;
import org.eclipse.php.core.documentModel.parser.Scanner.LexerState;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
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

	private final PhpTokenContainer tokensContaier = new PhpTokenContainer();
	private PhpLexer lexer;
	private final IProject project;

	public PhpScriptRegion(String newContext, int newStart, int newTextLength, int newLength, final String initialScript, IProject project) {
		super(newContext, newStart, newTextLength, newLength, "PHP Script");

		this.project = project;
		completeReparse(initialScript);
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
	 * Reparse the PHP editor model
	 * @param newText
	 * @param offset
	 * @param length
	 * @param deletedText
	 * @throws BadLocationException 
	 */
	public void reparse(String newText, final int offset, final int length, String deletedText) throws BadLocationException {
		assert newText.length() > offset - 1;

		// get the region to re-parse
		final int deletedLength = deletedText.length();
		final ITextRegion tokenStart = tokensContaier.getToken(offset == 0 ? 0 : offset - 1);
		final int oldEndOffset = offset + deletedLength;
		final ITextRegion tokenEnd = tokensContaier.getToken(oldEndOffset);
		int newTokenOffset = tokenStart.getStart();

		// get start and end states
		final LexerState startState = tokensContaier.getState(newTokenOffset);
		final LexerState endState = tokensContaier.getState(tokenEnd.getEnd());

		final PhpTokenContainer newContainer = new PhpTokenContainer();
		final PhpLexer phpLexer = getPhpLexer(project, getStream(newText, newTokenOffset), startState);
		try {
			Object state = startState;
			String yylex = phpLexer.yylex();
			int yylength;
			final int toOffset = Math.max(offset + length, tokenEnd.getEnd());
			while (yylex != null && newTokenOffset <= toOffset) {
				yylength = phpLexer.yylength();
				newContainer.addLast(yylex, newTokenOffset, yylength, yylength, state);
				newTokenOffset += yylength;
				state = phpLexer.createLexicalStateMemento();
				yylex = phpLexer.yylex();
			}
		} catch (IOException e) {
			Logger.logException(e);
		}

		// if the two streams end with the same lexer sate - 
		// 1. replace the regions
		// 2. adjust next regions start location
		// 3. update state changes
		final int size = length - deletedLength;
		if (phpLexer.yystate() == endState.getTopState() && newContainer.getLastToken().getEnd() <= tokenEnd.getEnd() + size) {
			// 1. replace the regions
			final ListIterator oldIterator = tokensContaier.removeSubList(tokenStart, tokenEnd);
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
			tokensContaier.updateStateChanges(newContainer, tokenStart.getStart(), oldEndOffset);

		} else {
			completeReparse(newText);
		}
	}

	/**
	 * Performing a fully parse process to php script
	 * @param newText
	 */
	private void completeReparse(String newText) {
		this.lexer = getPhpLexer(project, getStream(newText));
		setPhpTokens();
	}

	/**
	 * @param project
	 * @param stream
	 * @return a new lexer for the given project with the given stream
	 */
	private PhpLexer getPhpLexer(IProject project, InputStream stream, LexerState startState) {
		PhpLexer lexer;
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			lexer = new PhpLexer5(stream);
		} else {
			lexer = new PhpLexer4(stream);
		}
		lexer.initialize(PhpLexer.ST_PHP_IN_SCRIPTING);
		
		// set the wanted state
		if (startState != null) {
			startState.restoreState(lexer);
		}
		lexer.setAspTags(UseAspTagsHandler.useAspTagsAsPhp(project));
		return lexer;
	}

	private PhpLexer getPhpLexer(IProject project, InputStream stream) {
		return getPhpLexer(project, stream, null);
	}

	/**
	 * @param script 
	 * @return a list of php tokens
	 */
	private void setPhpTokens() {
		assert lexer != null;

		int start = 0;
		this.tokensContaier.getModelForCreation();
		this.tokensContaier.reset();
		try {
			Object state = lexer.createLexicalStateMemento();
			String yylex = lexer.yylex();
			int yylength;
			while (yylex != null) {
				yylength = lexer.yylength();
				this.tokensContaier.addLast(yylex, start, yylength, yylength, state);
				start += yylength;
				state = lexer.createLexicalStateMemento();
				yylex = lexer.yylex();
			}
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
	private final InputStream getStream(final String text, final int start) {
		return new InputStream() {
			private int index = start;
			private final int length = text.length();

			public int read() throws IOException {
				return index < length ? text.charAt(index++) : -1;
			}
		};
	}

	private final InputStream getStream(final String initialScript) {
		return getStream(initialScript, 0);
	}

	/**
	 *
	 * TODO: shold be re-write to find todo list
	 * 
	 * 
	 * 
	 */

	private void checkForTodo(String token, int commentStart, int commentLength, String comment) {
		ArrayList matchers = createMatcherList(comment);
		int startPosition = 0;

		Matcher matcher = getMinimalMatcher(matchers, startPosition);
		ITextRegion tRegion = null;
		while (matcher != null) {
			int startIndex = matcher.start();
			int endIndex = matcher.end();
			if (startIndex != startPosition) {
				tRegion = null; // new PHPContentRegion(commentStart + startPosition, startIndex - startPosition, startIndex - startPosition, token);
				// storedPhpTokens.add(tRegion);
			}
			tRegion = new ContextRegion(PHPRegionTypes.TASK, commentStart + startIndex, endIndex - startIndex, endIndex - startIndex);
			//			storedPhpTokens.add(tRegion);
			startPosition = endIndex;
			matcher = getMinimalMatcher(matchers, startPosition);
		}
		tRegion = new ContextRegion(token, commentStart + startPosition, commentLength - startPosition, commentLength - startPosition);
		//		storedPhpTokens.add(tRegion);
	}

	private ArrayList createMatcherList(String content) {
		Pattern[] todos = null;
		//		if (project != null) {
		//			todos = TaskPatternsProvider.getInstance().getPatternsForProject(project);
		//		} else {
		//			todos = TaskPatternsProvider.getInstance().getPetternsForWorkspace();
		//		}
		ArrayList list = new ArrayList(todos.length);
		for (int i = 0; i < todos.length; i++) {
			list.add(i, todos[i].matcher(content));
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
}
