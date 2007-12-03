/**
 * Copyright (c) 2006 Zend Technologies
 * 
 */
package org.eclipse.php.internal.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.dom.Utils;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
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

	protected ISelection createSelection(Object element) {
		ISelection selection = new StructuredSelection(element);
		return selection;
	}

	protected Object computeSelectedElement(IWorkbenchPart part, ISelection selection) {
		if (viewer == null || viewer.getControl() == null || viewer.getControl().isDisposed())
			return null;
		if (selection instanceof IStructuredSelection) {
			final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() > 0) {
				final Object firstElement = structuredSelection.getFirstElement();
				PHPCodeData codeData = null;
				if (firstElement instanceof NodeImpl) {
					final IDOMDocument doc = (IDOMDocument) ((NodeImpl) firstElement).getOwnerDocument();
					if (doc == null)
						return null;
					final IDOMModel model = doc.getModel();
					if (!(model instanceof DOMModelForPHP))
						return null;
					if (selection instanceof TextSelection) {
						codeData = Utils.getPHPCodeData((NodeImpl) firstElement, ((TextSelection) selection).getOffset());
					}
				} else if (firstElement instanceof PHPCodeData) {
					codeData = (PHPCodeData) firstElement;
				}
				Object selectedElement = null;
				if (codeData != null)
					if (codeData instanceof PHPFileData) {
						IResource res = PHPModelUtil.getResource(codeData);
						if (res != null && res.getProject() != null && res.getProject().isAccessible())
							selectedElement = res;
						else
							selectedElement = codeData;
					} else
						selectedElement = codeData;
				return selectedElement;
			}
		}
		return null;
	}

	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		Object selectedElement = computeSelectedElement(part, selection);
		if (selectedElement != null) {
			//Check if PHPCodeData since selection can be FileData, FunctionData etc...
			if (selectedElement instanceof PHPCodeData) {
				UserData userData = ((PHPCodeData) selectedElement).getUserData();
				if (userData != null && ExternalFilesRegistry.getInstance().isEntryExist(new Path(userData.getFileName()).toOSString())) {
					return;
				}
			}

			Object oldSelectedElement = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (selectedElement.equals(oldSelectedElement))
				return;
			if (selectedElement instanceof IResource && selectedElement.equals(PHPModelUtil.getResource(oldSelectedElement))) {
				return;
			}
			viewer.setSelection(createSelection(selectedElement), true);
		} else if (resetEmptySelection && !viewer.getSelection().isEmpty())
			viewer.setSelection(null);
	}

	public void setResetEmptySelection(final boolean resetEmptySelection) {
		this.resetEmptySelection = resetEmptySelection;
	}

	public void setViewer(final TreeViewer viewer) {
		this.viewer = viewer;
	}
}
