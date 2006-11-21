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
package org.eclipse.php.ui.outline;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.ui.actions.SortAction;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.php.ui.treecontent.TreeProvider;
import org.eclipse.php.ui.util.EditorUtility;
import org.eclipse.php.ui.util.PHPOutlineElementComparer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.wst.html.ui.views.contentoutline.HTMLContentOutlineConfiguration;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;

public class PHPContentOutlineConfiguration extends HTMLContentOutlineConfiguration {
	private PHPOutlineContentProvider fContentProvider = null;
	private PHPOutlineLabelProvider fLabelProvider = null;
	IPHPTreeContentProvider[] treeProviders;

	protected IContributionItem[] createMenuContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		final IContributionItem showPHPItem = new ActionContributionItem(new ChangeOutlineModeAction(PHPUIMessages.PHPOutlinePage_mode_php, PHPOutlineContentProvider.MODE_PHP, viewer));
		final IContributionItem showHTMLItem = new ActionContributionItem(new ChangeOutlineModeAction(PHPUIMessages.PHPOutlinePage_mode_html, PHPOutlineContentProvider.MODE_HTML, viewer));
		items = super.createMenuContributions(viewer);
		if (items == null)
			items = new IContributionItem[] { showPHPItem, showHTMLItem };
		else {
			final IContributionItem[] combinedItems = new IContributionItem[items.length + 2];
			System.arraycopy(items, 0, combinedItems, 0, items.length);
			combinedItems[items.length] = showPHPItem;
			combinedItems[items.length + 1] = showHTMLItem;
			items = combinedItems;
		}
		return items;
	}

	public static class DoubleClickListener implements IDoubleClickListener {

		private boolean enabled;

		public void doubleClick(DoubleClickEvent event) {
			ISelection selection = event.getSelection();
			if (!(selection instanceof IStructuredSelection)) {
				return;
			}
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (!(element instanceof PHPCodeData)) {
				return;
			}
			try {
				IEditorPart editor = EditorUtility.openInEditor(element, true);
				if (editor != null) {
					EditorUtility.revealInEditor(editor, (PHPCodeData) element);
				}
			} catch (PartInitException e) {
			}
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isEnabled() {
			return enabled;
		}
	}

	DoubleClickListener doubleClickListener = new DoubleClickListener();

	public DoubleClickListener getDoubleClickListener() {
		return doubleClickListener;
	}

	protected IContributionItem[] createToolbarContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		final IContributionItem showGroupsItem = new ActionContributionItem(new ShowGroupsAction("Show Groups", viewer));
		final IContributionItem toggleLinking = super.createMenuContributions(viewer)[0];
		final IContributionItem sortItem = new ActionContributionItem(new SortAction(viewer));
		items = super.createToolbarContributions(viewer);
		if (items == null)
			items = new IContributionItem[] { sortItem, showGroupsItem };
		else {
			final IContributionItem[] combinedItems = new IContributionItem[items.length + 3];
			System.arraycopy(items, 0, combinedItems, 0, items.length);
			combinedItems[items.length] = sortItem;
			combinedItems[items.length + 1] = showGroupsItem;
			combinedItems[items.length + 2] = toggleLinking;
			items = combinedItems;
		}
		return items;

	}

	public IContentProvider getContentProvider(final TreeViewer viewer) {
		if (fContentProvider == null) {
			viewer.setComparer(new PHPOutlineElementComparer());
			fContentProvider = new PHPOutlineContentProvider(viewer, (PHPOutlineLabelProvider) getLabelProvider(viewer));
			fContentProvider.phpContentProvider.setTreeProviders(getTreeProviders());
		}
		return fContentProvider;
	}

	public ILabelProvider getLabelProvider(final TreeViewer viewer) {
		if (fLabelProvider == null) {
			fLabelProvider = new PHPOutlineLabelProvider();
			fLabelProvider.phpLabelProvider.setTreeProviders(getTreeProviders());
		}
		return fLabelProvider;
	}

	public ISelection getSelection(final TreeViewer viewer, final ISelection selection) {
		final IContentProvider contentProvider = viewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			final PHPOutlineContentProvider phpOutline = (PHPOutlineContentProvider) contentProvider;
			if (phpOutline.mode == PHPOutlineContentProvider.MODE_PHP)
				if (selection instanceof IStructuredSelection && selection instanceof TextSelection) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					final Object obj = structuredSelection.getFirstElement();

					if (obj instanceof NodeImpl) {
						final PHPCodeData codeData = PHPElementImpl.getPHPCodeData((NodeImpl) obj, ((TextSelection) selection).getOffset());
						if (codeData != null)
							return new StructuredSelection(codeData);
					}
				}
		}
		return super.getSelection(viewer, selection);
	}

	private IPHPTreeContentProvider[] getTreeProviders() {
		if (treeProviders == null)
			treeProviders = TreeProvider.getTreeProviders(IPageLayout.ID_OUTLINE);
		return treeProviders;
	}

}