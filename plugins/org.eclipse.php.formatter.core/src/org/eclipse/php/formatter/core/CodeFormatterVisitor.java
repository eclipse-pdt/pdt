/*******************************************************************************
 * Copyright (c) 2013, 2015, 2016, 2017 Zend Techologies Ltd.
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.jface.text.*;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocBlock;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag;
import org.eclipse.php.core.compiler.ast.nodes.PHPDocTag.TagKind;
import org.eclipse.php.core.compiler.ast.nodes.VarComment;
import org.eclipse.php.formatter.core.profiles.CodeFormatterPreferences;
import org.eclipse.php.internal.core.Constants;
import org.eclipse.php.internal.core.ast.scanner.AstLexer;
import org.eclipse.php.internal.core.compiler.ast.parser.php56.CompilerParserConstants;
import org.eclipse.php.internal.core.compiler.ast.parser.php56.PHPTokenNames;
import org.eclipse.php.internal.core.documentModel.parser.PHPRegionContext;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPPartitionTypes;
import org.eclipse.php.internal.core.documentModel.partitioner.PHPStructuredTextPartitioner;
import org.eclipse.php.internal.core.format.ICodeFormattingProcessor;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.formatter.core.DocumentReader;
import org.eclipse.php.internal.formatter.core.Logger;
import org.eclipse.text.edits.MultiTextEdit;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.wst.sse.core.internal.provisional.text.*;

import java_cup.runtime.Symbol;

/**
 * Description: This class formats a given {@link StructuredDocument}
 * 
 * @author moshe, 2007
 */
public class CodeFormatterVisitor extends AbstractVisitor implements ICodeFormattingProcessor {

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
	private static final char CLOSE_CURLY = '}';
	private static final char OPEN_BRACKET = '[';
	private static final char CLOSE_BRACKET = ']';
	private static final char COLON = ':';
	private static final char SEMICOLON = ';';
	private static final char SPACE = ' ';
	private static final char COMMA = ',';
	private static final char QUESTION_MARK = '?';
	private static final String ELLIPSIS = "..."; //$NON-NLS-1$
	private static final String FROM = "from"; //$NON-NLS-1$
	private String lineSeparator;

	private CodeFormatterPreferences preferences;
	private final IDocument document;
	private PHPVersion phpVersion;
	private boolean useShortTags;

	private int indentationLevel;
	private boolean indentationLevelDescending = false;
	private AstLexer astLexer;
	private boolean isPHPEqualTag = false;
	private int startRegionPosition = -1;
	private int endRegionPosition = Integer.MAX_VALUE;
	private boolean isPrevSpace = false;
	private boolean isHeredocSemicolon = false;
	private int lineWidth = 0;
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
	private StringBuilder replaceBuffer = new StringBuilder();
	private List<Symbol> tokens = new ArrayList<Symbol>();

	/**
	 * list of <ReplaceEdit>
	 */
	private List<ReplaceEdit> changes = new LinkedList<ReplaceEdit>();
	private int stInScriptin = -1;
	private int stWhile = -1;
	private int stElse = -1;
	private int stElseIf = -1;

	private Stack<Integer> chainStack = new Stack<Integer>();
	private Integer peek;
	private Set<IfStatement> processedIfStatements = new HashSet<IfStatement>();
	private boolean newLineOfComment;
	private List<String> commentWords;
	/** disabling */
	boolean editsEnabled;
	boolean useTags;
	int tagsKind;
	private String disablingTag, enablingTag;
	// this is for never indent at first line
	private boolean doNotIndent = false;
	boolean inComment = false;
	private int indentLengthForComment = -1;
	private String indentStringForComment = null;
	private boolean blockEnd;
	private boolean recordCommentIndentVariables = false;
	// for block comment,multiline comment at the end of break statement of case
	// statement
	private List<Integer> indentationLevelList = new ArrayList<Integer>();
	Stack<CommentIndentationObject> commentIndentationStack = new Stack<CodeFormatterVisitor.CommentIndentationObject>();

	private boolean ignoreEmptyLineSetting = false;

	public CodeFormatterVisitor(IDocument document, CodeFormatterPreferences codeFormatterPreferences,
			String lineSeparator, PHPVersion phpVersion, boolean useShortTags, IRegion region) throws Exception {
		this(document, codeFormatterPreferences, lineSeparator, phpVersion, useShortTags, region, 0);
	}

	// public CodeFormatterVisitor(IDocument document, String lineSeparator,
	// PHPVersion phpVersion, boolean useShortTags,
	// IRegion region) throws Exception {
	// this(document, CodeFormatterPreferences.getDefaultPreferences(),
	// lineSeparator, phpVersion, useShortTags,
	// region, 0);
	// }

	public CodeFormatterVisitor(IDocument document, CodeFormatterPreferences codeFormatterPreferences,
			String lineSeparator, PHPVersion phpVersion, boolean useShortTags, IRegion region, int indentationLevel)
			throws Exception {
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
			program = ASTParser.newParser(reader, phpVersion, true).createAST(new NullProgressMonitor());
		} catch (Exception e) {
			Logger.log(Logger.INFO, "Parsing error, file could not be formatted.");//$NON-NLS-1$
		}

		this.useTags = preferences.use_tags;
		this.tagsKind = 0;
		if (this.useTags) {
			if (preferences.disabling_tag != null && preferences.disabling_tag.length > 0) {
				this.disablingTag = new String(preferences.disabling_tag);
			}
			if (preferences.enabling_tag != null && preferences.enabling_tag.length > 0) {
				this.enablingTag = new String(preferences.enabling_tag);
			}
		}
		this.editsEnabled = true;
		if (program != null) {
			program.accept(this);
		}

	}

	// insert chars to the buffer
	private void appendToBuffer(Object obj) {
		isPrevSpace = false;
		if (obj == null)
			return;
		replaceBuffer.append(obj);
		if (!lineSeparator.equals(obj)) {
			lineWidth += obj.toString().length();
		}
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

	private char getBufferFirstChar(int position) throws BadLocationException {
		for (int offset = position; offset < replaceBuffer.length(); offset++) {
			char currChar = replaceBuffer.charAt(offset);
			if (currChar != ' ' && currChar != '\t' && currChar != '\r' && currChar != '\n') {
				// not empty line
				return currChar;
			}
		}
		return '\0';
	}

	public List<ReplaceEdit> getChanges() {
		IRegion[] regions = new IRegion[0];
		try {
			regions = getAllSingleLine(TextUtilities.computePartitioning(document,
					IStructuredPartitioning.DEFAULT_STRUCTURED_PARTITIONING, 0, document.getLength(), false));
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
		List<ReplaceEdit> allChanges = Collections.unmodifiableList(changes);
		List<ReplaceEdit> result = new ArrayList<ReplaceEdit>();
		for (ReplaceEdit edit : allChanges) {
			if (isInSingleLine(edit, regions)) {
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
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php5.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php5.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php5.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php5.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP5_3.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_3, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php53.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php53.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP5_4.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_4, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php54.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php54.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP5_5.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_5, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php55.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php55.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP5_6.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php56.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php56.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP5_6, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php56.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php56.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP7_0.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php7.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php7.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP7_0, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php7.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php7.ParserConstants.T_ELSEIF;
		} else if (PHPVersion.PHP7_1.equals(phpVersion)) {
			result = new org.eclipse.php.internal.core.compiler.ast.parser.php71.CompilerAstLexer(reader);
			((org.eclipse.php.internal.core.compiler.ast.parser.php71.CompilerAstLexer) result)
					.setAST(new AST(reader, PHPVersion.PHP7_1, false, useShortTags));
			stInScriptin = org.eclipse.php.internal.core.compiler.ast.parser.php71.CompilerAstLexer.ST_IN_SCRIPTING; // save
			// the
			// initial
			// state
			// for
			// reset
			// operation
			stWhile = org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_WHILE;
			stElse = org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ELSE;
			stElseIf = org.eclipse.php.internal.core.ast.scanner.php71.ParserConstants.T_ELSEIF;
		} else {
			throw new IllegalArgumentException("unrecognized version " //$NON-NLS-1$
					+ phpVersion);
		}
		return result;
	}

	private byte getPHPStartTag(int offset) {
		try {
			// 6 = "<?php".length() + 1
			String text = document.get(offset, Math.min(6, document.getLength() - offset)).toLowerCase();
			if (text.startsWith("<%")) { //$NON-NLS-1$
				return PHP_OPEN_ASP_TAG;
			}
			if (text.startsWith("<?=")) { //$NON-NLS-1$
				return PHP_OPEN_SHORT_TAG_WITH_EQUAL;
			}
			if (text.startsWith("<?")) { //$NON-NLS-1$
				if (text.startsWith("<?php") && text.length() >= 6) { //$NON-NLS-1$
					char separatorChar = text.charAt(5);
					// the definition of token PHP_START in
					// PHPTokenizer.jflex tells us that "<?php" must ALWAYS
					// be followed by a whitespace character to be recognized as
					// a PHP_OPEN_TAG
					if (separatorChar == ' ' || separatorChar == '\t' || separatorChar == '\r'
							|| separatorChar == '\n') {
						return PHP_OPEN_TAG;
					}
				}
				// Short tag "<?".
				// But also (for example) "<?phpXYZ :" must be handled as
				// a PHP_OPEN_SHORT_TAG followed by label "XYZ".
				return PHP_OPEN_SHORT_TAG;
			}
		} catch (Exception e) {
			Logger.logException(e);
		}
		return -1;
	}

	private int getPHPTagIndentationLevel(int offset) {
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
	private void handleAction(int lastPosition, Statement action, boolean addNewlineBeforeAction) {
		boolean isIndentationAdded = false;
		if (action.getType() == ASTNode.BLOCK) {
			isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_block,
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
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=511502
			if (action.getType() == ASTNode.BLOCK) {
				indentationLevelDescending = true;
			}
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
	private boolean handleBlockOpenBrace(byte bracePosition, boolean placeSpaceBeforeOpenCurly) {
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
					handleComments(offset, end, astLexer.getCommentList(), false, 0);
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
	private void handleChars1(int offset, int end, boolean isIndented, int indentGap) {
		try {
			// check if the changed region is in the formatting requested region
			if (startRegionPosition < end && endRegionPosition >= end) {
				boolean hasComments = hasComments(offset, end);

				if (hasComments) {
					// handle the comments
					handleComments(offset, end, astLexer.getCommentList(), isIndented, indentGap);
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

	private int getFirstTokenOffset(int start, int end, int tokenId, boolean shouldScan) throws Exception {
		if (shouldScan) {
			scan(start, end);
		}

		assert astLexer != null;
		for (Symbol token : tokens) {
			if (token.sym == tokenId) {
				return start + token.left;
			}
		}
		return -1;
	}

	private void handleCharsWithoutComments(int offset, int end) throws BadLocationException {
		handleCharsWithoutComments(offset, end, false);
	}

	private void handleCharsWithoutComments(int offset, int end, boolean isComment) throws BadLocationException {
		String content = document.get(offset, end - offset).toLowerCase();
		int phpTagOpenIndex = -1;
		if (!isComment && ((phpTagOpenIndex = content.indexOf("<?")) != -1 //$NON-NLS-1$
				|| (phpTagOpenIndex = content.indexOf("<%")) != -1)) { //$NON-NLS-1$
			handleSplittedPHPBlock(offset + phpTagOpenIndex, end);
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
					// If newLinesInBuffer is = 0, it *could* mean that the
					// buffer was already (partly or completely) flushed (so
					// there are big chances that we already inserted a
					// newline), increase newLinesInBuffer by one to only insert
					// emptyLines - 1 newlines.
					// If newLinesInBuffer is > 0, first newline from buffer is
					// ignored because it either ends a non-empty line or some
					// formatter preference already forced the insertion of a
					// newline.
					for (int line = newLinesInBuffer == 0 ? 1 : newLinesInBuffer; line < emptyLines + 1; line++) {
						insertNewLine();
					}
					if (inComment) {
						if (!doNotIndent) {
							indentForComment(indentationLevelDescending);
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
				String afterNewLine = EMPTY_STRING;
				int position = replaceBuffer.lastIndexOf(lineSeparator);
				if (position >= 0) {
					if (getBufferFirstChar(position + lineSeparator.length()) == '\0') {
						afterNewLine = replaceBuffer.substring(position + lineSeparator.length(),
								replaceBuffer.length());
					}
				} else {
					if (getBufferFirstChar(0) == '\0') {
						afterNewLine = replaceBuffer.toString();
					}
				}
				indentStringForComment = afterNewLine;
			}
			indentationLevelDescending = false;
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
	private int handleCommaList(ASTNode[] array, int lastPosition, boolean insertSpaceBeforeComma,
			boolean insertSpaceAfterComma, int lineWrapPolicy, int indentGap, boolean forceSplit) {
		int oldIndentationLevel = indentationLevel;
		boolean wasBinaryExpressionWrapped = this.wasBinaryExpressionWrapped;
		if (array.length == 0) {
			return lastPosition;
		}

		// save the changes index position
		String savedBuffer = replaceBuffer.toString();
		int changesIndex = changes.size() - 1;
		int savedLastPosition = lastPosition;
		boolean isExtraIndentation = false;

		// Map<Integer, CommentIndentationObject> commentIndentationMap = new
		// HashMap<Integer, CommentIndentationObject>();
		CommentIndentationObject cio = new CommentIndentationObject();
		commentIndentationStack.add(cio);
		// commentIndentationMap.put(array., cio);
		boolean isFirst = true;
		for (int i = 0; i < array.length; i++) {
			if (!isFirst) {
				if (insertSpaceBeforeComma) {
					insertSpace();
				}
				appendToBuffer(COMMA);
			}

			// after the first element and wrap policy is except first element
			if (i == 1 && lineWrapPolicy == WRAP_ALL_ELEMENTS_EXCEPT_FIRST) {
				savedBuffer = replaceBuffer.toString();
				changesIndex = changes.size() - 1;
				savedLastPosition = lastPosition;
			}
			boolean isInsertNewLine = false;
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
					isInsertNewLine = true;
				}
				break;
			case WRAP_WHEN_NECESSARY:
				if (lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					insertNewLine();
					indent();
					isInsertNewLine = true;
				}
				break;
			case WRAP_FIRST_ELEMENT:
				if (forceSplit || lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = 0;
					lineWrapPolicy = WRAP_WHEN_NECESSARY;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();
					isInsertNewLine = true;
				}
				break;
			case WRAP_ALL_ELEMENTS:
				if (forceSplit || lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
					revert(savedBuffer, changesIndex);
					lastPosition = savedLastPosition;
					i = 0;
					lineWrapPolicy = ALWAYS_WRAP_ELEMENT;
					insertNewLine();
					if (!cio.indented) {
						indentationLevel += indentGap;
					}
					indent();
					isInsertNewLine = true;
				}
				break;
			case WRAP_ALL_ELEMENTS_NO_INDENT_FIRST:
				if (forceSplit || lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
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
					isInsertNewLine = true;

					// increase the indentation level after the first element
					indentationLevel++;
					isExtraIndentation = true;
				}
				break;
			case WRAP_ALL_ELEMENTS_EXCEPT_FIRST:
				if (forceSplit || lineWidth + array[i].getLength() > this.preferences.line_wrap_line_split) {
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
					isInsertNewLine = true;
				}
				break;
			case ALWAYS_WRAP_ELEMENT:
				insertNewLine();
				indent();
				isInsertNewLine = true;
				break;
			}

			if (!isInsertNewLine && !isFirst && insertSpaceAfterComma) {
				insertSpace();
			}

			handleChars1(lastPosition, array[i].getStart(), oldIndentationLevel != indentationLevel, indentGap);
			array[i].accept(this);
			lastPosition = array[i].getEnd();

			isFirst = false;
		}
		commentIndentationStack.pop();
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

	// TODO: Do correct comment placement
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=440209
	// https://bugs.eclipse.org/bugs/show_bug.cgi?id=440820
	private void handleComments(int offset, int end, List<org.eclipse.php.core.compiler.ast.nodes.Comment> commentList,
			boolean isIndented, int indentGap) throws Exception {
		boolean oldIgnoreEmptyLineSetting = ignoreEmptyLineSetting;
		ignoreEmptyLineSetting = false;

		int startLine = document.getLineOfOffset(offset);
		int start = offset;
		boolean needIndentNewLine = false;
		boolean indentationLevelDescending = this.indentationLevelDescending;
		inComment = true;
		boolean previousCommentIsSingleLine = false;
		resetCommentIndentVariables();

		comments: for (Iterator<org.eclipse.php.core.compiler.ast.nodes.Comment> iter = commentList.iterator(); iter
				.hasNext();) {
			org.eclipse.php.core.compiler.ast.nodes.Comment comment = iter.next();
			int commentStartLine = document.getLineOfOffset(comment.sourceStart() + offset);
			int position = replaceBuffer.lastIndexOf(lineSeparator);
			boolean startAtFirstColumn = (document.getLineOffset(commentStartLine) == comment.sourceStart() + offset);
			boolean endWithNewLineIndent = endWithNewLineIndent(replaceBuffer.toString());
			String afterNewLine = EMPTY_STRING;
			boolean indentOnFirstColumn;
			String commentContent;
			switch (comment.getCommentType()) {
			case org.eclipse.php.core.compiler.ast.nodes.Comment.TYPE_SINGLE_LINE:
				indentOnFirstColumn = !startAtFirstColumn
						|| !this.preferences.never_indent_line_comments_on_first_column;
				if (startLine == commentStartLine) {
					indentOnFirstColumn = false;
					IRegion startLinereg = document.getLineInformation(startLine);
					// TODO: Do line width calculation based on the
					// formatted content instead of the original content
					lineWidth = comment.sourceStart() + offset - startLinereg.getOffset();
					if (position >= 0) {
						if (getBufferFirstChar(position + lineSeparator.length()) == '\0') {
							afterNewLine = replaceBuffer.substring(position + lineSeparator.length(),
									replaceBuffer.length());
							replaceBuffer.replace(position, replaceBuffer.length(), ""); //$NON-NLS-1$
							insertSpaces(1);
						} else {
							insertSpace();
						}
					} else {
						if (getBufferFirstChar(0) == '\0') {
							replaceBuffer.setLength(0);
							insertSpaces(1);
						} else {
							insertSpace();
						}
					}
				} else {
					if (getBufferFirstChar(0) == '\0') {
						if (position >= 0) {
							if (getBufferFirstChar(position + lineSeparator.length()) == '\0') {
								replaceBuffer.replace(position, replaceBuffer.length(), ""); //$NON-NLS-1$
							}
							insertNewLine();
						} else {
							replaceBuffer.setLength(0);
							lineWidth = 0;
						}
					} else {
						if (position >= 0 && getBufferFirstChar(position + lineSeparator.length()) == '\0') {
							replaceBuffer.replace(position, replaceBuffer.length(), ""); //$NON-NLS-1$
						}
						insertNewLine();
						if (!isIndented && !commentIndentationStack.isEmpty()) {
							CommentIndentationObject cio = commentIndentationStack.peek();
							if (!cio.indented) {
								cio.indented = true;
								indentationLevel += indentGap;
							}
						}
						// TODO should add indent level
					}
					if (indentationLevelDescending || blockEnd) {
						for (int i = 0; i < preferences.indentationSize; i++) {
							appendToBuffer(preferences.indentationChar);
							lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
						}
					}
					needIndentNewLine = true;
				}

				if (!previousCommentIsSingleLine) {
					resetCommentIndentVariables();
				}
				doNotIndent = true;
				if (indentOnFirstColumn) {
					if (indentLengthForComment >= 0) {
						appendToBuffer(indentStringForComment);
						// adjust lineWidth, because indentLengthForComment may
						// contain '\t'
						lineWidth = indentLengthForComment;
					} else {
						indent();
					}
					doNotIndent = false;
					if (lineWidth > 0) {
						startAtFirstColumn = false;
					}
				}
				previousCommentIsSingleLine = true;

				handleCharsWithoutComments(start, comment.sourceStart() + offset);
				doNotIndent = false;
				resetEnableStatus(
						document.get(comment.sourceStart() + offset, comment.sourceEnd() - comment.sourceStart()));

				if (this.editsEnabled && this.preferences.comment_format_line_comment
						&& (startAtFirstColumn && this.preferences.comment_format_line_comment_starting_on_first_column
								|| !startAtFirstColumn)) {
					if (startLine == commentStartLine) {
						initCommentIndentVariables(offset, startLine, comment, endWithNewLineIndent);
						lineWidth = indentLengthForComment;
					}
					if (startAtFirstColumn && this.preferences.never_indent_line_comments_on_first_column) {
						indentLengthForComment = 0;
						indentStringForComment = ""; //$NON-NLS-1$
					}

					commentContent = document.get(comment.sourceStart() + offset,
							comment.sourceEnd() - comment.sourceStart());
					boolean needInsertNewLine = commentContent.endsWith(lineSeparator);
					if (!needInsertNewLine) {
						String[] delimiters = document.getLegalLineDelimiters();
						for (int i = 0; i < delimiters.length; i++) {
							needInsertNewLine = commentContent.endsWith(delimiters[i]);
							if (needInsertNewLine) {
								break;
							}
						}
					}
					int commentTokLen = commentContent.startsWith("#") ? 1 : 2;//$NON-NLS-1$
					commentWords = Arrays.asList(
							MagicMemberUtil.WHITESPACE_SEPERATOR.split(commentContent.substring(commentTokLen).trim()));
					commentWords = removeEmptyString(commentWords);
					commentContent = join(commentWords, " "); //$NON-NLS-1$
					commentContent = commentContent.trim();

					boolean newLineStart = true;
					appendToBuffer("//"); //$NON-NLS-1$

					for (String word : commentWords) {
						if (this.preferences.comment_line_length != 9999 && !newLineStart
								&& (lineWidth + 1 + word.length() > this.preferences.comment_line_length)) {
							insertNewLine();
							// start at first column, and more than
							// comment_line_length
							if (!startAtFirstColumn || (startAtFirstColumn && indentOnFirstColumn)) {
								if (indentLengthForComment >= 0) {
									appendToBuffer(indentStringForComment);
								} else {
									indent();
								}
							}
							appendToBuffer("//"); //$NON-NLS-1$
							insertSpaces(1);
							appendToBuffer(word);
						} else {
							insertSpaces(1);
							appendToBuffer(word);
							newLineStart = false;
						}
					}
					handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset, true);
					if (needInsertNewLine) {
						insertNewLine();
						needInsertNewLine = false;
					} else {
						insertSpaces(1);
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=489488
						needIndentNewLine = false;
						afterNewLine = EMPTY_STRING;
					}
				} else {
					commentContent = document.get(comment.sourceStart() + offset,
							comment.sourceEnd() - comment.sourceStart());
					boolean needInsertNewLine = commentContent.endsWith(lineSeparator);
					if (!needInsertNewLine) {
						String[] delimiters = document.getLegalLineDelimiters();
						for (int i = 0; i < delimiters.length; i++) {
							needInsertNewLine = commentContent.endsWith(delimiters[i]);
							if (needInsertNewLine) {
								break;
							}
						}
					}
					if (needInsertNewLine) {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=441825
						lineWidth = 0;
						needInsertNewLine = false;
					} else {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=489488
						needIndentNewLine = false;
						afterNewLine = EMPTY_STRING;
					}
				}

				start = comment.sourceEnd() + offset;
				break;
			case org.eclipse.php.core.compiler.ast.nodes.Comment.TYPE_PHPDOC:
				previousCommentIsSingleLine = false;
				inComment = false;
				handleCharsWithoutComments(start, comment.sourceStart() + offset);
				inComment = true;
				resetEnableStatus(
						document.get(comment.sourceStart() + offset, comment.sourceEnd() - comment.sourceStart()));
				String codeBeforeComment = document.get(0, comment.sourceStart() + offset).trim();
				boolean isHeaderComment = codeBeforeComment.equals("<?") //$NON-NLS-1$
						|| codeBeforeComment.equals("<?php"); //$NON-NLS-1$
				if ((!isHeaderComment || this.preferences.comment_format_header) && this.editsEnabled
						&& this.preferences.comment_format_javadoc_comment
						&& canHandlePHPDocComment((PHPDocBlock) comment, offset)) {
					PHPDocBlock block = (PHPDocBlock) comment;

					newLineOfComment = false;
					appendToBuffer("/**"); //$NON-NLS-1$

					commentWords = new ArrayList<>();
					org.eclipse.php.core.compiler.ast.nodes.Scalar[] texts = block.getTexts()
							.toArray(new org.eclipse.php.core.compiler.ast.nodes.Scalar[block.getTexts().size()]);
					PHPDocTag[] tags = block.getTags();
					if (tags == null || tags.length == 0) {
						texts = getNonblankScalars(texts);
					}
					boolean lastLineIsBlank = false;
					boolean isFirst = true;

					// description is blank
					if (getNonblankScalars(texts).length == 0) {
						texts = new org.eclipse.php.core.compiler.ast.nodes.Scalar[0];
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
						org.eclipse.php.core.compiler.ast.nodes.Scalar scalar = texts[j];
						String word = scalar.getValue();
						if (word.trim().length() > 0) {
							commentWords.add(word);
							if (this.preferences.join_lines_in_comments) {

								if (!isFirst) {
									insertNewLineForPHPDoc();
								}
								isFirst = false;
								initCommentWords();
								formatPHPDocText(commentWords, null, false, false);
								commentWords = new ArrayList<String>();
								lastLineIsBlank = false;
							}
						} else if (!this.preferences.comment_clear_blank_lines_in_javadoc_comment) {
							// don't duplicate first blank line
							if (isFirst && this.preferences.comment_new_lines_at_javadoc_boundaries
									&& commentWords.isEmpty()) {
								isFirst = false;
								lastLineIsBlank = true;
								continue;
							}
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
						lastLineIsBlank = false;
					}

					if (tags != null && tags.length > 0) {
						if (this.preferences.comment_insert_empty_line_before_root_tags && !lastLineIsBlank) {
							insertNewLineForPHPDoc();
							appendToBuffer(" "); //$NON-NLS-1$
						}
						for (int i = 0; i < tags.length; i++) {
							PHPDocTag phpDocTag = tags[i];
							boolean insertTag = true;
							String[] words = phpDocTag.getDescTexts().toArray(new String[0]);

							if ((i == tags.length - 1) && !this.preferences.comment_new_lines_at_javadoc_boundaries) {
								words = getNonblankWords(words);
							}
							commentWords = new ArrayList<>();

							if (getNonblankWords(words).length == 0) {
								boolean hasRefs = phpDocTag.getAllReferencesWithOrigOrder().size() != 0;
								int nbLines = words.length;
								// https://bugs.eclipse.org/bugs/show_bug.cgi?id=433938
								if (!hasRefs && nbLines >= 1) {
									nbLines--;
								}
								// insert several lines
								formatCommentWords(phpDocTag, insertTag, false);
								for (int j = 0; j < nbLines; j++) {
									insertNewLineForPHPDoc();
								}
							} else {
								for (int j = 0; j < words.length; j++) {
									String word = words[j];
									if (word.trim().length() > 0) {
										commentWords.add(word);
										if (this.preferences.join_lines_in_comments) {

											formatCommentWords(phpDocTag, insertTag, true);
											insertTag = false;
										}
									} else if (!this.preferences.comment_clear_blank_lines_in_javadoc_comment
											&& !insertTag) {

										formatCommentWords(phpDocTag, insertTag, true);
										insertTag = false;
									}

								}
								if (!commentWords.isEmpty() || insertTag) {
									formatCommentWords(phpDocTag, insertTag, !commentWords.isEmpty());
								}
							}
						}
						lastLineIsBlank = false;
					}
					if (this.preferences.comment_new_lines_at_javadoc_boundaries && !lastLineIsBlank) {
						insertNewLineForPHPDoc();
						appendToBuffer("/"); //$NON-NLS-1$
					} else if (lastLineIsBlank) {
						appendToBuffer("/"); //$NON-NLS-1$
					} else {
						indertWordToComment("*/"); //$NON-NLS-1$
					}
					handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset, true);
				} else {
					commentContent = document.get(comment.sourceStart() + offset,
							comment.sourceEnd() - comment.sourceStart());
					List<String> lines = Arrays.asList(commentContent.split("\r\n?|\n", -1)); //$NON-NLS-1$
					appendToBuffer(lines.get(0));
					// indent all lines, even empty lines
					for (int i = 1; i < lines.size(); i++) {
						insertNewLineForPHPDoc(false);
						appendToBuffer(lines.get(i).replaceFirst("^\\p{javaWhitespace}+", "")); //$NON-NLS-1$ //$NON-NLS-2$
					}
					handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset, true);
				}
				start = comment.sourceEnd() + offset;
				insertNewLine();
				indent();
				break;
			case org.eclipse.php.core.compiler.ast.nodes.Comment.TYPE_MULTILINE:
				previousCommentIsSingleLine = false;
				// ignore multi line comments in the middle of code
				// example while /* kuku */ ( /* kuku */$a > 0 )
				if (getBufferFirstChar(0) != '\0') {
					replaceBuffer.setLength(0);
					IRegion reg = document.getLineInformationOfOffset(end);
					// TODO: Do line width calculation based on the
					// formatted content instead of the original content
					lineWidth = end - reg.getOffset();
					resetEnableStatus(
							document.get(comment.sourceStart() + offset, comment.sourceEnd() - comment.sourceStart()));
					for (; iter.hasNext();) {
						org.eclipse.php.core.compiler.ast.nodes.Comment nextComment = iter.next();
						resetEnableStatus(document.get(nextComment.sourceStart() + offset,
								nextComment.sourceEnd() - nextComment.sourceStart()));
					}
					start = end;
					break comments;
				}

				// buffer contains only whitespace chars
				indentOnFirstColumn = !startAtFirstColumn
						|| !this.preferences.never_indent_block_comments_on_first_column;
				if (startLine == commentStartLine) {
					indentOnFirstColumn = false;
					IRegion startLinereg = document.getLineInformation(startLine);
					// TODO: Do line width calculation based on the
					// formatted content instead of the original content
					lineWidth = comment.sourceStart() + offset - startLinereg.getOffset();
					if (position >= 0) {
						// if (getBufferFirstChar(position
						// + lineSeparator.length()) == '\0') {
						afterNewLine = replaceBuffer.substring(position + lineSeparator.length(),
								replaceBuffer.length());
						replaceBuffer.replace(position, replaceBuffer.length(), ""); //$NON-NLS-1$
						insertSpaces(1);
						// } else {
						// insertSpace();
						// }
					} else {
						// if (getBufferFirstChar(0) == '\0') {
						replaceBuffer.setLength(0);
						insertSpaces(1);
						// } else {
						// insertSpace();
						// }
					}
				} else {
					if (position >= 0) {
						// if (getBufferFirstChar(position
						// + lineSeparator.length()) == '\0') {
						replaceBuffer.replace(position + lineSeparator.length(), replaceBuffer.length(), ""); //$NON-NLS-1$
						lineWidth = 0;
						// } else {
						// insertNewLine();
						// }
					} else {
						// if (getBufferFirstChar(0) == '\0') {
						replaceBuffer.setLength(0);
						lineWidth = 0;
						// } else {
						// insertNewLine();
						// }
					}
					if (indentationLevelDescending || blockEnd) {
						// add single indentationChar * indentationSize
						// Because the comment is the previous
						// indentation
						// level
						for (int i = 0; i < preferences.indentationSize; i++) {
							appendToBuffer(preferences.indentationChar);
							lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
						}
					}
					needIndentNewLine = true;
				}
				resetCommentIndentVariables();
				if (startLine != commentStartLine && blockEnd) {
					recordCommentIndentVariables = true;
				}
				doNotIndent = true;
				if (indentOnFirstColumn) {
					indent();
					doNotIndent = false;
					if (lineWidth > 0) {
						startAtFirstColumn = false;
					}
				}

				handleCharsWithoutComments(start, comment.sourceStart() + offset);
				doNotIndent = false;
				start = comment.sourceEnd() + offset;
				resetEnableStatus(
						document.get(comment.sourceStart() + offset, comment.sourceEnd() - comment.sourceStart()));

				if (startLine == commentStartLine) {
					initCommentIndentVariables(offset, startLine, comment, endWithNewLineIndent);
					lineWidth = indentLengthForComment;
				}
				if (startAtFirstColumn && this.preferences.never_indent_block_comments_on_first_column) {
					indentLengthForComment = 0;
					indentStringForComment = ""; //$NON-NLS-1$
				}
				if (this.editsEnabled && this.preferences.comment_format_block_comment
						&& !(comment instanceof VarComment)) {
					appendToBuffer("/*"); //$NON-NLS-1$
					commentContent = document.get(comment.sourceStart() + offset,
							comment.sourceEnd() - comment.sourceStart());

					// boolean needInsertNewLine = commentContent
					// .endsWith(lineSeparator);
					// if (!needInsertNewLine) {
					// String[] delimiters = document.getLegalLineDelimiters();
					// for (int i = 0; i < delimiters.length; i++) {
					// needInsertNewLine = commentContent
					// .endsWith(delimiters[i]);
					// if (needInsertNewLine) {
					// break;
					// }
					// }
					// }
					commentContent = commentContent.trim();
					commentContent = commentContent.substring(2, commentContent.length() - 2);
					List<String> lines = Arrays.asList(commentContent.split("\r\n?|\n", -1)); //$NON-NLS-1$
					commentWords = new ArrayList<>();
					if (lines.size() == 1) {
						String word = lines.get(0).trim();
						commentWords.add(word);
						initCommentWords();
						StringBuilder sb = new StringBuilder();
						for (String w : commentWords) {
							if (StringUtils.isBlank(w)) {
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
							commentWords = new ArrayList<>();
							handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset,
									true);
							// if (needInsertNewLine) {
							insertNewLine();
							// needInsertNewLine = false;
							// } else {
							// IRegion reg = document
							// .getLineInformation(commentEndLine);
							// int lengthAfterCommentEnd = reg.getOffset()
							// + reg.getLength()
							// - (comment.sourceEnd() + offset);
							// if (lengthAfterCommentEnd <= 0) {
							// insertNewLine();
							// } else {
							// String stringAfterCommentEnd = document
							// .get(comment.sourceEnd() + offset,
							// lengthAfterCommentEnd);
							// if (stringAfterCommentEnd.trim().length() == 0) {
							// insertNewLine();
							// } else {
							// insertSpaces(1);
							// needIndentNewLine = false;
							// afterNewLine = EMPTY_STRING;
							// }
							// }
							// }
							break;
						}
						commentWords = new ArrayList<>();
					}
					newLineOfComment = false;
					if (this.preferences.comment_new_lines_at_block_boundaries) {
						insertNewLineForPHPBlockComment(indentLengthForComment, indentStringForComment);
						newLineOfComment = true;
					}
					boolean isFirst = true;
					for (int j = 0; j < lines.size(); j++) {
						String word = lines.get(j).trim();
						if (word.startsWith("*")) { //$NON-NLS-1$
							word = word.substring(1);
						}
						if (word.length() > 0) {
							commentWords.add(word);
							if (this.preferences.join_lines_in_comments) {
								if (!isFirst) {
									insertNewLineForPHPBlockComment(indentLengthForComment, indentStringForComment);
									newLineOfComment = true;
								}
								isFirst = false;
								formatCommentBlockWords(indentLengthForComment, indentStringForComment);
							}
						} else if (!this.preferences.comment_clear_blank_lines_in_block_comment) {
							if (j != 0 && j != lines.size() - 1) {
								formatCommentBlockWords(indentLengthForComment, indentStringForComment);
								// don't duplicate first blank line
								if (isFirst && this.preferences.comment_new_lines_at_block_boundaries) {
									newLineOfComment = true;
									isFirst = false;
									continue;
								}
								insertNewLineForPHPBlockComment(indentLengthForComment, indentStringForComment);
								newLineOfComment = true;
								isFirst = false;
							}
						}
					}
					if (!commentWords.isEmpty()) {
						formatCommentBlockWords(indentLengthForComment, indentStringForComment);
						isFirst = false;
					}
					if (isFirst && this.preferences.comment_new_lines_at_block_boundaries) {
						appendToBuffer("/"); //$NON-NLS-1$
					} else if (newLineOfComment || this.preferences.comment_new_lines_at_block_boundaries) {
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
					newLineOfComment = false;
					handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset, true);
				} else {
					commentContent = document.get(comment.sourceStart() + offset,
							comment.sourceEnd() - comment.sourceStart());
					List<String> lines = Arrays.asList(commentContent.split("\r\n?|\n", -1)); //$NON-NLS-1$
					appendToBuffer(lines.get(0));
					// indent all lines, even empty lines
					for (int i = 1; i < lines.size(); i++) {
						insertNewLineForPHPBlockComment(indentLengthForComment, indentStringForComment, false);
						appendToBuffer(lines.get(i).replaceFirst("^\\p{javaWhitespace}+", "")); //$NON-NLS-1$ //$NON-NLS-2$
					}
					handleCharsWithoutComments(comment.sourceStart() + offset, comment.sourceEnd() + offset, true);
				}
				insertNewLine();
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
		resetCommentIndentVariables();
		handleCharsWithoutComments(start, end);
	}

	private boolean canHandlePHPDocComment(PHPDocBlock comment, int offset) throws BadLocationException {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=474332
		// do not handle single-line PHPDoc comment with @var tag inside
		PHPDocTag[] varTags = comment.getTags(TagKind.VAR);
		if (varTags.length != 1) {
			return true;
		}
		int commentStartLine = document.getLineOfOffset(comment.sourceStart() + offset);
		int commentEndLine = document.getLineOfOffset(comment.sourceEnd() + offset);
		return commentStartLine != commentEndLine;
	}

	private boolean endWithNewLineIndent(String string) {
		String indent = getIndent();
		return string.endsWith(lineSeparator + indent);
	}

	private String getIndent() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < indentationLevel * preferences.indentationSize; i++) {
			sb.append(preferences.indentationChar);
		}
		return sb.toString();
	}

	private void resetCommentIndentVariables() {
		indentLengthForComment = -1;
		indentStringForComment = null;
	}

	private void indentForComment(boolean indentationLevelDescending) {

		indent();
		if (indentationLevelDescending || blockEnd) {
			for (int i = 0; i < preferences.indentationSize; i++) {
				appendToBuffer(preferences.indentationChar);
				lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
			}
		}
	}

	private void initCommentIndentVariables(int offset, int startLine,
			org.eclipse.php.core.compiler.ast.nodes.Comment comment, boolean endWithNewLineIndent)
			throws BadLocationException {
		// TODO the value should be calculated from ReplaceEdit changes
		indentLengthForComment = 0;
		indentStringForComment = ""; //$NON-NLS-1$
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=461701
		// Indentation is never set and used when inside of <?= ?> tags.
		// See also method indent().
		if (isPHPEqualTag) {
			return;
		}
		IRegion startRegion = document.getLineInformation(startLine);
		String startLineContent = document
				.get(startRegion.getOffset(), comment.sourceStart() + offset - startRegion.getOffset()).trim();
		// indentStringForComment = FormatterUtils.getLineBlanks(document,
		// startRegion);

		StringBuilder sb = new StringBuilder();
		int lastIndentationLevel = indentationLevel;
		if (endWithNewLineIndent) {
			if (indentationLevelList.size() >= 2) {
				lastIndentationLevel = indentationLevelList.get(indentationLevelList.size() - 2);
			} else {
				lastIndentationLevel = indentationLevelList.get(indentationLevelList.size() - 1);
			}
		} else {
			lastIndentationLevel = indentationLevelList.get(indentationLevelList.size() - 1);
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
		List<String> result = new ArrayList<>();
		for (int i = 0; i < commentWords.size(); i++) {
			String word = commentWords.get(i);
			if (word.trim().length() != 0) {
				result.add(word);
			}

		}
		return result;
	}

	private org.eclipse.php.core.compiler.ast.nodes.Scalar[] getNonblankScalars(
			org.eclipse.php.core.compiler.ast.nodes.Scalar[] texts) {
		int end = texts.length;
		for (int i = texts.length - 1; i >= 0; i--) {
			if (StringUtils.isBlank(texts[i].getValue())) {
				if (end > 0) {
					end--;
				}
			} else {
				break;
			}
		}
		if (end == 0) {
			return new org.eclipse.php.core.compiler.ast.nodes.Scalar[0];
		}
		int start = 0;
		for (int i = 0; i < texts.length; i++) {
			if (StringUtils.isBlank(texts[i].getValue())) {
				if (start < texts.length - 1) {
					start++;
				}
			} else {
				break;
			}
		}
		org.eclipse.php.core.compiler.ast.nodes.Scalar[] result = new org.eclipse.php.core.compiler.ast.nodes.Scalar[end
				- start];
		System.arraycopy(texts, start, result, 0, end - start);
		return result;
	}

	private String[] getNonblankWords(String[] words) {
		int length = words.length;
		for (int i = words.length - 1; i >= 0; i--) {
			if (StringUtils.isBlank(words[i])) {
				length--;
			} else {
				break;
			}
		}
		String[] result = new String[length];
		System.arraycopy(words, 0, result, 0, length);
		return result;
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
			if (StringUtils.isBlank(word)) {
				continue;
			}
			indertWordToCommentBlock(word, indentLength, blanks);
		}
		commentWords = new ArrayList<>();

	}

	private void formatCommentWords(PHPDocTag phpDocTag, boolean insertTag, boolean hasDesc) {
		initCommentWords();
		insertNewLineForPHPDoc();
		formatPHPDocText(commentWords, phpDocTag, insertTag, hasDesc);
		commentWords = new ArrayList<String>();

	}

	private void initCommentWords() {
		String commentContent = join(commentWords, " "); //$NON-NLS-1$
		commentContent = commentContent.trim();
		commentWords = Arrays.asList(MagicMemberUtil.WHITESPACE_SEPERATOR.split(commentContent));
		commentWords = removeEmptyString(commentWords);
	}

	private void insertNewLineForPHPBlockComment(int indentLength, String blanks) {
		insertNewLineForPHPBlockComment(indentLength, blanks, true);
	}

	private void insertNewLineForPHPBlockComment(int indentLength, String blanks, boolean addCommentSymbol) {
		insertNewLine();
		if (indentLength >= 0) {
			appendToBuffer(blanks);
			lineWidth = lineWidth + (indentLength - blanks.length());
		} else {
			indent();
		}
		if (addCommentSymbol) {
			appendToBuffer(" *"); //$NON-NLS-1$
		} else {
			appendToBuffer(" "); //$NON-NLS-1$
		}
	}

	private void insertNewLineForPHPDoc() {
		insertNewLineForPHPDoc(true);
	}

	private void insertNewLineForPHPDoc(boolean addCommentSymbol) {
		insertNewLine();
		indent();
		if (addCommentSymbol) {
			appendToBuffer(" *"); //$NON-NLS-1$
		} else {
			appendToBuffer(" "); //$NON-NLS-1$
		}
	}

	private void formatPHPDocText(List<String> words, PHPDocTag phpDocTag, boolean insertTag, boolean hasDesc) {
		boolean insertSpace = true;
		String tag = ""; //$NON-NLS-1$
		// int indentLength = 0;
		if (phpDocTag != null) {
			// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469402
			tag = phpDocTag.getMatchedTag();
			// if (indentationLevelDescending) {
			// for (int i = 0; i < preferences.indentationSize; i++) {
			// indentLength += (preferences.indentationChar ==
			// CodeFormatterPreferences.SPACE_CHAR) ? 1
			// : 4;
			// }
			// }
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
			if (this.preferences.comment_insert_new_line_for_parameter && phpDocTag.getTagKind() == TagKind.PARAM) {
				if (insertTag && hasDesc) {
					insertNewLineForPHPDoc();
				}

				// we do no extra indentation (after a tag reference) when
				// inserting a tag (i.e. the tag name and the tag reference
				// parts) having no tag description (because no newline was
				// inserted after the tag reference)
				boolean doIndent = !(insertTag && !hasDesc);

				if (doIndent) {
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
							lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
						}

					}
				}
			} else if (!insertTag && this.preferences.comment_indent_root_tags) {
				insertSpaces(tagLength);
			}
		}

		for (String word : words) {
			if (StringUtils.isBlank(word)) {
				continue;
			}
			indertWordToComment(phpDocTag, tagLength, word, insertSpace);
			insertSpace = true;
		}
	}

	private String getTagReference(PHPDocTag phpDocTag) {
		SimpleReference[] reference = phpDocTag.getAllReferencesWithOrigOrder().toArray(new SimpleReference[0]);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < reference.length; i++) {
			if (i > 0 && reference[i - 1] instanceof TypeReference && reference[i] instanceof TypeReference) {
				sb.append(Constants.TYPE_SEPARATOR_CHAR).append(reference[i].getName());
			} else {
				sb.append(" ").append(reference[i].getName()); //$NON-NLS-1$
			}
		}
		return sb.toString();
	}

	private void indertWordToComment(String word) {
		indertWordToComment(null, 0, word, true);
	}

	private void indertWordToCommentBlock(String word, int indentLength, String blanks) {
		if (this.preferences.comment_line_length != 9999 && !newLineOfComment
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

	private void indertWordToComment(PHPDocTag phpDocTag, int tagLength, String word, boolean insertSpace) {
		word = word.trim();
		if (this.preferences.comment_line_length != 9999 && !newLineOfComment
				&& (lineWidth + 1 + word.length() > this.preferences.comment_line_length)) {
			insertNewLineForPHPDoc();
			appendToBuffer(" "); //$NON-NLS-1$

			if (phpDocTag != null) {
				if (this.preferences.comment_indent_root_tags) {
					insertSpaces(tagLength);
				}

				if (this.preferences.comment_indent_root_tags && this.preferences.comment_indent_parameter_description
						&& phpDocTag.getTagKind() == TagKind.PARAM) {
					for (int i = 0; i < preferences.indentationSize; i++) {
						appendToBuffer(preferences.indentationChar);
						lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
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

	private void handleForSemicolon(Expression[] beforExpressions, Expression[] afterExpressions) {
		if (this.preferences.insert_space_before_semicolon_in_for && beforExpressions.length > 0) {
			insertSpace();
		}
		appendToBuffer(SEMICOLON);
		if (this.preferences.insert_space_after_semicolon_in_for && afterExpressions.length > 0) {
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
	private void handlePHPEndTag(int start, int end, String endTagStr) {
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
		if (this.preferences.insert_space_before_semicolon && !isHeredocSemicolon) {
			insertSpace();
		}
		// check if the statement end with ; or ?>
		if (isContainChar(start, end, SEMICOLON)) {
			appendToBuffer(SEMICOLON);
			if (isHeredocSemicolon && isPHPEqualTag) {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=411322
				// always insert a new line after the closing HEREDOC tag
				isPHPEqualTag = false;
				insertNewLine();
				isPHPEqualTag = true;
			}
			isHeredocSemicolon = false;
		} else if (isContainChar(start, end, QUESTION_MARK)) {
			handlePHPEndTag(start, end, "?>"); //$NON-NLS-1$
		} else {
			handlePHPEndTag(start, end, "%>"); //$NON-NLS-1$
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
	 * indent and add the indentation level to the indentation level list
	 * 
	 */
	private void indent() {
		if (!isPHPEqualTag) {
			indentationLevelList.add(indentationLevel);
			for (int i = 0; i < indentationLevel * preferences.indentationSize; i++) {
				appendToBuffer(preferences.indentationChar);
				lineWidth += (preferences.indentationChar == CodeFormatterPreferences.SPACE_CHAR) ? 0 : 3;
			}
		}
	}

	// this method calculates the delta of lines width for AST
	// nodes such as
	// scalars, html in-lines etc... since these types can have multiple lines
	// we still need to check tabs length
	private void updateLinesWidth(ASTNode node) {
		try {
			int lineForStart = document.getLineOfOffset(node.getStart());
			int lineForEnd = document.getLineOfOffset(node.getEnd());

			if (lineForStart == lineForEnd) {
				lineWidth += node.getLength();
			} else {
				lineWidth = document.getLineLength(lineForEnd);
			}
		} catch (BadLocationException e) {
			Logger.logException(e);
		}
	}

	private void insertNewLine() {
		if (!isPHPEqualTag) {
			appendToBuffer(lineSeparator);
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

	private void handleSplittedPHPBlock(int offset, int end) throws BadLocationException {
		IRegion lineRegion = document.getLineInformationOfOffset(offset);
		switch (getPHPStartTag(offset)) {
		case PHP_OPEN_ASP_TAG:
		case PHP_OPEN_SHORT_TAG:
			if (document.get(offset + 2, lineRegion.getOffset() + lineRegion.getLength() - (offset + 2)).trim()
					.length() != 0) {
				insertNewLine();
			}
			handleCharsWithoutComments(offset + 2, end);
			break;
		case PHP_OPEN_SHORT_TAG_WITH_EQUAL:
			handleCharsWithoutComments(offset + 3, end);
			break;
		case PHP_OPEN_TAG:
			if (document.get(offset + 5, lineRegion.getOffset() + lineRegion.getLength() - (offset + 5)).trim()
					.length() != 0) {
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
			if (currChar != ' ' && currChar != '\t' && currChar != '\r' && currChar != '\n') {
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
			Logger.logException(e);
		}
	}

	private void setSpaceAfterBlock(Statement statement) {
		if (statement instanceof Block && !((Block) statement).isCurly()) {
			// do not insert a space when handling "if :" and "else :" blocks
			return;
		}
		if (this.preferences.insert_space_after_closing_brace_in_block) {
			insertSpace();
		}
	}

	@Override
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

		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=468155
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=439568
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=440209
		// https://bugs.eclipse.org/bugs/attachment.cgi?id=245293
		// if (arrayAccess.getArrayType() == ArrayAccess.VARIABLE_ARRAY) {
		// appendToBuffer(CLOSE_BRACKET);
		// } else {
		// appendToBuffer(CLOSE_CURLY);
		// }
		// handleChars(lastPosition, arrayAccess.getEnd());

		indentationLevelDescending = true;
		handleChars(lastPosition, arrayAccess.getEnd() - 1);
		lineWidth++;// we need to add the closing bracket/curly

		return false;
	}

	@Override
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
			int indentationGap = calculateIndentGap(this.preferences.line_wrap_expressions_in_array_init_indent_policy,
					this.preferences.line_wrap_array_init_indentation);

			// work around for close bracket.
			lineWidth++;

			lastPosition = handleCommaList(elements, lastPosition,
					this.preferences.insert_space_before_list_comma_in_array,
					this.preferences.insert_space_after_list_comma_in_array,
					this.preferences.line_wrap_expressions_in_array_init_line_wrap_policy, indentationGap,
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

		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=468155
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=439568
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=440209
		// https://bugs.eclipse.org/bugs/attachment.cgi?id=245293
		// if (arrayCreation.isHasArrayKey()) {
		// appendToBuffer(CLOSE_PARN);
		// } else {
		// appendToBuffer(CLOSE_BRACKET);
		// }
		// handleChars(lastPosition, arrayCreation.getEnd());

		indentationLevelDescending = true;
		handleChars(lastPosition, arrayCreation.getEnd() - 1);
		lineWidth++;// we need to add the closing bracket/parenthesis

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
				lineIndentation = (int) Math.ceil(lineWidth / this.preferences.indentationSize);
			}
			return lineIndentation - indentationLevel;
		case INDENT_ONE:
			return 1;
		default:
			return NO_LINE_WRAP_INDENT;
		}
	}

	@Override
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
			handleChars(arrayElement.getKey().getEnd(), arrayElement.getValue().getStart());
		}
		arrayElement.getValue().accept(this);
		return false;
	}

	@Override
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

	@Override
	public boolean visit(ASTError astError) {
		updateLinesWidth(astError);
		return false;
	}

	@Override
	public boolean visit(BackTickExpression backTickExpression) {
		updateLinesWidth(backTickExpression);
		return false;
	}

	@Override
	public boolean visit(Block block) {
		boolean blockIndentation = false;
		boolean isPHPMode = true;
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
					numberOfLines = this.preferences.blank_lines_after_namespace - numberOfLines;
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
				numberOfLines = this.preferences.blank_lines_after_namespace - numberOfLines;
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
			lastStatementEndOffset = block.getStart();
		} else {
			// start after curly position
			lastStatementEndOffset = block.getStart() + 1;
		}

		List<Statement> statementsList = block.statements();
		Statement[] statements = new Statement[statementsList.size()];
		statements = block.statements().toArray(statements);
		for (int i = 0; i < statements.length; i++) {
			boolean isHtmlStatement = statements[i].getType() == ASTNode.IN_LINE_HTML;
			boolean isASTError = statements[i].getType() == ASTNode.AST_ERROR;
			// fixed bug 441419
			// in case of previous statement is an error there is no need for
			// new lines
			// because the lastStatementEndOffset position move to the current
			// statement start position
			boolean isStatementAfterError = i > 0 ? statements[i - 1].getType() == ASTNode.AST_ERROR : false;
			if (isASTError && i + 1 < statements.length) {
				// move the lastStatementEndOffset position to the start of the
				// next statement start position
				lastStatementEndOffset = statements[i + 1].getStart();
			} else {
				if (isPHPMode && !isHtmlStatement) {
					// PHP -> PHP
					if (!isStatementAfterError && getPHPStartTag(lastStatementEndOffset) != -1) {
						// https://bugs.eclipse.org/bugs/show_bug.cgi?id=489361
						// if previous statement was in a <?= ?> section and
						// now we have a statement in a <?php ?> section,
						// we're still in the PHP -> PHP case, but
						// isPHPEqualTag is outdated
						isPHPEqualTag = getPHPStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
						insertNewLine();
					}
					if (isThrowOrReturnFormatCase(statements)) {
						// do nothing... This is a Throw/Return case
					} else {
						if (!isStatementAfterError) {
							insertNewLines(statements[i]);
							indent();
						}
					}
					if (lastStatementEndOffset <= statements[i].getStart()) {
						handleChars(lastStatementEndOffset, statements[i].getStart());
					}
				} else if (isPHPMode && isHtmlStatement) {
					// PHP -> HTML
					isPHPMode = false;
				} else if (!isPHPMode && !isHtmlStatement) {
					// HTML -> PHP
					if (!isStatementAfterError) {
						isPHPEqualTag = getPHPStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
						insertNewLines(statements[i]);
						indent();
					}
					if (lastStatementEndOffset <= statements[i].getStart()) {
						handleChars(lastStatementEndOffset, statements[i].getStart());
					}
					isPHPMode = true;
				} else {
					// HTML -> HTML
					assert false;
				}
				statements[i].accept(this);
				lastStatementEndOffset = statements[i].getEnd();
				if (isNamespace && i + 1 < statements.length && statements[i].getType() == ASTNode.USE_STATEMENT) {
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
						numberOfLines = this.preferences.blank_lines_after_use_statements - numberOfLines;
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
		if (!isPHPMode) {
			isPHPEqualTag = false;
		}
		// set the block end
		if (blockIndentation) {
			indentationLevel--;
			indentationLevelDescending = true;
		}
		int endPosition = block.getEnd();
		boolean hasComments = false;
		if (startRegionPosition < endPosition && endRegionPosition >= endPosition) {
			try {
				hasComments = hasComments(lastStatementEndOffset, endPosition);
			} catch (Exception e) {
				Logger.logException(e);
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

		if (endPosition > lastStatementEndOffset) {
			// exclude closing curly
			int end = endPosition - 1;
			if (!block.isCurly()) {
				switch (block.getParent().getType()) {
				case ASTNode.SWITCH_STATEMENT:
					end = endPosition - "endswitch".length();//$NON-NLS-1$
					break;
				case ASTNode.WHILE_STATEMENT:
					end = endPosition - "endwhile".length();//$NON-NLS-1$
					break;
				case ASTNode.FOR_STATEMENT:
					end = endPosition - "endfor".length();//$NON-NLS-1$
					break;
				case ASTNode.FOR_EACH_STATEMENT:
					end = endPosition - "endforeach".length();//$NON-NLS-1$
					break;
				case ASTNode.DECLARE_STATEMENT:
					end = endPosition - "enddeclare".length();//$NON-NLS-1$
					break;
				case ASTNode.IF_STATEMENT:
					end = endPosition;
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
			if (block.isCurly()) {
				lineWidth++;// closing curly
			}
		}
		return false;
	}

	// this checks whether it is an IF block (with curly) with one line and the
	// line
	// is either return OR throw expression AND the FORMAT flag is ON
	private boolean isThrowOrReturnFormatCase(Statement[] statements) {
		return preferences.control_statement_keep_guardian_on_one_line && (statements.length == 1)
				&& (statements[0].getParent().getParent() instanceof IfStatement)
				&& (((IfStatement) statements[0].getParent().getParent()).getFalseStatement() == null)
				&& (statements[0].getType() == ASTNode.RETURN_STATEMENT
						|| statements[0].getType() == ASTNode.YIELD_STATEMENT
						|| statements[0].getType() == ASTNode.THROW_STATEMENT);
	}

	@Override
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

	@Override
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

	@Override
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
		handleChars(catchClause.getStart() + 5, catchClause.getClassNames().get(0).getStart());

		// handle the catch identifiers
		catchClause.getClassNames().get(0).accept(this);
		for (int i = 1; i < catchClause.getClassNames().size(); i++) {
			handleChars(catchClause.getClassNames().get(i - 1).getEnd(), catchClause.getClassNames().get(i).getStart());
			insertSpace();
			appendToBuffer('|');
			insertSpace();
			catchClause.getClassNames().get(i).accept(this);
		}

		insertSpace();
		handleChars(catchClause.getClassNames().get(catchClause.getClassNames().size() - 1).getEnd(),
				catchClause.getVariable().getStart());
		catchClause.getVariable().accept(this);

		// set the catch closing parn spaces
		if (this.preferences.insert_space_before_closing_paren_in_catch) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_block,
				this.preferences.insert_space_before_opening_brace_in_block);
		handleChars(catchClause.getVariable().getEnd(), catchClause.getBody().getStart());
		catchClause.getBody().accept(this);
		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDescending = true;
		}
		return false;
	}

	@Override
	public boolean visit(ConstantDeclaration classConstantDeclaration) {
		boolean isFirst = true;

		// handle modifier
		String modifier = classConstantDeclaration.getModifierString();
		appendToBuffer(modifier);
		if (modifier != null && !modifier.isEmpty()) {
			insertSpace();
		}
		appendToBuffer("const"); //$NON-NLS-1$
		insertSpace();
		int lastPosition = classConstantDeclaration.getStart();
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

			List<Expression> initializers = classConstantDeclaration.initializers();

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

	@Override
	public boolean visit(ClassDeclaration classDeclaration) {
		// handle spaces between modifier, 'class' and class name
		String modifier = ClassDeclaration.getModifier(classDeclaration.getModifier());
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
		handleChars(classDeclaration.getStart(), classDeclaration.getName().getStart());

		classDeclaration.getName().accept(this);

		int lastPosition = classDeclaration.getName().getEnd();
		Expression superClass = classDeclaration.getSuperClass();
		// handle super class
		if (superClass != null) {
			appendToBuffer(" extends "); //$NON-NLS-1$
			handleChars(lastPosition, superClass.getStart());
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
			lastPosition = handleCommaList(interfaces, lastPosition,
					this.preferences.insert_space_before_comma_in_implements,
					this.preferences.insert_space_after_comma_in_implements,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy, indentationGap,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_force_split);
		}

		// handle class body
		boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_class,
				this.preferences.insert_space_before_opening_brace_in_class);
		handleChars(lastPosition, classDeclaration.getBody().getStart());

		classDeclaration.getBody().accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDescending = true;
		}
		return false;
	}

	@Override
	public boolean visit(ClassInstanceCreation classInstanceCreation) {
		// insertSpace();
		appendToBuffer("new "); //$NON-NLS-1$
		handleChars(classInstanceCreation.getStart(), classInstanceCreation.getClassName().getStart());
		classInstanceCreation.getClassName().accept(this);
		if (this.preferences.insert_space_before_opening_paren_in_function) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		List<Expression> ctorParams = classInstanceCreation.ctorParams();
		int numberOfCtorParameters = ctorParams.size();

		if (numberOfCtorParameters == 0 && this.preferences.insert_space_between_empty_paren_in_function) {
			insertSpace();
		}

		if (numberOfCtorParameters > 0 && this.preferences.insert_space_after_opening_paren_in_function) {
			insertSpace();
		}

		Expression[] arrayOfParameters = (Expression[]) ctorParams.toArray(new Expression[ctorParams.size()]);
		int indentationGap = calculateIndentGap(
				this.preferences.line_wrap_arguments_in_allocation_expression_indent_policy,
				this.preferences.line_wrap_wrapped_lines_indentation);
		int lastPosition = handleCommaList(arrayOfParameters, classInstanceCreation.getClassName().getEnd(),
				this.preferences.insert_space_before_comma_in_function,
				this.preferences.insert_space_after_comma_in_function,
				this.preferences.line_wrap_arguments_in_allocation_expression_line_wrap_policy, indentationGap,
				this.preferences.line_wrap_arguments_in_allocation_expression_force_split);

		if (numberOfCtorParameters > 0 && this.preferences.insert_space_before_closing_paren_in_function) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);
		if (classInstanceCreation.getAnonymousClassDeclaration() != null) {
			AnonymousClassDeclaration acd = classInstanceCreation.getAnonymousClassDeclaration();
			if (acd.getSuperClass() != null) {
				appendToBuffer(" extends "); //$NON-NLS-1$
				handleChars(lastPosition, acd.getSuperClass().getStart());
				acd.getSuperClass().accept(this);
				lastPosition = acd.getSuperClass().getEnd();
			}

			if (acd.getInterfaces() != null && acd.getInterfaces().size() > 0) {
				appendToBuffer(" implements "); //$NON-NLS-1$

				indentationGap = calculateIndentGap(
						this.preferences.line_wrap_superinterfaces_in_type_declaration_indent_policy,
						this.preferences.line_wrap_wrapped_lines_indentation);
				ASTNode[] nodes = acd.getInterfaces().toArray(new ASTNode[0]);
				lastPosition = handleCommaList(nodes, lastPosition,
						this.preferences.insert_space_before_comma_in_implements,
						this.preferences.insert_space_after_comma_in_implements,
						this.preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy, indentationGap,
						this.preferences.line_wrap_superinterfaces_in_type_declaration_force_split);
			}

			boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_lambda_function,
					this.preferences.insert_space_before_opening_brace_in_function);
			handleChars(lastPosition, acd.getBody().getStart());

			acd.getBody().accept(this);
			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDescending = true;
			}
			lastPosition = acd.getBody().getEnd();
		}
		handleChars(lastPosition, classInstanceCreation.getEnd());
		return false;
	}

	@Override
	public boolean visit(ClassName className) {
		className.getName().accept(this);
		return false;
	}

	@Override
	public boolean visit(CloneExpression cloneExpression) {
		insertSpace();
		lineWidth += 5;// the 'clone'
		// till the expression
		Expression expression = cloneExpression.getExpression();
		handleChars(cloneExpression.getStart() + 5, expression.getStart());

		expression.accept(this);
		return false;
	}

	@Override
	public boolean visit(Comment comment) {
		// do nothing
		return false;
	}

	@Override
	public boolean visit(ConditionalExpression conditionalExpression) {
		boolean isTernaryOperator = conditionalExpression.getOperatorType() == ConditionalExpression.OP_TERNARY;
		// start
		// condition ? true : false
		conditionalExpression.getCondition().accept(this);
		// condition -> if true
		if (this.preferences.insert_space_before_conditional_question_mark) {
			insertSpace();
		}

		appendToBuffer(QUESTION_MARK);
		if (!isTernaryOperator) {
			appendToBuffer(QUESTION_MARK);
		}

		Expression ifTrue = conditionalExpression.getIfTrue();
		Expression ifFalse = conditionalExpression.getIfFalse();
		int offset = conditionalExpression.getCondition().getStart();

		int colonOffset = 0;
		if (ifTrue != null) {
			if (this.preferences.insert_space_after_conditional_question_mark) {
				insertSpace();
			}
			handleChars(conditionalExpression.getCondition().getEnd(), ifTrue.getStart());
			ifTrue.accept(this);
			// iftrue -> iffalse
			if (this.preferences.insert_space_before_conditional_colon) {
				insertSpace();
			}
		} else {
			int length = offset;
			if (ifFalse != null) {
				length = ifFalse.getStart();
			}
			colonOffset = getCharPosition(conditionalExpression.getCondition().getEnd(), length, COLON);
			handleChars(conditionalExpression.getCondition().getEnd(), colonOffset);
		}

		if (isTernaryOperator) {
			appendToBuffer(COLON);

			if (this.preferences.insert_space_after_conditional_colon) {
				insertSpace();
			}
			if (ifTrue != null && ifFalse != null) {
				handleChars(ifTrue.getEnd(), conditionalExpression.getIfFalse().getStart());
			} else if (ifTrue == null && ifFalse != null) {
				handleChars(colonOffset, conditionalExpression.getIfFalse().getStart());
			} else if (ifTrue != null && ifFalse == null) {
				handleChars(ifTrue.getEnd(), colonOffset);
			}
		} else if (ifTrue != null) {
			handleChars(ifTrue.getEnd(), colonOffset);
		}

		if (ifFalse != null && isTernaryOperator) {
			ifFalse.accept(this);
		}
		// end

		return false;
	}

	@Override
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

	@Override
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

			List<Expression> directiveValuesList = declareStatement.directiveValues();
			Expression[] directiveValues = new Expression[directiveValuesList.size()];
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

	@Override
	public boolean visit(DoStatement doStatement) {
		// do-while body
		lineWidth += 2;
		Statement body = doStatement.getBody();
		handleAction(doStatement.getStart() + 2, body, true);
		if (preferences.control_statement_insert_newline_before_while_in_do) {
			insertNewLine();
			indent();
		} else {
			setSpaceAfterBlock(body);
		}

		int positionOfWhile = -1;
		int lastPosition = body.getEnd();
		try {
			positionOfWhile = getFirstTokenOffset(lastPosition, doStatement.getCondition().getStart(), stWhile, true);
		} catch (Exception e) {
			Logger.logException(e);
			return false;
		}
		if (positionOfWhile > lastPosition) {
			handleChars(lastPosition, positionOfWhile + 5); // 5 =
															// "while".length()
			appendToBuffer("while"); //$NON-NLS-1$
			handleChars(positionOfWhile + 5, positionOfWhile + 5);
			lastPosition = positionOfWhile + 5;
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

		handleSemicolon(doStatement.getCondition().getEnd(), doStatement.getEnd());
		return false;
	}

	@Override
	public boolean visit(EchoStatement echoStatement) {
		int lastPosition = echoStatement.getStart();

		List<Expression> expressionList = echoStatement.expressions();
		Expression[] expressions = new Expression[expressionList.size()];
		expressions = expressionList.toArray(expressions);

		// check if short echo syntax (<?=)
		if (expressions.length > 0 && echoStatement.getStart() != expressions[0].getStart()) {
			lastPosition += 4;
			lineWidth += 4;
			insertSpace();
		}

		lastPosition = handleCommaList(expressions, lastPosition, this.preferences.insert_space_before_comma_in_echo,
				this.preferences.insert_space_after_comma_in_echo, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, echoStatement.getEnd());
		return false;
	}

	@Override
	public boolean visit(EmptyStatement emptyStatement) {
		int start = emptyStatement.getStart();
		int end = emptyStatement.getEnd();
		if (isContainChar(start, end, '?')) {
			handlePHPEndTag(start, end, "?>"); //$NON-NLS-1$
		} else if (isContainChar(start, end, '%')) {
			handlePHPEndTag(start, end, "%>"); //$NON-NLS-1$
		} else {
			appendToBuffer(SEMICOLON);
		}
		handleChars(start, end);
		return false;
	}

	@Override
	public boolean visit(ExpressionStatement expressionStatement) {
		Expression expression = expressionStatement.getExpression();
		expression.accept(this);
		handleSemicolon(expression.getEnd(), expressionStatement.getEnd());
		return false;
	}

	@Override
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
		handleChars(fieldAccess.getDispatcher().getEnd(), fieldAccess.getField().getStart());

		fieldAccess.getField().accept(this);
		return false;
	}

	@Override
	public boolean visit(FieldsDeclaration fieldsDeclaration) {
		boolean isFirst = true;
		Variable[] variableNames = fieldsDeclaration.getVariableNames();
		Expression[] initialValues = fieldsDeclaration.getInitialValues();
		int lastPosition = variableNames[0].getStart();

		// handle field modifiers
		String modifier = fieldsDeclaration.getModifierString();
		char firstChar = SPACE;
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

	@Override
	public boolean visit(ForEachStatement forEachStatement) {
		if (this.preferences.insert_space_before_open_paren_in_foreach) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_open_paren_in_foreach) {
			insertSpace();
		}
		lineWidth += 7;
		handleChars(forEachStatement.getStart() + 7, forEachStatement.getExpression().getStart());
		// handle [as key => value] or just [as value]
		forEachStatement.getExpression().accept(this);
		appendToBuffer(" as "); //$NON-NLS-1$
		int lastPosition = forEachStatement.getExpression().getEnd();
		if (forEachStatement.getKey() != null) {
			handleChars(forEachStatement.getExpression().getEnd(), forEachStatement.getKey().getStart());
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

		handleAction(forEachStatement.getValue().getEnd(), forEachStatement.getStatement(), true);
		return false;
	}

	@Override
	public boolean visit(FormalParameter formalParameter) {
		// handle const in PHP4
		int lastPosition = formalParameter.getStart();
		if (formalParameter.isMandatory()) {
			// the word 'const'
			lastPosition += 5;
			lineWidth += 5;
		}

		// handle type
		Expression parameterType = formalParameter.getParameterType();
		if (parameterType != null) {
			if (parameterType instanceof Identifier && ((Identifier) parameterType).isNullable()) {
				appendToBuffer(QUESTION_MARK);
			}
			handleChars(formalParameter.getStart(), parameterType.getStart());
			parameterType.accept(this);
			lastPosition = parameterType.getEnd();
			insertSpace();
		}

		if (formalParameter.isVariadic() && formalParameter.getParameterName() instanceof Variable) {
			appendToBuffer(ELLIPSIS);
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
			handleChars(formalParameter.getParameterName().getEnd(), formalParameter.getDefaultValue().getStart());
		}
		return false;
	}

	@Override
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

		if (this.preferences.insert_space_after_open_paren_in_for && initializations.length > 0) {
			insertSpace();
		}

		lastPosition = handleCommaList(initializations, lastPosition, this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);
		handleForSemicolon(initializations, conditions);
		lastPosition = handleCommaList(conditions, lastPosition, this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);
		handleForSemicolon(conditions, increasements);
		lastPosition = handleCommaList(increasements, lastPosition, this.preferences.insert_space_before_comma_in_for,
				this.preferences.insert_space_after_comma_in_for, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		if (this.preferences.insert_space_before_close_paren_in_for && increasements.length > 0) {
			insertSpace();
		}
		appendToBuffer(CLOSE_PARN);

		// for body
		handleAction(lastPosition, forStatement.getBody(), true);
		return false;
	}

	@Override
	public boolean visit(FunctionDeclaration functionDeclaration) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getDocumentString(functionDeclaration.getStart(), functionDeclaration.getStart() + 8));// append
																												// 'function'

		// handle referenced function with '&'
		if (functionDeclaration.isReference()) {
			buffer.append(" &"); //$NON-NLS-1$
		} else {
			buffer.append(SPACE);
		}

		buffer.append(functionDeclaration.getFunctionName().getName());

		appendToBuffer(buffer.toString());
		handleChars(functionDeclaration.getStart(), functionDeclaration.getFunctionName().getEnd());

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

			List<FormalParameter> parameterList = functionDeclaration.formalParameters();
			FormalParameter[] parameters = new FormalParameter[parameterList.size()];
			parameters = parameterList.toArray(parameters);

			lastPosition = handleCommaList(parameters, lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy, indentationGap,
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

		if (functionDeclaration.getReturnType() != null) {
			appendToBuffer(COLON);
			insertSpace();
			if (functionDeclaration.getReturnType().isNullable()) {
				appendToBuffer(QUESTION_MARK);
			}
			handleChars(lastPosition, functionDeclaration.getReturnType().getStart());
			functionDeclaration.getReturnType().accept(this);

			lastPosition = functionDeclaration.getReturnType().getEnd();
		}

		// handle function body
		if (functionDeclaration.getBody() != null) {
			boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_function,
					this.preferences.insert_space_before_opening_brace_in_function);
			handleChars(lastPosition, functionDeclaration.getBody().getStart());

			functionDeclaration.getBody().accept(this);
			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDescending = true;
			}
		} else {
			handleSemicolon(lastPosition, functionDeclaration.getEnd());
		}

		return false;
	}

	@Override
	public boolean visit(FunctionInvocation functionInvocation) {

		// in case of function print there no need for parenthesis
		Expression functionName = functionInvocation.getFunctionName().getName();
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

	private void innerVisit(FunctionInvocation functionInvocation, boolean addParen) {
		Expression functionName = functionInvocation.getFunctionName().getName();
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

			lastPosition = handleCommaList(parameters, lastPosition,
					this.preferences.insert_space_before_comma_in_function,
					this.preferences.insert_space_after_comma_in_function,
					this.preferences.line_wrap_arguments_in_method_invocation_line_wrap_policy, indentationGap,
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
		handleChars(lastPosition, functionInvocation.getEnd());

	}

	private void handlePrintCall(FunctionInvocation functionInvocation) {

		List<Expression> parametersList = functionInvocation.parameters();
		Expression[] parameters = new Expression[parametersList.size()];
		parameters = parametersList.toArray(parameters);

		boolean hasParenthesis = parameters[0].getType() == ASTNode.PARENTHESIS_EXPRESSION; // print
		// always
		// have
		// one
		// parameter.

		if (hasParenthesis) {
			innerVisit(functionInvocation, false);
			return;
		}

		insertSpace();

		Expression functionName = functionInvocation.getFunctionName().getName();

		int lastPosition = functionName.getEnd();

		int indentationGap = calculateIndentGap(this.preferences.line_wrap_arguments_in_method_invocation_indent_policy,
				this.preferences.line_wrap_wrapped_lines_indentation);

		lastPosition = handleCommaList(parameters, lastPosition, this.preferences.insert_space_before_comma_in_function,
				this.preferences.insert_space_after_comma_in_function,
				this.preferences.line_wrap_arguments_in_method_invocation_line_wrap_policy, indentationGap,
				this.preferences.line_wrap_arguments_in_method_invocation_force_split);

		handleChars(lastPosition, functionInvocation.getEnd());
	}

	@Override
	public boolean visit(FunctionName functionName) {
		return true;
	}

	@Override
	public boolean visit(GlobalStatement globalStatement) {
		int lastPosition = globalStatement.getStart() + 6;
		lineWidth += 6;// the word 'global'
		insertSpace();

		List<Variable> varList = globalStatement.variables();
		Expression[] variables = new Expression[varList.size()];
		variables = varList.toArray(variables);

		lastPosition = handleCommaList(variables, lastPosition, this.preferences.insert_space_before_comma_in_global,
				this.preferences.insert_space_after_comma_in_global, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, globalStatement.getEnd());
		return false;
	}

	@Override
	public boolean visit(Identifier identifier) {
		lineWidth += identifier.getLength();
		return false;
	}

	@Override
	public boolean visit(IfStatement ifStatement) {
		int len;
		try {
			// looking for 'if' or 'elseif'
			len = getFirstTokenOffset(ifStatement.getStart(), ifStatement.getCondition().getStart(), stElseIf,
					true) != -1 ? 6 : 2;
		} catch (Exception e) {
			Logger.logException(e);
			return false;
		}

		// handle the chars between the 'while' and the condition start position
		if (this.preferences.insert_space_before_opening_paren_in_if) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		if (this.preferences.insert_space_after_opening_paren_in_if) {
			insertSpace();
		}
		lineWidth += len; // add the word 'if' OR 'elseif'
		handleChars(ifStatement.getStart() + len, ifStatement.getCondition().getStart());

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
		boolean addNewlineBeforeAction = true;
		if (ifStatement.getTrueStatement().getType() != ASTNode.BLOCK) {
			if (len == 2) {// if
				addNewlineBeforeAction = !(preferences.control_statement_keep_then_on_same_line
						|| (preferences.control_statement_keep_simple_if_on_one_line
								&& ifStatement.getFalseStatement() == null));
			} else if (len == 6) {// elseif
				addNewlineBeforeAction = !preferences.control_statement_keep_then_on_same_line;
			}
		}
		handleAction(ifStatement.getCondition().getEnd(), ifStatement.getTrueStatement(), addNewlineBeforeAction);
		if (ifStatement.getFalseStatement() == null || ifStatement.getFalseStatement().getType() == ASTNode.AST_ERROR) {
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
					setSpaceAfterBlock(ifStatement.getTrueStatement());
				}

				try {
					int lastPosition = internalHandleElse(ifStatement);
					handleAction(lastPosition, ifStatement.getFalseStatement(), true);
				} catch (BadLocationException ble) {
					Logger.logException(ble);
					return false;
				}
				boolean processed = isProcessed(ifStatement);
				if (!((Block) ifStatement.getTrueStatement()).isCurly() && !processed) {
					handleChars(ifStatement.getFalseStatement().getEnd(), ifStatement.getEnd());
					appendToBuffer("endif;"); //$NON-NLS-1$
					handleChars(ifStatement.getEnd(), ifStatement.getEnd());
				}
			} else { // if the true statement is not a block then we should add
				// new line
				insertNewLine();
				indent();

				try {
					int lastPosition = internalHandleElse(ifStatement);
					boolean elseActionInSameLine = preferences.control_statement_keep_else_on_same_line;
					handleAction(lastPosition, ifStatement.getFalseStatement(), !elseActionInSameLine);
				} catch (BadLocationException ble) {
					Logger.logException(ble);
					return false;
				}
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

	private void handleElseIfCases(IfStatement ifStatement) throws BadLocationException {
		IfStatement falseIfStatement = (IfStatement) ifStatement.getFalseStatement();
		boolean elseIndentationLevelChanged = false;
		int trueStatementEnd = ifStatement.getTrueStatement().getEnd();
		boolean isIfToken = false;
		int positionOfElse = -1;
		try {
			// looking for 'if' or 'elseif'
			isIfToken = getFirstTokenOffset(falseIfStatement.getStart(), falseIfStatement.getCondition().getStart(),
					stElseIf, true) == -1;
			// looking for 'else' or 'elseif'
			positionOfElse = getFirstTokenOffset(trueStatementEnd, ifStatement.getFalseStatement().getStart(), stElse,
					true);
			if (positionOfElse == -1) {
				positionOfElse = getFirstTokenOffset(trueStatementEnd, ifStatement.getFalseStatement().getStart(),
						stElseIf, false /* no need to re-scan same range */);
			}
		} catch (Exception e) {
			Logger.logException(e);
			return;
		}

		if (ifStatement.getTrueStatement().getType() == ASTNode.BLOCK) {
			if (preferences.control_statement_insert_newline_before_else_and_elseif_in_if) {
				insertNewLine();
				indent();
			} else {
				if (isIfToken) {
					setSpaceAfterBlock(ifStatement.getTrueStatement());
				}
			}

			if (!isIfToken) {// elseif case
				if (positionOfElse > trueStatementEnd) {
					handleChars(trueStatementEnd, ifStatement.getFalseStatement().getStart());
				} else {
					// fix for setSpaceAfterBlock when no space is required
					// before 'elseif'
					if (!preferences.control_statement_insert_newline_before_else_and_elseif_in_if
							&& preferences.insert_space_after_closing_brace_in_block) {
						insertSpace();
					}
					handleChars(trueStatementEnd, ifStatement.getFalseStatement().getStart());
				}
			} else {
				if (positionOfElse > trueStatementEnd) {
					handleChars(trueStatementEnd, positionOfElse);
					appendToBuffer("else "); //$NON-NLS-1$
					if (!preferences.control_statement_keep_else_if_on_same_line) {
						insertNewLine();
						indentationLevel++;
						elseIndentationLevelChanged = true;
						indent();
					}
					handleChars(positionOfElse, ifStatement.getFalseStatement().getStart());
				} else {
					appendToBuffer("else "); //$NON-NLS-1$
					if (!preferences.control_statement_keep_else_if_on_same_line) {
						insertNewLine();
						indentationLevel++;
						elseIndentationLevelChanged = true;
						indent();
					}
					// the following line also handles the case : '}else' when
					// setSpaceAfterBlock() is called and offset is set to +1
					handleChars(trueStatementEnd, ifStatement.getFalseStatement().getStart());
				}
			}
		} else { // if the true statement is not a block then we should add new
			// line
			insertNewLine();
			indent();
			if (positionOfElse > trueStatementEnd) {
				handleChars(trueStatementEnd, positionOfElse);
				appendToBuffer("else "); //$NON-NLS-1$
				if (!preferences.control_statement_keep_else_if_on_same_line) {
					insertNewLine();
					indentationLevel++;
					elseIndentationLevelChanged = true;
					indent();
				}
				handleChars(positionOfElse, ifStatement.getFalseStatement().getStart());
			} else {
				appendToBuffer(isIfToken ? "else " : EMPTY_STRING); //$NON-NLS-1$
				if (isIfToken && !preferences.control_statement_keep_else_if_on_same_line) {
					insertNewLine();
					indentationLevel++;
					elseIndentationLevelChanged = true;
					indent();
				}
				// in case of: STATEMENT;elseif ...
				handleChars(trueStatementEnd, ifStatement.getFalseStatement().getStart());
			}
		}
		boolean processed = isProcessed(ifStatement);
		ifStatement.getFalseStatement().accept(this);
		if (elseIndentationLevelChanged) {
			indentationLevel--;
		}
		if (ifStatement.getTrueStatement().getType() == ASTNode.BLOCK
				&& !((Block) ifStatement.getTrueStatement()).isCurly() && !processed) {
			handleChars(ifStatement.getFalseStatement().getEnd(), ifStatement.getEnd());
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
	private int internalHandleElse(IfStatement ifStatement) throws BadLocationException {
		int lastPosition = ifStatement.getTrueStatement().getEnd();
		int positionOfElse = -1;
		try {
			// information needed to handleChars between "if" statement end to
			// the "else"...
			positionOfElse = getFirstTokenOffset(lastPosition, ifStatement.getFalseStatement().getStart(), stElse,
					true);
		} catch (Exception e) {
			Logger.logException(e);
			return lastPosition;
		}
		if (positionOfElse > lastPosition) {
			handleChars(lastPosition, positionOfElse + 4); // 4 =
															// "else".length()
			appendToBuffer("else"); //$NON-NLS-1$
			handleChars(positionOfElse + 4, positionOfElse + 4);
			lastPosition = positionOfElse + 4;
		} else {
			appendToBuffer("else"); //$NON-NLS-1$
		}
		return lastPosition;
	}

	@Override
	public boolean visit(IgnoreError ignoreError) {
		lineWidth++;// the '@' sign
		ignoreError.getExpression().accept(this);
		return false;
	}

	@Override
	public boolean visit(Include include) {
		int lastPosition = include.getStart();
		int len = (include.getIncludeType() == Include.IT_INCLUDE || include.getIncludeType() == Include.IT_REQUIRE) ? 7
				: 12;
		lastPosition += len;
		lineWidth += len;// add 'include' 'require' 'require_once'

		insertSpace();
		handleChars(lastPosition, include.getExpression().getStart());

		include.getExpression().accept(this);
		return false;
	}

	@Override
	public boolean visit(InfixExpression infixExpression) {
		int oldIndentationLevel = indentationLevel;
		boolean oldWasBinaryExpressionWrapped = wasBinaryExpressionWrapped;
		boolean forceSplit = this.preferences.line_wrap_binary_expression_force_split;
		if (binaryExpressionLineWrapPolicy == -1) {// not initialized
			binaryExpressionLineWrapPolicy = this.preferences.line_wrap_binary_expression_line_wrap_policy;
			binaryExpressionIndentGap = calculateIndentGap(this.preferences.line_wrap_binary_expression_indent_policy,
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

		if (isStringOperator || this.preferences.insert_space_before_binary_operation) {
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
				binaryExpressionLineWrapPolicy = WRAP_WHEN_NECESSARY;
				insertNewLine();
				indentationLevel += binaryExpressionIndentGap;
				indent();
				wasBinaryExpressionWrapped = true;
			}
			break;
		case WRAP_WHEN_NECESSARY:
			if (lineW > this.preferences.line_wrap_line_split) {
				insertNewLine();
				indent();
				wasBinaryExpressionWrapped = true;
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
					wasBinaryExpressionWrapped = true;
				} else {
					binaryExpressionRevertPolicy = WRAP_FIRST_ELEMENT;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
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
					wasBinaryExpressionWrapped = true;
				} else {
					binaryExpressionRevertPolicy = WRAP_ALL_ELEMENTS;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
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
					wasBinaryExpressionWrapped = true;

					// increase the indentation level after the first element
					indentationLevel++;
					isBinaryExpressionExtraIndentation = true;
				} else {
					binaryExpressionRevertPolicy = WRAP_ALL_ELEMENTS_NO_INDENT_FIRST;
					binaryExpressionLineWrapPolicy = NO_LINE_WRAP;
				}
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
			}
			break;
		case ALWAYS_WRAP_ELEMENT:
			insertNewLine();
			indent();
			wasBinaryExpressionWrapped = true;
			break;
		}

		if (isStringOperator || this.preferences.insert_space_after_binary_operation) {
			insertSpace();
		}

		// handle the chars between the variable to the value
		handleChars(infixExpression.getLeft().getEnd(), infixExpression.getRight().getStart());

		if (binaryExpressionRevertPolicy != -1 && infixExpression == binaryExpressionSavedNode) {
			if (binaryExpressionLineWrapPolicy == WRAP_ALL_ELEMENTS
					&& binaryExpressionRevertPolicy == WRAP_ALL_ELEMENTS_EXCEPT_FIRST) {
				infixExpression.getRight().accept(this);
			} else {
				revert(binaryExpressionSavedBuffer, binaryExpressionSavedChangesIndex);
				binaryExpressionLineWrapPolicy = binaryExpressionRevertPolicy;
				// undo everything
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=506488
				binaryExpressionSavedBuffer = replaceBuffer.toString();
				// must be null to avoid infinite loop (note that
				// binaryExpressionSavedBuffer must stay non-null here to avoid
				// resetting binaryExpressionSavedNode to a non-null value later
				// again)
				binaryExpressionSavedNode = null;
				binaryExpressionSavedChangesIndex = changes.size() - 1;
				binaryExpressionRevertPolicy = -1;
				indentationLevel = oldIndentationLevel;
				wasBinaryExpressionWrapped = oldWasBinaryExpressionWrapped;
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

	@Override
	public boolean visit(InLineHtml inLineHtml) {
		updateLinesWidth(inLineHtml);
		return false;
	}

	@Override
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
		handleChars(instanceOfExpression.getExpression().getEnd(), instanceOfExpression.getClassName().getStart());

		instanceOfExpression.getClassName().accept(this);

		return false;
	}

	@Override
	public boolean visit(InterfaceDeclaration interfaceDeclaration) {
		insertSpace();
		lineWidth += 9;// interface
		handleChars(interfaceDeclaration.getStart() + 9, interfaceDeclaration.getName().getStart());
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
			lastPosition = handleCommaList(interfaces, lastPosition,
					this.preferences.insert_space_before_comma_in_implements,
					this.preferences.insert_space_after_comma_in_implements,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_line_wrap_policy, indentationGap,
					this.preferences.line_wrap_superinterfaces_in_type_declaration_force_split);
		}

		boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_class,
				this.preferences.insert_space_before_opening_brace_in_class);
		handleChars(lastPosition, interfaceDeclaration.getBody().getStart());

		interfaceDeclaration.getBody().accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDescending = true;
		}
		return false;
	}

	@Override
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
		List<Expression> variables = listVariable.variables();
		// XXX: variablesArray will contain one empty Variable object (i.e. with
		// zero-length name) to represent empty list() statements.
		Expression[] variablesArray = variables.toArray(new Expression[variables.size()]);
		lastPosition = handleCommaList(variablesArray, lastPosition, this.preferences.insert_space_before_comma_in_list,
				this.preferences.insert_space_after_comma_in_list, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

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

	@Override
	public boolean visit(MethodDeclaration classMethodDeclaration) {
		// handle method modifiers
		String originalModifier = getDocumentString(classMethodDeclaration.getStart(),
				classMethodDeclaration.getFunction().getStart()).trim();
		StringTokenizer tokenizer = new StringTokenizer(originalModifier);
		StringBuilder strBuffer = new StringBuilder();
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
		handleChars(classMethodDeclaration.getStart(), classMethodDeclaration.getFunction().getStart());
		classMethodDeclaration.getFunction().accept(this);
		return false;
	}

	@Override
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
		handleChars(methodInvocation.getDispatcher().getEnd(), methodInvocation.getMethod().getStart());

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

	@Override
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

	@Override
	public boolean visit(PostfixExpression postfixExpressions) {
		postfixExpressions.getVariable().accept(this);
		if (this.preferences.insert_space_before_postfix_expression) {
			insertSpace();
		}
		appendToBuffer(PostfixExpression.getOperator(postfixExpressions.getOperator()));
		if (this.preferences.insert_space_before_prefix_expression) {
			insertSpace();
		}
		// handle the chars between the variable to the value
		handleChars(postfixExpressions.getVariable().getEnd(), postfixExpressions.getEnd());

		return false;
	}

	@Override
	public boolean visit(PrefixExpression prefixExpression) {
		if (prefixExpression.getOperator() != PrefixExpression.OP_UNPACK
				&& this.preferences.insert_space_before_prefix_expression) {
			insertSpace();
		}
		appendToBuffer(PrefixExpression.getOperator(prefixExpression.getOperator()));
		if (prefixExpression.getOperator() != PrefixExpression.OP_UNPACK
				&& this.preferences.insert_space_after_prefix_expression) {
			insertSpace();
		}
		// handle the chars between the variable to the value
		handleChars(prefixExpression.getStart(), prefixExpression.getVariable().getStart());
		prefixExpression.getVariable().accept(this);
		return false;
	}

	@Override
	public boolean visit(Program program) {
		isPHPEqualTag = false;
		int lastStatementEndOffset = 0;
		boolean isPHPMode = false;
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
		// Logger.logException(e);
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
			boolean isStatementAfterError = i > 0 ? statements[i - 1].getType() == ASTNode.AST_ERROR : false;
			if (isASTError && i + 1 < statements.length) {
				// move the lastStatementEndOffset position to the start of the
				// next statement start position
				lastStatementEndOffset = statements[i + 1].getStart();
			} else {
				if (isPHPMode && !isHtmlStatement) {
					// PHP -> PHP
					if (lastStatementEndOffset > 0) {
						if (!isStatementAfterError && getPHPStartTag(lastStatementEndOffset) != -1) {
							// https://bugs.eclipse.org/bugs/show_bug.cgi?id=489361
							// if previous statement was in a <?= ?> section and
							// now we have a statement in a <?php ?> section,
							// we're still in the PHP -> PHP case, but
							// isPHPEqualTag and indentationLevel are outdated
							if (isPHPEqualTag) {
								// no need to recalculate the indentation if
								// previous statement was in a <?php ?> section
								// (i.e. previous value of isPHPEqualTag was
								// false)
								indentationLevel = getPHPTagIndentationLevel(lastStatementEndOffset);
							}
							isPHPEqualTag = getPHPStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
							insertNewLine();
						}
						if (!isStatementAfterError) {
							insertNewLines(statements[i]);
							indent();
						}
						if (lastStatementEndOffset <= statements[i].getStart()) {
							handleChars(lastStatementEndOffset, statements[i].getStart());
						}
					}
				} else if (isPHPMode && isHtmlStatement) {
					// PHP -> HTML
					if (lastStatementEndOffset > 0) {
						if (lastStatementEndOffset <= statements[i].getStart()) {
							handleChars(lastStatementEndOffset, statements[i].getStart());
						}
					}
					isPHPMode = false;
				} else if (!isPHPMode && !isHtmlStatement) {
					// HTML -> PHP
					if (!isStatementAfterError) {
						isPHPEqualTag = getPHPStartTag(lastStatementEndOffset) == PHP_OPEN_SHORT_TAG_WITH_EQUAL;
						indentationLevel = getPHPTagIndentationLevel(lastStatementEndOffset);
						insertNewLines(statements[i]);
						indent();
					}
					if (lastStatementEndOffset <= statements[i].getStart()) {
						handleChars(lastStatementEndOffset, statements[i].getStart());
					}
					isPHPMode = true;
				} else {
					// first HTML
					isPHPMode = false;
				}
				statements[i].accept(this);
				lastStatementEndOffset = statements[i].getEnd();
				// need check how many new lines will the next statement
				// insert
				if (i + 1 < statements.length && statements[i].getType() == ASTNode.NAMESPACE
						&& statements[i + 1].getType() == ASTNode.NAMESPACE) {
					int numberOfLines = getNumbreOfLines(statements[i + 1]) - 1;
					numberOfLines = this.preferences.blank_lines_between_namespaces - numberOfLines;
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

	@Override
	public boolean visit(Quote quote) {
		updateLinesWidth(quote);
		if (quote.getQuoteType() == Quote.QT_HEREDOC) {
			int i = quote.getEnd();
			if (isContainChar(i, i + 1, SEMICOLON)) {
				isHeredocSemicolon = true;
			} else {
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=411322
				// always insert a new line after the closing HEREDOC tag
				boolean isPHPEqualTagOld = isPHPEqualTag;
				isPHPEqualTag = false;
				insertNewLine();
				isPHPEqualTag = isPHPEqualTagOld;
			}
		}
		return false;
	}

	@Override
	public boolean visit(Reference reference) {
		lineWidth++;// &$a
		reference.getExpression().accept(this);
		return false;
	}

	@Override
	public boolean visit(ReflectionVariable reflectionVariable) {
		lineWidth++;// $$a
		reflectionVariable.getName().accept(this);
		return false;
	}

	@Override
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

	@Override
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
		} else if (yieldExpression.getOperator() == YieldExpression.OP_FROM) {
			insertSpace();
			appendToBuffer(FROM);
			insertSpace();
		}
		Expression expression = yieldExpression.getExpression();
		if (expression != null) {
			handleChars(lastPosition, expression.getStart());
			expression.accept(this);
			lastPosition = expression.getEnd();
		}
		return false;
	}

	@Override
	public boolean visit(Scalar scalar) {
		updateLinesWidth(scalar);
		return false;
	}

	@Override
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
		handleChars(staticConstantAccess.getClassName().getEnd(), staticConstantAccess.getConstant().getStart());
		staticConstantAccess.getConstant().accept(this);
		return false;
	}

	@Override
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
		handleChars(staticFieldAccess.getClassName().getEnd(), staticFieldAccess.getField().getStart());
		staticFieldAccess.getField().accept(this);
		return false;
	}

	@Override
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
		handleChars(staticMethodInvocation.getClassName().getEnd(), staticMethodInvocation.getMethod().getStart());
		staticMethodInvocation.getMethod().accept(this);
		return false;
	}

	@Override
	public boolean visit(StaticStatement staticStatement) {
		int lastPosition = staticStatement.getStart() + 6;
		lineWidth += 6;
		insertSpace();

		List<Expression> expList = staticStatement.expressions();
		Expression[] expressions = new Expression[expList.size()];
		expressions = expList.toArray(expressions);

		lastPosition = handleCommaList(expressions, lastPosition, this.preferences.insert_space_before_comma_in_static,
				this.preferences.insert_space_after_comma_in_static, NO_LINE_WRAP, NO_LINE_WRAP_INDENT, false);

		handleSemicolon(lastPosition, staticStatement.getEnd());
		return false;
	}

	@Override
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
			handleChars(switchCase.getStart() + 4, switchCase.getValue().getStart());
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
				this.indentationLevel += isBreakStatement ? breakStatementIndentation : regularStatementIndentation;
				insertNewLine();
				indent();
				handleChars(lastStatementEndOffset, actions[i].getStart());
				actions[i].accept(this);
				lastStatementEndOffset = actions[i].getEnd();
				this.indentationLevel -= isBreakStatement ? breakStatementIndentation : regularStatementIndentation;
			}
		}
		return false;
	}

	@Override
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
		isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_switch,
				this.preferences.insert_space_before_opening_brace_in_switch);
		Block body = switchStatement.getBody();
		handleChars(expression.getEnd(), body.getStart());

		body.accept(this);

		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDescending = true;
		}
		return false;
	}

	@Override
	public boolean visit(ThrowStatement throwStatement) {
		insertSpace();
		lineWidth += 5;
		Expression expr = throwStatement.getExpression();
		handleChars(throwStatement.getStart() + 5, expr.getStart());

		expr.accept(this);

		handleSemicolon(expr.getEnd(), throwStatement.getEnd());

		return false;
	}

	@Override
	public boolean visit(TryStatement tryStatement) {
		boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_block,
				this.preferences.insert_space_before_opening_brace_in_block);
		lineWidth += 3;
		Block body = tryStatement.getBody();
		handleChars(tryStatement.getStart() + 3, body.getStart());
		body.accept(this);
		if (isIndentationAdded) {
			indentationLevel--;
			indentationLevelDescending = true;
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
			handleChars(lastStatementEndOffset, tryStatement.finallyClause().getStart());
			tryStatement.finallyClause().accept(this);
			lastStatementEndOffset = tryStatement.finallyClause().getEnd();
		}
		return false;
	}

	@Override
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

	@Override
	public boolean visit(Variable variable) {
		if (variable.isDollared()) {
			lineWidth++;
		}
		variable.getName().accept(this);
		return false;
	}

	@Override
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
		handleChars(whileStatement.getStart() + 5, whileStatement.getCondition().getStart());

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

	@Override
	public boolean visit(NamespaceDeclaration namespaceDeclaration) {
		appendToBuffer("namespace"); //$NON-NLS-1$
		insertSpace();
		int lastPosition = namespaceDeclaration.getStart();
		if (namespaceDeclaration.getName() != null) {
			handleChars(namespaceDeclaration.getStart(), namespaceDeclaration.getName().getStart());
			namespaceDeclaration.getName().accept(this);

			lastPosition = namespaceDeclaration.getName().getEnd();
		}
		if (namespaceDeclaration.isBracketed()) {
			// handle class body
			boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_class,
					this.preferences.insert_space_before_opening_brace_in_class);
			handleChars(lastPosition, namespaceDeclaration.getBody().getStart());

			namespaceDeclaration.getBody().accept(this);

			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDescending = true;
			}
		} else {
			handleSemicolon(lastPosition, namespaceDeclaration.getBody().getStart());
			namespaceDeclaration.getBody().accept(this);
		}
		return false;
	}

	@Override
	public boolean visit(NamespaceName namespaceName) {
		if (namespaceName.isGlobal()) {
			appendToBuffer("\\"); //$NON-NLS-1$
		}

		if (namespaceName.isCurrent()) {
			appendToBuffer("namespace\\"); //$NON-NLS-1$
		}
		List<Identifier> segments = namespaceName.segments();
		if (segments.size() > 0) {
			handleChars(namespaceName.getStart(), segments.get(0).getStart());
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

	@Override
	public boolean visit(UseStatement useStatement) {
		int lastPosition = useStatement.getStart() + 3;
		lineWidth += 3;// the word 'use'
		insertSpace();

		appendStatementType(useStatement.getStatementType());

		int lineWrapPolicy = NO_LINE_WRAP;
		int indentationGap = NO_LINE_WRAP_INDENT;
		boolean spaceBeforeComma = this.preferences.insert_space_before_comma_in_global;
		boolean spaceAfterComma = this.preferences.insert_space_after_comma_in_global;
		boolean forceSplit = false;
		if (useStatement.getNamespace() != null) {
			insertSpace();
			handleChars(lastPosition, useStatement.getNamespace().getStart());
			useStatement.getNamespace().accept(this);

			lastPosition = useStatement.getNamespace().getEnd();
			insertSpace();
			appendToBuffer(OPEN_CURLY);

			lineWrapPolicy = WRAP_ALL_ELEMENTS;
			forceSplit = true;
			indentationGap = 1;
			spaceBeforeComma = false;
			spaceAfterComma = false;
		}

		List<UseStatementPart> parts = useStatement.parts();
		lastPosition = handleCommaList(parts.toArray(new ASTNode[parts.size()]), lastPosition, spaceBeforeComma,
				spaceAfterComma, lineWrapPolicy, indentationGap, forceSplit);

		if (useStatement.getNamespace() != null) {
			insertNewLine();
			indent();
			appendToBuffer(CLOSE_CURLY);
		}

		handleSemicolon(lastPosition, useStatement.getEnd());
		return false;
	}

	@Override
	public boolean visit(UseStatementPart useStatementPart) {
		appendStatementType(useStatementPart.getStatementType());

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

	private void appendStatementType(int statementType) {
		if (statementType == UseStatement.T_FUNCTION) {
			appendToBuffer(PHPTokenNames.getName(CompilerParserConstants.T_FUNCTION));
			insertSpace();
		} else if (statementType == UseStatement.T_CONST) {
			appendToBuffer(PHPTokenNames.getName(CompilerParserConstants.T_CONST));
			insertSpace();
		}
	}

	@Override
	public boolean visit(LambdaFunctionDeclaration lambdaFunctionDeclaration) {
		StringBuilder buffer = new StringBuilder();
		if (lambdaFunctionDeclaration.isStatic()) {
			buffer.append("static "); //$NON-NLS-1$
		}
		buffer.append(
				getDocumentString(lambdaFunctionDeclaration.getStart(), lambdaFunctionDeclaration.getStart() + 8));// append
																													// 'function'

		// handle referenced function with '&'
		if (lambdaFunctionDeclaration.isReference()) {
			buffer.append(" &"); //$NON-NLS-1$
		}

		appendToBuffer(buffer.toString());
		handleChars(lambdaFunctionDeclaration.getStart(), lambdaFunctionDeclaration.getStart() + 8);

		if (this.preferences.insert_space_before_opening_paren_in_function_declaration
				// https://bugs.eclipse.org/bugs/show_bug.cgi?id=492770
				|| !lambdaFunctionDeclaration.isReference()) {
			insertSpace();
		}
		appendToBuffer(OPEN_PARN);
		List<FormalParameter> formalParameters = lambdaFunctionDeclaration.formalParameters();
		ASTNode[] params = (FormalParameter[]) formalParameters.toArray(new FormalParameter[formalParameters.size()]);
		int lastPosition = lambdaFunctionDeclaration.getStart() + 8;
		if (params.length > 0) {
			if (this.preferences.insert_space_after_opening_paren_in_function_declaration) {
				insertSpace();
			}
			int indentationGap = calculateIndentGap(
					this.preferences.line_wrap_parameters_in_method_declaration_indent_policy,
					this.preferences.line_wrap_wrapped_lines_indentation);
			lastPosition = handleCommaList(params, lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy, indentationGap,
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

		List<Expression> variables = lambdaFunctionDeclaration.lexicalVariables();

		if (variables.size() > 0) {
			// TODO: Added a new preference?
			insertSpace();
			appendToBuffer("use"); //$NON-NLS-1$
			insertSpace();

			appendToBuffer(OPEN_PARN);
			if (this.preferences.insert_space_before_opening_paren_in_function_declaration) {
				insertSpace();
			}
			ASTNode[] vars = (Expression[]) variables.toArray(new Expression[variables.size()]);

			lastPosition = handleCommaList(vars, lastPosition,
					this.preferences.insert_space_before_comma_in_function_declaration,
					this.preferences.insert_space_after_comma_in_function_declaration,
					this.preferences.line_wrap_parameters_in_method_declaration_line_wrap_policy, 0,
					this.preferences.line_wrap_parameters_in_method_declaration_force_split);

			if (this.preferences.insert_space_before_closing_paren_in_function_declaration) {
				insertSpace();
			}

			appendToBuffer(CLOSE_PARN);
		}

		if (lambdaFunctionDeclaration.getReturnType() != null) {
			appendToBuffer(COLON);
			insertSpace();
			if (lambdaFunctionDeclaration.getReturnType().isNullable()) {
				appendToBuffer(QUESTION_MARK);
			}
			handleChars(lastPosition, lambdaFunctionDeclaration.getReturnType().getStart());
			lambdaFunctionDeclaration.getReturnType().accept(this);

			lastPosition = lambdaFunctionDeclaration.getReturnType().getEnd();
		}

		// handle function body
		if (lambdaFunctionDeclaration.getBody() != null) {
			boolean isIndentationAdded = handleBlockOpenBrace(this.preferences.brace_position_for_lambda_function,
					this.preferences.insert_space_before_opening_brace_in_function);
			handleChars(lastPosition, lambdaFunctionDeclaration.getBody().getStart());

			lambdaFunctionDeclaration.getBody().accept(this);
			if (isIndentationAdded) {
				indentationLevel--;
				indentationLevelDescending = true;
			}
		} else {
			handleSemicolon(lastPosition, lambdaFunctionDeclaration.getEnd());
		}

		return false;
	}

	@Override
	public boolean visit(TraitUseStatement node) {
		if (node.getTraitList().size() > 0) {
			// int lastPosition = node.getStart() + 3;
			lineWidth += 3;// the word 'use'
			insertSpace();
			handleChars(node.getStart() + 3, node.getTraitList().get(0).getStart());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.format.ICodeFormattingProcessor#
	 * createIndentationString(int)
	 */
	@Override
	public @NonNull String createIndentationString(int indentationUnits) {
		if (indentationUnits < 0) {
			throw new IllegalArgumentException();
		}
		int tabs = 0;
		tabs = indentationUnits;
		if (tabs == 0) {
			return ""; //$NON-NLS-1$
		}

		StringBuilder buffer = new StringBuilder(tabs * preferences.indentationSize);
		for (int i = 0; i < tabs * preferences.indentationSize; i++) {
			buffer.append(preferences.indentationChar);
		}
		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.format.ICodeFormattingProcessor#
	 * getTextEdits ()
	 */
	@Override
	public @NonNull MultiTextEdit getTextEdits() {
		List<ReplaceEdit> allChanges = getChanges();
		MultiTextEdit rootEdit = new MultiTextEdit();
		for (ReplaceEdit edit : allChanges) {
			TextEdit textEdit = new ReplaceEdit(edit.getOffset(), edit.getLength(), edit.getText());
			rootEdit.addChild(textEdit);
		}
		return rootEdit;
	}

	private boolean isInSingleLine(ReplaceEdit edit, IRegion[] partitions) {
		for (int i = 0; i < partitions.length; i++) {
			IRegion iTypedRegion = partitions[i];
			if (edit.getOffset() >= iTypedRegion.getOffset()
					&& edit.getOffset() + edit.getLength() <= iTypedRegion.getOffset() + iTypedRegion.getLength()) {
				return true;
			}
		}
		return false;
	}

	private List<IRegion> getAllPHPRegionsInContainer(int containerOffset, ITextRegionContainer container,
			ITypedRegion partition) {
		int start = partition.getOffset();
		int end = partition.getOffset() + partition.getLength();
		List<IRegion> result = new ArrayList<>();
		Iterator<?> regionsIt = container.getRegions().iterator();
		IRegion current = null;

		while (regionsIt.hasNext()) {
			ITextRegion region = (ITextRegion) regionsIt.next();
			if (containerOffset + region.getStart() < start) {
				continue;
			}
			if (containerOffset + region.getStart() + region.getLength() > end) {
				break;
			}
			if (PHPRegionContext.PHP_OPEN.equals(region.getType())) {
				if (current != null) {
					result.add(current);
				}
				current = new Region(containerOffset + region.getStart(), region.getLength());
			} else if (PHPRegionContext.PHP_CONTENT.equals(region.getType())) {
				if (current != null) {
					if (current.getOffset() + current.getLength() == containerOffset + region.getStart()) {
						current = new Region(current.getOffset(), current.getLength() + region.getLength());
					} else {
						result.add(current);
						current = new Region(containerOffset + region.getStart(), region.getLength());
					}
				} else {
					current = new Region(containerOffset + region.getStart(), region.getLength());
				}
			} else if (PHPRegionContext.PHP_CLOSE.equals(region.getType())) {
				if (current != null) {
					if (current.getOffset() + current.getLength() == containerOffset + region.getStart()) {
						result.add(new Region(current.getOffset(), current.getLength() + region.getLength()));
					} else {
						result.add(current);
						result.add(new Region(containerOffset + region.getStart(), region.getLength()));
					}
				} else {
					result.add(new Region(containerOffset + region.getStart(), region.getLength()));
				}
				current = null;
			}
		}
		if (current != null) {
			result.add(current);
		}

		return result;
	}

	/**
	 * PHP Partitions can contain contiguous &lt;?php ?&gt; regions (see
	 * {@link PHPStructuredTextPartitioner#computePartitioning(int, int)}), we
	 * have to split them manually.
	 * 
	 * @param partition
	 *            PHP Partition
	 * @return individual &lt;?php ?&gt; regions
	 */
	private List<IRegion> getAllPHPRegionsInPHPPartition(ITypedRegion partition) {
		assert document instanceof IStructuredDocument;
		assert PHPPartitionTypes.PHP_DEFAULT.equals(partition.getType());

		List<IRegion> regions = new ArrayList<>();
		int offset = partition.getOffset();
		int end = partition.getOffset() + partition.getLength();

		while (offset < end) {
			IStructuredDocumentRegion sdRegion = ((IStructuredDocument) document).getRegionAtCharacterOffset(offset);
			if (sdRegion == null) {
				return regions;
			}

			ITextRegion phpScriptRegion = sdRegion.getRegionAtCharacterOffset(offset);

			if (phpScriptRegion instanceof ITextRegionContainer) {
				regions.addAll(getAllPHPRegionsInContainer(sdRegion.getStartOffset() + phpScriptRegion.getStart(),
						(ITextRegionContainer) phpScriptRegion, partition));
				offset = sdRegion.getStartOffset() + phpScriptRegion.getStart() + phpScriptRegion.getLength();
			} else {
				// sdRegion contains opening PHP tag, PHP content and closing
				// PHP tag
				regions.add(new Region(sdRegion.getStartOffset(), sdRegion.getLength()));
				offset = sdRegion.getStartOffset() + sdRegion.getLength();
			}
		}
		return regions;
	}

	private IRegion[] getAllSingleLine(ITypedRegion[] partitions) throws BadLocationException {
		List<IRegion> result = new ArrayList<>();
		if (document instanceof IStructuredDocument) {
			for (int i = 0; i < partitions.length; i++) {
				ITypedRegion partition = partitions[i];
				if (PHPPartitionTypes.PHP_DEFAULT.equals(partition.getType())) {
					for (IRegion phpRegion : getAllPHPRegionsInPHPPartition(partition)) {
						if (isPHPRegionOnSingleLine(phpRegion.getOffset(), phpRegion.getLength())) {
							result.add(phpRegion);
						}
					}
				}
			}
		}
		return result.toArray(new IRegion[result.size()]);
	}

	private boolean isPHPRegionOnSingleLine(int start, int length) throws BadLocationException {
		assert length >= 0;
		int endTagLength = "?>".length(); //$NON-NLS-1$
		if (length < endTagLength || document.getLineOfOffset(start) != document.getLineOfOffset(start + length - 1)) {
			return false;
		}
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=418959
		// Tag "?>" is optional to end a PHP region.
		// If this tag is missing at the very end of a PHP region,
		// this region will not be considered as a single line.
		return "?>".equals(document.get(start + length - endTagLength, //$NON-NLS-1$
				endTagLength));
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