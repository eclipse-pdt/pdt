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
package org.eclipse.php.internal.ui.text.correction;

/**
 * Correction proposals implement this interface to by invokable by a command.
 * (e.g. keyboard shortcut)
 */
public interface ICommandAccess {

	/**
	 * Returns the id of the command that should invoke this correction proposal
	 * 
	 * @return the id of the command.
	 */
	String getCommandId();

}
