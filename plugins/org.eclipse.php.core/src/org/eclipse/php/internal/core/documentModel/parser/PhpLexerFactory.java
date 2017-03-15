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
package org.eclipse.php.internal.core.documentModel.parser;

import java.io.Reader;

import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.search.Messages;

public class PhpLexerFactory {

	public static AbstractPhpLexer createLexer(Reader reader, PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php54.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php55.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_6) {
			return new org.eclipse.php.internal.core.documentModel.parser.php56.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_0) {
			return new org.eclipse.php.internal.core.documentModel.parser.php7.PhpLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_1) {
			return new org.eclipse.php.internal.core.documentModel.parser.php71.PhpLexer(reader);
		}
		if (phpVersion == null) {
			throw new IllegalArgumentException(CoreMessages.getString("UnknownPHPVersion_0")); //$NON-NLS-1$
		} else {
			throw new IllegalArgumentException(
					Messages.format(CoreMessages.getString("UnknownPHPVersion_1"), phpVersion)); //$NON-NLS-1$
		}
	}
}
