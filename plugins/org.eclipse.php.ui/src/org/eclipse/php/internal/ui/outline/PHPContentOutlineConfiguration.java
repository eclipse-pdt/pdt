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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.documentModel.dom.Utils;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.actions.SortAction;
import org.eclipse.php.internal.ui.treecontent.TreeProvider;
import org.eclipse.php.internal.ui.util.AppearanceAwareLabelProvider;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.php.internal.ui.util.PHPElementLabels;
import org.eclipse.php.internal.ui.util.PHPOutlineElementComparer;
import org.eclipse.php.ui.treecontent.IPHPTreeContentProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.PartInitException;
import org.eclipse.wst.html.ui.views.contentoutline.HTMLContentOutlineConfiguration;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.xml.core.internal.document.NodeImpl;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.eclipse.wst.xml.ui.internal.contentoutline.XMLNodeActionManager;

public class PHPContentOutlineConfiguration extends HTMLContentOutlineConfiguration {
	protected PHPOutlineContentProvider fContentProvider = null;
	protected PHPOutlineLabelProvider fLabelProvider = null;
	IPHPTreeContentProvider[] treeProviders;

	protected IContributionItem[] createMenuContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		final IContributionItem showPHPItem = new ActionContributionItem(new ChangeOutlineModeAction(PHPUIMessages.getString("PHPOutlinePage_mode_php"), PHPOutlineContentProvider.MODE_PHP, viewer));
		ChangeOutlineModeAction action = new ChangeOutlineModeAction(PHPUIMessages.getString("PHPOutlinePage_mode_html"), PHPOutlineContentProvider.MODE_HTML, viewer);
		action.addPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals("checked")) { //$NON-NLS-1$
					boolean checked = ((Boolean) event.getNewValue()).booleanValue();
					if (sortAction != null) {
						sortAction.setEnabled(!checked);
					}
				}

			}

		});
		final IContributionItem showHTMLItem = new ActionContributionItem(action);
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
		if (action.isChecked()) {
			sortAction.setEnabled(false);
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
	private SortAction sortAction;
	private JFaceNodeLabelProvider fSimpleLabelProvider;

	public DoubleClickListener getDoubleClickListener() {
		return doubleClickListener;
	}

	protected IContributionItem[] createToolbarContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		final IContributionItem showGroupsItem = new ActionContributionItem(new ShowGroupsAction("Show Groups", viewer)); //$NON-NLS-1$
		// fixed bug 174653
		// use only the toggleLinking menu and dispose the others
		IContributionItem[] menuContributions = super.createMenuContributions(viewer);
		final IContributionItem toggleLinking = menuContributions[0];
		for (int i = 1; i < menuContributions.length; i++) {
			menuContributions[i].dispose();
		}
		sortAction = new SortAction(viewer);
		final IContributionItem sortItem = new ActionContributionItem(sortAction);

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
			PHPOutlineLabelProvider labelProvider = (PHPOutlineLabelProvider) getLabelProvider(viewer);
			fContentProvider = new PHPOutlineContentProvider(viewer, labelProvider);
			fContentProvider.phpContentProvider.setTreeProviders(getTreeProviders());
		}
		return fContentProvider;
	}

	public ILabelProvider getLabelProvider(final TreeViewer viewer) {
		if (fLabelProvider == null) {
			fLabelProvider = new PHPOutlineLabelProvider();
			fLabelProvider.setTreeProviders(getTreeProviders());
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
						final PHPCodeData codeData = Utils.getPHPCodeData((NodeImpl) obj, ((TextSelection) selection).getOffset());
						if (codeData != null)
							return new StructuredSelection(codeData);
					}
				}
		}
		return super.getSelection(viewer, selection);
	}

	protected IPHPTreeContentProvider[] getTreeProviders() {
		if (treeProviders == null)
			treeProviders = TreeProvider.getTreeProviders(IPageLayout.ID_OUTLINE);
		return treeProviders;
	}

	public ILabelProvider getStatusLineLabelProvider(TreeViewer treeViewer) {
		if (fSimpleLabelProvider == null) {
			fSimpleLabelProvider = new StatusLineLabelProvider(treeViewer);
		}
		return fSimpleLabelProvider;
	}

	private class StatusLineLabelProvider extends JFaceNodeLabelProvider {
		TreeViewer treeViewer = null;

		public StatusLineLabelProvider(TreeViewer viewer) {
			treeViewer = viewer;
		}

		public String getText(Object element) {
			if (element == null)
				return null;

			return PHPElementLabels.getTextLabel(element, AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS);
		}
	}

	protected XMLNodeActionManager createNodeActionManager(TreeViewer treeViewer) {
		return new PHPNodeActionManager((IStructuredModel) treeViewer.getInput(), treeViewer);
	}

	@Override
	protected void enableShowAttributes(boolean showAttributes, TreeViewer treeViewer) {
		super.enableShowAttributes(showAttributes, treeViewer);
		((PHPOutlineLabelProvider) getLabelProvider(treeViewer)).setShowAttributes(showAttributes);
	}

}