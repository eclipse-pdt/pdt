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

import java.io.CharArrayReader;
import java.io.Reader;

import org.eclipse.dltk.ast.declarations.ModuleDeclaration;
import org.eclipse.dltk.ast.parser.AbstractSourceParser;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.compiler.problem.IProblemReporter;
import org.eclipse.php.internal.core.project.ProjectOptions;

public abstract class AbstractPHPSourceParser extends AbstractSourceParser
		implements ISourceParser {
	private static boolean DEBUG = false;
	private String fileName;

	public AbstractPHPSourceParser(String fileName) {
		this.fileName = fileName;
	}

	public AbstractPHPSourceParser() {
		this(null);
	}

	public IModuleDeclaration parse(IModuleSource input,
			IProblemReporter reporter) {
		try {
			return parse(new CharArrayReader(input.getContentsAsCharArray()),
					reporter, ProjectOptions.useShortTags(input
							.getModelElement().getScriptProject().getProject()));
		} catch (Exception e) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=316077
			if (DEBUG) {
				e.printStackTrace();
			}
			// XXX: add recovery
			return new ModuleDeclaration(0);
		}

	}

	public abstract IModuleDeclaration parse(Reader in,
			IProblemReporter reporter, boolean useShortTags) throws Exception;

	protected IModuleDeclaration parse(AbstractASTParser parser) {
		parser.setFileName(fileName);
		try {
			parser.parse();
		} catch (Exception e) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=316077
			if (DEBUG) {
				e.printStackTrace();
			}
			// XXX: add recovery
			return new ModuleDeclaration(0);
		}
		return parser.getModuleDeclaration();
	}

}
