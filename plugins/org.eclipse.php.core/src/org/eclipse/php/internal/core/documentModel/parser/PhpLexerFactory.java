package org.eclipse.php.internal.core.documentModel.parser;

import java.io.InputStream;
import java.io.Reader;

import org.eclipse.php.internal.core.PHPVersion;

public class PhpLexerFactory {

	public static AbstractPhpLexer createLexer(Reader reader,
			PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php4.PhpLexer(
					reader);
		}
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer(
					reader);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PhpLexer(
					reader);
		}
		if (phpVersion == PHPVersion.PHP5_4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php54.PhpLexer(
					reader);
		}
		throw new IllegalArgumentException("Unknown PHP version"); //$NON-NLS-1$
	}

	public static AbstractPhpLexer createLexer(InputStream stream,
			PHPVersion phpVersion) {
		if (phpVersion == PHPVersion.PHP4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php4.PhpLexer(
					stream);
		}
		if (phpVersion == PHPVersion.PHP5) {
			return new org.eclipse.php.internal.core.documentModel.parser.php5.PhpLexer(
					stream);
		}
		if (phpVersion == PHPVersion.PHP5_3) {
			return new org.eclipse.php.internal.core.documentModel.parser.php53.PhpLexer(
					stream);
		}
		if (phpVersion == PHPVersion.PHP5_4) {
			return new org.eclipse.php.internal.core.documentModel.parser.php54.PhpLexer(
					stream);
		}
		throw new IllegalArgumentException("Unknown PHP version"); //$NON-NLS-1$
	}
}
