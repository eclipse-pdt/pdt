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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.ModelElementSorter;
import org.eclipse.dltk.ui.util.PixelConverter;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

public class PHPProjectsWorkbookPage extends BuildPathBasePage {

	private final int IDX_ADDPROJECT = 0;

	private final int IDX_EDIT = 2;
	private final int IDX_REMOVE = 3;

	private ListDialogField fBuildpathList;
	private IScriptProject fCurrJProject;

	private TreeListDialogField fProjectsList;

	private Control fSWTControl;

	private final IWorkbenchPreferenceContainer fPageContainer;

	public PHPProjectsWorkbookPage(ListDialogField buildpathList,
			IWorkbenchPreferenceContainer pageContainer) {
		fBuildpathList = buildpathList;
		fPageContainer = pageContainer;
		fSWTControl = null;

		String[] buttonLabels = new String[] {
				NewWizardMessages.ProjectsWorkbookPage_projects_add_button,
				null,
				NewWizardMessages.ProjectsWorkbookPage_projects_edit_button,
				NewWizardMessages.ProjectsWorkbookPage_projects_remove_button };

		ProjectsAdapter adapter = new ProjectsAdapter();

		fProjectsList = new TreeListDialogField(adapter, buttonLabels,
				new PHPIPListLabelProvider());
		fProjectsList.setDialogFieldListener(adapter);
		fProjectsList
				.setLabelText(NewWizardMessages.ProjectsWorkbookPage_projects_label);

		fProjectsList.enableButton(IDX_REMOVE, false);
		fProjectsList.enableButton(IDX_EDIT, false);

		fProjectsList.setViewerSorter(new BPListElementSorter());
	}

	public void init(IScriptProject jproject) {
		updateProjectsList(jproject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage#setTitle
	 * (java.lang.String)
	 */
	public void setTitle(String title) {
		fProjectsList.setLabelText(title);
	}

	private void updateProjectsList(IScriptProject currJProject) {
		// add the projects-cpentries that are already on the class path
		List cpelements = fBuildpathList.getElements();

		final List checkedProjects = new ArrayList(cpelements.size());

		for (int i = cpelements.size() - 1; i >= 0; i--) {
			BPListElement cpelem = (BPListElement) cpelements.get(i);
			if (isEntryKind(cpelem.getEntryKind())) {
				checkedProjects.add(cpelem);
			}
		}
		fProjectsList.setElements(checkedProjects);
		fCurrJProject = currJProject;
	}

	// -------- UI creation ---------

	public Control getControl(Composite parent) {
		PixelConverter converter = new PixelConverter(parent);

		Composite composite = new Composite(parent, SWT.NONE);

		LayoutUtil.doDefaultLayout(composite,
				new DialogField[] { fProjectsList }, true, SWT.DEFAULT,
				SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fProjectsList.getTreeControl(null));

		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fProjectsList.setButtonsMinWidth(buttonBarWidth);

		fSWTControl = composite;

		return composite;
	}

	private void updateBuildpathList() {
		List projelements = fProjectsList.getElements();

		boolean remove = false;
		List cpelements = fBuildpathList.getElements();
		// backwards, as entries will be deleted
		for (int i = cpelements.size() - 1; i >= 0; i--) {
			BPListElement cpe = (BPListElement) cpelements.get(i);
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
			fBuildpathList.setElements(cpelements);
		}
	}

	/*
	 * @see BuildPathBasePage#getSelection
	 */
	public List getSelection() {
		return fProjectsList.getSelectedElements();
	}

	/*
	 * @see BuildPathBasePage#setSelection
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
		return kind == IBuildpathEntry.BPE_PROJECT;
	}

	private class ProjectsAdapter implements IDialogFieldListener,
			ITreeListAdapter {

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
			if (element instanceof BPListElement) {
				return ((BPListElement) element).getChildren();
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
			projectPageDialogFieldChanged(field);
		}
	}

	private void projectPageCustomButtonPressed(DialogField field, int index) {
		BPListElement[] entries = null;
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
				BPListElement curr = entries[i];
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
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;
				if (key.equals(BPListElement.ACCESSRULES)) {
					value = new IAccessRule[0];
				}
				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);
			}
		}
		if (selElements.isEmpty()) {
			fProjectsList.refresh();
			fBuildpathList.dialogFieldChanged(); // validate
		} else {

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
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				if (attrib.getKey().equals(BPListElement.ACCESSRULES)) {
					return ((IAccessRule[]) attrib.getValue()).length > 0;
				}
				if (attrib.getValue() == null) {
					return false;
				}
				attributes++;
			} else if (elem instanceof BPListElement) {
				elements++;
			}
		}
		return attributes == selElements.size()
				|| elements == selElements.size();
	}

	private boolean canEdit(List selElements) {
		if (selElements.size() != 1) {
			return false;
		}
		Object elem = selElements.get(0);
		if (elem instanceof BPListElement) {
			return false;
		}
		if (elem instanceof BPListElementAttribute) {
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
			editElementEntry((BPListElement) elem);
		} else if (elem instanceof BPListElementAttribute) {
			editAttributeEntry((BPListElementAttribute) elem);
		}
	}

	private void editAttributeEntry(BPListElementAttribute elem) {
		String key = elem.getKey();
		if (key.equals(BPListElement.ACCESSRULES)) {
			showAccessRestrictionDialog(elem.getParent());
		}
		// else if (key.equals(BPListElement.NATIVE_LIB_PATH)) {
		// BPListElement selElement= elem.getParent();
		// NativeLibrariesDialog dialog= new NativeLibrariesDialog(getShell(),
		// selElement);
		// if (dialog.open() == Window.OK) {
		// selElement.setAttribute(BPListElement.NATIVE_LIB_PATH,
		// dialog.getNativeLibraryPath());
		// fProjectsList.refresh();
		// fClassPathList.dialogFieldChanged(); // validate
		// }
		// }
	}

	private void showAccessRestrictionDialog(BPListElement selElement) {
		AccessRulesDialog dialog = new AccessRulesDialog(getShell(),
				selElement, fCurrJProject, fPageContainer != null);
		int res = dialog.open();
		if (res == Window.OK || res == AccessRulesDialog.SWITCH_PAGE) {
			selElement.setAttribute(BPListElement.ACCESSRULES, dialog
					.getAccessRules());
			selElement.setAttribute(BPListElement.COMBINE_ACCESSRULES,
					new Boolean(dialog.doCombineAccessRules()));
			fProjectsList.refresh();
			fBuildpathList.dialogFieldChanged(); // validate

			if (res == AccessRulesDialog.SWITCH_PAGE) {
				dialog.performPageSwitch(fPageContainer);
			}
		}
	}

	private void editElementEntry(BPListElement elem) {
		BPListElement[] res = openProjectDialog(elem);
		if (res != null && res.length > 0) {
			BPListElement curr = res[0];
			curr.setExported(elem.isExported());
			fProjectsList.replaceElement(elem, curr);
		}

	}

	private Shell getShell() {
		if (fSWTControl != null) {
			return fSWTControl.getShell();
		}
		return DLTKUIPlugin.getActiveWorkbenchShell();
	}

	private BPListElement[] openProjectDialog(BPListElement elem) {

		try {
			ArrayList selectable = new ArrayList();
			final IScriptModel model = fCurrJProject.getModel();
			final IDLTKLanguageToolkit toolkit = fCurrJProject
					.getLanguageToolkit();
			selectable.addAll(Arrays.asList(toolkit != null ? model
					.getScriptProjects(toolkit.getNatureId()) : model
					.getScriptProjects()));
			selectable.remove(fCurrJProject);

			List elements = fProjectsList.getElements();
			for (int i = 0; i < elements.size(); i++) {
				BPListElement curr = (BPListElement) elements.get(i);
				IScriptProject proj = (IScriptProject) DLTKCore.create(curr
						.getResource());
				selectable.remove(proj);
			}
			Object[] selectArr = selectable.toArray();
			new ModelElementSorter().sort(null, selectArr);
			// IScriptProject project = elem.getScriptProject();
			ScriptUILabelProvider labelProvider = new ScriptUILabelProvider();
			ListSelectionDialog dialog = new ListSelectionDialog(
					getShell(),
					Arrays.asList(selectArr),
					new ArrayContentProvider(),
					labelProvider,
					NewWizardMessages.ProjectsWorkbookPage_chooseProjects_message);
			dialog
					.setTitle(NewWizardMessages.ProjectsWorkbookPage_chooseProjects_title);
			dialog.setHelpAvailable(false);
			if (dialog.open() == Window.OK) {
				Object[] result = dialog.getResult();
				BPListElement[] cpElements = new BPListElement[result.length];
				for (int i = 0; i < result.length; i++) {
					IScriptProject curr = (IScriptProject) result[i];
					cpElements[i] = new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_PROJECT, curr.getPath(), curr
									.getResource(), false);
				}
				return cpElements;
			}
		} catch (ModelException e) {
			return null;
		}
		return null;
	}

	protected void projectPageDoubleClicked(TreeListDialogField field) {
		List selection = fProjectsList.getSelectedElements();
		if (canEdit(selection)) {
			editEntry();
		}
	}

	protected void projectPageKeyPressed(TreeListDialogField field,
			KeyEvent event) {
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
		if (fCurrJProject != null) {
			// already initialized
			updateBuildpathList();
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
