package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;

public class NamespaceNonFinalClassesStrategy extends NamespaceTypesStrategy {

	public NamespaceNonFinalClassesStrategy(ICompletionContext context) {
		super(context);
	}

	@Override
	protected int getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
