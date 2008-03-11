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
package org.eclipse.php.internal.core.phpModel.parser.php4;

import java.io.Reader;

import java_cup.runtime.Scanner;

import org.eclipse.php.internal.core.phpModel.parser.CompletionLexer;
import org.eclipse.php.internal.core.phpModel.parser.PHPParserManager;
import org.eclipse.php.internal.core.phpModel.parser.PhpParser;


public class PHP4ParserManager extends PHPParserManager {

	protected CompletionLexer createCompletionLexer(Reader reader) {
		return new CompletionLexer4(reader);
	}

	protected PhpParser createPhpParser(Scanner lexer) {
		return new PhpParser4(lexer);
	}

}
