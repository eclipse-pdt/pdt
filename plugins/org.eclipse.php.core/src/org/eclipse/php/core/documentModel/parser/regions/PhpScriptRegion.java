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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.documentModel.parser.PhpLexer;
import org.eclipse.php.core.documentModel.parser.PhpLexer4;
import org.eclipse.php.core.documentModel.parser.PhpLexer5;
import org.eclipse.php.core.project.properties.handlers.PhpVersionProjectPropertyHandler;
import org.eclipse.php.core.project.properties.handlers.UseAspTagsHandler;
import org.eclipse.wst.sse.core.internal.parser.ForeignRegion;
import org.eclipse.wst.sse.core.internal.provisional.events.StructuredDocumentEvent;
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

	public final PhpTokenContainer tokensContaier = new PhpTokenContainer();
	private PhpLexer lexer;
	private final IProject project;

	public PhpScriptRegion(String newContext, int newStart, int newTextLength, int newLength, final String initialScript, IProject project) {
		super(newContext, newStart, newTextLength, newLength, "PHP Script");

		this.project = project;
		this.lexer = getPhpLexer(project, getStream(initialScript));		
		setPhpTokens();
	}

	/**
	 * returns the php token type in offset 
	 * @param offset
	 */
	public final String getPhpTokenType(int offset) {
		final ITextRegion tokenForOffset = getPhpToken(offset);
		return tokenForOffset == null ? null : tokenForOffset.getType();
	}

	/**
	 * returns the php token in offset 
	 * @param offset
	 */
	public final ITextRegion getPhpToken(int offset) {
		return tokensContaier.getTokenForOffset(offset);
	}

	/**
	 * @param project
	 * @param stream
	 * @return a new lexer for the given project with the given stream
	 */
	private PhpLexer getPhpLexer(IProject project, InputStream stream) {
		PhpLexer lexer;
		final String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
		if (phpVersion.equals(PHPCoreConstants.PHP5)) {
			lexer = new PhpLexer5(stream);
			lexer.initialize(PhpLexer5.ST_PHP_IN_SCRIPTING);
		} else {
			lexer = new PhpLexer4(stream);
			lexer.initialize(PhpLexer4.ST_PHP_IN_SCRIPTING);
		}
		lexer.setAspTags(UseAspTagsHandler.useAspTagsAsPhp(project));
		return lexer;
	}

	/**
	 * @param script 
	 * @return a list of php tokens
	 */
	private void setPhpTokens() {
		assert lexer != null;
		
		int start = 0;
		this.tokensContaier.clear();
		try {
			String yylex = lexer.yylex();
			while (yylex != null) {
				final int yylength = lexer.yylength();
				this.tokensContaier.pushToken(new PHPContentRegion(start, yylength, yylength, yylex), lexer.yystate());
				start += yylength;
				yylex = lexer.yylex();				
			}
		} catch (IOException e) {
			Logger.logException(e);
		}
	}

	/**
	 * Converts the streing to stream that at the end we have EOF (-1)
	 * @param initialScript
	 */
	private final InputStream getStream(final String initialScript) {
		return new InputStream() {
			private int index = 0;
			private final int length = initialScript.length();

			public int read() throws IOException {
				return index < length ? initialScript.charAt(index++) : -1;
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.core.internal.parser.ForeignRegion#updateRegion(java.lang.Object, org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion, java.lang.String, int, int)
	 */
	public StructuredDocumentEvent updateRegion(Object requester, IStructuredDocumentRegion flatnode, String changes, int requestStart, int lengthToReplace) {
		return super.updateRegion(requester, flatnode, changes, requestStart, lengthToReplace);
	}

	public void reparse(String newText) {
		this.lexer = getPhpLexer(project, getStream(newText));
		setPhpTokens();				
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
				tRegion = new PHPContentRegion(commentStart + startPosition, startIndex - startPosition, startIndex - startPosition, token);
				// storedPhpTokens.add(tRegion);
			}
			tRegion = new PHPContentRegion(commentStart + startIndex, endIndex - startIndex, endIndex - startIndex, PHPRegionTypes.TASK);
//			storedPhpTokens.add(tRegion);
			startPosition = endIndex;
			matcher = getMinimalMatcher(matchers, startPosition);
		}
		tRegion = new PHPContentRegion(commentStart + startPosition, commentLength - startPosition, commentLength - startPosition, token);
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
