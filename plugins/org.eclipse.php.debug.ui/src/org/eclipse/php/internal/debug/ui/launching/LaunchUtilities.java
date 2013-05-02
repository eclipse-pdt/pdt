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
package org.eclipse.php.internal.debug.ui.launching;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.debug.ui.PHPDebugUIMessages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class LaunchUtilities {

	/**
	 * Returns the standard display to be used. The method first checks, if the
	 * thread calling this method has an associated display. If so, this display
	 * is returned. Otherwise the method returns the default display.
	 * 
	 * @return the display
	 */
	public static Display getStandardDisplay() {
		Display display = Display.getCurrent();
		if (display == null)
			display = Display.getDefault();

		return display;
	}

	/**
	 * Return a shell for the workbench.
	 * 
	 * @return org.eclipse.swt.widgets.Shell
	 */
	public static Shell getShell() {
		return getStandardDisplay().getActiveShell();
	}

	/**
	 * Open a dialog window.
	 * 
	 * @param message
	 *            java.lang.String
	 */
	public static void openError(final String message) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Shell shell = getShell();
				MessageDialog.openError(shell,
						PHPDebugUIMessages.LaunchUtilities_error, message);
			}
		});
	}

	/**
	 * Open a dialog window.
	 * 
	 * @param message
	 *            java.lang.String
	 * @param status
	 *            IStatus
	 */
	public static void openError(final String message, final IStatus status) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				Shell shell = getShell();
				ErrorDialog.openError(shell,
						PHPDebugUIMessages.LaunchUtilities_error, message,
						status); 
			}
		});
	}

	/**
	 * Open a dialog window.
	 * 
	 * @param shell
	 *            the shell
	 * @param message
	 *            the message
	 */
	public static void openError(Shell shell, String message) {
		MessageDialog.openError(shell,
				PHPDebugUIMessages.LaunchUtilities_error, message); 
	}

	/**
	 * Open a dialog window.
	 * 
	 * @param shell
	 *            a shell
	 * @param message
	 *            a message
	 * @param status
	 *            a status
	 */
	public static void openError(Shell shell, String message, IStatus status) {
		ErrorDialog.openError(shell, PHPDebugUIMessages.LaunchUtilities_error,
				message, status); 
	}

	public static String getProjectFromDialog(Shell shell,
			String[] requiredNatures) {
		ProjectSelectionDialog dialog = new ProjectSelectionDialog(shell,
				requiredNatures, PHPDebugUIMessages.LaunchUtilities_projects,
				PHPDebugUIMessages.LaunchUtilities_selectProject);
		dialog.open();
		Object[] resource = dialog.getResult();
		String text = null;
		if (resource != null && resource.length > 0) {
			Object obj = resource[0];
			IPath path = null;

			if (obj instanceof IPath)
				path = (IPath) resource[0];
			else if (obj instanceof IProject)
				path = ((IProject) obj).getFullPath();

			text = path.makeRelative().toString();
		}

		return text;
	}

	/**
	 * Returns a selected {@link IResource} from a user dialog.
	 * 
	 * @param project
	 *            The project to display
	 * @param shell
	 *            A Shell
	 * @param fileExtensions
	 *            The required file extension
	 * @param requiredNatures
	 *            The required nature
	 * @param allowExternalFiles
	 *            Allow selection from an external files that are currently
	 *            opened in the editor
	 * @return A selected IResource
	 */
	public static IResource getFileFromDialog(IProject project, Shell shell,
			String[] fileExtensions, String[] requiredNatures,
			boolean allowExternalFiles) {
		LaunchUtilities inst = new LaunchUtilities();

		ApplicationFileSelectionDialog d = new ApplicationFileSelectionDialog(
				shell, inst.new WebLaunchLabelProvider(),
				PHPDebugUIMessages.LaunchUtilities_selectFile,
				PHPDebugUIMessages.LaunchUtilities_selectProjectFile,
				fileExtensions, requiredNatures, false, allowExternalFiles);

		if (project != null) {
			d.setInput(project);
			d.setInitialSelection(project);
		} else {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			d.setInput(root);
			d.setInitialSelection(root);
		}

		d.setAllowMultiple(false);
		d.setComparator(new ResourceSorter(ResourceSorter.TYPE));
		d.open();

		if (d.getReturnCode() == Window.OK) {
			Object[] targetFiles = d.getResult();
			Object target = targetFiles[0];

			return (IResource) target;
		}

		return null;
	}

	private class WebLaunchLabelProvider extends
			org.eclipse.ui.model.WorkbenchLabelProvider {

		protected String decorateText(String input, Object element) {
			if (element instanceof IFile) {
				IFile file = (IFile) element;
				String filename = file.getName();
				return filename;
			}
			return input;
		}
	}
}
