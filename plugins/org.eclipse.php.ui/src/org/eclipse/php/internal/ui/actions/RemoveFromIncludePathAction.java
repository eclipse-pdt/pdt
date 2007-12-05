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
package org.eclipse.php.internal.ui.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.core.phpModel.parser.*;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.IWorkbenchSite;

public class RemoveFromIncludePathAction extends SelectionDispatchAction {

	public RemoveFromIncludePathAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.getString("RemoveFromIncludePathAction_remove_from_include_path_title"));
		setToolTipText(PHPUIMessages.getString("RemoveFromIncludePathAction_remove_from_include_path_tooltip"));
		setDescription(PHPUIMessages.getString("RemoveFromIncludePathAction_remove_from_include_path_desc"));
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(ITextSelection selection) {
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(checkEnabled(selection));
	}

	private boolean checkEnabled(IStructuredSelection selection) {
		if (selection.isEmpty())
			return false;

		Object obj = selection.getFirstElement();
		if (obj instanceof PHPIncludePathModel) {
			return true;
		}

		return false;
	}

	/* (non-Javadoc)
	 * Method declared on SelectionDispatchAction.
	 */
	public void run(IStructuredSelection selection) {
		Object obj = selection.getFirstElement();
		if (obj instanceof PHPIncludePathModel) {
			IProject project = null;
			final PHPIncludePathModel includePathModel = (PHPIncludePathModel) obj;
			PHPProjectModel[] projectModels = PHPWorkspaceModelManager.getInstance().listModels();
			for (int i = 0; project == null && i < projectModels.length; ++i) {
				PHPIncludePathModelManager includePathModelManager = (PHPIncludePathModelManager) projectModels[i].getModel(PHPIncludePathModelManager.COMPOSITE_INCLUDE_PATH_MODEL_ID);
				if (includePathModelManager != null) {
					IPhpModel[] models = includePathModelManager.listModels();
					for (int j = 0; project == null && j < models.length; ++j) {
						if (models[j] == includePathModel) {
							project = PHPWorkspaceModelManager.getInstance().getProjectForModel(projectModels[i]);
						}
					}
				} 
			}
			if (project != null) {
				final PHPProjectOptions options = PHPProjectOptions.forProject(project);
				WorkspaceJob configureIncludePathJob = new WorkspaceJob(PHPUIMessages.getString("RemoveFromIncludePathAction_remove_from_include_path_job")) {
					public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
						IIncludePathEntry[] includePathEntries = options.readRawIncludePath();
						List newIncludePathEntries = new LinkedList();
						for (int i = 0; i < includePathEntries.length; ++i) {
							if (!includePathEntries[i].getPath().equals(new Path(includePathModel.getID()))) {
								newIncludePathEntries.add(includePathEntries[i]);
							}
						}
						options.setRawIncludePath((IIncludePathEntry[]) newIncludePathEntries.toArray(new IIncludePathEntry[newIncludePathEntries.size()]), new SubProgressMonitor(monitor, 1));
						return Status.OK_STATUS;
					}
				};
				configureIncludePathJob.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(project));
				configureIncludePathJob.schedule();
			}
		}
	}
}
