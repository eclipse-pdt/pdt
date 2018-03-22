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
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IOpenable;
import org.eclipse.dltk.internal.core.BufferManager;
import org.eclipse.php.core.project.ProjectOptions;
import org.eclipse.php.internal.core.Logger;

public abstract class AbstractPHPSourceParser extends AbstractSourceParser implements ISourceParser {
	private String fileName;

	public AbstractPHPSourceParser(String fileName) {
		this.fileName = fileName;
	}

	public AbstractPHPSourceParser() {
		this(null);
	}

	@Override
	public IModuleDeclaration parse(IModuleSource input, IProblemReporter reporter) {
		try {

			Reader reader = null;
			if (input instanceof IOpenable) {
				IBuffer buffer = BufferManager.getDefaultBufferManager().getBuffer((IOpenable) input);
				if (buffer != null) {
					reader = new CharArrayReader(buffer.getCharacters());
				}
			}
			if (reader == null) {
				reader = new CharArrayReader(input.getContentsAsCharArray());
			}
			return parse(reader, reporter,
					ProjectOptions.isSupportingASPTags(input.getModelElement().getScriptProject().getProject()),
					ProjectOptions.useShortTags(input.getModelElement().getScriptProject().getProject()));
		} catch (Exception e) {
			Logger.logException(e);
			// XXX: add recovery
			return new ModuleDeclaration(0);
		}

	}

	public abstract IModuleDeclaration parse(Reader in, IProblemReporter reporter, boolean isSupportingASPTags,
			boolean useShortTags) throws Exception;

	protected IModuleDeclaration parse(AbstractASTParser parser) {
		parser.setFileName(fileName);
		try {
			parser.parse();
		} catch (Exception e) {
			Logger.logException(e);
			// XXX: add recovery
			return new ModuleDeclaration(0);
		}
		return parser.getModuleDeclaration();
	}

}
