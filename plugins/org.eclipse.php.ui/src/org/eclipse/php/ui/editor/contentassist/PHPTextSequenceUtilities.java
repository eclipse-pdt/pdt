/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor.contentassist;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.php.core.Logger;
import org.eclipse.php.core.documentModel.parser.PhpLexer;
import org.eclipse.php.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.ui.editor.util.TextSequence;
import org.eclipse.php.ui.editor.util.TextSequenceUtilities;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;


public class PHPTextSequenceUtilities {
	
	private static final Pattern COMMENT_START_PATTERN = Pattern.compile("(/[*])|(//)");
	private static final Pattern COMMENT_END_PATTERN = Pattern.compile("[*]/");
	private static final String START_COMMENT = "/*";
	private static final String END_COMMENT = "*/";
	private static final char END_LINE = '\n';
	private static final Pattern FUNCTION_PATTERN = Pattern.compile("function\\s", Pattern.CASE_INSENSITIVE);
	private static final Pattern CLASS_PATTERN = Pattern.compile("(class|interface)\\s", Pattern.CASE_INSENSITIVE);

	private PHPTextSequenceUtilities() {
	}

	/**
	 * Returns the statment start.
	 * the statment start is: Math.max(position of the last ';', position of php start tag)
	 */
	public static TextSequence getStatment(int offset, IStructuredDocumentRegion sdRegion, boolean removeComments) {
		int startPosition = sdRegion.getStartOffset();
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(offset);
		if (tRegion == null){
			tRegion = sdRegion.getLastRegion();
		}
		// if we are standing at the beginning of a word and asking for completion 
		if (tRegion.getType() != PHPRegionTypes.PHP_OPENTAG && sdRegion.getStartOffset(tRegion) == offset) {
			tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
			if (tRegion == null) {
				sdRegion = sdRegion.getPrevious();
				if (sdRegion == null)
					return null;
				tRegion = sdRegion.getRegionAtCharacterOffset(offset - 1);
				if (tRegion == null)
					return null;
			}
		}
		
		IStructuredDocument document = sdRegion.getParentDocument();
		while (tRegion != null && tRegion.getType() != PHPRegionTypes.PHP_OPENTAG) {
			String type = tRegion.getType();
			int textStart = sdRegion.getStartOffset(tRegion);
			if (type == PHPRegionTypes.PHP_SEMICOLON || type == PHPRegionTypes.PHP_CURLY_OPEN || type == PHPRegionTypes.PHP_CURLY_CLOSE) {
				startPosition = textStart + 1;
				break;
			}
			tRegion = sdRegion.getRegionAtCharacterOffset(textStart - 1);
		}
		TextSequence textSequence = TextSequenceUtilities.createTextSequence(sdRegion, startPosition, offset - startPosition);

		if (removeComments) {
			textSequence = removeComments(textSequence);
		}
		// remove spaces from start.
		textSequence = textSequence.subTextSequence(readForwardSpaces(textSequence, 0), textSequence.length());
		return textSequence;
	}

	private static TextSequence removeComments(TextSequence textSequence) {
		while (true) {
			int commentStartPosition = getCommentStartIndex(textSequence);
			if (commentStartPosition > -1) {
				String startCommentString = textSequence.subSequence(commentStartPosition, commentStartPosition + 2).toString();
				if (startCommentString.equals(START_COMMENT)) {
					// we are inside comment.
					Matcher commentEndMatcher = COMMENT_END_PATTERN.matcher(textSequence);
					boolean foundEnd = commentEndMatcher.find(commentStartPosition);
					if (foundEnd) {
						int commentEndPosition = commentEndMatcher.end();
						textSequence = textSequence.cutTextSequence(commentStartPosition, commentEndPosition);
						continue;
					}
				} else {
					// we are inside line comment.
					int commentEndPosition = commentStartPosition + 2;
					for (; commentEndPosition < textSequence.length(); commentEndPosition++) {
						if (textSequence.charAt(commentEndPosition) == END_LINE) {
							textSequence = textSequence.cutTextSequence(commentStartPosition, commentEndPosition);
							continue;
						}
					}
				}
			}
			return textSequence;
		}
	}

	private static int getCommentStartIndex(TextSequence textSequence) {
		Matcher commentStartMatcher = COMMENT_START_PATTERN.matcher(textSequence);
		int start = 0;
		while (commentStartMatcher.find(start)) {
			String currentType = TextSequenceUtilities.getType(textSequence, commentStartMatcher.start());
			if (currentType != null && currentType.equals("")) {
			}
			if (!PhpLexer.isPHPCommentState(currentType) && !PhpLexer.isPHPQuotesState(currentType)) {
				return commentStartMatcher.start();
			}
			start = commentStartMatcher.start() + 2;
		}
		return -1;
	}
	
	public static ITextRegion getMultilineCommentStartRegion (IStructuredDocumentRegion sdRegion, int offset) {
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset (offset);
		ITextRegion startRegion = null;
		try {
			while (PhpLexer.isPHPMultiLineCommentState(tRegion.getType())) {
				if (tRegion.getType().equals(PHPRegionTypes.PHP_COMMENT_START)) {
					startRegion = tRegion;
					break;
				}
				tRegion = sdRegion.getRegionAtCharacterOffset(sdRegion.getStartOffset(tRegion) - 1);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return startRegion;
	}
	
	public static ITextRegion getMultilineCommentEndRegion (IStructuredDocumentRegion sdRegion, int offset) {
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset (offset);
		ITextRegion endRegion = null;
		try {
			while (PhpLexer.isPHPMultiLineCommentState(tRegion.getType())) {
				if (tRegion.getType().equals(PHPRegionTypes.PHP_COMMENT_END)) {
					endRegion = tRegion;
					break;
				}
				tRegion = sdRegion.getRegionAtCharacterOffset(sdRegion.getEndOffset(tRegion) + 1);
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return endRegion;
	}

	/**
	 * Checks if we are inside function declaretion statment.
	 * If yes the start offset of the function, otherwisw returns -1.
	 */
	public static int isInFunctionDeclaretion(TextSequence textSequence) {
		Matcher matcher = FUNCTION_PATTERN.matcher(textSequence);
		// search for the 'function' word.
		while (matcher.find()) {
			// verify char before 'function' word.
			int functionStart = matcher.start();
			if (functionStart != 0 && Character.isJavaIdentifierStart(textSequence.charAt(functionStart - 1))) {
				continue;
			}

			// verfy state
			String type = TextSequenceUtilities.getType(textSequence, functionStart + 1);
			if (PhpLexer.isPHPRegularState(type)) {
				// verify the function is not closed.
				int offset;
				for (offset = matcher.end(); offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == ')') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence, offset);
						if (PhpLexer.isPHPRegularState(type)) {
							break;
						}
					}
				}
				if (offset == textSequence.length()) {
					return functionStart;
				}
			}
		}
		return -1;
	}

	public static int isInClassDeclaretion(TextSequence textSequence) {
		Matcher matcher = CLASS_PATTERN.matcher(textSequence);
		// search for the 'class' or 'interface words.
		while (matcher.find()) {
			// verify char before start.
			int startOffset = matcher.start();
			if (startOffset != 0 && Character.isJavaIdentifierStart(textSequence.charAt(startOffset - 1))) {
				continue;
			}
			// verfy state
			String type = TextSequenceUtilities.getType(textSequence, startOffset + 1);
			if (PhpLexer.isPHPRegularState(type)) {
				int endOffset = matcher.end();
				// verify the class is not closed.
				int offset;
				for (offset = endOffset; offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == '}') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence, offset);
						if (PhpLexer.isPHPRegularState(type)) {
							break;
						}
					}
				}
				if (offset == textSequence.length()) {
					return endOffset;
				}
			}
		}
		return -1;
	}

	public static int readIdentifiarStartIndex(TextSequence textSequence, int startPosition, boolean includeDolar) {
		while (startPosition > 0) {
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				break;
			}
			startPosition--;
		}
		if (includeDolar && startPosition > 0 && textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		return startPosition;
	}

	public static int readIdentifiarEndIndex(TextSequence textSequence, int startPosition, boolean includeDolar) {
		int length = textSequence.length();
		if (includeDolar && startPosition < length && textSequence.charAt(startPosition) == '$') {
			startPosition++;
		}
		while (startPosition < length) {
			char ch = textSequence.charAt(startPosition);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				break;
			}
			startPosition++;
		}
		return startPosition;
	}

	public static int readBackwardSpaces(TextSequence textSequence, int startPosition) {
		int rv = startPosition;
		for (; rv > 0; rv--) {
			if (!Character.isWhitespace(textSequence.charAt(rv - 1))) {
				break;
			}
		}
		return rv;
	}

	public static int readForwardSpaces(TextSequence textSequence, int startPosition) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			if (!Character.isWhitespace(textSequence.charAt(rv))) {
				break;
			}
		}
		return rv;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int getPrivousTriggerIndex(TextSequence textSequence, int startPosition) {
		int rv = startPosition;
		int bracketsNum = 0;
		for (; rv > 0; rv--) {
			char currChar = textSequence.charAt(rv - 1);
			if (!Character.isLetterOrDigit(currChar) && currChar != '_' && currChar != '$' && !(Character.isWhitespace(currChar) && currChar != '\n')) {
				switch (currChar) {
					case '(':
					case '[':
					case '{':
						bracketsNum--;
						if (bracketsNum < 0) {
							return -1;
						}
						break;
					case ')':
					case ']':
					case '}':
						bracketsNum++;
						break;
					case ':':
						if (bracketsNum == 0 && rv >= 2) {
							if (textSequence.charAt(rv - 2) == ':') {
								return rv - 2;
							}
						}
						break;
					case '>':
						if (bracketsNum == 0 && rv >= 2) {
							if (textSequence.charAt(rv - 2) == '-') {
								return rv - 2;
							}
						}
						break;
					case '\n':
						return -1;
					default:
						if (bracketsNum == 0) {
							return -1;
						}
				}
			}
		}
		return -1;
	}

}
