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

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IMember;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.templates.PhpTemplateContext;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;

public class PhpTemplateNumberVariableResolver extends TemplateVariableResolver {

	private static final String DEFAULT_VAR = "index"; //$NON-NLS-1$

	public PhpTemplateNumberVariableResolver() {
		super("number_variable", PHPUIMessages.getString("PhpTemplateNumberVariableResolver.2")); //$NON-NLS-1$ //$NON-NLS-2$
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
			IFile fileData = phpDOMModel.getIFile();
			if (fileData == null) {
				return new String[] { DEFAULT_VAR };
			}
			
			final PHPCodeContext phpCodeContext = ModelSupport.createContext(fileData, phpTemplateContext.getStart());
			final PHPVariablesTypeManager variablesTypeManager = fileData.getVariableTypeManager();
			IField[] phpVariableDatas = variablesTypeManager.getVariables(phpCodeContext);
			if (phpVariableDatas == null) {
				return new String[] { DEFAULT_VAR };
			}
			ArrayList arrayList = new ArrayList();
			for (int i = 0; i < phpVariableDatas.length; i++) {
				IField phpVariableData = phpVariableDatas[i];
				PHPVariableTypeData variableTypeData = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, phpVariableData.getName(), phpTemplateContext.getStart());
				if (variableTypeData == null) {
					continue;
				}
				arrayList.add(phpVariableData);
			}
			Collections.sort(arrayList, new Comparator() {
				public int compare(Object obj0, Object obj1) {
					IField iField0 = (IField)obj0;
					IField iField1 = (IField)obj1;
					
					PHPVariableTypeData variableTypeData0 = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, iField0.getName(), phpTemplateContext.getStart());
					String variableType0 = getVariableType(variableTypeData0);
					boolean variableType0IsNumber = PhpVariableTypeUtil.isNumber(variableType0);

					PHPVariableTypeData variableTypeData1 = variablesTypeManager.getVariableTypeDataByPosition(phpCodeContext, iField1.getName(), phpTemplateContext.getStart());
					String variableType1 = getVariableType(variableTypeData1);
					boolean variableType1IsNumber = PhpVariableTypeUtil.isNumber(variableType1);
					
					if (variableType0IsNumber && variableType1IsNumber) {
						return iField0.getName().compareToIgnoreCase(iField1.getName());
					}

					if (variableType0IsNumber) {
						return -1;
					}
					
					if (variableType1IsNumber) {
						return 1;
					}
					if (variableType0 == null && variableType1 == null) {
						return iField0.getName().compareToIgnoreCase(iField1.getName());
					}
					
					if (variableType0 == null) {
						return -1;
					}
					
					if (variableType1 == null) {
						return 1;
					}
										
					return iField0.getName().compareToIgnoreCase(iField1.getName());
				}

				private String getVariableType(PHPVariableTypeData variableTypeData) {
					return variableTypeData.getType();
				}
			});
			
			for (Iterator iFieldIterator = arrayList.iterator(); iFieldIterator.hasNext();) {
				IField iField = (IField) iFieldIterator.next();
				variablesNames.add(iField.getElementName() );
			}
		} finally {
			structuredModel.releaseFromRead();
		}

		return (String[]) variablesNames.toArray(new String[variablesNames.size()]);
	}
}
