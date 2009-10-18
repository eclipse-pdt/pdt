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
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.corext.buildpath.BuildpathModifier;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.*;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.actions.AbstractOpenWizardAction;
import org.eclipse.dltk.ui.util.PixelConverter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.internal.IChangeListener;

public class PHPSourceContainerWorkbookPage extends BuildPathBasePage {

	public class OpenBuildPathWizardAction extends AbstractOpenWizardAction
			implements IPropertyChangeListener {

		private final BuildPathWizard fWizard;
		private final List fSelectedElements;

		public OpenBuildPathWizardAction(BuildPathWizard wizard) {
			fWizard = wizard;
			addPropertyChangeListener(this);
			fSelectedElements = fFoldersList.getSelectedElements();
		}

		/**
		 * {@inheritDoc}
		 */
		protected INewWizard createWizard() throws CoreException {
			return fWizard;
		}

		/**
		 * {@inheritDoc}
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(IAction.RESULT)) {
				if (event.getNewValue().equals(Boolean.TRUE)) {
					finishWizard();
				} else {
					fWizard.cancel();
				}
			}
		}

		protected void finishWizard() {
			List insertedElements = fWizard.getInsertedElements();
			refresh(insertedElements, fWizard.getRemovedElements(), fWizard
					.getModifiedElements());

			if (insertedElements.isEmpty()) {
				fFoldersList.postSetSelection(new StructuredSelection(
						fSelectedElements));
			}
		}

	}

	protected static AddSourceFolderWizard newSourceFolderWizard(
			BPListElement element, List/* <BPListElement> */existingElements,
			boolean newFolder) {
		BPListElement[] existing = (BPListElement[]) existingElements
				.toArray(new BPListElement[existingElements.size()]);
		AddSourceFolderWizard wizard = new AddSourceFolderWizard(existing,
				element, false, newFolder, newFolder, newFolder ? BPListElement
						.isProjectSourceFolder(existing, element
								.getScriptProject()) : false, newFolder);
		wizard.setDoFlushChange(false);
		return wizard;
	}

	private static AddSourceFolderWizard newLinkedSourceFolderWizard(
			BPListElement element, List/* <BPListElement> */existingElements,
			boolean newFolder) {
		BPListElement[] existing = (BPListElement[]) existingElements
				.toArray(new BPListElement[existingElements.size()]);
		AddSourceFolderWizard wizard = new AddSourceFolderWizard(existing,
				element, true, newFolder, newFolder, newFolder ? BPListElement
						.isProjectSourceFolder(existing, element
								.getScriptProject()) : false, newFolder);
		wizard.setDoFlushChange(false);
		return wizard;
	}

	private static EditFilterWizard newEditFilterWizard(BPListElement element,
			List/* <BPListElement> */existingElements) {
		BPListElement[] existing = (BPListElement[]) existingElements
				.toArray(new BPListElement[existingElements.size()]);
		EditFilterWizard result = new EditFilterWizard(existing, element);
		result.setDoFlushChange(false);
		return result;
	}

	protected ListDialogField fBuildpathList;
	protected IScriptProject fCurrJProject;

	private Control fSWTControl;
	protected TreeListDialogField fFoldersList;

	private final int IDX_ADD = 0;
	private final int IDX_ADD_LINK = 1;
	private final int IDX_EDIT = 3;
	private final int IDX_REMOVE = 4;

	protected List<IChangeListener> addedElementListeners = new ArrayList<IChangeListener>(
			1);

	protected int getIDX_ADD() {
		return IDX_ADD;
	}

	protected int getIDX_ADD_LINK() {
		return IDX_ADD_LINK;
	}

	protected int getIDX_EDIT() {
		return IDX_EDIT;
	}

	protected int getIDX_REMOVE() {
		return IDX_REMOVE;
	}

	public PHPSourceContainerWorkbookPage(ListDialogField buildpathList) {
		fBuildpathList = buildpathList;
		fSWTControl = null;
	}

	/**
	 * Initialize container elements. This code should not be in the constructor
	 * in order to enable extensibility
	 */
	protected void initContainerElements() {
		SourceContainerAdapter adapter = new SourceContainerAdapter();

		String[] buttonLabels;

		buttonLabels = new String[] {
				NewWizardMessages.SourceContainerWorkbookPage_folders_add_button,
				NewWizardMessages.SourceContainerWorkbookPage_folders_link_source_button,
				/* 1 */null,
				NewWizardMessages.SourceContainerWorkbookPage_folders_edit_button,
				NewWizardMessages.SourceContainerWorkbookPage_folders_remove_button };

		fFoldersList = new TreeListDialogField(adapter, buttonLabels,
				new PHPIPListLabelProvider());
		fFoldersList.setDialogFieldListener(adapter);
		fFoldersList
				.setLabelText(NewWizardMessages.SourceContainerWorkbookPage_folders_label);

		fFoldersList.setViewerSorter(new BPListElementSorter());
		fFoldersList.enableButton(getIDX_EDIT(), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage#setTitle
	 * (java.lang.String)
	 */
	public void setTitle(String title) {
		fFoldersList.setLabelText(title);
	}

	public void init(IScriptProject jproject) {
		fCurrJProject = jproject;
		updateFoldersList();
	}

	protected void updateFoldersList() {
		ArrayList folders = new ArrayList();

		List cpelements = fBuildpathList.getElements();
		for (int i = 0; i < cpelements.size(); i++) {
			BPListElement cpe = (BPListElement) cpelements.get(i);
			if (cpe.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
				folders.add(cpe);
			}
		}
		fFoldersList.setElements(folders);

		for (int i = 0; i < folders.size(); i++) {
			BPListElement cpe = (BPListElement) folders.get(i);
			IPath[] ePatterns = (IPath[]) cpe
					.getAttribute(BPListElement.EXCLUSION);
			IPath[] iPatterns = (IPath[]) cpe
					.getAttribute(BPListElement.INCLUSION);
			if (ePatterns.length > 0 || iPatterns.length > 0) {
				fFoldersList.expandElement(cpe, 3);
			}
		}
	}

	public Control getControl(Composite parent) {

		initContainerElements();

		PixelConverter converter = new PixelConverter(parent);
		Composite composite = new Composite(parent, SWT.NONE);

		LayoutUtil.doDefaultLayout(composite,
				new DialogField[] { fFoldersList }, true, SWT.DEFAULT,
				SWT.DEFAULT);
		LayoutUtil.setHorizontalGrabbing(fFoldersList.getTreeControl(null));

		int buttonBarWidth = converter.convertWidthInCharsToPixels(24);
		fFoldersList.setButtonsMinWidth(buttonBarWidth);

		fSWTControl = composite;

		// expand
		List elements = fFoldersList.getElements();
		for (int i = 0; i < elements.size(); i++) {
			BPListElement elem = (BPListElement) elements.get(i);
			IPath[] exclusionPatterns = (IPath[]) elem
					.getAttribute(BPListElement.EXCLUSION);
			IPath[] inclusionPatterns = (IPath[]) elem
					.getAttribute(BPListElement.INCLUSION);
			if (exclusionPatterns.length > 0 || inclusionPatterns.length > 0) {
				fFoldersList.expandElement(elem, 3);
			}
		}
		return composite;
	}

	protected Shell getShell() {
		if (fSWTControl != null && !fSWTControl.isDisposed()) {
			return fSWTControl.getShell();
		}
		return DLTKUIPlugin.getActiveWorkbenchShell();
	}

	public class SourceContainerAdapter implements ITreeListAdapter,
			IDialogFieldListener {

		private final Object[] EMPTY_ARR = new Object[0];

		// -------- IListAdapter --------
		public void customButtonPressed(TreeListDialogField field, int index) {
			sourcePageCustomButtonPressed(field, index);
		}

		public void selectionChanged(TreeListDialogField field) {
			sourcePageSelectionChanged(field);
		}

		public void doubleClicked(TreeListDialogField field) {
			sourcePageDoubleClicked(field);
		}

		public void keyPressed(TreeListDialogField field, KeyEvent event) {
			sourcePageKeyPressed(field, event);
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
			return (element instanceof BPListElement);
		}

		// ---------- IDialogFieldListener --------
		public void dialogFieldChanged(DialogField field) {
			sourcePageDialogFieldChanged(field);
		}

	}

	protected void sourcePageKeyPressed(TreeListDialogField field,
			KeyEvent event) {
		if (field == fFoldersList) {
			if (event.character == SWT.DEL && event.stateMask == 0) {
				List selection = field.getSelectedElements();
				if (canRemove(selection)) {
					removeEntry();
				}
			}
		}
	}

	protected void sourcePageDoubleClicked(TreeListDialogField field) {
		if (field == fFoldersList) {
			List selection = field.getSelectedElements();
			if (canEdit(selection)) {
				editEntry();
			}
		}
	}

	protected void sourcePageCustomButtonPressed(DialogField field, int index) {
		if (field == fFoldersList) {
			if (index == getIDX_ADD()) {
				IProject project = fCurrJProject.getProject();
				if (project.exists() && hasFolders(project)) {
					List existingElements = fFoldersList.getElements();
					BPListElement[] existing = (BPListElement[]) existingElements
							.toArray(new BPListElement[existingElements.size()]);
					CreateMultipleSourceFoldersDialog dialog = new CreateMultipleSourceFoldersDialog(
							fCurrJProject, existing, getShell());
					if (dialog.open() == Window.OK) {
						refresh(dialog.getInsertedElements(), dialog
								.getRemovedElements(), dialog
								.getModifiedElements());
					}
				} else {
					BPListElement newElement = new BPListElement(fCurrJProject,
							IBuildpathEntry.BPE_SOURCE, false);
					AddSourceFolderWizard wizard = newSourceFolderWizard(
							newElement, fFoldersList.getElements(), true);
					OpenBuildPathWizardAction action = new OpenBuildPathWizardAction(
							wizard);
					action.run();
				}
			} else if (index == getIDX_ADD_LINK()) {
				BPListElement newElement = new BPListElement(fCurrJProject,
						IBuildpathEntry.BPE_SOURCE, false);
				AddSourceFolderWizard wizard = newLinkedSourceFolderWizard(
						newElement, fFoldersList.getElements(), true);
				OpenBuildPathWizardAction action = new OpenBuildPathWizardAction(
						wizard);
				action.run();
			} else if (index == getIDX_EDIT()) {
				editEntry();
			} else if (index == getIDX_REMOVE()) {
				removeEntry();
			}
		}
	}

	protected boolean hasFolders(IContainer container) {

		try {
			IResource[] members = container.members();
			for (int i = 0; i < members.length; i++) {
				if (members[i] instanceof IContainer) {
					return true;
				}
			}
		} catch (CoreException e) {
			// ignore
		}

		List elements = fFoldersList.getElements();
		if (elements.size() > 1)
			return true;

		if (elements.size() == 0)
			return false;

		BPListElement single = (BPListElement) elements.get(0);
		if (single.getPath().equals(fCurrJProject.getPath()))
			return false;

		return true;
	}

	private void editEntry() {
		List selElements = fFoldersList.getSelectedElements();
		if (selElements.size() != 1) {
			return;
		}
		Object elem = selElements.get(0);
		if (fFoldersList.getIndexOfElement(elem) != -1) {
			editElementEntry((BPListElement) elem);
		} else if (elem instanceof BPListElementAttribute) {
			editAttributeEntry((BPListElementAttribute) elem);
		}
	}

	private void editElementEntry(BPListElement elem) {
		if (elem.getLinkTarget() != null) {
			AddSourceFolderWizard wizard = newLinkedSourceFolderWizard(elem,
					fFoldersList.getElements(), false);
			OpenBuildPathWizardAction action = new OpenBuildPathWizardAction(
					wizard);
			action.run();
		} else {
			AddSourceFolderWizard wizard = newSourceFolderWizard(elem,
					fFoldersList.getElements(), false);
			OpenBuildPathWizardAction action = new OpenBuildPathWizardAction(
					wizard);
			action.run();
		}
	}

	private void editAttributeEntry(BPListElementAttribute elem) {
		String key = elem.getKey();
		if (key.equals(BPListElement.EXCLUSION)
				|| key.equals(BPListElement.INCLUSION)) {
			EditFilterWizard wizard = newEditFilterWizard(elem.getParent(),
					fFoldersList.getElements());
			OpenBuildPathWizardAction action = new OpenBuildPathWizardAction(
					wizard);
			action.run();
			fFoldersList.refresh();
			fBuildpathList.dialogFieldChanged();
		}
		// else if (key.equals(BPListElement.NATIVE_LIB_PATH)) {
		// BPListElement selElement= elem.getParent();
		// NativeLibrariesDialog dialog= new NativeLibrariesDialog(getShell(),
		// selElement);
		// if (dialog.open() == Window.OK) {
		// selElement.setAttribute(BPListElement.NATIVE_LIB_PATH,
		// dialog.getNativeLibraryPath());
		// fFoldersList.refresh();
		// fClassPathList.dialogFieldChanged(); // validate
		// }
		// }
	}

	protected void sourcePageSelectionChanged(DialogField field) {
		List selected = fFoldersList.getSelectedElements();
		fFoldersList.enableButton(getIDX_EDIT(), canEdit(selected));
		fFoldersList.enableButton(getIDX_REMOVE(), canRemove(selected));
		boolean noAttributes = containsOnlyTopLevelEntries(selected);
		fFoldersList.enableButton(getIDX_ADD(), noAttributes);
	}

	protected void removeEntry() {
		List selElements = fFoldersList.getSelectedElements();
		for (int i = selElements.size() - 1; i >= 0; i--) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				String key = attrib.getKey();
				Object value = null;
				if (key.equals(BPListElement.EXCLUSION)
						|| key.equals(BPListElement.INCLUSION)) {
					value = new Path[0];
				}
				attrib.getParent().setAttribute(key, value);
				selElements.remove(i);
			}
		}
		if (selElements.isEmpty()) {
			fFoldersList.refresh();
			fBuildpathList.dialogFieldChanged(); // validate
		} else {
			for (Iterator iter = selElements.iterator(); iter.hasNext();) {
				BPListElement element = (BPListElement) iter.next();
				if (element.getEntryKind() == IBuildpathEntry.BPE_SOURCE) {
					List list = BuildpathModifier.removeFilters(element
							.getPath(), fCurrJProject, fFoldersList
							.getElements());
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						BPListElement modified = (BPListElement) iterator
								.next();
						fFoldersList.refresh(modified);
						fFoldersList.expandElement(modified, 3);
					}
				}
			}
			fFoldersList.removeElements(selElements);
		}
	}

	protected boolean canRemove(List selElements) {
		if (selElements.size() == 0) {
			return false;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElementAttribute) {
				BPListElementAttribute attrib = (BPListElementAttribute) elem;
				String key = attrib.getKey();
				if (BPListElement.INCLUSION.equals(key)) {
					if (((IPath[]) attrib.getValue()).length == 0) {
						return false;
					}
				} else if (BPListElement.EXCLUSION.equals(key)) {
					if (((IPath[]) attrib.getValue()).length == 0) {
						return false;
					}
				} else if (attrib.getValue() == null) {
					return false;
				}
			} else if (elem instanceof BPListElement) {
				BPListElement curr = (BPListElement) elem;
				if (curr.getParentContainer() != null) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean canEdit(List selElements) {
		if (selElements.size() != 1) {
			return false;
		}
		Object elem = selElements.get(0);
		if (elem instanceof BPListElement) {
			BPListElement cp = ((BPListElement) elem);
			if (cp.getPath().equals(cp.getScriptProject().getPath()))
				return false;

			return true;
		}
		if (elem instanceof BPListElementAttribute) {
			return true;
		}
		return false;
	}

	private void sourcePageDialogFieldChanged(DialogField field) {
		if (fCurrJProject == null) {
			// not initialized
			return;
		}

		if (field == fFoldersList) {
			updateBuildpathList();
		}
	}

	private void updateBuildpathList() {
		List srcelements = fFoldersList.getElements();

		List cpelements = fBuildpathList.getElements();
		int nEntries = cpelements.size();
		// backwards, as entries will be deleted
		int lastRemovePos = nEntries;
		int afterLastSourcePos = 0;
		for (int i = nEntries - 1; i >= 0; i--) {
			BPListElement cpe = (BPListElement) cpelements.get(i);
			int kind = cpe.getEntryKind();
			if (isEntryKind(kind)) {
				if (!srcelements.remove(cpe)) {
					cpelements.remove(i);
					lastRemovePos = i;
				} else if (lastRemovePos == nEntries) {
					afterLastSourcePos = i + 1;
				}
			}
		}

		if (!srcelements.isEmpty()) {
			int insertPos = Math.min(afterLastSourcePos, lastRemovePos);
			cpelements.addAll(insertPos, srcelements);
		}

		if (lastRemovePos != nEntries || !srcelements.isEmpty()) {
			fBuildpathList.setElements(cpelements);
		}
	}

	/*
	 * @see BuildPathBasePage#getSelection
	 */
	public List getSelection() {
		return fFoldersList.getSelectedElements();
	}

	/*
	 * @see BuildPathBasePage#setSelection
	 */
	public void setSelection(List selElements, boolean expand) {
		fFoldersList.selectElements(new StructuredSelection(selElements));
		if (expand) {
			for (int i = 0; i < selElements.size(); i++) {
				fFoldersList.expandElement(selElements.get(i), 1);
			}
		}
	}

	public boolean isEntryKind(int kind) {
		return kind == IBuildpathEntry.BPE_SOURCE;
	}

	protected void refresh(List insertedElements, List removedElements,
			List modifiedElements) {
		fFoldersList.addElements(insertedElements);
		for (Iterator iter = insertedElements.iterator(); iter.hasNext();) {
			BPListElement element = (BPListElement) iter.next();
			fFoldersList.expandElement(element, 3);
		}

		fFoldersList.removeElements(removedElements);

		for (Iterator iter = modifiedElements.iterator(); iter.hasNext();) {
			BPListElement element = (BPListElement) iter.next();
			fFoldersList.refresh(element);
			fFoldersList.expandElement(element, 3);
		}

		fFoldersList.refresh(); // does enforce the order of the entries.
		if (!insertedElements.isEmpty()) {
			fFoldersList.postSetSelection(new StructuredSelection(
					insertedElements));
		}
	}

	public void registerAddedElementListener(IChangeListener listener) {
		if (listener != null) {
			addedElementListeners.add(listener);
		}
	}

	public void unregisterAddedElementListener(IChangeListener listener) {
		if (listener != null) {
			addedElementListeners.remove(listener);
		}
	}

}
