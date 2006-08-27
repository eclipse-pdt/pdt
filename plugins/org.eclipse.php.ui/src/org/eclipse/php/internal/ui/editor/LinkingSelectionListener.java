/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.phpModel.PHPModelUtil;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.ui.editor.PHPStructuredEditor;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMDocument;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMModel;

/**
 * @author seva
 *
 */
public class LinkingSelectionListener implements ISelectionListener {

	private boolean resetEmptySelection;
	/* (non-Javadoc)
	 * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
	 */
	private TreeViewer viewer;

	public LinkingSelectionListener() {
	}

	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		if (viewer == null || viewer.getControl() == null || viewer.getControl().isDisposed())
			return;
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 0) {
				final Object firstElement = structuredSelection.getFirstElement();
				PHPCodeData codeData = null;
				if (firstElement instanceof PHPElementImpl && part instanceof PHPStructuredEditor) {
					final PHPElementImpl phpElement = (PHPElementImpl) firstElement;
					codeData = phpElement.getPHPCodeData(((TextSelection) selection).getOffset());
				} else if (firstElement instanceof NodeImpl) {
					final IDOMDocument doc = (IDOMDocument) ((NodeImpl) firstElement).getOwnerDocument();
					if (doc == null)
						return;
					final IDOMModel model = doc.getModel();
					if (!(model instanceof PHPEditorModel))
						return;
					codeData = PHPElementImpl.getPHPCodeData((NodeImpl) firstElement, ((TextSelection) selection).getOffset());
				} else if (firstElement instanceof PHPCodeData) {
					codeData = (PHPCodeData) firstElement;
					viewer.reveal(codeData);
					return;
				}
				Object selectedElement = null;
				if (codeData != null)
					if (codeData instanceof PHPFileData)
						selectedElement = PHPModelUtil.getResource(codeData);
					else
						selectedElement = codeData;
				if (selectedElement != null)
					viewer.setSelection(new StructuredSelection(selectedElement), true);
				else if (resetEmptySelection)
					viewer.setSelection(null);
			}
		}
	}

	public void setResetEmptySelection(final boolean resetEmptySelection) {
		this.resetEmptySelection = resetEmptySelection;
	}

	public void setViewer(final TreeViewer viewer) {
		this.viewer = viewer;
	}
}
