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
 * Represents an interface declaration
 * <pre>
 * <pre>e.g.<pre> 
 * interface MyInterface { },
 * interface MyInterface extends Interface1, Interface2 { 
 *	 const MY_CONSTANT = 3; 
 *	 public function myFunction($a);
 * }
 */
public class InterfaceDeclaration extends TypeDeclaration {

	private InterfaceDeclaration(int start, int end, Identifier interfaceName, Identifier[] interfaces, Block body) {
		super(start, end, interfaceName, interfaces, body);
	}

	public InterfaceDeclaration(int start, int end, Identifier interfaceName, List interfaces, Block body) {
		this(start, end, interfaceName, (Identifier[]) interfaces.toArray(new Identifier[interfaces.size()]), body);
	}

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public void childrenAccept(Visitor visitor) {
		getName().accept(visitor);
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].accept(visitor);
		}
		getBody().accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getName().traverseTopDown(visitor);
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < getInterfaces().length; i++) {
			interfaces[i].traverseTopDown(visitor);
		}
		getBody().traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getName().traverseBottomUp(visitor);
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].traverseBottomUp(visitor);
		}
		getBody().traverseBottomUp(visitor);
		accept(visitor);
	}

	public void toString(StringBuffer buffer, String tab) {
		buffer.append(tab).append("<InterfaceDeclaration"); //$NON-NLS-1$
		appendInterval(buffer);
		buffer.append(">\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<InterfaceName>\n"); //$NON-NLS-1$
		getName().toString(buffer, TAB + TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("</InterfaceName>\n"); //$NON-NLS-1$
		buffer.append(tab).append(TAB).append("<Interfaces>\n"); //$NON-NLS-1$
		Identifier[] interfaces = getInterfaces();
		for (int i = 0; interfaces != null && i < interfaces.length; i++) {
			interfaces[i].toString(buffer, TAB + TAB + tab);
			buffer.append("\n"); //$NON-NLS-1$
		}
		buffer.append(tab).append(TAB).append("</Interfaces>\n"); //$NON-NLS-1$
		getBody().toString(buffer, TAB + tab);
		buffer.append("\n"); //$NON-NLS-1$
		buffer.append(tab).append("</InterfaceDeclaration>"); //$NON-NLS-1$
	}

	public int getType() {
		return ASTNode.INTERFACE_DECLARATION;
	}

}
