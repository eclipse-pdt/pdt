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
package org.eclipse.php.internal.ui.editor.hyperlink;

import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.internal.ui.text.ScriptWordFinder;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.dltk.ui.infoviews.ModelElementArray;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.ui.editor.hover.IHyperlinkDetectorForPHP;
import org.eclipse.ui.IEditorPart;
import org.eclipse.wst.xml.core.internal.Logger;

public class PHPHyperlinkDetector implements IHyperlinkDetectorForPHP {

	private IEditorPart fEditor;

	/**
	 * Creates a new PHP element hyperlink detector.
	 */
	public PHPHyperlinkDetector(IEditorPart editor) {
		fEditor = editor;
	}

	/*
	 * @see org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion, boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		if (region == null) {
			return null;
		}

		IModelElement input = EditorUtility.getEditorInputModelElement(fEditor, false);
		if (input == null) {
			return null;
		}
		
		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();
		try {
			while (offset > 0 && !Character.isJavaIdentifierPart(document.getChar(offset))) {
				--offset;
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		try {
			IRegion wordRegion = ScriptWordFinder.findWord(document, offset);
			if (wordRegion == null)
				return null;

			IModelElement[] elements = null;
			elements = ((ICodeAssist) input).codeSelect(wordRegion.getOffset(), wordRegion.getLength());
			if (elements != null && elements.length > 0) {
				final IHyperlink link;
				if (elements.length == 1) {
					link = new ModelElementHyperlink(wordRegion, elements[0], new OpenAction(fEditor));
				} else {
					link = new ModelElementHyperlink(wordRegion, new ModelElementArray(elements), new OpenAction(fEditor));
				}
				return new IHyperlink[] { link };
			}
		} catch (ModelException e) {
			return null;
		}

		return null;
	}
}
