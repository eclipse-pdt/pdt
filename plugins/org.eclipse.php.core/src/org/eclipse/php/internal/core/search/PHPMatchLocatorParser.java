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
package org.eclipse.php.internal.core.search;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.ast.declarations.FieldDeclaration;
import org.eclipse.dltk.ast.expressions.CallArgumentsList;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ast.references.ConstantReference;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.TypeReference;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.search.matching.MatchLocator;
import org.eclipse.dltk.core.search.matching.MatchLocatorParser;
import org.eclipse.dltk.core.search.matching.PatternLocator;
import org.eclipse.php.internal.core.compiler.ast.nodes.*;

public class PHPMatchLocatorParser extends MatchLocatorParser {

	public PHPMatchLocatorParser(MatchLocator locator) {
		super(locator);
	}

	protected void processStatement(ASTNode node, PatternLocator locator) {
		if (node instanceof FieldDeclaration) {
			locator.match((FieldDeclaration) node, getNodeSet());
		} else if (node instanceof ClassConstantDeclaration) {
			ClassConstantDeclaration constDecl = (ClassConstantDeclaration) node;
			ConstantReference constantName = constDecl.getConstantName();
			FieldDeclaration decl = new FieldDeclaration(constantName.getName(), constantName.sourceStart(), constantName.sourceEnd(), constDecl.sourceStart(), constDecl.sourceEnd());
			decl.setModifiers(Modifiers.AccConstant);
			locator.match(decl, getNodeSet());
		} else if(node instanceof Assignment){
			Expression left = ((Assignment)node).getVariable();
			if (left instanceof FieldAccess) { // class variable ($this->a = .)
				FieldAccess fieldAccess = (FieldAccess) left;
				Expression dispatcher = fieldAccess.getDispatcher();
				if (dispatcher instanceof VariableReference && "$this".equals(((VariableReference) dispatcher).getName())) { //$NON-NLS-1$
					Expression field = fieldAccess.getField();
					if (field instanceof SimpleReference) {
						SimpleReference ref = (SimpleReference) field;
						FieldDeclaration decl = new FieldDeclaration('$' + ref.getName(), ref.sourceStart(), ref.sourceEnd(), node.sourceStart(), node.sourceEnd());
						locator.match(decl, getNodeSet());
					}
				}
			} else if (left instanceof VariableReference) {
				FieldDeclaration decl = new FieldDeclaration(((VariableReference)left).getName(), left.sourceStart(), left.sourceEnd(), node.sourceStart(), node.sourceEnd());
				locator.match(decl, getNodeSet());
			}
		} else if (node instanceof TypeReference) {
			locator.match((TypeReference)node, getNodeSet());
		} else if (node instanceof CallExpression) {
			locator.match((CallExpression)node, getNodeSet());
		} else if (node instanceof Include) {
			Include include = (Include) node;
			if (include.getExpr() instanceof Scalar) {
				Scalar filePath = (Scalar) include.getExpr();
				CallExpression callExpression = new CallExpression(filePath.sourceStart(), filePath.sourceEnd(), null, "include", new CallArgumentsList());
				locator.match(callExpression, getNodeSet());
			}
		}
	}
}
