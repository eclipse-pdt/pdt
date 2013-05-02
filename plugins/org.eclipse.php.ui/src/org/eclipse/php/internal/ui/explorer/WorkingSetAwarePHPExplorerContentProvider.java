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
package org.eclipse.php.internal.ui.explorer;

import java.util.*;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IScriptModel;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.internal.ui.scriptview.IMultiElementTreeContentProvider;
import org.eclipse.dltk.internal.ui.workingsets.WorkingSetModel;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;

/**
 * Displays content of the PHP Explorer when the root element is Working Sets
 * instead of Projects. Based on
 * org.eclipse.dltk.internal.ui.scriptview.WorkingSetAwareContentProvider
 * 
 * @author apeled, nirc
 * 
 */
public class WorkingSetAwarePHPExplorerContentProvider extends
		PHPExplorerContentProvider implements IMultiElementTreeContentProvider {

	private WorkingSetModel fWorkingSetModel;
	private IPropertyChangeListener fListener;

	public WorkingSetAwarePHPExplorerContentProvider(boolean provideMembers,
			WorkingSetModel model) {
		super(provideMembers);
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
	public boolean hasChildren(Object element) {
		if (element instanceof IWorkingSet)
			return true;
		return super.hasChildren(element);
	}

	/**
	 * {@inheritDoc}
	 */
	public Object[] getChildren(Object element) {
		Object[] children;
		if (element instanceof WorkingSetModel) {
			Assert.isTrue(fWorkingSetModel == element);
			return fWorkingSetModel.getActiveWorkingSets();
		} else if (element instanceof IWorkingSet) {
			children = getWorkingSetChildren((IWorkingSet) element);
		} else {
			children = super.getChildren(element);
		}
		return children;
	}

	private Object[] getWorkingSetChildren(IWorkingSet set) {
		IAdaptable[] elements = fWorkingSetModel.getChildren(set);
		boolean isKnownWorkingSet = isKnownWorkingSet(set);
		List result = new ArrayList(elements.length);
		for (int i = 0; i < elements.length; i++) {
			IAdaptable element = elements[i];
			boolean add = false;
			if (element instanceof IProject) {
				add = true;
			} else if (element instanceof IResource) {
				IProject project = ((IResource) element).getProject();
				add = project == null || project.isOpen();
			} else if (element instanceof IScriptProject) {
				add = true;
			} else if (element instanceof IModelElement) {
				IProject project = getProject((IModelElement) element);
				add = project == null || project.isOpen();
			}
			if (add) {
				if (isKnownWorkingSet) {
					result.add(element);
				} else {
					IProject project = (IProject) element
							.getAdapter(IProject.class);
					if (project != null && project.exists()) {
						IScriptProject jp = DLTKCore.create(project);
						if (jp != null && jp.exists()) {
							result.add(jp);
						} else {
							result.add(project);
						}
					}
				}
			}
		}
		return result.toArray();
	}

	private boolean isKnownWorkingSet(IWorkingSet set) {
		String id = set.getId();
		return "org.eclipse.dltk.ui.ScriptWorkingSetPage".equals(id) //$NON-NLS-1$
				|| "org.eclipse.dltk.internal.ui.OthersWorkingSet".equals(id); //$NON-NLS-1$
	}

	private IProject getProject(IModelElement element) {
		if (element == null)
			return null;
		IScriptProject project = element.getScriptProject();
		if (project == null)
			return null;
		return project.getProject();
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
		// stop at input or on ScriptModel. We never visualize it anyway.
		while (parent != null && !parent.equals(input)
				&& !(parent instanceof IScriptModel)) {
			result.add(parent);
			parent = super.getParent(parent);
		}
		Collections.reverse(result);
		return result;
	}

	private List/* <TreePath> */getTreePaths(List modelParents, int index) {
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
	public Object getParent(Object child) {
		Object[] parents = fWorkingSetModel.getAllParents(child);
		if (parents.length == 0)
			return super.getParent(child);
		Object first = parents[0];
		return first;
	}

	protected void augmentElementToRefresh(List toRefresh, int relation,
			Object affectedElement) {
		// we are refreshing the ScriptModel and are in working set mode.
		if (DLTKCore.create(ResourcesPlugin.getWorkspace().getRoot()).equals(
				affectedElement)) {
			toRefresh.remove(affectedElement);
			toRefresh.add(fWorkingSetModel);
		} else if (relation == GRANT_PARENT) {
			Object parent = internalGetParent(affectedElement);
			if (parent != null) {
				toRefresh.addAll(Arrays.asList(fWorkingSetModel
						.getAllParents(parent)));
			}
		}
		List nonProjetTopLevelElemens = fWorkingSetModel
				.getNonProjectTopLevelElements();
		if (nonProjetTopLevelElemens.isEmpty())
			return;
		List toAdd = new ArrayList();
		for (Iterator iter = nonProjetTopLevelElemens.iterator(); iter
				.hasNext();) {
			Object element = iter.next();
			if (isChildOf(element, toRefresh))
				toAdd.add(element);
		}
		toRefresh.addAll(toAdd);
	}

	private void workingSetModelChanged(PropertyChangeEvent event) {
		String property = event.getProperty();
		Object newValue = event.getNewValue();
		List toRefresh = new ArrayList(1);
		if (WorkingSetModel.CHANGE_WORKING_SET_MODEL_CONTENT.equals(property)) {
			toRefresh.add(fWorkingSetModel);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_CONTENT_CHANGE
				.equals(property)) {
			toRefresh.add(newValue);
		} else if (IWorkingSetManager.CHANGE_WORKING_SET_NAME_CHANGE
				.equals(property)) {
			toRefresh.add(newValue);
		}
		ArrayList runnables = new ArrayList();
		postRefresh(toRefresh, true, runnables);
		executeRunnables(runnables);
	}

	private boolean isChildOf(Object element, List potentialParents) {
		// Calling super get parent to bypass working set mapping
		Object parent = super.getParent(element);
		if (parent == null)
			return false;
		for (Iterator iter = potentialParents.iterator(); iter.hasNext();) {
			Object potentialParent = iter.next();
			while (parent != null) {
				if (parent.equals(potentialParent))
					return true;
				parent = super.getParent(parent);
			}

		}
		return false;
	}

}
