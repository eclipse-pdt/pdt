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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Statement;
import org.eclipse.dltk.utils.CorePrinter;
import org.eclipse.php.internal.core.compiler.ast.visitor.ASTPrintVisitor;

/**
 * Represent a 'use' statement.
 * 
 * <pre>
 * e.g.
 * 
 * <pre>
 * use A; use A as B; use \A\B as C;
 */
public class TraitUseStatement extends Statement {

	private List<TypeReference> traitList;
	private List<TraitStatement> tsList;

	public TraitUseStatement(int start, int end, List<TypeReference> traitList, List<TraitStatement> tsList) {
		super(start, end);

		assert traitList != null;
		this.traitList = traitList;
		this.tsList = tsList;
	}

	@Override
	public void traverse(ASTVisitor visitor) throws Exception {
		if (visitor.visit(this)) {
			if (traitList != null) {
				for (TypeReference s : traitList) {
					s.traverse(visitor);
				}
			}

			if (tsList != null) {
				for (TraitStatement s : tsList) {
					s.traverse(visitor);
				}
			}
			visitor.endvisit(this);
		}
	}

	public List<TypeReference> getTraitList() {
		return traitList;
	}

	public void setTraitList(List<TypeReference> traitList) {
		this.traitList = traitList;
	}

	public List<TraitStatement> getTsList() {
		if (tsList == null) {
			tsList = new ArrayList<>();
		}
		return tsList;
	}

	public void setTsList(List<TraitStatement> tsList) {
		this.tsList = tsList;
	}

	@Override
	public int getKind() {
		return ASTNodeKinds.USE_STATEMENT;
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
}
