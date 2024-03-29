/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.php.core.ast.nodes.EnumCaseDeclaration;
import org.eclipse.php.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.core.ast.nodes.Variable;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class StaticFieldHighlighting extends AbstractSemanticHighlighting {

	protected class StaticFieldApply extends AbstractSemanticApply {

		private boolean visitField = false;

		@Override
		public boolean visit(FieldsDeclaration fieldsDeclaration) {
			if ((fieldsDeclaration.getModifier() & Modifiers.AccStatic) != 0) {
				for (Variable var : fieldsDeclaration.getVariableNames()) {
					highlight(var);
				}
			}
			return true;
		}

		@Override
		public boolean visit(EnumCaseDeclaration fieldsDeclaration) {
			highlight(fieldsDeclaration.getName());
			return true;
		}

		@Override
		public boolean visit(StaticFieldAccess fieldAccess) {
			visitField = true;
			fieldAccess.getMember().accept(this);
			return false;
		}

		@Override
		public boolean visit(Variable var) {
			if (visitField && var.isDollared()) {
				highlight(var);
				visitField = false;
			}
			return true;
		}

		@Override
		public void endVisit(StaticFieldAccess fieldAccess) {
			visitField = false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new StaticFieldApply();
	}

	@Override
	public int getPriority() {
		return 110;
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setItalicByDefault(true).setDefaultTextColor(0, 0, 192);
	}

	@Override
	public String getDisplayName() {
		return Messages.StaticFieldHighlighting_0;
	}
}
