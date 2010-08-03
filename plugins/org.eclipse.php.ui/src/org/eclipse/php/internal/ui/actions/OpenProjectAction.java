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
package org.eclipse.php.internal.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.dltk.ui.ModelElementLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.ui.IPHPHelpContextIds;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ExceptionHandler;
import org.eclipse.ui.IWorkbenchSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.OpenResourceAction;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

;

/**
 * Action to open a closed project. Action either opens the closed projects
 * provided by the structured selection or presents a dialog from which the user
 * can select the projects to be opened.
 * 
 * <p>
 * This class may be instantiated; it is not intended to be subclassed.
 * </p>
 * 
 * @since 2.0
 */
public class OpenProjectAction extends SelectionDispatchAction implements
		IResourceChangeListener {

	private static final int EMPTY_SELECTION = 1;
	private static final int ELEMENT_SELECTION = 2;

	private int fMode;
	private OpenResourceAction fWorkbenchAction;

	/**
	 * Creates a new <code>OpenProjectAction</code>. The action requires that
	 * the selection provided by the site's selection provider is of type <code>
	 * org.eclipse.jface.viewers.IStructuredSelection</code> .
	 * 
	 * @param site
	 *            the site providing context information for this action
	 */
	public OpenProjectAction(IWorkbenchSite site) {
		super(site);
		fWorkbenchAction = new OpenResourceAction(site);
		setText(fWorkbenchAction.getText());
		setToolTipText(fWorkbenchAction.getToolTipText());
		setEnabled(hasCloseProjects());
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IPHPHelpContextIds.PROJECT);
	}

	/*
	 * @see IResourceChangeListener#resourceChanged(IResourceChangeEvent)
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		fWorkbenchAction.resourceChanged(event);
		switch (fMode) {
		case ELEMENT_SELECTION:
			setEnabled(fWorkbenchAction.isEnabled());
			break;
		case EMPTY_SELECTION:
			internalResourceChanged(event);
			break;
		}
	}

	private void internalResourceChanged(IResourceChangeEvent event) {
		IResourceDelta delta = event.getDelta();
		if (delta != null) {
			IResourceDelta[] projDeltas = delta
					.getAffectedChildren(IResourceDelta.CHANGED);
			for (int i = 0; i < projDeltas.length; ++i) {
				IResourceDelta projDelta = projDeltas[i];
				if ((projDelta.getFlags() & IResourceDelta.OPEN) != 0) {
					setEnabled(hasCloseProjects());
					return;
				}
			}
		}
	}

	// ---- normal selection -------------------------------------

	public void selectionChanged(ISelection selection) {
		setEnabled(hasCloseProjects());
		fMode = EMPTY_SELECTION;
	}

	public void run(ISelection selection) {
		internalRun();
	}

	// ---- structured selection ---------------------------------------

	public void selectionChanged(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			setEnabled(hasCloseProjects());
			fMode = EMPTY_SELECTION;
			return;
		}
		fWorkbenchAction.selectionChanged(selection);
		setEnabled(fWorkbenchAction.isEnabled());
		fMode = ELEMENT_SELECTION;
	}

	public void run(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			internalRun();
			return;
		}
		fWorkbenchAction.run();
	}

	private void internalRun() {
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(
				getShell(), new ModelElementLabelProvider());
		dialog.setTitle(PHPUIMessages.OpenProjectAction_dialog_title);
		dialog.setMessage(PHPUIMessages.OpenProjectAction_dialog_message);
		dialog.setElements(getClosedProjects());
		dialog.setMultipleSelection(true);
		int result = dialog.open();
		if (result != Window.OK)
			return;
		final Object[] projects = dialog.getResult();
		IWorkspaceRunnable runnable = createRunnable(projects);
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true,
					new WorkbenchRunnableAdapter(runnable));
		} catch (InvocationTargetException e) {
			ExceptionHandler.handle(e, getShell(),
					PHPUIMessages.OpenProjectAction_dialog_title,
					PHPUIMessages.OpenProjectAction_error_message);
		} catch (InterruptedException e) {
		}
	}

	private IWorkspaceRunnable createRunnable(final Object[] projects) {
		return new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				monitor.beginTask("", projects.length); //$NON-NLS-1$
				MultiStatus errorStatus = null;
				for (int i = 0; i < projects.length; i++) {
					IProject project = (IProject) projects[i];
					try {
						project.open(new SubProgressMonitor(monitor, 1));
					} catch (CoreException e) {
						if (errorStatus == null)
							errorStatus = new MultiStatus(
									PHPUiPlugin.ID,
									IStatus.ERROR,
									PHPUIMessages.OpenProjectAction_error_message,
									e);
						errorStatus.merge(e.getStatus());
					}
				}
				monitor.done();
				if (errorStatus != null)
					throw new CoreException(errorStatus);
			}
		};
	}

	private Object[] getClosedProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		List result = new ArrayList(5);
		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (!project.isOpen())
				result.add(project);
		}
		return result.toArray();
	}

	private boolean hasCloseProjects() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		for (int i = 0; i < projects.length; i++) {
			if (!projects[i].isOpen())
				return true;
		}
		return false;
	}
}
