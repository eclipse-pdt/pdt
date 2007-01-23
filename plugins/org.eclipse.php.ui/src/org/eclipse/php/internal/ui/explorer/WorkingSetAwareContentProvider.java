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
package org.eclipse.php.internal.ui.explorer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.util.TreePath;
import org.eclipse.php.internal.ui.workingset.HistoryWorkingSetUpdater;
import org.eclipse.php.internal.ui.workingset.WorkingSetModel;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;


public class WorkingSetAwareContentProvider extends ExplorerContentProvider implements IMultiElementTreeContentProvider {

	private WorkingSetModel fWorkingSetModel;
	private IPropertyChangeListener fListener;

	public WorkingSetAwareContentProvider(ExplorerPart part, boolean provideMembers, WorkingSetModel model) {
		super(part, provideMembers);
		fWorkingSetModel = model;
		fListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				workingSetModelChanged(event);
			}
		};
		fWorkingSetModel.addPropertyChangeListener(fListener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		fWorkingSetModel.removePropertyChangeListener(fListener);
		super.dispose();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean hasChildrenInternal(Object element) {
		if (element instanceof IWorkingSet)
			return true;
		return super.hasChildrenInternal(element);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] getChildrenInternal(Object element) {
		Object[] children;
		if (element instanceof WorkingSetModel) {
			Assert.isTrue(fWorkingSetModel == element);
			return fWorkingSetModel.getActiveWorkingSets();
		} else if (element instanceof IWorkingSet) {
			children = filterClosedElements(fWorkingSetModel.getChildren((IWorkingSet) element));
		} else {
			children = super.getChildrenInternal(element);
		}
		return children;
	}

	private Object[] filterClosedElements(Object[] children) {
		List result = new ArrayList(children.length);
		for (int i = 0; i < children.length; i++) {
			Object element = children[i];
			boolean add = false;
			if (element instanceof IProject) {
				add = true;
			} else if (element instanceof IResource) {
				IProject project = ((IResource) element).getProject();
				add = project == null || project.isOpen();
			} else if (element instanceof PHPProjectModel) {
				add = true;
			} else if (element instanceof PHPCodeData) {
				IProject project = getProject((PHPCodeData) element);
				add = project == null || project.isOpen();
			}
			if (add) {
				result.add(element);
			}
		}
		return result.toArray();
	}

	private IProject getProject(PHPCodeData element) {
		if (element == null)
			return null;
		IResource resource = PHPModelUtil.getResource(element);
		if (resource == null)
			return null;
		return resource.getProject();
	}

	/**
	 * {@inheritDoc}
	 */
	public TreePath[] getTreePaths(Object element) {
		if (element instanceof IWorkingSet) {
			TreePath path = new TreePath(new Object[] { element });
			return new TreePath[] { path };
		}
		List modelParents = getModelPath(element);
		List result = new ArrayList();
		for (int i = 0; i < modelParents.size(); i++) {
			result.addAll(getTreePaths(modelParents, i));
		}
		return (TreePath[]) result.toArray(new TreePath[result.size()]);
	}

	private List getModelPath(Object element) {
		List result = new ArrayList();
		result.add(element);
		Object parent = super.getParent(element);
		Object input = getViewerInput();
		// stop at input or on JavaModel. We never visualize it anyway.
		while (parent != null && !parent.equals(input) && !(parent instanceof PHPWorkspaceModelManager)) {
			result.add(parent);
			parent = super.getParent(parent);
		}
		Collections.reverse(result);
		return result;
	}

	private List/*<TreePath>*/getTreePaths(List modelParents, int index) {
		List result = new ArrayList();
		Object input = getViewerInput();
		Object element = modelParents.get(index);
		Object[] parents = fWorkingSetModel.getAllParents(element);
		for (int i = 0; i < parents.length; i++) {
			List chain = new ArrayList();
			if (!parents[i].equals(input))
				chain.add(parents[i]);
			for (int m = index; m < modelParents.size(); m++) {
				chain.add(modelParents.get(m));
			}
			result.add(new TreePath(chain.toArray()));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object internalGetParent(Object child) {
		Object[] parents = fWorkingSetModel.getAllParents(child);
		if (parents.length == 0)
			return super.internalGetParent(child);
		Object first = parents[0];
		if (first instanceof IWorkingSet && HistoryWorkingSetUpdater.ID.equals(((IWorkingSet) first).getId())) {
			if (parents.length > 1) {
				return parents[1];
			} else {
				return super.internalGetParent(child);
			}
		}
		return first;
	}

	protected void augmentElementToRefresh(List toRefresh, int relation, Object affectedElement) {
		// we are refreshing the JavaModel and are in working set mode.
		if (PHPWorkspaceModelManager.getInstance().equals(affectedElement)) {
			toRefresh.remove(affectedElement);
			toRefresh.add(fWorkingSetModel);
		} else if (relation == GRANT_PARENT) {
			Object parent = internalGetParent(affectedElement);
			if (parent != null) {
				toRefresh.addAll(Arrays.asList(fWorkingSetModel.getAllParents(parent)));
			}
		}
	}

	private void workingSetModelChanged(PropertyChangeEvent event) {
		String property = event.getProperty();
		Object newValue = event.getNewValue();
		List toRefresh = new ArrayList(1);
		if (WorkingSetModel.CHANGE_WORKING_SET_MODEL_CONTENT.equals(property)) {
			toRefresh.add(fWorkingSetModel);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE.equals(property)) {
			toRefresh.add(newValue);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE.equals(property)) {
			toRefresh.add(newValue);
		}
		postRefresh(toRefresh, true);
	}
}
