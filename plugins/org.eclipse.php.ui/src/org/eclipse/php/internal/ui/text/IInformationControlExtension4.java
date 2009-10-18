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
package org.eclipse.php.internal.ui.text;

/**
 * Extension interface for {@link org.eclipse.jface.text.IInformationControl}.
 * Adds API which allows to set this information control's status field text.
 * 
 * @see org.eclipse.jface.text.IInformationControl
 * @since 3.2
 */
public interface IInformationControlExtension4 {

	/**
	 * Sets the text of the status field.
	 * <p>
	 * The implementor can specify whether the new text affects an already
	 * visible information control.
	 * </p>
	 * 
	 * @param statusFieldText
	 *            the text to be used in the optional status field or
	 *            <code>null</code> if the status field should be hidden
	 * @since 3.2
	 */
	public void setStatusText(String statusFieldText);
}
