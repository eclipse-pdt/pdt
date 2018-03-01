/*******************************************************************************
 * Copyright (c) 2008, 2015 Zend Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Zend Technologies - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.refactoring.ui.utils;

import java.util.ArrayList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.contentassist.IContentAssistSubjectControl;
import org.eclipse.jface.contentassist.ISubjectControlContentAssistProcessor;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.*;

public class VariableNameProcessor implements IContentAssistProcessor, ISubjectControlContentAssistProcessor {

	private String[] fVariableNameProposals;

	private String fErrorMessage;

	public VariableNameProcessor(String[] variableNameProposals) {
		fVariableNameProposals = variableNameProposals;
	}

	public void explicitActivationRequest() {

	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

	@Override
	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

	@Override
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	@Override
	public String getErrorMessage() {
		return fErrorMessage;
	}

	@Override
	public ICompletionProposal[] computeCompletionProposals(IContentAssistSubjectControl contentAssistSubject,
			int documentOffset) {
		if (fVariableNameProposals.length == 0) {
			return null;
		}
		String input = contentAssistSubject.getDocument().get();

		ArrayList<ICompletionProposal> proposals = new ArrayList<>();
		String prefix = input.substring(0, documentOffset);
		for (int i = 0; i < fVariableNameProposals.length; i++) {
			String tempName = fVariableNameProposals[i];
			if (tempName.length() == 0 || !tempName.startsWith(prefix)) {
				continue;
			}
			CompletionProposal proposal = new CompletionProposal(tempName, 0, input.length(), 0);
			proposals.add(proposal);
		}
		fErrorMessage = proposals.size() > 0 ? null : "No completions available"; //$NON-NLS-1$
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	@Override
	public IContextInformation[] computeContextInformation(IContentAssistSubjectControl contentAssistSubjectControl,
			int documentOffset) {
		Assert.isTrue(false, "ITextViewer not supported"); //$NON-NLS-1$
		return null;
	}

}
