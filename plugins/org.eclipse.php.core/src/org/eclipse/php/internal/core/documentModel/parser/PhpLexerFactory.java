package org.eclipse.php.internal.core.documentModel.parser;

import java.io.InputStream;
import java.io.Reader;

/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
import org.eclipse.php.internal.core.PHPVersion;

public class PhpLexerFactory {
	
	public static AbstractPhpLexer createLexer(Reader reader, PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php4.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PhpLexer(reader);
		}
		throw new IllegalArgumentException("Unknown PHP version");
	}
	
	public static AbstractPhpLexer createLexer(InputStream stream, PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php4.PhpLexer(stream);
		}
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer(stream);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PhpLexer(stream);
		}
		throw new IllegalArgumentException("Unknown PHP version");
	}
}
