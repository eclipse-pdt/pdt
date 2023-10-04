/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
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
 * e.g.
 * 
 * class MyClass { }, class MyClass extends SuperClass implements Interface1,
 * Interface2 { const MY_CONSTANT = 3; public static final $myVar = 5, $yourVar;
 * var $anotherOne; private function myFunction($a) { } }
 * </pre>
 */
public class ClassDeclaration extends TypeDeclaration implements IPHPDocAwareDeclaration, IRecoverable, IAttributed {

	private PHPDocBlock phpDoc;
	private TypeReference superClass;
	private List<TypeReference> interfaceList;
	private boolean isRecovered;
	private List<Attribute> attributes;

	public ClassDeclaration(int start, int end, int nameStart, int nameEnd, int modifier, String className,
			TypeReference superClass, List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
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
			interfaceList = new LinkedList<>();
		}
		interfaceList.add(iface);
	}

	public void setInterfaceList(List<TypeReference> interfaceList) {
		this.interfaceList = interfaceList;
	}

	@Override
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

	@Override
	public List<String> getSuperClassNames() {
		List<String> names = new LinkedList<>();
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

	@Override
	public final void addSuperClass(ASTNode expression) {
		throw new IllegalStateException("Use setSuperClass() or setInterfaceList()/addInterface() instead"); //$NON-NLS-1$
	}

	@Override
	public final void setSuperClasses(ASTListNode exprList) {
		throw new IllegalStateException("Use setSuperClass() or setInterfaceList()/addInterface() instead"); //$NON-NLS-1$
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.CLASS_DECLARATION;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {

		if (visitor.visit(this)) {
			if (attributes != null) {
				for (Attribute attr : attributes) {
					attr.traverse(visitor);
				}
			}
			if (this.getSuperClasses() != null) {
				this.getSuperClasses().traverse(visitor);
			}
			if (this.fBody != null) {
				fBody.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.php.internal.core.compiler.ast.nodes.IRecoverable#isRecovered
	 * ()
	 */
	@Override
	public boolean isRecovered() {
		return isRecovered;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.php.internal.core.compiler.ast.nodes.IRecoverable#
	 * setRecovered(boolean)
	 */
	@Override
	public void setRecovered(boolean isRecovered) {
		this.isRecovered = isRecovered;
	}

	/**
	 * We don't print anything - we use {@link ASTPrintVisitor} instead
	 */
	@Override
	public final void printNode(CorePrinter output) {
	}

	@Override
	public String toString() {
		return ASTPrintVisitor.toXMLString(this);
	}

	@Override
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public void setPHPDoc(PHPDocBlock block) {
		this.phpDoc = block;
	}
}
