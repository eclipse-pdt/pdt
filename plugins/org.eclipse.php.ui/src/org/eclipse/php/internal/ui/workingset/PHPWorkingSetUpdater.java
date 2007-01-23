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
package org.eclipse.php.internal.ui.workingset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.ModelListener;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetUpdater;


public class PHPWorkingSetUpdater implements IWorkingSetUpdater, ModelListener {

	public static final String ID = "org.eclipse.php.ui.workingset.PHPWorkingSetPage"; //$NON-NLS-1$

	private List fWorkingSets;

	static final int ADD = 1;
	static final int CHANGE = 2;
	static final int REMOVE = 3;

	private static class WorkingSetDelta {
		private IWorkingSet fWorkingSet;
		private List fElements;
		private boolean fChanged;

		public WorkingSetDelta(IWorkingSet workingSet) {
			fWorkingSet = workingSet;
			fElements = new ArrayList(Arrays.asList(workingSet.getElements()));
		}

		public int indexOf(Object element) {
			return fElements.indexOf(element);
		}

		public void set(int index, Object element) {
			fElements.set(index, element);
			fChanged = true;
		}

		public void remove(int index) {
			if (fElements.remove(index) != null) {
				fChanged = true;
			}
		}

		public void process() {
			if (fChanged) {
				fWorkingSet.setElements((IAdaptable[]) fElements.toArray(new IAdaptable[fElements.size()]));
			}
		}
	}

	public PHPWorkingSetUpdater() {
		fWorkingSets = new ArrayList();
		PHPWorkspaceModelManager.getInstance().addModelListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(IWorkingSet workingSet) {
		checkElementExistence(workingSet);
		synchronized (fWorkingSets) {
			fWorkingSets.add(workingSet);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean remove(IWorkingSet workingSet) {
		boolean result;
		synchronized (fWorkingSets) {
			result = fWorkingSets.remove(workingSet);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean contains(IWorkingSet workingSet) {
		synchronized (fWorkingSets) {
			return fWorkingSets.contains(workingSet);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		synchronized (fWorkingSets) {
			fWorkingSets.clear();
		}
		PHPWorkspaceModelManager.getInstance().removeModelListener(this);
	}

	/**
	 * {@inheritDoc}
	 */
	public void elementChanged(PHPFileData fileData, int type) {
		IWorkingSet[] workingSets;
		synchronized (fWorkingSets) {
			workingSets = (IWorkingSet[]) fWorkingSets.toArray(new IWorkingSet[fWorkingSets.size()]);
		}
		for (int w = 0; w < workingSets.length; w++) {
			WorkingSetDelta workingSetDelta = new WorkingSetDelta(workingSets[w]);
			processPHPChange(workingSetDelta, fileData, type);
			workingSetDelta.process();
		}
	}

	private void processPHPChange(WorkingSetDelta result, PHPFileData fileData, int type) {
		int index = result.indexOf(fileData);

		if (index != -1) {
			if (type == REMOVE) {
				result.remove(index);
			}
		}
	}

	private void processResourceDelta(WorkingSetDelta result, IResourceDelta delta) {
		IResource resource = delta.getResource();
		int type = resource.getType();
		int index = result.indexOf(resource);
		int kind = delta.getKind();
		int flags = delta.getFlags();
		if (kind == IResourceDelta.CHANGED && type == IResource.PROJECT && index != -1) {
			if ((flags & IResourceDelta.OPEN) != 0) {
				result.set(index, resource);
			}
		}
		if (index != -1 && kind == IResourceDelta.REMOVED) {
			if ((flags & IResourceDelta.MOVED_TO) != 0) {
				result.set(index, ResourcesPlugin.getWorkspace().getRoot().findMember(delta.getMovedToPath()));
			} else {
				result.remove(index);
			}
		}
		IResourceDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			processResourceDelta(result, children[i]);
		}
	}

	private void checkElementExistence(IWorkingSet workingSet) {
		List elements = new ArrayList(Arrays.asList(workingSet.getElements()));
		boolean changed = false;
		for (Iterator iter = elements.iterator(); iter.hasNext();) {
			IAdaptable element = (IAdaptable) iter.next();
			boolean remove = false;
			if (element instanceof PHPCodeData) {
				PHPCodeData phpElement = (PHPCodeData) element;
				IResource resource = PHPModelUtil.getResource(phpElement);
				IProject project = resource.getProject();
				remove = (project != null ? project.isOpen() : true) && !resource.exists();
			} else if (element instanceof IResource) {
				IResource resource = (IResource) element;
				IProject project = resource.getProject();
				remove = (project != null ? project.isOpen() : true) && !resource.exists();
			}
			if (remove) {
				iter.remove();
				changed = true;
			}
		}
		if (changed) {
			workingSet.setElements((IAdaptable[]) elements.toArray(new IAdaptable[elements.size()]));
		}
	}

	public void fileDataChanged(PHPFileData fileData) {
		elementChanged(fileData, CHANGE);

	}

	public void fileDataAdded(PHPFileData fileData) {
		elementChanged(fileData, ADD);

	}

	public void fileDataRemoved(PHPFileData fileData) {
		elementChanged(fileData, REMOVE);

	}

	public void dataCleared() {
		// TODO Auto-generated method stub

	}
}
