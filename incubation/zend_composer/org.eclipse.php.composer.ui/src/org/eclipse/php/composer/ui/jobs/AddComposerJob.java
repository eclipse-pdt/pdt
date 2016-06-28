/*******************************************************************************
 * Copyright (c) 2013, 2016 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.composer.ui.jobs;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.php.composer.core.ComposerService;
import org.eclipse.php.composer.internal.ui.ComposerUIPlugin;
import org.eclipse.php.composer.internal.ui.editors.ComposerJsonEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

/**
 * @author Wojciech Galanciak, 2013
 * 
 */
public class AddComposerJob extends WorkspaceJob {

	private ComposerService composer;

	public AddComposerJob(ComposerService composer) {
		super(Messages.AddComposerJob_Name);
		this.composer = composer;
		setUser(false);
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) {
		monitor.beginTask(Messages.AddComposerJob_Title, IProgressMonitor.UNKNOWN);
		composer.addComposer(monitor);
		try {
			IContainer project = composer.getRoot();
			project.refreshLocal(IResource.DEPTH_ONE, monitor);
			if (ComposerService.isComposerProject(project)) {
				ComposerService.excludeTestPaths(project.getProject());
				openComposerEditor(project);
			}
		} catch (CoreException e) {
			return new Status(IStatus.ERROR, ComposerUIPlugin.PLUGIN_ID, Messages.InstallDependenciesJob_Error, e);
		}
		monitor.done();
		return Status.OK_STATUS;
	}

	private void openComposerEditor(final IContainer root) {
		final IProject project = root.getProject();
		IWorkbench wb = PlatformUI.getWorkbench();
		wb.getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbench wb = PlatformUI.getWorkbench();
				IWorkbenchWindow window = wb.getActiveWorkbenchWindow();

				if (window != null) {
					IFile composerJson = project.getFile(ComposerService.COMPOSER_JSON);
					try {
						window.getActivePage().openEditor(new FileEditorInput(composerJson), ComposerJsonEditor.ID);
					} catch (PartInitException e) {
						ComposerUIPlugin.logError(e);
					}
				}
			}
		});
	}

}
