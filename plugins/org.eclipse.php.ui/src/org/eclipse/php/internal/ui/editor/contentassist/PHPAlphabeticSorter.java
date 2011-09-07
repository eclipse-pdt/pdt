package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.AbstractProposalSorter;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class PHPAlphabeticSorter extends AbstractProposalSorter {

	private final CompletionProposalComparator fComparator = new CompletionProposalComparator();

	public PHPAlphabeticSorter() {
		fComparator.setOrderAlphabetically(false);
	}

	public int compare(ICompletionProposal p1, ICompletionProposal p2) {
		return fComparator.compare(p1, p2);
	}

}
