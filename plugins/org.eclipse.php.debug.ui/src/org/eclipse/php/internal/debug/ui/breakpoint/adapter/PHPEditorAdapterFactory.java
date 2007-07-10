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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.php.internal.core.documentModel.provisional.contenttype.ContentTypeIdForPHP;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.debug.ui.Logger;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPEditorAdapterFactory implements IAdapterFactory {
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		try {
			ITextEditor editorPart = (ITextEditor) adaptableObject;
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource == null && editorPart.getEditorInput() instanceof FileStoreEditorInput) {
				FileStoreEditorInput input = (FileStoreEditorInput)editorPart.getEditorInput();
				String filePath = input.getURI().getPath();
				resource = ExternalFilesRegistry.getInstance().getFileEntry(filePath);
				if (resource == null && filePath.length() > 0 && filePath.charAt(0) == '/') {
					resource = ExternalFilesRegistry.getInstance().getFileEntry(filePath.substring(1));
				}
			}
			if (resource != null) {
				if (resource.getType() == IResource.FILE) {
					IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
					IContentType fileContentType = ((IFile) resource).getContentDescription().getContentType();
					IContentType PHPContentType = contentTypeManager.getContentType(ContentTypeIdForPHP.ContentTypeID_PHP);
					if (PHPContentType != null) {
						if (fileContentType.isKindOf(PHPContentType)) {
							if (adapterType.equals(IRunToLineTarget.class)) {
								return new PHPRunToLineAdapter();
							}
						}
					}
				}
			}
		} catch (CoreException e1) {
			Logger.logException("PHPEditorAdapterFactory unexpected error", e1);
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
