/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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

	public boolean visit(FunctionDeclaration node) {
		return false;
	}

	public boolean visit(ASTError error) {
		hasSyntaxError = true;
		return false;
	}

}
