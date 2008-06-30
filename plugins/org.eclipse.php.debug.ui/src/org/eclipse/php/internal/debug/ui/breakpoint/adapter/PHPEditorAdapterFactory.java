/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
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
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPEditorAdapterFactory implements IAdapterFactory {

	@SuppressWarnings("unchecked")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ITextEditor editorPart = (ITextEditor) adaptableObject;
		
		boolean isPHPFile = false;
		
		if (editorPart instanceof PHPStructuredEditor) {
			IModelElement modelElement = ((PHPStructuredEditor)editorPart).getModelElement();
			isPHPFile = PHPModelUtil.isPhpElement(modelElement);
		} else {
			IResource resource = (IResource) editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource instanceof IFile) {
				isPHPFile = PHPModelUtil.isPhpFile((IFile) resource);
			}
		}
		
		if (isPHPFile && adapterType == IRunToLineTarget.class) {
			return new PHPRunToLineAdapter();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Class[] getAdapterList() {
		return new Class[] { IRunToLineTarget.class };
	}
}
