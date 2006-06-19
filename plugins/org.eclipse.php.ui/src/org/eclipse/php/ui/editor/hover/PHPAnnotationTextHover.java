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
package org.eclipse.php.ui.editor.hover;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.Logger;
import org.eclipse.php.core.documentModel.PHPEditorModel;
import org.eclipse.php.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.ui.util.PHPCodeDataHTMLDescriptionUtilities;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class PHPAnnotationTextHover extends AbstractPHPTextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			try {
				IStructuredDocument sDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(hoverRegion.getOffset());
				ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(hoverRegion.getOffset());
				if (sdRegion.getStartOffset() + textRegion.getTextEnd() >= hoverRegion.getOffset()) {
					CodeData codeData = CodeDataResolver.getCodeData(textViewer, sdRegion.getStartOffset() + textRegion.getTextEnd());
					if (codeData != null) {
						IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead (textViewer.getDocument());
						if (sModel instanceof PHPEditorModel) {
							try {
								PHPEditorModel editorModel = (PHPEditorModel) sModel;
								PHPProjectModel projectModel = editorModel.getProjectModel();
								return PHPCodeDataHTMLDescriptionUtilities.getHTMLHyperlinkDescriptionText(codeData, projectModel);
							} finally {
								sModel.releaseFromRead();
							}
						}
					}
				}
			} catch (BadLocationException e) {
				Logger.logException(e);
			}
		}
		return null;
	}
}
