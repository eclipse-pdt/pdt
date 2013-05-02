package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.NamespaceMemberContext;

public class NamespaceInstanceOfStrategy extends NamespaceTypesStrategy {

	private static final String SPLASH = "\\"; //$NON-NLS-1$

	public NamespaceInstanceOfStrategy(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public NamespaceInstanceOfStrategy(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		NamespaceMemberContext concreteContext = (NamespaceMemberContext) context;

		SourceRange replaceRange = getReplacementRange(context);

		String suffix = "";//$NON-NLS-1$ 
		String nsSuffix = getNSSuffix(concreteContext);
		IType[] types = getTypes(concreteContext);
		for (IType type : types) {
			try {
				int flags = type.getFlags();
				reporter.reportType(type,
						PHPFlags.isNamespace(flags) ? nsSuffix : suffix,
						replaceRange, null);
			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
		}
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return "(".equals(nextWord) ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public String getNSSuffix(AbstractCompletionContext abstractContext) {
		String nextWord = null;
		try {
			nextWord = abstractContext.getNextWord();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return SPLASH.equals(nextWord) ? "" : SPLASH; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
