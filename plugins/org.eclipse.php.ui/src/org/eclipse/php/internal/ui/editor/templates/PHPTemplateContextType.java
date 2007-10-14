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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.templates.GlobalTemplateVariables;
import org.eclipse.jface.text.templates.TemplateBuffer;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateContextType;
import org.eclipse.php.internal.ui.editor.templates.resolver.*;
import org.eclipse.text.edits.MalformedTreeException;
import org.eclipse.wst.xml.ui.internal.templates.EncodingTemplateVariableResolverXML;

public class PHPTemplateContextType extends TemplateContextType {

	public PHPTemplateContextType() {
		super();
		addResolver(new GlobalTemplateVariables.Cursor());
		addResolver(new GlobalTemplateVariables.Date());
		addResolver(new GlobalTemplateVariables.Dollar());
		addResolver(new GlobalTemplateVariables.LineSelection());
		addResolver(new GlobalTemplateVariables.Time());
		addResolver(new GlobalTemplateVariables.User());
		addResolver(new GlobalTemplateVariables.WordSelection());
		addResolver(new GlobalTemplateVariables.Year());
		addResolver(new EncodingTemplateVariableResolverXML());

		addResolver(new PhpTemplateVariableResolver());
		addResolver(new PhpTemplateFunctionContainerResolver());
		addResolver(new PhpTemplateClassContainerResolver());
		addResolver(new PhpTemplateNumberVariableResolver());
		addResolver(new PhpTemplateArrayVariableResolver());
		addResolver(new PhpTemplateClassResolver());
		addResolver(new PhpTemplateFileResolver());
	}

	public void resolve(TemplateBuffer buffer, TemplateContext context) throws MalformedTreeException, BadLocationException {
		super.resolve(buffer, context);
	}

}