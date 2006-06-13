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

import java.util.*;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.*;
import org.eclipse.jface.internal.text.link.contentassist.HTMLTextPresenter;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.source.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.core.containers.LocalFileStorage;
import org.eclipse.php.core.containers.ZipEntryStorage;
import org.eclipse.php.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.actions.*;
import org.eclipse.php.ui.PHPUiPlugin;
import org.eclipse.php.ui.containers.StorageEditorInput;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorators;
import org.eclipse.php.ui.editor.hover.SourceViewerInformationControl;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.texteditor.*;
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
	/** The information presenter. */
	protected InformationPresenter fInformationPresenter;

	private FoldingActionGroup foldingGroup;
	IHoverMessageDecorators messageDecorator;
	
	/** Cursor dependent actions. */
	private List fCursorActions= new ArrayList(5);

	public PHPStructuredEditor() {
		initFolding();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.php.ui.hoverMessageDecorators");
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement element = elements[i];
			try {
				messageDecorator = (IHoverMessageDecorators) element.createExecutableExtension("class");
			} catch (CoreException e) {
				// TODO  - log exception
				e.printStackTrace();
			}
		}

	}

	public void createPartControl(Composite parent) {
		super.createPartControl(parent);

		IInformationControlCreator informationControlCreator = new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {
				boolean cutDown = false;
				int style = cutDown ? SWT.NONE : (SWT.V_SCROLL | SWT.H_SCROLL);
				return new DefaultInformationControl(shell, SWT.RESIZE | SWT.TOOL, style, new HTMLTextPresenter(cutDown));
			}
		};

		fInformationPresenter = new InformationPresenter(informationControlCreator);
		fInformationPresenter.setSizeConstraints(60, 10, true, true);
		fInformationPresenter.install(getSourceViewer());
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
			StorageEditorInput editorInput = (StorageEditorInput) input;
			IStorage storage = editorInput.getStorage();
			if (storage instanceof ZipEntryStorage) {
				resource = ((ZipEntryStorage) storage).getProject();
			} else if (storage instanceof LocalFileStorage) {
				resource = ((LocalFileStorage) storage).getProject();
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
		IMenuManager foldingMenu = new MenuManager(PHPEditorMessages.PHP_Editor_FoldingMenu_name, "projection"); //$NON-NLS-1$
		menu.appendToGroup(ITextEditorActionConstants.GROUP_RULERS, foldingMenu);

		IAction action = getAction("FoldingToggle"); //$NON-NLS-1$
		foldingMenu.add(action);
		action = getAction("FoldingExpandAll"); //$NON-NLS-1$
		foldingMenu.add(action);
	}

	protected void createActions() {
		super.createActions();

		foldingGroup = new FoldingActionGroup(this, getTextViewer());

		ResourceBundle resourceBundle = ActionMessages.getResourceBundle();
		ISourceViewer sourceViewer = getSourceViewer();
		SourceViewerConfiguration configuration = getSourceViewerConfiguration();

		Action action = new ToggleCommentAction(resourceBundle, "ToggleCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.toggle.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.ToggleCommentAction", action); //$NON-NLS-1$
		((ToggleCommentAction) action).configure(sourceViewer, configuration);

		action = new AddBlockCommentAction(resourceBundle, "AddBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.add.block.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.AddBlockComment", action); //$NON-NLS-1$
		markAsSelectionDependentAction("org.eclipse.php.ui.actions.AddBlockComment", true); //$NON-NLS-1$
		((BlockCommentAction) action).configure(sourceViewer, configuration);

		action = new RemoveBlockCommentAction(resourceBundle, "RemoveBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.remove.block.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.RemoveBlockComment", action); //$NON-NLS-1$
		markAsCursorDependentAction("org.eclipse.php.ui.actions.RemoveBlockComment", true); //$NON-NLS-1$
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
		markAsCursorDependentAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction", true); //$NON-NLS-1$

		action = new OpenDeclarationAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.open.editor"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.Open", action); //$NON-NLS-1$
		markAsCursorDependentAction("org.eclipse.php.ui.actions.Open", true); //$NON-NLS-1$

		ResourceAction resAction = new TextOperationAction(ActionMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", this, ISourceViewer.INFORMATION, true);
		resAction = new InformationDispatchAction(ActionMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", (TextOperationAction) resAction); //$NON-NLS-1$
		resAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC); //$NON-NLS-1$
		setAction("ShowPHPDoc", resAction); //$NON-NLS-1$

	}

	/**
	 * Information provider used to present focusable information shells.
	 *
	 * @since 3.2
	 */
	private static final class InformationProvider implements IInformationProvider, IInformationProviderExtension, IInformationProviderExtension2 {

		private IRegion fHoverRegion;
		private Object fHoverInfo;
		private IInformationControlCreator fControlCreator;

		InformationProvider(IRegion hoverRegion, Object hoverInfo, IInformationControlCreator controlCreator) {
			fHoverRegion = hoverRegion;
			fHoverInfo = hoverInfo;
			fControlCreator = controlCreator;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProvider#getSubject(org.eclipse.jface.text.ITextViewer, int)
		 */
		public IRegion getSubject(ITextViewer textViewer, int invocationOffset) {
			return fHoverRegion;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProvider#getInformation(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
		 */
		public String getInformation(ITextViewer textViewer, IRegion subject) {
			return fHoverInfo.toString();
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProviderExtension#getInformation2(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
		 * @since 3.2
		 */
		public Object getInformation2(ITextViewer textViewer, IRegion subject) {
			return fHoverInfo;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProviderExtension2#getInformationPresenterControlCreator()
		 */
		public IInformationControlCreator getInformationPresenterControlCreator() {
			return fControlCreator;
		}
	}

	/**
	 * This action behaves in two different ways: If there is no current text
	 * hover, the javadoc is displayed using information presenter. If there is
	 * a current text hover, it is converted into a information presenter in
	 * order to make it sticky.
	 */
	class InformationDispatchAction extends TextEditorAction {

		/** The wrapped text operation action. */
		private final TextOperationAction fTextOperationAction;

		/**
		 * Creates a dispatch action.
		 *
		 * @param resourceBundle the resource bundle
		 * @param prefix the prefix
		 * @param textOperationAction the text operation action
		 */
		public InformationDispatchAction(ResourceBundle resourceBundle, String prefix, final TextOperationAction textOperationAction) {
			super(resourceBundle, prefix, PHPStructuredEditor.this);
			if (textOperationAction == null)
				throw new IllegalArgumentException();
			fTextOperationAction = textOperationAction;
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {

			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer == null) {
				fTextOperationAction.run();
				return;
			}

			if (sourceViewer instanceof ITextViewerExtension4) {
				ITextViewerExtension4 extension4 = (ITextViewerExtension4) sourceViewer;
				if (extension4.moveFocusToWidgetToken())
					return;
			}

			if (sourceViewer instanceof ITextViewerExtension2) {
				// does a text hover exist?
				ITextHover textHover = ((ITextViewerExtension2) sourceViewer).getCurrentTextHover();
				if (textHover != null && makeTextHoverFocusable(sourceViewer, textHover))
					return;
			}

			if (sourceViewer instanceof ISourceViewerExtension3) {
				// does an annotation hover exist?
				IAnnotationHover annotationHover = ((ISourceViewerExtension3) sourceViewer).getCurrentAnnotationHover();
				if (annotationHover != null && makeAnnotationHoverFocusable(sourceViewer, annotationHover))
					return;
			}

			// otherwise, just run the action
			fTextOperationAction.run();
		}

		/**
		 * Tries to make a text hover focusable (or "sticky").
		 * 
		 * @param sourceViewer the source viewer to display the hover over
		 * @param textHover the hover to make focusable
		 * @return <code>true</code> if successful, <code>false</code> otherwise
		 * @since 3.2
		 */
		private boolean makeTextHoverFocusable(ISourceViewer sourceViewer, ITextHover textHover) {
			Point hoverEventLocation = ((ITextViewerExtension2) sourceViewer).getHoverEventLocation();
			int offset = computeOffsetAtLocation(sourceViewer, hoverEventLocation.x, hoverEventLocation.y);
			if (offset == -1)
				return false;

			try {
				IRegion hoverRegion = textHover.getHoverRegion(sourceViewer, offset);
				if (hoverRegion == null)
					return false;

				String hoverInfo = textHover.getHoverInfo(sourceViewer, hoverRegion);

				if (messageDecorator != null) {
					String decoratedMessage = messageDecorator.getDecoratedMessage(hoverInfo);
					if (decoratedMessage != null && decoratedMessage.length() > 0) {
						hoverInfo = decoratedMessage;
					}
				}

				IInformationControlCreator controlCreator = null;
				if (textHover instanceof IInformationProviderExtension2)
					controlCreator = ((IInformationProviderExtension2) textHover).getInformationPresenterControlCreator();

				IInformationProvider informationProvider = new InformationProvider(hoverRegion, hoverInfo, controlCreator);

				fInformationPresenter.setOffset(offset);
				fInformationPresenter.setAnchor(AbstractInformationControlManager.ANCHOR_BOTTOM);
				fInformationPresenter.setMargins(6, 6); // default values from AbstractInformationControlManager
				String contentType = TextUtilities.getContentType(sourceViewer.getDocument(), PHPPartitionTypes.PHP_DOC, offset, true);
				fInformationPresenter.setInformationProvider(informationProvider, contentType);
				fInformationPresenter.showInformation();

				return true;

			} catch (BadLocationException e) {
				return false;
			}
		}

		/**
		 * Tries to make an annotation hover focusable (or "sticky").
		 * 
		 * @param sourceViewer the source viewer to display the hover over
		 * @param annotationHover the hover to make focusable
		 * @return <code>true</code> if successful, <code>false</code> otherwise
		 * @since 3.2
		 */
		private boolean makeAnnotationHoverFocusable(ISourceViewer sourceViewer, IAnnotationHover annotationHover) {
			IVerticalRulerInfo info = getVerticalRuler();
			int line = info.getLineOfLastMouseButtonActivity();
			if (line == -1)
				return false;

			try {

				// compute the hover information
				Object hoverInfo;
				if (annotationHover instanceof IAnnotationHoverExtension) {
					IAnnotationHoverExtension extension = (IAnnotationHoverExtension) annotationHover;
					ILineRange hoverLineRange = extension.getHoverLineRange(sourceViewer, line);
					if (hoverLineRange == null)
						return false;
					final int maxVisibleLines = Integer.MAX_VALUE; // allow any number of lines being displayed, as we support scrolling
					hoverInfo = extension.getHoverInfo(sourceViewer, hoverLineRange, maxVisibleLines);
				} else {
					hoverInfo = annotationHover.getHoverInfo(sourceViewer, line);
				}

				// hover region: the beginning of the concerned line to place the control right over the line
				IDocument document = sourceViewer.getDocument();
				int offset = document.getLineOffset(line);
				String contentType = TextUtilities.getContentType(document, PHPPartitionTypes.PHP_DOC, offset, true);

				IInformationControlCreator controlCreator = null;

				/* 
				 * XXX: This is a hack to avoid API changes at the end of 3.2,
				 * and should be fixed for 3.3, see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=137967
				 */
				if ("org.eclipse.jface.text.source.projection.ProjectionAnnotationHover".equals(annotationHover.getClass().getName())) { //$NON-NLS-1$
					controlCreator = new IInformationControlCreator() {
						public IInformationControl createInformationControl(Shell shell) {
							int shellStyle = SWT.RESIZE | SWT.TOOL | getOrientation();
							int style = SWT.V_SCROLL | SWT.H_SCROLL;
							return new SourceViewerInformationControl(shell, shellStyle, style);
						}
					};

				} else {
					if (annotationHover instanceof IInformationProviderExtension2)
						controlCreator = ((IInformationProviderExtension2) annotationHover).getInformationPresenterControlCreator();
					else if (annotationHover instanceof IAnnotationHoverExtension)
						controlCreator = ((IAnnotationHoverExtension) annotationHover).getHoverControlCreator();
				}

				IInformationProvider informationProvider = new InformationProvider(new Region(offset, 0), hoverInfo, controlCreator);

				fInformationPresenter.setOffset(offset);
				fInformationPresenter.setAnchor(AbstractInformationControlManager.ANCHOR_RIGHT);
				fInformationPresenter.setMargins(4, 0); // AnnotationBarHoverManager sets (5,0), minus SourceViewer.GAP_SIZE_1
				fInformationPresenter.setInformationProvider(informationProvider, contentType);
				fInformationPresenter.showInformation();

				return true;

			} catch (BadLocationException e) {
				return false;
			}
		}

		// modified version from TextViewer
		private int computeOffsetAtLocation(ITextViewer textViewer, int x, int y) {

			StyledText styledText = textViewer.getTextWidget();
			IDocument document = textViewer.getDocument();

			if (document == null)
				return -1;

			try {
				int widgetOffset = styledText.getOffsetAtLocation(new Point(x, y));
				Point p = styledText.getLocationAtOffset(widgetOffset);
				if (p.x > x)
					widgetOffset--;

				if (textViewer instanceof ITextViewerExtension5) {
					ITextViewerExtension5 extension = (ITextViewerExtension5) textViewer;
					return extension.widgetOffset2ModelOffset(widgetOffset);
				} else {
					IRegion visibleRegion = textViewer.getVisibleRegion();
					return widgetOffset + visibleRegion.getOffset();
				}
			} catch (IllegalArgumentException e) {
				return -1;
			}

		}
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
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, new Separator(openGroup));
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
	
	/**
	 * Updates the specified action by calling <code>IUpdate.update</code>
	 * if applicable.
	 *
	 * @param actionId the action id
	 */
	private void updateAction(String actionId) {
		Assert.isNotNull(actionId);
		IAction action= getAction(actionId);
		if (action instanceof IUpdate) {
			((IUpdate) action).update();
		}
	}
	
	/**
	 * Updates all cursor position dependent actions.
	 */
	protected void updateCursorDependentActions() {
		if (fCursorActions != null) {
			Iterator e= fCursorActions.iterator();
			while (e.hasNext())
				updateAction((String) e.next());
		}
	}
	
	/**
	 * Marks or unmarks the given action to be updated on text cursor position changes.
	 *
	 * @param actionId the action id
	 * @param mark <code>true</code> if the action is cursor position dependent
	 */
	public void markAsCursorDependentAction(String actionId, boolean mark) {
		Assert.isNotNull(actionId);
		if (mark) {
			if (!fCursorActions.contains(actionId))
				fCursorActions.add(actionId);
		} else {
			fCursorActions.remove(actionId);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.StructuredTextEditor#handleCursorPositionChanged()
	 */
	protected void handleCursorPositionChanged() {
		updateCursorDependentActions();
		super.handleCursorPositionChanged();
	}
}
