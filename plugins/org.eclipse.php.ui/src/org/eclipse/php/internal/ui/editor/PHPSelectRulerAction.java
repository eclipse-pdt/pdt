/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
public class PHPSelectRulerAction extends AbstractRulerActionDelegate {

	@Override
	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		return new PHPSelectAnnotationRulerAction(PHPUIMessages.getResourceBundle(), "PhpSelectAnnotationRulerAction.", //$NON-NLS-1$
				editor, rulerInfo);
	}
}
