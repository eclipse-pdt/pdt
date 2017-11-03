/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.contexts;

import java.util.regex.Pattern;

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.annotations.Nullable;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.MagicMemberUtil;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.wst.sse.core.internal.parser.ContextRegion;
import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

/**
 * This context represents the state when staying in a use statement. <br/>
 * Examples:
 * 
 * <pre>
 *  1. use |
 *  2. use A\B| 
 *  3. use A as |
 *  4. use A as B|
 *  5. use A\{ B, \C| }
 *  etc...
 * </pre>
 * 
 * @author blind
 */
public abstract class UseStatementContext extends StatementContext {

	public static final String FUNCTION_KEYWORD = "function"; //$NON-NLS-1$
	public static final String CONST_KEYWORD = "const"; //$NON-NLS-1$
	public static final String USE_KEYWORD = "use"; //$NON-NLS-1$

	public static final Pattern USE_GROUP_SEPARATOR = Pattern.compile("[,]\\p{javaWhitespace}*"); //$NON-NLS-1$

	private TYPES type;
	private TextSequence rebuiltUseStatementText;
	private TextSequence longestPrefixTextBeforeCursor;
	private TextSequence biggestCommonStatementText;
	private boolean isCursorInsideGroupStatement;

	protected boolean isUseFunctionStatement;
	protected boolean isUseConstStatement;

	public enum TYPES {
		NONE, TRAIT, USE, USE_GROUP
	}

	@Override
	public boolean isAbsolute() {
		return getType() != TYPES.TRAIT;
	}

	private boolean hasUsePrefix(TextSequence statementText) {
		if (statementText.length() >= 4) {
			if (USE_KEYWORD.equalsIgnoreCase(statementText.subSequence(0, 3).toString())
					&& Character.isWhitespace(statementText.subSequence(3, 4).charAt(0))) {
				return true;
			}
		}
		return false;
	}

	private void findConstOrFunctionWord(TextSequence statementText, int wordIdx, boolean isAfterOpeningCurly) {

		String[] groupStatements = isAfterOpeningCurly ? USE_GROUP_SEPARATOR.split(statementText.toString(), -1)
				: new String[] { statementText.toString() };
		String[] words = MagicMemberUtil.WHITESPACE_SEPERATOR.split(groupStatements[groupStatements.length - 1], -1);

		if (wordIdx >= words.length) {
			return;
		}

		// we should at least have one non-empty word after position wordIdx
		// or an empty word (which then means that cursor is not directly at the
		// end of the last non-empty word)
		if (wordIdx == words.length - 1) {
			return;
		}

		if (CONST_KEYWORD.equalsIgnoreCase(words[wordIdx])) {
			isUseConstStatement = true;
		} else if (FUNCTION_KEYWORD.equalsIgnoreCase(words[wordIdx])) {
			isUseFunctionStatement = true;
		}
	}

	private boolean buildUseStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean isClassStatementContext) {
		ContextRegion[] foundDelimiter = new ContextRegion[1];
		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true, null, 0,
				foundDelimiter);
		biggestCommonStatementText = longestPrefixTextBeforeCursor = rebuiltUseStatementText = statementText;
		type = TYPES.NONE;
		isCursorInsideGroupStatement = isUseFunctionStatement = isUseConstStatement = false;

		boolean hasUsePrefix = hasUsePrefix(statementText);
		if (hasUsePrefix) {
			if (isClassStatementContext) {
				if (getPHPVersion().isLessThan(PHPVersion.PHP5_4)) {
					return false;
				}
				type = TYPES.TRAIT;
			} else {
				type = TYPES.USE;
				findConstOrFunctionWord(statementText, 1, false);
			}
			return true;
		}
		if (isClassStatementContext) {
			return false;
		}

		if (!getPHPVersion().isLessThan(PHPVersion.PHP7_0) && foundDelimiter[0] != null
				&& foundDelimiter[0].getType() == PHPRegionTypes.PHP_CURLY_OPEN
				&& foundDelimiter[0].getStart() >= 4 /* "use " */) {
			// Check for "grouped use statements" like
			// "use X\Y\ { A, B, \C\D| };" with '|' the cursor position.
			// When found, at this point statementText will contain "A, B, \C\D"
			// and statementTextBeforeCurly will contain "use X\Y\ ".
			TextSequence statementTextBeforeOpeningCurly = PHPTextSequenceUtilities
					.getStatement(foundDelimiter[0].getStart(), sdRegion, true);
			if (hasUsePrefix(statementTextBeforeOpeningCurly)) {
				findConstOrFunctionWord(statementTextBeforeOpeningCurly, 1, false);
				findConstOrFunctionWord(statementText, 0, true);
				// 1. remove spaces at the end of "use X\Y\ "
				String s1 = statementTextBeforeOpeningCurly.toString();
				int endS1 = PHPTextSequenceUtilities.readBackwardSpaces(s1, s1.length());
				// 2. look for multiple statement parts separated by ',' in
				// "A, B, \C\D" and remove leading '\' in the last statement
				// part, to only keep "C\D"
				String s2 = statementText.toString();
				int idxS2, endS2;
				if (isUseFunctionStatement || isUseConstStatement) {
					endS2 = s2.length();
				} else {
					endS2 = PHPTextSequenceUtilities.readBackwardSpaces(s2, s2.length());
				}
				idxS2 = PHPTextSequenceUtilities.readNamespaceStartIndex(s2, endS2, false);
				int idx3 = idxS2;
				if (idxS2 < s2.length() && s2.charAt(idxS2) == NamespaceReference.NAMESPACE_SEPARATOR) {
					idxS2++;
				}
				// 3. merge statementTextBeforeCurly and statementText by
				// cutting unwanted characters, to store statement "use X\Y\C\D"
				// in rebuiltUseStatementText
				TextSequence fullStatementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true,
						new String[] { PHPRegionTypes.PHP_CURLY_OPEN }, 1, null);
				fullStatementText = fullStatementText.cutTextSequence(endS1,
						// NB: fullStatementText.length() can be greater than
						// statementTextBeforeOpeningCurly.length() +
						// statementText.length()
						fullStatementText.length() - (statementText.length() - idxS2));
				// 4. store "X\Y\" in biggestCommonStatementText and "C\D" in
				// longestStatementTextBeforeCursor
				biggestCommonStatementText = statementTextBeforeOpeningCurly.cutTextSequence(endS1,
						statementTextBeforeOpeningCurly.length());
				longestPrefixTextBeforeCursor = statementText.cutTextSequence(0, idx3);
				rebuiltUseStatementText = fullStatementText;
				type = TYPES.USE_GROUP;
				isCursorInsideGroupStatement = true;

				return true;
			}
		}

		return false;
	}

	public TYPES getType() {
		return type;
	}

	@Override
	@SuppressWarnings("null")
	@NonNull
	public TextSequence getStatementText() {
		return rebuiltUseStatementText;
	}

	/**
	 * Prefix part of a "grouped use statement", in the statement part before
	 * '{'. Returned value is null for other statement types. For example, with
	 * "use X\Y\ { A, B, \C\D| };" this method would return "X\Y\".
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	@Nullable
	public String getGroupPrefixBeforeOpeningCurly() {
		if (!isCursorInsideGroupStatement) {
			return null;
		}
		int statementLength = biggestCommonStatementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(biggestCommonStatementText, statementLength); // read
		// whitespace
		int prefixStart = PHPTextSequenceUtilities.readNamespaceStartIndex(biggestCommonStatementText, prefixEnd, true);
		return prefixStart < 0 ? "" : biggestCommonStatementText.subSequence(prefixStart, prefixEnd).toString(); //$NON-NLS-1$
	}

	/**
	 * When isCursorInsideGroupStatement() is true, only the prefix part after
	 * '{' is returned, otherwise same content as getPrefix() is returned. For
	 * example, with "use X\Y\ { A, B, \C\D| };" this method would return
	 * "\C\D".
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	@NonNull
	public String getPrefixBeforeCursor() {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		int statementLength = longestPrefixTextBeforeCursor.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(longestPrefixTextBeforeCursor, statementLength); // read
		// whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(getPHPVersion(),
				longestPrefixTextBeforeCursor, prefixEnd, true);
		return prefixStart < 0 ? "" : longestPrefixTextBeforeCursor.subSequence(prefixStart, prefixEnd).toString(); //$NON-NLS-1$
	}

	@Override
	public int getReplacementStart() {
		return getOffset() - getPrefixBeforeCursor().length();
	}

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}
		boolean isClassStatementContext = new ClassStatementContext().isValid(sourceModule, offset, requestor);
		return buildUseStatement(offset, getStructuredDocumentRegion(), isClassStatementContext);
	}
}
