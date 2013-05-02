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
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents a class declaration
 * 
 * <pre>
 * 
 * <pre>e.g.
 * 
 * <pre>
 * class MyClass { },
 * class MyClass extends SuperClass implements Interface1, Interface2 {
 *   const MY_CONSTANT = 3;
 *   public static final $myVar = 5, $yourVar;
 *   var $anotherOne;
 *   private function myFunction($a) { }
 * }
 */
public class ClassDeclaration extends TypeDeclaration implements
		IPHPDocAwareDeclaration, IRecoverable {

	private PHPDocBlock phpDoc;
	private TypeReference superClass;
	private List<TypeReference> interfaceList;
	private boolean isRecovered;

	public ClassDeclaration(int start, int end, int nameStart, int nameEnd,
			int modifier, String className, TypeReference superClass,
			List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
		super(className, nameStart, nameEnd, start, end);

		setModifiers(modifier);
		this.phpDoc = phpDoc;

		this.superClass = superClass;
		this.interfaceList = interfaces;

		setBody(body);
	}

	public TypeReference getSuperClass() {
		return superClass;
	}

	public String getSuperClassName() {
		if (superClass != null) {
			return superClass.getName();
		}
		return null;
	}

	public Collection<TypeReference> getInterfaceList() {
		return interfaceList;
	}

	public String[] getInterfaceNames() {
		if (interfaceList != null) {
			String[] names = new String[interfaceList.size()];
			int i = 0;
			for (TypeReference iface : interfaceList) {
				names[i++] = iface.getName();
			}
			return names;
		}
		return null;
	}

	public void setSuperClass(TypeReference superClass) {
		this.superClass = superClass;
	}

	public void addInterface(TypeReference iface) {
		if (interfaceList == null) {
			interfaceList = new LinkedList<TypeReference>();
		}
		interfaceList.add(iface);
	}

	public void setInterfaceList(List<TypeReference> interfaceList) {
		this.interfaceList = interfaceList;
	}

	public ASTListNode getSuperClasses() {
		int start = getBodyStart() - 1;
		ASTListNode listNode = new ASTListNode(start, start);
		if (superClass != null) {
			listNode.addNode(superClass);
			if (superClass.sourceStart() < start) {
				start = superClass.sourceStart();
			}
		}
		if (interfaceList != null) {
			for (TypeReference iface : interfaceList) {
				listNode.addNode(iface);
				if (iface.sourceStart() < start) {
					start = iface.sourceStart();
				}
			}
		}
		listNode.setStart(start);
		return listNode;
	}

	public List<String> getSuperClassNames() {
		List<String> names = new LinkedList<String>();
		if (superClass != null) {
			names.add(superClass.getName());
		}
		if (interfaceList != null) {
			for (TypeReference iface : interfaceList) {
				names.add(iface.getName());
			}
		}
		return names;
	}

	public final void addSuperClass(ASTNode expression) {
		throw new IllegalStateException(
				"Use setSuperClass() or setInterfaceList()/addInterface() instead"); //$NON-NLS-1$
	}

	public final void setSuperClasses(ASTListNode exprList) {
		throw new IllegalStateException(
				"Use setSuperClass() or setInterfaceList()/addInterface() instead"); //$NON-NLS-1$
	}

	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	public int getKind() {
		return ASTNodeKinds.CLASS_DECLARATION;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.compiler.ast.nodes.IRecoverable#isRecovered()
	 */
	public boolean isRecovered() {
		return isRecovered;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.php.internal.core.compiler.ast.nodes.IRecoverable#setRecovered(boolean)
	 */
	public void setRecovered(boolean isRecovered) {
		this.isRecovered = isRecovered;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	public final void printNode(CorePrinter output) {
	}

	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}
}
