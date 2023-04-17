/*******************************************************************************
 * Copyright (c) 2023 Dawid Pakuła and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;

/**
 * Union union representation
 * 
 * @author michael
 */
public class DNFTypeReference extends TypeReference {

	public final static int T_UNION = 1;
	public final static int T_INTERSECTION = 2;

	private final List<TypeReference> references;
	private final int type;

	public DNFTypeReference(int start, int end, List<TypeReference> references, int type) {
		super(start, end, mergeNames(references, type));
		this.references = references;
		this.type = type;
	}

	@Override
	public void traverse(ASTVisitor pVisitor) throws Exception {
		if (pVisitor.visit(this)) {
			for (TypeReference ref : references) {
				ref.traverse(pVisitor);
			}
			pVisitor.endvisit(this);
		}
	}

	public int getType() {
		return type;
	}

	public List<TypeReference> getReferences() {
		return references;
	}

	private static String mergeNames(List<TypeReference> list, int type) {
		StringBuilder sb = new StringBuilder();
		for (TypeReference ref : list) {
			if (!sb.isEmpty()) {
				switch (type) {
				case T_UNION:
					sb.append('|');
					break;
				case T_INTERSECTION:
					sb.append('&');
					break;
				}
			}
			sb.append(ref instanceof FullyQualifiedReference ? ((FullyQualifiedReference) ref).getFullyQualifiedName()
					: ref.getName());
		}

		return sb.toString();
	}
}
