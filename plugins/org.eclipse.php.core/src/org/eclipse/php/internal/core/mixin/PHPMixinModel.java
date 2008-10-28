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

import java.util.*;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.IShutdownListener;
import org.eclipse.dltk.core.mixin.IMixinElement;
import org.eclipse.dltk.core.mixin.MixinModel;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.PHPLanguageToolkit;

public class PHPMixinModel implements IShutdownListener {

	private static final Map<IScriptProject, PHPMixinModel> instances = new HashMap<IScriptProject, PHPMixinModel>();
	private static PHPMixinModel instance;
	private final MixinModel model;

	public static PHPMixinModel getWorkspaceInstance() {
		synchronized (instances) {
			if (instance == null)
				instance = new PHPMixinModel(null);
			return instance;
		}
	}

	public static PHPMixinModel getInstance(IScriptProject project) {
		Assert.isNotNull(project);
		synchronized (instances) {
			PHPMixinModel mixinModel = (PHPMixinModel) instances.get(project);
			if (mixinModel == null) {
				mixinModel = new PHPMixinModel(project);
				instances.put(project, mixinModel);
			}
			return mixinModel;
		}
	}

	public MixinModel getRawModel() {
		return model;
	}

	/**
	 * @param key
	 * @return
	 */
	public static void clearKeysCache(String key) {
		synchronized (instances) {
			if (instance != null) {
				instance.getRawModel().clearKeysCache(key);
			}
			for (Iterator<PHPMixinModel> i = instances.values().iterator(); i.hasNext();) {
				PHPMixinModel mixinModel = i.next();
				mixinModel.getRawModel().clearKeysCache(key);
			}
		}
	}

	private PHPMixinModel(IScriptProject project) {
		model = new MixinModel(PHPLanguageToolkit.getDefault(), project);
		PHPCorePlugin.getDefault().addShutdownListener(this);
	}

	public void shutdown() {
		model.stop();
	}

	private IModelElement[] filterElements(IMixinElement[] elements, int kind, IDLTKSearchScope scope) {
		List<IModelElement> filtered = new LinkedList<IModelElement>();
		for (IMixinElement element : elements) {
			Object[] objects = element.getAllObjects();
			for (Object obj : objects) {
				if (obj instanceof PHPMixinElementInfo) {
					PHPMixinElementInfo info = (PHPMixinElementInfo) obj;
					if ((info.getKind() & kind) != 0) {
						IModelElement modelElement = (IModelElement) info.getObject();
						if (scope == null || scope.encloses(modelElement)) {
							filtered.add(modelElement);
						}
					}
				}
			}
		}
		return filtered.toArray(new IModelElement[filtered.size()]);
	}

	public IModelElement[] getMethod(String className, String methodName) {
		return getMethod(className, methodName, null);
	}

	public IModelElement[] getMethod(String className, String methodName, IDLTKSearchScope scope) {
		if (className == null) {
			return getFunction(methodName, scope);
		}
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX + MixinModel.SEPARATOR + methodName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD, scope);
	}

	public IModelElement[] getMethodDoc(String className, String methodName) {
		return getMethodDoc(className, methodName, null);
	}

	public IModelElement[] getMethodDoc(String className, String methodName, IDLTKSearchScope scope) {
		if (className == null) {
			return getFunctionDoc(methodName, scope);
		}
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX + MixinModel.SEPARATOR + methodName);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC, scope);
	}

	public IModelElement[] getFunction(String functionName) {
		return getFunction(functionName, null);
	}

	public IModelElement[] getFunction(String functionName, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(MixinModel.SEPARATOR + functionName);
		return filterElements(elements, PHPMixinElementInfo.K_METHOD, scope);
	}

	public IModelElement[] getFunctionDoc(String functionName) {
		return getFunctionDoc(functionName, null);
	}

	public IModelElement[] getFunctionDoc(String functionName, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(MixinModel.SEPARATOR + functionName);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC, scope);
	}

	public IModelElement[] getClass(String className) {
		return getClass(className, null);
	}

	public IModelElement[] getClass(String className, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_CLASS | PHPMixinElementInfo.K_INTERFACE, scope);
	}

	public IModelElement[] getClassDoc(String className) {
		return getClassDoc(className, null);
	}

	public IModelElement[] getClassDoc(String className, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(className + PHPMixinParser.CLASS_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_PHPDOC, scope);
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
		return getVariable(variableName, methodName, typeName, null);
	}

	public IModelElement[] getVariable(String variableName, String methodName, String typeName, IDLTKSearchScope scope) {
		return filterElements(internalGetVariable(variableName, methodName, typeName), PHPMixinElementInfo.K_VARIABLE, scope);
	}

	public IModelElement[] getVariableDoc(String variableName, String methodName, String typeName) {
		return getVariableDoc(variableName, methodName, typeName, null);
	}

	public IModelElement[] getVariableDoc(String variableName, String methodName, String typeName, IDLTKSearchScope scope) {
		return filterElements(internalGetVariable(variableName, methodName, typeName), PHPMixinElementInfo.K_PHPDOC, scope);
	}

	private IMixinElement[] internalGetConstant(String constantName, String typeName) {
		String pattern = MixinModel.SEPARATOR + constantName;
		if (typeName != null) {
			pattern = typeName + PHPMixinParser.CLASS_SUFFIX + pattern;
		}
		return model.find(pattern);
	}

	public IModelElement[] getConstant(String constantName, String typeName) {
		return getConstant(constantName, typeName, null);
	}

	public IModelElement[] getConstant(String constantName, String typeName, IDLTKSearchScope scope) {
		return filterElements(internalGetConstant(constantName, typeName), PHPMixinElementInfo.K_CONSTANT, scope);
	}

	public IModelElement[] getConstantDoc(String constantName, String typeName) {
		return getConstantDoc(constantName, typeName, null);
	}

	public IModelElement[] getConstantDoc(String constantName, String typeName, IDLTKSearchScope scope) {
		return filterElements(internalGetConstant(constantName, typeName), PHPMixinElementInfo.K_PHPDOC_FOR_CONSTANT, scope);
	}

	public IModelElement[] getInclude(String fileName) {
		return getInclude(fileName, null);
	}

	public IModelElement[] getInclude(String fileName, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(fileName + PHPMixinParser.INCLUDE_SUFFIX);
		return filterElements(elements, PHPMixinElementInfo.K_INCLUDE, scope);
	}
}
