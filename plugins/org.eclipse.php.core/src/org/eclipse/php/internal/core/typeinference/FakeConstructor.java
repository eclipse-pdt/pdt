package org.eclipse.php.internal.core.typeinference;

import org.eclipse.dltk.core.*;
import org.eclipse.dltk.internal.core.ModelElement;
import org.eclipse.php.core.compiler.PHPFlags;
import org.eclipse.php.internal.core.PHPCorePlugin;

public class FakeConstructor extends FakeMethod {
	/**
	 * the code assist if happens in the class itself for example class Foo{
	 * function Foo($name){} function clone(){return new Foo("foo")} }
	 */
	private boolean isEnclosingClass;

	public FakeConstructor(ModelElement parent, String name, int offset,
			int length, int nameOffset, int nameLength, boolean isEnclosingClass) {
		super(parent, name, offset, length, nameOffset, nameLength);
		this.isEnclosingClass = isEnclosingClass;
	}

	public boolean isConstructor() throws ModelException {
		return true;
	}

	public boolean isEnclosingClass() {
		return isEnclosingClass;
	}

	public void setEnclosingClass(boolean isEnclosingClass) {
		this.isEnclosingClass = isEnclosingClass;
	}

	public static FakeConstructor createFakeConstructor(IMethod ctor,
			IType type, boolean isEnclosingClass) {
		ISourceRange sourceRange;
		try {
			sourceRange = type.getSourceRange();
			FakeConstructor ctorMethod = new FakeConstructor(
					(ModelElement) type, type.getElementName(), sourceRange
							.getOffset(), sourceRange.getLength(), sourceRange
							.getOffset(), sourceRange.getLength(),
					isEnclosingClass);
			if (ctor != null) {
				ctorMethod.setParameters(ctor.getParameters());
			}

			return ctorMethod;
		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return null;
	}

	/**
	 * 
	 * @param type
	 * @param isEnclosingClass
	 * @return IMethod[] constructors[0] is the type's constructor
	 *         constructors[1] is an available FakeConstructor for
	 *         constructors[0] both constructors[0] and constructors[1] could be
	 *         null
	 */
	public static IMethod[] getConstructors(IType type, boolean isEnclosingClass) {
		IMethod[] constructors = new IMethod[2];
		try {
			constructors = getConstructorsOfType(type, isEnclosingClass);

			// try to find constructor in super classes
			if (constructors[0] == null) {
				ITypeHierarchy newSupertypeHierarchy = type
						.newSupertypeHierarchy(null);
				IType[] allSuperclasses = newSupertypeHierarchy
						.getAllSuperclasses(type);
				if (allSuperclasses != null && allSuperclasses.length > 0) {
					for (IType superClass : allSuperclasses) {
						if (constructors[0] == null) {
							constructors = getConstructorsOfType(superClass,
									isEnclosingClass);
						} else {
							break;
						}
					}
				}
			}

		} catch (ModelException e) {
			PHPCorePlugin.log(e);
		}
		return constructors;
	}

	private static IMethod[] getConstructorsOfType(IType type,
			boolean isEnclosingClass) throws ModelException {
		IMethod[] constructors = new IMethod[2];
		IMethod[] methods = type.getMethods();
		if (methods != null && methods.length > 0) {
			for (IMethod method : methods) {
				if (method.isConstructor() && method.getParameters() != null
						&& method.getParameters().length > 0) {
					constructors[0] = method;
					if (isEnclosingClass
							|| !PHPFlags.isPrivate(constructors[0].getFlags())) {
						constructors[1] = FakeConstructor
								.createFakeConstructor(constructors[0], type,
										isEnclosingClass);
					}
				}
			}
		}
		return constructors;
	}
}
