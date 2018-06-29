/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.debug.ui.breakpoint.adapter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.php.core.PHPToolkitUtil;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.texteditor.ITextEditor;

public class PHPEditorAdapterFactory implements IAdapterFactory {

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		ITextEditor editorPart = (ITextEditor) adaptableObject;

		boolean isPHPFile = false;

		if (editorPart instanceof PHPStructuredEditor) {
			IModelElement modelElement = ((PHPStructuredEditor) editorPart).getModelElement();
			isPHPFile = modelElement != null && PHPToolkitUtil.isPHPElement(modelElement);
		} else {
			IResource resource = editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource instanceof IFile) {
				isPHPFile = PHPToolkitUtil.isPHPFile((IFile) resource);
			}
		}

		if (isPHPFile && adapterType == IRunToLineTarget.class) {
			return new PHPRunToLineAdapter();
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { IRunToLineTarget.class };
	}
}
