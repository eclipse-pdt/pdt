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

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.php.internal.ui.util.PHPPluginImages;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;

public class RefreshAction extends SelectionDispatchAction {

	/**
	 * Creates a new <code>RefreshAction</code>. The action requires
	 * that the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code>.
	 * 
	 * @param site the site providing context information for this action
	 */
	public RefreshAction(IWorkbenchSite site) {
		super(site);
		setText(PHPUIMessages.RefreshAction_label);
		setToolTipText(PHPUIMessages.RefreshAction_toolTip);
		PHPPluginImages.setLocalImageDescriptors(this, "refresh_nav.gif");//$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this, IPHPHelpContextIds.REFRESH_ACTION);
	}

	/* (non-Javadoc)
	 * Method declared in SelectionDispatchAction
	 */
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(checkEnabled(selection));
	}

	private boolean checkEnabled(IStructuredSelection selection) {
		if (selection.isEmpty())
			return true;
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IAdaptable) {
				IResource resource = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
				if (resource == null)
					return false;
				if (resource.getType() == IResource.PROJECT && !((IProject) resource).isOpen())
					return false;
			} else {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * Method declared in SelectionDispatchAction
	 */
	public void run(IStructuredSelection selection) {
		final IResource[] resources = getResources(selection);
		IWorkspaceRunnable operation = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask(PHPUIMessages.RefreshAction_progressMessage, resources.length * 2);
				monitor.subTask(""); //$NON-NLS-1$
				List javaElements = new ArrayList(5);
				for (int r = 0; r < resources.length; r++) {
					IResource resource = resources[r];
					if (resource.getType() == IResource.PROJECT) {
						checkLocationDeleted((IProject) resource);
					} else if (resource.getType() == IResource.ROOT) {
						IProject[] projects = ((IWorkspaceRoot) resource).getProjects();
						for (int p = 0; p < projects.length; p++) {
							checkLocationDeleted(projects[p]);
						}
					}
					resource.refreshLocal(IResource.DEPTH_INFINITE, new SubProgressMonitor(monitor, 1));
				}
			}
		};

		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, new WorkbenchRunnableAdapter(operation));
		} catch (InvocationTargetException e) {
			ExceptionHandler.handle(e, getShell(), PHPUIMessages.RefreshAction_error_title, PHPUIMessages.RefreshAction_error_message);
		} catch (InterruptedException e) {
			// canceled
		}
	}

	private IResource[] getResources(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			return new IResource[] { ResourcesPlugin.getWorkspace().getRoot() };
		}

		List result = new ArrayList(selection.size());
		for (Iterator iter = selection.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IAdaptable) {
				IResource resource = (IResource) ((IAdaptable) element).getAdapter(IResource.class);
				if (resource != null)
					result.add(resource);
			}
		}

		for (Iterator iter = result.iterator(); iter.hasNext();) {
			IResource resource = (IResource) iter.next();
			if (isDescendent(result, resource))
				iter.remove();
		}

		return (IResource[]) result.toArray(new IResource[result.size()]);
	}

	private boolean isDescendent(List candidates, IResource element) {
		IResource parent = element.getParent();
		while (parent != null) {
			if (candidates.contains(parent))
				return true;
			parent = parent.getParent();
		}
		return false;
	}

	private void checkLocationDeleted(IProject project) throws CoreException {
		if (!project.exists())
			return;
		File location = project.getLocation().toFile();
		if (!location.exists()) {
			final String message = MessageFormat.format(PHPUIMessages.RefreshAction_locationDeleted_message, //$NON-NLS-1$
				new Object[] { project.getName(), location.getAbsolutePath() });
			final boolean[] result = new boolean[1];
			// Must prompt user in UI thread (we're in the operation thread here).
			getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					result[0] = MessageDialog.openQuestion(getShell(), PHPUIMessages.RefreshAction_locationDeleted_title, message);
				}
			});
			if (result[0]) {
				project.delete(true, true, null);
			}
		}
	}

}
