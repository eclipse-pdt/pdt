/**
 * 
 */
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.core.runtime.Platform;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionReporter;
import org.eclipse.php.core.codeassist.ICompletionScope;
import org.eclipse.php.core.codeassist.ICompletionScope.Type;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.core.compiler.ast.nodes.NamespaceReference;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.PHPCoreConstants;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ProposalExtraInfo;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;
import org.eclipse.php.internal.core.typeinference.PHPModelUtils;

/**
 * This is a basic strategy that completes global classes after 'new' statement,
 * without any additional add-ons in final result
 * 
 * @author vadim.p
 * 
 */
public abstract class AbstractClassInstantiationStrategy extends TypesStrategy {

	private String enclosingClass;

	public AbstractClassInstantiationStrategy(ICompletionContext context, int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public AbstractClassInstantiationStrategy(ICompletionContext context) {
		this(context, 0, 0);
	}

	@Override
	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;
		int extraInfo = getExtraInfo();
		ICompletionScope scope = getCompanion().getScope().findParent(Type.CLASS, Type.INTERFACE);
		if (scope != null) {
			enclosingClass = scope.getName();
		}

		ISourceRange replaceRange = getReplacementRangeForMember(concreteContext);
		String namespaceOfPrefix = getNamespaceOfPrefix(context);
		if (namespaceOfPrefix.length() > 0) {
			extraInfo |= ProposalExtraInfo.PREFIX_HAS_NAMESPACE;
		} else if (!concreteContext.isAbsolute()) {
			extraInfo |= ProposalExtraInfo.IMPORT_PARENT;
		}

		String suffix = getSuffix(concreteContext, ""); //$NON-NLS-1$

		IType[] types = getTypes(concreteContext);
		for (IType type : types) {
			try {
				if (PHPFlags.isNamespace(type.getFlags())) {
					ISourceRange nsReplaceRange = getReplacementRange(concreteContext);
					if (concreteContext.isAbsoluteName()) {
						extraInfo |= ProposalExtraInfo.ABSOLUTE_NAME;
					}
					reporter.reportType(type, NamespaceReference.NAMESPACE_DELIMITER, nsReplaceRange,
							extraInfo | ProposalExtraInfo.MEMBER_IN_NAMESPACE);
					continue;
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}

			IMethod ctor = getConstructor(type);
			if (ctor != null) {
				reportMethod(reporter, ctor, suffix, replaceRange, extraInfo);
			}
		}
		addAlias(reporter, suffix);
	}

	protected IType[] getTypes(AbstractCompletionContext context) throws BadLocationException {
		/*
		 * if (StringUtils.isBlank(context.getPrefix())) { if (enclosingClass !=
		 * null) { return new IType[] { enclosingClass }; } return new IType[0];
		 * }
		 */
		return super.getTypes(context);
	}

	protected void reportMethod(ICompletionReporter reporter, IMethod method, String suffix, ISourceRange replaceRange,
			int extraInfo) {
		if (isExpandMethodEnabled()) {
			IMethod[] methods = PHPModelUtils.expandDefaultValueMethod(method);
			for (IMethod m : methods) {
				reporter.reportMethod(m, suffix, replaceRange, extraInfo);
			}
		} else {
			reporter.reportMethod(method, suffix, replaceRange, extraInfo);
		}
	}

	@Override
	protected void reportAlias(ICompletionReporter reporter, IDLTKSearchScope scope, ISourceModule module,
			ISourceRange replacementRange, IType type, String fullyQualifiedName, String alias, String suffix) {
		IType aliasType = new AliasType((ModelElement) type, fullyQualifiedName, alias);
		IMethod ctor = getConstructor(aliasType);
		reportMethod(reporter, ctor, suffix, replacementRange, getExtraInfo()); // $NON-NLS-1$
	}

	private IMethod getConstructor(IType type) {
		boolean isEnclosingClass = type.equals(enclosingClass);
		IMethod[] ctors = FakeConstructor.getConstructors(type, isEnclosingClass);
		if (ctors != null && ctors.length == 2) {
			if (ctors[1] != null) {
				return FakeConstructor.createFakeConstructor(ctors[1], type, isEnclosingClass);
			} else if (ctors[0] == null) {
				return FakeConstructor.createFakeConstructor(null, type, isEnclosingClass);
			}
		}
		return null;
	}

	private boolean isExpandMethodEnabled() {
		return Platform.getPreferencesService().getBoolean(PHPCorePlugin.ID,
				PHPCoreConstants.CODEASSIST_EXPAND_DEFAULT_VALUE_METHODS, true, null);
	}

	@Override
	protected int getExtraInfo() {
		if (!((AbstractCompletionContext) getContext()).isAbsolute()) {
			return super.getExtraInfo();
		}
		return super.getExtraInfo();
	}

}
