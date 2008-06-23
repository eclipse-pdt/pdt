/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.BodyDeclaration.Modifier;
import org.eclipse.php.internal.core.typeinference.FakeField;

/**
 * A variable binding represents either a field of a class or interface, or 
 * a local variable declaration (including formal parameters, local variables, 
 * and exception variables) or a global variable.
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 *  FIXME - need implementation
 * @author shalom
 */
public class VariableBinding implements IVariableBinding {

	private static final int VALID_MODIFIERS = Modifiers.AccPublic | Modifiers.AccProtected | Modifiers.AccPrivate | Modifiers.AccDefault | Modifiers.AccConst | Modifiers.AccStatic | Modifiers.AccGlobal;

	private final BindingResolver resolver;
	private final IField modelElement;
	private boolean isFakeField;

	private ITypeBinding declaringClassTypeBinding;

	/**
	 * 
	 */
	public VariableBinding(BindingResolver resolver, IField modelElement) {
		this.resolver = resolver;
		this.modelElement = modelElement;
		this.isFakeField = modelElement instanceof FakeField;
	}

	/**
	 * Returns this binding's constant value if it has one.
	 * Some variables may have a value computed at compile-time. If the variable has
	 * no compile-time computed value, the result is <code>null</code>.
	 * 
	 * @return the constant value, or <code>null</code> if none
	 * @since 3.0
	 */
	public Object getConstantValue() {
		// TODO ?
		return null;
	}

	/**
	 * Returns the type binding representing the class or interface
	 * that declares this field.
	 * <p>
	 * The declaring class of a field is the class or interface of which it is
	 * a member. Local variables have no declaring class. The field length of an 
	 * array type has no declaring class.
	 * </p>
	 * 
	 * @return the binding of the class or interface that declares this field,
	 *   or <code>null</code> if none
	 */
	public ITypeBinding getDeclaringClass() {
		if (declaringClassTypeBinding == null) {
			IModelElement parent = modelElement.getDeclaringType();
			if (parent instanceof IType) {
				declaringClassTypeBinding = resolver.getTypeBinding((IType) parent);
			}
		}
		return declaringClassTypeBinding;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#getDeclaringFunction()
	 */
	public IFunctionBinding getDeclaringFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#getName()
	 */
	public String getName() {
		return modelElement.getElementName();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#getType()
	 */
	public ITypeBinding getType() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#getVariableId()
	 */
	public int getVariableId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#isField()
	 */
	public boolean isField() {
		return IModelElement.FIELD == modelElement.getElementType() && !isFakeField;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#isGlobal()
	 */
	public boolean isGlobal() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#isLocal()
	 */
	public boolean isLocal() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IVariableBinding#isParameter()
	 */
	public boolean isParameter() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getKey()
	 */
	public String getKey() {
		return modelElement.getHandleIdentifier();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getKind()
	 */
	public int getKind() {
		return IBinding.VARIABLE;
	}

	/**
	 * Returns the modifiers for this binding.
	 *
	 * @return the bit-wise or of <code>Modifier</code> constants
	 * @see Modifier
	 */
	public int getModifiers() {
		if (isField()) {
			try {
				return ((IField) modelElement).getFlags() & VALID_MODIFIERS;
			} catch (ModelException e) {
				Logger.logException(e);
			}
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#getPHPElement()
	 */
	public IModelElement getPHPElement() {
		return modelElement;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.ast.nodes.IBinding#isDeprecated()
	 */
	public boolean isDeprecated() {
		return false;
	}
}
