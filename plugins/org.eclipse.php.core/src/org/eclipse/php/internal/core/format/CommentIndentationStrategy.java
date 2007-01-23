package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;

public class CommentIndentationStrategy extends DefaultIndentationStrategy {

	/**
	 * If we are inside a comment, check the previous line:
	 * In case it is the comment start (meaning the first line), this line will be indented.
	 */
	public void placeMatchingBlanks(IStructuredDocument document, StringBuffer result, int lineNumber, int forOffset) throws BadLocationException {

		if (lineNumber == 0) {
			return;
		}
		IRegion previousLine = document.getLineInformation(lineNumber - 1);
		
		IStructuredDocumentRegion sdRegion = document.getRegionAtCharacterOffset(previousLine.getOffset());
		if (sdRegion == null)
			return;
		
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(previousLine.getOffset());
		if (tRegion == null)
			return;
		
		if (tRegion.getStart() + sdRegion.getStartOffset() < previousLine.getOffset()){
			tRegion = sdRegion.getRegionAtCharacterOffset(tRegion.getEnd() + sdRegion.getStartOffset());
		}		
		
		// Check if the previous line is the start of the comment.
		if (tRegion.getType() == PHPRegionTypes.PHP_COMMENT_START || tRegion.getType() == PHPRegionTypes.PHPDOC_COMMENT_START) {
			final String blanks = FormatterUtils.getLineBlanks(document, previousLine);
			// add the indentation of jthe previous line and a single space in addition
			result.append(blanks); 
			result.append(" ");
		} else {
			super.placeMatchingBlanks(document, result, lineNumber, forOffset);
		}
	}

}
