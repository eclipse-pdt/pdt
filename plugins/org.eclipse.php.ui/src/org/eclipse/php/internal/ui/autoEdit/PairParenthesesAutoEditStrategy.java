package org.eclipse.php.internal.ui.autoEdit;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.format.DefaultIndentationStrategy;
import org.eclipse.php.internal.core.format.FormatPreferencesSupport;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.text.PHPDocumentRegionEdgeMatcher;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;
import org.eclipse.wst.xml.core.internal.parser.ContextRegionContainer;

@SuppressWarnings("restriction")
public class PairParenthesesAutoEditStrategy implements
		IAfterNewLineAutoEditStrategy {

	private static final char PAREN_OPEN = '(';
	private static final char PAREN_CLOSE = ')';

	private static final char BRACKET_OPEN = '[';
	private static final char BRACKET_CLOSE = ']';

	private static PHPDocumentRegionEdgeMatcher matcher = new PHPDocumentRegionEdgeMatcher();

	/**
	 * get the php token according to the given sdRegion and offset.
	 * 
	 * @param sdRegion
	 * @param offset
	 * @return
	 */
	private ITextRegion getPhpToken(IStructuredDocumentRegion sdRegion,
			int offset) {
		try {
			// get the ITextRegionContainer region or PhpScriptRegion region
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
			int regionStart = sdRegion.getStartOffset(tRegion);

			// in case of container we have the extract the PhpScriptRegion
			if (tRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
				regionStart += tRegion.getStart();
			}

			// find the specified php token in the PhpScriptRegion
			if (tRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
				tRegion = scriptRegion.getPhpToken(offset - regionStart);

				return tRegion;
			}
		} catch (BadLocationException e) {
		}
		return null;
	}

	public int autoEditAfterNewLine(IStructuredDocument document,
			DocumentCommand command, StringBuffer buffer) {
		try {

			boolean isClosingParen = isClosingParen(document, command);
			int offset = command.offset;

			int rvPosition = offset + buffer.length();
			if (!isClosingParen) {
				rvPosition += copyRestOfLine(document, command, buffer);
			}

			int parenCloseCounter = 0;
			int currOffset = offset;
			IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(currOffset);

			int regionStart = sdRegion.getStart();
			String text = sdRegion.getFullText();

			ITextRegion tRegion = null;
			int indexInText = text.length() - 1;
			while (indexInText >= 0) {
				char currChar = text.charAt(indexInText);
				if (currChar == PAREN_CLOSE || currChar == BRACKET_CLOSE) {
					tRegion = getPhpToken(sdRegion, regionStart + indexInText);
					if (tRegion == null
							|| tRegion.getType() == PHPRegionTypes.PHP_ARRAY) {
						parenCloseCounter++;
					}
				} else if (currChar == PAREN_OPEN || currChar == BRACKET_OPEN) {
					tRegion = getPhpToken(sdRegion, regionStart + indexInText);
					boolean found = false;
					if (tRegion != null) {
						if (tRegion.getType().equals(PHPRegionTypes.PHP_ARRAY)) {
							found = true;
						} else if (tRegion.getType().equals(
								PHPRegionTypes.PHP_TOKEN)
								&& tRegion.getLength() == 2) {
						}
					}
					if (!found) {
						indexInText--;
						continue;
					}
					parenCloseCounter--;
					if (parenCloseCounter < 0) {
						if (matcher.match(document, regionStart + indexInText
								+ 1) == null) {
							break;
						}
						parenCloseCounter++;
					}
				}
				indexInText--;
			}

			if (isClosingParen) {
				rvPosition += copyRestOfLine(document, command, buffer);
			}

			return rvPosition;
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return -1;
	}

	private boolean isClosingParen(IStructuredDocument document,
			DocumentCommand command) throws BadLocationException {
		int documentLength = document.getLength();
		if (command.offset + command.length == documentLength) { // if we're at
																	// the end
																	// of the
																	// document
																	// then
																	// nothing
																	// to copy
			return false;
		}
		int offset = command.offset;
		IRegion lineInfo = document.getLineInformationOfOffset(offset);
		int endOffset = lineInfo.getOffset() + lineInfo.getLength();
		int lengthToCopyDown = endOffset - offset;

		IStructuredDocumentRegion[] structuredDocumentRegions = document
				.getStructuredDocumentRegions(offset, lengthToCopyDown);
		if (structuredDocumentRegions != null
				&& structuredDocumentRegions.length > 0) {
			IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentRegions[0];
			ITextRegion firstRegion = structuredDocumentRegion.getFirstRegion();
			ITextRegion lastRegion = structuredDocumentRegion.getLastRegion();

			int xmlRelativeOffset = 0;

			// deal with PHP block within HTML tags
			if (!(firstRegion instanceof ContextRegion)) { // meaning
															// "not-PHP-Resgion"
				ITextRegion regionAtCharacterOffset = structuredDocumentRegion
						.getRegionAtCharacterOffset(offset);
				if (regionAtCharacterOffset instanceof ContextRegionContainer) {
					ContextRegionContainer phpContext = (ContextRegionContainer) regionAtCharacterOffset;
					lastRegion = phpContext.getLastRegion();
					firstRegion = phpContext.getFirstRegion();
					xmlRelativeOffset = firstRegion.getLength();
				}

			}
			int absolutOffset = lastRegion.getStart()
					+ structuredDocumentRegion.getStartOffset()
					+ xmlRelativeOffset;
			if (absolutOffset <= endOffset && offset <= absolutOffset) {
				lengthToCopyDown = absolutOffset - offset;
				;
			}
		}
		String lineEnd = document.get(offset, lengthToCopyDown).trim();
		if (lineEnd.startsWith(")") || lineEnd.startsWith("]")) {
			return true;
		}
		return false;
	}

	/**
	 * Copies the rest of the line (after the '(' ) into the buffer in order put
	 * the text inside the parentheses
	 * 
	 * @return The number of characters the caret should be advanced at
	 */
	private int copyRestOfLine(IStructuredDocument document,
			DocumentCommand command, StringBuffer buffer)
			throws BadLocationException {

		int documentLength = document.getLength();
		if (command.offset + command.length == documentLength) { // if we're at
																	// the end
																	// of the
																	// document
																	// then
																	// nothing
																	// to copy
			return 0;
		}
		int offset = command.offset;
		IRegion lineInfo = document.getLineInformationOfOffset(offset);
		int endOffset = lineInfo.getOffset() + lineInfo.getLength();
		int whiteSpacesAdded = 0;
		int lengthToCopyDown = endOffset - offset;

		IStructuredDocumentRegion[] structuredDocumentRegions = document
				.getStructuredDocumentRegions(offset, lengthToCopyDown);
		if (structuredDocumentRegions != null
				&& structuredDocumentRegions.length > 0) {
			IStructuredDocumentRegion structuredDocumentRegion = structuredDocumentRegions[0];
			ITextRegion firstRegion = structuredDocumentRegion.getFirstRegion();
			ITextRegion lastRegion = structuredDocumentRegion.getLastRegion();

			int xmlRelativeOffset = 0;

			// deal with PHP block within HTML tags
			if (!(firstRegion instanceof ContextRegion)) { // meaning
															// "not-PHP-Resgion"
				ITextRegion regionAtCharacterOffset = structuredDocumentRegion
						.getRegionAtCharacterOffset(offset);
				if (regionAtCharacterOffset instanceof ContextRegionContainer) {
					ContextRegionContainer phpContext = (ContextRegionContainer) regionAtCharacterOffset;
					lastRegion = phpContext.getLastRegion();
					firstRegion = phpContext.getFirstRegion();
					xmlRelativeOffset = firstRegion.getLength();
				}

			}
			int absolutOffset = lastRegion.getStart()
					+ structuredDocumentRegion.getStartOffset()
					+ xmlRelativeOffset;
			if (absolutOffset <= endOffset && offset <= absolutOffset) {
				lengthToCopyDown = absolutOffset - offset;
				;
			}
		}
		String lineEnd = document.get(offset, lengthToCopyDown);

		// if there are spaces between the '{' and the text in the line
		// (or the end of line)then need to override them
		for (int i = 0; i < lineEnd.length(); i++) {
			char c = lineEnd.charAt(i);
			lengthToCopyDown--; // for every iteration, need copy one less char
			if (c == '\n' || c == '\r' || !Character.isWhitespace(c)) {
				command.length += i;
				lengthToCopyDown++; // incrementing one back...
				break;
			}
		}

		String trimmedLineEnd = lineEnd.trim();
		if (trimmedLineEnd.length() > 0
				&& (trimmedLineEnd.charAt(0) == PAREN_CLOSE || trimmedLineEnd
						.charAt(0) == BRACKET_CLOSE)) {
			// if there is a ) then there is a problem with
			// parenCloseInsertionStrategy calc
			// and we need to add manually another indentation.
			int indentationSize = FormatPreferencesSupport.getInstance()
					.getIndentationSize(document);
			char indentationChar = FormatPreferencesSupport.getInstance()
					.getIndentationChar(document);
			for (int i = 0; i < indentationSize; i++) {
				buffer.append(indentationChar);
				whiteSpacesAdded++;
			}
			buffer.append(document.getLineDelimiter());
			int baseline = DefaultIndentationStrategy.getIndentationBaseLine(
					document, document.getLineOfOffset(offset), offset, true);
			lineInfo = document.getLineInformation(baseline);
			String blanks = FormatterUtils.getLineBlanks(document, lineInfo);
			buffer.append(blanks);
		}

		// copying the rest of the line after the "(" ,
		// using length calculated according to regionEnd or EndOfLine
		offset += command.length;
		char nextChar = document.getChar(offset);
		for (int i = 0; lengthToCopyDown > 0; lengthToCopyDown--) {
			buffer.append(nextChar);
			command.length++;
			offset++;
			if (offset == documentLength) {
				break;
			}
			nextChar = document.getChar(offset);
		}

		return whiteSpacesAdded;
	}

}
