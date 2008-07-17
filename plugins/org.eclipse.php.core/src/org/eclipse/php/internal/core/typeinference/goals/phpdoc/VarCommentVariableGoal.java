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
package org.eclipse.php.internal.core.typeinference.goals.phpdoc;

import org.eclipse.dltk.ast.expressions.Expression;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.AbstractGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.VarComment;

/**
 * This goal purpose is to determine variable type from the attached to its declaration "@var" comment
 */
public class VarCommentVariableGoal extends AbstractGoal {

	private VarComment varComment;
	private Expression varNode;

	public VarCommentVariableGoal(IContext context, VarComment varComment, Expression varNode) {
		super(context);
		this.varComment = varComment;
		this.varNode = varNode;
	}

	public VarComment getVarComment() {
		return varComment;
	}

	public Expression getVarNode() {
		return varNode;
	}
}
