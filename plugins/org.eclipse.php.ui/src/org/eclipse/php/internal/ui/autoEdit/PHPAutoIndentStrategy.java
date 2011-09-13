/**********************************************************************
 Copyright (c) 2000, 2002 IBM Corp. and others.
 All rights reserved. This program and the accompanying materials
 are made available under the terms of the Common Public License v1.0
 which accompanies this distribution, and is available at
 http://www.eclipse.org/legal/cpl-v10.html

 Contributors:
 IBM Corporation - Initial implementation
 www.phpeclipse.de
 **********************************************************************/
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.format.PhpFormatter;
import org.eclipse.php.internal.ui.PHPUiPlugin;
import org.eclipse.php.internal.ui.preferences.PreferenceConstants;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * Auto indent strategy sensitive to brackets.
 */
public class PHPAutoIndentStrategy extends DefaultIndentLineAutoEditStrategy {

	public PHPAutoIndentStrategy() {
	}

	/*
	 * (non-Javadoc) Method declared on IAutoIndentStrategy
	 */
	public void customizeDocumentCommand(IDocument d, DocumentCommand c) {
		// when user typing c.text.length()==1 except enter key,
		// if user type enter key,we may add some indentation spaces/tabs for
		// it,so we use c.text.trim().length() > 0 to filter it
		if (c.text != null
				&& c.text.length() > 1
				&& c.text.trim().length() > 1
				&& !getPreferenceStore().getBoolean(
						PreferenceConstants.EDITOR_SMART_PASTE)) {
			smartPaste(d, c);
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
		if (command.offset == -1 || document.getLength() == 0)
			return;
		try {
			if (document instanceof IStructuredDocument) {
				IStructuredDocument structuredDocument = (IStructuredDocument) document;
				IRegion region = document.getLineInformation(document
						.getLineOfOffset(command.offset));
				if (document.get(region.getOffset(), region.getLength()).trim()
						.length() == 0) {// blank line
				// if (command.offset != region.getOffset()) {
					document.replace(region.getOffset(), region.getLength(), "");
					// adjust the offset
					command.offset = region.getOffset();
					// }
					String oldContent = document.get();
					int endHalf = oldContent.length() - region.getOffset();
					document.replace(region.getOffset(), 0, command.text);
					PhpFormatter formatter = new PhpFormatter(
							region.getOffset(), command.text.length());

					formatter
							.format(structuredDocument
									.getStructuredDocumentRegions(
											region.getOffset(), 0)[0]);
					String newContent = document.get();
					command.text = newContent.substring(region.getOffset(),
							newContent.length() - endHalf);
					document.set(oldContent);
					return;

				} else {
					return;
				}
			}
		} catch (BadLocationException e) {
		}
	}
}
