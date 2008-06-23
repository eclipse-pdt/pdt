/*******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Zend and IBM - Initial implementation
 *******************************************************************************/
package org.eclipse.php.ui.editor.hover;

import org.eclipse.dltk.ui.text.hover.IScriptEditorTextHover;

/**
 * An extention of the ITextHover which has the ability to attach an IEditorPart to the implementing class.
 * 
 * @author shalom
 */
public interface IPHPTextHover extends IScriptEditorTextHover {

	/**
	 * Returns the text hover message decorator.
	 * 
	 * @return text hover message decorator, or <code>null</code> if not defined
	 */
	public IHoverMessageDecorator getMessageDecorator();
}
