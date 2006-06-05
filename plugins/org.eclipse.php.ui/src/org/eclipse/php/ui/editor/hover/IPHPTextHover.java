/*******************************************************************************
 * Copyright (c) 2006 Zend Corporation and IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor.hover;

import org.eclipse.jface.text.ITextHover;
import org.eclipse.ui.IEditorPart;

/**
 * An extention of the ITextHover which has the ability to attach an IEditorPart to the implementing class.
 * 
 * @author shalom
 */
public interface IPHPTextHover extends ITextHover {

	/**
	 * Sets the IEditorPart for this decorator. 
	 * 
	 * @param editorPart
	 */
	public void setEditorPart(IEditorPart editorPart);

	/**
	 * Returns the IEditorPart that is assigned to this decorator.
	 * 
	 * @return An IEditorPart.
	 */
	public IEditorPart getEditorPart();

}
