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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.php.internal.core.phpModel.ExternalPhpFilesRegistry;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;

/**
 * An ExtendedWorkbenchContentProvider provides the base content, plus any external (non-workspace) PHP files
 * that are opened in the editor.
 * This class can be used when presenting dialogs for selecting PHP files. 
 * 
 * @author shalom
 */
public class ExtendedWorkbenchContentProvider extends BaseWorkbenchContentProvider {

	/**
	 * Constructs a new ExtendedWorkbenchContentProvider.
	 */
	public ExtendedWorkbenchContentProvider() {
		super();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.model.BaseWorkbenchContentProvider#getChildren(java.lang.Object)
	 */
	public Object[] getChildren(Object element) {
		Object[] children = super.getChildren(element);
		if (element instanceof IWorkspaceRoot) {
			// Add the external files as IFiles
			IFile[] externalFiles = ExternalPhpFilesRegistry.getInstance().getAllAsIFiles();
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
}
