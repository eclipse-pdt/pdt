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

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateClassContainerResolver extends TemplateVariableResolver {

	public static final String NO_CLASS = "no_class_scope"; //$NON-NLS-1$
	public PhpTemplateClassContainerResolver() {
		super("class_container", PHPUIMessages.getString("PhpTemplateClassContainerResolver.1")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected String resolve(TemplateContext context) {
		PhpTemplateContext phpTemplateContext = (PhpTemplateContext) context;

		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return null;
		}

		IStructuredModel structuredModel = modelManager.getExistingModelForRead(phpTemplateContext.getDocument());
		if (structuredModel == null) {
			return null;
		}

		try {
			DOMModelForPHP phpDOMModel = (DOMModelForPHP) structuredModel;
			PHPFileData fileData = phpDOMModel.getFileData();
			if (fileData == null) {
				return null;
			}
			PHPCodeContext phpCodeContext = ModelSupport.createContext(fileData, phpTemplateContext.getStart());
			String className = phpCodeContext.getContainerClassName();
			if (className == null || className.equals("")) { //$NON-NLS-1$
				return NO_CLASS;
			}
			
			return className;
		} finally {
			structuredModel.releaseFromRead();
		}
	}

	
}
