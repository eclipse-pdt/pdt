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
import org.eclipse.dltk.core.index2.search.ISearchEngine.MatchRule;
import org.eclipse.php.core.ast.nodes.*;
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
		@Override
		public boolean visit(FunctionInvocation functionInvocation) {
			final Expression functionName = functionInvocation.getFunctionName().getName();
			final int invocationParent = functionInvocation.getParent().getType();
			if ((functionName.getType() == ASTNode.IDENTIFIER || functionName.getType() == ASTNode.NAMESPACE_NAME)
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
			IModelElement[] elements = findMethods(identifier, fCurrentNamespace);
			if (elements == null || elements.length == 0) {
				elements = findMethods(identifier, null);
			}
			if (elements != null && elements.length > 0 && elements[0] != null) {
				for (IModelElement modelElement : elements) {
					if (modelElement != null) {
						if (ModelUtils.isExternalElement(modelElement)) {
							highlight(identifier);
							return;
						}
					}
				}
			}
		}

		private IModelElement[] findMethods(Identifier identifier, NamespaceDeclaration namespace) {
			String fullName = AbstractOccurrencesFinder.getFullName(identifier, fLastUseParts, namespace);
			return PhpModelAccess.getDefault().findMethods(fullName, MatchRule.EXACT, 0, 0, createSearchScope(), null);
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new InternalFunctionApply(getSourceModule());
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(new RGB(0, 0, 192));
	}

	@Override
	public String getDisplayName() {
		return Messages.InternalFunctionHighlighting_0;
	}

	@Override
	public int getPriority() {
		return 110;
	}
}