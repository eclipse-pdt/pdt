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
package org.eclipse.php.internal.ui.search;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.*;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class PHPSearchEditorOpener {

	private IEditorPart fEditor;

	public IEditorPart openElement(Object element) throws PartInitException {
		IWorkbenchPage wbPage= PHPUiPlugin.getActivePage();
		if (NewSearchUI.reuseEditor())
			return showWithReuse(element, wbPage);
		else
			return showWithoutReuse(element, wbPage);
	}
		
	public IEditorPart openMatch(Match match) throws PartInitException {
		Object element= getElementToOpen(match);
		return openElement(element);
	}

	protected Object getElementToOpen(Match match) {
		return match.getElement();
	}

	private IEditorPart showWithoutReuse(Object element, IWorkbenchPage wbPage) throws PartInitException {
		return EditorUtility.openInEditor(element, false);
	}

	private IEditorPart showWithReuse(Object element, IWorkbenchPage wbPage) throws PartInitException {
		IFile file= getFile(element);
		if (file != null) {
			String editorID= getEditorID(file);
			return showInEditor(wbPage, new FileEditorInput(file), editorID);
		} 
//		else {
//			IClassFile cf= getClassFile(element);
//			if (cf != null)
//				return showInEditor(wbPage, new InternalClassFileEditorInput(cf), JavaUI.ID_CF_EDITOR);
//		}
		return null;
	}

	private IFile getFile(Object element) {
		if (element instanceof IFile)
			return (IFile) element;
		if (element instanceof IAdaptable) {
			return (IFile)((IAdaptable)element).getAdapter(IResource.class);
		}
		return null;
	}

	private String getEditorID(IFile file) throws PartInitException {
		IEditorDescriptor desc= IDE.getEditorDescriptor(file);
		if (desc == null)
			return PHPUiPlugin.getDefault().getWorkbench().getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID).getId();
		else
			return desc.getId();
	}

	private boolean isPinned(IEditorPart editor) {
		if (editor == null)
			return false;

		IEditorReference[] editorRefs= editor.getEditorSite().getPage().getEditorReferences();
		int i= 0;
		while (i < editorRefs.length) {
			if (editor.equals(editorRefs[i].getEditor(false)))
				return editorRefs[i].isPinned();
			i++;
		}
		return false;
	}

	private IEditorPart showInEditor(IWorkbenchPage page, IEditorInput input, String editorId) {
		IEditorPart editor= page.findEditor(input);
		if (editor != null)
			page.bringToTop(editor);
		else {
			boolean isOpen= false;
			if (fEditor != null) {
				IEditorReference[] parts= page.getEditorReferences();
				int i= 0;
				while (!isOpen && i < parts.length)
					isOpen= fEditor == parts[i++].getEditor(false);
			}

			boolean canBeReused= isOpen && !fEditor.isDirty() && !isPinned(fEditor);
			boolean showsSameInputType= fEditor != null && fEditor.getSite().getId().equals(editorId);
			if (canBeReused && !showsSameInputType) {
				page.closeEditor(fEditor, false);
				fEditor= null;
			}

			if (canBeReused && showsSameInputType) {
				((IReusableEditor) fEditor).setInput(input);
				page.bringToTop(fEditor);
				editor= fEditor;
			} else {
				try {
					editor= page.openEditor(input, editorId, false);
					if (editor instanceof IReusableEditor)
						fEditor= editor;
					else
						fEditor= null;
				} catch (PartInitException ex) {
					MessageDialog.openError(PHPUiPlugin.getActiveWorkbenchShell(), PHPUIMessages.getString("Search_Error_openEditor_title"), PHPUIMessages.getString("Search_Error_openEditor_message")); 
					return null;
				}
			}
		}
		return editor;
	}

}
