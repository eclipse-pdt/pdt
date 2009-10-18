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
/**
 * 
 */
package org.eclipse.php.internal.ui.editor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.php.internal.ui.PHPUIMessages;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * PHP select ruler action that generates a PhpSelectAnnotationRulerAction for
 * the annotations selection.
 * 
 * @author Shalom
 */
public class PhpSelectRulerAction extends AbstractRulerActionDelegate {

	protected IAction createAction(ITextEditor editor,
			IVerticalRulerInfo rulerInfo) {
		return new PhpSelectAnnotationRulerAction(PHPUIMessages
				.getResourceBundle(),
				"PhpSelectAnnotationRulerAction.", editor, rulerInfo); //$NON-NLS-1$
	}
}
