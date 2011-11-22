/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.dltk.ui.templates.ScriptTemplateVariables;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

/**
 * Context Type id for PHP
 */
public class PhpTemplateContextType extends ScriptTemplateContextType {

	public static final String PHP_CONTEXT_TYPE_ID = "php"; //$NON-NLS-1$

	public ScriptTemplateContext createContext(IDocument document, int offset,
			int length, ISourceModule sourceModule) {
		return new PhpTemplateContext(this, document, offset, length,
				sourceModule);
	}

	public ScriptTemplateContext createContext(IDocument document,
			Position position, ISourceModule sourceModule) {
		return new PhpTemplateContext(this, document, position, sourceModule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.ui.templates.ScriptTemplateContextType#addScriptResolvers
	 * ()
	 */
	@Override
	protected void addScriptResolvers() {
		super.addScriptResolvers();
		removeResolver(new ScriptTemplateVariables.Interpreter());

		addResolver(new PhpTemplateVariables.Encoding());
		addResolver(new PhpTemplateVariables.ClassContainer());
		addResolver(new PhpTemplateVariables.FunctionContainer());
		addResolver(new PhpTemplateVariables.Variable());
		addResolver(new PhpTemplateVariables.Index());
		addResolver(new PhpTemplateVariables.NewVariable());
		addResolver(new PhpTemplateVariables.Class());
		addResolver(new PhpTemplateVariables.NumberVariable());
	}
}