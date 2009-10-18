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
/**
 * 
 */
package org.eclipse.php.internal.debug.ui.model;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

/**
 * An ExtendedWorkbenchContentProvider provides the base content, plus any
 * external (non-workspace) PHP files that are opened in the editor. This class
 * can be used when presenting dialogs for selecting PHP files.
 * 
 * @author shalom
 */
public class ExtendedWorkbenchContentProvider extends
		BaseWorkbenchContentProvider {

	private boolean isProvidingExternals;

	/**
	 * Constructs a new ExtendedWorkbenchContentProvider. By default, the
	 * provider provides the external files when the
	 * {@link #getChildren(Object)} method is called.
	 */
	public ExtendedWorkbenchContentProvider() {
		super();
		isProvidingExternals = true;
	}

	/**
	 * Constructs a new ExtendedWorkbenchContentProvider.
	 * 
	 * @param provideExternalFiles
	 *            Set the content provider to provide external files when the
	 *            {@link #getChildren(Object)} is called.
	 */
	public ExtendedWorkbenchContentProvider(boolean provideExternalFiles) {
		super();
		isProvidingExternals = provideExternalFiles;
	}

	/**
	 * Set the content provider to provide external files when the
	 * {@link #getChildren(Object)} is called.
	 * 
	 * @param provide
	 */
	public void setProvideExternalFiles(boolean shouldProvide) {
		this.isProvidingExternals = shouldProvide;
	}

	/**
	 * Returns if this provider provides external files when the
	 * {@link #getChildren(Object)} method is called.
	 * 
	 * @return
	 */
	public boolean isProvidingExternalFiles() {
		return isProvidingExternals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang
	 * .Object)
	 */
	public Object[] getChildren(Object element) {
		Object[] children = super.getChildren(element);
		if (isProvidingExternals && element instanceof IWorkspaceRoot) {
			// TODO: combine children with external files
		}
		return children;
	}
}
