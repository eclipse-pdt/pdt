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
package org.eclipse.php.internal.ui.outline;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.dltk.ui.viewsupport.DecoratingModelLabelProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.SortAction;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.html.ui.views.contentoutline.HTMLContentOutlineConfiguration;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeLabelProvider;
import org.eclipse.wst.xml.ui.internal.contentoutline.XMLNodeActionManager;

/**
 * Configuration holder for the PHP outline at the WST outline 
 */
public class PHPContentOutlineConfiguration extends HTMLContentOutlineConfiguration {

	protected PHPOutlineContentProvider fContentProvider = null;
	protected JFaceNodeContentProvider fContentProviderHTML = null;
	protected DecoratingModelLabelProvider fLabelProvider = null;
	protected PHPOutlineLabelProvider fLabelProviderHTML = null;
	private IPropertyChangeListener propertyChangeListener;
	private ChangeOutlineModeAction changeOutlineModeActionPHP;
	private ChangeOutlineModeAction changeOutlineModeActionHTML;
	static Object[] NO_CHILDREN = new Object[0];

	public static final int MODE_HTML = 2;

	public static final int MODE_PHP = 1;
	private int mode;

	public PHPContentOutlineConfiguration() {
		super();
		mode = PHPUiPlugin.getDefault().getPreferenceStore().getInt(PreferenceConstants.PREF_OUTLINEMODE);
	}

	public int getMode() {
		return mode;
	}

	public void setMode(final int mode) {
		this.mode = mode;
	}

	protected IContributionItem[] createMenuContributions(final TreeViewer viewer) {
		IContributionItem[] items;

		changeOutlineModeActionPHP = new ChangeOutlineModeAction(PHPUIMessages.getString("PHPOutlinePage_mode_php"), MODE_PHP, this, viewer);
		final IContributionItem showPHPItem = new ActionContributionItem(changeOutlineModeActionPHP);

		changeOutlineModeActionHTML = new ChangeOutlineModeAction(PHPUIMessages.getString("PHPOutlinePage_mode_html"), MODE_HTML, this, viewer);

		propertyChangeListener = new IPropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent event) {
				if (event.getProperty().equals("checked")) { //$NON-NLS-1$
					boolean checked = ((Boolean) event.getNewValue()).booleanValue();
					if (sortAction != null) {
						sortAction.setEnabled(!checked);
					}
				}
			}
		};
		changeOutlineModeActionHTML.addPropertyChangeListener(propertyChangeListener);

		final IContributionItem showHTMLItem = new ActionContributionItem(changeOutlineModeActionHTML);
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
		if (changeOutlineModeActionHTML.isChecked()) {
			sortAction.setEnabled(false);
		}
		return items;
	}

	private SortAction sortAction;
	private JFaceNodeLabelProvider fSimpleLabelProvider;
	//	private ShowGroupsAction fShowGroupsAction;
	protected IPreferenceStore fStore = PHPUiPlugin.getDefault().getPreferenceStore();

	protected IContributionItem[] createToolbarContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		//		fShowGroupsAction = new ShowGroupsAction("Show Groups", viewer);
		//		final IContributionItem showGroupsItem = new ActionContributionItem(fShowGroupsAction); //$NON-NLS-1$

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
			items = new IContributionItem[] { sortItem /*, showGroupsItem*/};
		else {
			final IContributionItem[] combinedItems = new IContributionItem[items.length + 2];
			System.arraycopy(items, 0, combinedItems, 0, items.length);
			combinedItems[items.length] = sortItem;
			//			combinedItems[items.length + 1] = showGroupsItem;
			combinedItems[items.length + 1] = toggleLinking;
			items = combinedItems;
		}
		return items;

	}

	public void unconfigure(TreeViewer viewer) {
		//		if (fShowGroupsAction != null) {
		//			fShowGroupsAction.dispose();
		//		}
		if (changeOutlineModeActionHTML != null && propertyChangeListener != null) {
			changeOutlineModeActionHTML.removePropertyChangeListener(propertyChangeListener);
		}
		super.unconfigure(viewer);
	}

	public IContentProvider getContentProvider(final TreeViewer viewer) {
		if (MODE_PHP == mode) {
			if (fContentProvider == null) {
				fContentProvider = new PHPOutlineContentProvider(viewer);
			}
			viewer.setContentProvider(fContentProvider);
		} else if (MODE_HTML == mode) {
			if (fContentProviderHTML == null) {
				fContentProviderHTML = new JFaceNodeContentProvider() {
					public Object[] getElements(Object object) {
						if (object instanceof ISourceModule) {
							IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
							if (activeEditor instanceof StructuredTextEditor) {
								StructuredTextEditor editor = (StructuredTextEditor) activeEditor;
								object = editor.getModel();
							}
						}
						return super.getElements(object);
					}
				};
			}
			viewer.setContentProvider(fContentProviderHTML);
		}
		return viewer.getContentProvider();
	}

	public ILabelProvider getLabelProvider(final TreeViewer viewer) {
		if (MODE_PHP == mode) {
			if (fLabelProvider == null) {
				AppearanceAwareLabelProvider lprovider = new AppearanceAwareLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS | ScriptElementLabels.F_APP_TYPE_SIGNATURE | ScriptElementLabels.ALL_CATEGORY, AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS, fStore);
				fLabelProvider = new DecoratingModelLabelProvider(lprovider);
			}
			viewer.setLabelProvider(fLabelProvider);
		} else if (MODE_HTML == mode) {
			if (fLabelProviderHTML == null) {
				fLabelProviderHTML = new PHPOutlineLabelProvider();
			}
			viewer.setLabelProvider(fLabelProviderHTML);
		}
		return (ILabelProvider) viewer.getLabelProvider();
	}

	public ISelection getSelection(final TreeViewer viewer, final ISelection selection) {
		final IContentProvider contentProvider = viewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			final PHPOutlineContentProvider phpOutline = (PHPOutlineContentProvider) contentProvider;
			if (MODE_PHP == mode) {
				if (selection instanceof IStructuredSelection && selection instanceof TextSelection) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
					if (activeEditor instanceof PHPStructuredEditor) {
						ISourceReference computedSourceReference = ((PHPStructuredEditor) activeEditor).computeHighlightRangeSourceReference();
						if (computedSourceReference != null) {
							return new StructuredSelection(computedSourceReference);
						}
					}
				}
			}
		}
		return super.getSelection(viewer, selection);
	}

	public ILabelProvider getStatusLineLabelProvider(TreeViewer treeViewer) {
		if (fSimpleLabelProvider == null) {
			getLabelProvider(treeViewer);
		}
		return fSimpleLabelProvider;
	}

	protected XMLNodeActionManager createNodeActionManager(TreeViewer treeViewer) {
		IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
		return new PHPNodeActionManager(((StructuredTextEditor) activeEditor).getModel(), treeViewer);
	}
}