/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies - adapt for PHP refactoring
 *******************************************************************************/
package org.eclipse.php.refactoring.core.visitor;

import org.eclipse.php.core.ast.nodes.ASTError;
import org.eclipse.php.core.ast.nodes.FunctionDeclaration;
import org.eclipse.php.core.ast.visitor.AbstractVisitor;

/**
 * This purpose of this visitor is to check if there is a syntax error in the
 * same scope of the selection.
 * 
 * @author Eden K., 2008
 * 
 */
public class ScopeSyntaxErrorsVisitor extends AbstractVisitor {

	public boolean hasSyntaxError = false;

	@Override
	public boolean visit(FunctionDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(ASTError error) {
		hasSyntaxError = true;
		return false;
	}

}
