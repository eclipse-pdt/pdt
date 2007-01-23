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

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.information.IInformationProviderExtension2;
import org.eclipse.php.internal.core.phpModel.PHPModelUtil;
import org.eclipse.php.internal.core.phpModel.phpElementData.CodeData;
import org.eclipse.php.internal.core.phpModel.phpElementData.PHPVariableData;
import org.eclipse.php.internal.core.phpModel.phpElementData.UserData;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;
import org.eclipse.php.internal.ui.util.CodeDataResolver;
import org.eclipse.php.internal.ui.util.EditorUtility;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.ui.internal.StructuredTextViewer;

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
					final CodeData codeData = CodeDataResolver.getCodeData(textViewer, sdRegion.getStartOffset() + textRegion.getTextEnd());
					if (codeData != null && !(codeData instanceof PHPVariableData)) {
						UserData userData = codeData.getUserData();
						if (userData != null) {
							// if this is an open resource get the data from the document
							// else get the file from disk
							// REMARK: since the editor is accessiable ONLY from the Display thread
							// we need to use Display.sync() to get the actual data from the file
							final FindText findText = new FindText(codeData);
							Display.getDefault().syncExec(findText);
							final String text = findText.getText();
							
							// if the text is in one of the editors - fetch it from the editor source
							if (text != null) {
								return formatHoverInfo(text);
							} else { // else just go to the resource and find it
								IFile file = (IFile) PHPModelUtil.getResource(codeData);// ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(userData.getFileName()));
								if (file != null) {
									BufferedReader r = new BufferedReader(new InputStreamReader(file.getContents()));
									int startPosition = userData.getStartPosition();
									int len = userData.getEndPosition() - startPosition;
									char[] buf = new char[len];
									r.skip(startPosition);
									r.read(buf, 0, len);
									r.close();
									return formatHoverInfo(new String(buf));
								}
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
	
	
	private static class FindText implements Runnable {

		final CodeData codeData;
		private String text = null;
		
		public FindText(CodeData codeData) {
			this.codeData = codeData;
		}
		
		public void run()  {
			final IEditorPart openInEditor = EditorUtility.isOpenInEditor(codeData);
			if (openInEditor == null || !(openInEditor instanceof PHPStructuredEditor)) {
				return;
			}
			final StructuredTextViewer textViewer = ((PHPStructuredEditor) openInEditor).getTextViewer();
			final IDocument document = textViewer.getDocument();
			final UserData userData = codeData.getUserData();
			if (userData == null || document == null) {
				return;
			}
			int startPosition = userData.getStartPosition();
			int len = userData.getEndPosition() - startPosition;

			try {
				this.text = document.get(startPosition, len);
			} catch (BadLocationException e) {
				this.text = null;
			}
		}

		public String getText() {
			return text;
		}
	}
	

	public String formatHoverInfo(String info) {
		info = info.trim();
		String[] lines = info.split("[\r\n]"); //$NON-NLS-1$
		if (lines.length > 0) {
			String lastLine = lines[lines.length - 1];
			int numCharsToStrip = 0;
			while (Character.isWhitespace(lastLine.charAt(numCharsToStrip))) {
				numCharsToStrip++;
			}
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < lines.length; ++i) {
				int actuallyStrip = 0;
				while (actuallyStrip < numCharsToStrip && actuallyStrip < lines[i].length()) {
					if (!Character.isWhitespace(lines[i].charAt(actuallyStrip))) {
						break;
					}
					actuallyStrip++;
				}
				buf.append(lines[i].substring(actuallyStrip));
				buf.append("\n"); //$NON-NLS-1$
			}
			info = buf.toString();
		}
		return info;
	}
}