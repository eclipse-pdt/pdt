/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class FunctionHighlighting extends AbstractSemanticHighlighting {

	protected class FunctionApply extends AbstractSemanticApply {

		/**
		 * Visit the function declaration
		 */
		public boolean visit(FunctionDeclaration functionDeclaration) {
			if (functionDeclaration.getParent().getType() != ASTNode.METHOD_DECLARATION) {
				highlight(functionDeclaration.getFunctionName());
			}
			return true;
		}

		/**
		 * skip static call invocation, and add to changes list the global calls
		 */
		public boolean visit(FunctionInvocation functionInvocation) {
			final Expression functionName = functionInvocation
					.getFunctionName().getName();
			final int invocationParent = functionInvocation.getParent()
					.getType();
			if (invocationParent != ASTNode.METHOD_INVOCATION
					&& invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
				highlightFunctionName(functionName);
			}
			return true;
		}

		private void highlightFunctionName(Expression functionName) {
			if (functionName instanceof NamespaceName) {
				List<Identifier> segments = ((NamespaceName) functionName)
						.segments();
				Identifier segment = segments.get(segments.size() - 1);
				highlight(segment);
			} else {
				highlight(functionName);
			}
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new FunctionApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setItalicByDefault(true);
	}

	public String getDisplayName() {
		return Messages.FunctionHighlighting_0;
	}
}
