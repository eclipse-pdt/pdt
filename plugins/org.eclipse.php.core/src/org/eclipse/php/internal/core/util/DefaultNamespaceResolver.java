/*******************************************************************************
 * Copyright (c) 2018 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.util;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IProjectFragment;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.core.util.INamespaceResolver;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class DefaultNamespaceResolver implements INamespaceResolver {
	private final static String EMPTY = ""; //$NON-NLS-1$
	private IProject project;

	public String resolveNamespace(IPath folder) {
		folder = folder.removeFirstSegments(firstSegmentsToRemoveForNamespace(folder));
		if (folder.segmentCount() > 0) {
			String defaultNamespace = folder.toString().replace(IPath.SEPARATOR,
					NamespaceReference.NAMESPACE_SEPARATOR);
			if (defaultNamespace.endsWith(NamespaceReference.NAMESPACE_DELIMITER)) {
				defaultNamespace = defaultNamespace.substring(0, defaultNamespace.length() - 1);
			}
			return defaultNamespace;
		} else {
			return EMPTY;
		}
	}

	@Override
	public IPath resolveLocation(IPath path, String namespaceText) {
		int removedSegmentNumber = firstSegmentsToRemoveForNamespace(path);
		path = path.removeLastSegments(path.segmentCount() - removedSegmentNumber);
		String[] segments = namespaceText.split("\\\\"); //$NON-NLS-1$
		for (String segment : segments) {
			path = path.append(segment);
		}
		return path;
	}

	@Override
	public void init(IProject project) {
		this.project = project;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	private int firstSegmentsToRemoveForNamespace(IPath sourcePath) {
		try {
			if (project == null || !project.exists() || !PHPToolkitUtil.isPHPProject(project)) {
				return 1;
			}
			IScriptProject scriptProject = DLTKCore.create(project);
			for (IProjectFragment projectFragment : scriptProject.getProjectFragments()) {
				int matching = projectFragment.getPath().matchingFirstSegments(sourcePath);
				if (matching > 1) {
					return matching;
				}
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		} catch (CoreException e) {
			PHPCorePlugin.log(e);
		}
		return 1;
	}

}
