/*******************************************************************************
 * Copyright (c) 2017 Alex Xu and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alex Xu - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.dltk.ui.text.completion.AbstractScriptCompletionProposal;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

public class ProposalProcessorManager {

	private final Set<ProposalProcessor> processors = new LinkedHashSet<>();
	private final AbstractScriptCompletionProposal proposal;

	public ProposalProcessorManager(AbstractScriptCompletionProposal proposal) {
		this.proposal = proposal;
	}

	public void addProcessor(ProposalProcessor processor) {
		processors.add(processor);
	}

	public boolean prefixChanged(String prefix) {
		boolean keepProposal = false;
		int tmpRelevance = 0;

		for (ProposalProcessor p : processors) {
			keepProposal |= p.isPrefix(prefix);
			tmpRelevance += p.modifyRelevance();
		}
		proposal.setRelevance(proposal.getRelevance() + tmpRelevance);
		return keepProposal;
	}

	public StyledString decorateStyledDisplayString(StyledString mutableStyledString) {
		for (ProposalProcessor p : processors) {
			p.modifyDisplayString(mutableStyledString);
		}
		return mutableStyledString;
	}

	public Image decorateImage(Image proposalImage) {
		Image img = proposalImage;
		for (ProposalProcessor p : processors) {
			img = p.modifyImage(img);
		}
		return img;
	}
}
