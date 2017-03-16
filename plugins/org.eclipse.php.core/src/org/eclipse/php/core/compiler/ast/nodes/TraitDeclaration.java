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
package org.eclipse.php.core.compiler.ast.nodes;

import java.util.List;

import org.eclipse.dltk.ast.ASTVisitor;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.statements.Block;

public class TraitDeclaration extends ClassDeclaration {

	public TraitDeclaration(int start, int end, int nameStart, int nameEnd, int modifier, String className,
			TypeReference superClass, List<TypeReference> interfaces, Block body, PHPDocBlock phpDoc) {
		super(start, end, nameStart, nameEnd, modifier, className, superClass, interfaces, body, phpDoc);
	}

	/**
	 * Used to walk on tree. traverse order: superclasses, body.
	 */
	public void traverse(ASTVisitor visitor) throws Exception {

		if (visitor.visit(this)) {
			if (this.fBody != null) {
				fBody.traverse(visitor);
			}
			visitor.endvisit(this);
		}
	}

}
