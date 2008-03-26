package org.eclipse.php.internal.ui.search;

import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.ast.nodes.ASTNode;
import org.eclipse.php.internal.core.ast.nodes.Program;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.IndexedRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.xml.core.internal.provisional.document.IDOMElement;

/**
 * HTML matching pairs occurrences finder.
 * 
 * @author shalom
 *
 */
public class HTMLOccurrencesFinder extends AbstractOccurrencesFinder {

	public static final String ID = "HTMLOccurrencesFinder"; //$NON-NLS-1$
	private String htmlTag = "HTML tag";
	private int offset;
	private IStructuredDocument document;

	/**
	 * Constructs a new {@link HTMLOccurrencesFinder} with a given {@link IStructuredDocument} and 
	 * the selected offset in the document.
	 *
	 * @param document An {@link IStructuredDocument}
	 * @param selectionOffset
	 */
	public HTMLOccurrencesFinder(IDocument document, int selectionOffset) {
		if (document instanceof IStructuredDocument) {
			this.document = (IStructuredDocument) document;
		}
		this.offset = selectionOffset;
	}

	/**
	 * The initialize in this case just verify the inputs that we got in the constructor, and that
	 * the node is an ASTNode.IN_LINE_HTML.
	 */
	public String initialize(Program root, ASTNode node) {
		if (document != null && offset >= 0 && node != null && ASTNode.IN_LINE_HTML == node.getType()) {
			return null;
		}
		fDescription = "OccurrencesFinder_occurrence_description";
		return fDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#findOccurrences()
	 */
	protected void findOccurrences() {
		IStructuredDocument structuredDocument = ((IStructuredDocument) document);
		IStructuredDocumentRegion region = structuredDocument.getRegionAtCharacterOffset(offset);
		htmlTag = region.getFullText();
		fDescription = Messages.format(BASE_DESCRIPTION, htmlTag);
		if (region.isEnded()) {
			IStructuredModel existingModelForRead = null;
			try {
				existingModelForRead = StructuredModelManager.getModelManager().getExistingModelForRead(document);
				if (existingModelForRead instanceof DOMModelForPHP) {
					DOMModelForPHP domModelForPHP = (DOMModelForPHP) existingModelForRead;
					IndexedRegion indexedRegion = domModelForPHP.getIndexedRegion(offset);
					if (indexedRegion instanceof IDOMElement) {
						IDOMElement domElement = (IDOMElement) indexedRegion;
						IStructuredDocumentRegion endStructuredDocumentRegion = domElement.getEndStructuredDocumentRegion();
						IStructuredDocumentRegion startStructuredDocumentRegion = domElement.getStartStructuredDocumentRegion();
						if (endStructuredDocumentRegion != null && startStructuredDocumentRegion != null) {
							// mark the occurrences only when the HTML tag has a closing tag
							fResult.add(new OccurrenceLocation(startStructuredDocumentRegion.getStart(), startStructuredDocumentRegion.getLength(), getOccurrenceReadWriteType(null), fDescription));
							fResult.add(new OccurrenceLocation(endStructuredDocumentRegion.getStart(), endStructuredDocumentRegion.getLength(), getOccurrenceReadWriteType(null), fDescription));
						}
					}

				}
			} finally {
				if (existingModelForRead != null) {
					existingModelForRead.releaseFromRead();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.AbstractOccurrencesFinder#getOccurrenceReadWriteType(org.eclipse.php.internal.core.ast.nodes.ASTNode)
	 */
	protected int getOccurrenceReadWriteType(ASTNode node) {
		return IOccurrencesFinder.F_READ_OCCURRENCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getElementName()
	 */
	public String getElementName() {
		return htmlTag;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.search.IOccurrencesFinder#getID()
	 */
	public String getID() {
		return ID;
	}
}
