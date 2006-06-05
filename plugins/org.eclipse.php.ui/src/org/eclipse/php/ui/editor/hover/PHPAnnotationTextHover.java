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
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.ui.util.PHPElementLabels;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class PHPAnnotationTextHover extends AbstractPHPTextHover {
	private final long LABEL_FLAGS = PHPElementLabels.DEFAULT_QUALIFIED | PHPElementLabels.ROOT_POST_QUALIFIED | PHPElementLabels.APPEND_ROOT_PATH | PHPElementLabels.M_PARAMETER_TYPES | PHPElementLabels.M_PARAMETER_NAMES | PHPElementLabels.M_APP_RETURNTYPE | PHPElementLabels.F_APP_TYPE_SIGNATURE
		| PHPElementLabels.T_TYPE_PARAMETERS;

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			try {
				IStructuredDocument sDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(hoverRegion.getOffset());
				ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(hoverRegion.getOffset());
				if (textRegion.getTextEnd() >= hoverRegion.getOffset()) {
					CodeData codeData = CodeDataResolver.getCodeData(textViewer, textRegion.getTextEnd());
					if (codeData != null) {
						return PHPElementLabels.getElementLabel(codeData, LABEL_FLAGS);
					}
				}
			} catch (BadLocationException e) {
				Logger.logException(e);
			}
		}
		return null;
	}
}
