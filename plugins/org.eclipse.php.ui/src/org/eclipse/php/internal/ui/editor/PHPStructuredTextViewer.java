package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentAdapter;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.PhpScriptRegion;
import org.eclipse.php.internal.ui.editor.configuration.PHPStructuredTextViewerConfiguration;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.provisional.events.RegionChangedEvent;
import org.eclipse.wst.sse.core.internal.provisional.events.RegionsReplacedEvent;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.text.TextRegionListImpl;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
import org.eclipse.wst.sse.ui.internal.StructuredDocumentToTextAdapter;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

public class PHPStructuredTextViewer extends StructuredTextViewer {

	private static final String FORMAT_DOCUMENT_TEXT = SSEUIMessages.Format_Document_UI_; //$NON-NLS-1$

	public PHPStructuredTextViewer(Composite parent, IVerticalRuler verticalRuler, IOverviewRuler overviewRuler, boolean showAnnotationsOverview, int styles) {
		super(parent, verticalRuler, overviewRuler, showAnnotationsOverview, styles);
	}

	/**
	 * This method overrides WST since sometimes we get a subset of the document and NOT the whole document,
	 * although the case is FORMAT_DOCUMENT. In all other cases we call the parent method.
	 */
	public void doOperation(int operation) {
		Point selection = getTextWidget().getSelection();
		int cursorPosition = selection.x;
		int selectionLength = selection.y - selection.x;
		if (operation == FORMAT_DOCUMENT) {
			try {
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
				cursorPosition = selection.x;
				selectionLength = selection.y - selection.x;
				endRecording(cursorPosition, selectionLength);
			}
		} else if (operation == PASTE) {
			super.doOperation(operation);
			doOperation(FORMAT_ACTIVE_ELEMENTS);
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
	 * We override this function in order to use content assist for php 
	 * and not use the defualt one dictated by StructuredTextViewerConfiguration 
	 */
	public void configure(SourceViewerConfiguration configuration) {
		IContentAssistant oldContentAssistant = fContentAssistant;
		super.configure(configuration);

		if (!(configuration instanceof PHPStructuredTextViewerConfiguration)) {
			return;
		}
		PHPStructuredTextViewerConfiguration phpConfiguration = (PHPStructuredTextViewerConfiguration) configuration;
		IContentAssistant newAssistant = configuration.getContentAssistant(this);
		newAssistant.uninstall();
		newAssistant = phpConfiguration.getPHPContentAssistant(this);
		if (newAssistant != oldContentAssistant || newAssistant == null || oldContentAssistant == null) {
			if (fContentAssistant != null)
				fContentAssistant.uninstall();

			fContentAssistant = newAssistant;

			if (fContentAssistant != null) {
				fContentAssistant.install(this);
				fContentAssistantInstalled = true;
			} else {
				// 248036 - disable the content assist operation if no content assistant
				enableOperation(CONTENTASSIST_PROPOSALS, false);
			}
		}

	}

}
