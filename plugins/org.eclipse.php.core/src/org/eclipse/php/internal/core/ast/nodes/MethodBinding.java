/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.*;

/**
 * PHP method binding implementation.
 * 
 * @author shalom
 */
public class MethodBinding extends FunctionBinding implements IMethodBinding {

	private ITypeBinding declaringClassTypeBinding;

	/**
	 * Constructs a new MethodBinding.
	 * 
	 * @param resolver
	 * @param modelElement
	 */
	public MethodBinding(BindingResolver resolver, IMethod modelElement) {
		super(resolver, modelElement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.ast.nodes.IMethodBinding#getDeclaringClass
	 * ()
	 */
	public ITypeBinding getDeclaringClass() {
		if (declaringClassTypeBinding == null) {
			IModelElement parent = modelElement.getDeclaringType();
			if (parent instanceof IType) {
				declaringClassTypeBinding = resolver
						.getTypeBinding((IType) parent);
			}
		}
		return declaringClassTypeBinding;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.ast.nodes.IMethodBinding#isConstructor()
	 */
	public boolean isConstructor() {
		try {
			return modelElement.isConstructor();
		} catch (ModelException e) {
			if (DLTKCore.DEBUG) {
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * Returns whether this method overrides the given method.
	 * 
	 * @param method
	 *            the method that is possibly overriden
	 * @return <code>true</code> if this method overrides the given method, and
	 *         <code>false</code> otherwise
	 */
	public boolean overrides(IMethodBinding method) {
		/*
		 * // TODO - Implement as the JDT implementation ? LookupEnvironment
		 * lookupEnvironment = this.resolver.lookupEnvironment(); return
		 * lookupEnvironment != null &&
		 * lookupEnvironment.methodVerifier().doesMethodOverride(this.binding,
		 * ((MethodBinding) otherMethod).binding);
		 */
		if (isConstructor() || method.isConstructor()) {
			return false;
		}
		if (getDeclaringClass().isSubTypeCompatible(method.getDeclaringClass())) {
			// Check the method name
			if (getName().equalsIgnoreCase(method.getName())) {
				// Check that the given method is not final
				int otherModifiers = method.getModifiers();
				if ((otherModifiers & Modifiers.AccFinal) == 0) {
					// Check that we are not narrowing the method visibility
					if ((otherModifiers & Modifiers.AccPrivate) != 0) {
						return false; // the other method is private, thus, this
						// one is not overriding it.
					}
					int thisModifiers = getModifiers();
					if ((otherModifiers & Modifiers.AccPublic) != 0
							|| (otherModifiers & Modifiers.AccDefault) != 0) {
						// 'public' (default in PHP) can be overridden only by
						// other 'public' methods.
						return (thisModifiers & Modifiers.AccPublic) != 0
								|| (thisModifiers & Modifiers.AccDefault) != 0;
					}
					if ((otherModifiers & Modifiers.AccProtected) != 0) {
						// 'protected' can be overridden by 'default', 'public'
						// or 'protected'.
						return (thisModifiers & Modifiers.AccProtected) != 0
								|| (thisModifiers & Modifiers.AccPublic) != 0
								|| (thisModifiers & Modifiers.AccDefault) != 0;
					}
				}
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.ast.nodes.IFunctionBinding#getReturnType()
	 */
	public ITypeBinding[] getReturnType() {
		return resolver.getMethodReturnTypeBinding(modelElement);
	}
}
