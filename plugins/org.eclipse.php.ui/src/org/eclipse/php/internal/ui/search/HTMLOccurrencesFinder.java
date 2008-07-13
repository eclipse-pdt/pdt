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
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
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
		if (document != null && offset > 0 && node != null && ASTNode.IN_LINE_HTML == node.getType()) {
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
		if (region.getRegions().size() > 1) {
			htmlTag = region.getText(region.getRegions().get(1));
		} else {
			htmlTag = region.getFullText();
		}
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
						if (endStructuredDocumentRegion != null && startStructuredDocumentRegion != null && endStructuredDocumentRegion.getRegions().size() > 1 && startStructuredDocumentRegion.getRegions().size() > 1) {
							ITextRegion innerEndTag = endStructuredDocumentRegion.getRegions().get(1);
							ITextRegion innerStartTag = startStructuredDocumentRegion.getRegions().get(1);
							// mark the occurrences only when the HTML tag has a closing tag
							fDescription = Messages.format(BASE_DESCRIPTION, startStructuredDocumentRegion.getFullText());
							fResult.add(new OccurrenceLocation(startStructuredDocumentRegion.getStart() + innerStartTag.getStart(), innerStartTag.getTextLength(), getOccurrenceType(null), fDescription));
							fResult.add(new OccurrenceLocation(endStructuredDocumentRegion.getStart() + innerEndTag.getStart(), innerEndTag.getTextLength(), getOccurrenceType(null), fDescription));
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
	protected int getOccurrenceType(ASTNode node) {
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
