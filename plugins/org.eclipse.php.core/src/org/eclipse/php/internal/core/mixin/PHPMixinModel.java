/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
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

/**
 * @deprecated we should move to DLTK search mechanism, mixin is not needed for PHP.
 */
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

	private IMixinElement[] internalGetVariable(String variableName) {
		//{$globalVa
		return model.find(MixinModel.SEPARATOR + variableName);
	}

	public IModelElement[] getVariable(String variableName) {
		return getVariable(variableName, null);
	}

	public IModelElement[] getVariable(String variableName, IDLTKSearchScope scope) {
		return filterElements(internalGetVariable(variableName), PHPMixinElementInfo.K_VARIABLE, scope);
	}

	private IMixinElement[] internalGetConstant(String constantName) {
		StringBuilder buf = new StringBuilder();
		buf.append(MixinModel.SEPARATOR).append(constantName).append(PHPMixinParser.CONSTANT_SUFFIX);
		return model.find(buf.toString());
	}

	public IModelElement[] getConstant(String constantName) {
		return getConstant(constantName, null);
	}

	public IModelElement[] getConstant(String constantName, IDLTKSearchScope scope) {
		return filterElements(internalGetConstant(constantName), PHPMixinElementInfo.K_CONSTANT, scope);
	}

	public IModelElement[] getInclude(String fileName) {
		return getInclude(fileName, null);
	}

	public IModelElement[] getInclude(String fileName, IDLTKSearchScope scope) {
		IMixinElement[] elements = model.find(new StringBuilder(fileName).append(PHPMixinParser.INCLUDE_SUFFIX).toString());
		return filterElements(elements, PHPMixinElementInfo.K_INCLUDE, scope);
	}
}
