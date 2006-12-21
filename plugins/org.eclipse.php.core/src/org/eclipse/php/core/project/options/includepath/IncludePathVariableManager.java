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
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.php.core.IIncludePathVariableInitializer;
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
	private ArrayList listeners;

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
		fireIncludePathVariablesChanged(names, paths);
	}

	private void fireIncludePathVariablesChanged(String[] names, IPath[] paths) {
		if (listeners == null || listeners.size() == 0)
			return;
		for (Iterator i = listeners.iterator(); i.hasNext();) {
			((IncludePathVariablesListener) i.next()).includePathVariablesChanged(names, paths);
		}

	}

	public void addListener(IncludePathVariablesListener listener) {
		if (listeners == null) {
			listeners = new ArrayList(1);
		}
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(IncludePathVariablesListener listener) {
		if (listeners == null || listeners.size() == 0)
			return;
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
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
		// Not good since empty paths are allowed!!!
		// assert (names.length == paths.length); 
		for (int i = 0; i < names.length; i++) {
			String path;
			if (i < paths.length) {
				path = paths[i];
			} else {
				path = "";
			}
			variables.put(names[i], new Path(path));
		}

		initExtensionPoints();
	}

	private void initExtensionPoints() {
		Plugin phpCorePlugin = PHPCorePlugin.getDefault();
		if (phpCorePlugin == null)
			return;

		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(PHPCoreConstants.PLUGIN_ID, PHPCoreConstants.IP_VARIABLE_INITIALIZER_EXTPOINT_ID);
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			if ("variable".equals(element.getName())) { //$NON-NLS-1$
				String name = element.getAttribute("name"); //$NON-NLS-1$
				String value = element.getAttribute("value"); //$NON-NLS-1$
				if (element.getAttribute("initializer") != null) { //$NON-NLS-1$
					try {
						IIncludePathVariableInitializer initializer = (IIncludePathVariableInitializer)element.createExecutableExtension("initializer"); //$NON-NLS-1$
						value = initializer.initialize(name);
					} catch (CoreException e) {
						PHPCorePlugin.log(e);
					}
				}
				if (value != null) {
					PHPCorePlugin.setIncludePathVariable(name, new Path(value), null);
				}
			}
		}
	}

	public synchronized void putVariable(String name, IPath path) {
		this.variables.put(name, path);
	}

}
