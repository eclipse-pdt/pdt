package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.IUIConstants;
import org.eclipse.dltk.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceComparator;

public class PHPBuildpathDialogAccess {

	/**
	 * Shows the UI to select new JAR or ZIP archive entries located in the
	 * workspace. The dialog returns the selected entries or <code>null</code>
	 * if the dialog has been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell
	 *            The parent shell for the dialog.
	 * @param initialSelection
	 *            The path of the element (container or archive) to initially
	 *            select or <code>null</code> to not select an entry.
	 * @param usedEntries
	 *            An array of paths that are already on the buildpath and
	 *            therefore should not be selected again.
	 * @return Returns the new buildpath container entry paths or
	 *         <code>null</code> if the dialog has been canceled by the user.
	 */
	public static IPath[] chooseArchiveEntries(Shell shell,
			IPath initialSelection, IPath[] usedEntries) {
		if (usedEntries == null) {
			throw new IllegalArgumentException();
		}

		Class[] acceptedClasses = new Class[] { IFile.class };
		TypedElementSelectionValidator validator = new TypedElementSelectionValidator(
				acceptedClasses, true);
		ArrayList usedPhars = new ArrayList(usedEntries.length);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (int i = 0; i < usedEntries.length; i++) {
			IResource resource = root.findMember(usedEntries[i]);
			if (resource instanceof IFile) {
				usedPhars.add(resource);
			}
		}
		IResource focus = initialSelection != null ? root
				.findMember(initialSelection) : null;

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				shell, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setHelpAvailable(false);
		dialog.setValidator(validator);
		dialog
				.setTitle(IncludePathMessages.BuildPathDialogAccess_ZIPArchiveDialog_new_title);
		dialog
				.setMessage(IncludePathMessages.BuildPathDialogAccess_ZIPArchiveDialog_new_description);
		dialog.addFilter(new PHPArchiveFileFilter(usedPhars, true));
		dialog.setInput(root);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		dialog.setInitialSelection(focus);

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

	/**
	 * Shows the UI to configure a JAR or ZIP archive located in the workspace.
	 * The dialog returns the configured buildpath entry path or
	 * <code>null</code> if the dialog has been canceled. The dialog does not
	 * apply any changes.
	 * 
	 * @param shell
	 *            The parent shell for the dialog.
	 * @param initialEntry
	 *            The path of the initial archive entry
	 * @param usedEntries
	 *            An array of paths that are already on the buildpath and
	 *            therefore should not be selected again.
	 * @return Returns the configured buildpath container entry path or
	 *         <code>null</code> if the dialog has been canceled by the user.
	 */
	public static IPath configureArchiveEntry(Shell shell, IPath initialEntry,
			IPath[] usedEntries) {
		if (initialEntry == null || usedEntries == null) {
			throw new IllegalArgumentException();
		}

		Class[] acceptedClasses = new Class[] { IFile.class };
		TypedElementSelectionValidator validator = new TypedElementSelectionValidator(
				acceptedClasses, false);

		ArrayList usedJars = new ArrayList(usedEntries.length);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		for (int i = 0; i < usedEntries.length; i++) {
			IPath curr = usedEntries[i];
			if (!curr.equals(initialEntry)) {
				IResource resource = root.findMember(usedEntries[i]);
				if (resource instanceof IFile) {
					usedJars.add(resource);
				}
			}
		}

		IResource existing = root.findMember(initialEntry);

		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(
				shell, new WorkbenchLabelProvider(),
				new WorkbenchContentProvider());
		dialog.setValidator(validator);
		dialog
				.setTitle(IncludePathMessages.BuildPathDialogAccess_ZIPArchiveDialog_edit_title);
		dialog
				.setMessage(IncludePathMessages.BuildPathDialogAccess_ZIPArchiveDialog_edit_description);
		dialog.addFilter(new PHPArchiveFileFilter(usedJars, true));
		dialog.setInput(root);
		dialog.setComparator(new ResourceComparator(ResourceComparator.NAME));
		dialog.setInitialSelection(existing);

		if (dialog.open() == Window.OK) {
			IResource element = (IResource) dialog.getFirstResult();
			return element.getFullPath();
		}
		return null;
	}

	/**
	 * Shows the UI to select new external JAR or ZIP archive entries. The
	 * dialog returns the selected entry paths or <code>null</code> if the
	 * dialog has been canceled. The dialog does not apply any changes.
	 * 
	 * @param shell
	 *            The parent shell for the dialog.
	 * @return Returns the new buildpath container entry paths or
	 *         <code>null</code> if the dialog has been canceled by the user.
	 */
	public static IPath[] chooseExternalArchiveEntries(Shell shell,
			IEnvironment environment) {
		String lastUsedPath = DLTKUIPlugin.getDefault().getDialogSettings()
				.get(IUIConstants.DIALOGSTORE_LASTEXTZIP);
		if (lastUsedPath == null) {
			lastUsedPath = ""; //$NON-NLS-1$
		}
		FileDialog dialog = new FileDialog(shell, SWT.MULTI);
		dialog
				.setText(IncludePathMessages.BuildPathDialogAccess_ExtZIPArchiveDialog_new_title);
		dialog.setFilterExtensions(PHPArchiveFileFilter.FILTER_EXTENSIONS);
		dialog.setFilterPath(lastUsedPath);
		// IEnvironmentUI ui = (IEnvironmentUI) environment
		// .getAdapter(IEnvironmentUI.class);
		// String res = ui.selectFile(shell, IEnvironmentUI.ARCHIVE);
		// if (res == null) {
		// return null;
		// }
		dialog.open();
		String[] fileNames = dialog.getFileNames();
		int nChosen = fileNames.length;

		IPath filterPath = Path.fromOSString(dialog.getFilterPath());
		// IPath[] elems = new IPath[1];
		// elems[0] = EnvironmentPathUtils.getFullPath(environment, new
		// Path(res));
		IPath[] elems = new IPath[nChosen];
		for (int i = 0; i < nChosen; i++) {
			elems[i] = filterPath.append(fileNames[i]).makeAbsolute();
			elems[i] = EnvironmentPathUtils.getFullPath(environment, elems[i]);
		}
		DLTKUIPlugin.getDefault().getDialogSettings().put(
				IUIConstants.DIALOGSTORE_LASTEXTZIP, dialog.getFilterPath());

		return elems;
	}

	/**
	 * Shows the UI to configure an external JAR or ZIP archive. The dialog
	 * returns the configured or <code>null</code> if the dialog has been
	 * canceled. The dialog does not apply any changes.
	 * 
	 * @param shell
	 *            The parent shell for the dialog.
	 * @param initialEntry
	 *            The path of the initial archive entry.
	 * @return Returns the configured buildpath container entry path or
	 *         <code>null</code> if the dialog has been canceled by the user.
	 */
	public static IPath configureExternalArchiveEntry(Shell shell,
			IPath initialEntry) {
		if (initialEntry == null) {
			throw new IllegalArgumentException();
		}

		String lastUsedPath = initialEntry.removeLastSegments(1).toOSString();

		FileDialog dialog = new FileDialog(shell, SWT.SINGLE);
		dialog
				.setText(IncludePathMessages.BuildPathDialogAccess_ExtZIPArchiveDialog_edit_title);
		dialog.setFilterExtensions(PHPArchiveFileFilter.FILTER_EXTENSIONS);
		dialog.setFilterPath(lastUsedPath);
		dialog.setFileName(initialEntry.lastSegment());

		String res = dialog.open();
		if (res == null) {
			return null;
		}
		DLTKUIPlugin.getDefault().getDialogSettings().put(
				IUIConstants.DIALOGSTORE_LASTEXTZIP, dialog.getFilterPath());

		return Path.fromOSString(res).makeAbsolute();
	}

	/**
	 * Shows the UI for selecting new variable classpath entries. See {@link IClasspathEntry#CPE_VARIABLE} for
	 * details about variable classpath entries.
	 * The dialog returns an array of the selected variable entries or <code>null</code> if the dialog has
	 * been canceled. The dialog does not apply any changes.
	 *
	 * @param shell The parent shell for the dialog.
	 * @param existingPaths An array of paths that are already on the classpath and therefore should not be
	 * selected again.
	 * @return Returns an non empty array of the selected variable entries or <code>null</code> if the dialog has
	 * been canceled.
	 */
	public static IPath[] chooseVariableEntries(Shell shell, IEnvironment environment) {
//		if (existingPaths == null) {
//			throw new IllegalArgumentException();
//		}
		NewVariableEntryDialog dialog= new NewVariableEntryDialog(shell,environment);
		if (dialog.open() == Window.OK) {
			return dialog.getResult();
		}
		return null;
	}
}
