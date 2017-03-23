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
package org.eclipse.php.internal.core.compiler.ast.parser;

import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.parser.ISourceParserFactory;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.CoreMessages;
import org.eclipse.php.internal.core.search.Messages;

public class PHPSourceParserFactory extends AbstractSourceParser implements ISourceParserFactory, ISourceParser {

	public ISourceParser createSourceParser() {
		return this;
	}

	@Override
	public IModuleDeclaration parse(IModuleSource module, IProblemReporter reporter) {
		final String fileName = module.getFileName();
		AbstractPHPSourceParser parser = createParser(fileName);
		return parser.parse(module, reporter);
	}

	protected AbstractPHPSourceParser createParser(String fileName) {
		PHPVersion phpVersion = ProjectOptions.getPHPVersion(fileName);
		AbstractPHPSourceParser parser = createParser(fileName, phpVersion);
		if (parser == null) {
			if (phpVersion == null) {
				throw new IllegalStateException(CoreMessages.getString("UnknownPHPVersion_0")); //$NON-NLS-1$
			} else {
				throw new IllegalStateException(
						Messages.format(CoreMessages.getString("UnknownPHPVersion_1"), phpVersion)); //$NON-NLS-1$
			}
		}
		return parser;
	}

	/**
	 * Create source parser for the given PHP file name and PHP version
	 * 
	 * @param fileName
	 * @param phpVersion
	 * @return source parser instance or <code>null</code> in case PHP version
	 *         is incompatibleS
	 */
	public static AbstractPHPSourceParser createParser(String fileName, PHPVersion phpVersion) {
		if (PHPVersion.PHP5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php53.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5_4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php54.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5_5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php55.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5_6 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php56.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP7_0 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php7.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP7_1 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php71.PhpSourceParser(fileName);
		}
		return null;
	}

	/**
	 * Create source parser for the PHP version
	 * 
	 * @param phpVersion
	 * @return source parser instance or <code>null</code> in case PHP version
	 *         is incompatibleS
	 */
	public static AbstractPHPSourceParser createParser(PHPVersion phpVersion) {
		if (PHPVersion.PHP5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser();
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php53.PhpSourceParser();
		}
		if (PHPVersion.PHP5_4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php54.PhpSourceParser();
		}
		if (PHPVersion.PHP5_5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php55.PhpSourceParser();
		}
		if (PHPVersion.PHP5_6 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php56.PhpSourceParser();
		}
		if (PHPVersion.PHP7_0 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php7.PhpSourceParser();
		}
		if (PHPVersion.PHP7_1 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php71.PhpSourceParser();
		}
		return null;
	}
}
