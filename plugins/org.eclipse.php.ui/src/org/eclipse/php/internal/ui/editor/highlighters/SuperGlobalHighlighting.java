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

import org.eclipse.php.core.ast.nodes.ASTNodes;
import org.eclipse.php.core.ast.nodes.Identifier;
import org.eclipse.php.core.ast.nodes.Variable;
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
