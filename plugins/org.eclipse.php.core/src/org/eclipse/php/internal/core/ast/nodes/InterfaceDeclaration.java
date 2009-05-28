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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.match.ASTMatcher;
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

	public static final ChildPropertyDescriptor NAME_PROPERTY = 
		new ChildPropertyDescriptor(InterfaceDeclaration.class, "name", Identifier.class, MANDATORY, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildListPropertyDescriptor INTERFACES_PROPERTY = 
		new ChildListPropertyDescriptor(InterfaceDeclaration.class, "interfaces", Identifier.class, NO_CYCLE_RISK); //$NON-NLS-1$
	public static final ChildPropertyDescriptor BODY_PROPERTY = 
		new ChildPropertyDescriptor(InterfaceDeclaration.class, "body", Block.class, MANDATORY, CYCLE_RISK); //$NON-NLS-1$

	@Override
	protected ChildPropertyDescriptor getBodyProperty() {
		return BODY_PROPERTY;
	}

	@Override
	protected ChildListPropertyDescriptor getInterfacesProperty() {
		return INTERFACES_PROPERTY;
	}

	@Override
	protected ChildPropertyDescriptor getNameProperty() {
		return NAME_PROPERTY;
	}	
	
	/**
	 * A list of property descriptors (element type: 
	 * {@link StructuralPropertyDescriptor}),
	 * or null if uninitialized.
	 */
	private static final List<StructuralPropertyDescriptor> PROPERTY_DESCRIPTORS;
	
	static {
		List<StructuralPropertyDescriptor> propertyList = new ArrayList<StructuralPropertyDescriptor>(3);
		propertyList.add(NAME_PROPERTY);
		propertyList.add(INTERFACES_PROPERTY);
		propertyList.add(BODY_PROPERTY);
		PROPERTY_DESCRIPTORS = Collections.unmodifiableList(propertyList);
	}		

	private InterfaceDeclaration(int start, int end, AST ast, Identifier interfaceName, Identifier[] interfaces, Block body) {
		super(start, end, ast, interfaceName, interfaces, body);
	}
	
	public InterfaceDeclaration(int start, int end, AST ast, Identifier interfaceName, List interfaces, Block body) {
		this(start, end, ast, interfaceName, (Identifier[]) interfaces.toArray(new Identifier[interfaces.size()]), body);
	}

	public InterfaceDeclaration(AST ast) {
		super(ast);
	}
	
	public void accept0(Visitor visitor) {
		final boolean visit = visitor.visit(this);
		if (visit) {
			childrenAccept(visitor);
		}
		visitor.endVisit(this);
	}	

	public void childrenAccept(Visitor visitor) {
		getName().accept(visitor);
		final List interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.accept(visitor);
		}
		getBody().accept(visitor);
	}

	public void traverseTopDown(Visitor visitor) {
		accept(visitor);
		getName().traverseTopDown(visitor);
		final List interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.traverseTopDown(visitor);
		}
		getBody().traverseTopDown(visitor);
	}

	public void traverseBottomUp(Visitor visitor) {
		getName().traverseBottomUp(visitor);
		final List interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.traverseBottomUp(visitor);
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
		final List interfaes = interfaces();
		for (Object node : interfaes) {
			ASTNode inter = (ASTNode) node;
			inter.toString(buffer, TAB + TAB + tab);
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
	
	/* 
	 * Method declared on ASTNode.
	 */
	public boolean subtreeMatch(ASTMatcher matcher, Object other) {
		// dispatch to correct overloaded match method
		return matcher.match(this, other);
	}

	@Override
	ASTNode clone0(AST target) {
		final Identifier name = ASTNode.copySubtree(target, getName());
		final Block body = ASTNode.copySubtree(target, getBody());
		final List interfaces = ASTNode.copySubtrees(target, interfaces());
		final InterfaceDeclaration result = new InterfaceDeclaration(getStart(), getEnd(), target, name, interfaces, body);
		return result;
	}

	@Override
	List<StructuralPropertyDescriptor> internalStructuralPropertiesForType(PHPVersion apiLevel) {
		return PROPERTY_DESCRIPTORS;
	}
}
