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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.core.model.PhpModelAccess;
import org.eclipse.php.internal.core.search.AbstractOccurrencesFinder;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.swt.graphics.RGB;

public class InternalFunctionHighlighting extends AbstractSemanticHighlighting {

	protected class InternalFunctionApply extends AbstractSemanticApplyWithNS {

		public InternalFunctionApply(ISourceModule sourceModule) {
			super(sourceModule);
		}

		/**
		 * skip static call invocation, and add to changes list the global calls
		 */
		public boolean visit(FunctionInvocation functionInvocation) {
			final Expression functionName = functionInvocation
					.getFunctionName().getName();
			final int invocationParent = functionInvocation.getParent()
					.getType();
			if ((functionName.getType() == ASTNode.IDENTIFIER || functionName
					.getType() == ASTNode.NAMESPACE_NAME)
					&& invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
				final Identifier identifier = (Identifier) functionName;
				dealIdentifier(identifier);
			}
			return true;
		}

		/**
		 * @param identifier
		 */
		private void dealIdentifier(Identifier identifier) {
			String fullName = AbstractOccurrencesFinder.getFullName(identifier,
					fLastUseParts, fCurrentNamespace);
			IModelElement[] elements = PhpModelAccess.getDefault().findMethods(
					fullName, MatchRule.EXACT, 0, 0, createSearchScope(), null);
			if (elements != null && elements.length == 1 && elements[0] != null) {
				if (ModelUtils.isExternalElement(elements[0])) {
					highlight(identifier);
				}
			}
			// nodeToFullName.put(identifier, fullName);
		}

		// public boolean visit(FunctionInvocation functionInvocation) {
		// final Expression functionName = functionInvocation
		// .getFunctionName().getName();
		// final int invocationParent = functionInvocation.getParent()
		// .getType();
		// if ((functionName.getType() == ASTNode.IDENTIFIER || functionName
		// .getType() == ASTNode.NAMESPACE_NAME)
		// && invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
		// if (functionName instanceof Identifier) {
		// if (isInternalFunction(functionInvocation.getFunctionName())) {
		// highlight(functionName);
		// }
		// }
		// //
		// }
		// return true;
		// }

		private boolean isInternalFunction(FunctionName functionName) {
			try {
				ISourceModule module = getSourceModule();
				IModelElement[] elements = module.codeSelect(
						functionName.getStart(), functionName.getLength());
				if (elements.length == 1 && elements[0] != null) {
					IModelElement element = (IModelElement) elements[0];
					return ModelUtils.isExternalElement(element);
				}
			} catch (ModelException e) {
				Logger.logException(e);
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new InternalFunctionApply(getSourceModule());
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(
				new RGB(0, 0, 192));
	}

	public String getDisplayName() {
		return "Internal functions";
	}

	@Override
	public int getPriority() {
		return 110;
	}
}