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

import java.io.Reader;
import java.util.regex.Pattern;

public abstract class PHPParserManager {

	private PhpParser phpParser;

	protected abstract CompletionLexer createCompletionLexer(Reader reader);

	protected abstract PhpParser createPhpParser();

	/**
	 * Builds the scheduler for the parsing tasks
	 */
	private static PhpParserSchedulerTask scheduler = PhpParserSchedulerTask.getInstance();
	static {
		assert scheduler != null; 
		Thread thread = new Thread(scheduler);
		thread.setName("PHP Parser Scheduler");
		thread.start();
	}
	
	public void parse(Reader reader, String fileName, long lastModified, ParserClient client, boolean useAspTagsAsPhp) {
		parse(reader, fileName, lastModified, client, new Pattern[0], useAspTagsAsPhp);
	}

	public void parse(Reader reader, String fileName, long lastModified, ParserClient client, Pattern[] tasksPatterns, boolean useAspTagsAsPhp) {
		scheduler.schedule(this, phpParser, client, fileName, reader, tasksPatterns, lastModified, useAspTagsAsPhp);

	}
}