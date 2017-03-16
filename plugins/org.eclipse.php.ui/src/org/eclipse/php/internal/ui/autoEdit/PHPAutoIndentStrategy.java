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
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.format.IndentationObject;
import org.eclipse.php.internal.core.format.PhpIndentationFormatter;
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
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		// when user typing c.text.length()==1 except enter key,
		// if user type enter key, we may add some indentation spaces/tabs for
		// it, so we use c.text.trim().length() > 0 to filter it
		if (c.text != null && c.text.length() > 1 && c.text.trim().length() > 1
				&& getPreferenceStore().getBoolean(PreferenceConstants.EDITOR_SMART_PASTE)) {
			try {
				smartPaste(d, c);
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
				// adjust the length to include the blank characters
				command.length += i - selectionEndOffset;
				if (i < lineEndOffset) {
					int j = i + 1;
					for (; j < lineEndOffset && !(document.getChar(j) == ' ' || document.getChar(j) == '\t'); j++) {
					}
					// We need later to add (at least) first non-blank line
					// character to the command selection so we can correctly
					// calculate last line indentation. It's even better to have
					// all consecutive non-blank characters to handle correctly
					// special PHP keywords like "case" or "default".
					fakeFirstCharsAfterCommandText = document.get(i, j - i);
				}
			}

			JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(new PhpSourceParser());
			StringBuilder tempsb = new StringBuilder(command.offset + command.text.length() + 1);
			tempsb.append(document.get(0, command.offset)).append(command.text).append(fakeFirstCharsAfterCommandText);
			newdocument.set(tempsb.toString());
			PhpIndentationFormatter formatter = new PhpIndentationFormatter(command.offset, command.text.length(),
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
