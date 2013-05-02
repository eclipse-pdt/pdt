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
package org.eclipse.php.internal.core.ast.nodes;

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.core.compiler.PHPFlags;

/**
 * Base class for class member declarations
 */
public abstract class BodyDeclaration extends Statement {

	private int modifier;

	/**
	 * Should be implemented by concrete implementations of body  
	 */
	public abstract SimplePropertyDescriptor getModifierProperty();
	
	public BodyDeclaration(int start, int end, AST ast, int modifier, boolean shouldComplete) {
		super(start, end, ast);
		
		setModifier(shouldComplete ? completeModifier(modifier) : modifier);
	}

	public BodyDeclaration(int start, int end, AST ast, int modifier) {
		this(start, end, ast, modifier, false);
	}

	public BodyDeclaration(AST ast) {
		super(ast);
	}

	/**
	 * Complets the modidifer to public if needed 
	 * @param mod
	 */
	private static int completeModifier(int mod) {
		if (!PHPFlags.isPrivate(mod) && !PHPFlags.isProtected(mod)) {
			mod |= Modifiers.AccPublic;
		}
		return mod;
	}

	public String getModifierString() {
		return PHPFlags.toString(modifier);
	}

	public int getModifier() {
		return modifier;
	}
	
	/**
	 * Sets the operator of this assignment expression.
	 * 
	 * @param assignmentOperator the assignment operator
	 * @exception IllegalArgumentException if the argument is incorrect
	 */ 
	public void setModifier(int modifier) {
		if (PHPFlags.toString(modifier) == null) {
			throw new IllegalArgumentException("Invalid modifier"); //$NON-NLS-1$
		}
		preValueChange(getModifierProperty());
		this.modifier = modifier;
		postValueChange(getModifierProperty());
	}	

	int internalGetSetIntProperty(SimplePropertyDescriptor property, boolean get, int value) {
		if (property == getModifierProperty()) {
			if (get) {
				return getModifier();
			} else {
				setModifier((Integer) value);
				return 0;
			}
		}
		// allow default implementation to flag the error
		return super.internalGetSetIntProperty(property, get, value);
	}

	/**
	 * This is a utility for member modifiers 
	 * @deprecated use DLTK {@link Modifiers} instead 
	 */
	public static class Modifier {
		/**
		 * The <code>int</code> value representing the <code>public</code> modifier.
		 */
		public static final int PUBLIC = 0x00000001;

		/**
		 * The <code>int</code> value representing the <code>private</code> modifier.
		 */
		public static final int PRIVATE = 0x00000002;

		/**
		 * The <code>int</code> value representing the <code>protected</code> modifier.
		 */
		public static final int PROTECTED = 0x00000004;

		/**
		 * The <code>int</code> value representing the <code>static</code> modifier.
		 */
		public static final int STATIC = 0x00000008;

		/**
		 * The <code>int</code> value representing the <code>final</code> modifier.
		 */
		public static final int FINAL = 0x00000010;

		/**
		 * The <code>int</code> value representing the <code>abstract</code> modifier.
		 */
		public static final int ABSTRACT = 0x00000400;

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>public</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>public</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isPublic(int mod) {
			return (mod & PUBLIC) != 0;
		}

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>private</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>private</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isPrivate(int mod) {
			return (mod & PRIVATE) != 0;
		}

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>protected</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>protected</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isProtected(int mod) {
			return (mod & PROTECTED) != 0;
		}

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>static</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>static</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isStatic(int mod) {
			return (mod & STATIC) != 0;
		}

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>final</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>final</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isFinal(int mod) {
			return (mod & FINAL) != 0;
		}

		/**
		 * Return <tt>true</tt> if the integer argument includes the
		 * <tt>abstract</tt> modifer, <tt>false</tt> otherwise.
		 *
		 * @param 	mod a set of modifers
		 * @return <tt>true</tt> if <code>mod</code> includes the
		 * <tt>abstract</tt> modifier; <tt>false</tt> otherwise.
		 */
		public static boolean isAbstract(int mod) {
			return (mod & ABSTRACT) != 0;
		}

		public static String toString(int mod) {
			StringBuffer sb = new StringBuffer();

			if ((mod & PUBLIC) != 0) {
				sb.append("public "); //$NON-NLS-1$
			}
			if ((mod & PROTECTED) != 0) {
				sb.append("protected "); //$NON-NLS-1$
			}
			if ((mod & PRIVATE) != 0) {
				sb.append("private "); //$NON-NLS-1$
			}

			//Canonical order
			if ((mod & ABSTRACT) != 0) {
				sb.append("abstract "); //$NON-NLS-1$
			}
			if ((mod & STATIC) != 0) {
				sb.append("static "); //$NON-NLS-1$
			}
			if ((mod & FINAL) != 0) {
				sb.append("final "); //$NON-NLS-1$
			}

			int len;
			if ((len = sb.length()) > 0) { /* trim trailing space */
				return sb.toString().substring(0, len - 1);
			}
			return ""; //$NON-NLS-1$
		}
	}

}
