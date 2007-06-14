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
/**
 * 
 */
package org.eclipse.php.internal.debug.ui.model;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

/**
 * An ExtendedWorkbenchContentProvider provides the base content, plus any external (non-workspace) PHP files
 * that are opened in the editor.
 * This class can be used when presenting dialogs for selecting PHP files. 
 * 
 * @author shalom
 */
public class ExtendedWorkbenchContentProvider extends BaseWorkbenchContentProvider {

	private boolean isProvidingExternals;

	/**
	 * Constructs a new ExtendedWorkbenchContentProvider.
	 * By default, the provider provides the external files when the {@link #getChildren(Object)} method is called.
	 */
	public ExtendedWorkbenchContentProvider() {
		super();
		isProvidingExternals = true;
	}

	/**
	 * Constructs a new ExtendedWorkbenchContentProvider.
	 * 
	 * @param provideExternalFiles Set the content provider to provide external files when the {@link #getChildren(Object)} is called.
	 */
	public ExtendedWorkbenchContentProvider(boolean provideExternalFiles) {
		super();
		isProvidingExternals = provideExternalFiles;
	}

	/**
	 * Set the content provider to provide external files when the {@link #getChildren(Object)} is called.
	 * 
	 * @param provide
	 */
	public void setProvideExternalFiles(boolean shouldProvide) {
		this.isProvidingExternals = shouldProvide;
	}

	/**
	 * Returns if this provider provides external files when the {@link #getChildren(Object)} method is called.
	 * @return
	 */
	public boolean isProvidingExternalFiles() {
		return isProvidingExternals;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element) {
		Object[] children = super.getChildren(element);
		if (isProvidingExternals && element instanceof IWorkspaceRoot) {
			// Add the external files as IFiles
			IFile[] externalFiles = ExternalFilesRegistry.getInstance().getAllAsIFiles();
			externalFiles = filterNonExistingFiles(externalFiles);
			if (externalFiles.length > 0) {
				if (children.length == 0) {
					return externalFiles;
				}
				Object[] combinedChildren = new Object[children.length + externalFiles.length];
				System.arraycopy(children, 0, combinedChildren, 0, children.length);
				System.arraycopy(externalFiles, 0, combinedChildren, children.length, externalFiles.length);
				return combinedChildren;
			}
		}
		return children;
	}

	/*
	 * Filter out any non-existing files.
	 */
	private IFile[] filterNonExistingFiles(IFile[] files) {
		ArrayList existingFiles = new ArrayList(files.length);
		for (int i = 0; i < files.length; i++) {
			if (files[i].getFullPath().toFile().exists()) {
				existingFiles.add(files[i]);
			}
		}
		IFile[] existing = new IFile[existingFiles.size()];
		existingFiles.toArray(existing);
		return existing;
	}
}
