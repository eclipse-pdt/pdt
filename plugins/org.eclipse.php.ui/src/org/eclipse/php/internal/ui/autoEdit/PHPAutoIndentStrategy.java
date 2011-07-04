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

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.documentModel.parser.PhpSourceParser;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.PhpFormatter;
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
		StringBuffer helpBuffer = new StringBuffer();
		try {
			if (document instanceof IStructuredDocument) {
				DefaultIndentationStrategy
						.placeMatchingBlanksForStructuredDocument(
								(IStructuredDocument) document, helpBuffer,
								document.getLineOfOffset(command.offset),
								command.offset);
				IRegion region = document.getLineInformation(document
						.getLineOfOffset(command.offset));
				if (document.get(region.getOffset(), region.getLength()).trim()
						.length() == 0) {// blank line
					if (command.offset != region.getOffset()) {
						document.replace(region.getOffset(),
								region.getLength(), "");
						// adjust the offset
						command.offset = region.getOffset();
					}
				} else {
					return;
				}
			}
		} catch (BadLocationException e) {
		}

		Document tempdocument = new Document(command.text);
		String newline = tempdocument.getDefaultLineDelimiter();
		int lines = tempdocument.getNumberOfLines();
		StringBuffer tempsb = new StringBuffer();
		try {
			for (int i = 0; i < lines; i++) {
				IRegion region = tempdocument.getLineInformation(i);
				if (i > 0) {
					tempsb.append(newline);
				}
				tempsb.append(tempdocument.get(region.getOffset(),
						region.getLength()).trim());
			}
		} catch (BadLocationException e) {
		}
		JobSafeStructuredDocument newdocument = new JobSafeStructuredDocument(
				new PhpSourceParser());
		String start = "<?php";
		newdocument.set(start + tempsb.toString());
		PhpFormatter formatter = new PhpFormatter(0, newdocument.getLength());
		formatter.format(newdocument.getFirstStructuredDocumentRegion());

		Reader reader = new StringReader(newdocument.get());
		BufferedReader br = new BufferedReader(reader);
		List<String> list = new ArrayList<String>();
		try {
			int lineNumber = newdocument.getNumberOfLines();
			for (int i = 0; i < lineNumber; i++) {
				IRegion region = newdocument.getLineInformation(i);
				String line = newdocument.get(region.getOffset(),
						region.getLength());
				if (list.isEmpty()) {
					line = line.substring(start.length()).trim();
				}
				list.add(line);
			}
		} catch (BadLocationException e) {
		}
		// String newline = newdocument.getLineDelimiter();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append(helpBuffer.toString()).append(list.get(i));
			if (i != list.size() - 1) {
				sb.append(newline);
			}
		}
		command.text = sb.toString();
	}
}
