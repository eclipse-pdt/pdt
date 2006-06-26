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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.Logger;
import org.eclipse.php.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.text.PHPCodeReader;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class PHPSourceTextHover extends AbstractPHPTextHover implements IInformationProviderExtension2, ITextHoverExtension {
	/**
	 * The hover control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fHoverControlCreator;
	
	/**
	 * The presentation control creator.
	 * 
	 * @since 3.2
	 */
	private IInformationControlCreator fPresenterControlCreator;
	
	/*
	 * @see IInformationProviderExtension2#getInformationPresenterControlCreator()
	 * @since 3.1
	 * This is the format of the window on focus 
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if (fPresenterControlCreator == null) {
			fPresenterControlCreator = new AbstractReusableInformationControlCreator() {

				/*
				 * @see org.eclipse.jdt.internal.ui.text.java.hover.AbstractReusableInformationControlCreator#doCreateInformationControl(org.eclipse.swt.widgets.Shell)
				 */
				public IInformationControl doCreateInformationControl(Shell parent) {
					int shellStyle = SWT.RESIZE | SWT.TOOL;
					int style = SWT.V_SCROLL | SWT.H_SCROLL;
					return new PHPSourceViewerInformationControl(parent, shellStyle, style); 
				}
			};
		}
		return fPresenterControlCreator;
	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 * @since 3.2 - This is the format of the window on hover
	 */
	public IInformationControlCreator getHoverControlCreator() {
		if (fHoverControlCreator == null) {
			fHoverControlCreator = new AbstractReusableInformationControlCreator() {
				/*
				 * @see org.eclipse.jdt.internal.ui.text.java.hover.AbstractReusableInformationControlCreator#doCreateInformationControl(org.eclipse.swt.widgets.Shell)
				 */
				public IInformationControl doCreateInformationControl(Shell parent) {
					return new PHPSourceViewerInformationControl(parent, SWT.NONE, getTooltipAffordanceString());
				}
			};
		}
		return fHoverControlCreator;
	}
	
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		IDocument document = textViewer.getDocument();
		if (document instanceof IStructuredDocument) {
			try {
				IStructuredDocument sDoc = (IStructuredDocument) document;
				IStructuredDocumentRegion sdRegion = sDoc.getRegionAtCharacterOffset(hoverRegion.getOffset());
				ITextRegion textRegion = sdRegion.getRegionAtCharacterOffset(hoverRegion.getOffset());
				if (sdRegion.getStartOffset() + textRegion.getTextEnd() >= hoverRegion.getOffset()) {
					CodeData codeData = CodeDataResolver.getCodeData(textViewer, sdRegion.getStartOffset() + textRegion.getTextEnd());
					if (codeData != null && !(codeData instanceof PHPVariableData)) {
						UserData userData = codeData.getUserData();
						if (userData != null) {
							IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(userData.getFileName()));
							if (file != null) {
								BufferedReader r = new BufferedReader(new InputStreamReader(file.getContents()));
								r.skip(userData.getStartPosition());
								StringBuffer buf = new StringBuffer();
								String line;
								while ((line = r.readLine()) != null) {
									buf.append(line);
									buf.append("\n");
								}
								r.close();
								return cutStatement(buf.toString());
							}
						}
					}
				}
			} catch (Exception e) {
				Logger.logException(e);
			}
		}
		return null;
	}
	
	private String cutStatement(String code) {
		try {
			PHPCodeReader codeReader = new PHPCodeReader();
			Document doc = new Document(code);
			codeReader.configureForwardReader(doc, 0, doc.getLength(), true, true);
			
			int curlyBraces = 0;
			
			int ch = codeReader.read();
			while (ch != PHPCodeReader.EOF) {
				if (ch == ';' && curlyBraces == 0) {
					break;
				}
				if (ch == '{') {
					curlyBraces ++;
				}
				if (ch == '}') {
					curlyBraces --;
					if (curlyBraces == 0) {
						break;
					}
				}
				ch = codeReader.read();
			}
			code = code.substring(0, codeReader.getOffset()+1);
		} catch (Exception e) {
		}
		return code;
	}
}