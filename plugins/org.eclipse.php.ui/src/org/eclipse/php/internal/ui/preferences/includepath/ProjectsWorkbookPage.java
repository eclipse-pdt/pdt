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
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.project.IIncludePathEntry;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.ListContentProvider;
import org.eclipse.php.internal.ui.util.PHPElementSorter;
import org.eclipse.php.internal.ui.util.PHPUILabelProvider;
import org.eclipse.php.internal.ui.util.PixelConverter;
import org.eclipse.php.internal.ui.wizards.fields.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class ProjectsWorkbookPage extends IncludePathBasePage {

	private final int IDX_ADDPROJECT = 0;

	private final int IDX_EDIT = 2;
	private final int IDX_REMOVE = 3;

	private ListDialogField fIncludePathList;
	private IProject fCurrPHPProject;

	private TreeListDialogField fProjectsList;

	private Control fSWTControl;

	private final IWorkbenchPreferenceContainer fPageContainer;

	public ProjectsWorkbookPage(ListDialogField includePathList, IWorkbenchPreferenceContainer pageContainer) {
		fIncludePathList = includePathList;
		fPageContainer = pageContainer;
		fSWTControl = null;

		String[] buttonLabels = new String[] { PHPUIMessages.getString("ProjectsWorkbookPage_projects_add_button"), null, PHPUIMessages.getString("ProjectsWorkbookPage_projects_edit_button"), PHPUIMessages.getString("ProjectsWorkbookPage_projects_remove_button") };

		ProjectsAdapter adapter = new ProjectsAdapter();

		fProjectsList = new TreeListDialogField(adapter, buttonLabels, new IPListLabelProvider());
		fProjectsList.setDialogFieldListener(adapter);
		fProjectsList.setLabelText(PHPUIMessages.getString("ProjectsWorkbookPage_projects_label"));

		fProjectsList.enableButton(IDX_REMOVE, false);
		fProjectsList.enableButton(IDX_EDIT, false);

		fProjectsList.setViewerSorter(new IPListElementSorter());
	}

	public void init(IProject project) {
		updateProjectsList(project);
	}

	private void updateProjectsList(IProject currProject) {
		// add the projects-ipentries that are already on the include path
		List cpelements = fIncludePathList.getElements();

		final List checkedProjects = new ArrayList(cpelements.size());

		for (int i = cpelements.size() - 1; i >= 0; i--) {
			IPListElement cpelem = (IPListElement) cpelements.get(i);
			if (isEntryKind(cpelem.getEntryKind())) {
				checkedProjects.add(cpelem);
			}
		}
		fProjectsList.setElements(checkedProjects);
		fCurrPHPProject = currProject;
	}

	// -------- UI creation ---------

	public Control getControl(Composite parent) {
		PixelConverter converter = new PixelConverter(parent);

		Composite composite = new Composite(parent, SWT.NONE);

		LayoutUtil.doDefaultLayout(composite, new DialogField[] { fProjectsList }, true, SWT.DEFAULT, SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fProjectsList.getTreeControl(null));

		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fProjectsList.setButtonsMinWidth(buttonBarWidth);

		fSWTControl = composite;

		return composite;
	}

	private void updateIncludePathList() {
		List projelements = fProjectsList.getElements();

		boolean remove = false;
		List cpelements = fIncludePathList.getElements();
		// backwards, as entries will be deleted
		for (int i = cpelements.size() - 1; i >= 0; i--) {
			IPListElement cpe = (IPListElement) cpelements.get(i);
			if (isEntryKind(cpe.getEntryKind())) {
				if (!projelements.remove(cpe)) {
					cpelements.remove(i);
					remove = true;
				}
			}
		}
		for (int i = 0; i < projelements.size(); i++) {
			cpelements.add(projelements.get(i));
		}
		if (remove || (projelements.size() > 0)) {
			fIncludePathList.setElements(cpelements);
		}
	}

	/*
	 * @see IncludePathBasePage#getSelection
	 */
	public List getSelection() {
		return fProjectsList.getSelectedElements();
	}

	/*
	 * @see IncludePathBasePage#setSelection
	 */
	public void setSelection(List selElements, boolean expand) {
		fProjectsList.selectElements(new StructuredSelection(selElements));
		if (expand) {
			for (int i = 0; i < selElements.size(); i++) {
				fProjectsList.expandElement(selElements.get(i), 1);
			}
		}
	}

	public boolean isEntryKind(int kind) {
		return kind == IIncludePathEntry.IPE_PROJECT;
	}

	private class ProjectsAdapter implements IDialogFieldListener, ITreeListAdapter {

		private final Object[] EMPTY_ARR = new Object[0];

		// -------- IListAdapter --------
		public void customButtonPressed(TreeListDialogField field, int index) {
			projectPageCustomButtonPressed(field, index);
		}

		public void selectionChanged(TreeListDialogField field) {
			projectPageSelectionChanged(field);
		}

		public void doubleClicked(TreeListDialogField field) {
			projectPageDoubleClicked(field);
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
			projectPageKeyPressed(field, event);
		}

		public Object[] getChildren(TreeListDialogField field, Object element) {
			if (element instanceof IPListElement) {
				return ((IPListElement) element).getChildren(false);
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
			projectPageDialogFieldChanged(field);
		}
	}

	private void projectPageCustomButtonPressed(DialogField field, int index) {
		IPListElement[] entries = null;
		switch (index) {
			case IDX_ADDPROJECT: /* add project */
				entries = openProjectDialog(null);
				break;
			case IDX_EDIT: /* edit */
				editEntry();
				return;
			case IDX_REMOVE: /* remove */
				removeEntry();
				return;
		}
		if (entries != null) {
			int nElementsChosen = entries.length;
			// remove duplicates
			List cplist = fProjectsList.getElements();
			List elementsToAdd = new ArrayList(nElementsChosen);
			for (int i = 0; i < nElementsChosen; i++) {
				IPListElement curr = entries[i];
				if (!cplist.contains(curr) && !elementsToAdd.contains(curr)) {
					elementsToAdd.add(curr);
				}
			}

			fProjectsList.addElements(elementsToAdd);
			if (index == IDX_ADDPROJECT) {
				fProjectsList.refresh();
			}
			fProjectsList.postSetSelection(new StructuredSelection(entries));
		}
	}

	private void removeEntry() {
		List selElements = fProjectsList.getSelectedElements();
		for (int i = selElements.size() - 1; i >= 0; i--) {
			Object elem = selElements.get(i);
			if (elem instanceof IPListElementAttribute) {
				IPListElementAttribute attrib = (IPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;

				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);
			}
		}
		if (selElements.isEmpty()) {
			fProjectsList.refresh();
			fIncludePathList.dialogFieldChanged(); // validate
		} else {
			fProjectsList.removeElements(selElements);
		}
	}

	private boolean canRemove(List selElements) {
		if (selElements.size() == 0) {
			return false;
		}
		int elements = 0;
		int attributes = 0;
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof IPListElementAttribute) {
				IPListElementAttribute attrib = (IPListElementAttribute) elem;

				if (attrib.getValue() == null) {
					return false;
				}
				attributes++;
			} else if (elem instanceof IPListElement) {
				elements++;
			}
		}
		return attributes == selElements.size() || elements == selElements.size();
	}

	private boolean canEdit(List selElements) {
		if (selElements.size() != 1) {
			return false;
		}
		Object elem = selElements.get(0);
		if (elem instanceof IPListElement) {
			return false;
		}
		if (elem instanceof IPListElementAttribute) {
			return true;
		}
		return false;
	}

	/**
	 * Method editEntry.
	 */
	private void editEntry() {
		List selElements = fProjectsList.getSelectedElements();
		if (selElements.size() != 1) {
			return;
		}
		Object elem = selElements.get(0);
		if (fProjectsList.getIndexOfElement(elem) != -1) {
			editElementEntry((IPListElement) elem);
		} else if (elem instanceof IPListElementAttribute) {
			editAttributeEntry((IPListElementAttribute) elem);
		}
	}

	private void editAttributeEntry(IPListElementAttribute elem) {
		String key = elem.getKey();
	}

	private void editElementEntry(IPListElement elem) {
		IPListElement[] res = openProjectDialog(elem);
		if (res != null && res.length > 0) {
			IPListElement curr = res[0];
			curr.setExported(elem.isExported());
			fProjectsList.replaceElement(elem, curr);
		}

	}

	private Shell getShell() {
		if (fSWTControl != null) {
			return fSWTControl.getShell();
		}
		return PHPUiPlugin.getActiveWorkbenchShell();
	}

	private IPListElement[] openProjectDialog(IPListElement elem) {

		ArrayList selectable = new ArrayList();
		selectable.addAll(Arrays.asList(PHPWorkspaceModelManager.getInstance().listProjects()));
		selectable.remove(fCurrPHPProject);

		List elements = fProjectsList.getElements();
		for (int i = 0; i < elements.size(); i++) {
			IPListElement curr = (IPListElement) elements.get(i);
			selectable.remove(curr.getResource());
		}
		Object[] selectArr = selectable.toArray();
		new PHPElementSorter().sort(null, selectArr);

		ListSelectionDialog dialog = new ListSelectionDialog(getShell(), Arrays.asList(selectArr), new ListContentProvider(), new PHPUILabelProvider(), PHPUIMessages.getString("ProjectsWorkbookPage_chooseProjects_message"));
		dialog.setTitle(PHPUIMessages.getString("ProjectsWorkbookPage_chooseProjects_title"));
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			IPListElement[] cpElements = new IPListElement[result.length];
			for (int i = 0; i < result.length; i++) {
				IProject curr = (IProject) result[i];
				cpElements[i] = new IPListElement(fCurrPHPProject, IIncludePathEntry.IPE_PROJECT, IIncludePathEntry.K_SOURCE, curr.getFullPath(), curr);
			}
			return cpElements;
		}

		return null;
	}

	protected void projectPageDoubleClicked(TreeListDialogField field) {
		List selection = fProjectsList.getSelectedElements();
		if (canEdit(selection)) {
			editEntry();
		}
	}

	protected void projectPageKeyPressed(TreeListDialogField field, KeyEvent event) {
		if (field == fProjectsList) {
			if (event.character == SWT.DEL && event.stateMask == 0) {
				List selection = field.getSelectedElements();
				if (canRemove(selection)) {
					removeEntry();
				}
			}
		}
	}

	private void projectPageDialogFieldChanged(DialogField field) {
		if (fCurrPHPProject != null) {
			// already initialized
			updateIncludePathList();
		}
	}

	private void projectPageSelectionChanged(DialogField field) {
		List selElements = fProjectsList.getSelectedElements();
		fProjectsList.enableButton(IDX_EDIT, canEdit(selElements));
		fProjectsList.enableButton(IDX_REMOVE, canRemove(selElements));

		boolean noAttributes = containsOnlyTopLevelEntries(selElements);
		fProjectsList.enableButton(IDX_ADDPROJECT, noAttributes);
	}

}
