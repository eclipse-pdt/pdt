package org.eclipse.php.internal.core.format;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

@SuppressWarnings("restriction")
public class ParenthesesCloseIndentationStrategy implements
		IIndentationStrategy {

	public void placeMatchingBlanks(IStructuredDocument document,
			StringBuffer result, int lineNumber, int forOffset)
			throws BadLocationException {
		final IRegion parenOpenLine = getParenOpenLineInformation(document,
				forOffset);
		if (parenOpenLine == null) {
			return;
		}
		int indentationBaseLineIndex = DefaultIndentationStrategy
				.getIndentationBaseLine(document,
						document.getLineOfOffset(parenOpenLine.getOffset()),
						forOffset, true);
		final IRegion indentationBaseLine = document
				.getLineInformation(indentationBaseLineIndex);
		String blanks = "";
		if (indentationBaseLine != null) {
			blanks = FormatterUtils
					.getLineBlanks(document, indentationBaseLine);
		} else { // if no matching bracket was found leaving the bracket as is.
			blanks = FormatterUtils.getLineBlanks(document,
					document.getLineInformationOfOffset(forOffset));
		}
		result.append(blanks);
	}

	/**
	 * This function returns the line in which the corresponding '(' of the ')'
	 * found
	 * 
	 * TODO this function has a bug in it: if there is a '(' inside inner state
	 * then it will not ignore it as it should.
	 */
	protected IRegion getParenOpenLineInformation(IStructuredDocument document,
			int forOffset) throws BadLocationException {
		int offset = forOffset;
		int parenCount = 0;

		IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(offset);
		if (sdRegion == null) {
			return null;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		if (tRegion == null && offset == document.getLength()) {
			tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
		}
		int regionStart = sdRegion.getStartOffset(tRegion);

		// in case of container we have the extract the PhpScriptRegion
		if (tRegion instanceof ITextRegionContainer) {
			ITextRegionContainer container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
			regionStart += tRegion.getStart();
		}
		do {
			if (tRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
				tRegion = scriptRegion.getPhpToken(offset - regionStart - 1);

				// go backward over the region to find a 'case' or 'default'
				// region
				// in this case is the same indentation
				// other case if look for the '(' of the 'switch' region
				while (tRegion != null) {
					String token = tRegion.getType();
					if (token == PHPRegionTypes.PHP_ARRAY) {
						parenCount--;
						if (parenCount < 0) {
							return document.getLineInformationOfOffset(tRegion
									.getStart() + regionStart);
						}
					}
					if (tRegion.getStart() > 0) {
						tRegion = scriptRegion
								.getPhpToken(tRegion.getStart() - 1);
					} else {
						break;
					}
				}
			}

			tRegion = null;
		} while (tRegion != null);

		return null;
	}
}
