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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElementAttribute;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElementSorter;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.ListDialogField;
import org.eclipse.dltk.internal.ui.wizards.dialogfields.TreeListDialogField;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.php.internal.core.buildpath.BuildPathUtils;
import org.eclipse.php.internal.core.includepath.IncludePath;
import org.eclipse.php.internal.core.includepath.IncludePathManager;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.IChangeListener;

/**
 * Source page for the include path preference page
 * 
 * @author Eden K., 2008
 * 
 */
public class PHPIncludePathSourcePage extends PHPSourceContainerWorkbookPage {

	// redefine the indexes of the buttons
	protected int IDX_ADD = 0;
	protected int IDX_REMOVE = 1;
	protected int IDX_ADD_LINK = 2;
	protected int IDX_EDIT = 3;

	private List<BPListElement> fAddedElements = new ArrayList<BPListElement>(1);

	private boolean addToBuildPath = false;

	public boolean shouldAddToBuildPath() {
		return addToBuildPath;
	}

	public List<BPListElement> getAddedElements() {
		return fAddedElements;
	}

	public PHPIncludePathSourcePage(ListDialogField buildpathList) {
		super(buildpathList);
	}

	/**
	 * This is actually the content provider for the folders list. override the
	 * hasChildren method according to the filter
	 * 
	 * @author Eden K., 2008
	 * 
	 */
	protected class PHPSourceContainerAdapter extends SourceContainerAdapter {
		public boolean hasChildren(TreeListDialogField field, Object element) {
			return false;
		}
	}

	/**
	 * Define which elements in the tree should be displayed
	 * 
	 * @param element
	 * @return
	 */
	private boolean shouldDisplayElement(Object element) {
		if (element instanceof BPListElementAttribute) {
			BPListElementAttribute attribute = (BPListElementAttribute) element;
			String key = attribute.getKey();
			// do not display include and exclude nodes
			if (key.equals(BPListElement.INCLUSION)
					|| key.equals(BPListElement.EXCLUSION)) {
				return false;
			}
		}
		return true;
	}

	protected boolean canRemove(List selElements) {
		if (selElements.size() == 0) {
			return false;
		}
		for (int i = 0; i < selElements.size(); i++) {
			Object elem = selElements.get(i);
			if (elem instanceof BPListElement) {
				BPListElement curr = (BPListElement) elem;
				if (BuildPathUtils.isInBuildpath(curr.getPath(), fCurrJProject)) {
					return false;
				}
			}
		}
		return super.canRemove(selElements);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.dltk.internal.ui.wizards.buildpath.SourceContainerWorkbookPage
	 * #initContainerElements()
	 */
	protected void initContainerElements() {
		SourceContainerAdapter adapter = new PHPSourceContainerAdapter();

		String[] buttonLabels;

		buttonLabels = new String[] {
				NewWizardMessages.SourceContainerWorkbookPage_folders_add_button,
				NewWizardMessages.SourceContainerWorkbookPage_folders_remove_button };

		fFoldersList = new TreeListDialogField(adapter, buttonLabels,
				new PHPIPListLabelProvider());
		fFoldersList.setDialogFieldListener(adapter);
		fFoldersList
				.setLabelText(PHPUIMessages.IncludePathSourcePage_Folders_Label); 

		fFoldersList.setViewerSorter(new BPListElementSorter());

	}

	protected void updateFoldersList() {
		ArrayList folders = new ArrayList();

		IncludePath[] includePath = IncludePathManager.getInstance()
				.getIncludePaths(fCurrJProject.getProject());

		// the include path is made of resources and/or buildpath entries
		// extract the resource out of the entries and create "build path list"
		// elements
		// for display purposes
		List<IncludePath> includePathEntries = Arrays.asList(includePath);
		for (IncludePath entry : includePathEntries) {
			Object includePathEntry = entry.getEntry();
			IResource resource = null;
			if (!(includePathEntry instanceof IBuildpathEntry)) {
				resource = (IResource) includePathEntry;
				folders.add(new BPListElement(fCurrJProject,
						IBuildpathEntry.BPE_SOURCE, resource.getFullPath(),
						resource, false));
			}
		}
		fFoldersList.setElements(folders);

	}

	/**
	 * Get the original functionality and add a filter
	 */
	public Control getControl(Composite parent) {
		Control control = super.getControl(parent);
		addFilter();
		return control;
	}

	private void addFilter() {
		fFoldersList.getTreeViewer().addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement,
					Object element) {
				return shouldDisplayElement(element);
			}
		});
	}

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

	protected void sourcePageCustomButtonPressed(DialogField field, int index) {
		if (field == fFoldersList) {
			if (index == IDX_ADD) {
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
			} else {
				super.sourcePageCustomButtonPressed(field, index);
			}
		}
	}

	protected void refresh(List insertedElements, List removedElements,
			List modifiedElements) {

		fAddedElements.clear();

		fFoldersList.addElements(insertedElements);

		// for each added source entry, check if it is already a part of the
		// buildpath
		// in case it is not, add the entry to the added elements list
		// and ask the user if he would like to add it to the build path as well

		for (Iterator iterator = insertedElements.iterator(); iterator
				.hasNext();) {
			BPListElement element = (BPListElement) iterator.next();
			if (!BuildPathUtils.isContainedInBuildpath(element.getPath(),
					fCurrJProject)) {
				fAddedElements.add(element);
			}
		}

		if (fAddedElements.size() > 0) {
			addToBuildPath = IncludePathUtils.openConfirmationDialog(
					getShell(), PHPUIMessages.IncludePath_AddEntryTitle,
					PHPUIMessages.IncludePath_AddEntryToBuildPathMessage); //
			for (IChangeListener listener : addedElementListeners) {
				listener.update(true);
			}
		}

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
}
