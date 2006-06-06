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
package org.eclipse.php.ui.workingset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.Assert;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.parser.ModelListener;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetUpdater;
import org.eclipse.ui.PlatformUI;


public class HistoryWorkingSetUpdater implements IWorkingSetUpdater {

	public static final String ID = "org.eclipse.php.ui.HistoryWorkingSet"; //$NON-NLS-1$

	private class Tracker extends EditorTracker {
		public void editorOpened(IEditorPart part) {
			IAdaptable file = getInput(part);
			if (file == null)
				return;
			fOpenFiles.add(file);
		}

		public void editorClosed(IEditorPart part) {
			IAdaptable file = getInput(part);
			if (file == null)
				return;
			fOpenFiles.remove(file);

		}

		private IAdaptable getInput(IEditorPart part) {
			IEditorInput input = part.getEditorInput();
			if (!(input instanceof IFileEditorInput))
				return null;

			return ((IFileEditorInput) input).getFile();
		}
	}

	private IWorkingSet fWorkingSet;
	private Set fOpenFiles;
	private Tracker fTracker;
	private ModelListener fPHPListener;
	private int fMaxElements = 15;

	private class ResourceChangeListener implements IResourceChangeListener {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			try {
				delta.accept(new IResourceDeltaVisitor() {
					public boolean visit(IResourceDelta delta) throws CoreException {
						IResource resource = delta.getResource();
						if (resource.getType() != IResource.FILE)
							return true;
						IFile file = (IFile) resource;
						if ((delta.getKind() & IResourceDelta.CHANGED) != 0) {
							if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {
								elementSaved(file);
							}
						} else if ((delta.getKind() & IResourceDelta.REMOVED) != 0) {
							if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
								IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getMovedToPath());
								if (!newFile.exists()) {
									elementRemoved(file);
								} else {
									elementMoved(file, newFile);
								}
							} else {
								elementRemoved(file);
							}
						}
						return false;
					}
				});
			} catch (CoreException e) {
			}

		}
	}

	private IResourceChangeListener fResourceChangeListener;

	public HistoryWorkingSetUpdater() {
		fOpenFiles = new HashSet();
		initListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(IWorkingSet workingSet) {
		Assert.isTrue(fWorkingSet == null);
		fWorkingSet = workingSet;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(IWorkingSet workingSet) {
		Assert.isTrue(fWorkingSet == workingSet);
		fWorkingSet = null;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(IWorkingSet workingSet) {
		return fWorkingSet == workingSet;
	}

	public void dispose() {
		if (fTracker != null) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			workbench.removeWindowListener(fTracker);
			IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
			for (int i = 0; i < windows.length; i++) {
				windows[i].removePageListener(fTracker);
				IWorkbenchPage[] pages = windows[i].getPages();
				for (int j = 0; j < pages.length; j++) {
					pages[j].removePartListener(fTracker);
				}
			}
			fTracker = null;
		}
		if (fPHPListener != null) {
			PHPWorkspaceModelManager.getInstance().removeModelListener(fPHPListener);
			fPHPListener = null;
		}
		if (fResourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fResourceChangeListener);
			fResourceChangeListener = null;
		}
	}

	private void initListeners() {
		fTracker = new Tracker();
		IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.addWindowListener(fTracker);
		IWorkbenchWindow[] windows = workbench.getWorkbenchWindows();
		for (int i = 0; i < windows.length; i++) {
			windows[i].addPageListener(fTracker);
			IWorkbenchPage[] pages = windows[i].getPages();
			for (int j = 0; j < pages.length; j++) {
				pages[j].addPartListener(fTracker);
			}
		}
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fResourceChangeListener, IResourceChangeEvent.POST_CHANGE);

		fPHPListener = new ModelListener() {
			public void fileDataChanged(PHPFileData fileData) {
				IFile file = (IFile) PHPModelUtil.getResource(fileData);
				elementSaved(file);

			}

			public void fileDataAdded(PHPFileData fileData) {
				// TODO Auto-generated method stub

			}

			public void fileDataRemoved(PHPFileData fileData) {
				IFile file = (IFile) PHPModelUtil.getResource(fileData);
				elementRemoved(file);

			}

			public void dataCleared() {

			}
		};
		PHPWorkspaceModelManager.getInstance().addModelListener(fPHPListener);
	}

	private void elementSaved(IAdaptable element) {
		if (!fOpenFiles.contains(element))
			return;
		updateHistory(element);
	}

	private void elementRemoved(IAdaptable element) {
		fOpenFiles.remove(element);
		List elements = getElements();
		if (elements.remove(element)) {
			setElements(elements);
		}
	}

	private void elementMoved(IAdaptable oldElement, IAdaptable newElement) {
		List elements = getElements();
		int index = elements.indexOf(oldElement);
		if (index == -1)
			return;
		elements.set(index, newElement);
		fOpenFiles.remove(oldElement);
		fOpenFiles.add(newElement);
		setElements(elements);
	}

	private void projectClosed(IProject project) {
		List elements = getElements();
		int removed = 0;
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			IAdaptable element = (IAdaptable) iter.next();
			IProject container = getProject(element);
			if (project.equals(container)) {
				iter.remove();
				removed++;
			}
		}
		if (removed > 0) {
			setElements(elements);
		}
	}

	private IProject getProject(IAdaptable element) {
		if (element instanceof IResource) {
			return ((IResource) element).getProject();
		} else if (element instanceof PHPCodeData) {
			return PHPModelUtil.getResource((PHPCodeData) element).getProject();
		}
		return null;
	}

	private void updateHistory(IAdaptable element) {
		List elements = getElements();
		int index = elements.indexOf(element);
		if (index != -1) {
			elements.remove(index);
			elements.add(0, element);
		} else {
			if (elements.size() == fMaxElements) {
				elements.remove(elements.size() - 1);
			}
			elements.add(0, element);
		}
		setElements(elements);
	}

	private List getElements() {
		return new ArrayList(Arrays.asList(fWorkingSet.getElements()));
	}

	private void setElements(List elements) {
		fWorkingSet.setElements((IAdaptable[]) elements.toArray(new IAdaptable[elements.size()]));
	}
}
