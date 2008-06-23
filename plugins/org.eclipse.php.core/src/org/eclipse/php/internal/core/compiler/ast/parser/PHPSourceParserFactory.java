/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
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
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionProjectPropertyHandler;

public class PHPSourceParserFactory extends AbstractSourceParser implements ISourceParserFactory, ISourceParser {

	public ISourceParser createSourceParser() {
		return this;
	}

	public ModuleDeclaration parse(char[] fileName, char[] source, IProblemReporter reporter) {
		AbstractPHPSourceParser parser = createParser(new String(fileName));
		return parser.parse(fileName, source, reporter);
	}

	protected AbstractPHPSourceParser createParser(String fileName) {
		IResource resource = ResourcesPlugin.getWorkspace().getRoot().findMember(fileName);
		if (resource != null) {
			IProject project = resource.getProject();
			if (project.isAccessible()) {
				String phpVersion = PhpVersionProjectPropertyHandler.getVersion(project);
				if (PHPCoreConstants.PHP4.equals(phpVersion)) {
					return new org.eclipse.php.internal.core.compiler.ast.parser.php4.Php4SourceParser(fileName);
				}
			}
		}
		return new org.eclipse.php.internal.core.compiler.ast.parser.php5.Php5SourceParser(fileName);
	}
}
