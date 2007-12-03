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
package org.eclipse.php.internal.debug.ui.breakpoint.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPEditorAdapterFactory implements IAdapterFactory {
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ITextEditor editorPart = (ITextEditor) adaptableObject;
		IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
		if (resource == null && editorPart.getEditorInput() instanceof FileStoreEditorInput) {
			FileStoreEditorInput input = (FileStoreEditorInput) editorPart.getEditorInput();
			String filePath = new Path(input.getURI().getPath()).toOSString();
			resource = ExternalFilesRegistry.getInstance().getFileEntry(filePath);
			if (resource == null && filePath.length() > 0 && filePath.charAt(0) == '/') {
				resource = ExternalFilesRegistry.getInstance().getFileEntry(filePath.substring(1));
			}
		}
		if (resource == null) {
			return null;
		}
		if (resource.getType() != IResource.FILE) {
			return null;
		}
		if (!PHPModelUtil.isPhpFile((IFile) resource)) {
			return null;
		}
		if (adapterType.equals(IRunToLineTarget.class)) {
			return new PHPRunToLineAdapter();
		}
		return null;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class[] getAdapterList() {
		return new Class[] { IRunToLineTarget.class };
	}
}
