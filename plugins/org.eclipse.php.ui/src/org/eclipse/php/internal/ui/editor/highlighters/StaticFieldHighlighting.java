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

import org.eclipse.dltk.ast.Modifiers;
import org.eclipse.dltk.core.Flags;
import org.eclipse.dltk.core.IField;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.FieldAccess;
import org.eclipse.php.internal.core.ast.nodes.FieldsDeclaration;
import org.eclipse.php.internal.core.ast.nodes.StaticConstantAccess;
import org.eclipse.php.internal.core.ast.nodes.StaticFieldAccess;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;

public class StaticFieldHighlighting extends AbstractSemanticHighlighting {

	protected class StaticFieldApply extends AbstractSemanticApply {

		/**
		 * Mark CON on: MyClass::CON;
		 */
		@Override
		public boolean visit(StaticConstantAccess classConstantAccess) {
			highlight(classConstantAccess.getConstant());
			return true;
		}

		/**
		 * Mark var on: MyClass::var;
		 */
		@Override
		public boolean visit(StaticFieldAccess fieldAccess) {
			highlight(fieldAccess.getField());
			return super.visit(fieldAccess);
		}

		@Override
		public boolean visit(FieldsDeclaration fieldsDeclaration) {
			if ((fieldsDeclaration.getModifier() & Modifiers.AccStatic) != 0) {

				for (int i = 0; i < fieldsDeclaration.getVariableNames().length; i++) {
					highlight(fieldsDeclaration.getVariableNames()[i]);
				}

			}
			return super.visit(fieldsDeclaration);
		}

		@Override
		public boolean visit(FieldAccess fieldAccess) {
			IField field = ModelUtils.getField(fieldAccess);
			if (field != null) {
				try {
					if (Flags.isStatic(field.getFlags())) {
						highlight(fieldAccess.getMember());
					}
				} catch (ModelException e) {
					Logger.logException(e);
				}
			}
			return true;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new StaticFieldApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setItalicByDefault(true).setDefaultTextColor(0, 0, 192);
	}

	@Override
	public int getPriority() {
		return 100;
	}

	public String getDisplayName() {
		return "Static fields";
	}
}
