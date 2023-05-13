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

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.php.core.ast.nodes.NamedExpression;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;

public class NamedExpressionHighlighting extends AbstractSemanticHighlighting {

	protected class NamedExpressionApply extends AbstractSemanticApplyWithNS {

		public NamedExpressionApply(ISourceModule sourceModule) {
			super(sourceModule);
		}

		@Override
		public boolean visit(NamedExpression namedExpression) {
			highlight(namedExpression.getStart(), namedExpression.getName().length());
			return true;
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new NamedExpressionApply(getSourceModule());
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setBoldByDefault(false).setItalicByDefault(false)
				.setDefaultTextColor(new RGB(235, 75, 100));
	}

	@Override
	public String getDisplayName() {
		return Messages.NamedParameterHighlighting_0;
	}

}
