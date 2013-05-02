package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.IElementFilter;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.ArrayKeyContext;
import org.eclipse.php.internal.core.typeinference.FakeField;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

public class LocalMethodVariablesStrategyForArray extends
		AbstractCompletionStrategy {

	public LocalMethodVariablesStrategyForArray(ICompletionContext context,
			IElementFilter elementFilter) {
		super(context, elementFilter);
	}

	public LocalMethodVariablesStrategyForArray(ICompletionContext context) {
		super(context);
	}

	public void apply(ICompletionReporter reporter) throws Exception {
		ICompletionContext context = getContext();
		if (!(context instanceof ArrayKeyContext)) {
			return;
		}

		ArrayKeyContext arrayContext = (ArrayKeyContext) context;
		String prefix = arrayContext.getPrefix();
		CompletionRequestor requestor = arrayContext.getCompletionRequestor();
		IModelElement enclosingElement;
		try {
			enclosingElement = arrayContext.getSourceModule().getElementAt(
					arrayContext.getOffset());
			while (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (!(enclosingElement instanceof IMethod)) {
				return;
			}
			SourceRange replaceRange = getReplacementRange(arrayContext);
			IMethod enclosingMethod = (IMethod) enclosingElement;

			// complete class variable: $this
			if (!PHPFlags.isStatic(enclosingMethod.getFlags())) {
				IType declaringType = enclosingMethod.getDeclaringType();
				if (declaringType != null) {
					if ("$this".startsWith(prefix)) { //$NON-NLS-1$
						reporter.reportField(
								new FakeField((ModelElement) declaringType,
										"$this", 0, 0), "", replaceRange, false); //NON-NLS-1  //$NON-NLS-1$//$NON-NLS-2$
					}
				}
			}

			for (IModelElement element : PHPModelUtils.getMethodFields(
					enclosingMethod, prefix,
					requestor.isContextInformationMode(), null)) {
				reporter.reportField((IField) element, "", replaceRange, false); //$NON-NLS-1$
			}

		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
