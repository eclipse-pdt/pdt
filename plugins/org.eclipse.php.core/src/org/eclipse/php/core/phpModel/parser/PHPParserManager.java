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

public abstract class PHPParserManager {

	private PhpParser phpParser;

	protected abstract CompletionLexer createCompletionLexer(Reader reader);

	protected abstract PhpParser createPhpParser();

	public void parse(Reader reader, String fileName, long lastModified, ParserClient client, Pattern[] tasksPatterns, boolean useAspTagsAsPhp) throws IOException {
		CompletionLexer lexer = createCompletionLexer(reader);
		lexer.setUseAspTagsAsPhp(useAspTagsAsPhp);
		lexer.setParserClient(client);
		lexer.setTasksPatterns(tasksPatterns);
		
		if (phpParser == null) {
			phpParser = createPhpParser();
		}
		phpParser.setScanner(lexer);
		phpParser.setParserClient(client);

		client.startParsing(fileName);
		try {
			phpParser.parse();
		} catch (IOException ioe) {
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.finishParsing(phpParser.getLength(), phpParser.getCurrentLine(), lastModified);
			reader.close();
		}

	}
	
	public void parse(Reader reader, String fileName, long lastModified, ParserClient client, boolean useAspTagsAsPhp) throws IOException {
		parse(reader, fileName, lastModified, client, new Pattern[0], useAspTagsAsPhp);
	}

}