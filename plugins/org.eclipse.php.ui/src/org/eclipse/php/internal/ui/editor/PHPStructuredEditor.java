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
package org.eclipse.php.internal.ui.editor;

import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.*;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.action.*;
import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.preference.IPreferenceStore;
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
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.core.preferences.IPreferencesPropagatorListener;
import org.eclipse.php.internal.core.preferences.PreferencesPropagatorEvent;
import org.eclipse.php.internal.core.project.properties.handlers.PhpVersionChangedHandler;
import org.eclipse.php.internal.core.resources.ExternalFileWrapper;
import org.eclipse.php.internal.core.resources.ExternalFilesRegistry;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.PHPUiConstants;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.actions.*;
import org.eclipse.php.internal.ui.editor.hover.SourceViewerInformationControl;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration.DoubleClickListener;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.text.DocumentCharacterIterator;
import org.eclipse.php.internal.ui.text.PHPWordIterator;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorator;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.*;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.ui.editors.text.EditorsUI;
import org.eclipse.ui.editors.text.TextFileDocumentProvider;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.texteditor.*;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.sse.ui.internal.SSEUIPlugin;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.actions.ActionDefinitionIds;
import org.eclipse.wst.sse.ui.internal.contentoutline.ConfigurableContentOutlinePage;
import org.eclipse.wst.sse.ui.internal.projection.IStructuredTextFoldingProvider;

public class PHPStructuredEditor extends StructuredTextEditor {

	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN = "org.eclipse.php.ui.actions.Open"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN_FUNCTIONS_MANUAL_ACTION = "org.eclipse.php.ui.actions.OpenFunctionsManualAction"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_UNCOMMENT = "org.eclipse.php.ui.actions.Uncomment"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_COMMENT = "org.eclipse.php.ui.actions.Comment"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_REMOVE_BLOCK_COMMENT = "org.eclipse.php.ui.actions.RemoveBlockComment"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_PHP_UI_ACTIONS_ADD_BLOCK_COMMENT = "org.eclipse.php.ui.actions.AddBlockComment"; //$NON-NLS-1$

	protected PHPPairMatcher fBracketMatcher = new PHPPairMatcher(BRACKETS);
	private CompositeActionGroup fContextMenuGroup;
	private CompositeActionGroup fActionGroups;
	/**Indicates whether the structure editor is displaying an external file*/
	protected boolean isExternal;

	/**
	 * This action behaves in two different ways: If there is no current text hover, the javadoc is displayed using
	 * information presenter. If there is a current text hover, it is converted into a information presenter in order to
	 * make it sticky.
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
		public InformationDispatchAction(final ResourceBundle resourceBundle, final String prefix, final TextOperationAction textOperationAction) {
			super(resourceBundle, prefix, PHPStructuredEditor.this);
			if (textOperationAction == null)
				throw new IllegalArgumentException();
			fTextOperationAction = textOperationAction;
		}

		// modified version from TextViewer
		private int computeOffsetAtLocation(final ITextViewer textViewer, final int x, final int y) {

			final StyledText styledText = textViewer.getTextWidget();
			final IDocument document = textViewer.getDocument();

			if (document == null)
				return -1;

			try {
				int widgetOffset = styledText.getOffsetAtLocation(new Point(x, y));
				final Point p = styledText.getLocationAtOffset(widgetOffset);
				if (p.x > x)
					widgetOffset--;

				if (textViewer instanceof ITextViewerExtension5) {
					final ITextViewerExtension5 extension = (ITextViewerExtension5) textViewer;
					return extension.widgetOffset2ModelOffset(widgetOffset);
				}
				final IRegion visibleRegion = textViewer.getVisibleRegion();
				return widgetOffset + visibleRegion.getOffset();
			} catch (final IllegalArgumentException e) {
				return -1;
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
		private boolean makeAnnotationHoverFocusable(final ISourceViewer sourceViewer, final IAnnotationHover annotationHover) {
			final IVerticalRulerInfo info = getVerticalRuler();
			final int line = info.getLineOfLastMouseButtonActivity();
			if (line == -1)
				return false;

			try {

				// compute the hover information
				Object hoverInfo;
				if (annotationHover instanceof IAnnotationHoverExtension) {
					final IAnnotationHoverExtension extension = (IAnnotationHoverExtension) annotationHover;
					final ILineRange hoverLineRange = extension.getHoverLineRange(sourceViewer, line);
					if (hoverLineRange == null)
						return false;
					final int maxVisibleLines = Integer.MAX_VALUE; // allow any number of lines being displayed, as we
					// support scrolling
					hoverInfo = extension.getHoverInfo(sourceViewer, hoverLineRange, maxVisibleLines);
				} else
					hoverInfo = annotationHover.getHoverInfo(sourceViewer, line);

				// hover region: the beginning of the concerned line to place the control right over the line
				final IDocument document = sourceViewer.getDocument();
				final int offset = document.getLineOffset(line);
				final String contentType = TextUtilities.getContentType(document, PHPPartitionTypes.PHP_DOC, offset, true);

				IInformationControlCreator controlCreator = null;

				/*
				 * XXX: This is a hack to avoid API changes at the end of 3.2,
				 */
				if ("org.eclipse.jface.text.source.projection.ProjectionAnnotationHover".equals(annotationHover.getClass().getName())) //$NON-NLS-1$
					controlCreator = new IInformationControlCreator() {
						public IInformationControl createInformationControl(final Shell shell) {
							final int shellStyle = SWT.RESIZE | SWT.TOOL | getOrientation();
							final int style = SWT.V_SCROLL | SWT.H_SCROLL;
							return new SourceViewerInformationControl(shell, shellStyle, style);
						}
					};
				else if (annotationHover instanceof IInformationProviderExtension2)
					controlCreator = ((IInformationProviderExtension2) annotationHover).getInformationPresenterControlCreator();
				else if (annotationHover instanceof IAnnotationHoverExtension)
					controlCreator = ((IAnnotationHoverExtension) annotationHover).getHoverControlCreator();

				final IInformationProvider informationProvider = new InformationProvider(new Region(offset, 0), hoverInfo, controlCreator);

				fInformationPresenter.setOffset(offset);
				fInformationPresenter.setAnchor(AbstractInformationControlManager.ANCHOR_RIGHT);
				fInformationPresenter.setMargins(4, 0); // AnnotationBarHoverManager sets (5,0), minus
				// SourceViewer.GAP_SIZE_1
				fInformationPresenter.setInformationProvider(informationProvider, contentType);
				fInformationPresenter.showInformation();

				return true;

			} catch (final BadLocationException e) {
				return false;
			}
		}

		/**
		 * Tries to make a text hover focusable (or "sticky").
		 *
		 * @param sourceViewer the source viewer to display the hover over
		 * @param textHover the hover to make focusable
		 * @return <code>true</code> if successful, <code>false</code> otherwise
		 * @since 3.2
		 */
		private boolean makeTextHoverFocusable(final ISourceViewer sourceViewer, final ITextHover textHover) {
			final Point hoverEventLocation = ((ITextViewerExtension2) sourceViewer).getHoverEventLocation();
			final int offset = computeOffsetAtLocation(sourceViewer, hoverEventLocation.x, hoverEventLocation.y);
			if (offset == -1)
				return false;

			try {
				final IRegion hoverRegion = textHover.getHoverRegion(sourceViewer, offset);
				if (hoverRegion == null)
					return false;

				String hoverInfo = textHover.getHoverInfo(sourceViewer, hoverRegion);

				if (textHover instanceof IPHPTextHover) {
					final IHoverMessageDecorator decorator = ((IPHPTextHover) textHover).getMessageDecorator();
					if (decorator != null) {
						final String decoratedMessage = decorator.getDecoratedMessage(hoverInfo);
						if (decoratedMessage != null && decoratedMessage.length() > 0)
							hoverInfo = decoratedMessage;
					}
				}

				IInformationControlCreator controlCreator = null;
				if (textHover instanceof IInformationProviderExtension2)
					controlCreator = ((IInformationProviderExtension2) textHover).getInformationPresenterControlCreator();

				final IInformationProvider informationProvider = new InformationProvider(hoverRegion, hoverInfo, controlCreator);

				fInformationPresenter.setOffset(offset);
				fInformationPresenter.setAnchor(AbstractInformationControlManager.ANCHOR_BOTTOM);
				fInformationPresenter.setMargins(6, 6); // default values from AbstractInformationControlManager
				final String contentType = TextUtilities.getContentType(sourceViewer.getDocument(), PHPPartitionTypes.PHP_DOC, offset, true);
				fInformationPresenter.setInformationProvider(informationProvider, contentType);
				fInformationPresenter.showInformation();

				return true;

			} catch (final BadLocationException e) {
				return false;
			}
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {

			final ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer == null) {
				fTextOperationAction.run();
				return;
			}

			if (sourceViewer instanceof ITextViewerExtension4) {
				final ITextViewerExtension4 extension4 = (ITextViewerExtension4) sourceViewer;
				if (extension4.moveFocusToWidgetToken())
					return;
			}

			if (sourceViewer instanceof ITextViewerExtension2) {
				// does a text hover exist?
				final ITextHover textHover = ((ITextViewerExtension2) sourceViewer).getCurrentTextHover();
				if (textHover != null && makeTextHoverFocusable(sourceViewer, textHover))
					return;
			}

			if (sourceViewer instanceof ISourceViewerExtension3) {
				// does an annotation hover exist?
				final IAnnotationHover annotationHover = ((ISourceViewerExtension3) sourceViewer).getCurrentAnnotationHover();
				if (annotationHover != null && makeAnnotationHoverFocusable(sourceViewer, annotationHover))
					return;
			}

			// otherwise, just run the action
			fTextOperationAction.run();
		}
	}

	/**
	 * Information provider used to present focusable information shells.
	 *
	 * @since 3.2
	 */
	private static final class InformationProvider implements IInformationProvider, IInformationProviderExtension, IInformationProviderExtension2 {

		private IInformationControlCreator fControlCreator;
		private Object fHoverInfo;
		private IRegion fHoverRegion;

		InformationProvider(final IRegion hoverRegion, final Object hoverInfo, final IInformationControlCreator controlCreator) {
			fHoverRegion = hoverRegion;
			fHoverInfo = hoverInfo;
			fControlCreator = controlCreator;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProvider#getInformation(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
		 */
		public String getInformation(final ITextViewer textViewer, final IRegion subject) {
			return fHoverInfo.toString();
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProviderExtension#getInformation2(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
		 * @since 3.2
		 */
		public Object getInformation2(final ITextViewer textViewer, final IRegion subject) {
			return fHoverInfo;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProviderExtension2#getInformationPresenterControlCreator()
		 */
		public IInformationControlCreator getInformationPresenterControlCreator() {
			return fControlCreator;
		}

		/*
		 * @see org.eclipse.jface.text.information.IInformationProvider#getSubject(org.eclipse.jface.text.ITextViewer, int)
		 */
		public IRegion getSubject(final ITextViewer textViewer, final int invocationOffset) {
			return fHoverRegion;
		}
	}

	private IPreferencesPropagatorListener phpVersionListener = new IPreferencesPropagatorListener() {
		public void preferencesEventOccured(PreferencesPropagatorEvent event) {
			try {
				// get the structured document and go over its regions
				// in case of PhpScriptRegion reparse the region text
				IDocument doc = getDocumentProvider().getDocument(getEditorInput());
				if (doc instanceof IStructuredDocument) {
					IStructuredDocumentRegion[] sdRegions = ((IStructuredDocument) doc).getStructuredDocumentRegions();
					for (IStructuredDocumentRegion element : sdRegions) {
						Iterator regionsIt = element.getRegions().iterator();
						reparseRegion(doc, regionsIt, element.getStartOffset());
					}
				}
			} catch (BadLocationException e) {
			}
		}

		public IProject getProject() {
			IFile file = getFile();
			if (file == null)
				return null;
			return file.getProject();
		}
	};

	/**
	 * iterate over regions in case of PhpScriptRegion reparse the region. in case of region contaioner iterate over the
	 * container regions.
	 *
	 * @param doc structured document
	 * @param regionsIt regions iterator
	 * @param offset the container region start offset
	 * @throws BadLocationException
	 */
	private void reparseRegion(IDocument doc, Iterator regionsIt, int offset) throws BadLocationException {
		while (regionsIt.hasNext()) {
			ITextRegion region = (ITextRegion) regionsIt.next();
			if (region instanceof ITextRegionContainer) {
				reparseRegion(doc, ((ITextRegionContainer) region).getRegions().iterator(), offset + region.getStart());
			}
			if (region instanceof PhpScriptRegion) {
				final PhpScriptRegion phpRegion = (PhpScriptRegion) region;
				phpRegion.completeReparse(doc, offset + region.getStart(), region.getLength());
			}
		}
	}

	/** Cursor dependent actions. */
	private final List fCursorActions = new ArrayList(5);

	/** The information presenter. */
	protected InformationPresenter fInformationPresenter;

	public PHPStructuredEditor() {
		/**
		 * Bug fix: #158170 Set WST's folding support enablement according to PHP editor folding support status. Must be
		 * removed, when WTP releases folding support
		 */
		boolean foldingEnabled = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		SSEUIPlugin.getDefault().getPreferenceStore().setValue(IStructuredTextFoldingProvider.FOLDING_ENABLED, foldingEnabled);
	}

	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
	}

	protected void initializeEditor() {
		super.initializeEditor();

		setPreferenceStore(createCombinedPreferenceStore());
	}

	/**
	 * Create a preference store that combines the source editor preferences
	 * with the base editor's preferences.
	 *
	 * @return IPreferenceStore
	 */
	private IPreferenceStore createCombinedPreferenceStore() {
		IPreferenceStore sseEditorPrefs = SSEUIPlugin.getDefault().getPreferenceStore();
		IPreferenceStore baseEditorPrefs = EditorsUI.getPreferenceStore();
		IPreferenceStore phpEditorPrefs = PHPUiPlugin.getDefault().getPreferenceStore();
		return new ChainedPreferenceStore(new IPreferenceStore[] { sseEditorPrefs, baseEditorPrefs, phpEditorPrefs });
	}

	protected void setDocumentProvider(IEditorInput input) {
		if (input instanceof FileStoreEditorInput || input instanceof NonExistingPHPFileEditorInput) {
			setDocumentProvider(new TextFileDocumentProvider());
		} else {
			super.setDocumentProvider(input);
		}
	}

	public void dispose() {
		PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);
		super.dispose();
	}

	/*
	 * @see AbstractTextEditor#editorContextMenuAboutToShow(IMenuManager)
	 */
	public void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);

		if (fContextMenuGroup != null) {
			ActionContext context = new ActionContext(getSelectionProvider().getSelection());
			fContextMenuGroup.setContext(context);
			fContextMenuGroup.fillContextMenu(menu);
			fContextMenuGroup.setContext(null);
		}
	}

	protected void addContextMenuActions(final IMenuManager menu) {
		super.addContextMenuActions(menu);

		if (getSourceViewer().isEditable()) {
			final String label = PHPUIMessages.getString("PHPStructuredEditor_Source");
			final MenuManager subMenu = new MenuManager(label, "org.eclipse.php.ui.source.menu"); //$NON-NLS-1$
			subMenu.add(new GroupMarker("editGroup")); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.ToggleCommentAction"); //$NON-NLS-1$
			addAction(subMenu, PHPStructuredEditor.ORG_ECLIPSE_PHP_UI_ACTIONS_ADD_BLOCK_COMMENT);
			addAction(subMenu, PHPStructuredEditor.ORG_ECLIPSE_PHP_UI_ACTIONS_REMOVE_BLOCK_COMMENT);
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, subMenu);

			final String openGroup = "group.open"; //$NON-NLS-1$
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, new Separator(openGroup));
			IAction action = getAction(PHPStructuredEditor.ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN);
			if (action != null)
				menu.appendToGroup(openGroup, action);
			action = getAction(PHPStructuredEditor.ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN_FUNCTIONS_MANUAL_ACTION);
			if (action != null)
				menu.appendToGroup(openGroup, action);
			action = getAction(IPHPEditorActionDefinitionIds.OPEN_DECLARATION);
			if (action != null)
				menu.appendToGroup(openGroup, action);

		}
	}

	protected void createNavigationActions() {
		super.createNavigationActions();
		final StyledText textWidget = getSourceViewer().getTextWidget();

		IAction action = new SmartLineStartAction(textWidget, false);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.LINE_START);
		setAction(ITextEditorActionDefinitionIds.LINE_START, action);

		action = new SmartLineStartAction(textWidget, true);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SELECT_LINE_START);
		setAction(ITextEditorActionDefinitionIds.SELECT_LINE_START, action);

		action = new SmartLineEndAction(textWidget, false);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.LINE_END);
		setAction(ITextEditorActionDefinitionIds.LINE_END, action);

		action = new SmartLineEndAction(textWidget, true);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SELECT_LINE_END);
		setAction(ITextEditorActionDefinitionIds.SELECT_LINE_END, action);

		action = new NavigatePreviousSubWordAction();
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.WORD_PREVIOUS);
		setAction(ITextEditorActionDefinitionIds.WORD_PREVIOUS, action);
		textWidget.setKeyBinding(SWT.CTRL | SWT.ARROW_LEFT, SWT.NULL);

		action = new NavigateNextSubWordAction();
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.WORD_NEXT);
		setAction(ITextEditorActionDefinitionIds.WORD_NEXT, action);
		textWidget.setKeyBinding(SWT.CTRL | SWT.ARROW_RIGHT, SWT.NULL);

		action = new SelectPreviousSubWordAction();
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SELECT_WORD_PREVIOUS);
		setAction(ITextEditorActionDefinitionIds.SELECT_WORD_PREVIOUS, action);
		textWidget.setKeyBinding(SWT.CTRL | SWT.SHIFT | SWT.ARROW_LEFT, SWT.NULL);

		action = new SelectNextSubWordAction();
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.SELECT_WORD_NEXT);
		setAction(ITextEditorActionDefinitionIds.SELECT_WORD_NEXT, action);
		textWidget.setKeyBinding(SWT.CTRL | SWT.SHIFT | SWT.ARROW_RIGHT, SWT.NULL);
	}

	/**
	 * This action implements smart home. (Taken from JDT implementation) Instead of going to the start of a line it
	 * does the following: - if smart home/end is enabled and the caret is after the line's first non-whitespace then
	 * the caret is moved directly before it, taking PHPDoc and multi-line comments into account. - if the caret is
	 * before the line's first non-whitespace the caret is moved to the beginning of the line - if the caret is at the
	 * beginning of the line see first case.
	 */
	protected class SmartLineStartAction extends LineStartAction {

		private boolean fDoSelect;

		/**
		 * Creates a new smart line start action
		 *
		 * @param textWidget the styled text widget
		 * @param doSelect a boolean flag which tells if the text up to the beginning of the line should be selected
		 */
		public SmartLineStartAction(final StyledText textWidget, final boolean doSelect) {
			super(textWidget, doSelect);
			fDoSelect = doSelect;
		}

		/*
		 * @see org.eclipse.ui.texteditor.AbstractTextEditor.LineStartAction#getLineStartPosition(java.lang.String, int, java.lang.String)
		 */
		protected int getLineStartPosition(final IDocument document, final String line, final int length, final int offset) {

			String type = IDocument.DEFAULT_CONTENT_TYPE;
			try {
				type = TextUtilities.getContentType(document, PHPPartitionTypes.PHP_DEFAULT, offset, true);
			} catch (BadLocationException exception) {
				// Should not happen
			}

			int index = super.getLineStartPosition(document, line, length, offset);
			if (type.equals(PHPPartitionTypes.PHP_DOC) || type.equals(PHPPartitionTypes.PHP_MULTI_LINE_COMMENT)) {
				if (index < length - 1 && line.charAt(index) == '*' && line.charAt(index + 1) != '/') {
					do {
						++index;
					} while (index < length && Character.isWhitespace(line.charAt(index)));
				}
			} else {
				if (index < length - 1 && line.charAt(index) == '/' && line.charAt(index + 1) == '/') {
					index++;
					do {
						++index;
					} while (index < length && Character.isWhitespace(line.charAt(index)));
				}
			}
			return index;
		}

		private IPreferenceStore getPreferenceStore() {
			return PHPUiPlugin.getDefault().getPreferenceStore();
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {
			boolean isSmartHomeEndEnabled = true;
			IPreferenceStore store = getPreferenceStore();
			if (store != null) {
				isSmartHomeEndEnabled = store.getBoolean(PreferenceConstants.USE_SMART_HOME_END);
			}

			ISourceViewer fSourceViewer = getSourceViewer();
			StyledText st = fSourceViewer.getTextWidget();
			if (st == null || st.isDisposed())
				return;

			int caretOffset = st.getCaretOffset();
			int lineNumber = st.getLineAtOffset(caretOffset);
			int lineOffset = st.getOffsetAtLine(lineNumber);

			int lineLength;
			int caretOffsetInDocument;
			final IDocument document = fSourceViewer.getDocument();

			try {
				caretOffsetInDocument = widgetOffset2ModelOffset(fSourceViewer, caretOffset);
				lineLength = document.getLineInformationOfOffset(caretOffsetInDocument).getLength();
			} catch (BadLocationException ex) {
				return;
			}

			String line = ""; //$NON-NLS-1$
			if (lineLength > 0) {
				int end = lineOffset + lineLength - 1;
				end = Math.min(end, st.getCharCount() - 1);
				line = st.getText(lineOffset, end);
			}

			// Compute the line start offset
			int index = getLineStartPosition(document, line, lineLength, caretOffsetInDocument);

			// Remember current selection
			Point oldSelection = st.getSelection();

			// Compute new caret position
			int newCaretOffset = -1;
			if (isSmartHomeEndEnabled) {

				if (caretOffset - lineOffset == index)
					// to beginning of line
					newCaretOffset = lineOffset;
				else
					// to beginning of text
					newCaretOffset = lineOffset + index;

			} else {

				if (caretOffset > lineOffset)
					// to beginning of line
					newCaretOffset = lineOffset;
			}

			if (newCaretOffset == -1)
				newCaretOffset = caretOffset;
			else
				st.setCaretOffset(newCaretOffset);

			if (fDoSelect) {
				if (caretOffset < oldSelection.y)
					st.setSelection(oldSelection.y, newCaretOffset);
				else
					st.setSelection(oldSelection.x, newCaretOffset);
			} else
				st.setSelection(newCaretOffset);

			fireSelectionChanged(oldSelection);
		}
	}

	/**
	 * This action implements smart end. (Taken from org.eclipse.ui.texteditor.AbstractTextEditor.LineEndAction) Instead
	 * of going to the end of a line it does the following: - if smart home/end is enabled and the caret is before the
	 * line's last non-whitespace and then the caret is moved directly after it - if the caret is after last
	 * non-whitespace the caret is moved at the end of the line - if the caret is at the end of the line the caret is
	 * moved directly after the line's last non-whitespace character
	 */
	protected class SmartLineEndAction extends TextNavigationAction {

		/** boolean flag which tells if the text up to the line end should be selected. */
		private boolean fDoSelect;

		/**
		 * Create a new line end action.
		 *
		 * @param textWidget the styled text widget
		 * @param doSelect a boolean flag which tells if the text up to the line end should be selected
		 */
		public SmartLineEndAction(StyledText textWidget, boolean doSelect) {
			super(textWidget, ST.LINE_END);
			fDoSelect = doSelect;
		}

		private IPreferenceStore getPreferenceStore() {
			return PHPUiPlugin.getDefault().getPreferenceStore();
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {
			boolean isSmartHomeEndEnabled = true;
			IPreferenceStore store = getPreferenceStore();
			if (store != null) {
				isSmartHomeEndEnabled = store.getBoolean(PreferenceConstants.USE_SMART_HOME_END);
			}

			ISourceViewer fSourceViewer = getSourceViewer();
			StyledText st = fSourceViewer.getTextWidget();
			if (st == null || st.isDisposed())
				return;
			int caretOffset = st.getCaretOffset();
			int lineNumber = st.getLineAtOffset(caretOffset);
			int lineOffset = st.getOffsetAtLine(lineNumber);

			int lineLength;
			try {
				int caretOffsetInDocument = widgetOffset2ModelOffset(fSourceViewer, caretOffset);
				lineLength = fSourceViewer.getDocument().getLineInformationOfOffset(caretOffsetInDocument).getLength();
			} catch (BadLocationException ex) {
				return;
			}
			int lineEndOffset = lineOffset + lineLength;

			int delta = lineEndOffset - st.getCharCount();
			if (delta > 0) {
				lineEndOffset -= delta;
				lineLength -= delta;
			}

			String line = ""; //$NON-NLS-1$
			if (lineLength > 0)
				line = st.getText(lineOffset, lineEndOffset - 1);
			int i = lineLength - 1;
			while (i > -1 && Character.isWhitespace(line.charAt(i))) {
				i--;
			}
			i++;

			// Remember current selection
			Point oldSelection = st.getSelection();

			// Compute new caret position
			int newCaretOffset = -1;

			if (isSmartHomeEndEnabled) {

				if (caretOffset - lineOffset == i)
					// to end of line
					newCaretOffset = lineEndOffset;
				else
					// to end of text
					newCaretOffset = lineOffset + i;

			} else {

				if (caretOffset < lineEndOffset)
					// to end of line
					newCaretOffset = lineEndOffset;

			}

			if (newCaretOffset == -1)
				newCaretOffset = caretOffset;
			else
				st.setCaretOffset(newCaretOffset);

			st.setCaretOffset(newCaretOffset);
			if (fDoSelect) {
				if (caretOffset < oldSelection.y)
					st.setSelection(oldSelection.y, newCaretOffset);
				else
					st.setSelection(oldSelection.x, newCaretOffset);
			} else
				st.setSelection(newCaretOffset);

			fireSelectionChanged(oldSelection);
		}
	}

	/**
	 * Text navigation action to navigate to the next sub-word.
	 */
	protected abstract class NextSubWordAction extends TextNavigationAction {

		protected PHPWordIterator fIterator = new PHPWordIterator();

		/**
		 * Creates a new next sub-word action.
		 *
		 * @param code Action code for the default operation. Must be an action code from
		 * @see org.eclipse.swt.custom.ST.
		 */
		protected NextSubWordAction(int code) {
			super(getSourceViewer().getTextWidget(), code);
		}

		private IPreferenceStore getPreferenceStore() {
			return PHPUiPlugin.getDefault().getPreferenceStore();
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {
			// Check whether the feature is enabled in Preferences
			final IPreferenceStore store = getPreferenceStore();
			if (!store.getBoolean(PreferenceConstants.USE_SUB_WORD_NAVIGATION)) {
				super.run();
				return;
			}

			final ISourceViewer viewer = getSourceViewer();
			final IDocument document = viewer.getDocument();
			fIterator.setText((CharacterIterator) new DocumentCharacterIterator(document));
			int position = widgetOffset2ModelOffset(viewer, viewer.getTextWidget().getCaretOffset());
			if (position == -1) {
				return;
			}

			int next = findNextPosition(position);
			if (next != BreakIterator.DONE) {
				setCaretPosition(next);
				getTextWidget().showSelection();
				fireSelectionChanged();
			}

		}

		/**
		 * Finds the next position after the given position.
		 *
		 * @param position the current position
		 * @return the next position
		 */
		protected int findNextPosition(int position) {
			ISourceViewer viewer = getSourceViewer();
			int widget = -1;
			while (position != BreakIterator.DONE && widget == -1) { // TODO: optimize
				position = fIterator.following(position);
				if (position != BreakIterator.DONE) {
					widget = modelOffset2WidgetOffset(viewer, position);
				}
			}
			return position;
		}

		/**
		 * Sets the caret position to the sub-word boundary given with <code>position</code>.
		 *
		 * @param position Position where the action should move the caret
		 */
		protected abstract void setCaretPosition(int position);
	}

	/**
	 * Text operation action to select the next sub-word.
	 */
	protected class SelectNextSubWordAction extends NextSubWordAction {

		/**
		 * Creates a new select next sub-word action.
		 */
		public SelectNextSubWordAction() {
			super(ST.SELECT_WORD_NEXT);
		}

		/*
		 * @see NextSubWordAction#setCaretPosition(int)
		 */
		protected void setCaretPosition(final int position) {
			final ISourceViewer viewer = getSourceViewer();

			final StyledText text = viewer.getTextWidget();
			if (text != null && !text.isDisposed()) {

				final Point selection = text.getSelection();
				final int caret = text.getCaretOffset();
				final int offset = modelOffset2WidgetOffset(viewer, position);

				if (caret == selection.x) {
					text.setSelectionRange(selection.y, offset - selection.y);
				} else {
					text.setSelectionRange(selection.x, offset - selection.x);
				}
			}
		}
	}

	/**
	 * Text navigation action to navigate to the previous sub-word.
	 */
	protected abstract class PreviousSubWordAction extends TextNavigationAction {

		protected PHPWordIterator fIterator = new PHPWordIterator();

		/**
		 * Creates a new previous sub-word action.
		 *
		 * @param code Action code for the default operation. Must be an action code from
		 * @see org.eclipse.swt.custom.ST.
		 */
		protected PreviousSubWordAction(final int code) {
			super(getSourceViewer().getTextWidget(), code);
		}

		private IPreferenceStore getPreferenceStore() {
			return PHPUiPlugin.getDefault().getPreferenceStore();
		}

		/*
		 * @see org.eclipse.jface.action.IAction#run()
		 */
		public void run() {
			// Check whether we are in a java code partition and the preference is enabled
			final IPreferenceStore store = getPreferenceStore();
			if (!store.getBoolean(PreferenceConstants.USE_SUB_WORD_NAVIGATION)) {
				super.run();
				return;
			}

			final ISourceViewer viewer = getSourceViewer();
			final IDocument document = viewer.getDocument();
			fIterator.setText((CharacterIterator) new DocumentCharacterIterator(document));
			int position = widgetOffset2ModelOffset(viewer, viewer.getTextWidget().getCaretOffset());
			if (position == -1) {
				return;
			}

			int previous = findPreviousPosition(position);
			if (previous != BreakIterator.DONE) {
				setCaretPosition(previous);
				getTextWidget().showSelection();
				fireSelectionChanged();
			}

		}

		/**
		 * Finds the previous position before the given position.
		 *
		 * @param position the current position
		 * @return the previous position
		 */
		protected int findPreviousPosition(int position) {
			ISourceViewer viewer = getSourceViewer();
			int widget = -1;
			while (position != BreakIterator.DONE && widget == -1) { // TODO: optimize
				position = fIterator.preceding(position);
				if (position != BreakIterator.DONE) {
					widget = modelOffset2WidgetOffset(viewer, position);
				}
			}
			return position;
		}

		/**
		 * Sets the caret position to the sub-word boundary given with <code>position</code>.
		 *
		 * @param position Position where the action should move the caret
		 */
		protected abstract void setCaretPosition(int position);
	}

	/**
	 * Text navigation action to navigate to the next sub-word.
	 *
	 * @since 3.0
	 */
	protected class NavigateNextSubWordAction extends NextSubWordAction {

		/**
		 * Creates a new navigate next sub-word action.
		 */
		public NavigateNextSubWordAction() {
			super(ST.WORD_NEXT);
		}

		/*
		 * @see NextSubWordAction#setCaretPosition(int)
		 */
		protected void setCaretPosition(final int position) {
			getTextWidget().setCaretOffset(modelOffset2WidgetOffset(getSourceViewer(), position));
		}
	}

	/**
	 * Text operation action to select the previous sub-word.
	 *
	 * @since 3.0
	 */
	protected class SelectPreviousSubWordAction extends PreviousSubWordAction {

		/**
		 * Creates a new select previous sub-word action.
		 */
		public SelectPreviousSubWordAction() {
			super(ST.SELECT_WORD_PREVIOUS);
		}

		/*
		 * @see PreviousSubWordAction#setCaretPosition(int)
		 */
		protected void setCaretPosition(final int position) {
			final ISourceViewer viewer = getSourceViewer();

			final StyledText text = viewer.getTextWidget();
			if (text != null && !text.isDisposed()) {

				final Point selection = text.getSelection();
				final int caret = text.getCaretOffset();
				final int offset = modelOffset2WidgetOffset(viewer, position);

				if (caret == selection.x) {
					text.setSelectionRange(selection.y, offset - selection.y);
				} else {
					text.setSelectionRange(selection.x, offset - selection.x);
				}
			}
		}
	}

	/**
	 * Text navigation action to navigate to the previous sub-word.
	 *
	 * @since 3.0
	 */
	protected class NavigatePreviousSubWordAction extends PreviousSubWordAction {

		/**
		 * Creates a new navigate previous sub-word action.
		 */
		public NavigatePreviousSubWordAction() {
			super(ST.WORD_PREVIOUS);
		}

		/*
		 * @see PreviousSubWordAction#setCaretPosition(int)
		 */
		protected void setCaretPosition(final int position) {
			getTextWidget().setCaretOffset(modelOffset2WidgetOffset(getSourceViewer(), position));
		}
	}

	protected void createActions() {
		super.createActions();

		final ResourceBundle resourceBundle = PHPUIMessages.getResourceBundle();
		final ISourceViewer sourceViewer = getSourceViewer();
		final SourceViewerConfiguration configuration = getSourceViewerConfiguration();

		Action action = new ToggleCommentAction(resourceBundle, "ToggleCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.toggle.comment"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.ToggleCommentAction", action); //$NON-NLS-1$
		((ToggleCommentAction) action).configure(sourceViewer, configuration);

		action = new GotoMatchingBracketAction(this);
		action.setActionDefinitionId(IPHPEditorActionDefinitionIds.GOTO_MATCHING_BRACKET);
		setAction(GotoMatchingBracketAction.GOTO_MATCHING_BRACKET, action);

		action = new AddBlockCommentActionDelegate(resourceBundle, "AddBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.add.block.comment"); //$NON-NLS-1$
		setAction(ORG_ECLIPSE_PHP_UI_ACTIONS_ADD_BLOCK_COMMENT, action);
		markAsSelectionDependentAction(ORG_ECLIPSE_PHP_UI_ACTIONS_ADD_BLOCK_COMMENT, true);
		((BlockCommentAction) action).configure(sourceViewer, configuration);

		action = new RemoveBlockCommentActionDelegate(resourceBundle, "RemoveBlockCommentAction_", this); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.remove.block.comment"); //$NON-NLS-1$
		setAction(ORG_ECLIPSE_PHP_UI_ACTIONS_REMOVE_BLOCK_COMMENT, action);
		markAsCursorDependentAction(ORG_ECLIPSE_PHP_UI_ACTIONS_REMOVE_BLOCK_COMMENT, true);
		((BlockCommentAction) action).configure(sourceViewer, configuration);

		action = new TextOperationAction(resourceBundle, "CommentAction_", this, ITextOperationTarget.PREFIX); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.comment"); //$NON-NLS-1$
		setAction(ORG_ECLIPSE_PHP_UI_ACTIONS_COMMENT, action);
		markAsStateDependentAction(ORG_ECLIPSE_PHP_UI_ACTIONS_COMMENT, true);

		action = new TextOperationAction(resourceBundle, "UncommentAction_", this, ITextOperationTarget.PREFIX); //$NON-NLS-1$
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.uncomment"); //$NON-NLS-1$
		setAction(ORG_ECLIPSE_PHP_UI_ACTIONS_UNCOMMENT, action);
		markAsStateDependentAction(ORG_ECLIPSE_PHP_UI_ACTIONS_UNCOMMENT, true);

		action = new OpenFunctionsManualAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.OpenFunctionsManualAction"); //$NON-NLS-1$
		setAction(ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN_FUNCTIONS_MANUAL_ACTION, action);
		markAsCursorDependentAction(ORG_ECLIPSE_PHP_UI_ACTIONS_OPEN_FUNCTIONS_MANUAL_ACTION, true);

		action = new OpenDeclarationAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.OpenDeclaration"); //$NON-NLS-1$
		setAction(IPHPEditorActionDefinitionIds.OPEN_DECLARATION, action);
		markAsCursorDependentAction(IPHPEditorActionDefinitionIds.OPEN_DECLARATION, true);

		ResourceAction resAction = new TextOperationAction(PHPUIMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", this, ISourceViewer.INFORMATION, true); //$NON-NLS-1$
		resAction = new InformationDispatchAction(PHPUIMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", (TextOperationAction) resAction); //$NON-NLS-1$
		resAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		setAction("ShowPHPDoc", resAction); //$NON-NLS-1$

		resAction = new TextOperationAction(PHPUIMessages.getBundleForConstructedKeys(), "ShowOutline.", this, ISourceViewer.QUICK_ASSIST); //$NON-NLS-1$
		resAction.setActionDefinitionId("org.eclipse.pdt.ui.edit.text.php.show.outline"); //$NON-NLS-1$
		setAction(IPHPEditorActionDefinitionIds.SHOW_OUTLINE, resAction);

		if (isExternal) {
			// Override the way breakpoints are set on external files.
			action = new ToggleExternalBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.TOGGLE_BREAKPOINTS, action);
			// StructuredTextEditor Action - manage breakpoints
			action = new ManageExternalBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.MANAGE_BREAKPOINTS, action);
			// StructuredTextEditor Action - edit breakpoints
			action = new EditExternalBreakpointAction(this, getVerticalRuler());
			setAction(ActionDefinitionIds.EDIT_BREAKPOINTS, action);

			// Set the ruler double-click behavior.
			setAction(ITextEditorActionConstants.RULER_DOUBLE_CLICK, new ToggleExternalBreakpointAction(this, getVerticalRuler(), null));
		}

		ActionGroup rg = new RefactorActionGroup(this, ITextEditorActionConstants.GROUP_EDIT);
		// We have to keep the context menu group separate to have better control over positioning
		fContextMenuGroup = new CompositeActionGroup(new ActionGroup[] { rg });

		fActionGroups = new CompositeActionGroup(new ActionGroup[] { rg });

	}

	/**
	 * Returns the standard action group of this editor.
	 *
	 * @return returns this editor's standard action group
	 */
	public ActionGroup getActionGroup() {
		return fActionGroups;
	}

	/**
	 * Jumps to the matching bracket.
	 */
	public void gotoMatchingBracket() {

		ISourceViewer sourceViewer = getSourceViewer();
		IDocument document = sourceViewer.getDocument();
		if (document == null)
			return;

		IRegion selection = getSignedSelection(sourceViewer);

		int selectionLength = Math.abs(selection.getLength());
		if (selectionLength > 1) {
			setStatusLineErrorMessage(PHPUIMessages.getString("GotoMatchingBracket_error_invalidSelection"));
			sourceViewer.getTextWidget().getDisplay().beep();
			return;
		}

		// #26314
		int sourceCaretOffset = selection.getOffset() + selection.getLength();
		if (isSurroundedByBrackets(document, sourceCaretOffset))
			sourceCaretOffset -= selection.getLength();

		IRegion region = fBracketMatcher.match(document, sourceCaretOffset);
		if (region == null) {
			setStatusLineErrorMessage(PHPUIMessages.getString("GotoMatchingBracket_error_noMatchingBracket"));
			sourceViewer.getTextWidget().getDisplay().beep();
			return;
		}

		int offset = region.getOffset();
		int length = region.getLength();

		if (length < 1)
			return;

		int anchor = fBracketMatcher.getAnchor();
		// http://dev.eclipse.org/bugs/show_bug.cgi?id=34195
		int targetOffset = ICharacterPairMatcher.RIGHT == anchor ? offset + 1 : offset + length;

		boolean visible = false;
		if (sourceViewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
			visible = extension.modelOffset2WidgetOffset(targetOffset) > -1;
		} else {
			IRegion visibleRegion = sourceViewer.getVisibleRegion();
			// http://dev.eclipse.org/bugs/show_bug.cgi?id=34195
			visible = targetOffset >= visibleRegion.getOffset() && targetOffset <= visibleRegion.getOffset() + visibleRegion.getLength();
		}

		if (!visible) {
			setStatusLineErrorMessage(PHPUIMessages.getString("GotoMatchingBracket_error_bracketOutsideSelectedElement"));
			sourceViewer.getTextWidget().getDisplay().beep();
			return;
		}

		if (selection.getLength() < 0)
			targetOffset -= selection.getLength();

		sourceViewer.setSelectedRange(targetOffset, selection.getLength());
		sourceViewer.revealRange(targetOffset, selection.getLength());
	}

	private static boolean isSurroundedByBrackets(IDocument document, int offset) {
		if (offset == 0 || offset == document.getLength())
			return false;

		try {
			return isBracket(document.getChar(offset - 1)) && isBracket(document.getChar(offset));

		} catch (BadLocationException e) {
			return false;
		}
	}

	private static boolean isBracket(char character) {
		for (int i = 0; i != BRACKETS.length; ++i)
			if (character == BRACKETS[i])
				return true;
		return false;
	}

	/**
	 * Returns the signed current selection. The length will be negative if the resulting selection is right-to-left
	 * (RtoL).
	 * <p>
	 * The selection offset is model based.
	 * </p>
	 *
	 * @param sourceViewer the source viewer
	 * @return a region denoting the current signed selection, for a resulting RtoL selections length is < 0
	 */
	protected IRegion getSignedSelection(ISourceViewer sourceViewer) {
		StyledText text = sourceViewer.getTextWidget();
		Point selection = text.getSelectionRange();

		if (text.getCaretOffset() == selection.x) {
			selection.x = selection.x + selection.y;
			selection.y = -selection.y;
		}

		selection.x = widgetOffset2ModelOffset(sourceViewer, selection.x);

		return new Region(selection.x, selection.y);
	}

	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		getSite().getWorkbenchWindow().addPerspectiveListener(new IPerspectiveListener2() {

			public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, IWorkbenchPartReference partRef, String changeId) {
				if (changeId == IWorkbenchPage.CHANGE_EDITOR_CLOSE) {
					if (partRef.getPart(false) == getEditorPart()) {
						final IFile file = getFile();
						if (file != null) {
							ExternalFilesRegistry externalRegistry = ExternalFilesRegistry.getInstance();
							if (file.exists()) {
								IProject proj = file.getProject();
								try {
									//remove the file from project model when it is an RSE project.
									//this is to prevent display of the completion from this file
									//when it is closed
									if (proj.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID)) {
										PHPWorkspaceModelManager.getInstance().getModelForProject(proj).removeFileFromModel(file);
									} else {
										//parse the file in case the editor was closed without saving

										// making sure the project model exists (in case the editor closing is during PDT startup)
										if (PHPWorkspaceModelManager.getInstance().getModelForProject(proj) != null) {
											WorkspaceJob job = new WorkspaceJob("") { //$NON-NLS-1$
												public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
													PHPWorkspaceModelManager.getInstance().addFileToModel(file);
													return Status.OK_STATUS;
												}
											};
											job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
											job.schedule();
										}
									}

								} catch (CoreException ce) {
									Logger.logException(ce);
									return;
								}
							}
							//external php file
							else {
								IStructuredModel model = getModel();
								if (model != null) {
									String fileName = model.getBaseLocation();
									if (externalRegistry.isEntryExist(fileName)) {
										//if there are more than one editor opening the external file using "New Editor", do not remove it from model and registry
										IEditorReference[] existingEditors = ((WorkbenchPage) PHPStructuredEditor.this.getSite().getWorkbenchWindow().getActivePage()).getEditorManager().findEditors(getEditorInput(), null, IWorkbenchPage.MATCH_INPUT);

										// Make sure that the file has a full path before we try to remove it from the model.
										if (existingEditors.length == 1) { //a single editor
											IFile fileDecorator = ExternalFilesRegistry.getInstance().getFileEntry(fileName);
											PHPWorkspaceModelManager.getInstance().removeFileFromModel(fileDecorator);
											externalRegistry.removeFileEntry(fileName);
										}
									}
								}
							}
						}
						getSite().getWorkbenchWindow().removePerspectiveListener(this);
					}
				}
			}

			public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
			}

			public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
			}
		});
		final IInformationControlCreator informationControlCreator = new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell shell) {
				boolean cutDown = false;
				int style = cutDown ? SWT.NONE : SWT.V_SCROLL | SWT.H_SCROLL;
				return new DefaultInformationControl(shell, SWT.RESIZE | SWT.TOOL, style, new HTMLTextPresenter(cutDown));
			}
		};

		fInformationPresenter = new InformationPresenter(informationControlCreator);
		fInformationPresenter.setSizeConstraints(60, 10, true, true);
		fInformationPresenter.install(getSourceViewer());

		// bug fix - #154817
		StyledText styledText = getTextViewer().getTextWidget();
		styledText.getContent().addTextChangeListener(new TextChangeListener() {

			public void textChanging(TextChangingEvent event) {
			}

			public void textChanged(TextChangedEvent event) {
			}

			public void textSet(TextChangedEvent event) {
				refreshViewer();
			}

		});

		//		 bug fix - #156810
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new IResourceChangeListener() {

			public void resourceChanged(IResourceChangeEvent event) {
				try {
					if (getSite().getPage().getActiveEditor().equals(PHPStructuredEditor.this) && event.getType() == IResourceChangeEvent.POST_CHANGE && event.getDelta() != null) {
						refreshViewer();
					}
				} catch (NullPointerException e) {

				}
			}

		});

	}

	private void refreshViewer() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				StructuredTextViewer viewer = getTextViewer();
				if (viewer != null) {
					viewer.getTextWidget().redraw();
				}
			}
		});

	}

	protected void doSetInput(IEditorInput input) throws CoreException {
		IResource resource = null;
		IPath externalPath = null;
		isExternal = false;
		if (input instanceof IFileEditorInput) {
			// This is the existing workspace file
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();

			//we add this test to provide model for PHP files opened from RSE (temp) project
			IProject proj = resource.getProject();
			if (proj.isAccessible() && proj.hasNature(PHPUiConstants.RSE_TEMP_PROJECT_NATURE_ID)) {
				PHPWorkspaceModelManager.getInstance().getModelForProject(proj, true);
			}
		} else if (input instanceof IStorageEditorInput) {
			// This kind of editor input usually means non-workspace file, like
			// PHP file which comes from include path, remote file which comes from
			// Web server while debugging, file from ZIP archive, etc...

			final IStorageEditorInput editorInput = (IStorageEditorInput) input;
			final IStorage storage = editorInput.getStorage();

			if (storage instanceof ZipEntryStorage) {
				resource = ((ZipEntryStorage) storage).getProject();
			} else if (storage instanceof LocalFileStorage && ((LocalFileStorage) storage).getProject() != null) {
				resource = ((LocalFileStorage) storage).getProject();
			} else {
				// This is, probably, a remote storage:
				externalPath = storage.getFullPath();
				resource = ExternalFileWrapper.createFile(externalPath.toString());
			}
		} else if (input instanceof IURIEditorInput || input instanceof NonExistingPHPFileEditorInput) {
			// External file editor input. It's usually used when opening PHP file
			// via "File -> Open File" menu option, or using D&D:
			//OR
			// When we are dealing with an Untitled PHP document and the underlying PHP file
			// does not really exist, but is still considered as an "External" file.
			if (input instanceof NonExistingPHPFileEditorInput) {
				externalPath = ((NonExistingPHPFileEditorInput) input).getPath();
			} else {
				externalPath = URIUtil.toPath(((IURIEditorInput) input).getURI());
			}
			resource = ExternalFileWrapper.createFile(externalPath.toString());
		}

		if (resource instanceof IFile) {
			if (PHPModelUtil.isPhpFile((IFile) resource)) {
				// Add file decorator entry to the list of external files:
				if (externalPath != null && resource instanceof ExternalFileWrapper) {
					ExternalFilesRegistry.getInstance().addFileEntry(externalPath.toString(), (ExternalFileWrapper) resource);
					isExternal = true;
				}
				// Remove an older record from the external files registry in case this editor
				// is being reused to display a new content.
				IEditorInput oldInput = getEditorInput();
				if (oldInput != null && oldInput instanceof IStorageEditorInput) {
					String storagePath = ((IStorageEditorInput) oldInput).getStorage().getFullPath().toString();
					ExternalFilesRegistry.getInstance().removeFileEntry(storagePath);
				}
				PhpSourceParser.editFile.set(resource);
				super.doSetInput(input);
				PhpVersionChangedHandler.getInstance().addPhpVersionChangedListener(phpVersionListener);
			} else {
				super.doSetInput(input);
				//				close(false);
			}
		} else {
			super.doSetInput(input);
		}
	}

	ISelectionChangedListener selectionListener;

	public Object getAdapter(final Class required) {
		final Object adapter = super.getAdapter(required);

		// add selection listener to outline page
		// so that if outline selects codedata, editor selects correct item
		if (adapter instanceof ConfigurableContentOutlinePage && IContentOutlinePage.class.equals(required)) {
			final ConfigurableContentOutlinePage outlinePage = (ConfigurableContentOutlinePage) adapter;
			DoubleClickListener doubleClickListener = ((PHPContentOutlineConfiguration) outlinePage.getConfiguration()).getDoubleClickListener();
			if (!doubleClickListener.isEnabled()) {
				outlinePage.addDoubleClickListener(doubleClickListener);
				doubleClickListener.setEnabled(true);
			}
			if (selectionListener == null) {
				selectionListener = new ISelectionChangedListener() {

					boolean selecting = false;

					public void selectionChanged(final SelectionChangedEvent event) {
						if (!outlinePage.getConfiguration().isLinkedWithEditor(null)) {
							return;
						}
						/*
						 * The isFiringSelection check only works if a
						 * selection listener
						 */
						if (event.getSelection().isEmpty() || selecting)
							return;

						if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null && !getSourceViewer().getTextWidget().isDisposed() && getSite().getPage().getActivePart() != getEditorPart())
							if (event.getSelection() instanceof IStructuredSelection) {
								final ISelection current = getSelectionProvider().getSelection();
								if (current instanceof IStructuredSelection) {
									final Object[] currentSelection = ((IStructuredSelection) current).toArray();
									final Object[] newSelection = ((IStructuredSelection) event.getSelection()).toArray();
									if (!Arrays.equals(currentSelection, newSelection))
										if (newSelection.length > 0) {
											/*
											 * No ordering is guaranteed for
											 * multiple selection
											 */
											final Object o = newSelection[0];
											selecting = true;
											if (o instanceof PHPCodeData)
												setSelection((PHPCodeData) o, true);
											selecting = false;
										}
								}
							}
						clearStatusLine();
					}
				};
			}
			outlinePage.addSelectionChangedListener(selectionListener);
		}
		return adapter;
	}

	protected void clearStatusLine() {
		setStatusLineErrorMessage(null);
		setStatusLineMessage(null);
	}

	public IFile getFile() {
		// when a file with no content type associated with it is opened with this editor
		// there will be no model for it. If it has a FileEditorInput we'll return the IFile from it
		// else, null.
		if (getModel() == null) {
			if (getEditorInput() instanceof IFileEditorInput) {
				return ((IFileEditorInput) getEditorInput()).getFile();
			} else {
				return null;
			}
		}
		IPath path = new Path(getModel().getBaseLocation());
		if (ExternalFilesRegistry.getInstance().isEntryExist(path.toString())) {
			return ExternalFilesRegistry.getInstance().getFileEntry(path.toString());
		}
		//could be that it is an external file BUT was already removed !, check :
		else if (path.segmentCount() == 1) {
			return ExternalFileWrapper.createFile(path.toString());
		}

		//handle case of workspace file AND/OR an external file with more than 1 segment
		return (ResourcesPlugin.getWorkspace().getRoot()).getFile(path);
	}

	public PHPFileData getPHPFileData() {
		if (getModel() == null) {
			return null;
		}
		return PHPWorkspaceModelManager.getInstance().getModelForFile(getModel().getBaseLocation());
	}

	public SourceViewerConfiguration getSourceViwerConfiguration() {
		return super.getSourceViewerConfiguration();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.StructuredTextEditor#handleCursorPositionChanged()
	 */
	protected void handleCursorPositionChanged() {
		updateCursorDependentActions();
		super.handleCursorPositionChanged();
	}

	protected void handlePreferenceStoreChanged(final PropertyChangeEvent event) {
		final String property = event.getProperty();
		try {
			if (PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIERS.equals(property) || PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIER_MASKS.equals(property))
				updateHoverBehavior();
		} finally {
			super.handlePreferenceStoreChanged(event);
		}
	}

	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(new String[] { "org.eclipse.php.ui.phpEditorScope" }); //$NON-NLS-1$
	}

	/**
	 * Marks or unmarks the given action to be updated on text cursor position changes.
	 *
	 * @param actionId the action id
	 * @param mark <code>true</code> if the action is cursor position dependent
	 */
	public void markAsCursorDependentAction(final String actionId, final boolean mark) {
		assert actionId != null;
		if (mark) {
			if (!fCursorActions.contains(actionId))
				fCursorActions.add(actionId);
		} else
			fCursorActions.remove(actionId);
	}

	public IDocument getDocument() {
		return getSourceViewer().getDocument();
	}

	public void setSelection(final PHPCodeData element, boolean reveal) {
		if (element != null) {
			final UserData userData = element.getUserData();
			PHPFileData fileData = getPHPFileData();
			if (userData == null || fileData == null || !userData.getFileName().equals(fileData.getUserData().getFileName())) {
				return;
			}
			int start = userData.getStartPosition();
			int length = userData.getEndPosition() - userData.getStartPosition() + 1;

			final IDocument document = getSourceViewer().getDocument();
			if (document instanceof IStructuredDocument) {
				final IStructuredDocument sDocument = (IStructuredDocument) document;
				final IStructuredDocumentRegion sdRegion = sDocument.getRegionAtCharacterOffset(start);
				if (sdRegion != null) {
					// Need it in case the php document doesn't start at the first
					// line of the page
					final int sdRegionStart = sdRegion.getStartOffset();

					ITextRegion region = sdRegion.getRegionAtCharacterOffset(start);
					if (region.getType() == PHPRegionContext.PHP_CONTENT) {
						PhpScriptRegion phpScriptRegion = (PhpScriptRegion) region;
						try {
							region = phpScriptRegion.getPhpToken(start - sdRegionStart - phpScriptRegion.getStart());

							String elementName = element.getName();
							if (element instanceof PHPVariableData) {
								elementName = "$" + elementName; //$NON-NLS-1$
							}

							while (region.getEnd() != phpScriptRegion.getLength()) {
								final String text = document.get(sdRegionStart + phpScriptRegion.getStart() + region.getStart(), region.getTextLength()).trim().replaceAll("[\"']+", ""); //$NON-NLS-1$ //$NON-NLS-2$
								if (elementName.equals(text)) {
									start = sdRegionStart + phpScriptRegion.getStart() + region.getStart();
									length = region.getTextLength();
									break;
								}
								region = phpScriptRegion.getPhpToken(region.getEnd());
							}
						} catch (BadLocationException e) {
							PHPUiPlugin.log(e);
						}
					}
				}
			}

			if (!reveal)
				getSourceViewer().setSelectedRange(start, length);
			else
				selectAndReveal(start, length);
		}
	}

	/**
	 * Updates the specified action by calling <code>IUpdate.update</code> if applicable.
	 *
	 * @param actionId the action id
	 */
	private void updateAction(final String actionId) {
		assert actionId != null;
		final IAction action = getAction(actionId);
		if (action instanceof IUpdate)
			((IUpdate) action).update();
	}

	/**
	 * Updates all cursor position dependent actions.
	 */
	protected void updateCursorDependentActions() {
		if (fCursorActions != null) {
			final Iterator e = fCursorActions.iterator();
			while (e.hasNext())
				updateAction((String) e.next());
		}
	}

	/*
	 * Update the hovering behavior depending on the preferences.
	 */
	private void updateHoverBehavior() {
		final SourceViewerConfiguration configuration = getSourceViewerConfiguration();
		final String[] types = configuration.getConfiguredContentTypes(getSourceViewer());

		for (final String t : types) {

			final ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension2) {
				// Remove existing hovers
				((ITextViewerExtension2) sourceViewer).removeTextHovers(t);

				final int[] stateMasks = configuration.getConfiguredTextHoverStateMasks(getSourceViewer(), t);

				if (stateMasks != null)
					for (final int stateMask : stateMasks) {
						final ITextHover textHover = configuration.getTextHover(sourceViewer, t, stateMask);
						((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, stateMask);
					}
				else {
					final ITextHover textHover = configuration.getTextHover(sourceViewer, t);
					((ITextViewerExtension2) sourceViewer).setTextHover(textHover, t, ITextViewerExtension2.DEFAULT_HOVER_STATE_MASK);
				}
			} else
				sourceViewer.setTextHover(configuration.getTextHover(sourceViewer, t), t);
		}
	}

	protected StructuredTextViewer createStructedTextViewer(Composite parent, IVerticalRuler verticalRuler, int styles) {
		return new PHPStructuredTextViewer(this, parent, verticalRuler, getOverviewRuler(), isOverviewRulerVisible(), styles);
	}
}