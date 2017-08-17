/*******************************************************************************
 * Copyright (c) 2009, 2017 IBM Corporation and others.
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
 * @author michael
 */
public abstract class UseStatementContext extends StatementContext {

	protected TYPES type;
	protected TextSequence rebuiltUseStatementText;
	protected TextSequence longestStatementTextBeforeCursor;

	public enum TYPES {
		NONE, TRAIT, USE, GROUP
	}

	private static boolean hasUsePrefix(TextSequence statementText) {
		if (statementText.length() >= 4) {
			if ("use".equalsIgnoreCase( //$NON-NLS-1$
					statementText.subSequence(0, 3).toString())
					&& Character.isWhitespace(statementText.subSequence(3, 4).charAt(0))) {
				return true;
			}
		}
		return false;
	}

	private static TextSequence buildUseStatement(int offset, @NonNull IStructuredDocumentRegion sdRegion,
			boolean isClassStatementContext, boolean returnFirstFoundStatement, TYPES[] useType,
			TextSequence[] statementTextBeforeCursor) {
		useType[0] = TYPES.NONE;

		ContextRegion[] foundDelimiter = new ContextRegion[1];
		TextSequence statementText = PHPTextSequenceUtilities.getStatement(offset, sdRegion, true, foundDelimiter);
		statementTextBeforeCursor[0] = statementText;

		boolean hasUsePrefix = hasUsePrefix(statementText);
		if (hasUsePrefix || returnFirstFoundStatement) {
			useType[0] = isClassStatementContext ? TYPES.TRAIT : TYPES.USE;
			return statementText;
		}
		if (isClassStatementContext) {
			return null;
		}

		if (foundDelimiter[0] != null && foundDelimiter[0].getType() == PHPRegionTypes.PHP_CURLY_OPEN
				&& foundDelimiter[0].getStart() >= 4 /* "use " */) {
			// Check for "grouped use statements" like
			// "use X\Y\ { A, B, \C\D| };" with "|" the cursor position.
			// When found, statementText will contain here "A, B, \C\D" and
			// statementTextBeforeCurly will contain "use X\Y\ ".
			TextSequence statementTextBeforeCurly = PHPTextSequenceUtilities.getStatement(foundDelimiter[0].getStart(),
					sdRegion, true, foundDelimiter);
			if (hasUsePrefix(statementTextBeforeCurly)) {
				useType[0] = TYPES.GROUP;

				// 1. remove spaces at the end of "use X\Y\ "
				String s1 = statementTextBeforeCurly.toString();
				int endS1 = PHPTextSequenceUtilities.readBackwardSpaces(s1, s1.length());
				// 2. look for multiple use statements separed by ',' and remove
				// spaces in front of current statement (i.e. just before
				// cursor), to only keep "\C\D"
				String s2 = statementText.toString();
				int idxS2 = s2.lastIndexOf(',') /* may be -1 */ + 1;
				idxS2 = PHPTextSequenceUtilities.readForwardSpaces(s2, idxS2);
				// 3. remove useless '\' in front of grouped statement, to only
				// keep "C\D"
				if (idxS2 < s2.length() && s2.charAt(idxS2) == NamespaceReference.NAMESPACE_SEPARATOR) {
					idxS2++;
				}
				// 4. merge "statementTextBeforeCurly" and "statementText" and
				// cut useless characters, to get final statement "use X\Y\C\D"
				int start1 = statementTextBeforeCurly.getOriginalOffset(0);
				int start2 = statementText.getOriginalOffset(0);
				TextSequence res = TextSequenceUtilities.createTextSequence(statementTextBeforeCurly.getSource(),
						start1, (start2 - start1) + statementText.length());
				res = res.cutTextSequence(endS1, (start2 - start1) + idxS2);

				statementTextBeforeCursor[0] = TextSequenceUtilities.createTextSequence(statementText.getSource(),
						start2 + idxS2, statementText.length() - idxS2);

				return res;
			}
		}

		return null;
	}

	@SuppressWarnings("null")
	@NonNull
	public TextSequence getStatementText() {
		return rebuiltUseStatementText;
	}

	@SuppressWarnings("null")
	public String getRealPrefixBeforeCursor() {
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
		return getOffset() - getRealPrefixBeforeCursor().length();
	}

	@Override
	public boolean isValid(@NonNull ISourceModule sourceModule, int offset, CompletionRequestor requestor) {
		if (!super.isValid(sourceModule, offset, requestor)) {
			return false;
		}

		boolean isClassStatementContext = new ClassStatementContext().isValid(sourceModule, offset, requestor);
		TextSequence[] statementsText = new TextSequence[1];
		TYPES types[] = new TYPES[1];
		rebuiltUseStatementText = buildUseStatement(offset, getStructuredDocumentRegion(), isClassStatementContext,
				false, types, statementsText);
		longestStatementTextBeforeCursor = statementsText[0];
		type = types[0];

		assert (rebuiltUseStatementText == null && type == TYPES.NONE)
				|| (rebuiltUseStatementText != null && type != TYPES.NONE);

		return rebuiltUseStatementText != null;
	}

	public TYPES getType() {
		return type;
	}
}
