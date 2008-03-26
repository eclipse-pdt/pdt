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

import java.io.IOException;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.*;

import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ISavePolicy;
import org.eclipse.dltk.internal.ui.editor.ISourceModuleDocumentProvider;
import org.eclipse.dltk.internal.ui.text.IScriptReconcilingListener;
import org.eclipse.dltk.internal.ui.text.ScriptWordFinder;
import org.eclipse.dltk.ui.DLTKUIPlugin;
import org.eclipse.jface.action.*;
import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.information.IInformationProvider;
import org.eclipse.jface.text.information.IInformationProviderExtension;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.jface.text.information.InformationPresenter;
import org.eclipse.jface.text.link.LinkedModeModel;
import org.eclipse.jface.text.source.*;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.*;
import org.eclipse.php.internal.core.ast.locator.PhpElementConciliator;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.ast.nodes.Scalar;
import org.eclipse.php.internal.core.containers.LocalFileStorage;
import org.eclipse.php.internal.core.containers.ZipEntryStorage;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
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
import org.eclipse.php.internal.ui.containers.LocalFileStorageEditorInput;
import org.eclipse.php.internal.ui.corext.dom.NodeFinder;
import org.eclipse.php.internal.ui.editor.hover.SourceViewerInformationControl;
import org.eclipse.php.internal.ui.editor.input.NonExistingPHPFileEditorInput;
import org.eclipse.php.internal.ui.explorer.PHPSearchActionGroup;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration;
import org.eclipse.php.internal.ui.outline.PHPContentOutlineConfiguration.DoubleClickListener;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.php.internal.ui.search.IOccurrencesFinder;
import org.eclipse.php.internal.ui.search.MethodExitsFinder;
import org.eclipse.php.internal.ui.search.OccurrencesFinderFactory;
import org.eclipse.php.internal.ui.search.IOccurrencesFinder.OccurrenceLocation;
import org.eclipse.php.internal.ui.text.DocumentCharacterIterator;
import org.eclipse.php.internal.ui.text.PHPWordIterator;
import org.eclipse.php.internal.ui.viewsupport.ISelectionListenerWithAST;
import org.eclipse.php.internal.ui.viewsupport.SelectionListenerWithASTManager;
import org.eclipse.php.ui.editor.SharedASTProvider;
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
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.texteditor.*;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.wst.common.frameworks.internal.DoNotUseMeThisWillBeDeletedPost15;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
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
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;

public class PHPStructuredEditor extends StructuredTextEditor implements IPhpScriptReconcilingListener {

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

	/** The editor's save policy */
	protected ISavePolicy fSavePolicy = null;

	/**
	 * The internal shell activation listener for updating occurrences.
	 * @since 3.4
	 */
	private ActivationListener fActivationListener= new ActivationListener();
	private ISelectionListenerWithAST fPostSelectionListenerWithAST;
	private OccurrencesFinderJob fOccurrencesFinderJob;
	/** The occurrences finder job canceler */
	private OccurrencesFinderJobCanceler fOccurrencesFinderJobCanceler;	

	/**
	 * The selection used when forcing occurrence marking
	 * through code.
	 * @since 3.4
	 */
	private ISelection fForcedMarkOccurrencesSelection;
	/**
	 * The document modification stamp at the time when the last
	 * occurrence marking took place.
	 * @since 3.4
	 */
	private long fMarkOccurrenceModificationStamp= IDocumentExtension4.UNKNOWN_MODIFICATION_STAMP;
	/**
	 * The region of the word under the caret used to when
	 * computing the current occurrence markings.
	 * @since 3.4
	 */
	private IRegion fMarkOccurrenceTargetRegion;

	/**
	 * Holds the current occurrence annotations.
	 * @since 3.4
	 */
	private Annotation[] fOccurrenceAnnotations= null;
	/**
	 * Tells whether all occurrences of the element at the
	 * current caret location are automatically marked in
	 * this editor.
	 * @since 3.4
	 */
	private boolean fMarkOccurrenceAnnotations;
	/**
	 * Tells whether the occurrence annotations are sticky
	 * i.e. whether they stay even if there's no valid Java
	 * element at the current caret position.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fStickyOccurrenceAnnotations;
	/**
	 * Tells whether to mark type occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkTypeOccurrences;
	/**
	 * Tells whether to mark method and declaration occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkMethodOccurrences;
	/**
	 * Tells whether to mark function occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkFunctionOccurrences;
	/**
	 * Tells whether to mark constant occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkConstantOccurrences;
	/**
	 * Tells whether to mark field global variable in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkGlobalVariableOccurrences;
	/**
	 * Tells whether to mark local variable occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkLocalVariableOccurrences;
	/**
	 * Tells whether to mark exception occurrences in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkExceptions;
	/**
	 * Tells whether to mark method exits in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkMethodExitPoints;
	
	/**
	 * Tells whether to mark targets of <code>break</code> and <code>continue</code> statements in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkBreakContinueTargets;
	
	/**
	 * Tells whether to mark implementors in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkImplementors;

	/**
	 * Tells whether to mark HTML tags in this editor.
	 * Only valid if {@link #fMarkOccurrenceAnnotations} is <code>true</code>.
	 * @since 3.4
	 */
	private boolean fMarkHTMLTags;

	/**
	 * Internal implementation class for a change listener.
	 * @since 3.0
	 */
	protected abstract class AbstractSelectionChangedListener implements ISelectionChangedListener  {

		/**
		 * Installs this selection changed listener with the given selection provider. If
		 * the selection provider is a post selection provider, post selection changed
		 * events are the preferred choice, otherwise normal selection changed events
		 * are requested.
		 *
		 * @param selectionProvider
		 */
		public void install(ISelectionProvider selectionProvider) {
			if (selectionProvider == null)
				return;

			if (selectionProvider instanceof IPostSelectionProvider)  {
				IPostSelectionProvider provider= (IPostSelectionProvider) selectionProvider;
				provider.addPostSelectionChangedListener(this);
			} else  {
				selectionProvider.addSelectionChangedListener(this);
			}
		}

		/**
		 * Removes this selection changed listener from the given selection provider.
		 *
		 * @param selectionProvider the selection provider
		 */
		public void uninstall(ISelectionProvider selectionProvider) {
			if (selectionProvider == null)
				return;

			if (selectionProvider instanceof IPostSelectionProvider)  {
				IPostSelectionProvider provider= (IPostSelectionProvider) selectionProvider;
				provider.removePostSelectionChangedListener(this);
			} else  {
				selectionProvider.removeSelectionChangedListener(this);
			}
		}
	}


	/**
	 * Updates the Java outline page selection and this editor's range indicator.
	 *
	 * @since 3.0
	 */
	private class EditorSelectionChangedListener extends AbstractSelectionChangedListener {

		/*
		 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			// XXX: see https://bugs.eclipse.org/bugs/show_bug.cgi?id=56161
			PHPStructuredEditor.this.selectionChanged();
		}
	}	
	
	/**
	 * The editor selection changed listener.
	 */
	private EditorSelectionChangedListener fEditorSelectionChangedListener;

	private final class OutlineSelectionListener implements ISelectionChangedListener {
		private final ConfigurableContentOutlinePage outlinePage;
		boolean selecting = false;

		private OutlineSelectionListener(ConfigurableContentOutlinePage outlinePage) {
			this.outlinePage = outlinePage;
		}

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
	}

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
		@Override
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
	 * Internal activation listener.
	 * @since 3.0
	 */
	private class ActivationListener implements IWindowListener {

		/*
		 * @see org.eclipse.ui.IWindowListener#windowActivated(org.eclipse.ui.IWorkbenchWindow)
		 * @since 3.1
		 */
		public void windowActivated(IWorkbenchWindow window) {
			if (window == getEditorSite().getWorkbenchWindow() && fMarkOccurrenceAnnotations && isActivePart()) {
				fForcedMarkOccurrencesSelection= getSelectionProvider().getSelection();
				IModelElement sourceModule = getInputModelElement();
				if (sourceModule.getElementType() == IModelElement.SOURCE_MODULE) {
					try {
						updateOccurrenceAnnotations((ITextSelection)fForcedMarkOccurrencesSelection, SharedASTProvider.getAST((ISourceModule) sourceModule, SharedASTProvider.WAIT_NO, getProgressMonitor()));
					} catch (ModelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowDeactivated(org.eclipse.ui.IWorkbenchWindow)
		 * @since 3.1
		 */
		public void windowDeactivated(IWorkbenchWindow window) {
			if (window == getEditorSite().getWorkbenchWindow() && fMarkOccurrenceAnnotations && isActivePart()) {
				removeOccurrenceAnnotations();
			}
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowClosed(org.eclipse.ui.IWorkbenchWindow)
		 * @since 3.1
		 */
		public void windowClosed(IWorkbenchWindow window) {
		}

		/*
		 * @see org.eclipse.ui.IWindowListener#windowOpened(org.eclipse.ui.IWorkbenchWindow)
		 * @since 3.1
		 */
		public void windowOpened(IWorkbenchWindow window) {
		}
	}
	
	/**
	 * Cancels the occurrences finder job upon document changes.
	 *
	 * @since 3.0
	 */
	class OccurrencesFinderJobCanceler implements IDocumentListener, ITextInputListener {

		public void install() {
			ISourceViewer sourceViewer= getSourceViewer();
			if (sourceViewer == null)
				return;

			StyledText text= sourceViewer.getTextWidget();
			if (text == null || text.isDisposed())
				return;

			sourceViewer.addTextInputListener(this);

			IDocument document= sourceViewer.getDocument();
			if (document != null)
				document.addDocumentListener(this);
		}

		public void uninstall() {
			ISourceViewer sourceViewer= getSourceViewer();
			if (sourceViewer != null)
				sourceViewer.removeTextInputListener(this);

			IDocumentProvider documentProvider= getDocumentProvider();
			if (documentProvider != null) {
				IDocument document= documentProvider.getDocument(getEditorInput());
				if (document != null)
					document.removeDocumentListener(this);
			}
		}


		/*
		 * @see org.eclipse.jface.text.IDocumentListener#documentAboutToBeChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentAboutToBeChanged(DocumentEvent event) {
			if (fOccurrencesFinderJob != null)
				fOccurrencesFinderJob.doCancel();
		}

		/*
		 * @see org.eclipse.jface.text.IDocumentListener#documentChanged(org.eclipse.jface.text.DocumentEvent)
		 */
		public void documentChanged(DocumentEvent event) {
		}

		/*
		 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentAboutToBeChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
		 */
		public void inputDocumentAboutToBeChanged(IDocument oldInput, IDocument newInput) {
			if (oldInput == null)
				return;

			oldInput.removeDocumentListener(this);
		}

		/*
		 * @see org.eclipse.jface.text.ITextInputListener#inputDocumentChanged(org.eclipse.jface.text.IDocument, org.eclipse.jface.text.IDocument)
		 */
		public void inputDocumentChanged(IDocument oldInput, IDocument newInput) {
			if (newInput == null)
				return;
			newInput.addDocumentListener(this);
		}
	}	
	
	/**
	 * Finds and marks occurrence annotations.
	 *
	 * @since 3.0
	 */
	class OccurrencesFinderJob extends Job {

		private final IDocument fDocument;
		private final ISelection fSelection;
		private final ISelectionValidator fPostSelectionValidator;
		private boolean fCanceled= false;
		private final OccurrenceLocation[] fLocations;

		public OccurrencesFinderJob(IDocument document, OccurrenceLocation[] locations, ISelection selection) {
			super("mark occrrences job name"); // TODO should externals
			fDocument= document;
			fSelection= selection;
			fLocations= locations;

			if (getSelectionProvider() instanceof ISelectionValidator)
				fPostSelectionValidator= (ISelectionValidator)getSelectionProvider();
			else
				fPostSelectionValidator= null;
		}

		// cannot use cancel() because it is declared final
		void doCancel() {
			fCanceled= true;
			cancel();
		}

		private boolean isCanceled(IProgressMonitor progressMonitor) {
			return fCanceled || progressMonitor.isCanceled()
				||  fPostSelectionValidator != null && !(fPostSelectionValidator.isValid(fSelection) || fForcedMarkOccurrencesSelection == fSelection)
				|| LinkedModeModel.hasInstalledModel(fDocument);
		}

		/*
		 * @see Job#run(org.eclipse.core.runtime.IProgressMonitor)
		 */
		public IStatus run(IProgressMonitor progressMonitor) {
			if (isCanceled(progressMonitor))
				return Status.CANCEL_STATUS;

			ITextViewer textViewer= getTextViewer();
			if (textViewer == null)
				return Status.CANCEL_STATUS;

			IDocument document= textViewer.getDocument();
			if (document == null)
				return Status.CANCEL_STATUS;

			IDocumentProvider documentProvider= getDocumentProvider();
			if (documentProvider == null)
				return Status.CANCEL_STATUS;

			IAnnotationModel annotationModel= documentProvider.getAnnotationModel(getEditorInput());
			if (annotationModel == null)
				return Status.CANCEL_STATUS;

			// Add occurrence annotations
			int length= fLocations.length;
			Map<Annotation, Position> annotationMap= new HashMap<Annotation, Position>(length);
			for (int i= 0; i < length; i++) {

				if (isCanceled(progressMonitor))
					return Status.CANCEL_STATUS;
				
				OccurrenceLocation location= fLocations[i];
				Position position= new Position(location.getOffset(), location.getLength());

				String description= location.getDescription();
				String annotationType= (location.getFlags() == IOccurrencesFinder.F_WRITE_OCCURRENCE) ? "org.eclipse.php.ui.occurrences.write" : "org.eclipse.php.ui.occurrences"; //$NON-NLS-1$ //$NON-NLS-2$
				
				annotationMap.put(new Annotation(annotationType, false, description), position);
			}

			if (isCanceled(progressMonitor))
				return Status.CANCEL_STATUS;

			synchronized (getLockObject(annotationModel)) {
				if (annotationModel instanceof IAnnotationModelExtension) {
					((IAnnotationModelExtension)annotationModel).replaceAnnotations(fOccurrenceAnnotations, annotationMap);
				} else {
					removeOccurrenceAnnotations();
					for (Map.Entry<Annotation, Position> entry : annotationMap.entrySet()) {
						annotationModel.addAnnotation(entry.getKey(), entry.getValue());	
					}
				}
				fOccurrenceAnnotations= annotationMap.keySet().toArray(new Annotation[annotationMap.keySet().size()]);
			}

			return Status.OK_STATUS;
		}
	}
	
	
	/**
	 * Information provider used to present focusable information shells.
	 *
	 * @since 3.2
	 */
	private static final class InformationProvider implements IInformationProvider, IInformationProviderExtension, IInformationProviderExtension2 {

		private final IInformationControlCreator fControlCreator;
		private final Object fHoverInfo;
		private final IRegion fHoverRegion;

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

	protected final IPreferencesPropagatorListener phpVersionListener = new IPreferencesPropagatorListener() {
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
					PHPStructuredTextViewer textViewer = (PHPStructuredTextViewer) getTextViewer();
					textViewer.reconcile();
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
			if (region instanceof IPhpScriptRegion) {
				final IPhpScriptRegion phpRegion = (IPhpScriptRegion) region;
				phpRegion.completeReparse(doc, offset + region.getStart(), region.getLength());
			}
		}
	}

	/** Cursor dependent actions. */
	private final List<String> fCursorActions = new ArrayList<String>(5);

	/** The information presenter. */
	protected InformationPresenter fInformationPresenter;

	public PHPStructuredEditor() {
		/**
		 * Bug fix: #158170 Set WST's folding support enablement according to PHP editor folding support status. Must be
		 * removed, when WTP releases folding support
		 */
		boolean foldingEnabled = PHPUiPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_FOLDING_ENABLED);
		SSEUIPlugin.getDefault().getPreferenceStore().setValue(IStructuredTextFoldingProvider.FOLDING_ENABLED, foldingEnabled);
		setDocumentProvider(DLTKUIPlugin.getDocumentProvider());
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();

		IPreferenceStore store = createCombinedPreferenceStore();
		setPreferenceStore(store);
		
		fMarkOccurrenceAnnotations= store.getBoolean(PreferenceConstants.EDITOR_MARK_OCCURRENCES);
		fStickyOccurrenceAnnotations= store.getBoolean(PreferenceConstants.EDITOR_STICKY_OCCURRENCES);
		fMarkTypeOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_TYPE_OCCURRENCES);
		fMarkMethodOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_METHOD_OCCURRENCES);
		fMarkFunctionOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_FUNCTION_OCCURRENCES);
		fMarkConstantOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_CONSTANT_OCCURRENCES);
		fMarkGlobalVariableOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_GLOBAL_VARIABLE_OCCURRENCES);
		fMarkLocalVariableOccurrences= store.getBoolean(PreferenceConstants.EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES);
		fMarkExceptions= store.getBoolean(PreferenceConstants.EDITOR_MARK_EXCEPTION_OCCURRENCES);
		fMarkImplementors= store.getBoolean(PreferenceConstants.EDITOR_MARK_IMPLEMENTORS);
		fMarkMethodExitPoints= store.getBoolean(PreferenceConstants.EDITOR_MARK_METHOD_EXIT_POINTS);
		fMarkBreakContinueTargets= store.getBoolean(PreferenceConstants.EDITOR_MARK_BREAK_CONTINUE_TARGETS);
		fMarkHTMLTags= store.getBoolean(PreferenceConstants.EDITOR_MARK_HTML_TAGS);
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

	@Override
	public void dispose() {
		PhpVersionChangedHandler.getInstance().removePhpVersionChangedListener(phpVersionListener);
		if (fActivationListener != null) {
			PlatformUI.getWorkbench().removeWindowListener(fActivationListener);
			fActivationListener= null;
		}
		uninstallOccurrencesFinder();
		super.dispose();
	}

	/*
	 * @see AbstractTextEditor#editorContextMenuAboutToShow(IMenuManager)
	 */
	@Override
	public void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);

		if (fContextMenuGroup != null) {
			ActionContext context = new ActionContext(getSelectionProvider().getSelection());
			fContextMenuGroup.setContext(context);
			fContextMenuGroup.fillContextMenu(menu);
			fContextMenuGroup.setContext(null);
		}
	}

	@Override
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

	@Override
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

		private final boolean fDoSelect;

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
		@Override
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
		@Override
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
		private final boolean fDoSelect;

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
		@Override
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
		@Override
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
		@Override
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
		@Override
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
		@Override
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
		@Override
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
		@Override
		protected void setCaretPosition(final int position) {
			getTextWidget().setCaretOffset(modelOffset2WidgetOffset(getSourceViewer(), position));
		}
	}

	@Override
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
		ActionGroup jsg = new PHPSearchActionGroup(this);

		// We have to keep the context menu group separate to have better control over positioning
		fActionGroups= new CompositeActionGroup(new ActionGroup[] { rg,  jsg });
		fContextMenuGroup = new CompositeActionGroup(new ActionGroup[] { rg, jsg });
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

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		
		if (isMarkingOccurrences())
			installOccurrencesFinder(true);
		
		getSite().getWorkbenchWindow().addPerspectiveListener(new IPerspectiveListener2() {

			public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, IWorkbenchPartReference partRef, String changeId) {
				if (changeId == IWorkbenchPage.CHANGE_EDITOR_CLOSE) {
					if (partRef.getPart(false) == getEditorPart()) {
						final IFile file = getFile();
						if (file != null) {
							final ExternalFilesRegistry externalRegistry = ExternalFilesRegistry.getInstance();
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
												@Override
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
									final String fileName = new Path(model.getBaseLocation()).toOSString();
									if (externalRegistry.isEntryExist(fileName)) {
										//if there are more than one editor opening the external file using "New Editor", do not remove it from model and registry
										IEditorReference[] existingEditors = null;
										WorkbenchPage activePage = (WorkbenchPage) PHPStructuredEditor.this.getSite().getWorkbenchWindow().getActivePage();
										if (activePage != null) {
											existingEditors = activePage.getEditorManager().findEditors(getEditorInput(), null, IWorkbenchPage.MATCH_INPUT);
										}
										// Make sure that the file has a full path before we try to remove it from the model.
										if (existingEditors == null || existingEditors.length == 1) { //a single editor
											final IFile fileWrapper = ExternalFilesRegistry.getInstance().getFileEntry(fileName);
											WorkspaceJob job = new WorkspaceJob("") { //$NON-NLS-1$
												@Override
												public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
													PHPWorkspaceModelManager.getInstance().removeFileFromModel(fileWrapper);
													externalRegistry.removeFileEntry(fileName);
													return Status.OK_STATUS;
												}
											};
											job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
											job.schedule();
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

		fEditorSelectionChangedListener = new EditorSelectionChangedListener();
		fEditorSelectionChangedListener.install(getSelectionProvider());
		PlatformUI.getWorkbench().addWindowListener(fActivationListener);
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

	@Override
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
			} else if (storage instanceof LocalFileStorage) {
				// don't create external resource, it's wrong! Include paths should not have a resource.
			} else {
				// This is, probably, a remote storage:
				externalPath = storage.getFullPath();
				resource = ExternalFileWrapper.createFile(externalPath.toOSString());
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
			resource = ExternalFileWrapper.createFile(externalPath.toOSString());
		}

		if (resource instanceof IFile) {
			if (PHPModelUtil.isPhpFile((IFile) resource)) {
				// Add file decorator entry to the list of external files:
				if (externalPath != null && resource instanceof ExternalFileWrapper) {
					ExternalFilesRegistry.getInstance().addFileEntry(externalPath.toOSString(), (ExternalFileWrapper) resource);
					isExternal = true;
				}
				// Remove an older record from the external files registry in case this editor
				// is being reused to display a new content.
				IEditorInput oldInput = getEditorInput();
				if (oldInput != null && oldInput instanceof IStorageEditorInput) {
					String storagePath = ((IStorageEditorInput) oldInput).getStorage().getFullPath().toOSString();
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
		
		ImageDescriptor imageDescriptor = input.getImageDescriptor();
		if (imageDescriptor != null) {
			setTitleImage(JFaceResources.getResources().createImageWithDefault(imageDescriptor));
		}
	}

	ISelectionChangedListener selectionListener;

	@Override
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
				selectionListener = new OutlineSelectionListener(outlinePage);
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
		if (ExternalFilesRegistry.getInstance().isEntryExist(path.toOSString())) {
			return ExternalFilesRegistry.getInstance().getFileEntry(path.toOSString());
		}
		//could be that it is an external file BUT was already removed !, check :
		else if (path.segmentCount() == 1) {
			return ExternalFileWrapper.createFile(path.toOSString());
		}

		//handle case of workspace file AND/OR an external file with more than 1 segment
		return ResourcesPlugin.getWorkspace().getRoot().getFile(path);
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
	@Override
	protected void handleCursorPositionChanged() {
		updateCursorDependentActions();
		super.handleCursorPositionChanged();
	}

	@Override
	protected void handlePreferenceStoreChanged(final PropertyChangeEvent event) {
		final String property = event.getProperty();
		try {
			if (PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIERS.equals(property) || PreferenceConstants.EDITOR_TEXT_HOVER_MODIFIER_MASKS.equals(property)) {
				updateHoverBehavior();
				return;
			}
			boolean newBooleanValue= false;
			Object newValue= event.getNewValue();
			if (newValue != null) {
				newBooleanValue= Boolean.valueOf(newValue.toString()).booleanValue();
			}
			if (PreferenceConstants.EDITOR_MARK_OCCURRENCES.equals(property)) {
				if (newBooleanValue != fMarkOccurrenceAnnotations) {
					fMarkOccurrenceAnnotations= newBooleanValue;
					if (!fMarkOccurrenceAnnotations)
						uninstallOccurrencesFinder();
					else
						installOccurrencesFinder(true);
				}
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_TYPE_OCCURRENCES.equals(property)) {
				fMarkTypeOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_METHOD_OCCURRENCES.equals(property)) {
				fMarkMethodOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_FUNCTION_OCCURRENCES.equals(property)) {
				fMarkFunctionOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_CONSTANT_OCCURRENCES.equals(property)) {
				fMarkConstantOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_GLOBAL_VARIABLE_OCCURRENCES.equals(property)) {
				fMarkGlobalVariableOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_LOCAL_VARIABLE_OCCURRENCES.equals(property)) {
				fMarkLocalVariableOccurrences= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_EXCEPTION_OCCURRENCES.equals(property)) {
				fMarkExceptions= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_METHOD_EXIT_POINTS.equals(property)) {
				fMarkMethodExitPoints= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_BREAK_CONTINUE_TARGETS.equals(property)) {
				fMarkBreakContinueTargets= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_IMPLEMENTORS.equals(property)) {
				fMarkImplementors= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_STICKY_OCCURRENCES.equals(property)) {
				fStickyOccurrenceAnnotations= newBooleanValue;
				return;
			}
			if (PreferenceConstants.EDITOR_MARK_HTML_TAGS.equals(property)) {
				fMarkHTMLTags= newBooleanValue;
				return;
			}
		} finally {
			super.handlePreferenceStoreChanged(event);
		}
	}

	@Override
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
						IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) region;
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
			final Iterator<String> e = fCursorActions.iterator();
			while (e.hasNext())
				updateAction(e.next());
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

	@Override
	protected StructuredTextViewer createStructedTextViewer(Composite parent, IVerticalRuler verticalRuler, int styles) {
		return new PHPStructuredTextViewer(this, parent, verticalRuler, getOverviewRuler(), isOverviewRulerVisible(), styles);
	}

	/*
	 * @see org.eclipse.ui.texteditor.AbstractTextEditor#performSave(boolean,
	 *      org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected void performSave(boolean overwrite, IProgressMonitor progressMonitor) {
		IDocumentProvider p = getDocumentProvider();
		if (p instanceof ISourceModuleDocumentProvider) {
			ISourceModuleDocumentProvider cp = (ISourceModuleDocumentProvider) p;
			cp.setSavePolicy(fSavePolicy);
		}
		try {
			super.performSave(overwrite, progressMonitor);
		} finally {
			if (p instanceof ISourceModuleDocumentProvider) {
				ISourceModuleDocumentProvider cp = (ISourceModuleDocumentProvider) p;
				cp.setSavePolicy(null);
			}
		}
	}	

	@Override
	public IDocumentProvider getDocumentProvider() {
		if (getEditorInput() instanceof LocalFileStorageEditorInput) {
			IDocumentProvider provider = LocalStorageModelProvider.getInstance();
			if (provider != null) {
				return provider;
			}
		}
		return super.getDocumentProvider();
	}

	/**
	 * IScriptReconcilingListener methods - reconcile listeners   
	 */
	private ListenerList fReconcilingListeners = new ListenerList(ListenerList.IDENTITY);
	public void addReconcileListener(IScriptReconcilingListener reconcileListener) {
		synchronized (fReconcilingListeners) {
			fReconcilingListeners.add(reconcileListener);
		}
	}

	public void removeReconcileListener(IScriptReconcilingListener reconcileListener) {
		synchronized (fReconcilingListeners) {
			fReconcilingListeners.remove(reconcileListener);
		}
	}

	public void aboutToBeReconciled() {

		// Notify AST provider
		PHPUiPlugin.getDefault().getASTProvider().aboutToBeReconciled((ISourceModule) getInputModelElement());

		// Notify listeners
		Object[] listeners = fReconcilingListeners.getListeners();
		for (int i = 0, length = listeners.length; i < length; ++i)
			((IScriptReconcilingListener) listeners[i]).aboutToBeReconciled();
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.java.IJavaReconcilingListener#reconciled(CompilationUnit,
	 *      boolean, IProgressMonitor)
	 * @since 3.0
	 */
	public void reconciled(Program ast, boolean forced, IProgressMonitor progressMonitor) {

		// see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=58245
		PHPUiPlugin phpPlugin= PHPUiPlugin.getDefault();
		if (phpPlugin == null)
			return;
		
		// Always notify AST provider
		ISourceModule inputModelElement = (ISourceModule) getInputModelElement();
		// TODO: notify AST provider
		phpPlugin.getASTProvider().reconciled(ast , inputModelElement, progressMonitor);

		// Notify listeners
		Object[] listeners = fReconcilingListeners.getListeners();
		for (int i = 0, length = listeners.length; i < length; ++i)
			((IScriptReconcilingListener) listeners[i]).reconciled(inputModelElement, forced, progressMonitor);
	}
	
	/**
	 * Returns the model element wrapped by this editors input.
	 * 
	 * @return the model element wrapped by this editors input.
	 * 
	 */
	public IModelElement getInputModelElement() {
		return EditorUtility.getEditorInputModelElement(this , false);
	}

	/**
	 * Returns the most narrow model element including the given offset.
	 * 
	 * @param offset
	 *            the offset inside of the requested element
	 * @return the most narrow model element
	 */
	protected IModelElement getElementAt(int offset) {
		return getElementAt(offset, true);
	}

	/**
	 * Returns the most narrow element including the given offset. If
	 * <code>reconcile</code> is <code>true</code> the editor's input
	 * element is reconciled in advance. If it is <code>false</code> this
	 * method only returns a result if the editor's input element does not need
	 * to be reconciled.
	 * 
	 * @param offset
	 *            the offset included by the retrieved element
	 * @param reconcile
	 *            <code>true</code> if working copy should be reconciled
	 * @return the most narrow element which includes the given offset
	 */
	protected IModelElement getElementAt(int offset, boolean reconcile) {
		ISourceModule unit = (ISourceModule) getInputModelElement();
		if (unit != null) {
			try {
				if (reconcile) {
					ScriptModelUtil.reconcile(unit);
					return unit.getElementAt(offset);
				} else if (unit.isConsistent())
					return unit.getElementAt(offset);
			} catch (ModelException x) {
				if (!x.isDoesNotExist())
					// DLTKUIPlugin.log(x.getStatus());
					System.err.println(x.getStatus());
				// nothing found, be tolerant and go on
			}
		}
		return null;
	}

	/**
	 * Support mark occurrences in PHP Editor
	 */
	
	/**
	 * Returns the lock object for the given annotation model.
	 *
	 * @param annotationModel the annotation model
	 * @return the annotation model's lock object
	 * @since 3.0
	 */
	private Object getLockObject(IAnnotationModel annotationModel) {
		if (annotationModel instanceof ISynchronizable) {
			Object lock= ((ISynchronizable)annotationModel).getLockObject();
			if (lock != null)
				return lock;
		}
		return annotationModel;
	}


	/**
	 * Updates the occurrences annotations based
	 * on the current selection.
	 *
	 * @param selection the text selection
	 * @param astRoot the compilation unit AST
	 * @since 3.0
	 */
	protected void updateOccurrenceAnnotations(ITextSelection selection, Program astRoot) {

		if (fOccurrencesFinderJob != null)
			fOccurrencesFinderJob.cancel();

		if (!fMarkOccurrenceAnnotations)
			return;

		if (astRoot == null || selection == null)
			return;

		IDocument document= getSourceViewer().getDocument();
		if (document == null)
			return;

		boolean hasChanged= false;
		if (document instanceof IDocumentExtension4) {
			int offset= selection.getOffset();
			long currentModificationStamp= ((IDocumentExtension4)document).getModificationStamp();
			IRegion markOccurrenceTargetRegion= fMarkOccurrenceTargetRegion;
			hasChanged= currentModificationStamp != fMarkOccurrenceModificationStamp;
			if (markOccurrenceTargetRegion != null && !hasChanged) {
				if (markOccurrenceTargetRegion.getOffset() <= offset && offset <= markOccurrenceTargetRegion.getOffset() + markOccurrenceTargetRegion.getLength())
					return;
			}
			fMarkOccurrenceTargetRegion= ScriptWordFinder.findWord(document, offset);
			fMarkOccurrenceModificationStamp= currentModificationStamp;
		}

		OccurrenceLocation[] locations= null;
		
		ASTNode selectedNode= NodeFinder.perform(astRoot, selection.getOffset(), selection.getLength());
		if (fMarkHTMLTags) {
			IOccurrencesFinder finder = OccurrencesFinderFactory.createHTMLOccurrencesFinder(document, selection.getOffset());
			if (finder.initialize(astRoot, selectedNode) == null) {
				locations = finder.getOccurrences();
			}
		}
		
		if (locations == null && fMarkExceptions) {
//          TODO : Implement Me!
//			ExceptionOccurrencesFinder finder= new ExceptionOccurrencesFinder();
//			if (finder.initialize(astRoot, selectedNode) == null) {
//				locations= finder.getOccurrences();
//			}
		}

		if (locations == null && fMarkMethodExitPoints) {
//          TODO : Implement Me!			
			MethodExitsFinder finder= new MethodExitsFinder();
			if (finder.initialize(astRoot, selectedNode) == null) {
				locations= finder.getOccurrences();
			}
		}

		if (locations == null && fMarkBreakContinueTargets) {
//          TODO : Implement Me!			
//			BreakContinueTargetFinder finder= new BreakContinueTargetFinder();
//			if (finder.initialize(astRoot, selectedNode) == null) {
//				locations= finder.getOccurrences();
//			}
		}
		
		if (locations == null && fMarkImplementors) {
			//TODO
//			IOccurrencesFinder finder= OccurrencesFinderFactory.createImplementorsOccurrencesFinder();
//			if (finder.initialize(astRoot, selectedNode) == null) {
//				locations= finder.getOccurrences();
//			}
		}

		if (locations == null && (selectedNode instanceof Identifier || selectedNode instanceof Scalar)) {
			int type = PhpElementConciliator.concile(selectedNode);
			if (markOccurrencesOfType(type)) {
				IOccurrencesFinder finder = OccurrencesFinderFactory.getOccurrencesFinder(type);
				if (finder != null) {
					if (finder.initialize(astRoot, selectedNode) == null) {
						locations = finder.getOccurrences();
					}
				}
			}
		}

		if (locations == null) {
			if (!fStickyOccurrenceAnnotations)
				removeOccurrenceAnnotations();
			else if (hasChanged) // check consistency of current annotations
				removeOccurrenceAnnotations();
			return;
		}

		fOccurrencesFinderJob= new OccurrencesFinderJob(document, locations, selection);
		//fOccurrencesFinderJob.setPriority(Job.DECORATE);
		//fOccurrencesFinderJob.setSystem(true);
		//fOccurrencesFinderJob.schedule();
		fOccurrencesFinderJob.run(new NullProgressMonitor());
	}
	
	protected void installOccurrencesFinder(boolean forceUpdate) {
		fMarkOccurrenceAnnotations= true;

		fPostSelectionListenerWithAST= new ISelectionListenerWithAST() {
			public void selectionChanged(IEditorPart part, ITextSelection selection, Program astRoot) {
				updateOccurrenceAnnotations(selection, astRoot);
			}
		};
		SelectionListenerWithASTManager.getDefault().addListener(this, fPostSelectionListenerWithAST);
		if (forceUpdate && getSelectionProvider() != null) {
			fForcedMarkOccurrencesSelection= getSelectionProvider().getSelection();
			IModelElement source = getInputModelElement();
			if (source.getElementType() == IModelElement.SOURCE_MODULE) {
				try {
					final Program ast = SharedASTProvider.getAST((ISourceModule) source, SharedASTProvider.WAIT_NO, getProgressMonitor());
					updateOccurrenceAnnotations((ITextSelection)fForcedMarkOccurrencesSelection, ast);
				} catch (ModelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (fOccurrencesFinderJobCanceler == null) {
			fOccurrencesFinderJobCanceler= new OccurrencesFinderJobCanceler();
			fOccurrencesFinderJobCanceler.install();
		}
	}

	protected void uninstallOccurrencesFinder() {
		fMarkOccurrenceAnnotations= false;

		if (fOccurrencesFinderJob != null) {
			fOccurrencesFinderJob.cancel();
			fOccurrencesFinderJob= null;
		}

		if (fOccurrencesFinderJobCanceler != null) {
			fOccurrencesFinderJobCanceler.uninstall();
			fOccurrencesFinderJobCanceler= null;
		}

		if (fPostSelectionListenerWithAST != null) {
			SelectionListenerWithASTManager.getDefault().removeListener(this, fPostSelectionListenerWithAST);
			fPostSelectionListenerWithAST= null;
		}

		removeOccurrenceAnnotations();
	}

	public boolean isMarkingOccurrences() {
		IPreferenceStore store= getPreferenceStore();
		return store != null && store.getBoolean(PreferenceConstants.EDITOR_MARK_OCCURRENCES);
	}

	/**
	 * Returns is the occurrences of the type should be marked.
	 * 
	 * @param type One of the {@link PhpElementConciliator} constants integer type. 
	 * @return True, if the type occurrences should be marked; False, otherwise.
	 */
	boolean markOccurrencesOfType(int type) {
		switch (type) {
			case PhpElementConciliator.CONCILIATOR_GLOBAL_VARIABLE:
				return fMarkGlobalVariableOccurrences;
			case PhpElementConciliator.CONCILIATOR_LOCAL_VARIABLE:
				return fMarkLocalVariableOccurrences;
			case PhpElementConciliator.CONCILIATOR_FUNCTION:
				return fMarkFunctionOccurrences;
			case PhpElementConciliator.CONCILIATOR_CLASSNAME:
				return fMarkTypeOccurrences;
			case PhpElementConciliator.CONCILIATOR_CONSTANT:
				return fMarkConstantOccurrences;
			case PhpElementConciliator.CONCILIATOR_CLASS_MEMBER:
				return fMarkMethodOccurrences;
			case PhpElementConciliator.CONCILIATOR_UNKNOWN:
			case PhpElementConciliator.CONCILIATOR_PROGRAM:
			default:
				return false;
		}
	}

	void removeOccurrenceAnnotations() {
		fMarkOccurrenceModificationStamp= IDocumentExtension4.UNKNOWN_MODIFICATION_STAMP;
		fMarkOccurrenceTargetRegion= null;

		IDocumentProvider documentProvider= getDocumentProvider();
		if (documentProvider == null)
			return;

		IAnnotationModel annotationModel= documentProvider.getAnnotationModel(getEditorInput());
		if (annotationModel == null || fOccurrenceAnnotations == null)
			return;

		synchronized (getLockObject(annotationModel)) {
			if (annotationModel instanceof IAnnotationModelExtension) {
				((IAnnotationModelExtension)annotationModel).replaceAnnotations(fOccurrenceAnnotations, null);
			} else {
				for (int i= 0, length= fOccurrenceAnnotations.length; i < length; i++)
					annotationModel.removeAnnotation(fOccurrenceAnnotations[i]);
			}
			fOccurrenceAnnotations= null;
		}
	}

	protected boolean isActivePart() {
		IWorkbenchPart part= getActivePart();
		return part != null && part.equals(this);
	}
	
	private IWorkbenchPart getActivePart() {
		IWorkbenchWindow window= getSite().getWorkbenchWindow();
		IPartService service= window.getPartService();
		IWorkbenchPart part= service.getActivePart();
		return part;
	}
	
	/**
	 * React to changed selection.
	 *
	 * @since 3.0
	 */
	protected void selectionChanged() {
		if (getSelectionProvider() == null)
			return;
		ISourceReference element= computeHighlightRangeSourceReference();
		// if (getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SYNC_OUTLINE_ON_CURSOR_MOVE))
		//	  synchronizeOutlinePage(element);
		setSelection(element, false);
		// if (!fSelectionChangedViaGotoAnnotation)
		// 	updateStatusLine();
		// fSelectionChangedViaGotoAnnotation= false;
	}
	
	/**
	 * Computes and returns the source reference that includes the caret and
	 * serves as provider for the outline page selection and the editor range
	 * indication.
	 * 
	 * @return the computed source reference
	 */
	protected ISourceReference computeHighlightRangeSourceReference() {
		ISourceViewer sourceViewer = getSourceViewer();
		if (sourceViewer == null)
			return null;
		StyledText styledText = sourceViewer.getTextWidget();
		if (styledText == null)
			return null;
		int caret = 0;
		if (sourceViewer instanceof ITextViewerExtension5) {
			ITextViewerExtension5 extension = (ITextViewerExtension5) sourceViewer;
			caret = extension.widgetOffset2ModelOffset(styledText
					.getCaretOffset());
		} else {
			int offset = sourceViewer.getVisibleRegion().getOffset();
			caret = offset + styledText.getCaretOffset();
		}
		IModelElement element = getElementAt(caret, false);
		if (!(element instanceof ISourceReference))
			return null;
		return (ISourceReference) element;
	}

	protected void setSelection(ISourceReference reference, boolean moveCursor) {
		if (getSelectionProvider() == null)
			return;
		ISelection selection = getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			// PR 39995: [navigation] Forward history cleared after going back
			// in navigation history:
			// mark only in navigation history if the cursor is being moved
			// (which it isn't if
			// this is called from a PostSelectionEvent that should only update
			// the magnet)
			if (moveCursor
					&& (textSelection.getOffset() != 0 || textSelection
							.getLength() != 0))
				markInNavigationHistory();
		}
		if (reference != null) {
			StyledText textWidget = null;
			ISourceViewer sourceViewer = getSourceViewer();
			if (sourceViewer != null)
				textWidget = sourceViewer.getTextWidget();
			if (textWidget == null)
				return;
			try {
				ISourceRange range = null;
				range = reference.getSourceRange();
				if (range == null)
					return;
				int offset = range.getOffset();
				int length = range.getLength();
				if (offset < 0 || length < 0)
					return;
				setHighlightRange(offset, length, moveCursor);
				if (!moveCursor)
					return;
				offset = -1;
				length = -1;
				if (reference instanceof IMember) {
					range = ((IMember) reference).getNameRange();
					if (range != null) {
						offset = range.getOffset();
						length = range.getLength();
					}
				}
				if (offset > -1 && length > 0) {
					try {
						textWidget.setRedraw(false);
						sourceViewer.revealRange(offset, length);
						sourceViewer.setSelectedRange(offset, length);
					} finally {
						textWidget.setRedraw(true);
					}
					markInNavigationHistory();
				}
			} catch (ModelException x) {
			} catch (IllegalArgumentException x) {
			}
		} else if (moveCursor) {
			resetHighlightRange();
			markInNavigationHistory();
		}
	}
}