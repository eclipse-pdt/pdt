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
package org.eclipse.php.internal.ui.actions;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.action.Action;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.php.internal.ui.editor.PHPStructuredEditor;

/**
 * This Action is in charge of activating the Goto Mathing Bracket function of
 * the PHP editor
 * 
 * @author yaronm
 * 
 */
public class GotoMatchingBracketAction extends Action {

	public final static String GOTO_MATCHING_BRACKET = "GotoMatchingBracket"; //$NON-NLS-1$

	private final PHPStructuredEditor fEditor;

	public GotoMatchingBracketAction(PHPStructuredEditor editor) {
		super(PHPUIMessages.GotoMatchingBracket_label);
		Assert.isNotNull(editor);
		fEditor = editor;
		setEnabled(true);
	}

	public void run() {
		fEditor.gotoMatchingBracket();
	}
}
