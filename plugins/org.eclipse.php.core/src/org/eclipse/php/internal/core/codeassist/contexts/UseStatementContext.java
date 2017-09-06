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

import org.eclipse.dltk.annotations.NonNull;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.PHPVersion;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.documentModel.parser.regions.PHPRegionTypes;
import org.eclipse.php.internal.core.util.text.PHPTextSequenceUtilities;
import org.eclipse.php.internal.core.util.text.TextSequence;
import org.eclipse.php.internal.core.util.text.TextSequenceUtilities;
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

	protected TYPES type;
	protected TextSequence rebuiltUseStatementText;
	protected TextSequence longestStatementTextBeforeCursor;
	protected TextSequence biggestCommonStatementText;
	protected boolean isCursorInsideGroupStatement;

	public enum TYPES {
		NONE, TRAIT, USE, GROUP
	}

	private boolean hasUsePrefix(TextSequence statementText) {
		if (statementText.length() >= 4) {
			if ("use".equalsIgnoreCase( //$NON-NLS-1$
					statementText.subSequence(0, 3).toString())
					&& Character.isWhitespace(statementText.subSequence(3, 4).charAt(0))) {
				return true;
			}
		}
		return false;
	}

	private boolean buildUseStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean isClassStatementContext) {
		ContextRegion[] foundDelimiter = new ContextRegion[1];
		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true, foundDelimiter);
		biggestCommonStatementText = longestStatementTextBeforeCursor = rebuiltUseStatementText = statementText;
		type = TYPES.NONE;
		isCursorInsideGroupStatement = false;

		boolean hasUsePrefix = hasUsePrefix(statementText);
		if (hasUsePrefix) {
			if (isClassStatementContext && getPHPVersion().isLessThan(PHPVersion.PHP5_4)) {
				return false;
			}
			type = isClassStatementContext ? TYPES.TRAIT : TYPES.USE;
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
					.getStatement(foundDelimiter[0].getStart(), sdRegion, true, foundDelimiter);
			if (hasUsePrefix(statementTextBeforeOpeningCurly)) {
				// 1. remove spaces at the end of "use X\Y\ "
				String s1 = statementTextBeforeOpeningCurly.toString();
				int endS1 = PHPTextSequenceUtilities.readBackwardSpaces(s1, s1.length());
				// 2. look for multiple statement parts separated by ',' in
				// "A, B, \C\D" and remove leading '\' in the last statement
				// part, to only keep "C\D"
				String s2 = statementText.toString();
				int idxS2 = PHPTextSequenceUtilities.readNamespaceStartIndex(s2, s2.length(), false);
				if (idxS2 < s2.length() && s2.charAt(idxS2) == NamespaceReference.NAMESPACE_SEPARATOR) {
					idxS2++;
				}
				// 3. merge statementTextBeforeCurly and statementText by
				// cutting useless characters, to store statement "use X\Y\C\D"
				// in rebuiltUseStatementText
				// !!! We must not forget to take removed comments in account
				// when calculating new statement offsets and lengths !!!
				int start1 = statementTextBeforeOpeningCurly.getOriginalOffset(0);
				TextSequence res = TextSequenceUtilities.createTextSequence(statementTextBeforeOpeningCurly.getSource(),
						start1, offset - start1);
				res = res.cutTextSequence(endS1, offset - (s2.length() - idxS2) - start1);
				// 4. store "X\Y\" in biggestCommonStatementText and "C\D" in
				// longestStatementTextBeforeCursor
				// !!! We must not forget to take removed comments in account
				// when calculating new statement offsets and lengths !!!
				biggestCommonStatementText = TextSequenceUtilities
						.createTextSequence(statementTextBeforeOpeningCurly.getSource(), start1, endS1);
				longestStatementTextBeforeCursor = TextSequenceUtilities.createTextSequence(statementText.getSource(),
						offset - (s2.length() - idxS2), s2.length() - idxS2);
				rebuiltUseStatementText = res;
				type = TYPES.GROUP;
				isCursorInsideGroupStatement = true;

				return true;
			}
		}

		return false;
	}

	/**
	 * @return true when type is TYPES.GROUP and cursor position is after '{',
	 *         false otherwise
	 */
	public boolean isCursorInsideGroupStatement() {
		return isCursorInsideGroupStatement;
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
	 * '{'. Returned value has no special meaning for other use statement types.
	 * For example, with "use X\Y\ { A, B, \C\D| };" this method would return
	 * "X\Y\".
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public String getGroupPrefixBeforeOpeningCurly() {
		int statementLength = biggestCommonStatementText.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(biggestCommonStatementText, statementLength); // read
		// whitespace
		int prefixStart = PHPTextSequenceUtilities.readNamespaceStartIndex(biggestCommonStatementText, prefixEnd, true);
		return prefixStart < 0 ? "" : biggestCommonStatementText.subSequence(prefixStart, prefixEnd).toString(); //$NON-NLS-1$
	}

	/**
	 * When isCursorInsideGroupStatement() is true, only the prefix part after
	 * '{' is returned (leading '\' excluded), otherwise same content as
	 * getPrefix() is returned. For example, with "use X\Y\ { A, B, \C\D| };"
	 * this method would return "C\D".
	 * 
	 * @return
	 */
	@SuppressWarnings("null")
	public String getLongestPrefixBeforeCursor() {
		if (hasWhitespaceBeforeCursor()) {
			return ""; //$NON-NLS-1$
		}
		int statementLength = longestStatementTextBeforeCursor.length();
		int prefixEnd = PHPTextSequenceUtilities.readBackwardSpaces(longestStatementTextBeforeCursor, statementLength); // read
		// whitespace
		int prefixStart = PHPTextSequenceUtilities.readIdentifierStartIndex(getPHPVersion(),
				longestStatementTextBeforeCursor, prefixEnd, true);
		return prefixStart < 0 ? "" : longestStatementTextBeforeCursor.subSequence(prefixStart, prefixEnd).toString(); //$NON-NLS-1$
	}

	@Override
	public int getReplacementStart() {
		return getOffset() - getLongestPrefixBeforeCursor().length();
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
