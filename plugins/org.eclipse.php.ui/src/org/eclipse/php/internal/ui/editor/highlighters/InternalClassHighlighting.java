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

import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.Logger;
import org.eclipse.php.internal.core.ast.nodes.Identifier;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.php.internal.ui.editor.highlighter.ModelUtils;
import org.eclipse.swt.graphics.RGB;

public class InternalClassHighlighting extends AbstractSemanticHighlighting {

	protected class InternalClassApply extends AbstractSemanticApply {

		@Override
		public boolean visit(Identifier identifier) {
			if (isInternalClass(identifier)) {
				highlight(identifier);
			}
			return true;
		}

	}

	@Override
	public AbstractSemanticApply getSemanticApply() {
		return new InternalClassApply();
	}

	@Override
	public void initDefaultPreferences() {
		getStyle().setEnabledByDefault(false).setDefaultTextColor(
				new RGB(0, 0, 192));
	}

	public String getDisplayName() {
		return "Internal classes";
	}

	@Override
	public int getPriority() {
		return 110;
	}

	private boolean isInternalClass(Identifier type) {
		try {
			ISourceModule module = getSourceModule();
			IModelElement[] elements = module.codeSelect(type.getStart(), type
					.getLength());
			if (elements != null && elements.length == 1 && elements[0] != null) {
				IModelElement element = (IModelElement) elements[0];
				return ModelUtils.isExternalElement(element);
			}
		} catch (ModelException e) {
			Logger.logException(e);
		}
		return false;
	}
}
