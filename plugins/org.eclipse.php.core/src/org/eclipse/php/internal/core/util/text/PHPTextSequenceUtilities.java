/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016 IBM Corporation and others.
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

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.ISourceRange;
import org.eclipse.dltk.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.PHPVersion;
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

	private static final Pattern FUNCTION_PATTERN = Pattern.compile("function[ \\t\\n\\r]", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);
	private static final Pattern CLASS_PATTERN = Pattern.compile("(class|interface)[ \\t\\n\\r]", //$NON-NLS-1$
			Pattern.CASE_INSENSITIVE);

	private static final String LBRACE = "{"; //$NON-NLS-1$
	private static final String RBRACE = "}"; //$NON-NLS-1$
	private static final String LPAREN = "("; //$NON-NLS-1$
	private static final String RPAREN = ")"; //$NON-NLS-1$
	private static final String COMMA = ","; //$NON-NLS-1$
	private static final String LBRACKET = "["; //$NON-NLS-1$
	private static final String RBRACKET = "]"; //$NON-NLS-1$

	private static final String OBJECT_OPERATOR = "->"; //$NON-NLS-1$
	private static final String PAAMAYIM_NEKUDOTAYIM = "::"; //$NON-NLS-1$

	private PHPTextSequenceUtilities() {
	}

	/**
	 * This function returns statement text depending on the current offset. It
	 * searches backwards (starting from offset - 1) until it finds ';', '{' or
	 * '}'.
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
	public static @NonNull TextSequence getStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean removeComments) {
		int documentOffset = offset;
		if (documentOffset == sdRegion.getEndOffset()) {
			documentOffset -= 1;
		}
		ITextRegion tRegion = sdRegion.getRegionAtCharacterOffset(documentOffset);

		ITextRegionCollection container = sdRegion;

		if (tRegion instanceof ITextRegionContainer) {
			container = (ITextRegionContainer) tRegion;
			tRegion = container.getRegionAtCharacterOffset(offset);
		}
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CLOSE) {
			tRegion = container.getRegionAtCharacterOffset(container.getStartOffset() + tRegion.getStart() - 1);
		}

		// This text region must be of type PhpScriptRegion:
		if (tRegion != null && tRegion.getType() == PHPRegionContext.PHP_CONTENT) {
			IPhpScriptRegion phpScriptRegion = (IPhpScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset() + phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPhpToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPhpToken(offset - startOffset - 1);
				}

				List<IRegion> comments = new ArrayList<IRegion>();
				while (true) {
					// If statement start is at the beginning of the PHP script
					// region:
					if (startTokenRegion.getStart() == 0) {
						break;
					}

					String type = startTokenRegion.getType();
					if (removeComments && PHPPartitionTypes.isPHPCommentState(type)) {
						comments.add(new Region(phpScriptRegion.getStart() + startTokenRegion.getStart(),
								startTokenRegion.getLength()));
					}

					if (type == PHPRegionTypes.PHP_CURLY_CLOSE || type == PHPRegionTypes.PHP_CURLY_OPEN
							|| type == PHPRegionTypes.PHP_SEMICOLON
					/* || startTokenRegion.getType() == PHPRegionTypes.PHP_IF */) {
						// Calculate starting position of the statement (it
						// should go right after this startTokenRegion):
						startOffset += startTokenRegion.getEnd();
						break;
					}
					startTokenRegion = phpScriptRegion.getPhpToken(startTokenRegion.getStart() - 1);
				}
				TextSequence textSequence = TextSequenceUtilities.createTextSequence(sdRegion, startOffset,
						offset - startOffset);

				// remove comments
				if (removeComments) {
					textSequence = removeComments(textSequence, comments);
				}

				// remove spaces from start.
				return textSequence.subTextSequence(readForwardSpaces(textSequence, 0), textSequence.length());
			} catch (BadLocationException e) {
			}
		}

		return TextSequenceUtilities.createTextSequence(sdRegion, 0, 0);
	}

	/**
	 * <p>
	 * This function returns statement region depending on the current offset.
	 * It searches backwards (starting from offset - 1) until it finds ';', '{'
	 * or '}'.
	 * </p>
	 * <p>
	 * <b> Be careful, empty region can be returned (i.e. region's length is 0)
	 * when no statement was found. In this case, the offset from the returned
	 * region has no special meaning.
	 * </p>
	 * </b>
	 * 
	 * @param offset
	 *            The absolute offset in the document
	 * @param sdRegion
	 *            Structured document region of the offset
	 * @param ignoreStartComments
	 *            move start offset to no-comment region
	 * 
	 * @return text sequence region, cannot be null
	 */
	public static @NonNull Region getStatementRegion(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean ignoreStartComments) {
		// temporary workaround to fix
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=472197
		TextSequence textSequence = getStatement(offset, sdRegion, ignoreStartComments);
		return new Region(textSequence.getOriginalOffset(0), textSequence.length());
	}

	/**
	 * 
	 * @param textSequence
	 * @param comments
	 *            comments must be reverse ordered (i.e. from latest to first in
	 *            document)
	 * @return
	 */
	private static @NonNull TextSequence removeComments(@NonNull TextSequence textSequence, List<IRegion> comments) {
		int seqStart = textSequence.getOriginalOffset(0);
		for (IRegion commentStartRegion : comments) {
			int textSequenceLength = textSequence.length();
			if (textSequenceLength == 0) {
				break;
			}
			int start = commentStartRegion.getOffset() - seqStart;
			int end = start + commentStartRegion.getLength();
			if (end <= 0) {
				// no need to handle remaining comments
				break;
			}
			if (start >= textSequenceLength) {
				continue;
			}
			start = Math.max(0, start);
			end = Math.min(textSequenceLength, end);
			textSequence = textSequence.cutTextSequence(start, end);
		}
		return textSequence;
	}

	public static int getMethodEndIndex(@NonNull CharSequence textSequence, int offset,
			boolean allowToStartWithWhitespaces) {
		int length = textSequence.length();
		if (allowToStartWithWhitespaces) {
			while (offset < length && Character.isWhitespace(textSequence.charAt(offset))) {
				++offset;
			}
		}
		if (offset < length && textSequence.charAt(offset) == '(') {
			++offset;
		} else {
			return -1;
		}
		while (offset < length && textSequence.charAt(offset) != ')') {
			++offset;
		}
		if (textSequence.length() > offset && textSequence.charAt(offset) == ')') {
			return offset + 1;
		}
		return -1;
	}

	/**
	 * Checks if we are inside function declaration statement. If yes the start
	 * offset of the function, otherwise returns -1.
	 */
	public static int isInFunctionDeclaration(@NonNull TextSequence textSequence) {
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
			if (PHPPartitionTypes.isPHPRegularState(type)) {
				// verify the function is not closed.
				int offset;
				boolean possibleReturnType = false;
				boolean returnType = false;
				for (offset = matcher.end(); offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == ')') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence, offset);
						if (PHPPartitionTypes.isPHPRegularState(type)) {
							possibleReturnType = true;
						}
					} else if ((possibleReturnType || returnType) && textSequence.charAt(offset) == '{') {
						break;
					} else if (possibleReturnType && textSequence.charAt(offset) == ':') {
						possibleReturnType = false;
						returnType = true;
					} else if (possibleReturnType && !Character.isWhitespace(textSequence.charAt(offset))) {
						break;
					}
				}
				if (offset == textSequence.length()) {
					return functionStart;
				}
			}
		}
		return -1;
	}

	private static boolean isClassOrInterfaceKeyword(@NonNull TextSequence textSequence, int classStartOffset) {
		if (classStartOffset == 0) {
			return true;
		}
		int offset = readBackwardSpaces(textSequence, classStartOffset);
		if (offset == 0) {
			return true;
		}
		if (offset == classStartOffset && Character.isJavaIdentifierStart(textSequence.charAt(offset - 1))) {
			return false;
		}
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=501974
		// Since PHP 5.5, keyword "class" can be used as a class constant name
		// (for class name resolution). Let's keep it simple, exclude current
		// match when there is an object/class operator before keyword "class"
		// or "interface".
		// XXX: handle comments between object/class operators and keyword
		// "class" or "interface".
		assert OBJECT_OPERATOR.length() == 2 && PAAMAYIM_NEKUDOTAYIM.length() == 2;
		if (offset < 2) {
			return true;
		}
		String s = textSequence.subSequence(offset - 2, offset).toString();
		if (OBJECT_OPERATOR.equals(s) || PAAMAYIM_NEKUDOTAYIM.equals(s)) {
			return false;
		}
		return true;
	}

	public static int isInClassDeclaration(@NonNull TextSequence textSequence) {
		Matcher matcher = CLASS_PATTERN.matcher(textSequence);
		// search for the 'class' or 'interface words.
		while (matcher.find()) {
			// verify char before start.
			int startOffset = matcher.start();
			if (!isClassOrInterfaceKeyword(textSequence, startOffset)) {
				continue;
			}
			// verify state
			String type = TextSequenceUtilities.getType(textSequence, startOffset + 1);
			if (PHPPartitionTypes.isPHPRegularState(type)) {
				int endOffset = matcher.end();
				// verify the class is not closed.
				int offset;
				for (offset = endOffset; offset < textSequence.length(); offset++) {
					if (textSequence.charAt(offset) == '}') {
						// verify state
						type = TextSequenceUtilities.getType(textSequence, offset);
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

	/**
	 * @return start index (can be < 0)
	 */
	public static int readNamespaceStartIndex(@NonNull CharSequence textSequence, int startPosition,
			boolean includeDollar) {
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
		if (includeDollar && startPosition > 0 && textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		startPosition = startPosition >= 0 ? readForwardSpaces(textSequence, startPosition) : startPosition;
		// FIXME bug 291970 i do not know if this is right or not
		if (startPosition > oldStartPosition) {
			startPosition = oldStartPosition;
		}
		return startPosition;
	}

	public static int readNamespaceEndIndex(@NonNull CharSequence textSequence, int startPosition,
			boolean includeDollar) {
		boolean onBackslash = false;
		boolean onWhitespace = false;

		int length = textSequence.length();
		if (includeDollar && startPosition < length && textSequence.charAt(startPosition) == '$') {
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
		return startPosition >= 0 ? readBackwardSpaces(textSequence, startPosition) : startPosition;
	}

	/**
	 * @return start index (can be < 0)
	 */
	public static int readIdentifierStartIndex(@NonNull CharSequence textSequence, int startPosition,
			boolean includeDollar) {
		while (startPosition > 0) {
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				break;
			}
			startPosition--;
		}
		if (includeDollar && startPosition > 0 && textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		return startPosition;
	}

	public static int readIdentifierEndIndex(@NonNull CharSequence textSequence, int startPosition,
			boolean includeDollar) {
		int length = textSequence.length();
		if (includeDollar && startPosition < length && textSequence.charAt(startPosition) == '$') {
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

	/**
	 * @return start index (can be < 0)
	 */
	public static int readIdentifierStartIndex(@NonNull PHPVersion phpVersion, @NonNull CharSequence textSequence,
			int startPosition, boolean includeDollar) {
		if (phpVersion.isLessThan(PHPVersion.PHP5_3)) {
			return readIdentifierStartIndex(textSequence, startPosition, includeDollar);
		}
		return readNamespaceStartIndex(textSequence, startPosition, includeDollar);
	}

	public static int readIdentifierEndIndex(@NonNull PHPVersion phpVersion, @NonNull CharSequence textSequence,
			int startPosition, boolean includeDollar) {
		if (phpVersion.isLessThan(PHPVersion.PHP5_3)) {
			return readIdentifierEndIndex(textSequence, startPosition, includeDollar);
		}
		return readNamespaceEndIndex(textSequence, startPosition, includeDollar);
	}

	/**
	 * Tries to find identifier enclosing given position.
	 * 
	 * @param contents
	 * @param pos
	 * @return
	 */
	public static @Nullable ISourceRange getEnclosingIdentifier(@NonNull CharSequence textSequence, int pos) {
		if (pos < 0 || pos >= textSequence.length())
			return null;

		int start = readIdentifierStartIndex(textSequence, pos, true);
		int end = readIdentifierEndIndex(textSequence, pos, true);

		if (start < 0 || start > end)
			return null;

		return new SourceRange(start, end - start + 1);
	}

	public static int readBackwardSpaces(@NonNull CharSequence textSequence, int startPosition) {
		int rv = startPosition;
		for (; rv > 0; rv--) {
			if (!Character.isWhitespace(textSequence.charAt(rv - 1))) {
				break;
			}
		}
		return rv;
	}

	public static int readForwardSpaces(@NonNull IDocument document, int startPosition, int endPosition)
			throws BadLocationException {
		int rv = startPosition;
		for (; rv < endPosition; rv++) {
			if (!Character.isWhitespace(document.getChar(rv))) {
				break;
			}
		}
		return rv;
	}

	public static int readForwardSpaces(@NonNull CharSequence textSequence, int startPosition) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			if (!Character.isWhitespace(textSequence.charAt(rv))) {
				break;
			}
		}
		return rv;
	}

	public static int readForwardUntilSpaces(@NonNull CharSequence textSequence, int startPosition) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			if (Character.isWhitespace(textSequence.charAt(rv))) {
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
	public static int readForwardUntilDelim(@NonNull CharSequence textSequence, int startPosition,
			@NonNull char[] delims) {
		int rv = startPosition;
		for (; rv < textSequence.length(); rv++) {
			char c = textSequence.charAt(rv);
			if (isDelimiter(c, delims)) {
				break;
			}
		}
		return rv;
	}

	private static boolean isDelimiter(char c, @NonNull char[] delims) {
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

	public static int getPreviousTriggerIndex(@NonNull CharSequence textSequence, int startPosition) {
		int rv = startPosition;
		int bracketsNum = 0;
		char inStringMode = 0;
		boolean inWhiteSpaceBeforeLiteral = false;
		boolean inLiteral = false;
		for (; rv > 0; rv--) {
			char currChar = textSequence.charAt(rv - 1);
			if (currChar == '\'' || currChar == '"') {
				inStringMode = inStringMode == 0 ? currChar : inStringMode == currChar ? 0 : inStringMode;
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

			if (!Character.isLetterOrDigit(currChar) && currChar != '_' && currChar != '$'
					&& !Character.isWhitespace(currChar)) {
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

	public static int readIdentifierListStartIndex(@NonNull CharSequence textSequence, int endPosition) {
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
	public static @NonNull String[] getArgNames(@Nullable PHPVersion phpVersion, @Nullable CharSequence textSequence) {
		List<String> args = new ArrayList<String>();
		if (textSequence != null && textSequence.length() > 2) {
			if (textSequence.charAt(textSequence.length() - 1) == ')') {
				textSequence = textSequence.subSequence(0, textSequence.length() - 1);
			}
			if (textSequence != null && textSequence.charAt(0) == '(') {
				textSequence = textSequence.subSequence(1, textSequence.length());
			}
			if (textSequence == null) {
				// should never happen (but makes @Nullable control for
				// parameter textSequence happy)
				return args.toArray(new String[args.size()]);
			}
			if (phpVersion == null) {
				phpVersion = PHPVersion.getLatestVersion();
			}

			AbstractPhpLexer lexer = PhpLexerFactory.createLexer(new StringReader(textSequence.toString()), phpVersion);
			lexer.initialize(lexer.getScriptingState());
			String symbol = null;
			int level = 0;
			int argIndex = 0;
			do {
				try {
					symbol = lexer.getNextToken();
					if (symbol != null) {
						CharSequence text = textSequence.subSequence(lexer.getTokenStart(),
								lexer.getTokenStart() + lexer.getLength());
						if (symbol.equals(PHPRegionTypes.PHP_TOKEN)) {
							if (text.equals(LPAREN) || text.equals(LBRACE) || text.equals(LBRACKET)) {
								level++;
							} else if (text.equals(RPAREN) || text.equals(RBRACE) || text.equals(RBRACKET)) {
								level--;
							} else if (level == 0 && text.equals(COMMA)) {
								argIndex++;
							}
						} else if (level == 0 && symbol.equals(PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING)) {
							if (args.size() < argIndex + 1) {
								args.add(text.toString());
							}
						} else if (level == 0 && !symbol.equals(PHPRegionTypes.WHITESPACE)) {
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

	public static @Nullable String suggestObjectOperator(@NonNull CharSequence statement) {
		String insert = null;
		statement = statement.toString().trim();
		int statementPosition = statement.length() - 1;
		if (statementPosition < 0) {
			return null;
		}

		int charAt = statement.charAt(statementPosition);
		if (charAt == '>') {
			return null;
		}
		if (charAt == '-') {
			insert = String.valueOf('>');
		} else if (charAt == ':') {
			if (statementPosition > 0 && statement.charAt(statementPosition - 1) == ':') {
				return null;
			}
			insert = String.valueOf(':');
		} else {
			statementPosition = readBackwardSpaces(statement, statementPosition);
			switch (statement.charAt(statementPosition)) {
			case '}':
			case ')':
			case ']':
				insert = OBJECT_OPERATOR;
				break;
			case '>':
			case ':':
				return null;
			default:
				int identStart = readIdentifierStartIndex(statement, statementPosition, true);
				if (identStart < 0) {
					return null;
				}
				if (statement.charAt(identStart) == '$' || statement.charAt(identStart) == '}') {
					insert = OBJECT_OPERATOR;
				} else {
					identStart = readBackwardSpaces(statement, identStart - 1);
					if (identStart > 1 && statement.charAt(identStart) == '>'
							&& statement.charAt(identStart - 1) == '-') {
						insert = OBJECT_OPERATOR;
					} else {
						insert = PAAMAYIM_NEKUDOTAYIM;
					}
				}
			}

		}

		return insert;
	}

}
