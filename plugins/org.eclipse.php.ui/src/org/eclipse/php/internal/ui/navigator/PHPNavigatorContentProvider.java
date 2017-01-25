/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dawid Pakuła - PDT port
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.php.internal.ui.explorer.PHPExplorerContentProvider;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.IPipelinedTreeContentProvider;
import org.eclipse.ui.navigator.PipelinedShapeModification;
import org.eclipse.ui.navigator.PipelinedViewerUpdate;

public class PHPNavigatorContentProvider extends PHPExplorerContentProvider implements IPipelinedTreeContentProvider {

	public static final String PDT_EXTENSION_ID = "org.eclipse.php.ui.phpContent"; //$NON-NLS-1$

	public PHPNavigatorContentProvider() {
		super(true);
	}

	@Override
	public void init(ICommonContentExtensionSite aConfig) {
		IMemento memento = aConfig.getMemento();

		restoreState(memento);
		setIsFlatLayout(false);
		boolean showCUChildren = DLTKUIPlugin.getDefault().getPreferenceStore()
				.getBoolean(PreferenceConstants.SHOW_SOURCE_MODULE_CHILDREN);
		setProvideMembers(showCUChildren);
	}

	@Override
	public Object getParent(Object element) {
		Object parent = super.getParent(element);
		if (parent instanceof IScriptModel) {
			return ((IScriptModel) parent).getWorkspace().getRoot();
		} else if (parent instanceof IScriptFolder) {
			return ((IScriptFolder) parent).getResource();
		} else if (parent instanceof IScriptProject) {
			return ((IScriptProject) parent).getProject();
		} else if (parent instanceof ISourceModule) {
			return ((ISourceModule) parent).getResource();
		}
		return parent;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return super.getChildren(parentElement);
	}

	@Override
	public Object[] getElements(Object parent) {
		return getChildren(parent);
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof IProject) {
			return ((IProject) element).isAccessible();
		} else if (element instanceof IFile) {
			IModelElement create = DLTKCore.create((IFile) element);
			if (create != null) {
				return super.hasChildren(create);
			}
		}
		return super.hasChildren(element);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getPipelinedChildren(Object parent, Set currentChildren) {
		if (parent instanceof IProject) {
			IProject project = (IProject) parent;
			IScriptProject scriptProject = DLTKCore.create(project);
			currentChildren.addAll(getScriptProjectContent(scriptProject));
		} else if (parent instanceof IFile) {
			IModelElement create = DLTKCore.create((IFile) parent);
			if (create != null) {
				currentChildren.addAll(Arrays.asList(super.getChildren(create)));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getPipelinedElements(Object input, Set currentElements) {
		getPipelinedChildren(input, currentElements);
	}

	@Override
	public Object getPipelinedParent(Object anObject, Object aSuggestedParent) {
		return aSuggestedParent;
	}

	@Override
	public PipelinedShapeModification interceptAdd(PipelinedShapeModification addModification) {
		return addModification;
	}

	@Override
	public PipelinedShapeModification interceptRemove(PipelinedShapeModification removeModification) {
		return removeModification;
	}

	@Override
	public boolean interceptRefresh(PipelinedViewerUpdate refreshSynchronization) {
		return false;

	}

	@Override
	public boolean interceptUpdate(PipelinedViewerUpdate updateSynchronization) {
		return false;
	}

	@Override
	protected void postRefresh(List<?> toRefreshOld, boolean updateLabels, Collection<Runnable> runnables) {
		List<Object> toRefresh = new ArrayList<>(toRefreshOld);
		int size = toRefresh.size();
		for (int i = 0; i < size; i++) {
			Object element = toRefresh.get(i);
			if (element instanceof IScriptProject) {
				toRefresh.set(i, ((IScriptProject) element).getProject());
			}
		}
		for (Iterator<Object> iter = toRefresh.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (element instanceof IModelElement) {
				iter.remove();
				toRefresh.add(((IModelElement) element).getModel().getWorkspace().getRoot());
				super.postRefresh(toRefresh, updateLabels, runnables);
				return;
			}
		}
		super.postRefresh(toRefreshOld, updateLabels, runnables);
	}

	@Override
	public void restoreState(IMemento aMemento) {

	}

	@Override
	public void saveState(IMemento aMemento) {

	}

}
