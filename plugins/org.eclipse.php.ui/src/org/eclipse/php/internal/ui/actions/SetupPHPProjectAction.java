/*******************************************************************************
 * Copyright (c) 2007, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.project.PHPNature;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.progress.IProgressService;

/**
 * Not API
 */
public class SetupPHPProjectAction implements IObjectActionDelegate, IActionDelegate {

	IWorkbenchPart fPart;
	Object[] fTarget;

	private void doInstall(IProject project, IProgressMonitor monitor) {
		try {
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = PHPNature.ID;
			description.setNatureIds(newNatures);
			project.setDescription(description, monitor);
		} catch (CoreException ex) {
			Logger.logException(ex);
		}
	}

	private void install(final IProject project) {
		IProgressService service = null;
		if (fPart != null) {
			service = (IProgressService) fPart.getSite().getService(IProgressService.class);
		}
		if (service == null) {
			doInstall(project, null);
		} else {
			IRunnableWithProgress runnable = new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					doInstall(project, monitor);
				}
			};
			try {
				service.run(false, false, runnable);
			} catch (InvocationTargetException e) {
				Logger.logException(e);
			} catch (InterruptedException e) {
				Logger.logException(e);
			}
		}
	}

	@Override
	public void run(IAction action) {
		if (fTarget == null)
			return;

		for (int i = 0; i < fTarget.length; i++) {
			if (fTarget[i] instanceof IResource) {
				final IProject project = ((IResource) fTarget[i]).getProject();
				if (!DLTKLanguageManager.hasScriptNature(project)) {
					install(project);
				}
			}
		}

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			fTarget = ((IStructuredSelection) selection).toArray();
			boolean enabled = true;
			for (Object obj : fTarget) {
				if (!(obj instanceof IProject)) {
					enabled = false;
					break;
				}
				IProject project = (IProject) obj;
				if (!project.isAccessible() || DLTKLanguageManager.hasScriptNature(project)) {
					enabled = false;
					break;
				}
			}
			action.setEnabled(enabled);
		} else {
			fTarget = null;
			action.setEnabled(false);
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		fPart = targetPart;
	}
}