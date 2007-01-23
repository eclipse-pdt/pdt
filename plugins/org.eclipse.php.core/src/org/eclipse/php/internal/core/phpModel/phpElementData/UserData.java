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
package org.eclipse.php.internal.core.phpModel.phpElementData;

public interface UserData {

	/**
	 * Returns the name of the file.
	 */
	public String getFileName();

	/**
	 * Returns the start position of the user data in the file.
	 */
	public int getStartPosition();

	/**
	 * Returns the end position of the user data in the file.
	 */
	public int getEndPosition();

	/**
	 * Returns the stop position of the user data in the file.
	 */
	public int getStopPosition();

	/**
	 * Returns the stop line of the user data in the file.
	 */
	public int getStopLine();

}