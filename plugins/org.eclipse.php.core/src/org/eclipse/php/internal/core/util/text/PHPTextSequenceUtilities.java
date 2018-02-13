/*******************************************************************************
 * Copyright (c) 2009, 2015, 2016, 2017 IBM Corporation and others.
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

import org.apache.commons.lang3.ArrayUtils;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.NamedMember;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.compiler.ast.parser.ASTUtils;
import org.eclipse.php.internal.core.documentModel.parser.AbstractPHPLexer;
import org.eclipse.php.internal.core.documentModel.parser.PHPLexerFactory;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.parser.regions.IPHPScriptRegion;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
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
	 * searches backwards (starting from offset - 1) until it finds delimiter ';',
	 * '{' or '}'.
	 * 
	 * @param offset
	 *            The absolute offset in the document
	 * @param sdRegion
	 *            Structured document region of the offset
	 * @param removeComments
	 *            Flag determining whether to remove comments in the resulted text
	 *            sequence
	 * 
	 * @return text sequence of the statement, cannot be null
	 */
	public static @NonNull TextSequence getStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean removeComments) {
		return getStatement(offset, sdRegion, removeComments, null, 0, null);
	}

	/**
	 * This function returns statement text depending on the current offset. It
	 * searches backwards (starting from offset - 1) until it finds delimiter ';',
	 * '{' or '}'.
	 * 
	 * @param offset
	 *            The absolute offset in the document
	 * @param sdRegion
	 *            Structured document region of the offset
	 * @param removeComments
	 *            Flag determining whether to remove comments in the resulted text
	 *            sequence
	 * @param ignoreDelimiters
	 *            Delimiter types that will be ignored while searching backwards
	 *            (ignoreDelimiters can be null). Supported delimiter types are
	 *            PHPRegionTypes.PHP_CURLY_OPEN, PHPRegionTypes.PHP_CURLY_CLOSE and
	 *            PHPRegionTypes.PHP_SEMICOLON
	 * @param limit
	 *            Controls how many times a delimiter (from ignoreDelimiters) should
	 *            be ignored. 0 or less means no limit.
	 * @param foundDelimiter
	 *            If foundDelimiter is not null and foundDelimiter length is greater
	 *            than 0 then foundDelimiter[0] will contain the delimiter region
	 *            found while searching backwards (or null if no delimiter was
	 *            found, typically when the backward search reached the beginning of
	 *            sdRegion). Note that the foundDelimiter[0] offset will be
	 *            <b>relative to the beginning of the document</b>.
	 * 
	 * @return text sequence of the statement, cannot be null
	 */
	public static @NonNull TextSequence getStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean removeComments, @Nullable String[] ignoreDelimiters, int limit,
			@Nullable ContextRegion[] foundDelimiter) {
		if (foundDelimiter != null && foundDelimiter.length != 0) {
			foundDelimiter[0] = null;
		}
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
			IPHPScriptRegion phpScriptRegion = (IPHPScriptRegion) tRegion;

			try {
				// Set default starting position to the beginning of the
				// PhpScriptRegion:
				int startOffset = container.getStartOffset() + phpScriptRegion.getStart();

				// Now, search backwards for the statement start (in this
				// PhpScriptRegion):
				ITextRegion startTokenRegion;
				if (documentOffset == startOffset) {
					startTokenRegion = phpScriptRegion.getPHPToken(0);
				} else {
					startTokenRegion = phpScriptRegion.getPHPToken(offset - startOffset - 1);
				}

				List<IRegion> comments = new ArrayList<>();
				int nbIgnoredDelimiters = 0;
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

					if ((type == PHPRegionTypes.PHP_CURLY_CLOSE || type == PHPRegionTypes.PHP_CURLY_OPEN
							|| type == PHPRegionTypes.PHP_SEMICOLON
					/* || startTokenRegion.getType() == PHPRegionTypes.PHP_IF */)
							&& !(ArrayUtils.contains(ignoreDelimiters, type)
									&& (limit <= 0 || nbIgnoredDelimiters++ < limit))) {
						if (foundDelimiter != null && foundDelimiter.length != 0) {
							foundDelimiter[0] = new ContextRegion(type, startOffset + startTokenRegion.getStart(),
									startTokenRegion.getTextLength(), startTokenRegion.getLength());
						}
						// Calculate starting position of the statement (it
						// should go right after this startTokenRegion):
						startOffset += startTokenRegion.getEnd();
						break;
					}
					startTokenRegion = phpScriptRegion.getPHPToken(startTokenRegion.getStart() - 1);
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
	 * This function returns statement region depending on the current offset. It
	 * searches backwards (starting from offset - 1) until it finds ';', '{' or '}'.
	 * </p>
	 * <p>
	 * <b> Be careful, empty region can be returned (i.e. region's length is 0) when
	 * no statement was found. In this case, the offset from the returned region has
	 * no special meaning.
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
		while (startPosition > 0) {
			char ch = textSequence.charAt(startPosition - 1);
			if (!Character.isLetterOrDigit(ch) && ch != '_') {
				if (ch == '\\') {
					if (onBackslash) {
						break;
					}
					onBackslash = true;
				} else {
					break;
				}
			} else {
				onBackslash = false;
			}
			startPosition--;
		}
		if (includeDollar && startPosition > 0 && textSequence.charAt(startPosition - 1) == '$') {
			startPosition--;
		}
		return startPosition;
	}

	public static int readNamespaceEndIndex(@NonNull CharSequence textSequence, int startPosition,
			boolean includeDollar) {
		boolean onBackslash = false;

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
				} else {
					break;
				}
			} else {
				onBackslash = false;
			}
			startPosition++;
		}
		return startPosition;
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
	 * Returns the next position on the text where one the given delimiters start
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
	 * @param phpVersion
	 * @param textSequence
	 * @return
	 */
	@SuppressWarnings("null")
	public static @NonNull String[] getArgNames(@Nullable PHPVersion phpVersion, @Nullable CharSequence textSequence) {
		return getArgNames(phpVersion, textSequence, null, -1);
	}

	/**
	 * Read string argnames from CharSequence
	 * 
	 * TODO Nested parenthesis expression
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public static @NonNull String[] getArgNames(@Nullable PHPVersion phpVersion, @Nullable CharSequence textSequence,
			@Nullable ISourceModule sourceModule, int offset) {
		List<String> args = new ArrayList<>();
		if (textSequence != null && textSequence.length() > 2) {
			if (textSequence.charAt(textSequence.length() - 1) == ')') {
				textSequence = textSequence.subSequence(0, textSequence.length() - 1);
			}
			if (textSequence != null && textSequence.charAt(0) == '(') {
				textSequence = textSequence.subSequence(1, textSequence.length());
			}
			if (textSequence == null) {
				// should never happen (but makes @Nullable control for parameter textSequence
				// happy)
				return args.toArray(new String[args.size()]);
			}
			if (phpVersion == null) {
				phpVersion = PHPVersion.getLatestVersion();
			}

			AbstractPHPLexer lexer = PHPLexerFactory.createLexer(new StringReader(textSequence.toString()), phpVersion);
			lexer.initialize(lexer.getScriptingState());
			ArgNameClassDecoder ancd = null;
			if (sourceModule != null && !phpVersion.isLessThan(PHPVersion.PHP5_5)) {
				PHPTextSequenceUtilities out = new PHPTextSequenceUtilities();
				ancd = out.new ArgNameClassDecoder();
			}
			String symbol = null;
			int level = 0;
			int argIndex = 0;
			do {
				try {
					symbol = lexer.getNextToken();
					if (symbol != null) {
						CharSequence text = textSequence.subSequence(lexer.getTokenStart(),
								lexer.getTokenStart() + lexer.getLength());
						if (level == 0 && ancd != null) {
							ancd.addSymbol(symbol, text);
						}
						if (symbol.equals(PHPRegionTypes.PHP_TOKEN)) {
							if (text.equals(LPAREN) || text.equals(LBRACE) || text.equals(LBRACKET)) {
								level++;
							} else if (text.equals(RPAREN) || text.equals(RBRACE) || text.equals(RBRACKET)) {
								level--;
							} else if (level == 0 && text.equals(COMMA)) {
								argIndex++;
								if (ancd != null) {
									ancd.reset();
								}
							}
						} else if (level == 0) {
							if (symbol.equals(PHPRegionTypes.PHP_CONSTANT_ENCAPSED_STRING)) {
								if (args.size() < argIndex + 1) {
									args.add(ASTUtils.stripQuotes(text.toString()));
								}
							} else if (ancd != null && ancd.isBuilding()) {
								if (ancd.getAssignedArgumentIndex() < 0) {
									ancd.setAssignedArgumentIndex(args.size());
								}
								if (args.size() < ancd.getAssignedArgumentIndex() + 1) {
									args.add(null);
								}
							} else if (ancd != null && ancd.isReady()) {
								if (argIndex == ancd.getAssignedArgumentIndex()) {
									args.set(argIndex, ancd.resolve(sourceModule, offset));
								}
							} else if (!symbol.equals(PHPRegionTypes.WHITESPACE)) {
								if (args.size() < argIndex + 1) {
									args.add(null);
								} else {
									args.set(argIndex, null);
								}
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

	@SuppressWarnings("null")
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

	private class ArgNameClassDecoder {
		private final int STATE_DISABLED = 0;
		private final int STATE_EMPTY = 1;
		private final int STATE_BUILDING_SUBJECT = 2;
		private final int STATE_PAAMAYIMNEKUDOTAYIM_RECEIVED = 3;
		private final int STATE_CLASS_RECEIVED = 4;
		private int assignedArgumentIndex;
		private StringBuilder subject;
		private String resolveResult;
		private int state;

		public ArgNameClassDecoder() {
			this.reset();
		}

		public void reset() {
			this.subject = null;
			this.state = STATE_EMPTY;
			this.assignedArgumentIndex = -1;
			this.resolveResult = null;
		}

		private void disable() {
			this.reset();
			this.state = STATE_DISABLED;
		}

		public void addSymbol(String symbol, @NonNull CharSequence text) {
			if (this.state == STATE_DISABLED) {
				return;
			}
			if (this.state >= STATE_CLASS_RECEIVED) {
				this.disable();
				return;
			} else {
				switch (symbol) {
				case PHPRegionTypes.WHITESPACE:
					if (this.state > STATE_EMPTY && this.state < STATE_CLASS_RECEIVED) {
						this.disable();
					}
					break;
				case PHPRegionTypes.PHP_NS_SEPARATOR:
				case PHPRegionTypes.PHP_LABEL:
					if (this.state == STATE_EMPTY) {
						this.subject = new StringBuilder();
						this.state = STATE_BUILDING_SUBJECT;
					}
					if (this.state == STATE_BUILDING_SUBJECT) {
						this.subject.append(text);
					} else {
						this.disable();
					}
					break;
				case PHPRegionTypes.PHP_PAAMAYIM_NEKUDOTAYIM:
					if (this.state == STATE_BUILDING_SUBJECT) {
						this.state = STATE_PAAMAYIMNEKUDOTAYIM_RECEIVED;
					} else {
						this.disable();
					}
					break;
				case PHPRegionTypes.PHP_CLASS:
					if (this.state == STATE_PAAMAYIMNEKUDOTAYIM_RECEIVED) {
						this.state = STATE_CLASS_RECEIVED;
					} else {
						this.disable();
					}
					break;
				default:
					this.disable();
					break;
				}
			}
		}

		public boolean isBuilding() {
			return this.state >= STATE_BUILDING_SUBJECT && this.state <= STATE_PAAMAYIMNEKUDOTAYIM_RECEIVED;
		}

		public boolean isReady() {
			return this.state == STATE_CLASS_RECEIVED;
		}

		public void setAssignedArgumentIndex(int value) {
			this.assignedArgumentIndex = value;
		}

		public int getAssignedArgumentIndex() {
			return this.assignedArgumentIndex;
		}

		@Nullable
		public String resolve(@Nullable ISourceModule sourceModule, int offset) {
			if (this.state != STATE_CLASS_RECEIVED || sourceModule == null) {
				return null;
			}
			if (this.resolveResult == null) {
				String localClassName = this.subject.toString();
				if (localClassName.equalsIgnoreCase("self") || localClassName.equalsIgnoreCase("static")) { //$NON-NLS-1$ //$NON-NLS-2$
					IType type = PHPModelUtils.getCurrentType(sourceModule, offset);
					if (type != null) {
						System.out.println(type.getClass().toString());
					}
					IModelElement element = null;
					try {
						element = sourceModule.getElementAt(offset);
					} catch (ModelException e) {
						Logger.logException(e);
					}
					while (element instanceof IModelElement) {
						if (element.getElementType() == IModelElement.TYPE) {
							try {
								this.resolveResult = ((NamedMember) element)
										.getFullyQualifiedName(NamespaceReference.NAMESPACE_DELIMITER, false);
							} catch (ModelException e) {
								Logger.logException(e);
							}
							break;
						}
						element = element.getParent();
					}
				} else {
					this.resolveResult = PHPModelUtils.getFullName(localClassName, sourceModule, offset);
				}
			}
			return this.resolveResult;
		}
	}
}
