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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.ast.parser.ISourceParserFactory;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;

public class PHPSourceParserFactory extends AbstractSourceParser implements ISourceParserFactory, ISourceParser {

	public ISourceParser createSourceParser() {
		return this;
	}

	public ModuleDeclaration parse(char[] fileName, char[] source, IProblemReporter reporter) {
		AbstractPHPSourceParser parser = createParser(new String(fileName));
		return parser.parse(fileName, source, reporter);
	}

	protected AbstractPHPSourceParser createParser(String fileName) {
		PHPVersion phpVersion = PHPVersion.PHP5; // default
		
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
		if (resource != null) {
			IProject project = resource.getProject();
			if (project.isAccessible()) {
				phpVersion = ProjectOptions.getPhpVersion(project);
			}
		}
		
		AbstractPHPSourceParser parser = createParser(fileName, phpVersion);
		if (parser == null) {
			throw new IllegalStateException("Unknown PHP version!");
		}
		return parser;
	}
	
	/**
	 * Create source parser for the given PHP file name and PHP version
	 * @param fileName
	 * @param phpVersion
	 * @return source parser instance or <code>null</code> in case PHP version is incompatibleS
	 */
	public static AbstractPHPSourceParser createParser(String fileName, PHPVersion phpVersion) {
		if (PHPVersion.PHP4 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php4.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php5.PhpSourceParser(fileName);
		}
		if (PHPVersion.PHP5_3 == phpVersion) {
			return new org.eclipse.php.internal.core.compiler.ast.parser.php53.PhpSourceParser(fileName);
		}
		return null;
	}
	
	/**
	 * Create source parser for the PHP version
	 * @param phpVersion
	 * @return source parser instance or <code>null</code> in case PHP version is incompatibleS
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
		return null;
	}
}
