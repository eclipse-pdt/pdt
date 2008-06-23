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
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPEditorAdapterFactory implements IAdapterFactory {

	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ITextEditor editorPart = (ITextEditor) adaptableObject;
		IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
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
