/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 
 *******************************************************************************/
package org.eclipse.php.internal.ui.preferences.includepath;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuildpathEntry;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.wizards.NewWizardMessages;
import org.eclipse.dltk.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BPListElement;
import org.eclipse.dltk.internal.ui.wizards.buildpath.BuildPathBasePage;
import org.eclipse.dltk.ui.actions.AbstractOpenWizardAction;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class CreateMultipleSourceFoldersDialog extends TrayDialog {

	private final class FakeFolderBaseWorkbenchContentProvider extends
			BaseWorkbenchContentProvider {
		/**
		 * {@inheritDoc}
		 */
		public Object getParent(Object element) {
			Object object = fNonExistingFolders.get(element);
			if (object != null)
				return object;

			return super.getParent(element);
		}

		/**
		 * {@inheritDoc}
		 */
		public Object[] getChildren(Object element) {
			List result = new ArrayList();
			// all keys with value element
			Set keys = fNonExistingFolders.keySet();
			for (Iterator iter = keys.iterator(); iter.hasNext();) {
				Object key = iter.next();
				if (fNonExistingFolders.get(key).equals(element)) {
					result.add(key);
				}
			}
			if (result.size() == 0)
				return super.getChildren(element);

			Object[] children = super.getChildren(element);
			for (int i = 0; i < children.length; i++) {
				result.add(children[i]);
			}
			return result.toArray();
		}
	}

	private final IScriptProject fScriptProject;
	private final BPListElement[] fExistingElements;
	private final HashSet fRemovedElements;
	private final HashSet fModifiedElements;
	private final HashSet fInsertedElements;
	private final Hashtable fNonExistingFolders;

	public CreateMultipleSourceFoldersDialog(
			final IScriptProject scriptProject,
			final BPListElement[] existingElements, Shell shell) {
		super(shell);
		fScriptProject = scriptProject;
		fExistingElements = existingElements;
		fRemovedElements = new HashSet();
		fModifiedElements = new HashSet();
		fInsertedElements = new HashSet();
		fNonExistingFolders = new Hashtable();

		for (int i = 0; i < existingElements.length; i++) {
			BPListElement cur = existingElements[i];
			if (cur.getResource() == null || !cur.getResource().exists()) {
				addFakeFolder(fScriptProject.getProject(), cur);
			}
		}
	}

	public int open() {
		Class[] acceptedClasses = new Class[] { IProject.class, IFolder.class };
		List existingContainers = getExistingContainers(fExistingElements);

		IProject[] allProjects = ResourcesPlugin.getWorkspace().getRoot()
				.getProjects();
		ArrayList rejectedElements = new ArrayList(allProjects.length);
		IProject currProject = fScriptProject.getProject();
		for (int i = 0; i < allProjects.length; i++) {
			if (!allProjects[i].equals(currProject)) {
				rejectedElements.add(allProjects[i]);
			}
		}
		ViewerFilter filter = new TypedViewerFilter(acceptedClasses,
				rejectedElements.toArray());

		ILabelProvider lp = new WorkbenchLabelProvider();
		ITreeContentProvider cp = new FakeFolderBaseWorkbenchContentProvider();

		String title = NewWizardMessages.SourceContainerWorkbookPage_ExistingSourceFolderDialog_new_title;
		String message = NewWizardMessages.SourceContainerWorkbookPage_ExistingSourceFolderDialog_edit_description;

		MultipleFolderSelectionDialog dialog = new MultipleFolderSelectionDialog(
				getShell(), lp, cp) {
			protected Control createDialogArea(Composite parent) {
				Control result = super.createDialogArea(parent);
				if (DLTKCore.DEBUG) {
					System.err
							.println("CreateMultipleSourceFoldersDialog: Add help support"); //$NON-NLS-1$ 
				}
				// PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				// IDLTKHelpContextIds.BP_CHOOSE_EXISTING_FOLDER_TO_MAKE_SOURCE_FOLDER);
				return result;
			}

			protected Object createFolder(final IContainer container) {
				final Object[] result = new Object[1];
				final BPListElement newElement = new BPListElement(
						fScriptProject, IBuildpathEntry.BPE_SOURCE, false);
				final AddSourceFolderWizard wizard = newSourceFolderWizard(
						newElement, fExistingElements, container);
				AbstractOpenWizardAction action = new AbstractOpenWizardAction() {
					protected INewWizard createWizard() throws CoreException {
						return wizard;
					}
				};
				action.addPropertyChangeListener(new IPropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent event) {
						if (event.getProperty().equals(IAction.RESULT)) {
							if (event.getNewValue().equals(Boolean.TRUE)) {
								result[0] = addFakeFolder(
										fScriptProject.getProject(), newElement);
							} else {
								wizard.cancel();
							}
						}
					}
				});
				action.run();
				return result[0];
			}
		};
		dialog.setExisting(existingContainers.toArray());
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.addFilter(filter);
		dialog.setInput(fScriptProject.getProject().getParent());
		dialog.setInitialFocus(fScriptProject.getProject());

		if (dialog.open() == Window.OK) {
			Object[] elements = dialog.getResult();
			for (int i = 0; i < elements.length; i++) {
				IResource res = (IResource) elements[i];
				fInsertedElements.add(new BPListElement(fScriptProject,
						IBuildpathEntry.BPE_SOURCE, res.getFullPath(), res,
						false));
			}

			if (fExistingElements.length == 1) {
			} else {
				ArrayList added = new ArrayList(fInsertedElements);
				HashSet updatedEclusionPatterns = new HashSet();
				addExlusionPatterns(added, updatedEclusionPatterns);
				fModifiedElements.addAll(updatedEclusionPatterns);
			}
			return Window.OK;
		} else {
			return Window.CANCEL;
		}
	}

	public List getInsertedElements() {
		return new ArrayList(fInsertedElements);
	}

	public List getRemovedElements() {
		return new ArrayList(fRemovedElements);
	}

	public List getModifiedElements() {
		return new ArrayList(fModifiedElements);
	}

	private void addExlusionPatterns(List newEntries, Set modifiedEntries) {
		BuildPathBasePage.fixNestingConflicts((BPListElement[]) newEntries
				.toArray(new BPListElement[newEntries.size()]),
				fExistingElements, modifiedEntries);
		if (!modifiedEntries.isEmpty()) {
			String title = NewWizardMessages.SourceContainerWorkbookPage_exclusion_added_title;
			String message = NewWizardMessages.SourceContainerWorkbookPage_exclusion_added_message;
			MessageDialog.openInformation(getShell(), title, message);
		}
	}

	private AddSourceFolderWizard newSourceFolderWizard(BPListElement element,
			BPListElement[] existing, IContainer parent) {
		AddSourceFolderWizard wizard = new AddSourceFolderWizard(existing,
				element, false, true, false, false, false, parent);
		wizard.setDoFlushChange(false);
		return wizard;
	}

	private List getExistingContainers(BPListElement[] existingElements) {
		List res = new ArrayList();
		for (int i = 0; i < existingElements.length; i++) {
			IResource resource = existingElements[i].getResource();
			if (resource instanceof IContainer) {
				res.add(resource);
			}
		}
		Set keys = fNonExistingFolders.keySet();
		for (Iterator iter = keys.iterator(); iter.hasNext();) {
			IFolder folder = (IFolder) iter.next();
			res.add(folder);
		}
		return res;
	}

	private IFolder addFakeFolder(final IContainer container,
			final BPListElement element) {
		IFolder result;
		IPath projectPath = fScriptProject.getPath();
		IPath path = element.getPath();
		if (projectPath.isPrefixOf(path)) {
			path = path.removeFirstSegments(projectPath.segmentCount());
		}
		result = container.getFolder(path);
		IFolder folder = result;
		do {
			IContainer parent = folder.getParent();
			fNonExistingFolders.put(folder, parent);
			if (parent instanceof IFolder) {
				folder = (IFolder) parent;
			} else {
				folder = null;
			}
		} while (folder != null && !folder.exists());
		return result;
	}
}
