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

import org.eclipse.php.internal.core.ast.nodes.Expression;
import org.eclipse.php.internal.core.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.ast.nodes.SingleFieldDeclaration;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class FieldHighlighting extends AbstractSemanticHighlighting {

	protected class FielApply extends AbstractSemanticApply {
		@Override
		public boolean visit(SingleFieldDeclaration fieldDecl) {
			highlight(fieldDecl.getName());
			return true;
		}

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			if (!fieldAccess.getField().isDollared()) {
				Expression expr = fieldAccess.getField().getName();
				if (expr instanceof Variable) {
					Variable var = (Variable) expr;
					if (!var.isDollared()) {
						highlight(var);
					}
				} else {
					highlight(expr);
				}

			}
			return true;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new FielApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setDefaultTextColor(0, 0, 192);
	}

	public String getDisplayName() {
		return "Fields";
	}
}
