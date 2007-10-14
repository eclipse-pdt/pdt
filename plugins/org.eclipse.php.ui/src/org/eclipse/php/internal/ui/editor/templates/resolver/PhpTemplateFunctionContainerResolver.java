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

public class PhpTemplateFunctionContainerResolver extends TemplateVariableResolver {

	public static final String NO_FUNCTION = "no_function_scope"; //$NON-NLS-1$
	public PhpTemplateFunctionContainerResolver() {
		super("function_container", PHPUIMessages.getString("PhpTemplateFunctionContainerResolver.2")); //$NON-NLS-1$ //$NON-NLS-2$
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
			String functionName = phpCodeContext.getContainerFunctionName();
			if (functionName == null || functionName.equals("")) { //$NON-NLS-1$
				return NO_FUNCTION;
			}
			
			return functionName;
		} finally {
			structuredModel.releaseFromRead();
		}
	}

	
}
