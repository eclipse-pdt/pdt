/**
 * 
 */
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.compiler.env.IModuleSource;
import org.eclipse.dltk.core.*;
import org.eclipse.dltk.core.search.IDLTKSearchScope;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.AliasType;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeConstructor;

/**
 * This is a basic strategy that completes global classes after 'new' statement,
 * without any additional add-ons in final result
 * 
 * @author vadim.p
 * 
 */
public abstract class AbstractClassInstantiationStrategy extends
		GlobalTypesStrategy {

	private IType enclosingClass;

	public AbstractClassInstantiationStrategy(ICompletionContext context,
			int trueFlag, int falseFlag) {
		super(context, trueFlag, falseFlag);
	}

	public AbstractClassInstantiationStrategy(ICompletionContext context) {
		this(context, 0, 0);
	}

	public void apply(ICompletionReporter reporter) throws BadLocationException {

		ICompletionContext context = getContext();
		AbstractCompletionContext concreteContext = (AbstractCompletionContext) context;

		enclosingClass = null;
		try {
			IModelElement enclosingElement = concreteContext.getSourceModule()
					.getElementAt(concreteContext.getOffset());
			while (enclosingElement instanceof IField) {
				enclosingElement = enclosingElement.getParent();
			}
			if (enclosingElement instanceof IMethod) {
				IModelElement parent = ((IMethod) enclosingElement).getParent();
				if (parent instanceof IType) {
					enclosingClass = (IType) parent;
				}
			}
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}

		SourceRange replaceRange = getReplacementRange(context);
		String suffix = getSuffix(concreteContext);

		IType[] types = getTypes(concreteContext);
		for (IType type : types) {
			if (!concreteContext.getCompletionRequestor()
					.isContextInformationMode()) {
				// here we use fake method,and do the real work in class
				// ParameterGuessingProposal
				IMethod ctorMethod = FakeConstructor.createFakeConstructor(
						null, type, type.equals(enclosingClass));
				reporter.reportMethod(ctorMethod, suffix, replaceRange);
			} else {
				// if this is context information mode,we use this,
				// because the number of types' length is very small
				IMethod[] ctors = FakeConstructor.getConstructors(type,
						type.equals(enclosingClass));
				if (ctors != null && ctors.length == 2) {
					if (ctors[1] != null) {
						reporter.reportMethod(ctors[1], suffix, replaceRange);
					} else if (ctors[0] == null) {
						reporter.reportType(type, suffix, replaceRange);
					}
				}
			}

		}
		addAlias(reporter, suffix);
	}

	@Override
	protected void reportAlias(ICompletionReporter reporter,
			IDLTKSearchScope scope, IModuleSource module,
			SourceRange replacementRange, IType type,
			String fullyQualifiedName, String alias, String suffix) {
		IType aliasType = new AliasType((ModelElement) type,
				fullyQualifiedName, alias);
		IMethod ctorMethod = FakeConstructor.createFakeConstructor(null,
				aliasType, type.equals(enclosingClass));
		reporter.reportMethod(ctorMethod, "", replacementRange); //$NON-NLS-1$
	}

	public String getSuffix(AbstractCompletionContext abstractContext) {
		boolean insertMode = isInsertMode();

		char nextChar = ' ';
		try {
			if(insertMode){
				nextChar = abstractContext.getNextChar();
			}else{
				SourceRange replacementRange = getReplacementRange(abstractContext);
				nextChar = abstractContext.getDocument().getChar(replacementRange.getOffset()+replacementRange.getLength());
			}
			
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return '(' == nextChar ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

}
