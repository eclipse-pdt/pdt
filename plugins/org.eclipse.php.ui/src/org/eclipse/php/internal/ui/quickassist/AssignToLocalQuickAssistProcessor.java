/*******************************************************************************
 * Copyright (c) 2016 Dawid Pakuła and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dawid Pakuła - initial API and implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.quickassist;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.php.core.ast.nodes.ASTNode;
import org.eclipse.php.core.ast.nodes.Expression;
import org.eclipse.php.internal.ui.editor.contentassist.AssignToLocalCompletionProposal;
import org.eclipse.php.ui.text.correction.IInvocationContext;
import org.eclipse.php.ui.text.correction.IProblemLocation;
import org.eclipse.php.ui.text.correction.IQuickAssistProcessor;

public class AssignToLocalQuickAssistProcessor implements IQuickAssistProcessor {
	@Override
	public boolean hasAssists(IInvocationContext context) throws CoreException {
		if (context.getCoveringNode() == null) {
			return false;
		}
		final Expression mainExpression = AssignToLocalCompletionProposal.getMainExpression(context.getCoveringNode());
		if (mainExpression == null) {
			return false;
		}

		switch (mainExpression.getType()) {
		case ASTNode.METHOD_INVOCATION:
		case ASTNode.STATIC_METHOD_INVOCATION:
		case ASTNode.FUNCTION_INVOCATION:
		case ASTNode.CLASS_INSTANCE_CREATION:
		case ASTNode.ARRAY_ACCESS:
			return true;
		}

		return false;
	}

	@Override
	public IScriptCompletionProposal[] getAssists(IInvocationContext context, IProblemLocation[] locations)
			throws CoreException {
		final ASTNode coveringNode = context.getCoveringNode();
		if (hasAssists(context)) {
			return new IScriptCompletionProposal[] {
					new AssignToLocalCompletionProposal(context.getCompilationUnit(), coveringNode) };
		}

		return null;
	}
}
