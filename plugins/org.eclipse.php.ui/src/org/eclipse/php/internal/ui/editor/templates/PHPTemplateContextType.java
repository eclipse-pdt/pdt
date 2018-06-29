/*******************************************************************************
 * Copyright (c) 2009, 2010 IBM Corporation and others.
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
public class PHPTemplateContextType extends ScriptTemplateContextType {

	// for all php except comment
	public static final String PHP_CONTEXT_TYPE_ID = "php"; //$NON-NLS-1$
	// for all statements
	public static final String PHP_STATEMENTS_CONTEXT_TYPE_ID = "php-statements"; //$NON-NLS-1$
	// for class,trait,interface statements(private,public and method
	// proposal)
	public static final String PHP_TYPE_MEMBERS_CONTEXT_TYPE_ID = "php-type-members"; //$NON-NLS-1$
	// for statements in type method
	public static final String PHP_TYPE_METHOD_STATEMENTS_CONTEXT_TYPE_ID = "php-type-method-statements"; //$NON-NLS-1$
	// for statements out of function and class
	public static final String PHP_GLOBAL_MEMBERS_CONTEXT_TYPE_ID = "php-global-members"; //$NON-NLS-1$

	// public static final String PHP_TRAIT_STATEMENT_CONTEXT_TYPE_ID =
	// "php-trait-statement"; //$NON-NLS-1$
	public static final String PHP_CLASS_MEMBERS_CONTEXT_TYPE_ID = "php-class-members"; //$NON-NLS-1$

	@Override
	public ScriptTemplateContext createContext(IDocument document, int offset, int length, ISourceModule sourceModule) {
		return new PHPTemplateContext(this, document, offset, length, sourceModule);
	}

	@Override
	public ScriptTemplateContext createContext(IDocument document, Position position, ISourceModule sourceModule) {
		return new PHPTemplateContext(this, document, position, sourceModule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.dltk.ui.templates.ScriptTemplateContextType#
	 * addScriptResolvers ()
	 */
	@Override
	protected void addScriptResolvers() {
		super.addScriptResolvers();
		removeResolver(new ScriptTemplateVariables.Interpreter());

		addResolver(new PHPTemplateVariables.Encoding());
		addResolver(new PHPTemplateVariables.ClassContainer());
		addResolver(new PHPTemplateVariables.FunctionContainer());
		addResolver(new PHPTemplateVariables.Variable());
		addResolver(new PHPTemplateVariables.Index());
		addResolver(new PHPTemplateVariables.NewVariable());
		addResolver(new PHPTemplateVariables.Class());
		addResolver(new PHPTemplateVariables.NumberVariable());
	}
}