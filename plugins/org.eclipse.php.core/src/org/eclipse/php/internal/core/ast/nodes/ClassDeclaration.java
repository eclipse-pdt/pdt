/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.core.ast.nodes;

import java.util.List;

import org.eclipse.php.internal.core.ast.visitor.Visitor;

/**
 * Represents a class declaration
 * <pre>
 * <pre>e.g.<pre> 
 * class MyClass { },
 * class MyClass extends SuperClass implements Interface1, Interface2 { 
 *   const MY_CONSTANT = 3; 
 *   public static final $myVar = 5, $yourVar; 
 *   var $anotherOne; 
 *   private function myFunction($a) { }
 * }
 */
public class ClassDeclaration extends TypeDeclaration {

	public static final int MODIFIER_NONE = 0;
	public static final int MODIFIER_ABSTRACT = 1;
	public static final int MODIFIER_FINAL = 2;

	private final int modifier;
	private final Identifier superClass;

	private ClassDeclaration(int start, int end, int modifier, Identifier className, Identifier superClass, Identifier[] interfaces, Block body) {
		super(start, end, className, interfaces, body);

		this.modifier = modifier;
		this.superClass = superClass;

		if (superClass != null) {
			superClass.setParent(this);
		}
	}

	public ClassDeclaration(int start, int end, int modifier, Identifier className, Identifier superClass, List interfaces, Block body) {
		this(start, end, modifier, className, superClass, interfaces == null ? null : (Identifier[]) interfaces.toArray(new Identifier[interfaces.size()]), body);
	}

	public void accept(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		getName().accept(visitor);
		if (superClass != null) {
			superClass.accept(visitor);
		}
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].accept(visitor);
		}
		getBody().accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getName().traverseTopDown(visitor);
		if (superClass != null) {
			superClass.traverseTopDown(visitor);
		}
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].traverseTopDown(visitor);
		}
		getBody().traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getName().traverseBottomUp(visitor);
		if (superClass != null) {
			superClass.traverseBottomUp(visitor);
		}
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].traverseBottomUp(visitor);
		}
		getBody().traverseBottomUp(visitor);
		accept(visitor);
	}

	public static String getModifier(int modifier) {
		switch (modifier) {
			case MODIFIER_NONE:
				return ""; //$NON-NLS-1$
			case MODIFIER_ABSTRACT:
				return "abstract"; //$NON-NLS-1$
			case MODIFIER_FINAL:
				return "final"; //$NON-NLS-1$
			default:
				throw new IllegalArgumentException();
		}
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<ClassDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(" modifier='").append(getModifier(modifier)).append("'>\n"); //$NON-NLS-1$ //$NON-NLS-2$
		buffer.append(tab).append(TAB).append("<ClassName>\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</ClassName>\n"); //$NON-NLS-1$

		buffer.append(tab).append(TAB).append("<SuperClassName>\n"); //$NON-NLS-1$
		if (superClass != null) {
			superClass.toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</SuperClassName>\n"); //$NON-NLS-1$

		buffer.append(tab).append(TAB).append("<Interfaces>\n"); //$NON-NLS-1$
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Interfaces>\n"); //$NON-NLS-1$
		getBody().toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</ClassDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.CLASS_DECLARATION;
	}

	public int getModifier() {
		return modifier;
	}

	public Identifier getSuperClass() {
		return superClass;
	}
}
