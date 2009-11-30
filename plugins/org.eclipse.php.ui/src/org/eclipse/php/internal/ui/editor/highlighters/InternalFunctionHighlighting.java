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
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.*;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.swt.graphics.RGB;

public class InternalFunctionHighlighting extends AbstractSemanticHighlighting {

	protected class InternalFunctionApply extends AbstractSemanticApply {

		public boolean visit(FunctionInvocation functionInvocation) {
			final Expression functionName = functionInvocation
					.getFunctionName().getName();
			final int invocationParent = functionInvocation.getParent()
					.getType();
			if ((functionName.getType() == ASTNode.IDENTIFIER || functionName
					.getType() == ASTNode.NAMESPACE_NAME)
					&& invocationParent != ASTNode.STATIC_METHOD_INVOCATION) {
				if (functionName instanceof Identifier) {
					String name = ((Identifier) functionName).getName();
					if (isInternalFunction(functionInvocation.getFunctionName())) {
						highlight(functionName);
					}
				}
				//
			}
			return true;
		}

		private boolean isInternalFunction(FunctionName functionName) {
			try {
				ISourceModule module = getSourceModule();
				IModelElement[] elements = module.codeSelect(functionName
						.getStart(), functionName.getLength());
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
		return new InternalFunctionApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setDefaultTextColor(new RGB(0, 0, 192));
	}

	public String getDisplayName() {
		return "Internal functions";
	}

	@Override
	public int getPriority() {
		return 110;
	}
}