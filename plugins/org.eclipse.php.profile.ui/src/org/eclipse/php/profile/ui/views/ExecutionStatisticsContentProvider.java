/*******************************************************************************
 * Copyright (c) 2017 Rogue Wave Software Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Rogue Wave Software Inc. - initial implementation
 *******************************************************************************/
package org.eclipse.php.profile.ui.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.php.profile.core.data.ProfilerFunctionData;
import org.eclipse.php.profile.ui.ProfilerUIConstants;
import org.eclipse.php.profile.ui.ProfilerUiPlugin;
import org.eclipse.php.profile.ui.preferences.PreferenceKeys;

/**
 * Execution statistics content provider.
 */
public class ExecutionStatisticsContentProvider implements ITreeContentProvider {

	private IPreferenceStore fStore;

	public ExecutionStatisticsContentProvider() {
		fStore = ProfilerUiPlugin.getDefault().getPreferenceStore();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		TreeElement parent = (TreeElement) parentElement;
		if (parent.getParent() == null) {
			if (fStore.getInt(PreferenceKeys.EXECUTION_VIEW_GROUP_BY) != ProfilerUIConstants.GROUP_BY_FILE) {
				List<Object> children = new ArrayList<>();
				Object[] files = parent.getChildren();
				for (int i = 0; i < files.length; ++i) {
					Object[] classes = ((TreeElement) files[i]).getChildren();
					for (int j = 0; j < classes.length; ++j) {
						if (fStore
								.getInt(PreferenceKeys.EXECUTION_VIEW_GROUP_BY) == ProfilerUIConstants.GROUP_BY_CLASS) {
							children.add(classes[j]);
						} else if (fStore.getInt(
								PreferenceKeys.EXECUTION_VIEW_GROUP_BY) == ProfilerUIConstants.GROUP_BY_FUNCTION) {
							if (((TreeElement) classes[j]).getData() instanceof ProfilerFunctionData) {
								children.add(classes[j]);
							} else {
								Object[] functions = ((TreeElement) classes[j]).getChildren();
								for (int k = 0; k < functions.length; ++k) {
									children.add(functions[k]);
								}
							}
						}
					}
				}
				return children.toArray();
			}
		}
		return ((TreeElement) parentElement).getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object )
	 */
	@Override
	public Object getParent(Object element) {
		return ((TreeElement) element).getParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		return ((TreeElement) element).hasChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}
}
