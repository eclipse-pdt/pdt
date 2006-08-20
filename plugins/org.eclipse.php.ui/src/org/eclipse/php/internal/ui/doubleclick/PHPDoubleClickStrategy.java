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
package org.eclipse.php.internal.ui.doubleclick;

import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;
import org.w3c.dom.Node;

/**
 * This class was added in order to solve the problem of selecting variable name in the editor.
 * The default behaviour when double-clicking a variable, for instance $myVar, is to make only myVar selected - without the dollar sign.
 * This class fixes this behaviour.
 * 
 * @author guy.g
 *
 */
public class PHPDoubleClickStrategy extends DefaultTextDoubleClickStrategy {

	public void doubleClicked(ITextViewer textViewer) {
		IStructuredModel structuredModel = null;
		if (!(textViewer instanceof StructuredTextViewer)) {
			super.doubleClicked(textViewer);
			return;
		}
		StructuredTextViewer structuredTextViewer = (StructuredTextViewer) textViewer;
		try {
			structuredModel = StructuredModelManager.getModelManager().getExistingModelForRead(structuredTextViewer.getDocument());

			if (structuredModel == null) {
				super.doubleClicked(textViewer);
				return;
			}
			int caretPosition = textViewer.getSelectedRange().x;
			if (caretPosition < 0) {
				super.doubleClicked(textViewer);
				return;
			}

			Node node = (Node) structuredModel.getIndexedRegion(caretPosition);
			if (node == null) {
				super.doubleClicked(textViewer);
				return;
			}

			IStructuredDocumentRegion sdRegion = structuredModel.getStructuredDocument().getRegionAtCharacterOffset(caretPosition);
			if (sdRegion == null) {
				super.doubleClicked(textViewer);
				return;
			}
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(caretPosition);
			if (tRegion == null) {
				super.doubleClicked(textViewer);
				return;
			}
			if(tRegion.getType() == PHPRegionTypes.PHP_VARIABLE){
				structuredTextViewer.setSelectedRange(sdRegion.getStartOffset() + tRegion.getStart(), tRegion.getTextLength());
			} else {
				super.doubleClicked(textViewer);				
			}
		} finally {
			if (structuredModel != null)
				structuredModel.releaseFromRead();
		}

	}

}
