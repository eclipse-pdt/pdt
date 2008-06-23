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
package org.eclipse.php.internal.core.phpModel;

public interface IPHPModelExtension {

	/**
	 * Returns path to the directory that contains language model PHP files
	 * @return file path
	 */
	public String getDirectory();

	/**
	 * Returns whether this extension should be enabled
	 * @return whether this extension is enabled
	 */
	public boolean isEnabled();

	/**
	 * Returns PHP version that this language model extension is applicable
	 * @return PHP version
	 */
	public String getPHPVersion();
}
