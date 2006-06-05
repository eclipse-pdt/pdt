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
package org.eclipse.php.core.project.options.includepath;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.core.IncludePathVariableInitializer;
import org.eclipse.php.core.PHPCoreConstants;
import org.eclipse.php.core.PHPCorePlugin;

public class IncludePathVariableManager {

	private static IncludePathVariableManager instance;

	public static IncludePathVariableManager instance() {
		if (instance == null) {
			instance = new IncludePathVariableManager();
		}
		return instance;
	}

	IPreferenceStore preferenceStore = PHPCorePlugin.getDefault().getPreferenceStore();

	public String[] getReservedIncludePathVariableNames() {
		//		ArrayList list = new ArrayList();
		//		list.addAll(reservedVariables.keySet());
		//		return (String[]) list.toArray(new String[list.size()]);
		return new String[0];
	}

	HashMap variables = new HashMap();

	private IncludePathVariableManager() {
	}

	public IPath getIncludePathVariable(String variableName) {
		return (IPath) variables.get(variableName);
	}

	public void setIncludePathVariables(String[] names, IPath[] paths, SubProgressMonitor monitor) {
		variables.clear();
		StringBuffer namesString = new StringBuffer();
		StringBuffer pathsString = new StringBuffer();
		for (int i = 0; i < names.length; i++) {
			if (paths[i] != null) {
				variables.put(names[i], paths[i]);
				if (i > 0) {
					namesString.append(",");
					pathsString.append(",");
				}
				namesString.append(names[i]);
				pathsString.append(paths[i].toOSString());
			}
		}
		preferenceStore.setValue(PHPCoreConstants.INCLUDE_PATH_VARIABLE_NAMES, namesString.toString());
		preferenceStore.setValue(PHPCoreConstants.INCLUDE_PATH_VARIABLE_PATHS, pathsString.toString());
	}

	public String[] getIncludePathVariableNames() {
		ArrayList list = new ArrayList();
		list.addAll(variables.keySet());
		return (String[]) list.toArray(new String[list.size()]);

	}

	public void startUp() {
		String namesString = preferenceStore.getString(PHPCoreConstants.INCLUDE_PATH_VARIABLE_NAMES);
		String pathsString = preferenceStore.getString(PHPCoreConstants.INCLUDE_PATH_VARIABLE_PATHS);
		String[] names = {};
		if (namesString.length() > 0)
			names = namesString.split(",");
		String[] paths = {};
		if (pathsString.length() > 0)
			paths = pathsString.split(",");
		assert (names.length == paths.length);
		for (int i = 0; i < names.length; i++) {
			IPath path = new Path(paths[i]);
			variables.put(names[i], path);
		}

		initExtensionPoints();
	}

	private void initExtensionPoints() {
		Plugin phpCorePlugin = PHPCorePlugin.getDefault();
		if (phpCorePlugin == null)
			return;

		IExtensionPoint extension = Platform.getExtensionRegistry().getExtensionPoint(PHPCoreConstants.PLUGIN_ID, PHPCoreConstants.IP_VARIABLE_INITIALIZER_EXTPOINT_ID);
		if (extension != null) {
			IExtension[] extensions = extension.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement[] configElements = extensions[i].getConfigurationElements();
				for (int j = 0; j < configElements.length; j++) {
					String varAttribute = configElements[j].getAttribute("variable"); //$NON-NLS-1$
					try {
						Object execExt = configElements[j].createExecutableExtension("class"); //$NON-NLS-1$
						if (execExt instanceof IncludePathVariableInitializer) {
							((IncludePathVariableInitializer) execExt).initialize(varAttribute);
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized void putVariable(String name, IPath path) {
		this.variables.put(name, path);
	}

}
