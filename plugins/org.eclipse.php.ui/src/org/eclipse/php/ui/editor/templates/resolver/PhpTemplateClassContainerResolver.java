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
package org.eclipse.php.ui.editor.templates.resolver;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.phpModel.parser.ModelSupport;
import org.eclipse.php.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateClassContainerResolver extends TemplateVariableResolver {

	public static final String NO_CLASS = "no_class_scope"; //$NON-NLS-1$
	public PhpTemplateClassContainerResolver() {
		super("class_container", "resolving a php class name");
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
			PHPEditorModel phpEditorModel = (PHPEditorModel) structuredModel;
			PHPFileData fileData = phpEditorModel.getFileData();
			if (fileData == null) {
				return null;
			}
			PHPCodeContext phpCodeContext = ModelSupport.createContext(fileData, phpTemplateContext.getStart());
			String className = phpCodeContext.getContainerClassName();
			if (className == null || className.equals("")) {
				return NO_CLASS;
			}
			
			return className;
		} finally {
			structuredModel.releaseFromRead();
		}
	}

	
}
