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
package org.eclipse.php.internal.ui.importer;

import java.io.File;
import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.core.libfolders.LibraryFolderManager;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.ui.wizards.datatransfer.ProjectConfigurator;

public class PHPProjectConfigurator implements ProjectConfigurator {

	private static final String COMPOSER_JSON = "composer.json"; //$NON-NLS-1$
	private static final String VENDOR_DIRECTORY = "vendor"; //$NON-NLS-1$

	private static final Set<String> SEARCHED_FILES = new HashSet<>(
			Arrays.asList(new String[] { "index.php", COMPOSER_JSON })); //$NON-NLS-1$

	@Override
	public Set<File> findConfigurableLocations(File root, IProgressMonitor monitor) {
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
					} else if (file.isFile()) {
						if (SEARCHED_FILES.contains(file.getName())) {
							return Collections.singleton(current);
						}
					}
				}
			}
		}
		return Collections.emptySet();
	}

	@Override
	public boolean canConfigure(IProject project, Set<IPath> ignoredPaths, IProgressMonitor monitor) {
		return shouldBeAnEclipseProject(project, monitor);
	}

	@Override
	public void configure(IProject project, Set<IPath> ignoredPaths, IProgressMonitor monitor) {
		try {
			if (PHPToolkitUtil.isPHPProject(project)) {
				return;
			}
			String[] natureIds = new String[] { PHPNature.ID };
			if (null != natureIds) {
				IProjectDescription desc = project.getDescription();
				desc.setNatureIds(natureIds);
				project.setDescription(desc, null);
			}

			if (project.getFile(COMPOSER_JSON).exists()) {
				IFolder vendorFolder = project.getFolder(VENDOR_DIRECTORY);
				// mark as library folder always because 'vendor' may not exist
				// yet
				LibraryFolderManager.getInstance().useAsLibraryFolder(new IFolder[] { vendorFolder },
						SubMonitor.convert(monitor, 1));
			}
		} catch (OperationCanceledException | InterruptedException | CoreException e) {
			Logger.logException(e);
		}
	}

	@Override
	public boolean shouldBeAnEclipseProject(IContainer container, IProgressMonitor monitor) {
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
	public Set<IFolder> getFoldersToIgnore(IProject project, IProgressMonitor monitor) {
		return null;
	}

}
