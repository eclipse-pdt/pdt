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
package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.format.CurlyCloseIndentationStrategy;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

/**
 * @author guy.g
 * 
 */

public class CurlyCloseAutoEditStrategy extends CurlyCloseIndentationStrategy
		implements IAutoEditStrategy {

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (command.text != null && command.text.trim().endsWith("}")) { //$NON-NLS-1$
			autoIndentAfterCurlyClose((IStructuredDocument) document, command);
		}
	}

	private StringBuffer helpBuffer = new StringBuffer();

	private void autoIndentAfterCurlyClose(IStructuredDocument document,
			DocumentCommand command) {
		helpBuffer.setLength(0);
		int currentOffset = command.offset;

		int lineNumber = document.getLineOfOffset(currentOffset);
		try {
			IRegion lineInfo = document.getLineInformation(lineNumber);
			if (isBlanks(document, lineInfo.getOffset(), command.offset)) {
				placeMatchingBlanks(document, helpBuffer, lineNumber,
						currentOffset);

				// removing the whiteSpaces in the begining of the line
				command.offset = lineInfo.getOffset();
				command.length += (currentOffset - lineInfo.getOffset());
			}

		} catch (BadLocationException e) {
			Logger.logException(e);
		}

		command.text = helpBuffer.toString() + command.text;
		// JobSafeStructuredDocument newdocument = new
		// JobSafeStructuredDocument(
		// new PhpSourceParser());
		// String start = "<?php";
		// newdocument.set(start + command.text);
		// PhpFormatter formatter = new PhpFormatter(0,
		// newdocument.getLength());
		// formatter.format(newdocument.getFirstStructuredDocumentRegion());
		//
		// Reader reader = new StringReader(newdocument.get());
		// BufferedReader br = new BufferedReader(reader);
		// List<String> list = new ArrayList<String>();
		// String line = null;
		// try {
		// while ((line = br.readLine()) != null) {
		// if (list.isEmpty()) {
		// line = line.substring(start.length()).trim();
		// }
		// list.add(line);
		// }
		// } catch (IOException e) {
		// }
		// String newline = newdocument.getLineDelimiter();
		// StringBuffer sb = new StringBuffer();
		// for (int i = 0; i < list.size(); i++) {
		// sb.append(helpBuffer.toString()).append(list.get(i));
		// if (i != list.size() - 1) {
		// sb.append(newline);
		// }
		// }
		// command.text = sb.toString();
	}

	protected static boolean isBlanks(IStructuredDocument document,
			int startOffset, int endOffset) throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim()
				.length() == 0;
	}

}
