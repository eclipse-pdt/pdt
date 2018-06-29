/*******************************************************************************
 * Copyright (c) 2006, 2018 Zend Corporation and IBM Corporation.
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

import org.eclipse.php.core.ast.nodes.*;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class SuperGlobalHighlighting extends AbstractSemanticHighlighting {
	protected class SuperGlobalApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Variable var) {
			if (isSuperGlobal(var)) {
				highlight(var);
			}
			return true;
		}

		@Override
		public boolean visit(SingleFieldDeclaration singleFieldDeclaration) {
			// only handle property values (but not the property names)
			if (singleFieldDeclaration.getValue() != null) {
				singleFieldDeclaration.getValue().accept(this);
			}
			return false;
		}

		@Override
		public boolean visit(StaticFieldAccess staticMember) {
			// only handle class names (but not the static property names)
			staticMember.getClassName().accept(this);
			return false;
		}

		private boolean isSuperGlobal(Variable var) {
			if ((var.isDollared() || ASTNodes.isQuotedDollaredCurlied(var)) && var.getName() instanceof Identifier) {
				String name = "$" //$NON-NLS-1$
						+ ((Identifier) var.getName()).getName();
				return PHPVariables.isVariable(name, var.getAST().apiLevel());
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new SuperGlobalApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setDefaultTextColor(127, 0, 85).setBoldByDefault(true);
	}

	@Override
	public String getDisplayName() {
		return Messages.SuperGlobalHighlighting_0;
	}
}
