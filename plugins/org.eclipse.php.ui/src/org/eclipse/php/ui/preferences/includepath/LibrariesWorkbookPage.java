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
package org.eclipse.php.ui.preferences.includepath;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.project.IIncludePathEntry;
import org.eclipse.php.core.project.options.PHPProjectOptions;
import org.eclipse.php.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.util.ExceptionHandler;
import org.eclipse.php.ui.wizards.fields.DialogField;
import org.eclipse.php.ui.wizards.fields.IDialogFieldListener;
import org.eclipse.php.ui.wizards.fields.ITreeListAdapter;
import org.eclipse.php.ui.wizards.fields.LayoutUtil;
import org.eclipse.php.ui.wizards.fields.ListDialogField;
import org.eclipse.php.ui.wizards.fields.TreeListDialogField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class LibrariesWorkbookPage extends IncludePathBasePage {

	private ListDialogField fIncludePathList;
	private IProject fCurrJProject;

	private TreeListDialogField fLibrariesList;

	private Control fSWTControl;
	private final IWorkbenchPreferenceContainer fPageContainer;

	private final int IDX_ADDVAR = 0;
	private final int IDX_ADDZIP = 1;
	private final int IDX_ADDEXT = 2;
	private final int IDX_ADDFOL = 3;

	private final int IDX_EDIT = 4;
	private final int IDX_REMOVE = 5;

	public LibrariesWorkbookPage(ListDialogField includePathList, IWorkbenchPreferenceContainer pageContainer) {
		fIncludePathList = includePathList;
		fPageContainer = pageContainer;
		fSWTControl = null;

		String[] buttonLabels = new String[] { PHPUIMessages.LibrariesWorkbookPage_libraries_addvariable_button, PHPUIMessages.LibrariesWorkbookPage_libraries_addzip_button, PHPUIMessages.LibrariesWorkbookPage_libraries_addextzip_button,
			PHPUIMessages.LibrariesWorkbookPage_libraries_addincludepathfolder_button, PHPUIMessages.LibrariesWorkbookPage_libraries_edit_button, PHPUIMessages.LibrariesWorkbookPage_libraries_remove_button };

		LibrariesAdapter adapter = new LibrariesAdapter();

		fLibrariesList = new TreeListDialogField(adapter, buttonLabels, new IPListLabelProvider());
		fLibrariesList.setDialogFieldListener(adapter);
		fLibrariesList.setLabelText(PHPUIMessages.LibrariesWorkbookPage_libraries_label);

		fLibrariesList.enableButton(IDX_REMOVE, false);
		fLibrariesList.enableButton(IDX_EDIT, false);

		fLibrariesList.setViewerSorter(new IPListElementSorter());

	}

	public void init(IProject jproject) {
		fCurrJProject = jproject;
		updateLibrariesList();
	}

	private void updateLibrariesList() {
		List cpelements = fIncludePathList.getElements();
		List libelements = new ArrayList(cpelements.size());

		int nElements = cpelements.size();
		for (int i = 0; i < nElements; i++) {
			IPListElement cpe = (IPListElement) cpelements.get(i);
			if (isEntryKind(cpe.getEntryKind())) {
				libelements.add(cpe);
			}
		}
		fLibrariesList.setElements(libelements);
	}

	// -------- UI creation

	public Control getControl(Composite parent) {
		PixelConverter converter = new PixelConverter(parent);

		Composite composite = new Composite(parent, SWT.NONE);

		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fLibrariesList }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fLibrariesList.getTreeControl(null));

		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fLibrariesList.setButtonsMinWidth(buttonBarWidth);

		fLibrariesList.getTreeViewer().setSorter(new IPListElementSorter());

		fSWTControl = composite;

		return composite;
	}

	private Shell getShell() {
		if (fSWTControl != null) {
			return fSWTControl.getShell();
		}
		return PHPUiPlugin.getActiveWorkbenchShell();
	}

	private class LibrariesAdapter implements IDialogFieldListener, ITreeListAdapter {

		private final Object[] EMPTY_ARR = new Object[0];

		// -------- IListAdapter --------
		public void customButtonPressed(TreeListDialogField field, int index) {
			libaryPageCustomButtonPressed(field, index);
		}

		public void selectionChanged(TreeListDialogField field) {
			libaryPageSelectionChanged(field);
		}

		public void doubleClicked(TreeListDialogField field) {
			libaryPageDoubleClicked(field);
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
			libaryPageKeyPressed(field, event);
		}

		public Object[] getChildren(TreeListDialogField field, Object element) {
			if (element instanceof IPListElement) {
				return ((IPListElement) element).getChildren(false);
			} else if (element instanceof IPListElementAttribute) {
				IPListElementAttribute attribute = (IPListElementAttribute) element;

			}
			return EMPTY_ARR;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof IPListElementAttribute) {
				return ((IPListElementAttribute) element).getParent();
			}
			return null;
		}

		public boolean hasChildren(TreeListDialogField field, Object element) {
			return getChildren(field, element).length > 0;
		}

		// ---------- IDialogFieldListener --------

		public void dialogFieldChanged(DialogField field) {
			libaryPageDialogFieldChanged(field);
		}
	}

	private void libaryPageCustomButtonPressed(DialogField field, int index) {
		IPListElement[] libentries = null;
		switch (index) {
			case IDX_ADDZIP: /* add Zip */
				libentries = openZipFileDialog(null);
				break;
			case IDX_ADDEXT: /* add external Zip */
				libentries = openExtZIPFileDialog(null);
				break;
			case IDX_ADDVAR: /* add variable */
				libentries = openVariableSelectionDialog(null);
				break;
			case IDX_ADDFOL: /* add folder */
				libentries = openIncludeFolderDialog(null);
				break;
			case IDX_EDIT: /* edit */
				editEntry();
				return;
			case IDX_REMOVE: /* remove */
				removeEntry();
				return;
		}
		if (libentries != null) {
			int nElementsChosen = libentries.length;
			// remove duplicates
			List cplist = fLibrariesList.getElements();
			List elementsToAdd = new ArrayList(nElementsChosen);

			for (int i = 0; i < nElementsChosen; i++) {
				IPListElement curr = libentries[i];
				if (!cplist.contains(curr) && !elementsToAdd.contains(curr)) {
					elementsToAdd.add(curr);
				}
			}
			if (!elementsToAdd.isEmpty() && (index == IDX_ADDFOL)) {
				askForAddingExclusionPatternsDialog(elementsToAdd);
			}

			fLibrariesList.addElements(elementsToAdd);
			fLibrariesList.postSetSelection(new StructuredSelection(libentries));
		}
	}

	private void askForAddingExclusionPatternsDialog(List newEntries) {
		HashSet modified = new HashSet();
		fixNestingConflicts(newEntries, fIncludePathList.getElements(), modified);
		if (!modified.isEmpty()) {
			String title = PHPUIMessages.LibrariesWorkbookPage_exclusion_added_title;
			String message = PHPUIMessages.LibrariesWorkbookPage_exclusion_added_message;
			MessageDialog.openInformation(getShell(), title, message);
		}
	}

	protected void libaryPageDoubleClicked(TreeListDialogField field) {
		List selection = fLibrariesList.getSelectedElements();
		if (canEdit(selection)) {
			editEntry();
		}
	}

	protected void libaryPageKeyPressed(TreeListDialogField field, KeyEvent event) {
		if (field == fLibrariesList) {
			if (event.character == SWT.DEL && event.stateMask == 0) {
				List selection = field.getSelectedElements();
				if (canRemove(selection)) {
					removeEntry();
				}
			}
		}
	}

	private void removeEntry() {
		List selElements = fLibrariesList.getSelectedElements();
		HashSet containerEntriesToUpdate = new HashSet();
		for (int i = selElements.size() - 1; i >= 0; i--) {
			Object elem = selElements.get(i);
			if (elem instanceof IPListElementAttribute) {
				IPListElementAttribute attrib = (IPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;
				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);

				if (attrib.getParent().getParentContainer() instanceof IPListElement) { // inside a container: apply changes right away
					containerEntriesToUpdate.add(attrib.getParent());
				}
			}
		}
		if (selElements.isEmpty()) {
			fLibrariesList.refresh();
			fIncludePathList.dialogFieldChanged(); // validate
		} else {
			fLibrariesList.removeElements(selElements);
		}
		for (Iterator iter = containerEntriesToUpdate.iterator(); iter.hasNext();) {
			IPListElement curr = (IPListElement) iter.next();
			IIncludePathEntry updatedEntry = curr.getIncludePathEntry();
			updateContainerEntry(updatedEntry, fCurrJProject, ((IPListElement) curr.getParentContainer()).getPath());
		}
	}

	private boolean canRemove(List selElements) {
		if (selElements.size() == 0) {
			return false;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof IPListElementAttribute) {
				IPListElementAttribute attrib = (IPListElementAttribute) elem;
				if (attrib.isInNonModifiableContainer()) {
					return false;
				}
				if (attrib.getValue() == null) {
					return false;
				}
			} else if (elem instanceof IPListElement) {
				IPListElement curr = (IPListElement) elem;
				if (curr.getParentContainer() != null) {
					return false;
				}
			} else { // unknown element
				return false;
			}
		}
		return true;
	}

	/**
	 * Method editEntry.
	 */
	private void editEntry() {
		List selElements = fLibrariesList.getSelectedElements();
		if (selElements.size() != 1) {
			return;
		}
		Object elem = selElements.get(0);
		if (fLibrariesList.getIndexOfElement(elem) != -1) {
			editElementEntry((IPListElement) elem);
		} else if (elem instanceof IPListElementAttribute) {
			editAttributeEntry((IPListElementAttribute) elem);
		}
	}

	private void editAttributeEntry(IPListElementAttribute elem) {
		String key = elem.getKey();
		IPListElement selElement = elem.getParent();

	}

	private void attributeUpdated(IPListElement selElement) {
		Object parentContainer = selElement.getParentContainer();
		if (parentContainer instanceof IPListElement) { // inside a container: apply changes right away
			IIncludePathEntry updatedEntry = selElement.getIncludePathEntry();
			updateContainerEntry(updatedEntry, fCurrJProject, ((IPListElement) parentContainer).getPath());
		}
	}

	private void updateContainerEntry(final IIncludePathEntry newEntry, final IProject project, final IPath containerPath) {
		try {
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					PHPProjectOptions options = PHPProjectOptions.forProject(project);
					options.modifyIncludePathEntry(newEntry, project, containerPath, monitor);
				}
			};
			PlatformUI.getWorkbench().getProgressService().run(true, true, new WorkbenchRunnableAdapter(runnable));

		} catch (InvocationTargetException e) {
			String title = PHPUIMessages.LibrariesWorkbookPage_configurecontainer_error_title;
			String message = PHPUIMessages.LibrariesWorkbookPage_configurecontainer_error_message;
			ExceptionHandler.handle(e, getShell(), title, message);
		} catch (InterruptedException e) {
			// 
		}
	}

	private void editElementEntry(IPListElement elem) {
		IPListElement[] res = null;

		switch (elem.getEntryKind()) {
			case IIncludePathEntry.IPE_CONTAINER:
				res = openContainerSelectionDialog(elem);
				break;
			case IIncludePathEntry.IPE_LIBRARY:
				IResource resource = elem.getResource();
				if (resource == null) {
					if (elem.getContentKind() == IIncludePathEntry.K_BINARY) {
						res = openExtZIPFileDialog(elem);
					} else {
						res = openIncludeFolderDialog(elem);
					}
				} else if (resource.getType() == IResource.FOLDER) {
					if (resource.exists()) {
						res = openFolderDialog(elem);
					} else {
						res = openNewFolderDialog(elem);
					}
				} else if (resource.getType() == IResource.FILE) {
					res = openZipFileDialog(elem);
				}
				break;
			case IIncludePathEntry.IPE_VARIABLE:
				res = openVariableSelectionDialog(elem);
				break;
		}
		if (res != null && res.length > 0) {
			IPListElement curr = res[0];
			curr.setExported(elem.isExported());
			fLibrariesList.replaceElement(elem, curr);
		}

	}

	private void libaryPageSelectionChanged(DialogField field) {
		List selElements = fLibrariesList.getSelectedElements();
		fLibrariesList.enableButton(IDX_EDIT, canEdit(selElements));
		fLibrariesList.enableButton(IDX_REMOVE, canRemove(selElements));

		boolean noAttributes = containsOnlyTopLevelEntries(selElements);
		fLibrariesList.enableButton(IDX_ADDEXT, noAttributes);
		fLibrariesList.enableButton(IDX_ADDFOL, noAttributes);
		fLibrariesList.enableButton(IDX_ADDZIP, noAttributes);
		fLibrariesList.enableButton(IDX_ADDVAR, noAttributes);
	}

	private boolean canEdit(List selElements) {
		if (selElements.size() != 1) {
			return false;
		}
		Object elem = selElements.get(0);
		if (elem instanceof IPListElement) {
			IPListElement curr = (IPListElement) elem;
			return !(curr.getResource() instanceof IFolder) && curr.getParentContainer() == null;
		}
		if (elem instanceof IPListElementAttribute) {
			IPListElementAttribute attrib = (IPListElementAttribute) elem;
			if (attrib.isInNonModifiableContainer()) {
				return false;
			}
			return true;
		}
		return false;
	}

	private void libaryPageDialogFieldChanged(DialogField field) {
		if (fCurrJProject != null) {
			// already initialized
			updateIncludePathList();
		}
	}

	private void updateIncludePathList() {
		List projelements = fLibrariesList.getElements();

		List cpelements = fIncludePathList.getElements();
		int nEntries = cpelements.size();
		// backwards, as entries will be deleted
		int lastRemovePos = nEntries;
		for (int i = nEntries - 1; i >= 0; i--) {
			IPListElement cpe = (IPListElement) cpelements.get(i);
			int kind = cpe.getEntryKind();
			if (isEntryKind(kind)) {
				if (!projelements.remove(cpe)) {
					cpelements.remove(i);
					lastRemovePos = i;
				}
			}
		}

		cpelements.addAll(lastRemovePos, projelements);

		if (lastRemovePos != nEntries || !projelements.isEmpty()) {
			fIncludePathList.setElements(cpelements);
		}
	}

	private IPListElement[] openNewFolderDialog(IPListElement existing) {
		String title = (existing == null) ? PHPUIMessages.LibrariesWorkbookPage_NewIncludePathDialog_new_title : PHPUIMessages.LibrariesWorkbookPage_NewIncludePathDialog_edit_title;
		IProject currProject = fCurrJProject.getProject();

		NewContainerDialog dialog = new NewContainerDialog(getShell(), title, currProject, getUsedContainers(existing), existing);
		IPath projpath = currProject.getFullPath();
		dialog.setMessage(MessageFormat.format(PHPUIMessages.LibrariesWorkbookPage_NewIncludePathDialog_description, new String[] { projpath.toString() }));
		if (dialog.open() == Window.OK) {
			IFolder folder = dialog.getFolder();
			return new IPListElement[] { newCPLibraryElement(folder) };
		}
		return null;
	}

	private IPListElement[] openFolderDialog(IPListElement existing) {
		if (existing == null) {
			IPath[] selected = IncludePathDialogAccess.chooseFolderEntries(getShell(), fCurrJProject.getLocation(), getUsedContainers(existing));
			if (selected != null) {
				IWorkspaceRoot root = fCurrJProject.getProject().getWorkspace().getRoot();
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					IPath curr = selected[i];
					IResource resource = root.findMember(curr);
					if (resource instanceof IContainer) {
						res.add(newCPLibraryElement(resource));
					}
				}
				return (IPListElement[]) res.toArray(new IPListElement[res.size()]);
			}
		} else {
			// disabled
		}
		return null;
	}

	private IPListElement[] openIncludeFolderDialog(IPListElement existing) {

		if (existing == null) {
			IPath[] selected = IncludePathDialogAccess.chooseIncludePathFoldersEntries(getShell());
			if (selected != null) {
				ArrayList res = new ArrayList();

				for (int i = 0; i < selected.length; i++) {
					res.add(new IPListElement(fCurrJProject, IIncludePathEntry.IPE_LIBRARY, IIncludePathEntry.K_SOURCE, selected[i], null));
				}
				return (IPListElement[]) res.toArray(new IPListElement[res.size()]);
			}
		} else {
			IPath configured = IncludePathDialogAccess.configureIncludePathFolderEntry(getShell(), existing.getPath());
			if (configured != null) {
				IPListElement elem = new IPListElement(fCurrJProject, IIncludePathEntry.IPE_LIBRARY, IIncludePathEntry.K_SOURCE, configured, null);
				return new IPListElement[] { elem };
			}
		}

		return null;
	}

	private IPListElement[] openZipFileDialog(IPListElement existing) {
		IWorkspaceRoot root = fCurrJProject.getProject().getWorkspace().getRoot();

		if (existing == null) {
			IPath[] selected = IncludePathDialogAccess.chooseZIPEntries(getShell(), fCurrJProject.getLocation(), getUsedZipFiles(existing));
			if (selected != null) {
				ArrayList res = new ArrayList();

				for (int i = 0; i < selected.length; i++) {
					IPath curr = selected[i];
					IResource resource = root.findMember(curr);
					if (resource instanceof IFile) {
						res.add(newCPLibraryElement(resource));
					}
				}
				return (IPListElement[]) res.toArray(new IPListElement[res.size()]);
			}
		} else {
			IPath configured = IncludePathDialogAccess.configureZIPEntry(getShell(), existing.getPath(), getUsedZipFiles(existing));
			if (configured != null) {
				IResource resource = root.findMember(configured);
				if (resource instanceof IFile) {
					return new IPListElement[] { newCPLibraryElement(resource) };
				}
			}
		}
		return null;
	}

	private IPath[] getUsedContainers(IPListElement existing) {
		ArrayList res = new ArrayList();

		List cplist = fLibrariesList.getElements();
		for (int i = 0; i < cplist.size(); i++) {
			IPListElement elem = (IPListElement) cplist.get(i);
			if (elem.getEntryKind() == IIncludePathEntry.IPE_LIBRARY && (elem != existing)) {
				IResource resource = elem.getResource();
				if (resource instanceof IContainer && !resource.equals(existing)) {
					res.add(resource.getFullPath());
				}
			}
		}
		return (IPath[]) res.toArray(new IPath[res.size()]);
	}

	private IPath[] getUsedZipFiles(IPListElement existing) {
		List res = new ArrayList();
		List cplist = fLibrariesList.getElements();
		for (int i = 0; i < cplist.size(); i++) {
			IPListElement elem = (IPListElement) cplist.get(i);
			if (elem.getEntryKind() == IIncludePathEntry.IPE_LIBRARY && (elem != existing)) {
				IResource resource = elem.getResource();
				if (resource instanceof IFile) {
					res.add(resource.getFullPath());
				}
			}
		}
		return (IPath[]) res.toArray(new IPath[res.size()]);
	}

	private IPListElement newCPLibraryElement(IResource res) {
		return new IPListElement(fCurrJProject, IIncludePathEntry.IPE_LIBRARY, IIncludePathEntry.K_BINARY, res.getFullPath(), res);
	}

	private IPListElement[] openExtZIPFileDialog(IPListElement existing) {
		if (existing == null) {
			IPath[] selected = IncludePathDialogAccess.chooseExternalZIPEntries(getShell());
			if (selected != null) {
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					res.add(new IPListElement(fCurrJProject, IIncludePathEntry.IPE_LIBRARY, IIncludePathEntry.K_BINARY, selected[i], null));
				}
				return (IPListElement[]) res.toArray(new IPListElement[res.size()]);
			}
		} else {
			IPath configured = IncludePathDialogAccess.configureExternalZIPEntry(getShell(), existing.getPath());
			if (configured != null) {
				return new IPListElement[] { new IPListElement(fCurrJProject, IIncludePathEntry.IPE_LIBRARY, IIncludePathEntry.K_BINARY, configured, null) };
			}
		}
		return null;
	}

	private IPListElement[] openVariableSelectionDialog(IPListElement existing) {
		List existingElements = fLibrariesList.getElements();
		ArrayList existingPaths = new ArrayList(existingElements.size());
		for (int i = 0; i < existingElements.size(); i++) {
			IPListElement elem = (IPListElement) existingElements.get(i);
			if (elem.getEntryKind() == IIncludePathEntry.IPE_VARIABLE) {
				existingPaths.add(elem.getPath());
			}
		}
		IPath[] existingPathsArray = (IPath[]) existingPaths.toArray(new IPath[existingPaths.size()]);

		if (existing == null) {
			IPath[] paths = IncludePathDialogAccess.chooseVariableEntries(getShell(), existingPathsArray);
			if (paths != null) {
				ArrayList result = new ArrayList();
				for (int i = 0; i < paths.length; i++) {
					IPListElement elem = new IPListElement(fCurrJProject, IIncludePathEntry.IPE_VARIABLE, IIncludePathEntry.K_SOURCE, paths[i], null);
					IPath resolvedPath = PHPProjectOptions.getResolvedVariablePath(paths[i]);
					elem.setIsMissing((resolvedPath == null) || !resolvedPath.toFile().exists());
					if (!existingElements.contains(elem)) {
						result.add(elem);
					}
				}
				return (IPListElement[]) result.toArray(new IPListElement[result.size()]);
			}
		} else {
			IPath path = IncludePathDialogAccess.configureVariableEntry(getShell(), existing.getPath(), existingPathsArray);
			if (path != null) {
				IPListElement elem = new IPListElement(fCurrJProject, IIncludePathEntry.IPE_VARIABLE, IIncludePathEntry.K_SOURCE, path, null);
				return new IPListElement[] { elem };
			}
		}
		return null;
	}

	private IPListElement[] openContainerSelectionDialog(IPListElement existing) {
		if (existing == null) {
			IIncludePathEntry[] created = IncludePathDialogAccess.chooseContainerEntries(getShell(), fCurrJProject, getRawIncludePath());
			if (created != null) {
				IPListElement[] res = new IPListElement[created.length];
				for (int i = 0; i < res.length; i++) {
					res[i] = new IPListElement(fCurrJProject, IIncludePathEntry.IPE_CONTAINER, IIncludePathEntry.K_SOURCE, created[i].getPath(), null);
				}
				return res;
			}
		} else {
			IIncludePathEntry created = IncludePathDialogAccess.configureContainerEntry(getShell(), existing.getIncludePathEntry(), fCurrJProject, getRawIncludePath());
			if (created != null) {
				IPListElement elem = new IPListElement(fCurrJProject, IIncludePathEntry.IPE_CONTAINER, IIncludePathEntry.K_SOURCE, created.getPath(), null);
				return new IPListElement[] { elem };
			}
		}
		return null;
	}

	private IIncludePathEntry[] getRawIncludePath() {
		IIncludePathEntry[] currEntries = new IIncludePathEntry[fIncludePathList.getSize()];
		for (int i = 0; i < currEntries.length; i++) {
			IPListElement curr = (IPListElement) fIncludePathList.getElement(i);
			currEntries[i] = curr.getIncludePathEntry();
		}
		return currEntries;
	}

	public boolean isEntryKind(int kind) {
		return kind == IIncludePathEntry.IPE_LIBRARY || kind == IIncludePathEntry.IPE_VARIABLE || kind == IIncludePathEntry.IPE_CONTAINER;
	}

	/*
	 * @see IncludePathBasePage#getSelection
	 */
	public List getSelection() {
		return fLibrariesList.getSelectedElements();
	}

	/*
	 * @see IncludePathBasePage#setSelection
	 */
	public void setSelection(List selElements, boolean expand) {
		fLibrariesList.selectElements(new StructuredSelection(selElements));
		if (expand) {
			for (int i = 0; i < selElements.size(); i++) {
				fLibrariesList.expandElement(selElements.get(i), 1);
			}
		}
	}

}
