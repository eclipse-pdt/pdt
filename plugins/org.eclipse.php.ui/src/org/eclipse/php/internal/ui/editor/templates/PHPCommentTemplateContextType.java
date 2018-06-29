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

/**
 * Context Type id for PHPDOC and comments
 */
public class PHPCommentTemplateContextType extends ScriptTemplateContextType {

	public static final String PHP_COMMENT_CONTEXT_TYPE_ID = "phpcomment"; //$NON-NLS-1$

	@Override
	public ScriptTemplateContext createContext(IDocument document, int offset, int length, ISourceModule sourceModule) {
		return new PHPTemplateContext(this, document, offset, length, sourceModule);
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
	}

}
