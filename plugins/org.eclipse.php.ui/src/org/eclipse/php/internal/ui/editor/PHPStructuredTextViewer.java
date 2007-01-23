package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.formatter.FormattingContext;
import org.eclipse.jface.text.formatter.FormattingContextProperties;
import org.eclipse.jface.text.formatter.IContentFormatterExtension;
import org.eclipse.jface.text.formatter.IFormattingContext;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.undo.IStructuredTextUndoManager;
import org.eclipse.wst.sse.ui.internal.SSEUIMessages;
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
				IRegion region = new Region(0,getDocument().getLength());
				if (fContentFormatter instanceof IContentFormatterExtension) {
					IContentFormatterExtension extension = (IContentFormatterExtension) fContentFormatter;
					IFormattingContext context = new FormattingContext();
					context.setProperty(FormattingContextProperties.CONTEXT_DOCUMENT, Boolean.TRUE);
					context.setProperty(FormattingContextProperties.CONTEXT_REGION, region);
					extension.format(getDocument(), context);
				}
				else {
					fContentFormatter.format(getDocument(), region);
				}
			}
			finally {
				// end recording
				selection = getTextWidget().getSelection();
				cursorPosition = selection.x;
				selectionLength = selection.y - selection.x;
				endRecording(cursorPosition, selectionLength);
			}
		}
		else {
			super.doOperation(operation);
		}
	}
	
	private void beginRecording(String label, String description, int cursorPosition, int selectionLength) {
		IDocument doc = getDocument();
		if (doc instanceof IStructuredDocument) {
			IStructuredDocument structuredDocument = (IStructuredDocument) doc;
			IStructuredTextUndoManager undoManager = structuredDocument.getUndoManager();
			undoManager.beginRecording(this, label, description, cursorPosition, selectionLength);
		}
		else {
			// TODO: how to handle other document types?
		}
	}
	
	private void endRecording(int cursorPosition, int selectionLength) {
		IDocument doc = getDocument();
		if (doc instanceof IStructuredDocument) {
			IStructuredDocument structuredDocument = (IStructuredDocument) doc;
			IStructuredTextUndoManager undoManager = structuredDocument.getUndoManager();
			undoManager.endRecording(this, cursorPosition, selectionLength);
		}
		else {
			// TODO: how to handle other document types?
		}
	}

}
