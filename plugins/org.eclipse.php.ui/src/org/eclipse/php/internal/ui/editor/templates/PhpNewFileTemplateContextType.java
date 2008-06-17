/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ui.templates.ScriptTemplateContext;
import org.eclipse.dltk.ui.templates.ScriptTemplateContextType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.wst.xml.ui.internal.templates.EncodingTemplateVariableResolverXML;

public class PhpNewFileTemplateContextType extends ScriptTemplateContextType {

	public PhpNewFileTemplateContextType() {
		super();
		addResolver(new GlobalTemplateVariables.Cursor());
		addResolver(new GlobalTemplateVariables.Date());
		addResolver(new GlobalTemplateVariables.Dollar());
		addResolver(new GlobalTemplateVariables.Time());
		addResolver(new GlobalTemplateVariables.User());
		addResolver(new GlobalTemplateVariables.Year());
		addResolver(new EncodingTemplateVariableResolverXML());
	}

	@Override
	public ScriptTemplateContext createContext(IDocument document, int completionPosition, int length, ISourceModule sourceModule) {
		return new PhpTemplateContext(this, document, completionPosition, length, sourceModule);
	}

}
