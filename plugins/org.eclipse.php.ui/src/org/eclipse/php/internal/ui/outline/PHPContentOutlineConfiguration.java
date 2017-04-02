/*******************************************************************************
 * Copyright (c) 2009, 2016, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *     Dawid Pakuła [444025] [469503] 
 *******************************************************************************/
package org.eclipse.php.internal.ui.outline;

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ISourceReference;
import org.eclipse.dltk.ui.DLTKPluginImages;
import org.eclipse.dltk.ui.ProblemsLabelDecorator;
import org.eclipse.dltk.ui.ScriptElementImageProvider;
import org.eclipse.dltk.ui.ScriptElementLabels;
import org.eclipse.dltk.ui.viewsupport.AppearanceAwareLabelProvider;
import org.eclipse.dltk.ui.viewsupport.ScriptUILabelProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.SortAction;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.outline.PHPOutlineContentProvider.UseStatementsNode;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.ui.OverrideIndicatorLabelDecorator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.wst.html.ui.views.contentoutline.HTMLContentOutlineConfiguration;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.sse.ui.views.contentoutline.ContentOutlineFilterProcessor;
import org.eclipse.wst.xml.ui.internal.contentoutline.JFaceNodeContentProvider;
import org.eclipse.wst.xml.ui.internal.contentoutline.XMLNodeActionManager;

/**
 * Configuration holder for the PHP outline at the WST outline
 */
public class PHPContentOutlineConfiguration extends HTMLContentOutlineConfiguration {

	private static final String OUTLINE_PAGE = "org.eclipse.php.ui.OutlinePage"; //$NON-NLS-1$
	private static final String OUTLINE_SHOW_ATTRIBUTE_ID = "outline-show-attribute"; //$NON-NLS-1$
	private final static String OUTLINE_MODE = "OUTLINE_MODE"; //$NON-NLS-1$
	private static final IContributionItem[] NO_CONTRIBUTION = new IContributionItem[0];
	public static final int MODE_PHP = 1;
	public static final int MODE_HTML = 2;

	protected PHPOutlineContentProvider fContentProvider = null;
	protected JFaceNodeContentProvider fContentProviderHTML = null;
	protected ScriptUILabelProvider fLabelProvider = null;
	protected PHPOutlineLabelProvider fLabelProviderHTML = null;
	private IPropertyChangeListener propertyChangeListener;
	private ChangeOutlineModeAction changeOutlineModeActionPHP;
	private ChangeOutlineModeAction changeOutlineModeActionHTML;
	static Object[] NO_CHILDREN = new Object[0];
	private SortAction sortAction;
	private ScriptUILabelProvider fSimpleLabelProvider;
	// private ShowGroupsAction fShowGroupsAction;
	private boolean fShowAttributes = false;
	private CustomFiltersActionGroup fCustomFiltersActionGroup;

	/** See {@link #MODE_PHP}, {@link #MODE_HTML} */
	private int mode;
	private ISelection lastSelection = null;

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

	@Override
	protected IContributionItem[] createMenuContributions(final TreeViewer viewer) {
		IContributionItem[] items;

		changeOutlineModeActionPHP = new ChangeOutlineModeAction(PHPUIMessages.PHPOutlinePage_mode_php, MODE_PHP, this,
				viewer);
		final IContributionItem showPHPItem = new ActionContributionItem(changeOutlineModeActionPHP);

		changeOutlineModeActionHTML = new ChangeOutlineModeAction(PHPUIMessages.PHPOutlinePage_mode_html, MODE_HTML,
				this, viewer);

		propertyChangeListener = new IPropertyChangeListener() {
			@Override
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

		if (fCustomFiltersActionGroup == null) {
			fCustomFiltersActionGroup = new CustomFiltersActionGroup(OUTLINE_PAGE, viewer);
		}

		items = super.createMenuContributions(viewer);

		if (items == null) {
			items = new IContributionItem[] { showPHPItem, showHTMLItem };
		} else {
			final IContributionItem[] combinedItems = new IContributionItem[items.length + 3];
			combinedItems[0] = showPHPItem;
			combinedItems[1] = showHTMLItem;
			combinedItems[2] = new Separator(OUTLINE_MODE);

			for (int i = 0; i < items.length; i++) {
				if (OUTLINE_SHOW_ATTRIBUTE_ID.equals(items[i].getId())) {
					items[i] = new ModeBasedContributionItem(MODE_HTML, items[i]);
					break;
				}
			}

			System.arraycopy(items, 0, combinedItems, 3, items.length);
			items = combinedItems;
		}
		if (changeOutlineModeActionHTML.isChecked() && sortAction != null) {
			sortAction.setEnabled(false);
		}
		return items;
	}

	@Override
	protected IContributionItem[] createToolbarContributions(final TreeViewer viewer) {
		IContributionItem[] items;
		// fShowGroupsAction = new ShowGroupsAction("Show Groups", viewer);
		// final IContributionItem showGroupsItem = new
		// ActionContributionItem(fShowGroupsAction);

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
			items = new IContributionItem[] { sortItem /* , showGroupsItem */ };
		else {
			final IContributionItem[] combinedItems = new IContributionItem[items.length + 2];
			System.arraycopy(items, 0, combinedItems, 0, items.length);
			combinedItems[items.length] = sortItem;
			// combinedItems[items.length + 1] = showGroupsItem;
			combinedItems[items.length + 1] = toggleLinking;
			items = combinedItems;
		}
		return items;

	}

	@Override
	public void unconfigure(TreeViewer viewer) {
		// if (fShowGroupsAction != null) {
		// fShowGroupsAction.dispose();
		// }
		if (fCustomFiltersActionGroup != null) {
			fCustomFiltersActionGroup.dispose();
			fCustomFiltersActionGroup = null;
		}

		if (changeOutlineModeActionHTML != null && propertyChangeListener != null) {
			changeOutlineModeActionHTML.removePropertyChangeListener(propertyChangeListener);
		}
		super.unconfigure(viewer);
	}

	@Override
	public IContentProvider getContentProvider(final TreeViewer viewer) {
		if (MODE_PHP == mode) {
			if (fContentProvider == null) {
				fContentProvider = new PHPOutlineContentProvider(viewer);
			}
			viewer.setContentProvider(fContentProvider);
		} else if (MODE_HTML == mode) {
			if (fContentProviderHTML == null) {
				fContentProviderHTML = new JFaceNodeContentProvider() {
					@Override
					public Object[] getElements(Object object) {
						if (object instanceof ISourceModule) {
							IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
							if (activeEditor instanceof StructuredTextEditor) {
								StructuredTextEditor editor = (StructuredTextEditor) activeEditor;
								IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
								IStructuredModel model = null;
								try {
									model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
									return super.getElements(model);
								} finally {
									if (model != null) {
										model.releaseFromRead();
									}
								}
							}
						}
						return super.getElements(object);
					}

					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
						if (newInput instanceof ISourceModule) {
							IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
							if (activeEditor instanceof StructuredTextEditor) {
								StructuredTextEditor editor = (StructuredTextEditor) activeEditor;
								IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
								IStructuredModel model = null;
								try {
									model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
								} finally {
									if (model != null) {
										model.releaseFromRead();
									}
								}
								newInput = model;
							}
						}
						super.inputChanged(viewer, oldInput, newInput);
					}
				};
			}
			viewer.setContentProvider(fContentProviderHTML);
		}
		return viewer.getContentProvider();
	}

	@Override
	public ILabelProvider getLabelProvider(final TreeViewer viewer) {
		// workaround for https://bugs.eclipse.org/bugs/show_bug.cgi?id=493711
		if (viewer.getInput() == null) {
			viewer.setUseHashlookup(true);
		}

		if (fLabelProvider == null) {
			fLabelProvider = new PHPAppearanceAwareLabelProvider(AppearanceAwareLabelProvider.DEFAULT_TEXTFLAGS
					| ScriptElementLabels.F_APP_TYPE_SIGNATURE | ScriptElementLabels.ALL_CATEGORY,
					AppearanceAwareLabelProvider.DEFAULT_IMAGEFLAGS, null);
			fLabelProvider.addLabelDecorator(new OverrideIndicatorLabelDecorator());
			fLabelProvider.addLabelDecorator(new ProblemsLabelDecorator());
		}

		if (fLabelProviderHTML == null) {
			fLabelProviderHTML = new PHPOutlineLabelProvider(fLabelProvider);
			fLabelProviderHTML.fShowAttributes = fShowAttributes;
		}
		if (MODE_PHP == mode) {
			return new PHPStyledDecoratingModelLabelProvider((IStyledLabelProvider) fLabelProvider);
		} else if (MODE_HTML == mode) {
			return new PHPStyledDecoratingModelLabelProvider((IStyledLabelProvider) fLabelProviderHTML);
		}
		return (ILabelProvider) viewer.getLabelProvider();
	}

	@Override
	public ISelection getSelection(final TreeViewer viewer, final ISelection selection) {
		if (!isLinkedWithEditor(viewer)) {
			return lastSelection;
		}
		if (!selection.isEmpty()) {
			lastSelection = selection;
		}
		final IContentProvider contentProvider = viewer.getContentProvider();
		if (contentProvider instanceof PHPOutlineContentProvider) {
			if (MODE_PHP == mode) {
				if (lastSelection instanceof IStructuredSelection && lastSelection instanceof TextSelection) {
					IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
					if (activeEditor instanceof PHPStructuredEditor) {
						ISourceReference computedSourceReference = ((PHPStructuredEditor) activeEditor)
								.computeHighlightRangeSourceReference();
						if (computedSourceReference != null) {
							Object parent = ((PHPOutlineContentProvider) contentProvider)
									.getParent(computedSourceReference);
							for (Object element : ((PHPOutlineContentProvider) contentProvider).getChildren(parent))
								if (element == computedSourceReference) {
									lastSelection = new StructuredSelection(computedSourceReference);
									return lastSelection;
								}
							lastSelection = new StructuredSelection(parent);
							return lastSelection;
						}
					}
				}
			}
		}
		return super.getSelection(viewer, lastSelection);
	}

	@Override
	public ILabelProvider getStatusLineLabelProvider(TreeViewer treeViewer) {
		if (fSimpleLabelProvider == null) {
			fSimpleLabelProvider = new ScriptUILabelProvider();
			fSimpleLabelProvider
					.setTextFlags(ScriptElementLabels.DEFAULT_QUALIFIED | ScriptElementLabels.ROOT_POST_QUALIFIED
							| ScriptElementLabels.APPEND_ROOT_PATH | ScriptElementLabels.M_PARAMETER_TYPES
							| ScriptElementLabels.M_PARAMETER_NAMES | ScriptElementLabels.M_PARAMETER_INITIALIZERS
							| ScriptElementLabels.M_APP_RETURNTYPE | ScriptElementLabels.M_EXCEPTIONS
							| ScriptElementLabels.F_APP_TYPE_SIGNATURE | ScriptElementLabels.T_TYPE_PARAMETERS);
		}
		return fSimpleLabelProvider;
	}

	@Override
	protected XMLNodeActionManager createNodeActionManager(TreeViewer treeViewer) {
		IEditorPart activeEditor = PHPUiPlugin.getActiveEditor();
		if (activeEditor instanceof StructuredTextEditor) {
			StructuredTextEditor editor = (StructuredTextEditor) activeEditor;
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			IStructuredModel model = null;
			try {
				model = StructuredModelManager.getModelManager().getExistingModelForRead(document);
				return new PHPNodeActionManager(model, treeViewer);
			} finally {
				if (model != null) {
					model.releaseFromRead();
				}
			}
		}
		return null;
	}

	@Override
	protected void enableShowAttributes(boolean showAttributes, TreeViewer treeViewer) {
		super.enableShowAttributes(showAttributes, treeViewer);
		// fix bug #241111 - show attributes in outline
		if (fLabelProviderHTML != null) {
			// This option is only relevant for the HTML outline
			fLabelProviderHTML.fShowAttributes = showAttributes;
		}
		fShowAttributes = showAttributes;
	}

	class UseStatementAwareImageProvider extends ScriptElementImageProvider {

		@Override
		public ImageDescriptor getBaseImageDescriptor(IModelElement element, int renderFlags) {
			if (element instanceof UseStatementsNode) {
				return DLTKPluginImages.DESC_OBJS_IMPCONT;
			}

			// If element is a field or method and it's parent is not a class or
			// interface
			// then we can avoid time consuming flag checking
			// (public/protected/private flags)
			IModelElement parent = element.getParent();
			if (parent != null && parent.getElementType() != IModelElement.TYPE) {
				if (element.getElementType() == IModelElement.FIELD) {
					return DLTKPluginImages.DESC_FIELD_PUBLIC;
				}
				if (element.getElementType() == IModelElement.METHOD) {
					return DLTKPluginImages.DESC_METHOD_PUBLIC;
				}
			}

			return super.getBaseImageDescriptor(element, renderFlags);
		}
	}

	class PHPStyledDecoratingModelLabelProvider extends DecoratingStyledCellLabelProvider implements ILabelProvider {

		public PHPStyledDecoratingModelLabelProvider(IStyledLabelProvider labelProvider) {
			super(labelProvider, null, null);
		}

		@Override
		public String getText(Object element) {
			return getStyledText(element).toString();
		}

	}

	class PHPAppearanceAwareLabelProvider extends AppearanceAwareLabelProvider {

		public PHPAppearanceAwareLabelProvider(IPreferenceStore store) {
			super(store == null ? PHPUiPlugin.getDefault().getPreferenceStore() : store);
			fImageLabelProvider = new UseStatementAwareImageProvider();
		}

		public PHPAppearanceAwareLabelProvider(long textFlags, int imageFlags, IPreferenceStore store) {
			super(textFlags, imageFlags, store == null ? PHPUiPlugin.getDefault().getPreferenceStore() : store);
			fImageLabelProvider = new UseStatementAwareImageProvider();
		}

		@Override
		public String getText(Object element) {
			if (element instanceof UseStatementsNode) {
				return PHPUIMessages.PHPContentOutlineConfiguration_2;
			}
			if (element instanceof IModelElement) {
				IModelElement me = (IModelElement) element;
				if (me.getElementType() == IModelElement.FIELD) {
					return me.getElementName();
				}
			}
			return super.getText(element);
		}
	}

	@Override
	public ContentOutlineFilterProcessor getOutlineFilterProcessor(TreeViewer viewer) {
		if (getMode() == MODE_PHP) {
			return new PHPOutlineFilterProcessor(getPreferenceStore(), getOutlineFilterTarget(), viewer);
		}
		return super.getOutlineFilterProcessor(viewer);
	}

	private class PHPOutlineFilterProcessor extends ContentOutlineFilterProcessor {
		// Custom filter group
		public PHPOutlineFilterProcessor(IPreferenceStore store, String ownerId, StructuredViewer viewer) {
			super(store, ownerId, viewer);
		}

		@Override
		public void openDialog() {
			fCustomFiltersActionGroup.new ShowFilterDialogAction().run();
		}

	}

	private class ModeBasedContributionItem extends CompoundContributionItem {

		private final int fMode;
		private IContributionItem fWrappedContribution;

		public ModeBasedContributionItem(int mode, IContributionItem wrappedContribution) {
			super(wrappedContribution.getId());
			fMode = mode;
			fWrappedContribution = wrappedContribution;
		}

		@Override
		protected IContributionItem[] getContributionItems() {
			if (getMode() == fMode) {
				return new IContributionItem[] { fWrappedContribution };
			}
			return NO_CONTRIBUTION;
		}

		@Override
		public void dispose() {
			super.dispose();
			fWrappedContribution.dispose();
		}
	}
}