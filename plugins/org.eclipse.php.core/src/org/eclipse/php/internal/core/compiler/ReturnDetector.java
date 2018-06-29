/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
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
package org.eclipse.php.internal.core.compiler;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.declarations.MethodDeclaration;
import org.eclipse.dltk.ast.declarations.TypeDeclaration;
import org.eclipse.php.core.compiler.ast.nodes.ReturnStatement;
import org.eclipse.php.core.compiler.ast.nodes.YieldExpression;
import org.eclipse.php.core.compiler.ast.visitor.PHPASTVisitor;

public class ReturnDetector extends PHPASTVisitor {
	private boolean found = false;

	public boolean hasReturn() {
		return found;
	}

	@Override
	public boolean visitGeneral(ASTNode node) throws Exception {
		return !found;
	}

	@Override
	public boolean visit(ReturnStatement node) {
		found = true;
		return false;
	}

	@Override
	public boolean visit(YieldExpression node) {
		found = true;
		return false;
	}

	@Override
	public boolean visit(MethodDeclaration dec) {
		return false;
	}

	public boolean visit(FieldDeclaration dec) {
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration dec) {
		return false;
	}
}