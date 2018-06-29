/*******************************************************************************
 * Copyright (c) 2010 Zend Technologies and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.templates;

import java.util.*;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.php.internal.core.codeassist.CompletionRequestorExtension;

public abstract class CodeCompletionRequestor extends CompletionRequestor implements CompletionRequestorExtension {
	List<CompletionProposal> proposals;
	Comparator<CompletionProposal> sorter;

	public CodeCompletionRequestor() {
		proposals = new ArrayList<>();
		sorter = getSorter();
		setIgnored(CompletionProposal.KEYWORD, true);
	}

	protected Comparator<CompletionProposal> getSorter() {
		return new Comparator<CompletionProposal>() {

			@Override
			public int compare(CompletionProposal o1, CompletionProposal o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}

	@Override
	public void accept(CompletionProposal proposal) {
		if (isIgnored(proposal.getKind())) {
			return;
		}
		addProposal(proposal);
	}

	public final void addProposal(CompletionProposal proposal) {
		proposals.add(proposal);
	}

	public String[] getVariables() {
		Collections.sort(proposals, sorter);
		Set<String> nameSet = new HashSet<>();
		List<String> nameList = new ArrayList<>();
		for (Iterator<CompletionProposal> iterator = proposals.iterator(); iterator.hasNext();) {
			CompletionProposal proposal = iterator.next();
			if (!nameSet.contains(proposal.getName())) {
				nameSet.add(proposal.getName());
				nameList.add(proposal.getName());
			}
		}
		return nameList.toArray(new String[nameList.size()]);
	}

	public String[] getArrayVariables() {
		return getVariables();
	}
}