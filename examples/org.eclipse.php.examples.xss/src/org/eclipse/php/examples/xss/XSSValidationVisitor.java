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
package org.eclipse.php.examples.xss;

import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblem;
import org.eclipse.dltk.compiler.problem.ProblemSeverities;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.php.internal.core.compiler.ast.nodes.ArrayVariableReference;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.visitor.PHPASTVisitor;

/**
 * This validator searches for unsafe places that can be used for XSS, and
 * notifies developer about them. Unsafe places are considered to be:
 * <ul>
 *  <li>Using direct reference to URL parameter variables ($_GET, $_POST, $_REQUEST) 
 *  	without testing it with isset() and htmlentities() first.</li>
 *  <li>Add more...</li>
 * </ul>
 */
public class XSSValidationVisitor extends PHPASTVisitor {

	private IBuildContext context;
	private boolean hasSafeCallInParent;

	public XSSValidationVisitor(IBuildContext context) {
		this.context = context;
	}
	
	public boolean visit(PHPCallExpression node) throws Exception {
		// Check the parent: it should be either isset() or htmlentities() call
		if (node.getReceiver() == null) { // if this is a function call, not method
			String funcName = node.getName();
			if ("isset".equalsIgnoreCase(funcName) || "htmlentities".equalsIgnoreCase(funcName)) {
				hasSafeCallInParent = true;
			}
		}
		return visitGeneral(node);
	}

	public boolean endvisit(PHPCallExpression node) throws Exception {
		hasSafeCallInParent = false;
		endvisitGeneral(node);
		return true;
	}

	/**
	 * Checks whether this variable is a reference to the URL parameter.
	 * @param s Variable reference node
	 * @return
	 */
	protected boolean isURLParemeterVariable(VariableReference s) {
		String name = s.getName();
		return ("$_GET".equals(name) || "$_POST".equals(name) || "$_REQUEST".equals(name));
	}

	public boolean visit(ArrayVariableReference s) throws Exception {
		if (isURLParemeterVariable(s) && !hasSafeCallInParent) {
			context.getProblemReporter().reportProblem(
				new DefaultProblem(
					context.getFile().getName(),
					"Unsafe use of " + s.getName() + ": possible XSS attack",
					IProblem.Unclassified,
					new String[0],
					ProblemSeverities.Warning,
					s.sourceStart(),
					s.sourceEnd(),
					context.getLineTracker().getLineNumberOfOffset(s.sourceStart()))
			);
		}
		return super.visit(s);
	}
}
