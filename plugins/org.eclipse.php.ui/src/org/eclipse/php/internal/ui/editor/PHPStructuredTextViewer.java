package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.php.ui.editor.contentassist.IContentAssistProcessorForPHP;
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

public class PHPStructuredTextViewer extends StructuredTextViewer {

	private static final String FORMAT_DOCUMENT_TEXT = SSEUIMessages.Format_Document_UI_;
	private SourceViewerConfiguration config;
	private ITextEditor textEditor;
	private IInformationPresenter fOutlinePresenter;

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
				for (int i = 0; i < all.length; i++) {
					if (all[i] instanceof IContentAssistProcessorForPHP) {
						((IContentAssistProcessorForPHP) all[i]).explicitActivationRequest();
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
		} else {
			super.doOperation(operation);
		}
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

	protected IDocumentAdapter createDocumentAdapter() {
		return new StructuredDocumentToTextAdapterForPhp(getTextWidget());
	}

	public class StructuredDocumentToTextAdapterForPhp extends StructuredDocumentToTextAdapter {

		public StructuredDocumentToTextAdapterForPhp() {
			super();
		}

		public StructuredDocumentToTextAdapterForPhp(StyledText styledTextWidget) {
			super(styledTextWidget);
		}

		protected void redrawRegionChanged(RegionChangedEvent structuredDocumentEvent) {
			if (structuredDocumentEvent != null && structuredDocumentEvent.getRegion() != null && structuredDocumentEvent.getRegion().getType() == PHPRegionContext.PHP_CONTENT) {
				final PhpScriptRegion region = (PhpScriptRegion) structuredDocumentEvent.getRegion();
				if (region.isFullReparsed) {
					final TextRegionListImpl newList = new TextRegionListImpl();
					newList.add(region);
					final IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentEvent.getStructuredDocumentRegion();
					final IStructuredDocument structuredDocument = structuredDocumentEvent.getStructuredDocument();
					final RegionsReplacedEvent regionsReplacedEvent = new RegionsReplacedEvent(structuredDocument, structuredDocumentRegion, structuredDocumentRegion, null, newList, null, 0, 0);
					redrawRegionsReplaced(regionsReplacedEvent);
				} else {
					region.isFullReparsed = true;
				}
			}
			super.redrawRegionChanged(structuredDocumentEvent);
		}
	}

	/**
	 * We override this function in order to use content assist for php and not use the default one dictated by StructuredTextViewerConfiguration
	 */
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
		
		fOutlinePresenter= phpConfiguration.getOutlinePresenter(this);
		if (fOutlinePresenter != null)
			fOutlinePresenter.install(this);
	}
	
	/**
	 * override the parent method to prevent initialization of wrong
	 * fAnnotationHover specific instance 
	 */
	protected void ensureAnnotationHoverManagerInstalled() {
		if (fAnnotationHover instanceof PHPStructuredTextAnnotationHover) {
			super.ensureAnnotationHoverManagerInstalled();
		}
	}
	
	/**
	 * (non-Javadoc)
	 * @see org.eclipse.wst.sse.ui.internal.StructuredTextViewer#modelLine2WidgetLine(int)
	 * TODO: ask Seva why he put this here - we shouldn't handle things like this 
	 */
	public int modelLine2WidgetLine(int modelLine) {
		try {
			return super.modelLine2WidgetLine(modelLine);
		} catch (IllegalStateException e) {
			return -1;
		}
	}
}
