/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dltk.codeassist.RelevanceConstants;
import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.LazyScriptCompletionProposal;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.text.contentassist.BoldStylerProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.php.internal.core.util.text.LCSS;
import org.eclipse.php.internal.ui.corext.util.Strings;

public class SubwordsProposalProcessor extends ProposalProcessor {

	private static final int[] EMPTY_SEQUENCE = new int[0];

	// Negative value ensures subsequence matches have a lower relevance than
	// standard PDT or template proposals
	public static final int SUBWORDS_RANGE_START = -9000;
	public static final int CASE_SENSITIVE_EXACT_MATCH_START = 16
			* (RelevanceConstants.R_EXACT_NAME + RelevanceConstants.R_CASE);
	public static final int CASE_INSENSITIVE_EXACT_MATCH_START = 16 * RelevanceConstants.R_EXACT_NAME;

	private BoldStylerProvider styler = new BoldStylerProvider(JFaceResources.getDefaultFont());

	private int[] bestSequence = EMPTY_SEQUENCE;

	private String prefix;
	private final String matchingArea;

	public SubwordsProposalProcessor(IScriptCompletionProposal scriptProposal, CompletionProposal coreProposal) {
		String completionIdentifier = computeCompletionIdentifier(scriptProposal, coreProposal);
		matchingArea = Strings.getPrefixMatchingArea(completionIdentifier);
	}

	@Override
	public boolean isPrefix(String prefix) {
		if (this.prefix != prefix) {
			this.prefix = prefix;
			bestSequence = LCSS.bestSubsequence(matchingArea, prefix);
		}
		return prefix.isEmpty() || bestSequence.length > 0;
	}

	@Override
	public void modifyDisplayString(StyledString displayString) {
		if (bestSequence != null && bestSequence.length > 0) {
			for (int index : bestSequence) {
				displayString.setStyle(index, 1, styler.getBoldStyler());
			}
		}
	}

	@Override
	public int modifyRelevance() {
		if (ArrayUtils.isEmpty(bestSequence)) {
			return 0;
		}

		int relevanceBoost = 0;
		if (StringUtils.equals(prefix, matchingArea)) {
			relevanceBoost = CASE_SENSITIVE_EXACT_MATCH_START;
		} else if (StringUtils.equalsIgnoreCase(prefix, matchingArea)) {
			relevanceBoost = CASE_INSENSITIVE_EXACT_MATCH_START;
		} else if (StringUtils.startsWithIgnoreCase(matchingArea, prefix)) {
		} else if (CharOperation.camelCaseMatch(prefix.toCharArray(), matchingArea.toCharArray())) {
		} else {
			int score = LCSS.scoreSubsequence(bestSequence);
			relevanceBoost = SUBWORDS_RANGE_START + score;
		}
		return relevanceBoost;
	}

	private String computeCompletionIdentifier(IScriptCompletionProposal javaProposal,
			CompletionProposal coreProposal) {
		String completionIdentifier;
		if (javaProposal instanceof LazyScriptCompletionProposal && coreProposal != null) {
			if (coreProposal.isConstructor()) {
				completionIdentifier = new StringBuilder().append(coreProposal.getName()).toString();
			} else {
				switch (coreProposal.getKind()) {
				case CompletionProposal.TYPE_REF: {
					// result: ClassSimpleName fully.qualified.ClassSimpleName
					completionIdentifier = new StringBuilder().append(coreProposal.getModelElement().getElementName())
							.toString();
					break;
				}
				case CompletionProposal.METHOD_REF:
				case CompletionProposal.METHOD_NAME_REFERENCE: {
					// result: myMethodName(Lsome/Param;I)V
					completionIdentifier = new StringBuilder().append(coreProposal.getName()).toString();
					break;
				}
				default:
					// result: display string. This should not happen. We should
					// issue a warning here...
					completionIdentifier = javaProposal.getDisplayString();
					break;
				}
			}
		} else {
			completionIdentifier = javaProposal.getDisplayString();
		}
		return completionIdentifier;
	}

}
