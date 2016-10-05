/*******************************************************************************
 * Copyright (c) 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies -  initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.navigator;

import org.eclipse.core.resources.IFile;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.ILinkHelper;

public class PHPNavigatorLinkHelper implements ILinkHelper {

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		IFile file = ResourceUtil.getFile(anInput);
		if (file != null) {
			return new StructuredSelection(file);
		}

		ISourceModule sourceModule = DLTKUIPlugin.getEditorInputModelElement(anInput);
		return sourceModule != null ? new StructuredSelection(sourceModule) : StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		Object obj = aSelection.getFirstElement();

		if (aSelection.size() == 1) {
			IEditorPart part = EditorUtility.isOpenInEditor(obj);
			if (part != null) {
				IWorkbenchPage page = aPage;
				page.bringToTop(part);
				if (obj instanceof IModelElement) {
					EditorUtility.revealInEditor(part, (IModelElement) obj);
				}
			}
		}
	}

}
