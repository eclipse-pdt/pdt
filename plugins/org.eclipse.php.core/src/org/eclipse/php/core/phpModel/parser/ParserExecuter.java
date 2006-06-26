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
package org.eclipse.php.core.phpModel.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

import org.eclipse.php.core.Logger;

/**
 * This class handles the execution of a parser
 * @author Roy Ganor, 2006
 */
public final class ParserExecuter implements Runnable {

	public  final PHPParserManager parserManager;
	public  final ParserClient client;
	public  final String filename;
	private final Reader reader;
	public  final long lastModified;
	public  final Pattern[] tasksPatterns;
	public  final boolean useAspTagsAsPhp;
	private PhpParser phpParser; // maybe we should re-create the parser

	public ParserExecuter(PHPParserManager parserManager, PhpParser phpParser, ParserClient client, String filename, Reader reader, Pattern[] tasksPatterns, long lastModified, boolean useAspTagsAsPhp) {
		this.parserManager = parserManager;
		this.phpParser = phpParser;
		this.client = client;
		this.filename = filename;
		this.reader = reader;
		this.tasksPatterns = tasksPatterns;
		this.lastModified = lastModified;
		this.useAspTagsAsPhp = useAspTagsAsPhp;
	}
	
	/**
	 * The parsing action - in a seperate (async) thread 
	 * @throws InterruptedException 
	 */
	public void run() {
		try {
			
			final CompletionLexer lexer = parserManager.createCompletionLexer(reader);
			lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
			lexer.setParserClient(client);
			lexer.setTasksPatterns(tasksPatterns);

			if (phpParser == null) {
				phpParser = parserManager.createPhpParser();
			}
			phpParser.setScanner(lexer);
			phpParser.setParserClient(client);
			
			client.startParsing(filename);

			phpParser.parse();

		} catch (Exception e) {
			Logger.logException(e);

		} finally {

			try {
				if (client != null && phpParser != null) {
					client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
				}
				
			} catch (Exception ex) {
				Logger.logException(ex);
			
			} finally {
				try {
					reader.close();
				} catch (IOException exception) {
					Logger.logException(exception);
				}					

			}
		}
	}
}
