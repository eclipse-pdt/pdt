/**********************************************************************
 Copyright (c) 2000, 2015, 2017 IBM Corp. and others.
 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Common Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/cpl-v10.html

 Contributors:
 IBM Corporation - Initial implementation
 www.phpeclipse.de
 Dawid Pakuła [459462]
 **********************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.documentModel.parser.PHPSourceParser;
import org.eclipse.php.internal.core.format.IndentationObject;
import org.eclipse.php.internal.core.format.PHPIndentationFormatter;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.text.JobSafeStructuredDocument;

/**
 * Auto indent strategy sensitive to brackets.
 */
public class PHPAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	public PHPAutoIndentStrategy() {
	}

	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		if (command.text == null) {
			return;
		}
		String text = command.text.trim();
		// this class will always take care of curly brackets, we should not
		// directly use CurlyOpenAutoEditStrategy or CurlyCloseAutoEditStrategy
		if (text.endsWith("{") //$NON-NLS-1$
				|| text.endsWith("}") //$NON-NLS-1$
				// if user types enter key, we may add some indentation
				// spaces/tabs for it, so we use text.length() > 1 to filter it
				|| (text.length() > 1 && getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SMART_PASTE))) {
			try {
				smartPaste(document, command);
			} catch (Exception e) {
				PHPUiPlugin.log(e);
			}
		}
	}

	private static IPreferenceStore getPreferenceStore() {
		return PHPUiPlugin.getDefault().getPreferenceStore();
	}

	/**
	 * Set the indent of a bracket based on the command provided in the supplied
	 * document.
	 * 
	 * @param document
	 *            - the document being parsed
	 * @param command
	 *            - the command being performed
	 */
	protected void smartPaste(IDocument document, DocumentCommand command) {
		if (command.offset == -1 || document.getLength() == 0) {
			return;
		}
		IndentationObject indentationObject = null;
		String fakeFirstCharsAfterCommandText = "#"; //$NON-NLS-1$
		try {
			if (document instanceof IStructuredDocument) {
				indentationObject = new IndentationObject((IStructuredDocument) document);
				IRegion region = document.getLineInformation(document.getLineOfOffset(command.offset));

				if (command.offset == region.getOffset()) {
					// nothing to do
				} else {
					String textBeforeCommandOffset = document.get(region.getOffset(),
							command.offset - region.getOffset());
					if (StringUtils.isBlank(textBeforeCommandOffset)) {
						// adjust the length
						command.length += command.offset - region.getOffset();
						// and finally adjust the offset
						command.offset = region.getOffset();
					} else {
						// add line text located before command offset
						StringBuilder tempsb = new StringBuilder(
								textBeforeCommandOffset.length() + command.text.length());
						tempsb.append(textBeforeCommandOffset).append(command.text);
						command.text = tempsb.toString();
						// adjust the length
						command.length += command.offset - region.getOffset();
						// and finally adjust the offset
						command.offset = region.getOffset();
					}
				}

				// be smart and remove remaining blank characters after
				// selection
				int selectionEndOffset = command.offset + command.length;
				region = document.getLineInformation(document.getLineOfOffset(selectionEndOffset));
				int i = selectionEndOffset;
				int lineEndOffset = region.getOffset() + region.getLength();
				for (; i < lineEndOffset && (document.getChar(i) == ' ' || document.getChar(i) == '\t'); i++) {
				}
				if (i < lineEndOffset) {
					Document tempdocument = new Document(command.text);
					int lines = tempdocument.getNumberOfLines();
					// process blanks after command text only if the command
					// text has more than one line (to avoid removing blanks
					// between future cursor position and first non-blank
					// characters)
					if (lines > 1) {
						int j = i + 1;
						for (; j < lineEndOffset && !(document.getChar(j) == ' ' || document.getChar(j) == '\t'); j++) {
						}
						// adjust the length to include the blank characters
						command.length += i - selectionEndOffset;
						// We need later to add (at least) first non-blank line
						// character to the command selection so we can
						// correctly calculate last line indentation. It's even
						// better to have all consecutive non-blank characters
						// to handle correctly special PHP keywords like "case"
						// or "default".
						fakeFirstCharsAfterCommandText = document.get(i, j - i);
					}
				} else {
					// adjust the length to include the blank characters
					command.length += i - selectionEndOffset;
				}
			}

			JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(new PHPSourceParser());
			StringBuilder tempsb = new StringBuilder(command.offset + command.text.length() + 1);
			tempsb.append(document.get(0, command.offset)).append(command.text).append(fakeFirstCharsAfterCommandText);
			newdocument.set(tempsb.toString());
			PHPIndentationFormatter formatter = new PHPIndentationFormatter(command.offset, command.text.length(),
					indentationObject);
			formatter.format(newdocument.getRegionAtCharacterOffset(command.offset));

			if (fakeFirstCharsAfterCommandText.length() == 1
					&& newdocument.getChar(newdocument.getLength() - 1) == fakeFirstCharsAfterCommandText.charAt(0)) {
				// fast path
				command.text = newdocument.get(command.offset, newdocument.getLength() - command.offset - 1);
			} else {
				command.text = newdocument.get(command.offset, newdocument.getLength() - command.offset);
				command.text = command.text.substring(0, command.text.lastIndexOf(fakeFirstCharsAfterCommandText));
			}
		} catch (BadLocationException e) {
			PHPUiPlugin.log(e);
		}
	}
}
