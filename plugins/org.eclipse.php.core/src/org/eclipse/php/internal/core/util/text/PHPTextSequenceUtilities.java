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
package org.eclipse.php.internal.core.util.text;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPhpLexer;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.PhpLexerFactory;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPhpScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionCollection;
import org.eclipse.wst.sse.core.internal.provisional.text.ITextRegionContainer;

public class PHPTextSequenceUtilities {

	private static final Pattern COMMENT_END_PATTERN = Pattern.compile("[*]/"); //$NON-NLS-1$
	private static final String START_LINE_COMMENT = "//"; //$NON-NLS-1$
	private static final String START_BLOCK_COMMENT = "/*"; //$NON-NLS-1$

	private static final char END_LINE = '\n';
	private static final Pattern FUNCTION_PATTERN = Pattern.compile(
			"function\\s", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$
	private static final Pattern CLASS_PATTERN = Pattern.compile(
			"(class|interface)\\s", Pattern.CASE_INSENSITIVE); //$NON-NLS-1$

	private static final String LBRACE = "{"; //$NON-NLS-1$
	private static final String RBRACE = "}"; //$NON-NLS-1$
	private static final String LPAREN = "("; //$NON-NLS-1$
	private static final String RPAREN = ")"; //$NON-NLS-1$
	private static final String COMMA = ","; //$NON-NLS-1$
	private static final String LBRACKET = "["; //$NON-NLS-1$
	private static final String RBRACKET = "]"; //$NON-NLS-1$

	private PHPTextSequenceUtilities() {
	}

	/**
	 * This function returns statement text depending on the current offset. It
	 * searches backwards until it finds ';', '{' or '}'.
	 * 
	 * @param offset
	 *            The absolute offset in the document
	 * @param sdRegion
	 *            Structured document region of the offset
	 * @param removeComments
	 *            Flag determining whether to remove comments in the resulted
	 *            text sequence
	 * 
	 * @return text sequence of the statement, cannot be null
	 */
	public static TextSequence getStatement(int offset,
			IStructuredDocumentRegion sdRegion, boolean removeComments) {
		int documentOffset = offset;
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion
				.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
		}
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			tRegion = container.getRegionAtCharacterOffset(container
					.getStartOffset() + tRegion.getStart() - 1);
		}

		// This text region must be of type PhpScriptRegion:
		if (tRegion != null
				&& tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset()
						+ phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPhpToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPhpToken(offset
							- startOffset - 1);
				}
				while (true) {
					// If statement start is at the beginning of the PHP script
					// region:
					if (startTokenRegion.getStart() == 0) {
						break;
					}
					if (startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_CLOSE
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_CURLY_OPEN
							|| startTokenRegion.getType() == PHPRegionTypes.PHP_SEMICOLON
					/* || startTokenRegion.getType() == PHPRegionTypes.PHP_IF */) {
						// Calculate starting position of the statement (it
						// should go right after this startTokenRegion):
						startOffset += startTokenRegion.getEnd();
						break;
					}
					startTokenRegion = phpScriptRegion
							.getPhpToken(startTokenRegion.getStart() - 1);
				}

				TextSequence textSequence = TextSequenceUtilities
						.createTextSequence(sdRegion, startOffset, offset
								- startOffset);

				// remove comments
				if (removeComments) {
					textSequence = removeComments(textSequence);
				}

				// remove spaces from start.
				textSequence = textSequence.subTextSequence(
						readForwardSpaces(textSequence, 0),
						textSequence.length());
				return textSequence;
			} catch (BadLocationException e) {
			}
		}

		return TextSequenceUtilities.createTextSequence(sdRegion, 0, 0);
	}

	private static TextSequence removeComments(TextSequence textSequence) {
		List<IRegion> comments = collectComments(textSequence);
		for (int i = comments.size() - 1; i >= 0; i--) {
			IRegion commentStartRegion = comments.get(i);
			int end = Math.min(commentStartRegion.getOffset()
					+ commentStartRegion.getLength(), textSequence.length());
			textSequence = textSequence.cutTextSequence(
					commentStartRegion.getOffset(), end);
		}
		return textSequence;
	}

	private static List<IRegion> collectComments(TextSequence textSequence) {
		StringBuffer buffer = new StringBuffer(textSequence);
		List<IRegion> commentRegions = new ArrayList<IRegion>();
		int start = 0;
		int foundIndex = 0;
		while ((foundIndex = buffer.indexOf("/", start)) != -1) { //$NON-NLS-1$
			int commentStartPosition = foundIndex;
			String currentType = TextSequenceUtilities.getType(textSequence,
					commentStartPosition);
			if (PHPPartitionTypes.isPHPCommentState(currentType)
					&& !PHPPartitionTypes.isPHPQuotesState(currentType)
					&& commentStartPosition + 2 < textSequence.length()) {
				String startCommentString = textSequence.subSequence(
						commentStartPosition, commentStartPosition + 2)
						.toString();
				if (startCommentString.equals(START_BLOCK_COMMENT)) {
					// we are inside comment.
					Matcher commentEndMatcher = COMMENT_END_PATTERN
							.matcher(textSequence);
					boolean foundEnd = commentEndMatcher
							.find(commentStartPosition);
					if (foundEnd) {
						int commentEndPosition = commentEndMatcher.end();
						IRegion range = new Region(commentStartPosition,
								commentEndPosition - commentStartPosition);
						commentRegions.add(range);
						start = commentEndPosition;
					} else {
						start = commentStartPosition + 2;
					}
				} else if (startCommentString.equals(START_LINE_COMMENT)) {
					// we are inside line comment.
					for (int commentEndPosition = commentStartPosition + 2; commentEndPosition < textSequence
							.length(); commentEndPosition++) {
						if (textSequence.charAt(commentEndPosition) == END_LINE) {
							IRegion range = new Region(commentStartPosition,
									commentEndPosition - commentStartPosition);
							commentRegions.add(range);
							start = commentEndPosition;
							break;
						}
					}
					start = commentStartPosition + 2;
				} else {
					start = commentStartPosition + 1;
				}
			} else {
				start = commentStartPosition + 2;
			}
		}
		return commentRegions;
	}

	public static int getMethodEndIndex(CharSequence textSequence, int offset) {
		int length = textSequence.length();
		while (offset < length
				&& Character.isWhitespace(textSequence.charAt(offset))) {
			++offset;
		}
		if (offset < length && textSequence.charAt(offset) == '(') {
			++offset;
		} else {
			return -1;
		}
		while (offset < length && textSequence.charAt(offset) != ')') {
			++offset;
		}
		if (textSequence.length() > offset
				&& textSequence.charAt(offset) == ')') {
			return offset + 1;
		}
		return -1;
	}

	/**
	 * Checks if we are inside function declaration statement. If yes the start
	 * offset of the function, otherwise returns -1.
	 */
	public static int isInFunctionDeclaration(TextSequence textSequence) {
		Matcher matcher = FUNCTION_PATTERN.matcher(textSequence);
		// search for the 'function' word.
		while (matcher.find()) {
			// verify char before 'function' word.
			int functionStart = matcher.start();
			if (functionStart != 0
					&& Character.isJavaIdentifierStart(textSequence
							.charAt(functionStart - 1))) {
				continue;
			}

			// verfy state
			String type = TextSequenceUtilities.getType(textSequence,
					functionStart + 1);
			if (PHPPartitionTypes.isPHPRegularState(type)) {
				// verify the function is not closed.
				int offset;
				for (offset = matcher.end(); offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == ')') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence,
								offset);
						if (PHPPartitionTypes.isPHPRegularState(type)) {
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

	public static int isInClassDeclaration(TextSequence textSequence) {
		Matcher matcher = CLASS_PATTERN.matcher(textSequence);
		// search for the 'class' or 'interface words.
		while (matcher.find()) {
			// verify char before start.
			int startOffset = matcher.start();
			if (startOffset != 0
					&& Character.isJavaIdentifierStart(textSequence
							.charAt(startOffset - 1))) {
				continue;
			}
			// verify state
			String type = TextSequenceUtilities.getType(textSequence,
					startOffset + 1);
			if (PHPPartitionTypes.isPHPRegularState(type)) {
				int endOffset = matcher.end();
				// verify the class is not closed.
				int offset;
				for (offset = endOffset; offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == '}') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence,
								offset);
						if (PHPPartitionTypes.isPHPRegularState(type)) {
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

	public static int readNamespaceStartIndex(CharSequence textSequence,
			int startPosition, boolean includeDollar) {
		boolean onBackslash = false;
		boolean onWhitespace = false;
		int oldStartPosition = startPosition;
		startPosition = readBackwardSpaces(textSequence, startPosition);
		while (startPosition > 0) {
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				if (ch == '\\') {
					if (onBackslash) {
						break;
					}
					onBackslash = true;
					onWhitespace = false;
				} else if (Character.isWhitespace(ch)) {
					onWhitespace = true;
					onBackslash = false;
				} else {
					break;
				}
			} else {
				if (onWhitespace) {
					break;
				}
				onBackslash = false;
				onWhitespace = false;
			}
			startPosition--;
		}
		if (includeDollar && startPosition > 0
				&& textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		startPosition = startPosition >= 0 ? readForwardSpaces(textSequence,
				startPosition) : startPosition;
		// FIXME bug 291970 i do not know if this is right or not
		if (startPosition > oldStartPosition) {
			startPosition = oldStartPosition;
		}
		return startPosition;
	}

	public static int readNamespaceEndIndex(CharSequence textSequence,
			int startPosition, boolean includeDollar) {
		boolean onBackslash = false;
		boolean onWhitespace = false;

		int length = textSequence.length();
		if (includeDollar && startPosition < length
				&& textSequence.charAt(startPosition) == '$') {
			startPosition++;
		}
		while (startPosition < length) {
			char ch = textSequence.charAt(startPosition);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				if (ch == '\\') {
					if (onBackslash) {
						break;
					}
					onBackslash = true;
					onWhitespace = false;
				} else if (Character.isWhitespace(ch)) {
					onWhitespace = true;
					onBackslash = false;
				} else {
					break;
				}
			} else {
				if (onWhitespace) {
					break;
				}
				onBackslash = false;
				onWhitespace = false;
			}
			startPosition++;
		}
		return startPosition >= 0 ? readBackwardSpaces(textSequence,
				startPosition) : startPosition;
	}

	public static int readIdentifierStartIndex(CharSequence textSequence,
			int startPosition, boolean includeDolar) {
		while (startPosition > 0) {
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				break;
			}
			startPosition--;
		}
		if (includeDolar && startPosition > 0
				&& textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		return startPosition;
	}

	public static int readIdentifierEndIndex(CharSequence textSequence,
			int startPosition, boolean includeDolar) {
		int length = textSequence.length();
		if (includeDolar && startPosition < length
				&& textSequence.charAt(startPosition) == '$') {
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

	public static int readIdentifierStartIndex(PHPVersion phpVersion,
			CharSequence textSequence, int startPosition, boolean includeDollar) {
		if (phpVersion.isLessThan(PHPVersion.PHP5_3)) {
			return PHPTextSequenceUtilities.readIdentifierStartIndex(
					textSequence, startPosition, includeDollar);
		}
		return PHPTextSequenceUtilities.readNamespaceStartIndex(textSequence,
				startPosition, includeDollar);
	}

	public static int readIdentifierEndIndex(PHPVersion phpVersion,
			CharSequence textSequence, int startPosition, boolean includeDollar) {
		if (phpVersion.isLessThan(PHPVersion.PHP5_3)) {
			return PHPTextSequenceUtilities.readIdentifierEndIndex(
					textSequence, startPosition, includeDollar);
		}
		return PHPTextSequenceUtilities.readNamespaceEndIndex(textSequence,
				startPosition, includeDollar);
	}

	/**
	 * Tries to find identifier enclosing given position.
	 * 
	 * @param contents
	 * @param pos
	 * @return
	 */
	public static ISourceRange getEnclosingIdentifier(
			CharSequence textSequence, int pos) {
		if (pos < 0 || pos >= textSequence.length())
			return null;

		int start = readIdentifierStartIndex(textSequence, pos, true);
		int end = readIdentifierEndIndex(textSequence, pos, true);

		if (start > end)
			return null;

		return new SourceRange(start, end - start + 1);
	}

	public static int readBackwardSpaces(CharSequence textSequence,
			int startPosition) {
		int rv = startPosition;
		for (; rv > 0; rv--) {
			if (!Character.isWhitespace(textSequence.charAt(rv - 1))) {
				break;
			}
		}
		return rv;
	}

	public static int readForwardSpaces(CharSequence textSequence,
			int startPosition) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			if (!Character.isWhitespace(textSequence.charAt(rv))) {
				break;
			}
		}
		return rv;
	}

	/**
	 * Returns the next position on the text where one the given delimiters
	 * start
	 * 
	 * @param textSequence
	 *            - The input text sequence
	 * @param startPosition
	 *            - The current position in the text sequence to start from
	 * @param delims
	 *            - The array of delimiters
	 */
	public static int readForwardUntilDelim(CharSequence textSequence,
			int startPosition, char[] delims) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			char c = textSequence.charAt(rv);
			if (isDelimiter(c, delims)) {
				break;
			}
		}
		return rv;
	}

	private static boolean isDelimiter(char c, char[] delims) {
		for (char curr : delims) {
			if (curr == c) {
				return true;
			}
		}
		return false;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int getPrivousTriggerIndex(CharSequence textSequence,
			int startPosition) {
		int rv = startPosition;
		int bracketsNum = 0;
		char inStringMode = 0;
		boolean inWhiteSpaceBeforeLiteral = false;
		boolean inLiteral = false;
		for (; rv > 0; rv--) {
			char currChar = textSequence.charAt(rv - 1);
			if (currChar == '\'' || currChar == '"') {
				inStringMode = inStringMode == 0 ? currChar
						: inStringMode == currChar ? 0 : inStringMode;
			}
			if (inStringMode != 0) {
				continue;
			}

			// The next block solves bug #205034:
			// store state for whitespace before literals and if another literal
			// comes before it - return 'not found'
			if (Character.isLetterOrDigit(currChar) || currChar == '$') {
				if (inWhiteSpaceBeforeLiteral && bracketsNum == 0) {
					return -1;
				}
				inLiteral = true;
			} else {
				if (inLiteral && Character.isWhitespace(currChar)) {
					inWhiteSpaceBeforeLiteral = true;
				}
				if (!Character.isWhitespace(currChar)) {
					inWhiteSpaceBeforeLiteral = false;
				}
				inLiteral = false;
			}

			if (!Character.isLetterOrDigit(currChar) && currChar != '_'
					&& currChar != '$' && !Character.isWhitespace(currChar)) {
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
						} else {
							return -1;
						}
					}
					break;
				case '>':
					if (bracketsNum == 0 && rv >= 2) {
						if (textSequence.charAt(rv - 2) == '-') {
							return rv - 2;
						} else {
							return -1;
						}
					}
					break;
				default:
					if (bracketsNum == 0) {
						return -1;
					}
				}
			}
		}
		return -1;
	}

	public static int readIdentifierListStartIndex(CharSequence textSequence,
			int endPosition) {
		int startPosition = endPosition;
		int listStartPosition = startPosition;
		boolean beforeWhitespace = false;
		boolean beforeComma = false;
		while (startPosition > 0) {
			final char ch = textSequence.charAt(startPosition - 1);
			if (Character.isLetterOrDigit(ch) || ch == '_') {
				if (beforeWhitespace) {
					// identifiers delimited by a whitespace are not a list:
					return --listStartPosition;
				}
				listStartPosition = startPosition;
				beforeComma = false;
			} else if (ch == ',') {
				if (beforeComma) {
					// only one comma may delimit a list
					return endPosition;
				}
				beforeComma = true;
				beforeWhitespace = false;
			} else if (Character.isWhitespace(ch) && !beforeComma) {
				beforeWhitespace = true;
			} else {
				return --listStartPosition;
			}
			startPosition--;
		}
		return listStartPosition;
	}

	/**
	 * Read string argnames from CharSequence
	 * 
	 * TODO Nested parenthesis expression
	 * 
	 * @param phpVersion
	 * @param textSequence
	 * @return
	 */
	public static String[] getArgNames(PHPVersion phpVersion,
			CharSequence textSequence) {
		List<String> args = new ArrayList<String>();
		if (textSequence != null && textSequence.length() > 2) {
			if (textSequence.charAt(textSequence.length() - 1) == ')') {
				textSequence = textSequence.subSequence(0,
						textSequence.length() - 1);
			}
			if (textSequence.charAt(0) == '(') {
				textSequence = textSequence.subSequence(1,
						textSequence.length());
			}
			if (phpVersion == null) {
				phpVersion = PHPVersion.getLatestVersion();
			}

			AbstractPhpLexer lexer = PhpLexerFactory.createLexer(
					new StringReader(textSequence.toString()), phpVersion);
			lexer.initialize(lexer.getScriptingState());
			String symbol = null;
			int level = 0;
			int argIndex = 0;
			do {
				try {
					symbol = lexer.getNextToken();
					if (symbol != null) {
						CharSequence text = textSequence.subSequence(
								lexer.getTokenStart(), lexer.getTokenStart()
										+ lexer.getLength());
						if (symbol.equals(PHPRegionTypes.PHP_TOKEN)) {
							if (text.equals(LPAREN) || text.equals(LBRACE)
									|| text.equals(LBRACKET)) {
								level++;
							} else if (text.equals(RPAREN)
									|| text.equals(RBRACE)
									|| text.equals(RBRACKET)) {
								level--;
							} else if (level == 0 && text.equals(COMMA)) {
								argIndex++;
							}
						} else if (level == 0
								&& symbol
										.equals(PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING)) {
							if (args.size() < argIndex + 1) {
								args.add(text.toString());
							}
						} else if (level == 0
								&& !symbol.equals(PHPRegionTypes.WHITESPACE)) {
							if (args.size() < argIndex + 1) {
								args.add(null);
							} else {
								args.set(argIndex, null);
							}
						}

					}
				} catch (IOException e) {
					symbol = null;
				}
			} while (symbol != null);
		}

		return args.toArray(new String[args.size()]);
	}

}
