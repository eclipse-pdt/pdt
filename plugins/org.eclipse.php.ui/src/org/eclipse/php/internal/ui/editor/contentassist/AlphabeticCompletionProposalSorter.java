package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.AbstractProposalSorter;
import org.eclipse.dltk.ui.text.completion.CompletionProposalComparator;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class AlphabeticCompletionProposalSorter extends AbstractProposalSorter {

	private final CompletionProposalComparator fComparator;

	public AlphabeticCompletionProposalSorter() {
		fComparator = new CompletionProposalComparator();
		fComparator.setOrderAlphabetically(true);
	}

	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		return fComparator.compare(p1, p2);
	}
}
