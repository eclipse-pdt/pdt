/*******************************************************************************
 * Copyright (c) 2006, 2017 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import java.util.List;

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.language.keywords.PHPKeywords;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class FunctionHighlighting extends AbstractSemanticHighlighting {
	protected class FunctionApply extends AbstractSemanticApply {

		/**
		 * Visit the function declaration
		 */
		@Override
		public boolean visit(FunctionDeclaration functionDeclaration) {
			if (functionDeclaration.getParent().getType() != ASTNode.METHOD_DECLARATION) {
				highlight(functionDeclaration.getFunctionName());
			}
			return true;
		}

		/**
		 * skip static call invocation, and add to changes list the global calls
		 */
		@Override
		public boolean visit(FunctionInvocation functionInvocation) {
			final Expression functionName = functionInvocation.getFunctionName().getName();
			final int invocationParent = functionInvocation.getParent().getType();
			if (invocationParent != ASTNode.METHOD_INVOCATION && invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
				highlightFunctionName(functionName);
			}
			return true;
		}

		private void highlightFunctionName(Expression functionName) {
			if (functionName instanceof NamespaceName) {
				List<Identifier> segments = ((NamespaceName) functionName).segments();
				if (segments.size() > 0) {
					Identifier segment = segments.get(segments.size() - 1);
					highlight(segment);
				}
			} else if (functionName instanceof Identifier) {
				PHPKeywords keywords = PHPKeywords.getInstance(functionName.getAST().apiLevel());
				String name = ((Identifier) functionName).getName();
				if (!keywords.getKeywordNames().contains(name.toLowerCase())) {
					highlight(functionName);
				}
			} else if (functionName instanceof ParenthesisExpression) {
				// do nothing
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
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setItalicByDefault(true);
	}

	@Override
	public String getDisplayName() {
		return Messages.FunctionHighlighting_0;
	}
}
