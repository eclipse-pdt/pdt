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