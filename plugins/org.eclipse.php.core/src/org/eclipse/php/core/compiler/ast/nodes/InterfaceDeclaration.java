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

import java.util.List;

import org.eclipse.dltk.ast.ASTListNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represents an interface declaration
 * 
 * <pre>
 * 
 * e.g.
 * 
 * interface MyInterface { }, interface MyInterface extends Interface1,
 * Interface2 { const MY_CONSTANT = 3; public function myFunction($a); }
 * </pre>
 */
public class InterfaceDeclaration extends TypeDeclaration
		implements IPHPDocAwareDeclaration, IRecoverable, IAttributed {

	private PHPDocBlock phpDoc;
	private boolean isRecovered;
	private List<Attribute> attributes;

	public InterfaceDeclaration(int start, int end, int nameStart, int nameEnd, String interfaceName,
			List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
		super(interfaceName, nameStart, nameEnd, start, end);
		if (interfaceName == null) {
			System.out.println("empty");
		}

		this.phpDoc = phpDoc;

		int nodesStart = body.sourceStart() - 1;
		ASTListNode parentsList = new ASTListNode(nodesStart, nodesStart);
		for (TypeReference intface : interfaces) {
			parentsList.addNode(intface);
			if (parentsList.sourceStart() > intface.sourceStart()) {
				parentsList.setStart(intface.sourceStart());
			}
		}

		if (parentsList.getChilds().size() > 0) {
			setSuperClasses(parentsList);
		}

		setBody(body);

		setModifier(Modifiers.AccInterface);
	}

	@Override
	public PHPDocBlock getPHPDoc() {
		return phpDoc;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.INTERFACE_DECLARATION;
	}

	@Override
	public boolean isRecovered() {
		return isRecovered;
	}

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

	@Override
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
}
