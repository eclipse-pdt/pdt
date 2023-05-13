/*******************************************************************************
 * Copyright (c) 2006-2019 Zend Corporation and IBM Corporation.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   William Candillon {wcandillon@gmail.com} - Initial implementation
 *   Dawid Pakuła [469267]
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.highlighters;

import org.eclipse.php.core.ast.nodes.Attribute;
import org.eclipse.php.core.ast.nodes.AttributeGroup;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;

public class AttributeHighlighting extends AbstractSemanticHighlighting {

	protected class AttributeApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Attribute attribute) {
			highlight(attribute.getAttributeName());
			return false;
		}

		@Override
		public boolean visit(AttributeGroup node) {
			highlight(node.getStart(), 2);
			highlight(node.getEnd() - 1, 1);

			return true;
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new AttributeApply();
	}

	@Override
	protected void initDefaultPreferences() {
		getStyle().setEnabledByDefault(true).setBoldByDefault(true).setItalicByDefault(false).setDefaultTextColor(85,
				85, 85);
	}

	@Override
	public String getDisplayName() {
		return Messages.AttributeHighlighting_0;
	}

	@Override
	public int getPriority() {
		return 250;
	}
}