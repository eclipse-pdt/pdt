package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;

public class PHPCompletionProposalComparator extends
		CompletionProposalComparator {
	@Override
	public int compare(Object o1, Object o2) {
		ICompletionProposal p1 = (ICompletionProposal) o1;
		ICompletionProposal p2 = (ICompletionProposal) o2;
		if (isSpecialProposal(p1) || isSpecialProposal(p2)) {
			int result = compareRelevance(p1, p2);
			if (result == 0) {
				result = compareAlphabetically(p1, p2);
			}
			return result;
		}
		return super.compare(o1, o2);
	}

	private boolean isSpecialProposal(ICompletionProposal cp) {
		if (cp instanceof IPHPCompletionProposalExtension) {
			IPHPCompletionProposalExtension p = (IPHPCompletionProposalExtension) cp;
			if (ProposalExtraInfo.STUB.equals(p.getExtraInfo())
					|| ProposalExtraInfo.MAGIC_METHOD.equals(p.getExtraInfo())) {
				return true;
			}
		}
		return false;
	}
}
