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

import java.util.ArrayList;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.phpModel.parser.ModelSupport;
import org.eclipse.php.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableTypeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariablesTypeManager;
import org.eclipse.php.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateVariableResolver extends TemplateVariableResolver {

	private static final String DEFAULT_VAR = "var";

	public PhpTemplateVariableResolver() {
		super("variable", "resolving a php variable");
	}

	protected String[] resolveAll(TemplateContext context) {
		ArrayList arrayList = new ArrayList();
		PhpTemplateContext phpTemplateContext = (PhpTemplateContext) context;

		IModelManager modelManager = StructuredModelManager.getModelManager();
		if (modelManager == null) {
			return new String[] { DEFAULT_VAR };
		}

		IStructuredModel structuredModel = modelManager.getExistingModelForRead(phpTemplateContext.getDocument());
		if (structuredModel == null) {
			return new String[] { DEFAULT_VAR };
		}

		try {
			PHPEditorModel phpEditorModel = (PHPEditorModel) structuredModel;
			PHPFileData fileData = phpEditorModel.getFileData();
			if (fileData == null) {
				return new String[] { DEFAULT_VAR };
			}
			PHPCodeContext phpCodeContext = ModelSupport.createContext(fileData, phpTemplateContext.getStart());
			PHPVariablesTypeManager variablesTypeManager = fileData.getVariableTypeManager();
			PHPVariableData[] phpVariableDatas = variablesTypeManager.getVariables(phpCodeContext);
			if (phpVariableDatas == null) {
				return new String[] { DEFAULT_VAR };
			}
			for (int i = 0; i < phpVariableDatas.length; i++) {
				PHPVariableData phpVariableData = phpVariableDatas[i];
				PHPVariableTypeData variableTypeData = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, phpVariableData.getName(), phpTemplateContext.getStart());
				if (variableTypeData == null) {
					continue;
				}

				arrayList.add(phpVariableData.getName());
			}
		} finally {
			structuredModel.releaseFromRead();
		}

		return (String[]) arrayList.toArray(new String[arrayList.size()]);
	}
}
