/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.hyperlink;

import org.eclipse.dltk.core.ICodeAssist;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.ui.editor.EditorUtility;
import org.eclipse.dltk.internal.ui.editor.ModelElementHyperlink;
import org.eclipse.dltk.ui.actions.OpenAction;
import org.eclipse.dltk.ui.infoviews.ModelElementArray;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.project.ProjectOptions;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

public class PHPHyperlinkDetector extends AbstractHyperlinkDetector {

	/*
	 * @see org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion, boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		PHPStructuredEditor editor = org.eclipse.php.internal.ui.util.EditorUtility.getPHPEditor(textViewer);
		if (editor == null) {
			return null;
		}
		
		if (region == null) {
			return null;
		}

		IModelElement input = EditorUtility.getEditorInputModelElement(editor, false);
		if (input == null) {
			return null;
		}
		
		PHPVersion phpVersion = ProjectOptions.getPhpVersion(input.getScriptProject().getProject());
		boolean namespacesSupported = phpVersion.isGreaterThan(PHPVersion.PHP5); // PHP 5.3 and greater

		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();
		try {
			IRegion wordRegion = findWord(document, offset, namespacesSupported);
			if (wordRegion == null)
				return null;

			IModelElement[] elements = null;
			elements = ((ICodeAssist) input).codeSelect(wordRegion.getOffset(), wordRegion.getLength());
			if (elements != null && elements.length > 0) {
				final IHyperlink link;
				if (elements.length == 1) {
					link = new ModelElementHyperlink(wordRegion, elements[0], new OpenAction(editor));
				} else {
					link = new ModelElementHyperlink(wordRegion, new ModelElementArray(elements), new OpenAction(editor));
				}
				return new IHyperlink[] { link };
			}
		} catch (ModelException e) {
			return null;
		}

		return null;
	}

	public static IRegion findWord(IDocument document, int offset, boolean namespacesSupported) {

		int start = -2;
		int end = -1;

		try {
			int pos = offset;
			char c;

			int rightmostNsSeparator = -1;
			while (pos >= 0) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c) && (!namespacesSupported || c != '\\')) {
					break;
				}
				if (namespacesSupported && c == '\\' && rightmostNsSeparator == -1) {
					rightmostNsSeparator = pos;
				}
				--pos;
			}
			start = pos;

			pos = offset;
			int length = document.getLength();
			
			while (pos < length) {
				c = document.getChar(pos);
				if (!Character.isJavaIdentifierPart(c) && (!namespacesSupported || c != '\\')) {
					break;
				}
				if (namespacesSupported && c == '\\') {
					rightmostNsSeparator = pos;
				}
				++pos;
			}
			end = pos;
			
			if (rightmostNsSeparator != -1) {
				if (rightmostNsSeparator > offset) {
					end = rightmostNsSeparator;
				}
				else {
					start = rightmostNsSeparator;
				}
			}

		} catch (BadLocationException x) {
		}

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset) {
				return new Region(offset, 0);
			}
			else if (start == offset) {
				return new Region(start, end - start);
			}
			else {
				return new Region(start + 1, end - start - 1);
			}
		}

		return null;
	}
}
