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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CompletionRequestorExtension;
import org.eclipse.php.internal.core.codeassist.contexts.ClassObjMemberContext;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalMethodStatementContextForTemplate;
import org.eclipse.php.internal.core.codeassist.templates.contexts.GlobalStatementContextForTemplate;

public class CodeCompletionRequestor extends CompletionRequestor implements
		CompletionRequestorExtension {
	List<String> proposals;

	public CodeCompletionRequestor() {
		proposals = new ArrayList<String>();

		setIgnored(CompletionProposal.KEYWORD, true);
	}

	@Override
	public void accept(CompletionProposal proposal) {
		if (isIgnored(proposal.getKind()))
			return;

		String completion = proposal.getCompletion(); // cut starting dollar
		String name = completion.substring(1);

		switch (proposal.getKind()) {
		case CompletionProposal.LOCAL_VARIABLE_REF:
			proposals.add(name);
			break;
		case CompletionProposal.FIELD_REF:
			proposals.add(name);
			break;

		default:
			break;
		}
	}

	public String[] getVariables() {
		return proposals.toArray(new String[proposals.size()]);
	}

	public String[] getArrayVariables() {
		return proposals.toArray(new String[proposals.size()]);
	}

	public ICompletionContext[] createContexts() {
		return new ICompletionContext[] { new ClassObjMemberContext(),
				new GlobalMethodStatementContextForTemplate(),
				new GlobalStatementContextForTemplate(), };
	}
}