package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.format.ParenthesesCloseIndentationStrategy;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;

@SuppressWarnings("restriction")
public class ParenthesesCloseAutoEditStrategy extends
		ParenthesesCloseIndentationStrategy implements IAutoEditStrategy {

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (command.text != null && command.text.trim().endsWith(")")) { //$NON-NLS-1$
			autoIndentAfterParenClose((IStructuredDocument) document, command);
		}
	}

	private StringBuffer helpBuffer = new StringBuffer();

	private void autoIndentAfterParenClose(IStructuredDocument document,
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
	}

	protected static boolean isBlanks(IStructuredDocument document,
			int startOffset, int endOffset) throws BadLocationException {
		return document.get(startOffset, endOffset - startOffset).trim()
				.length() == 0;
	}

}