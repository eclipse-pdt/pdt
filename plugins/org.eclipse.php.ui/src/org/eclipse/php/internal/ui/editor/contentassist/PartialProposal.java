package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.core.runtime.Path;

/**
 * A wrapper for CodeDataCompletionProposal which reduces display string's common suffix
 * @author seva, 2007
 */
class PartialProposal extends CodeDataCompletionProposal {

	private int matchingSegments;

	public PartialProposal(CodeDataCompletionProposal proposal, int matchingSegments) {
		super(proposal.getProjectModel(), proposal.getCodeData(), proposal.getOffset(), proposal.getLength(), proposal.getSelectionLength(), proposal.getPrefix(), proposal.getSuffix(), proposal.getCaretOffsetInSuffix(), proposal.getShowHints());
		this.matchingSegments = matchingSegments;
	}

	/** (non-Javadoc)
	 * @see org.eclipse.php.internal.ui.editor.contentassist.CodeDataCompletionProposal#getDisplayString()
	 */
	@Override
	public String getDisplayString() {
		String displayString = super.getDisplayString();
		return (matchingSegments != 0 ? "..._" : "") + new Path(displayString.replaceAll("_", "/")).removeFirstSegments(matchingSegments).toString().replaceAll("/", "_"); //$NON-NLS-1$
	}
}