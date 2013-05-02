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
package org.eclipse.php.internal.ui.actions.format;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IWorkbenchSiteProgressService;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;

/**
 * @author nir.c FormatActionDelegate class extends WST's
 *         'FormatActionDelegate', and actually "translates" the DLTK model
 *         based selection in the PHP explorer, to an Eclipse-based
 *         (IModelElement -> IResource).
 */
public class FormatActionDelegate extends
		org.eclipse.wst.sse.ui.internal.actions.FormatActionDelegate {
	private IWorkbenchSiteProgressService getActiveProgressService() {
		IWorkbenchSiteProgressService service = null;
		if (PlatformUI.isWorkbenchRunning()) {
			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow();
			if (activeWorkbenchWindow != null) {
				IWorkbenchPage activePage = activeWorkbenchWindow
						.getActivePage();
				if (activePage != null) {
					IWorkbenchPart activePart = activePage.getActivePart();
					if (activePart != null) {
						service = (IWorkbenchSiteProgressService) activePart
								.getSite().getAdapter(
										IWorkbenchSiteProgressService.class);
					}
				}
			}
		}
		return service;
	}

	class FormatJob extends Job {

		public FormatJob(String name) {
			super(name);
		}

		protected IStatus run(IProgressMonitor monitor) {
			IStatus status = Status.OK_STATUS;

			Object[] elements = fSelection.toArray();
			monitor.beginTask("", elements.length); //$NON-NLS-1$ 
			for (int i = 0; i < elements.length; i++) {
				IResource resource = null;
				if (elements[i] instanceof IModelElement) {
					resource = ((IModelElement) elements[i]).getResource();

				} else if (elements[i] instanceof IResource) {
					resource = (IResource) elements[i];
					monitor.worked(1);
				}
				if (resource != null) {
					process(new SubProgressMonitor(monitor, 1), resource);
				}
				monitor.worked(1);
			}
			monitor.done();

			if (fErrorStatus.getChildren().length > 0) {
				status = fErrorStatus;
				fErrorStatus = new MultiStatus(SSEUIPlugin.ID, IStatus.ERROR,
						SSEUIMessages.FormatActionDelegate_errorStatusMessage,
						null);
			}

			return status;
		}

	}

	private MultiStatus fErrorStatus = new MultiStatus(SSEUIPlugin.ID,
			IStatus.ERROR,
			SSEUIMessages.FormatActionDelegate_errorStatusMessage, null);

	protected Job getJob() {
		return new FormatJob(SSEUIMessages.FormatActionDelegate_jobName);
	}

	// @Override
	// public void run(IAction action) {
	// if (fSelection != null && !fSelection.isEmpty()) {
	// Job job = getJob();
	// if (job != null) {
	// IWorkbenchSiteProgressService progressService =
	// getActiveProgressService();
	// if (progressService != null) {
	// progressService.schedule(job);
	// } else {
	// job.schedule();
	// }
	// }
	// }
	// }

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			fSelection = (IStructuredSelection) selection;
			boolean available = false;

			Object[] elements = fSelection.toArray();
			for (int i = 0; i < elements.length; i++) {
				if (elements[i] instanceof IResource) {
					IResource resource = (IResource) elements[i];
					available = (null != resource) ? processorAvailable(resource)
							: false;

					if (available)
						break;
				}
				if (elements[i] instanceof IModelElement) {
					IResource resource = ((IModelElement) elements[i])
							.getResource();
					available = (null != resource) ? processorAvailable(resource)
							: false;

					if (available)
						break;
				}
			}

			action.setEnabled(available);
		}
	}

}
