/*******************************************************************************
 * Copyright (c) 2009-2018 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.documentModel.parser;

import java.io.Reader;

import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.search.Messages;

public class PHPLexerFactory {

	public static AbstractPHPLexer createLexer(Reader reader, PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php54.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php55.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP5_6) {
			return new org.eclipse.php.internal.core.documentModel.parser.php56.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_0) {
			return new org.eclipse.php.internal.core.documentModel.parser.php7.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_1) {
			return new org.eclipse.php.internal.core.documentModel.parser.php71.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_2) {
			return new org.eclipse.php.internal.core.documentModel.parser.php72.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php73.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP7_4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php74.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP8_0) {
			return new org.eclipse.php.internal.core.documentModel.parser.php80.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP8_1) {
			return new org.eclipse.php.internal.core.documentModel.parser.php81.PHPLexer(reader);
		}
		if (phpVersion == PHPVersion.PHP8_2) {
			return new org.eclipse.php.internal.core.documentModel.parser.php82.PHPLexer(reader);
		}
		if (phpVersion == null) {
			throw new IllegalArgumentException(CoreMessages.getString("UnknownPHPVersion_0")); //$NON-NLS-1$
		} else {
			throw new IllegalArgumentException(
					Messages.format(CoreMessages.getString("UnknownPHPVersion_1"), phpVersion)); //$NON-NLS-1$
		}
	}
}
