/**
 * 
 */
package org.eclipse.php.internal.core.codeassist.strategies;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.dltk.internal.core.SourceRange;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;
import org.eclipse.php.internal.core.codeassist.ICompletionReporter;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.typeinference.FakeMethod;

/**
 * This is a basic strategy that completes global classes after 'new' statement,
 * without any additional add-ons in final result
 * 
 * @author vadim.p
 * 
 */
public abstract class AbstractClassInstantiationStrategy extends
		GlobalTypesStrategy {

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

		IType enclosingClass = null;
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
		String suffix = getSuffix(concreteContext, replaceRange.getOffset()
				+ replaceRange.getLength());

		IType[] types = getTypes(concreteContext);
		for (IType type : types) {

			IMethod ctor = null;
			try {
				IMethod[] methods = type.getMethods();
				if (methods != null && methods.length > 0) {
					for (IMethod method : methods) {
						if (method.isConstructor()
								&& method.getParameters() != null
								&& method.getParameters().length > 0) {
							ctor = method;
							if (!PHPFlags.isPrivate(ctor.getFlags())
									|| type.equals(enclosingClass)) {
								IMethod ctorMethod = createFakeMethod(ctor,
										type);
								reporter.reportMethod(ctorMethod, suffix,
										replaceRange);
								break;
							}
						}
					}
				}

				// try to find constructor in super classes
				if (ctor == null) {
					ITypeHierarchy newSupertypeHierarchy = type
							.newSupertypeHierarchy(null);
					IType[] allSuperclasses = newSupertypeHierarchy
							.getAllSuperclasses(type);
					if (allSuperclasses != null && allSuperclasses.length > 0) {
						for (IType superClass : allSuperclasses) {
							methods = superClass.getMethods();
							// find first constructor and exit
							if (methods != null && methods.length > 0) {
								for (IMethod method : methods) {
									if (method.isConstructor()
											&& method.getParameters() != null
											&& method.getParameters().length > 0) {
										ctor = method;
										if (!PHPFlags
												.isPrivate(ctor.getFlags())
												|| type.equals(enclosingClass)) {
											IMethod ctorMethod = createFakeMethod(
													ctor, type);
											reporter.reportMethod(ctorMethod,
													suffix, replaceRange);
											break;
										}
									}
								}
							}
						}
					}
				}

			} catch (ModelException e) {
				PHPCorePlugin.log(e);
			}
			if (ctor == null) {
				reporter.reportType(type, suffix, replaceRange);
			}
		}
	}

	public String getSuffix(AbstractCompletionContext abstractContext,
			int currentPosition) {
		char nextWord = ' ';
		try {
			nextWord = abstractContext.getNextChar();
		} catch (BadLocationException e) {
			PHPCorePlugin.log(e);
		}
		return '(' == nextWord ? "" : "()"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	class FakeConstructor extends FakeMethod {
		private IMethod ctor;

		public FakeConstructor(ModelElement parent, String name, int offset,
				int length, int nameOffset, int nameLength, IMethod ctor) {
			super(parent, name, offset, length, nameOffset, nameLength);
			this.ctor = ctor;
		}

		public boolean isConstructor() throws ModelException {
			return true;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof FakeConstructor) {
				FakeConstructor fakeConstructor = (FakeConstructor) o;
				return this.ctor == fakeConstructor.ctor;
			}
			return false;
		}
	}

	private FakeMethod createFakeMethod(IMethod ctor, IType type) {
		ISourceRange sourceRange;
		try {
			sourceRange = type.getSourceRange();
			FakeMethod ctorMethod = new FakeConstructor((ModelElement) type,
					type.getElementName(), sourceRange.getOffset(), sourceRange
							.getLength(), sourceRange.getOffset(), sourceRange
							.getLength(), ctor) {

			};
			ctorMethod.setParameters(ctor.getParameters());
			// ctorMethod
			// .setParameterInitializers(ctor.getParameterInitializers());
			return ctorMethod;
		} catch (ModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
