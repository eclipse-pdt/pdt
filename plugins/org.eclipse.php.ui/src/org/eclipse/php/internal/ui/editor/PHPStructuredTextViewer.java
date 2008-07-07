/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor;

import java.util.ArrayList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextPresentationListener;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.projection.ProjectionMapping;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.AnnotationRulerColumn;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.IVerticalRulerColumn;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.php.internal.ui.editor.contentassist.PHPCompletionProcessor;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.events.RegionChangedEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.RegionsReplacedEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.text.TextRegionListImpl;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.StructuredDocumentToTextAdapter;
import org.eclipse.wst.sse.ui.internal.StructuredTextAnnotationHover;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.eclipse.wst.sse.ui.internal.reconcile.StructuredRegionProcessor;

public class PHPStructuredTextViewer extends StructuredTextViewer {

	/**
	 * Text operation code for requesting the hierarchy for the current input.
	 */
	public static final int SHOW_HIERARCHY = 53;
	private static final String FORMAT_DOCUMENT_TEXT = SSEUIMessages.Format_Document_UI_;

	private SourceViewerConfiguration config;
	private ITextEditor textEditor;
	private IInformationPresenter fOutlinePresenter;
	private IInformationPresenter fHierarchyPresenter;

	private IAnnotationHover fProjectionAnnotationHover;

	public PHPStructuredTextViewer(Composite parent, IVerticalRuler verticalRuler, IOverviewRuler overviewRuler, boolean showAnnotationsOverview, int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview, styles);
	}

	public PHPStructuredTextViewer(ITextEditor textEditor, Composite parent, IVerticalRuler verticalRuler, IOverviewRuler overviewRuler, boolean showAnnotationsOverview, int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview, styles);
		this.textEditor = textEditor;
	}

	public ITextEditor getTextEditor() {
		return textEditor;
	}

	/**
	 * This method overrides WST since sometimes we get a subset of the document and NOT the whole document, although the case is FORMAT_DOCUMENT. In all other cases we call the parent method.
	 */
	@Override
	public void doOperation(int operation) {
		Point selection = getTextWidget().getSelection();
		int cursorPosition = selection.x;
		// save the last cursor position and the top visible line.
		int selectionLength = selection.y - selection.x;
		int topLine = getTextWidget().getTopIndex();
		if (operation == FORMAT_DOCUMENT) {
			try {
				setRedraw(false);
				// begin recording
				beginRecording(FORMAT_DOCUMENT_TEXT, FORMAT_DOCUMENT_TEXT, cursorPosition, selectionLength);

				// format the whole document !
				IRegion region = new Region(0, getDocument().getLength());
				if (fContentFormatter instanceof IContentFormatterExtension) {
					IContentFormatterExtension extension = (IContentFormatterExtension) fContentFormatter;
					IFormattingContext context = new FormattingContext();
					context.setProperty(FormattingContextProperties.CONTEXT_DOCUMENT, Boolean.TRUE);
					context.setProperty(FormattingContextProperties.CONTEXT_REGION, region);
					extension.format(getDocument(), context);
				} else {
					fContentFormatter.format(getDocument(), region);
				}
			} finally {
				// end recording
				selection = getTextWidget().getSelection();

				selectionLength = selection.y - selection.x;
				endRecording(cursorPosition, selectionLength);
				// return the cursor to its original position after the formatter change its position.
				getTextWidget().setSelection(cursorPosition);
				getTextWidget().setTopIndex(topLine);
				setRedraw(true);
			}
		} else if (operation == PASTE) {
			super.doOperation(operation);

			// IStructuredDocument sDoc = (IStructuredDocument) getDocument();
			// IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(selection.x);
			// ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(selection.x);
			//
			// boolean shouldFormat = false;
			//
			// if (textRegion instanceof ITextRegionContainer) {
			// textRegion = ((ITextRegionContainer) textRegion).getRegionAtCharacterOffset(selection.x);
			// if (textRegion.getType() == PHPRegionContext.PHP_OPEN || textRegion.getType() ==
			// PHPRegionContext.PHP_CLOSE || textRegion instanceof PhpScriptRegion) {
			// shouldFormat = true;
			// }
			// } else if (textRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			// shouldFormat = true;
			// }
			// if(shouldFormat) {
			// TextTransfer plainTextTransfer = TextTransfer.getInstance();
			// String text = (String) new Clipboard(getTextWidget().getDisplay()).getContents(plainTextTransfer,
			// DND.CLIPBOARD);
			// IRegion region = new Region(selection.x, text.length());
			// ((IStructuredDocument) getDocument()).getUndoManager().disableUndoManagement();
			// fContentFormatter.format(getDocument(), region);
			// ((IStructuredDocument) getDocument()).getUndoManager().enableUndoManagement();
			// }
		} else if (operation == CONTENTASSIST_PROPOSALS) {
			// notifing the processors that the next request for completion is an explicit request
			if (config != null) {
				PHPStructuredTextViewerConfiguration structuredTextViewerConfiguration = (PHPStructuredTextViewerConfiguration) config;
				IContentAssistProcessor[] all = structuredTextViewerConfiguration.getContentAssistProcessors(this, PHPPartitionTypes.PHP_DEFAULT);
				for (IContentAssistProcessor element : all) {
					if (element instanceof PHPCompletionProcessor) {
						((PHPCompletionProcessor) element).setExplicitRequest(true);
					}
				}
			}
			super.doOperation(operation);
			// }
			// else if (operation == CONTENTASSIST_CONTEXT_INFORMATION) {
			// if (fContentAssistant != null) {
			// String err = fContentAssistant.showContextInformation();
			// PlatformStatusLineUtil.displayErrorMessage(err);
			// PlatformStatusLineUtil.addOneTimeClearListener();
			// }
		} else if (operation == QUICK_ASSIST) {
			if (fOutlinePresenter != null) {
				fOutlinePresenter.showInformation();
			}
		} else if (operation == SHIFT_LEFT) {
			shift(false, false, true);
		} else if (operation == SHOW_HIERARCHY) {
			if (fHierarchyPresenter != null) {
				fHierarchyPresenter.showInformation();
			}
		} else {
			super.doOperation(operation);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.StructuredTextViewer#canDoOperation(int)
	 */
	public boolean canDoOperation(int operation) {
		if (operation == SHOW_HIERARCHY) {
			return fHierarchyPresenter != null;
		}
		return super.canDoOperation(operation);
	}

	private void beginRecording(String label, String description, int cursorPosition, int selectionLength) {
		IDocument doc = getDocument();
		if (doc instanceof IStructuredDocument) {
			IStructuredDocument structuredDocument = (IStructuredDocument) doc;
			IStructuredTextUndoManager undoManager = structuredDocument.getUndoManager();
			undoManager.beginRecording(this, label, description, cursorPosition, selectionLength);
		} else {
			// TODO: how to handle other document types?
		}
	}

	private void endRecording(int cursorPosition, int selectionLength) {
		IDocument doc = getDocument();
		if (doc instanceof IStructuredDocument) {
			IStructuredDocument structuredDocument = (IStructuredDocument) doc;
			IStructuredTextUndoManager undoManager = structuredDocument.getUndoManager();
			undoManager.endRecording(this, cursorPosition, selectionLength);
		} else {
			// TODO: how to handle other document types?
		}
	}

	@Override
	protected IDocumentAdapter createDocumentAdapter() {
		return new StructuredDocumentToTextAdapterForPhp(getTextWidget());
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.source.projection.ProjectionViewer#addVerticalRulerColumn(org.eclipse.jface.text.source.IVerticalRulerColumn)
	 *
	 * This method is only called to add Projection ruler column.
	 * It's actually a hack to override Projection presentation (information control) in order to enable syntax highlighting
	 */
	@Override
	public void addVerticalRulerColumn(IVerticalRulerColumn column) {
		// bug #210211 fix
		if (fProjectionAnnotationHover == null) {
			fProjectionAnnotationHover = new PHPStructuredTextProjectionAnnotationHover();
		}
		((AnnotationRulerColumn) column).setHover(fProjectionAnnotationHover);
		super.addVerticalRulerColumn(column);
	}

	public class StructuredDocumentToTextAdapterForPhp extends StructuredDocumentToTextAdapter {

		public StructuredDocumentToTextAdapterForPhp() {
			super();
		}

		public StructuredDocumentToTextAdapterForPhp(StyledText styledTextWidget) {
			super(styledTextWidget);
		}

		@Override
		protected void redrawRegionChanged(RegionChangedEvent structuredDocumentEvent) {
			if (structuredDocumentEvent != null && structuredDocumentEvent.getRegion() != null && structuredDocumentEvent.getRegion().getType() == PHPRegionContext.PHP_CONTENT) {
				final IPhpScriptRegion region = (IPhpScriptRegion) structuredDocumentEvent.getRegion();
				if (region.isFullReparsed()) {
					final TextRegionListImpl newList = new TextRegionListImpl();
					newList.add(region);
					final IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentEvent.getStructuredDocumentRegion();
					final IStructuredDocument structuredDocument = structuredDocumentEvent.getStructuredDocument();
					final RegionsReplacedEvent regionsReplacedEvent = new RegionsReplacedEvent(structuredDocument, structuredDocumentRegion, structuredDocumentRegion, null, newList, null, 0, 0);
					redrawRegionsReplaced(regionsReplacedEvent);
				} else {
					region.setFullReparsed(true);
				}
			}
			super.redrawRegionChanged(structuredDocumentEvent);
		}
	}

	/**
	 * We override this function in order to use content assist for php and not use the default one dictated by StructuredTextViewerConfiguration
	 */
	@Override
	public void configure(SourceViewerConfiguration configuration) {

		super.configure(configuration);

		// release old annotation hover before setting new one
		if (fAnnotationHover instanceof StructuredTextAnnotationHover) {
			((StructuredTextAnnotationHover) fAnnotationHover).release();
		}
		// set PHP fAnnotationHover and initial the AnnotationHoverManager
		setAnnotationHover(new PHPStructuredTextAnnotationHover());

		ensureAnnotationHoverManagerInstalled();

		if (!(configuration instanceof PHPStructuredTextViewerConfiguration)) {
			return;
		}
		config = configuration;

		PHPStructuredTextViewerConfiguration phpConfiguration = (PHPStructuredTextViewerConfiguration) configuration;
		IContentAssistant newPHPAssistant = phpConfiguration.getPHPContentAssistant(this, true);

		// Uninstall content assistant created in super:
		if (fContentAssistant != null) {
			fContentAssistant.uninstall();
		}

		// Assign, and configure our content assistant:
		fContentAssistant = newPHPAssistant;
		if (fContentAssistant != null) {
			fContentAssistant.install(this);
			fContentAssistantInstalled = true;
		} else {
			// 248036 - disable the content assist operation if no content assistant
			enableOperation(CONTENTASSIST_PROPOSALS, false);
		}

		fOutlinePresenter = phpConfiguration.getOutlinePresenter(this);
		if (fOutlinePresenter != null) {
			fOutlinePresenter.install(this);
		}

		fHierarchyPresenter = phpConfiguration.getHierarchyPresenter(this, true);
		if (fHierarchyPresenter != null) {
			fHierarchyPresenter.install(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.StructuredTextViewer#unconfigure()
	 */
	public void unconfigure() {
		if (fHierarchyPresenter != null) {
			fHierarchyPresenter.uninstall();
			fHierarchyPresenter = null;
		}
		if (fOutlinePresenter != null) {
			fOutlinePresenter.uninstall();
			fOutlinePresenter = null;
		}
		super.unconfigure();
	}

	/**
	 * override the parent method to prevent initialization of wrong
	 * fAnnotationHover specific instance
	 */
	@Override
	protected void ensureAnnotationHoverManagerInstalled() {
		if (fAnnotationHover instanceof PHPStructuredTextAnnotationHover) {
			super.ensureAnnotationHoverManagerInstalled();
		}
	}

	/**
	 * (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.StructuredTextViewer#modelLine2WidgetLine(int)
	 * Workaround for bug #195600 IllegalState is thrown by {@link ProjectionMapping#toImageLine(int)}
	 */
	@Override
	public int modelLine2WidgetLine(int modelLine) {
		try {
			return super.modelLine2WidgetLine(modelLine);
		} catch (IllegalStateException e) {
			return -1;
		}
	}

	/** (non-Javadoc)
	 * @see org.eclipse.jface.text.TextViewer#getClosestWidgetLineForModelLine(int)
	 * Workaround for bug #195600 IllegalState is thrown by {@link ProjectionMapping#toImageLine(int)}
	 */
	@Override
	protected int getClosestWidgetLineForModelLine(int modelLine) {
		try {
			return super.getClosestWidgetLineForModelLine(modelLine);
		} catch (IllegalStateException e) {
			return -1;
		}
	}

	/**
	 * Reconciles the whole document (to re-run PHPValidator)
	 */
	public void reconcile() {
		((StructuredRegionProcessor) fReconciler).processDirtyRegion(new DirtyRegion(0, getDocument().getLength(), DirtyRegion.INSERT, getDocument().get()));

	}

	/**
	 * Sets the given reconciler.
	 * 
	 * @param reconciler
	 *            the reconciler
	 *
	 */
	void setReconciler(IReconciler reconciler) {
		fReconciler = reconciler;
	}

	/**
	 * Returns the reconciler.
	 * 
	 * @return the reconciler or <code>null</code> if not set
	 *
	 */
	IReconciler getReconciler() {
		return fReconciler;
	}
	
	/**
	 * Prepends the text presentation listener at the beginning of the viewer's
	 * list of text presentation listeners.  If the listener is already registered
	 * with the viewer this call moves the listener to the beginning of
	 * the list.
	 *
	 * @param listener the text presentation listener
	 * @since 3.0
	 */
	public void prependTextPresentationListener(ITextPresentationListener listener) {

		Assert.isNotNull(listener);

		if (fTextPresentationListeners == null)
			fTextPresentationListeners= new ArrayList();

		fTextPresentationListeners.remove(listener);
		fTextPresentationListeners.add(0, listener);
	}	
}
