/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.ui.editor.templates.resolver.*;

/**
 * Context Type id for PHP 
 */
public class PhpTemplateContextType extends ScriptTemplateContextType {
	
	public static final String PHP_CONTEXT_TYPE_ID = "php"; //$NON-NLS-1$
	
	public ScriptTemplateContext createContext(IDocument document, int offset, int length, ISourceModule sourceModule) {
		return new PhpTemplateContext(this, document, offset, length, sourceModule);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.dltk.ui.templates.ScriptTemplateContextType#addScriptResolvers()
	 */
	@Override
	protected void addScriptResolvers() {
		super.addScriptResolvers();
		
		// empty constructor
		addResolver(new PhpTemplateVariableResolver());
		addResolver(new PhpTemplateFunctionContainerResolver());
		addResolver(new PhpTemplateClassContainerResolver());
		addResolver(new PhpTemplateNumberVariableResolver());
		addResolver(new PhpTemplateArrayVariableResolver());
		addResolver(new PhpTemplateClassResolver());
		addResolver(new PhpTemplateFileResolver());
	}
}
