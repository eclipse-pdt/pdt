package org.eclipse.php.internal.ui.actions;

import java.util.ResourceBundle;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.xml.ui.internal.actions.RemoveBlockCommentActionXMLDelegate;

public class RemoveBlockCommentActionDelegate extends RemoveBlockCommentAction {

	private RemoveBlockCommentActionXMLDelegate xmlBlockCommentAction;
	private boolean isPHPSelection;
	
	public RemoveBlockCommentActionDelegate(ResourceBundle bundle, String prefix, ITextEditor editor) {
		super(bundle, prefix, editor);
		
		xmlBlockCommentAction = new RemoveBlockCommentActionXMLDelegate();
		xmlBlockCommentAction.setActiveEditor(null, editor);
	}

	protected boolean isValidSelection(ITextSelection selection, IDocumentExtension3 docExtension) {
		isPHPSelection = false;
		
		if (super.isValidSelection(selection, docExtension)) {
			isPHPSelection = true;
			return true;
		} else {
			int offset = selection.getOffset();
			if (docExtension instanceof IStructuredDocument) {
				IStructuredDocument sDoc = (IStructuredDocument) docExtension;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(offset);
				if (sdRegion == null) {
					return false;
				}
				ITextRegion region = sdRegion.getRegionAtCharacterOffset(offset);
				if (region != null && region.getType() != PHPRegionContext.PHP_CONTENT) {
					return true;
				}
			}
		}
		return false;
	}

	protected void runInternal(ITextSelection selection, IDocumentExtension3 docExtension) throws BadLocationException, BadPartitioningException {
		if (isPHPSelection) {
			super.runInternal(selection, docExtension);
		} else {
			xmlBlockCommentAction.run(null);
		}
	}
}
