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
package org.eclipse.php.ui.editor;

import java.util.Arrays;
import java.util.ResourceBundle;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.core.containers.LocalFileStorage;
import org.eclipse.php.core.containers.ZipEntryStorage;
import org.eclipse.php.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.actions.ActionMessages;
import org.eclipse.php.internal.ui.actions.AddBlockCommentAction;
import org.eclipse.php.internal.ui.actions.BlockCommentAction;
import org.eclipse.php.internal.ui.actions.FoldingActionGroup;
import org.eclipse.php.internal.ui.actions.OpenDeclarationAction;
import org.eclipse.php.internal.ui.actions.OpenFunctionsManualAction;
import org.eclipse.php.internal.ui.actions.RemoveBlockCommentAction;
import org.eclipse.php.internal.ui.actions.ToggleCommentAction;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.containers.StorageEditorInput;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.contentoutline.ConfigurableContentOutlinePage;
import org.eclipse.wst.sse.ui.internal.projection.IStructuredTextFoldingProvider;

public class PHPStructuredEditor extends StructuredTextEditor {

	static {
		// Needed to enable the folding
		// See StructuredTextEditor#isFoldingEnabled()
		System.setProperty("org.eclipse.wst.sse.ui.foldingenabled", "foldingenabled"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private FoldingActionGroup foldingGroup;
	
	public PHPStructuredEditor() {
		initFolding();
	}

	/*
	 * Initialize the folding support (hack the WST to enable folding)
	 */
	private void initFolding() {
		// TODO - Might need a fix after the WST will support code folding
		// officially.
		boolean foldingEnabled = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		if (foldingEnabled) {
			// Needed to enable the folding
			// See StructuredTextEditor#isFoldingEnabled()
			SSEUIPlugin.getDefault().getPreferenceStore().setValue(IStructuredTextFoldingProvider.FOLDING_ENABLED, true);
		}
	}

	protected void handlePreferenceStoreChanged(PropertyChangeEvent event) {
		String property = event.getProperty();
		try {
			if (PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIERS.equals(property)) {
				updateHoverBehavior();
			}
		} finally {
			super.handlePreferenceStoreChanged(event);
		}
	}

	protected void doSetInput(IEditorInput input) throws CoreException {
		IResource resource = null;
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();
		} else if (input instanceof StorageEditorInput) {
			StorageEditorInput editorInput = (StorageEditorInput)input;
			IStorage storage = editorInput.getStorage();
			if(storage instanceof ZipEntryStorage){
				resource = ((ZipEntryStorage)storage).getProject();
			} else if (storage instanceof LocalFileStorage){
				resource = ((LocalFileStorage)storage).getProject();
			}
		}
		PhpSourceParser.editFile.set(resource);
		super.doSetInput(input);
	}

	public PHPFileData getPHPFileData() {
		String filename = getModel().getBaseLocation();
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filename));
		return PHPWorkspaceModelManager.getInstance().getModelForProject(file.getProject()).getFileData(filename);
	}

	public void setSelection(PHPCodeData element, boolean reveal) {
		if (element != null) {
			UserData userData = element.getUserData();
			int start = userData.getStartPosition();
			int length = userData.getEndPosition() - userData.getStartPosition() + 1;

			IDocument document = getSourceViewer().getDocument();
			if (document instanceof IStructuredDocument) {
				IStructuredDocument sDocument = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDocument.getRegionAtCharacterOffset(start);
				if (sdRegion != null) {
					// Need it in case the php document doesn't start at the first
					// line of the page
					int sdRegionStart = sdRegion.getStartOffset();

					ITextRegion region = sdRegion.getRegionAtCharacterOffset(start);
					String elementName = element.getName();
					if (element instanceof PHPVariableData) {
						elementName = "$" + elementName; //$NON-NLS-1$
					}
					while (region != null && (region.getEnd() + sdRegionStart) < start + length) {
						String text = sdRegion.getText(region).trim();
						if (elementName.equals(text)) {
							start = region.getStart() + sdRegionStart;
							length = region.getTextLength();
							break;
						}
						region = sdRegion.getRegionAtCharacterOffset(region.getEnd() + sdRegionStart);
					}
				}
			}

			if (!reveal)
				getSourceViewer().setSelectedRange(start, length);
			else
				selectAndReveal(start, length);
		}
	}

	public IFile getFile() {
		String filename = getModel().getBaseLocation();
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filename));
	}

	public SourceViewerConfiguration getSourceViwerConfiguration() {
		return super.getSourceViewerConfiguration();
	}

	public Object getAdapter(Class required) {
		Object adapter = super.getAdapter(required);

		// add selection listener to outline page
		// so that if outline selects codedata, editor selects correct item
		if (adapter instanceof ConfigurableContentOutlinePage && IContentOutlinePage.class.equals(required)) {
			ConfigurableContentOutlinePage outlinePage = (ConfigurableContentOutlinePage) adapter;
			outlinePage.addSelectionChangedListener(new ISelectionChangedListener() {

				boolean selecting = false;

				public void selectionChanged(SelectionChangedEvent event) {
					/*
					 * The isFiringSelection check only works if a
					 * selection listener
					 */
					if (event.getSelection().isEmpty() || selecting)
						return;

					if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null && !getSourceViewer().getTextWidget().isDisposed() && !getSourceViewer().getTextWidget().isFocusControl()) {
						if (event.getSelection() instanceof IStructuredSelection) {
							ISelection current = getSelectionProvider().getSelection();
							if (current instanceof IStructuredSelection) {
								Object[] currentSelection = ((IStructuredSelection) current).toArray();
								Object[] newSelection = ((IStructuredSelection) event.getSelection()).toArray();
								if (!Arrays.equals(currentSelection, newSelection)) {
									if (newSelection.length > 0) {
										/*
										 * No ordering is guaranteed for
										 * multiple selection
										 */
										Object o = newSelection[0];
										selecting = true;
										if (o instanceof PHPCodeData) {
											setSelection((PHPCodeData) o, true);
										}
										selecting = false;
									}
								}
							}
						}
					}
				}

			});
		}
		return adapter;
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.StructuredTextEditor#dispose()
	 */
	public void dispose() {
		super.dispose();
		if (foldingGroup != null) {
			foldingGroup.dispose();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#rulerContextMenuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	protected void rulerContextMenuAboutToShow(IMenuManager menu) {
		super.rulerContextMenuAboutToShow(menu);
		IMenuManager foldingMenu= new MenuManager(PHPEditorMessages.PHP_Editor_FoldingMenu_name, "projection"); //$NON-NLS-1$
		menu.appendToGroup(ITextEditorActionConstants.GROUP_RULERS, foldingMenu);

		IAction action= getAction("FoldingToggle"); //$NON-NLS-1$
		foldingMenu.add(action);
		action= getAction("FoldingExpandAll"); //$NON-NLS-1$
		foldingMenu.add(action);
	}
	
	protected void createActions() {
		super.createActions();
		
		foldingGroup= new FoldingActionGroup(this, getTextViewer());
		
		ResourceBundle resourceBundle = ActionMessages.getResourceBundle();
		ISourceViewer sourceViewer = getSourceViewer();
		SourceViewerConfiguration configuration = getSourceViewerConfiguration();

		Action action = new ToggleCommentAction(resourceBundle, "ToggleCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.toggle.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.ToggleCommentAction", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.ToggleCommentAction", true); //$NON-NLS-1$
		((ToggleCommentAction) action).configure(sourceViewer, configuration);

		action = new AddBlockCommentAction(resourceBundle, "AddBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.add.block.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.AddBlockComment", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.AddBlockComment", true); //$NON-NLS-1$
		markAsSelectionDependentAction("org.eclipse.php.ui.actions.AddBlockComment", true); //$NON-NLS-1$
		((BlockCommentAction) action).configure(sourceViewer, configuration);

		action = new RemoveBlockCommentAction(resourceBundle, "RemoveBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.remove.block.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.RemoveBlockComment", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.RemoveBlockComment", true); //$NON-NLS-1$
		((BlockCommentAction) action).configure(sourceViewer, configuration);

		action = new TextOperationAction(resourceBundle, "CommentAction_", this, ITextOperationTarget.PREFIX); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.Comment", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.Comment", true); //$NON-NLS-1$

		action = new TextOperationAction(resourceBundle, "UncommentAction_", this, ITextOperationTarget.PREFIX); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.uncomment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.Uncomment", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.Uncomment", true); //$NON-NLS-1$

		action = new OpenFunctionsManualAction(resourceBundle, this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.OpenFunctionsManualAction"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction", true); //$NON-NLS-1$
		
		action = new OpenDeclarationAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.open.editor"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.Open", action); //$NON-NLS-1$
		markAsStateDependentAction("org.eclipse.php.ui.actions.Open", true); //$NON-NLS-1$
	}

	protected void addContextMenuActions(IMenuManager menu) {
		super.addContextMenuActions(menu);

		if (getSourceViewer().isEditable()) {
			String label = PHPEditorMessages.PHPStructuredEditor_Source;
			MenuManager subMenu = new MenuManager(label, "org.eclipse.php.ui.source.menu"); //$NON-NLS-1$
			subMenu.add(new GroupMarker("editGroup")); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.ToggleCommentAction"); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.AddBlockComment"); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.RemoveBlockComment"); //$NON-NLS-1$
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, subMenu);
			
			String openGroup = "group.open"; //$NON-NLS-1$
			menu.appendToGroup (ITextEditorActionConstants.GROUP_EDIT, new Separator(openGroup));
			IAction action = getAction("org.eclipse.php.ui.actions.Open"); //$NON-NLS-1$
			if (action != null) {
				menu.appendToGroup(openGroup, action);
			}
			action = getAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction"); //$NON-NLS-1$
			if (action != null) {
				menu.appendToGroup(openGroup, action);
			}
		}
	}

	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(new String[] { "org.eclipse.php.ui.phpEditorScope" }); //$NON-NLS-1$
	}

	/*
	 * Update the hovering behavior depending on the preferences.
	 */
	private void updateHoverBehavior() {
		SourceViewerConfiguration configuration = getSourceViewerConfiguration();
		String[] types = configuration.getConfiguredContentTypes(getSourceViewer());

		for (int i = 0; i < types.length; i++) {

			String t = types[i];

			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension2) {
				// Remove existing hovers
				((ITextViewerExtension2) sourceViewer).removeTextHovers(t);

				int[] stateMasks = configuration.getConfiguredTextHoverStateMasks(getSourceViewer(), t);

				if (stateMasks != null) {
					for (int j = 0; j < stateMasks.length; j++) {
						int stateMask = stateMasks[j];
						ITextHover textHover = configuration.getTextHover(sourceViewer, t, stateMask);
						((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, stateMask);
					}
				} else {
					ITextHover textHover = configuration.getTextHover(sourceViewer, t);
					((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
				}
			} else
				sourceViewer.setTextHover(configuration.getTextHover(sourceViewer, t), t);
		}
	}
}
