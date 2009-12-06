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

public class FieldHighlighting extends AbstractSemanticHighlighting {

	protected class FielApply extends AbstractSemanticApply {

		private int visitField = 0;

		@Override
		public boolean visit(SingleFieldDeclaration fieldDecl) {
			highlight(fieldDecl.getName());
			return true;
		}

		@Override
		public boolean visit(FunctionInvocation method) {
			boolean visit = !(visitField > 0);
			if (!visit) {
				for (Expression parameter : method.parameters()) {
					parameter.accept(this);
				}
			}
			return visit;
		}

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			visitField++;
			return true;
		}

		public boolean visit(Variable var) {
			if (visitField > 0 && !var.isDollared()) {
				highlight(var);
			}
			return true;
		}

		@Override
		public void endVisit(FieldAccess fieldAccess) {
			visitField--;
		}

		@Override
		public void endVisit(Program program) {
			if (visitField < 0) {
				throw new IllegalStateException("visitField is negative: "
						+ visitField);
			}
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
