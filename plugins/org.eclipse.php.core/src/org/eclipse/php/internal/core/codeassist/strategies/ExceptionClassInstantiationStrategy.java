package org.eclipse.php.internal.core.codeassist.strategies;

import java.util.*;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.model.PhpModelAccess;

public class ExceptionClassInstantiationStrategy extends
		AbstractClassInstantiationStrategy {
	private static final String CORE_PHP = "Core.php"; //$NON-NLS-1$
	private static final String BASIC_PHP = "basic.php"; //$NON-NLS-1$
	private static final String EXCEPTION = "Exception"; //$NON-NLS-1$

	private IType exceptionType;

	public ExceptionClassInstantiationStrategy(ICompletionContext context) {
		super(context);
	}

	protected IType[] getTypes(AbstractCompletionContext context)
			throws BadLocationException {
		IType exceptionType = getExceptionType();
		if (exceptionType == null)
			return super.getTypes(context);
		List<IType> result = new LinkedList<IType>();
		ITypeHierarchy typeHierarchy;
		ISourceModule sourceModule = context.getSourceModule();
		IScriptProject scriptProject = sourceModule.getScriptProject();
		try {
			if (scriptProject != null) {
				typeHierarchy = exceptionType.newTypeHierarchy(scriptProject,
						new NullProgressMonitor());
			} else {
				typeHierarchy = exceptionType
						.newTypeHierarchy(new NullProgressMonitor());
			}

			IType[] classes = typeHierarchy.getAllSubtypes(exceptionType);
			Set<IType> set = new HashSet<IType>();
			set.add(exceptionType);
			set.addAll(Arrays.asList(classes));
			String prefix = context.getPrefix();
			if (prefix.trim().length() != 0) {
				IType[] types = super.getTypes(context);
				for (int i = 0; i < types.length; i++) {
					IType type = types[i];
					if (set.contains(type)) {
						result.add(type);
					}
				}
			} else {
				result.addAll(set);
			}

		} catch (ModelException e) {
			return super.getTypes(context);
		}

		return (IType[]) result.toArray(new IType[result.size()]);
	}

	protected IType getExceptionType() {
		if (exceptionType != null)
			return exceptionType;
		IDLTKSearchScope scope = createSearchScope();
		IType[] exceptionTypes = PhpModelAccess.getDefault().findTypes(
				EXCEPTION, MatchRule.EXACT, trueFlag, falseFlag, scope, null);
		for (int i = 0; i < exceptionTypes.length; i++) {
			if (isExctptionType(exceptionTypes[i])) {
				exceptionType = exceptionTypes[i];
				break;
			}
		}
		return exceptionType;
	}

	private boolean isExctptionType(IType iType) {
		if (EXCEPTION.equals(iType.getElementName())
				&& (CORE_PHP.equals(iType.getParent().getElementName()) || BASIC_PHP
						.equals(iType.getParent().getElementName())))
			return true;
		return false;
	}
}
