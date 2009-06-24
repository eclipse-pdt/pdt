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
package org.eclipse.php.internal.core.language;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.core.BuildpathEntry;
import org.eclipse.php.internal.core.Logger;


public class LanguageModelContainer implements IBuildpathContainer {
	
	private IPath containerPath;
	private IBuildpathEntry[] buildPathEntries;

	public LanguageModelContainer(IPath containerPath) {
		this.containerPath = containerPath;
	}

	public IBuildpathEntry[] getBuildpathEntries(IScriptProject project) {
		if (buildPathEntries == null) {
			try {
				List<IBuildpathEntry> entries = new LinkedList<IBuildpathEntry>();
				for (ILanguageModelProvider provider : LanguageModelInitializer.getContributedProviders()) {
					IPath path = provider.getPath(project);
					if (path != null) {
						IEnvironment environment = EnvironmentManager.getEnvironment(project);
						if (environment != null) {
							path = EnvironmentPathUtils.getFullPath(environment, path);
						}
						entries.add(
							DLTKCore.newLibraryEntry(
								path,
								BuildpathEntry.NO_ACCESS_RULES,
								BuildpathEntry.NO_EXTRA_ATTRIBUTES,
								BuildpathEntry.INCLUDE_ALL,
								BuildpathEntry.EXCLUDE_NONE,
								false,
								true
							)
						);
					}
				}
				buildPathEntries = (IBuildpathEntry[]) entries.toArray(new IBuildpathEntry[entries.size()]);
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		return buildPathEntries;
	}

	public IBuiltinModuleProvider getBuiltinProvider(IScriptProject project) {
		return null;
	}

	public String getDescription(IScriptProject project) {
		return LanguageModelInitializer.PHP_LANGUAGE_LIBRARY;
	}

	public int getKind() {
		return K_SYSTEM;
	}

	public IPath getPath() {
		return containerPath;
	}
}