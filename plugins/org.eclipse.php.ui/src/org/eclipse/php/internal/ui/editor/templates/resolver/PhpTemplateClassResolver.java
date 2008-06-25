/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.templates.resolver;

import java.util.ArrayList;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceModule;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateClassResolver extends TemplateVariableResolver {

	private static final String DEFAULT_VAR = "Object"; //$NON-NLS-1$

	public PhpTemplateClassResolver() {
		super("class", PHPUIMessages.getString("PhpTemplateClassResolver.2")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected String[] resolveAll(TemplateContext context) {
		ArrayList classNames = new ArrayList();
		final PhpTemplateContext phpTemplateContext = (PhpTemplateContext) context;

		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return new String[] { DEFAULT_VAR };
		}

		IStructuredModel structuredModel = modelManager.getExistingModelForRead(phpTemplateContext.getDocument());
		if (structuredModel == null) {
			return new String[] { DEFAULT_VAR };
		}

		try {
			DOMModelForPHP phpDOMModel = (DOMModelForPHP) structuredModel;
			IType[] types = ((SourceModule) (phpDOMModel.getModelElement())).getAllTypes();//getProjectModel().getClasses();
			for (int i = 0; i < types.length; i++) {
				IType type = types[i];
				classNames.add(type.getElementName());
			}
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			structuredModel.releaseFromRead();
		}

		return (String[]) classNames.toArray(new String[classNames.size()]);
	}
}
