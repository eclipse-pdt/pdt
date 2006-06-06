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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.IWorkingSetUpdater;
import org.eclipse.ui.PlatformUI;

public class OthersWorkingSetUpdater implements IWorkingSetUpdater {

	public static final String ID = "org.eclipse.php.ui.OthersWorkingSet"; //$NON-NLS-1$

	private IWorkingSet fWorkingSet;
	private WorkingSetModel fWorkingSetModel;

	private class ResourceChangeListener implements IResourceChangeListener {
		public void resourceChanged(IResourceChangeEvent event) {
			IResourceDelta delta = event.getDelta();
			IResourceDelta[] affectedChildren = delta.getAffectedChildren(IResourceDelta.ADDED | IResourceDelta.REMOVED, IResource.PROJECT);
			if (affectedChildren.length > 0) {
				updateElements(fWorkingSetModel.getActiveWorkingSets());
			} else {
				affectedChildren = delta.getAffectedChildren(IResourceDelta.CHANGED, IResource.PROJECT);
				for (int i = 0; i < affectedChildren.length; i++) {
					IResourceDelta projectDelta = affectedChildren[i];
					if ((projectDelta.getFlags() & IResourceDelta.DESCRIPTION) != 0) {
						updateElements(fWorkingSetModel.getActiveWorkingSets());
						// one is enough
						return;
					}
				}
			}
		}
	}

	private IResourceChangeListener fResourceChangeListener;

	private class WorkingSetListener implements IPropertyChangeListener {
		public void propertyChange(PropertyChangeEvent event) {
			if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(event.getProperty())) {
				IWorkingSet changedWorkingSet = (IWorkingSet) event.getNewValue();
				if (changedWorkingSet != fWorkingSet) {
					IWorkingSet[] activeWorkingSets = fWorkingSetModel.getActiveWorkingSets();
					if (contains(activeWorkingSets, changedWorkingSet) && !HistoryWorkingSetUpdater.ID.equals(changedWorkingSet.getId()))
						updateElements(activeWorkingSets);
				}
			}
		}

		private boolean contains(IWorkingSet[] workingSets, IWorkingSet workingSet) {
			for (int i = 0; i < workingSets.length; i++) {
				if (workingSets[i] == workingSet)
					return true;
			}
			return false;
		}
	}

	private IPropertyChangeListener fWorkingSetListener;

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

	public void init(WorkingSetModel model) {
		fWorkingSetModel = model;
		fResourceChangeListener = new ResourceChangeListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(fResourceChangeListener, IResourceChangeEvent.POST_CHANGE);
		fWorkingSetListener = new WorkingSetListener();
		PlatformUI.getWorkbench().getWorkingSetManager().addPropertyChangeListener(fWorkingSetListener);
		updateElements(fWorkingSetModel.getActiveWorkingSets());
	}

	public void dispose() {
		if (fResourceChangeListener != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(fResourceChangeListener);
			fResourceChangeListener = null;
		}
		if (fWorkingSetListener != null) {
			PlatformUI.getWorkbench().getWorkingSetManager().removePropertyChangeListener(fWorkingSetListener);
			fWorkingSetListener = null;
		}
	}

	public void updateElements() {
		updateElements(fWorkingSetModel.getActiveWorkingSets());
	}

	private void updateElements(IWorkingSet[] activeWorkingSets) {
		List result = new ArrayList();
		Set projects = new HashSet();
		for (int i = 0; i < activeWorkingSets.length; i++) {
			if (activeWorkingSets[i] == fWorkingSet)
				continue;
			IAdaptable[] elements = activeWorkingSets[i].getElements();
			for (int j = 0; j < elements.length; j++) {
				IAdaptable element = elements[j];
				IResource resource = (IResource) element.getAdapter(IResource.class);
				if (resource != null && resource.getType() == IResource.PROJECT) {
					projects.add(resource);
				}
			}
		}
		IProject[] phpProjects = PHPWorkspaceModelManager.getInstance().listProjects();
		for (int i = 0; i < phpProjects.length; i++) {
			if (!projects.contains(phpProjects[i]))
				result.add(phpProjects[i]);
		}
		//		Object[] rProjects= model.getNonPHPResources();
		//		for (int i= 0; i < rProjects.length; i++) {
		//				if (!projects.contains(rProjects[i]))
		//					result.add(rProjects[i]);
		//		}
		fWorkingSet.setElements((IAdaptable[]) result.toArray(new IAdaptable[result.size()]));
	}
}
