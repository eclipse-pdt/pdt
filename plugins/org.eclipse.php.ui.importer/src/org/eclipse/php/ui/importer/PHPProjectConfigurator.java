/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.ui.importer;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPLanguageToolkit;
import org.eclipse.php.internal.core.PHPToolkitUtil;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.wizards.datatransfer.ProjectConfigurator;

public class PHPProjectConfigurator implements ProjectConfigurator {

	private static Set<String> SEARCHED_FILES = new HashSet<>(
			Arrays.asList(new String[] { "index.php", "composer.json" })); //$NON-NLS-1$ //$NON-NLS-2$

	@Override
	public Set<File> findConfigurableLocations(File root, IProgressMonitor monitor) {
		Set<File> res = new HashSet<>();
		LinkedList<File> directoriesToProcess = new LinkedList<>();
		directoriesToProcess.addFirst(root);
		while (!directoriesToProcess.isEmpty()) {
			if (monitor.isCanceled()) {
				return Collections.emptySet();
			}
			File current = directoriesToProcess.pop();
			if (current.isDirectory()) {
				for (File file : current.listFiles()) {
					if (file.isDirectory()) {
						directoriesToProcess.add(file);
					} else if (file.isFile() && SEARCHED_FILES.contains(file.getName())) {
						res.add(current);
					}
				}
			}
		}
		return res;
	}

	@Override
	public boolean canConfigure(IProject project, Set<IPath> arg1, IProgressMonitor monitor) {
		return shouldBeAnEclipseProject(project, monitor);
	}

	@Override
	public void configure(IProject project, Set<IPath> arg1, IProgressMonitor arg2) {
		try {
			if (PHPToolkitUtil.isPhpProject(project)) {
				return;
			}
			String[] natureIds = new String[] { PHPNature.ID };
			if (null != natureIds) {
				IProjectDescription desc = project.getDescription();
				desc.setNatureIds(natureIds);
				project.setDescription(desc, null);
			}
		} catch (CoreException e) {
			Logger.logException(e);
		}
	}

	@Override
	public boolean shouldBeAnEclipseProject(IContainer container, IProgressMonitor arg1) {
		boolean shouldBeProject = false;
		for (String filename : SEARCHED_FILES) {
			IFile file = container.getFile(new Path(filename));
			if (file.exists()) {
				shouldBeProject = true;
				break;
			}
		}
		return shouldBeProject;
	}

	@Override
	public Set<IFolder> getDirectoriesToIgnore(IProject arg0, IProgressMonitor arg1) {
		return null;
	}

	@Override
	public IWizard getConfigurationWizard() {
		return null;
	}

}
