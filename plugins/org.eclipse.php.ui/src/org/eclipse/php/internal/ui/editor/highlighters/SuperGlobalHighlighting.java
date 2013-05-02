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

import org.eclipse.php.internal.core.PHPVersion;
import org.eclipse.php.internal.core.ast.nodes.ArrayAccess;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.core.ast.nodes.Variable;
import org.eclipse.php.internal.core.ast.nodes.VariableBase;
import org.eclipse.php.internal.core.language.PHPVariables;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class SuperGlobalHighlighting extends AbstractSemanticHighlighting {
	protected class SuperGlobalApply extends AbstractSemanticApply {

		public boolean visit(ArrayAccess n) {
			if (isSuperGlobal(n.getName())) {
				highlight(n.getName());
			}
			return true;
		}

		private boolean isSuperGlobal(VariableBase n) {
			if (n instanceof Variable
					&& ((Variable) n).getName() instanceof Identifier) {
				String name = "$" //$NON-NLS-1$
						+ ((Identifier) ((Variable) n).getName()).getName();
				String[] globals = PHPVariables.getVariables(PHPVersion.PHP5_3);
				for (String global : globals) {
					if (global.equals(name)) {
						return true;
					}
				}
			}
			return false;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new SuperGlobalApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(127, 0, 85)
				.setBoldByDefault(true);
	}

	public String getDisplayName() {
		return Messages.SuperGlobalHighlighting_0;
	}
}
