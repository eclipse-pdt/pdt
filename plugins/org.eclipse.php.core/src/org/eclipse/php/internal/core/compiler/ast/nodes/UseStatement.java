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
package org.eclipse.php.internal.core.compiler.ast.nodes;

import java.util.Collection;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a 'use' statement.
 * <pre>e.g.<pre>
 * use A;
 * use A as B;
 * use \A\B as C;
 */
public class UseStatement extends Statement {
	
	public static class UsePart {
		
		public String namespace;
		public String alias;
		
		public UsePart(String namespace, String alias) {
			this.namespace = namespace;
			this.alias = alias;
		}

		public String toString() {
			StringBuilder buf = new StringBuilder("[USE: ").append(namespace);
			if (alias != null) {
				buf.append(" AS ").append(alias);
			}
			buf.append("]");
			return buf.toString();
		}
	}

	private List<UsePart> parts;

	public UseStatement(int start, int end, List<UsePart> parts) {
		super(start, end);

		assert parts != null;
		this.parts = parts;
	}
	
	public void traverse(ASTVisitor visitor) throws Exception {
		visitor.visit(this);
		visitor.endvisit(this);
	}

	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
	}

	public Collection<UsePart> getParts() {
		return parts;
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
