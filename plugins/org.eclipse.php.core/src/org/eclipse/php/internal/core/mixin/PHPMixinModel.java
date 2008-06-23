/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.mixin;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class PHPMixinModel {

	private static PHPMixinModel instance = new PHPMixinModel();
	private final MixinModel model;

	private PHPMixinModel() {
		model = new MixinModel(PHPLanguageToolkit.getDefault());
	}

	public static PHPMixinModel getInstance() {
		return instance;
	}

	public static MixinModel getRawInstance() {
		return getInstance().getRawModel();
	}

	public MixinModel getRawModel() {
		return model;
	}

	private IModelElement[] filterElements(IMixinElement[] elements, int kind) {
		List<IModelElement> filtered = new LinkedList<IModelElement>();
		for (IMixinElement element : elements) {
			Object[] objects = element.getAllObjects();
			for (Object obj : objects) {
				if (obj instanceof PHPMixinElementInfo) {
					PHPMixinElementInfo info = (PHPMixinElementInfo) obj;
					if ((info.getKind() & kind) != 0) {
						filtered.add((IModelElement) info.getObject());
					}
				}
			}
		}
		return filtered.toArray(new IModelElement[filtered.size()]);
	}

	public IModelElement[] getMethod(String className, String methodName) {
		if (className == null) {
			return getFunction(methodName);
		}
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX + MixinModel.SEPARATOR + methodName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD);
	}

	public IModelElement[] getMethodDoc(String className, String methodName) {
		if (className == null) {
			return getFunctionDoc(methodName);
		}
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX + MixinModel.SEPARATOR + methodName);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC);
	}

	public IModelElement[] getFunction(String functionName) {
		IMixinElement[] elements = model.find(MixinModel.SEPARATOR + functionName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD);
	}

	public IModelElement[] getFunctionDoc(String functionName) {
		IMixinElement[] elements = model.find(MixinModel.SEPARATOR + functionName);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC);
	}

	public IModelElement[] getClass(String className) {
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_CLASS | PHPMixinElementInfo.K_INTERFACE);
	}

	public IModelElement[] getClassDoc(String className) {
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC);
	}

	private IMixinElement[] internalGetVariable(String variableName, String methodName, String typeName) {
		//{$globalVa
		String pattern = MixinModel.SEPARATOR + variableName;
		if (methodName != null) {
			//{myFunction{$methodVar2
			pattern = MixinModel.SEPARATOR + methodName + pattern;
		}
		if (typeName != null) {
			// AClass%{classMethod{$methodVariable
			pattern = typeName + PHPMixinParser.CLASS_SUFFIX + pattern;
		}
		return model.find(pattern);
	}

	public IModelElement[] getVariable(String variableName, String methodName, String typeName) {
		return filterElements(internalGetVariable(variableName, methodName, typeName), PHPMixinElementInfo.K_VARIABLE);
	}

	public IModelElement[] getVariableDoc(String variableName, String methodName, String typeName) {
		return filterElements(internalGetVariable(variableName, methodName, typeName), PHPMixinElementInfo.K_PHPDOC);
	}

	private IMixinElement[] internalGetConstant(String constantName, String typeName) {
		String pattern = MixinModel.SEPARATOR + constantName;
		if (typeName != null) {
			pattern = typeName + PHPMixinParser.CLASS_SUFFIX + pattern;
		}
		return model.find(pattern);
	}

	public IModelElement[] getConstant(String constantName, String typeName) {
		return filterElements(internalGetConstant(constantName, typeName), PHPMixinElementInfo.K_CONSTANT);
	}

	public IModelElement[] getConstantDoc(String constantName, String typeName) {
		return filterElements(internalGetConstant(constantName, typeName), PHPMixinElementInfo.K_PHPDOC_FOR_CONSTANT);
	}

	public IModelElement[] getInclude(String fileName) {
		IMixinElement[] elements = model.find(fileName + PHPMixinParser.INCLUDE_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_INCLUDE);
	}
}
