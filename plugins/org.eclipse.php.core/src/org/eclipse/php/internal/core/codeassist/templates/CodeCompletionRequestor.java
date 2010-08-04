/*******************************************************************************
 * Copyright (c) 2010 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.codeassist.templates;

import java.util.*;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.php.internal.core.codeassist.CompletionRequestorExtension;

public abstract class CodeCompletionRequestor extends CompletionRequestor
		implements CompletionRequestorExtension {
	List<CompletionProposal> proposals;
	Comparator sorter;

	public CodeCompletionRequestor() {
		proposals = new ArrayList<CompletionProposal>();
		sorter = getSorter();
		setIgnored(CompletionProposal.KEYWORD, true);
	}

	protected Comparator getSorter() {
		return new Comparator<CompletionProposal>() {

			public int compare(CompletionProposal o1, CompletionProposal o2) {
				return o1.getName().compareTo(o2.getName());
			}
		};
	}

	@Override
	public void accept(CompletionProposal proposal) {
		if (isIgnored(proposal.getKind()))
			return;
		addProposal(proposal);
	}

	public final void addProposal(CompletionProposal proposal) {
		proposals.add(proposal);
	}

	public String[] getVariables() {
		Collections.sort(proposals, sorter);
		Set<String> nameSet = new HashSet<String>();
		List<String> nameList = new ArrayList<String>();
		for (Iterator iterator = proposals.iterator(); iterator.hasNext();) {
			CompletionProposal proposal = (CompletionProposal) iterator.next();
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