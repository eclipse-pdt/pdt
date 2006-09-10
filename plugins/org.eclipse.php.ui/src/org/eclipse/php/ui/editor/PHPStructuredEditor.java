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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
import org.eclipse.jface.internal.text.link.contentassist.HTMLTextPresenter;
import org.eclipse.jface.text.AbstractInformationControlManager;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITextViewerExtension2;
import org.eclipse.jface.text.ITextViewerExtension4;
import org.eclipse.jface.text.ITextViewerExtension5;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHoverExtension;
import org.eclipse.jface.text.source.ILineRange;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.ISourceViewerExtension3;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.php.PHPUIMessages;
import org.eclipse.php.core.containers.LocalFileStorage;
import org.eclipse.php.core.containers.ZipEntryStorage;
import org.eclipse.php.core.documentModel.dom.PHPElementImpl;
import org.eclipse.php.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.core.phpModel.parser.PHPWorkspaceModelManager;
import org.eclipse.php.core.phpModel.phpElementData.PHPCodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPFileData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.actions.AddBlockCommentAction;
import org.eclipse.php.internal.ui.actions.BlockCommentAction;
import org.eclipse.php.internal.ui.actions.IPHPEditorActionDefinitionIds;
import org.eclipse.php.internal.ui.actions.OpenDeclarationAction;
import org.eclipse.php.internal.ui.actions.OpenFunctionsManualAction;
import org.eclipse.php.internal.ui.actions.RemoveBlockCommentAction;
import org.eclipse.php.internal.ui.actions.ToggleCommentAction;
import org.eclipse.php.ui.containers.StorageEditorInput;
import org.eclipse.php.ui.editor.hover.IHoverMessageDecorators;
import org.eclipse.php.ui.editor.hover.IPHPTextHover;
import org.eclipse.php.ui.editor.hover.SourceViewerInformationControl;
import org.eclipse.php.ui.preferences.PreferenceConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
import org.eclipse.ui.texteditor.IUpdate;
import org.eclipse.ui.texteditor.ResourceAction;
import org.eclipse.ui.texteditor.TextEditorAction;
import org.eclipse.ui.texteditor.TextOperationAction;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.StructuredTextEditor;
import org.eclipse.wst.sse.ui.internal.contentoutline.ConfigurableContentOutlinePage;

public class PHPStructuredEditor extends StructuredTextEditor {
	
	IWorkbenchPart getPart() {
		return this;
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
					final int maxVisibleLines = Integer.MAX_VALUE; // allow any number of lines being displayed, as we support scrolling
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
				if ("org.eclipse.jface.text.source.projection.ProjectionAnnotationHover".equals(annotationHover.getClass().getName()))
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
				fInformationPresenter.setMargins(4, 0); // AnnotationBarHoverManager sets (5,0), minus SourceViewer.GAP_SIZE_1
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
					final IHoverMessageDecorators decorator = ((IPHPTextHover) textHover).getMessageDecorator();
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

	/** Cursor dependent actions. */
	private final List fCursorActions = new ArrayList(5);

	/** The information presenter. */
	protected InformationPresenter fInformationPresenter;

	public PHPStructuredEditor() {
	}

	protected void addContextMenuActions(final IMenuManager menu) {
		super.addContextMenuActions(menu);

		if (getSourceViewer().isEditable()) {
			final String label = PHPUIMessages.PHPStructuredEditor_Source;
			final MenuManager subMenu = new MenuManager(label, "org.eclipse.php.ui.source.menu"); //$NON-NLS-1$
			subMenu.add(new GroupMarker("editGroup")); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.ToggleCommentAction"); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.AddBlockComment"); //$NON-NLS-1$
			addAction(subMenu, "org.eclipse.php.ui.actions.RemoveBlockComment"); //$NON-NLS-1$
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, subMenu);

			final String openGroup = "group.open"; //$NON-NLS-1$
			menu.appendToGroup(ITextEditorActionConstants.GROUP_EDIT, new Separator(openGroup));
			IAction action = getAction("org.eclipse.php.ui.actions.Open"); //$NON-NLS-1$
			if (action != null)
				menu.appendToGroup(openGroup, action);
			action = getAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction"); //$NON-NLS-1$
			if (action != null)
				menu.appendToGroup(openGroup, action);
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

		action = new OpenFunctionsManualAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.OpenFunctionsManualAction"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction", action); //$NON-NLS-1$
		markAsCursorDependentAction("org.eclipse.php.ui.actions.OpenFunctionsManualAction", true); //$NON-NLS-1$

		action = new OpenDeclarationAction(resourceBundle, this);
		action.setActionDefinitionId("org.eclipse.php.ui.edit.text.open.editor"); //$NON-NLS-1$
		setAction("org.eclipse.php.ui.actions.Open", action); //$NON-NLS-1$
		markAsCursorDependentAction("org.eclipse.php.ui.actions.Open", true); //$NON-NLS-1$

		ResourceAction resAction = new TextOperationAction(PHPUIMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", this, ISourceViewer.INFORMATION, true);
		resAction = new InformationDispatchAction(PHPUIMessages.getBundleForConstructedKeys(), "ShowPHPDoc.", (TextOperationAction) resAction); //$NON-NLS-1$
		resAction.setActionDefinitionId(IPHPEditorActionDefinitionIds.SHOW_PHPDOC);
		setAction("ShowPHPDoc", resAction); //$NON-NLS-1$

	}

	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

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
	}

	protected void doSetInput(final IEditorInput input) throws CoreException {
		IResource resource = null;
		if (input instanceof IFileEditorInput) {
			final IFileEditorInput fileInput = (IFileEditorInput) input;
			resource = fileInput.getFile();
		} else if (input instanceof StorageEditorInput) {
			final StorageEditorInput editorInput = (StorageEditorInput) input;
			final IStorage storage = editorInput.getStorage();
			if (storage instanceof ZipEntryStorage)
				resource = ((ZipEntryStorage) storage).getProject();
			else if (storage instanceof LocalFileStorage)
				resource = ((LocalFileStorage) storage).getProject();
		}
		PhpSourceParser.editFile.set(resource);
		super.doSetInput(input);
	}

	public Object getAdapter(final Class required) {
		final Object adapter = super.getAdapter(required);

		// add selection listener to outline page
		// so that if outline selects codedata, editor selects correct item
		if (adapter instanceof ConfigurableContentOutlinePage && IContentOutlinePage.class.equals(required)) {
			final ConfigurableContentOutlinePage outlinePage = (ConfigurableContentOutlinePage) adapter;
			outlinePage.addSelectionChangedListener(new ISelectionChangedListener() {

				boolean selecting = false;

				public void selectionChanged(final SelectionChangedEvent event) {
					/*
					 * The isFiringSelection check only works if a
					 * selection listener
					 */
					if (event.getSelection().isEmpty() || selecting)
						return;

					if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null && !getSourceViewer().getTextWidget().isDisposed() && getSite().getPage().getActivePart() != getPart())
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
				}

			});
		}
		return adapter;
	}

	public IFile getFile() {
		final String filename = getModel().getBaseLocation();
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filename));
	}

	public PHPFileData getPHPFileData() {
		final String filename = getModel().getBaseLocation();
		return PHPWorkspaceModelManager.getInstance().getModelForFile(filename);
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
			if (PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIERS.equals(property))
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
		Assert.isNotNull(actionId);
		if (mark) {
			if (!fCursorActions.contains(actionId))
				fCursorActions.add(actionId);
		} else
			fCursorActions.remove(actionId);
	}

	public void setSelection(final PHPCodeData element, boolean reveal) {
		if (element != null) {
			final UserData userData = element.getUserData();
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
					String elementName = element.getName();
					if (element instanceof PHPVariableData)
						elementName = "$" + elementName; //$NON-NLS-1$
					while (region != null && region.getEnd() + sdRegionStart < start + length) {
						final String text = sdRegion.getText(region).trim().replaceAll("[\"']+", "");
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

	/**
	 * Updates the specified action by calling <code>IUpdate.update</code>
	 * if applicable.
	 *
	 * @param actionId the action id
	 */
	private void updateAction(final String actionId) {
		Assert.isNotNull(actionId);
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

		for (int i = 0; i < types.length; i++) {

			final String t = types[i];

			final ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer instanceof ITextViewerExtension2) {
				// Remove existing hovers
				((ITextViewerExtension2) sourceViewer).removeTextHovers(t);

				final int[] stateMasks = configuration.getConfiguredTextHoverStateMasks(getSourceViewer(), t);

				if (stateMasks != null)
					for (int j = 0; j < stateMasks.length; j++) {
						final int stateMask = stateMasks[j];
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
}
