package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceUseNameContext;

public class NamespaceUseNameStrategy extends AbstractCompletionStrategy {

	public NamespaceUseNameStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {
		ICompletionContext context = getContext();
		if (!(context instanceof NamespaceUseNameContext)) {
			return;
		}

		NamespaceUseNameContext concreteContext = (NamespaceUseNameContext) context;
		// now we compute type suffix in PHPCompletionProposalCollector
		String suffix = "";//$NON-NLS-1$ 
		SourceRange replaceRange = getReplacementRange(concreteContext);

		for (IType type : getTypes(concreteContext)) {
			reporter.reportType(type, suffix, replaceRange, getExtraInfo());
		}
	}

	public IType[] getTypes(NamespaceUseNameContext context)
			throws BadLocationException {
		String prefix = context.getPrefix();

		List<IType> result = new LinkedList<IType>();
		for (IType ns : context.getNamespaces()) {
			try {
				for (IType type : ns.getTypes()) {
					if (CodeAssistUtils.startsWithIgnoreCase(type
							.getElementName(), prefix)) {
						result.add(type);
					}
				}
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
		return (IType[]) result.toArray(new IType[result.size()]);
	}

	public SourceRange getReplacementRange(ICompletionContext context)
			throws BadLocationException {
		SourceRange replacementRange = super.getReplacementRange(context);
		if (replacementRange.getLength() > 0) {
			return new SourceRange(replacementRange.getOffset(),
					replacementRange.getLength() - 1);
		}
		return replacementRange;
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "::".equals(nextWord) ? "" : "::"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	protected Object getExtraInfo() {
		return ProposalExtraInfo.TYPE_ONLY;
	}
}
