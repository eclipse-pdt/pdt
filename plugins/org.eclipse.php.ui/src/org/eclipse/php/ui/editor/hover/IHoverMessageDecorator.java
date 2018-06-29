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
package org.eclipse.php.ui.editor.hover;

/**
 * Decorates hover message
 * 
 * @author eden
 */
public interface IHoverMessageDecorator {

	/**
	 * Gets the hover message and returns the decorated format
	 * 
	 * @param msg
	 *            (can be null)
	 */
	public String getDecoratedMessage(String msg);

}
