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
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class PHPSourceParserFactory extends AbstractSourceParser implements
		ISourceParserFactory, ISourceParser {

	public ISourceParser createSourceParser() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.ast.parser.ISourceParser#parse(org.eclipse.dltk.compiler
	 * .env.IModuleSource, org.eclipse.dltk.compiler.problem.IProblemReporter)
	 */
	public IModuleDeclaration parse(IModuleSource module,
			IProblemReporter reporter) {
		final String fileName = module.getFileName();
		AbstractPHPSourceParser parser = createParser(fileName);
		return parser.parse(module, reporter);
	}

	protected AbstractPHPSourceParser createParser(String fileName) {
		PHPVersion phpVersion = ProjectOptions.getPhpVersion(fileName);
		AbstractPHPSourceParser parser = createParser(fileName, phpVersion);
		if (parser == null) {
			throw new IllegalStateException(Messages.PHPSourceParserFactory_0);
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
	public static AbstractPHPSourceParser createParser(String fileName,
			PHPVersion phpVersion) {
		if (PHPVersion.PHP4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php4.PhpSourceParser(
					fileName);
		}
		if (PHPVersion.PHP5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser(
					fileName);
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php53.PhpSourceParser(
					fileName);
		}
		if (PHPVersion.PHP5_4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php54.PhpSourceParser(
					fileName);
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
		if (PHPVersion.PHP4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php4.PhpSourceParser();
		}
		if (PHPVersion.PHP5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser();
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php53.PhpSourceParser();
		}
		if (PHPVersion.PHP5_4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php54.PhpSourceParser();
		}
		return null;
	}
}
