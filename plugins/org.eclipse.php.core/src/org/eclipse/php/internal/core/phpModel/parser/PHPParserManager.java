/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.phpModel.parser;

import java.io.Reader;
import java.util.regex.Pattern;

import java_cup.runtime.Scanner;

public abstract class PHPParserManager {

	protected abstract CompletionLexer createCompletionLexer(Reader reader);

	protected abstract PhpParser createPhpParser(Scanner lexer);

	/**
	 * Parse now is used by the PHP builders - there is no schedule or delay process
	 * @param reader
	 * @param fileName
	 * @param lastModified
	 * @param client
	 * @param tasksPatterns
	 * @param useAspTagsAsPhp
	 */
	public void parseNow(Reader reader, String fileName, long lastModified, ParserClient client, Pattern[] tasksPatterns, boolean useAspTagsAsPhp) {
		final ParserExecuter parserExecuter = new ParserExecuter(this, client, fileName, reader, tasksPatterns, lastModified, useAspTagsAsPhp);
		parserExecuter.run();
	}
}