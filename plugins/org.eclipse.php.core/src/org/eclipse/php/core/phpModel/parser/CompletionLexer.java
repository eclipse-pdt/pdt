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

import java.util.regex.Pattern;

public abstract class CompletionLexer implements org.eclipse.php.core.phpModel.javacup.runtime.Scanner {

	public abstract void setUseAspTagsAsPhp(boolean useAspTagsAsPhp);

	public abstract int getCurrentLine();

	public abstract void setParserClient(ParserClient parserClient);

	public abstract void setTasksPatterns(Pattern[] tasksPatterns);

	public abstract int getLength();

	public abstract Object[] getPHPDoc(int location);

	public abstract Object[] getFirstPHPDoc();

	public abstract String createString(int startOffset, int endOffset);

	public abstract void yyclose() throws java.io.IOException;

	public abstract void yyreset(java.io.Reader reader) throws java.io.IOException;

	public abstract int yystate();

	public abstract void yybegin(int newState);

	public abstract String yytext();

	public abstract char yycharat(int pos);

	public abstract int yylength();

	public abstract org.eclipse.php.core.phpModel.javacup.runtime.Symbol next_token() throws java.io.IOException;
}
