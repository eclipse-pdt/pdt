/*******************************************************************************
 * Copyright (c) 2013 Zend Techologies Ltd.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zend Technologies Ltd. - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.formatter.core;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import java_cup.runtime.Symbol;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.jface.text.*;
import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.internal.core.compiler.ast.parser.php5.CompilerAstLexer;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

/**
 * Description: This class formats a given {@link StructuredDocument}
 * 
 * @author moshe, 2007
 */
public class CodeFormatterVisitor extends AbstractVisitor implements
		ICodeFormattingProcessor {

	public static final int NO_LINE_WRAP = 0;
	public static final int FIRST_WRAP_WHEN_NECESSARY = 1;
	public static final int WRAP_FIRST_ELEMENT = 2;
	public static final int WRAP_ALL_ELEMENTS = 3;
	public static final int WRAP_ALL_ELEMENTS_NO_INDENT_FIRST = 4;
	public static final int WRAP_ALL_ELEMENTS_EXCEPT_FIRST = 5;
	public static final int ALWAYS_WRAP_ELEMENT = 6;
	public static final int WRAP_WHEN_NECESSARY = 7;

	private static final int NO_LINE_WRAP_INDENT = -1;
	private static final int DEFAULT_INDENTATION = 0;
	private static final int INDENT_ON_COLUMN = 1;
	private static final int INDENT_ONE = 2;

	private static final String EMPTY_STRING = ""; //$NON-NLS-1$
	private static final String FUNCTION_NAME_PRINT = "print"; //$NON-NLS-1$

	private static final byte PHP_OPEN_TAG = 0;
	private static final byte PHP_OPEN_SHORT_TAG = 1;
	private static final char PHP_OPEN_SHORT_TAG_WITH_EQUAL = 2;
	private static final char PHP_OPEN_ASP_TAG = 3;

	private static final char EQUAL = '=';
	private static final String KEY_VALUE_OPERATOR = "=>"; //$NON-NLS-1$
	private static final char OPEN_PARN = '(';
	private static final char CLOSE_PARN = ')';
	private static final char OPEN_CURLY = '{';
	// private static final char CLOSE_CURLY = '}';
	private static final char OPEN_BRACKET = '[';
	private static final char CLOSE_BRACKET = ']';
	private static final char COLON = ':';
	private static final char SEMICOLON = ';';
	private static final char SPACE = ' ';
	private static final char COMMA = ',';
	private static final char QUESTION_MARK = '?';
	private String lineSeparator;

	private CodeFormatterPreferences preferences;
	private final IDocument document;
	private PHPVersion phpVersion;
	private boolean useShortTags;

	private int indentationLevel;
	private boolean indentationLevelDesending = false;
	private AstLexer astLexer;
	private boolean isPhpEqualTag = false;
	private int startRegionPosition = -1;
	private int endRegionPosition = Integer.MAX_VALUE;
	private boolean isPrevSpace = false;
	private boolean isHeredocSemicolon = false;
	private int lineWidth = 0;
	private int lineNumber = 0;
	private int binaryExpressionLineWrapPolicy = -1;// use this as local since
	// it changes its state
	private int binaryExpressionIndentGap = 0;// use this as local since it
	// changes its state
	private boolean wasBinaryExpressionWrapped = false;
	private String binaryExpressionSavedBuffer = null;
	private InfixExpression binaryExpressionSavedNode = null;
	private int binaryExpressionSavedChangesIndex = -1;
	private int binaryExpressionRevertPolicy = -1;
	private boolean isBinaryExpressionExtraIndentation = false;

	// append chars to buffer through insertSpace or appendToBuffer
	private StringBuffer replaceBuffer = new StringBuffer();
	private List<Symbol> tokens = new ArrayList<Symbol>();
	private boolean secondReplaceBufferNeeded = false;

	/**
	 * list of <ReplaceEdit>
	 */
	private List<ReplaceEdit> changes = new LinkedList<ReplaceEdit>();
	private int stInScriptin = -1;
	private boolean isInsideFun;

	private Stack<Integer> chainStack = new Stack<Integer>();
	private Integer peek;
	private Set<IfStatement> processedIfStatements = new HashSet<IfStatement>();
	private boolean newLineOfComment;
	private String commentContent;
	private List<String> commentWords;
	/** disabling */
	boolean editsEnabled;
	boolean useTags;
	int tagsKind;
	private String disablingTag, enablingTag;
	// this is for never indent at first line
	private boolean doNotIndent = false;
	boolean inComment = false;
	private int indentLengthForComment;
	private String indentStringForComment;
	private boolean blockEnd;
	private boolean recordCommentIndentVariables = false;
	// for block comment,multiline comment at the end of break statement of case
	// statement
	private List<Integer> indentationLevelList = new ArrayList<Integer>();
	Stack<CommentIndentationObject> commentIndetationStack = new Stack<CodeFormatterVisitor.CommentIndentationObject>();

	private boolean ignoreEmptyLineSetting = false;
	private boolean justCommentLine = false;

	public CodeFormatterVisitor(IDocument document,
			CodeFormatterPreferences codeFormatterPreferences,
			String lineSeparator, PHPVersion phpVersion, boolean useShortTags,
			IRegion region) throws Exception {
		this(document, codeFormatterPreferences, lineSeparator, phpVersion,
				useShortTags, region, 0);
	}

	public CodeFormatterVisitor(IDocument document,
			CodeFormatterPreferences codeFormatterPreferences,
			String lineSeparator, PHPVersion phpVersion, boolean useShortTags,
			IRegion region, int indentationLevel) throws Exception {
		this.phpVersion = phpVersion;
		this.useShortTags = useShortTags;
		this.document = document;
		this.lineSeparator = lineSeparator;
		this.indentationLevel = indentationLevel;
		this.preferences = codeFormatterPreferences;
		this.startRegionPosition = region.getOffset();
		this.endRegionPosition = startRegionPosition + region.getLength();

		ignoreEmptyLineSetting = !preferences.indent_empty_lines;

		Program program = null;
		try {
			final Reader reader = new StringReader(document.get());
			program = ASTParser.newParser(reader, phpVersion, true).createAST(
					new NullProgressMonitor());
		} catch (Exception e) {
			Logger.log(Logger.INFO,
					"Parsing error, file could not be formatted.");//$NON-NLS-1$
		}

		this.useTags = preferences.use_tags;
		this.tagsKind = 0;
		if (this.useTags) {
			if (preferences.disabling_tag != null
					&& preferences.disabling_tag.length > 0) {
				this.disablingTag = new String(preferences.disabling_tag);
			}
			if (preferences.enabling_tag != null
					&& preferences.enabling_tag.length > 0) {
				this.enablingTag = new String(preferences.enabling_tag);
			}
		}
		this.editsEnabled = true;
		if (program != null) {
			program.accept(this);
		}

	}

	public CodeFormatterVisitor(IDocument document, String lineSeparator,
			PHPVersion phpVersion, boolean useShortTags, IRegion region)
			throws Exception {
		this(document, CodeFormatterPreferences.getDefaultPreferences(),
				lineSeparator, phpVersion, useShortTags, region, 0);
	}

	// insert chars to the buffer except space
	private void appendToBuffer(Object obj) {
		isPrevSpace = false;
		if (obj == null)
			return;
		replaceBuffer.append(obj);
		if (!lineSeparator.equals(obj)) {
			lineWidth += obj.toString().length();
		}
	}

	private int checkFirstTokenLength(int start, int end) {
		int length = 0;
		try {
			scan(start, end);
			Symbol token = (Symbol) tokens.get(0);
			length = token.right - token.left;
		} catch (Exception e) {
			Logger.logException(e);
		}
		return length;
	}

	private int countStrInBuffer(String str) {
		int count = 0;
		int index = replaceBuffer.indexOf(str);
		while (index >= 0 && index < replaceBuffer.length()) {
			index = replaceBuffer.indexOf(str, index + 1);
			count++;
		}
		return count;
	}

	private char getBufferFirstChar() throws BadLocationException {
		for (int offset = 0; offset < replaceBuffer.length(); offset++) {
			char currChar = replaceBuffer.charAt(offset);
			if (currChar != ' ' && currChar != '\t' && currChar != '\r'
					&& currChar != '\n') {
				// not empty line
				return currChar;
			}
		}
		return '\0';
	}

	public List<ReplaceEdit> getChanges() {
		IRegion[] partitions = new IRegion[0];
		try {
			partitions = getAllSingleLine(TextUtilities.computePartitioning(
					document,
					IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING, 0,
					document.getLength(), false));
		} catch (BadLocationException e) {
		}
		List<ReplaceEdit> allChanges = Collections.unmodifiableList(changes);
		List<ReplaceEdit> result = new ArrayList<ReplaceEdit>();
		for (ReplaceEdit edit : allChanges) {
			if (isInSingleLine(edit, partitions, 0)) {
				continue;
			}
			result.add(edit);
		}
		return Collections.unmodifiableList(result);
	}

	private int getCharPosition(int start, int end, char c) {
		try {
			for (int index = 0; start + index < end; index++) {
				if (document.getChar(start + index) == c) {
					return start + index;
				}
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return -1;
	}

	private String getDocumentString(int start, int end) {
		char[] result = new char[end - start];
		try {
			for (int index = 0; start + index < end; index++) {
				result[index] = document.getChar(start + index);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return new String(result);
	}

	private AstLexer getLexer(Reader reader) throws Exception {
		AstLexer result = null;
		if (PHPVersion.PHP5.equals(phpVersion)) {
			result = new CompilerAstLexer(reader);
			((CompilerAstLexer) result).setAST(new AST(reader, PHPVersion.PHP5,
					false, useShortTags));
			stInScriptin = CompilerAstLexer.ST_IN_SCRIPTING; // save the initial
			// state for reset
			// operation
		} else if (PHPVersion.PHP4.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php4.CompilerAstLexer(
					reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php4.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP4, false,
							useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php4.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
		} else if (PHPVersion.PHP5_3.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer(
					reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_3, false,
							useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
		} else if (PHPVersion.PHP5_4.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer(
					reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_4, false,
							useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
		} else if (PHPVersion.PHP5_5.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer(
					reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_5, false,
							useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
		} else {
			throw new IllegalArgumentException("unrecognized version " //$NON-NLS-1$
					+ phpVersion);
		}
		return result;
	}

	private byte getPhpStartTag(int offset) {
		try {
			if (document.getChar(offset) == '<') {
				if (document.getChar(offset + 1) == '%') {
					return PHP_OPEN_ASP_TAG;
				} else if (document.getChar(offset + 2) == '=') {
					return PHP_OPEN_SHORT_TAG_WITH_EQUAL;
				} else if (document.getChar(offset + 2) != 'p'
						&& document.getChar(offset + 2) != 'P') {
					return PHP_OPEN_SHORT_TAG;
				} else if (document.getChar(offset + 1) == '?') {
					return PHP_OPEN_TAG;
				}
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return -1;
	}

	private int getPhpTagIndentationLevel(int offset) {
		try {
			final int line = document.getLineOfOffset(offset);
			final int startLineOffset = document.getLineOffset(line);

			int diff = 0;
			for (int i = startLineOffset; i < offset; i++) {
				if (document.getChar(i) == '\t' || document.getChar(i) == ' ') {
					diff++;
				} else {
					break;
				}
			}

			if (preferences.indentationChar == CodeFormatterPreferences.TAB_CHAR) {
				return diff;
			} else {
				if (preferences.indentationSize <= 0) {
					return -1;
				}
				return diff / preferences.indentationSize;
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return -1;
	}

	/**
	 * handle the action of if, while, do while, for statements.
	 */
	private void handleAction(int lastPosition, Statement action,
			boolean addNewlineBeforeAction) {
		boolean isIndentationAdded = false;
		if (action.getType() == ASTNode.BLOCK) {
			isIndentationAdded = handleBlockOpenBrace(
					this.preferences.brace_position_for_block,
					this.preferences.insert_space_before_opening_brace_in_block);
		} else if (action.getType() == ASTNode.EMPTY_STATEMENT) {
			// This is an empty statement
			if (this.preferences.new_line_for_empty_statement) {
				insertNewLine();
				indentationLevel++;
				indent();
				isIndentationAdded = true;
			}
		} else {
			if (addNewlineBeforeAction) {
				// single statement should indent
				indentationLevel++;
				insertNewLine();
				indent();
				isIndentationAdded = true;
			} else {
				insertSpace();
			}
		}
		handleChars(lastPosition, action.getStart());

		action.accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
	}

	/**
	 * handle the '{' of a block
	 * 
	 * @param bracePosition
	 *            one of Sameline
	 * @param placeSpaceBeforeOpenCurly
	 * @return
	 */
	private boolean handleBlockOpenBrace(byte bracePosition,
			boolean placeSpaceBeforeOpenCurly) {
		boolean isIndentationAdded = false;
		switch (bracePosition) {
		case CodeFormatterPreferences.NEXT_LINE_INDENT:
			indentationLevel++;
			isIndentationAdded = true;
		case CodeFormatterPreferences.NEXT_LINE:
			insertNewLine();
			indent();
			break;
		default:
		case CodeFormatterPreferences.SAME_LINE:
			if (placeSpaceBeforeOpenCurly) {
				insertSpace();
			}
			break;
		}
		return isIndentationAdded;
	}

	@SuppressWarnings("unchecked")
	private void handleChars(int offset, int end) {
		try {
			// check if the changed region is in the formatting requested region
			if (startRegionPosition < end && endRegionPosition >= end) {
				boolean hasComments = hasComments(offset, end);

				if (hasComments) {
					// handle the comments
					handleComments(offset, end, astLexer.getCommentList(),
							false, 0);
				} else {
					handleCharsWithoutComments(offset, end);
				}
			}

			// clear the buffer
			replaceBuffer.setLength(0);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void handleChars1(int offset, int end, boolean isIndented,
			int indentGap) {
		try {
			// check if the changed region is in the formatting requested region
			if (startRegionPosition < end && endRegionPosition >= end) {
				boolean hasComments = hasComments(offset, end);

				if (hasComments) {
					// handle the comments
					handleComments(offset, end, astLexer.getCommentList(),
							isIndented, indentGap);
				} else {
					handleCharsWithoutComments(offset, end);
				}
			}

			// clear the buffer
			replaceBuffer.setLength(0);
		} catch (Exception e) {
			Logger.logException(e);
		}
	}

	private boolean hasComments(int offset, int end) throws Exception {
		scan(offset, end);

		assert astLexer != null;
		boolean hasComments = astLexer.getCommentList().size() > 0;
		return hasComments;
	}

	private void handleCharsWithoutComments(int offset, int end)
			throws BadLocationException {
		handleCharsWithoutComments(offset, end, false);
	}

	private void handleCharsWithoutComments(int offset, int end,
			boolean isComment) throws BadLocationException {
		String content = document.get(offset, end - offset).toLowerCase();
		int phpTagOpenIndex = -1;
		if (!isComment
				&& ((phpTagOpenIndex = content.indexOf("<?")) != -1 || (phpTagOpenIndex = content //$NON-NLS-1$
						.indexOf("<%")) != -1)) { //$NON-NLS-1$
			handleSplittedPhpBlock(offset + phpTagOpenIndex, end);
		}

		else {
			// reset the isPrevSpace while replacing the chars
			isPrevSpace = false;
			int startLine = document.getLineOfOffset(offset);
			int endLine = document.getLineOfOffset(end);
			int emptyLines = 0;

			if (!ignoreEmptyLineSetting) {
				// count empty lines
				for (int line = startLine; line < endLine; line++) {
					if (isEmptyLine(line)) {
						emptyLines++;
					}
				}

				// set the preserve empty lines
				if (emptyLines > preferences.blank_line_preserve_empty_lines) {
					emptyLines = preferences.blank_line_preserve_empty_lines;
				}

				int newLinesInBuffer = countStrInBuffer(lineSeparator);

				// add empty lines
				if (emptyLines > 0 && newLinesInBuffer < emptyLines + 1) {
					for (int line = newLinesInBuffer; line < emptyLines + 1; line++) {
						insertNewLine();
					}
					if (inComment) {
						if (!doNotIndent) {
							indentForComment(indentationLevelDesending);
						}
					} else {
						indent();
					}

				}

			}
			ignoreEmptyLineSetting = false;

			// check if the replacement and the origin string are the same
			boolean needToReplace = true;
			if (end - offset == replaceBuffer.length()) {
				// in case the buffer is empty and the doc length is 0
				// no need to replace
				if (end - offset == 0 && replaceBuffer.length() == 0) {
					needToReplace = false;
				} else {
					// the buffer and document segment length are the same
					// in case of 2 different chars we need to replace the
					// document segment
					needToReplace = false;
					for (int index = 0; offset + index < end; index++) {
						char docChar = document.getChar(offset + index);
						char bufferChar = replaceBuffer.charAt(index);
						if (docChar != bufferChar) {
							needToReplace = true;
							break;
						}
					}
				}
			}
			if (needToReplace && editsEnabled) {
				insertString(offset, end, replaceBuffer.toString());
			}
			if (recordCommentIndentVariables) {
				recordCommentIndentVariables = false;
				indentLengthForComment = lineWidth;
				String afterNewLine;
				int position = replaceBuffer.lastIndexOf(lineSeparator);
				if (position >= 0) {
					afterNewLine = replaceBuffer.substring(position
							+ lineSeparator.length(), replaceBuffer.length());
				} else {
					afterNewLine = replaceBuffer.toString();
				}
				indentStringForComment = afterNewLine;
			}
			indentationLevelDesending = false;
			// clear the buffer
			replaceBuffer.setLength(0);
		}
	}

	class CommentIndentationObject {
		boolean indented;

	}

	/**
	 * handle comma list (e.g. 1,2,3)
	 * 
	 * @param array
	 *            ASTNode array
	 * @param lastPosition
	 *            the position of the last ASTNode
	 * @param insertSpaceBeforeComma
	 * @param insertSpaceAfterComma
	 * @param b
	 * @param k
	 * @param j
	 * @return the last element end position
	 */
	private int handleCommaList(ASTNode[] array, int lastPosition,
			boolean insertSpaceBeforeComma, boolean insertSpaceAfterComma,
			int lineWrapPolicy, int indentGap, boolean forceSplit) {
		int oldIndentationLevel = indentationLevel;
		boolean wasBinaryExpressionWrapped = this.wasBinaryExpressionWrapped;
		if (array.length == 0) {
			return lastPosition;
		}

		// save the changes index position
		String savedBuffer = replaceBuffer.toString();
		;
		int changesIndex = changes.size() - 1;
		int savedLastPosition = lastPosition;
		boolean isExtraIndentation = false;

		// Map<Integer, CommentIndentationObject> commentIndetationMap = new
		// HashMap<Integer, CommentIndentationObject>();
		CommentIndentationObject cio = new CommentIndentationObject();
		commentIndetationStack.add(cio);
		// commentIndetationMap.put(array., cio);
		boolean isFirst = true;
		for (int i = 0; i < array.length; i++) {
			if (!isFirst) {
				if (insertSpaceBeforeComma) {
					insertSpace();
				}
				appendToBuffer(COMMA);
				if (insertSpaceAfterComma) {
					insertSpace();
				}
			}

			// after the first element and wrap policy is except first element
			if (i == 1 && lineWrapPolicy == WRAP_ALL_ELEMENTS_EXCEPT_FIRST) {
				savedBuffer = replaceBuffer.toString();
				;
				changesIndex = changes.size() - 1;
				savedLastPosition = lastPosition;
			}

			switch (lineWrapPolicy) {
			case NO_LINE_WRAP:
				break;
			case FIRST_WRAP_WHEN_NECESSARY:
				if (lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					lineWrapPolicy = WRAP_WHEN_NECESSARY;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}

					indent();
				}
				break;
			case WRAP_WHEN_NECESSARY:
				if (lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					insertNewLine();
					indent();
				}
				break;
			case WRAP_FIRST_ELEMENT:
				if (forceSplit
						|| lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = 0;
					lineWrapPolicy = WRAP_WHEN_NECESSARY;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();
				}
				break;
			case WRAP_ALL_ELEMENTS:
				if (forceSplit
						|| lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = 0;
					lineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();
				}
				break;
			case WRAP_ALL_ELEMENTS_NO_INDENT_FIRST:
				if (forceSplit
						|| lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					// revert the buffer
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = 0;
					lineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();

					// increase the indentation level after the first element
					indentationLevel++;
					isExtraIndentation = true;
				}
				break;
			case WRAP_ALL_ELEMENTS_EXCEPT_FIRST:
				if (forceSplit
						|| lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					// revert
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = (i > 0) ? 1 : 0;
					lineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();
				}
				break;
			case ALWAYS_WRAP_ELEMENT:
				insertNewLine();
				indent();
				break;
			}

			// workaround; remove this after fixing of
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=326384
			int start = array[i].getStart();
			if (array[i] instanceof NamespaceName
					&& ((NamespaceName) array[i]).isGlobal()) {
				start -= 1;
			} else if (i == 0 && array[i] instanceof UseStatementPart
					&& ((UseStatementPart) array[i]).getName() != null
					&& ((UseStatementPart) array[i]).getName().isGlobal()) {
				start -= 1;
			}
			// workaround end
			handleChars1(lastPosition, start,
					oldIndentationLevel != indentationLevel, indentGap);
			array[i].accept(this);
			if (array[i] instanceof FunctionInvocation) {
				FunctionInvocation functionInvocation = (FunctionInvocation) array[i];
				if (functionInvocation.getArrayDereferenceList() != null
						&& !functionInvocation.getArrayDereferenceList()
								.getDereferences().isEmpty()) {
					lastPosition = functionInvocation
							.getArrayDereferenceList()
							.getDereferences()
							.get(functionInvocation.getArrayDereferenceList()
									.getDereferences().size() - 1).getEnd();
				} else {
					lastPosition = array[i].getEnd();
				}
			} else {
				lastPosition = array[i].getEnd();
			}

			isFirst = false;
		}
		commentIndetationStack.pop();
		if (isExtraIndentation) {
			indentationLevel--;
		}

		if (oldIndentationLevel != indentationLevel) {
			indentationLevel = oldIndentationLevel;
		}

		if (wasBinaryExpressionWrapped != this.wasBinaryExpressionWrapped) {
			this.wasBinaryExpressionWrapped = wasBinaryExpressionWrapped;
		}
		return lastPosition;
	}

	private void handleComments(
			int offset,
			int end,
			List<org.eclipse.php.internal.core.compiler.ast.nodes.Comment> commentList,
			boolean isIndented, int indentGap) throws Exception {
		boolean oldIgnoreEmptyLineSetting = ignoreEmptyLineSetting;
		ignoreEmptyLineSetting = false;

		String secondReplaceBuffer = null;
		int startLine = document.getLineOfOffset(offset);
		int endLine = document.getLineOfOffset(end);
		int commentStartLine = -1;
		// int commentEndLine = -1;
		int start = offset;
		String afterNewLine = EMPTY_STRING;
		boolean needIndentNewLine = false;
		boolean indentationLevelDesending = this.indentationLevelDesending;
		inComment = true;
		boolean previousCommentIsSingleLine = false;
		justCommentLine = false;

		for (Iterator<org.eclipse.php.internal.core.compiler.ast.nodes.Comment> iter = commentList
				.iterator(); iter.hasNext();) {
			org.eclipse.php.internal.core.compiler.ast.nodes.Comment comment = iter
					.next();
			commentStartLine = document.getLineOfOffset(comment.sourceStart()
					+ offset);
			// commentEndLine = document.getLineOfOffset(comment.sourceEnd()
			// + offset);
			int position = replaceBuffer.indexOf(lineSeparator);
			boolean startAtFirstColumn = (document
					.getLineOffset(commentStartLine) == comment.sourceStart()
					+ offset);
			boolean indentOnFirstColumn;
			boolean endWithNewLineIndent = endWithNewLineIndent(replaceBuffer
					.toString());
			switch (comment.getCommentType()) {
			case org.eclipse.php.internal.core.compiler.ast.nodes.Comment.TYPE_SINGLE_LINE:
				indentOnFirstColumn = !startAtFirstColumn
						|| !this.preferences.never_indent_line_comments_on_first_column;
				if (startLine == commentStartLine) {
					if (position >= 0) {
						afterNewLine = replaceBuffer.substring(position
								+ lineSeparator.length(),
								replaceBuffer.length());
						replaceBuffer.replace(position, replaceBuffer.length(),
								" "); //$NON-NLS-1$

						IRegion reg = document.getLineInformation(startLine);
						lineWidth = comment.sourceStart() + offset
								- reg.getOffset() + 1;
						indentOnFirstColumn = false;
					}
				} else {
					afterNewLine = EMPTY_STRING;
					if (indentationLevelDesending) {
						IRegion reg = document
								.getLineInformation(commentStartLine - 1);
						char previousChar = document.getChar(reg.getOffset()
								+ reg.getLength() - 1);
						int indentationSize = preferences.indentationSize;

						// add empty lines
						if (previousChar != '{') {
							for (int line = 0; line < preferences.blank_line_preserve_empty_lines; line++) {
								insertNewLine();
							}
							if (isInsideFun) {
								indentationSize++;
							}
						}
						// End fixing.

						// add single indentationChar * indentationSize
						// Because the comment is the previous indentation level
						for (int i = 0; i < indentationSize; i++) {
							appendToBuffer(preferences.indentationChar);
							lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
									: 3;
						}
					}
					if (position >= 0) {
						if (!secondReplaceBufferNeeded)
							secondReplaceBuffer = replaceBuffer
									.substring(position
											+ lineSeparator.length());
						replaceBuffer.replace(
								position + lineSeparator.length(),
								replaceBuffer.length(), ""); //$NON-NLS-1$
						lineWidth = 0;
					} else {
						if (replaceBuffer.toString().trim().length() == 0) {
							replaceBuffer.setLength(0);
							lineWidth = 0;
						} else {
							insertNewLine();
							if (!isIndented) {
								CommentIndentationObject cio = commentIndetationStack
										.peek();
								if (!cio.indented) {
									cio.indented = true;
									indentationLevel += indentGap;
								}
							}
							// TODO should add indent level
						}
					}
					if (indentationLevelDesending || blockEnd) {
						for (int i = 0; i < preferences.indentationSize; i++) {
							appendToBuffer(preferences.indentationChar);
							lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
									: 3;
						}
					}
					needIndentNewLine = true;
				}

				doNotIndent = true;
				boolean resetCommentIndentVariables = true;
				if (indentOnFirstColumn) {
					if (previousCommentIsSingleLine
							&& indentStringForComment != null) {
						appendToBuffer(indentStringForComment);
						// adjust lineWidth,because indentLengthForComment may
						// contain '\t'
						lineWidth = indentLengthForComment;
						resetCommentIndentVariables = false;
					} else {
						indent();
					}
					if (lineWidth > 0) {
						startAtFirstColumn = false;
					}
					doNotIndent = false;
				}
				previousCommentIsSingleLine = true;
				handleCharsWithoutComments(start, comment.sourceStart()
						+ offset);

				// fix for format removing close_parn of array with comment
				IRegion endLineInformation = document
						.getLineInformation(endLine);

				if (!isComment(document
						.getLineInformation(commentStartLine - 1))) {
					search: for (int i = 0; i < endLineInformation.getLength(); i++) {
						switch (document.getChar(endLineInformation.getOffset()
								+ i)) {
						case CLOSE_PARN:
							secondReplaceBufferNeeded = true;
							break search;
						case ' ':
						case '\t':
						case '\r':
						case '\n':
							break;
						default:
							break search;
						}
					}
				}
				/*
				 * if (preferences.comment_line_length == -1 &&
				 * secondReplaceBufferNeeded && !isComment(document
				 * .getLineInformation(commentStartLine + 1))) { replaceBuffer =
				 * secondReplaceBuffer;
				 * handleCharsWithoutComments(comment.sourceEnd() + offset,
				 * comment.sourceEnd() + offset + 1); secondReplaceBufferNeeded
				 * = false; }
				 */
				// end of fix
				doNotIndent = false;
				resetEnableStatus(document.get(comment.sourceStart() + offset,
						comment.sourceEnd() - comment.sourceStart()));
				if (this.editsEnabled
						&& this.preferences.comment_format_line_comment
						&& (startAtFirstColumn
								&& this.preferences.comment_format_line_comment_starting_on_first_column || !startAtFirstColumn)) {
					if (resetCommentIndentVariables) {
						resetCommentIndentVariables();
					}

					if (startLine == commentStartLine) {
						initCommentIndentVariables(offset, startLine, comment,
								endWithNewLineIndent);
						// adjust lineWidth,because indentLengthForComment may
						// contain '\t'
						lineWidth = indentLengthForComment;
					}
					if (startAtFirstColumn
							&& this.preferences.never_indent_line_comments_on_first_column) {
						indentLengthForComment = 0;
						indentStringForComment = ""; //$NON-NLS-1$
					}

					commentContent = document.get(comment.sourceStart()
							+ offset,
							comment.sourceEnd() - comment.sourceStart());
					boolean needInsertNewLine = commentContent
							.endsWith(lineSeparator);
					if (!needInsertNewLine) {
						String[] delimiters = document.getLegalLineDelimiters();
						for (int i = 0; i < delimiters.length; i++) {
							needInsertNewLine = commentContent
									.endsWith(delimiters[i]);
							if (needInsertNewLine) {
								break;
							}
						}
					}
					int commentTokLen = commentContent.startsWith("#") ? 1 : 2;//$NON-NLS-1$
					commentWords = Arrays.asList(commentContent
							.substring(commentTokLen).trim().split("[ \t]")); //$NON-NLS-1$
					commentWords = removeEmptyString(commentWords);
					commentContent = join(commentWords, " "); //$NON-NLS-1$
					commentContent = commentContent.trim();
					IRegion commentLineInformation = document
							.getLineInformation(commentStartLine);
					String commentLineContent = document.get(
							commentLineInformation.getOffset(),
							commentLineInformation.getLength()).trim();

					if (commentLineContent.startsWith("//") //$NON-NLS-1$
							|| commentLineContent.startsWith("/*") //$NON-NLS-1$
							|| commentLineContent.startsWith("*")) //$NON-NLS-1$
						justCommentLine = true;

					/*
					 * if (!justCommentLine) { if (commentWords != null &&
					 * (lineWidth + 1 + commentWords.get(0).length() >
					 * this.preferences.comment_line_length)) { insertNewLine();
					 * indentBaseOnPrevLine(commentStartLine); }
					 * 
					 * }
					 */
					boolean newLineStart = true;
					appendToBuffer("//"); //$NON-NLS-1$

					for (String word : commentWords) {
						if (this.preferences.comment_line_length != 9999
								&& !newLineStart
								&& (lineWidth + 1 + word.length() > this.preferences.comment_line_length)) {
							insertNewLine();
							if (!justCommentLine && indentLengthForComment == 0)
								indentBaseOnPrevLine(commentStartLine);
							// start at first column,and more than
							// comment_line_length
							if (!startAtFirstColumn
									|| (startAtFirstColumn && indentOnFirstColumn)) {
								if (indentLengthForComment >= 0) {
									appendToBuffer(indentStringForComment);

								} else {
									indent();
								}
							}
							// if (indentOnFirstColumn) {
							// indent();
							// }
							appendToBuffer("//"); //$NON-NLS-1$
							insertSpaces(1);
							appendToBuffer(word);
						} else {
							insertSpaces(1);
							appendToBuffer(word);
							newLineStart = false;
						}
					}
					if (secondReplaceBufferNeeded
							&& !isComment(document
									.getLineInformation(commentStartLine + 1))) {
						insertNewLine();
						indent();
						needInsertNewLine = false;
						needIndentNewLine = false;
						if (secondReplaceBuffer == null) {
							secondReplaceBuffer = afterNewLine;
						}
						appendToBuffer(secondReplaceBuffer);
						afterNewLine = EMPTY_STRING;
						secondReplaceBufferNeeded = false;
					}
					handleCharsWithoutComments(comment.sourceStart() + offset,
							comment.sourceEnd() + offset, true);
					if (needInsertNewLine) {
						insertNewLine();
						indent();
						needIndentNewLine = false;
						afterNewLine = EMPTY_STRING;
					}
				}

				start = comment.sourceEnd() + offset;
				startLine = commentStartLine;
				break;
			case org.eclipse.php.internal.core.compiler.ast.nodes.Comment.TYPE_PHPDOC:
				previousCommentIsSingleLine = false;
				inComment = false;
				handleCharsWithoutComments(start, comment.sourceStart()
						+ offset);
				inComment = true;
				resetEnableStatus(document.get(comment.sourceStart() + offset,
						comment.sourceEnd() - comment.sourceStart()));
				String codeBeforeComment = document.get(0,
						comment.sourceStart() + offset).trim();
				boolean isHeaderComment = codeBeforeComment.equals("<?") //$NON-NLS-1$
						|| codeBeforeComment.equals("<?php"); //$NON-NLS-1$
				if ((!isHeaderComment || this.preferences.comment_format_header)
						&& this.editsEnabled
						&& this.preferences.comment_format_javadoc_comment) {
					PHPDocBlock block = (PHPDocBlock) comment;

					appendToBuffer("/**"); //$NON-NLS-1$

					commentWords = new ArrayList<String>();
					org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[] texts = block
							.getTexts()
							.toArray(
									new org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[block
											.getTexts().size()]);
					PHPDocTag[] tags = block.getTags();
					if ((tags == null || tags.length == 0)) {
						texts = getNonblankScalars(texts);
					}
					boolean lastLineIsBlank = false;
					boolean isFirst = true;

					// description is blank
					if (getNonblankScalars(texts).length == 0) {
						texts = new org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[0];
					}
					if (this.preferences.comment_new_lines_at_javadoc_boundaries) {
						insertNewLineForPHPDoc();
						// description is blank
						if (texts.length == 0) {
							lastLineIsBlank = true;
						}
					}
					int textsLength = texts.length;
					for (int j = 0; j < textsLength; j++) {
						org.eclipse.php.internal.core.compiler.ast.nodes.Scalar scalar = texts[j];
						String word = scalar.getValue();
						if (word.trim().length() > 0) {
							commentWords.add(word);
							if (this.preferences.join_lines_in_comments) {

								if (!isFirst) {
									insertNewLineForPHPDoc();
								}
								isFirst = false;
								initCommentWords();
								formatPHPDocText(commentWords, null, false,
										false);
								commentWords = new ArrayList<String>();
								lastLineIsBlank = false;
							}
						} else if (!this.preferences.comment_clear_blank_lines_in_javadoc_comment) {
							isFirst = false;
							initCommentWords();
							formatPHPDocText(commentWords, null, false, false);
							insertNewLineForPHPDoc();
							commentWords = new ArrayList<String>();
							lastLineIsBlank = true;
						}

					}
					if (!commentWords.isEmpty()) {
						initCommentWords();
						formatPHPDocText(commentWords, null, false, false);
					}

					if (tags != null && tags.length > 0) {
						if (this.preferences.comment_insert_empty_line_before_root_tags
								&& !lastLineIsBlank) {
							insertNewLine();
							indent();
							appendToBuffer(" * "); //$NON-NLS-1$
						}
						for (int i = 0; i < tags.length; i++) {
							PHPDocTag phpDocTag = tags[i];
							boolean insertTag = true;
							String[] words = phpDocTag.getDescTexts();

							if ((i == tags.length - 1)
									&& !this.preferences.comment_new_lines_at_javadoc_boundaries) {
								words = getNonblankWords(words);
							}
							commentWords = new ArrayList<String>();

							if (getNonblankWords(words).length == 0) {
								// insert several lines
								formatCommentWords(phpDocTag, insertTag, false);
								for (int j = 0; j < words.length; j++) {
									insertNewLineForPHPDoc();
								}
							} else {
								for (int j = 0; j < words.length; j++) {
									String word = words[j];
									// if (word.length() == 0 || isAll(word,
									// '*')) {
									// continue;
									// }
									if (word.trim().length() > 0) {
										commentWords.add(word);
										if (this.preferences.join_lines_in_comments) {

											formatCommentWords(phpDocTag,
													insertTag, true);
											insertTag = false;
										}
									} else if (!this.preferences.comment_clear_blank_lines_in_javadoc_comment
											&& !insertTag) {

										formatCommentWords(phpDocTag,
												insertTag, true);
										insertTag = false;
									}

								}
								if (!commentWords.isEmpty() || insertTag) {
									formatCommentWords(phpDocTag, insertTag,
											!commentWords.isEmpty());
								}
							}

						}
						lastLineIsBlank = false;
					}
					if (this.preferences.comment_new_lines_at_javadoc_boundaries
							&& !lastLineIsBlank) {
						insertNewLine();
						indent();
						appendToBuffer(" */"); //$NON-NLS-1$
					} else if (lastLineIsBlank) {
						appendToBuffer("/"); //$NON-NLS-1$
					} else {
						indertWordToComment("*/"); //$NON-NLS-1$
					}
					handleCharsWithoutComments(comment.sourceStart() + offset,
							comment.sourceEnd() + offset, true);
				}
				start = comment.sourceEnd() + offset;
				insertNewLine();
				indent();
				afterNewLine = EMPTY_STRING;
				break;
			case org.eclipse.php.internal.core.compiler.ast.nodes.Comment.TYPE_MULTILINE:
				previousCommentIsSingleLine = false;
				IRegion startLinereg = document.getLineInformation(startLine);
				// ignore multi line comment in the middle of code in one line
				// example while /* kuku */ ( /* kuku */$a > 0 )
				if (getBufferFirstChar() == '\0'
						&& (startLine == endLine
								&& document
										.get(comment.sourceEnd() + offset,
												startLinereg.getOffset()
														+ startLinereg
																.getLength()
														- (comment.sourceEnd() + offset))
										.trim().length() == 0 || startLine != endLine)) {
					// buffer contains only whitespace chars
					indentOnFirstColumn = !startAtFirstColumn
							|| !this.preferences.never_indent_block_comments_on_first_column;
					if (startLine == commentStartLine) {
						if (position >= 0) {
							afterNewLine = replaceBuffer.substring(position
									+ lineSeparator.length(),
									replaceBuffer.length());
							replaceBuffer.replace(position,
									replaceBuffer.length(), " "); //$NON-NLS-1$
							lineWidth = comment.sourceStart() + offset
									- startLinereg.getOffset() + 1;
							indentOnFirstColumn = false;
						}
					} else {
						afterNewLine = EMPTY_STRING;
						needIndentNewLine = true;
						if ((this.preferences.never_indent_block_comments_on_first_column)
								&& indentOnFirstColumn) {

							if (position >= 0) {
								replaceBuffer.replace(
										position + lineSeparator.length(),
										replaceBuffer.length(), ""); //$NON-NLS-1$
								lineWidth = 0;
							} else {
								if (replaceBuffer.toString().trim().length() == 0) {
									replaceBuffer.setLength(0);
									lineWidth = 0;
								} else {
									insertNewLine();
								}
							}
						}
						if (position >= 0) {
							replaceBuffer.replace(
									position + lineSeparator.length(),
									replaceBuffer.length(), ""); //$NON-NLS-1$
							lineWidth = 0;
						} else {
							if (replaceBuffer.toString().trim().length() == 0) {
								replaceBuffer.setLength(0);
								lineWidth = 0;
							} else {
								insertNewLine();
							}
						}
						if (indentationLevelDesending || blockEnd) {
							// add single indentationChar * indentationSize
							// Because the comment is the previous indentation
							// level
							for (int i = 0; i < preferences.indentationSize; i++) {
								appendToBuffer(preferences.indentationChar);
								lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
										: 3;
							}
						}
					}
					resetCommentIndentVariables();
					if (startLine != commentStartLine && blockEnd) {
						recordCommentIndentVariables = true;
					}
					if (start <= comment.sourceStart() + offset) {
						doNotIndent = true;
						if (indentOnFirstColumn) {
							indent();
							doNotIndent = false;
							if (lineWidth > 0) {
								startAtFirstColumn = false;
							}
						}

						handleCharsWithoutComments(start, comment.sourceStart()
								+ offset);
						doNotIndent = false;
					}
					start = comment.sourceEnd() + offset;
					resetEnableStatus(document.get(comment.sourceStart()
							+ offset,
							comment.sourceEnd() - comment.sourceStart()));
					if (this.editsEnabled
							&& this.preferences.comment_format_block_comment) {
						if (startLine == commentStartLine) {
							initCommentIndentVariables(offset, startLine,
									comment, endWithNewLineIndent);
							lineWidth = indentLengthForComment;
						}
						if (startAtFirstColumn
								&& this.preferences.never_indent_block_comments_on_first_column) {
							indentLengthForComment = 0;
							indentStringForComment = ""; //$NON-NLS-1$
						}

						appendToBuffer("/*"); //$NON-NLS-1$
						commentContent = document.get(comment.sourceStart()
								+ offset,
								comment.sourceEnd() - comment.sourceStart());

						boolean needInsertNewLine = commentContent
								.endsWith(lineSeparator);
						if (!needInsertNewLine) {
							String[] delimiters = document
									.getLegalLineDelimiters();
							for (int i = 0; i < delimiters.length; i++) {
								needInsertNewLine = commentContent
										.endsWith(delimiters[i]);
								if (needInsertNewLine) {
									break;
								}
							}
						}
						commentContent = commentContent.trim();
						commentContent = commentContent.substring(2,
								commentContent.length() - 2);
						commentContent = commentContent
								.replaceAll("\r\n", "\n"); //$NON-NLS-1$ //$NON-NLS-2$
						List<String> lines = Arrays.asList(commentContent
								.split("\n")); //$NON-NLS-1$
						commentWords = new ArrayList<String>();
						if (lines.size() == 1) {

							String word = lines.get(0).trim();
							if (word.startsWith("*")) { //$NON-NLS-1$
								word = word.substring(1);
							}
							commentWords.add(word);
							initCommentWords();
							StringBuffer sb = new StringBuffer();
							for (String w : commentWords) {
								if (w.trim().length() == 0) {
									continue;
								}
								sb.append(w).append(" "); //$NON-NLS-1$
							}
							// +1 means ' ' after "/*",+2 means "*/"
							if (this.preferences.comment_line_length == 9999
									|| lineWidth + 1 + sb.length() + 2 <= this.preferences.comment_line_length) {
								appendToBuffer(" "); //$NON-NLS-1$
								appendToBuffer(sb.toString());
								appendToBuffer("*/"); //$NON-NLS-1$
								commentWords = new ArrayList<String>();
								handleCharsWithoutComments(
										comment.sourceStart() + offset,
										comment.sourceEnd() + offset, true);
								startLine = endLine;
								if (needInsertNewLine) {
									insertNewLine();
								} else {
									IRegion reg = document
											.getLineInformation(commentStartLine - 1);
									int lengthAfterCommentEnd = reg.getOffset()
											+ reg.getLength()
											- (comment.sourceEnd() + offset);
									if (lengthAfterCommentEnd <= 0) {
										insertNewLine();
									} else {
										String stringAfterCommentEnd = document
												.get(comment.sourceEnd()
														+ offset,
														lengthAfterCommentEnd);
										if (stringAfterCommentEnd.trim()
												.length() == 0) {
											insertNewLine();
										}
									}
								}
								break;
							}
							commentWords = new ArrayList<String>();
						} else {

						}
						if (this.preferences.comment_new_lines_at_block_boundaries) {
							insertNewLineForPHPBlockComment(
									indentLengthForComment,
									indentStringForComment);
						}

						for (int j = 0; j < lines.size(); j++) {
							String word = lines.get(j).trim();
							if (word.startsWith("*")) { //$NON-NLS-1$
								word = word.substring(1);
							}
							if (word.length() > 0) {
								commentWords.add(word);
								if (this.preferences.join_lines_in_comments) {

									formatCommentBlockWords(
											indentLengthForComment,
											indentStringForComment);
								}
							} else if (!this.preferences.comment_clear_blank_lines_in_block_comment) {

								if (j != 0 && j != lines.size() - 1) {
									formatCommentBlockWords(
											indentLengthForComment,
											indentStringForComment);
								}
							}

						}
						formatCommentBlockWords(indentLengthForComment,
								indentStringForComment);
						if (this.preferences.comment_new_lines_at_block_boundaries) {
							insertNewLine();
							if (indentLengthForComment >= 0) {
								appendToBuffer(indentStringForComment);

							} else {
								indent();
							}
							appendToBuffer(" */"); //$NON-NLS-1$
						} else {
							indertWordToComment("*/"); //$NON-NLS-1$
						}
						handleCharsWithoutComments(comment.sourceStart()
								+ offset, comment.sourceEnd() + offset, true);

					}
					insertNewLine();
					startLine = endLine;
				} else {
					// don't handle multiline
					start = end;
					replaceBuffer.setLength(0);
					resetEnableStatus(document.get(comment.sourceStart()
							+ offset,
							comment.sourceEnd() - comment.sourceStart()));
				}
				break;
			}
			if (needIndentNewLine) {
				indent();
				needIndentNewLine = false;
				afterNewLine = EMPTY_STRING;
			}
			appendToBuffer(afterNewLine);
		}
		inComment = false;
		ignoreEmptyLineSetting = oldIgnoreEmptyLineSetting;
		handleCharsWithoutComments(start, end);
	}

	private void indentBaseOnPrevLine(int commentStartLine)
			throws BadLocationException {
		IRegion prevLine = document.getLineInformation(commentStartLine);
		loop: for (int i = 0; i < prevLine.getLength(); i++) {
			switch (document.getChar(i + prevLine.getOffset())) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				appendToBuffer(document.getChar(i + prevLine.getOffset()));
				break;
			default:
				break loop;
			}

		}
	}

	private boolean isComment(IRegion iRegion) {
		for (int i = 0; i < iRegion.getLength() - 1; i++) {
			try {
				switch (document.getChar(iRegion.getOffset() + i)) {
				case '/':
					if (document.getChar(iRegion.getOffset() + i + 1) == '/')
						return true;
					else if (document.getChar(iRegion.getOffset() + i + 1) == '*')
						return true;
				case '*':
					return true;
				case ' ':
				case '\t':
					break;
				default:
					return false;
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	private boolean endWithNewLineIndent(String string) {
		String indent = getIndent();
		return string.endsWith(lineSeparator + indent);
	}

	private String getIndent() {
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < indentationLevel * preferences.indentationSize; i++) {
			sb.append(preferences.indentationChar);
		}
		return sb.toString();
	}

	private void resetCommentIndentVariables() {
		indentLengthForComment = -1;
		indentStringForComment = null;
	}

	private void indentForComment(boolean indentationLevelDesending) {

		indent();
		if (indentationLevelDesending || blockEnd) {
			for (int i = 0; i < preferences.indentationSize; i++) {
				appendToBuffer(preferences.indentationChar);
				lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
						: 3;
			}
		}
	}

	private void initCommentIndentVariables(int offset, int startLine,
			org.eclipse.php.internal.core.compiler.ast.nodes.Comment comment,
			boolean endWithNewLineIndent) throws BadLocationException {
		// TODO the value should be calculated from ReplaceEdit changes
		indentLengthForComment = 0;
		indentStringForComment = ""; //$NON-NLS-1$
		IRegion startRegion = document.getLineInformation(startLine);
		String startLineContent;
		startLineContent = document.get(startRegion.getOffset(),
				comment.sourceStart() + offset - startRegion.getOffset())
				.trim();
		// indentStringForComment = FormatterUtils.getLineBlanks(document,
		// startRegion);

		StringBuffer sb = new StringBuffer();
		int lastIndentationLevel = indentationLevel;
		if (endWithNewLineIndent) {
			if (indentationLevelList.size() >= 2) {
				lastIndentationLevel = indentationLevelList
						.get(indentationLevelList.size() - 2);
			} else {
				lastIndentationLevel = indentationLevelList
						.get(indentationLevelList.size() - 1);
			}
		} else {
			lastIndentationLevel = indentationLevelList
					.get(indentationLevelList.size() - 1);
		}
		for (int i = 0; i < lastIndentationLevel * preferences.indentationSize; i++) {
			sb.append(preferences.indentationChar);
		}
		for (int i = 0; i < startLineContent.length(); i++) {
			sb.append(" "); //$NON-NLS-1$
		}
		if (startLineContent.length() > 0) {
			sb.append(" "); //$NON-NLS-1$
		}
		indentStringForComment = sb.toString();
		char[] blankArray = indentStringForComment.toCharArray();
		for (int i = 0; i < blankArray.length; i++) {
			if (blankArray[i] == '\t') {
				indentLengthForComment += 3;
			} else {
				indentLengthForComment++;
			}
		}
	}

	public static List<String> removeEmptyString(List<String> commentWords) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < commentWords.size(); i++) {
			String word = commentWords.get(i);
			if (word.trim().length() != 0) {
				result.add(word);
			}

		}
		return result;
	}

	private org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[] getNonblankScalars(
			org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[] texts) {
		int end = texts.length;
		for (int i = texts.length - 1; i >= 0; i--) {
			if (texts[i].getValue().trim().length() == 0) {
				if (end > 0) {
					end--;
				}
			} else {
				break;
			}
		}
		if (end == 0) {
			return new org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[0];
		}
		int start = 0;
		for (int i = 0; i < texts.length; i++) {
			if (texts[i].getValue().trim().length() == 0) {
				if (start < texts.length - 1) {
					start++;
				}
			} else {
				break;
			}
		}
		org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[] result = new org.eclipse.php.internal.core.compiler.ast.nodes.Scalar[end
				- start];
		System.arraycopy(texts, start, result, 0, end - start);
		return result;
	}

	private String[] getNonblankWords(String[] words) {
		int length = words.length;
		for (int i = words.length - 1; i >= 0; i--) {
			if (words[i].trim().length() == 0) {
				length--;
			} else {
				break;
			}
		}
		String[] result = new String[length];
		System.arraycopy(words, 0, result, 0, length);
		return result;
	}

	private boolean isAll(String word, char c) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != c) {
				return false;
			}
		}
		return true;
	}

	private void resetEnableStatus(String content) {
		int enablingTagIndex = -1;
		int disablingTagIndex = -1;
		if (this.useTags) {
			if (this.disablingTag != null) {
				disablingTagIndex = content.lastIndexOf(disablingTag);
			}
			if (this.enablingTag != null) {
				enablingTagIndex = content.lastIndexOf(enablingTag);
			}
			if (enablingTagIndex < disablingTagIndex) {
				this.editsEnabled = false;
			} else if (enablingTagIndex > disablingTagIndex) {
				this.editsEnabled = true;
			}
		}
	}

	private void formatCommentBlockWords(int indentLength, String blanks) {
		initCommentWords();
		for (String word : commentWords) {
			if (word.trim().length() == 0) {
				continue;
			}
			indertWordToCommentBlock(word, indentLength, blanks);
		}
		commentWords = new ArrayList<String>();

	}

	private void formatCommentWords(PHPDocTag phpDocTag, boolean insertTag,
			boolean hasDesc) {
		initCommentWords();
		insertNewLineForPHPDoc();
		formatPHPDocText(commentWords, phpDocTag, insertTag, hasDesc);
		commentWords = new ArrayList<String>();

	}

	private void initCommentWords() {
		commentContent = join(commentWords, " "); //$NON-NLS-1$
		commentContent = commentContent.trim();
		commentWords = Arrays.asList(commentContent.split("[ \r\n]")); //$NON-NLS-1$
		commentWords = removeEmptyString(commentWords);
	}

	private void insertNewLineForPHPBlockComment(int indentLength, String blanks) {
		insertNewLine();
		if (indentLength >= 0) {
			appendToBuffer(blanks);
			lineWidth = lineWidth + (indentLength - blanks.length());
		} else {
			indent();
		}
		appendToBuffer(" *"); //$NON-NLS-1$
	}

	private void insertNewLineForPHPDoc() {
		insertNewLine();
		indent();
		appendToBuffer(" *"); //$NON-NLS-1$
	}

	private void formatPHPDocText(List<String> words, PHPDocTag phpDocTag,
			boolean insertTag, boolean hasDesc) {
		boolean insertSpace = true;
		String tag = ""; //$NON-NLS-1$
		int indentLength = 0;
		if (phpDocTag != null) {
			tag = "@" + PHPDocTag.getTagKind(phpDocTag.getTagKind()); //$NON-NLS-1$
			if (indentationLevelDesending) {
				for (int i = 0; i < preferences.indentationSize; i++) {
					indentLength += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 1
							: 4;
				}
			}
		}
		int tagLength = tag.length() + 1;
		newLineOfComment = true;
		if (phpDocTag != null) {
			if (insertTag) {
				insertSpaces(1);
				String reference = getTagReference(phpDocTag);
				appendToBuffer(tag);
				appendToBuffer(reference);
			}
			newLineOfComment = false;
			if (this.preferences.comment_insert_new_line_for_parameter
					&& phpDocTag.getTagKind() == PHPDocTag.PARAM) {
				if (insertTag && hasDesc) {
					insertNewLineForPHPDoc();
				}
				if (this.preferences.comment_indent_root_tags) {
					insertSpaces(tagLength);
				}

				insertSpaces(1);
				insertSpace = false;

				newLineOfComment = true;
				if (this.preferences.comment_indent_root_tags
						&& this.preferences.comment_indent_parameter_description) {
					for (int i = 0; i < preferences.indentationSize; i++) {
						appendToBuffer(preferences.indentationChar);
						lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
								: 3;
					}

				}
			} else if (!insertTag && this.preferences.comment_indent_root_tags) {
				insertSpaces(tagLength);
			}
		}

		for (String word : words) {
			if (word.trim().length() == 0) {
				continue;
			}
			indertWordToComment(phpDocTag, tagLength, word, insertSpace);
			insertSpace = true;
		}
	}

	private String getTagReference(PHPDocTag phpDocTag) {
		SimpleReference[] reference = phpDocTag.getReferencesWithOrigOrder();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < reference.length; i++) {
			sb.append(" ").append(reference[i].getName()); //$NON-NLS-1$
		}
		return sb.toString();
	}

	private void indertWordToComment(String word) {
		indertWordToComment(null, 0, word, true);
	}

	private void indertWordToCommentBlock(String word, int indentLength,
			String blanks) {
		if (this.preferences.comment_line_length != 9999
				&& !newLineOfComment
				&& (lineWidth + 1 + word.length() > this.preferences.comment_line_length)) {
			insertNewLine();
			if (indentLength >= 0) {
				appendToBuffer(blanks);
				lineWidth = lineWidth + (indentLength - blanks.length());
			} else {
				indent();
			}

			appendToBuffer(" * "); //$NON-NLS-1$

			appendToBuffer(word);
		} else {
			insertSpaces(1);
			appendToBuffer(word);
			newLineOfComment = false;
		}
	}

	private void indertWordToComment(PHPDocTag phpDocTag, int tagLength,
			String word, boolean insertSpace) {
		word = word.trim();
		if (this.preferences.comment_line_length != 9999
				&& !newLineOfComment
				&& (lineWidth + 1 + word.length() > this.preferences.comment_line_length)) {
			insertNewLine();
			indent();
			appendToBuffer(" * "); //$NON-NLS-1$

			if (phpDocTag != null) {
				if (this.preferences.comment_indent_root_tags) {
					insertSpaces(tagLength);
				}

				if (this.preferences.comment_indent_root_tags
						&& this.preferences.comment_indent_parameter_description
						&& phpDocTag.getTagKind() == PHPDocTag.PARAM) {
					for (int i = 0; i < preferences.indentationSize; i++) {
						appendToBuffer(preferences.indentationChar);
						lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
								: 3;
					}
				}
			}
			appendToBuffer(word);
		} else {
			if (insertSpace) {
				insertSpaces(1);
			}
			appendToBuffer(word);
			newLineOfComment = false;
		}
	}

	private void insertSpaces(int size) {
		for (int i = 0; i < size; i++) {
			replaceBuffer.append(SPACE);
			lineWidth++;
			isPrevSpace = true;
		}
	}

	private void handleForSemicolon(Expression[] beforExpressions,
			Expression[] afterExpressions) {
		if (this.preferences.insert_space_before_semicolon_in_for
				&& beforExpressions.length > 0) {
			insertSpace();
		}
		appendToBuffer(SEMICOLON);
		if (this.preferences.insert_space_after_semicolon_in_for
				&& afterExpressions.length > 0) {
			insertSpace();
		}
	}

	/**
	 * handle the PHP end tag
	 * 
	 * @param start
	 *            the end position of the ASTNode before the semicolon
	 * @param end
	 *            the position of the semicolon -1
	 */
	private void handlePhpEndTag(int start, int end, String endTagStr) {
		appendToBuffer(endTagStr);

		try {
			boolean foundTag = false;
			for (int index = 0; start + index < end; index++) {
				char currentChar = document.getChar(start + index);
				if (foundTag) {
					appendToBuffer(currentChar);
				} else if (currentChar == '>') {
					foundTag = true;
				}
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	/**
	 * handle the last semicolon of statement
	 * 
	 * @param start
	 *            the end position of the ASTNode before the semicolon
	 * @param end
	 *            the position of the semicolon -1
	 */
	private void handleSemicolon(int start, int end) {
		if (this.preferences.insert_space_before_semicolon
				&& !isHeredocSemicolon) {
			insertSpace();
		}
		// check if the statement end with ; or ?>
		if (isContainChar(start, end, SEMICOLON)) {
			appendToBuffer(SEMICOLON);
			isHeredocSemicolon = false;
		} else if (isContainChar(start, end, QUESTION_MARK)) {
			handlePhpEndTag(start, end, "?>"); //$NON-NLS-1$
		} else {
			handlePhpEndTag(start, end, "%>"); //$NON-NLS-1$
		}
		// till the semicolon
		handleChars(start, end);
		binaryExpressionLineWrapPolicy = -1;// reset the policy for binary
		// expression states
		if (wasBinaryExpressionWrapped) {
			indentationLevel -= binaryExpressionIndentGap;
			wasBinaryExpressionWrapped = false;
		}
		binaryExpressionIndentGap = 0;
		binaryExpressionSavedBuffer = null;
		if (isBinaryExpressionExtraIndentation) {
			indentationLevel--;
			isBinaryExpressionExtraIndentation = false;
		}
	}

	/**
	 * add the indentation text in the
	 * 
	 */
	private void indent() {
		if (!isPhpEqualTag) {
			indentationLevelList.add(indentationLevel);
			for (int i = 0; i < indentationLevel * preferences.indentationSize; i++) {
				appendToBuffer(preferences.indentationChar);
				lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0
						: 3;
			}
		}
	}

	// this method calculates the delta of lines width and lines number for AST
	// nodes such as
	// scalars, html in-lines etc... since these types can have multiple lines
	// we still need to check tabs length
	private void calcLinesAndWidth(ASTNode node) {
		try {
			int lineForStart = document.getLineOfOffset(node.getStart());
			int lineForEnd = document.getLineOfOffset(node.getEnd());

			if (lineForStart == lineForEnd) {
				lineWidth += node.getLength();
			} else {
				lineNumber += (lineForEnd - lineForStart);
				lineWidth = document.getLineLength(lineForEnd);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	private void insertNewLine() {
		if (!isPhpEqualTag) {
			appendToBuffer(lineSeparator);
			lineNumber++;
			lineWidth = 0;
		}
	}

	private void insertNewLines(Statement statement) {

		int numberOfLines = getNumbreOfLines(statement);

		for (int i = 0; i < numberOfLines; i++) {
			insertNewLine();
		}
	}

	private int getNumbreOfLines(Statement statement) {
		int numberOfLines = 1;
		switch (statement.getType()) {
		case ASTNode.NAMESPACE:
			numberOfLines = this.preferences.blank_lines_before_namespace + 1;
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;
			break;
		case ASTNode.FUNCTION_DECLARATION:
		case ASTNode.METHOD_DECLARATION:
			numberOfLines = this.preferences.blank_line_before_method_declaration + 1;
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;
			break;
		case ASTNode.FIELD_DECLARATION:
			numberOfLines = this.preferences.blank_line_before_field_declaration + 1;
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;
			break;
		case ASTNode.CLASS_DECLARATION:
		case ASTNode.INTERFACE_DECLARATION:
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;
			numberOfLines = this.preferences.blank_line_before_class_declaration + 1;
			break;
		case ASTNode.CONSTANT_DECLARATION:
			numberOfLines = this.preferences.blank_line_before_constant_declaration + 1;
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;
			break;
		default:
			// no empty lines
			numberOfLines = 1;
			break;
		}
		return numberOfLines;
	}

	private void handleSplittedPhpBlock(int offset, int end)
			throws BadLocationException {
		int line = document.getLineOfOffset(offset);
		IRegion lineRegion = document.getLineInformationOfOffset(offset);
		int lineLength = document.getLineLength(line);
		switch (getPhpStartTag(offset)) {
		case PHP_OPEN_ASP_TAG:
		case PHP_OPEN_SHORT_TAG:
			if (document
					.get(offset + 2,
							lineRegion.getOffset() + lineRegion.getLength()
									- (offset + 2)).trim().length() != 0) {
				insertNewLine();
			}
			handleCharsWithoutComments(offset + 2, end);
			break;
		case PHP_OPEN_SHORT_TAG_WITH_EQUAL:
			handleCharsWithoutComments(offset + 3, end);
			break;
		case PHP_OPEN_TAG:
			if (document
					.get(offset + 5,
							lineRegion.getOffset() + lineRegion.getLength()
									- (offset + 5)).trim().length() != 0) {
				insertNewLine();
			}
			handleCharsWithoutComments(offset + 5, end);
			break;
		}
	}

	private void insertSpace() {
		if (!isPrevSpace) {
			replaceBuffer.append(SPACE);
			lineWidth++;
			isPrevSpace = true;
		}
	}

	private void insertString(int offset, int end, String content) {
		assert end >= offset;
		ReplaceEdit replaceEdit = new ReplaceEdit(offset, end - offset, content);
		changes.add(replaceEdit);
	}

	private boolean isContainChar(int start, int end, char c) {
		try {
			for (int index = 0; start + index < end; index++) {
				if (document.getChar(start + index) == c) {
					return true;
				}
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return false;
	}

	private boolean isEmptyLine(int line) throws BadLocationException {
		int lineStart = document.getLineOffset(line);
		int lineEnd = lineStart + document.getLineLength(line);

		for (int offset = lineStart; offset < lineEnd; offset++) {
			char currChar = document.getChar(offset);
			if (currChar != ' ' && currChar != '\t' && currChar != '\r'
					&& currChar != '\n') {
				// not empty line
				return false;
			}
		}
		return true;
	}

	// this operation "reverts" the visitor into the last "saved" state of the
	// changes
	// since when we need to go back to first element within comma-separated
	// list
	// after we already added the formatting changes into the buffer.
	private void revert(String savedBuffer, int changesIndex) {
		replaceBuffer.setLength(0);
		replaceBuffer.append(savedBuffer);
		for (int index = changes.size() - 1; index > changesIndex; index--) {
			changes.remove(index);
		}
	}

	private void scan(final int offset, final int end) throws Exception {
		final Reader reader = new DocumentReader(document, offset, end - offset);

		if (astLexer == null) {
			// create the lexer for the first time
			astLexer = getLexer(reader);
		} else {
			// reset the lexer
			astLexer.yyreset(reader);
			astLexer.resetCommentList();
		}
		// ST_IN_SCRIPTING
		assert stInScriptin != -1;
		astLexer.yybegin(stInScriptin);

		tokens.clear();
		try {
			Symbol symbol = null;
			do {
				symbol = astLexer.next_token();
				tokens.add(symbol);
			} while (symbol != null && symbol.sym != 0);
		} catch (Exception e) {
			// do nothing
		}
	}

	private int setSpaceAfterBlock(int offset) {
		if (this.preferences.insert_space_after_closing_brace_in_block) {
			insertSpace();
			// handleChars(offset, offset + 1);
			// return offset + 1;
			handleChars(offset, offset);
		}
		return offset;
	}

	public boolean visit(ArrayAccess arrayAccess) {
		Expression variableName = arrayAccess.getName();
		variableName.accept(this);
		if (this.preferences.insert_space_before_opening_bracket_in_array) {
			insertSpace();
		}
		if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_ARRAY) {
			appendToBuffer(OPEN_BRACKET);
		} else {
			appendToBuffer(OPEN_CURLY);
		}
		int lastPosition = variableName.getEnd();
		if (arrayAccess.getIndex() == null) {
			if (this.preferences.insert_space_between_empty_brackets) {
				insertSpace();
			}
		} else {
			if (this.preferences.insert_space_after_opening_bracket_in_array) {
				insertSpace();
			}
			handleChars(lastPosition, arrayAccess.getIndex().getStart());

			arrayAccess.getIndex().accept(this);
			if (this.preferences.insert_space_before_closing_bracket_in_array) {
				insertSpace();
			}
			lastPosition = arrayAccess.getIndex().getEnd();
		}
		handleChars(lastPosition, arrayAccess.getEnd() - 1);
		lineWidth++;// we need to add the closing bracket/curly
		return false;
	}

	public boolean visit(ArrayCreation arrayCreation) {
		if (this.preferences.insert_space_before_opening_paren_in_array) {
			insertSpace();
		}
		int lastPosition; // array
		if (arrayCreation.isHasArrayKey()) {
			appendToBuffer(OPEN_PARN);
			lastPosition = arrayCreation.getStart() + 5;
		} else {
			// appendToBuffer(OPEN_BRACKET);
			lastPosition = arrayCreation.getStart() + 1;
		}

		List<ArrayElement> eleList = arrayCreation.elements();
		ArrayElement[] elements = new ArrayElement[eleList.size()];
		elements = eleList.toArray(elements);

		if (elements.length > 0) {
			if (this.preferences.insert_space_after_opening_paren_in_array) {
				insertSpace();
			}

			if (this.preferences.new_line_after_open_array_parenthesis) {
				insertNewLine();
				indentationLevel++;
				indent();
				indentationLevel--;
			}

			lineWidth += 5;
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_expressions_in_array_init_indent_policy,
					this.preferences.line_wrap_array_init_indentation);

			// work around for close bracket.
			lineWidth++;

			lastPosition = handleCommaList(
					elements,
					lastPosition,
					this.preferences.insert_space_before_list_comma_in_array,
					this.preferences.insert_space_after_list_comma_in_array,
					this.preferences.line_wrap_expressions_in_array_init_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_expressions_in_array_init_force_split);

			// work around for close bracket.
			lineWidth--;

			if (this.preferences.insert_space_before_closing_paren_in_array) {
				insertSpace();
			}

			if (this.preferences.new_line_before_close_array_parenthesis_array) {
				insertNewLine();
				indent();
			}

		}

		if (arrayCreation.isHasArrayKey()) {
			appendToBuffer(CLOSE_PARN);
		} else {
			appendToBuffer(CLOSE_BRACKET);
		}

		if (arrayCreation.getArrayDereferenceList() != null
				&& !arrayCreation.getArrayDereferenceList().getDereferences()
						.isEmpty()) {
			lastPosition = formatDereference(lastPosition,
					arrayCreation.getArrayDereferenceList());
		} else {
			handleChars(lastPosition, arrayCreation.getEnd());
		}

		return false;
	}

	private int calculateIndentGap(int indentationPolicy, int defaultIndentation) {
		switch (indentationPolicy) {
		case DEFAULT_INDENTATION:
			return defaultIndentation;
		case INDENT_ON_COLUMN:
			// gap is the number of indentation for all the line
			// we increase the indentationLevel to get the gap
			int lineIndentation;
			if (this.preferences.indentationChar == '\t') {
				lineIndentation = (int) Math.ceil(lineWidth / 4);
			} else {
				lineIndentation = (int) Math.ceil(lineWidth
						/ this.preferences.indentationSize);
			}
			return lineIndentation - indentationLevel;
		case INDENT_ONE:
			return 1;
		default:
			return NO_LINE_WRAP_INDENT;
		}
	}

	public boolean visit(ArrayElement arrayElement) {
		if (arrayElement.getKey() != null) {
			arrayElement.getKey().accept(this);
			if (this.preferences.insert_space_before_arrow_in_array) {
				insertSpace();
			}
			appendToBuffer(KEY_VALUE_OPERATOR);
			if (this.preferences.insert_space_after_arrow_in_array) {
				insertSpace();
			}
			handleChars(arrayElement.getKey().getEnd(), arrayElement.getValue()
					.getStart());
		}
		arrayElement.getValue().accept(this);
		return false;
	}

	public boolean visit(Assignment assignment) {
		VariableBase leftSide = assignment.getLeftHandSide();
		leftSide.accept(this);
		if (this.preferences.insert_space_before_assignment) {
			insertSpace();
		}
		appendToBuffer(Assignment.getOperator(assignment.getOperator()));
		if (this.preferences.insert_space_after_assignment) {
			insertSpace();
		}

		// handle the chars between the variable to the value
		Expression rightSideValue = assignment.getRightHandSide();

		handleChars(leftSide.getEnd(), rightSideValue.getStart());

		rightSideValue.accept(this);
		return false;
	}

	public boolean visit(ASTError astError) {
		calcLinesAndWidth(astError);
		return false;
	}

	public boolean visit(BackTickExpression backTickExpression) {
		calcLinesAndWidth(backTickExpression);
		return false;
	}

	public boolean visit(Block block) {
		boolean blockIndentation = false;
		boolean isPhpMode = true;
		boolean isEmptyBlockNewLine = true;
		boolean isUnbracketedNamespace = false;
		boolean isNamespace = false;

		boolean isClassDeclaration = false;
		boolean isFunctionDeclaration = false;

		switch (block.getParent().getType()) {
		case ASTNode.NAMESPACE:
			isNamespace = true;
			if (!block.isCurly()) {
				isEmptyBlockNewLine = false;
				isUnbracketedNamespace = true;
				if (block.statements().size() > 0) {
					Statement statement = block.statements().get(0);
					// need check how many new lines will the next statement
					// insert
					int numberOfLines = getNumbreOfLines(statement) - 1;
					numberOfLines = this.preferences.blank_lines_after_namespace
							- numberOfLines;
					if (numberOfLines > 0) {
						for (int j = 0; j < numberOfLines; j++) {
							insertNewLine();
						}
					}
				} else {
					for (int i = 0; i < this.preferences.blank_lines_after_namespace; i++) {
						insertNewLine();
					}
				}
				// ignoreEmptyLineSetting = true;
				ignoreEmptyLineSetting = !preferences.indent_empty_lines;

				break;
			}
			if (block.statements().size() > 0) {
				Statement statement = block.statements().get(0);
				// need check how many new lines will the next statement insert
				int numberOfLines = getNumbreOfLines(statement) - 1;
				numberOfLines = this.preferences.blank_lines_after_namespace
						- numberOfLines;
				if (numberOfLines > 0) {
					for (int j = 0; j < numberOfLines; j++) {
						insertNewLine();
					}
				}
			} else {
				for (int i = 0; i < this.preferences.blank_lines_after_namespace; i++) {
					insertNewLine();
				}
			}
			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;

		case ASTNode.CLASS_DECLARATION:
		case ASTNode.INTERFACE_DECLARATION:
			isEmptyBlockNewLine = preferences.new_line_in_empty_class_body;
			blockIndentation = this.preferences.indent_statements_within_type_declaration;
			isClassDeclaration = true;
			break;
		case ASTNode.SWITCH_STATEMENT:
			blockIndentation = this.preferences.indent_statements_within_switch;
			break;
		case ASTNode.FUNCTION_DECLARATION:
			isEmptyBlockNewLine = preferences.new_line_in_empty_method_body;
			blockIndentation = this.preferences.indent_statements_within_function;
			for (int i = 0; i < this.preferences.blank_line_at_begin_of_method; i++) {
				insertNewLine();
			}
			isFunctionDeclaration = true;

			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;

			break;
		default:
			isEmptyBlockNewLine = preferences.new_line_in_empty_block;
			blockIndentation = this.preferences.indent_statements_within_block;
			break;
		}

		if (blockIndentation) {
			indentationLevel++;
		}

		int lastStatementEndOffset;
		if (isUnbracketedNamespace) {
			lastStatementEndOffset = block.getStart() - 1;
		} else {
			lastStatementEndOffset = block.getStart() + 1;
		}

		List<Statement> statementsList = block.statements();
		Statement[] statements = new Statement[statementsList.size()];
		statements = block.statements().toArray(statements);
		for (int i = 0; i < statements.length; i++) {
			boolean isHtmlStatement = statements[i].getType() == ASTNode.IN_LINE_HTML;
			boolean isASTError = statements[i].getType() == ASTNode.AST_ERROR;
			if (isASTError && i + 1 < statements.length) {
				lastStatementEndOffset = statements[i + 1].getStart();
			} else {
				if (isPhpMode && !isHtmlStatement) {
					// PHP -> PHP
					if (getPhpStartTag(lastStatementEndOffset) != -1) {
						insertNewLine();
					}
					if (isThrowOrReturnFormatCase(statements)) {
						// do nothing... This is a Throw/Return case
					} else {
						insertNewLines(statements[i]);
						indent();
					}
					handleChars(lastStatementEndOffset,
							statements[i].getStart());
				} else if (isPhpMode && isHtmlStatement) {
					// PHP -> HTML
					isPhpMode = false;
				} else if (!isPhpMode && !isHtmlStatement) {
					// HTML -> PHP
					isPhpEqualTag = getPhpStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
					insertNewLines(statements[i]);
					indent();
					if (lastStatementEndOffset <= statements[i].getStart()) {
						handleChars(lastStatementEndOffset,
								statements[i].getStart());
					}
					isPhpMode = true;
				} else {
					// HTML -> HTML
					assert false;
				}
				statements[i].accept(this);
				lastStatementEndOffset = statements[i].getEnd();
				if (isNamespace && i + 1 < statements.length
						&& statements[i].getType() == ASTNode.USE_STATEMENT) {
					if (statements[i + 1].getType() == ASTNode.USE_STATEMENT) {
						// for (int j = 0; j <
						// this.preferences.blank_lines_between_use_statements;
						// j++) {
						// insertNewLine();
						// }

						// ignoreEmptyLineSetting = true;
						ignoreEmptyLineSetting = !preferences.indent_empty_lines;

					} else {
						// need check how many new lines will the next statement
						// insert
						int numberOfLines = getNumbreOfLines(statements[i + 1]) - 1;
						numberOfLines = this.preferences.blank_lines_after_use_statements
								- numberOfLines;
						if (numberOfLines > 0) {
							for (int j = 0; j < numberOfLines; j++) {
								insertNewLine();
							}
						}

						// ignoreEmptyLineSetting = true;
						ignoreEmptyLineSetting = !preferences.indent_empty_lines;

					}
				}
			}
		}
		// in case of the last statement is html statement
		if (!isPhpMode) {
			isPhpEqualTag = false;
		}
		// set the block end
		if (blockIndentation) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
		int endPosition = block.getEnd() - 1;
		boolean hasComments = false;
		if (startRegionPosition < endPosition
				&& endRegionPosition >= endPosition) {
			try {
				hasComments = hasComments(lastStatementEndOffset, endPosition);
			} catch (Exception e) {
			}
		}

		if (statements.length > 0 || isEmptyBlockNewLine || hasComments) {
			if (isUnbracketedNamespace || isThrowOrReturnFormatCase(statements)) {
				// do not add new line... Throw/Return Statements within an If
				// Statement block
			} else {
				// if ((statements.length > 0 || hasComments)
				// && (isClassDeclaration || isFunctionDeclaration)) {
				// if (isClassDeclaration) {
				// for (int j = 0; j < preferences.blank_line_at_end_of_class;
				// j++) {
				// insertNewLine();
				// }
				// if (preferences.blank_line_at_end_of_class > 0) {
				// indent();
				// }
				// } else {
				// for (int j = 0; j < preferences.blank_line_at_end_of_method;
				// j++) {
				// insertNewLine();
				// }
				// if (preferences.blank_line_at_end_of_method > 0) {
				// indent();
				// }
				// }
				// } else {
				insertNewLine();
				indent();
				// }

			}
		}

		if (block.getEnd() > block.getStart()) {
			int end = block.getEnd() - 1;
			if (!block.isCurly()) {
				switch (block.getParent().getType()) {
				case ASTNode.SWITCH_STATEMENT:
					end = block.getEnd() - "endswitch".length();//$NON-NLS-1$
					break;
				case ASTNode.WHILE_STATEMENT:
					end = block.getEnd() - "endwhile".length();//$NON-NLS-1$
					break;
				case ASTNode.FOR_STATEMENT:
					end = block.getEnd() - "endfor".length();//$NON-NLS-1$
					break;
				case ASTNode.FOR_EACH_STATEMENT:
					end = block.getEnd() - "endforeach".length();//$NON-NLS-1$
					break;
				case ASTNode.DECLARE_STATEMENT:
					end = block.getEnd() - "enddeclare".length();//$NON-NLS-1$
					break;
				case ASTNode.IF_STATEMENT:
					end = block.getEnd();
					break;
				}
			}
			if (/*
				 * (statements.length > 0 || hasComments) &&
				 */(isClassDeclaration || isFunctionDeclaration)) {
				if (isClassDeclaration) {
					for (int j = 0; j < preferences.blank_line_at_end_of_class; j++) {
						insertNewLine();
					}
					if (preferences.blank_line_at_end_of_class > 0) {
						indent();
					}
				} else {
					for (int j = 0; j < preferences.blank_line_at_end_of_method; j++) {
						insertNewLine();
					}
					if (preferences.blank_line_at_end_of_method > 0) {
						indent();
					}
				}
			}

			// ignoreEmptyLineSetting = true;
			ignoreEmptyLineSetting = !preferences.indent_empty_lines;

			blockEnd = true;
			handleChars(lastStatementEndOffset, end);
			blockEnd = false;
			lineWidth++;// closing curly
		}
		return false;
	}

	// this checks whether it is an IF block (with curly) with one line and the
	// line
	// is either return OR throw expression AND the FORMAT flag is ON
	private boolean isThrowOrReturnFormatCase(Statement[] statements) {
		return preferences.control_statement_keep_guardian_on_one_line
				&& (statements.length == 1)
				&& (statements[0].getParent().getParent() instanceof IfStatement)
				&& (((IfStatement) statements[0].getParent().getParent())
						.getFalseStatement() == null)
				&& (statements[0].getType() == ASTNode.RETURN_STATEMENT
						|| statements[0].getType() == ASTNode.YIELD_STATEMENT || statements[0]
						.getType() == ASTNode.THROW_STATEMENT);
	}

	public boolean visit(BreakStatement breakStatement) {
		int lastPosition = breakStatement.getStart() + 5;
		lineWidth += 5;
		Expression expression = breakStatement.getExpression();
		if (expression != null) {
			insertSpace();
			handleChars(lastPosition, expression.getStart());

			expression.accept(this);
			lastPosition = expression.getEnd();
		}

		handleSemicolon(lastPosition, breakStatement.getEnd());
		return false;
	}

	public boolean visit(CastExpression castExpression) {
		// (type) expression
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_before_cast_type) {
			insertSpace();
		}

		// get the exact cast type
		String type = EMPTY_STRING;
		int start = castExpression.getStart();
		int end = castExpression.getExpression().getStart();
		switch (castExpression.getCastingType()) {
		case CastExpression.TYPE_INT:
			if (isContainChar(start, end, 'e')) {
				type = "integer"; //$NON-NLS-1$
			} else {
				type = "int"; //$NON-NLS-1$
			}
			break;
		case CastExpression.TYPE_REAL:
			if (isContainChar(start, end, 'f')) {
				type = "float"; //$NON-NLS-1$
			} else if (isContainChar(start, end, 'r')) {
				type = "real"; //$NON-NLS-1$
			} else {
				type = "double"; //$NON-NLS-1$
			}
			break;
		case CastExpression.TYPE_STRING:
			type = "string"; //$NON-NLS-1$
			break;
		case CastExpression.TYPE_ARRAY:
			type = "array"; //$NON-NLS-1$
			break;
		case CastExpression.TYPE_OBJECT:
			type = "object"; //$NON-NLS-1$
			break;
		case CastExpression.TYPE_BOOL:
			if (isContainChar(start, end, 'e')) {
				type = "boolean"; //$NON-NLS-1$
			} else {
				type = "bool"; //$NON-NLS-1$
			}
			break;
		case CastExpression.TYPE_UNSET:
			type = "unset"; //$NON-NLS-1$
			break;
		}

		appendToBuffer(type);
		if (this.preferences.insert_space_after_cast_type) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);
		if (this.preferences.insert_space_after_cast_expression) {
			insertSpace();
		}
		// till the expression
		handleChars(start, end);

		castExpression.getExpression().accept(this);
		return false;
	}

	public boolean visit(CatchClause catchClause) {
		// handle the chars between the 'catch' and the identifier start
		// position
		if (this.preferences.insert_space_before_opening_paren_in_catch) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_catch) {
			insertSpace();
		}
		lineWidth += 5;
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=326384
		int start = catchClause.getClassName().getStart();
		if (catchClause.getClassName() instanceof NamespaceName) {
			NamespaceName namespaceName = (NamespaceName) catchClause
					.getClassName();
			try {
				if (namespaceName.isGlobal()
						&& (Character.isWhitespace(document.getChar(start - 1)) || document
								.getChar(start - 1) == '\\')) {
					start -= 1;
				}
			} catch (BadLocationException e) {
				// should not be here
			}
		}
		// end
		handleChars(catchClause.getStart() + 5, start);

		// handle the catch identifier
		catchClause.getClassName().accept(this);
		insertSpace();
		handleChars(catchClause.getClassName().getEnd(), catchClause
				.getVariable().getStart());
		catchClause.getVariable().accept(this);

		// set the catch closing parn spaces
		if (this.preferences.insert_space_before_closing_paren_in_catch) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		boolean isIndentationAdded = handleBlockOpenBrace(
				this.preferences.brace_position_for_block,
				this.preferences.insert_space_before_opening_brace_in_block);
		handleChars(catchClause.getVariable().getEnd(), catchClause.getBody()
				.getStart());
		catchClause.getBody().accept(this);
		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
		return false;
	}

	public boolean visit(ConstantDeclaration classConstantDeclaration) {
		boolean isFirst = true;
		insertSpace();
		int lastPosition = classConstantDeclaration.getStart() + 5;
		lineWidth += 5;
		List<Identifier> names = classConstantDeclaration.names();

		Identifier[] variableNames = new Identifier[names.size()];
		variableNames = names.toArray(variableNames);

		for (int i = 0; i < variableNames.length; i++) {
			// handle comma between variables
			if (!isFirst) {
				if (this.preferences.insert_space_before_comma_in_class_constant) {
					insertSpace();
				}
				appendToBuffer(COMMA);
				if (this.preferences.insert_space_after_comma_in_class_constant) {
					insertSpace();
				}
			}
			handleChars(lastPosition, variableNames[i].getStart());
			variableNames[i].accept(this);
			lastPosition = variableNames[i].getEnd();

			// handle initial assignments
			if (this.preferences.insert_space_before_assignment) {
				insertSpace();
			}
			appendToBuffer(EQUAL);
			if (this.preferences.insert_space_after_assignment) {
				insertSpace();
			}

			List<Expression> initializers = classConstantDeclaration
					.initializers();

			Expression[] constantValues = new Expression[initializers.size()];

			constantValues = initializers.toArray(constantValues);

			handleChars(lastPosition, constantValues[i].getStart());
			constantValues[i].accept(this);
			lastPosition = constantValues[i].getEnd();
			isFirst = false;
		}
		handleSemicolon(lastPosition, classConstantDeclaration.getEnd());
		return false;
	}

	public boolean visit(ClassDeclaration classDeclaration) {
		// handle spaces between modifier, 'class' and class name
		String modifier = ClassDeclaration.getModifier(classDeclaration
				.getModifier());
		if (!modifier.equals(EMPTY_STRING)) {
			appendToBuffer(modifier);
			insertSpace();
		}
		if (classDeclaration instanceof TraitDeclaration) {
			appendToBuffer("trait"); //$NON-NLS-1$
		} else {
			appendToBuffer("class"); //$NON-NLS-1$
		}
		insertSpace();
		handleChars(classDeclaration.getStart(), classDeclaration.getName()
				.getStart());

		classDeclaration.getName().accept(this);

		int lastPosition = classDeclaration.getName().getEnd();
		Expression superClass = classDeclaration.getSuperClass();
		// handle super class
		if (superClass != null) {
			appendToBuffer(" extends "); //$NON-NLS-1$
			int start = superClass.getStart();
			// workaround
			// remove this after fixing of
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=326384
			if (superClass instanceof NamespaceName
					&& ((NamespaceName) superClass).isGlobal()) {
				start -= 1;
			}
			// end

			handleChars(lastPosition, start);
			classDeclaration.getSuperClass().accept(this);
			lastPosition = classDeclaration.getSuperClass().getEnd();
		}

		List<Identifier> interfacesList = classDeclaration.interfaces();
		Identifier[] interfaces = new Identifier[interfacesList.size()];
		interfaces = interfacesList.toArray(interfaces);
		// handle class implements
		if (interfaces != null && interfaces.length > 0) {
			appendToBuffer(" implements "); //$NON-NLS-1$

			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_superinterfaces_in_type_declaration_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);
			lastPosition = handleCommaList(
					interfaces,
					lastPosition,
					this.preferences.insert_space_before_comma_in_implements,
					this.preferences.insert_space_after_comma_in_implements,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_force_split);
		}

		// handle class body
		boolean isIndentationAdded = handleBlockOpenBrace(
				this.preferences.brace_position_for_class,
				this.preferences.insert_space_before_opening_brace_in_class);
		handleChars(lastPosition, classDeclaration.getBody().getStart());

		classDeclaration.getBody().accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
		return false;
	}

	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		if (classInstanceCreation.getChainingInstanceCall() != null
				&& !classInstanceCreation.getChainingInstanceCall()
						.getChainingMethodOrProperty().isEmpty()) {
			appendToBuffer(OPEN_PARN);
		}
		// insertSpace();
		appendToBuffer("new "); //$NON-NLS-1$
		// lineWidth += 3; // the 'new' word
		handleChars(classInstanceCreation.getStart(), classInstanceCreation
				.getClassName().getStart());
		classInstanceCreation.getClassName().accept(this);
		if (this.preferences.insert_space_before_opening_paren_in_function) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		List<Expression> ctorParams = classInstanceCreation.ctorParams();
		int numberOfCtorParameters = ctorParams.size();

		if (numberOfCtorParameters == 0
				&& this.preferences.insert_space_between_empty_paren_in_function) {
			insertSpace();
		}

		if (numberOfCtorParameters > 0
				&& this.preferences.insert_space_after_opening_paren_in_function) {
			insertSpace();
		}

		Expression[] arrayOfParameters = (Expression[]) ctorParams
				.toArray(new Expression[ctorParams.size()]);
		int indentationGap = calculateIndentGap(
				this.preferences.line_wrap_arguments_in_allocation_expression_indent_policy,
				this.preferences.line_wrap_wrapped_lines_indentation);
		int lastPosition = handleCommaList(
				arrayOfParameters,
				classInstanceCreation.getClassName().getEnd(),
				this.preferences.insert_space_before_comma_in_function,
				this.preferences.insert_space_after_comma_in_function,
				this.preferences.line_wrap_arguments_in_allocation_expression_line_wrap_policy,
				indentationGap,
				this.preferences.line_wrap_arguments_in_allocation_expression_force_split);

		if (numberOfCtorParameters > 0
				&& this.preferences.insert_space_before_closing_paren_in_function) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);
		if (classInstanceCreation.getChainingInstanceCall() != null
				&& !classInstanceCreation.getChainingInstanceCall()
						.getChainingMethodOrProperty().isEmpty()) {
			appendToBuffer(CLOSE_PARN);
		}
		handleChars(lastPosition, classInstanceCreation.getEnd());
		return false;
	}

	public boolean visit(ClassName className) {
		className.getName().accept(this);
		return false;
	}

	public boolean visit(CloneExpression cloneExpression) {
		insertSpace();
		lineWidth += 5;// the 'clone'
		// till the expression
		Expression expression = cloneExpression.getExpression();
		handleChars(cloneExpression.getStart() + 5, expression.getStart());

		expression.accept(this);
		return false;
	}

	public boolean visit(Comment comment) {
		// do nothing
		return false;
	}

	public boolean visit(ConditionalExpression conditionalExpression) {

		// start
		// condition ? true : false
		conditionalExpression.getCondition().accept(this);
		int lastPosition = conditionalExpression.getCondition().getEnd();
		// condition -> if true
		if (this.preferences.insert_space_before_conditional_question_mark) {
			insertSpace();
		}
		appendToBuffer(QUESTION_MARK);
		lastPosition++;
		if (this.preferences.insert_space_after_conditional_question_mark) {
			insertSpace();
		}
		Expression ifTrue = conditionalExpression.getIfTrue();
		Expression ifFalse = conditionalExpression.getIfFalse();
		int offset = conditionalExpression.getCondition().getStart();

		int colonOffset = 0;
		if (ifTrue != null) {
			handleChars(conditionalExpression.getCondition().getEnd(),
					ifTrue.getStart());
			ifTrue.accept(this);
		} else {
			int length = offset;
			if (ifFalse != null) {
				length = ifFalse.getStart();
			}
			colonOffset = getCharPosition(conditionalExpression.getCondition()
					.getEnd(), length, ':');
			handleChars(conditionalExpression.getCondition().getEnd(),
					colonOffset);
		}

		// iftrue -> iffalse
		if (this.preferences.insert_space_before_conditional_colon) {
			insertSpace();
		}
		appendToBuffer(COLON);

		if (this.preferences.insert_space_after_conditional_colon) {
			insertSpace();
		}

		if (ifTrue != null && ifFalse != null) {
			handleChars(ifTrue.getEnd(), conditionalExpression.getIfFalse()
					.getStart());
		} else if (ifTrue == null && ifFalse != null) {
			handleChars(colonOffset, conditionalExpression.getIfFalse()
					.getStart());
		} else if (ifTrue != null && ifFalse == null) {
			handleChars(ifTrue.getEnd(), colonOffset);
		}

		if (ifFalse != null) {
			ifFalse.accept(this);
		}
		// end

		return false;
	}

	public boolean visit(ContinueStatement continueStatement) {
		int lastPosition = continueStatement.getStart() + 8;
		lineWidth += 8;
		Expression expression = continueStatement.getExpression();
		if (expression != null) {
			insertSpace();
			handleChars(lastPosition, expression.getStart());

			expression.accept(this);
			lastPosition = expression.getEnd();
		}

		handleSemicolon(lastPosition, continueStatement.getEnd());
		return false;
	}

	public boolean visit(DeclareStatement declareStatement) {
		boolean isFirst = true;
		if (this.preferences.insert_space_before_opening_paren_in_declare) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_declare) {
			insertSpace();
		}
		int lastPosition = declareStatement.getStart() + 7;
		lineWidth += 7;
		List<Identifier> direciveNameList = declareStatement.directiveNames();
		Identifier[] directiveNames = new Identifier[direciveNameList.size()];
		directiveNames = direciveNameList.toArray(directiveNames);

		for (int i = 0; i < directiveNames.length; i++) {
			// handle comma between variables
			if (!isFirst) {
				if (this.preferences.insert_space_before_comma_in_class_variable) {
					insertSpace();
				}
				appendToBuffer(COMMA);
				if (this.preferences.insert_space_after_comma_in_class_variable) {
					insertSpace();
				}
			}
			handleChars(lastPosition, directiveNames[i].getStart());
			directiveNames[i].accept(this);
			lastPosition = directiveNames[i].getEnd();

			// handle initial assignments
			if (this.preferences.insert_space_before_assignment) {
				insertSpace();
			}
			appendToBuffer(EQUAL);
			if (this.preferences.insert_space_after_assignment) {
				insertSpace();
			}

			List<Expression> directiveValuesList = declareStatement
					.directiveValues();
			Expression[] directiveValues = new Expression[directiveValuesList
					.size()];
			directiveValues = directiveValuesList.toArray(directiveValues);

			handleChars(lastPosition, directiveValues[i].getStart());
			directiveValues[i].accept(this);
			lastPosition = directiveValues[i].getEnd();
			isFirst = false;
		}
		if (this.preferences.insert_space_before_closing_paren_in_declare) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		handleAction(lastPosition, declareStatement.getBody(), true);
		return false;
	}

	public boolean visit(DoStatement doStatement) {
		// do-while body
		lineWidth += 2;
		Statement body = doStatement.getBody();
		handleAction(doStatement.getStart() + 2, body, true);
		int lastPosition = body.getEnd();// this variable
		// will be changed
		int doActionEnd = body.getEnd();
		if (preferences.control_statement_insert_newline_before_while_in_do) {
			insertNewLine();
			indent();
		} else {
			lastPosition = setSpaceAfterBlock(doActionEnd);
		}

		String textBetween = ""; //$NON-NLS-1$
		int indexOfWhile = -1;
		try {
			textBetween = document.get(doActionEnd,
					doStatement.getCondition().getStart() - doActionEnd)
					.toLowerCase();
		} catch (BadLocationException e) {
			Logger.logException(e);
			return false;
		}
		indexOfWhile = textBetween.indexOf("while"); //$NON-NLS-1$
		if (indexOfWhile > 0) {
			indexOfWhile += doActionEnd;
			handleChars(lastPosition, indexOfWhile);
			appendToBuffer("while"); //$NON-NLS-1$
			handleChars(indexOfWhile, indexOfWhile);
			lastPosition = indexOfWhile;
		} else {
			appendToBuffer("while"); //$NON-NLS-1$
		}

		// handle the chars between the 'while' and the condition start position
		if (this.preferences.insert_space_before_opening_paren_in_while) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_while) {
			insertSpace();
		}
		handleChars(lastPosition, doStatement.getCondition().getStart());

		// handle the while condition
		doStatement.getCondition().accept(this);

		// set the while closing paren spaces
		if (this.preferences.insert_space_before_closing_paren_in_while) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		handleSemicolon(doStatement.getCondition().getEnd(),
				doStatement.getEnd());
		return false;
	}

	public boolean visit(EchoStatement echoStatement) {
		int lastPosition = echoStatement.getStart() + 4;
		lineWidth += 4;
		insertSpace();

		List<Expression> expressionList = echoStatement.expressions();
		Expression[] expressions = new Expression[expressionList.size()];
		expressions = expressionList.toArray(expressions);

		lastPosition = handleCommaList(expressions, lastPosition,
				this.preferences.insert_space_before_comma_in_echo,
				this.preferences.insert_space_after_comma_in_echo,
				NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, echoStatement.getEnd());
		return false;
	}

	public boolean visit(EmptyStatement emptyStatement) {
		int start = emptyStatement.getStart();
		int end = emptyStatement.getEnd();
		if (isContainChar(start, end, '?')) {
			handlePhpEndTag(start, end, "?>"); //$NON-NLS-1$
		} else if (isContainChar(start, end, '%')) {
			handlePhpEndTag(start, end, "%>"); //$NON-NLS-1$
		} else {
			appendToBuffer(SEMICOLON);
		}
		handleChars(start, end);
		return false;
	}

	public boolean visit(ExpressionStatement expressionStatement) {
		Expression expression = expressionStatement.getExpression();
		expression.accept(this);
		handleSemicolon(expression.getEnd(), expressionStatement.getEnd());
		return false;
	}

	public boolean visit(FieldAccess fieldAccess) {
		fieldAccess.getDispatcher().accept(this);
		if (this.preferences.insert_space_before_arrow_in_field_access) {
			insertSpace();
		}
		appendToBuffer("->"); //$NON-NLS-1$
		if (this.preferences.insert_space_after_arrow_in_field_access) {
			insertSpace();
		}

		// handle the chars between the dispatcher to the property
		handleChars(fieldAccess.getDispatcher().getEnd(), fieldAccess
				.getField().getStart());

		fieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		boolean isFirst = true;
		Variable[] variableNames = fieldsDeclaration.getVariableNames();
		Expression[] initialValues = fieldsDeclaration.getInitialValues();
		int lastPosition = variableNames[0].getStart();

		// handle field modifiers
		String modifier = fieldsDeclaration.getModifierString();
		char firstChar = ' ';
		try {
			firstChar = document.getChar(fieldsDeclaration.getStart());
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		if (modifier.equalsIgnoreCase("public") //$NON-NLS-1$
				&& (firstChar == 'v' || firstChar == 'V')) {
			modifier = "var"; //$NON-NLS-1$
		}

		appendToBuffer(modifier);
		insertSpace();

		handleChars(fieldsDeclaration.getStart(), lastPosition);

		for (int i = 0; i < variableNames.length; i++) {
			// handle comma between variables
			if (!isFirst) {
				if (this.preferences.insert_space_before_comma_in_class_variable) {
					insertSpace();
				}
				appendToBuffer(COMMA);
				if (this.preferences.insert_space_after_comma_in_class_variable) {
					insertSpace();
				}
			}
			handleChars(lastPosition, variableNames[i].getStart());
			variableNames[i].accept(this);
			lastPosition = variableNames[i].getEnd();

			if (initialValues[i] != null) {
				// handle initial assignments
				if (this.preferences.insert_space_before_assignment) {
					insertSpace();
				}
				appendToBuffer(EQUAL);
				if (this.preferences.insert_space_after_assignment) {
					insertSpace();
				}

				handleChars(lastPosition, initialValues[i].getStart());
				initialValues[i].accept(this);
				lastPosition = initialValues[i].getEnd();
			}
			isFirst = false;
		}
		handleSemicolon(lastPosition, fieldsDeclaration.getEnd());
		return false;
	}

	public boolean visit(ForEachStatement forEachStatement) {
		if (this.preferences.insert_space_before_open_paren_in_foreach) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_open_paren_in_foreach) {
			insertSpace();
		}
		lineWidth += 7;
		handleChars(forEachStatement.getStart() + 7, forEachStatement
				.getExpression().getStart());
		// handle [as key => value] or just [as value]
		forEachStatement.getExpression().accept(this);
		appendToBuffer(" as "); //$NON-NLS-1$
		int lastPosition = forEachStatement.getExpression().getEnd();
		if (forEachStatement.getKey() != null) {
			handleChars(forEachStatement.getExpression().getEnd(),
					forEachStatement.getKey().getStart());
			forEachStatement.getKey().accept(this);
			if (this.preferences.insert_space_before_arrow_in_foreach) {
				insertSpace();
			}
			appendToBuffer(KEY_VALUE_OPERATOR);
			if (this.preferences.insert_space_after_arrow_in_foreach) {
				insertSpace();
			}
			lastPosition = forEachStatement.getKey().getEnd();
		}
		handleChars(lastPosition, forEachStatement.getValue().getStart());
		forEachStatement.getValue().accept(this);

		if (this.preferences.insert_space_before_close_paren_in_foreach) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		handleAction(forEachStatement.getValue().getEnd(),
				forEachStatement.getStatement(), true);
		return false;
	}

	public boolean visit(FormalParameter formalParameter) {
		// handle const in PHP4
		int lastPosition = formalParameter.getStart();
		if (formalParameter.isMandatory()) {
			// the word 'const'
			lastPosition += 5;
			lineWidth += 5;
		}

		// handle type
		if (formalParameter.getParameterType() != null) {
			formalParameter.getParameterType().accept(this);
			lastPosition = formalParameter.getParameterType().getEnd();
			insertSpace();
		}
		handleChars(lastPosition, formalParameter.getParameterName().getStart());

		formalParameter.getParameterName().accept(this);
		if (formalParameter.hasDefaultValue()) {
			if (this.preferences.insert_space_before_assignment) {
				insertSpace();
			}
			appendToBuffer(EQUAL);
			if (this.preferences.insert_space_after_assignment) {
				insertSpace();
			}

			// handle the chars between the variable to the value
			handleChars(formalParameter.getParameterName().getEnd(),
					formalParameter.getDefaultValue().getStart());
		}
		return false;
	}

	public boolean visit(ForStatement forStatement) {
		int lastPosition = forStatement.getStart() + 3;
		lineWidth += 3;
		if (this.preferences.insert_space_before_open_paren_in_for) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);

		List<Expression> forExpressions = null;

		// handle initializers
		forExpressions = forStatement.initializers();
		Expression[] initializations = new Expression[forExpressions.size()];
		forExpressions.toArray(initializations);

		// handle conditions
		forExpressions = forStatement.conditions();
		Expression[] conditions = new Expression[forExpressions.size()];
		forExpressions.toArray(conditions);

		// handle updaters/increasements
		forExpressions = forStatement.updaters();
		Expression[] increasements = new Expression[forExpressions.size()];
		forExpressions.toArray(increasements);

		if (this.preferences.insert_space_after_open_paren_in_for
				&& initializations.length > 0) {
			insertSpace();
		}

		lastPosition = handleCommaList(initializations, lastPosition,
				this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP,
				NO_LINE_WRAP_INDENT, false);
		handleForSemicolon(initializations, conditions);
		lastPosition = handleCommaList(conditions, lastPosition,
				this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP,
				NO_LINE_WRAP_INDENT, false);
		handleForSemicolon(conditions, increasements);
		lastPosition = handleCommaList(increasements, lastPosition,
				this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP,
				NO_LINE_WRAP_INDENT, false);

		if (this.preferences.insert_space_before_close_paren_in_for
				&& increasements.length > 0) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		// for body
		handleAction(lastPosition, forStatement.getBody(), true);
		return false;
	}

	public boolean visit(FunctionDeclaration functionDeclaration) {
		isInsideFun = true;
		StringBuffer buffer = new StringBuffer();
		buffer.append(getDocumentString(functionDeclaration.getStart(),
				functionDeclaration.getStart() + 8));// append 'function'

		// handle referenced function with '&'
		if (functionDeclaration.isReference()) {
			buffer.append(" &"); //$NON-NLS-1$
		} else {
			buffer.append(' ');
		}

		buffer.append(functionDeclaration.getFunctionName().getName());

		appendToBuffer(buffer.toString());
		handleChars(functionDeclaration.getStart(), functionDeclaration
				.getFunctionName().getEnd());

		if (this.preferences.insert_space_before_opening_paren_in_function_declaration) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		int lastPosition = functionDeclaration.getFunctionName().getEnd();
		if (functionDeclaration.formalParameters().size() > 0) {
			if (this.preferences.insert_space_after_opening_paren_in_function_declaration) {
				insertSpace();
			}
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_parameters_in_method_declaration_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);

			List<FormalParameter> parameterList = functionDeclaration
					.formalParameters();
			FormalParameter[] parameters = new FormalParameter[parameterList
					.size()];
			parameters = parameterList.toArray(parameters);

			lastPosition = handleCommaList(
					parameters,
					lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_parameters_in_method_declaration_force_split);

			if (this.preferences.insert_space_before_closing_paren_in_function_declaration) {
				insertSpace();
			}
		} else {
			if (this.preferences.insert_space_between_empty_paren_in_function_declaration) {
				insertSpace();
			}
		}
		appendToBuffer(CLOSE_PARN);

		// handle function body
		if (functionDeclaration.getBody() != null) {
			boolean isIndentationAdded = handleBlockOpenBrace(
					this.preferences.brace_position_for_function,
					this.preferences.insert_space_before_opening_brace_in_function);
			handleChars(lastPosition, functionDeclaration.getBody().getStart());

			functionDeclaration.getBody().accept(this);
			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDesending = true;
			}
		} else {
			handleSemicolon(lastPosition, functionDeclaration.getEnd());
		}

		isInsideFun = false;
		return false;
	}

	public boolean visit(FunctionInvocation functionInvocation) {

		// in case of function print there no need for parenthesis
		Expression functionName = functionInvocation.getFunctionName()
				.getName();
		if (functionName.getType() == ASTNode.IDENTIFIER) {
			final String name = ((Identifier) functionName).getName();
			if (FUNCTION_NAME_PRINT.equalsIgnoreCase(name)) {
				handlePrintCall(functionInvocation);
				return false;
			}
		}

		innerVisit(functionInvocation);
		return false;
	}

	private void innerVisit(FunctionInvocation functionInvocation) {
		innerVisit(functionInvocation, true);
	}

	private void innerVisit(FunctionInvocation functionInvocation,
			boolean addParen) {
		Expression functionName = functionInvocation.getFunctionName()
				.getName();
		functionName.accept(this);

		if (this.preferences.insert_space_before_opening_paren_in_function) {
			insertSpace();
		}

		if (addParen) {
			appendToBuffer(OPEN_PARN);
		}

		int lastPosition = functionName.getEnd();
		if (functionInvocation.parameters().size() > 0) {
			if (this.preferences.insert_space_after_opening_paren_in_function) {
				insertSpace();
			}
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_arguments_in_method_invocation_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);

			List<Expression> parametersList = functionInvocation.parameters();
			Expression[] parameters = new Expression[parametersList.size()];
			parameters = parametersList.toArray(parameters);

			// work around. count close bracket now.
			if (addParen) {
				lineWidth++;
			}

			// work around. count space now.
			if (this.preferences.insert_space_before_closing_paren_in_function) {
				lineWidth++;
			}

			lastPosition = handleCommaList(
					parameters,
					lastPosition,
					this.preferences.insert_space_before_comma_in_function,
					this.preferences.insert_space_after_comma_in_function,
					this.preferences.line_wrap_arguments_in_method_invocation_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_arguments_in_method_invocation_force_split);

			if (this.preferences.insert_space_before_closing_paren_in_function) {
				// work around. count space now.
				lineWidth--;
				insertSpace();
			}

			// work around. count close bracket now.
			if (addParen) {
				lineWidth--;
			}

		} else {
			if (this.preferences.insert_space_between_empty_paren_in_function) {
				insertSpace();
			}
		}

		if (addParen) {
			appendToBuffer(CLOSE_PARN);
		}
		if (functionInvocation.getArrayDereferenceList() != null
				&& !functionInvocation.getArrayDereferenceList()
						.getDereferences().isEmpty()) {
			lastPosition = formatDereference(lastPosition,
					functionInvocation.getArrayDereferenceList());
		} else {
			handleChars(lastPosition, functionInvocation.getEnd());
		}

	}

	private int formatDereference(int lastPosition, PHPArrayDereferenceList list) {
		handleChars(lastPosition, list.getDereferences().get(0).getStart());
		lastPosition = list.getDereferences().get(0).getStart();
		for (DereferenceNode dereferenceNode : list.getDereferences()) {

			if (dereferenceNode.getName() instanceof Scalar) {
				appendToBuffer(OPEN_BRACKET);
				// handleChars(lastPosition, dereferenceNode.getStart());
				Scalar scalar = (Scalar) dereferenceNode.getName();
				appendToBuffer(scalar.getStringValue());
				appendToBuffer(CLOSE_BRACKET);
				handleChars(dereferenceNode.getStart(),
						dereferenceNode.getEnd());
			} else {
				appendToBuffer(OPEN_BRACKET);
				handleChars(lastPosition, dereferenceNode.getName().getStart());
				dereferenceNode.getName().accept(this);
				appendToBuffer(CLOSE_BRACKET);
				handleChars(dereferenceNode.getName().getEnd(),
						dereferenceNode.getEnd());
			}

			// handleChars(dereferenceNode.getEnd(),
			// dereferenceNode.getEnd());
			lastPosition = dereferenceNode.getEnd();
		}

		return lastPosition;
	}

	private void handlePrintCall(FunctionInvocation functionInvocation) {

		List<Expression> parametersList = functionInvocation.parameters();
		Expression[] parameters = new Expression[parametersList.size()];
		parameters = parametersList.toArray(parameters);

		boolean hasParenthsis = parameters[0].getType() == ASTNode.PARENTHESIS_EXPRESSION; // print
		// always
		// have
		// one
		// parameter.

		if (hasParenthsis) {
			innerVisit(functionInvocation, false);
			return;
		}

		insertSpace();

		Expression functionName = functionInvocation.getFunctionName()
				.getName();

		int lastPosition = functionName.getEnd();

		int indentationGap = calculateIndentGap(
				this.preferences.line_wrap_arguments_in_method_invocation_indent_policy,
				this.preferences.line_wrap_wrapped_lines_indentation);

		lastPosition = handleCommaList(
				parameters,
				lastPosition,
				this.preferences.insert_space_before_comma_in_function,
				this.preferences.insert_space_after_comma_in_function,
				this.preferences.line_wrap_arguments_in_method_invocation_line_wrap_policy,
				indentationGap,
				this.preferences.line_wrap_arguments_in_method_invocation_force_split);

		handleChars(lastPosition, functionInvocation.getEnd());
	}

	public boolean visit(FunctionName functionName) {
		return true;
	}

	public boolean visit(GlobalStatement globalStatement) {
		int lastPosition = globalStatement.getStart() + 6;
		lineWidth += 6;// the word 'global'
		insertSpace();

		List<Variable> varList = globalStatement.variables();
		Expression[] variables = new Expression[varList.size()];
		variables = varList.toArray(variables);

		lastPosition = handleCommaList(variables, lastPosition,
				this.preferences.insert_space_before_comma_in_global,
				this.preferences.insert_space_after_comma_in_global,
				NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, globalStatement.getEnd());
		return false;
	}

	public boolean visit(Identifier identifier) {
		lineWidth += identifier.getLength();
		return false;
	}

	public boolean visit(IfStatement ifStatement) {
		// check if the token is 'if' or 'elseif'
		int len = checkFirstTokenLength(ifStatement.getStart(), ifStatement
				.getCondition().getStart());

		// handle the chars between the 'while' and the condition start position
		if (this.preferences.insert_space_before_opening_paren_in_if) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_if) {
			insertSpace();
		}
		lineWidth += len; // add the word 'if' OR 'elseif'
		handleChars(ifStatement.getStart() + len, ifStatement.getCondition()
				.getStart());

		// handle the if condition
		ifStatement.getCondition().accept(this);

		if (wasBinaryExpressionWrapped) {
			indentationLevel -= binaryExpressionIndentGap;
			wasBinaryExpressionWrapped = false;
		}
		// handle the chars between the condition end position and action start
		// position

		// set the while closing paren spaces
		if (this.preferences.insert_space_before_closing_paren_in_if) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		// action
		int lastPosition = ifStatement.getCondition().getEnd();
		boolean addNewlineBeforeAction = true;
		if (ifStatement.getTrueStatement().getType() != ASTNode.BLOCK) {
			if (len == 2) {// if
				addNewlineBeforeAction = !(preferences.control_statement_keep_then_on_same_line || (preferences.control_statement_keep_simple_if_on_one_line && ifStatement
						.getFalseStatement() == null));
			} else if (len == 6) {// elseif
				addNewlineBeforeAction = !preferences.control_statement_keep_then_on_same_line;
			}
		}
		handleAction(lastPosition, ifStatement.getTrueStatement(),
				addNewlineBeforeAction);
		lastPosition = ifStatement.getTrueStatement().getEnd();
		if (ifStatement.getFalseStatement() == null
				|| ifStatement.getFalseStatement().getType() == ASTNode.AST_ERROR) {
			return false;
		}

		if (ifStatement.getFalseStatement().getType() == ASTNode.IF_STATEMENT) {
			try {
				handleElseIfCases(ifStatement);
			} catch (BadLocationException ble) {
				Logger.logException(ble);
				return false;
			}
		} else { // the false statement is not 'elseif' or 'else if'
			if (ifStatement.getTrueStatement().getType() == ASTNode.BLOCK) {
				if (preferences.control_statement_insert_newline_before_else_and_elseif_in_if) {
					insertNewLine();
					indent();
				} else {
					lastPosition = setSpaceAfterBlock(ifStatement
							.getTrueStatement().getEnd());
				}

				try {
					lastPosition = internalHandleElse(ifStatement, lastPosition);
				} catch (BadLocationException ble) {
					Logger.logException(ble);
					return false;
				}
				handleAction(lastPosition, ifStatement.getFalseStatement(),
						true);
				boolean processed = isProcessed(ifStatement);
				if (!((Block) ifStatement.getTrueStatement()).isCurly()
						&& !processed) {
					handleChars(ifStatement.getFalseStatement().getEnd(),
							ifStatement.getEnd());
					appendToBuffer("endif;"); //$NON-NLS-1$
					handleChars(ifStatement.getEnd(), ifStatement.getEnd());
				}
			} else { // if the true statement is not a block then we should add
				// new line
				insertNewLine();
				indent();

				try {
					lastPosition = internalHandleElse(ifStatement, lastPosition);
				} catch (BadLocationException ble) {
					Logger.logException(ble);
					return false;
				}

				boolean elseActionInSameLine = preferences.control_statement_keep_else_on_same_line;
				handleAction(lastPosition, ifStatement.getFalseStatement(),
						!elseActionInSameLine);
			}
		}
		return false;
	}

	private boolean isProcessed(IfStatement ifStatement) {
		boolean precessed = true;
		if (!processedIfStatements.contains(ifStatement)) {
			addAllIfStatements(ifStatement);
			precessed = false;
		}
		return precessed;
	}

	private void handleElseIfCases(IfStatement ifStatement)
			throws BadLocationException {
		int lastPosition = ifStatement.getTrueStatement().getEnd();
		int len;
		IfStatement falseIfStatement = (IfStatement) ifStatement
				.getFalseStatement();
		len = checkFirstTokenLength(falseIfStatement.getStart(),
				falseIfStatement.getCondition().getStart());
		boolean elseIndentationLevelChanged = false;

		// information needed to handleChars between if statement end to the
		// else if...
		String textBetween = ""; //$NON-NLS-1$
		int trueStatementEnd = ifStatement.getTrueStatement().getEnd();
		int indexOfElse = -1;
		textBetween = document.get(trueStatementEnd,
				ifStatement.getFalseStatement().getStart() - trueStatementEnd)
				.toLowerCase();
		indexOfElse = textBetween.lastIndexOf("else"); //$NON-NLS-1$

		if (ifStatement.getTrueStatement().getType() == ASTNode.BLOCK) {
			if (preferences.control_statement_insert_newline_before_else_and_elseif_in_if) {
				insertNewLine();
				indent();
			} else {
				if (len == 2) {
					lastPosition = setSpaceAfterBlock(ifStatement
							.getTrueStatement().getEnd());
				}
			}

			if (len != 2) {// elseif case
				if (indexOfElse > 0) {
					handleChars(lastPosition, ifStatement.getFalseStatement()
							.getStart());
				} else {
					// fix for setSpaceAfterBlock when no space is required
					// before 'elseif'
					if (!preferences.control_statement_insert_newline_before_else_and_elseif_in_if
							&& preferences.insert_space_after_closing_brace_in_block) {
						insertSpace();
					}
					handleChars(ifStatement.getTrueStatement().getEnd(),
							ifStatement.getFalseStatement().getStart());
				}
			} else {
				if (indexOfElse > 0) {
					indexOfElse += trueStatementEnd;
					handleChars(lastPosition, indexOfElse);
					appendToBuffer("else "); //$NON-NLS-1$
					if (!preferences.control_statement_keep_else_if_on_same_line) {
						insertNewLine();
						indentationLevel++;
						elseIndentationLevelChanged = true;
						indent();
					}
					handleChars(indexOfElse, ifStatement.getFalseStatement()
							.getStart());
					lastPosition = indexOfElse;
				} else {
					appendToBuffer("else "); //$NON-NLS-1$
					if (!preferences.control_statement_keep_else_if_on_same_line) {
						insertNewLine();
						indentationLevel++;
						elseIndentationLevelChanged = true;
						indent();
					}
					// the following line handles the case : '}else' when
					// setSpaceAfterBlock() is called and offset is set to +1
					indexOfElse = (trueStatementEnd < lastPosition) ? lastPosition
							: indexOfElse + trueStatementEnd;

					handleChars(indexOfElse, ifStatement.getFalseStatement()
							.getStart());
				}
			}
		} else { // if the true statement is not a block then we should add new
			// line
			insertNewLine();
			indent();
			if (indexOfElse > 0) {
				indexOfElse += trueStatementEnd;
				handleChars(lastPosition, indexOfElse);
				appendToBuffer("else "); //$NON-NLS-1$
				if (!preferences.control_statement_keep_else_if_on_same_line) {
					insertNewLine();
					indentationLevel++;
					elseIndentationLevelChanged = true;
					indent();
				}
				handleChars(indexOfElse, ifStatement.getFalseStatement()
						.getStart());
				lastPosition = indexOfElse;
			} else {
				appendToBuffer(len == 2 ? "else " : EMPTY_STRING); //$NON-NLS-1$
				if ((len == 2)
						&& !preferences.control_statement_keep_else_if_on_same_line) {
					insertNewLine();
					indentationLevel++;
					elseIndentationLevelChanged = true;
					indent();
				}
				if (indexOfElse == -1) {// in case of: STATEMENT;elseif ...
					indexOfElse = 0;
				}

				handleChars(indexOfElse + trueStatementEnd, ifStatement
						.getFalseStatement().getStart());
			}
		}
		boolean processed = isProcessed(ifStatement);
		ifStatement.getFalseStatement().accept(this);
		if (elseIndentationLevelChanged) {
			indentationLevel--;
		}
		if (ifStatement.getTrueStatement().getType() == ASTNode.BLOCK
				&& !((Block) ifStatement.getTrueStatement()).isCurly()
				&& !processed) {
			handleChars(ifStatement.getFalseStatement().getEnd(),
					ifStatement.getEnd());
			appendToBuffer("endif;"); //$NON-NLS-1$
			handleChars(ifStatement.getEnd(), ifStatement.getEnd());
		}
	}

	private void addAllIfStatements(IfStatement ifStatement) {
		processedIfStatements.add(ifStatement);
		Statement falseIfStatement;
		while ((falseIfStatement = ifStatement.getFalseStatement()) instanceof IfStatement) {
			ifStatement = (IfStatement) falseIfStatement;
			processedIfStatements.add(ifStatement);
		}

	}

	// this will perform handleChars() between the statement's end AND the
	// 'else'
	private int internalHandleElse(IfStatement ifStatement, int lastPosition)
			throws BadLocationException {
		String textBetween = ""; //$NON-NLS-1$
		int trueStatementEnd = ifStatement.getTrueStatement().getEnd();
		int indexOfElse = -1;
		textBetween = document.get(trueStatementEnd,
				ifStatement.getFalseStatement().getStart() - trueStatementEnd)
				.toLowerCase();
		indexOfElse = textBetween.lastIndexOf("else"); //$NON-NLS-1$
		if (indexOfElse > 0) {
			indexOfElse += trueStatementEnd;
			handleChars(lastPosition, indexOfElse);
			appendToBuffer("else"); //$NON-NLS-1$
			handleChars(indexOfElse, indexOfElse);
			lastPosition = indexOfElse;
		} else {
			appendToBuffer("else"); //$NON-NLS-1$
		}
		return lastPosition;
	}

	public boolean visit(IgnoreError ignoreError) {
		lineWidth++;// the '@' sign
		ignoreError.getExpression().accept(this);
		return false;
	}

	public boolean visit(Include include) {
		int lastPosition = include.getStart();
		int len = (include.getIncludeType() == Include.IT_INCLUDE || include
				.getIncludeType() == Include.IT_REQUIRE) ? 7 : 12;
		lastPosition += len;
		lineWidth += len;// add 'include' 'require' 'require_once'

		insertSpace();
		handleChars(lastPosition, include.getExpression().getStart());

		include.getExpression().accept(this);
		return false;
	}

	public boolean visit(InfixExpression infixExpression) {
		boolean forceSplit = this.preferences.line_wrap_binary_expression_force_split;
		if (binaryExpressionLineWrapPolicy == -1) {// not initialized
			binaryExpressionLineWrapPolicy = this.preferences.line_wrap_binary_expression_line_wrap_policy;
			binaryExpressionIndentGap = calculateIndentGap(
					this.preferences.line_wrap_binary_expression_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);

		}

		if (binaryExpressionSavedBuffer == null) {
			binaryExpressionSavedBuffer = replaceBuffer.toString();
			binaryExpressionSavedNode = infixExpression;
			binaryExpressionSavedChangesIndex = changes.size() - 1;
			binaryExpressionRevertPolicy = -1;
		}

		infixExpression.getLeft().accept(this);
		int operator = infixExpression.getOperator();
		boolean isStringOperator = ((operator == InfixExpression.OP_STRING_AND)
				|| (operator == InfixExpression.OP_STRING_OR) || (operator == InfixExpression.OP_STRING_XOR));

		if (isStringOperator
				|| this.preferences.insert_space_before_binary_operation) {
			insertSpace();
		}
		appendToBuffer(InfixExpression.getOperator(operator));

		// Need consider the right expression at first,because the right
		// expression might be
		// long enough to expend the line width.
		// This should cause the line wrap.
		int lineW = calcLinesWidth(infixExpression.getRight());

		switch (binaryExpressionLineWrapPolicy) {
		case NO_LINE_WRAP:
			// no_wrap
			break;
		case FIRST_WRAP_WHEN_NECESSARY:
			if (lineW > this.preferences.line_wrap_line_split) {
				wasBinaryExpressionWrapped = indentationLevel == 1;
				binaryExpressionLineWrapPolicy = WRAP_WHEN_NECESSARY;
				insertNewLine();
				indentationLevel += binaryExpressionIndentGap;
				wasBinaryExpressionWrapped = true;
				indent();
			}
			break;
		case WRAP_WHEN_NECESSARY:
			if (lineW > this.preferences.line_wrap_line_split) {
				wasBinaryExpressionWrapped = true;
				insertNewLine();
				indent();
			}
			break;
		case WRAP_FIRST_ELEMENT:
			if (forceSplit || lineW > this.preferences.line_wrap_line_split) {
				if (binaryExpressionRevertPolicy != -1) {
					binaryExpressionRevertPolicy = -1;
					binaryExpressionLineWrapPolicy = WRAP_WHEN_NECESSARY;
					insertNewLine();
					indentationLevel += binaryExpressionIndentGap;
					indent();
				} else {
					binaryExpressionRevertPolicy = WRAP_FIRST_ELEMENT;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
				wasBinaryExpressionWrapped = true;
			}
			break;
		case WRAP_ALL_ELEMENTS:
			if (forceSplit || lineW > this.preferences.line_wrap_line_split) {
				if (binaryExpressionRevertPolicy != -1) {
					binaryExpressionRevertPolicy = -1;
					binaryExpressionLineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					indentationLevel += binaryExpressionIndentGap;
					indent();
				} else {
					binaryExpressionRevertPolicy = WRAP_ALL_ELEMENTS;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
				wasBinaryExpressionWrapped = true;
			}
			break;
		case WRAP_ALL_ELEMENTS_NO_INDENT_FIRST:
			if (forceSplit || lineW > this.preferences.line_wrap_line_split) {
				if (binaryExpressionRevertPolicy != -1) {
					binaryExpressionRevertPolicy = -1;
					binaryExpressionLineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					indentationLevel += binaryExpressionIndentGap;
					indent();

					// increase the indentation level after the first element
					indentationLevel++;
					isBinaryExpressionExtraIndentation = true;
				} else {
					binaryExpressionRevertPolicy = WRAP_ALL_ELEMENTS_NO_INDENT_FIRST;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
				wasBinaryExpressionWrapped = true;
			}
			break;
		case WRAP_ALL_ELEMENTS_EXCEPT_FIRST:
			if (forceSplit || lineW > this.preferences.line_wrap_line_split) {
				if (binaryExpressionRevertPolicy != -1) {
					binaryExpressionLineWrapPolicy = WRAP_ALL_ELEMENTS;
				} else {
					binaryExpressionRevertPolicy = WRAP_ALL_ELEMENTS_EXCEPT_FIRST;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
				wasBinaryExpressionWrapped = true;
			}
			break;
		case ALWAYS_WRAP_ELEMENT:
			insertNewLine();
			indent();
			break;
		}

		if (isStringOperator
				|| this.preferences.insert_space_after_binary_operation) {
			insertSpace();
		}

		// handle the chars between the variable to the value
		handleChars(infixExpression.getLeft().getEnd(), infixExpression
				.getRight().getStart());

		if (binaryExpressionRevertPolicy != -1
				&& infixExpression == binaryExpressionSavedNode) {
			if (binaryExpressionLineWrapPolicy == WRAP_ALL_ELEMENTS
					&& binaryExpressionRevertPolicy == WRAP_ALL_ELEMENTS_EXCEPT_FIRST) {
				infixExpression.getRight().accept(this);
			} else {
				revert(binaryExpressionSavedBuffer,
						binaryExpressionSavedChangesIndex);
				binaryExpressionLineWrapPolicy = binaryExpressionRevertPolicy;
				infixExpression.accept(this);
			}
		} else {
			infixExpression.getRight().accept(this);
		}
		return false;
	}

	private int calcLinesWidth(ASTNode node) {
		int lineW = lineWidth;
		try {
			int lineForStart = document.getLineOfOffset(node.getStart());
			int lineForEnd = document.getLineOfOffset(node.getEnd());

			if (lineForStart == lineForEnd) {
				lineW += node.getLength();
			} else {
				lineW = document.getLineLength(lineForEnd);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		return lineW;
	}

	public boolean visit(InLineHtml inLineHtml) {
		calcLinesAndWidth(inLineHtml);
		return false;
	}

	public boolean visit(InstanceOfExpression instanceOfExpression) {
		instanceOfExpression.getExpression().accept(this);
		if (this.preferences.insert_space_before_binary_operation) {
			insertSpace();
		}
		appendToBuffer("instanceof"); //$NON-NLS-1$
		if (this.preferences.insert_space_after_binary_operation) {
			insertSpace();
		}

		// handle the chars between the variable to the value
		handleChars(instanceOfExpression.getExpression().getEnd(),
				instanceOfExpression.getClassName().getStart());

		instanceOfExpression.getClassName().accept(this);

		return false;
	}

	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		insertSpace();
		lineWidth += 9;// interface
		handleChars(interfaceDeclaration.getStart() + 9, interfaceDeclaration
				.getName().getStart());
		interfaceDeclaration.getName().accept(this);

		int lastPosition = interfaceDeclaration.getName().getEnd();

		List<Identifier> interfaceList = interfaceDeclaration.interfaces();
		Identifier[] interfaces = new Identifier[interfaceList.size()];
		interfaces = interfaceList.toArray(interfaces);

		if (interfaces.length > 0) {
			appendToBuffer(" extends "); //$NON-NLS-1$
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_superinterfaces_in_type_declaration_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);
			lastPosition = handleCommaList(
					interfaces,
					lastPosition,
					this.preferences.insert_space_before_comma_in_implements,
					this.preferences.insert_space_after_comma_in_implements,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_force_split);
		}

		boolean isIndentationAdded = handleBlockOpenBrace(
				this.preferences.brace_position_for_class,
				this.preferences.insert_space_before_opening_brace_in_class);
		handleChars(lastPosition, interfaceDeclaration.getBody().getStart());

		interfaceDeclaration.getBody().accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
		return false;
	}

	public boolean visit(ListVariable listVariable) {
		if (this.preferences.insert_space_before_opening_paren_in_list) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_list) {
			insertSpace();
		}

		int lastPosition = listVariable.getStart() + 4;
		lineWidth += 4;
		List<VariableBase> variables = listVariable.variables();
		VariableBase[] variablesArray = variables
				.toArray(new VariableBase[variables.size()]);
		lastPosition = handleCommaList(variablesArray, lastPosition,
				this.preferences.insert_space_before_comma_in_list,
				this.preferences.insert_space_after_comma_in_list,
				NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		if (this.preferences.insert_space_before_closing_paren_in_list) {
			if (variablesArray[variablesArray.length - 1].getLength() == 0
					&& this.preferences.insert_space_after_comma_in_list) {
				// in the following case list($a,) we don't want the second
				// empty parameter to have two spaces. (like this: list( $a, ))
			} else {
				insertSpace();
			}
		}
		appendToBuffer(CLOSE_PARN);
		handleChars(lastPosition, listVariable.getEnd());

		return false;
	}

	public boolean visit(MethodDeclaration classMethodDeclaration) {
		// handle method modifiers
		String originalModifier = getDocumentString(
				classMethodDeclaration.getStart(),
				classMethodDeclaration.getFunction().getStart()).trim();
		StringTokenizer tokenizer = new StringTokenizer(originalModifier);
		StringBuffer strBuffer = new StringBuffer();
		while (tokenizer.hasMoreTokens()) {
			strBuffer.append(tokenizer.nextToken() + " "); //$NON-NLS-1$
		}
		int len;
		String formattedModifier = ""; //$NON-NLS-1$
		if ((len = strBuffer.length()) > 0) { /* trim trailing space */
			formattedModifier = strBuffer.toString().substring(0, len - 1);
		}

		appendToBuffer(formattedModifier);
		if (formattedModifier.length() > 0) {
			insertSpace();
		}
		handleChars(classMethodDeclaration.getStart(), classMethodDeclaration
				.getFunction().getStart());
		classMethodDeclaration.getFunction().accept(this);
		return false;
	}

	public boolean visit(MethodInvocation methodInvocation) {
		VariableBase dispatch = methodInvocation.getDispatcher();

		// 0029458: [Ticket 188445][Roman][Feature] Method chaining in Code
		// Formatter
		// Track the chain level here.
		if (this.preferences.new_line_in_second_invoke > 0) {
			if (chainStack.isEmpty() || chainStack.peek() == -1) {
				chainStack.push(1);
			} else {
				int value = chainStack.pop();
				chainStack.push(++value);
			}

			if (!(dispatch instanceof MethodInvocation)) {
				chainStack.push(-1);
			}
		}

		dispatch.accept(this);

		if (this.preferences.insert_space_before_arrow_in_method_invocation) {
			insertSpace();
		}

		if (this.preferences.new_line_in_second_invoke > 0) {
			// 0029458: [Ticket 188445][Roman][Feature] Method chaining in Code
			// Formatter
			// calculate the peek chain level.
			if (peek == null) {
				peek = -1;
			}
			if (chainStack.peek() == -1) {
				chainStack.pop();
			}

			peek = peek > chainStack.peek() ? peek : chainStack.peek();

			// if peek chain level bigger than the preference, wrap them
			if (peek > this.preferences.new_line_in_second_invoke - 1) {
				if (dispatch instanceof MethodInvocation) {

					insertNewLine();

					indentationLevel++;
					indent();
					indentationLevel--;
				}
			}
		}

		appendToBuffer("->"); //$NON-NLS-1$
		if (this.preferences.insert_space_after_arrow_in_method_invocation) {
			insertSpace();
		}

		if (this.preferences.insert_space_after_arrow_in_method_invocation) {
			insertSpace();
		}

		// handle the chars between the dispatcher to the property
		handleChars(methodInvocation.getDispatcher().getEnd(), methodInvocation
				.getMethod().getStart());

		methodInvocation.getMethod().accept(this);

		// 0029458: [Ticket 188445][Roman][Feature] Method chaining in Code
		// Formatter
		// Track the chain level.
		if (this.preferences.new_line_in_second_invoke > 0) {
			if (!chainStack.isEmpty()) {
				int value = chainStack.pop();
				value -= 1;
				if (value > 0) {
					chainStack.push(value);
				} else {
					if (!chainStack.isEmpty()) {
						peek = chainStack.peek();
					} else {
						peek = -1;
					}
				}
			}
		}
		return false;
	}

	public boolean visit(ParenthesisExpression parenthesisExpression) {
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_open_paren_in_parenthesis_expression) {
			insertSpace();
		}
		int lastPosition = parenthesisExpression.getStart();
		Expression expression = parenthesisExpression.getExpression();
		if (expression != null) {
			// till the expression
			handleChars(lastPosition, expression.getStart());
			expression.accept(this);
			lastPosition = expression.getEnd();
		}
		if (this.preferences.insert_space_before_close_paren_in_parenthesis_expression) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);
		handleChars(lastPosition, parenthesisExpression.getEnd());
		return false;
	}

	public boolean visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		if (this.preferences.insert_space_before_postfix_expression) {
			insertSpace();
		}
		appendToBuffer(PostfixExpression.getOperator(postfixExpressions
				.getOperator()));
		if (this.preferences.insert_space_before_prefix_expression) {
			insertSpace();
		}
		// handle the chars between the variable to the value
		handleChars(postfixExpressions.getVariable().getEnd(),
				postfixExpressions.getEnd());

		return false;
	}

	public boolean visit(PrefixExpression prefixExpression) {
		if (this.preferences.insert_space_before_prefix_expression) {
			insertSpace();
		}
		appendToBuffer(PostfixExpression.getOperator(prefixExpression
				.getOperator()));
		if (this.preferences.insert_space_after_prefix_expression) {
			insertSpace();
		}
		// handle the chars between the variable to the value
		handleChars(prefixExpression.getStart(), prefixExpression.getVariable()
				.getStart());
		prefixExpression.getVariable().accept(this);
		return false;
	}

	public boolean visit(Program program) {
		int lastStatementEndOffset = 0;
		boolean isPhpMode = false;
		List<Statement> statementList = program.statements();
		Statement[] statements = new Statement[statementList.size()];
		statements = statementList.toArray(statements);
		// FIXME if the php file only contains comments,the comments will not be
		// formatted
		// if (statements.length == 0 && !program.comments().isEmpty()) {
		// try {
		// Comment comment = program.comments().get(
		// program.comments().size() - 1);
		// boolean hasComments = hasComments(program.getStart(),
		// comment.getEnd());
		// if (hasComments) {
		// // handle the comments
		// handleComments(program.getStart(), program.getEnd(),
		// astLexer.getCommentList());
		// } else {
		// }
		// } catch (Exception e) {
		// }
		//
		// }

		for (int i = 0; i < statements.length; i++) {
			boolean isHtmlStatement = statements[i].getType() == ASTNode.IN_LINE_HTML;
			boolean isASTError = statements[i].getType() == ASTNode.AST_ERROR;
			// fixed bug 0015682
			// in case of previous statement is an error there is no need for
			// new lines
			// because the lastStatementEndOffset position move to the current
			// statement start position
			boolean isStatementAfterError = i > 0 ? statements[i - 1].getType() == ASTNode.AST_ERROR
					: false;
			if (isASTError && i + 1 < statements.length) {
				// move the lastStatementEndOffset position to the start of the
				// next statement start position
				lastStatementEndOffset = statements[i + 1].getStart();
			} else {
				if (isPhpMode && !isHtmlStatement) {

					// PHP -> PHP
					if (lastStatementEndOffset > 0) {
						if (!isStatementAfterError
								&& getPhpStartTag(lastStatementEndOffset) != -1) {
							insertNewLine();
						}
						insertNewLines(statements[i]);
						indent();
						if (lastStatementEndOffset <= statements[i].getStart()) {
							handleChars(lastStatementEndOffset,
									statements[i].getStart());
						}
					}
				} else if (isPhpMode && isHtmlStatement) {
					// PHP -> HTML
					if (lastStatementEndOffset > 0) {
						if (lastStatementEndOffset <= statements[i].getStart()) {
							handleChars(lastStatementEndOffset,
									statements[i].getStart());
						}
					}
					isPhpMode = false;
				} else if (!isPhpMode && !isHtmlStatement) {
					// HTML -> PHP
					if (!isStatementAfterError) {
						isPhpEqualTag = getPhpStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
						indentationLevel = getPhpTagIndentationLevel(lastStatementEndOffset);
						insertNewLines(statements[i]);
					}
					indent();
					if (lastStatementEndOffset <= statements[i].getStart()) {
						handleChars(lastStatementEndOffset,
								statements[i].getStart());
					}
					isPhpMode = true;
				} else {
					// first HTML
					isPhpMode = false;
				}
				statements[i].accept(this);
				lastStatementEndOffset = statements[i].getEnd();
				// need check how many new lines will the next statement
				// insert
				if (i + 1 < statements.length
						&& statements[i].getType() == ASTNode.NAMESPACE
						&& statements[i + 1].getType() == ASTNode.NAMESPACE) {
					int numberOfLines = getNumbreOfLines(statements[i + 1]) - 1;
					numberOfLines = this.preferences.blank_lines_between_namespaces
							- numberOfLines;
					if (numberOfLines > 0) {
						for (int j = 0; j < numberOfLines; j++) {
							insertNewLine();
						}
					}
					// ignoreEmptyLineSetting = true;
					ignoreEmptyLineSetting = !preferences.indent_empty_lines;
				}
			}
		}
		return false;
	}

	public boolean visit(Quote quote) {
		calcLinesAndWidth(quote);
		if (quote.getQuoteType() == Quote.QT_HEREDOC) {
			int i = quote.getEnd();
			if (isContainChar(i, i + 1, SEMICOLON)) {
				isHeredocSemicolon = true;
			}
		}
		return false;
	}

	public boolean visit(Reference reference) {
		lineWidth++;// &$a
		reference.getExpression().accept(this);
		return false;
	}

	public boolean visit(ReflectionVariable reflectionVariable) {
		lineWidth++;// $$a
		reflectionVariable.getName().accept(this);
		return false;
	}

	public boolean visit(ReturnStatement returnStatement) {
		int lastPosition = returnStatement.getStart() + 6;
		lineWidth += 6;

		Expression expression = returnStatement.getExpression();
		if (expression != null) {
			insertSpace();
			handleChars(lastPosition, expression.getStart());
			expression.accept(this);
			lastPosition = expression.getEnd();
		}

		handleSemicolon(lastPosition, returnStatement.getEnd());
		return false;
	}

	public boolean visit(YieldExpression yieldExpression) {
		lineWidth += 5;
		// handle [key => expr] or just [expr]
		int lastPosition = yieldExpression.getStart() + 5;
		insertSpace();
		if (yieldExpression.getKey() != null) {
			handleChars(lastPosition, yieldExpression.getKey().getStart());
			yieldExpression.getKey().accept(this);
			if (this.preferences.insert_space_before_arrow_in_yield) {
				insertSpace();
			}
			appendToBuffer(KEY_VALUE_OPERATOR);
			if (this.preferences.insert_space_after_arrow_in_yield) {
				insertSpace();
			}
			lastPosition = yieldExpression.getKey().getEnd();
		}
		handleChars(lastPosition, yieldExpression.getExpression().getStart());
		yieldExpression.getExpression().accept(this);
		lastPosition = yieldExpression.getExpression().getEnd();

		return false;
	}

	public boolean visit(Scalar scalar) {
		calcLinesAndWidth(scalar);
		return false;
	}

	public boolean visit(StaticConstantAccess staticConstantAccess) {
		staticConstantAccess.getClassName().accept(this);
		if (this.preferences.insert_space_before_coloncolon_in_field_access) {
			insertSpace();
		}
		appendToBuffer(COLON);
		appendToBuffer(COLON);
		if (this.preferences.insert_space_after_coloncolon_in_field_access) {
			insertSpace();
		}
		handleChars(staticConstantAccess.getClassName().getEnd(),
				staticConstantAccess.getConstant().getStart());
		staticConstantAccess.getConstant().accept(this);
		return false;
	}

	public boolean visit(StaticFieldAccess staticFieldAccess) {
		staticFieldAccess.getClassName().accept(this);
		if (this.preferences.insert_space_before_coloncolon_in_field_access) {
			insertSpace();
		}
		appendToBuffer(COLON);
		appendToBuffer(COLON);
		if (this.preferences.insert_space_after_coloncolon_in_field_access) {
			insertSpace();
		}
		handleChars(staticFieldAccess.getClassName().getEnd(),
				staticFieldAccess.getField().getStart());
		staticFieldAccess.getField().accept(this);
		return false;
	}

	public boolean visit(StaticMethodInvocation staticMethodInvocation) {
		staticMethodInvocation.getClassName().accept(this);
		if (this.preferences.insert_space_before_coloncolon_in_method_invocation) {
			insertSpace();
		}
		appendToBuffer(COLON);
		appendToBuffer(COLON);
		if (this.preferences.insert_space_after_coloncolon_in_method_invocation) {
			insertSpace();
		}
		handleChars(staticMethodInvocation.getClassName().getEnd(),
				staticMethodInvocation.getMethod().getStart());
		staticMethodInvocation.getMethod().accept(this);
		return false;
	}

	public boolean visit(StaticStatement staticStatement) {
		int lastPosition = staticStatement.getStart() + 6;
		lineWidth += 6;
		insertSpace();

		List<Expression> expList = staticStatement.expressions();
		Expression[] expressions = new Expression[expList.size()];
		expressions = expList.toArray(expressions);

		lastPosition = handleCommaList(expressions, lastPosition,
				this.preferences.insert_space_before_comma_in_static,
				this.preferences.insert_space_after_comma_in_static,
				NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, staticStatement.getEnd());
		return false;
	}

	public boolean visit(SwitchCase switchCase) {
		// handle the chars between the 'case'/'default' and the condition start
		// position/ first statement
		int lastStatementEndOffset = 0;
		if (switchCase.isDefault()) {
			if (this.preferences.insert_space_after_switch_default) {
				insertSpace();
			}
			lastStatementEndOffset = switchCase.getStart() + 7;
			lineWidth += 7;// the word 'default'
		} else {
			insertSpace();
			lineWidth += 4;// the word 'case'
			handleChars(switchCase.getStart() + 4, switchCase.getValue()
					.getStart());
			switchCase.getValue().accept(this);
			if (this.preferences.insert_space_after_switch_case_value) {
				insertSpace();
			}
			lastStatementEndOffset = switchCase.getValue().getEnd();

		}
		appendToBuffer(COLON);

		int regularStatementIndentation = 0;
		int breakStatementIndentation = 0;
		if (this.preferences.indent_statements_within_case) {
			regularStatementIndentation++;
		}
		if (this.preferences.indent_break_statements_within_case) {
			breakStatementIndentation++;
		}
		Statement[] actions = new Statement[switchCase.actions().size()];
		switchCase.actions().toArray(actions);
		if (actions.length == 0) {
			handleChars(lastStatementEndOffset, switchCase.getEnd());
		} else {
			for (int i = 0; i < actions.length; i++) {
				if (actions[i].getType() == ASTNode.IN_LINE_HTML) {
					handleChars(lastStatementEndOffset, lastStatementEndOffset);
					lastStatementEndOffset = actions[i].getEnd();
					continue;
				}
				boolean isBreakStatement = actions[i].getType() == ASTNode.BREAK_STATEMENT;
				this.indentationLevel += isBreakStatement ? breakStatementIndentation
						: regularStatementIndentation;
				insertNewLine();
				indent();
				handleChars(lastStatementEndOffset, actions[i].getStart());
				actions[i].accept(this);
				lastStatementEndOffset = actions[i].getEnd();
				this.indentationLevel -= isBreakStatement ? breakStatementIndentation
						: regularStatementIndentation;
			}
		}
		return false;
	}

	public boolean visit(SwitchStatement switchStatement) {
		// handle the chars between the 'switch' and the expr start position
		if (this.preferences.insert_space_before_opening_paren_in_switch) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_switch) {
			insertSpace();
		}
		lineWidth += 6;
		Expression expression = switchStatement.getExpression();
		handleChars(switchStatement.getStart() + 6, expression.getStart());

		// handle the switch expr
		expression.accept(this);

		// handle the chars between the expression end position and action start
		// position

		// set the switch closing parn spaces
		if (this.preferences.insert_space_before_closing_paren_in_switch) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		// switch body
		boolean isIndentationAdded = false;
		isIndentationAdded = handleBlockOpenBrace(
				this.preferences.brace_position_for_switch,
				this.preferences.insert_space_before_opening_brace_in_switch);
		Block body = switchStatement.getBody();
		handleChars(expression.getEnd(), body.getStart());

		body.accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}
		return false;
	}

	public boolean visit(ThrowStatement throwStatement) {
		insertSpace();
		lineWidth += 5;
		Expression expr = throwStatement.getExpression();
		handleChars(throwStatement.getStart() + 5, expr.getStart());

		expr.accept(this);

		handleSemicolon(expr.getEnd(), throwStatement.getEnd());

		return false;
	}

	public boolean visit(TryStatement tryStatement) {
		boolean isIndentationAdded = handleBlockOpenBrace(
				this.preferences.brace_position_for_block,
				this.preferences.insert_space_before_opening_brace_in_block);
		lineWidth += 3;
		Block body = tryStatement.getBody();
		handleChars(tryStatement.getStart() + 3, body.getStart());
		body.accept(this);
		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDesending = true;
		}

		int lastStatementEndOffset = body.getEnd();
		List<CatchClause> clausesList = tryStatement.catchClauses();
		CatchClause[] catchClauses = new CatchClause[clausesList.size()];
		catchClauses = clausesList.toArray(catchClauses);

		for (int i = 0; i < catchClauses.length; i++) {
			if (preferences.control_statement_insert_newline_before_catch_in_try) {
				insertNewLine();
				indent();
			} else {
				if (this.preferences.insert_space_after_closing_brace_in_block) {
					insertSpace();
				}
			}
			handleChars(lastStatementEndOffset, catchClauses[i].getStart());
			catchClauses[i].accept(this);
			lastStatementEndOffset = catchClauses[i].getEnd();
		}
		if (tryStatement.finallyClause() != null) {
			if (preferences.control_statement_insert_newline_before_finally_in_try) {
				insertNewLine();
				indent();
			} else {
				if (this.preferences.insert_space_after_closing_brace_in_block) {
					insertSpace();
				}
			}
			handleChars(lastStatementEndOffset, tryStatement.finallyClause()
					.getStart());
			tryStatement.finallyClause().accept(this);
			lastStatementEndOffset = tryStatement.finallyClause().getEnd();
		}
		return false;
	}

	public boolean visit(UnaryOperation unaryOperation) {
		if (this.preferences.insert_space_before_unary_expression) {
			insertSpace();
		}
		appendToBuffer(UnaryOperation.getOperator(unaryOperation.getOperator()));
		if (this.preferences.insert_space_after_unary_expression) {
			insertSpace();
		}
		// handle the chars between the variable to the value
		Expression expr = unaryOperation.getExpression();
		handleChars(unaryOperation.getStart(), expr.getStart());
		expr.accept(this);
		return false;
	}

	public boolean visit(Variable variable) {
		if (variable.isDollared()) {
			lineWidth++;
		}
		variable.getName().accept(this);
		return false;
	}

	public boolean visit(WhileStatement whileStatement) {
		// handle the chars between the 'while' and the condition start position
		if (this.preferences.insert_space_before_opening_paren_in_while) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_while) {
			insertSpace();
		}
		lineWidth += 5;
		handleChars(whileStatement.getStart() + 5, whileStatement
				.getCondition().getStart());

		// handle the while condition
		whileStatement.getCondition().accept(this);

		// handle the chars between the condition end position and action start
		// position

		// set the while closing paren spaces
		if (this.preferences.insert_space_before_closing_paren_in_while) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		// while action
		final int lastPosition = whileStatement.getCondition().getEnd();
		handleAction(lastPosition, whileStatement.getBody(), true);
		return false;
	}

	// PHP 5.3 new nodes:

	public boolean visit(NamespaceDeclaration namespaceDeclaration) {
		appendToBuffer("namespace"); //$NON-NLS-1$
		insertSpace();
		handleChars(namespaceDeclaration.getStart(), namespaceDeclaration
				.getName().getStart());

		namespaceDeclaration.getName().accept(this);
		int lastPosition = namespaceDeclaration.getName().getEnd();

		if (namespaceDeclaration.isBracketed()) {
			// handle class body
			boolean isIndentationAdded = handleBlockOpenBrace(
					this.preferences.brace_position_for_class,
					this.preferences.insert_space_before_opening_brace_in_class);
			handleChars(lastPosition, namespaceDeclaration.getBody().getStart());

			namespaceDeclaration.getBody().accept(this);

			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDesending = true;
			}
		} else {
			handleSemicolon(lastPosition, namespaceDeclaration.getBody()
					.getStart() - 1);
			namespaceDeclaration.getBody().accept(this);
		}
		return false;
	}

	public boolean visit(NamespaceName namespaceName) {
		if (namespaceName.isGlobal()) {
			appendToBuffer("\\"); //$NON-NLS-1$
		}

		if (namespaceName.isCurrent()) {
			appendToBuffer("namespace\\"); //$NON-NLS-1$
		}
		List<Identifier> segments = namespaceName.segments();
		if (segments.size() > 0) {
			// workaround
			// remove this after fixing of
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=326384
			int start = namespaceName.getStart();
			try {
				if (namespaceName.isGlobal()
						&& (Character.isWhitespace(document.getChar(start - 1)) || document
								.getChar(start - 1) == '\\')) {
					start -= 1;
				}
			} catch (BadLocationException e) {
				// should not be here
			}
			// end

			handleChars(start, segments.get(0).getStart());
			Iterator<Identifier> it = segments.iterator();
			Identifier prev = null;
			while (it.hasNext()) {
				Identifier identifier = it.next();
				if (prev != null) {
					handleChars(prev.getEnd(), identifier.getStart());
				}
				identifier.accept(this);
				if (it.hasNext()) {
					appendToBuffer("\\"); //$NON-NLS-1$
					prev = identifier;
				}
			}
		} else {
			handleChars(namespaceName.getStart(), namespaceName.getEnd());
		}
		return false;
	}

	public boolean visit(UseStatement useStatement) {
		int lastPosition = useStatement.getStart() + 3;
		lineWidth += 3;// the word 'use'
		insertSpace();

		List<UseStatementPart> parts = useStatement.parts();
		lastPosition = handleCommaList(
				parts.toArray(new ASTNode[parts.size()]), lastPosition,
				this.preferences.insert_space_before_comma_in_global,
				this.preferences.insert_space_after_comma_in_global,
				NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, useStatement.getEnd());
		return false;
	}

	public boolean visit(UseStatementPart useStatementPart) {
		useStatementPart.getName().accept(this);
		Identifier alias = useStatementPart.getAlias();
		if (alias != null) {
			insertSpace();
			appendToBuffer("as"); //$NON-NLS-1$
			insertSpace();
			handleChars(useStatementPart.getName().getEnd(), alias.getStart());
			alias.accept(this);
		}
		return false;
	}

	public boolean visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {
		StringBuffer buffer = new StringBuffer();
		if (lambdaFunctionDeclaration.isStatic()) {
			buffer.append("static "); //$NON-NLS-1$
		}
		buffer.append(getDocumentString(lambdaFunctionDeclaration.getStart(),
				lambdaFunctionDeclaration.getStart() + 8));// append 'function'

		// handle referenced function with '&'
		if (lambdaFunctionDeclaration.isReference()) {
			buffer.append(" &"); //$NON-NLS-1$
		} else {
			buffer.append(' ');
		}

		appendToBuffer(buffer.toString());
		handleChars(lambdaFunctionDeclaration.getStart(),
				lambdaFunctionDeclaration.getStart() + 8);

		if (this.preferences.insert_space_before_opening_paren_in_function_declaration) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		List<FormalParameter> formalParameters = lambdaFunctionDeclaration
				.formalParameters();
		ASTNode[] params = (FormalParameter[]) formalParameters
				.toArray(new FormalParameter[formalParameters.size()]);
		int lastPosition = lambdaFunctionDeclaration.getStart() + 8;
		if (params.length > 0) {
			if (this.preferences.insert_space_after_opening_paren_in_function_declaration) {
				insertSpace();
			}
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_parameters_in_method_declaration_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);
			lastPosition = handleCommaList(
					params,
					lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy,
					indentationGap,
					this.preferences.line_wrap_parameters_in_method_declaration_force_split);

			if (this.preferences.insert_space_before_closing_paren_in_function_declaration) {
				insertSpace();
			}
		} else {
			if (this.preferences.insert_space_between_empty_paren_in_function_declaration) {
				insertSpace();
			}
		}
		appendToBuffer(CLOSE_PARN);

		List<Expression> variables = lambdaFunctionDeclaration
				.lexicalVariables();

		if (variables.size() > 0) {
			// TODO: Added a new preference?
			insertSpace();
			appendToBuffer("use"); //$NON-NLS-1$

			appendToBuffer(OPEN_PARN);
			if (this.preferences.insert_space_before_opening_paren_in_function_declaration) {
				insertSpace();
			}
			ASTNode[] vars = (Expression[]) variables
					.toArray(new Expression[variables.size()]);

			lastPosition = handleCommaList(
					vars,
					lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy,
					0,
					this.preferences.line_wrap_parameters_in_method_declaration_force_split);

			if (this.preferences.insert_space_before_closing_paren_in_function_declaration) {
				insertSpace();
			}

			appendToBuffer(CLOSE_PARN);
		}

		// handle function body
		if (lambdaFunctionDeclaration.getBody() != null) {
			boolean isIndentationAdded = handleBlockOpenBrace(
					this.preferences.brace_position_for_function,
					this.preferences.insert_space_before_opening_brace_in_function);
			handleChars(lastPosition, lambdaFunctionDeclaration.getBody()
					.getStart());

			lambdaFunctionDeclaration.getBody().accept(this);
			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDesending = true;
			}
		} else {
			handleSemicolon(lastPosition, lambdaFunctionDeclaration.getEnd());
		}

		return false;
	}

	public boolean visit(TraitUseStatement node) {
		if (node.getTraitList().size() > 0) {
			int lastPosition = node.getStart() + 3;
			lineWidth += 3;// the word 'use'
			insertSpace();
			handleChars(node.getStart() + 3, node.getTraitList().get(0)
					.getStart());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.php.internal.core.format.ICodeFormattingProcessor#
	 * createIndentationString(int)
	 */
	public String createIndentationString(int indentationUnits) {
		if (indentationUnits < 0) {
			throw new IllegalArgumentException();
		}
		int tabs = 0;
		tabs = indentationUnits;
		if (tabs == 0) {
			return ""; //$NON-NLS-1$
		}
		StringBuffer buffer = new StringBuffer(tabs);
		for (int i = 0; i < tabs; i++) {
			buffer.append('\t');
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.format.ICodeFormattingProcessor#getTextEdits
	 * ()
	 */
	public MultiTextEdit getTextEdits() {
		List<ReplaceEdit> allChanges = getChanges();
		MultiTextEdit rootEdit = new MultiTextEdit();
		for (ReplaceEdit edit : allChanges) {
			TextEdit textEdit = new org.eclipse.text.edits.ReplaceEdit(
					edit.offset, edit.length, edit.content);
			rootEdit.addChild(textEdit);
		}
		return rootEdit;
	}

	private boolean isInSingleLine(ReplaceEdit edit, IRegion[] partitions,
			int removedLength) {
		removedLength = 0;
		for (int i = 0; i < partitions.length; i++) {
			IRegion iTypedRegion = partitions[i];
			if (edit.offset >= iTypedRegion.getOffset() + removedLength
					&& edit.offset + edit.length <= iTypedRegion.getOffset()
							+ iTypedRegion.getLength() + removedLength) {
				return true;
			}
		}
		return false;
	}

	private IRegion[] getAllSingleLine(ITypedRegion[] partitions) {
		List<IRegion> result = new ArrayList<IRegion>();
		if (document instanceof IStructuredDocument) {
			IStructuredDocument structuredDocument = (IStructuredDocument) document;
			ITextRegion phpOpenRegion = null;
			for (int i = 0; i < partitions.length; i++) {
				ITypedRegion iTypedRegion = partitions[i];

				if (PHPPartitionTypes.PHP_DEFAULT
						.equals(iTypedRegion.getType())) {
					if (isInSingleLine(iTypedRegion.getOffset(),
							iTypedRegion.getLength())) {
						result.add(iTypedRegion);
						continue;
					}
					IStructuredDocumentRegion structuredDocumentRegion = structuredDocument
							.getRegionAtCharacterOffset(iTypedRegion
									.getOffset());
					ITextRegionList regions = structuredDocumentRegion
							.getRegions();
					for (Iterator iterator = regions.iterator(); iterator
							.hasNext();) {
						ITextRegion iTypedRegion2 = (ITextRegion) iterator
								.next();
						// if (iTypedRegion2 instanceof ContextRegionContainer)
						// {
						// ContextRegionContainer new_name =
						// (ContextRegionContainer) iTypedRegion2;
						//
						// }
						if (PHPRegionContext.PHP_OPEN.equals(iTypedRegion2
								.getType())) {
							if (phpOpenRegion == null) {
								phpOpenRegion = iTypedRegion2;
							}
						} else if (PHPRegionContext.PHP_CLOSE
								.equals(iTypedRegion2.getType())) {
							if (phpOpenRegion != null) {
								IRegion region = new Region(
										structuredDocumentRegion.getStart()
												+ phpOpenRegion.getStart(),
										iTypedRegion2.getStart()
												+ iTypedRegion2.getLength());
								result.add(region);
								phpOpenRegion = null;
							}
						}
					}
				}
			}
			List<IRegion> temp = new ArrayList<IRegion>();
			for (Iterator iterator = result.iterator(); iterator.hasNext();) {
				IRegion iRegion = (IRegion) iterator.next();
				if (isInSingleLine(iRegion.getOffset(), iRegion.getLength())) {
					temp.add(iRegion);
				}
			}
			result = temp;
		}
		return result.toArray(new IRegion[result.size()]);
	}

	private boolean isInSingleLine(int start, int length) {
		try {
			String text = document.get(start, length);
			int index = text.indexOf("<?"); //$NON-NLS-1$
			start += index;
			if (text.lastIndexOf("?>") >= 0) { //$NON-NLS-1$
				length = text.lastIndexOf("?>") + 2 - index; //$NON-NLS-1$
			}
			if (document.getLineOfOffset(start) == document
					.getLineOfOffset(start + length)) {
				return true;
			}
		} catch (BadLocationException e) {
		}
		return false;
	}

	public static String join(Collection<String> s, String delimiter) {
		if (s == null || s.isEmpty())
			return ""; //$NON-NLS-1$
		Iterator<String> iter = s.iterator();
		StringBuilder builder = new StringBuilder(iter.next());
		while (iter.hasNext()) {
			builder.append(delimiter).append(iter.next());
		}
		return builder.toString();
	}

}
