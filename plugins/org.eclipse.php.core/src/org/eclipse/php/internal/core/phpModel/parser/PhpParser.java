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
package org.eclipse.php.internal.core.phpModel.parser;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public interface PhpParser {

	public void setParserClient(ParserClient client);

	public void setScanner(Scanner s);

	public Symbol parse() throws java.lang.Exception;

	public int getLength();

	public int getCurrentLine();

}