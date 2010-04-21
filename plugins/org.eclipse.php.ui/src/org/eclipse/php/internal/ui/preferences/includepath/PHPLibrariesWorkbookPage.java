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
package org.eclipse.php.internal.ui.preferences.includepath;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.Map.Entry;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.environment.EnvironmentManager;
import org.eclipse.dltk.core.environment.EnvironmentPathUtils;
import org.eclipse.dltk.core.environment.IEnvironment;
import org.eclipse.dltk.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.dltk.internal.ui.wizards.BuildpathDialogAccess;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.util.ExceptionHandler;
import org.eclipse.dltk.ui.util.PixelConverter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPLibrariesWorkbookPage extends BuildPathBasePage {
	private ListDialogField fBuildPathList;
	private IScriptProject fCurrJProject;
	private TreeListDialogField fLibrariesList;
	private Control fSWTControl;
	private final IWorkbenchPreferenceContainer fPageContainer;
	private final int IDX_ADDZIP = 0;
	private final int IDX_ADDEXT = 1;
	private final int IDX_ADDLIB = 2;
	// private final int IDX_ADDFOL = 3;
	private final int IDX_ADDEXTFOL = 3;
	private final int IDX_ADDVAR = 4;
	private final int IDX_EDIT = 5;
	private final int IDX_REMOVE = 6;
	private final int IDX_REPLACE = 8;

	private final int IDX_WITHOUTZIP = -2;
	private int IDX_ADD = 0;
	private boolean fWithZip = false;
	private IScriptProject scriptProject;

	private boolean fSupportZips = false;

	public PHPLibrariesWorkbookPage(boolean supportZips,
			ListDialogField classPathList,
			IWorkbenchPreferenceContainer pageContainer) {
		fBuildPathList = classPathList;
		fPageContainer = pageContainer;
		fSupportZips = supportZips;
		fSWTControl = null;

		initContainerElements();

	}

	protected void initContainerElements() {

		String[] buttonLabelsWith = new String[] {
				IncludePathMessages.LibrariesWorkbookPage_libraries_addzip_button,
				IncludePathMessages.LibrariesWorkbookPage_libraries_addextzip_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_addlibrary_button,
				// NewWizardMessages.LibrariesWorkbookPage_libraries_add_source_folder_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_add_external_source_folder_button,
				IncludePathMessages.LibrariesWorkbookPage_libraries_addvariables_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_edit_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_remove_button,
				/* */null,
				IncludePathMessages.LibrariesWorkbookPage_libraries_replace_button };
		String[] buttonLabelsWithout = new String[] {
				NewWizardMessages.LibrariesWorkbookPage_libraries_addlibrary_button,
				// NewWizardMessages.LibrariesWorkbookPage_libraries_add_source_folder_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_add_external_source_folder_button,
				IncludePathMessages.LibrariesWorkbookPage_libraries_addvariables_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_edit_button,
				NewWizardMessages.LibrariesWorkbookPage_libraries_remove_button };
		String[] buttonLabels;
		buttonLabels = buttonLabelsWithout;
		IDX_ADD = IDX_WITHOUTZIP;
		fWithZip = fSupportZips;
		if (fWithZip) {
			buttonLabels = buttonLabelsWith;
			IDX_ADD = 0;
		}
		LibrariesAdapter adapter = new LibrariesAdapter();
		fLibrariesList = new TreeListDialogField(adapter, buttonLabels,
				new PHPIPListLabelProvider());
		fLibrariesList.setDialogFieldListener(adapter);
		if (this.fWithZip) {
			fLibrariesList
					.setLabelText(NewWizardMessages.LibrariesWorkbookPage_libraries_label);
		} else {
			fLibrariesList
					.setLabelText(NewWizardMessages.LibrariesWorkbookPage_libraries_without_label);
		}
		fLibrariesList.enableButton(IDX_REMOVE + IDX_ADD, false);
		fLibrariesList.enableButton(IDX_EDIT + IDX_ADD, false);
		if (fWithZip) {
			fLibrariesList.enableButton(IDX_REPLACE + IDX_ADD, false);
		}
		fLibrariesList.setViewerSorter(new BPListElementSorter());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage#setTitle
	 * (java.lang.String)
	 */
	public void setTitle(String title) {
		fLibrariesList.setLabelText(title);
	}

	public void init(IScriptProject jproject) {
		fCurrJProject = jproject;
		updateLibrariesList();
	}

	private void updateLibrariesList() {
		List cpelements = fBuildPathList.getElements();
		List libelements = new ArrayList(cpelements.size());
		int nElements = cpelements.size();
		for (int i = 0; i < nElements; i++) {
			BPListElement cpe = (BPListElement) cpelements.get(i);
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
		LayoutUtil.doDefaultLayout(composite,
				new DialogField[] { fLibrariesList }, true, SWT.DEFAULT,
				SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fLibrariesList.getTreeControl(null));
		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fLibrariesList.setButtonsMinWidth(buttonBarWidth);
		fLibrariesList.getTreeViewer().setSorter(new BPListElementSorter());
		fSWTControl = composite;
		return composite;
	}

	private Shell getShell() {
		if (fSWTControl != null) {
			return fSWTControl.getShell();
		}
		return DLTKUIPlugin.getActiveWorkbenchShell();
	}

	private class LibrariesAdapter implements IDialogFieldListener,
			ITreeListAdapter {
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
			if (element instanceof BPListElement) {
				return ((BPListElement) element).getChildren();
			} else if (element instanceof BPListElementAttribute) {
				BPListElementAttribute attribute = (BPListElementAttribute) element;
				if (BPListElement.ACCESSRULES.equals(attribute.getKey())) {
					return (IAccessRule[]) attribute.getValue();
				}
			}
			return EMPTY_ARR;
		}

		public Object getParent(TreeListDialogField field, Object element) {
			if (element instanceof BPListElementAttribute) {
				return ((BPListElementAttribute) element).getParent();
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
		BPListElement[] libentries = null;

		IEnvironment environment = EnvironmentManager
				.getEnvironment(this.fCurrJProject);
		switch (index - IDX_ADD) {
		case IDX_ADDZIP: /* add archive */
			if (fWithZip) {
				libentries = openZipFileDialog(null);
				break;
			}
		case IDX_ADDEXT: /* add external archive */
			if (fWithZip) {
				libentries = openExtZipFileDialog(null, environment);
				break;
			}
		case IDX_ADDLIB: /* add library */
			libentries = openContainerSelectionDialog(null);
			break;
		case IDX_ADDEXTFOL: /* add folder */
			libentries = opensExtSourceFolderDialog(null, environment);
			break;
		// case IDX_ADDFOL: /* add folder */
		// libentries = opensSourceFolderDialog(null);
		// break;
		case IDX_ADDVAR: /* add variables */
			libentries = addVariablesDialog(null, environment);
			break;
		case IDX_EDIT: /* edit */
			editEntry();
			return;
		case IDX_REMOVE: /* remove */
			removeEntry();
			return;
		case IDX_REPLACE: /* replace */
			replaceArchiveFile();
			return;
		}
		if (libentries != null) {
			int nElementsChosen = libentries.length;
			// remove duplicates
			List cplist = fLibrariesList.getElements();
			List elementsToAdd = new ArrayList(nElementsChosen);
			for (int i = 0; i < nElementsChosen; i++) {
				BPListElement curr = libentries[i];
				if (!cplist.contains(curr) && !elementsToAdd.contains(curr)) {
					elementsToAdd.add(curr);
				}
			}
			// if (!elementsToAdd.isEmpty() && (index == IDX_ADDFOL+IDX_ADD)) {
			// askForAddingExclusionPatternsDialog(elementsToAdd);
			// }
			fLibrariesList.addElements(elementsToAdd);
			if (index == IDX_ADDLIB + IDX_ADD) {
				fLibrariesList.refresh();
			}
			fLibrariesList
					.postSetSelection(new StructuredSelection(libentries));
		}
	}

	private BPListElement[] addVariablesDialog(BPListElement existing,
			IEnvironment environment) {
		if (existing == null) {
			IPath[] selected = PHPBuildpathDialogAccess.chooseVariableEntries(
					getShell(), environment);
			if (selected != null) {
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					IPath path = EnvironmentPathUtils.getFullPath(
							environment,
							DLTKCore.getBuildpathVariable(selected[i]
									.segment(0).toString())).append(
							selected[i].removeFirstSegments(1));
					res.add(new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_LIBRARY, path, null, true));
				}
				return (BPListElement[]) res.toArray(new BPListElement[res
						.size()]);
			}
		}
		return null;
	}

	public void addElement(BPListElement element) {
		fLibrariesList.addElement(element);
		fLibrariesList.postSetSelection(new StructuredSelection(element));
	}

	private void askForAddingExclusionPatternsDialog(List newEntries) {
		HashSet modified = new HashSet();
		List existing = fBuildPathList.getElements();
		fixNestingConflicts((BPListElement[]) newEntries
				.toArray(new BPListElement[newEntries.size()]),
				(BPListElement[]) existing.toArray(new BPListElement[existing
						.size()]), modified);
		if (!modified.isEmpty()) {
			String title = NewWizardMessages.LibrariesWorkbookPage_exclusion_added_title;
			String message = NewWizardMessages.LibrariesWorkbookPage_exclusion_added_message;
			MessageDialog.openInformation(getShell(), title, message);
		}
	}

	protected void libaryPageDoubleClicked(TreeListDialogField field) {
		List selection = fLibrariesList.getSelectedElements();
		if (canEdit(selection)) {
			editEntry();
		}
	}

	protected void libaryPageKeyPressed(TreeListDialogField field,
			KeyEvent event) {
		if (field == fLibrariesList) {
			if (event.character == SWT.DEL && event.stateMask == 0) {
				List selection = field.getSelectedElements();
				if (canRemove(selection)) {
					removeEntry();
				}
			}
		}
	}

	private void replaceArchiveFile() {
	}

	private IProjectFragment getSelectedProjectFragment() {
		final List elements = fLibrariesList.getSelectedElements();
		if (elements.size() == 1) {
			final Object object = elements.get(0);
			if (object instanceof BPListElement) {
				// final BPListElement element = (BPListElement) object;
				// final IBuildpathEntry entry = element.getBuildpathEntry();
				// if (JarImportWizard.isValidBuildPathEntry(entry)) {
				// final IScriptProject project = element.getScriptProject();
				// if (project != null) {
				// try {
				// final IProjectFragment[] roots = project
				// .getProjectFragments();
				// for (int index = 0; index < roots.length; index++) {
				// if (entry.equals(roots[index]
				// .getRawBuildpathEntry()))
				// return roots[index];
				// }
				// } catch (ModelException exception) {
				// DLTKUIPlugin.log(exception);
				// }
				// }
				// }
			}
		}
		return null;
	}

	private void removeEntry() {
		List selElements = fLibrariesList.getSelectedElements();
		HashMap containerEntriesToUpdate = new HashMap();
		for (int i = selElements.size() - 1; i >= 0; i--) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;
				if (key.equals(BPListElement.ACCESSRULES)) {
					value = new IAccessRule[0];
				}
				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);
				if (attrib.getParent().getParentContainer() instanceof BPListElement) { // inside
					// a
					// container:
					// apply
					// changes
					// right
					// away
					BPListElement containerEntry = attrib.getParent();
					HashSet changedAttributes = (HashSet) containerEntriesToUpdate
							.get(containerEntry);
					if (changedAttributes == null) {
						changedAttributes = new HashSet();
						containerEntriesToUpdate.put(containerEntry,
								changedAttributes);
					}
					changedAttributes.add(key); // collect the changed
					// attributes
				}
			}
		}
		if (selElements.isEmpty()) {
			fLibrariesList.refresh();
			fBuildPathList.dialogFieldChanged(); // validate
		} else {
			fLibrariesList.removeElements(selElements);

			// also remove library entry from build path
			for (Iterator iterator = selElements.iterator(); iterator.hasNext();) {
				BPListElement entry = (BPListElement) iterator.next();
				try {
					BuildPathUtils.removeEntryFromBuildPath(fCurrJProject,
							entry.getBuildpathEntry());
				} catch (ModelException e) {
					PHPCorePlugin.log(e);
				}
			}
		}

		for (Iterator iter = containerEntriesToUpdate.entrySet().iterator(); iter
				.hasNext();) {
			Map.Entry entry = (Entry) iter.next();
			BPListElement curr = (BPListElement) entry.getKey();
			HashSet attribs = (HashSet) entry.getValue();
			String[] changedAttributes = (String[]) attribs
					.toArray(new String[attribs.size()]);
			IBuildpathEntry changedEntry = curr.getBuildpathEntry();
			updateContainerEntry(changedEntry, changedAttributes,
					fCurrJProject, ((BPListElement) curr.getParentContainer())
							.getPath());
		}
	}

	private boolean canRemove(List selElements) {
		if (selElements.size() == 0) {
			return false;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				if (attrib.isInNonModifiableContainer()) {
					return false;
				}
				if (attrib.getKey().equals(BPListElement.ACCESSRULES)) {
					return ((IAccessRule[]) attrib.getValue()).length > 0;
				}
				if (attrib.getValue() == null) {
					return false;
				}
			} else if (elem instanceof BPListElement) {
				BPListElement curr = (BPListElement) elem;
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
			editElementEntry((BPListElement) elem);
		} else if (elem instanceof BPListElementAttribute) {
			editAttributeEntry((BPListElementAttribute) elem);
		}
	}

	private void editAttributeEntry(BPListElementAttribute elem) {
		String key = elem.getKey();
		BPListElement selElement = elem.getParent();
		if (key.equals(BPListElement.ACCESSRULES)) {
			AccessRulesDialog dialog = new AccessRulesDialog(getShell(),
					selElement, fCurrJProject, fPageContainer != null);
			int res = dialog.open();
			if (res == Window.OK || res == AccessRulesDialog.SWITCH_PAGE) {
				selElement.setAttribute(BPListElement.ACCESSRULES, dialog
						.getAccessRules());
				String[] changedAttributes = { BPListElement.ACCESSRULES };
				attributeUpdated(selElement, changedAttributes);
				fLibrariesList.refresh(elem);
				fBuildPathList.dialogFieldChanged(); // validate
				updateEnabledState();
				if (res == AccessRulesDialog.SWITCH_PAGE) { // switch after
					// updates and
					// validation
					dialog.performPageSwitch(fPageContainer);
				}
			}
		}
		// else if (key.equals(BPListElement.NATIVE_LIB_PATH)) {
		// NativeLibrariesDialog dialog= new NativeLibrariesDialog(getShell(),
		// selElement);
		// if (dialog.open() == Window.OK) {
		// selElement.setAttribute(BPListElement.NATIVE_LIB_PATH,
		// dialog.getNativeLibraryPath());
		// String[] changedAttributes= { BPListElement.NATIVE_LIB_PATH };
		// attributeUpdated(selElement, changedAttributes);
		//				
		// fLibrariesList.refresh(elem);
		// fBuildPathList.dialogFieldChanged(); // validate
		// updateEnabledState();
		// }
		// }
	}

	private void attributeUpdated(BPListElement selElement,
			String[] changedAttributes) {
		Object parentContainer = selElement.getParentContainer();
		if (parentContainer instanceof BPListElement) { // inside a container:
			// apply changes right
			// away
			IBuildpathEntry updatedEntry = selElement.getBuildpathEntry();
			updateContainerEntry(updatedEntry, changedAttributes,
					fCurrJProject, ((BPListElement) parentContainer).getPath());
		}
	}

	private void updateContainerEntry(final IBuildpathEntry newEntry,
			final String[] changedAttributes, final IScriptProject jproject,
			final IPath containerPath) {
		try {
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					BuildPathSupport
							.modifyBuildpathEntry(null, newEntry,
									changedAttributes, jproject, containerPath,
									monitor);
				}
			};
			PlatformUI.getWorkbench().getProgressService().run(true, true,
					new WorkbenchRunnableAdapter(runnable));
		} catch (InvocationTargetException e) {
			String title = NewWizardMessages.LibrariesWorkbookPage_configurecontainer_error_title;
			String message = NewWizardMessages.LibrariesWorkbookPage_configurecontainer_error_message;
			ExceptionHandler.handle(e, getShell(), title, message);
		} catch (InterruptedException e) {
			// 
		}
	}

	private void editElementEntry(BPListElement elem) {
		BPListElement[] res = null;
		switch (elem.getEntryKind()) {
		case IBuildpathEntry.BPE_CONTAINER:
			res = openContainerSelectionDialog(elem);
			break;
		case IBuildpathEntry.BPE_LIBRARY:
			IEnvironment environment = EnvironmentManager
					.getEnvironment(this.scriptProject);
			IResource resource = elem.getResource();
			if (resource == null) {
				if (Util.isArchiveFileName(elem.getPath().toOSString())) {
					res = openExtZipFileDialog(elem, environment);
				} else {
					res = opensExtSourceFolderDialog(elem, environment);
				}
			} else if (resource.getType() == IResource.FOLDER) {
				if (resource.exists()) {
					res = opensSourceFolderDialog(elem);
				} else {
					res = openNewClassFolderDialog(elem);
				}
			} else if (resource.getType() == IResource.FILE) {
				res = openZipFileDialog(elem);
			}
			break;
		}
		if (res != null && res.length > 0) {
			BPListElement curr = res[0];
			curr.setExported(elem.isExported());
			fLibrariesList.replaceElement(elem, curr);
		}
	}

	private void libaryPageSelectionChanged(DialogField field) {
		updateEnabledState();
	}

	private void updateEnabledState() {
		List selElements = fLibrariesList.getSelectedElements();
		fLibrariesList.enableButton(IDX_EDIT + IDX_ADD, canEdit(selElements));
		fLibrariesList.enableButton(IDX_REMOVE + IDX_ADD,
				canRemove(selElements));
		boolean noAttributes = containsOnlyTopLevelEntries(selElements);
		if (fWithZip) {
			fLibrariesList.enableButton(IDX_ADDEXT + IDX_ADD, noAttributes);
			fLibrariesList.enableButton(IDX_ADDZIP + IDX_ADD, noAttributes);
			fLibrariesList.enableButton(IDX_REPLACE + IDX_ADD,
					getSelectedProjectFragment() != null);
		}
		// fLibrariesList.enableButton(IDX_ADDFOL+IDX_ADD, noAttributes);
		fLibrariesList.enableButton(IDX_ADDVAR + IDX_ADD, noAttributes);
		fLibrariesList.enableButton(IDX_ADDLIB + IDX_ADD, noAttributes);
	}

	private boolean canEdit(List selElements) {
		if (selElements.size() != 1) {
			return false;
		}
		Object elem = selElements.get(0);
		if (elem instanceof BPListElement) {
			BPListElement curr = (BPListElement) elem;
			return !(curr.getResource() instanceof IFolder || curr
					.isExternalFolder())
					&& curr.getParentContainer() == null;
		}
		if (elem instanceof BPListElementAttribute) {
			BPListElementAttribute attrib = (BPListElementAttribute) elem;
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
			updateBuildpathList();
		}
	}

	private void updateBuildpathList() {
		List projelements = fLibrariesList.getElements();
		List cpelements = fBuildPathList.getElements();
		int nEntries = cpelements.size();
		// backwards, as entries will be deleted
		int lastRemovePos = nEntries;
		for (int i = nEntries - 1; i >= 0; i--) {
			BPListElement cpe = (BPListElement) cpelements.get(i);
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
			fBuildPathList.setElements(cpelements);
		}
	}

	private BPListElement[] openNewClassFolderDialog(BPListElement existing) {
		String title = (existing == null) ? NewWizardMessages.LibrariesWorkbookPage_NewClassFolderDialog_new_title
				: NewWizardMessages.LibrariesWorkbookPage_NewClassFolderDialog_edit_title;
		IProject currProject = fCurrJProject.getProject();
		NewContainerDialog dialog = new NewContainerDialog(getShell(), title,
				currProject, getUsedContainers(existing), existing);
		IPath projpath = currProject.getFullPath();
		dialog
				.setMessage(org.eclipse.dltk.internal.corext.util.Messages
						.format(
								NewWizardMessages.LibrariesWorkbookPage_NewClassFolderDialog_description,
								projpath.toString()));
		if (dialog.open() == Window.OK) {
			IFolder folder = dialog.getFolder();
			return new BPListElement[] { newBPLibraryElement(folder, false) };
		}
		return null;
	}

	private BPListElement[] opensExtSourceFolderDialog(BPListElement existing,
			IEnvironment environment) {
		if (existing == null) {
			IPath[] selected = BuildpathDialogAccess
					.chooseExtSourceFolderEntries(getShell(), fCurrJProject
							.getPath(), getUsedContainers(existing),
							environment);
			if (selected != null) {
				// IWorkspaceRoot root =
				// fCurrJProject.getProject().getWorkspace().getRoot();
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					// IPath curr = selected[i];
					res.add(new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_LIBRARY, selected[i], null,
							true));
				}
				return (BPListElement[]) res.toArray(new BPListElement[res
						.size()]);
			}
		} else {
			// disabled
		}
		return null;
	}

	private BPListElement[] opensSourceFolderDialog(BPListElement existing) {
		if (existing == null) {
			IPath[] selected = BuildpathDialogAccess.chooseSourceFolderEntries(
					getShell(), fCurrJProject.getPath(),
					getUsedContainers(existing));
			if (selected != null) {
				IWorkspaceRoot root = fCurrJProject.getProject().getWorkspace()
						.getRoot();
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					IPath curr = selected[i];
					IResource resource = root.findMember(curr);
					if (resource instanceof IContainer) {
						res.add(newBPLibraryElement(resource, false));
					}
				}
				return (BPListElement[]) res.toArray(new BPListElement[res
						.size()]);
			}
		} else {
			// disabled
		}
		return null;
	}

	private BPListElement[] openZipFileDialog(BPListElement existing) {
		IWorkspaceRoot root = fCurrJProject.getProject().getWorkspace()
				.getRoot();
		if (existing == null) {
			IPath[] selected = PHPBuildpathDialogAccess.chooseArchiveEntries(
					getShell(), fCurrJProject.getPath(),
					getUsedArchiveFiles(existing));
			if (selected != null) {
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					IPath curr = selected[i];
					IResource resource = root.findMember(curr);
					if (resource instanceof IFile) {
						res.add(newBPLibraryElement(resource, false));
					}
				}
				return (BPListElement[]) res.toArray(new BPListElement[res
						.size()]);
			}
		} else {
			IPath configured = PHPBuildpathDialogAccess.configureArchiveEntry(
					getShell(), existing.getPath(),
					getUsedArchiveFiles(existing));
			if (configured != null) {
				IResource resource = root.findMember(configured);
				if (resource instanceof IFile) {
					return new BPListElement[] { newBPLibraryElement(resource,
							false) };
				}
			}
		}
		return null;
	}

	private IPath[] getUsedContainers(BPListElement existing) {
		ArrayList res = new ArrayList();
		List cplist = fLibrariesList.getElements();
		for (int i = 0; i < cplist.size(); i++) {
			BPListElement elem = (BPListElement) cplist.get(i);
			if (elem.getEntryKind() == IBuildpathEntry.BPE_LIBRARY
					&& (elem != existing)) {
				IResource resource = elem.getResource();
				if (resource instanceof IContainer
						&& !resource.equals(existing)) {
					res.add(resource.getFullPath());
				}
			}
		}
		return (IPath[]) res.toArray(new IPath[res.size()]);
	}

	private IPath[] getUsedArchiveFiles(BPListElement existing) {
		List res = new ArrayList();
		List cplist = fLibrariesList.getElements();
		for (int i = 0; i < cplist.size(); i++) {
			BPListElement elem = (BPListElement) cplist.get(i);
			if (elem.getEntryKind() == IBuildpathEntry.BPE_LIBRARY
					&& (elem != existing)) {
				IResource resource = elem.getResource();
				if (resource instanceof IFile) {
					res.add(resource.getFullPath());
				}
			}
		}
		return (IPath[]) res.toArray(new IPath[res.size()]);
	}

	private BPListElement newBPLibraryElement(IResource res, boolean external) {
		return new BPListElement(fCurrJProject, IBuildpathEntry.BPE_LIBRARY,
				res.getFullPath(), res, external);
	}

	private BPListElement[] openExtZipFileDialog(BPListElement existing,
			IEnvironment environment) {
		if (existing == null) {
			IPath[] selected = PHPBuildpathDialogAccess
					.chooseExternalArchiveEntries(getShell(), environment);
			if (selected != null) {
				ArrayList res = new ArrayList();
				for (int i = 0; i < selected.length; i++) {
					res.add(new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_LIBRARY, selected[i], null,
							true));
				}
				return (BPListElement[]) res.toArray(new BPListElement[res
						.size()]);
			}
		} else {
			IPath configured = PHPBuildpathDialogAccess
					.configureExternalArchiveEntry(getShell(), existing
							.getPath());
			if (configured != null) {
				return new BPListElement[] { new BPListElement(fCurrJProject,
						IBuildpathEntry.BPE_LIBRARY, configured, null, true) };
			}
		}
		return null;
	}

	private BPListElement[] openContainerSelectionDialog(BPListElement existing) {
		if (existing == null) {
			IBuildpathEntry[] created = BuildpathDialogAccess
					.chooseContainerEntries(getShell(), fCurrJProject,
							getRawBuildpath());
			if (created != null) {
				BPListElement[] res = new BPListElement[created.length];
				for (int i = 0; i < res.length; i++) {
					res[i] = new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_CONTAINER,
							created[i].getPath(), null, false);
				}
				return res;
			}
		} else {
			IBuildpathEntry created = BuildpathDialogAccess
					.configureContainerEntry(getShell(), existing
							.getBuildpathEntry(), fCurrJProject,
							getRawBuildpath());
			if (created != null) {
				BPListElement elem = BPListElement.createFromExisting(created,
						fCurrJProject);
				return new BPListElement[] { elem };
			}
		}
		return null;
	}

	private IBuildpathEntry[] getRawBuildpath() {
		IBuildpathEntry[] currEntries = new IBuildpathEntry[fBuildPathList
				.getSize()];
		for (int i = 0; i < currEntries.length; i++) {
			BPListElement curr = (BPListElement) fBuildPathList.getElement(i);
			currEntries[i] = curr.getBuildpathEntry();
		}
		return currEntries;
	}

	public boolean isEntryKind(int kind) {
		return kind == IBuildpathEntry.BPE_LIBRARY
				|| kind == IBuildpathEntry.BPE_CONTAINER;
	}

	/*
	 * @see BuildPathBasePage#getSelection
	 */
	public List getSelection() {
		return fLibrariesList.getSelectedElements();
	}

	/*
	 * @see BuildPathBasePage#setSelection
	 */
	public void setSelection(List selElements, boolean expand) {
		fLibrariesList.selectElements(new StructuredSelection(selElements));
		if (expand) {
			for (int i = 0; i < selElements.size(); i++) {
				fLibrariesList.expandElement(selElements.get(i), 1);
			}
		}
	}

	public void setScriptProject(IScriptProject scriptProject) {
		this.scriptProject = scriptProject;
	}
}
