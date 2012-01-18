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

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.SimpleReference;

/**
 * Represents static function invocation. Holds the function invocation and the
 * class name.
 * 
 * <pre>e.g.
 * 
 * <pre>
 * MyClass::foo($a)
 */
public class StaticMethodInvocation extends PHPCallExpression {
	/**
	 * this property is for php5.4 TODO there are lots of changes for this
	 */
	private Expression functionName;

	public StaticMethodInvocation(ASTNode receiver, String name,
			CallArgumentsList args) {
		super(receiver, name, args);
	}

	public StaticMethodInvocation(int start, int end, ASTNode receiver,
			Expression functionName, SimpleReference name,
			CallArgumentsList args) {
		super(start, end, receiver, name, args);
		this.functionName = functionName;
	}

	public StaticMethodInvocation(int start, int end, ASTNode receiver,
			SimpleReference name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public StaticMethodInvocation(int start, int end, ASTNode receiver,
			String name, CallArgumentsList args) {
		super(start, end, receiver, name, args);
	}

	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (functionName == null) {
			super.traverse(pVisitor);
		} else {
			if (pVisitor.visit(this)) {
				if (receiver != null) {
					receiver.traverse(pVisitor);
				}
				functionName.traverse(pVisitor);
				if (getArgs() != null) {
					getArgs().traverse(pVisitor);
				}
				if (getArrayDereferenceList() != null) {
					getArrayDereferenceList().traverse(pVisitor);
				}
				pVisitor.endvisit(this);
			}
		}
	}

	@Override
	public String getName() {
		if (functionName != null && functionName instanceof SimpleReference) {
			return ((SimpleReference) functionName).getName();
		}
		return super.getName();
	}

	public int getKind() {
		return ASTNodeKinds.STATIC_METHOD_INVOCATION;
	}
}
