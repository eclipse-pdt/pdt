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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.TypedViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Class that gives access to dialogs used by the PHP include path page to configure include path entries
 * and properties of include path entries.
 * Static methods are provided to show dialogs for:
 * <ul>
 *  <li> configuration of source attachments</li>
 *  <li> configuration of Javadoc locations</li>
 *  <li> configuration and selection of include path variable entries</li>
 *  <li> configuration and selection of include path container entries</li>
 *  <li> selection of class and source folders</li>
 * </ul>
 * <p>
 * This class is not intended to be instantiated or subclassed by clients.
 * </p>
 * @since 3.0
 */
public final class IncludePathDialogAccess {
	public static final String DIALOGSTORE_LASTVARIABLE = PHPUiPlugin.ID + ".lastvariable"; //$NON-NLS-1$
	public static final String DIALOGSTORE_LASTINCLUDEFOLDER = PHPUiPlugin.ID + ".lastincludefolder"; //$NON-NLS-1$

	private IncludePathDialogAccess() {
		// do not instantiate
	}

	/**
	 * Shows the UI for configuring a variable include path entry. See {@link IIncludePathEntry#IPE_VARIABLE} for
	 * details about variable include path entries.
	 * The dialog returns the configured include path entry path or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param initialEntryPath The initial variable include path variable path or <code>null</code> to use
	 * an empty path. 
	 * @param existingPaths An array of paths that are already on the include path and therefore should not be
	 * selected again.
	 * @return Returns the configures include path entry path or <code>null</code> if the dialog has
	 * been canceled.
	 */
	public static IPath configureVariableEntry(Shell shell, IPath initialEntryPath, IPath[] existingPaths) {
		if (existingPaths == null) {
			throw new IllegalArgumentException();
		}

		EditVariableEntryDialog dialog = new EditVariableEntryDialog(shell, initialEntryPath, existingPaths);
		if (dialog.open() == Window.OK) {
			return dialog.getPath();
		}
		return null;
	}

	/**
	 * Shows the UI for selecting new variable include path entries. See {@link IIncludePathEntry#IPE_VARIABLE} for
	 * details about variable include path entries.
	 * The dialog returns an array of the selected variable entries or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param existingPaths An array of paths that are already on the include path and therefore should not be
	 * selected again.
	 * @return Returns an non empty array of the selected variable entries or <code>null</code> if the dialog has
	 * been canceled.
	 */
	public static IPath[] chooseVariableEntries(Shell shell, IPath[] existingPaths) {
		if (existingPaths == null) {
			throw new IllegalArgumentException();
		}
		NewVariableEntryDialog dialog = new NewVariableEntryDialog(shell);
		if (dialog.open() == Window.OK) {
			return dialog.getResult();
		}
		return null;
	}

	/**
	 * Shows the UI to configure a include path container include path entry. See {@link IIncludePathEntry#IPE_CONTAINER} for
	 * details about container include path entries.
	 * The dialog returns the configured include path entry or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param initialEntry The initial include path container entry.
	 * @param project The project the entry belongs to. The project does not have to exist and can also be <code>null</code>.
	 * @param currentIncludePath The include path entries currently selected to be set as the projects include path. This can also
	 * include the entry to be edited. The dialog uses these entries as information only (e.g. to avoid duplicate entries); The user still can make changes after the
	 * the include path container dialog has been closed. See {@link IIncludePathContainerPageExtension} for
	 * more information.
	 * @return Returns the configured include path container entry or <code>null</code> if the dialog has
	 * been canceled by the user.
	 */
	public static IIncludePathEntry configureContainerEntry(Shell shell, IIncludePathEntry initialEntry, IProject project, IIncludePathEntry[] currentIncludePath) {
		if (initialEntry == null || currentIncludePath == null) {
			throw new IllegalArgumentException();
		}

		IncludePathContainerWizard wizard = new IncludePathContainerWizard(initialEntry, project, currentIncludePath);
		if (IncludePathContainerWizard.openWizard(shell, wizard) == Window.OK) {
			IIncludePathEntry[] created = wizard.getNewEntries();
			if (created != null && created.length == 1) {
				return created[0];
			}
		}
		return null;
	}

	/**
	 * Shows the UI to choose new include path container include path entries. See {@link IIncludePathEntry#IPE_CONTAINER} for
	 * details about container include path entries.
	 * The dialog returns the selected include path entries or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param project The project the entry belongs to. The project does not have to exist and
	 * can also be <code>null</code>.
	 * @param currentIncludePath The include path entries currently selected to be set as the projects include path. This can also
	 * include the entry to be edited. The dialog uses these entries as information only; The user still can make changes after the
	 * the include path container dialog has been closed. See {@link IIncludePathContainerPageExtension} for
	 * more information.
	 * @return Returns the selected include path container entries or <code>null</code> if the dialog has
	 * been canceled by the user.
	 */
	public static IIncludePathEntry[] chooseContainerEntries(Shell shell, IProject project, IIncludePathEntry[] currentIncludePath) {
		if (currentIncludePath == null) {
			throw new IllegalArgumentException();
		}

		IncludePathContainerWizard wizard = new IncludePathContainerWizard((IIncludePathEntry) null, project, currentIncludePath);
		if (IncludePathContainerWizard.openWizard(shell, wizard) == Window.OK) {
			return wizard.getNewEntries();
		}
		return null;
	}

	public static IPath[] chooseIncludePathFoldersEntries(Shell shell) {
		String lastUsedPath = PHPUiPlugin.getDefault().getDialogSettings().get(DIALOGSTORE_LASTINCLUDEFOLDER);
		
		if (lastUsedPath == null) {
			lastUsedPath = ""; //$NON-NLS-1$
		}
		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SINGLE);
		dialog.setText(PHPUIMessages.IncludePathDialogAccess_IncludePathFolderDialog_new_title);
		dialog.setMessage(PHPUIMessages.IncludePathDialogAccess_IncludePathFolderDialog_new_description);
		dialog.setFilterPath(lastUsedPath);
		
		String res = dialog.open();
		if (res == null) {
			return null;
		}
		
		IPath path = new Path(res).makeAbsolute();
		PHPUiPlugin.getDefault().getDialogSettings().put(DIALOGSTORE_LASTINCLUDEFOLDER, path.removeLastSegments(1).toOSString());
		
		return new IPath[]{path};
	}
	
	/**
	 * Shows the UI to configure include path folder entry.
	 * The dialog returns the configured or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param initialEntry The path of the initial include path folder entry.
	 * @return Returns the configured include path container entry path or <code>null</code> if the dialog has
	 * been canceled by the user.
	 */
	public static IPath configureIncludePathFolderEntry(Shell shell, IPath initialEntry) {
		if (initialEntry == null) {
			throw new IllegalArgumentException();
		}

		String lastUsedPath = initialEntry.removeLastSegments(1).toOSString();

		DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SINGLE);
		dialog.setText(PHPUIMessages.IncludePathDialogAccess_0);
		dialog.setFilterPath(lastUsedPath);

		String res = dialog.open();
		if (res == null) {
			return null;
		}
		IPath path = new Path(res).makeAbsolute();
		PHPUiPlugin.getDefault().getDialogSettings().put(DIALOGSTORE_LASTINCLUDEFOLDER, path.removeLastSegments(1).toOSString());
		return path;
	}

	/**
	 * Shows the UI to select new  folders.
	 * The dialog returns the selected include path entry paths or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param initialSelection The path of the element to initially select or <code>null</code>.
	 * @param usedEntries An array of paths that are already on the include path and therefore should not be
	 * selected again.
	 * @return Returns the configured include path container entry path or <code>null</code> if the dialog has
	 * been canceled by the user.
	 */
	public static IPath[] chooseFolderEntries(Shell shell, IPath initialSelection, IPath[] usedEntries) {
		if (usedEntries == null) {
			throw new IllegalArgumentException();
		}
		String title = PHPUIMessages.IncludePathDialogAccess_ExistingPHPFolderDialog_new_title;
		String message = PHPUIMessages.IncludePathDialogAccess_ExistingPHPFolderDialog_new_description;
		return internalChooseFolderEntry(shell, initialSelection, usedEntries, title, message);
	}

	/**
	 * Shows the UI to select new source folders.
	 * The dialog returns the selected include path entry paths or <code>null</code> if the dialog has
	 * been canceled The dialog does not apply any changes.
	 * 
	 * @param shell The parent shell for the dialog.
	 * @param initialSelection The path of the element to initially select or <code>null</code>
	 * @param usedEntries An array of paths that are already on the include path and therefore should not be
	 * selected again.
	 * @return Returns the configured include path container entry path or <code>null</code> if the dialog has
	 * been canceled by the user.
	 */
	public static IPath[] chooseSourceFolderEntries(Shell shell, IPath initialSelection, IPath[] usedEntries) {
		if (usedEntries == null) {
			throw new IllegalArgumentException();
		}
		String title = PHPUIMessages.IncludePathDialogAccess_ExistingSourceFolderDialog_new_title;
		String message = PHPUIMessages.IncludePathDialogAccess_ExistingSourceFolderDialog_new_description;
		return internalChooseFolderEntry(shell, initialSelection, usedEntries, title, message);
	}

	private static IPath[] internalChooseFolderEntry(Shell shell, IPath initialSelection, IPath[] usedEntries, String title, String message) {
		Class[] acceptedClasses = new Class[] { IProject.class, IFolder.class };

		ArrayList usedContainers = new ArrayList(usedEntries.length);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (int i = 0; i < usedEntries.length; i++) {
			IResource resource = root.findMember(usedEntries[i]);
			if (resource instanceof IContainer) {
				usedContainers.add(resource);
			}
		}

		IResource focus = initialSelection != null ? root.findMember(initialSelection) : null;
		Object[] used = usedContainers.toArray();

		MultipleFolderSelectionDialog dialog = new MultipleFolderSelectionDialog(shell, new WorkbenchLabelProvider(), new WorkbenchContentProvider());
		dialog.setExisting(used);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.addFilter(new TypedViewerFilter(acceptedClasses, used));
		dialog.setInput(root);
		dialog.setInitialFocus(focus);

		if (dialog.open() == Window.OK) {
			Object[] elements = dialog.getResult();
			IPath[] res = new IPath[elements.length];
			for (int i = 0; i < res.length; i++) {
				IResource elem = (IResource) elements[i];
				res[i] = elem.getFullPath();
			}
			return res;
		}
		return null;
	}
}
