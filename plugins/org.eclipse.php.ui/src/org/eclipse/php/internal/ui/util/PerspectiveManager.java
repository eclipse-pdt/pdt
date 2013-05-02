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
package org.eclipse.php.internal.ui.util;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.osgi.util.NLS;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;

/**
 * This class has utilities related to switching between perspectives
 * 
 * @author Eden K.,2007
 * 
 */
public class PerspectiveManager {

	/**
	 * Flag used to indicate that the user is already being prompted to switch
	 * perspectives. This flag allows us to not open multiple prompts at the
	 * same time.
	 */
	public static boolean fPrompting;

	/**
	 * Switches to the specified perspective
	 * 
	 * @param id
	 *            perspective identifier
	 */
	public static void switchToPerspective(IWorkbenchWindow window, String id) {
		try {
			window.getWorkbench().showPerspective(id, window);
		} catch (WorkbenchException e) {
			MessageDialog.openError(window.getShell(),
					PHPUIMessages.PerspectiveManager_PerspectiveError_Title,
					PHPUIMessages.PerspectiveManager_PerspectiveError_Message); 
		}
	}

	/**
	 * Returns whether or not the user wishes to switch to the specified
	 * perspective when a launch occurs.
	 * 
	 * @param window
	 * @param perspectiveId
	 * 
	 * @return whether or not the user wishes to switch to the specified
	 *         perspective automatically
	 */
	public static boolean shouldSwitchPerspective(IWorkbenchWindow window,
			String perspectiveId) {

		String perspectiveName = getPerspectiveLabel(perspectiveId);
		String message = NLS.bind(
				PHPUIMessages.PerspectiveManager_Switch_Dialog_Message,
				perspectiveName); 

		final String preferenceKey = perspectiveId + ".switch_to_perspective"; //$NON-NLS-1$

		if (isCurrentPerspective(window, perspectiveId)) {
			return false;
		}

		if (perspectiveName == null) {
			return false;
		}

		String switchPerspective = PHPUiPlugin.getDefault()
				.getPreferenceStore().getString(preferenceKey);
		if (MessageDialogWithToggle.ALWAYS.equals(switchPerspective)) {
			return true;
		} else if (MessageDialogWithToggle.NEVER.equals(switchPerspective)) {
			return false;
		}

		Shell shell = window.getShell();
		if (shell == null || fPrompting) {
			return false;
		}
		fPrompting = true;
		// Activate the shell if necessary so the prompt is visible
		if (shell.getMinimized()) {
			shell.setMinimized(false);
		}

		MessageDialogWithToggle dialog = MessageDialogWithToggle
				.openYesNoQuestion(shell,
						PHPUIMessages.PerspectiveManager_Switch_Dialog_Title,
						message, null, false, PHPUiPlugin.getDefault()
								.getPreferenceStore(), preferenceKey); 
		boolean answer = (dialog.getReturnCode() == IDialogConstants.YES_ID);
		synchronized (PerspectiveManager.class) {
			fPrompting = false;
			PerspectiveManager.class.notifyAll();
		}
		if (isCurrentPerspective(window, perspectiveId)) {
			// While prompting in response to one event (say, a launch),
			// another event can occur which changes the perspective.
			// Double-check that we're not in the right perspective.
			answer = false;
		}
		return answer;
	}

	/**
	 * Returns whether the given perspective identifier matches the identifier
	 * of the current perspective.
	 * 
	 * @param perspectiveId
	 *            the identifier
	 * @return whether the given perspective identifier matches the identifier
	 *         of the current perspective
	 */
	public static boolean isCurrentPerspective(IWorkbenchWindow window,
			String perspectiveId) {
		boolean isCurrent = false;
		if (window != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IPerspectiveDescriptor perspectiveDescriptor = page
						.getPerspective();
				if (perspectiveDescriptor != null) {
					isCurrent = perspectiveId.equals(perspectiveDescriptor
							.getId());
				}
			}
		}
		return isCurrent;
	}

	/**
	 * Returns the label of the perspective with the given identifier or
	 * <code>null</code> if no such perspective exists.
	 * 
	 * @param perspectiveId
	 *            the identifier
	 * @return the label of the perspective with the given identifier or
	 *         <code>null</code> if no such perspective exists
	 */
	public static String getPerspectiveLabel(String perspectiveId) {
		IPerspectiveDescriptor newPerspective = PlatformUI.getWorkbench()
				.getPerspectiveRegistry().findPerspectiveWithId(perspectiveId);
		if (newPerspective == null) {
			return null;
		}
		return newPerspective.getLabel();
	}

}
