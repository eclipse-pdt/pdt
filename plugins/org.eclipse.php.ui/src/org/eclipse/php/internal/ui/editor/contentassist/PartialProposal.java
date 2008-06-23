/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;


/**
 * A wrapper for CodeDataCompletionProposal which reduces display string's common suffix
 * @author seva, 2007
 */
class PartialProposal extends CodeDataCompletionProposal {

	private int segmentsToCut;

	public PartialProposal(CodeDataCompletionProposal proposal, int segmentsToCut) {
		super(proposal.getProjectModel(), proposal.getCodeData(), proposal.getOffset(), proposal.getLength(), proposal.getSelectionLength(), proposal.getPrefix(), proposal.getSuffix(), proposal.getCaretOffsetInSuffix(), proposal.getShowHints());
		this.segmentsToCut = segmentsToCut;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.editor.contentassist.CodeDataCompletionProposal#getDisplayString()
	 */
	@Override
	public String getDisplayString() {
		String displayString = super.getDisplayString();
		return (segmentsToCut != 0 ? CompletionProposalGroup.COLLAPSED_PREFIX + CompletionProposalGroup.ELEMENT_NAME_SEPARATOR : "") + CompletionProposalGroup.elementPathToName(CompletionProposalGroup.elementNameToPath(displayString).removeFirstSegments(segmentsToCut)); //$NON-NLS-1$
	}
}