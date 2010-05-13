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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.FormatterUtils;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

/**
 * 
 * @author guy.g
 * 
 */

public class MatchingBracketAutoEditStrategy extends
		MatchingCharAutoEditStrategy {

	public void customizeDocumentCommand(IDocument document,
			DocumentCommand command) {
		if (TypingPreferences.closeBrackets && command.text != null) {
			int length = command.text.length();
			if (length == 0 && command.length == 1) {
				try {
					char removedChar = document.getChar(command.offset);
					if (removedChar == ROUND_OPEN || removedChar == SQUARE_OPEN) {
						deletePairBreaket((IStructuredDocument) document,
								command, removedChar);
					}

				} catch (BadLocationException e) {
					Logger.logException(e);
				}
			}
			if (length == 1) {
				char c = command.text.charAt(0);
				if (c == ROUND_OPEN || c == SQUARE_OPEN) {
					autoAddPairBracket((IStructuredDocument) document, command);
				} else if (c == ROUND_CLOSE || c == SQUARE_CLOSE) {
					insertClosingChar((IStructuredDocument) document, command);
				}
			}
		}
	}

	private void autoAddPairBracket(IStructuredDocument document,
			DocumentCommand command) {
		int currentPosition = command.offset;
		int commandLength = command.length;
		char c = command.text.charAt(0);
		boolean extraCharWasAdded = false;
		String removedText = ""; //$NON-NLS-1$

		try {
			if (currentPosition + commandLength < document.getLength()) {
				if (!shouldAddClosingBracket(document, currentPosition
						+ commandLength, false)) {
					return;
				}
			}

			// adding the first ( in order to make the calculation more easy
			removedText = document.get(currentPosition, commandLength);

			// tarning of the undo
			document.getUndoManager().disableUndoManagement();
			document.replace(currentPosition, commandLength, command.text);
			extraCharWasAdded = true;

			int result = isMatchingCharNeeded(document, currentPosition, c);
			// rollback the change we made
			document.replace(currentPosition, 1, removedText);
			// putting back the undo
			document.getUndoManager().enableUndoManagement();

			if (result == MATCHING_BRACKET_NEEDED) {
				char match = getMatchingChar(c);
				command.text = command.text + match;
				// making the change in ther documet ourselfs and consuming the
				// original command
				document.replace(currentPosition, commandLength, command.text);
				document.getUndoManager().disableUndoManagement();
				document.replace(currentPosition + 1, 0, ""); //$NON-NLS-1$
				document.getUndoManager().enableUndoManagement();
				adjustDocumentOffset(command); // this will cause the caret to
												// be set between the brackets.

				command.length = 0;
				command.text = ""; //$NON-NLS-1$
			}

		} catch (Exception exc) {
			Logger.logException(exc);
			if (extraCharWasAdded) {
				try {
					document.replace(currentPosition, 1, removedText);
					document.getUndoManager().enableUndoManagement();
				} catch (BadLocationException e) {
					Logger.logException(e);
				}
			}
		}

	}

	protected int isMatchingCharNeeded(IStructuredDocument document,
			int offset, char bracketChar) {
		try {
			String postCharState = FormatterUtils.getPartitionType(document,
					offset + 1);
			if (!(postCharState == PHPPartitionTypes.PHP_DEFAULT
					|| postCharState == PHPRegionTypes.PHP_OPENTAG || postCharState == PHPRegionTypes.PHP_CLOSETAG)) {
				if (isSpecialOpenCurlyInQuotes(document, offset)) {
					postCharState = FormatterUtils.getPartitionType(document,
							offset + 2);
				}
			}
			if (document.getLength() == offset + 1) { // if we are in the end of
														// the document
				postCharState = FormatterUtils.getPartitionType(document,
						offset);
				if (postCharState == PHPPartitionTypes.PHP_DEFAULT
						|| postCharState == PHPRegionTypes.PHP_OPENTAG
						|| postCharState == PHPRegionTypes.PHP_CLOSETAG) {
					if (document.getChar(offset) == getMatchingChar(bracketChar)) {
						return MATCHING_BRACKET_NOT_NEEDED;
					}
					return MATCHING_BRACKET_NEEDED;
				}
				return MATCHING_BRACKET_NOT_NEEDED;
			}

			if (postCharState != PHPPartitionTypes.PHP_DEFAULT
					&& postCharState != PHPRegionTypes.PHP_OPENTAG
					&& postCharState != PHPRegionTypes.PHP_CLOSETAG) {
				return SEARCH_NOT_VALID;
			}

			int currOffset = offset + 1;
			IStructuredDocumentRegion sdRegion = document
					.getRegionAtCharacterOffset(currOffset);
			while (currOffset >= 0 && sdRegion != null) {
				if (sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
					currOffset = sdRegion.getStartOffset() - 1;
					sdRegion = document.getRegionAtCharacterOffset(currOffset);
					continue;
				}

				ITextRegion tRegion = sdRegion
						.getRegionAtCharacterOffset(currOffset);

				// in case the cursor on the beginning of '?>' tag
				// we decrease the offset to get the PhpScriptRegion
				if (tRegion.getType().equals(PHPRegionContext.PHP_CLOSE)) {
					tRegion = sdRegion
							.getRegionAtCharacterOffset(currOffset - 1);
				}

				int regionStart = sdRegion.getStartOffset(tRegion);
				// in case of container we have the extract the PhpScriptRegion
				if (tRegion instanceof ITextRegionContainer) {
					ITextRegionContainer container = (ITextRegionContainer) tRegion;
					tRegion = container.getRegionAtCharacterOffset(currOffset);
					regionStart += tRegion.getStart();
				}

				if (tRegion instanceof IPhpScriptRegion) {
					IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
					tRegion = scriptRegion
							.getPhpToken(currOffset - regionStart);

					while (tRegion != null) {
						String regionType = tRegion.getType();
						if (regionType == PHPRegionTypes.PHP_TOKEN) {
							char token = document.getChar(regionStart
									+ tRegion.getStart());
							if (token == ROUND_OPEN || token == SQUARE_OPEN) {
								if (token == bracketChar) {
									if (matcher.match(document, regionStart
											+ tRegion.getStart() + 1) == null) {
										return MATCHING_BRACKET_NEEDED;
									}
								}
							}
						} else if (regionType == PHPRegionTypes.PHP_CURLY_OPEN
								|| regionType == PHPRegionTypes.PHP_CURLY_CLOSE) {
							return MATCHING_BRACKET_NOT_NEEDED;
						}
						if (tRegion.getStart() > 0) {
							tRegion = scriptRegion.getPhpToken(tRegion
									.getStart() - 1);
						} else {
							break;
						}
					}
				}

				currOffset = sdRegion.getStartOffset() - 1;
				sdRegion = (currOffset < 0) ? null : document
						.getRegionAtCharacterOffset(currOffset);

			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return MATCHING_BRACKET_NOT_NEEDED;
	}

	private void insertClosingChar(IStructuredDocument document,
			DocumentCommand command) {
		int endSelection = command.offset + command.length;
		char addedChar = command.text.charAt(0);
		if (endSelection == document.getLength()) // nothing to match
			return;
		try {
			char nextChar = document.getChar(endSelection);
			if (nextChar == addedChar) {
				int result;
				result = isMatchingCharNeeded(document, endSelection,
						getMatchingChar(addedChar));
				// this check means that all opening brackets has a closing one
				if (result == MATCHING_BRACKET_NOT_NEEDED) {
					// this check is for the case of ()) when the carret is
					// between the two ')'
					// typing ')' will add another ')' to the document
					if (matcher.match(document, endSelection + 1) != null
							|| document.getLength() == endSelection + 1) {
						if (command.length == 0) {
							adjustDocumentOffset(command);
							command.text = ""; //$NON-NLS-1$
						} else {
							command.length++;
						}
					}
				}
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return;
	}

	private void deletePairBreaket(IStructuredDocument document,
			DocumentCommand command, char deletedChar) {
		int offset = command.offset;
		IStructuredDocumentRegion sdRegion = document
				.getRegionAtCharacterOffset(offset);
		if (sdRegion == null
				|| sdRegion.getType() != PHPRegionTypes.PHP_CONTENT) {
			return;
		}
		try {
			ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);

			// in case of container we have the extract the PhpScriptRegion
			if (tRegion instanceof ITextRegionContainer) {
				ITextRegionContainer container = (ITextRegionContainer) tRegion;
				tRegion = container.getRegionAtCharacterOffset(offset);
			}

			if (tRegion instanceof IPhpScriptRegion) {
				IPhpScriptRegion scriptRegion = (IPhpScriptRegion) tRegion;
				tRegion = scriptRegion.getPhpToken(offset
						- sdRegion.getStartOffset(scriptRegion));

				if (tRegion == null
						|| tRegion.getType() != PHPRegionTypes.PHP_TOKEN) {
					return;
				}

				char nextChar = document.getChar(offset + 1);
				char matchingChar = getMatchingChar(deletedChar);
				if (matchingChar == '-' || nextChar != matchingChar) {
					return;
				}

				boolean removeBoth = (isMatchingCharNeeded(document, offset,
						deletedChar) == MATCHING_BRACKET_NOT_NEEDED);

				if (removeBoth) {
					command.length = 2;
				}

			}
		} catch (BadLocationException e) {
		}
	}

}
