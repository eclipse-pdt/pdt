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

import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class MethodHighlighting extends AbstractSemanticHighlighting {

	protected class MethodApply extends AbstractSemanticApply {
		@Override
		public boolean visit(MethodDeclaration classMethodDeclaration) {
			Identifier functionName = classMethodDeclaration.getFunction()
					.getFunctionName();
			highlight(functionName);
			return true;
		}

		/**
		 * Mark foo() on: $a->foo();
		 */
		public boolean visit(MethodInvocation methodInvocation) {
			checkDispatch(methodInvocation.getMethod().getFunctionName()
					.getName());
			return true;
		}

		/**
		 * @param dispatch
		 * @throws RuntimeException
		 */
		private void checkDispatch(ASTNode node) {
			if (node.getType() == ASTNode.IDENTIFIER) {
				highlight(node);
			}
			if (node.getType() == ASTNode.VARIABLE) {
				Variable id = (Variable) node;
				checkDispatch(id.getName());
			}
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new MethodApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false);
	}

	public String getDisplayName() {
		return "Methods";
	}
}