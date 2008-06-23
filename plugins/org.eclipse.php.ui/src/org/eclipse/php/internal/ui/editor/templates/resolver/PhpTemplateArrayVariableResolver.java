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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.parser.ModelSupport;
import org.eclipse.php.internal.core.phpModel.parser.PHPCodeContext;
import org.eclipse.php.internal.core.phpModel.phpElementData.*;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateArrayVariableResolver extends TemplateVariableResolver {

	private static final String DEFAULT_VAR = "array"; //$NON-NLS-1$

	public PhpTemplateArrayVariableResolver() {
		super("array_variable", PHPUIMessages.getString("PhpTemplateArrayVariableResolver.2")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	protected String[] resolveAll(TemplateContext context) {
		ArrayList variablesNames = new ArrayList();
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
			PHPFileData fileData = phpDOMModel.getFileData();
			if (fileData == null) {
				return new String[] { DEFAULT_VAR };
			}
			
			final PHPCodeContext phpCodeContext = ModelSupport.createContext(fileData, phpTemplateContext.getStart());
			final PHPVariablesTypeManager variablesTypeManager = fileData.getVariableTypeManager();
			PHPVariableData[] phpVariableDatas = variablesTypeManager.getVariables(phpCodeContext);
			if (phpVariableDatas == null) {
				return new String[] { DEFAULT_VAR };
			}
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < phpVariableDatas.length; i++) {
				PHPVariableData phpVariableData = phpVariableDatas[i];
				PHPVariableTypeData variableTypeData = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, phpVariableData.getName(), phpTemplateContext.getStart());
				if (variableTypeData == null) {
					continue;
				}
				arrayList.add(phpVariableData);
			}
			Collections.sort(arrayList, new Comparator() {
				public int compare(Object o0, Object o1) {
					PHPVariableData phpVariableData0 = (PHPVariableData)o0;
					PHPVariableData phpVariableData1 = (PHPVariableData)o1;
					
					PHPVariableTypeData variableTypeData0 = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, phpVariableData0.getName(), phpTemplateContext.getStart());
					String variableType0 = getVariableType(variableTypeData0);
					boolean variableType0IsArray = PhpVariableTypeUtil.isArray(variableType0);

					PHPVariableTypeData variableTypeData1 = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, phpVariableData1.getName(), phpTemplateContext.getStart());
					String variableType1 = getVariableType(variableTypeData1);
					boolean variableType1IsArray = PhpVariableTypeUtil.isArray(variableType1);
					
					if ((variableType0IsArray && variableType1IsArray) || (!variableType0IsArray && !variableType1IsArray)) {
						return phpVariableData0.getName().compareToIgnoreCase(phpVariableData0.getName());
					}
					
					if (variableType0IsArray) {
						return -1;
					}
					
					if (variableType1IsArray) {
						return 1;
					}
					
					if (variableType0 == null && variableType1 == null) {
						return phpVariableData0.getName().compareToIgnoreCase(phpVariableData1.getName());
					}
					
					if (variableType0 == null) {
						return -1;
					}
					
					if (variableType1 == null) {
						return 1;
					}
										
					return phpVariableData0.getName().compareToIgnoreCase(phpVariableData1.getName());
				}

				private String getVariableType(PHPVariableTypeData variableTypeData) {
					return variableTypeData.getType();
				}
			});
			
			for (Iterator PHPVariableDataIterator = arrayList.iterator(); PHPVariableDataIterator.hasNext();) {
				PHPVariableData phpVariableData = (PHPVariableData) PHPVariableDataIterator.next();
				variablesNames.add(phpVariableData.getName());
			}
		} finally {
			structuredModel.releaseFromRead();
		}

		return (String[]) variablesNames.toArray(new String[variablesNames.size()]);
	}

}
