/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Zend Technologies
 *******************************************************************************/
package org.eclipse.php.internal.ui.editor.contentassist;

import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.ui.IEditorPart;

public class PHPContentAssistInvocationContext extends
		ScriptContentAssistInvocationContext {

	private boolean explicit;

	public PHPContentAssistInvocationContext(ITextViewer viewer, int offset,
			IEditorPart editor, String natureId, boolean explicit) {
		super(viewer, offset, editor, natureId);
		this.explicit = explicit;
	}

	public boolean isExplicit() {
		return explicit;
	}
}
