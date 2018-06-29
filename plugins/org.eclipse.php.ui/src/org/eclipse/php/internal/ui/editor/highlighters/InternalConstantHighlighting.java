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

import org.eclipse.php.core.ast.nodes.Scalar;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class InternalConstantHighlighting extends AbstractSemanticHighlighting {

	protected class InternalConstantApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Scalar scalar) {
			if (scalar.getScalarType() == Scalar.TYPE_SYSTEM) {
				highlight(scalar);
			}
			return true;
		}
	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new InternalConstantApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setItalicByDefault(true).setBoldByDefault(true).setDefaultTextColor(0, 0,
				192);
	}

	@Override
	public String getDisplayName() {
		return Messages.InternalConstantHighlighting_0;
	}

	@Override
	public int getPriority() {
		return 110;
	}
}
