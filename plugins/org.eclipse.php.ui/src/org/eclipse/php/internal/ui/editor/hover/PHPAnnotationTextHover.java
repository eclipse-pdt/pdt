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
package org.eclipse.php.internal.ui.editor.hover;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.php.internal.core.documentModel.DOMModelForPHP;
import org.eclipse.php.internal.core.phpModel.parser.PHPProjectModel;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.internal.ui.util.PHPCodeDataHTMLDescriptionUtilities;
import org.eclipse.wst.sse.core.StructuredModelManager;
import org.eclipse.wst.sse.core.internal.provisional.IStructuredModel;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

public class PHPAnnotationTextHover extends AbstractPHPTextHover {

	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			IStructuredDocument sDoc = (IStructuredDocument) document;
			IStructuredModel sModel = StructuredModelManager.getModelManager().getExistingModelForRead(document);
			try {
				if (sModel instanceof DOMModelForPHP) {
					DOMModelForPHP editorModel = (DOMModelForPHP) sModel;
					CodeData[] codeDatas = CodeDataResolver.getInstance().resolve(sDoc, hoverRegion.getOffset(), editorModel);
					if (codeDatas.length != 0) {
						StringBuffer concatenatedInfo = new StringBuffer();
						for (int i = 0; i < codeDatas.length; ++i) {
							CodeData codeData = codeDatas[i];
							PHPProjectModel projectModel = editorModel.getProjectModel();
							concatenatedInfo.append(PHPCodeDataHTMLDescriptionUtilities.getHTMLHyperlinkDescriptionText(codeData, projectModel));
							if (i + 1 != codeDatas.length)
								concatenatedInfo.append("\n");
						}
						return concatenatedInfo.toString();
					}
				}
			} finally {
				if (sModel != null) {
					sModel.releaseFromRead();
				}
			}
		}
		return null;
	}
}
